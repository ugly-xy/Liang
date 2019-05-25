//package com.zb.service;
//
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import com.mongodb.BasicDBObject;
//import com.mongodb.DBObject;
//import com.zb.core.Page;
//import com.zb.models.DocName;
//import com.zb.models.UserPackLog;
//
//@Service
//public class UserPackLogService extends BaseService {
//
//	static final Logger log = LoggerFactory.getLogger(UserPackLogService.class);
//
//	public void save(UserPackLog log) {
//		getMongoTemplate().save(log);
//	}
//
//	public DBObject findById(Long id) {
//		return getC(DocName.USER_PACKLOG).findOne(new BasicDBObject("_id", id));
//	}
//
//	public List<DBObject> findUserPackLog(Long uid, Integer bgId, Integer op, Integer page, Integer size) {
//		DBObject q = new BasicDBObject();
//		if (null != uid && uid != 0) {
//			q.put("uid", uid);
//		}
//
//		if (null != bgId && bgId != 0) {
//			q.put("bgId", bgId);
//		}
//		if (null != op && op != 0) {
//			q.put("op", op);
//		}
//
//		return getC(DocName.USER_PACKLOG).find(q).skip(super.getStart(page, size)).limit(getSize(size))
//				.sort(new BasicDBObject("_id", -1)).toArray();
//	}
//
//	public int countUserPackLog(Long uid, Integer bgId, Integer op) {
//		DBObject q = new BasicDBObject();
//		if (null != uid && uid != 0) {
//			q.put("uid", uid);
//		}
//
//		if (null != bgId && bgId != 0) {
//			q.put("bgId", bgId);
//		}
//		if (null != op && op != 0) {
//			q.put("op", op);
//		}
//		return getC(DocName.USER_PACKLOG).find(q).count();
//	}
//
//	public Page<DBObject> queryUserPackLog(Long userId, Integer bgId, Integer op, Integer page, Integer size) {
//		size = getSize(size);
//		page = getPage(page);
//		List<DBObject> dbos = this.findUserPackLog(userId, bgId, op, page, size);
//		int count = this.countUserPackLog(userId, bgId, op);
//		return new Page<DBObject>(count, size, page, dbos);
//	}
//}
