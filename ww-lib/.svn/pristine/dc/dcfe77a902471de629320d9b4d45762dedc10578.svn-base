package com.zb.service;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.DateUtil;
import com.zb.common.utils.T2TUtil;
import com.zb.core.Page;
import com.zb.models.DocName;
import com.zb.models.st.GiftRecvTop;

@Service
public class GiftRecvTopService extends BaseService {
	static final Logger log = LoggerFactory.getLogger(GiftRecvTopService.class);

	@Autowired
	UserService userService;
	@Autowired
	RankListService rankListService;

	/** 存储该天花魁到名人堂 */
	public void save2GiftRecvTop(DBObject giftRecv, DBObject user) {
		Long uid = DboUtil.getLong(giftRecv, "uid");
		Long count = DboUtil.getLong(giftRecv, "count");
		Long amount = DboUtil.getLong(giftRecv, "amount");
		Integer bgId = DboUtil.getInt(giftRecv, "bgId");
		// 7周花魁 1普通花魁
		Integer type = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == 1 ? 7 : 1;
		Integer day = DboUtil.getInt(giftRecv, "day");
		String avatar = userService.toMaxAvatar(user);
		String nickname = DboUtil.getString(user, "nickname");
		String personLabel = DboUtil.getString(user, "personLabel");
		int sex = DboUtil.getInt(user, "sex");
		GiftRecvTop giftRecvTop = new GiftRecvTop(uid, bgId, type, day, count, amount, avatar, nickname, personLabel,
				sex);
		giftRecvTop.set_id(super.getNextId(DocName.GIFTRECVTOP));
		super.getMongoTemplate().save(giftRecvTop);
	}

	public DBObject findByDay(int day) {
		return getC(DocName.GIFTRECVTOP).findOne(new BasicDBObject("day", day));
	}

	public List<DBObject> find(Integer page, Integer size) {
		return getC(DocName.GIFTRECVTOP).find().skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("day", -1)).toArray();
	}

	public List<DBObject> findLastMonth() {
		String date = DateUtil.dateFormatyyyyMMdd(DateUtil.getMonthFirstDay());
		String stDate = DateUtil.dateFormatyyyyMMdd(DateUtil.getLastMonthFirstDay());
		long st = T2TUtil.str2Long(stDate);
		long et = T2TUtil.str2Long(date);
		DBObject q = new BasicDBObject("day", new BasicDBObject("$gte", st).append("$lt", et));
		return getC(DocName.GIFTRECVTOP).find(q).toArray();
	}

	/** 花魁名人堂 */
	public Page<DBObject> queryGiftRecvTop(Integer page, Integer size) {
		page = super.getPage(page);
		size = super.getSize(size);
		// String key = DateUtil.getTodayZeroTime() + "";
		List<DBObject> dbos = find(page, size);
		return new Page<DBObject>(0, size, page, dbos);
	}

	public DBObject recvTop1() {
		int day = RankListService.getDay(-1);
		DBObject dbo = findByDay(day);
		if (null != dbo && !dbo.containsField("sex")) {
			Long uid = DboUtil.getLong(dbo, "uid");
			if (null != uid) {
				DBObject user = userService.findByIdSafe(uid);
				dbo.put("sex", DboUtil.getInt(user, "sex"));
			}
		}
		return dbo;
	}
}
