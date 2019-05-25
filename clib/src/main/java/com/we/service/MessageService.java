package com.we.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.we.common.Constant.OperationType;
import com.we.common.Constant.ReCode;
import com.we.common.mongo.DboUtil;
import com.we.common.mongo.Op;
import com.we.common.utils.JSONUtil;
import com.we.core.Page;
import com.we.core.redis.RedisChannel;
import com.we.core.web.ReMsg;
import com.we.models.DocName;
import com.we.models.Message;
import com.we.socket.model.JsonMsg;
import com.we.socket.model.Msg;

@Service
public class MessageService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(MessageService.class);

	@Autowired
	UserService userService;

	@Autowired
	private MongoTemplate chatMongoTemplate;

	public MongoTemplate getChatMongoTemplate() {
		return chatMongoTemplate;
	}

	public void saveMsg(final Msg msg) {

		// final MongoTemplate mt = getChatMongoTemplate();
		// final RedisTemplate<String, String> rt = super.getChatRedisTemplate();
		// Thread t = new Thread(new Runnable() {
		// public void run() {
		// try {
		// if (msg.get_id() < 1) {
		// msg.set_id(incrMsgId());
		// }
		// if (msg.getSt() == null) {
		// msg.setSt(2);
		// }
		// String json = JSONUtil.beanToJson(msg);
		// DBObject dbo = BasicDBObject.parse(json);
		// mt.getCollection("msg").insert(dbo);
		// rt.opsForHash().put(RK.imLastMsg(msg.getTo()), "" + msg.getFr(), json);
		// rt.expire(RK.imLastMsg(msg.getTo()), 7, TimeUnit.DAYS);
		// } catch (Exception e) {
		// log.error("save msg err:", e);
		// }
		// }
		// });
		// t.start();
	}

	public List<DBObject> unreadMsgsByFr(Long uid, Long fr, int size) {
		DBObject q = new BasicDBObject("fr", fr).append("to", uid).append("st", 2);
		return this.getChatMongoTemplate().getCollection("msg").find(q).sort(new BasicDBObject("_id", -1)).limit(size)
				.toArray();
	}

	public void msgReaded(Long id) {
		// Thread t = new Thread(new Runnable() {
		// public void run() {
		// DBObject dbo = getChatMongoTemplate().getCollection("msg").findOne(new
		// BasicDBObject("_id", id));
		// if (dbo != null) {
		// getChatMongoTemplate().getCollection("msg").update(new BasicDBObject("_id",
		// id),
		// new BasicDBObject("$set", new BasicDBObject("st", 1)));
		// getChatRedisTemplate().opsForHash().delete(RK.imLastMsg(DboUtil.getLong(dbo,
		// "to")),
		// "" + DboUtil.getInt(dbo, "fr"));
		// }
		// }
		// });
		// t.start();

	}

	public ReMsg sendAll(JsonMsg msg) {
		if (msg != null) {
			super.getRedisTemplate().convertAndSend(RedisChannel.ALL.get(), JSONUtil.beanToJson(msg));
		}
		return new ReMsg(ReCode.OK);
	}

	public ReMsg sendP2P(JsonMsg msg) {
		if (msg != null) {
			super.getRedisTemplate().convertAndSend(RedisChannel.USER.get(), JSONUtil.beanToJson(msg));
		}
		return new ReMsg(ReCode.OK);
	}

	public Integer imMsgHandler(long uid, long toUid, String body, Map<String, Object> ext, String imType) {
		return 1;// imMsgHandler(uid, toUid, body, ext, imType, null);
	}

	public ReMsg getUnreadMessage() {
		Long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		int count = 0;
		DBObject dbo = super.findById(DocName.UNREAD_MESSAGE, uid);
		if (dbo != null) {
			count = DboUtil.getInt(dbo, "count");
		}
		return new ReMsg(ReCode.OK, count);
	}

	public DBObject findById(Long id) {
		return super.findById(DocName.MESSAGE, id);
	}

	public Page<DBObject> query(String op, String opId, Long uid, Integer status, Op qop, Integer page, Integer size) {
		// System.out.println("op:" + op + ",opId:" + opId + ",uid:" + uid
		// + ",status:" + status);
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = find(op, opId, uid, status, qop, page, size);
		Integer count = count(op, opId, uid, status, qop);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public List<DBObject> find(String op, String opId, Long uid, Integer status, Op qop, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (null != status && status != 0) {
			if (qop != null) {
				q = qop.getOp(q, "status", status);
			} else {
				q.put("status", status);
			}
		}
		if (uid != null && uid > 0) {
			q.put("recvUid", uid);
		}
		if (StringUtils.isNotBlank(op)) {
			q.put("op", op);
		}
		if (StringUtils.isNotBlank(opId)) {
			q.put("opId", opId);
		}
		// for(String k :q.keySet()){
		// System.out.println(k+":"+q.get(k));
		// }
		return getC(DocName.MESSAGE).find(q).sort(new BasicDBObject("status", -1).append("_id", -1))
				.skip(getStart(page, size)).limit(getSize(size)).toArray();
	}

	public Integer count(String op, String opId, Long uid, Integer status, Op qop) {
		DBObject q = new BasicDBObject();
		if (null != status && status != 0) {
			if (qop != null) {
				q = qop.getOp(q, "status", status);
			} else {
				q.put("status", status);
			}
		}
		if (uid != null && uid > 0) {
			q.put("recvUid", uid);
		}
		if (StringUtils.isNotBlank(op)) {
			q.put("op", op);
		}
		if (StringUtils.isNotBlank(opId)) {
			q.put("opId", opId);
		}
		return getC(DocName.MESSAGE).find(q).count();
	}

	public List<DBObject> find(Long baseId, Integer size) {
		DBObject q = new BasicDBObject();
		if (baseId != null && baseId > 0) {
			q.put("baseId", baseId);
		}
		return getC(DocName.MESSAGE).find(q).sort(new BasicDBObject("status", -1).append("_id", -1))
				.skip(getStart(0, size)).limit(getSize(size)).toArray();
	}

	public ReMsg readAllMessage() {
		Long userId = super.getUid();
		if (userId < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis()).append("status", Message.STATUS_READ);
		getC(DocName.MESSAGE).update(new BasicDBObject("recvUid", userId), new BasicDBObject("$set", u));
		DBObject uo = new BasicDBObject("count", 0);
		super.getC(DocName.UNREAD_MESSAGE).update(new BasicDBObject("_id", userId), uo);
		return new ReMsg(ReCode.OK);
	}

	public ReMsg readMessage(Long id) {
		Long userId = super.getUid();
		if (userId < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		List<DBObject> dbos = new ArrayList<DBObject>();
		DBObject dbo = findById(id);
		if (!isMyMessage(userId, dbo)) {
			return new ReMsg(ReCode.ROOL_NO);
		}

		long baseId = DboUtil.getLong(dbo, "baseId");
		// dbos.add(dbo);
		if (baseId > 0 && baseId != id) {
			DBObject q = new BasicDBObject("baseId", baseId);
			q = Op.GT.getOp(q, "status", Message.STATUS_DEL);
			// q = Op.LTE.getOp(q, "_id", id);
			List<DBObject> cdbos = getC(DocName.MESSAGE).find(q).sort(new BasicDBObject("_id", -1)).limit(20).toArray();
			for (int i = cdbos.size() - 1; i > -1; i--) {
				dbos.add(cdbos.get(i));
			}
		} else {
			dbos.add(dbo);
		}
		this.update(dbo, Message.STATUS_READ, userId);
		return new ReMsg(ReCode.OK, dbos);
	}

	private boolean isMyMessage(long uid, DBObject dbo) {
		if (dbo != null) {
			Long recvUid = DboUtil.getLong(dbo, "recvUid");
			if (uid == recvUid) {
				return true;
			}
		}
		return false;
	}

	public ReMsg del(Long id) {
		Long userId = super.getUid();
		if (userId < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		DBObject dbo = findById(id);
		return update(dbo, Message.STATUS_DEL, userId);
	}

	public ReMsg del(String ids) {
		Long userId = super.getUid();
		if (userId < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		String[] idarr = ids.split(",");
		for (int i = 0; i < idarr.length; i++) {
			try {
				del(Long.parseLong(idarr[i]));
			} catch (Exception e) {

			}
		}
		// DBObject q = new BasicDBObject("_id", new BasicDBObject("$in", lids))
		// .append("recvUid", userId);
		// DBObject u = new BasicDBObject("updateTime",
		// System.currentTimeMillis())
		// .append("status", Message.STATUS_DEL);
		//
		// super.getC(DocName.MESSAGE).update(q, new BasicDBObject("$set", u),
		// false, true);
		return new ReMsg(ReCode.OK);

	}

	public ReMsg update(DBObject dbo, Integer status, Long uid) {
		if (null == dbo) {
			return new ReMsg(ReCode.FAIL);
		}

		if (isMyMessage(uid, dbo)) {
			if (DboUtil.getInt(dbo, "status") == Message.STATUS_UNREAD
					&& (status == Message.STATUS_DEL || status == Message.STATUS_READ)) {
				DBObject uo = new BasicDBObject("$inc", new BasicDBObject("count", Integer.valueOf(-1)));
				super.getC(DocName.UNREAD_MESSAGE).update(new BasicDBObject("_id", uid), uo);
			}
			DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
			if (status != 0) {
				u.put("status", status);
			}
			getC(DocName.MESSAGE).update(new BasicDBObject("_id", DboUtil.getLong(dbo, "_id")),
					new BasicDBObject("$set", u));
		}
		return new ReMsg(ReCode.OK);
	}

	public ReMsg socketMsgSave(int type, String content, Long to, Long fr, OperationType op, String opId) {

		long userId = fr;
		Long id = super.getNextId(DocName.MESSAGE);
		String sendNickname = null;
		String sendAvatar = null;
		if (Message.TYPE_USER_MESSAGE == type) {
			if (userId < 1) {
				return new ReMsg(ReCode.NOT_AUTHORIZED);
			}
			DBObject dbo = userService.findById(userId);
			if (dbo != null) {
				sendNickname = DboUtil.getString(dbo, "nickname");
				sendAvatar = DboUtil.getString(dbo, "avatar");
			}
			opId = "" + id;
		} else {
			sendNickname = "시스템 알림";//系统通知
		}
		Long baseId = id;

		Message g = new Message(id, type, sendNickname, userId, Message.STATUS_UNREAD, to, sendAvatar, content, baseId,
				op.getOp(), opId);
		getMongoTemplate().save(g);

		// super.pubMessageIm(RedisChannel.ALL.get(), JSONUtil.beanToJson(msg));
		DBObject uo = new BasicDBObject("$inc", new BasicDBObject("count", Integer.valueOf(1)));
		super.getC(DocName.UNREAD_MESSAGE).update(new BasicDBObject("_id", to), uo, true, false);
		return new ReMsg(ReCode.OK, g);
	}

	// public ReMsg save(int type, String content, Long recvUid, Long replyId,
	// OperationType op, String opId,
	// HttpServletRequest request) {
	// // if (type == Message.TYPE_NOTICE
	// // && !op.getOp().equals(OperationType.USERSPACE.getOp())
	// // && this.count(op.getOp(), opId, recvUid, Message.STATUS_UNREAD,
	// // null) > 0) {
	// // return new ReMsg(ReCode.OK);
	// // }
	// long userId = 0;
	// Long id = super.getNextId(DocName.MESSAGE);
	// String sendNickname = null;
	// String sendAvatar = null;
	// int fr = 0;
	// if (Message.TYPE_USER_MESSAGE == type) {
	// userId = super.getUid();
	// if (userId < 1) {
	// return new ReMsg(ReCode.NOT_AUTHORIZED);
	// }
	// sendNickname = super.getUser("nickname").toString();
	// sendAvatar = super.getUser("avatar").toString();
	// opId = "" + id;
	// } else {
	// sendNickname = "系统通知";
	// }
	// Long baseId = id;
	// if (null != replyId && replyId > 0) {
	// DBObject dbo = findById(replyId);
	// if (dbo != null) {
	// baseId = DboUtil.getLong(dbo, "baseId");
	// }
	// }
	// Message g = new Message(id, type, sendNickname, userId,
	// Message.STATUS_UNREAD, recvUid, sendAvatar, content,
	// baseId, op.getOp(), opId);
	// getMongoTemplate().save(g);
	// MapBody mb = new MapBody();
	// MsgType msgType = null;
	// if (type == Message.TYPE_NOTICE) {
	// if (op.getOp().equals(OperationType.USERSPACE.getOp())) {
	// userId = super.getUid();
	// if (userId < 1) {
	// return new ReMsg(ReCode.NOT_AUTHORIZED);
	// }
	// if (replyId == null) {
	// msgType = MsgType.ADD_FRIENDS;
	// mb.append("rid", userId);
	// fr = MsgType.ADD_FRIENDS.getT();
	// } else {
	// // msgType = MsgType.ADD_FRIENDS_BACK;
	// // mb.append("status", 1);
	// msgType = MsgType.CHAT;
	// mb.append("fmt", "txt").append("msg", "你们已经是好友了，赶紧打个招呼吧！");
	// fr = (int) userId;
	// }
	// } else {
	// msgType = MsgType.NOTICE;
	// mb.append("op", op.getOp()).append("opId", opId).append("content", content);
	// }
	// } else {
	// msgType = MsgType.CHAT;
	// mb.append("fmt", "txt").append("msg", content);
	// fr = (int) userId;
	// }
	// Msg msg = new Msg(super.incrId(RK.imMsgId()), msgType, fr, recvUid,
	// System.currentTimeMillis(), mb.getData());
	// // Msg msg = SysMsgUtil.getSysMsg(super.incrId(RK.imMsgId()), recvUid,
	// // fr,
	// // mb, msgType);
	// // System.out.println(JSONUtil.beanToJson(msg));
	//
	// msgHandler(msg);
	// // super.pubMessageIm(RedisChannel.ALL.get(), JSONUtil.beanToJson(msg));
	// DBObject uo = new BasicDBObject("$inc", new BasicDBObject("count",
	// Integer.valueOf(1)));
	// super.getC(DocName.UNREAD_MESSAGE).update(new BasicDBObject("_id", recvUid),
	// uo, true, false);
	// return new ReMsg(ReCode.OK, g);
	// }

}
