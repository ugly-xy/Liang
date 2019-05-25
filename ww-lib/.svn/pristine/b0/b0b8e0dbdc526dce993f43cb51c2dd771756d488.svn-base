package com.zb.service;

import static com.zb.service.task.TaskAspect.TaskType.ADDFRIENDS;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.OperationType;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.mongo.Op;
import com.zb.common.redis.RK;
import com.zb.common.utils.JSONUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.Message;
import com.zb.models.goods.BaseGoods;
import com.zb.models.user.Relationship;
import com.zb.service.othergames.StarTrekService;
import com.zb.service.task.TaskAspect;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.Msg;
import com.zb.socket.model.MsgType;

@Service
public class RelationshipService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(RelationshipService.class);
	private static long BLACK_TIME = 48 * Const.HOUR;
	// test
	// private static long BLACK_TIME = 60 * Const.SECOND;
	@Autowired
	UserService userService;

	@Autowired
	MessageService messageService;

	@Autowired
	StarTrekService starTrekService;

	@Autowired
	GoodsService goodsService;

	public List<DBObject> findAllAdmin(Long myId, Integer r) {
		if (null == myId || myId < 1) {
			return null;
		}
		DBObject q = new BasicDBObject("myId", myId);
		if (null != r && r != 0) {
			q.put("r", r);
		}
		return getC(DocName.RELATIONSHIP).find(q).toArray();
	}

	public List<Long> getFrinds(Long myId) {
		if (null == myId || myId < 1) {
			return null;
		}
		String key = "" + myId;
		String json = super.getRedisTemplate().opsForValue().get(RK.userFriends(key));
		if (json != null) {
			return JSONUtil.jsonToArray(json, Long.class);
		} else {
			List<Long> uids = new ArrayList<Long>();
			DBObject q = new BasicDBObject("myId", myId).append("r", Relationship.FRIENDS);
			List<DBObject> dbos = getC(DocName.RELATIONSHIP).find(q).toArray();
			for (DBObject dbo : dbos) {
				uids.add(DboUtil.getLong(dbo, "rid"));
			}
			super.getRedisTemplate().opsForValue().set(RK.userFriends(key), JSONUtil.beanToJson(uids), 6,
					TimeUnit.HOURS);
			return uids;
		}

	}

	public DBObject findById(String id) {
		return getC(DocName.RELATIONSHIP).findOne(new BasicDBObject("_id", id));
	}

	public List<DBObject> find(Long myId, Long rid, Integer r, Integer page, Integer size, Op op) {
		DBObject q = new BasicDBObject();
		if (null != myId && myId != 0) {
			q.put("myId", myId);
		}

		if (null != rid && rid != 0) {
			q.put("rid", rid);
		}
		if (op != null) {
			op.getOp(q, "r", r);
		} else if (null != r && r != 0) {
			q.put("r", r);
		}

		List<DBObject> dbos = getC(DocName.RELATIONSHIP).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", 1)).toArray();
		for (DBObject dbo : dbos) {
			DBObject user = userService.findById(DboUtil.getLong(dbo, "rid"));
			dbo.put("nickname", DboUtil.getString(user, "nickname"));
			dbo.put("sex", DboUtil.getInt(user, "sex"));
			dbo.put("point", DboUtil.getInt(user, "point"));
			dbo.put("avatar", DboUtil.getString(user, "avatar"));
			dbo.put("personLabel", DboUtil.getString(user, "personLabel"));
			Integer vip = DboUtil.getInt(user, "vip");
			dbo.put("vip", null == vip ? 0 : vip);
		}
		return dbos;
	}

	public List<DBObject> queryIds(Long myId) {
		DBObject q = new BasicDBObject("myId", myId);
		DBObject qq = new BasicDBObject("rid", 1).append("_id", 0);
		return getC(DocName.RELATIONSHIP).find(q, qq).toArray();
	}

	public int count(Long myId, Long rid, Integer r, Op op) {
		DBObject q = new BasicDBObject();
		if (null != myId && myId != 0) {
			q.put("myId", myId);
		}

		if (null != rid && rid != 0) {
			q.put("rid", rid);
		}
		if (op != null) {
			op.getOp(q, "r", r);
		} else if (null != r && r != 0) {
			q.put("r", r);
		}
		return getC(DocName.RELATIONSHIP).find(q).count();

	}

	/** 好友数量 */
	public ReMsg queryFriendsCnt() {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return new ReMsg(ReCode.OK, count(uid, null, Relationship.FRIENDS, null));
	}

	public Page<DBObject> query(Long myId, Long rid, Integer r, Integer page, Integer size, Op op) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = this.find(myId, rid, r, page, size, op);
		int count = count(myId, rid, r, op);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public ReMsg getRelation(Long rid) {
		long myId = super.getUid();
		if (myId < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (rid == null) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		// 有一方是10000
		if (Const.SYSTEM_ID == myId || Const.SYSTEM_ID == rid || myId == Const.SYSTEM_ID2 || rid == Const.SYSTEM_ID2) {
			return new ReMsg(ReCode.OK, Relationship.FRIENDS);
		}
		DBObject dbo = this.findById(getId(myId, rid));
		if (dbo == null) {
			return new ReMsg(ReCode.OK, Relationship.NULL);
		}
		return new ReMsg(ReCode.OK, DboUtil.getInt(dbo, "r"));
	}

	private String getId(Long myId, Long rid) {
		return myId + "_" + rid;
	}

	public ReMsg del(Long rid) {
		Long myId = super.getUid();
		if (myId < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		del(getId(myId, rid));
		del(getId(rid, myId));
		super.getRedisTemplate().delete(RK.userFriends("" + myId));
		super.getRedisTemplate().delete(RK.userFriends("" + rid));
		return new ReMsg(ReCode.OK);
	}

	@TaskAspect(ADDFRIENDS)
	public ReMsg add(Long rid, Integer local, Long localId) {
		long myId = super.getUid();
		// String nickname = (String) super.getUser("nickname");
		if (myId < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (myId == rid) {
			return new ReMsg(ReCode.ADD_YOURSELF);
		}
		DBObject friend = findById(getId(myId, rid));
		if (friend != null) {

			int i = DboUtil.getInt(friend, "r");
			if (i == Relationship.FRIENDS) {
				return new ReMsg(ReCode.ALREADY_FRIENDS);
			} else if (i == Relationship.BLACK) {
				long time = BLACK_TIME - (System.currentTimeMillis() - DboUtil.getLong(friend, "updateTime"));
				if (time > 0) {
					return new ReMsg(ReCode.HAVE_BLACK, time);
				}
			}
		}
		DBObject dbo = userService.findById(rid);
		if (dbo != null) {
			Integer worth = DboUtil.getInt(dbo, "friendWorth");
			if (worth == null) {
				worth = 1;
			}
			ReMsg rm = goodsService.sendGift(BaseGoods.Gift.FLOWER.getV().getId(), worth, local, localId, rid, myId);
			if (rm.getCode() == ReCode.OK.reCode()) {
				add(myId, rid, Relationship.FRIENDS);
				add(rid, myId, Relationship.FRIENDS);
				return new ReMsg(ReCode.OK);
			} else {
				return rm;
			}
		} else {
			return new ReMsg(ReCode.USER_ID_ERR);
		}
	}

	private ReCode add(Long myId, Long rid, int r) {
		Relationship sh = new Relationship(getId(myId, rid), myId, rid, r);
		getMongoTemplate().save(sh);
		super.getRedisTemplate().delete(RK.userFriends("" + myId));
		super.getRedisTemplate().delete(RK.userFriends("" + rid));
		return ReCode.OK;
	}

	/**
	 * 添加好友回复
	 *
	 * @param rid
	 *            对方id
	 * @param status
	 *            1 同意添加好友 2 不同意
	 * @return
	 */
	public ReMsg addReply(Long rid, int status) {
		Long myId = super.getUid();
		String nickname = (String) super.getUser("nickname");
		if (myId < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (status == 1) {
			update(getId(myId, rid), Relationship.FRIENDS);
			update(getId(rid, myId), Relationship.FRIENDS);
			messageService.save(Message.TYPE_NOTICE, nickname + "[" + myId + "]已经是你的好友了，快一起玩耍吧！", rid, -1L,
					OperationType.USERSPACE, "" + myId, null);
		} else {
			del(getId(myId, rid));
			del(getId(rid, myId));
		}
		super.getRedisTemplate().delete(RK.userFriends("" + myId));
		super.getRedisTemplate().delete(RK.userFriends("" + rid));
		return new ReMsg(ReCode.OK);
	}

	/**
	 * 黑名单操作
	 *
	 * @param rid
	 *            对方id
	 * @param status
	 *            1 加入黑名单 2恢复好友
	 * @return
	 */
	public ReMsg black(long rid, int status) {
		long myId = super.getUid();
		if (myId < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		int i = getRelation(myId, rid);
		if (i == Relationship.BLACK) {
			return new ReMsg(ReCode.OK);
		}
		if (status == 1) {
			// 双向拉黑
			update(getId(myId, rid), Relationship.BLACK);
			update(getId(rid, myId), Relationship.BLACK);
			// 拉黑好友消息
			MapBody mb = new MapBody("uid", myId);
			Msg msg = new Msg(super.incrMsgId(), MsgType.DEL_FRIENDS, 0, rid, System.currentTimeMillis(), mb.getData());
			messageService.msgHandler(msg);
		} else {
			update(getId(myId, rid), Relationship.FRIENDS);
			update(getId(rid, myId), Relationship.FRIENDS);
		}
		Long guard = DboUtil.getLong(userService.findById(myId), "guard");
		if (null != guard && guard == rid) {// 对方是我的守护 移除守护
			userService.removeGuard(myId);
		}
		guard = DboUtil.getLong(userService.findById(rid), "guard");
		if (null != guard && guard == myId) {// 我是对方的守护
			userService.removeGuard(rid);
		}
		super.getRedisTemplate().delete(RK.userFriends("" + myId));
		super.getRedisTemplate().delete(RK.userFriends("" + rid));
		starTrekService.blackSlave(myId, rid);
		return new ReMsg(ReCode.OK);
	}

	// public ReMsg upsert(Long rid, Integer r) {
	// Long myId = super.getUid();
	// if (myId < 1) {
	// return new ReMsg(ReCode.NOT_AUTHORIZED);
	// }
	// List<DBObject> mydbos = this.find(myId, rid, r, 0, 1, null);
	// Long id = null;
	// Integer myR = null;
	// if (mydbos.size() > 0) {
	// DBObject dbo = mydbos.get(0);
	// id = DboUtil.getLong(dbo, "_id");
	// myR = DboUtil.getInt(dbo, "r");
	// }
	//
	// List<DBObject> dbos = this.find(rid, myId, r, 0, 1, null);
	// Long youId = null;
	// Integer youR = null;
	// if (dbos.size() > 0) {
	// DBObject dbo = dbos.get(0);
	// youId = DboUtil.getLong(dbo, "_id");
	// youR = DboUtil.getInt(dbo, "r");
	// }
	//
	// if (id == null || id < 1L) {// 新建关系
	// id = super.getNextId(DocName.RELATIONSHIP);
	// if (youId != null) {// 对方已经和我有关系
	// if (Relationship.FOLLOW == youR) {// 对方已经关注
	// update(youId, Relationship.FRIENDS);
	// r = Relationship.FRIENDS;
	// }
	// }
	// Relationship sh = new Relationship(id, myId, rid, r);
	// getMongoTemplate().save(sh);
	// } else {// 修改关系
	// if (r == myR) {
	// return new ReMsg(ReCode.OK);
	// }
	// if (youId != null) {
	// if (Relationship.FOLLOW == r) {
	// if (Relationship.FOLLOW == youR) {// 双方关注都成为好朋友
	// update(id, Relationship.FRIENDS);
	// update(youId, Relationship.FRIENDS);
	// } else {
	// update(id, r);
	// }
	// } else if (Relationship.UNFOLLOW == r) {
	// if (Relationship.FRIENDS == youR) {
	// del(id);
	// update(youId, Relationship.FOLLOW);
	// } else {
	// del(id);
	// }
	// } else if (Relationship.BLACK == r) {// 一方拉黑，双方都进入黑名单
	// update(id, r);
	// update(youId, r);
	// }
	// } else {
	// update(id, r);
	// }
	// }
	// return new ReMsg(ReCode.OK);
	// }

	private ReCode update(String id, Integer r) {
		BasicDBObject u = new BasicDBObject("r", r).append("updateTime", System.currentTimeMillis());
		super.getC(DocName.RELATIONSHIP).update(new BasicDBObject("_id", id), new BasicDBObject("$set", u));
		return ReCode.OK;
	}

	private ReCode del(String id) {
		super.getC(DocName.RELATIONSHIP).remove(new BasicDBObject("_id", id));
		return ReCode.OK;
	}

	public int getRelation(Long myId, Long rid) {
		DBObject dbo = getC(DocName.RELATIONSHIP).findOne(new BasicDBObject("_id", myId + "_" + rid));
		if (dbo == null) {
			return Relationship.NULL;
		}
		return DboUtil.getInt(dbo, "r");
	}
}
