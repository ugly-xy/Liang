package com.zb.service;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import com.zb.models.AppCover;
import com.zb.models.DocName;

@Service
public class AppCoverService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(AppCoverService.class);

	@Autowired
	UserService userService;

	public DBObject findById(Long _id) {
		return getC(DocName.APPCOVER).findOne(new BasicDBObject("_id", _id));
	}

	/** save */
	public void save(AppCover appCover) {
		appCover.setUpdateTime(System.currentTimeMillis());
		super.getMongoTemplate().save(appCover);
	}

	/** 后台upset */
	public ReMsg adminUpSet(long _id, Long uid, String nickname, String url, long adminId) {
		this.save(new AppCover(_id, uid, nickname, url, adminId));
		super.getRedisTemplate().delete(RK.keyAppCover(_id));
		return new ReMsg(ReCode.OK);
	}

	/** 后台查询 */
	public Page<DBObject> query(Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = this.find(page, size);
		int count = this.count();
		return new Page<DBObject>(count, size, page, dbos);
	}

	/** 后台查询 */
	public List<DBObject> find(Integer page, Integer size) {
		return getC(DocName.APPCOVER).find().skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
	}

	/** 后台查询 */
	public int count() {
		return getC(DocName.APPCOVER).find().count();
	}

	/** 后台删除 */
	public ReMsg del(Long id) {
		super.getC(DocName.APPCOVER).remove(new BasicDBObject("_id", id));
		return new ReMsg(ReCode.OK);
	}

	/** save */
	public void saveAppCover(long uid) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(DateUtil.getWeekZeroTime() + Const.DAY * 7);// 下周时间
		long day = c.get(Calendar.YEAR) * 100 + c.get(Calendar.WEEK_OF_YEAR);
		DBObject dbo = findById(day);
		if (null == dbo) {// 下周数据为空
			DBObject user = userService.findById(uid);
			this.save(new AppCover(day, uid, DboUtil.getString(user, "nickname"), userService.toMaxAvatar(user), 0));
		}
	}

	/** 获取本周启动封面 */
	public DBObject queryAppCover(Long day) {
		if (day == null) {// 为空默认查询本周
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(DateUtil.getWeekZeroTime());
			day = (long) (c.get(Calendar.YEAR) * 100 + c.get(Calendar.WEEK_OF_YEAR));// 本周日期201746
		}
		ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();
		String objects = opsv.get(RK.keyAppCover(day));
		if (objects != null) {
			return (DBObject) JSON.parse(objects);
		}
		DBObject dbo = findById(day);
		if (null != dbo) {
			// 存储缓存
			opsv.set(RK.keyAppCover(day), JSON.serialize(dbo), 7, TimeUnit.DAYS);
		}
		return dbo;
	}

	public static void main(String[] args) {
		// Calendar c = Calendar.getInstance();
		// c.set(Calendar.HOUR_OF_DAY, 0);
		// c.set(Calendar.MINUTE, 0);
		// c.set(Calendar.SECOND, 0);
		// c.set(Calendar.MILLISECOND, 0);
		// c.setFirstDayOfWeek(Calendar.MONDAY);
		// c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(DateUtil.getWeekZeroTime() + Const.DAY * 7);
		System.out.println(c.get(Calendar.YEAR) * 100 + c.get(Calendar.WEEK_OF_YEAR));
		System.out.println(DateUtil.toDayyyyMMdd(System.currentTimeMillis()));
	}
}
