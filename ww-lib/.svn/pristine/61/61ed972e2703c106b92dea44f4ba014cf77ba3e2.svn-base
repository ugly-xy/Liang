package com.zb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.core.Page;
import com.zb.models.DocName;

@Service
public class LoginLogService extends BaseService {
	static final Logger log = LoggerFactory.getLogger(LoginLogService.class);

	public List<DBObject> findByLoginLogId(Integer start, Integer end, Double version, Long userId, Integer page,
			Integer size) {
		DBObject q = new BasicDBObject();
		if (null != start && null != end) {
			q.put("day", new BasicDBObject("$gte", start).append("$lte", end));
		} else if (null != start) {
			q.put("day", new BasicDBObject("$gte", start));
		} else if (null != end) {
			q.put("day", new BasicDBObject("$lte", end));
		}
		if (null != version && version != 0) {
			q.put("version", version);
		}
		if (null != userId && userId != 0) {
			q.put("userId", userId);
		}

		return getC(DocName.LOGIN_LOG).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
	}

	public int countLoginLog(Integer start, Integer end, Double version, Long userId) {
		DBObject q = new BasicDBObject();
		if (null != start && null != end) {
			q.put("day", new BasicDBObject("$gte", start).append("$lte", end));
		} else if (null != start) {
			q.put("day", new BasicDBObject("$gte", start));
		} else if (null != end) {
			q.put("day", new BasicDBObject("$lte", end));
		}
		if (null != version && version != 0) {
			q.put("version", version);
		}
		if (null != userId && userId != 0) {
			q.put("userId", userId);
		}

		return getC(DocName.LOGIN_LOG).find(q).count();
	}

	public Page<DBObject> queryLoginLog(Integer start, Integer end, Double version, Long userId, Integer page,
			Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = this.findByLoginLogId(start, end, version, userId, page, size);
		int count = this.countLoginLog(start, end, version, userId);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public List<DBObject> queryLastLogByUser(long uid) {
		return getC(DocName.LOGIN_LOG).find(new BasicDBObject("userId", uid)).sort(new BasicDBObject("updateTime", -1))
				.limit(1).toArray();

	}
}
