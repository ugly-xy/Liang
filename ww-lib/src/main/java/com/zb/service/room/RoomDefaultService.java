package com.zb.service.room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zb.common.Constant.ActivityType;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.redis.RK;
import com.zb.common.utils.JSONUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.common.utils.RegexUtil;
import com.zb.common.utils.T2TUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.VipMap;
import com.zb.models.finance.CoinLog;
import com.zb.models.room.Actor;
import com.zb.models.room.Room;
import com.zb.service.RelationshipService;
import com.zb.service.UserAttenRoomService;
import com.zb.service.UserInRoomLogService;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.MsgType;
import com.zb.socket.model.RoomId4Type;

@Service
public class RoomDefaultService extends RoomService {
	static final Logger log = LoggerFactory.getLogger(RoomDefaultService.class);

	@Autowired
	UserAttenRoomService userAttenRoomService;

	@Autowired
	UserInRoomLogService userInRoomLogService;

	@Autowired
	RelationshipService relationshipService;

	Map<Integer, Long> srMap = new HashMap<Integer, Long>();

	public static final int ROOM_MAX_COUNT = 3;// 用户最大包间数

	public Long getSpecificRoom(int type) {
		Long roomId = srMap.get(type);
		if (roomId == null) {
			BasicDBObject q = new BasicDBObject("_id", RoomId4Type.map.get(type))
					.append("useStatus", Room.USE_STATUS_OK).append("pub", true);
			DBObject dbo = getC(DocName.ROOM).findOne(q);
			if (null == dbo) {
				createSpecificRoom(RoomId4Type.map.get(type), type);
			}
			roomId = RoomId4Type.map.get(type);
			srMap.put(type, roomId);
		}
		return roomId;
	}

	public Room createSpecificRoom(Long id, int type) {
		return save(id, 0L, Room.SELL_STATUS_SHELVES, true, type, Room.OPEN);
	}

	// 老版本，从数据库获取
	public Long getActivityRoom(int type, String historyRoomIds, int userCount) {
		BasicDBObject q = new BasicDBObject("type", type).append("useStatus", Room.USE_STATUS_OK)
				.append("count", new BasicDBObject("$lt", userCount)).append("pub", true);

		if (!MsgType.ignoreRoomStatusType.contains(type)) {
			q.append("status", Room.OPEN);
		}
		List<DBObject> dbos = getC(DocName.ROOM).find(q).limit(20).sort(new BasicDBObject("count", -1).append("_id", 1))
				.toArray();
		Room r = null;
		for (DBObject dbo : dbos) {
			r = DboUtil.toBean(dbo, Room.class);
			if (r.getCount() > 0 && System.currentTimeMillis() - r.getUpdateTime() > 3 * Const.MINUTE) {
				super.closeRoom(r.get_id());
			} else if (null == historyRoomIds || "".equals(historyRoomIds.trim())) {
				break;
			} else if (historyRoomIds.toString().contains(r.get_id().toString())) {
				r = null;
				continue;
			}
		}
		if (r == null) {
			r = createRoom(type);
		}
		if (r != null) {
			return r.get_id();
		}
		return null;
	}

	// 双人匹配
	public Long matchingActivity(final int type, final int matchType) {
		long uid = super.getUid();
		int sex = T2TUtil.obj2Int(super.getUser("sex"), 0);
		if (sex == 0) {
			sex = 2;
		}
		int msex = 1;
		if (sex == 1) {
			msex = 2;
		}
		boolean match = true;
		long rid = 0;
		while (match) {
			if (type == ActivityType.GAME_CP.getType()) {// 随机匹配游戏
				String muid = getRedisTemplate().opsForList().leftPop(RK.matchUserList(0, type));
				if (muid != null) {// 匹配上随机的用户
					rid = canMatch(muid, uid, type, type);
					if (rid > 0) {
						match = false;
					}
				} else {// 匹配不上，匹配指定游戏类型用户
					for (int curtype : MsgType.matchRoomType) {
						muid = getRedisTemplate().opsForList().leftPop(RK.matchUserList(msex, curtype));
						if (muid == null) {
							muid = getRedisTemplate().opsForList().leftPop(RK.matchUserList(sex, curtype));
						}
						if (muid != null) {// 匹配上
							rid = canMatch(muid, uid, curtype, curtype);
							if (rid > 0) {
								match = false;
								break;
							}
						}
					}
					if (muid == null) {// 匹配不上
						getRedisTemplate().opsForList().rightPush(RK.matchUserList(0, type), "" + uid);
						getRedisTemplate().opsForZSet().add(RK.matchUser(type), "" + uid, System.currentTimeMillis());
						match = false;
					}
				}
			} else {// 指定游戏匹配
				String muid = getRedisTemplate().opsForList().leftPop(RK.matchUserList(msex, type));
				if (muid == null) {
					muid = getRedisTemplate().opsForList().leftPop(RK.matchUserList(sex, type));
				}
				int mType = type;
				if (muid == null) {
					muid = getRedisTemplate().opsForList().leftPop(RK.matchUserList(0, ActivityType.GAME_CP.getType()));
					mType = ActivityType.GAME_CP.getType();
				}
				if (muid == null) {// 没有可匹配用户
					getRedisTemplate().opsForList().rightPush(RK.matchUserList(sex, type), "" + uid);
					getRedisTemplate().opsForZSet().add(RK.matchUser(type), "" + uid, System.currentTimeMillis());
					match = false;
				} else {
					rid = canMatch(muid, uid, type, mType);
					if (rid > 0) {
						match = false;
					}
				}
			}
		}
		return rid;
	}

	static Object[] cps = MsgType.matchRoomType.toArray();

	private int getRandomType() {
		return (int) cps[RandomUtil.nextInt(cps.length)];
	}

	/**
	 * 
	 * @param muid
	 * @param uid
	 * @param type
	 *            开始游戏类型
	 * @param mType
	 *            匹配上游戏类型，用作删除用
	 * @return
	 */
	private long canMatch(String muid, long uid, int type, int mType) {
		long mLuid = T2TUtil.str2Long(muid);
		if (mLuid != uid) {// 匹配到自己，出现断线等情况重新加入
			long count = 0;
			count = getRedisTemplate().opsForZSet().remove(RK.matchUser(mType), muid);
			if (count > 0) {// 开始游戏
				if (userService.isOnline(mLuid)) {// 在线用户才开始
					return matchRoom2Start(type, uid, mLuid, Const.ACTIVITY_MATCH);
				}
			}
		}
		return 0;
	}

	// 双人机器人匹配
	public long matchingActivity(final long robitUid, final String muid, final int type, final int matchType) {
		int sex = 1;
		long mLuid = Long.parseLong(muid);
		long rid = 0;
		String smuid = getRedisTemplate().opsForList().leftPop(RK.matchUserList(sex, type));
		if (smuid != null) {
			if (smuid.equals(muid)) {
				long count = getRedisTemplate().opsForZSet().remove(RK.matchUser(type), muid);
				if (count > 0) {// 开始游戏
					if (userService.isOnline(mLuid)) {// 在线用户才开始
						rid = matchRoom2Start(type, robitUid, mLuid, Const.ACTIVITY_MATCH);
					}
				}
			} else {
				getRedisTemplate().opsForList().leftPush(RK.matchUserList(sex, type), smuid);
			}
		}

		sex = 2;
		smuid = getRedisTemplate().opsForList().leftPop(RK.matchUserList(sex, type));
		if (smuid != null) {
			if (smuid.equals(muid)) {
				long count = getRedisTemplate().opsForZSet().remove(RK.matchUser(type), muid);
				if (count > 0) {// 开始游戏
					if (userService.isOnline(mLuid)) {// 在线用户才开始
						rid = matchRoom2Start(type, robitUid, mLuid, Const.ACTIVITY_MATCH);
					}
				}
			} else {
				getRedisTemplate().opsForList().leftPush(RK.matchUserList(sex, type), smuid);
			}
		}
		return rid;
	}

	// 获取长时间未匹配用户
	public Set<String> unmatchUser(final int type, final int time) {
		long end = System.currentTimeMillis() - time * 1000;
		Set<String> uids = getRedisTemplate().opsForZSet().rangeByScore(RK.matchUser(type), 0, end);
		return uids;
	}

	public long matchRoom2Start(int type, final long uid, final Long fuid, final int model) {
		if (type == ActivityType.GAME_CP.getType()) {// 如果是父类型，随机选择一个子类型
			type = getRandomType();
		}
		long rid = getMatchActivityRoom(type);
		MapBody mb = new MapBody("roomId", rid).append("type", type).append("fuid", fuid).append("model", model);
		super.pubUserMsg(uid, MsgType.ACTIVITY_MATCHED, mb);
		mb.append("fuid", uid);
		super.pubUserMsg(fuid, MsgType.ACTIVITY_MATCHED, mb);
		if (rid > 0) {// 6秒等待用户进入房间
			super.addJob(rid, 6);
		}
		return rid;
	}

	// 老版本，从数据库获取
	public Long getMatchActivityRoom(int type) {
		BasicDBObject q = new BasicDBObject("type", type).append("useStatus", Room.USE_STATUS_OK)
				.append("status", Room.OPEN).append("pub", true);
		List<DBObject> dbos = getC(DocName.ROOM).find(q).limit(1).sort(new BasicDBObject("_id", 1)).toArray();
		long roomId;
		if (dbos.isEmpty()) {
			Room r = createRoom(type, Room.ACTIVITY);
			roomId = r.get_id();
		} else {
			roomId = DboUtil.getLong(dbos.get(0), "_id");
			getC(DocName.ROOM).update(new BasicDBObject("_id", roomId),
					new BasicDBObject("$set", new BasicDBObject("status", Room.ACTIVITY)));
		}
		return roomId;
	}

	// 取消匹配
	public ReMsg unMatchingActivity(final int type) {
		long uid = super.getUid();
		getRedisTemplate().opsForZSet().remove(RK.matchUser(type), "" + uid);
		return new ReMsg(ReCode.OK);
	}

	public Long getActivityRoomV2(long uid, int type, String historyRoomIds) {
		int max = 5;
		int offset = 0;
		int count = 5;
		if (type == MsgType.WEREWOLF.getT()) {
			max = 11;
		} else if (type == MsgType.WEREWOLF6.getT()) {
			max = 5;
		} else if (type == MsgType.WEREWOLF9.getT()) {
			max = 8;
		}
		Long id = null;
		int i = 0;
		if (super.lock(RK.userSelectGame(uid), 2)) {// 两秒内，一个用户只能选择一个游戏
			while (id == null) {
				offset = i * count + 1;
				Set<String> rids = getActivityRoomIds(type, max, offset, count);
				if (rids.size() > 0) {
					for (String cid : rids) {
						if (!contains(cid, historyRoomIds)) {
							id = Long.parseLong(cid);
							break;
						}
					}
				}
				if (id == null && rids.size() < count) {
					BasicDBObject q = new BasicDBObject("type", type).append("useStatus", Room.USE_STATUS_OK)
							.append("count", 0).append("status", Room.OPEN).append("pub", true);
					List<DBObject> dbos = getC(DocName.ROOM).find(q, new BasicDBObject("_id", 1))
							.sort(new BasicDBObject("_id", 1)).limit(5).toArray();
					if (dbos.size() == 0) {
						Room r = createRoom(type);
						id = r.get_id();
						addActivityRoomId(type, id, 0);
					} else {
						for (DBObject dbo : dbos) {
							id = DboUtil.getLong(dbo, "_id");
							addActivityRoomId(type, id, 0);
						}
					}
				}
				i++;
			}
			changeActivityRoomScore(type, id, 1);
			log.error("getActivityRoomV2:" + type + " ,roomId：" + id + " ,uid:" + uid);
		}
		return id;
	}

	private boolean contains(String id, String historyRoomIds) {
		if (StringUtils.isNotBlank(historyRoomIds)) {
			if (historyRoomIds.contains(id)) {
				return true;
			}
		}
		return false;
	}

	// public Room getActivityRoom(int type) {
	// return getActivityRoom(type, null);
	// }

	@Override
	public ReMsg handle(Room room, long fr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReMsg outRoom(long uid, Room r) {
		// TODO Auto-generated method stub
		return new ReMsg(ReCode.OK);
	}

	@Override
	public ReMsg disconnectRoom(long uid, Room r) {
		// TODO Auto-generated method stub
		return new ReMsg(ReCode.OK);
	}

	@Override
	public ReMsg kick(long uid, Room r) {
		// TODO Auto-generated method stub
		return new ReMsg(ReCode.OK);
	}

	// @Override
	// public ReMsg userFastCard(long uid, Room r) {
	// // TODO Auto-generated method stub
	// return null;
	// }

	@Override
	public ReMsg getActorStatus(long uid, Room r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReMsg canInRoom(int type, long actId, long roomId) {
		return new ReMsg(ReCode.OK, 2);
	}

	public void findZombiesRoom() {
		DBObject q = new BasicDBObject("count", new BasicDBObject("$gt", 0));
		q.put("updateTime", new BasicDBObject("$lt", System.currentTimeMillis() - 30 * Const.MINUTE));
		List<DBObject> dbos = getC(DocName.ROOM).find(q, new BasicDBObject("_id", 1)).toArray();
		for (DBObject dbo : dbos) {
			long roomId = DboUtil.getLong(dbo, "_id");
			super.closeRoom(roomId);
			log.error("CLOSE ZombiesRoom:", roomId);
		}
	}

	public List<Long> getRoomIds() {
		List<Long> list = new ArrayList<>();
		DBObject q = new BasicDBObject("count", new BasicDBObject("$gt", 0));
		List<DBObject> temp = getC(DocName.ROOM).find(q)
				// .skip(index).limit(20)
				.sort(new BasicDBObject("updateTime", -1)).toArray();
		if (!temp.isEmpty()) {
			for (DBObject dbo : temp) {
				Room r = DboUtil.toBean(dbo, Room.class);
				list.add(r.get_id());
			}
		}
		return list;
	}

	@Override
	public ReMsg inRoom(long uid, Room r, int model, boolean robit) {
		// TODO Auto-generated method stub
		return null;
	}

	static String aaa = "(\\d)\\1\\1";
	static String abc = "123|234|345|456|567|678|789";
	static String cba = "987|876|765|654|543|432|321";
	static String aab = "(\\d)\\1\\d";
	static String abb = "(\\d)(\\d)\\2";
	static String d3 = "(\\d){3}";

	static String aaaa = "(\\d)\\1\\1\\1";
	static String abcd = "1234|2345|3456|4567|5678|6789";
	static String dcba = "9876|8765|7654|6543|5432|4321";
	static String aabb = "(\\d)\\1(\\d)\\2";
	static String abbb = "(\\d)(\\d)\\2\\2";
	static String aaab = "(\\d)\\1\\1\\d";
	static String abcc = "(\\d)(\\d)(\\d)\\3";
	static String d4 = "(\\d){4}";

	static String aaaaa = "(\\d)\\1\\1\\1\\1";
	static String aaaab = "(\\d)\\1\\1\\1\\d";
	static String aaabb = "(\\d)\\1\\1(\\d)\\2";
	static String aabbb = "(\\d)\\1(\\d)\\2\\2";
	static String abbbb = "(\\d)(\\d)\\2\\2\\2";
	static String abcde = "12345|23456|34567|45678|56789";
	static String edbca = "98765|87654|76543|65432|54321";
	static String abccc = "(\\d)(\\d)(\\d)\\3\\3";
	static String abcdd = "(\\d)(\\d)(\\d)(\\d)\\4";
	static String aaabc = "(\\d)\\1\\1(\\d)(\\d)";
	static String d5 = "(\\d){5}";

	static String[] r3 = { aaa, abc, abb, cba, aab, d3 };
	static String[] r4 = { aaaa, abcd, dcba, aabb, abbb, aaab, abcc, d4 };
	static String[] r5 = { aaaaa, abbbb, aaaab, aaabb, aabbb, abcde, edbca, abccc, abcdd, aaabc, d5 };
	// static Map<Double, String> rateMap = new HashMap<Double, String>();

	static Double[] rate = { 0.0001, 0.0002, 0.0003, 0.0003, 0.0005, 0.001, 0.0006, 0.0012, 0.0014, 0.002, 0.0012,
			0.0032, 0.0025, 0.005, 0.002, 0.004, 0.006, 0.0045, 0.0048, 0.006, 0.01, 0.01, 0.05, 0.1, 0.7832 };
	static String[] vipRoomType = { "aaa", "abc", "cba", "abb", "aab", "d3", "aaaa", "abbb", "abcd", "dcba", "aabb",
			"abcc", "aaab", "d4", "aaaaa", "abcde", "edcba", "abbbb", "aabbb", "aaabb", "aaaab", "abccc", "abcdd",
			"aaabc", "d5" };

	static Map<String, String> map = new HashMap<String, String>();
	static {
		map.put(aaa, "aaa");
		map.put(abb, "abb");
		map.put(aab, "aab");
		map.put(abc, "abc");
		map.put(cba, "cba");
		map.put(d3, "d3");

		map.put(aaaa, "aaaa");
		map.put(abcd, "abcd");
		map.put(dcba, "dcba");
		map.put(aabb, "aabb");
		map.put(abbb, "abbb");
		map.put(aaab, "aaab");
		map.put(abcc, "abcc");
		map.put(d4, "d4");

		map.put(aaaaa, "aaaaa");
		map.put(aaaab, "aaaab");
		map.put(aaabb, "aaabb");
		map.put(aabbb, "aabbb");
		map.put(abbbb, "abbbb");
		map.put(abcde, "abcde");
		map.put(edbca, "edbca");
		map.put(abccc, "abccc");
		map.put(abcdd, "abcdd");
		map.put(aaabc, "aaabc");
		map.put(d5, "d5");

	}

	public void initVipRoom(int id) {
		int t = 0;
		for (int i = 100; i < 100000; i++) {
			String type = vipRoomType(i);
		}
	}

	public String vipRoomType(long id) {
		String s = id + "";
		if (id < 1000) {
			for (String exp : r3) {
				if (RegexUtil.isMatches(exp, s)) {
					return map.get(exp);
				}
			}
		} else if (id < 10000) {
			for (String exp : r4) {
				if (RegexUtil.isMatches(exp, s)) {
					return map.get(exp);
				}
			}
		} else {
			for (String exp : r5) {
				if (RegexUtil.isMatches(exp, s)) {
					return map.get(exp);
				}
			}
		}
		return null;
	}

	/** 后台更改售卖房间信息 */
	public ReMsg updateRoom(Long roomId, Long buyUid, Integer useStatus, Integer sellStatus, Boolean sys, Long expiry) {
		DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
		if (buyUid != null && buyUid != 0) {
			u.put("buyUid", buyUid);
			u.put("lotteryTime", System.currentTimeMillis());
		}
		if (useStatus != null && useStatus != 0) {
			u.put("useStatus", useStatus);
		}
		if (sellStatus != null && sellStatus != 0) {
			u.put("sellStatus", sellStatus);
		}
		if (sys != null) {
			u.put("sys", sys);
		}
		if (expiry != null) {
			u.put("expiry", expiry);
		}
		u.put("vipRoomType", vipRoomType(roomId));
		updateRoom(new BasicDBObject("_id", roomId), u);
		return new ReMsg(ReCode.OK);
	}

	public void updateRoom(DBObject q, DBObject u) {
		u.put("updateTime", System.currentTimeMillis());
		getC(DocName.ROOM).update(q, new BasicDBObject("$set", u), false, true);
	}

	/** 后台创建房间 roomIds:888888,666666,555555-555560 */
	public ReMsg adminCreateRoom(String roomIds, int sellStatus) {
		Set<Room> rooms = new HashSet<Room>();
		String[] split = roomIds.split(",");
		Room room = null;
		long beginId = 0;
		long endId = 0;
		for (String ids : split) {// 多个房间,分隔
			if (ids.contains("-")) {// -号代表创建该区间的所有不存在的房间 200199-200999
				String[] split2 = ids.split("-");
				if (split2.length == 2) {
					beginId = Long.parseLong(split2[0]);
					endId = Long.parseLong(split2[1]);
					while (beginId <= endId) {
						room = getRoom(beginId);
						if (room == null) {
							room = new Room(beginId, 0L, sellStatus, false, ActivityType.CHATBOX.getType(), Room.OPEN,
									false, vipRoomType(beginId));
							rooms.add(room);
							System.out.println(beginId);
						} else if (room.getSellStatus() != Room.SELL_STATUS_SOLD
								&& room.getSellStatus() != Room.SELL_STATUS_OBLIGATION) {
							room = new Room(beginId, 0L, sellStatus, false, ActivityType.CHATBOX.getType(), Room.OPEN,
									false, vipRoomType(beginId));
							super.getMongoTemplate().save(room);
						}
						beginId += 1;
					}
				}
			} else {
				room = getRoom(Long.parseLong(ids));
				if (room == null) {
					room = new Room(Long.parseLong(ids), 0L, sellStatus, false, ActivityType.CHATBOX.getType(),
							Room.OPEN, false, vipRoomType(Long.parseLong(ids)));
					rooms.add(room);
				} else if (room.getSellStatus() != Room.SELL_STATUS_SOLD
						&& room.getSellStatus() != Room.SELL_STATUS_OBLIGATION) {
					room = new Room(Long.parseLong(ids), 0L, sellStatus, false, ActivityType.CHATBOX.getType(),
							Room.OPEN, false, vipRoomType(beginId));
					super.getMongoTemplate().save(room);
				}
			}
		}
		super.getMongoTemplate().insertAll(rooms);
		return new ReMsg(ReCode.OK);
	}

	public static final int BASE = 100000;
	public static final String DEFAULT_TYPE = "d5";

	/** 抽取房间类型 */
	public String randomVipRoomType() {
		int random = RandomUtil.nextInt(BASE);
		int spike = 0;
		for (int i = 0; i < rate.length; i++) {
			spike = spike + (int) (BASE * rate[i]);
			if (random <= spike) {
				return vipRoomType[i];
			}
		}
		return DEFAULT_TYPE;
	}

	public static String test() {
		int random = RandomUtil.nextInt(BASE);
		int spike = 0;
		for (int i = 0; i < rate.length; i++) {
			spike = spike + (int) (BASE * rate[i]);
			if (random <= spike) {
				return vipRoomType[i];
			}
		}
		return DEFAULT_TYPE;
	}

	public static void main(String[] args) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < 100000; i++) {
			String type = test();
			if (map.containsKey(type)) {
				map.put(type, map.get(type) + 1);
			} else {
				map.put(type, 1);
			}
		}

		for (Entry<String, Integer> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}

	/** job重置过期未付款房间 */
	public void resetBuyRoomJob() {
		DBObject q = new BasicDBObject();
		// 抽取未付款 且时间已过半小时
		q.put("sellStatus", Room.SELL_STATUS_OBLIGATION);
		// q.put("sys", false);
		q.put("lotteryTime", new BasicDBObject("$lte", System.currentTimeMillis() - Const.MINUTE * 30));
		DBObject u = new BasicDBObject();
		// 重置状态
		u.put("sellStatus", Room.SELL_STATUS_SALES);
		u.put("lotteryTime", 0L);
		u.put("buyUid", 0L);
		updateRoom(q, u);
	}

	/** job重置过期一周未续费房间 */
	public void resetExpiryRoomJob() {
		DBObject q = new BasicDBObject();
		// 已售出的非系统房间 有效期已过期一周
		q.put("sellStatus", Room.SELL_STATUS_SOLD);
		q.put("count", 0);// 房间内无人
		// q.put("sys", false);
		q.put("expiry", new BasicDBObject("$lte", System.currentTimeMillis() - Const.DAY * 7));

		List<DBObject> list = getC(DocName.ROOM).find(q).toArray();
		if (list.size() > 0) {
			DBObject u = new BasicDBObject();
			// 重置状态
			u.put("sellStatus", Room.SELL_STATUS_SALES);
			u.put("expiry", 0L);
			u.put("buyUid", 0L);
			u.put("rName", null);// 房间名
			u.put("attenCnt", null);// 关注人数
			u.put("pwd", null);// 密码
			u.put("pub", false);// 是否公开
			u.put("type", ActivityType.CHATBOX.getType());
			updateRoom(q, u);

			Long[] ids = new Long[list.size()];
			for (int i = 0; i < list.size(); i++) {
				ids[i] = DboUtil.getLong(list.get(i), "_id");
			}
			userAttenRoomService.removeAttenRooms(ids);
			userInRoomLogService.removeInRoomLogs(ids);
		}
	}

	/** 用户抽取房间 */
	public ReMsg lotteryRoom(long uid, Long lastRoomId) {
		if (super.lock("lotteryRoom:" + uid, 2)) {
			try {
				List<DBObject> dbos = null;
				if (null != lastRoomId && lastRoomId > 0) {// 放弃房间
					waiveRoom(uid, lastRoomId);
				}
				if (super.count(null, null, uid, null, Room.SELL_STATUS_OBLIGATION, null, null, null) > 0) {
					// 存在没付钱的房间 不能再次抽房间
					return new ReMsg(ReCode.HAVE_OBLIGATION_ROOM);
				}
				if (count(null, null, uid, null, Room.SELL_STATUS_SOLD, null, null, null) >= ROOM_MAX_COUNT) {
					// 已拥有房间数量上限 无法抽取
					return new ReMsg(ReCode.ROOM_COUNT_MAX);
				}
				ReCode rc = coinService.reduce(uid, CoinLog.OUT_BUYROOM, uid, 1000, 0, "花费金币抽取房间");
				if (rc.reCode() == ReCode.OK.reCode()) {// 扣钱成功 抽取房间
					int cnt = 0;
					DBObject room = null;
					while (room == null) {
						if (cnt > 10) {// 随便给一个上架的房间
							dbos = find(null, null, null, null, Room.SELL_STATUS_SALES, null, null, null, 1, 1);
							if (dbos != null && dbos.size() > 0) {// 还有
								room = dbos.get(0);
							} else {// 再也没有上架的房间了
								super.unlock("lotteryRoom:" + uid);
								return new ReMsg(ReCode.NO_SALES_ROOM);
							}
						} else {
							cnt++;
							String roomType = randomVipRoomType();
							DBObject q = getQ(null, null, null, null, Room.SELL_STATUS_SALES, null, roomType, null);
							DBCursor cursor = getC(DocName.ROOM).find(q);
							if (cursor.count() > 0) {
								int temp = RandomUtil.nextInt(cursor.count());
								cursor.skip(temp);
								if (cursor.hasNext()) {
									room = cursor.next();
									cursor.close();
								}
							}
						}
					}
					room.put("sellStatus", Room.SELL_STATUS_OBLIGATION);
					room.put("buyUid", uid);
					room.put("lotteryTime", System.currentTimeMillis());
					room.put("updateTime", System.currentTimeMillis());
					super.getC(DocName.ROOM).save(room);
					super.unlock("lotteryRoom:" + uid);
					return new ReMsg(ReCode.OK, room);
				} else {
					super.unlock("lotteryRoom:" + uid);
					return new ReMsg(rc);
				}
			} finally {
				super.unlock("lotteryRoom:" + uid);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	// 房间时间长度及价格
	public static final int[] ROOM_TIME = new int[] { 0, 90, 180, 365 };
	public static final int[] ROOM_PRICE = new int[] { 0, 100000, 200000, 400000 };

	/**
	 * 用户购买房间或续费 type: 1 90天 2 180天 3 365天
	 */
	public ReMsg buyRoom(long uid, final long roomId, int type) {
		int time = ROOM_TIME[type];
		int price = ROOM_PRICE[type];
		Room room = getRoom(roomId);
		if (super.lock("buyRoom:" + roomId, 2)) {
			try {
				// 非空 且不是系统房间 房主是自己
				if (null != room && !room.isSys() && room.getBuyUid() == uid) {
					long nowTime = System.currentTimeMillis();
					if (room.getSellStatus() == Room.SELL_STATUS_OBLIGATION) {// 已抽取待购买
						// if (count(null, null, uid, null,
						// Room.SELL_STATUS_SOLD, null, null, null) >=
						// ROOM_MAX_COUNT) {
						// // 已拥有房间数量上限
						// return new ReMsg(ReCode.ROOM_COUNT_MAX);
						// }
						room.setExpiry(nowTime + Const.DAY * time);
						room.setSellStatus(Room.SELL_STATUS_SOLD);
						room.setPwd(null);// 重置密码
						room.setrName(DboUtil.getString(userService.findById(uid), "nickname") + "的房间");
					} else if (room.getSellStatus() == Room.SELL_STATUS_SOLD) {// 已售
						if (room.getExpiry() <= nowTime) {// 已过期 续费
							room.setExpiry(nowTime + Const.DAY * time);
						} else {// 未过期续费
							room.setExpiry(room.getExpiry() + Const.DAY * time);
						}
					}
					ReCode rc = coinService.reduce(uid, CoinLog.OUT_BUYROOM, room.get_id(), price, 0, "购买房间" + time);
					if (rc.reCode() == ReCode.OK.reCode()) {// 扣钱成功
						room.setUpdateTime(System.currentTimeMillis());
						super.getMongoTemplate().save(room);
						super.unlock("buyRoom:" + roomId);
						return new ReMsg(ReCode.OK, room);
					}
					super.unlock("buyRoom:" + roomId);
					return new ReMsg(rc);
				}
			} finally {
				super.unlock("buyRoom:" + roomId);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 用户放弃该房间或放弃付款 */
	public ReMsg waiveRoom(long uid, long roomId) {
		Room room = getRoom(roomId);
		if (room != null && !room.isSys() && room.getBuyUid() == uid) {
			if (room.getCount() != 0) {// 房间内有人 关闭房间
				super.closeRoom(roomId);
			}
			room.setSellStatus(Room.SELL_STATUS_SALES);
			room.setBuyUid(0L);
			room.setExpiry(0L);
			room.setrName(null);
			room.setPwd(null);
			room.setPub(false);
			room.setType(ActivityType.CHATBOX.getType());
			room.setLotteryTime(0L);
			room.setUpdateTime(System.currentTimeMillis());
			super.getMongoTemplate().save(room);
			// 取消用户对该房间的关注
			userAttenRoomService.removeAttenRooms(roomId);
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 查询我的包间 */
	public ReMsg queryMyRoom(long uid, Integer sellStatus, Integer page, Integer size) {
		if (null == sellStatus) {
			sellStatus = Room.SELL_STATUS_SOLD;
		}
		page = super.getPage(page);
		size = super.getSize(size);
		List<DBObject> dbos = super.find(null, null, uid, null, sellStatus, false, null, null, page, size);
		DBObject user = userService.findById(uid);
		for (DBObject room : dbos) {
			room.put("masterName", DboUtil.getString(user, "nickname"));
			room.put("masterAvatar", DboUtil.getString(user, "avatar"));
			Boolean pub = DboUtil.getBool(room, "pub");
			// 公开的 系统可以匹配进入 或者是密码字段为空或空串
			if (null == pub || (null != pub && pub) || StringUtils.isBlank(DboUtil.getString(room, "pwd"))) {
				room.put("pwd", false);
			} else {
				room.put("pwd", true);
			}
		}
		return new ReMsg(ReCode.OK, dbos);
	}

	/** 查询我的未付款包间 */
	public ReMsg queryMyNotPayRoom(long uid) {
		List<DBObject> dbos = super.find(null, null, uid, null, Room.SELL_STATUS_OBLIGATION, false, null, null, 1, 1);
		if (null != dbos && dbos.size() > 0) {
			return new ReMsg(ReCode.OK, dbos.get(0));
		}
		return new ReMsg(ReCode.OK);
	}

	/** 房主设置房间信息 密码 名字 快速进入 */
	@SuppressWarnings("deprecation")
	public ReMsg upSetRoomInfo(long uid, long roomId, Boolean pub, String pwd, String roomName, Integer type) {
		Room room = getRoom(roomId);
		// 购买人操作自己的已售房间
		if (room != null && room.getSellStatus() == Room.SELL_STATUS_SOLD && room.getBuyUid() == uid) {
			boolean save = false;
			if (pwd != null) {// 密码
				if (pwd.equals("aabbccdd")) {
					room.setPwd("");
				} else {
					room.setPwd(pwd.replaceAll(" ", ""));
				}
				save = true;
			}
			if (StringUtils.isNotBlank(roomName)) {// 房间名 最长16
				if (roomName.length() > 16) {
					roomName = roomName.substring(0, 16);
				}
				room.setrName(roomName);
				save = true;
			}
			if (null != pub) {// 快速进入
				room.setPub(pub);
				save = true;
			}
			ReCode rc = ReCode.OK;
			boolean openActive = false;
			if (null != type && type != 0) {// 房间类型
				if (room.getStatus() == Room.OPEN && room.getType() != type) {// 切换房间活动
					if (type == ActivityType.DICE.getType()
							&& VipMap.getLevel(DboUtil.getInt(userService.findById(uid), "vip")) < 1) {
						rc = ReCode.DICE_VIP_LEVEL_LOW;
					} else {
						save = true;
						openActive = true;
					}
				} else {
					rc = ReCode.CANNOT_UPSET_ROOMINFO;
				}
			}
			if (openActive) {// 需要切换房间活动
				rc = openActive(uid, room, type);
			} else if (save) {// 需要保存
				room.setUpdateTime(System.currentTimeMillis());
				super.getMongoTemplate().save(room);
			}
			return new ReMsg(rc);
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 用户关注或取消关注某个房间 */
	public ReMsg saveUserAttenRoom(long uid, long roomId, boolean attenRoom) {
		Room room = getRoom(roomId);
		if (room.getSellStatus() == Room.SELL_STATUS_SOLD && room.getBuyUid() != uid) {// 已售房间不是房主
			userAttenRoomService.saveUserAttenRoom(uid, room, attenRoom);
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 最近进入的房间 按照时间排序 */
	public ReMsg userInRooms(long uid, Integer page, Integer size) {
		page = super.getPage(page);
		size = super.getSize(size);
		List<DBObject> logs = userInRoomLogService.findUserInRoomLog(uid, null, page, size);
		long[] ids = new long[logs.size()];
		for (int i = 0; i < logs.size(); i++) {
			ids[i] = DboUtil.getLong(logs.get(i), "roomId");
		}
		BasicDBObject q = new BasicDBObject("_id", new BasicDBObject("$in", ids));
		List<DBObject> dbos = getC(DocName.ROOM).find(q).toArray();
		return new ReMsg(ReCode.OK, addUserInfo(sort(ids, dbos)));
	}

	/** 我的好友包间 */
	public ReMsg friendsRooms(long uid, Integer page, Integer size) {
		List<Long> dbos = relationshipService.getFrinds(uid);
		int len = dbos.size();
		if (len != 0) {
			Long[] uids = new Long[dbos.size()];
			dbos.toArray(uids);
			List<DBObject> rooms = findFriendsRooms(uids, page, size);
			return new ReMsg(ReCode.OK, addUserInfo(rooms));
		}
		return new ReMsg(ReCode.OK);
	}

	/** 热门房间 按照更新时间排序 */
	public ReMsg hotRooms(Integer page, Integer size) {
		page = super.getPage(page);
		size = super.getSize(size);
		BasicDBObject q = new BasicDBObject("sellStatus", Room.SELL_STATUS_SOLD).append("count",
				new BasicDBObject("$gt", 0));
		List<DBObject> rooms = getC(DocName.ROOM).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("updateTime", -1)).toArray();
		return new ReMsg(ReCode.OK, addUserInfo(rooms));
	}

	/** 用户关注的包间列表 */
	public ReMsg userAttenRooms(long uid, Integer page, Integer size) {
		page = super.getPage(page);
		size = super.getSize(size);
		List<DBObject> logs = userAttenRoomService.findUserAttenRoom(uid, page, size);
		long[] ids = new long[logs.size()];
		for (int i = 0; i < logs.size(); i++) {
			ids[i] = DboUtil.getLong(logs.get(i), "roomId");
		}
		BasicDBObject q = new BasicDBObject("_id", new BasicDBObject("$in", ids));
		List<DBObject> dbos = getC(DocName.ROOM).find(q).toArray();
		return new ReMsg(ReCode.OK, addUserInfo(sort(ids, dbos)));
	}

	/** 校验密码 */
	public ReMsg checkPwd(long roomId, String pwd) {
		Room room = getRoom(roomId);
		if (StringUtils.isNotBlank(room.getPwd())) {
			if (room.getPwd().equals(pwd)) {
				return new ReMsg(ReCode.OK);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 检查是否已经关注该房间 */
	public ReMsg isAttenRoom(long uid, long roomId) {
		DBObject dbo = userAttenRoomService.findById(uid, roomId);
		if (null != dbo) {// 已关注该房间
			return new ReMsg(ReCode.OK, 1);
		}
		return new ReMsg(ReCode.OK, 2);
	}

	/** 个人资料页 ta的包间 */
	public ReMsg userSpaceRoom(long uid) {
		Long roomId = null;
		DBObject q = new BasicDBObject("buyUid", uid).append("sellStatus", Room.SELL_STATUS_SOLD);
		List<DBObject> dbos = getC(DocName.ROOM).find(q).skip(super.getStart(1, 1)).limit(getSize(1))
				.sort(new BasicDBObject("count", -1).append("updateTime", -1)).toArray();
		if (null != dbos && dbos.size() > 0) {
			roomId = DboUtil.getLong(dbos.get(0), "_id");
		}
		return new ReMsg(ReCode.OK, roomId);
	}

	public List<DBObject> sort(long[] ids, List<DBObject> dbos) {
		List<DBObject> rooms = new ArrayList<DBObject>();
		for (int i = 0; i < ids.length; i++) {
			long id = ids[i];
			Iterator<DBObject> iterator = dbos.iterator();
			while (iterator.hasNext()) {
				DBObject dbo = iterator.next();
				if (DboUtil.getLong(dbo, "_id") == id) {
					rooms.add(dbo);
					iterator.remove();
					break;
				}
			}
		}
		return rooms;
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

	/** addUserInfo */
	public List<DBObject> addUserInfo(List<DBObject> list) {
		List<DBObject> res = new ArrayList<DBObject>();
		DBObject user = null;
		for (DBObject room : list) {
			if (DboUtil.getInt(room, "sellStatus") == Room.SELL_STATUS_SOLD) {// 房间已售
				user = userService.findById(DboUtil.getLong(room, "buyUid"));
				room.put("masterName", DboUtil.getString(user, "nickname"));
				room.put("masterAvatar", DboUtil.getString(user, "avatar"));
				Boolean pub = DboUtil.getBool(room, "pub");
				// 公开的 系统可以匹配进入 或者是密码字段为空或空串
				if (null == pub || (null != pub && pub) || StringUtils.isBlank(DboUtil.getString(room, "pwd"))) {
					room.put("pwd", false);
				} else {
					room.put("pwd", true);
				}
				res.add(room);
			}
		}
		return res;
	}
}
