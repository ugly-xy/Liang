package com.zb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.models.DocName;
import com.zb.models.UserAttenRoom;
import com.zb.models.room.Room;

@Service
public class UserAttenRoomService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(UserAttenRoomService.class);

	/** 用户关注房间 */
	public void saveUserAttenRoom(long uid, Room room, boolean attenRoom) {
		String _id = uid + "-" + room.get_id();
		if (attenRoom) {// 关注
			UserAttenRoom userAttenRoom = new UserAttenRoom(_id, uid, room.get_id(), System.currentTimeMillis());
			super.getMongoTemplate().save(userAttenRoom);
			room.setAttenCnt(room.getAttenCnt() == null ? 1 : room.getAttenCnt() + 1);
			super.getMongoTemplate().save(room);
		} else {// 取消关注
			super.getC(DocName.USER_ATTEN_ROOM).remove(new BasicDBObject("_id", _id));
			room.setAttenCnt(room.getAttenCnt() > 1 ? room.getAttenCnt() - 1 : 0);
			super.getMongoTemplate().save(room);
		}
	}

	/** 用户进入房间日志 */
	public List<DBObject> findUserAttenRoom(Long uid, Integer page, Integer size) {
		page = super.getPage(page);
		size = super.getSize(size);
		DBObject q = new BasicDBObject();
		if (null != uid && uid != 0) {
			q.put("uid", uid);
		}
		return getC(DocName.USER_ATTEN_ROOM).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("updateTime", -1)).toArray();
	}

	public DBObject findById(long uid, long roomId) {
		return this.findById(uid + "-" + roomId);
	}

	public DBObject findById(String _id) {
		return getC(DocName.USER_ATTEN_ROOM).findOne(new BasicDBObject("_id", _id));
	}

	public int countUserAttenRoom(Long uid) {
		DBObject q = new BasicDBObject();
		if (null != uid && uid != 0) {
			q.put("uid", uid);
		}
		return getC(DocName.USER_ATTEN_ROOM).find(q).count();
	}

	/** 房间过期之后 删除对此房间的关注 */
	public void removeAttenRooms(Long[] roomIds) {
		getC(DocName.USER_ATTEN_ROOM).remove(new BasicDBObject("roomId", new BasicDBObject("$in", roomIds)));
	}

	public void removeAttenRooms(long roomId) {
		getC(DocName.USER_ATTEN_ROOM).remove(new BasicDBObject("roomId", roomId));
	}
}
