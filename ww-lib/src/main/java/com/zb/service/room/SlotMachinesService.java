package com.zb.service.room;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.zb.common.Constant.ActivityType;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.redis.RK;
import com.zb.common.utils.DateUtil;
import com.zb.common.utils.JSONUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.common.utils.T2TUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.BlackUsers;
import com.zb.models.DocName;
import com.zb.models.SlotMachinesInfo;
import com.zb.models.SlotMachinesModel;
import com.zb.models.SlotMachinesModelPool;
import com.zb.models.SlotMachinesUser;
import com.zb.models.Stake;
import com.zb.models.finance.CoinLog;
import com.zb.models.room.Actor;
import com.zb.models.room.Room;
import com.zb.models.room.machines.SlotMachines;
import com.zb.models.room.machines.SlotMachinesActor;
import com.zb.models.usertask.Task;
import com.zb.service.room.vo.SlotMachinesRoomInfo;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.MsgType;
import com.zb.socket.model.SmType;
import com.zb.socket.model.SocketHandler;

@Service
public class SlotMachinesService extends RoomService {
	static final Logger LOGGER = LoggerFactory.getLogger(SlotMachinesService.class);

	private static final int TIME_BET = 45;// 押注限时
	private static final int TIME_ROTATE = 10;// 开盘限时
	public static final int TIME_NEXT = 1;// 下一阶段
	private static final int TIME_COMMON = 5;// 限时
	public static final int STATUS_START = 2;// 开始阶段
	public static final int STATUS_BET = 10;// 下注阶段
	public static final int STATUS_ROTATE = 11;// 转盘阶段
	public static final int STATUS_SETTLEMENT = 12;// 结算阶段
	private static final int GAME_HISTORY_COUNT = 10; // 展示历史纪录条数
	public static final double SUBTACT_EARN = 0.05;// 抽红比例
	public static final double CACHE_PUT = 0.1;// 存入cache池比例
	public static final double CACHE_GET = 0.8;// 取出cache池比例
	private static final long BUFFER_MAX = 300 * 10000L;// buffer池回收上限
	private static final long COIN_MAX = 300 * 10000L;// coin池回收上限
	private static final int HERO_NUM = 8; // 每次获取英雄数量
	private static final int WORLD_SHOCK = 10000; // 世界广播最低中奖数量
	private static final long WORLD_ADMIN_TYPE = 1;
	private static final int DICE_MAX = 20000; // dicemax

	// private static double[][] rand_arr = { { 0.012, 0.145, 0.16, 0.141, 0.11,
	// 0.172, 0.06, 0.18, 0.02 },
	// { 0.0105, 0.145, 0.165, 0.161, 0.1, 0.164, 0.065, 0.17, 0.0195 },
	// { 0.01, 0.155, 0.175, 0.161, 0.1, 0.133, 0.065, 0.18, 0.021 } };
	@Autowired
	WorldChatService worldChatService;

	private static double[][] rand_arr = { { 0.018, 0.19, 0.16, 0.181, 0.11, 0.152, 0.055, 0.125, 0.009 },
			{ 0.015, 0.18, 0.169, 0.181, 0.1, 0.164, 0.06, 0.122, 0.009 },
			{ 0.016, 0.185, 0.175, 0.181, 0.1, 0.142, 0.06, 0.13, 0.011 } };

	private static int[][] rand_bound = new int[3][9];

	private static int rand_scope = 100000;

	static {
		for (int i = 0; i < 3; i++) {
			int bnd_val = 0;
			for (int j = 0; j < 8; j++) {
				bnd_val += (int) (rand_scope * rand_arr[i][j]);
				rand_bound[i][j] = bnd_val;
			}
			rand_bound[i][8] = rand_scope;
		}
	}

	/** 根据指定的概率获取随机数 */
	private static int nextBrodIndex() {
		int curLottery = RandomUtil.nextInt(3);
		int n = RandomUtil.nextInt(rand_scope);
		for (int i = 0; i < rand_bound[curLottery].length; i++) {
			if (rand_bound[curLottery][i] > n) {
				return i;
			}
		}
		return 1;
	}

	public static void main(String[] args) {
		// int[] arr = new int[9];
		// for (int i = 0; i < 100000; i++) {
		// int ss = nextBrodIndex();
		// if (ss == 0) {
		// arr[0] += 1;
		// } else if (ss == 1) {
		// arr[1] += 1;
		// } else if (ss == 2) {
		// arr[2] += 1;
		// } else if (ss == 3) {
		// arr[3] += 1;
		// } else if (ss == 4) {
		// arr[4] += 1;
		// } else if (ss == 5) {
		// arr[5] += 1;
		// } else if (ss == 6) {
		// arr[6] += 1;
		// } else if (ss == 7) {
		// arr[7] += 1;
		// } else if (ss == 8) {
		// arr[8] += 1;
		// }
		// }
		// System.out.println(arr);
	}

	@Override
	public ReMsg handle(Room room, long fr) {
		if (room.getType() != ActivityType.SLOT_MACHINES.getType()) {
			log.error(room.get_id() + " " + room.getType());
			return new ReMsg(ReCode.FAIL);
		}
		SlotMachines uc = super.getActivity(room.get_id(), SlotMachines.class);
		if (SlotMachines.STATUS_READY == uc.getStatus()) {
			startBet(room, uc); // 下注
		} else if (SlotMachines.STATUS_BET == uc.getStatus()) {
			rotate(room, uc); // 开盘
		} else if (SlotMachines.STATUS_ROTATE == uc.getStatus() || SlotMachines.STATUS_SETTLEMENT == uc.getStatus()) {
			end(room, uc); // 结束
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 下注倒计时 */
	public void startBet(Room room, SlotMachines uc) {
		String betNo = null;
		try {
			betNo = getBetNo(uc);
			uc.setBetNo(betNo);
			uc.setStatus(SlotMachines.STATUS_BET);
		} catch (Exception e) {
			log.error("下注倒计时出错", e);
		} finally {
			addJob(room.get_id(), TIME_BET);
		}
		MapBody mb = new MapBody(SocketHandler.SLOT_MACHINES_BET).append(SocketHandler.FN_ROOM_ID, room.get_id())
				.append(SocketHandler.FN_LIMIT, TIME_BET).append("betNo", betNo);
		super.pubRoomMsg(room.get_id(), MsgType.SLOTMACHINES, mb);
		super.saveActivity(room.get_id(), uc);
	}

	/** 获取期号 */
	public String getBetNo(SlotMachines uc) {
		SlotMachinesModel model = querySm();
		uc.setInning(null == model ? 1 : model.getInning() + 1);
		return DateUtil.dateFormatyyyyMMdd(new Date())
				.concat(String.format("%04d", null == model ? 1 : model.getInning() + 1));
	}

	/** 机器人下注 */
	public int betting(final long roomId, long uid, Map<Integer, Integer> map) {
		int ret = ReCode.OK.reCode();
		for (Integer e : map.keySet()) {
			ReMsg rm = betting(roomId, uid, e, map.get(e));
			if (ret != rm.getCode()) {
				ret = rm.getCode();
				break;
			}
		}
		return ret;
	}

	/** 用户下注 */
	public ReMsg betting(final long roomId, long uid, int choice, int stakeCoin) {
		// log.error("=======uid===" + uid + "choice" + choice + "coin=" +
		// stakeCoin);
		SlotMachines uc = super.getActivity(roomId, SlotMachines.class);
		if (SlotMachines.STATUS_BET == uc.getStatus()) {
			if (checkChoice(choice)) {
				if (ReCode.OK.reCode() == coinService
						.reduce(uid, CoinLog.SLOT_MACHINES, uc.get_id(), stakeCoin, 0L, "王者大乱斗下注").reCode()) {
					SlotMachinesActor ua = super.getRoomActor(roomId, uid, SlotMachinesActor.class);
					if (null == ua) {
						return new ReMsg(ReCode.NET_WORK_ERROR);
					}
					List<Stake> stakes = ua.getStakes();
					int nowsum = getSum(stakes);
					if (stakeCoin + nowsum > DICE_MAX) {
						return new ReMsg(ReCode.DICEMAX); 
					}
					ua.setBet(true);
					if (stakes.isEmpty()) {
						stakes.add(new Stake(choice, stakeCoin));
					} else {
						boolean has = false;
						for (int i = 0; i < stakes.size(); i++) {
							Stake s = stakes.get(i);
							if (s.getChoice() == choice) {
								s.setCoin(s.getCoin() + stakeCoin);
								has = true;
								break;
							}
						}
						if (!has) {
							stakes.add(new Stake(choice, stakeCoin));
						}
					}
					ua.setStakes(stakes);
					int sum = getSum(stakes);
					ua.setTotalPay(sum);
					super.saveRoomActor(roomId, ua);
					long coin = coinService.findLastCoin(uid);
					MapBody mb = new MapBody(SocketHandler.SLOT_MACHINES_BETTING).append("coin", coin)
							.append("choice", choice).append("stakeCoin", stakeCoin)
							.append(SocketHandler.FN_USER_ID, uid);
					super.pubRoomUserMsg(roomId, uid, MsgType.SLOTMACHINES, mb, System.currentTimeMillis());
				} else {
					return new ReMsg(ReCode.COIN_BALANCE_LOW);
				}
			} else {
				return new ReMsg(ReCode.INVALID_OPTION);
			}
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.ROOM_STATUS_NOT_OP);
	}

	/** 判断下注有效性 */
	boolean checkChoice(int choice) {
		return SmType.canBet.contains(choice);
	}

	/** 开盘 */
	public void rotate(Room room, SlotMachines uc) {
		MapBody mb = null;
		try {
			mb = new MapBody(SocketHandler.SLOT_MACHINES_ROTATE).append(SocketHandler.FN_ROOM_ID, room.get_id())
					.append(SocketHandler.FN_LIMIT, TIME_ROTATE);
			uc.setStatus(SlotMachines.STATUS_ROTATE);
		} catch (Exception e) {
			log.error("开盘error", e);
		} finally {
			addJob(room.get_id(), TIME_ROTATE); // 转动限时
		}
		super.pubRoomMsg(room.get_id(), MsgType.SLOTMACHINES, mb);
		super.saveActivity(room.get_id(), uc);
		makeLotteries(uc, room.get_id()); // 生成开奖号码
		settlement(room, uc);
	}

	/** 结算 */
	void settlement(Room room, SlotMachines uc) {
		saveSlotMachines(uc, room.get_id()); // 保存对局信息
		uc.setStatus(SlotMachines.STATUS_SETTLEMENT);
		super.saveActivity(room.get_id(), uc);
		Set<SlotMachinesActor> as = super.getRoomActors(room.get_id(), SlotMachinesActor.class);
		long dt = System.currentTimeMillis();
		List<String[]> winUserList = as.stream().filter(e -> e.getTotalWin() > 0)
				.sorted((e1, e2) -> e1.getTotalWin() - e2.getTotalWin())
				.map(e -> new String[] { e.getNickname(), Integer.toString(e.getTotalWin()) })
				.collect(Collectors.toList());
		MapBody mb = new MapBody(SocketHandler.SLOT_MACHINES_SETTLEMENT).append(SocketHandler.FN_ROOM_ID, room.get_id())
				.append("winUserList", winUserList).append("lottery", uc.getLottery());
		for (SlotMachinesActor a : as) {
			mb.append("income", a.getChangeCoin());
			super.pubRoomUserMsg(room.get_id(), a.getUid(), MsgType.SLOTMACHINES, mb, dt);
		}
		// 世界广播
		CompletableFuture.runAsync(() -> {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e3) {
			}
			as.stream().filter(e -> e.getTotalWin() >= WORLD_SHOCK).forEach(e -> {
				StringBuilder sb = new StringBuilder();
				DBObject user = userService.findById(e.getUid());
				sb.append(Const.USERNAME_PREFIX + DboUtil.getString(user, "nickname") + Const.USERNAME_SUFFIX)
						.append("在" + Const.GAME_PREFIX + "王者大乱斗" + Const.GAME_SUFFIX + "游戏中凭借过人的智慧喜获")
						.append(Const.WINCOIN_PREFIX + e.getTotalWin() + Const.WINCOIN_SUFFIX).append("金币奖励，恭喜！");
				worldChatService.worldChat(0L, 0L, WORLD_ADMIN_TYPE, "txt", sb.toString(), true);
			});
		});
		// 触发任务
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
			for (SlotMachinesActor actor : as) {
				if (!actor.isRobit() && actor.isBet()) {
					jobsDone(actor.getUid(), Task.SLOT_MACHINESE, 1);
				}
			}
			return null;
		});
		try {
			future.get(1L, TimeUnit.SECONDS);
		} catch (Exception e) {
			log.error("调用任务slot", e);
		}
	}

	/** 下一轮 */
	void end(Room room, SlotMachines uc) {
		try {
			Set<SlotMachinesActor> actors = getRoomActors(room.get_id(), SlotMachinesActor.class);
			super.delActivity(room.get_id());
			Set<SlotMachinesActor> initActors = new TreeSet<SlotMachinesActor>();
			int robitCount = 0;
			for (SlotMachinesActor actor : actors) {
				if (Actor.STATUS_ONLINE != actor.getStatus()) {
					super.delRoomActor(room.get_id(), actor.getUid());
				} else {
					actor.setRole(SlotMachinesActor.ROLE_EXECUTOR);
					actor.setStatus(SlotMachinesActor.STATUS_ONLINE);
					actor.setChangeCoin(0);
					List<Stake> stakes = new ArrayList<>();
					actor.setStakes(stakes);
					actor.setTotalPay(0);
					actor.setBet(false);
					initActors.add(actor);
					super.saveRoomActor(room.get_id(), actor);
					if (actor.isRobit()) {
						robitCount++;
					}
				}
			}
			room.setCount(initActors.size());
			room.setRobitCount(robitCount);
			// 缓存下一局信息
			cacheNextInfo();
		} catch (Exception e) {
			log.error("restarterror", e);
		} finally {
			addJob(room.get_id(), TIME_NEXT);
		}
	}

	/** 遍历所有用户，组装model */
	SlotMachinesModel assembleModel(SlotMachines uc, final long roomId) {
		SlotMachinesModel model = querySm(uc.get_id());
		if (null == model) {
			int lottery = uc.getLottery();
			String HeroName = getHeroName(lottery);
			model = new SlotMachinesModel();
			Set<SlotMachinesActor> actors = super.getRoomActors(roomId, SlotMachinesActor.class);
			Set<SlotMachinesUser> uas = new HashSet<>();
			long time = 0L;
			Double et = super.getJobEndTime(roomId);
			if (et != null) {
				time = T2TUtil.half_round(et);
			} else {
				time = System.currentTimeMillis();
			}
			for (SlotMachinesActor actor : actors) {
				int winCoin = 0;
				int payCoin = actor.getTotalPay();
				if (actor.isBet()) {
					for (Stake e : actor.getStakes()) {
						if (lottery == e.getChoice() || lottery == SmType.SLAM.getT()) {
							winCoin += SmType.eMap.get(e.getChoice()) * e.getCoin();
						}
					}
				}
				SlotMachinesUser u = new SlotMachinesUser();
				u.setUid(actor.getUid());
				u.setStakes(actor.getStakes());
				u.setWinCoin(winCoin);
				u.setPayCoin(payCoin);
				// log.error("wincoin=======" + winCoin + "payCoin=======" +
				// payCoin);
				u.setIncome(winCoin - payCoin);
				if (actor.isBet()) {
					uas.add(u);
				}
				actor.setChangeCoin(u.getIncome());
				actor.setTotalWin(winCoin);
				super.saveRoomActor(roomId, actor);
				if (winCoin > 0) {
					coinService.addCoin(actor.getUid(), CoinLog.SLOT_MACHINES, uc.get_id(), winCoin, 0L, "大乱斗赢得金币");
				}
			}
			long inCoin = uc.getInCoin();
			long outCoin = uc.getOutCoin();
			model.setInCoin(inCoin);
			model.setOutCoin(outCoin);
			model.setTotalIncome(inCoin - outCoin);
			model.set_id(uc.get_id());
			model.setUas(uas);
			model.setHeroName(HeroName);
			model.setDate(DateUtil.dateFormatyyyyMMdd(new Date()));

			model.setTime(time);
			model.setLottery(uc.getLottery());
			model.setCoinPool(uc.getCoinPool());
			model.setBufferPool(uc.getBufferPool());
			model.setCachePool(uc.getCachePool());
			model.setNextGetCacheCount(uc.getNextGetCacheCount());
			model.setIncomePool(uc.getIncomePool());
			model.setInning(uc.getInning());
		}
		return model;
	}

	/** 根据id查询对局信息 */
	public SlotMachinesModel querySm(Long id) {
		DBObject dbo = getC(DocName.SLOT_MACHINES).findOne(new BasicDBObject("_id", id));
		return DboUtil.toBean(dbo, SlotMachinesModel.class);
	}

	/** 查询最新对局信息 */
	public SlotMachinesModel querySm() {
		DBCursor cs = getC(DocName.SLOT_MACHINES).find(new BasicDBObject()).sort(new BasicDBObject("_id", -1)).limit(1);
		if (cs.hasNext()) {
			DBObject dbo = cs.next();
			return DboUtil.toBean(dbo, SlotMachinesModel.class);
		} else {
			return null;
		}
	}

	/** 查询最新碧池信息 */
	public SlotMachinesModelPool querySmPool() {
		DBObject dbo = getC(DocName.SLOT_MACHINES_POOL).findOne(new BasicDBObject());
		return DboUtil.toBean(dbo, SlotMachinesModelPool.class);
	}

	/** 查询黑名单信息 */
	Set<Long> queryBlackUserFromDB() {
		Set<Long> set = new HashSet<>();
		List<DBObject> list = getC(DocName.BLACK_USERS).find(new BasicDBObject()).toArray();
		if (!list.isEmpty()) {
			for (DBObject o : list) {
				set.add(DboUtil.getLong(o, "_id"));
			}
		}
		return set;
	}

	/** 扣除金币后保存本轮信息 */
	void saveSlotMachines(SlotMachines uc, final long roomId) {
		long inCoin = uc.getInCoin();
		long outCoin = uc.getOutCoin();
		SlotMachinesModel model = assembleModel(uc, roomId);
		exChange(model, inCoin, outCoin);
		output(model);
		super.getMongoTemplate().save(model);
		SlotMachinesModelPool pool = querySmPool();
		if (null == pool) {
			pool = new SlotMachinesModelPool();
			pool.set_id(uc.get_id());
		}
		pool.setUpdateTime(System.currentTimeMillis());
		pool.setCoinPool(model.getCoinPool());
		pool.setBufferPool(model.getBufferPool());
		pool.setCachePool(model.getCachePool());
		pool.setIncomePool(model.getIncomePool());
		pool.setNextGetCacheCount(model.getNextGetCacheCount());
		super.getMongoTemplate().save(pool);
	}

	/** buffer池coin池交换 */
	/**
	 * 
	 * @param model
	 * @param inCoin
	 *            本局总收入
	 * @param outCoin
	 *            本局总支出
	 */
	void exChange(SlotMachinesModel model, long inCoin, long outCoin) {
		long id = model.get_id();
		long coinPool = model.getCoinPool();//
		long incomePool = model.getIncomePool();
		long bufferPool = model.getBufferPool();// 交换池
		long cachePool = model.getCachePool();
		// log.error("::::::::coinPool=" + coinPool + "incomePool=" + incomePool
		// + "bufferPool=" + bufferPool);
		long exchange = 0L;// 是否需要交换，和交换数量
		coinPool += T2TUtil.half_round(inCoin * (1 - SUBTACT_EARN - CACHE_PUT) - outCoin);// 奖池
		incomePool += T2TUtil.half_round(SUBTACT_EARN * inCoin);// 抽红池
		cachePool += inCoin * CACHE_PUT;// 蓄水池
		if (coinPool < COIN_MAX && 0 < bufferPool && id % 5 == 0) {
			Long dif = COIN_MAX - coinPool - bufferPool;
			if (dif < 0) {
				exchange = bufferPool + dif;
			} else {
				exchange = bufferPool;
			}
		} else if (coinPool > COIN_MAX && bufferPool < BUFFER_MAX) {
			exchange = COIN_MAX - coinPool;// 负数
		}
		if (exchange > 0 && exchange > bufferPool * 0.3) {
			exchange = T2TUtil.half_round(bufferPool * 0.3);
		}
		coinPool += exchange;
		bufferPool -= exchange;
		model.setCoinPool(coinPool);
		model.setBufferPool(bufferPool);
		model.setIncomePool(incomePool);
		model.setCachePool(cachePool);
		// log.error("::::::::coinPool=" + model.getCoinPool() + "incomePool=" +
		// model.getIncomePool() + "bufferPool="
		// + model.getBufferPool());
	}

	/** cache放钱 */
	void output(SlotMachinesModel model) {
		long coinPool = model.getCoinPool();
		long cachePool = model.getCachePool();
		int nextCount = model.getNextGetCacheCount();
		if (nextCount < 1) {
			nextCount = RandomUtil.nextInt(100) + 10;
			model.setCoinPool(coinPool += cachePool * CACHE_GET);
			model.setCachePool(cachePool -= cachePool * CACHE_GET);
			// log.error(":::out:::::coinPool=" + model.getCoinPool() +
			// "incomePool=" + model.getIncomePool()
			// + "bufferPool=" + model.getBufferPool());
		}
		--nextCount;
		model.setNextGetCacheCount(nextCount);
	}

	/** 获取多次下注总和 */
	int getSum(List<Stake> a) {
		int ret = 0;
		for (Stake s : a) {
			ret += s.getCoin();
			// log.error("====计算本轮扣币总和" + s.getCoin());
		}
		// log.error("===================本轮扣币总和=" + ret);
		return ret;
	}

	long qtime = 0L;
	Set<Long> blackUsers = null;

	/** 摇奖结果 */
	void makeLotteries(SlotMachines uc, final long roomId) {
		SlotMachinesModel model = querySm();
		SlotMachinesModelPool pool = querySmPool();
		if (null == model) {
			model = new SlotMachinesModel();
		}
		long outSum;
		long inSum;
		int index;
		boolean oneBig;
		if (blackUsers == null || System.currentTimeMillis() - qtime > 5 * Const.MINUTE) {
			blackUsers = queryBlackUserFromDB();
			qtime = System.currentTimeMillis();
		}
		do {
			oneBig = false;
			outSum = 0L;
			inSum = 0L;
			index = nextBrodIndex();
			Set<SlotMachinesActor> actors = super.getRoomActors(roomId, SlotMachinesActor.class);
			for (SlotMachinesActor actor : actors) {
				if (!actor.isBet()) {
					continue;
				}
				int eachInSum = actor.getTotalPay(); // 每人花费金币
				// log.error("===================结算每人花费金币=" + eachInSum);
				List<Stake> list = actor.getStakes();
				int eachOutSum = 0;
				for (Stake e : list) {
					if (e.getChoice() == index || SmType.SLAM.getT() == index) {
						eachOutSum += SmType.eMap.get(e.getChoice()) * e.getCoin(); // 每人获得金币
					}
				}
				if (blackUsers.contains(actor.getUid())) {// 如果是黑名单用户
					if (eachOutSum > 9999) {
						if (eachOutSum > 40000) {
							if (eachOutSum > 80000) {
								if (eachOutSum > 140000) {
									oneBig = true;
								} else {
									if (RandomUtil.nextInt(100) < 80) {// 20%
																		// 重新开
										oneBig = true;
									}
								}
							} else {
								if (RandomUtil.nextInt(100) < 50) {// 10% 重新开
									oneBig = true;
								}
							}
						} else {
							if (RandomUtil.nextInt(100) < 30) {// 5% 重新开
								oneBig = true;
							}
						}
					}
					if (oneBig) {
						log.error("===================结算从新开始：" + actor.getUid() + " Out:" + eachOutSum);
					}
				}
				outSum += eachOutSum; // 本轮总支出
				inSum += eachInSum; // 本轮总收入
			}
		} while (oneBig || checkEffectiveness(outSum, inSum, pool.getCoinPool()));
		uc.setInCoin(inSum);
		uc.setOutCoin(outSum);
		uc.setLottery(index);
		uc.setInning(model.getDate().equals(DateUtil.dateFormatyyyyMMdd(new Date())) ? model.getInning() + 1 : 1);
		uc.setCoinPool(pool.getCoinPool());
		uc.setCachePool(pool.getCachePool());
		uc.setBufferPool(pool.getBufferPool());
		uc.setNextGetCacheCount(pool.getNextGetCacheCount());
		uc.setIncomePool(pool.getIncomePool());
		super.saveActivity(roomId, uc);
	}

	/** 校验结果有效性 */
	boolean checkEffectiveness(long outSum, long inSum, long coinPool) {
		long diff = outSum - inSum;
		if (diff < 0) {// 为负数，直接开
			return false;
		}
		boolean reDraw = diff + T2TUtil.half_round(inSum * (SUBTACT_EARN + CACHE_PUT)) > coinPool;
		if (reDraw) {// 不够赔
			return true;
		}
		long rax = (outSum * 50) / coinPool;// 支出占总奖池的比的一半
		if (rax < 5) {// 中出总奖池的2*5%之内合理，直接开
			return false;
		} else {
			if (diff > 50000) {// 收支差大于50000万
				if (RandomUtil.nextInt(100) < rax) {// 5-50% 重新开
					return true;
				}
			}
		}
		return false;
	}

	/** 添加黑名单用户 */
	public void addBlackSet(Set<Long> set) {
		SetOperations<String, String> so = getRedisTemplate().opsForSet();
		for (String e : set.stream().map(em -> em + "").collect(Collectors.toSet())) {
			so.add(RK.blackUser(), e);
		}
	}

	/** 获取黑名单列表 */
	public Set<Long> getBlackSet() {
		return getRedisTemplate().opsForSet().members(RK.blackUser()).stream().map(e -> Long.parseLong(e))
				.collect(Collectors.toSet());
	}

	// /** 获取上次查询黑名单时间 */
	// public String getLastQueryTime() {
	// return getRedisTemplate().opsForValue().get(RK.blackUserQueryTime());
	// }
	//
	// /** 设置上次查询黑名单时间 */
	// public void setLastQueryTime(String time) {
	// getRedisTemplate().opsForValue().set(RK.blackUserQueryTime(), time);
	// }

	@Override
	public ReMsg inRoom(long uid, Room r, int model, boolean robit) {
		SlotMachinesRoomInfo ri = new SlotMachinesRoomInfo(ActivityType.SLOT_MACHINES.getType());
		if (r == null || r.getType() != ActivityType.SLOT_MACHINES.getType()) {
			log.error(r.get_id() + " " + r.getType());
			ri.setCode(1);
			return new ReMsg(ReCode.FAIL, ri);
		}
		SlotMachinesActor ua = super.getRoomActor(r.get_id(), uid, SlotMachinesActor.class);
		if (ua != null) {
			ua.setStatus(Actor.STATUS_ONLINE);
			// ua.setExit(false);
		} else {
			DBObject user = userService.findById(uid);
			if (user == null) {
				ri.setCode(1);
				return new ReMsg(ReCode.FAIL, ri);
			}
			ua = new SlotMachinesActor();
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
			ua.setGame("王者大乱斗");
			// List<Stake> stakes = ua.getStakes();
			// for (Integer e : SmType.canBet) {
			// Stake stake = new Stake();
			// stake.setChoice(e);
			// stake.setCoin(0);
			// stakes.add(stake);
			// }
			// ua.setStakes(stakes);
		}
		super.saveRoomActor(r.get_id(), ua);

		Set<SlotMachinesActor> as = super.getRoomActors(r.get_id(), SlotMachinesActor.class);
		super.changeRoomCount(r, as, T_NULL);
		ri.setActors(as);
		SlotMachines uc = super.getActivity(r.get_id(), SlotMachines.class);
		Double t = super.getJobEndTime(r.get_id());
		if (null == t && SlotMachines.STATUS_READY == uc.getStatus()) {
			r.setActivityDate(System.currentTimeMillis());
			this.updateRoom(r, T_NULL);
			startBet(r, uc); // 游戏启动
		} else if (null == t && SlotMachines.STATUS_READY != uc.getStatus()) {
			addJob(r.get_id(), TIME_COMMON);
		}
		ri.setStatus(Room.ACTIVITY);
		ri.setTimeType(uc.getStatus());
		long nextTime = 0;
		if (t != null) {
			nextTime = T2TUtil.half_round(t) - System.currentTimeMillis();
		}
		ri.setNextTime(nextTime < 0 ? 0 : nextTime); // 下步开始时间
		ri.setLottery(uc.getLottery());// 开奖结果
		ri.setBetNo(null == uc.getBetNo() ? getBetNo(uc) : uc.getBetNo());
		ri.setStakes(ua.getStakes());
		MapBody mb = new MapBody(SocketHandler.IN_ROOM, SocketHandler.FN_USER_ID, ua.getUid())
				.append("nickname", ua.getNickname()).append("sex", ua.getSex()).append("avatar", ua.getAvatar())
				.append("actors", as).append("province", ua.getProvince()).append("city", ua.getCity())
				.append("role", ua.getRole());
		long dt = System.currentTimeMillis();
		for (Actor a : as) {
			if (uid != a.getUid()) {
				super.pubRoomUserMsg(r.get_id(), a.getUid(), MsgType.ROOM, mb, dt);
			}
		}
		return new ReMsg(ReCode.OK, ri);
	}

	@Override
	public ReMsg outRoom(long uid, Room r) {
		return outRoom(uid, r, SocketHandler.OUT_ROOM);
	}

	@Override
	public ReMsg disconnectRoom(long uid, Room r) {
		return outRoom(uid, r, SocketHandler.DISCONNECT);
	}

	public ReMsg outRoom(long uid, Room r, int type) {
		if (r.getType() != ActivityType.SLOT_MACHINES.getType()) {
			log.error(r.get_id() + " " + r.getType());
			return new ReMsg(ReCode.FAIL);
		}
		SlotMachinesActor ua = super.getRoomActor(r.get_id(), uid, SlotMachinesActor.class);
		if (ua == null) {
			return new ReMsg(ReCode.ACTOR_ERROR);
		}
		if (ua.isBet()) {
			if (SocketHandler.DISCONNECT == type) {
				ua.setStatus(Actor.STATUS_OFFLINE);
			} else {
				ua.setStatus(Actor.STATUS_OUT_ROOM);
			}
			super.saveRoomActor(r.get_id(), ua);
		} else {
			super.delRoomActor(r.get_id(), uid);
			Set<SlotMachinesActor> as = super.getRoomActors(r.get_id(), SlotMachinesActor.class);
			super.changeRoomCount(r, as, T_NULL);
		}
		return new ReMsg(ReCode.OK);
	}

	// @Override
	// public ReMsg userFastCard(long uid, Room r) {
	// return new ReMsg(ReCode.FAIL);
	// }

	@Override
	public ReMsg getActorStatus(long uid, Room r) {
		return new ReMsg(ReCode.OK);
	}

	@Override
	public ReMsg canInRoom(int type, long actId, final long roomId) {
		SlotMachines uc = super.getActivity(roomId, SlotMachines.class);
		if (uc.getInning() > actId) {
			return new ReMsg(ReCode.OK, 2);
		} else {
			return new ReMsg(ReCode.OK, 1);
		}
	}

	public Page<DBObject> query(Integer state, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		state = state == null ? 0 : state;
		List<DBObject> dbos = find(state, page, size);
		int count = count();
		return new Page<DBObject>(count, size, page, dbos);
	}

	public void save(String name, String code) {
		SlotMachinesInfo model = new SlotMachinesInfo(Long.parseLong(code), name);
		getMongoTemplate().save(model);
	}

	public void delete(Long id) {
		DBObject dbo = new BasicDBObject("available", 2);
		super.getC(DocName.SLOT_INFO).update(new BasicDBObject("_id", id), new BasicDBObject("$set", dbo));
	}

	public void up(Long id) {
		DBObject dbo = new BasicDBObject("available", 1);
		super.getC(DocName.SLOT_INFO).update(new BasicDBObject("_id", id), new BasicDBObject("$set", dbo));
	}

	public int count() {
		return getC(DocName.SLOT_INFO).find(new BasicDBObject()).count();
	}

	public List<DBObject> find(Integer state, Integer page, Integer size) {
		DBObject q;
		if (state == 0) {
			q = new BasicDBObject();
		} else {
			q = new BasicDBObject("available", state);
		}
		return getC(DocName.SLOT_INFO).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
	}

	/** 获取游戏资源 */
	public List<DBObject> getSlotInfo4Game() {
		return getC(DocName.SLOT_INFO).find(new BasicDBObject("available", 1)).toArray();
	}

	/** 缓存下一局信息 */
	public void cacheNextInfo() {
		List<DBObject> temp = getSlotInfo4Game();
		List<DBObject> list = new ArrayList<>();
		int size = temp.size();
		for (int i = 0; i < HERO_NUM; i++) {
			int index = RandomUtil.nextInt(size);
			list.add(temp.get(index));
			temp.remove(index);
			size--;
		}
		super.getRedisTemplate().opsForValue().set(RK.nextGameInfo("SLOTMACHINES"), JSONUtil.beanToJson(list));
	}

	/** 获取本局英雄名称 */
	String getHeroName(int index) {
		String json = super.getRedisTemplate().opsForValue().get(RK.nextGameInfo("SLOTMACHINES"));
		@SuppressWarnings("unchecked")
		List<Map<String, String>> temp = JSONUtil.jsonToArray(json);
		return index < 1 ? "ACE" : temp.get(index - 1).get("name");
	}

	/** 历史中奖纪录 */
	public List<DBObject> getGameHistory() {
		return getC(DocName.SLOT_MACHINES).find(new BasicDBObject()).sort(new BasicDBObject("_id", -1))
				.limit(GAME_HISTORY_COUNT).toArray();
	}

	/** 每期开奖查询 */
	public Page<DBObject> getAllHistory(Integer page, Integer size, Long st, Long et) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = findAll(page, size, st, et);
		int count = countAll(st, et);
		return new Page<DBObject>(count, size, page, dbos);
	}

	/** 每期开奖详情 */
	public DBObject getOneHistory(Long id) {
		return getC(DocName.SLOT_MACHINES).findOne(new BasicDBObject("_id", id));
	}

	public List<DBObject> findAll(Integer page, Integer size, Long startDate, Long endDate) {
		BasicDBObject q = new BasicDBObject();
		if (startDate != null && startDate != 0 && endDate != null && endDate != 0) {
			q.put("time", new BasicDBObject("$gte", startDate).append("$lt", endDate));
		} else if (startDate != null && startDate != 0) {
			q.put("time", new BasicDBObject("$gte", startDate));
		} else if (endDate != null && endDate != 0) {
			q.put("time", new BasicDBObject("$lt", endDate));
		}
		return getC(DocName.SLOT_MACHINES).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
	}

	public int countAll(Long startDate, Long endDate) {
		BasicDBObject q = new BasicDBObject();
		if (startDate != null && startDate != 0 && endDate != null && endDate != 0) {
			q.put("time", new BasicDBObject("$gte", startDate).append("$lt", endDate));
		} else if (startDate != null && startDate != 0) {
			q.put("time", new BasicDBObject("$gte", startDate));
		} else if (endDate != null && endDate != 0) {
			q.put("time", new BasicDBObject("$lt", endDate));
		}
		return getC(DocName.SLOT_MACHINES).find(q).count();
	}

	public void changePool(String type, Long change) {
		DBObject query = new BasicDBObject();
		DBObject update = new BasicDBObject("$inc", new BasicDBObject(type, change));
		getC(DocName.SLOT_MACHINES_POOL).findAndModify(query, update);
	}

	@Override
	public ReMsg kick(long uid, Room r) {
		return null;
	}

	public Page<DBObject> slotUserSearch(Long startDate, Long endDate, Long uid, Integer page, Integer size) {
		if (null == uid || uid < 1) {
			return new Page<DBObject>(0, 20, 0, new ArrayList<DBObject>());
		}
		page = super.getPage(page);
		size = super.getSize(size);
		BasicDBObject q = new BasicDBObject();
		if (startDate != null && startDate != 0 && endDate != null && endDate != 0) {
			q.put("time", new BasicDBObject("$gte", startDate).append("$lt", endDate));
		} else if (startDate != null && startDate != 0) {
			q.put("time", new BasicDBObject("$gte", startDate));
		} else if (endDate != null && endDate != 0) {
			q.put("time", new BasicDBObject("$lt", endDate));
		}
		q.put("uas.uid", uid);
		List<DBObject> list = getC(DocName.SLOT_MACHINES).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
		int count = getC(DocName.SLOT_MACHINES).find(q).count();
		for (DBObject dbo : list) {
			@SuppressWarnings("unchecked")
			ArrayList<DBObject> us = DboUtil.get(dbo, "uas", ArrayList.class);
			for (DBObject user : us) {
				if (DboUtil.getLong(user, "uid") - uid == 0) {
					dbo.put("payCoin", DboUtil.getInt(user, "payCoin"));
					dbo.put("winCoin", DboUtil.getInt(user, "winCoin"));
					dbo.put("income", DboUtil.getInt(user, "income"));
					dbo.put("stakes", DboUtil.get(user, "stakes", ArrayList.class));
					break;
				}
			}
		}
		return new Page<DBObject>(count, size, page, list);
	}

	public Page<DBObject> blackUsers(Long uid, Integer page, Integer size) {
		page = super.getPage(page);
		size = super.getSize(size);
		BasicDBObject q = new BasicDBObject();
		if (null != uid)
			q.put("_id", uid);
		List<DBObject> list = getC(DocName.BLACK_USERS).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
		int count = getC(DocName.BLACK_USERS).find(q).count();
		return new Page<DBObject>(count, size, page, list);
	}

	public ReMsg addblackUsers(Long uid) {
		if (null == uid || uid < 1) {
			return new ReMsg(ReCode.USER_ID_ERR);
		}
		BlackUsers b = new BlackUsers(uid);
		super.getMongoTemplate().save(b);
		return new ReMsg(ReCode.OK);
	}

	public ReMsg delBlackUsers(Long uid) {
		if (null == uid || uid < 1) {
			return new ReMsg(ReCode.USER_ID_ERR);
		}
		super.getC(DocName.BLACK_USERS).remove(new BasicDBObject("_id", uid));
		return new ReMsg(ReCode.OK);
	}
}
