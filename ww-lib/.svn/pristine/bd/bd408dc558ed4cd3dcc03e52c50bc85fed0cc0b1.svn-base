package com.zb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.models.DocName;
import com.zb.models.UserInRoomLog;

@Service
public class UserInRoomLogService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(UserInRoomLogService.class);

	/** 用户进入房间日志 */
	public List<DBObject> findUserInRoomLog(Long uid, Long roomId, Integer page, Integer size) {
		page = super.getPage(page);
		size = super.getSize(size);
		DBObject q = new BasicDBObject();
		if (null != uid && uid != 0) {
			q.put("uid", uid);
		}
		if (null != roomId && roomId != 0) {
			q.put("roomId", roomId);
		}
		return getC(DocName.USER_IN_ROOM_LOG).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("updateTime", -1)).toArray();
	}

	public int countUserInRoomLog(Long uid, Integer bgId, Integer op) {
		DBObject q = new BasicDBObject();
		if (null != uid && uid != 0) {
			q.put("uid", uid);
		}
		return getC(DocName.USER_IN_ROOM_LOG).find(q).count();
	}

	/** 存储进入房间日志 */
	public void saveUserInRoomLog(long uid, long roomId) {
		UserInRoomLog log = new UserInRoomLog(uid + "-" + roomId, uid, roomId, System.currentTimeMillis());
		super.getMongoTemplate().save(log);
	}

	/** 房间过期之后 删除进房间的日志 */
	public void removeInRoomLogs(Long[] roomIds) {
		getC(DocName.USER_IN_ROOM_LOG).remove(new BasicDBObject("roomId", new BasicDBObject("$in", roomIds)));
	}

}
