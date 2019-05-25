package com.zb.service.room;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.ActivityType;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.IMType;
import com.zb.common.Constant.OperationType;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.redis.RK;
import com.zb.common.utils.DateUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.common.utils.T2TUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.VipMap;
import com.zb.models.WerewolfKillLog;
import com.zb.models.room.GameResource;
import com.zb.models.room.Room;
import com.zb.models.room.activity.OnlineActivity;
import com.zb.service.BaseService;
import com.zb.service.MessageService;
import com.zb.service.UserInRoomLogService;
import com.zb.service.UserService;
import com.zb.service.room.vo.ActivityForbiddenLog;
import com.zb.service.room.vo.ActivityPraiseLog;
import com.zb.service.room.vo.ActivityReportLog;
import com.zb.service.room.vo.ActivityReportSt;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.Msg;
import com.zb.socket.model.MsgType;
import com.zb.socket.model.SocketHandler;

@Service
public class UserActivityService extends BaseService {
	static final Logger log = LoggerFactory.getLogger(UserActivityService.class);

	@Autowired
	RoomDefaultService roomDefaultService;

	@Autowired
	RoomHandlerDispatcher roomHandlerDispatcher;

	@Autowired
	UserService userService;

	@Autowired
	MessageService messageService;

	@Autowired
	UserInRoomLogService userInRoomLogService;

	public ReMsg userInActivity(int activityType, String historyRoomIds) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		// 能否进入房间
		DBObject user = userService.findById(uid);
		DBObject game = getOnlineActivity((long) activityType);
		// 获取用户经验和vip等级
		Integer point = UserService.getLevel(DboUtil.getInt(user, "point"));
		Integer vip = VipMap.getLevel(DboUtil.getInt(user, "vip"));
		// 经验等级不足 或vip等级不足
		if (point < DboUtil.getInt(game, "point") || vip < DboUtil.getInt(game, "vip")) {
			return new ReMsg(ReCode.ROOL_NO);
		}
		// 狼人杀胜场判断
		if (ActivityType.WEREWOLF9.getType() == activityType || ActivityType.WEREWOLF.getType() == activityType) {
			int winCount = 0;
			List<DBObject> list = getC(DocName.WEREWOLF_KILL_LOG).find(new BasicDBObject("uid", uid)).toArray();
			for (DBObject dbo : list) {
				WerewolfKillLog wl = DboUtil.toBean(dbo, WerewolfKillLog.class);
				winCount += wl.getAllWinCount();
			}
			if (ActivityType.WEREWOLF9.getType() == activityType) {
				if (winCount < 10) {
					return new ReMsg(ReCode.WINCOUNTNOTENOUGH);
				}
			} else {
				if (winCount < 20) {
					return new ReMsg(ReCode.WINCOUNTNOTENOUGH);
				}
			}
		}
		Long rid = null;
		if (MsgType.specificRoomType.contains(activityType)) {
			rid = roomDefaultService.getSpecificRoom(activityType);
		} else if (MsgType.matchRoomType.contains(activityType) || activityType == ActivityType.GAME_CP.getType()) {
			rid = roomDefaultService.matchingActivity(activityType, 0);
		} else {
			if (MsgType.wolfRoom.keySet().contains(activityType)) {
				rid = roomDefaultService.getActivityRoom(activityType, historyRoomIds,
						MsgType.wolfRoom.get(activityType));
			} else {
				rid = roomDefaultService.getActivityRoom(activityType, historyRoomIds, 6);
			}
		}
		if (rid != null) {
			return new ReMsg(ReCode.OK, rid);
		} else {
			return new ReMsg(ReCode.FAIL);
		}
	}

	public ReMsg userInActivityV2(int activityType, String historyRoomIds) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		// 能否进入房间
		DBObject user = userService.findById(uid);
		DBObject game = getOnlineActivity((long) activityType);
		// 获取用户经验和vip等级
		Integer point = UserService.getLevel(DboUtil.getInt(user, "point"));
		Integer vip = VipMap.getLevel(DboUtil.getInt(user, "vip"));
		// 经验等级不足 或vip等级不足
		if (point < DboUtil.getInt(game, "point") || vip < DboUtil.getInt(game, "vip")) {
			return new ReMsg(ReCode.ROOL_NO);
		}

		Long rid = null;
		if (MsgType.specificRoomType.contains(activityType)) {
			rid = roomDefaultService.getSpecificRoom(activityType);
		} else {
			rid = roomDefaultService.getActivityRoomV2(uid, activityType, historyRoomIds);
		}
		if (rid != null) {
			return new ReMsg(ReCode.OK, rid);
		} else {
			return new ReMsg(ReCode.FAIL);
		}
	}

	// 邀请游戏
	public ReMsg inviteActivity(int activityType, long fuid) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		long id = incrId(RK.activityId());
		super.getRedisTemplate().opsForValue().set(RK.inviteActivity(id),
				activityType + "-" + uid + "-" + fuid + "-" + "1", 31, TimeUnit.SECONDS);
		// 游戏邀请通知
		MapBody mb = new MapBody("type", "invite").append("uid", uid).append("gameType", activityType)
				.append("activityId", id).append("model", 1);
		roomDefaultService.pubUserMsg(fuid, MsgType.GAMECP_INVITE, mb);

		// FIXME 邀请二人对战游戏
		String activityName = ActivityType.getActivityName(activityType);
		Map<String, Object> ext = new HashMap<String, Object>();
		ext.put("type", IMType.GAME_CP.getType());// 消息类型 二人对战游戏 gameCP
		ext.put("op", activityType); // 游戏类型 type
		ext.put("opId", null);
		ext.put("st", System.currentTimeMillis());// 开始时间
		ext.put("limit", 30);// 有效时长
		ext.put("activityId", id);
		String body = "[" + uid + "] 邀请 [" + fuid + "] 一起来玩一局 " + activityName;
		messageService.imMsgHandler(uid, fuid, body, ext, IMType.GAME_CP.getType());
		// TODO 发送邀请请求,或者客户端直接发
		// messageService.imMsgHandler(uid, fuid, "xxx邀请你玩游戏", ext,
		// imType);
		// 需要包含，游戏类型，开始时间，有效时长
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("activityId", id);
		return new ReMsg(ReCode.OK, res);
	}

	// 再来一局游戏
	public ReMsg playAgainActivity(int activityType, long fuid) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		long id = incrId(RK.activityId());
		super.getRedisTemplate().opsForValue().set(RK.inviteActivity(id),
				activityType + "-" + uid + "-" + fuid + "-" + "2", 31, TimeUnit.SECONDS);
		// 游戏邀请通知
		MapBody mb = new MapBody("type", "invite").append("uid", uid).append("gameType", activityType)
				.append("activityId", id).append("model", 2);
		roomDefaultService.pubUserMsg(fuid, MsgType.GAMECP_INVITE, mb);
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("activityId", id);
		return new ReMsg(ReCode.OK, res);
	}

	// 接受邀请
	public ReMsg invitedActivity(long activityId) {
		String uids = super.getRedisTemplate().opsForValue().get(RK.inviteActivity(activityId));
		if (uids != null) {// 开始游戏
			super.getRedisTemplate().delete(RK.inviteActivity(activityId));
			String[] arrU = uids.split("-");
			roomDefaultService.matchRoom2Start(T2TUtil.str2Int(arrU[0]), T2TUtil.str2Long(arrU[1]),
					T2TUtil.str2Long(arrU[2]), Const.ACTIVITY_INVITE);
			return new ReMsg(ReCode.OK);
		} else {
			return new ReMsg(ReCode.INVITE_INVALID);
		}
	}

	// 取消邀请
	public ReMsg cancelInviteActivity(long activityId) {
		String uids = super.getRedisTemplate().opsForValue().get(RK.inviteActivity(activityId));
		if (uids != null) {
			// 取消游戏邀请通知
			String[] arrU = uids.split("-");
			MapBody mb = new MapBody("type", "cancelInvite").append("uid", T2TUtil.str2Long(arrU[1]))
					.append("gameType", T2TUtil.str2Long(arrU[0])).append("activityId", activityId)
					.append("model", T2TUtil.str2Int(arrU[3]));
			roomDefaultService.pubUserMsg(T2TUtil.str2Long(arrU[2]), MsgType.GAMECP_INVITE, mb);
			super.getRedisTemplate().delete(RK.inviteActivity(activityId));
		}
		return new ReMsg(ReCode.OK);
	}

	// 拒绝邀请
	public ReMsg refuseInviteActivity(long activityId) {
		String uids = super.getRedisTemplate().opsForValue().get(RK.inviteActivity(activityId));
		if (uids != null) {
			// 取消游戏邀请通知
			String[] arrU = uids.split("-");
			MapBody mb = new MapBody("type", "refuseInvite").append("uid", T2TUtil.str2Long(arrU[1]))
					.append("gameType", T2TUtil.str2Long(arrU[0])).append("activityId", activityId)
					.append("model", T2TUtil.str2Int(arrU[3]));
			roomDefaultService.pubUserMsg(T2TUtil.str2Long(arrU[1]), MsgType.GAMECP_INVITE, mb);
			super.getRedisTemplate().delete(RK.inviteActivity(activityId));
		}
		return new ReMsg(ReCode.OK);
	}

	public ReMsg canInRoom(int type, long actId, long roomId, HttpServletRequest req) {
		if (actId == 0) {
			return new ReMsg(ReCode.OK, 1);
		}
		Room r = roomDefaultService.getRoom(roomId);
		return roomHandlerDispatcher.getService(r).canInRoom(type, actId, roomId);
	}

	public ReMsg searchRoom(long roomId, HttpServletRequest req) {
		if (roomId < 1) {
			return new ReMsg(ReCode.FAIL);
		}
		DBObject r = roomDefaultService.findById(roomId);
		// Room r = roomDefaultService.getRoom(roomId);
		if (r == null) {
			return new ReMsg(ReCode.FAIL);
		}
		Boolean sys = DboUtil.getBool(r, "sys");
		Integer sellStatus = DboUtil.getInt(r, "sellStatus");
		if (null != sys && !sys) {// 包间
			if (null != sellStatus && sellStatus != Room.SELL_STATUS_SOLD) {// 不是已售
				return new ReMsg(ReCode.FAIL);
			}
			DBObject user = userService.findById(DboUtil.getLong(r, "buyUid"));
			r.put("masterName", DboUtil.getString(user, "nickname"));
			r.put("masterAvatar", DboUtil.getString(user, "avatar"));
			Boolean pub = DboUtil.getBool(r, "pub");
			// 公开的 系统可以匹配进入 或者是密码字段为空或空串
			if (null == pub || (null != pub && pub) || StringUtils.isBlank(DboUtil.getString(r, "pwd"))) {
				r.put("pwd", false);
			} else {
				r.put("pwd", true);
			}
		} else {
			r.put("pwd", false);
		}
		return new ReMsg(ReCode.OK, r);
	}

	public ReMsg kick(long uid, long roomId) {
		Room r = roomDefaultService.getRoom(roomId);
		return roomHandlerDispatcher.getService(r).kick(uid, r);
	}

	/**
	 * 用户离开或者退出房间
	 */
	public ReMsg outRoom(long uid, long roomId) {
		log.error("主动退出了UserActivityService");
		Room r = roomDefaultService.getRoom(roomId);
		return roomHandlerDispatcher.getService(r).outRoom(uid, r);
	}

	public void disconnectRoom(long uid, long roomId) {
		Room r = roomDefaultService.getRoom(roomId);
		roomHandlerDispatcher.getService(r).disconnectRoom(uid, r);
	}

	// public ReMsg inRoom(final long uid, final long roomId) {
	// Room r = roomDefaultService.getRoom(roomId);
	// return roomHandlerDispatcher.getService(r).inRoom(uid, r);
	// }

	public ReMsg inRoom(final long uid, final long roomId, int model) {
		Room r = roomDefaultService.getRoom(roomId);
		if (null != r && !r.isSys()) {// 包间
			userInRoomLogService.saveUserInRoomLog(uid, roomId);
			if (r.getCount() == 0 && !r.isPub()) {// 房间内无人 且非公开
				r.setUid(uid);
				r.setUpdateTime(System.currentTimeMillis());
				super.getMongoTemplate().save(r);
			}
		}
		return roomHandlerDispatcher.getService(r).inRoom(uid, r, model, false);
	}

	public void chat(long uid, long roomId, String fmt, String txt) {
		roomHandlerDispatcher.getService(roomId).chat(uid, roomId, fmt, txt);
	}

	public void getActorStatus(long uid, long roomId) {
		Room r = roomDefaultService.getRoom(roomId);
		roomHandlerDispatcher.getService(r).getActorStatus(uid, r);
	}

	/** 发言 */
	public void speaking(long uid, long roomId) {
		roomHandlerDispatcher.getService(roomId).speaking(uid, roomId);
	}

	/** 暂停发言 */
	public void pauseSpeaking(long uid, long roomId) {
		roomHandlerDispatcher.getService(roomId).pauseSpeaking(uid, roomId);
	}

	/** 取消发言 */
	public void cancelSpeak(long uid, long roomId) {
		roomHandlerDispatcher.getService(roomId).cancelSpeak(uid, roomId);
	}

	private int[][] base = { { 100, 300 }, { 80, 200 }, { 50, 100 }, { 30, 100 }, { 20, 100 }, { 10, 100 }, { 20, 100 },
			{ 20, 100 }, { 30, 100 }, { 50, 100 }, { 70, 100 }, { 100, 100 }, { 110, 200 }, { 110, 200 }, { 90, 100 },
			{ 90, 100 }, { 110, 200 }, { 130, 300 }, { 90, 100 }, { 110, 300 }, { 140, 400 }, { 170, 500 },
			{ 190, 500 }, { 140, 400 } };

	// 老版本，不用
	public ReMsg getActivityList(HttpServletRequest req) {
		List<OnlineActivity> as = new ArrayList<OnlineActivity>();

		int n = RandomUtil.nextInt(3) + 8;
		OnlineActivity draw = new OnlineActivity(ActivityType.DRAW_SOMETHING.getType(), getCount(n), "你画我猜",
				"http://imgzb.oss-cn-beijing.aliyuncs.com/game/131.png", Const.STATUS_UP, 1, 0.0);
		n = RandomUtil.nextInt(3) + 6;
		OnlineActivity undercover = new OnlineActivity(ActivityType.UNDERCOVER.getType(), getCount(n), "谁是卧底",
				"http://imgzb.oss-cn-beijing.aliyuncs.com/game/130.png", Const.STATUS_UP, 1, 0.0);
		n = RandomUtil.nextInt(3) + 6;
		OnlineActivity dice = new OnlineActivity(ActivityType.DICE.getType(), getCount(n), "吹牛",
				"http://imgzb.oss-cn-beijing.aliyuncs.com/game/131.png", Const.STATUS_UP, 1, 0.0);
		n = RandomUtil.nextInt(3) + 6;
		OnlineActivity slot = new OnlineActivity(ActivityType.SLOT_MACHINES.getType(), getCount(n), "非诚勿扰",
				"http://imgzb.oss-cn-beijing.aliyuncs.com/game/130.png", Const.STATUS_UP, 1, 0.0);
		as.add(draw);
		as.add(undercover);
		as.add(dice);
		as.add(slot);
		Page<OnlineActivity> p = new Page<OnlineActivity>(2, 100, 0, as);
		return new ReMsg(ReCode.OK, p);
	}

	public Page<DBObject> queryActivity(Integer status, Integer page, Integer size, HttpServletRequest req) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = findActivityList(status, null, page, size, req, true);
		int count = findActivityCount(status, req);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public List<DBObject> findOnlineActivity(HttpServletRequest req) {
		List<DBObject> dbos = findActivityList(Const.STATUS_UP, super.getVer(req), 0, 20, req);
		int count = dbos.size() + 6;
		int i = 0;
		// List<DBObject> as = new ArrayList<DBObject>();
		for (DBObject dbo : dbos) {
			dbo.put("count", getCount(count - RandomUtil.nextInt(3) - i));
			i++;
		}
		return dbos;
	}

	Map<String, List<DBObject>> alMap = new HashMap<String, List<DBObject>>();

	public List<DBObject> findActivityList(Integer status, Double ver, Integer page, Integer size,
			HttpServletRequest req, boolean adm) {
		size = getSize(size);
		page = getPage(page);
		String key = page + "-" + size;

		DBObject q = new BasicDBObject();
		if (!adm) {
			q.put("parentId", 0);
		}
		if (status != null && status > 0) {
			q.put("status", status);
			key = key + "-" + status;
		}
		if (ver != null && ver > 0) {
			q.put("ver", new BasicDBObject("$lte", ver));
			key = key + "-" + ver;
		}
		List<DBObject> rl = alMap.get(key);
		if (rl == null) {
			rl = super.getC(DocName.ONlINE_ACTIVITY).find(q).sort(new BasicDBObject("sort", -1))
					.skip(super.getStart(page, size)).limit(size).toArray();
			if (rl.size() > 0) {
				alMap.put(key, rl);
			}
		}
		return rl;
	}

	public List<DBObject> findActivityList(Integer status, Double ver, Integer page, Integer size,
			HttpServletRequest req) {
		return findActivityList(status, ver, page, size, req, false);
	}

	public List<DBObject> findActivityChildrenList(Integer status, int parentId, HttpServletRequest req) {
		DBObject q = new BasicDBObject("parentId", parentId);
		if (status != null && status > 0) {
			q.put("status", status);
		}
		List<DBObject> list = getC(DocName.ONlINE_ACTIVITY).find(q).sort(new BasicDBObject("sort", -1)).toArray();
		long uid = super.getUid();
		if (uid > 0) {
			// 狼人杀胜场判断
			int winCount = 0;
			boolean canIn9 = false;
			boolean canIn12 = false;
			List<DBObject> temp = getC(DocName.WEREWOLF_KILL_LOG).find(new BasicDBObject("uid", uid)).toArray();
			for (DBObject dbo : temp) {
				WerewolfKillLog wl = DboUtil.toBean(dbo, WerewolfKillLog.class);
				winCount += wl.getAllWinCount();
			}
			if (winCount >= 10) {
				canIn9 = true;
			}
			if (winCount >= 20) {
				canIn12 = true;
			}
			Iterator<DBObject> it = list.iterator();
			while (it.hasNext()) {
				DBObject dbo = it.next();
				long dboId = DboUtil.getLong(dbo, "_id");
				if (dboId == ActivityType.WEREWOLF9.getType() && !canIn9) {
					dbo.put("canIn", false);
					dbo.put("pic", DboUtil.getString(dbo, "lockPic"));
				} else if (dboId == ActivityType.WEREWOLF.getType() && !canIn12) {
					dbo.put("canIn", false);
					dbo.put("pic", DboUtil.getString(dbo, "lockPic"));
				} else if (dboId == ActivityType.SCHOOL_WAR.getType() || dboId == ActivityType.ANIMAL_CHESS.getType()
						|| dboId == ActivityType.GOMOKU.getType() || dboId == ActivityType.WINMINE.getType()
						|| dboId == ActivityType.NEURO_CAT.getType()) {
					Double ver = super.getVer(req);
					if (null != ver && ver < DboUtil.getDouble(dbo, "ver")) {
						it.remove();
					}
				} else {
					dbo.put("canIn", true);
				}
			}
		}
		return list;
	}

	public int findActivityCount(Integer status, HttpServletRequest req) {
		DBObject q = new BasicDBObject();
		if (status != null && status > 0) {
			q.put("status", status);
		}
		return super.getC(DocName.ONlINE_ACTIVITY).find(q).count();
	}

	public ReMsg save(Long id, String name, String pic, int status, int sort, int point, int vip, double ver,
			Integer type, Integer handleType, Integer parentId, String url, String lockPic) {
		OnlineActivity draw = new OnlineActivity(id, 0, name, pic, status, sort, point, vip, ver, type, handleType,
				parentId, url, lockPic);
		super.getMongoTemplate().save(draw);
		return new ReMsg(ReCode.OK);
	}

	Map<Long, DBObject> oaMap = new HashMap<Long, DBObject>();

	public DBObject getOnlineActivity(final Long id) {
		DBObject dbo = oaMap.get(id);
		if (dbo == null) {
			dbo = super.getC(DocName.ONlINE_ACTIVITY).findOne(new BasicDBObject("_id", id));
			if (dbo != null) {
				oaMap.put(id, dbo);
			}
		}
		return dbo;
	}

	private int getCount(int n) {
		int h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int[] hbase = base[h];
		return (RandomUtil.nextInt(20) + hbase[0]) * n + RandomUtil.nextInt(hbase[1]);
	}
	// //获取游戏准进条件
	// public ReMsg getCondition(long activityType){
	// DBObject game = getOnlineActivity(activityType);
	// if(null==game){
	// return new ReMsg(ReCode.FAIL);
	// }
	// game = removeFile(game, new String[] { "_class", "name", "count", "pic",
	// "status", "sort","ver" });
	// return new ReMsg(ReCode.OK,game);
	// }

	/** 查询赞数 */
	public ReMsg queryPraise(int type, long uid) {
		BasicDBObject q = new BasicDBObject("uid", uid).append("type", type);
		int count = super.getC(DocName.ACTIVITYPRAISELOG).find(q).count();
		return new ReMsg(ReCode.OK, count);
	}

	/** 点赞 */
	public ReMsg praiseSomebody(long roomId, long uid, long pid, long actId) {
		if (uid == pid) {
			return new ReMsg(ReCode.OK);
		}
		BasicDBObject q = new BasicDBObject("_id", pid + "_" + actId + "_" + uid);
		if (super.getC(DocName.ACTIVITYPRAISELOG).find(q).count() > 0) {
			return new ReMsg(ReCode.PRAISE_YET);
		}
		Room r = roomDefaultService.getRoom(roomId);
		ActivityPraiseLog pl = new ActivityPraiseLog(uid, pid, roomId, actId, r.getType());
		super.getMongoTemplate().save(pl);
		MapBody mb = new MapBody(SocketHandler.PRAISE, SocketHandler.FN_ROOM_ID, roomId)
				.append(SocketHandler.FN_USER_ID, uid);
		roomDefaultService.pubRoomUserMsg(roomId, pid, MsgType.ROOM, mb, System.currentTimeMillis());
		return new ReMsg(ReCode.OK);
	}

	/** 游戏中举报 */
	public ReMsg reportSomebody(long roomId, long uid, long rid, long actId, int rType, int playerCount) {
		Room r = roomDefaultService.getRoom(roomId);
		BasicDBObject q = new BasicDBObject("_id", rid + "_" + actId + "_" + uid);
		if (super.getC(DocName.ACTIVITYREPORTLOG).find(q).count() > 0) {
			return new ReMsg(ReCode.REPORT_YET);
		}
		int date = Integer.parseInt((DateUtil.dateFormatyyyyMMdd(new Date())));
		ActivityReportLog rl = new ActivityReportLog(uid, rid, roomId, actId, r.getType());
		super.getMongoTemplate().save(rl);
		BasicDBObject q1 = new BasicDBObject("_id",
				uid + "_" + actId + "_" + r.getType() + "_" + ActivityReportSt.TYPE_ACT + "_" + rType);
		BasicDBObject q2 = new BasicDBObject("_id",
				uid + "_" + date + "_" + r.getType() + "_" + ActivityReportSt.TYPE_DAY + "_" + rType);
		BasicDBObject q3 = new BasicDBObject("_id",
				uid + "_" + r.getType() + "_" + ActivityReportSt.TYPE_ALL + "_" + rType);
		if (super.getC(DocName.ACTIVITYREPORTST).find(q3).count() == 0) {
			super.getMongoTemplate().save(new ActivityReportSt(uid, rType, r.getType()));
			super.getMongoTemplate().save(new ActivityReportSt(uid, rType, r.getType(), date));
			super.getMongoTemplate().save(new ActivityReportSt(uid, rType, r.getType(), actId));
		} else {
			super.getC(DocName.ACTIVITYREPORTST).findAndModify(q3, null, null, false,
					new BasicDBObject("$inc", new BasicDBObject("count", 1)), false, false);
			if (super.getC(DocName.ACTIVITYREPORTST).find(q2).count() == 0) {
				super.getMongoTemplate().save(new ActivityReportSt(uid, rType, r.getType(), date));
				super.getMongoTemplate().save(new ActivityReportSt(uid, rType, r.getType(), actId));
			} else {
				super.getC(DocName.ACTIVITYREPORTST).findAndModify(q2, null, null, false,
						new BasicDBObject("$inc", new BasicDBObject("count", 1)), true, false);
				if (super.getC(DocName.ACTIVITYREPORTST).find(q1).count() == 0) {
					super.getMongoTemplate().save(new ActivityReportSt(uid, rType, r.getType(), actId));
				} else {
					DBObject dbo1 = super.getC(DocName.ACTIVITYREPORTST).findAndModify(q1, null, null, false,
							new BasicDBObject("$inc", new BasicDBObject("count", 1)), true, false);
					if (DboUtil.getInt(dbo1, "count") > playerCount / 2) {
						forbiddenGame(rid, rType);
					} else if (DboUtil.getInt(dbo1, "count") == 2) {
						promptUser(rid);
					}
				}
				int yesterday = Integer
						.parseInt((DateUtil.dateFormatyyyyMMdd(new Date(System.currentTimeMillis() - Const.DAY))));
				Set<Integer> set = new HashSet<>();
				set.add(date);
				set.add(yesterday);
				BasicDBObject q4 = new BasicDBObject("uid", uid).append("gType", r.getType())
						.append("type", ActivityReportSt.TYPE_DAY).append("date", new BasicDBObject("$in", set));
				int count48 = 0;
				List<DBObject> list = super.getC(DocName.ACTIVITYREPORTST).find(q4).toArray();
				for (DBObject dbo : list) {
					int c = DboUtil.getInt(dbo, "count");
					count48 += c;
				}
				if (count48 % 10 == 0 && count48 > 9) {
					forbiddenGame(rid, FORBIDDENEMAP.get(count48));
				}
			}
		}
		return new ReMsg(ReCode.OK);
	}

	/** 发送系统消息 */
	private void promptUser(long uid) {
		MapBody mb = new MapBody("op", OperationType.NULL.getOp()).append("content",
				"* 系统消息：亲爱的玩家，经核实您近期被多次举报，请注意调整自己的心态，认真对待每局游戏，否则将会面临被禁止参与狼人杀游戏的处罚。");
		Msg msg = new Msg(super.incrMsgId(), MsgType.NOTICE, 0, uid, System.currentTimeMillis(), mb.getData());
		messageService.msgHandler(msg);
	}

	private void forbiddenGame(long rid, long time) {
		long now = System.currentTimeMillis();
		BasicDBObject q = new BasicDBObject("_id", rid);
		DBObject dbo = super.getC(DocName.ACTIVITYREPORTST).findOne(q);
		long endForbiddenTime = now + time;
		if (null != dbo) {
			endForbiddenTime = DboUtil.getLong(dbo, "endForbiddenTime");
			if (endForbiddenTime > now) {
				endForbiddenTime += time;
				super.getC(DocName.ACTIVITYFORBIDDENLOG).update(q, new BasicDBObject("$set",
						new BasicDBObject("endForbiddenTime", endForbiddenTime).append("updateTime", now)));
			} else {
				endForbiddenTime = now + time;
				super.getC(DocName.ACTIVITYFORBIDDENLOG).update(q,
						new BasicDBObject("$set", new BasicDBObject("beginForbiddenTime", now).append("updateTime", now)
								.append("endForbiddenTime", endForbiddenTime)));
			}
		} else {
			super.getMongoTemplate().save(new ActivityForbiddenLog(rid, now, endForbiddenTime));
		}
		MapBody mb = new MapBody("op", OperationType.NULL.getOp()).append("content",
				"* 系统消息：“由于您近期被多次举报，已严重影响了游戏体验，系统将禁止您参与狼人杀游戏至" + DateUtil.dateFormatLongCN(new Date(endForbiddenTime))
						+ "，请积极维护健康娱乐环境。。");
		Msg msg = new Msg(super.incrMsgId(), MsgType.NOTICE, 0, rid, System.currentTimeMillis(), mb.getData());
		messageService.msgHandler(msg);

	}

	private void forbiddenGame(long rid, int rType) {
		forbiddenGame(rid, FORBIDDENEMAP.get(rType));
	}

	public static final Map<Integer, String> REPORTTYPEMAP = new HashMap<>();
	public static final Map<Integer, Long> FORBIDDENEMAP = new HashMap<>();
	static {
		for (ReportMap e : EnumSet.allOf(ReportMap.class)) {
			REPORTTYPEMAP.put(e.getI(), e.getDes());
			FORBIDDENEMAP.put(e.getI(), e.getForbiddenTime());
		}
		for (ForbiddenMap e : EnumSet.allOf(ForbiddenMap.class)) {
			FORBIDDENEMAP.put(e.getI(), e.getForbiddenTime());
		}
	}

	/** 获取举报类型 */
	public ReMsg queryReportType() {
		return new ReMsg(ReCode.OK, REPORTTYPEMAP);
	}

	private enum ForbiddenMap {
		FOR1(10, 10 * Const.MINUTE), FOR2(20, 1 * Const.HOUR), FOR3(30, 24 * Const.HOUR), FOR4(40, 72 * Const.HOUR),;
		ForbiddenMap(int i, long forbiddenTime) {
			this.i = i;
			this.forbiddenTime = forbiddenTime;
		}

		private int i;
		private long forbiddenTime;

		public int getI() {
			return i;
		}

		public long getForbiddenTime() {
			return forbiddenTime;
		}

	}

	private enum ReportMap {
		REP1(1, "辱骂他人", 1 * Const.HOUR), REP2(2, "恶意挂机", 30 * Const.MINUTE), REP3(3, "开黑作弊", 30 * Const.MINUTE), REP4(4,
				"态度消极", 30 * Const.MINUTE), REP5(5, "色情、广告", 1 * Const.HOUR),;

		ReportMap(int i, String des, long forbiddenTime) {
			this.i = i;
			this.des = des;
			this.forbiddenTime = forbiddenTime;
		}

		private int i;
		private String des;
		private long forbiddenTime;

		public int getI() {
			return i;
		}

		public String getDes() {
			return des;
		}

		public long getForbiddenTime() {
			return forbiddenTime;
		}
	}

	private Map<String, DBObject> zipMap = new HashMap<>();

	public ReMsg getGameZip(int gameType, int via, double ver, String appCode) {
		String key = gameType + "_" + via + "_" + ver;
		DBObject value = zipMap.get(key);
		if (null == value) {
			BasicDBObject q = new BasicDBObject();
			List<Integer> list = new ArrayList<>();
			list.add(via);
			list.add(0);
			q.append("gameType", gameType).append("via", new BasicDBObject("$in", list)).append("ver", ver)
					.append("appCode", appCode);
			DBObject dbo = super.getC(DocName.GAMERESOURCE).findOne(q);
			if (null == dbo) {
				q.append("ver", new BasicDBObject("$lt", ver));
				List<DBObject> dbos = super.getC(DocName.GAMERESOURCE).find(q).sort(new BasicDBObject("ver", -1))
						.toArray();
				if (!dbos.isEmpty()) {
					dbo = dbos.get(0);
				}
			}
			if (null != dbo) {
				zipMap.put(key, dbo);
				value = dbo;
			}
		}
		return new ReMsg(ReCode.OK, value);
	}

	public ReMsg saveGameZip(Long id, int gameType, int via, double ver, String url, String appCode) {
		id = null == id ? super.getNextId(DocName.GAMERESOURCE) : id;
		GameResource gr = new GameResource(id, via, ver, gameType, url, appCode);
		super.getMongoTemplate().save(gr);
		return new ReMsg(ReCode.OK);
	}

	public Page<DBObject> queryGameZip(Integer gameType, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		BasicDBObject q = new BasicDBObject();
		if (null != gameType) {
			q.append("gameType", gameType);
		}
		List<DBObject> dbos = super.getC(DocName.GAMERESOURCE).find(q).skip(super.getStart(page, size))
				.limit(getSize(size)).sort(new BasicDBObject("ver", -1)).toArray();
		int count = getC(DocName.GAMERESOURCE).find(q).count();
		return new Page<DBObject>(count, size, page, dbos);
	}

	public DBObject queryGameZipById(Long id) {
		BasicDBObject q = new BasicDBObject("_id", id);
		return super.getC(DocName.GAMERESOURCE).findOne(q);
	}
}
