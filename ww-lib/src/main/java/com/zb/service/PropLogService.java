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
public class PropLogService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(PropLogService.class);

	public DBObject findById(Long id) {
		return getC(DocName.PROP_LOG).findOne(new BasicDBObject("_id", id));
	}

	public Page<DBObject> queryPropLog(Long uid, Integer bgId, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = this.findPropLog(uid, bgId, page, size);
		int count = this.countPropLog(uid, bgId);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public List<DBObject> findPropLog(Long uid, Integer bgId, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (null != uid && uid != 0) {
			q.put("uid", uid);
		}

		if (null != bgId && bgId != 0) {
			q.put("bgId", bgId);
		}

		return getC(DocName.PROP_LOG).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
	}

	public int countPropLog(Long uid, Integer bgId) {
		DBObject q = new BasicDBObject();
		if (null != uid && uid != 0) {
			q.put("uid", uid);
		}

		if (null != bgId && bgId != 0) {
			q.put("bgId", bgId);
		}
		return getC(DocName.PROP_LOG).find(q).count();
	}
	
	public List<DBObject> getPropLogs(long id, int size) {
		return super.getC(DocName.PROP_LOG).find(new BasicDBObject("_id", new BasicDBObject("$gt", id))).limit(size)
				.toArray();
	}

}
