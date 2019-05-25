package com.zb.service;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.mongo.DboUtil;
import com.zb.core.Page;
import com.zb.models.DocName;

@Service
public class PointLogService extends BaseService {


	static final Logger log = LoggerFactory.getLogger(PointLogService.class);

	public DBObject findById(Long id) {
		return getC(DocName.POINT_LOG).findOne(new BasicDBObject("_id", id));
	}

	public List<DBObject> findPointLog(Long userId, Integer opType,
			Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (null != userId && userId != 0) {
			q.put("userId", userId);
		}
		
		if (null != opType && opType != 0) {
			q.put("opType", opType);
		}

		return getC(DocName.POINT_LOG).find(q).skip(super.getStart(page, size))
				.limit(getSize(size)).sort(new BasicDBObject("_id", -1))
				.toArray();
	}

	public int countPointLog(Long userId, Integer opType) {
		DBObject q = new BasicDBObject();
		if (null != userId && userId != 0) {
			q.put("userId", userId);
		}
		
		if (null != opType && opType != 0) {
			q.put("opType", opType);
		}
		return getC(DocName.POINT_LOG).find(q).count();
	}

	public Page<DBObject> queryPointLog(Long userId, Integer opType,
			Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = this.findPointLog(userId, opType, page, size);
		int count = this.countPointLog(userId, opType);
		return new Page<DBObject>(count, size, page, dbos);
	}
	
}
