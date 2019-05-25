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
public class GiftLogService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(GiftLogService.class);

	public DBObject findById(Long id) {
		return getC(DocName.GIFT_LOG).findOne(new BasicDBObject("_id", id));
	}

	public Page<DBObject> queryGiftLog(Long sendUid,Long recvUid, Integer bgId,Integer local, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = this.findGiftLog(sendUid,recvUid, bgId,local, page, size);
		int count = this.countGiftLog(sendUid,recvUid, bgId,local);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public List<DBObject> findGiftLog(Long sendUid,Long reccvUid, Integer bgId,Integer local, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (null != sendUid && sendUid != 0) {
			q.put("sendUid", sendUid);
		}
		if (null != reccvUid && reccvUid != 0) {
			q.put("recvUid", reccvUid);
		}
		if (null != bgId && bgId != 0) {
			q.put("bgId", bgId);
		}
		if (null != local && local != 0) {
			q.put("local", local);
		}
		return getC(DocName.GIFT_LOG).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
	}

	public int countGiftLog(Long sendUid,Long reccvUid, Integer bgId,Integer local) {
		DBObject q = new BasicDBObject();
		if (null != sendUid && sendUid != 0) {
			q.put("sendUid", sendUid);
		}
		if (null != reccvUid && reccvUid != 0) {
			q.put("recvUid", reccvUid);
		}
		if (null != bgId && bgId != 0) {
			q.put("bgId", bgId);
		}
		if (null != local && local != 0) {
			q.put("local", local);
		}
		return getC(DocName.GIFT_LOG).find(q).count();
	}
}
