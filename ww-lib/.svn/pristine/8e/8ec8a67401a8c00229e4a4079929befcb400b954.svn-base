package com.zb.service.room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zb.common.Constant.ActivityType;
import com.zb.common.Constant.IMType;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.redis.RK;
import com.zb.common.utils.JSONUtil;
import com.zb.common.utils.T2TUtil;
import com.zb.core.Page;
import com.zb.core.redis.RedisChannel;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.room.Activity;
import com.zb.models.room.Actor;
import com.zb.models.room.ActorState;
import com.zb.models.room.Room;
import com.zb.service.BaseService;
import com.zb.service.MessageService;
import com.zb.service.UserPackService;
import com.zb.service.UserService;
import com.zb.service.pay.CoinService;
import com.zb.service.usertask.UserTaskService;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.Msg;
import com.zb.socket.model.MsgType;
import com.zb.socket.model.SocketHandler;

public abstract class RoomService extends BaseService {
	static final Logger log = LoggerFactory.getLogger(RoomService.class);

	@Autowired
	protected MessageService messageService;

	@Autowired
	protected PunishService punishService;

	@Autowired
	protected CoinService coinService;

	@Autowired
	protected UserService userService;

	@Autowired
	protected UserPackService userPackService;

	@Autowired
	protected UserTaskService userTaskService;

	public DBObject findById(Long id) {
		return getC(DocName.ROOM).findOne(new BasicDBObject("_id", id));
	}

	/** 查询好友的包间 */
	public List<DBObject> findFriendsRooms(Long[] uids, Integer page, Integer size) {
		size = super.getSize(size);
		page = super.getPage(page);
		DBObject q = new BasicDBObject("buyUid", new BasicDBObject("$in", uids))
				.append("sellStatus", Room.SELL_STATUS_SOLD).append("count", new BasicDBObject("$gt", 0));
		return getC(DocName.ROOM).find(q).sort(new BasicDBObject("updateTime", -1)).skip(getStart(page, size))
				.limit(getSize(size)).toArray();
	}

	public Room getRoom(Long id) {
		DBObject dbo = findById(id);
		Room r = DboUtil.toBean(dbo, Room.class);
		return r;
	}

	public Page<DBObject> query(Long roomId, Integer status, Long buyUid, Integer type, Integer sellStatus, Boolean sys,
			String vipRoomType, Integer numLength, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = find(roomId, status, buyUid, type, sellStatus, sys, vipRoomType, numLength, page, size);
		int count = count(roomId, status, buyUid, type, sellStatus, sys, vipRoomType, numLength);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public List<DBObject> find(Long roomId, Integer status, Long buyUid, Integer type, Integer sellStatus, Boolean sys,
			String vipRoomType, Integer numLength, Integer page, Integer size) {
		DBObject q = getQ(roomId, status, buyUid, type, sellStatus, sys, vipRoomType, numLength);
		return getC(DocName.ROOM).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
	}

	public int count(Long roomId, Integer status, Long buyUid, Integer type, Integer sellStatus, Boolean sys,
			String vipRoomType, Integer numLength) {
		DBObject q = getQ(roomId, status, buyUid, type, sellStatus, sys, vipRoomType, numLength);
		return getC(DocName.ROOM).find(q).count();
	}

	public DBObject getQ(Long roomId, Integer status, Long buyUid, Integer type, Integer sellStatus, Boolean sys,
			String vipRoomType, Integer numLength) {
		DBObject q = new BasicDBObject();
		if (roomId != null && roomId != 0) {
			q.put("_id", roomId);
		}
		if (status != null && status != 0) {
			q.put("status", status);
		}
		if (buyUid != null && buyUid > 0) {
			q.put("buyUid", buyUid);
		}
		if (type != null) {
			q.put("type", type);
		}
		if (sellStatus != null && sellStatus != 0) {
			q.put("sellStatus", sellStatus);
		}
		if (sys != null) {
			q.put("sys", sys);
		}
		if (StringUtils.isNotBlank(vipRoomType)) {
			q.put("vipRoomType", vipRoomType);
		}
		if (null != numLength && numLength != 0) {
			q.put("numLength", numLength);
		}
		return q;
	}

	public int getActivityRoomCount(int type, Integer status) {
		BasicDBObject q = new BasicDBObject("type", type).append("count", new BasicDBObject("$gt", 0));
		if (status != null && status > 0) {
			q.put("status", status);
		}
		return getC(DocName.ROOM).find(q).count();
	}

	/** 自建房 */
	public Room creatRoomByUser(long uid, int type) {
		// 自建房只能从系统房间中选取
		BasicDBObject q = new BasicDBObject("sys", true);
		q.append("count", 0).append("updateTime", new BasicDBObject("$lt", System.currentTimeMillis() - 600 * 1000))
				.append("_id", new BasicDBObject("$lt", SYSTEM_ROOM_END));
		List<DBObject> list = getC(DocName.ROOM).find(q).sort(new BasicDBObject("updateTime", 1)).limit(1).toArray();
		Room r;
		if (null != list && !list.isEmpty()) {
			DBObject dbo = list.get(0);
			r = DboUtil.toBean(dbo, Room.class);
			r.setUid(uid);
			r.setType(type);
			r.setPub(false);
			r.setUpdateTime(System.currentTimeMillis());
			super.getMongoTemplate().save(r);
		} else {
			r = createRoom(uid, type, false, Room.OPEN);
		}
		return r;
	}

	private static final int SYSTEM_ROOM_START = 200000;
	private static final int SYSTEM_ROOM_END = 9999999;

	public Room createRoom(int type) {
		return createRoom(0L, type, true, Room.OPEN);
	}

	public Room createRoom(int type, int status) {
		return createRoom(0L, type, true, status);
	}

	public Room createRoom(long uid, int type, boolean pub, int status) {
		DBObject q = new BasicDBObject("_id", new BasicDBObject("$lt", SYSTEM_ROOM_END));
		List<DBObject> dbos = super.getC(DocName.ROOM).find(q).sort(new BasicDBObject("_id", -1)).limit(1).toArray();
		Long id = 0L;
		if (dbos.size() > 0) {
			id = super.getNextId(DocName.ROOM);// DboUtil.getLong(dbos.get(0),
												// "_id") + 1;
		}
		if (id < SYSTEM_ROOM_START) {
			id = (long) SYSTEM_ROOM_START;
		}
		return this.save(id, uid, Room.SELL_STATUS_SHELVES, true, type, pub, status);
	}

	public void saveRoomActor(final long roomId, Actor actor) {
		if (actor != null) {
			super.getRedisTemplate().opsForHash().put(RK.roomUser(roomId), "" + actor.getUid(),
					JSONUtil.beanToJson(actor));
		}
	}

	public void delRoomActor(final long roomId, long uid) {
		super.getRedisTemplate().opsForHash().delete(RK.roomUser(roomId), "" + uid);
	}

	public Actor getRoomActor(final long roomId, long uid) {
		String json = (String) super.getRedisTemplate().opsForHash().get(RK.roomUser(roomId), "" + uid);
		return JSONUtil.jsonToBean(json, Actor.class);
	}

	public <T> T getRoomActor(final long roomId, long uid, Class<T> clazz) {
		return JSONUtil.jsonToBean((String) super.getRedisTemplate().opsForHash().get(RK.roomUser(roomId), "" + uid),
				clazz);
	}

	public <T> T getRoomActorState(final long roomId, long uid, Class<T> clazz) {
		return JSONUtil.jsonToBean(
				(String) super.getRedisTemplate().opsForHash().get(RK.roomUserState(roomId), "" + uid), clazz);
	}

	public void saveRoomActorState(final long roomId, ActorState actorState) {
		if (actorState != null) {
			super.getRedisTemplate().opsForHash().put(RK.roomUserState(roomId), "" + actorState.getUid(),
					JSONUtil.beanToJson(actorState));
		}
	}

	public void delRoomActorState(final long roomId, long uid) {
		super.getRedisTemplate().opsForHash().delete(RK.roomUserState(roomId), "" + uid);
	}

	public void delRoomAllActors(final long roomId) {
		super.getRedisTemplate().delete(RK.roomUser(roomId));
	}

	public void delRoomAllActorsState(final long roomId) {
		super.getRedisTemplate().delete(RK.roomUserState(roomId));
	}

	public Long getActorSize(final long roomId) {
		return super.getRedisTemplate().opsForHash().size(RK.roomUser(roomId));
	}

	public Set<Long> getRoomAids(final long roomId) {
		Set<Long> ls = new TreeSet<Long>();
		Set<Object> objs = getRedisTemplate().opsForHash().keys(RK.roomUser(roomId));
		for (Object obj : objs) {
			ls.add(T2TUtil.obj2Long(obj));
		}
		return ls;
	}

	public Room createGoodRoom(Long id, int sellStatus) {
		if (this.findById(id) != null) {
			// TODO
			return null;
		}
		return this.save(id, 0L, sellStatus, false, 0, Room.OPEN);
	}

	protected Room save(Long id, Long uid, int sellStatus, boolean sys, int type, int status) {
		return save(id, uid, sellStatus, sys, type, true, status);
	}

	private Room save(Long id, Long uid, int sellStatus, boolean sys, int type, boolean pub, int status) {
		Room r = new Room(id, uid, sellStatus, sys, type, status);
		r.setPub(pub);
		super.getMongoTemplate().save(r);
		return r;
	}

	/** 用户房间切换活动 */
	public ReMsg openActive(long uid, long roomId, int type) {
		Room room = getRoom(roomId);
		return new ReMsg(openActive(uid, room, type));
	}

	public ReCode openActive(long uid, Room room, int type) {
		if (null != room && room.getSellStatus() == Room.SELL_STATUS_SOLD && room.getStatus() == Room.OPEN
				&& uid == room.getBuyUid()) {
			int oldType = room.getType();
			room.setType(type);
			room.setUid(uid);
			room.setCount(0);
			room.setRobitCount(0);
			updateRoom(room, T_RESTART);
			delJob(room.get_id());
			delActivity(room.get_id());
			MapBody mb = new MapBody(SocketHandler.CHANGE_ROOM_TYPE, SocketHandler.FN_ROOM_ID, room.get_id())
					.append("oldType", oldType).append("newType", type).append("buyUid", uid);
			pubUserMsg(uid, MsgType.ROOM, mb);
			Set<Actor> roomActors = this.getRoomActors(room.get_id());
			for (Actor actor : roomActors) {
				if (actor.getUid() != uid) {
					System.out.println("openActive: user:" + actor.getUid());
					pubUserMsg(actor.getUid(), MsgType.ROOM, mb);
				}
			}
			delRoomAllActors(room.get_id());
			return ReCode.OK;
		}
		return ReCode.FAIL;
	}

	public Map<Long, Actor> getRoomActorsMap(final long roomId) {
		Map<Object, Object> omap = getRedisTemplate().opsForHash().entries(RK.roomUser(roomId));
		Map<Long, Actor> amap = new HashMap<Long, Actor>();
		for (Object o : omap.keySet()) {
			amap.put((Long) o, JSONUtil.jsonToBean((String) omap.get(o), Actor.class));
		}
		return amap;
	}

	public Set<Actor> getRoomActors(final long roomId) {
		List<Object> omap = getRedisTemplate().opsForHash().values(RK.roomUser(roomId));
		Set<Actor> amap = new TreeSet<Actor>();
		for (Object o : omap) {
			Actor t = JSONUtil.jsonToBean((String) o, Actor.class);
			amap.add(t);
		}
		return amap;
	}

	public <T extends Actor> Set<T> getRoomActors(final long roomId, Class<T> clazz) {
		List<Object> omap = getRedisTemplate().opsForHash().values(RK.roomUser(roomId));
		Set<T> amap = new TreeSet<T>();
		for (Object o : omap) {
			T t = JSONUtil.jsonToBean((String) o, clazz);
			if (t != null) {
				amap.add(t);
			}
		}
		return amap;
	}

	public <T extends ActorState> Set<T> getRoomActorsState(final long roomId, Class<T> clazz) {
		List<Object> omap = getRedisTemplate().opsForHash().values(RK.roomUserState(roomId));
		Set<T> amap = new TreeSet<T>();
		for (Object o : omap) {
			T t = JSONUtil.jsonToBean((String) o, clazz);
			amap.add(t);
		}
		return amap;
	}

	public void pubRoomUserMsg(final long roomId, long uid, MsgType mt, MapBody mb, Long curTime) {
		Msg msg = new Msg(super.incrMsgId(), mt, 0, uid, curTime, mb.getData());
		super.getChatRedisTemplate().convertAndSend(RedisChannel.ROOM_USER.get() + roomId + ":" + uid,
				JSONUtil.beanToJson(msg));
	}

	public void pubRoomMsg(Long roomId, MsgType mt, MapBody mb) {
		Msg msg = new Msg(super.incrMsgId(), mt, 0, roomId, System.currentTimeMillis(), mb.getData());
		super.getChatRedisTemplate().convertAndSend(RedisChannel.ROOM.get() + roomId, JSONUtil.beanToJson(msg));
	}

	public void pubUserMsg(Long uid, MsgType mt, MapBody mb) {
		Msg msg = new Msg(super.incrMsgId(), mt, 0, uid, System.currentTimeMillis(), mb.getData());
		super.getChatRedisTemplate().convertAndSend(RedisChannel.USER.get() + msg.getTo(), JSONUtil.beanToJson(msg));
	}

	public abstract ReMsg outRoom(long uid, Room r);

	public abstract ReMsg disconnectRoom(long uid, Room r);

	public abstract ReMsg kick(long uid, Room r);

	// public abstract ReMsg inRoom(long uid, Room r);

	public abstract ReMsg inRoom(long uid, Room r, int model, boolean robit);

	// public abstract ReMsg inRoom(long uid, Room r, boolean robit);

	public abstract ReMsg getActorStatus(long uid, Room r);

	public abstract ReMsg canInRoom(int type, long actId, final long roomId);

	// public abstract Long getActivityRoom(String historyRoomIds);

	public void jobsDone(long uid, int[][] tm, final int count) {
		userTaskService.doUserTask(uid, tm, count);
	}

	// public Room changeRoomCount(Room r, Set<? extends Actor> as) {
	// return changeRoomCount(r,as,0);
	// }

	public Room changeRoomCount(Room r, Set<? extends Actor> as, int type) {
		int count = 0;
		int rc = 0;
		if (as != null) {
			count = as.size();
		}
		if (count == 0) {
			r.setStatus(Room.OPEN);
		} else {
			for (Actor a : as) {
				if (a.isRobit()) {
					rc++;
				}
			}
		}
		r.setCount(count);
		r.setRobitCount(rc);
		return updateRoom(r, type);
	}

	// public Room updateRoom(Room r) {
	// return updateRoom(r,0);
	// }

	protected final static int T_NULL = 0;
	protected final static int T_INROOM = 1;
	protected final static int T_OUTROOM = 2;
	protected final static int T_START = 3;
	protected final static int T_RESTART = 4;
	protected final static int T_CLOSE = 5;

	/**
	 * 
	 * @param r
	 * @param as
	 * @param type
	 *            1代表进入房间 2 代表退出房间 3代表开始 4重新开始 5关闭房间
	 * @return
	 */
	public Room updateRoom(Room r, int type) {
		// TODO 新版本有问题注释掉
		// if (type == T_INROOM) {
		// Double score = getActivityRoomScore(type, r.get_id());
		// if (score != null) {
		// if (r.getCount() > score) {
		// changeActivityRoomScore(type, r.get_id(), 1);
		// }
		// }
		// } else if (type == T_OUTROOM) {
		// Double score = getActivityRoomScore(type, r.get_id());
		// if (score != null&&score>0) {
		// changeActivityRoomScore(type, r.get_id(), -1);
		// }
		// } else if (type == T_START) {
		// delActivityRoomId(type, r.get_id());
		// } else if (type == T_RESTART) {
		// addActivityRoomId(type, r.get_id(), r.getCount());
		// } else if (type == T_CLOSE) {
		// delActivityRoomId(type, r.get_id());
		// }
		r.setUpdateTime(System.currentTimeMillis());
		super.getMongoTemplate().save(r);
		return r;
	}

	public List<Room> getActiveRooms(Integer size) {
		size = super.getSize(size);
		// Criteria cr = new Criteria();
		// Criteria c1 =
		// Criteria.where("count").gt(0).lt(11).and("type").is(MsgType.WEREWOLF.getT()).and("status")
		// .is(Room.OPEN).and("pub").is(true);
		// Criteria c2 =
		// Criteria.where("count").gt(0).lt(8).and("type").is(MsgType.WEREWOLF9.getT()).and("status")
		// .is(Room.OPEN).and("pub").is(true);
		// Criteria c3 =
		// Criteria.where("count").gt(0).lt(5).and("type").is(MsgType.WEREWOLF6.getT()).and("status")
		// .is(Room.OPEN).and("pub").is(true);
		// Criteria c4 = Criteria.where("count").gt(0).lt(6).and("type")
		// .nin(MsgType.WEREWOLF.getT(), MsgType.WEREWOLF9.getT(),
		// MsgType.WEREWOLF6.getT()).and("status")
		// .is(Room.OPEN).and("pub").is(true);
		// Query q = new Query(cr.orOperator(c1, c2, c3,
		// c4)).limit(size).with(new Sort(Direction.ASC, "count"));
		// return super.getMongoTemplate().find(q, Room.class);

		Criteria c = Criteria.where("count").gt(0).lt(6).and("type")
				.in(MsgType.UNDERCOVER.getT(), MsgType.DRAWSOMETHING.getT(), MsgType.DICE.getT(),
						MsgType.COW_LOW.getT(), MsgType.COW_HIGH.getT(), MsgType.TEXAS_LOW.getT(),
						MsgType.TEXAS_MID.getT(), MsgType.TEXAS_HIGH.getT())
				.and("status").is(Room.OPEN).and("pub").is(true).and("sys").is(true);
		Query q = new Query(c).limit(size).with(new Sort(Direction.ASC, "count"));
		return super.getMongoTemplate().find(q, Room.class);

	}

	public List<Long> getActiveRooms() {
		List<Long> rids = new ArrayList<Long>();
		Set<String> sets = getActivityRoomIds(ActivityType.DICE.getType(), 1, 3, 0, 5);
		for (String s : sets) {
			rids.add(Long.parseLong(s));
		}
		sets = getActivityRoomIds(ActivityType.DRAW_SOMETHING.getType(), 1, 3, 0, 5);
		for (String s : sets) {
			rids.add(Long.parseLong(s));
		}
		sets = getActivityRoomIds(ActivityType.UNDERCOVER.getType(), 1, 3, 0, 5);
		for (String s : sets) {
			rids.add(Long.parseLong(s));
		}
		// sets = getActivityRoomIds(ActivityType.SOUTHPENGUIN.getType(), 1, 3,
		// 0, 5);
		// for (String s : sets) {
		// rids.add(Long.parseLong(s));
		// }
		sets = getActivityRoomIds(ActivityType.WEREWOLF.getType(), 1, 3, 0, 5);
		for (String s : sets) {
			rids.add(Long.parseLong(s));
		}
		sets = getActivityRoomIds(ActivityType.WEREWOLF6.getType(), 1, 3, 0, 5);
		for (String s : sets) {
			rids.add(Long.parseLong(s));
		}
		sets = getActivityRoomIds(ActivityType.WEREWOLF9.getType(), 1, 3, 0, 5);
		for (String s : sets) {
			rids.add(Long.parseLong(s));
		}
		return rids;
	}

	Double getActivityRoomScore(int type, Long id) {
		return getRedisTemplate().opsForZSet().score(RK.activityRoom(type), "" + id);
	}

	Double changeActivityRoomScore(int type, Long id, int score) {
		return getRedisTemplate().opsForZSet().incrementScore(RK.activityRoom(type), "" + id, score);
	}

	void addActivityRoomId(int type, Long id, int score) {
		getRedisTemplate().opsForZSet().add(RK.activityRoom(type), "" + id, score);
	}

	void delActivityRoomId(int type, Long id) {
		getRedisTemplate().opsForZSet().remove(RK.activityRoom(type), "" + id);
	}

	Set<String> getActivityRoomIds(int type, int max, int offset, int count) {
		return getActivityRoomIds(type, 0, max, offset, count);
	}

	Set<String> getActivityRoomIds(int type, int min, int max, int offset, int count) {
		return getRedisTemplate().opsForZSet().reverseRangeByScore(RK.activityRoom(type), min, max, offset, count);
	}

	public void chat(final long uid, final long roomId, final String fmt, final String txt) {
		DBObject user = userService.findByIdSafe(uid);
		String nickname = "";
		String avatar = "";
		if (null != user) {
			nickname = DboUtil.getString(user, "nickname");
			avatar = DboUtil.getString(user, "avatar");
		}
		MapBody m = new MapBody(SocketHandler.HANDLER_NAME, SocketHandler.ROOM_CHAT)
				.append(SocketHandler.FN_USER_ID, uid).append(SocketHandler.FN_ROOM_ID, roomId).append("fmt", fmt)
				.append("msg", txt).append("nickname", nickname).append("avatar", avatar);
		this.pubRoomMsg(roomId, MsgType.ROOM, m);
	}

	public static final int FR_SYS = 0;

	// public abstract ReMsg handle(Long roomId, int fr);

	public abstract ReMsg handle(Room room, long fr);

	public void closeRoom(final long roomId) {
		log.error("CLOSE room:" + roomId);
		Room room = getRoom(roomId);
		delActivity(room.get_id());
		delRoomAllActors(room.get_id());
		room.setCount(0);
		room.setRobitCount(0);
		room.setStatus(Room.OPEN);
		room.setUid(0L);
		if (room.isSys()) {// 系统房间设为公开
			room.setPub(true);
		}
		updateRoom(room, T_CLOSE);
		delJob(room.get_id());
		MapBody mb = new MapBody(SocketHandler.ROOM_CLOSE, SocketHandler.FN_ROOM_ID, room.get_id());
		pubRoomMsg(room.get_id(), MsgType.ROOM, mb);
	}

	/**
	 * 
	 * @param roomId
	 * @param time
	 *            秒
	 */
	public void addJob(Long roomId, int time) {
		super.getRedisTemplate().opsForZSet().add(RK.roomJob(), "" + roomId, System.currentTimeMillis() + time * 1000);
	}

	public Double getJobEndTime(Long roomId) {
		return super.getRedisTemplate().opsForZSet().score(RK.roomJob(), "" + roomId);
	}

	public void delJob(Long roomId) {
		super.getRedisTemplate().opsForZSet().remove(RK.roomJob(), "" + roomId);
	}

	public void delRoomUserRobitJob(Long roomId, Long robitId) {
		super.getRedisTemplate().opsForZSet().remove(RK.roomUserRobitJob(), roomId + "-" + robitId);
	}

	private final Lock lock = new ReentrantLock();

	public <T extends Activity> T getActivity(final long roomId, Class<T> clazz) {
		try {
			lock.lock();
			String json = super.getRedisTemplate().opsForValue().get(RK.activity(roomId));
			// System.out.println(json);
			if (json != null) {
				try {
					T t = JSONUtil.jsonToBean(super.getRedisTemplate().opsForValue().get(RK.activity(roomId)), clazz);
					return t;
				} catch (Exception e) {
					log.error("ActivityroomId=========" + roomId + "Activityclazz=====" + clazz);
					throw e;
				}
			} else {
				try {
					T t = clazz.newInstance();
					t.set_id(incrId(RK.activityId()));
					return t;
				} catch (InstantiationException | IllegalAccessException e) {
					log.error("new ActivityroomId=========" + roomId + "Activityclazz=====" + clazz);
					return null;
				}
			}
		} finally {
			lock.unlock();
		}
	}

	public void saveActivity(Long roomId, Activity activity) {
		try {
			lock.lock();
			super.getRedisTemplate().opsForValue().set(RK.activity(roomId), JSONUtil.beanToJson(activity));
		} finally {
			lock.unlock();
		}
	}

	public void delActivity(Long roomId) {
		try {
			lock.lock();
			super.getRedisTemplate().delete(RK.activity(roomId));
		} finally {
			lock.unlock();
		}
	}

	public ReMsg inviteFriends(final long roomId, String uids, int type) {
		long uid = super.getUid();
		if (uid < 1)
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		String nickname = super.getUser("nickname").toString();
		String[] ids = uids.trim().split(",");
		if (ids.length < 1) {
			return new ReMsg(ReCode.FAIL);
		}
		Room room = getRoom(roomId);
		if (null == room) {
			return new ReMsg(ReCode.FAIL);
		}
		MapBody mb = new MapBody("uid", uid).append("roomId", roomId).append("type", type).append("nickname", nickname)
				.append("hasOwner", 0 == room.getUid() ? false : true).append("pub", room.isPub());
		long dt = System.currentTimeMillis();
		for (String id : ids) {
			if (StringUtils.isNotBlank(id)) {
				long to = 0l;
				try {
					to = Long.parseLong(id);
				} finally {

				}
				if (to > 0) {
					// String body = "[" + uid + "] 在" + roomId + "房间邀请 [" + to
					// + "] 一起来玩一局 "
					// + ActivityType.getActivityName(type) + " 立即进入>";
					String body = "[" + uid + "] 邀请 [" + to + "] 来玩 " + ActivityType.getActivityName(type) + "  点击进入>";

					mb.append("invited", to);
					Msg msg = new Msg(super.incrMsgId(), MsgType.INVITEFRIENDS, (int) uid, to, dt, mb.getData());
					messageService.msgHandler(msg);
					if (type != ActivityType.CHATBOX.getType()) {// 语音聊天室不发im消息
						// 游戏邀请的好友消息
						Map<String, Object> ext = new HashMap<String, Object>();
						ext.put("type", IMType.GAME.getType());
						ext.put("op", type);
						ext.put("opId", roomId + "");
						String ebody = "我在" + roomId + "房间邀请你一起来玩一局" + ActivityType.getActivityName(type) + "  立即进入>";
						messageService.imMsgHandler(uid, to, body, ext, IMType.GAME.getType(), ebody);
						// messageService.easeMsgHandler(uid, to, body, ext,
						// IMType.GAME.getType());
					}
				}
			}
		}
		return new ReMsg(ReCode.OK);
	}

	public Page<DBObject> getActiveRooms(Integer type, Integer page, Integer size) {
		page = super.getPage(page);
		size = super.getSize(size);
		BasicDBObject q = new BasicDBObject("count", new BasicDBObject("$gt", 0));
		if (null != type && type > 0) {
			q.put("type", type);
		}
		DBCursor dbc = getC(DocName.ROOM).find(q);
		int count = dbc.count();
		List<DBObject> dbos = dbc.limit(size).skip((page - 1) * size).sort(new BasicDBObject("_id", 1)).toArray();
		return new Page<DBObject>(count, size, page, dbos);
	}

	// public ReCode fastCard(long uid, long linkId) {
	// ReMsg rs = userPackService.useGoods(uid, BaseGoods.Prop.FAST_CARD.getV(),
	// 1L);
	// int used = 0;
	// if (rs.getCode() == ReCode.OK.reCode()) {
	// used = Integer.parseInt(rs.getData().toString());
	// }
	// ReCode rc = ReCode.OK;
	// if (used < 1) {
	// rc = coinService.reduce(uid, CoinLog.OUT_FAST_CARD, linkId,
	// BaseGoods.Prop.FAST_CARD.getV().getPrice(), 0,
	// "使用快进卡");
	// }
	// return rc;
	// }

	// 踢出机器人
	// public void kickRobit(Set<? extends Actor> actors, Room r) {
	// boolean isChange = false;
	// Iterator<? extends Actor> as = actors.iterator();
	// while (as.hasNext()) {
	// Actor a = as.next();
	// if (a.isRobit()) {
	// if (DrawSomethingActor.A_STATUS_IN_ROOM == a.getUcStatus()) {
	// if (System.currentTimeMillis() - a.getInTime() > 15000) {
	// // outRoom(a.getUid(), r);
	// this.delRoomActor(r.get_id(), a.getUid());
	// isChange = true;
	// actors.remove(a);
	// }
	// }
	// }
	// }
	// if(isChange){
	// this.changeRoomCount(r, actors);
	// }
	// }

	// 检查房间，如果房间内15分钟没有准备用户，则关闭房间，否则过段时间继续检测
	protected void checkReadyRoom(Room r, int readyCount, int timeStart) {
		if (r.getType() == ActivityType.WEREWOLF.getType()) {
			if ((readyCount == 0 && System.currentTimeMillis() - r.getUpdateTime() > 2700 * 1000)
					|| System.currentTimeMillis() - r.getUpdateTime() > 4800 * 1000) {// 准备人数为0且房间15分钟未更新，或者房间半小时未更新，关闭
				closeRoom(r.get_id());
				return;
			}
		} else if (r.getType() == ActivityType.WEREWOLF9.getType()) {
			if ((readyCount == 0 && System.currentTimeMillis() - r.getUpdateTime() > 1800 * 1000)
					|| System.currentTimeMillis() - r.getUpdateTime() > 3600 * 1000) {// 准备人数为0且房间15分钟未更新，或者房间半小时未更新，关闭
				closeRoom(r.get_id());
				return;
			}
		} else {
			if ((readyCount == 0 && System.currentTimeMillis() - r.getUpdateTime() > 900 * 1000)
					|| System.currentTimeMillis() - r.getUpdateTime() > 1800 * 1000) {// 准备人数为0且房间15分钟未更新，或者房间半小时未更新，关闭
				closeRoom(r.get_id());
				return;
			}
		}
		addJob(r.get_id(), timeStart);
	}

	/** 发言 */
	public ReMsg speaking(long uid, long roomId) {
		Actor ua = this.getRoomActor(roomId, uid);
		if (ua != null) {
			MapBody mb = new MapBody(SocketHandler.SPEAKING, SocketHandler.FN_ROOM_ID, roomId)
					.append(SocketHandler.FN_USER_ID, uid);
			this.pubRoomMsg(roomId, MsgType.ROOM, mb);
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 暂停发言 */
	public ReMsg pauseSpeaking(long uid, long roomId) {
		Actor ua = this.getRoomActor(roomId, uid);
		if (ua != null) {
			MapBody mb = new MapBody(SocketHandler.PAUSESPEAKING, SocketHandler.FN_ROOM_ID, roomId)
					.append(SocketHandler.FN_USER_ID, uid);
			this.pubRoomMsg(roomId, MsgType.ROOM, mb);
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 取消发言 */
	public ReMsg cancelSpeak(long uid, long roomId) {
		Actor ua = this.getRoomActor(roomId, uid);
		if (ua != null) {
			MapBody mb = new MapBody(SocketHandler.CANCELSPEAK, SocketHandler.FN_ROOM_ID, roomId)
					.append(SocketHandler.FN_USER_ID, uid);
			this.pubRoomMsg(roomId, MsgType.ROOM, mb);
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL);
	}

}
