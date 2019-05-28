package com.zb.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.redis.RK;
import com.zb.common.utils.DateUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.article.Article;
import com.zb.models.goods.BaseGoods;
import com.zb.service.jobs.RankListJob;
import com.zb.service.jobs.StJob;

@Service
public class RankListService extends BaseService {
	static final Logger log = LoggerFactory.getLogger(RankListService.class);

	@Autowired
	UserService userService;

	public static int ALL = 0;
	public static int DAY = 1;
	public static int WEEK = 7;
	public static int MONTH = 30;
	public static int YESTERDAY = -1;

	public static final int CHARM = 0;
	public static final int RICH = 1;

	public static final int PAGE = 1;
	public static final int SIZE = 30;

	/*
	 * 查询条件 day 日期类型 0全部 1天 7周 30月 type 榜单类型 魅力榜 富豪榜
	 */
	public ReMsg queryRankList(Integer day, Integer bgId, int type, Integer page, Integer size) {
		return new ReMsg(ReCode.OK, query(getDay(day), bgId, PAGE, SIZE, type, false));
	}

	public Page<DBObject> query(Integer day, Integer bgId, Integer page, Integer size, Integer type, boolean admin) {
		String docName = null;
		if (null != type && type == RICH) {// 富豪榜
			if (null == bgId || bgId == 0) {// 查询所有支出
				bgId = 0;
				docName = DocName.SEND_TOTAL;
			} else {// 针对bgId查询
				docName = DocName.GIFT_SEND;
			}
		} else {// 魅力榜
			type = CHARM;
			if (null == bgId || bgId == 0) {// 所有收入
				bgId = 0;
				docName = DocName.RECV_TOTAL;
			} else {// 针对bgId查询
				docName = DocName.GIFT_RECV;
			}
		}
		ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();
		String key = type + "-" + bgId + "-" + day;
		String objects = opsv.get(RK.keyRankListFlower(key));
		if (objects != null) {
			List<DBObject> curL = (List<DBObject>) JSON.parse(objects);
			return new Page<DBObject>(admin ? count(day, bgId, docName) : curL.size(), size, page, curL);
		}
		// 查询数据库
		List<DBObject> dbos = find(day, bgId, page, size, docName);
		if (null == dbos || dbos.size() == 0) {
			return new Page<DBObject>(0, size, page, dbos);
		}
		for (DBObject dbo : dbos) {
			// 基本信息
			DBObject user = userService.findById(DboUtil.getLong(dbo, "uid"));
			dbo.put("nickname", DboUtil.getString(user, "nickname"));
			dbo.put("avatar", userService.toMaxAvatar(user));
			dbo.put("point", DboUtil.getInt(user, "point"));
			dbo.put("vip", DboUtil.getInt(user, "vip"));
			dbo.put("sex", DboUtil.getInt(user, "sex"));
			dbo.put("cover", DboUtil.getString(user, "cover"));
		}
		// key 使用拼接 docName + "-" + type + "-" + day
		if (admin) {
			return new Page<DBObject>(count(day, bgId, docName), size, page, dbos);
		}
		if (day == getDay(YESTERDAY)) {// 昨日花魁
			long time = DateUtil.getTodayZeroTime() + Const.DAY - System.currentTimeMillis();
			opsv.set(RK.keyRankListFlower(key), JSON.serialize(dbos), time, TimeUnit.MILLISECONDS);
		} else {
			opsv.set(RK.keyRankListFlower(key), JSON.serialize(dbos), 30, TimeUnit.SECONDS);
		}
		return new Page<DBObject>(size, size, page, dbos);
	}

	// 获取榜单类型 day 对应的数据库查询条件day
	public static int getDay(Integer day) {
		if (day == ALL) {
			return 0;
		}
		Calendar c = Calendar.getInstance();
		if (day == YESTERDAY) {
			c.add(Calendar.DATE, -1);
		}
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DAY_OF_MONTH);

		if (null == day || day == DAY || day == YESTERDAY) {
			return StJob.getDay(y, m, d);
		}
		if (day == WEEK) {
			int w = c.get(Calendar.WEEK_OF_YEAR);
			return y * 100 + w;
		}
		if (day == MONTH) {
			return y * 100 + m;
		}

		return -1;
	}
	
	public static void main(String [] args){
		System.out.println(getDay(DAY));
	}

	// 物品id 榜单类型 type 0 1 7 30 2 具体日期day 数据库名 docName
	public List<DBObject> find(Integer day, Integer bgId, Integer page, Integer size, String docName) {
		DBObject q = new BasicDBObject();
		if (day != null) {
			q.put("day", day);
		}
		if (null != bgId && bgId != 0) {
			q.put("bgId", bgId);
		}
		return getC(docName).find(q).sort(new BasicDBObject("count", -1).append("uid", 1)).skip(getStart(page, size))
				.limit(getSize(size)).toArray();
	}

	private Integer count(Integer day, Integer bgId, String docName) {
		DBObject q = new BasicDBObject();
		if (null != day) {
			q.put("day", day);
		}
		if (null != bgId && bgId != 0) {
			q.put("bgId", bgId);
		}
		return getC(docName).find(q).count();
	}

	/** 取昨日花魁榜八名 */
	public List<DBObject> get8Flower() {
		List<DBObject> ret = null;
		int day = getDay(YESTERDAY);
		List<DBObject> temp = this.query(day, BaseGoods.Gift.FLOWER.getV().getId(), 1, 30, CHARM, false).getList();
		if (null != temp && !temp.isEmpty()) {
			Collections.shuffle(temp);
			ret = temp.subList(0, temp.size() < 8 ? temp.size() : 8);
		}
		return ret;
	}

	/** 获取情人节期间的收花送花排行榜 */
	public List<DBObject> getValentineFlowerTop(Integer rankType, Integer size) {
		int start = RankListJob.VALENTINE_DAY_START;
		int end = RankListJob.VALENTINE_DAY_END;
		rankType = rankType == null ? CHARM : rankType;
		size = null == size ? 10 : size;
		ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();
		String key = start + "-" + end + "-" + rankType + "-" + size;
		String objects = opsv.get(RK.keyRankListFlower(key));
		if (objects != null) {
			return (List<DBObject>) JSON.parse(objects);
		}
		List<DBObject> dbos = getDateRangeFlowerTop(start, end, rankType, size);
		if (null == dbos || dbos.size() == 0) {
			return dbos;
		}
		for (DBObject dbo : dbos) {
			// 基本信息
			DBObject user = userService.findByIdSafe(DboUtil.getLong(dbo, "_id"));
			dbo.put("nickname", DboUtil.getString(user, "nickname"));
			dbo.put("avatar", userService.toMaxAvatar(user));
			dbo.put("point", DboUtil.getInt(user, "point"));
			dbo.put("vip", DboUtil.getInt(user, "vip"));
			dbo.put("sex", DboUtil.getInt(user, "sex"));
		}
		opsv.set(RK.keyRankListFlower(key), JSON.serialize(dbos), 10, TimeUnit.MINUTES);
		return dbos;
	}

	/** 获取昨天圣诞圈子文章top */
	public List<DBObject> getChristmasArticleFlowerTop(String topic, long groupId, int cnt) {
		List<BasicDBObject> pipeline = new ArrayList<BasicDBObject>();
		// 发布时间大于昨天凌晨 小于今天凌晨
		BasicDBObject q = new BasicDBObject("topic", topic).append("groupId", groupId)
				.append("status", new BasicDBObject("$gt", Article.DOWN))
				.append("publishTime", new BasicDBObject("$gt", System.currentTimeMillis() - Const.DAY * 1)
						.append("$lt", System.currentTimeMillis()));
		pipeline.add(new BasicDBObject("$sort", new BasicDBObject("flower", -1).append("_id", 1)));
		pipeline.add(new BasicDBObject("$match", q));
		pipeline.add(new BasicDBObject("$group",
				new BasicDBObject("_id", "$userId").append("articleId", new BasicDBObject("$first", "$_id"))
						.append("title", new BasicDBObject("$first", "$title")).append("flower",
								new BasicDBObject("$max", "$flower"))));
		pipeline.add(new BasicDBObject("$sort", new BasicDBObject("flower", -1).append("_id", 1)));
		pipeline.add(new BasicDBObject("$limit", cnt));
		Iterable<DBObject> dbos = getC(DocName.ARTICLE).aggregate(pipeline).results();
		return IteratorUtils.toList(dbos.iterator());
	}

	// 获取指定日期时间内的鲜花送花收花排行榜
	public List<DBObject> getDateRangeFlowerTop(int start, int end, Integer type, Integer size) {
		String docName = null;
		if (null != type && type == RICH) {// 富豪榜
			docName = DocName.GIFT_SEND;
		} else {// 魅力榜
			docName = DocName.GIFT_RECV;
		}
		// 取这两个日期之间的送花和收花记录的和 bgId=20001, 20171222<= day <=20171224
		List<BasicDBObject> pipeline = new ArrayList<BasicDBObject>();
		BasicDBObject q = new BasicDBObject("bgId", BaseGoods.Gift.FLOWER.getV().getId()).append("day",
				new BasicDBObject("$gte", start).append("$lte", end));
		pipeline.add(new BasicDBObject("$match", q));
		pipeline.add(new BasicDBObject("$group",
				new BasicDBObject("_id", "$uid").append("count", new BasicDBObject("$sum", "$count"))));
		pipeline.add(new BasicDBObject("$sort", new BasicDBObject("count", -1).append("_id", 1)));
		pipeline.add(new BasicDBObject("$limit", size));
		// 送花
		Iterable<DBObject> dbos = getC(docName).aggregate(pipeline).results();
		return IteratorUtils.toList(dbos.iterator());
	}

}
