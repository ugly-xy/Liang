package com.zb.service.room;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
import com.zb.models.CowLog;
import com.zb.models.DocName;
import com.zb.models.finance.CoinLog;
import com.zb.models.room.Activity;
import com.zb.models.room.Actor;
import com.zb.models.room.Room;
import com.zb.models.room.activity.Cow;
import com.zb.models.room.activity.CowActor;
import com.zb.service.UserService;
import com.zb.service.WinCoinRankService;
import com.zb.service.room.vo.CowRoomInfo;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.MsgType;
import com.zb.socket.model.SocketHandler;

@Service
public class CowService extends RoomService {

	@Autowired
	UserService userService;

	@Autowired
	WinCoinRankService winCoinRankService;

	@Override
	public ReMsg handle(Room room, long fr) {
		try {
			Cow wk = super.getActivity(room.get_id(), Cow.class);
			if (Cow.STATUS_READY == wk.getStatus()) {// 开始游戏
				start(room.get_id(), wk);
			} else if (Cow.STATUS_DEAL == wk.getStatus()) {// 进入叫庄阶段
				firstHog(room.get_id(), wk);
			} else if (Cow.STATUS_ROBBANKER == wk.getStatus()) {// 自动叫庄
				autoHoging(room.get_id(), wk);
			} else if (Cow.STATUS_ROBBANKER_END == wk.getStatus()) {// 进入加注阶段
				firstFill(room.get_id(), wk);
			} else if (Cow.STATUS_FILLER == wk.getStatus()) {// 自动加注
				autoFilling(room.get_id(), wk);
			} else if (Cow.STATUS_FILLER_END == wk.getStatus()) {// 摊牌阶段
				end(room.get_id(), wk);
			} else if (Cow.STATUS_SHOWDOWN_END == wk.getStatus()) {// 摊牌结束 重启
				restart(room, wk);
			}
		} catch (Exception e) {
			log.error("调用任务roomId=" + room.get_id() + ",", e);
		}
		return new ReMsg(ReCode.OK);
	}

	public static final int BOMB = 1000000;// 炸弹
	public static final int MIN_FIVE = 40000;// 五小
	public static final int HUA_FIVE = 30000;// 五花
	public static final int HUA_FOUR = 20000;// 四花
	public static final int NIU_BASE = 1000;// 牛基础分

	private static final double BUFFER = 0.05;// 抽红

	private static final int TIME_BUFFER = 2;// 缓冲时间
	private static final int TIME_DEAL = 3;// 发牌时间
	private static final int TIME_HOG = 15;// 抢庄时间
	private static final int TIME_FILL = 15;// 加注时间
	private static final int TIME_END = 5;// 结束的展示时间
	private static final int TIME_TEMPLATE = 3;// 游戏开始倒计时

	public static final int COUNT_MAX = 6;
	public static final int COUNT_MIN = 2;

	/** 游戏类型 */
	private MsgType getMsgType(Cow wk) {
		if (wk.getType() == CowSetup.FOLD_LOW.getType()) {
			return MsgType.COW_LOW;
		} else if (wk.getType() == CowSetup.FOLD_HIGH.getType()) {
			return MsgType.COW_HIGH;
		}
		return MsgType.DEFAULT;
	}

	/** 上树 */
	public ReMsg climbTheTree(long uid, long roomId) {
		Cow wk = super.getActivity(roomId, Cow.class);
		CowActor wa = super.getRoomActor(roomId, uid, CowActor.class);
		if (null == wa) {
			return new ReMsg(ReCode.USER_NOT_EXISTS);
		} else if (wa.getRole() != Actor.ROLE_EXECUTOR) {
			return new ReMsg(ReCode.ON_TREE_YET);
		}
		if (Cow.STATUS_READY == wk.getStatus() || !wk.getActors().contains(uid)) {
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
			Cow wk = super.getActivity(roomId, Cow.class);
			CowActor wa = super.getRoomActor(roomId, uid, CowActor.class);
			if (null == wa) {
				return new ReMsg(ReCode.USER_NOT_EXISTS);
			}
			if (seatNo < 0 || seatNo > COUNT_MAX - 1) {// 人数上限
				return new ReMsg(ReCode.SEATNO_ERROR);
			}
			if (wa.getRole() == Actor.ROLE_VIEWER) {
				if (DboUtil.tryGetInt(userService.findById(uid), "coin") < getCowAccessCoin(wk.getType())) {
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
					if (Cow.STATUS_READY == wk.getStatus()) {// 准备状态下才能加入map
						wk.putActor(uid);
					}
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
				super.saveRoomActor(roomId, wa);
				MapBody mb = new MapBody(SocketHandler.GETDOWNTREE, SocketHandler.FN_USER_ID, uid)
						.append(SocketHandler.FN_ROOM_ID, roomId).append("actId", wk.get_id()).append("seatNo", seatNo);
				super.pubRoomMsg(roomId, getMsgType(wk), mb);
				if (Cow.STATUS_READY == wk.getStatus()) {// 准备状态下才能检查开始
					checkStart(wk, roomId, false);
				}
			}
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.SEAT_BE_USE);
	}

	/** 游戏开始 */
	public ReMsg start(long roomId, Cow wk) {
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
			changeRoomCount(room, super.getRoomActors(room.get_id(), CowActor.class), T_START);
			// 开始发牌
			deal(room.get_id(), wk);
			unlock(RK.outRoomLock(roomId));
		}
		return new ReMsg(ReCode.OK);
	}

	/** 发牌 */
	public ReMsg deal(long roomId, Cow wk) {
		wk.setStatus(Cow.STATUS_DEAL);// 设置为发牌阶段
		List<Integer> pokers = PokerUtil.getShufflePoker();// 获取扑克牌
		Set<Long> actors = wk.getActors();
		for (Long uid : actors) {// 初始化用户的三张扑克牌
			wk.putUserPoker(uid,
					new int[] { pokers.remove(RandomUtil.nextInt(pokers.size())),
							pokers.remove(RandomUtil.nextInt(pokers.size())),
							pokers.remove(RandomUtil.nextInt(pokers.size())), 0, 0 });
		}
		wk.setPokers(pokers);
		super.saveActivity(roomId, wk);
		MapBody mb = new MapBody(SocketHandler.COW_DEAL, SocketHandler.FN_ROOM_ID, roomId)
				.append(SocketHandler.FN_LIMIT, TIME_DEAL).append("actId", wk.get_id())
				.append("baseCoin", wk.getBaseCoin()).append("userPokers", wk.getUserPokers());
		super.pubRoomMsg(roomId, getMsgType(wk), mb);
		super.addJob(roomId, TIME_DEAL);
		return new ReMsg(ReCode.OK);
	}

	/** 第一次进入抢庄阶段 */
	public ReMsg firstHog(long roomId, Cow wk) {
		// 初始化数据
		long[] userTable = wk.getSeatUserTable();// 座位
		List<Long> table = wk.getSpeakerTable();
		Set<Long> actors = wk.getActors();
		table.clear();
		int firstSeat = (int) wk.getDealer();
		if (firstSeat == 0) {// 随机选出第一个发言的座位
			firstSeat = RandomUtil.nextInt(COUNT_MAX);
		} else {
			firstSeat = firstSeat == 6 ? 0 : firstSeat;
		}
		for (int i = firstSeat; i < COUNT_MAX; i++) {
			if (actors.contains(userTable[i])) {
				table.add(userTable[i]);
			}
		}
		for (int i = 0; i < firstSeat; i++) {
			if (actors.contains(userTable[i])) {
				table.add(userTable[i]);
			}
		}
		wk.setDealer(0);
		wk.setSpeakerTable(table);// 发言顺序
		wk.setStatus(Cow.STATUS_ROBBANKER);// 抢庄阶段
		super.saveActivity(roomId, wk);
		// 进入抢庄阶段
		intoHog(roomId, wk, 0);
		return null;
	}

	/** 进入抢庄阶段 */
	public ReMsg intoHog(long roomId, Cow wk, int fold) {
		List<Long> speakerTable = wk.getSpeakerTable();
		if (speakerTable.size() == 0 || fold == 3) {// 下边没人了或者是已到达最大倍数 庄家产生
			putDealer(roomId, wk);
			wk.setStatus(Cow.STATUS_ROBBANKER_END);
			addJob(roomId, TIME_BUFFER);// 距离用户加注倒计时
		} else if (speakerTable.size() > 0) {
			for (Long uid : speakerTable) {// 发言顺序
				MapBody mb = new MapBody(SocketHandler.COW_HOG, SocketHandler.FN_ROOM_ID, roomId)
						.append(SocketHandler.FN_LIMIT, TIME_HOG).append(SocketHandler.FN_USER_ID, uid)
						.append("actId", wk.get_id()).append("folds", wk.getFolds())
						.append("baseCoin", wk.getBaseCoin());
				super.pubRoomMsg(roomId, getMsgType(wk), mb);
				break;
			}
			addJob(roomId, TIME_HOG);
		}
		super.saveActivity(roomId, wk);
		return new ReMsg(ReCode.OK);
	}

	/** 用户抢庄报点 */
	public ReMsg hoging(long uid, long roomId, int fold) {
		Cow wk = super.getActivity(roomId, Cow.class);
		List<Long> table = wk.getSpeakerTable();
		if (table.size() > 0 && table.get(0) == uid) {// 发言列表中有人 而且当前发言人正确
			DBObject user = userService.findById(uid);
			if (DboUtil.tryGetInt(user, "coin") < wk.getBaseCoin() * fold * (wk.getActors().size() - 1)) {
				// 金币不足 不能抢庄
				return new ReMsg(ReCode.COIN_BALANCE_LOW);
			}
			table.remove(0);// 移除该叫点的用户
			wk.putUserFold(uid, fold);// 放置叫点
			wk.setSpeakerTable(table);
			MapBody mb = new MapBody(SocketHandler.COW_HOGING, SocketHandler.FN_ROOM_ID, roomId)
					.append(SocketHandler.FN_LIMIT, TIME_BUFFER).append(SocketHandler.FN_USER_ID, uid)
					.append("actId", wk.get_id()).append("folds", wk.getFolds()).append("fold", fold);
			super.pubRoomMsg(roomId, getMsgType(wk), mb);
			// 临时进行谁是庄家的判断
			if (fold != 0) {// 不是放弃抢庄
				if (wk.getDealer() == 0 || wk.getUserFold(wk.getDealer()) < fold) {
					// 没有庄家 或者是现庄家的点数比我小
					wk.setDealer(uid);
				}
			}
			intoHog(roomId, wk, fold);// 下一玩家抢庄报点
		}
		return new ReMsg(ReCode.OK);
	}

	/** 系统抢庄报点 */
	public ReMsg autoHoging(long roomId, Cow wk) {
		List<Long> table = wk.getSpeakerTable();
		if (table.size() > 0) {// 默认为不抢庄 即0点
			hoging(table.get(0), roomId, 0);
		}
		return new ReMsg(ReCode.OK);
	}

	/** 产生庄家socket */
	public void putDealer(long roomId, Cow wk) {
		boolean sysRandom = false;
		int dealerFold;
		if (wk.getDealer() == 0) {// 还没有庄家产生
			sysRandom = true;
			dealerFold = 1;
			List<Long> ids = new ArrayList<>(wk.getActors());
			wk.setDealer(ids.get(RandomUtil.nextInt(ids.size())));
		} else {
			dealerFold = wk.getUserFold(wk.getDealer());
		}
		wk.clearFolds();
		// 存储庄家的倍数
		wk.putUserFold(wk.getDealer(), dealerFold);
		wk.setBaseCoin(dealerFold * wk.getBaseCoin());// 底分
		super.saveActivity(roomId, wk);
		// 庄家产生
		MapBody mb = new MapBody(SocketHandler.COW_HOG_COMEOUT, SocketHandler.FN_ROOM_ID, roomId)
				.append(SocketHandler.FN_LIMIT, TIME_BUFFER).append("dealer", wk.getDealer())
				.append("actId", wk.get_id()).append("fold", dealerFold).append("baseCoin", wk.getBaseCoin())
				.append("sys", sysRandom);// 底分
		super.pubRoomMsg(roomId, getMsgType(wk), mb);
	}

	/** 第一次进入加注阶段 */
	public ReMsg firstFill(long roomId, Cow wk) {
		long[] userTable = wk.getSeatUserTable();
		List<Long> table = wk.getSpeakerTable();
		Set<Long> actors = wk.getActors();
		table.clear();
		int seat = super.getRoomActor(roomId, wk.getDealer(), CowActor.class).getSeat();
		long dealer = wk.getDealer();
		for (int i = seat + 1; i < COUNT_MAX; i++) {
			if (actors.contains(userTable[i]) && userTable[i] != dealer) {
				table.add(userTable[i]);
			}
		}
		for (int i = 0; i < seat; i++) {
			if (actors.contains(userTable[i]) && userTable[i] != dealer) {
				table.add(userTable[i]);
			}
		}
		table.remove(dealer);
		wk.setSpeakerTable(table);// 能加注的人 即闲家
		wk.setStatus(Cow.STATUS_FILLER);// 加注阶段
		super.saveActivity(roomId, wk);
		MapBody mb = new MapBody(SocketHandler.COW_FILL, SocketHandler.FN_ROOM_ID, roomId)
				.append(SocketHandler.FN_LIMIT, TIME_FILL).append("actId", wk.get_id()).append("folds", wk.getFolds())
				.append("baseCoin", wk.getBaseCoin()).append("speakerTable", table);
		super.pubRoomMsg(roomId, getMsgType(wk), mb);
		addJob(roomId, TIME_FILL);
		return new ReMsg(ReCode.OK);
	}

	/** 用户加注报点 */
	public ReMsg filling(long uid, long roomId, int fold) {
		Cow wk = super.getActivity(roomId, Cow.class);
		List<Long> table = wk.getSpeakerTable();
		if (wk.getStatus() == Cow.STATUS_FILLER) {// 加注阶段
			if (table.contains(uid)) {// 此人可以加注
				table.remove(uid);// 移除该叫点的用户
				if (wk.getUserFolds().containsKey(uid)) {
					return new ReMsg(ReCode.FAIL);
				}
				DBObject user = userService.findById(uid);
				if (DboUtil.tryGetInt(user, "coin") < wk.getBaseCoin() * fold) {
					// 金币不足 不能加注
					return new ReMsg(ReCode.COIN_BALANCE_LOW);
				}
				wk.putUserFold(uid, fold);// 放置叫点
				wk.setSpeakerTable(table);
				MapBody mb = new MapBody(SocketHandler.COW_FILLING, SocketHandler.FN_ROOM_ID, roomId)
						.append(SocketHandler.FN_USER_ID, uid).append("actId", wk.get_id())
						.append("folds", wk.getFolds()).append("fold", fold);
				super.pubRoomMsg(roomId, getMsgType(wk), mb);
				super.saveActivity(roomId, wk);
			}
			if (table.size() == 0) {// 全部加注完毕 准备进入摊牌阶段
				wk.setStatus(Cow.STATUS_FILLER_END);
				addJob(roomId, TIME_BUFFER);
				super.saveActivity(roomId, wk);
			}
		}
		return new ReMsg(ReCode.OK);
	}

	/** 系统加注报点 */
	public ReMsg autoFilling(long roomId, Cow wk) {
		for (Long uid : wk.getSpeakerTable()) {// 自动加注
			filling(uid, roomId, 1);
		}
		return new ReMsg(ReCode.OK);
	}

	/** 游戏结束阶段 */
	public void end(long roomId, Cow wk) {
		Map<Long, Integer> coinRes = wk.getCoinRes();// 金币结果
		Map<Long, String> pokerTypes = new HashMap<Long, String>();// 每个人的牌型
		// 先给每位玩家再分配两张牌
		Map<Long, int[]> userPokers = wk.getUserPokers();
		Map<Long, Object[]> pokerScores = wk.getPokerScores();
		List<Integer> pokers = wk.getPokers();
		int[] poker;
		for (Long uid : userPokers.keySet()) {// 再给每个人分配两张牌
			poker = userPokers.get(uid);
			poker[3] = pokers.remove(RandomUtil.nextInt(pokers.size()));
			poker[4] = pokers.remove(RandomUtil.nextInt(pokers.size()));
			userPokers.put(uid, poker);
			pokerScores.put(uid, getScore(poker));// 牌型以及得分
		}
		// 进行牌的比较
		long dealer = wk.getDealer();
		Object[] dealerPokerScore = pokerScores.get(dealer);// 庄家的牌
		pokerTypes.put(dealer, (String) dealerPokerScore[1]);// 放入牌型
		Object[] otherPokerScore;// 其他人的分数以及牌型
		int dealerCoin = 0;
		int coin = 0;
		double buffer = 1 - BUFFER;// 最终金币得率
		for (Long uid : wk.getActors()) {// 进行最终结果统计
			if (uid != dealer) {// 不是庄家
				coin = wk.getBaseCoin() * wk.getUserFold(uid);// 基础金币*自己倍数
				otherPokerScore = pokerScores.get(uid);// 牌型
				// 存储log
				pokerTypes.put(uid, (String) otherPokerScore[1]);// 放入牌型
				if ((int) dealerPokerScore[0] >= (int) otherPokerScore[0]) {// 庄家赢
					if ((int) dealerPokerScore[0] >= NIU_BASE * 10) {
						// 庄家分大于等于牛牛
						coin = coin * 3;
					} else if ((int) dealerPokerScore[0] >= NIU_BASE * 7) {
						// 庄家分是牛7到牛9 2倍
						coin = coin * 2;
					}

				} else {// 庄家输
					if ((int) otherPokerScore[0] >= NIU_BASE * 10) {
						// 闲家分大于等于牛牛
						coin = -coin * 3;
					} else if ((int) otherPokerScore[0] >= NIU_BASE * 7) {
						// 闲家分是牛7到牛9 2倍
						coin = -coin * 2;
					} else {
						coin = -coin;
					}
				}
				// 庄家对这个人的金币输赢结果
				// 庄家总计的输赢结果
				if (coin > 0) {// 庄家赢 赢95 闲家输全部
					dealerCoin = dealerCoin + (int) (coin * buffer);
					coinRes.put(uid, coin);
				} else {// 庄家输 输全部 闲家赢95
					dealerCoin = dealerCoin + coin;
					coinRes.put(uid, (int) (coin * buffer));
				}
				// 闲家游戏结果
				saveCowLog(uid, userPokers.get(uid), (int) otherPokerScore[0], (String) otherPokerScore[1], -coin);
			}
		}
		Map<Long, Integer> newCoinRes = new HashMap<Long, Integer>();
		for (Entry<Long, Integer> entry : coinRes.entrySet()) {
			newCoinRes.put(entry.getKey(), -entry.getValue());
		}
		newCoinRes.put(dealer, dealerCoin);
		// 庄家的游戏结果
		saveCowLog(dealer, userPokers.get(dealer), (int) dealerPokerScore[0], (String) dealerPokerScore[1], dealerCoin);
		String time = DateUtil.dateFormatyyyyMMdd(new Date());
		long dt = System.currentTimeMillis();
		MapBody mb = new MapBody(SocketHandler.COW_END, SocketHandler.FN_ROOM_ID, roomId)
				.append(SocketHandler.FN_LIMIT, TIME_END).append("actId", wk.get_id())
				.append("userPokers", wk.getUserPokers()).append("pokerTypes", pokerTypes).append("coinRes", coinRes)
				.append("newCoinRes", newCoinRes);
		// 闲家金币结果
		Set<Actor> actors = super.getRoomActors(roomId);
		String detail = "";
		for (Actor actor : actors) {
			if (actor.getUid() == dealer) {
				detail = "庄家";
			} else {
				detail = "闲家";
			}
			coin = newCoinRes.get(actor.getUid()) == null ? 0 : newCoinRes.get(actor.getUid());
			if (coin > 0) {
				winCoinRankService.saveWinLog(actor.getUid(), time, coin, ActivityType.COW_FATHER.getType());
				coinService.addCoin(actor.getUid(), CoinLog.COW, wk.get_id(), coin, 0, detail + "胜利加币");
			} else if (coin < 0) {
				coinService.forceReduce(actor.getUid(), CoinLog.COW, wk.get_id(), Math.abs(coin), 0, detail + "失败扣币");
			}
			mb.append("coin", coin);
			super.pubRoomUserMsg(roomId, actor.getUid(), getMsgType(wk), mb, dt);
		}
		wk.setCoinRes(newCoinRes);
		wk.setStatus(Cow.STATUS_SHOWDOWN_END);
		wk.setUpdateTime(System.currentTimeMillis());
		super.saveActivity(roomId, wk);
		super.getMongoTemplate().save(wk);
		super.addJob(roomId, TIME_END);
	}

	/** 重新开始 */
	public void restart(Room r, Cow wk) {
		Set<CowActor> actors = getRoomActors(r.get_id(), CowActor.class);
		long dealer = 0;
		for (CowActor cowActor : actors) {
			if (cowActor.getUid() == wk.getDealer()) {
				dealer = cowActor.getSeat() + 1;
				break;
			}
		}
		wk.setStatus(Activity.STATUS_READY);
		int type = wk.getType();
		long owner = wk.getOwner() == null ? 0 : wk.getOwner();
		super.delActivity(r.get_id());
		wk = super.getActivity(r.get_id(), Cow.class);
		wk.setDealer(dealer);
		wk.getActors().clear();
		wk.setType(type);
		wk.setBaseCoin(this.getCowByType(type).getBaseCoin());// 底分
		Set<CowActor> initActors = new TreeSet<>();
		int robitCount = 0;
		Set<Integer> usedSeat = new TreeSet<>();
		long[] seatTable = new long[COUNT_MAX];
		for (CowActor actor : actors) {
			// 是不在线的用户 不判断机器人
			if ((Actor.STATUS_ONLINE != actor.getStatus() || StringUtils
					.isBlank(super.getChatRedisTemplate().opsForValue().get(RK.imOnlineUser(actor.getUid()))))
					&& !actor.isRobit()) {
				super.delRoomActor(r.get_id(), actor.getUid());
				if (actor.getUid() == owner) {
					CowActor newOua = getNewOwner(actors);
					if (newOua != null) {
						owner = newOua.getUid();
					}
				}
			} else {
				actor.setStatus(CowActor.STATUS_ONLINE);
				actor.setUcStatus(CowActor.A_STATUS_READY);
				if (actor.isRobit()) {
					robitCount++;
				}
				if (actor.getRole() == Actor.ROLE_EXECUTOR) {
					if (DboUtil.tryGetInt(userService.findById(actor.getUid()),
							"coin") < getCowAccessCoin(wk.getType())) {
						// 金币不够准入金币
						actor.setSeat(-1);
						actor.setRole(Actor.ROLE_VIEWER);// 变为观众
					} else {
						actor.setPlayCnt(actor.getPlayCnt() + 1);
						usedSeat.add(actor.getSeat());
						wk.putActor(actor.getUid());
						int seat = actor.getSeat();
						seatTable[seat] = actor.getUid();
					}
				}
				super.saveRoomActor(r.get_id(), actor);
				initActors.add(actor);
			}
		}
		wk.setSeatUserTable(seatTable);
		for (CowActor wa : initActors) {
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
	}

	private ReMsg synchronizeDate(long uid, Room r, boolean bot) {
		if (r == null) {
			return new ReMsg(ReCode.FAIL);
		}
		CowRoomInfo ri = new CowRoomInfo(r.getType());
		long roomId = r.get_id();
		boolean hasKey = getRedisTemplate().hasKey(RK.roomUser(roomId));
		Cow wk = super.getActivity(roomId, Cow.class);
		if (!hasKey) {
			int type = r.getType();
			wk.setType(type);
			wk.setBaseCoin(this.getCowByType(type).getBaseCoin());// 基础金币
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
		boolean firstIn = false;
		CowActor wa = super.getRoomActor(roomId, uid, CowActor.class);
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
			wa = new CowActor();
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
			wa.setGame("牛牛");
			if (DboUtil.tryGetInt(userService.findById(uid), "coin") < getCowAccessCoin(wk.getType())) {
				wa.setRole(Actor.ROLE_VIEWER);
				wa.setSeat(-1);
			} else {
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
						if (Room.OPEN == r.getStatus()) {// 游戏还没开始
							wk.putActor(uid);
						}
						wa.setSeat(seatNo);
						wa.setUcStatus(Actor.A_STATUS_READY);
					}
				}
			}
			if (null != r.getBuyUid() && uid == r.getBuyUid()) {// 房间购买人设置最高权限
				wa.setBuyUser(true);
			}
		}
		if (wa.isBuyUser() && (null == wk.getOwner() || wk.getOwner() != wa.getUid())) {// 房主不是房管
			if (null != wk.getOwner()) {// 原房管不为空
				CowActor actor = super.getRoomActor(r.get_id(), wk.getOwner(), CowActor.class);
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
		Set<CowActor> as = super.getRoomActors(roomId, CowActor.class);
		super.saveActivity(roomId, wk);
		ri.setPub(r.isPub());
		ri.setSys(r.isSys());// 系统房间或包间
		ri.setHasOwner(true);
		// 庄家id
		if (wk.getStatus() >= Cow.STATUS_ROBBANKER_END) {// 抢庄已经结束
			ri.setDealer(wk.getDealer());
		}
		if (wk.getSpeakerTable().size() > 0) {
			ri.setSpeaker(wk.getSpeakerTable().get(0));
		}
		ri.setFolds(wk.getFolds());// 用户的倍数
		ri.setUserPokers(wk.getUserPokers());// 用户的扑克牌
		ri.setBaseCoin(wk.getBaseCoin());// 底分
		ri.setCowStatus(wk.getStatus());
		if (r.getStatus() == Room.OPEN) {
			ri.setStatus(Room.OPEN);
		} else {
			ri.setStatus(Room.ACTIVITY);
		}
		super.changeRoomCount(r, as, T_INROOM);
		if (wa.getSeat() != -1 && firstIn && Room.OPEN == r.getStatus()) {
			// 用户第一次进入并坐下 游戏未开始
			checkStart(wk, roomId, false);
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
		ri.setActId(wk.get_id());
		return new ReMsg(ReCode.OK, ri);
	}

	public void checkStart(Cow wk, long roomId, boolean isRestart) {
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
			Cow wk = super.getActivity(roomId, Cow.class);
			CowActor actor = super.getRoomActor(roomId, uid, CowActor.class);
			boolean ucChange = false;
			if (null == actor) {
				unlock(RK.outRoomLock(r.get_id()));
				return new ReMsg(ReCode.ACTOR_ERROR);
			} else {
				if (Room.OPEN == r.getStatus() && Actor.ROLE_EXECUTOR == actor.getRole()) {
					if (CowActor.A_STATUS_READY == actor.getUcStatus()
							|| CowActor.A_STATUS_IN_ROOM == actor.getUcStatus()) {
						if (SocketHandler.KICK == type) {
							if (actor.isBuyUser()) {// 房主不能被踢
								return new ReMsg(ReCode.FAIL);
							}
							try {
								MapBody mb = new MapBody(type).append("nickname",
										super.getRoomActor(r.get_id(), getActivity(r.get_id(), Cow.class).getOwner())
												.getNickname());
								super.pubUserMsg(uid, MsgType.PROMPT, mb);
							} catch (Exception e) {
								log.error("发送踢人信息", e);
							}
						}
					}
					if (CowActor.A_STATUS_READY == actor.getUcStatus()) {
						wk.removeActor(uid);
						ucChange = true;
					}
					this.delRoomActor(roomId, uid);
					Set<CowActor> as = super.getRoomActors(roomId, CowActor.class);
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
						CowActor newOwn = this.getNewOwner(as);
						pubOwnerChange(uid, newOwn, r, wk);
					}
				}
				if (Actor.ROLE_VIEWER == actor.getRole() || !wk.getActors().contains(actor.getUid())) {
					this.delRoomActor(roomId, actor.getUid());
					Set<CowActor> as = super.getRoomActors(r.get_id(), CowActor.class);
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
				Set<CowActor> actors = super.getRoomActors(r.get_id(), CowActor.class);
				for (CowActor ua : actors) {
					if (ua.getStatus() != CowActor.STATUS_ONLINE || ua.isRobit()) {
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
		CowActor uca = super.getRoomActor(r.get_id(), uid, CowActor.class);
		if (uca == null) {
			return new ReMsg(ReCode.OK, 1);
		} else {
			return new ReMsg(ReCode.OK, 2);
		}
	}

	@Override
	public ReMsg canInRoom(int type, long actId, long roomId) {
		Cow uc = super.getActivity(roomId, Cow.class);
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

	private CowActor getNewOwner(Set<CowActor> as) {
		for (CowActor a : as) {
			if (CowActor.STATUS_ONLINE == a.getStatus() && Actor.ROLE_EXECUTOR == a.getRole()) {
				return a;
			}
		}
		return null;
	}

	private void pubOwnerChange(Long uid, CowActor a, Room r, Cow wk) {
		if (uid != null) {
			CowActor wa = super.getRoomActor(r.get_id(), uid, CowActor.class);
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
		for (int i = 0; i < seat.length; i++)
			if (uid == seat[i])
				return i;
		return -1;
	}

	private enum CowSetup {
		FOLD_LOW(148, 100, 3000), FOLD_HIGH(149, 1000, 30000);
		CowSetup(int type, int baseCoin, int accessCoin) {
			this.type = type;
			this.baseCoin = baseCoin;
			this.accessCoin = accessCoin;
		}

		private int type;
		private int baseCoin;// 基础金币
		private int accessCoin;// 准入金币

		public int getType() {
			return type;
		}

		public int getBaseCoin() {
			return baseCoin;
		}

		public int getAccessCoin() {
			return accessCoin;
		}
	}

	public CowSetup getCowByType(int activityType) {
		if (activityType == ActivityType.COW_LOW.getType()) {
			return CowSetup.FOLD_LOW;
		} else if (activityType == ActivityType.COW_HIGH.getType()) {
			return CowSetup.FOLD_HIGH;
		}
		return null;
	}

	public static int getCowAccessCoin(int activityType) {
		if (activityType == ActivityType.COW_LOW.getType()) {
			return CowSetup.FOLD_LOW.getAccessCoin();
		} else if (activityType == ActivityType.COW_HIGH.getType()) {
			return CowSetup.FOLD_HIGH.getAccessCoin();
		}
		return 0;
	}

	/** 第一位分数 第二位牌型 */
	public Object[] getScore(int[] pokers) {
		// 牌型：炸弹> 五小 > 五花 > 四花 > 牛牛 > 有牛 > 无牛。
		// 花色： 黑桃 > 红桃 > 草花 > 方块。
		// 单张：K > Q > J > 10 > 9 > 8 > 7 > 6 > 5 > 4 > 3 > 2 > A。
		// 无牛：比单张大小。
		// 有牛：比分数大小，牛九 > 牛八 > 牛七 > 牛六 > 牛五 > 牛四 > 牛三 > 牛二 > 牛一。
		// 相同牌型比较：
		// 炸弹看牌大小，大小按单张牌一样（4个k最大） 100000*牌的大小 最小也是11
		// 庄家和闲家都是五小，庄家赢 30000 五张加起来为10
		// 庄家和闲家都是五花，庄家赢 20000 全部JQK
		// 庄家和闲家都是四花，庄家赢 一个10剩下JQK 10000
		// 庄家和闲家牛相同，庄家赢 牛1 1000 牛2 2000 牛3 3000....
		// 庄家和闲家都无牛，看大牌大小，大牌一样比花色，黑>红>草>方 130 120
		Map<Integer, Integer> temp = new HashMap<Integer, Integer>();
		for (int poker : pokers) {
			poker = poker / 10;// 拿到对应的牌大小
			int pokerCnt = temp.get(poker) == null ? 1 : temp.get(poker) + 1;
			temp.put(poker, pokerCnt);
		}
		if (temp.size() == 2) {// 只有两个大小的牌出现
			for (Entry<Integer, Integer> entry : temp.entrySet()) {
				if (entry.getValue() == 4) {// 有一个出现了四次
					return new Object[] { BOMB * entry.getKey(), "炸弹" };
				}
			}
		}
		int tenCnt = 0;
		int jqkCnt = 0;
		int otherCnt = 0;
		for (int i = 0; i < 5; i++) {
			if (pokers[i] / 10 == 10) {// 10
				tenCnt = tenCnt + 1;
			} else if (pokers[i] / 10 > 10) {// 大于10
				jqkCnt = jqkCnt + 1;
			} else {// 小于10
				otherCnt = otherCnt + 1;
			}
		}
		if (otherCnt == 5) {// 五张都小于10
			int score = 0;
			for (int i = 0; i < 5; i++) {
				score = score + (pokers[i] / 10);
			}
			if (score == 10) {// 五张牌加起来是10
				return new Object[] { MIN_FIVE, "五小" };
			}
		}
		if (jqkCnt == 5) {// 如果五个牌都是11以上
			return new Object[] { HUA_FIVE, "五花" };
		}

		if (jqkCnt == 4 && tenCnt == 1) {// 一个10 剩下的4个都大于10
			return new Object[] { HUA_FOUR, "四花" };
		}

		int sum = 0;
		// 牛几
		for (int i = 0; i < pokers.length; i++) {
			for (int j = i + 1; j < pokers.length; j++) {
				for (int x = j + 1; x < pokers.length; x++) {
					sum = 0;
					sum = sum + (pokers[i] > 100 ? 10 : pokers[i] / 10);
					sum = sum + (pokers[j] > 100 ? 10 : pokers[j] / 10);
					sum = sum + (pokers[x] > 100 ? 10 : pokers[x] / 10);
					if (sum % 10 == 0) {// 有牛
						sum = 0;
						for (int index = 0; index < pokers.length; index++) {
							if (index != i && index != j && index != x) {
								sum = sum + (pokers[index] > 100 ? 10 : pokers[index] / 10);
							}
						}
						sum = sum % 10;
						if (sum == 0) {
							return new Object[] { NIU_BASE * 10, "牛牛" };
						} else {
							return new Object[] { sum * NIU_BASE, "牛" + sum };
						}
					}
				}
			}
		}
		// 没牛
		sum = pokers[0];
		for (Integer poker : pokers) {
			if (poker > sum) {
				sum = poker;
			}
		}
		// sum就是最大的牌
		return new Object[] { sum, "没牛" };
	}

	/** 存储log */
	public void saveCowLog(long uid, int[] pokers, int pokersScore, String pokersStr, int coin) {
		DBObject dbo = findCowLog(uid);
		CowLog cowLog;
		long time = System.currentTimeMillis();
		if (null == dbo) {
			if (coin > 0) {
				cowLog = new CowLog(1, pokersScore, pokers, pokersStr, 1, 0);
			} else {
				cowLog = new CowLog(1, pokersScore, pokers, pokersStr, 0, 1);
			}
			cowLog.set_id(uid);
			cowLog.setMaxPokerTime(time);
			cowLog.setUpdateTime(time);
		} else {
			cowLog = DboUtil.toBean(dbo, CowLog.class);
			cowLog.setUpdateTime(time);
			cowLog.setAllCnt(cowLog.getAllCnt() + 1);
			if (coin > 0) {
				cowLog.setWinCnt(cowLog.getWinCnt() + 1);
			} else {
				cowLog.setLoseCnt(cowLog.getLoseCnt() + 1);
			}
			if (pokersScore > cowLog.getPokersScore()) {
				cowLog.setPokersScore(pokersScore);
				cowLog.setPokers(pokers);
				cowLog.setPokersStr(pokersStr);
				cowLog.setMaxPokerTime(time);
			}
		}
		super.getMongoTemplate().save(cowLog);
	}

	public DBObject findCowLog(long uid) {
		return super.getC(DocName.COW_LOG).findOne(new BasicDBObject("_id", uid));
	}

	/** 牛牛首页的个人信息 */
	public DBObject getCowLog(long uid) {
		DBObject dbo = findCowLog(uid);
		if (dbo != null) {// 胜率
			dbo.put("winRate", DboUtil.toBean(dbo, CowLog.class).getAllRate() + "%");
		}
		return dbo;
	}

	/** 统计24小时内玩牛牛的人 */
	@SuppressWarnings("unchecked")
	public List<Long> find24hCowPlayer() {
		BasicDBObject q = new BasicDBObject("updateTime",
				new BasicDBObject("$gt", System.currentTimeMillis() - Const.DAY));
		return super.getC(DocName.COW_LOG).distinct("_id", q);
	}

}
