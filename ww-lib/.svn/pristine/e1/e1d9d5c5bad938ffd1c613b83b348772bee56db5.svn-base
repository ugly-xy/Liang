package com.zb.service.room;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.ActivityType;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.Point;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.redis.RK;
import com.zb.common.utils.DateUtil;
import com.zb.common.utils.JSONUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.WerewolfKillLog;
import com.zb.models.WerewolfKillLogDetail;
import com.zb.models.finance.CoinLog;
import com.zb.models.goods.GoodsItem;
import com.zb.models.room.Activity;
import com.zb.models.room.Actor;
import com.zb.models.room.Room;
import com.zb.models.room.activity.WerewolfKill;
import com.zb.models.room.activity.WerewolfKillActor;
import com.zb.models.room.activity.WerewolfKillActorState;
import com.zb.service.RelationshipService;
import com.zb.service.UserService;
import com.zb.service.room.vo.IdentitySetupVO;
import com.zb.service.room.vo.WerewolfRoomInfo;
import com.zb.service.room.vo.WolfRankVO;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.MsgType;
import com.zb.socket.model.SocketHandler;

@Service
public class WerewolfKillService extends RoomService {

	@Autowired
	UserService userService;

	@Autowired
	RelationshipService relationshipService;

	@Override
	public ReMsg handle(Room room, long fr) {
		try {
			WerewolfKill wk = super.getActivity(room.get_id(), WerewolfKill.class);
			log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + wk.getStatus());
			if (WerewolfKill.STATUS_READY == wk.getStatus()) {
				log.error("<><><><>换房主begin" + room.get_id());
				changeOwner(room, wk);
				log.error("<><><><>换房主end" + room.get_id());
			} else if (WerewolfKillActor.STATUS_BUY == wk.getStatus()) {
				log.error("<><><><>分配身份begin" + room.get_id());
				sendRolesBegin(room, wk);
				log.error("<><><><>分配身份end" + room.get_id());
			} else if (WerewolfKillActor.STATUS_SEND_ROLE == wk.getStatus()) {
				if (null != wk.getIdentities().get(WerewolfKillActor.DEFENDER)) {
					log.error("<><><><>守卫阶段begin" + room.get_id());
					defendBegin(room, wk);
					log.error("<><><><>守卫阶段end" + room.get_id());
				} else {
					log.error("<><><><>杀人查人阶段begin" + room.get_id());
					killCheckBegin(room, wk);
					log.error("<><><><>杀人查人阶段end" + room.get_id());
				}
			} else if (WerewolfKillActor.STATUS_DEFEND == wk.getStatus()) {
				log.error("<><><><>杀人查人阶段begin" + room.get_id());
				killCheckBegin(room, wk);
				log.error("<><><><>杀人查人阶段end" + room.get_id());
			} else if (WerewolfKillActor.STATUS_KILL_CHECK == wk.getStatus()) {
				log.error("<><><><>计算死人阶段begin" + room.get_id());
				deadCalculation(room, wk);
				log.error("<><><><>计算死人阶段end" + room.get_id());
			} else if (WerewolfKillActor.STATUS_CALCULATION == wk.getStatus()) {
				if (null != wk.getIdentities().get(WerewolfKillActor.WITCH)) {
					log.error("<><><><>救人毒人begin" + room.get_id());
					savePoisonBegin(room, wk);
					log.error("<><><><>救人毒人end" + room.get_id());
				} else {
					log.error("<><><><>天亮了begin" + wk.getDay());
					sunRise(room, wk);
					log.error("<><><><>天亮了end" + room.get_id());
				}
			} else if (WerewolfKillActor.STATUS_SAVE_POISON == wk.getStatus()) {
				log.error("<><><><>天亮了begin" + room.get_id());
				sunRise(room, wk);
				log.error("<><><><>天亮了end" + room.get_id());
			} else if (WerewolfKillActor.STATUS_DAY == wk.getStatus()) {
				log.error("<><><><>公布夜晚死人begin" + room.get_id());
				publishDead(room, wk);
				log.error("<><><><>公布夜晚死人end" + room.get_id());
			} else if (WerewolfKillActor.STATUS_SPEAK == wk.getStatus()) {
				log.error("<><><><>开始发言begin" + room.get_id());
				speakBegin(room, wk);
				log.error("<><><><>开始发言end" + room.get_id());
			} else if (WerewolfKillActor.STATUS_VOTE == wk.getStatus()) {
				log.error("<><><><>投票结果展示begin" + room.get_id());
				showVoteBegin(room, wk);
				log.error("<><><><>投票结果展示end" + room.get_id());
			} else if (WerewolfKillActor.STATUS_SHOW_VOTE == wk.getStatus()) {
				log.error("<><><><>投票死亡遗言begin" + room.get_id());
				lastWordsBegin(room, wk);
				log.error("<><><><>投票死亡遗言end" + room.get_id());
			} else if (WerewolfKillActor.STATUS_LAST_WORDS == wk.getStatus()) {
				if (null != wk.getIdentities().get(WerewolfKillActor.DEFENDER)) {
					log.error("<><><><>守卫阶段begin" + room.get_id());
					defendBegin(room, wk);
					log.error("<><><><>守卫阶段end" + room.get_id());
				} else {
					log.error("<><><><>杀人查人阶段begin" + room.get_id());
					killCheckBegin(room, wk);
					log.error("<><><><>杀人查人阶段end" + room.get_id());
				}
			} else if (WerewolfKillActor.STATUS_REVENGE == wk.getStatus()) {
				if (wk.getHunterDeadType() == WerewolfKillActor.WOLF_KILL) {
					log.error("<><><><>猎人发言begin" + room.get_id());
					speakBegin(room, wk);
					log.error("<><><><>猎人发言end" + room.get_id());
				} else if (wk.getHunterDeadType() == WerewolfKillActor.VOTE_KILL) {
					if (null != wk.getIdentities().get(WerewolfKillActor.DEFENDER)) {
						log.error("<><><><>守卫阶段begin" + room.get_id());
						defendBegin(room, wk);
						log.error("<><><><>守卫阶段end" + room.get_id());
					} else {
						log.error("<><><><>杀人查人阶段begin" + room.get_id());
						killCheckBegin(room, wk);
						log.error("<><><><>杀人查人阶段end" + room.get_id());
					}
				}
			} else if (WerewolfKillActor.STATUS_END == wk.getStatus()) {
				log.error("<><><><>结算阶段begin" + room.get_id());
				showEndBegin(room, wk);
				log.error("<><><><>结算阶段begin" + room.get_id());
			} else if (WerewolfKillActor.STATUS_SHOW_END == wk.getStatus()) {
				log.error("<><><><>重新开始阶段begin" + room.get_id());
				restart(room, wk);
				log.error("<><><><>重新开始阶段end" + room.get_id());
			}
		} catch (Exception e) {
			log.error("调用任务roomId=" + room.get_id() + ",", e);
		}
		return new ReMsg(ReCode.OK);
	}

	void checkReadyRoom(long roomId, WerewolfKill wk, boolean in) {
		if (WerewolfKill.STATUS_READY == wk.getStatus()) {
			Room r = super.getRoom(roomId);
			int readyCount = wk.getActors().size();
			int timeStart = 20;
			int playerCount = WolfSetup.map.get(r.getType());
			if (!in) {
				super.delJob(roomId);
			}
			if (in && readyCount + 1 >= playerCount) {
				super.saveActivity(r.get_id(), wk);
				super.addJob(roomId, timeStart);
			}
			checkReadyRoom(r, readyCount, timeStart);
		}
	}

	@Override
	protected void checkReadyRoom(Room r, int readyCount, int timeStart) {
		if (r.getType() == ActivityType.WEREWOLF.getType()) {
			if ((readyCount == 0 && System.currentTimeMillis() - r.getUpdateTime() > Const.HOUR)
					|| System.currentTimeMillis() - r.getUpdateTime() > 2 * Const.HOUR) {
				this.closeRoom(r.get_id());
				return;
			}
		} else if (r.getType() == ActivityType.WEREWOLF9.getType()) {
			if ((readyCount == 0 && System.currentTimeMillis() - r.getUpdateTime() > 45 * Const.MINUTE)
					|| System.currentTimeMillis() - r.getUpdateTime() > Const.HOUR) {
				this.closeRoom(r.get_id());
				return;
			}
		} else {
			if ((readyCount == 0 && System.currentTimeMillis() - r.getUpdateTime() > 30 * Const.MINUTE)
					|| System.currentTimeMillis() - r.getUpdateTime() > 45 * Const.MINUTE) {
				this.closeRoom(r.get_id());
				return;
			}
		}
	}

	/** 游戏类型 */
	private MsgType getMsgType(WerewolfKill wk) {
		if (wk.getType() == WolfSetup.SIX.getType()) {
			return MsgType.WEREWOLF6;
		} else if (wk.getType() == WolfSetup.NINE.getType()) {
			return MsgType.WEREWOLF9;
		} else if (wk.getType() == WolfSetup.TWELVE.getType()) {
			return MsgType.WEREWOLF;
		}
		return MsgType.DEFAULT;
	}

	/** 玩家准备 */
	public ReMsg ready(long uid, long roomId) {
		log.error("::::::::::::::begin ready=" + uid);
		WerewolfKill wk = super.getActivity(roomId, WerewolfKill.class);
		WerewolfKillActor wa = super.getRoomActor(roomId, uid, WerewolfKillActor.class);
		if (null != wk && null != wa && WerewolfKill.STATUS_READY == wk.getStatus()
				&& Actor.A_STATUS_IN_ROOM == wa.getUcStatus() && wa.getRole() == Actor.ROLE_EXECUTOR) {
			checkReadyRoom(roomId, wk, true);
			wa.setUcStatus(Actor.A_STATUS_READY);
			wk.putActor(uid, WerewolfKillActor.A_STATUS_START);
			super.saveRoomActor(roomId, wa);
			super.saveActivity(roomId, wk);
			MapBody mb = new MapBody(SocketHandler.ACTIVITY_READY, SocketHandler.FN_USER_ID, uid)
					.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", wk.get_id());
			super.pubRoomMsg(roomId, getMsgType(wk), mb);
			log.error("::::::::::::::end ready=" + uid);
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 玩家取消准备 */
	public ReMsg cancelReady(long uid, long roomId) {
		log.error("::::::::::::::begin cancelready=" + uid);
		WerewolfKill wk = super.getActivity(roomId, WerewolfKill.class);
		WerewolfKillActor wa = super.getRoomActor(roomId, uid, WerewolfKillActor.class);
		if (null != wk && null != wa && WerewolfKill.STATUS_READY == wk.getStatus()
				&& Actor.A_STATUS_READY == wa.getUcStatus() && !wa.isOwner()) {
			if (!lock(RK.readyRoomLock(roomId), 1)) {
				log.error(":::::::::::::::::没退出去");
				return new ReMsg(ReCode.START_YET);
			} else {
				try {
					checkReadyRoom(roomId, wk, false);
					wa.setUcStatus(Actor.A_STATUS_IN_ROOM);
					wk.removeActor(uid);
					super.saveRoomActor(roomId, wa);
					super.saveActivity(roomId, wk);
					MapBody mb = new MapBody(SocketHandler.ACTIVITY_UNREADY, SocketHandler.FN_USER_ID, uid)
							.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", wk.get_id());
					super.pubRoomMsg(roomId, getMsgType(wk), mb);
					log.error("::::::::::::::end cancelready=" + uid);
				} finally {
					unlock(RK.readyRoomLock(roomId));
				}
			}
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 上树 */
	public ReMsg climbTheTree(long uid, long roomId) {
		log.error("::::::::::::::begin climbTheTree=" + uid);
		WerewolfKill wk = super.getActivity(roomId, WerewolfKill.class);
		WerewolfKillActor wa = super.getRoomActor(roomId, uid, WerewolfKillActor.class);
		if (null == wa) {
			return new ReMsg(ReCode.USER_NOT_EXISTS);
		} else if (WerewolfKill.STATUS_READY != wk.getStatus()) {
			return new ReMsg(ReCode.FAIL);
		} else if (Actor.A_STATUS_READY == wa.getUcStatus()) {
			return new ReMsg(ReCode.CANNOT_CLIMB_TREE);
		} else if (wa.getRole() != Actor.ROLE_EXECUTOR) {
			return new ReMsg(ReCode.ON_TREE_YET);
		}
		wa.setRole(Actor.ROLE_VIEWER);
		int index = -1;
		wa.setSeat(index);
		long[] seatUserTable = wk.getSeatUserTable();
		for (int i = 0; i < seatUserTable.length; i++) {
			if (seatUserTable[i] == uid) {
				index = i;
				seatUserTable[i] = 0;
				break;
			}
		}
		wk.setSeatUserTable(seatUserTable);
		if (index != -1) {
			super.getRedisTemplate().opsForList().leftPush(RK.seatList(roomId), Integer.toString(index));
		}
		super.saveActivity(roomId, wk);
		super.saveRoomActor(roomId, wa);
		MapBody mb = new MapBody(SocketHandler.CLIMBTREE, SocketHandler.FN_USER_ID, uid)
				.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", wk.get_id()).append("emptyNo", index);
		super.pubRoomMsg(roomId, getMsgType(wk), mb);
		log.error("::::::::::::::end climbTheTree=" + uid);
		return new ReMsg(ReCode.OK);

	}

	/** 下树 */
	public ReMsg getDownTheTree(long uid, long roomId, int seatNo) {
		log.error("::::::::::::::begin getDownTheTree=" + uid);
		if (lock(RK.seatLock(roomId), 1L)) {
			WerewolfKill wk = super.getActivity(roomId, WerewolfKill.class);
			WerewolfKillActor wa = super.getRoomActor(roomId, uid, WerewolfKillActor.class);
			if (null == wa) {
				return new ReMsg(ReCode.USER_NOT_EXISTS);
			}
			int playerCount = WolfSetup.map.get(wk.getType());
			if (seatNo < 0 || seatNo > playerCount - 1) {
				return new ReMsg(ReCode.SEATNO_ERROR);
			}
			if (wa.getRole() == Actor.ROLE_VIEWER) {
				long[] seatTable = wk.getSeatUserTable();
				if (seatTable[seatNo] != 0) {
					unlock(RK.seatLock(roomId));
					return new ReMsg(ReCode.SEAT_BE_USE);
				} else {
					seatTable[seatNo] = uid;
					wk.setSeatUserTable(seatTable);
					wk.putActor(uid, WerewolfKillActor.A_STATUS_START);
					wa.setSeat(seatNo);
					wa.setUcStatus(Actor.A_STATUS_READY);
					wa.setRole(Actor.ROLE_EXECUTOR);
					super.saveRoomActor(roomId, wa);
					super.saveActivity(roomId, wk);
					if (getRedisTemplate().opsForList().remove(RK.seatList(roomId), 0, Integer.toString(seatNo)) < 1) {
						unlock(RK.seatLock(roomId));
						return new ReMsg(ReCode.SEATNO_ERROR);
					}
					unlock(RK.seatLock(roomId));
				}
				wa.setRole(Actor.ROLE_EXECUTOR);
			}
			super.saveRoomActor(roomId, wa);
			MapBody mb = new MapBody(SocketHandler.GETDOWNTREE, SocketHandler.FN_USER_ID, uid)
					.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", wk.get_id()).append("seatNo", seatNo);
			super.pubRoomMsg(roomId, getMsgType(wk), mb);
			log.error("::::::::::::::end getDownTheTree=" + uid);
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.SEAT_BE_USE);
	}

	/** 游戏开始 */
	public ReMsg start(long uid, long roomId) {
		log.error("游戏开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		if (!lock(RK.outRoomLock(roomId), 1)) {
			return new ReMsg(ReCode.SOMEBODY_OUT);
		} else {
			if (!lock(RK.readyRoomLock(roomId), 1)) {
				return new ReMsg(ReCode.SOMEONE_NOT_READY);
			} else {
				WerewolfKill wk = super.getActivity(roomId, WerewolfKill.class);
				if (null == wk.getOwner() || uid != wk.getOwner()) {
					return new ReMsg(ReCode.FAIL);
				}
				int readyCount = wk.getActors().size();
				log.error("::::::::::::::Count()=" + readyCount);
				if (WolfSetup.SIX.getCount() > readyCount) {
					return new ReMsg(ReCode.SOMEONE_NOT_READY);
				}
				super.saveActivity(roomId, wk);
				Room room = super.getRoom(roomId);
				room.setStatus(Room.ACTIVITY);
				room.setActivityDate(System.currentTimeMillis());
				changeRoomCount(room, super.getRoomActors(room.get_id(), WerewolfKillActor.class), T_START);
				MapBody mb = new MapBody(SocketHandler.ACTIVITY_START, SocketHandler.FN_ROOM_ID, roomId)
						.append(SocketHandler.FN_LIMIT, WerewolfKillActor.START_TIME).append("actId", wk.get_id());
				super.pubRoomMsg(roomId, getMsgType(wk), mb);
				buy(room, wk);
				// super.addJob(roomId, WerewolfKillActor.START_TIME);
				log.error("::::::::::::::end start=" + uid);
				unlock(RK.readyRoomLock(roomId));
			}
			unlock(RK.outRoomLock(roomId));
		}
		return new ReMsg(ReCode.OK);
	}

	/** 换房主 */
	public void changeOwner(Room r, WerewolfKill wk) {
		log.error(">>>>>>>>>>>>>>>>>>>>>>开始换房主");
		Long owner = wk.getOwner();
		log.error(">>>>>>>>>>>>>>>>>>>>>>旧房主" + owner);
		Set<WerewolfKillActor> actors = getRoomActors(r.get_id(), WerewolfKillActor.class);
		WerewolfKillActor newOwn = this.getNextOwner(actors, owner);
		pubOwnerChange(owner, newOwn, r, wk);
		super.addJob(r.get_id(), 20);
	}

	/** 买身份阶段 */
	public void buy(Room room, WerewolfKill wk) {
		log.error("::::::::::::::buy begin");
		long roomId = room.get_id();
		int playerCount = wk.getActors().size();
		IdentitySetupVO is = getSetup(playerCount);
		// wk.setIdentitySetup(is);
		wk.setStatus(WerewolfKillActor.STATUS_BUY);
		List<Integer> godList = new ArrayList<>();
		Map<Integer, Integer> identities = new HashMap<>();
		if (is.getProphetCount() > 0) {
			godList.add(WerewolfKillActor.PROPHET);
			identities.put(WerewolfKillActor.PROPHET, is.getProphetCount());
		}
		if (is.getWitchCount() > 0) {
			godList.add(WerewolfKillActor.WITCH);
			identities.put(WerewolfKillActor.WITCH, is.getWitchCount());
		}
		if (is.getHunterCount() > 0) {
			godList.add(WerewolfKillActor.HUNTER);
			identities.put(WerewolfKillActor.HUNTER, is.getHunterCount());
		}
		if (is.getSbCount() > 0) {
			godList.add(WerewolfKillActor.SB);
			identities.put(WerewolfKillActor.SB, is.getSbCount());
		}
		if (is.getDefenderCount() > 0) {
			godList.add(WerewolfKillActor.DEFENDER);
			identities.put(WerewolfKillActor.DEFENDER, is.getDefenderCount());
		}
		identities.put(WerewolfKillActor.WOLF, is.getWolvesCount());
		identities.put(WerewolfKillActor.VILLAGE, is.getVillageCount());
		wk.setIdentities(identities);
		Collections.shuffle(godList);
		Set<Integer> robSet = new HashSet<>();
		robSet.add(WerewolfKillActor.WOLF);
		robSet.add(WerewolfKillActor.VILLAGE);
		robSet.addAll(godList);
		wk.setRobSet(robSet);
		getRedisTemplate().delete(RK.idenList(roomId, WerewolfKillActor.WOLF));
		getRedisTemplate().delete(RK.idenList(roomId, WerewolfKillActor.VILLAGE));
		getRedisTemplate().delete(RK.idenList(roomId, WerewolfKillActor.PROPHET));
		getRedisTemplate().delete(RK.idenList(roomId, WerewolfKillActor.HUNTER));
		getRedisTemplate().delete(RK.idenList(roomId, WerewolfKillActor.WITCH));
		getRedisTemplate().delete(RK.idenList(roomId, WerewolfKillActor.SB));
		getRedisTemplate().delete(RK.idenList(roomId, WerewolfKillActor.DEFENDER));
		for (int i = 0; i < is.getWolvesCount() - 1; i++) {
			getRedisTemplate().opsForList().leftPush(RK.idenList(roomId, WerewolfKillActor.WOLF), "");
		}
		for (int i = 0; i < is.getVillageCount() - 1; i++) {
			getRedisTemplate().opsForList().leftPush(RK.idenList(roomId, WerewolfKillActor.VILLAGE), "");
		}
		for (int i = 0; i < godList.size(); i++) {
			getRedisTemplate().opsForList().leftPush(RK.idenList(roomId, godList.get(i)), "" + godList.get(i));
		}
		super.delRoomAllActorsState(roomId);
		for (Long uid : wk.getActors().keySet()) {
			WerewolfKillActorState actorState = new WerewolfKillActorState();
			actorState.setUid(uid);
			actorState.setDeath(false);
			super.saveRoomActorState(roomId, actorState);
		}
		super.saveActivity(roomId, wk);
		super.addJob(roomId, WerewolfKillActor.BUY_TIME);
		MapBody mb = new MapBody(SocketHandler.BUY_ROLES, SocketHandler.FN_ROOM_ID, roomId).append("actId", wk.get_id())
				.append(SocketHandler.FN_LIMIT, WerewolfKillActor.BUY_TIME)
				.append("buyType", WerewolfKillActor.BUYTYPE);
		Set<WerewolfKillActorState> set = super.getRoomActorsState(roomId, WerewolfKillActorState.class);
		long now = System.currentTimeMillis();
		for (WerewolfKillActorState ws : set) {
			Map<Integer, Integer> robMap = new HashMap<>();
			robMap.put(WerewolfKillActor.WOLF, getPrice(WerewolfKillActor.WOLF));
			robMap.put(WerewolfKillActor.VILLAGE, getPrice(WerewolfKillActor.VILLAGE));
			Collections.shuffle(godList);
			robMap.put(godList.get(0), getPrice(WerewolfKillActor.PROPHET));
			mb.append("robMap", robMap);
			super.pubRoomUserMsg(roomId, ws.getUid(), getMsgType(wk), mb, now);
		}
		log.error("::::::::::::::buy end");
	}

	/** 买身份 */
	public ReMsg buy(long uid, long roomId, int iden) {
		log.error("::::::::::::::buyy开始抢" + uid);
		DBObject dbo = coinService.findById(uid);
		int coin = DboUtil.getInt(dbo, "coin");
		if (getPrice(iden) > coin) {
			log.error("::::::::::::::buyy开始抢jinbibuzu" + uid);
			return new ReMsg(ReCode.COIN_BALANCE_LOW);
		}
		WerewolfKill wk = super.getActivity(roomId, WerewolfKill.class);
		if (WerewolfKillActor.STATUS_BUY == wk.getStatus()) {
			Set<Integer> robSet = wk.getRobSet();
			// IdentitySetupVO is = wk.getIdentitySetup();
			if (null != robSet && !robSet.isEmpty() && robSet.contains(iden)) {
				WerewolfKillActorState ws = super.getRoomActorState(roomId, uid, WerewolfKillActorState.class);
				if (null != ws.getIdentity()) {
					log.error("::::::::::::::buyy已经购买" + uid);
					return new ReMsg(ReCode.BUY_YET);
				} else {
					String hasIden = getRedisTemplate().opsForList().rightPop(RK.idenList(roomId, iden));
					if (hasIden == null) {
						return new ReMsg(ReCode.BUY_FAIL);
					} else {
						coinService.reduce(uid, CoinLog.WEREWOLF, wk.get_id(), getPrice(iden), 0L, "买身份");
						ws.setIdentity(iden);
						super.saveActivity(roomId, wk);
						super.saveRoomActorState(roomId, ws);
					}
				}
				MapBody mb = new MapBody(SocketHandler.BUYING, SocketHandler.FN_USER_ID, uid)
						.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", wk.get_id()).append("iden", iden);
				long curTime = System.currentTimeMillis();
				super.pubRoomUserMsg(roomId, uid, getMsgType(wk), mb, curTime);
				log.error("::::::::::::::buyy抢购成功" + uid);
				return new ReMsg(ReCode.OK);
			}
		}
		log.error("::::::::::::::buyy抢购失败" + uid);
		return new ReMsg(ReCode.BUY_FAIL);
	}

	/** 派发身份开始 */
	public void sendRolesBegin(Room room, WerewolfKill wk) {
		long roomId = room.get_id();
		wk.setRobSet(null);
		List<Long> hasNoIden = new ArrayList<>();
		List<Long> wolves = new ArrayList<>();// 狼
		List<Long> villages = new ArrayList<>();// 村民
		long prophet = 0; // 预言家
		long witch = 0;// 女巫
		long hunter = 0; // 猎人
		long defender = 0; // 守卫
		long sb = 0; // 白痴
		Set<WerewolfKillActorState> wss = super.getRoomActorsState(roomId, WerewolfKillActorState.class);
		log.error(":::::::::::::::::::wss" + wss.size());
		for (WerewolfKillActorState ws : wss) {
			if (ws.getIdentity() == null) {
				hasNoIden.add(ws.getUid());
			} else {
				int iden = ws.getIdentity();
				long uid = ws.getUid();
				if (iden == WerewolfKillActor.WOLF) {
					wolves.add(uid);
				} else if (iden == WerewolfKillActor.VILLAGE) {
					villages.add(uid);
				} else if (iden == WerewolfKillActor.PROPHET) {
					prophet = uid;
				} else if (iden == WerewolfKillActor.WITCH) {
					witch = uid;
				} else if (iden == WerewolfKillActor.HUNTER) {
					hunter = uid;
				} else if (iden == WerewolfKillActor.SB) {
					sb = uid;
				} else if (iden == WerewolfKillActor.DEFENDER) {
					defender = uid;
				}
			}
		}
		log.error(":::::::::::::::::::hasNoIden" + hasNoIden.size());
		Collections.shuffle(hasNoIden);
		// IdentitySetupVO is = wk.getIdentitySetup();
		int index = hasNoIden.size();
		long wolfSize = getRedisTemplate().opsForList().size(RK.idenList(roomId, WerewolfKillActor.WOLF));
		long villageSize = getRedisTemplate().opsForList().size(RK.idenList(roomId, WerewolfKillActor.VILLAGE));
		long prophetSize = getRedisTemplate().opsForList().size(RK.idenList(roomId, WerewolfKillActor.PROPHET));
		long hunterSize = getRedisTemplate().opsForList().size(RK.idenList(roomId, WerewolfKillActor.HUNTER));
		long witchSize = getRedisTemplate().opsForList().size(RK.idenList(roomId, WerewolfKillActor.WITCH));
		long sbSize = getRedisTemplate().opsForList().size(RK.idenList(roomId, WerewolfKillActor.SB));
		long defenderSize = getRedisTemplate().opsForList().size(RK.idenList(roomId, WerewolfKillActor.DEFENDER));
		if (index != 0) {
			for (int i = 0; i < wolfSize + 1 && index > 0; i++) {
				long uid = hasNoIden.get(--index);
				WerewolfKillActorState ws = super.getRoomActorState(roomId, uid, WerewolfKillActorState.class);
				ws.setIdentity(WerewolfKillActor.WOLF);
				super.saveRoomActorState(roomId, ws);
				wolves.add(uid);
			}
			for (int i = 0; i < villageSize + 1 && index > 0; i++) {
				long uid = hasNoIden.get(--index);
				WerewolfKillActorState ws = super.getRoomActorState(roomId, uid, WerewolfKillActorState.class);
				ws.setIdentity(WerewolfKillActor.VILLAGE);
				super.saveRoomActorState(roomId, ws);
				villages.add(uid);
			}
			if (prophetSize > 0 && index > 0) {
				long uid = hasNoIden.get(--index);
				WerewolfKillActorState ws = super.getRoomActorState(roomId, uid, WerewolfKillActorState.class);
				ws.setIdentity(WerewolfKillActor.PROPHET);
				super.saveRoomActorState(roomId, ws);
				prophet = uid;
			}
			if (witchSize > 0 && index > 0) {
				long uid = hasNoIden.get(--index);
				WerewolfKillActorState ws = super.getRoomActorState(roomId, uid, WerewolfKillActorState.class);
				ws.setIdentity(WerewolfKillActor.WITCH);
				super.saveRoomActorState(roomId, ws);
				witch = uid;
			}
			if (hunterSize > 0 && index > 0) {
				long uid = hasNoIden.get(--index);
				WerewolfKillActorState ws = super.getRoomActorState(roomId, uid, WerewolfKillActorState.class);
				ws.setIdentity(WerewolfKillActor.HUNTER);
				super.saveRoomActorState(roomId, ws);
				hunter = uid;
			}
			if (sbSize > 0 && index > 0) {
				long uid = hasNoIden.get(--index);
				WerewolfKillActorState ws = super.getRoomActorState(roomId, uid, WerewolfKillActorState.class);
				ws.setIdentity(WerewolfKillActor.SB);
				super.saveRoomActorState(roomId, ws);
				sb = uid;
			}
			if (defenderSize > 0 && index > 0) {
				long uid = hasNoIden.get(--index);
				WerewolfKillActorState ws = super.getRoomActorState(roomId, uid, WerewolfKillActorState.class);
				ws.setIdentity(WerewolfKillActor.DEFENDER);
				super.saveRoomActorState(roomId, ws);
				defender = uid;
			}
		}
		wk.setWolves(wolves);
		wk.setVillages(villages);
		wk.setProphet(prophet);
		wk.setWitch(witch);
		wk.setHunter(hunter);
		wk.setSb(sb);
		wk.setDefender(defender);
		wk.setStatus(WerewolfKillActor.STATUS_SEND_ROLE);
		wk.setIdentitySetup(null);
		super.saveActivity(roomId, wk);
		getRedisTemplate().delete(RK.idenList(roomId, WerewolfKillActor.WOLF));
		getRedisTemplate().delete(RK.idenList(roomId, WerewolfKillActor.VILLAGE));
		getRedisTemplate().delete(RK.idenList(roomId, WerewolfKillActor.PROPHET));
		getRedisTemplate().delete(RK.idenList(roomId, WerewolfKillActor.HUNTER));
		getRedisTemplate().delete(RK.idenList(roomId, WerewolfKillActor.WITCH));
		getRedisTemplate().delete(RK.idenList(roomId, WerewolfKillActor.SB));
		getRedisTemplate().delete(RK.idenList(roomId, WerewolfKillActor.DEFENDER));
		super.addJob(roomId, WerewolfKillActor.SEND_ROLE_TIME);
		MapBody mb = new MapBody(SocketHandler.SEND_ROLES, SocketHandler.FN_ROOM_ID, roomId)
				.append(SocketHandler.FN_LIMIT, WerewolfKillActor.SEND_ROLE_TIME).append("actId", wk.get_id())
				.append("identities", wk.getIdentities());
		long now = System.currentTimeMillis();
		wss = super.getRoomActorsState(roomId, WerewolfKillActorState.class);
		for (WerewolfKillActorState ws : wss) {
			mb.append("identity", ws.getIdentity());
			super.pubRoomUserMsg(roomId, ws.getUid(), getMsgType(wk), mb, now);
		}
	}

	/** 守卫阶段 */
	public void defendBegin(Room room, WerewolfKill wk) {
		long roomId = room.get_id();
		Set<WerewolfKillActorState> set = super.getRoomActorsState(roomId, WerewolfKillActorState.class);
		for (WerewolfKillActorState ws : set) {
			if (ws.getDeath()) {
				ws.setDeadLater(false);
				super.saveRoomActorState(roomId, ws);
			}
		}
		if (checkEnd(room, wk, set)) {
			return;
		}
		wk.setStatus(WerewolfKillActor.STATUS_DEFEND);
		super.saveActivity(roomId, wk);
		super.addJob(roomId, WerewolfKillActor.DEFEND_TIME);
		MapBody mb = new MapBody(SocketHandler.FN_ROOM_ID, roomId)
				.append(SocketHandler.HANDLER_NAME, SocketHandler.DEFEND).append("actId", wk.get_id())
				.append(SocketHandler.FN_LIMIT, WerewolfKillActor.DEFEND_TIME);
		Map<Integer, Long> defendMap = wk.getDefendMap();
		int day = wk.getDay();
		Long lastDefend = null;
		if (day != WerewolfKillActor.FIRST_DAY) {
			lastDefend = defendMap.get(wk.getDay());
		}
		Set<WerewolfKillActor> as = super.getRoomActors(roomId, WerewolfKillActor.class);
		long curTime = System.currentTimeMillis();
		for (WerewolfKillActor actor : as) {
			if (wk.getDefender() == actor.getUid()) {
				mb.append("lastDefend", lastDefend);
			} else {
				mb.remove("lastDefend");
			}
			super.pubRoomUserMsg(roomId, actor.getUid(), getMsgType(wk), mb, curTime);
		}
	}

	/** 杀人查人阶段 */
	public void killCheckBegin(Room room, WerewolfKill wk) {
		int playerCount = wk.getActors().size();
		wk.setStatusTable(new long[2][playerCount]);
		long roomId = room.get_id();
		Set<WerewolfKillActorState> set = super.getRoomActorsState(roomId, WerewolfKillActorState.class);
		for (WerewolfKillActorState ws : set) {
			if (ws.getDeath()) {
				ws.setDeadLater(false);
				super.saveRoomActorState(roomId, ws);
			}
		}
		if (checkEnd(room, wk, set)) {
			return;
		}
		wk.setDay(wk.getDay() + 1);
		wk.setStatus(WerewolfKillActor.STATUS_KILL_CHECK);
		wk.getKillMap().clear();
		super.saveActivity(roomId, wk);
		super.addJob(roomId, WerewolfKillActor.KILL_CHECK_TIME);
		MapBody mb = new MapBody(SocketHandler.FN_ROOM_ID, roomId).append("actId", wk.get_id())
				.append(SocketHandler.FN_LIMIT, WerewolfKillActor.KILL_CHECK_TIME);
		Set<WerewolfKillActor> as = super.getRoomActors(roomId, WerewolfKillActor.class);
		long curTime = System.currentTimeMillis();
		for (WerewolfKillActor actor : as) {
			if (actor.getRole() == Actor.ROLE_EXECUTOR && actor.isRobit()) {
				actor.setStatus(Actor.STATUS_OFFLINE);
				super.saveRoomActor(roomId, actor);
			}
			if (wk.getWolves().contains(actor.getUid())) {
				mb.append(SocketHandler.HANDLER_NAME, SocketHandler.KILLING).append("wolves", wk.getWolves());
			} else if (wk.getProphet() == actor.getUid()) {
				mb.append(SocketHandler.HANDLER_NAME, SocketHandler.CHECKING);
				mb.remove("wolves");
			} else {
				mb.append(SocketHandler.HANDLER_NAME, SocketHandler.KILL_CHECK);
				mb.remove("wolves");
			}
			super.pubRoomUserMsg(roomId, actor.getUid(), getMsgType(wk), mb, curTime);
		}
	}

	/** 计算死人阶段 */
	public void deadCalculation(Room room, WerewolfKill wk) {
		long roomId = room.get_id();
		Long deathId = null;
		Map<Long, Long> killMap = wk.getKillMap();
		if (!killMap.isEmpty()) {
			deathId = killMap.values().stream().collect(Collectors.toMap(e -> e, e -> 1, (e1, e2) -> e1 + e2))
					.entrySet().stream().max((e1, e2) -> e1.getValue() - e2.getValue()).get().getKey();
			Long defend = wk.getDefendMap().get(wk.getDay());
			defend = null == defend ? 0 : defend;
			if (defend - deathId != 0) {
				WerewolfKillActorState actorState = super.getRoomActorState(roomId, deathId,
						WerewolfKillActorState.class);
				actorState.setDeath(true);
				actorState.setDeadLater(true);
				actorState.setDeathType(WerewolfKillActor.WOLF_KILL);
				actorState.setDeathDay(wk.getDay());
				super.saveRoomActorState(roomId, actorState);
			}
		}
		wk.setStatus(WerewolfKillActor.STATUS_CALCULATION);
		wk.setDead(deathId);
		super.saveActivity(roomId, wk);
		super.addJob(roomId, WerewolfKillActor.CALCULATION_TIME);
		MapBody mb = new MapBody(SocketHandler.CALCULATION, SocketHandler.FN_ROOM_ID, roomId)
				.append("actId", wk.get_id()).append("dead", deathId);
		Set<WerewolfKillActor> as = super.getRoomActors(roomId, WerewolfKillActor.class);
		long curTime = System.currentTimeMillis();
		for (WerewolfKillActor actor : as) {
			long uid = actor.getUid();
			if (wk.getWolves().contains(uid)) {
				super.pubRoomUserMsg(roomId, uid, getMsgType(wk), mb, curTime);
			}
		}
	}

	/** 救人毒人阶段 */
	public void savePoisonBegin(Room room, WerewolfKill wk) {
		long roomId = room.get_id();
		Long deathId = wk.getDead();
		wk.setStatus(WerewolfKillActor.STATUS_SAVE_POISON);
		super.saveActivity(roomId, wk);
		super.addJob(roomId, WerewolfKillActor.SAVE_POISON_TIME);
		MapBody mb = new MapBody(SocketHandler.SAVE_POISON, SocketHandler.FN_ROOM_ID, roomId)
				.append("actId", wk.get_id()).append(SocketHandler.FN_LIMIT, WerewolfKillActor.SAVE_POISON_TIME)
				.append("day", wk.getDay());
		Set<WerewolfKillActor> as = super.getRoomActors(roomId, WerewolfKillActor.class);
		long curTime = System.currentTimeMillis();
		for (WerewolfKillActor actor : as) {
			if (wk.getWitch() == actor.getUid()) {
				Integer sday = wk.getSaveMap().get(actor.getUid());
				Integer pday = wk.getPoisonMap().get(actor.getUid());
				if (null == sday) {
					mb.append("dead", deathId).append("hasSave", true);
				} else {
					mb.append("hasSave", false).remove("dead");
				}
				mb.append("hasPoison", null == pday ? true : false);
			} else {
				mb.remove("dead").remove("hasSave").remove("hasPoison");
			}
			log.error(" actor.getUid()" + actor.getUid());
			super.pubRoomUserMsg(roomId, actor.getUid(), getMsgType(wk), mb, curTime);
		}
	}

	/** 守卫守人 */
	public ReMsg defend(long uid, long did, long roomId) {
		log.error("::::::::::::::defending begin");
		WerewolfKill wk = super.getActivity(roomId, WerewolfKill.class);
		if (WerewolfKillActor.STATUS_DEFEND == wk.getStatus()) {
			WerewolfKillActorState ua = super.getRoomActorState(roomId, uid, WerewolfKillActorState.class);
			WerewolfKillActorState da = super.getRoomActorState(roomId, did, WerewolfKillActorState.class);
			if (null != ua && null != da && ua.getIdentity() == WerewolfKillActor.DEFENDER && !ua.getDeath()
					&& !da.getDeath()) {
				Map<Integer, Long> defendMap = wk.getDefendMap();
				int day = wk.getDay();
				if (day != WerewolfKillActor.FIRST_DAY - 1) {
					Long lastDefend = defendMap.get(day);
					if (null != lastDefend && lastDefend - did == 0) {
						return new ReMsg(ReCode.FAIL);
					} else {
						defendMap.put(day + 1, did);
					}
				} else {
					defendMap.put(day + 1, did);
				}
				wk.setDefendMap(defendMap);
				super.saveActivity(roomId, wk);
				MapBody mb = new MapBody(SocketHandler.DEFENDING, SocketHandler.FN_USER_ID, uid)
						.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", wk.get_id()).append("did", did);
				super.pubRoomUserMsg(roomId, uid, getMsgType(wk), mb, System.currentTimeMillis());
			}
			log.error("::::::::::::::defending end");
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 狼人杀人 */
	public ReMsg kill(long uid, long kid, long roomId) {
		log.error("::::::::::::::killing begin");
		WerewolfKill wk = super.getActivity(roomId, WerewolfKill.class);
		if (WerewolfKillActor.STATUS_KILL_CHECK == wk.getStatus()) {
			WerewolfKillActorState ua = super.getRoomActorState(roomId, uid, WerewolfKillActorState.class);
			WerewolfKillActorState ka = super.getRoomActorState(roomId, kid, WerewolfKillActorState.class);
			if (null != ua && null != ka && ua.getIdentity() == WerewolfKillActor.WOLF && !ua.getDeath()
					&& !ka.getDeath()) {
				Map<Long, Long> killMap = wk.getKillMap();
				killMap.put(uid, kid);
				MapBody mb = new MapBody(SocketHandler.KILL, SocketHandler.FN_USER_ID, uid)
						.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", wk.get_id()).append("kid", kid);
				List<Long> wolves = wk.getWolves();
				long curTime = System.currentTimeMillis();
				wk.setKillMap(killMap);
				super.saveActivity(roomId, wk);
				for (Long wolf : wolves) {
					super.pubRoomUserMsg(roomId, wolf, getMsgType(wk), mb, curTime);
				}
				log.error("::::::::::::::killing end");
				return new ReMsg(ReCode.OK);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 狼人取消杀人 */
	public ReMsg cancelKill(long uid, long roomId) {
		log.error("::::::::::::::cancelkilling begin");
		WerewolfKill wk = super.getActivity(roomId, WerewolfKill.class);
		if (WerewolfKillActor.STATUS_KILL_CHECK == wk.getStatus()) {
			WerewolfKillActorState ua = super.getRoomActorState(roomId, uid, WerewolfKillActorState.class);
			if (null != ua && ua.getIdentity() == WerewolfKillActor.WOLF && !ua.getDeath()) {
				Map<Long, Long> killMap = wk.getKillMap();
				Long kid = killMap.get(uid);
				killMap.remove(uid);
				MapBody mb = new MapBody(SocketHandler.CANCELKILL, SocketHandler.FN_USER_ID, uid)
						.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", wk.get_id()).append("kid", kid);
				List<Long> wolves = wk.getWolves();
				long curTime = System.currentTimeMillis();
				wk.setKillMap(killMap);
				super.saveActivity(roomId, wk);
				for (Long wolf : wolves) {
					super.pubRoomUserMsg(roomId, wolf, getMsgType(wk), mb, curTime);
				}
				log.error("::::::::::::::killing end");
				return new ReMsg(ReCode.OK);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 狼人自爆 */
	public ReMsg wolfBoom(long uid, long roomId) {
		log.error("::::::::::::::booming begin");
		WerewolfKill wk = super.getActivity(roomId, WerewolfKill.class);
		if (WerewolfKillActor.STATUS_SPEAK == wk.getStatus() || WerewolfKillActor.STATUS_VOTE == wk.getStatus()
				|| WerewolfKillActor.STATUS_LAST_WORDS == wk.getStatus()) {
			WerewolfKillActorState ua = super.getRoomActorState(roomId, uid, WerewolfKillActorState.class);
			if (null != ua && ua.getIdentity() == WerewolfKillActor.WOLF && !ua.getDeath()) {
				ua.setDeath(true);
				ua.setDeathType(WerewolfKillActor.BOOM);
				ua.setDeathDay(ua.getDeathDay());
				super.saveRoomActorState(roomId, ua);
				MapBody mb = new MapBody(SocketHandler.BOOM, SocketHandler.FN_USER_ID, uid)
						.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", wk.get_id());
				super.pubRoomMsg(roomId, getMsgType(wk), mb);
				wk.setStatus(WerewolfKillActor.STATUS_SEND_ROLE);
				super.addJob(roomId, WerewolfKillActor.BOOM_TIME);
				log.error("::::::::::::::boom end");
				return new ReMsg(ReCode.OK);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 预言家验人 */
	public ReMsg check(long uid, long cid, long roomId) {
		WerewolfKill wk = super.getActivity(roomId, WerewolfKill.class);
		if (WerewolfKillActor.STATUS_KILL_CHECK == wk.getStatus()) {
			log.error(":::::::::::::::::::::预言家验人 begin");
			WerewolfKillActorState ua = super.getRoomActorState(roomId, uid, WerewolfKillActorState.class);
			WerewolfKillActorState ca = super.getRoomActorState(roomId, cid, WerewolfKillActorState.class);
			long prophet = wk.getProphet();
			if (null != ua && null != ca && prophet == uid && !ua.getDeath() && !ca.getDeath()) {
				Map<Integer, Long> checkMap = wk.getCheckMap();
				if (null != checkMap.get(wk.getDay())) {
					return new ReMsg(ReCode.WAS_CHECKED);
				} else {
					boolean goodman = true;
					if (wk.getWolves().contains(cid)) {
						goodman = false;
					}
					checkMap.put(wk.getDay(), cid);
					wk.setCheckMap(checkMap);
					super.saveActivity(roomId, wk);
					MapBody mb = new MapBody(SocketHandler.CHECK, SocketHandler.FN_USER_ID, uid)
							.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", wk.get_id())
							.append("goodman", goodman).append("cid", cid);
					long curTime = System.currentTimeMillis();
					log.error(":::::::::::::::::::::预言家验人 end");
					super.pubRoomUserMsg(roomId, uid, getMsgType(wk), mb, curTime);
					return new ReMsg(ReCode.OK);
				}
			}
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 女巫救人 */
	public ReMsg save(long uid, long sid, long roomId) {
		log.error("::::::::::::::saving begin");
		WerewolfKill wk = super.getActivity(roomId, WerewolfKill.class);
		if (WerewolfKillActor.STATUS_SAVE_POISON == wk.getStatus()) {
			WerewolfKillActorState ua = super.getRoomActorState(roomId, uid, WerewolfKillActorState.class);
			WerewolfKillActorState sa = super.getRoomActorState(roomId, sid, WerewolfKillActorState.class);
			if (null != ua && null != sa && ua.getIdentity() == WerewolfKillActor.WITCH) {
				if (uid == sid && wk.getDay() != WerewolfKillActor.FIRST_DAY) {
					return new ReMsg(ReCode.CANNOT_SAVEYOURSELF);
				}
				Map<Long, Integer> saveMap = wk.getSaveMap();
				Map<Long, Integer> poisonMap = wk.getPoisonMap();
				if (null != saveMap.get(uid)) {
					return new ReMsg(ReCode.WAS_SAVED);
				} else if (null != poisonMap.get(uid) && poisonMap.get(uid) == wk.getDay()) {
					return new ReMsg(ReCode.CANNOT_USE_TOGETHER);
				}
				saveMap.put(uid, wk.getDay());
				wk.setSaveMap(saveMap);
				super.saveActivity(roomId, wk);
				Long defend = wk.getDefendMap().get(wk.getDay());
				log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>sid=" + sid + "defend=" + defend);
				if (null == defend || null != defend && defend != sid) {
					log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>save");
					sa.setDeath(false);
					sa.setDeathType(WerewolfKillActor.ALIVE);
				} else {
					log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>kill");
					sa.setDeath(true);
					sa.setDeadLater(true);
					sa.setDeathType(WerewolfKillActor.DEFENDANDSAVE);
					sa.setDeathDay(wk.getDay());
				}
				super.saveRoomActorState(roomId, sa);
				MapBody mb = new MapBody(SocketHandler.SAVE, SocketHandler.FN_USER_ID, uid)
						.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", wk.get_id()).append("sid", sid);
				long curTime = System.currentTimeMillis();
				super.pubRoomUserMsg(roomId, uid, getMsgType(wk), mb, curTime);
			}
			log.error("::::::::::::::saving end");
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 女巫杀人 */
	public ReMsg poison(long uid, long pid, long roomId) {
		log.error("::::::::::::::poisoning begin");
		WerewolfKill wk = super.getActivity(roomId, WerewolfKill.class);
		if (WerewolfKillActor.STATUS_SAVE_POISON == wk.getStatus()) {
			WerewolfKillActorState ua = super.getRoomActorState(roomId, uid, WerewolfKillActorState.class);
			WerewolfKillActorState pa = super.getRoomActorState(roomId, pid, WerewolfKillActorState.class);
			if (null != pa && pa.getDeath()) {
				MapBody mb = new MapBody(SocketHandler.POISON, SocketHandler.FN_USER_ID, uid)
						.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", wk.get_id());
				long curTime = System.currentTimeMillis();
				super.pubRoomUserMsg(roomId, uid, getMsgType(wk), mb, curTime);
			} else if (null != ua && ua.getIdentity() == WerewolfKillActor.WITCH) {
				Map<Long, Integer> saveMap = wk.getSaveMap();
				Map<Long, Integer> poisonMap = wk.getPoisonMap();
				if (null != poisonMap.get(uid)) {
					return new ReMsg(ReCode.WAS_POISONED);
				} else if (null != saveMap.get(uid) && saveMap.get(uid) == wk.getDay()) {
					return new ReMsg(ReCode.CANNOT_USE_TOGETHER);
				}
				poisonMap.put(uid, wk.getDay());
				wk.setPoisonMap(poisonMap);
				super.saveActivity(roomId, wk);
				pa.setDeath(true);
				pa.setDeadLater(true);
				pa.setDeathDay(wk.getDay());
				pa.setDeathType(WerewolfKillActor.WITCH_KILL);
				super.saveRoomActorState(roomId, pa);
				MapBody mb = new MapBody(SocketHandler.POISON, SocketHandler.FN_USER_ID, uid)
						.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", wk.get_id());
				long curTime = System.currentTimeMillis();
				super.pubRoomUserMsg(roomId, uid, getMsgType(wk), mb, curTime);
			}
			log.error("::::::::::::::poisoning end");
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 猎人杀人 */
	public ReMsg shot(long uid, long kid, long roomId) {
		log.error("::::::::::::::shotting begin");
		WerewolfKill wk = super.getActivity(roomId, WerewolfKill.class);
		if (WerewolfKillActor.STATUS_REVENGE == wk.getStatus()) {
			WerewolfKillActorState ua = super.getRoomActorState(roomId, uid, WerewolfKillActorState.class);
			WerewolfKillActorState ka = super.getRoomActorState(roomId, kid, WerewolfKillActorState.class);
			if (null != ua && null != ka && ua.getIdentity() == WerewolfKillActor.HUNTER && !ka.getDeath()) {
				super.saveActivity(roomId, wk);
				ka.setDeath(true);
				ka.setDeathDay(wk.getDay());
				ka.setDeathType(WerewolfKillActor.HUNTER_KILL);
				super.saveRoomActorState(roomId, ka);
				super.addJob(roomId, WerewolfKillActor.REVENGED_TIME);
				MapBody mb = new MapBody(SocketHandler.SHOT, SocketHandler.FN_ROOM_ID, roomId).append("kid", kid)
						.append("actId", wk.get_id());
				super.pubRoomMsg(roomId, getMsgType(wk), mb);
			}
			log.error("::::::::::::::shotting end");
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 猎人取消杀人 */
	public ReMsg cancelShot(long uid, long roomId) {
		log.error("::::::::::::::cancelshotting begin");
		WerewolfKill wk = super.getActivity(roomId, WerewolfKill.class);
		if (WerewolfKillActor.STATUS_REVENGE == wk.getStatus()) {
			WerewolfKillActorState ua = super.getRoomActorState(roomId, uid, WerewolfKillActorState.class);
			if (null != ua && ua.getIdentity() == WerewolfKillActor.HUNTER) {
				super.addJob(roomId, WerewolfKillActor.REVENGED_TIME);
				MapBody mb = new MapBody(SocketHandler.CANCEL_SHOT, SocketHandler.FN_ROOM_ID, roomId).append("actId",
						wk.get_id());
				super.pubRoomMsg(roomId, getMsgType(wk), mb);
			}
			log.error("::::::::::::::cancelshotting end");
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 天亮了 */
	public void sunRise(Room room, WerewolfKill wk) {
		long roomId = room.get_id();
		Set<WerewolfKillActorState> set = super.getRoomActorsState(roomId, WerewolfKillActorState.class);
		Set<Long> deathUsers = new HashSet<>();
		int day = wk.getDay();
		Long defend = wk.getDefendMap().get(day);
		defend = null == defend ? 0 : defend;
		for (WerewolfKillActorState ws : set) {
			Long uid = ws.getUid();
			if (ws.getDeath() && ws.getDeathDay() == day
					&& (ws.getDeathType() == WerewolfKillActor.WITCH_KILL
							|| ws.getDeathType() == WerewolfKillActor.DEFENDANDSAVE
							|| ws.getDeathType() == WerewolfKillActor.WOLF_KILL && defend != uid)) {
				System.out.println("::::::::::::::::::::::同救同守死亡");
				deathUsers.add(uid);
				ws.setDeadLater(false);
				super.saveRoomActorState(roomId, ws);
			}
		}
		super.saveActivity(roomId, wk);
		MapBody mb = new MapBody(SocketHandler.KILLED, SocketHandler.FN_ROOM_ID, roomId).append("actId", wk.get_id())
				.append("deathUsers", deathUsers).append("safeNight", deathUsers.isEmpty());
		super.pubRoomMsg(roomId, getMsgType(wk), mb);
		wk.setStatus(WerewolfKillActor.STATUS_DAY);
		super.saveActivity(roomId, wk);
		super.addJob(roomId, WerewolfKillActor.DAY_TIME);

	}

	/** 公布死人阶段 */
	public void publishDead(Room room, WerewolfKill wk) {
		long roomId = room.get_id();
		Set<WerewolfKillActorState> set = super.getRoomActorsState(roomId, WerewolfKillActorState.class);
		if (checkEnd(room, wk, set)) {
			return;
		}
		if (null != wk.getIdentities().get(WerewolfKillActor.HUNTER) && !wk.isShotted() && hunterDead(room, wk)) {
			revengeBegin(room, wk);
		} else {
			speakBegin(room, wk);
		}
	}

	/** 检查猎人是否死亡 */
	public boolean hunterDead(Room room, WerewolfKill wk) {
		long hunter = wk.getHunter();
		Set<WerewolfKillActorState> set = super.getRoomActorsState(room.get_id(), WerewolfKillActorState.class);
		WerewolfKillActorState cur = null;
		boolean hunterDead = false;
		for (WerewolfKillActorState e : set) {
			if (e.getUid() == hunter) {
				if (e.getDeath()) {
					hunterDead = true;
				}
				cur = e;
				break;
			}
		}
		if (hunterDead) {
			int type = cur.getDeathType();
			if (cur.getDeathDay() != wk.getDay()) {
				hunterDead = false;
			}
			wk.setHunterDeadType(type);
			super.saveActivity(room.get_id(), wk);
			if (type == WerewolfKillActor.WITCH_KILL) {
				hunterDead = false;
			}
		}
		return hunterDead;
	}

	/** 猎人复仇开始 */
	public void revengeBegin(Room room, WerewolfKill wk) {
		long roomId = room.get_id();
		Set<WerewolfKillActorState> set = super.getRoomActorsState(roomId, WerewolfKillActorState.class);
		if (checkEnd(room, wk, set)) {
			return;
		}
		wk.setStatus(WerewolfKillActor.STATUS_REVENGE);
		wk.setShotted(true);
		boolean hasLastWords = false;
		WerewolfKillActorState ws = super.getRoomActorState(roomId, wk.getHunter(), WerewolfKillActorState.class);
		if (ws.getDeathType() == WerewolfKillActor.VOTE_KILL
				|| wk.getDay() == WerewolfKillActor.FIRST_DAY && ws.getDeathType() == WerewolfKillActor.WOLF_KILL) {
			hasLastWords = true;
		}
		super.addJob(roomId, WerewolfKillActor.REVENGE_TIME);
		MapBody mb = new MapBody(SocketHandler.REVENGE, SocketHandler.FN_ROOM_ID, roomId).append("actId", wk.get_id())
				.append(SocketHandler.FN_USER_ID, wk.getHunter())
				.append(SocketHandler.FN_LIMIT, WerewolfKillActor.REVENGE_TIME).append("hasLastWords", hasLastWords);
		super.saveActivity(roomId, wk);
		super.pubRoomMsg(roomId, getMsgType(wk), mb);
	}

	/** 发言阶段 */
	public void speakBegin(Room room, WerewolfKill wk) {
		log.error(":::::::::::::::::发言begin");
		long roomId = room.get_id();
		wk.setStatus(WerewolfKillActor.STATUS_SPEAK);
		wk.setSpeaked(null);
		Set<WerewolfKillActorState> set = super.getRoomActorsState(roomId, WerewolfKillActorState.class);
		List<Long> servivals = set.stream().filter(e -> !e.getDeath()).map(e -> e.getUid())
				.collect(Collectors.toList());
		if (checkEnd(room, wk, set)) {
			return;
		}
		long[][] statusTable = wk.getStatusTable();
		long[] seatTable = wk.getSeatUserTable();
		int playerCount = wk.getActors().size();
		boolean firstSpeak = false;
		if (statusTable[0][0] == 0) {
			firstSpeak = true;
			int maxNo = -1;
			int firstSize = 0;
			int day = wk.getDay();
			List<Integer> deathList = set.stream().filter(e -> {
				return e.getDeath() && e.getDeathDay() == wk.getDay();
			}).map(e -> indexOf(e.getUid(), seatTable)).sorted((e1, e2) -> e1 - e2).collect(Collectors.toList());
			if (!deathList.isEmpty()) {
				int dSize = deathList.size();
				for (int i = 0; i < dSize; i++) {
					if (day == WerewolfKillActor.FIRST_DAY) {
						statusTable[0][i] = seatTable[deathList.get(i)];
						firstSize = dSize;
					}
					maxNo = deathList.get(dSize - 1);
				}
			} else {
				maxNo = RandomUtil.nextInt(playerCount);
			}
			List<Long> afterList = new ArrayList<>();
			List<Long> beforeList = new ArrayList<>();
			for (Long e : servivals) {
				if (indexOf(e, seatTable) < maxNo) {
					afterList.add(e);
				} else {
					beforeList.add(e);
				}
			}
			beforeList = beforeList.stream().sorted((e1, e2) -> indexOf(e1, seatTable) - indexOf(e2, seatTable))
					.collect(Collectors.toList());
			afterList = afterList.stream().sorted((e1, e2) -> indexOf(e1, seatTable) - indexOf(e2, seatTable))
					.collect(Collectors.toList());
			for (int i = 0; i < beforeList.size(); i++) {
				statusTable[0][firstSize + i] = beforeList.get(i);
			}
			for (int i = 0; i < afterList.size(); i++) {
				statusTable[0][firstSize + beforeList.size() + i] = afterList.get(i);
			}
		}
		int curSpeaker = -1;
		for (int i = 0; i < statusTable[0].length; i++) {
			if (statusTable[0][i] != 0 && statusTable[1][i] == WerewolfKillActor.A_STATUS_START) {
				if (isDeadHunter(statusTable[0][i], wk, roomId)) {
					continue;
				}
				if (super.getRoomActor(roomId, statusTable[0][i], WerewolfKillActor.class)
						.getStatus() != Actor.STATUS_ONLINE) {
					continue;
				}
				curSpeaker = i;
				statusTable[1][i] = WerewolfKillActor.A_STATUS_SPEAK;
				wk.setStatusTable(statusTable);
				break;
			}
		}
		super.saveActivity(roomId, wk);
		if (curSpeaker == -1) {
			log.error(":::::开始投票begin");
			voteBegin(room, wk);
			log.error(":::::开始投票end");
		} else {
			wk.setStatusTable(statusTable);
			long uid = statusTable[0][curSpeaker];
			wk.setSpeaker(uid);
			super.saveActivity(roomId, wk);
			super.addJob(roomId, WerewolfKillActor.SPEAK_TIME);
			MapBody mb = new MapBody(servivals.contains(uid) ? SocketHandler.SPEAK : SocketHandler.FIRST_BLOOD,
					SocketHandler.FN_ROOM_ID, roomId).append("actId", wk.get_id()).append(SocketHandler.FN_USER_ID, uid)
							.append(SocketHandler.FN_LIMIT, WerewolfKillActor.SPEAK_TIME)
							.append("firstSpeak", firstSpeak);
			super.pubRoomMsg(roomId, getMsgType(wk), mb);

		}
		log.error(":::::::::::::::::发言end");
	}

	/** 死猎人 */
	private boolean isDeadHunter(long uid, WerewolfKill wk, long roomId) {
		boolean isDeadHunter = false;
		if (0 != wk.getHunter()) {
			long hid = wk.getHunter();
			WerewolfKillActorState ws = super.getRoomActorState(roomId, hid, WerewolfKillActorState.class);
			if (uid == hid && ws.getDeath()) {
				isDeadHunter = true;
			}
		}
		return isDeadHunter;
	}

	/** 发言 */
	public ReMsg speak(long uid, long roomId) {
		WerewolfKill wk = super.getActivity(roomId, WerewolfKill.class);
		if (WerewolfKillActor.STATUS_SPEAK == wk.getStatus()) {
			if (null != wk.getSpeaked() && wk.getSpeaked() == uid) {
				MapBody mb = new MapBody(SocketHandler.SPEAKING, SocketHandler.FN_ROOM_ID, roomId)
						.append(SocketHandler.FN_USER_ID, uid);
				super.pubRoomMsg(roomId, getMsgType(wk), mb);
			} else {
				wk.setSpeaked(uid);
				super.saveActivity(roomId, wk);
				super.addJob(roomId, WerewolfKillActor.REAL_SPEAK_TIME);
				MapBody mb = new MapBody(SocketHandler.SPEAKING, SocketHandler.FN_ROOM_ID, roomId)
						.append(SocketHandler.FN_USER_ID, uid)
						.append(SocketHandler.FN_LIMIT, WerewolfKillActor.REAL_SPEAK_TIME);
				super.pubRoomMsg(roomId, getMsgType(wk), mb);
			}
			return new ReMsg(ReCode.OK);
		} else {
			MapBody mb = new MapBody(SocketHandler.SPEAKING, SocketHandler.FN_ROOM_ID, roomId)
					.append(SocketHandler.FN_USER_ID, uid);
			super.pubRoomMsg(roomId, getMsgType(wk), mb);
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 暂停发言 */
	public ReMsg pauseSpeak(long uid, long roomId) {
		WerewolfKill wk = super.getActivity(roomId, WerewolfKill.class);
		MapBody mb = new MapBody(SocketHandler.PAUSESPEAKING, SocketHandler.FN_ROOM_ID, roomId)
				.append(SocketHandler.FN_USER_ID, uid);
		super.pubRoomMsg(roomId, getMsgType(wk), mb);
		return new ReMsg(ReCode.OK);
	}

	/** 取消发言 */
	public ReMsg cancelSpeak(long uid, long roomId) {
		WerewolfKill wk = super.getActivity(roomId, WerewolfKill.class);
		if (WerewolfKillActor.STATUS_REVENGE == wk.getStatus()) {
			WerewolfKillActorState ua = super.getRoomActorState(roomId, uid, WerewolfKillActorState.class);
			if (null != ua && ua.getIdentity() == WerewolfKillActor.HUNTER) {
				super.addJob(roomId, WerewolfKillActor.REVENGED_TIME);
				MapBody mb = new MapBody(SocketHandler.CANCEL_SHOT, SocketHandler.FN_ROOM_ID, roomId).append("actId",
						wk.get_id());
				super.pubRoomMsg(roomId, getMsgType(wk), mb);
			}
			return new ReMsg(ReCode.OK);
		}
		if (WerewolfKillActor.STATUS_SPEAK == wk.getStatus() || WerewolfKillActor.STATUS_LAST_WORDS == wk.getStatus()) {
			super.addJob(roomId, 1);
			MapBody mb = new MapBody(SocketHandler.CANCELSPEAK, SocketHandler.FN_ROOM_ID, roomId)
					.append(SocketHandler.FN_USER_ID, uid);
			super.pubRoomMsg(roomId, getMsgType(wk), mb);
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 投票阶段 */
	public void voteBegin(Room room, WerewolfKill wk) {
		long roomId = room.get_id();
		Set<WerewolfKillActorState> set = super.getRoomActorsState(roomId, WerewolfKillActorState.class);
		for (WerewolfKillActorState ws : set) {
			ws.setVote(false);
			super.saveRoomActorState(roomId, ws);
		}
		wk.setStatus(WerewolfKillActor.STATUS_VOTE);
		super.saveActivity(roomId, wk);
		super.addJob(room.get_id(), WerewolfKillActor.VOTE_TIME);
		MapBody mb = new MapBody(SocketHandler.VOTE, SocketHandler.FN_ROOM_ID, roomId).append("actId", wk.get_id())
				.append(SocketHandler.FN_LIMIT, WerewolfKillActor.VOTE_TIME);
		super.pubRoomMsg(roomId, getMsgType(wk), mb);
		if (checkEnd(room, wk, set)) {
			return;
		}
	}

	/** 投票 */
	public ReMsg vote(long uid, long vid, long roomId) {
		WerewolfKill wk = super.getActivity(roomId, WerewolfKill.class);
		if (WerewolfKillActor.STATUS_VOTE == wk.getStatus()) {
			Set<WerewolfKillActorState> set = super.getRoomActorsState(roomId, WerewolfKillActorState.class);
			Set<Long> servivals = new HashSet<>();
			Set<Long> unVotes = new HashSet<>();
			for (WerewolfKillActorState ws : set) {
				if (!ws.getDeath()) {
					servivals.add(ws.getUid());
					if (!ws.getVote()) {
						unVotes.add(ws.getUid());
					}
				}
			}
			if (servivals.contains(uid) && servivals.contains(vid)) {
				if (!unVotes.contains(uid)) {
					return new ReMsg(ReCode.WAS_VOTED);
				} else {
					WerewolfKillActorState ws = super.getRoomActorState(roomId, uid, WerewolfKillActorState.class);
					ws.setVote(true);
					ws.setVid(vid);
					super.saveRoomActorState(roomId, ws);
				}
				MapBody mb = new MapBody(SocketHandler.VOTING, SocketHandler.FN_ROOM_ID, roomId)
						.append(SocketHandler.FN_USER_ID, uid).append("actId", wk.get_id());
				long curTime = System.currentTimeMillis();
				super.pubRoomUserMsg(roomId, uid, getMsgType(wk), mb, curTime);
				return new ReMsg(ReCode.OK);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 投票结果展示阶段 */
	public void showVoteBegin(Room room, WerewolfKill wk) {
		boolean up = false;
		long roomId = room.get_id();
		wk.setStatus(WerewolfKillActor.STATUS_SHOW_VOTE);
		Set<WerewolfKillActorState> asset = super.getRoomActorsState(roomId, WerewolfKillActorState.class);
		Set<Long> unVotes = new HashSet<>();
		Map<Long, Set<Long>> voteMap = new HashMap<>();
		for (WerewolfKillActorState ws : asset) {
			if (!ws.getDeath()) {
				if (ws.getVote()) {
					Long vid = ws.getVid();
					Set<Long> set = voteMap.get(vid);
					set = null == set ? new HashSet<>() : set;
					set.add(ws.getUid());
					voteMap.put(vid, set);
				} else {
					unVotes.add(ws.getUid());
				}
			}
		}
		Set<Long> set = voteMap.keySet();
		boolean hasDeath = false;
		boolean hasSb = false;
		Long deadId = null;
		if (!set.isEmpty()) {
			List<Long> list = set.stream().sorted((e1, e2) -> voteMap.get(e2).size() - voteMap.get(e1).size())
					.collect(Collectors.toList());
			if (list.size() > 1) {
				hasDeath = voteMap.get(list.get(0)).size() == voteMap.get(list.get(1)).size() ? false : true;
			} else {
				hasDeath = true;
			}
			deadId = list.get(0);
		}
		if (null != deadId && null != wk.getIdentities().get(WerewolfKillActor.SB) && wk.getSb() - deadId == 0) {
			hasSb = true;
		}
		if (hasDeath && !hasSb) {
			WerewolfKillActorState wa = super.getRoomActorState(roomId, deadId, WerewolfKillActorState.class);
			wa.setDeath(true);
			wa.setDeadLater(true);
			wa.setDeathDay(wk.getDay());
			wa.setDeathType(WerewolfKillActor.VOTE_KILL);
			super.saveRoomActorState(roomId, wa);
			wk.setLastWordUser(deadId);
			up = true;
		} else {
			wk.setStatus(WerewolfKillActor.STATUS_LAST_WORDS);
		}
		MapBody mb;
		if (hasDeath && hasSb) {
			wk.setSbBeVoted(true);
			mb = new MapBody(SocketHandler.SHOW_VOTE, SocketHandler.FN_ROOM_ID, roomId).append("actId", wk.get_id())
					.append(SocketHandler.FN_LIMIT, WerewolfKillActor.SHOW_VOTE_TIME).append("votes", voteMap)
					.append("unVotes", unVotes).append("sb", deadId);
		} else {
			mb = new MapBody(SocketHandler.SHOW_VOTE, SocketHandler.FN_ROOM_ID, roomId).append("actId", wk.get_id())
					.append(SocketHandler.FN_LIMIT, WerewolfKillActor.SHOW_VOTE_TIME).append("votes", voteMap)
					.append("unVotes", unVotes);
			if (hasDeath) {
				mb.append("deadId", deadId);
			}
		}
		super.addJob(room.get_id(), WerewolfKillActor.SHOW_VOTE_TIME);
		super.pubRoomMsg(roomId, getMsgType(wk), mb);
		super.saveActivity(roomId, wk);
		if (null != wk.getIdentities().get(WerewolfKillActor.HUNTER) && !wk.isShotted() && hunterDead(room, wk)) {
			revengeBegin(room, wk);
		}
		if (up) {
			asset = super.getRoomActorsState(roomId, WerewolfKillActorState.class);
		}
	}

	/** 发表遗言阶段 */
	public void lastWordsBegin(Room room, WerewolfKill wk) {
		long roomId = room.get_id();
		Set<WerewolfKillActorState> asset = super.getRoomActorsState(roomId, WerewolfKillActorState.class);
		if (checkEnd(room, wk, asset)) {
			return;
		}
		wk.setStatus(WerewolfKillActor.STATUS_LAST_WORDS);
		Long lastWordUser = wk.getLastWordUser();
		super.saveActivity(roomId, wk);
		super.addJob(room.get_id(), WerewolfKillActor.LAST_WORDS_TIME);
		MapBody mb = new MapBody(SocketHandler.LAST_WORDS, SocketHandler.FN_ROOM_ID, roomId)
				.append("actId", wk.get_id()).append(SocketHandler.FN_LIMIT, WerewolfKillActor.LAST_WORDS_TIME)
				.append(SocketHandler.FN_USER_ID, lastWordUser);
		super.pubRoomMsg(roomId, getMsgType(wk), mb);
	}

	/** 游戏结束阶段 */
	public void showEndBegin(Room room, WerewolfKill wk) {
		int type = wk.getWinType();
		int playerCount = WolfSetup.map.get(wk.getType());
		long roomId = room.get_id();
		Set<WerewolfKillActorState> set = super.getRoomActorsState(roomId, WerewolfKillActorState.class);
		for (WerewolfKillActorState ws : set) {
			if (ws.getIdentity() > WerewolfKillActor.WOLF && type == WerewolfKillActor.GOODWIN
					|| ws.getIdentity() == WerewolfKillActor.WOLF && type == WerewolfKillActor.WOLFWIN) {
				ws.setWinCoin(WerewolfKillActor.WIN_COIN);
				ws.setWinPoint(WerewolfKillActor.WIN_POINT);
				coinService.addCoin(ws.getUid(), CoinLog.WEREWOLF, wk.get_id(), WerewolfKillActor.WIN_COIN, 0, "狼人杀胜利");
				userService.changePoint(ws.getUid(), Point.WEREWOLF.getType(), WerewolfKillActor.WIN_POINT, 0);
				saveWerewolfKillLog(roomId, ws, wk, true);
			} else {
				ws.setWinPoint(WerewolfKillActor.LOSE_POINT);
				userService.changePoint(ws.getUid(), Point.WEREWOLF.getType(), WerewolfKillActor.LOSE_POINT, 0);
				saveWerewolfKillLog(roomId, ws, wk, false);
			}
		}
		MapBody mb = new MapBody(SocketHandler.SHOW_END, SocketHandler.FN_ROOM_ID, roomId).append("actId", wk.get_id())
				.append("actorState", set).append("result", type)
				.append(SocketHandler.FN_LIMIT, WerewolfKillActor.SHOW_END_TIME);
		Set<WerewolfKillActor> as = super.getRoomActors(roomId, WerewolfKillActor.class);
		long curTime = System.currentTimeMillis();
		for (WerewolfKillActor actor : as) {
			// if (actor.getRole() == Actor.ROLE_EXECUTOR) {
			double newRate;
			double changeRate;
			WerewolfKillLog wl = getRateLog(actor.getUid(), playerCount);
			if (null == wl) {
				int winCoin = 0;
				for (WerewolfKillActorState ws : set) {
					if (actor.getUid() == ws.getUid()) {
						winCoin = ws.getWinCoin();
					}
				}
				if (winCoin > 0) {
					newRate = 100;
					changeRate = 100;
				} else {
					newRate = 0;
					changeRate = 0;
				}
			} else {
				double newAllCount = wl.getAllCount();
				double newWinCount = wl.getAllWinCount();
				double oldAllCount = newAllCount - 1;
				double oldWinCount = 0;
				for (WerewolfKillActorState ws : set) {
					if (actor.getUid() == ws.getUid()) {
						if (ws.getWinCoin() > 0) {
							oldWinCount = newWinCount - 1;
						} else {
							oldWinCount = newWinCount;
						}
					}
				}
				double oldRate;
				if (newAllCount == 0) {
					newRate = 0;
				} else {
					newRate = newWinCount * 100 / newAllCount;
					BigDecimal b = new BigDecimal(newRate);
					newRate = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				if (oldAllCount == 0) {
					oldRate = 0;
				} else {
					oldRate = oldWinCount * 100 / oldAllCount;
					BigDecimal b = new BigDecimal(oldRate);
					oldRate = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				changeRate = Math.abs(newRate - oldRate);
				BigDecimal b = new BigDecimal(changeRate);
				changeRate = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			}
			if (actor.getRole() == Actor.ROLE_VIEWER) {
				changeRate = 0;
			}
			mb.append("changeRate", changeRate + "%");
			mb.append("newRate", newRate + "%");
			// log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>newRate" + newRate);
			// log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>changeRate" +
			// changeRate);
			// } else {
			// mb.remove("changeRate").remove("newRate");
			// }
			log.error(" actor.getUid()" + actor.getUid());
			super.pubRoomUserMsg(roomId, actor.getUid(), getMsgType(wk), mb, curTime);
		}
		wk.setStatus(WerewolfKillActor.STATUS_SHOW_END);
		wk.setUpdateTime(System.currentTimeMillis());
		super.saveActivity(roomId, wk);
		super.addJob(roomId, WerewolfKillActor.SHOW_END_TIME);
		super.getMongoTemplate().save(wk);
	}

	/** 获取日志 */
	private WerewolfKillLog getRateLog(long uid, int playerCount) {
		DBObject dbo = getC(DocName.WEREWOLF_KILL_LOG).findOne(new BasicDBObject("_id", uid + "-" + playerCount));
		if (null == dbo) {
			return null;
		}
		return DboUtil.toBean(dbo, WerewolfKillLog.class);
	}

	/** 记录游戏日志 */
	public void saveWerewolfKillLog(long roomId, WerewolfKillActorState ws, WerewolfKill wk, boolean win) {
		long uid = ws.getUid();
		int identity = ws.getIdentity();
		int playerCount = WolfSetup.map.get(wk.getType());
		int count = getC(DocName.WEREWOLF_KILL_LOG).find(new BasicDBObject("_id", uid + "-" + playerCount)).count();
		if (count > 0) {
			String field = "";
			String winField;
			switch (identity) {
			case WerewolfKillActor.WOLF:
				field = "wolf";
				break;
			case WerewolfKillActor.VILLAGE:
				field = "village";
				break;
			case WerewolfKillActor.PROPHET:
				field = "prophet";
				break;
			case WerewolfKillActor.WITCH:
				field = "witch";
				break;
			case WerewolfKillActor.HUNTER:
				field = "hunter";
				break;
			case WerewolfKillActor.SB:
				field = "sb";
				break;
			case WerewolfKillActor.DEFENDER:
				field = "defender";
				break;
			}
			BasicDBObject inc = new BasicDBObject(field, 1).append("allCnt", 1);
			if (win) {
				winField = field + "Win";
				inc.append(winField, 1).append("winCnt", 1);
			}
			BasicDBObject u = new BasicDBObject("$inc", inc).append("$set",
					new BasicDBObject("updateTime", System.currentTimeMillis()));
			if (super.getRoomActor(roomId, uid, WerewolfKillActor.class).getStatus() == Actor.STATUS_OFFLINE) {
				inc.append("offlineCount", 1);
			}
			DBObject dbo = super.getC(DocName.WEREWOLF_KILL_LOG).findAndModify(
					new BasicDBObject("_id", uid + "-" + playerCount), null, null, false, u, true, false);
			if (DboUtil.getInt(dbo, "allCnt") < 2) {// 老数据
				WerewolfKillLog wl = DboUtil.toBean(dbo, WerewolfKillLog.class);
				wl.setAllCnt(wl.getAllCount());
				wl.setWinCnt(wl.getAllWinCount());
				super.getMongoTemplate().save(wl);
			}
		} else {
			WerewolfKillLog wl = new WerewolfKillLog(uid, playerCount, win, identity);
			super.getMongoTemplate().save(wl);
		}
		WerewolfKillLogDetail wd = new WerewolfKillLogDetail(super.getNextId(DocName.WEREWOLF_KILL_LOG_DETAIL), uid,
				playerCount, win);
		super.getMongoTemplate().save(wd);
	}

	/** 查询胜率 */
	public ReMsg queryWinRate(long uid, int userCount) {
		DBObject dbo = getC(DocName.WEREWOLF_KILL_LOG).find(new BasicDBObject("_id", uid + "-" + userCount)).one();
		Map<String, String> map = new HashMap<>();
		if (null == dbo) {
			map.put("count", "0");
			map.put("rate", "0%");
		} else {
			WerewolfKillLog wl = DboUtil.toBean(dbo, WerewolfKillLog.class);
			map.put("count", Integer.toString(wl.getAllCount()));
			map.put("rate", (wl.getAllRate() + "%"));
		}
		return new ReMsg(ReCode.OK, map);
	}

	/** 查询总胜率 */
	public ReMsg queryWholeWinRate(long uid) {
		List<DBObject> list = getC(DocName.WEREWOLF_KILL_LOG).find(new BasicDBObject("uid", uid)).toArray();
		Map<String, String> map = new HashMap<>();
		if (list.isEmpty()) {
			map.put("count", "0");
			map.put("rate", "0%");
		} else {
			int allCount = 0;
			int winCount = 0;
			for (DBObject dbo : list) {
				WerewolfKillLog wl = DboUtil.toBean(dbo, WerewolfKillLog.class);
				allCount += wl.getAllCount();
				winCount += wl.getAllWinCount();
			}
			int rate = allCount == 0 ? 0 : (winCount * 100 / allCount);
			map.put("count", Integer.toString(allCount));
			map.put("rate", rate + "%");
		}
		return new ReMsg(ReCode.OK, map);
	}

	/** 查询总胜场 */
	public int queryWholeWinCount(long uid) {
		List<DBObject> list = getC(DocName.WEREWOLF_KILL_LOG).find(new BasicDBObject("uid", uid)).toArray();
		int winCount = 0;
		if (!list.isEmpty()) {
			for (DBObject dbo : list) {
				WerewolfKillLog wl = DboUtil.toBean(dbo, WerewolfKillLog.class);
				winCount += wl.getAllWinCount();
			}
		}
		return winCount;
	}

	/** 查询胜率详情 */
	public ReMsg queryWinRateDetail(long uid, int userCount) {
		DBObject dbo = getC(DocName.WEREWOLF_KILL_LOG).find(new BasicDBObject("_id", uid + "-" + userCount)).one();
		return new ReMsg(ReCode.OK, dbo);
	}

	/** 重新开始 */
	public void restart(Room r, WerewolfKill wk) {
		Set<WerewolfKillActor> actors = getRoomActors(r.get_id(), WerewolfKillActor.class);
		Set<WerewolfKillActorState> actorsState = getRoomActorsState(r.get_id(), WerewolfKillActorState.class);
		wk.setStatus(Activity.STATUS_READY);
		wk.setWinType(0);
		int type = wk.getType();
		long owner = wk.getOwner();
		int playerCount = WolfSetup.map.get(type);
		super.delActivity(r.get_id());
		super.delRoomAllActorsState(r.get_id());
		wk = super.getActivity(r.get_id(), WerewolfKill.class);
		wk.getActors().clear();
		wk.setType(type);
		Set<WerewolfKillActor> initActors = new TreeSet<>();
		int robitCount = 0;
		Set<Integer> usedSeat = new TreeSet<>();
		long[] seatTable = new long[playerCount];
		for (WerewolfKillActor actor : actors) {
			if (Actor.STATUS_ONLINE != actor.getStatus()) {
				super.delRoomActor(r.get_id(), actor.getUid());
				if (actor.getUid() == owner) {
					WerewolfKillActor newOua = getNewOwner(actors);
					if (newOua != null) {
						owner = newOua.getUid();
					}
				}
				delGameLock(actor.getUid());
			} else {
				actor.setStatus(WerewolfKillActor.STATUS_ONLINE);
				actor.setUcStatus(WerewolfKillActor.A_STATUS_READY);
				initActors.add(actor);
				super.saveRoomActor(r.get_id(), actor);
				if (actor.isRobit()) {
					robitCount++;
				}
				if (actor.getRole() == Actor.ROLE_EXECUTOR) {
					usedSeat.add(actor.getSeat());
					wk.putActor(actor.getUid(), WerewolfKillActor.A_STATUS_START);
					int seat = actor.getSeat();
					seatTable[seat] = actor.getUid();
				}
			}
		}
		wk.setSeatUserTable(seatTable);
		for (WerewolfKillActor wa : initActors) {
			if (wa.getUid() == owner) {
				wa.setOwner(true);
				super.saveRoomActor(r.get_id(), wa);
			}
		}
		int seatSize = usedSeat.size();
		if (seatSize < playerCount) {
			List<String> list = new ArrayList<>();
			for (int i = 0; i < playerCount; i++) {
				if (!usedSeat.contains(i)) {
					list.add(Integer.toString(i));
				}
			}
			String[] seats = {};
			seats = list.toArray(seats);
			getRedisTemplate().opsForList().leftPushAll(RK.seatList(r.get_id()), seats);
		}
		r.setStatus(Room.OPEN);
		r.setCount(initActors.size());
		r.setRobitCount(robitCount);
		super.updateRoom(r, T_RESTART);
		wk.setOwner(owner);
		super.saveActivity(r.get_id(), wk);
		MapBody mb = new MapBody(SocketHandler.ROOM_RESTART, SocketHandler.FN_ROOM_ID, r.get_id())
				.append("actors", initActors).append("actorsState", actorsState);
		long dt = System.currentTimeMillis();
		for (Actor actor : initActors) {
			super.pubRoomUserMsg(r.get_id(), actor.getUid(), MsgType.ROOM, mb, dt);
		}
	}

	public ReMsg synchronizeDate(long uid, long roomId) {
		return synchronizeDate(uid, super.getRoom(roomId), false);
	}

	private ReMsg synchronizeDate(long uid, Room r, boolean bot) {
		if (r == null) {
			return new ReMsg(ReCode.FAIL);
		}
		DBObject dbo = super.getC(DocName.ACTIVITYFORBIDDENLOG).findOne(new BasicDBObject("_id", uid));
		if (null != dbo) {
			long endForbiddenTime = DboUtil.getLong(dbo, "end");
			long lastTime = endForbiddenTime - System.currentTimeMillis();
			if (lastTime > 0) {
				return new ReMsg(ReCode.IN_FORBIDDEN, DateUtil.getNomalString(lastTime));
			}
		}
		if (!checkGameLock(uid, r.get_id())) {
			String oldRoomId = getGameLock(uid);
			Room room = super.getRoom(Long.parseLong(oldRoomId));
			WerewolfRoomInfo ri = new WerewolfRoomInfo(room.getType());
			ri.setCode(ReCode.INGAME.reCode());
			ri.setWord(getGameLock(uid));
			return new ReMsg(ReCode.OK, ri);
		} else {
			delGameLock(uid);
		}
		WerewolfRoomInfo ri = new WerewolfRoomInfo(r.getType());
		long roomId = r.get_id();
		boolean hasKey = getRedisTemplate().hasKey(RK.roomUser(roomId));
		WerewolfKill wk = super.getActivity(roomId, WerewolfKill.class);
		checkReadyRoom(roomId, wk, true);
		if (!hasKey) {
			int type = r.getType();
			wk.setType(type);
			int playerCount = WolfSetup.map.get(type);
			wk.setSeatUserTable(new long[playerCount]);
			String[] seats = new String[playerCount];
			List<String> list = new ArrayList<>();
			for (int i = 0; i < playerCount; i++) {
				list.add(Integer.toString(i));
			}
			Collections.shuffle(list);
			for (int i = 0; i < playerCount; i++) {
				seats[i] = list.get(i);
			}
			if (getRedisTemplate().opsForList().size(RK.seatList(roomId)) == 0) {
				getRedisTemplate().opsForList().leftPushAll(RK.seatList(roomId), seats);
			}
		}
		WerewolfKillActor wa = super.getRoomActor(roomId, uid, WerewolfKillActor.class);
		if (null != wa) {
			int seat = wa.getSeat();
			if (seat != -1) {
				long[] seatTable = wk.getSeatUserTable();
				if (seatTable[seat] != 0 && seatTable[seat] != wa.getUid()) {
					wa.setRole(Actor.ROLE_VIEWER);
					wa.setSeat(-1);
				}
			}
			wa.setStatus(Actor.STATUS_ONLINE);
		} else {
			DBObject user = userService.findById(uid);
			if (user == null) {
				ri.setCode(ReCode.FAIL.reCode());
				return new ReMsg(ReCode.FAIL, ri);
			}
			wa = new WerewolfKillActor();
			if (null != r.getBuyUid() && uid == r.getBuyUid()) {// 房间购买人设置最高权限
				wa.setBuyUser(true);
			}
			wa.setUid(DboUtil.getLong(user, "_id"));
			wa.setAvatar(DboUtil.getString(user, "avatar"));
			wa.setRobit(bot);
			wa.setPoint(DboUtil.getInt(user, "point"));
			wa.setSex(DboUtil.getInt(user, "sex"));
			wa.setNickname(DboUtil.getString(user, "nickname"));
			wa.setProvince(DboUtil.getString(user, "province") == null ? "火星" : DboUtil.getString(user, "province"));
			if (wa.getProvince().contains("市")) {
				wa.setCity("");
			} else {
				wa.setCity(DboUtil.getString(user, "city") == null ? "" : DboUtil.getString(user, "city"));
			}
			if (Room.ACTIVITY == r.getStatus()) {
				wa.setRole(Actor.ROLE_VIEWER);
			} else {
				wa.setGame("欢乐狼人杀");
				long[] seatTable = wk.getSeatUserTable();
				String seat = super.getRedisTemplate().opsForList().rightPop(RK.seatList(roomId));
				if (null == seat) {
					wa.setRole(Actor.ROLE_VIEWER);
				} else {
					int seatNo = Integer.parseInt(seat);
					if (seatTable[seatNo] != 0 && seatTable[seatNo] != wa.getUid()) {
						wa.setRole(Actor.ROLE_VIEWER);
						wa.setSeat(-1);
					} else {
						seatTable[seatNo] = uid;
						wk.setSeatUserTable(seatTable);
						wk.putActor(uid, WerewolfKillActor.A_STATUS_START);
						wa.setSeat(seatNo);
						wa.setUcStatus(Actor.A_STATUS_READY);
					}
				}
				if (!hasKey) {
					wk.setOwner(uid);
					wa.setOwner(true);
				}
			}
		}
		if (wa.isBuyUser() && (null == wk.getOwner() || wk.getOwner() != wa.getUid())) {// 房主不是房管
			if (null != wk.getOwner()) {// 原房管不为空
				WerewolfKillActor actor = super.getRoomActor(r.get_id(), wk.getOwner(), WerewolfKillActor.class);
				if (null != actor) {
					actor.setOwner(false);
					super.saveRoomActor(r.get_id(), actor);
				}
			}
			wa.setOwner(true);
			wk.setOwner(uid);
			saveActivity(r.get_id(), wk);
		}
		super.saveRoomActor(r.get_id(), wa);
		Set<WerewolfKillActor> as = super.getRoomActors(roomId, WerewolfKillActor.class);
		// if (!bot) {
		// for (WerewolfKillActor e : as) {
		// if (e.isRobit()) {
		// outRoom(e.getUid(), r);
		// as.remove(e);
		// break;
		// }
		// }
		// }
		super.saveActivity(roomId, wk);
		ri.setSpeaker(wk.getSpeaker());
		ri.setPub(r.isPub());
		ri.setSys(r.isSys());// 系统房间或包间
		ri.setHasOwner(true);
		if (r.getStatus() == Room.OPEN) {
			ri.setStatus(Room.OPEN);
		} else {
			ri.setStatus(Room.ACTIVITY);
		}
		super.changeRoomCount(r, as, T_INROOM);
		ri.setDay(wk.getDay());
		ri.setWkStatus(wk.getStatus());
		if (wk.getStatus() != Activity.STATUS_READY && wk.getStatus() != WerewolfKillActor.STATUS_SEND_ROLE) {
			Set<WerewolfKillActorState> actorsStates = super.getRoomActorsState(roomId, WerewolfKillActorState.class);
			Map<Long, Integer> idenMap = new HashMap<>();
			if (null != actorsStates && !actorsStates.isEmpty()) {
				List<Long> wolves = wk.getWolves();
				Integer identity = 0;
				for (WerewolfKillActorState actorsState : actorsStates) {
					long id = actorsState.getUid();
					Integer iden = actorsState.getIdentity();
					idenMap.put(id, iden);
					if (id == uid) {
						identity = iden;
					}
					if (!(wolves.contains(uid) && wolves.contains(id)) && uid != id) {
						if (iden == WerewolfKillActor.HUNTER && actorsState.getDeath()) {
							// 猎人死了
						} else if (iden == WerewolfKillActor.SB && wk.isSbBeVoted()) {
							// 白痴翻牌
						} else {
							actorsState.setIdentity(null);
						}
					}
					if (null != actorsState.getDeathType()
							&& actorsState.getDeathType() == WerewolfKillActor.WITCH_KILL) {
						actorsState.setDeathType(WerewolfKillActor.WOLF_KILL);
					}
				}
				if (identity == WerewolfKillActor.WOLF) {
					Map<Long, Long> killMap = wk.getKillMap();
					if (!killMap.isEmpty()) {
						ri.setKillMap(killMap);
					}
				} else if (identity == WerewolfKillActor.PROPHET) {
					Map<Integer, Long> checkMap = wk.getCheckMap();
					Long todayCheck = checkMap.get(wk.getDay());
					Map<Long, Boolean> checkLogMap = new HashMap<>();
					if (!checkMap.isEmpty()) {
						java.util.Iterator<Long> it = checkMap.values().iterator();
						while (it.hasNext()) {
							Long id = it.next();
							for (Long e : idenMap.keySet()) {
								if (e - id == 0) {
									checkLogMap.put(id, idenMap.get(id) > WerewolfKillActor.WOLF);
								}
							}
						}
						ri.setCheckMap(checkLogMap);
						ri.setTodayCheck(todayCheck);
					}
				} else if (identity == WerewolfKillActor.WITCH) {
					Map<Long, Integer> poisonMap = wk.getPoisonMap();
					Map<Long, Integer> saveMap = wk.getSaveMap();
					if (!poisonMap.isEmpty()) {
						ri.setPoisonSet(poisonMap.keySet());
					}
					if (!saveMap.isEmpty()) {
						ri.setSaveSet(saveMap.keySet());
					}
					ri.setDead(wk.getDead());
				} else if (identity == WerewolfKillActor.DEFENDER) {
					Map<Integer, Long> defendMap = wk.getDefendMap();
					if (!defendMap.isEmpty()) {
						Long defendId = defendMap.get(wk.getDay() + 1);
						ri.setDefendId(defendId);
						Long lastDefendId = defendMap.get(wk.getDay());
						ri.setLastDefendId(lastDefendId);
					}
				} else if (identity == WerewolfKillActor.SB) {
					ri.setSbBeVoted(wk.isSbBeVoted());
				}
				ri.setActorsStates(actorsStates);
			}
		}
		MapBody mb = new MapBody(SocketHandler.IN_ROOM, SocketHandler.FN_USER_ID, wa.getUid())
				.append("nickname", wa.getNickname()).append("sex", wa.getSex()).append("avatar", wa.getAvatar())
				.append("actors", as).append("province", wa.getProvince()).append("city", wa.getCity())
				.append("role", wa.getRole());
		long dt = System.currentTimeMillis();
		for (Actor a : as) {
			if (uid != a.getUid()) {
				super.pubRoomUserMsg(r.get_id(), a.getUid(), MsgType.ROOM, mb, dt);
			}
		}
		ri.setActors(as);
		ri.setIdentities(wk.getIdentities());
		ri.setActId(wk.get_id());
		return new ReMsg(ReCode.OK, ri);
	}

	@Override
	public ReMsg outRoom(long uid, Room r) {
		log.error("主动退出了md" + uid + "roomId" + r.get_id());
		return outRoom(uid, r, SocketHandler.OUT_ROOM);
	}

	@Override
	public ReMsg disconnectRoom(long uid, Room r) {
		log.error("掉线了md");
		return outRoom(uid, r, SocketHandler.DISCONNECT);
	}

	@Override
	public ReMsg kick(long uid, Room r) {
		log.error("被踢了md");
		return outRoom(uid, r, SocketHandler.KICK);
	}

	public ReMsg outRoom(long uid, Room r, int type) {
		if (!lock(RK.outRoomLock(r.get_id()), 1)) {
			return new ReMsg(ReCode.START_YET);
		} else {
			if (Room.ACTIVITY == r.getStatus() && SocketHandler.KICK == type) {
				unlock(RK.outRoomLock(r.get_id()));
				return new ReMsg(ReCode.KICK_ERROR);
			}
			long roomId = r.get_id();
			WerewolfKill wk = super.getActivity(roomId, WerewolfKill.class);
			WerewolfKillActor actor = super.getRoomActor(roomId, uid, WerewolfKillActor.class);
			boolean ucChange = false;
			boolean force = false;
			if (null == actor) {
				unlock(RK.outRoomLock(r.get_id()));
				return new ReMsg(ReCode.ACTOR_ERROR);
			} else {
				if (Room.OPEN == r.getStatus() && Actor.ROLE_EXECUTOR == actor.getRole()) {
					checkReadyRoom(roomId, wk, false);
					if (WerewolfKillActor.A_STATUS_READY == actor.getUcStatus()
							|| WerewolfKillActor.A_STATUS_IN_ROOM == actor.getUcStatus()) {
						if (SocketHandler.KICK == type) {
							if (actor.isBuyUser()) {// 房主不能被踢
								return new ReMsg(ReCode.FAIL);
							}
							try {
								MapBody mb = new MapBody(type)
										.append("nickname",
												super.getRoomActor(r.get_id(),
														getActivity(r.get_id(), WerewolfKill.class).getOwner())
																.getNickname());
								super.pubUserMsg(uid, MsgType.PROMPT, mb);
							} catch (Exception e) {
								log.error("发送踢人信息", e);
							}
						}
					}
					if (WerewolfKillActor.A_STATUS_READY == actor.getUcStatus()) {
						wk.removeActor(uid);
						ucChange = true;
					}
					this.delRoomActor(roomId, uid);
					Set<WerewolfKillActor> as = super.getRoomActors(roomId, WerewolfKillActor.class);
					super.changeRoomCount(r, as, T_OUTROOM);
					String seat = "-1";
					long[] seatTable = wk.getSeatUserTable();
					for (int i = 0; i < seatTable.length; i++) {
						if (seatTable[i] == uid) {
							seatTable[i] = 0;
							seat = Integer.toString(i);
						}
					}
					wk.setSeatUserTable(seatTable);
					if (!seat.equals("-1")) {
						super.getRedisTemplate().opsForList().leftPush(RK.seatList(roomId), seat);
					}
					super.saveActivity(roomId, wk);
					ucChange = false;
					Long owner = wk.getOwner();
					if (null != owner && uid == owner) {
						WerewolfKillActor newOwn = this.getNewOwner(as);
						pubOwnerChange(uid, newOwn, r, wk);
					}
				}
				if (Actor.ROLE_VIEWER == actor.getRole()) {
					this.delRoomActor(roomId, actor.getUid());
					Set<WerewolfKillActor> as = super.getRoomActors(r.get_id(), WerewolfKillActor.class);
					super.changeRoomCount(r, as, T_OUTROOM);
				} else if (Room.ACTIVITY == r.getStatus() && Actor.ROLE_EXECUTOR == actor.getRole()) {
					if (SocketHandler.DISCONNECT != type) {
						actor.setStatus(Actor.STATUS_OUT_ROOM);
					} else {
						actor.setStatus(Actor.STATUS_OFFLINE);
					}
					this.saveRoomActor(roomId, actor);
					WerewolfKillActorState ws = super.getRoomActorState(roomId, uid, WerewolfKillActorState.class);
					if (null != ws && !ws.getDeath()) {
						force = true;
					}
				}
				if (force) {
					addUserGameLock(uid, roomId);
				}
				MapBody mb = new MapBody(type, SocketHandler.FN_USER_ID, actor.getUid())
						.append("nickname", actor.getNickname()).append(SocketHandler.FN_ROOM_ID, roomId)
						.append("role", actor.getRole());
				this.pubRoomMsg(roomId, MsgType.ROOM, mb);
				if (ucChange) {
					super.saveActivity(r.get_id(), wk);
				}
				int outCount = 0;
				Set<WerewolfKillActor> actors = super.getRoomActors(r.get_id(), WerewolfKillActor.class);
				for (WerewolfKillActor ua : actors) {
					if (ua.getStatus() != WerewolfKillActor.STATUS_ONLINE || ua.isRobit()) {
						outCount++;
					}
				}
				if (outCount == actors.size()) {
					this.closeRoom(r.get_id());
				}
			}
			unlock(RK.outRoomLock(r.get_id()));
		}
		return new ReMsg(ReCode.OK);
	}

	private void addUserGameLock(long uid, long roomId) {
		super.getRedisTemplate().opsForValue().set(RK.roomUserGameLock(uid), Long.toString(roomId));
		super.getRedisTemplate().expire(RK.roomUserGameLock(uid), 5, TimeUnit.MINUTES);
	}

	private void delGameLock(long uid) {
		super.getRedisTemplate().delete(RK.roomUserGameLock(uid));
	}

	private boolean checkGameLock(long uid, long roomId) {
		String oldRoomId = getGameLock(uid);
		return null == oldRoomId || oldRoomId.equals(Long.toString(roomId));
	}

	private String getGameLock(long uid) {
		return super.getRedisTemplate().opsForValue().get(RK.roomUserGameLock(uid));
	}

	@Override
	public void closeRoom(final long roomId) {
		Set<Actor> set = getRoomActors(roomId);
		for (Actor actor : set) {
			delGameLock(actor.getUid());
		}
		getRedisTemplate().delete(RK.seatList(roomId));
		delRoomAllActorsState(roomId);
		super.closeRoom(roomId);
	}

	@Override
	public ReMsg getActorStatus(long uid, Room r) {
		WerewolfKillActor uca = super.getRoomActor(r.get_id(), uid, WerewolfKillActor.class);
		if (uca == null) {
			return new ReMsg(ReCode.OK, 1);
		} else {
			return new ReMsg(ReCode.OK, 2);
		}
	}

	@Override
	public ReMsg canInRoom(int type, long actId, long roomId) {
		WerewolfKill uc = super.getActivity(roomId, WerewolfKill.class);
		if (actId == uc.get_id()) {
			if (uc.getStatus() > Activity.STATUS_READY)
				return new ReMsg(ReCode.OK, 1);
		}
		return new ReMsg(ReCode.OK, 2);
	}

	@Override
	public ReMsg inRoom(long uid, Room r, int model, boolean robit) {
		return synchronizeDate(uid, r, robit);
	}

	private WerewolfKillActor getNewOwner(Set<WerewolfKillActor> as) {
		for (WerewolfKillActor a : as) {
			if (WerewolfKillActor.STATUS_ONLINE == a.getStatus() && Actor.ROLE_EXECUTOR == a.getRole()) {
				return a;
			}
		}
		return null;
	}

	private WerewolfKillActor getNextOwner(Set<WerewolfKillActor> as, Long owner) {
		if (null != owner) {
			List<WerewolfKillActor> list = as.stream().collect(Collectors.toList());
			Collections.shuffle(list);
			for (WerewolfKillActor a : list) {
				if (a.getUid() != owner && WerewolfKillActor.STATUS_ONLINE == a.getStatus()
						&& Actor.ROLE_EXECUTOR == a.getRole()) {
					return a;
				}
			}
		}
		return null;
	}

	private void pubOwnerChange(Long uid, WerewolfKillActor a, Room r, WerewolfKill wk) {
		if (uid != null) {
			WerewolfKillActor wa = super.getRoomActor(r.get_id(), uid, WerewolfKillActor.class);
			if (null != wa) {
				wa.setOwner(false);
				super.saveRoomActor(r.get_id(), wa);
			}
			if (null == a) {
				wk.setOwner(null);
			} else {
				wk.setOwner(a.getUid());
				a.setOwner(true);
				super.saveRoomActor(r.get_id(), a);
			}
			super.saveActivity(r.get_id(), wk);
			MapBody mb = new MapBody(SocketHandler.CHANGE_OWNER).append("lastOwner", uid).append("curOwner",
					wk.getOwner());
			super.pubRoomMsg(r.get_id(), MsgType.ROOM, mb);
		}
	}

	/** 检查游戏是否结束 */
	public boolean checkEnd(Room room, WerewolfKill wk, Set<WerewolfKillActorState> set) {
		long roomId = room.get_id();
		List<Long> wolves = new ArrayList<>();
		List<Long> villages = new ArrayList<>();
		List<Long> gods = new ArrayList<>();
		for (WerewolfKillActorState ws : set) {
			if (!ws.getDeath()) {
				if (ws.getIdentity() == WerewolfKillActor.WOLF) {
					if (wk.getDay() >= 5) {
						WerewolfKillActor actor = getRoomActor(roomId, ws.getUid(), WerewolfKillActor.class);
						if (actor.getStatus() == WerewolfKillActor.STATUS_ONLINE) {
							wolves.add(ws.getUid());
						}
					} else {
						wolves.add(ws.getUid());
					}
				} else if (ws.getIdentity() == WerewolfKillActor.VILLAGE) {
					if (wk.getDay() >= 5) {
						WerewolfKillActor actor = getRoomActor(roomId, ws.getUid(), WerewolfKillActor.class);
						if (actor.getStatus() == WerewolfKillActor.STATUS_ONLINE) {
							villages.add(ws.getUid());
						}
					} else {
						villages.add(ws.getUid());
					}
				} else if (ws.getIdentity() > WerewolfKillActor.VILLAGE) {
					if (wk.getDay() >= 5) {
						WerewolfKillActor actor = getRoomActor(roomId, ws.getUid(), WerewolfKillActor.class);
						if (actor.getStatus() == WerewolfKillActor.STATUS_ONLINE) {
							gods.add(ws.getUid());
						}
					} else {
						gods.add(ws.getUid());
					}
				}
			}
		}
		int endType = 0;
		if (villages.isEmpty() || gods.isEmpty()) {
			endType = WerewolfKillActor.WOLFWIN;
		} else if (wolves.isEmpty()) {
			endType = WerewolfKillActor.GOODWIN;
		}
		if (endType > 0) {
			wk.setWinType(endType);
			wk.setStatus(WerewolfKillActor.STATUS_END);
			super.saveActivity(roomId, wk);
			super.addJob(roomId, WerewolfKillActor.END_TIME);
			return true;
		}
		return false;
	}

	/** 查座位 */
	public int indexOf(long uid, long[] seat) {
		for (int i = 0; i < seat.length; i++)
			if (uid == seat[i])
				return i;
		return -1;
	}

	/** 聊天 */
	public ReMsg groupChat(Long uid, String fmt, String txt, Long roomId) {
		Set<WerewolfKillActorState> set = super.getRoomActorsState(roomId, WerewolfKillActorState.class);
		Set<Long> servivals = set.stream().filter(e -> !e.getDeath() || e.getDeath() && e.isDeadLater())
				.map(e -> e.getUid()).collect(Collectors.toSet());
		MapBody mb = new MapBody().append(SocketHandler.FN_USER_ID, uid).append("fmt", fmt).append("txt", txt)
				.append("roomId", roomId).append("chatTime", System.currentTimeMillis());
		WerewolfKill wk = super.getActivity(roomId, WerewolfKill.class);
		long curTime = System.currentTimeMillis();
		Set<WerewolfKillActor> ws = super.getRoomActors(roomId, WerewolfKillActor.class);
		if (wk.getStatus() > WerewolfKill.STATUS_READY) {
			if (!servivals.contains(uid)
					|| super.getRoomActor(roomId, uid, WerewolfKillActor.class).getRole() == Actor.ROLE_VIEWER) {
				if (wk.getStatus() == WerewolfKillActor.STATUS_LAST_WORDS) {
					Long lastWordUser = wk.getLastWordUser();
					if (null != lastWordUser && lastWordUser - uid == 0) {
						mb.append(SocketHandler.HANDLER_NAME, SocketHandler.DAY_CHAT);
						for (WerewolfKillActor wa : ws) {
							super.pubRoomUserMsg(roomId, wa.getUid(), getMsgType(wk), mb, curTime);
						}
					}
				} else {
					mb.append(SocketHandler.HANDLER_NAME, SocketHandler.VIEW_CHAT);
					for (WerewolfKillActor wa : ws) {
						if (!servivals.contains(wa.getUid())
								|| super.getRoomActor(roomId, wa.getUid(), WerewolfKillActor.class)
										.getRole() == Actor.ROLE_VIEWER) {
							super.pubRoomUserMsg(roomId, wa.getUid(), getMsgType(wk), mb, curTime);
						}
					}
				}
			} else if ((wk.getStatus() == WerewolfKillActor.STATUS_KILL_CHECK
					|| wk.getStatus() == WerewolfKillActor.STATUS_SAVE_POISON) && wk.getWolves().contains(uid)) {
				mb.append(SocketHandler.HANDLER_NAME, SocketHandler.NIGHT_CHAT);
				for (Long wolf : wk.getWolves()) {
					super.pubRoomUserMsg(roomId, wolf, getMsgType(wk), mb, curTime);
				}
			} else if (wk.getStatus() != WerewolfKillActor.STATUS_KILL_CHECK) {
				mb.append(SocketHandler.HANDLER_NAME, SocketHandler.DAY_CHAT);
				for (WerewolfKillActor wa : ws) {
					super.pubRoomUserMsg(roomId, wa.getUid(), getMsgType(wk), mb, curTime);
				}
			}
		} else {
			mb.append(SocketHandler.HANDLER_NAME, SocketHandler.DAY_CHAT);
			for (WerewolfKillActor wa : ws) {
				super.pubRoomUserMsg(roomId, wa.getUid(), getMsgType(wk), mb, curTime);
			}
		}
		return new ReMsg(ReCode.OK);
	}

	private enum WolfSetup {
		TWELVE(136, 12), SIX(137, 6), NINE(138, 9);
		WolfSetup(int type, int count) {
			this.type = type;
			this.count = count;
		}

		private int type;
		private int count;

		public int getType() {
			return type;
		}

		public int getCount() {
			return count;
		}

		static final Map<Integer, Integer> map = new HashMap<>();
		static {
			for (WolfSetup e : EnumSet.allOf(WolfSetup.class)) {
				map.put(e.getType(), e.getCount());
			}
		}
	}

	/** 初始化角色 */
	private IdentitySetupVO getSetup(int count) {
		IdentitySetupVO vo = new IdentitySetupVO();
		int rindex = RandomUtil.nextInt(101);
		switch (count) {
		case 6:
			vo.setWolvesCount(2);
			vo.setProphetCount(1);
			vo.setVillageCount(2);
			vo.setHunterCount(1);
			break;
		case 7:
			vo.setWolvesCount(2);
			vo.setVillageCount(3);
			vo.setProphetCount(1);
			if (rindex > 75) {
				vo.setHunterCount(1);
			} else if (rindex > 50) {
				vo.setDefenderCount(1);
			} else if (rindex > 25) {
				vo.setSbCount(1);
			} else {
				vo.setWitchCount(1);
			}
			break;
		case 8:
			if (rindex > 50) {
				vo.setWolvesCount(3);
				vo.setVillageCount(3);
			} else {
				vo.setWolvesCount(2);
				vo.setVillageCount(4);
			}
			vo.setProphetCount(1);
			if (rindex > 75) {
				vo.setHunterCount(1);
			} else if (rindex > 50) {
				vo.setDefenderCount(1);
			} else if (rindex > 25) {
				vo.setSbCount(1);
			} else {
				vo.setWitchCount(1);
			}
			break;
		case 9:
			vo.setWolvesCount(3);
			vo.setProphetCount(1);
			vo.setWitchCount(1);
			vo.setVillageCount(3);
			if (rindex > 67) {
				vo.setDefenderCount(1);
			} else if (rindex > 34) {
				vo.setHunterCount(1);
			} else {
				vo.setSbCount(1);
			}
			break;
		case 10:
			vo.setWolvesCount(3);
			vo.setProphetCount(1);
			vo.setWitchCount(1);
			vo.setVillageCount(4);
			if (rindex > 67) {
				vo.setDefenderCount(1);
			} else if (rindex > 34) {
				vo.setHunterCount(1);
			} else {
				vo.setSbCount(1);
			}
			break;
		case 11:
			vo.setWolvesCount(3);
			vo.setProphetCount(1);
			vo.setWitchCount(1);
			vo.setVillageCount(4);
			vo.setHunterCount(1);
			if (rindex > 50) {
				vo.setDefenderCount(1);
			} else {
				vo.setSbCount(1);
			}
			break;
		case 12:
			if (rindex > 50) {
				vo.setWolvesCount(3);
				vo.setProphetCount(1);
				vo.setWitchCount(1);
				vo.setVillageCount(5);
				vo.setHunterCount(1);
				if (rindex > 75) {
					vo.setDefenderCount(1);
				} else {
					vo.setSbCount(1);
				}
			} else {
				vo.setWolvesCount(4);
				vo.setProphetCount(1);
				vo.setWitchCount(1);
				vo.setVillageCount(4);
				vo.setHunterCount(1);
				if (rindex > 25) {
					vo.setDefenderCount(1);
				} else {
					vo.setSbCount(1);
				}
			}
			break;
		}
		return vo;
	}

	private int getPrice(int iden) {
		int ret = 0;
		if (WerewolfKillActor.BUYTYPE == GoodsItem.Money.COIN.getV().getId()) {
			if (iden == WerewolfKillActor.WOLF) {
				ret = 200;
			} else if (iden == WerewolfKillActor.VILLAGE) {
				ret = 50;
			} else if (iden > WerewolfKillActor.VILLAGE) {
				ret = 100;
			}
		} else {
		}
		return ret;
	}

	/** 后台根据胜率排名 */
	public Page<DBObject> adminSt(Long uid, Integer actorCount, Integer rateType, Integer sort, Integer page,
			Integer size) {
		size = getSize(size);
		page = getPage(page);
		if (null == actorCount) {// 6人场
			actorCount = 6;
		}
		if (null == sort) {// 总场次
			sort = 0;
		}
		BasicDBObject q = new BasicDBObject("actorCount", actorCount);// 几人场
		if (null != uid) {
			q.append("_id", uid + "-" + actorCount);
		}
		String field = null;// 默认按照总场次排序
		if (null != rateType && rateType != 0) {
			switch (rateType) {
			case WerewolfKillActor.WOLF:
				field = "wolf";
				break;
			case WerewolfKillActor.VILLAGE:
				field = "village";
				break;
			case WerewolfKillActor.PROPHET:
				field = "prophet";
				break;
			case WerewolfKillActor.WITCH:
				field = "witch";
				break;
			case WerewolfKillActor.HUNTER:
				field = "hunter";
				break;
			case WerewolfKillActor.SB:
				field = "sb";
				break;
			case WerewolfKillActor.DEFENDER:
				field = "defender";
				break;
			}
		}
		// if (StringUtils.isNotBlank(field) && null == uid) {
		// q.put(field, new BasicDBObject("$gt", 0));
		// }
		if (null != field) {
			if (sort == 1) {// 胜利场次
				field = field + "Win";
			}
		} else {
			if (sort == 1) {// 胜利场次
				field = "winCnt";
			} else {
				field = "allCnt";
			}
		}
		List<DBObject> dbos = getC(DocName.WEREWOLF_KILL_LOG).find(q).skip(super.getStart(page, size))
				.limit(getSize(size)).sort(new BasicDBObject(field, -1)).toArray();
		int count = getC(DocName.WEREWOLF_KILL_LOG).find(q).count();
		return new Page<DBObject>(count, size, page, dbos);
	}

	/** 后台根据胜场排名 */
	public Page<DBObject> adminDetailSt(Long uid, Integer actorCount, Integer page, Integer size, Long startDate,
			Long endDate) {
		size = getSize(size);
		page = getPage(page);
		BasicDBObject q = new BasicDBObject();
		if (null != actorCount) {
			q.append("actorCount", actorCount);
		}
		if (null != uid) {
			q.append("uid", uid);
		}
		if (startDate != null && startDate != 0 && endDate != null && endDate != 0) {
			q.put("updateTime", new BasicDBObject("$gte", startDate).append("$lt", endDate));
		} else if (startDate != null && startDate != 0) {
			q.put("updateTime", new BasicDBObject("$gte", startDate));
		} else if (endDate != null && endDate != 0) {
			q.put("updateTime", new BasicDBObject("$lt", endDate));
		}
		q.put("win", true);
		BasicDBObject match = new BasicDBObject("$match", q);
		BasicDBObject group = new BasicDBObject("$group",
				new BasicDBObject("_id", "$uid").append("num_tutorial", new BasicDBObject("$sum", 1)));
		BasicDBObject limit = new BasicDBObject("$limit", getSize(size));
		BasicDBObject skip = new BasicDBObject("$skip", super.getStart(page, size));
		List<DBObject> dbos = new ArrayList<>();
		dbos.add(match);
		dbos.add(limit);
		dbos.add(group);
		dbos.add(skip);
		Iterable<DBObject> itb = super.getC(DocName.WEREWOLF_KILL_LOG_DETAIL).aggregate(dbos).results();
		dbos.clear();
		Iterator<DBObject> it = itb.iterator();
		int count = 0;
		while (it.hasNext()) {
			count++;
			DBObject e = it.next();
			dbos.add(e);
		}
		return new Page<DBObject>(count, size, page, dbos);
	}

	/** 好友根据胜场排名 */
	public Page<DBObject> gameRankList(long uid, Integer actorCount, Integer rateType, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<Long> ids = relationshipService.getFrinds(uid);
		List<DBObject> friends = ids.stream().map(e -> {
			DBObject user = userService.findById(e);
			DBObject ret = new BasicDBObject();
			ret.put("rid", e);
			ret.put("nickname", DboUtil.getString(user, "nickname"));
			ret.put("sex", DboUtil.getInt(user, "sex"));
			ret.put("point", DboUtil.getInt(user, "point"));
			ret.put("avatar", DboUtil.getString(user, "avatar"));
			ret.put("personLabel", DboUtil.getString(user, "personLabel"));
			Integer vip = DboUtil.getInt(user, "vip");
			ret.put("vip", null == vip ? 0 : vip);
			int win = this.queryWholeWinCount(e);
			ret.put("win", win);
			return ret;
		}).filter(e -> (int) (e.get("win")) > 0).sorted((e1, e2) -> (int) (e2.get("win")) - (int) (e1.get("win")))
				.collect(Collectors.toList());
		int count = null == friends ? 0 : friends.size();
		List<DBObject> ret = new ArrayList<>();
		if (count > (page - 1) * size) {
			if (count <= page * size) {
				ret = friends.subList((page - 1) * size, count - 1);
			} else {
				ret = friends.subList((page - 1) * size, page * size - 1);
			}
		}
		return new Page<DBObject>(count, size, page, ret);
	}

	/** 总胜率排名 */
	public Page<WolfRankVO> queryRankList(Integer actorCount, Integer rateType, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<WolfRankVO> list = new ArrayList<>();
		String flag = getRedisTemplate().opsForValue().get(RK.wolfRankListFlag());// 最新数据标识
		int type = flag != null ? Integer.parseInt(flag) : 1;
		int index = (page - 1) * size;
		long count = getRedisTemplate().opsForZSet().zCard(RK.wolfRankList(type));
		Set<TypedTuple<String>> set = getRedisTemplate().opsForZSet()
				.reverseRangeByScoreWithScores(RK.wolfRankList(type), 0, 500, index, size);
		if (null != set && !set.isEmpty()) {
			for (TypedTuple<String> e : set) {
				String json = e.getValue();
				WolfRankVO vo = JSONUtil.jsonToBean(json, WolfRankVO.class);
				list.add(vo);
			}
		}
		return new Page<WolfRankVO>((int) count, size, page, list);
	}

	/** 计算狼人杀排名 */
	public void calRankList(int type) {
		BasicDBObject q = new BasicDBObject();
		// TODO
		q.append("winCnt", new BasicDBObject("$gt", 0));
		List<DBObject> dbos = super.getC(DocName.WEREWOLF_KILL_LOG).find(q).toArray();
		dbos = dbos.stream().map(e -> {
			DBObject user = userService.findById(DboUtil.getLong(e, "uid"));
			if (null != user) {
				Integer winCnt = DboUtil.getInt(e, "winCnt");
				Integer allCnt = DboUtil.getInt(e, "allCnt");
				winCnt = null == winCnt ? 0 : winCnt;
				allCnt = null == allCnt ? 0 : allCnt;
				user.put("winCnt", winCnt);
				user.put("allCnt", allCnt);
				if (allCnt == 0) {
					user.put("winRate", 0);
				} else {
					BigDecimal b = new BigDecimal((double) (winCnt * 100) / allCnt);
					double rate = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					user.put("winRate", rate);
				}
			}
			return user;
		}).filter(e -> null != e).collect(Collectors.toList());
		pushData4Page(dbos, type);
	}

	/** 添加缓存 */
	public void pushData4Page(List<DBObject> dbos, int type) {
		super.getRedisTemplate().delete(RK.wolfRankList(type));
		for (DBObject dbo : dbos) {
			WolfRankVO vo = DboUtil.toBean(dbo, WolfRankVO.class);
			super.getRedisTemplate().opsForZSet().add(RK.wolfRankList(type), JSONUtil.beanToJson(vo), vo.getWinRate());
		}
	}

	/** 统计24小时内玩狼人杀的人 */
	@SuppressWarnings("unchecked")
	public List<Long> find24hWolfPlayer() {
		BasicDBObject q = new BasicDBObject("updateTime",
				new BasicDBObject("$gt", System.currentTimeMillis() - Const.DAY));
		return super.getC(DocName.WEREWOLF_KILL_LOG).distinct("uid", q);
	}

}
