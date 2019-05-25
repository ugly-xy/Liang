package com.zb.service.room;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.ActivityType;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.redis.RK;
import com.zb.common.utils.DateUtil;
import com.zb.common.utils.PokerUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.TexasLog;
import com.zb.models.finance.CoinLog;
import com.zb.models.room.Activity;
import com.zb.models.room.Actor;
import com.zb.models.room.Room;
import com.zb.models.room.activity.Texas;
import com.zb.models.room.activity.TexasActor;
import com.zb.service.UserService;
import com.zb.service.WinCoinRankService;
import com.zb.service.room.vo.TexasRoomInfo;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.MsgType;
import com.zb.socket.model.SocketHandler;

/**
 * @author bibi
 * 
 */
@Service
public class TexasService extends RoomService {

	@Autowired
	UserService userService;

	@Autowired
	WinCoinRankService winCoinRankService;

	@Override
	public ReMsg handle(Room room, long fr) {
		try {
			Texas wk = super.getActivity(room.get_id(), Texas.class);
			if (Texas.STATUS_READY == wk.getStatus()) {// 开始游戏
				start(room.get_id(), wk);
			} else if (Texas.STATUS_DEAL == wk.getStatus()) {// 开始下注
				firstIntoFill(room.get_id(), wk);
			} else if (Texas.STATUS_BET == wk.getStatus()) {// 自动加注
				autoFilling(room.get_id(), wk);
			} else if (Texas.STATUS_BETEND == wk.getStatus()) {// 加注结束
				if (wk.getPubPokers().size() == PUB_POKER_SIZE) {// 底牌已经发完了
					end(room.get_id(), wk);
				} else {
					dealPubPokers(room.get_id(), wk);
				}
			} else if (Texas.STATUS_SHOWDOWN_END == wk.getStatus()) {// 摊牌结束 重启
				restart(room, wk);
			}
		} catch (Exception e) {
			log.error("调用任务roomId=" + room.get_id() + ",", e);
		}
		return new ReMsg(ReCode.OK);
	}

	private static final double BUFFER = 0.05;// 抽红

	private static final int TIME_BUFFER = 2;// 缓冲时间
	private static final int TIME_DEAL = 3;// 发牌时间
	private static final int TIME_FILL = 15;// 加注时间
	private static final int TIME_END = 5;// 结束的展示时间
	private static final int TIME_TEMPLATE = 3;// 倒计时

	public static final int FILL_TYPE_ADDFILL = 1;// 加注
	public static final int FILL_TYPE_FILL = 2;// 跟注
	public static final int FILL_TYPE_GIVEUP = 3;// 弃牌
	public static final int FILL_TYPE_CHECK = 4;// 让牌 过
	public static final int FILL_TYPE_PUTALL = 5;// 梭哈

	public static final int PUB_POKER_SIZE = 5;// 公共牌数

	public static final int COUNT_MAX = 6;
	public static final int COUNT_MIN = 2;

	/** 游戏类型 */
	private MsgType getMsgType(Texas wk) {
		if (wk.getType() == TexasSetup.FOLD_LOW.getType()) {
			return MsgType.TEXAS_LOW;
		} else if (wk.getType() == TexasSetup.FOLD_HIGH.getType()) {
			return MsgType.TEXAS_HIGH;
		} else if (wk.getType() == TexasSetup.FOLD_MID.getType()) {
			return MsgType.TEXAS_MID;
		}
		return MsgType.DEFAULT;
	}

	/** 金币兑换成筹码 返回true false */
	private boolean transCoinToJetton(Texas wk, long uid, int coin) {
		ReCode rc = coinService.reduce(uid, CoinLog.TEXAS, wk.get_id(), coin, 0, "金币兑换筹码");
		return rc.reCode() == ReCode.OK.reCode();
	}

	/** 筹码兑换成金币 系统抽水 */
	private void transJettonToCoin(Texas wk, long uid, int jetton) {
		// 抽水放在结算时
		// jetton = (int) (jetton * (1 - BUFFER));
		coinService.addCoin(uid, CoinLog.TEXAS, wk.get_id(), jetton, 0, "筹码兑换金币");
	}

	/** 上树 */
	public ReMsg climbTheTree(long uid, long roomId) {
		Texas wk = super.getActivity(roomId, Texas.class);
		TexasActor wa = super.getRoomActor(roomId, uid, TexasActor.class);
		if (null == wa) {
			return new ReMsg(ReCode.USER_NOT_EXISTS);
		} else if (wa.getRole() != Actor.ROLE_EXECUTOR) {
			return new ReMsg(ReCode.ON_TREE_YET);
		}
		if (Texas.STATUS_READY == wk.getStatus() || !wk.getActors().keySet().contains(uid)) {
			// 游戏未开始 或者玩家不在本局游戏中
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
			// 筹码兑换金币
			transJettonToCoin(wk, uid, wk.getActorCoin(uid));
			wk.removeActorCoin(uid);
			wa.setUcStatus(Actor.A_STATUS_IN_ROOM);
			wk.removeActor(uid);
			wk.setSeatUserTable(seatUserTable);
			if (index != -1) {
				super.getRedisTemplate().opsForList().leftPush(RK.seatList(roomId), Integer.toString(index));
			}
			super.saveActivity(roomId, wk);
			super.saveRoomActor(roomId, wa);
			MapBody mb = new MapBody(SocketHandler.CLIMBTREE, SocketHandler.FN_USER_ID, uid)
					.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", wk.get_id()).append("emptyNo", index);
			super.pubRoomMsg(roomId, getMsgType(wk), mb);
		}
		return new ReMsg(ReCode.OK);
	}

	/** 下树 */
	public ReMsg getDownTheTree(long uid, long roomId, int seatNo) {
		if (lock(RK.seatLock(roomId), 1L)) {
			Texas wk = super.getActivity(roomId, Texas.class);
			TexasActor wa = super.getRoomActor(roomId, uid, TexasActor.class);
			if (null == wa) {
				return new ReMsg(ReCode.USER_NOT_EXISTS);
			}
			if (seatNo < 0 || seatNo > COUNT_MAX - 1) {// 人数上限
				return new ReMsg(ReCode.SEATNO_ERROR);
			}
			if (wa.getRole() == Actor.ROLE_VIEWER) {
				if (DboUtil.tryGetInt(userService.findById(uid), "coin") < getTexasAccessCoin(wk.getType())) {
					// 金币不够准入金币
					return new ReMsg(ReCode.COIN_BALANCE_LOW);
				}
				long[] seatTable = wk.getSeatUserTable();
				if (seatTable[seatNo] != 0) {
					unlock(RK.seatLock(roomId));
					return new ReMsg(ReCode.SEAT_BE_USE);
				} else {
					seatTable[seatNo] = uid;
					wk.setSeatUserTable(seatTable);
					if (Texas.STATUS_READY == wk.getStatus()) {// 准备状态下才能加入map
						wk.putActor(uid, TexasActor.A_STATUS_START);
						wa.setUcStatus(Actor.A_STATUS_READY);
					} else {
						wa.setUcStatus(Actor.A_STATUS_IN_ROOM);
					}
					// 金币兑换筹码
					int jetton = getTexasJetton(wk.getType());
					if (!transCoinToJetton(wk, uid, jetton)) {
						// 兑换金币失败 金币不足无法下树
						return new ReMsg(ReCode.COIN_BALANCE_LOW);
					}
					wk.putActorCoin(uid, jetton);
					wa.setSeat(seatNo);

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
				super.saveRoomActor(roomId, wa);
				MapBody mb = new MapBody(SocketHandler.GETDOWNTREE, SocketHandler.FN_USER_ID, uid)
						.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", wk.get_id()).append("seatNo", seatNo);
				super.pubRoomMsg(roomId, getMsgType(wk), mb);
				if (Texas.STATUS_READY == wk.getStatus()) {// 准备状态下才能检查开始
					checkStart(wk, roomId, false);
				}
			}
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.SEAT_BE_USE);
	}

	/** 游戏开始 */
	public ReMsg start(long roomId, Texas wk) {
		log.error("游戏开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		if (!lock(RK.outRoomLock(roomId), 1)) {
			return new ReMsg(ReCode.SOMEBODY_OUT);
		} else {
			int readyCount = wk.getActors().size();
			log.error("::::::::::::::Count()=" + readyCount);
			if (COUNT_MIN > readyCount) {// 座位上人数不足
				return new ReMsg(ReCode.SOMEONE_NOT_READY);
			}
			super.saveActivity(roomId, wk);
			Room room = super.getRoom(roomId);
			room.setStatus(Room.ACTIVITY);
			room.setActivityDate(System.currentTimeMillis());
			changeRoomCount(room, super.getRoomActors(room.get_id(), TexasActor.class), T_START);
			TexasActor actor = null;
			for (long uid : wk.getActors().keySet()) {
				actor = super.getRoomActor(roomId, uid, TexasActor.class);
				actor.setUcStatus(TexasActor.A_STATUS_START);
				super.saveRoomActor(roomId, actor);
			}
			// 开始发牌
			dealUserPokers(room.get_id(), wk);
			unlock(RK.outRoomLock(roomId));
		}
		return new ReMsg(ReCode.OK);
	}

	/** 发每个用户的两张牌 */
	public ReMsg dealUserPokers(long roomId, Texas wk) {
		log.error("dealUserPokers-----------" + roomId);
		wk.setStatus(Texas.STATUS_DEAL);// 设置为发牌阶段
		wk.setPlayUserCnt(wk.getActors().size());// 玩家人数
		int seat = wk.getSeat();
		boolean dealerLive = false;
		long[] userTable = wk.getSeatUserTable();
		for (int i = seat + 1; i < COUNT_MAX; i++) {// 选出庄的位置
			if (userTable[i] != 0) {
				seat = i;
				dealerLive = true;
				break;
			}
		}
		if (!dealerLive) {
			for (int i = 0; i < seat; i++) {
				if (userTable[i] != 0) {
					seat = i;
					break;
				}
			}
		}
		wk.setPlayUserCnt(wk.getActors().size());
		wk.setSeat(seat);
		List<Integer> pokers = PokerUtil.getShufflePoker2();// 获取扑克牌
		Set<Long> actors = wk.getActors().keySet();
		for (Long uid : actors) {// 初始化用户的两张张扑克牌
			wk.putUserPoker(uid, new int[] { pokers.remove(RandomUtil.nextInt(pokers.size())),
					pokers.remove(RandomUtil.nextInt(pokers.size())) });
		}
		System.out.println("初始化用户扑克牌结束");
		wk.setAllPokers(pokers);
		super.saveActivity(roomId, wk);
		// 金币池 用户的牌 庄家的座位 actors
		MapBody mb = new MapBody(SocketHandler.TEXAS_DEAL_USERPOKER, SocketHandler.FN_ROOM_ID, roomId)
				.append(SocketHandler.FN_LIMIT, TIME_DEAL).append("actId", wk.get_id())
				.append("coinPool", wk.getCoinPool()).append("userPokers", wk.getUserPokers())
				.append("seat", wk.getSeat()).append("baseCoin", wk.getBaseCoin())
				.append("actors", super.getRoomActors(roomId, TexasActor.class));
		super.pubRoomMsg(roomId, getMsgType(wk), mb);
		super.addJob(roomId, TIME_DEAL);
		System.out.println("dealUserPokers-----------end");
		return new ReMsg(ReCode.OK);
	}

	/** 计算分堆 */
	public void calculatePiles(Texas wk) {
		log.error("计算分堆-----------begin");
		int coinPool = wk.getCoinPool();
		List<Long[]> tempAllIn = wk.getTempAllIn();
		Map<Long, Integer> userBetCoins = wk.getUserBetCoins();
		List<Integer> pileCoins = wk.getPileCoins();// 堆金币
		Map<Integer, Map<Long, Integer>> pileCoinsFrom = wk.getPileCoinsFrom();// 堆金币来源
		Iterator<Long[]> iterator = tempAllIn.iterator();
		int index = 0;
		int allInCoin = 0;
		int pileCoin = 0;
		while (iterator.hasNext()) {
			Long[] next = iterator.next();
			pileCoin = 0;
			allInCoin = Integer.parseInt(next[1].toString());// 这个人AllIn的金币
			// 这个堆的金币来源
			Map<Long, Integer> coinsFrom = new HashMap<Long, Integer>();
			for (Entry<Long, Integer> entry : userBetCoins.entrySet()) {
				long uid = entry.getKey();
				int value = entry.getValue();
				if (value == 0) {
					continue;
				} else if (value <= allInCoin) {
					coinPool = coinPool - value;
					pileCoin = pileCoin + value;
					// 他押注的金币不够我的 证明他弃牌了 有多少要多少
					coinsFrom.put(uid, value);
					userBetCoins.put(uid, 0);
				} else {
					coinPool = coinPool - allInCoin;
					pileCoin = pileCoin + allInCoin;
					coinsFrom.put(uid, allInCoin);
					userBetCoins.put(uid, value - allInCoin);
				}
			}
			pileCoins.add(pileCoin);// 添加堆金币进入集合
			index = pileCoins.size() - 1;// 该堆下标
			pileCoinsFrom.put(index, coinsFrom);// 堆来源
			iterator.remove();
		}
		wk.setCoinPool(coinPool);// 刷新底池
		wk.setPileCoins(pileCoins);// 堆金币
		wk.setPileCoinsFrom(pileCoinsFrom);// 堆金币来源
		wk.clearTempAllIn();// 清除AllIn缓存
		System.out.println("计算分堆-----------end");
	}

	public Map<Long, String> getPokerDepict(Map<Long, Object[]> pokerScores) {
		Map<Long, String> pokerDepicts = new HashMap<Long, String>();
		for (Entry<Long, Object[]> entry : pokerScores.entrySet()) {
			pokerDepicts.put(entry.getKey(), entry.getValue()[1].toString());
		}
		return pokerDepicts;
	}

	/** 发底牌 */
	public ReMsg dealPubPokers(long roomId, Texas wk) {
		log.error("dealPubPokers-----------begin");
		boolean isPile = false;
		// TODO 用户堆数据的计算
		if (wk.getTempAllIn().size() != 0) {
			calculatePiles(wk);
			isPile = true;
		}
		List<Integer> pubPokers = wk.getPubPokers();
		int sendPokerCnt = 0;// 本回合发的公共牌数量
		if (pubPokers.size() == 0) {// 公共牌为空 发三张
			sendPokerCnt = 3;
		} else if (pubPokers.size() < PUB_POKER_SIZE) {// 有公共牌 再发一张
			sendPokerCnt = 1;
		} else {
			sendPokerCnt = 0;// 一张不发 准备结束
		}
		int giveUpCnt = 0;
		int putAllCnt = 0;
		int playUserCnt = 0;
		for (int status : wk.getActors().values()) {
			// 不是弃牌和梭哈 即可以继续玩的人
			if (status == FILL_TYPE_GIVEUP) {
				giveUpCnt++;
			} else if (status == FILL_TYPE_PUTALL) {
				putAllCnt++;
			} else {
				playUserCnt++;
			}
		}
		if (putAllCnt == wk.getActors().size() || putAllCnt + 1 == wk.getActors().size() || playUserCnt == 1) {
			// 只有一个人没梭哈或者都梭哈了 或者只有一个人在玩了 发完所有的底牌
			sendPokerCnt = PUB_POKER_SIZE - pubPokers.size();
		}
		if (giveUpCnt + 1 == wk.getActors().size() || sendPokerCnt == 0) {
			// 只有一个人没放弃 或者无牌可发 直接结束
			end(roomId, wk);
			return null;
		}
		wk.setPlayUserCnt(playUserCnt);
		wk.setInit(wk.getInit() + 1);// 回合数+1
		// 上回合下注用户的下注回合数+1
		for (long userId : wk.getTempBetUser()) {
			wk.addUserBetCnt(userId);
		}
		// 清空上回合下注用户
		wk.clearTempBetUser();
		System.out.println("开始发底牌-----------" + roomId);
		wk.setStatus(Texas.STATUS_DEAL);// 设置为发牌阶段
		List<Integer> allPokers = wk.getAllPokers();
		List<Integer> thisPokers = new ArrayList<Integer>();
		// 发底牌
		for (int i = 0; i < sendPokerCnt; i++) {
			Integer poker = allPokers.remove(RandomUtil.nextInt(allPokers.size()));
			thisPokers.add(poker);
			pubPokers.add(poker);
		}

		System.out.println("发底牌结束--------开始计算用户最新牌型:pubPokers.size:" + pubPokers.size());
		// TODO 计算用户最新的牌型得分
		Object[] score;
		for (long userId : wk.getActors().keySet()) {
			score = getScore(wk.getUserPoker(userId), pubPokers);
			System.out.println("score:size:" + score.length);
			for (Object object : score) {
				if (object != null) {
					System.out.println(object.toString());
				}
			}
			wk.putUserScore(userId, score);
		}
		System.out.println("最新牌型计算结束-----------end");
		super.saveActivity(roomId, wk);
		// 牌型 只推给用户最大牌型
		// 金币池 最新的公共牌 以及这次加进去的公共牌 每个用户的牌型分 用户的分堆数据
		MapBody mb = new MapBody(SocketHandler.TEXAS_DEAL_PUBPOKER, SocketHandler.FN_ROOM_ID, roomId)
				.append(SocketHandler.FN_LIMIT, TIME_DEAL).append("actId", wk.get_id())
				.append("coinPool", wk.getCoinPool()).append("pubPokers", wk.getPubPokers())
				.append("thisPokers", thisPokers).append("pileCoins", wk.getPileCoins())
				.append("pokerTypes", getPokerDepict(wk.getPokerScores())).append("userBetCoins", wk.getUserBetCoins())
				.append("isPile", isPile);
		super.pubRoomMsg(roomId, getMsgType(wk), mb);
		super.addJob(roomId, TIME_DEAL);
		System.out.println("dealPubPokers-----------end");
		return new ReMsg(ReCode.OK);
	}

	/** 发牌阶段之后开始加注 */
	public ReMsg firstIntoFill(long roomId, Texas wk) {
		if (wk.getPlayUserCnt() <= 1) {// 新的回合开始 只有一个人还能下注
			end(roomId, wk);
			return null;
		}
		log.error("开始fill-------" + roomId);
		List<Integer> pubPokers = wk.getPubPokers();
		int seat = wk.getSeat();
		wk.setStatus(Texas.STATUS_BET);
		if (pubPokers.isEmpty()) {// 证明是第一次发用户的牌 让大忙小忙下注
			// TODO 大小盲注消息合并 socket 355 待修改
			int baseCoin = wk.getBaseCoin();
			Map<Long, Integer> userAllCoins = wk.getUserAllCoins();
			Map<Long, Integer> betCoins = wk.getUserBetCoins();
			// 初始化发言集合
			initSpeakTable(seat, roomId, wk);
			long littleBlind = wk.getSpeakerTable().get(0);
			initSpeakTable(littleBlind, roomId, wk);
			long bigBlind = wk.getSpeakerTable().get(0);
			// 小盲 下注单倍
			userAllCoins.put(littleBlind, userAllCoins.get(littleBlind) - baseCoin);
			betCoins.put(littleBlind, baseCoin);
			// 大盲 下注双倍
			userAllCoins.put(bigBlind, userAllCoins.get(bigBlind) - baseCoin * 2);
			betCoins.put(bigBlind, baseCoin * 2);
			wk.setBoutCoin(baseCoin * 2);
			wk.setCoinPool(baseCoin * 3);
			wk.setUserBetCoins(betCoins);
			wk.setUserAllCoins(userAllCoins);
			// 大小盲下注消息
			System.out.println("大小忙下注------");
			MapBody mb = new MapBody(SocketHandler.TEXAS_BLIND, SocketHandler.FN_ROOM_ID, roomId)
					.append(SocketHandler.FN_LIMIT, TIME_BUFFER).append("actId", wk.get_id())
					.append("coinPool", wk.getCoinPool()).append("userAllCoins", wk.getUserAllCoins())
					.append("userBetCoins", wk.getUserBetCoins()).append("boutCoin", wk.getBoutCoin())
					.append("littleBlind", littleBlind).append("bigBlind", bigBlind);
			super.pubRoomMsg(roomId, getMsgType(wk), mb);
			// 初始化发言集合 从大盲下家发言
			initSpeakTable(bigBlind, roomId, wk);
		} else {// 初始化发言集合 从庄家开始发言
			wk.setBoutCoin(0);
			initSpeakTable(seat - 1, roomId, wk);
		}
		intoFilling(roomId, wk, false);
		System.out.println("fill结束-------" + roomId);
		return new ReMsg(ReCode.OK);
	}

	/** 初始化发言集合 */
	public void initSpeakTable(long uid, long roomId, Texas wk) {
		TexasActor actor = super.getRoomActor(roomId, uid, TexasActor.class);
		initSpeakTable(actor.getSeat(), roomId, wk);
	}

	/** 从这个座位之后的人发言 */
	public void initSpeakTable(int seat, long roomId, Texas wk) {
		log.error("initSpeakTable------------" + roomId);
		List<Long> speakerTable = wk.getSpeakerTable();
		speakerTable.clear();// 清除原来的数据
		long[] userTable = wk.getSeatUserTable();
		for (int i = seat + 1; i < COUNT_MAX; i++) {// 发言集合里边没有自己
			if (userTable[i] != 0) {
				int status = wk.getActors().get(userTable[i]);
				if (status != FILL_TYPE_PUTALL && status != FILL_TYPE_GIVEUP) {// 不是弃牌和梭哈
					speakerTable.add(userTable[i]);
				}
			}
		}
		for (int i = 0; i < seat; i++) {
			if (userTable[i] != 0) {
				int status = wk.getActors().get(userTable[i]);
				if (status != FILL_TYPE_PUTALL && status != FILL_TYPE_GIVEUP) {// 不是弃牌和梭哈
					speakerTable.add(userTable[i]);
				}
			}
		}
		wk.setSpeakerTable(speakerTable);
		super.saveActivity(roomId, wk);
		System.out.println("initSpeakTable------------发言顺序" + roomId);
		for (Long uid : speakerTable) {
			System.out.println(uid);
		}
		System.out.println("initSpeakTable------------END" + roomId);
	}

	/** 系统加注报点 */
	public ReMsg autoFilling(long roomId, Texas wk) {
		List<Long> table = wk.getSpeakerTable();
		if (table.size() > 0) {// 直接弃牌
			System.out.println("autoFilling----弃牌--------" + roomId + "," + table.get(0));
			filling(table.get(0), roomId, FILL_TYPE_GIVEUP, null);
		}
		return new ReMsg(ReCode.OK);
	}

	/** 用户下注 跟注 加注 弃牌 让 只有加注时 coin有值 */
	public ReMsg filling(long uid, long roomId, int type, Integer coin) {
		log.error("用户开始加注--------uid=" + uid + ",roomId=" + roomId + "," + "type=" + type + ",coin=" + coin);
		Texas wk = super.getActivity(roomId, Texas.class);
		if (wk.getStatus() != Texas.STATUS_BET) {// 下注阶段
			System.out.println("不是下注阶段 ------错误");
			return new ReMsg(ReCode.FAIL);
		}
		System.out.println("阶段检测通过----");
		List<Long> table = wk.getSpeakerTable();
		int coinPool = wk.getCoinPool();
		if (type != FILL_TYPE_CHECK && type != FILL_TYPE_GIVEUP) {
			// 不是弃牌也不是让牌 当作本回合加注
			wk.putTempBetUser(uid);
		}
		System.out.println("开始计算金钱");
		// TODO 计算金钱
		if (table.size() > 0 && table.get(0) == uid) {// 发言列表中有人 而且当前发言人正确
			Map<Long, Integer> userBetCoins = wk.getUserBetCoins();
			table.remove(uid);// 移除用户
			wk.setSpeakerTable(table);
			int boutCoin = wk.getBoutCoin();// 本回合最大金币
			int userCoin = wk.getActorCoin(uid);// 我的余额
			boolean succeed = false;
			if (type == FILL_TYPE_GIVEUP) {// 弃牌 游戏玩家减少一个
				succeed = true;
				System.out.println("弃牌 不作处理");
			} else if (type == FILL_TYPE_PUTALL) {// 梭哈
				System.out.println("梭哈");
				int hasBoutCoin = userBetCoins.get(uid) == null ? 0 : userBetCoins.get(uid);// 已下注筹码
				if (userCoin > 0) {
					succeed = true;
					System.out.println("梭哈成功！！！！");
					int newCoin = userCoin + hasBoutCoin;// 所有金币
					userBetCoins.put(uid, newCoin);// 设置本回合已下注金币
					wk.putActorCoin(uid, 0);// 设置新的金币余额
					coinPool = coinPool + userCoin;// 最新底池
					if (newCoin > boutCoin) {// 我最大
						boutCoin = newCoin;// 设置为最大筹码
					}
				}
			} else if (type == FILL_TYPE_CHECK) {// 让牌 无需操作
				if (boutCoin != 0) {// 无法让牌
					return new ReMsg(ReCode.FAIL);
				}
				succeed = true;
			} else if (type == FILL_TYPE_FILL || type == FILL_TYPE_ADDFILL) {// 跟注或者加注
				System.out.println("加注或者跟注！！！！");
				int hasBetCoin = userBetCoins.get(uid) == null ? 0 : userBetCoins.get(uid);// 已下注筹码
				int needCoin = 0;// 需要付出的金币
				if (boutCoin == 0 && type == FILL_TYPE_ADDFILL) {// 我是本回合第一个下注的
					System.out.println("本回合第一个下注的！！！");
					if (userCoin >= coin) {// 筹码足够
						boutCoin = hasBetCoin + coin;// 设置为最大筹码
						coinPool = coinPool + coin;// 最新底池
						initSpeakTable(uid, roomId, wk);// 初始化发言集合
						wk.putActorCoin(uid, userCoin - coin);// 设置新的金币余额
						userBetCoins.put(uid, boutCoin);// 增加已下注金币
						succeed = true;
					}
				} else {// 本回合已有人下注
					System.out.println("本回合有人下注！！！！");
					if (type == FILL_TYPE_FILL) {// 跟注
						if (boutCoin == 0) {// 无法跟注
							return new ReMsg(ReCode.FAIL);
						}
						needCoin = boutCoin - hasBetCoin;// 跟注所需金币
						if (userCoin >= needCoin) {// 筹码足够
							System.out.println("跟注成功！！！！！！！");
							coinPool = coinPool + needCoin;// 最新底池
							wk.putActorCoin(uid, userCoin - needCoin);// 设置新的金币余额
							userBetCoins.put(uid, hasBetCoin + needCoin);// 增加已下注金币
							succeed = true;
						}
					} else if (type == FILL_TYPE_ADDFILL) {// 加注
						needCoin = coin + boutCoin - hasBetCoin;// 加注所需金币
						if (userCoin >= needCoin) {// 筹码足够
							System.out.println("加注成功！！！！！！！！！！");
							coinPool = coinPool + needCoin;// 最新底池
							boutCoin = hasBetCoin + needCoin;// 设置为最大筹码
							initSpeakTable(uid, roomId, wk);// 初始化发言集合
							wk.putActorCoin(uid, userCoin - needCoin);// 设置新的金币余额
							userBetCoins.put(uid, boutCoin);// 增加已下注金币
							succeed = true;
						}
					}
				}
			}
			if (succeed) {
				wk.setCoinPool(coinPool);
				wk.setBoutCoin(boutCoin);
				userCoin = wk.getActorCoin(uid);// 我的余额
				System.out.println("开始判断是否满足梭哈");
				if (userCoin == 0 || type == FILL_TYPE_PUTALL) {
					// 没钱就是梭哈
					type = FILL_TYPE_PUTALL;
					wk.putActor(uid, FILL_TYPE_PUTALL);
					System.out.println("设置为梭哈");
					long allInCoin = userBetCoins.get(uid) == null ? 0 : userBetCoins.get(uid);
					List<Long[]> tempAllIn = wk.getTempAllIn();
					int index = -1;
					for (int i = 0; i < tempAllIn.size(); i++) {
						Long[] temp = tempAllIn.get(i);
						if (allInCoin < temp[1]) {
							index = i;
						}
					}
					// 按照大小顺序添加进临时allIn数组
					System.out.println("进行allIn数组计算");
					if (index == -1) {
						tempAllIn.add(new Long[] { uid, allInCoin });
					} else {
						tempAllIn.add(index, new Long[] { uid, allInCoin });
					}
				}
				System.out.println("梭哈计算结束！！！！！！");
				if (type == FILL_TYPE_GIVEUP || type == FILL_TYPE_PUTALL) {
					TexasActor actor = super.getRoomActor(roomId, uid, TexasActor.class);
					actor.setUcStatus(type);
					wk.putActor(uid, type);
					super.saveRoomActor(roomId, actor);
				}
				System.out.println("存储uc----");
				super.saveActivity(roomId, wk);
				MapBody mb = new MapBody(SocketHandler.TEXAS_BETTING, SocketHandler.FN_ROOM_ID, roomId)
						.append(SocketHandler.FN_LIMIT, TIME_BUFFER).append(SocketHandler.FN_USER_ID, uid)
						.append("type", type).append("coin", coin).append("coinPool", coinPool)
						.append("userAllCoins", wk.getUserAllCoins()).append("userBetCoins", wk.getUserBetCoins());
				super.pubRoomMsg(roomId, getMsgType(wk), mb);
				System.out.println("pushMsg结束-------");
				boolean end = false;
				if (type == FILL_TYPE_GIVEUP) {
					int giveUpCnt = 0;
					int putAllCnt = 0;
					int playUserCnt = 0;
					for (int status : wk.getActors().values()) {
						// 不是弃牌和梭哈 即可以继续玩的人
						if (status == FILL_TYPE_GIVEUP) {
							giveUpCnt++;
						} else if (status == FILL_TYPE_PUTALL) {
							putAllCnt++;
						} else {
							playUserCnt++;
						}
					}
					if (putAllCnt == wk.getActors().size() || putAllCnt + 1 == wk.getActors().size()
							|| playUserCnt == 1) {
						// 只有一个人能玩了 而且这把没人allIn 证明这把都是弃牌了 可以结束了
						if (wk.getTempAllIn().size() == 0) {// 这把没有人allIn
							end = true;
						}
					}
					if (giveUpCnt + 1 == wk.getActors().size()) {
						// 只有一个人没放弃
						end = true;
					}
				}
				intoFilling(roomId, wk, end);// 下一玩家下注
			}
		}
		System.out.println("计算下注结束------------------");
		return new ReMsg(ReCode.OK);
	}

	/** 下一个下注的人 */
	public ReMsg intoFilling(long roomId, Texas wk, boolean end) {
		System.out.println("intoFilling------------------");
		List<Long> speakerTable = wk.getSpeakerTable();
		if (speakerTable.size() == 0 || end) {// 下注结束
			wk.setStatus(Texas.STATUS_BETEND);
			addJob(roomId, TIME_BUFFER);
		} else if (speakerTable.size() > 0) {
			int playCnt = 0;
			for (int status : wk.getActors().values()) {
				// 不是弃牌和梭哈 即可以继续玩的人
				if (status != FILL_TYPE_GIVEUP || status != FILL_TYPE_PUTALL) {
					playCnt++;
				}
			}
			// 下一个下注的人 推送信息 金币池数量 自己跟注所需金币 用户所有的筹码
			MapBody mb = new MapBody(SocketHandler.TEXAS_BET, SocketHandler.FN_ROOM_ID, roomId)
					.append(SocketHandler.FN_LIMIT, TIME_FILL).append(SocketHandler.FN_USER_ID, speakerTable.get(0))
					.append("actId", wk.get_id()).append("coinPool", wk.getCoinPool())
					.append("userAllCoins", wk.getUserAllCoins()).append("userBetCoins", wk.getUserBetCoins())
					.append("boutCoin", wk.getBoutCoin()).append("canAddFill", playCnt > 1);
			super.pubRoomMsg(roomId, getMsgType(wk), mb);
			addJob(roomId, TIME_FILL);
		}
		super.saveActivity(roomId, wk);
		System.out.println("intoFilling------end！！！！");
		return new ReMsg(ReCode.OK);
	}

	/** 游戏结束阶段 */
	public void end(long roomId, Texas wk) {
		System.out.println("end------------------" + roomId);
		Object[] highPoker = null;// 高牌
		Object[] temp = null;// 中间牌
		Map<Long, Object[]> scores = wk.getPokerScores();
		for (long uid : wk.getActors().keySet()) {
			if (wk.getActors().get(uid) != FILL_TYPE_GIVEUP) {
				if (wk.getWinner().size() == 0) {
					wk.addWinner(uid);
					highPoker = scores.get(uid);
				} else {
					temp = scores.get(uid);
					if (temp == null) {
						continue;
					}
					if ((int) temp[0] < (int) highPoker[0]) {// 比我牌小
						continue;
					} else if ((int) temp[0] > (int) highPoker[0]) {// 比我牌大
						wk.clearWinner();
						wk.addWinner(uid);
						highPoker = temp;
					} else {// 牌型分相等 比较单牌
						List<Integer> poker1 = (List<Integer>) highPoker[3];
						List<Integer> poker2 = (List<Integer>) temp[3];
						boolean pokerEquals = true;
						if (poker1 != null && poker2 != null) {
							for (int i = 0; i < poker1.size(); i++) {
								if (poker1.get(i) > poker2.get(i)) {
									// 原来的牌大 不用操作
									pokerEquals = false;
									break;
								} else if (poker1.get(i) < poker2.get(i)) {
									pokerEquals = false;
									// 原来的牌小 替换赢家
									wk.clearWinner();
									wk.addWinner(uid);
									highPoker = temp;
									break;
								}
							}
							if (pokerEquals) {// 两者扑克牌完全相等
								wk.addWinner(uid);
							}
						}
					}
				}
			}
		}
		Map<Long, Integer> coinRes = wk.getCoinRes();
		List<Integer> pileCoins = wk.getPileCoins();
		Map<Integer, Map<Long, Integer>> pileCoinsFrom = wk.getPileCoinsFrom();
		// 最终输的人扣金币 筹码不动 赢的人加金币 筹码也对应往上加
		List<Long> tempWinUser = new ArrayList<Long>();
		Set<Long> winner = wk.getWinner();
		// 不是梭哈 就可以参与分底池
		for (Long winnerUid : winner) {
			if (wk.getActorStatus(winnerUid) != FILL_TYPE_PUTALL) {
				tempWinUser.add(winnerUid);
			}
		}
		if (tempWinUser.size() != 0) {// 平分底池金币
			int coinPool = wk.getCoinPool();
			for (long userId : tempWinUser) {
				coinRes.put(userId, coinPool / tempWinUser.size());
			}
		} else {// 底池金币退回
			Map<Long, Integer> userBetCoins = wk.getUserBetCoins();
			for (Entry<Long, Integer> entry : userBetCoins.entrySet()) {
				coinRes.put(entry.getKey(), entry.getValue());
			}
		}
		int pileCoin = 0;
		int coin = 0;
		long uid = 0;
		int value = 0;
		Map<Long, Integer> coinFrom = new HashMap<Long, Integer>();// 金币来源
		for (int i = pileCoins.size() - 1; i >= 0; i--) {// 堆金币
			tempWinUser.clear();
			pileCoin = pileCoins.get(i);
			coinFrom = pileCoinsFrom.get(i);
			for (long userId : coinFrom.keySet()) {
				if (winner.contains(userId)) {// 参与了堆的贡献 可以参与分配这个堆
					tempWinUser.add(userId);
				}
			}
			if (tempWinUser.size() != 0) {// 这几个人平分此堆
				for (long userId : tempWinUser) {
					coin = coinRes.get(userId) == null ? 0 : coinRes.get(userId);
					coin = coin + (pileCoin / tempWinUser.size());
					coinRes.put(userId, coin);
				}
			} else {// 无人参与 该堆金币退回
				for (Entry<Long, Integer> entry : coinFrom.entrySet()) {
					uid = entry.getKey();
					value = entry.getValue();
					coin = coinRes.get(uid) == null ? 0 : coinRes.get(uid);
					coin = coin + value;
					coinRes.put(uid, coin);
				}
			}
		}
		double buffer = 1 - BUFFER;
		Map<Long, Integer> userAllCoins = wk.getUserAllCoins();
		// 进行最终的筹码加减
		for (Entry<Long, Integer> entry : coinRes.entrySet()) {
			uid = entry.getKey();
			value = (int) (entry.getValue() * buffer);// 进行抽水
			coinRes.put(uid, value);
			if (value > 0) {// 保存赢币
				winCoinRankService.saveWinLog(uid, DateUtil.dateFormatyyyyMMdd(new Date()), value,
						ActivityType.TEXAX_POKER_FATHER.getType());
			}
			userAllCoins.put(uid, value + (userAllCoins.get(uid) == null ? 0 : userAllCoins.get(uid)));
		}
		int initCnt = wk.getInit();
		boolean win;
		int userBetCnt = 0;
		int showdownCnt = 0;
		for (long userId : wk.getActors().keySet()) {// 记录log
			userBetCnt = wk.getUserBetCnt(userId);// 参与下注的回合数
			temp = scores.get(userId);// 该用户的牌
			win = winner.contains(userId);// 是否胜利 即牌最大
			if (wk.getActorStatus(userId) != FILL_TYPE_GIVEUP) {// 不是弃牌
				showdownCnt = 1;
			} else {
				showdownCnt = 0;
			}
			// 保存Texaslog
			if (temp == null) {// 没有走到发底牌的时候 牌型是空的
				saveTexasLog(userId, null, 0, userBetCnt, initCnt, showdownCnt, null, win);
			} else {
				saveTexasLog(userId, (List<Integer>) temp[2], (int) temp[0], userBetCnt, initCnt, showdownCnt,
						(String) temp[1], win);
			}
		}

		wk.setCoinRes(coinRes);// 本局输赢结果
		wk.setUserAllCoins(userAllCoins);// 最新筹码
		wk.setStatus(Texas.STATUS_SHOWDOWN_END);
		wk.setUpdateTime(System.currentTimeMillis());
		super.saveActivity(roomId, wk);
		MapBody mb = new MapBody(SocketHandler.TEXAS_END, SocketHandler.FN_ROOM_ID, roomId)
				.append(SocketHandler.FN_LIMIT, TIME_END).append("actId", wk.get_id())
				.append("pokerTypes", getPokerDepict(wk.getPokerScores())).append("coinRes", coinRes)
				.append("userAllCoins", wk.getUserAllCoins());
		super.pubRoomMsg(roomId, getMsgType(wk), mb);
		super.getMongoTemplate().save(wk);
		System.out.println("end-------------end!!!!");
		super.addJob(roomId, TIME_END);
	}

	/** 重新开始 */
	public void restart(Room r, Texas wk) {
		System.out.println("restart:" + r.get_id());
		// TODO 重启时不在房间 进行筹码兑换成金币的操作
		Set<TexasActor> actors = getRoomActors(r.get_id(), TexasActor.class);
		int dealerSeat = wk.getSeat();
		wk.setStatus(Activity.STATUS_READY);
		Map<Long, Integer> userAllCoins = wk.getUserAllCoins();
		int type = wk.getType();
		long owner = wk.getOwner() == null ? 0 : wk.getOwner();
		super.delActivity(r.get_id());
		wk = super.getActivity(r.get_id(), Texas.class);
		wk.setSeat(dealerSeat);
		wk.getActors().clear();
		wk.setUserAllCoins(userAllCoins);
		wk.setType(type);
		wk.setBaseCoin(this.getTexasSetupByType(type).getBaseCoin());// 基础金币
		Set<TexasActor> initActors = new TreeSet<>();
		int robitCount = 0;
		Set<Integer> usedSeat = new TreeSet<>();
		long[] seatTable = new long[COUNT_MAX];
		for (TexasActor actor : actors) {
			// 是不在线的用户 不判断机器人
			if ((Actor.STATUS_ONLINE != actor.getStatus() || StringUtils
					.isBlank(super.getChatRedisTemplate().opsForValue().get(RK.imOnlineUser(actor.getUid()))))
					&& !actor.isRobit()) {
				super.delRoomActor(r.get_id(), actor.getUid());
				Integer coin = userAllCoins.get(actor.getUid());
				if (null != coin && coin >= 0) {
					// 不在线用户筹码兑换为金币给用户
					transJettonToCoin(wk, actor.getUid(), coin);
					wk.removeActorCoin(actor.getUid());
				}
				if (actor.getUid() == owner) {
					TexasActor newOua = getNewOwner(actors);
					if (newOua != null) {
						owner = newOua.getUid();
					}
				}
			} else {
				actor.setStatus(TexasActor.STATUS_ONLINE);
				actor.setUcStatus(TexasActor.A_STATUS_READY);
				if (actor.isRobit()) {
					robitCount++;
				}
				if (actor.getRole() == Actor.ROLE_EXECUTOR) {
					Integer coin = userAllCoins.get(actor.getUid()) == null ? 0 : userAllCoins.get(actor.getUid());
					boolean stayRoom = true;// 默认可以继续待在房间
					int jetton = getTexasJetton(wk.getType());
					if (coin < jetton) {// 如果筹码不足 加筹码
						stayRoom = transCoinToJetton(wk, actor.getUid(), jetton);
						if (stayRoom) {// 兑换成功
							userAllCoins.put(actor.getUid(), coin + jetton);
						}
					}
					if (stayRoom) {// 可以留在房间
						usedSeat.add(actor.getSeat());
						wk.putActor(actor.getUid(), TexasActor.A_STATUS_START);
						actor.setPlayCnt(actor.getPlayCnt() + 1);
						int seat = actor.getSeat();
						seatTable[seat] = actor.getUid();
					} else {// 如果筹码不足 且加筹码失败 筹码换为金币给用户
						transJettonToCoin(wk, actor.getUid(), userAllCoins.get(actor.getUid()));
						wk.removeActorCoin(actor.getUid());
						actor.setSeat(-1);
						actor.setRole(Actor.ROLE_VIEWER);// 变为观众
					}
				}
				initActors.add(actor);
				super.saveRoomActor(r.get_id(), actor);
			}
		}
		wk.setSeatUserTable(seatTable);
		for (TexasActor wa : initActors) {
			if (wa.getUid() == owner) {
				wa.setOwner(true);
				super.saveRoomActor(r.get_id(), wa);
			}
		}
		int seatSize = usedSeat.size();
		if (seatSize < COUNT_MAX) {
			List<String> list = new ArrayList<>();
			for (int i = 0; i < COUNT_MAX; i++) {
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
		MapBody mb = new MapBody(SocketHandler.ROOM_RESTART, SocketHandler.FN_ROOM_ID, r.get_id()).append("actors",
				initActors);
		long dt = System.currentTimeMillis();
		for (Actor actor : initActors) {
			super.pubRoomUserMsg(r.get_id(), actor.getUid(), MsgType.ROOM, mb, dt);
		}
		// 检查是否能够开始
		checkStart(wk, r.get_id(), true);
		System.out.println("restart------------end");
	}

	private ReMsg synchronizeDate(long uid, Room r, boolean bot) {
		if (r == null) {
			return new ReMsg(ReCode.FAIL);
		}
		TexasRoomInfo ri = new TexasRoomInfo(r.getType());
		long roomId = r.get_id();
		boolean hasKey = getRedisTemplate().hasKey(RK.roomUser(roomId));
		Texas wk = super.getActivity(roomId, Texas.class);
		if (!hasKey) {
			int type = r.getType();
			wk.setType(type);
			wk.setBaseCoin(this.getTexasSetupByType(type).getBaseCoin());// 基础金币
			String[] seats = new String[COUNT_MAX];
			List<String> list = new ArrayList<>();
			for (int i = 0; i < COUNT_MAX; i++) {
				list.add(Integer.toString(i));
			}
			Collections.shuffle(list);
			for (int i = 0; i < COUNT_MAX; i++) {
				seats[i] = list.get(i);
			}
			if (getRedisTemplate().opsForList().size(RK.seatList(roomId)) == 0) {
				getRedisTemplate().opsForList().leftPushAll(RK.seatList(roomId), seats);
			}
		}
		TexasActor wa = super.getRoomActor(roomId, uid, TexasActor.class);
		boolean firstIn = false;
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
			firstIn = true;
			DBObject user = userService.findById(uid);
			if (user == null) {
				ri.setCode(ReCode.FAIL.reCode());
				return new ReMsg(ReCode.FAIL, ri);
			}
			wa = new TexasActor();
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
			wa.setGame("德州扑克");
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
					int jetton = getTexasJetton(wk.getType());
					if (transCoinToJetton(wk, uid, jetton)) {// 兑换金币成功
						wk.putActorCoin(uid, jetton);
						seatTable[seatNo] = uid;
						wk.setSeatUserTable(seatTable);
						if (Room.OPEN == r.getStatus()) {// 游戏还没开始
							wk.putActor(uid, TexasActor.A_STATUS_START);
							wa.setUcStatus(Actor.A_STATUS_READY);
						} else {
							wa.setUcStatus(Actor.A_STATUS_IN_ROOM);
						}
						wa.setSeat(seatNo);
					} else {// 钱不够 还是观众
						wa.setRole(Actor.ROLE_VIEWER);
						wa.setSeat(-1);
					}
				}
			}
			if (null != r.getBuyUid() && uid == r.getBuyUid()) {// 房间购买人设置最高权限
				wa.setBuyUser(true);
			}
		}
		if (wa.isBuyUser() && (null == wk.getOwner() || wk.getOwner() != wa.getUid())) {// 房主不是房管
			if (null != wk.getOwner()) {// 原房管不为空
				TexasActor actor = super.getRoomActor(r.get_id(), wk.getOwner(), TexasActor.class);
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
		Set<TexasActor> as = super.getRoomActors(roomId, TexasActor.class);
		super.saveActivity(roomId, wk);
		ri.setPub(r.isPub());
		ri.setSys(r.isSys());// 系统房间或包间
		ri.setHasOwner(true);
		// 正在发言的人
		if (wk.getSpeakerTable().size() > 0) {
			ri.setSpeaker(wk.getSpeakerTable().get(0));
		}
		ri.setBaseCoin(wk.getBaseCoin());// 底分
		ri.setCoinPool(wk.getCoinPool());// 金币底池
		ri.setSeat(wk.getSeat());// 庄家座位
		ri.setPubPokers(wk.getPubPokers());// 公共牌
		ri.setUserPokers(wk.getUserPokers());// 用户的扑克牌
		ri.setStatus(wk.getStatus());
		ri.setUserAllCoins(wk.getUserAllCoins());// 用户剩余筹码
		ri.setPokerScores(getPokerDepict(wk.getPokerScores()));// 用户目前最大牌型
		ri.setUserBetCoins(wk.getUserBetCoins());// 用户押注金币
		ri.setPileCoins(wk.getPileCoins());// 已有分堆
		if (r.getStatus() == Room.OPEN) {
			ri.setStatus(Room.OPEN);
		} else {
			ri.setStatus(Room.ACTIVITY);
		}
		super.changeRoomCount(r, as, T_INROOM);
		MapBody mb = new MapBody(SocketHandler.IN_ROOM, SocketHandler.FN_USER_ID, wa.getUid())
				.append("nickname", wa.getNickname()).append("sex", wa.getSex()).append("avatar", wa.getAvatar())
				.append("actors", as).append("province", wa.getProvince()).append("city", wa.getCity())
				.append("role", wa.getRole()).append("userAllCoins", wk.getUserAllCoins());
		long dt = System.currentTimeMillis();
		for (Actor a : as) {
			if (uid != a.getUid()) {
				super.pubRoomUserMsg(r.get_id(), a.getUid(), MsgType.ROOM, mb, dt);
			}
		}
		ri.setActors(as);
		ri.setActId(wk.get_id());
		if (wa.getSeat() != -1 && firstIn) {// 用户第一次进入并坐下 检测开始
			checkStart(wk, roomId, false);
		}
		return new ReMsg(ReCode.OK, ri);
	}

	public void checkStart(Texas wk, long roomId, boolean isRestart) {
		int readyCount = wk.getActors().size();
		if ((isRestart && readyCount >= COUNT_MIN) || readyCount == COUNT_MIN) {
			this.addJob(roomId, TIME_TEMPLATE);
			MapBody mb = new MapBody(SocketHandler.ACTIVITY_READY).append(SocketHandler.FN_ROOM_ID, roomId)
					.append(SocketHandler.FN_LIMIT, TIME_TEMPLATE);
			super.pubRoomMsg(roomId, getMsgType(wk), mb);// 游戏开始倒计时
		}
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
			Texas wk = super.getActivity(roomId, Texas.class);
			TexasActor actor = super.getRoomActor(roomId, uid, TexasActor.class);
			boolean ucChange = false;
			if (null == actor) {
				unlock(RK.outRoomLock(r.get_id()));
				return new ReMsg(ReCode.ACTOR_ERROR);
			} else {
				if (Room.OPEN == r.getStatus() && Actor.ROLE_EXECUTOR == actor.getRole()) {
					if (TexasActor.A_STATUS_READY == actor.getUcStatus()
							|| TexasActor.A_STATUS_IN_ROOM == actor.getUcStatus()) {
						if (SocketHandler.KICK == type) {
							if (actor.isBuyUser()) {// 房主不能被踢
								return new ReMsg(ReCode.FAIL);
							}
							try {
								MapBody mb = new MapBody(type).append("nickname",
										super.getRoomActor(r.get_id(), getActivity(r.get_id(), Texas.class).getOwner())
												.getNickname());
								super.pubUserMsg(uid, MsgType.PROMPT, mb);
							} catch (Exception e) {
								log.error("发送踢人信息", e);
							}
						}
					}
					if (TexasActor.A_STATUS_READY == actor.getUcStatus()) {
						// 准备状态下退出房间 筹码转成金币
						transJettonToCoin(wk, uid, wk.getActorCoin(uid));
						wk.getActors().remove(uid);
						ucChange = true;
					}
					this.delRoomActor(roomId, uid);
					Set<TexasActor> as = super.getRoomActors(roomId, TexasActor.class);
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
						TexasActor newOwn = this.getNewOwner(as);
						pubOwnerChange(uid, newOwn, r, wk);
					}
				}
				if (Actor.ROLE_VIEWER == actor.getRole() || !wk.getActors().keySet().contains(actor.getUid())) {
					this.delRoomActor(roomId, actor.getUid());
					Set<TexasActor> as = super.getRoomActors(r.get_id(), TexasActor.class);
					super.changeRoomCount(r, as, T_OUTROOM);
				} else if (Room.ACTIVITY == r.getStatus() && Actor.ROLE_EXECUTOR == actor.getRole()) {
					if (SocketHandler.DISCONNECT != type) {
						actor.setStatus(Actor.STATUS_OUT_ROOM);
					} else {
						actor.setStatus(Actor.STATUS_OFFLINE);
					}
					this.saveRoomActor(roomId, actor);
				}
				MapBody mb = new MapBody(type, SocketHandler.FN_USER_ID, actor.getUid())
						.append("nickname", actor.getNickname()).append(SocketHandler.FN_ROOM_ID, roomId)
						.append("role", actor.getRole());
				this.pubRoomMsg(roomId, MsgType.ROOM, mb);
				if (ucChange) {
					super.saveActivity(r.get_id(), wk);
				}
				int outCount = 0;
				Set<TexasActor> actors = super.getRoomActors(r.get_id(), TexasActor.class);
				for (TexasActor ua : actors) {
					if (ua.getStatus() != TexasActor.STATUS_ONLINE || ua.isRobit()) {
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

	@Override
	public void closeRoom(final long roomId) {
		getRedisTemplate().delete(RK.seatList(roomId));
		super.closeRoom(roomId);
	}

	@Override
	public ReMsg getActorStatus(long uid, Room r) {
		TexasActor uca = super.getRoomActor(r.get_id(), uid, TexasActor.class);
		if (uca == null) {
			return new ReMsg(ReCode.OK, 1);
		} else {
			return new ReMsg(ReCode.OK, 2);
		}
	}

	@Override
	public ReMsg canInRoom(int type, long actId, long roomId) {
		Texas uc = super.getActivity(roomId, Texas.class);
		if (actId == uc.get_id()) {
			if (uc.getStatus() > Activity.STATUS_READY) {
				return new ReMsg(ReCode.OK, 1);
			}
		}
		return new ReMsg(ReCode.OK, 2);
	}

	@Override
	public ReMsg inRoom(long uid, Room r, int model, boolean robit) {
		return synchronizeDate(uid, r, robit);
	}

	private TexasActor getNewOwner(Set<TexasActor> as) {
		for (TexasActor a : as) {
			if (TexasActor.STATUS_ONLINE == a.getStatus() && Actor.ROLE_EXECUTOR == a.getRole()) {
				return a;
			}
		}
		return null;
	}

	private void pubOwnerChange(Long uid, TexasActor a, Room r, Texas wk) {
		if (uid != null) {
			TexasActor wa = super.getRoomActor(r.get_id(), uid, TexasActor.class);
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

	/** 查座位 */
	public int indexOf(long uid, long[] seat) {
		for (int i = 0; i < seat.length; i++) {
			if (uid == seat[i]) {
				return i;
			}
		}
		return -1;
	}

	private enum TexasSetup {
		FOLD_LOW(153, 50, 3000, 1000), FOLD_MID(154, 100, 30000, 5000), FOLD_HIGH(155, 200, 300000, 10000);
		TexasSetup(int type, int baseCoin, int accessCoin, int jetton) {
			this.type = type;
			this.baseCoin = baseCoin;
			this.accessCoin = accessCoin;
			this.jetton = jetton;
		}

		private int type;
		private int baseCoin;// 基础金币
		private int accessCoin;// 准入金币
		private int jetton;// 每次兑换的筹码

		public int getType() {
			return type;
		}

		public int getBaseCoin() {
			return baseCoin;
		}

		public int getAccessCoin() {
			return accessCoin;
		}

		public int getJetton() {
			return jetton;
		}
	}

	public TexasSetup getTexasSetupByType(int activityType) {
		if (activityType == ActivityType.TEXAS_LOW.getType()) {
			return TexasSetup.FOLD_LOW;
		} else if (activityType == ActivityType.TEXAS_HIGH.getType()) {
			return TexasSetup.FOLD_HIGH;
		} else if (activityType == ActivityType.TEXAS_MID.getType()) {
			return TexasSetup.FOLD_MID;
		}
		return null;
	}

	public int getTexasAccessCoin(int activityType) {
		TexasSetup texasSetup = this.getTexasSetupByType(activityType);
		if (null != texasSetup) {
			return texasSetup.getAccessCoin();
		}
		return 0;
	}

	public int getTexasJetton(int activityType) {
		TexasSetup texasSetup = this.getTexasSetupByType(activityType);
		if (null != texasSetup) {
			return texasSetup.getJetton();
		}
		return 0;
	}

	/**
	 * 根据给定的牌返回得分 pokers长度一定为4
	 * 
	 * index0分数 index1牌型 index2牌 index3剩余的单牌
	 * 
	 * pokers顺序 从大到小
	 */
	private Object[] getScore(List<Integer> pokers) {
		/**
		 * 皇家同花顺 同一花色最大的牌 70000 get
		 * 
		 * 同花顺 同一花色顺序的牌 6结束的顺子 55000,A结束的顺子 63000 get
		 * 
		 * 四条 四张点数一样+一个单张 最大 A 54000 最小2 42000 get
		 * 
		 * 葫芦 三张点数一样+两个点数一样 最大A 44000 最小2 32000 get
		 * 
		 * 同花 五张一样颜色的牌 最大A 31000 最小7 24000
		 * 
		 * 顺子 五张顺序的牌 6结束的顺子 15000,A结束的顺子 23000 get
		 * 
		 * 三条 三张点数一样+两张点数不一样 2000-14000 get
		 * 
		 * 两对 两张点数一样 两张点数一样的+单张 0202-1414 get
		 * 
		 * 一对 只有一对 高牌 四位 20-140 get
		 * 
		 * 高牌 啥都没有 按最大的来 两位分 2-14
		 */
		Map<Integer, Integer> temp = new HashMap<Integer, Integer>();
		for (int poker : pokers) {
			poker = poker / 10;
			Integer pokerCnt = temp.get(poker) == null ? 1 : temp.get(poker) + 1;
			temp.put(poker, pokerCnt);
		}
		if (temp.size() == 2) {// 只有两个 也就是说是葫芦或者四条
			for (Entry<Integer, Integer> entry : temp.entrySet()) {
				if (entry.getValue() == 4) {// 四条
					return new Object[] { (entry.getKey() + 40) * 1000, "四条", pokers, null };
				} else if (entry.getValue() == 3) {// 葫芦
					return new Object[] { (entry.getKey() + 30) * 1000, "葫芦", pokers, null };
				}
			}
		} else if (temp.size() == 3) {// 只有三种牌 三条或者两对
			for (Entry<Integer, Integer> entry : temp.entrySet()) {
				if (entry.getValue() == 3) {// 三条
					return new Object[] { entry.getKey() * 1000, "三条", pokers, null };
				}
			}
			// 两对
			int high = 0;
			int low = 0;
			for (Entry<Integer, Integer> entry : temp.entrySet()) {
				if (entry.getValue() == 2) {// 两对 //查看哪个对子大
					if (high == 0 && low == 0) {
						high = entry.getKey();
						low = entry.getKey();
					} else {
						high = entry.getKey() > high ? entry.getKey() : high;
						low = entry.getKey() < low ? entry.getKey() : low;
					}
				}
			}
			List<Integer> remainPokers = new ArrayList<Integer>();
			for (int i = 0; i < pokers.size(); i++) {
				if (pokers.get(i) / 10 != high && pokers.get(i) / 10 != low) {
					// 最后一个单牌
					remainPokers.add(pokers.get(i));
				}
			}
			return new Object[] { high * 100 + low, "两对", pokers, remainPokers };
		} else if (temp.size() == 4) {// 只有一对
			for (Entry<Integer, Integer> entry2 : temp.entrySet()) {
				if (entry2.getValue() == 2) {// 对子
					List<Integer> remainPokers = new ArrayList<Integer>();
					for (int i = 0; i < pokers.size(); i++) {
						if (pokers.get(i) / 10 != entry2.getKey()) {
							// 单牌
							remainPokers.add(pokers.get(i));
						}
					}
					return new Object[] { entry2.getKey() * 10, "一对", pokers, remainPokers };
				}
			}
		} else {// 没有重复的
			int zero = pokers.get(0) / 10;
			int one = pokers.get(1) / 10;
			int two = pokers.get(2) / 10;
			int three = pokers.get(3) / 10;
			int four = pokers.get(4) / 10;
			boolean straight = false;// 顺子
			boolean flush = false;// 同花
			// 顺子
			if (zero - 1 == one && one - 1 == two && two - 1 == three && three - 1 == four) {
				straight = true;
			}
			// 同花
			if (pokers.get(0) % 10 == pokers.get(1) % 10 && pokers.get(1) % 10 == pokers.get(2) % 10
					&& pokers.get(2) % 10 == pokers.get(3) % 10 && pokers.get(3) % 10 == pokers.get(4) % 10) {
				flush = true;
			}
			if (straight && flush) {// 同花顺
				if (zero == 14) {// A结束的顺子
					return new Object[] { 70000, "皇家同花顺", pokers, null };
				} else {
					return new Object[] { (zero + 49) * 1000, "同花顺", pokers, null };
				}
			}
			if (flush) {// 同花
				return new Object[] { (zero + 17) * 1000, "同花", pokers, null };
			} else if (straight) {// 顺子
				return new Object[] { (zero + 9) * 1000, "顺子", pokers, null };
			} else {// 什么都没有的垃圾牌
				List<Integer> remainPokers = new ArrayList<Integer>();
				for (int i = 1; i < pokers.size(); i++) {// 去除第一个最大的牌
					remainPokers.add(pokers.get(i));
				}
				return new Object[] { zero, "高牌", pokers, remainPokers };
			}
		}
		return null;
	}

	/** 完全比较两种扑克牌 返回较大的一个 */
	private Object[] comparePoker(Object[] poker1, Object[] poker2) {
		// 牌型分比较
		if ((int) poker1[0] < (int) poker2[0]) {
			return poker2;
		}
		if ((int) poker2[0] < (int) poker1[0]) {
			return poker1;
		}
		// 单牌比较
		if (poker1[3] != null && poker2[3] != null) {
			List<Integer> akj1 = (List<Integer>) poker1[3];
			List<Integer> akj2 = (List<Integer>) poker2[3];
			if (akj1 != null && akj2 != null) {
				for (int i = 0; i < akj1.size(); i++) {
					if (akj1.get(i) > akj2.get(i)) {
						// 原来的牌大 不用操作
						return poker1;
					} else if (akj1.get(i) < akj2.get(i)) {
						return poker2;
					}
				}
			}
			// 两者完全相等
		}
		return poker1;
	}

	/** 第一位分数 第二位牌型文字描述 第三位选出来的五张牌 第四位剩余的单牌 */
	private Object[] getScore(int[] userPokers, List<Integer> pubPokers) {
		System.out.println("userPokers:" + userPokers[0] + userPokers[1]);
		System.out.println("pubPokers--------------");
		for (int s : pubPokers) {
			System.out.println(s);
		}
		System.out.println("pubPokers-------end!!!!");
		List<Integer> pokers = new ArrayList<Integer>();
		pokers.addAll(pubPokers);
		pokers.add(userPokers[0]);
		pokers.add(userPokers[1]);
		// 按照从大到小排序
		Collections.sort(pokers);
		Collections.reverse(pokers);
		Object[] resScore = new Object[] { -1, "默认", new ArrayList<Integer>(), null };
		if (pokers.size() == 5) {// 直接五位计算
			resScore = comparePoker(resScore, getScore(pokers));
		} else {
			List<Integer> temp = new ArrayList<Integer>();
			if (pokers.size() == 6) {// 每次不取其中一位计算
				for (int i = 0; i < pokers.size(); i++) {
					temp.clear();
					for (int index = 0; index < pokers.size(); index++) {
						if (index != i) {// 不取这一个
							temp.add(pokers.get(index));
						}
					}
					resScore = comparePoker(resScore, getScore(temp));
				}
			} else if (pokers.size() == 7) {// 每次不取其中两位位计算
				for (int i = 0; i < pokers.size(); i++) {
					for (int j = i + 1; j < pokers.size(); j++) {
						temp.clear();
						for (int index = 0; index < pokers.size(); index++) {
							if (index != i && index != j) {// 不取这两个
								temp.add(pokers.get(index));
							}
						}
						resScore = comparePoker(resScore, getScore(temp));
					}
				}
			}
		}
		return resScore;
	}

	/**
	 * 存储log betCnt 自己下注回合数
	 * 
	 * boutCnt 总回合数
	 * 
	 * showdownCnt 是否活到摊牌
	 */
	public void saveTexasLog(long uid, List<Integer> pokers, int pokersScore, int betCnt, int boutCnt, int showdownCnt,
			String pokersStr, boolean win) {
		DBObject dbo = findTexasLog(uid);
		TexasLog texasLog;
		if (null == dbo) {
			if (win) {// 胜利
				texasLog = new TexasLog(pokersScore, pokers, pokersStr, 1, 0, showdownCnt, 1, betCnt, boutCnt);
			} else {// 失败
				texasLog = new TexasLog(pokersScore, pokers, pokersStr, 0, 1, showdownCnt, 1, betCnt, boutCnt);
			}
			texasLog.set_id(uid);
			texasLog.setUpdateTime(System.currentTimeMillis());
		} else {
			texasLog = DboUtil.toBean(dbo, TexasLog.class);
			texasLog.setUpdateTime(System.currentTimeMillis());
			texasLog.setAllCnt(texasLog.getAllCnt() + 1);
			if (win) {
				texasLog.setWinCnt(texasLog.getWinCnt() + 1);
			} else {
				texasLog.setLoseCnt(texasLog.getLoseCnt() + 1);
			}
			texasLog.setBetCnt(texasLog.getBetCnt() + betCnt);// 下注场次
			texasLog.setBoutCnt(texasLog.getBoutCnt() + boutCnt);// 总回合场次
			texasLog.setShowdownCnt(texasLog.getShowdownCnt() + showdownCnt);// 摊牌场次
			if (pokersScore > texasLog.getPokersScore()) {
				texasLog.setPokersScore(pokersScore);
				texasLog.setPokers(pokers);
				texasLog.setPokersStr(pokersStr);
			}
		}
		super.getMongoTemplate().save(texasLog);
	}

	public DBObject findTexasLog(long uid) {
		return super.getC(DocName.TEXAS_LOG).findOne(new BasicDBObject("_id", uid));
	}

	/** 德州首页的个人信息 */
	public DBObject getTexasLog(long uid) {
		DBObject dbo = findTexasLog(uid);
		if (dbo != null) {// 胜率
			dbo.put("winRate", DboUtil.toBean(dbo, TexasLog.class).getWinRate() + "%");
			dbo.put("betRate", DboUtil.toBean(dbo, TexasLog.class).getBetRate() + "%");
			dbo.put("showdownRate", DboUtil.toBean(dbo, TexasLog.class).getShowdownRate() + "%");
		}
		return dbo;
	}

	/** 统计24小时内玩德州的人 */
	@SuppressWarnings("unchecked")
	public List<Long> find24hTexasPlayer() {
		BasicDBObject q = new BasicDBObject("updateTime",
				new BasicDBObject("$gt", System.currentTimeMillis() - Const.DAY));
		return super.getC(DocName.TEXAS_LOG).distinct("_id", q);
	}

}
