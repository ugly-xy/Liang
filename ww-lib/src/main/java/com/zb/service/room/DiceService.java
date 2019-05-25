package com.zb.service.room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
//import java.util.stream.Collectors;
//import java.util.HashSet;
//import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mongodb.DBObject;
import com.zb.common.Constant.ActivityType;
import com.zb.common.Constant.Point;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.finance.CoinLog;
import com.zb.models.room.Activity;
import com.zb.models.room.Actor;
import com.zb.models.room.Room;
import com.zb.models.room.activity.Dice;
import com.zb.models.room.activity.DiceActor;
import com.zb.models.usertask.Task;
import com.zb.service.room.vo.DiceRoomInfo;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.MsgType;
import com.zb.socket.model.SocketHandler;

@Service
public class DiceService extends RoomService {
	static final Logger log = LoggerFactory.getLogger(DiceService.class);

	private static final int START_MIN_COUNT = 4;
	private static final int START_MAX_COUNT = 6;
	private static final int TIME_START = 10;// 10;
	private static final int TIME_DICING = 25;
	private static final int TIME_GUESSING = 25;
	private static final int TIME_DICED = 2;
	private static final int TIME_END = 4;// 5;
	// private static final int TIME_PUNISHED = 45;// 40;
	private static final int TIME_TEMPLATE = 3;

	private static final int COIN_REWORD = 30;
	// 退出房间
	private static final int OUT_COIN = 50;
	// 掉线
	private static final int OUT_LINE_COIN = 30;
	private static final int INC_COIN = 10;

	private static final int POINT_WIN = 200;
	private static final int POINT_END = 40;
	private static final int POINT_LOST = 20;
	private static final int IN_COIN = 100;
	private static final int NUM_MIN = 3;
	private static final int COUNT_MIN = 2;
	private static final double TIP_PROPORTION = 0.1;
	private static final double PRI_TIP_PROPORTION = 0.1;
	private static final int[] COINADD = { 10, 20, 50, 100 };

	@Override
	public ReMsg handle(Room room, long fr) {
		if (room.getType() != ActivityType.DICE.getType()) {
			log.error(room.get_id() + " " + room.getType());
			return new ReMsg(ReCode.FAIL);
		}
		try {
			Dice uc = super.getActivity(room.get_id(), Dice.class);
			if (Dice.STATUS_READY == uc.getStatus()) {
				start(room, uc);
			} else if (Dice.STATUS_DICING == uc.getStatus() || Dice.STATUS_DICED == uc.getStatus()) {
				firstIntoGrag(room.get_id(), uc); // 进入叫阶段
			} else if (Dice.STATUS_GUESSING == uc.getStatus()) {
				autoGrag(room, uc); // 超时自动叫
			} else if (Dice.STATUS_SHOW == uc.getStatus()) {
				settlement(room, uc);// 结算
			} else if (Dice.STATUS_END == uc.getStatus()) {
				restart(room, fr, uc);
			}
		} catch (Exception e) {
			log.error("调用任务roomId=" + room.get_id() + "\n", e);
			this.addJob(room.get_id(), TIME_START);
		}
		return new ReMsg(ReCode.FAIL);
	}

	// public ReMsg inRoom(long uid, Room r) {
	// return inRoom(uid, r, false);
	// }

	public ReMsg inRoom(long uid, Room r, int model, boolean robit) {
		DiceRoomInfo ri = new DiceRoomInfo(ActivityType.DICE.getType());
		if (r == null || r.getType() != ActivityType.DICE.getType()) {
			ri.setCode(1);
			log.error(r.get_id() + " " + r.getType());
			return new ReMsg(ReCode.FAIL, ri);
		}
		Integer vip = DboUtil.getInt(userService.findById(uid), "vip");
		if (!r.isPub() && (null == vip || vip < 1)) {
			ri.setCode(ReCode.CANNOTDICEWITHOUTVIP.reCode());
			return new ReMsg(ReCode.CANNOTDICEWITHOUTVIP, ri);
		}
		DiceActor ua = super.getRoomActor(r.get_id(), uid, DiceActor.class);
		if (ua != null) {// 恢复重连
			ua.setStatus(Actor.STATUS_ONLINE);
		} else {// 进入房间
			DBObject user = userService.findById(uid);
			if (user == null) {
				ri.setCode(1);
				return new ReMsg(ReCode.FAIL, ri);
			}
			ua = new DiceActor();
			ua.setVip(DboUtil.getInt(user, "vip") == null ? 0 : DboUtil.getInt(user, "vip"));
			ua.setRobit(robit);
			ua.setUid(DboUtil.getLong(user, "_id"));
			ua.setAvatar(DboUtil.getString(user, "avatar"));
			ua.setPoint(DboUtil.getInt(user, "point"));
			ua.setSex(DboUtil.getInt(user, "sex"));
			ua.setNickname(DboUtil.getString(user, "nickname"));
			ua.setProvince(DboUtil.getString(user, "province") == null ? "火星" : DboUtil.getString(user, "province"));
			if (ua.getProvince().contains("市")) {
				ua.setCity("");
			} else {
				ua.setCity(DboUtil.getString(user, "city") == null ? "" : DboUtil.getString(user, "city"));
			}
			if (Room.ACTIVITY == r.getStatus()) {
				ua.setRole(Actor.ROLE_VIEWER);
			}
			if (null != r.getBuyUid() && uid == r.getBuyUid()) {// 房间购买人设置最高权限
				ua.setBuyUser(true);
			}
			ua.setGame("吹牛");
		}
		Dice uc = super.getActivity(r.get_id(), Dice.class);
		if (ua.isBuyUser() && (null == uc.getOwner() || uc.getOwner() != ua.getUid())) {// 房主不是房管
			if (null != uc.getOwner()) {// 原房管不为空
				DiceActor actor = super.getRoomActor(r.get_id(), uc.getOwner(), DiceActor.class);
				if (null != actor) {
					actor.setOwner(false);
					super.saveRoomActor(r.get_id(), actor);
				}
			}
			ua.setOwner(true);
			uc.setOwner(uid);
			saveActivity(r.get_id(), uc);
		}
		super.saveRoomActor(r.get_id(), ua);
		// super.changeRoomCount(r);
		Set<DiceActor> as = super.getRoomActors(r.get_id(), DiceActor.class);
		// super.kickRobit(as, r);// 踢出机器人
		// as = super.getRoomActors(r.get_id(), DiceActor.class);
		super.changeRoomCount(r, as, T_INROOM);
		Set<DiceActor> tas = new TreeSet<>();
		for (DiceActor a : as) {
			if (a.getUid() != uid) {
				a.setDices(null);
			}
			tas.add(a);
		}
		if (r.getUid() != 0 && (null == uc.getOwner() || uc.getOwner() == 0)) {
			for (DiceActor a : tas) {
				uc.setOwner(a.getUid());
				a.setOwner(true);
				saveRoomActor(r.get_id(), a);
				saveActivity(r.get_id(), uc);
				break;
			}
		}
		ri.setPub(r.isPub());
		ri.setHasOwner(r.getUid() == 0 ? false : true);
		ri.setActors(tas);
		ri.setCurrentActor(getCurrentActor(uc));
		ri.setTotalCoinPool(uc.getTotalCoinPool());
		ri.setSys(r.isSys());// 系统房间或包间
		if (r.getStatus() == Room.OPEN) {
			ri.setStatus(Room.OPEN);
		} else {
			// if (uc.getStatus() == Dice.STATUS_PUNISH) {
			// ri.setStatus(3);
			// } else {
			ri.setStatus(Room.ACTIVITY);
			// }
		}
		MapBody mb = new MapBody(SocketHandler.IN_ROOM, SocketHandler.FN_USER_ID, ua.getUid())
				.append("nickname", ua.getNickname()).append("sex", ua.getSex()).append("avatar", ua.getAvatar())
				.append("actors", tas).append("province", ua.getProvince()).append("city", ua.getCity())
				.append("role", ua.getRole());
		long dt = System.currentTimeMillis();
		for (Actor a : as) {
			if (uid != a.getUid()) {
				super.pubRoomUserMsg(r.get_id(), a.getUid(), MsgType.ROOM, mb, dt);
			}
		}
		return new ReMsg(ReCode.OK, ri);
	}

	public synchronized ReMsg ready(final long uid, final long roomId) {
		Dice uc = super.getActivity(roomId, Dice.class);
		if (Dice.STATUS_READY == uc.getStatus()) {// 房间在准备状态下，才能准备
			DiceActor a = super.getRoomActor(roomId, uid, DiceActor.class);
			if (a != null && Actor.A_STATUS_IN_ROOM == a.getUcStatus()) {
				if (!a.isRobit()) {
					DBObject user = super.userService.findById(uid);
					if (user == null || DboUtil.tryGetInt(user, "coin") < IN_COIN) {
						return new ReMsg(ReCode.COIN_BALANCE_LOW);
					}
				}
				a.setUcStatus(Actor.A_STATUS_READY);
				super.saveRoomActor(roomId, a);
				uc.putActor(uid, DiceActor.A_STATUS_START);
				super.saveActivity(roomId, uc);

				Set<DiceActor> actors = getRoomActors(roomId, DiceActor.class);
				MapBody mb = new MapBody(SocketHandler.ACTIVITY_READY, SocketHandler.FN_USER_ID, uid)
						.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", uc.get_id());
				boolean start = false;
				int readyCount = uc.getActors().size();
				long total = actors.size();

				if (readyCount >= START_MAX_COUNT) {// 准备人数超过最大游戏数，立即开始
					start = true;
				} else if (readyCount >= START_MIN_COUNT) {// 准备人数超过最小人数
					if (readyCount == total) {// 全体准备
						start = true;
					} else {// 还有未准备用户
						addJob(roomId, TIME_START);// 增加系统调用倒计时
						mb.append(SocketHandler.FN_LIMIT, TIME_START);
					}
				}
				super.pubRoomMsg(roomId, MsgType.DICE, mb);// 发送用户准备通知
				if (start) {
					this.addJob(roomId, TIME_TEMPLATE);
				}
				return new ReMsg(ReCode.OK);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	private boolean start(Room r, Dice uc) {
		// log.error("===================begin start service");
		Set<DiceActor> actors = super.getRoomActors(r.get_id(), DiceActor.class);
		boolean start = false;
		int readyCount = uc.getActors().size();
		if (readyCount >= START_MIN_COUNT) {// 准备人数超过最小人数
			start = true;
		} else {
			checkReadyRoom(r, readyCount, TIME_START);
		}
		if (start) {
			addJob(r.get_id(), TIME_DICING);// 摇骰子倒计时
			Set<Long> kickIds = new TreeSet<Long>();
			uc.clearActors();
			int kickCount = readyCount - START_MAX_COUNT;
			List<Long> readyActors = null;
			if (kickCount > 0) {
				readyActors = new ArrayList<>();
			}
			for (DiceActor actor : actors) {
				if (Actor.A_STATUS_READY == actor.getUcStatus()) {
					actor.setUcStatus(DiceActor.A_STATUS_START);
					if (kickCount > 0) {
						if (actor.isRobit()) {
							kickIds.add(actor.getUid());
							kickCount--;
						}
					}
					if (kickCount > 0 && !actor.isRobit()) {
						readyActors.add(actor.getUid());
					}
				} else {
					kickIds.add(actor.getUid());
				}
			}
			int i = 0;
			while (kickCount > 0) {
				i++;
				kickIds.add(readyActors.get(readyActors.size() - i));
				kickCount--;
			}
			for (DiceActor actor : actors) {
				if (kickIds.contains(actor.getUid())) {
					super.delRoomActor(r.get_id(), actor.getUid());
					uc.removeActor(actor.getUid());
				} else {
					actor.setUcStatus(DiceActor.A_STATUS_START);
					saveRoomActor(r.get_id(), actor);
					uc.putActor(actor.getUid(), DiceActor.A_STATUS_START);
				}
			}
			initDices(r, uc, actors, kickIds);// 初始化骰子
			r.setStatus(Room.ACTIVITY);
			changeRoomCount(r, super.getRoomActors(r.get_id(), DiceActor.class), T_START);
			// this.updateRoom(r);
		}
		// log.error("===================end start service");
		return start;
	}

	/** 初始化骰子 */
	private void initDices(Room r, Dice uc, Set<DiceActor> actors, Set<Long> kickIds) {
		// log.error("===================begin initdices service");
		uc.clearDices();
		Map<Long, Integer> as = uc.getActors();
		for (long cuid : as.keySet()) {
			int[] dices = new int[Dice.DICE_NUM];
			for (int i = 0; i < Dice.DICE_NUM; i++) {
				dices[i] = RandomUtil.nextInt(6) + 1;
			}
			uc.getDices().put(cuid, dices); // 初始化对局骰子表
			uc.getReDiceTimeMap().put(cuid, 1); // 初始化重摇次数
		}
		Set<DiceActor> acs = new TreeSet<>();
		for (DiceActor a : actors) {
			if (uc.getActors().containsKey(a.getUid())) {
				acs.add(a);
			}
		}
		long stateMap[][] = uc.getStateMap();
		int i = 0;
		long id = getFirstBragger(uc); // 随机选择第一个叫的玩家
		for (DiceActor e : acs) { // 初始化玩家遍历表
			stateMap[0][i] = e.getUid();
			if (e.getUid() == id) {
				stateMap[1][i] = DiceActor.A_STATUS_GUESSING;
			} else {
				stateMap[1][i] = DiceActor.A_STATUS_GUESSED;
			}
			i++;
		}
		uc.setStateMap(stateMap);
		uc.setStatus(Dice.STATUS_DICING);
		uc.setTotalCoinPool(0);
		saveActivity(r.get_id(), uc);
		long curTime = System.currentTimeMillis();
		MapBody mb2 = new MapBody(SocketHandler.DICE_DISTRIBUTE, SocketHandler.FN_ROOM_ID, r.get_id())
				.append(SocketHandler.FN_LIMIT, TIME_DICING).append("inning", uc.getInning()).append("actors", acs)
				.append("kicks", kickIds);
		for (DiceActor a : actors) {
			if (a.getStatus() == DiceActor.STATUS_ONLINE) {
				this.pubRoomUserMsg(r.get_id(), a.getUid(), MsgType.DICE, mb2, curTime);
			}
		}
		// log.error("===================end initdices service" + uc.getStatus()
		// + "roomid=" + r.get_id());
	}

	/** 重新摇 */
	public void reDicing(long roomId, long uid) {
		// log.error("===================begin reDicing service");
		Dice uc = super.getActivity(roomId, Dice.class);
		if (Dice.STATUS_DICING == uc.getStatus()) {
			Map<Long, Integer> reDiceMap = uc.getReDiceTimeMap();
			ReCode r = coinService.reduce(uid, CoinLog.DICE, uc.get_id(), COIN_REWORD * reDiceMap.get(uid), 0L, "重摇");
			if (ReCode.OK.reCode() == r.reCode()) {
				Map<Long, int[]> diceMap = uc.getDices();
				int[] dices = new int[Dice.DICE_NUM];
				for (int i = 0; i < Dice.DICE_NUM; i++) {
					dices[i] = RandomUtil.nextInt(6) + 1;
				}
				diceMap.put(uid, dices);
				uc.setDices(diceMap);
				reDiceMap.put(uid, reDiceMap.get(uid) + 1);
				uc.setReDiceTimeMap(reDiceMap);
				saveActivity(roomId, uc);
				long curTime = System.currentTimeMillis();
				MapBody mb = new MapBody(SocketHandler.DICE_REDICING, SocketHandler.FN_USER_ID, uid)
						.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", uc.get_id());
				Set<DiceActor> actors = super.getRoomActors(roomId, DiceActor.class);
				for (DiceActor a : actors) {
					if (a.getStatus() == DiceActor.STATUS_ONLINE) {
						if (a.getUid() == uid) {
							mb.append("dices", dices).append("rePubCoin", COIN_REWORD * reDiceMap.get(uid));
						} else {
							mb.remove("dices").remove("rePubCoin");
						}
						this.pubRoomUserMsg(roomId, a.getUid(), MsgType.DICE, mb, curTime);
					}
				}
			} else {
				MapBody mb = new MapBody("code", 1).append("title", "金币不足").append("body", "金币余额不足，无法重摇！")
						.append("prompt", SocketHandler.PROMPT_LOW_COIN);
				this.pubRoomUserMsg(roomId, uid, MsgType.PROMPT, mb, System.currentTimeMillis());
			}
		}
	}

	/** 获取自己骰子 */
	public void getDices(final long roomId, final long uid) {
		Dice uc = super.getActivity(roomId, Dice.class);
		// log.error("begin getDices" + uc.getStatus() + "uid=" + uid +
		// "roomid=" + roomId);
		// if (Dice.STATUS_DICING == uc.getStatus()) {
		Map<Long, int[]> diceMap = uc.getDices();
		int[] dices = diceMap.get(uid);
		long curTime = System.currentTimeMillis();
		MapBody mb = new MapBody(SocketHandler.DICE_GET, SocketHandler.FN_USER_ID, uid)
				.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", uc.get_id());
		Set<DiceActor> actors = super.getRoomActors(roomId, DiceActor.class);
		for (DiceActor a : actors) {
			if (a.getStatus() == DiceActor.STATUS_ONLINE) {
				if (a.getUid() == uid) {
					mb.append("dices", dices).append("rePubCoin", COIN_REWORD);
				} else {
					mb.remove("dices").remove("rePubCoin");
				}
				this.pubRoomUserMsg(roomId, a.getUid(), MsgType.DICE, mb, curTime);
			}
		}
		// }
		// log.error("===================end reDicing service");
	}

	/** 摇骰确定 */
	public void confirmDice(final long roomId, final long uid) {
		// log.error("===================begin confirmDice service");
		Dice uc = super.getActivity(roomId, Dice.class);
		uc.getConfirmUid().put(uid, 0);

		if (uc.getConfirmUid().size() == uc.getActors().size()) {
			uc.setStatus(Dice.STATUS_DICED);
			// super.saveActivity(roomId, uc);
			addJob(roomId, TIME_DICED);// 确认骰子后倒计时
		}
		super.saveActivity(roomId, uc);
		MapBody mb = new MapBody(SocketHandler.DICE_CONFIRM, SocketHandler.FN_USER_ID, uid)
				.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", uc.get_id());
		super.pubRoomMsg(roomId, MsgType.DICE, mb);

		// log.error("===================end confirmDice service");
	}

	/** 第一个叫的玩家 */
	private long getFirstBragger(Dice uc) {
		Set<Long> set = uc.getActors().keySet();
		int index = RandomUtil.nextInt(set.size());
		int count = 0;
		long bragger = 0L;
		for (long e : set) {
			if (count == index) {
				bragger = e;
				break;
			}
			count++;
		}
		return bragger;
	}

	/** 开始叫阶段 */
	public void intoGrag(final long roomId, Dice uc) {
		// log.error("===================begin intoGrag service");
		addJob(roomId, TIME_GUESSING);// 猜骰子倒计时
		uc.setStatus(Dice.STATUS_GUESSING);
		boolean isFull = uc.isBeFull();
		Set<DiceActor> actors = super.getRoomActors(roomId, DiceActor.class);
		for (DiceActor a : actors) {
			long dt = System.currentTimeMillis();
			MapBody mb = new MapBody(SocketHandler.DICE_BRAG_START, SocketHandler.FN_ROOM_ID, roomId)
					.append(SocketHandler.FN_LIMIT, TIME_GUESSING).append(SocketHandler.FN_USER_ID, getCurrentActor(uc))
					.append("actId", uc.get_id()).append("inning", uc.getInning())
					.append("quantity", uc.getDiceTotalNum()).append("dot", uc.getDiceCount())
					.append("lastUid", getLastActor(uc)).append("isFull", isFull ? 1 : 0);
			super.pubRoomUserMsg(roomId, a.getUid(), MsgType.DICE, mb, dt);
		}
		super.saveActivity(roomId, uc);
		// log.error("===================end intoGrag service");
	}

	/** 首次开始叫阶段，缓存玩家骰子信息 */
	public void firstIntoGrag(final long roomId, Dice uc) {
		// log.error("===================begin firstIntoGrag service");
		if (uc.getInning() == 0) {
			Set<DiceActor> actors = super.getRoomActors(roomId, DiceActor.class);
			Map<Long, int[]> dices = uc.getDices();
			for (DiceActor actor : actors) {
				for (Long e : dices.keySet()) {
					if (e == actor.getUid()) {
						actor.setDices(dices.get(e));
						super.saveRoomActor(roomId, actor);
					}
				}
			}
		}
		intoGrag(roomId, uc);
		// log.error("===================end firstIntoGrag service");
	}

	/** 系统自动叫 */
	public void autoGrag(Room r, Dice uc) {
		bragDices(r.get_id(), 0, 0, 0, INC_COIN);
	}

	/** 叫点 */
	public ReMsg bragDices(final long roomId, long uid, int num, int count, int coin) {
		coin = 0 == coin ? INC_COIN : coin;
		if (Arrays.binarySearch(COINADD, coin) < 0) {
			return new ReMsg(ReCode.ADD_COIN_ERROR);
		}
		Dice uc = getActivity(roomId, Dice.class);
		if (Math.abs(num) > uc.getActors().size() * Dice.DICE_NUM) {
			// 叫数超过场上骰子数
			return new ReMsg(ReCode.DICE_OVERFLOW);
		}
		if (coin > INC_COIN) {
			DiceActor da = super.getRoomActor(roomId, uid, DiceActor.class);
			if (!da.isRobit()) {
				DBObject user = super.userService.findById(uid);
				if (user == null || DboUtil.tryGetInt(user, "coin") < uc.getTotalCoinPool()) {// 不是机器人
					return new ReMsg(ReCode.COIN_BALANCE_LOW);
				}
			}
		}
		if (count == 1) {
			uc.setOneCalled(true);
		}
		if (Dice.STATUS_GUESSING == uc.getStatus()) {
			long[][] arr = uc.getStateMap();
			boolean autoCall = true;
			if (uid < 1) {// 系统叫
				num = uc.getDiceTotalNum();
				count = uc.getDiceCount();
				if (Math.abs(num) > uc.getActors().size() * Dice.DICE_NUM - 1) {
					// 叫数等于场上骰子数
					uc.setStatus(Dice.STATUS_SHOW);
					saveActivity(roomId, uc);
					settlement(super.getRoom(roomId), uc);
					return new ReMsg(ReCode.DICE_OVERFLOW);
				}
				if (num == 0 && count == 0) {
					num = NUM_MIN;
					count = COUNT_MIN;
				} else {
					if (6 > count && (RandomUtil.nextInt(2) & 1) == 0) {// num+或count+
						++count;
					} else {
						++num;
					}
				}
			} else {
				autoCall = false;
				super.delJob(roomId);
			}
			for (int i = 0; i < arr[1].length; i++) {
				if (arr[1][i] == DiceActor.A_STATUS_GUESSING) {
					arr[1][i] = DiceActor.A_STATUS_GUESSED;
					if (i > uc.getActors().size() - 2) {// i是最后一个元素
						arr[1][0] = DiceActor.A_STATUS_GUESSING;
						uid = arr[0][0];
						break;
					} else {
						arr[1][i + 1] = DiceActor.A_STATUS_GUESSING;
						uid = arr[0][i + 1];
						break;
					}
				}
			}
			uc.setDiceTotalNum(num);
			uc.setDiceCount(count);
			uc.setStateMap(arr);
			uc.setInning(uc.getInning() + 1);
			uc.setTotalCoinPool(uc.getTotalCoinPool() + coin);
			boolean isFull = false;
			if (Math.abs(num) == uc.getActors().size() * Dice.DICE_NUM) {
				// 叫数等于场上骰子数
				isFull = true;
				uc.setBeFull(isFull);
			}
			saveActivity(roomId, uc);
			MapBody mb = new MapBody(SocketHandler.DICE_BRAG, SocketHandler.FN_ROOM_ID, roomId)
					.append(SocketHandler.FN_USER_ID, uid).append("quantity", num).append("dot", count)
					.append("totalCoinPool", uc.getTotalCoinPool()).append("actId", uc.get_id())
					.append("isFull", isFull ? 1 : 0).append("autoCall", autoCall ? 1 : 0);
			Set<DiceActor> actors = super.getRoomActors(roomId, DiceActor.class);
			long dt = System.currentTimeMillis();
			for (DiceActor a : actors) {
				if (a.getStatus() == DiceActor.STATUS_ONLINE) {
					this.pubRoomUserMsg(roomId, a.getUid(), MsgType.DICE, mb, dt);
				}
			}
			intoGrag(roomId, uc);// 进入下一玩家叫阶段
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 开阶段 */
	public ReMsg showDice(final long roomId, final long uid) {
		// log.error("===================begin showDice service");
		Dice uc = super.getActivity(roomId, Dice.class);
		if (Dice.STATUS_GUESSING == uc.getStatus()) {
			uc.setStatus(Dice.STATUS_SHOW);
			// super.delJob(roomId);
			super.addJob(roomId, uc.getActors().size() + 3);// 每秒展示一个用户
			int diceTotalNum = uc.getDiceTotalNum();
			int diceCount = uc.getDiceCount();
			long luid = getLastActor(uc);// 前一个玩家
			Map<Long, int[]> dices = uc.getDices();
			int diceSum = 0;
			boolean oneCalled = uc.isOneCalled();// 是否叫过1
			if (oneCalled) {
				for (long e : dices.keySet()) {
					int[] temp = dices.get(e);
					int count = 0;
					for (int i = 0; i < temp.length; i++) {
						if (diceCount == temp[i]) {
							++count;
						}
					}
					diceSum += count;
				}
			} else {
				for (long e : dices.keySet()) {
					int[] temp = dices.get(e);
					int count = 0;
					for (int i = 0; i < temp.length; i++) {
						if (diceCount == temp[i] || 1 == temp[i]) {
							++count;
						}
					}
					diceSum += count;
				}
			}
			if (diceTotalNum <= diceSum) {
				uc.setWinner(luid);
				uc.setLoser(uid);
			} else {
				uc.setWinner(uid);
				uc.setLoser(luid);
			}
			super.saveActivity(roomId, uc);
			Set<DiceActor> uas = super.getRoomActors(roomId, DiceActor.class);
			for (DiceActor dsa : uas) {
				long dt = System.currentTimeMillis();
				MapBody mb = new MapBody(SocketHandler.DICE_SHOW, SocketHandler.FN_ROOM_ID, roomId)
						.append(SocketHandler.FN_USER_ID, uid).append("quantity", uc.getDiceTotalNum())
						.append("dot", uc.getDiceCount()).append("winner", uc.getWinner())
						.append("loser", uc.getLoser()).append("allDices", uc.getDices())
						.append("oneCalled", oneCalled ? 1 : 0).append("actId", uc.get_id());
				super.pubRoomUserMsg(roomId, dsa.getUid(), MsgType.DICE, mb, dt);
			}
			return new ReMsg(ReCode.OK);
		}
		// log.error("===================end showDice service");
		return new ReMsg(ReCode.FAIL);
	}

	/** 获取当前玩家 */
	private long getCurrentActor(Dice uc) {
		long[][] arr = uc.getStateMap();
		long uid = 0L;
		for (int i = 0; i < arr[1].length; i++) {
			if (arr[1][i] == DiceActor.A_STATUS_GUESSING) {
				if (i > uc.getActors().size() - 2) {// i是最后一个元素
					uid = arr[0][0];
					break;
				} else {
					uid = arr[0][i + 1];
					break;
				}
			}
		}
		return uid;
	}

	/** 获取前一个玩家 */
	private long getLastActor(Dice uc) {
		long[][] arr = uc.getStateMap();
		for (int i = 0; i < arr[1].length; i++) {
			if (arr[1][i] == DiceActor.A_STATUS_GUESSING) {
				return arr[0][i];
			}
		}
		return 0;
	}

	/** 结算阶段 */
	public void settlement(Room room, Dice uc) {
		// log.error("===================begin settlement service");
		if (Dice.STATUS_SHOW == uc.getStatus()) {
			uc.setStatus(Dice.STATUS_END);
			super.addJob(room.get_id(), TIME_END);// 5秒倒计时，5秒后进入进入奖惩阶段
			super.saveActivity(room.get_id(), uc);

			double pro = TIP_PROPORTION;
			int coinPool = uc.getTotalCoinPool(); // 金币池，全部由输家提供
			int participation = coinPool / 100 == 0 ? 1 : coinPool / 100;// 参与奖励
			int tip = 0;
			int winMoney = 0;
			int lostb = coinPool;
			if (!room.isPub()) {
				pro = PRI_TIP_PROPORTION;
				DBObject dbo = userService.findById(uc.getLoser());
				if (dbo != null) {
					lostb = DboUtil.getInt(dbo, "coin");
				}
				if (lostb < coinPool) {
					tip = (int) (lostb * pro);
					winMoney = lostb - participation * (uc.getActors().size() - 2) - tip;
				} else {
					tip = (int) (coinPool * pro);
					winMoney = coinPool - participation * (uc.getActors().size() - 2) - tip;
				}
			} else {
				tip = (int) (coinPool * pro);
				winMoney = coinPool - participation * (uc.getActors().size() - 2) - tip;
			}
			// 小费

			MapBody mb = new MapBody(SocketHandler.DICE_END, SocketHandler.FN_ROOM_ID, room.get_id())
					.append(SocketHandler.FN_LIMIT, TIME_END).append("actId", uc.get_id());
			long dt = System.currentTimeMillis();
			Set<DiceActor> uas = super.getRoomActors(room.get_id(), DiceActor.class);
			mb.append("tip", tip);
			// 制作列表
			List<String[]> list = new ArrayList<String[]>();
			list.add(new String[] { 1 + "", uc.getWinner() + "", winMoney + "" });
			list.add(new String[] { 3 + "", uc.getLoser() + "", 0 - coinPool + "" });
			for (DiceActor dsa : uas) {
				if (dsa.getRole() == DiceActor.ROLE_EXECUTOR) {
					if (uc.getWinner() - dsa.getUid() != 0 && uc.getLoser() - dsa.getUid() != 0) {
						list.add(1, new String[] { 2 + "", dsa.getUid() + "", participation + "" });
					}
				}
			}
			final int tmpWin = winMoney;
			for (DiceActor dsa : uas) {
				if (dsa.getRole() == DiceActor.ROLE_EXECUTOR) {
					mb.append(SocketHandler.FN_USER_ID, dsa.getUid()).append("list", list);
					if (uc.getWinner() == dsa.getUid()) {// 胜利
						mb.append("status", 1).append("coin", winMoney);
						coinService.addCoin(dsa.getUid(), CoinLog.DICE, uc.get_id(), winMoney, 0, "游戏胜利加币");
						userService.changePoint(dsa.getUid(), Point.DICE.getType(), POINT_WIN, 0);
						// 触发任务
						CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
							if (!dsa.isRobit()) {
								jobsDone(dsa.getUid(), Task.DICE, tmpWin);
							}
							return null;
						});
						try {
							future.get();
						} catch (Exception e) {
							log.error("调用任务dice uid=" + dsa.getUid(), e);
						}
					} else if (uc.getLoser() == dsa.getUid()) {// 失败
						mb.append("status", 3).append("coin", 0 - coinPool);
						coinService.forceReduce(dsa.getUid(), CoinLog.DICE, uc.get_id(), coinPool, 0, "游戏失败扣币");
						userService.changePoint(dsa.getUid(), Point.DICE.getType(), POINT_LOST, 0);
					} else {
						mb.append("status", 2).append("coin", participation);
						coinService.addCoin(dsa.getUid(), CoinLog.DICE, uc.get_id(), participation, 0, "完成游戏加币");
						userService.changePoint(dsa.getUid(), Point.DICE.getType(), POINT_END, 0);
						// 触发任务
						CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
							if (!dsa.isRobit()) {
								jobsDone(dsa.getUid(), Task.DICE, participation);
							}
							return null;
						});
						try {
							future.get();
						} catch (Exception e) {
							log.error("调用任务dice：uid=" + dsa.getUid(), e);
						}
					}
					if (dsa.getStatus() == DiceActor.STATUS_ONLINE) {
						super.pubRoomUserMsg(room.get_id(), dsa.getUid(), MsgType.DICE, mb, dt);
					} else {
						if (!dsa.isReduceCoin()) {
							if (dsa.getStatus() == DiceActor.STATUS_OUT_ROOM) {
								coinService.forceReduce(dsa.getUid(), CoinLog.DICE, uc.get_id(), OUT_COIN, 0,
										"未完成游戏退出房间惩罚扣币");
								dsa.setReduceCoin(true);
							} else if (dsa.getStatus() == DiceActor.STATUS_OFFLINE) {
								coinService.forceReduce(dsa.getUid(), CoinLog.DICE, uc.get_id(), OUT_LINE_COIN, 0,
										"未完成游戏掉线惩罚扣币");
								dsa.setReduceCoin(true);
							}
						}
						super.saveRoomActor(room.get_id(), dsa);
					}
				}
			}
			uc.setUpdateTime(System.currentTimeMillis());
			super.getMongoTemplate().save(uc);
		}
		// log.error("===================end settlement service");
	}

	public static void main(String[] args) {
		// DiceActor d1 = new DiceActor();
		// DiceActor d2 = new DiceActor();
		// DiceActor d3 = new DiceActor();
		// d1.setInTime(1L);
		// d2.setInTime(3L);
		// d3.setInTime(2L);
		// d1.setUid(1L);
		// d2.setUid(2L);
		// d3.setUid(3L);
		// Set<DiceActor> temp = new TreeSet<>();
		// temp.add(d1);
		// temp.add(d2);
		// temp.add(d3);
		// List<DiceActor> list = temp.stream().sorted((e1, e2) -> (int)
		// (e2.getUid() - e1.getUid()))
		// .collect(Collectors.toList());
		// System.out.println("");
	}

	// private ReMsg punished(Room r, Dice uc) {
	// // log.error("===================begin punished service");
	// uc.setStatus(Dice.STATUS_PUNISH);
	// super.saveActivity(r.get_id(), uc);
	// super.getMongoTemplate().save(uc);
	// super.addJob(r.get_id(), TIME_PUNISHED);
	// Set<String> punish = punishService.getPunish(6);
	// MapBody mb = new MapBody(SocketHandler.DICE_REWARD,
	// SocketHandler.FN_ROOM_ID, r.get_id())
	// .append("uids", new HashSet<Long>() {
	// {
	// add(uc.getLoser());
	// }
	// }).append("punish", punish).append(SocketHandler.FN_LIMIT,
	// TIME_PUNISHED).append("actId", uc.get_id());
	// super.pubRoomMsg(r.get_id(), MsgType.DICE, mb);
	// // log.error("===================end punished service");
	// return new ReMsg(ReCode.OK);
	// }

	public boolean restart(Room r, long fr, Dice uc) {
		// log.error("===================begin restart service");
		Set<DiceActor> actors = getRoomActors(r.get_id(), DiceActor.class);
		long owner = 0L;
		if (r.getUid() != 0) {
			if (uc == null) {
				uc = super.getActivity(r.get_id(), Dice.class);
			}
			owner = uc.getOwner();
		}
		super.delActivity(r.get_id());
		Set<DiceActor> initActors = new TreeSet<DiceActor>();
		int robitCount = 0;
		for (DiceActor actor : actors) {
			if (Actor.STATUS_ONLINE != actor.getStatus()) {
				super.delRoomActor(r.get_id(), actor.getUid());
				if (r.getUid() != 0 && actor.getUid() == owner) {
					DiceActor newOua = getNewOwner(actors);
					if (newOua != null) {
						owner = newOua.getUid();
					}
				}
			} else {
				actor.setRole(DiceActor.ROLE_EXECUTOR);
				actor.setStatus(DiceActor.STATUS_ONLINE);
				actor.setUcStatus(DiceActor.A_STATUS_IN_ROOM);
				actor.setChangeCoin(0);
				actor.setDices(null);
				initActors.add(actor);
				super.saveRoomActor(r.get_id(), actor);
				if (actor.isRobit()) {
					robitCount++;
				}
			}
		}
		r.setStatus(Room.OPEN);
		r.setCount(initActors.size());
		r.setRobitCount(robitCount);
		super.updateRoom(r, T_RESTART);

		if (r.getUid() != 0) {
			uc = super.getActivity(r.get_id(), Dice.class);
			uc.setOwner(owner);
			super.saveActivity(r.get_id(), uc);
		}
		MapBody mb = new MapBody(SocketHandler.ROOM_RESTART, SocketHandler.FN_ROOM_ID, r.get_id()).append("actors",
				initActors);

		long dt = System.currentTimeMillis();
		for (Actor actor : initActors) {
			super.pubRoomUserMsg(r.get_id(), actor.getUid(), MsgType.ROOM, mb, dt);
		}
		// log.error("===================end restart service");
		return true;
	}

	// public DBObject findWordById(String id) {
	// return getC(DocName.GUESS_WORD).findOne(new BasicDBObject("_id", id));
	// }
	//
	// public Page<DBObject> query(Integer status, Integer page, Integer size) {
	// size = getSize(size);
	// page = getPage(page);
	// List<DBObject> dbos = find(status, page, size);
	// int count = count(status);
	// return new Page<DBObject>(count, size, page, dbos);
	// }

	// public int count(Integer status) {
	// DBObject q = new BasicDBObject();
	// if (status != null) {
	// q.put("status", status);
	// }
	// return getC(DocName.GUESS_WORD).find(q).count();
	// }
	//
	// public List<DBObject> find(Integer status, Integer page, Integer size) {
	// DBObject q = new BasicDBObject();
	// if (status != null) {
	// q.put("status", status);
	// }
	// return getC(DocName.GUESS_WORD).find(q).skip(super.getStart(page,
	// size)).limit(getSize(size))
	// .sort(new BasicDBObject("_id", -1)).toArray();
	// }

	// public ReMsg adds(String words, String tip, String provider) {
	// String[] ww = words.split(";");
	// for (String w : ww) {
	// upsert(w, tip, Const.STATUS_OK, provider);
	// }
	// return new ReMsg(ReCode.OK);
	// }

	// public ReMsg upsert(String word, String tip, Integer status, String
	// provider) {
	// GuessWord banner = new GuessWord(word, status, tip, provider);
	// getMongoTemplate().save(banner);
	// return new ReMsg(ReCode.OK);
	// }

	@Override
	public ReMsg outRoom(long uid, Room r) {
		return outRoom(uid, r, SocketHandler.OUT_ROOM);
	}

	@Override
	public ReMsg disconnectRoom(long uid, Room r) {
		return outRoom(uid, r, SocketHandler.DISCONNECT);
	}

	@Override
	public ReMsg kick(long uid, Room r) {
		return outRoom(uid, r, SocketHandler.KICK);
	}

	public ReMsg outRoom(long uid, Room r, int type) {
		if (r.getType() != ActivityType.DICE.getType()) {
			log.error(r.get_id() + " " + r.getType());
			return new ReMsg(ReCode.FAIL);
		}
		if (Room.ACTIVITY == r.getStatus() && SocketHandler.KICK == type) {
			return new ReMsg(ReCode.KICK_ERROR);
		}
		DiceActor actor = super.getRoomActor(r.get_id(), uid, DiceActor.class);
		Dice uc = super.getActivity(r.get_id(), Dice.class);
		boolean ucChange = false;
		if (null == actor) {
			return new ReMsg(ReCode.ACTOR_ERROR);
		} else {
			if (Room.OPEN == r.getStatus()) {
				if (DiceActor.A_STATUS_READY == actor.getUcStatus()
						|| DiceActor.A_STATUS_IN_ROOM == actor.getUcStatus()) {
					if (SocketHandler.KICK == type) {
						if (actor.isBuyUser()) {// 房主不能被踢
							return new ReMsg(ReCode.FAIL);
						}
						try {
							MapBody mb = new MapBody(type).append("nickname",
									super.getRoomActor(r.get_id(), uc.getOwner()).getNickname());
							super.pubUserMsg(uid, MsgType.PROMPT, mb);
						} catch (Exception e) {
							log.error("发送踢人信息", e);
						}
					}
				}
				if (DiceActor.A_STATUS_READY == actor.getUcStatus()) {
					// Dice uc = super.getActivity(r.get_id(), Dice.class);
					uc.removeActor(uid);
					// super.saveActivity(r.get_id(), uc);
					ucChange = true;
				}
				this.delRoomActor(r.get_id(), uid);
				Set<DiceActor> as = super.getRoomActors(r.get_id(), DiceActor.class);
				super.changeRoomCount(r, as, T_OUTROOM);
			} else if (Actor.ROLE_VIEWER == actor.getRole()) {
				this.delRoomActor(r.get_id(), actor.getUid());
				Set<DiceActor> as = super.getRoomActors(r.get_id(), DiceActor.class);
				super.changeRoomCount(r, as, T_OUTROOM);
			} else {
				// Dice uc = super.getActivity(r.get_id(), Dice.class);
				if (SocketHandler.DISCONNECT != type) {
					// if (Dice.STATUS_PUNISH == uc.getStatus()) {
					// if (!actor.isReduceCoin()) {
					// coinService.forceReduce(uid, CoinLog.DICE, uc.get_id(),
					// OUT_COIN, 0, "未完成游戏惩罚扣币");
					// actor.setReduceCoin(true);
					// }
					// super.delRoomActor(r.get_id(), uid);
					// uc.removeActor(uid);
					// super.saveActivity(r.get_id(), uc);
					// } else {
					actor.setStatus(Actor.STATUS_OUT_ROOM);
					// }
				} else {
					actor.setStatus(Actor.STATUS_OFFLINE);
				}
				this.saveRoomActor(r.get_id(), actor);
			}
			MapBody mb = new MapBody(type, SocketHandler.FN_USER_ID, actor.getUid())
					.append("nickname", actor.getNickname()).append(SocketHandler.FN_ROOM_ID, r.get_id());
			this.pubRoomMsg(r.get_id(), MsgType.ROOM, mb);
			if (r.getUid() != 0 && SocketHandler.DISCONNECT != type) {
				Long owner = uc.getOwner();
				if (uid == owner) {
					Set<DiceActor> as = super.getRoomActors(r.get_id(), DiceActor.class);
					DiceActor newOwn = this.getNewOwner(as);
					if (newOwn != null) {
						pubOwnerChange(uid, newOwn, r, uc);
						ucChange = false;
					}
				}
			}
		}
		if (ucChange) {
			super.saveActivity(r.get_id(), uc);
		}
		int outCount = 0;
		Set<DiceActor> actors = super.getRoomActors(r.get_id(), DiceActor.class);
		for (DiceActor ua : actors) {
			if (ua.getStatus() != DiceActor.STATUS_ONLINE) {
				outCount++;
			}
		}
		if (outCount == actors.size()) {
			super.closeRoom(r.get_id());
		}
		return new ReMsg(ReCode.OK);
	}

	// @Override
	// public ReMsg userFastCard(long uid, Room r) {
	// // Dice uc = super.getActivity(r.get_id(), Dice.class);
	// // if (uc.getStatus() == Dice.STATUS_PUNISH) {
	// // ReCode rc = fastCard(uid, uc.get_id());
	// // if (rc.reCode() == ReCode.OK.reCode()) {
	// // this.restart(r, uid);
	// // }
	// // return new ReMsg(rc);
	// // }
	// return new ReMsg(ReCode.CAN_NOT_USE_FAST_CARD);
	// }

	@Override
	public ReMsg getActorStatus(long uid, Room r) {
		DiceActor uca = super.getRoomActor(r.get_id(), uid, DiceActor.class);
		if (uca == null) {
			return new ReMsg(ReCode.OK, 1);
		} else {
			// Dice uc = super.getActivity(r.get_id(), Dice.class);
			// if (uc.getStatus() == Dice.STATUS_PUNISH) {
			// return new ReMsg(ReCode.OK, 1);
			// } else {
			return new ReMsg(ReCode.OK, 2);
			// }
		}
	}

	@Override
	public ReMsg canInRoom(int type, long actId, long roomId) {
		Dice uc = super.getActivity(roomId, Dice.class);
		if (actId == uc.get_id()) {
			if (uc.getStatus() > Activity.STATUS_READY && uc.getStatus() < Dice.STATUS_PUNISH)
				return new ReMsg(ReCode.OK, 1);
		}
		return new ReMsg(ReCode.OK, 2);
	}

	private DiceActor getNewOwner(Set<DiceActor> as) {
		for (DiceActor a : as) {
			if (Actor.STATUS_ONLINE == a.getStatus()) {
				return a;
			}
		}
		return null;
	}

	private void pubOwnerChange(long uid, DiceActor a, Room r, Dice uc) {
		if (r.getType() != ActivityType.DICE.getType()) {
			log.error(r.get_id() + " " + r.getType());
			return;
		}
		uc.setOwner(a.getUid());
		a.setOwner(true);
		super.saveRoomActor(r.get_id(), a);
		super.saveActivity(r.get_id(), uc);
		MapBody mb = new MapBody(SocketHandler.CHANGE_OWNER).append("lastOwner", uid).append("curOwner", uc.getOwner());
		super.pubRoomMsg(r.get_id(), MsgType.ROOM, mb);
	}

}
