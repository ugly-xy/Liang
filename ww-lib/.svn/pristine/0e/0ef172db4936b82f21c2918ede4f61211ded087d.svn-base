package com.zb.service.room;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
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
import com.zb.common.utils.MapUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.common.utils.T2TUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.BlackUsers;
import com.zb.models.DocName;
import com.zb.models.SakuranModel;
import com.zb.models.SakuranModelPool;
import com.zb.models.Stake;
import com.zb.models.finance.CoinLog;
import com.zb.models.room.Actor;
import com.zb.models.room.Room;
import com.zb.models.room.activity.SakuranActor;
import com.zb.models.room.machines.Sakuran;
import com.zb.models.room.machines.SakuranScene;
import com.zb.models.room.machines.SakuranUser;
import com.zb.models.room.machines.SakuranUserVO;
import com.zb.service.RankListService;
import com.zb.service.WinCoinRankService;
import com.zb.service.room.vo.SakuranRoomInfo;
import com.zb.socket.model.MapBody;
import com.zb.socket.model.MsgType;
import com.zb.socket.model.SmType;
import com.zb.socket.model.SocketHandler;

@Service
public class SakuranService extends RoomService {
	static final Logger LOGGER = LoggerFactory.getLogger(SakuranService.class);

	private static final int TIME_BET = 60;// 押注限时
	private static final int TIME_ROTATE = 60;// 开盘限时 +缓冲5s
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
	private static final long BUFFER_MAX = 1000 * 10000L;// buffer池回收上限
	private static final long COIN_MAX = 200 * 10000L;// coin池回收上限
	private static final int HERO_NUM = 8; // 每次获取英雄数量
	private static final int DICE_MAX = 20000; // dicemax
	private static final double STEP = 10;
	private static final double RATE_SE = 2;// 小秘书出现 金币翻倍

	private static int rand_scope = 100000;

	private static final String JUDGE_AVATAR = "http://zim.zhuangdianbi.com/62a724008edcd8fa4723ac59ae330378.png";
	private static final String JUDGE_NAME = "法官";
	private static final int JUDGE_SEX = 1;

	// -1 死 0 移动 1 砍 2 被打 3 回血
	private static final int ACT_DIE = -1;// 死亡
	private static final int ACT_HIT = 1;// 打人
	private static final int ACT_BEATEN = 2;// 被打
	private static final int ACT_MOV = 0;// 移动
	private static final int ACT_BAK = 3;// 回血

	@Autowired
	RankListService rankListService;

	@Autowired
	WinCoinRankService winCoinRankService;

	@Override
	public ReMsg handle(Room room, long fr) {
		if (room.getType() != ActivityType.SAKURAN.getType()) {
			log.error(room.get_id() + " " + room.getType());
			return new ReMsg(ReCode.FAIL);
		}
		Sakuran sa = super.getActivity(room.get_id(), Sakuran.class);
		if (Sakuran.STATUS_READY == sa.getStatus()) {
			sakuranStartBet(room, sa); // 下注
		} else if (Sakuran.STATUS_BET == sa.getStatus()) {
			sakuranRotate(room, sa); // 开盘
		} else if (Sakuran.STATUS_ROTATE == sa.getStatus() || Sakuran.STATUS_SETTLEMENT == sa.getStatus()) {
			sakuranNext(room, sa); // 下轮
		}
		return new ReMsg(ReCode.FAIL);
	}

	/** 获取期号 */
	public String getBetNo(Sakuran sa) {
		SakuranModel model = querySm();
		sa.setInning(null == model ? 1 : model.getInning() + 1);
		return DateUtil.dateFormatyyyyMMdd(new Date())
				.concat(String.format("%04d", null == model ? 1 : model.getInning() + 1));
	}

	/** 开始下注 */
	private void sakuranStartBet(Room room, Sakuran sa) {
		long roomId = room.get_id();
		try {
			// 初始化概率和赔率
			double[] rateArr = new double[8];
			double[] odds = new double[8];
			List<Map<String, Object>> slist = getSakurans();
			int i = 0;
			for (Map<String, Object> map : slist) {
				rateArr[i] = MapUtil.getDouble(map, "rate") == null ? 0.125 : MapUtil.getDouble(map, "rate");
				odds[i] = MapUtil.getDouble(map, "arate");
				i++;
			}
			String betNo = getBetNo(sa);
			sa.setRateArr(rateArr);
			sa.setOdds(odds);
			sa.setBetNo(betNo);
			sa.setStatus(Sakuran.STATUS_BET);
			String acMoney = super.getRedisTemplate().opsForValue().get(RK.activityMoney(roomId));
			long aclMoney = Long.parseLong(acMoney = null == acMoney ? "0" : acMoney);
			long allCoins = getAll(aclMoney);
			MapBody mb = new MapBody(SocketHandler.SAKURAN_BET).append(SocketHandler.FN_ROOM_ID, room.get_id())
					.append(SocketHandler.FN_LIMIT, TIME_BET).append("bg", RandomUtil.nextInt(3) + 1)
					.append("allCoins", allCoins);
			super.pubRoomMsg(roomId, MsgType.SAKURAN, mb);
			super.saveActivity(roomId, sa);
		} catch (Exception e) {
			log.error("下注倒计时出错", e);
		} finally {
			addJob(room.get_id(), TIME_BET);
		}
	}

	/** 开盘 */
	@SuppressWarnings("serial")
	private void sakuranRotate(Room room, Sakuran uc) {
		try {
			boolean hasJudge = false;
			boolean hasSe = false;
			SakuranModelPool pool = querySmPool();
			Long juTime = pool.getNextJuTime();
			Long seTime = pool.getNextSeTime();
			long now = System.currentTimeMillis();
			juTime = null == juTime ? now + (10 + RandomUtil.nextInt(20) * 2) * Const.MINUTE + 41 * Const.SECOND
					: juTime;
			seTime = null == seTime ? now + (10 + RandomUtil.nextInt(20) * 2) * Const.MINUTE + 31 * Const.SECOND
					: seTime;
			// 到达出现时间附近 或者45min以上未出现
			if (Math.abs(juTime - now) < Const.SECOND * 100 || now - pool.getLastJuTime() > Const.MINUTE * 35) {
				pool.setLastJuTime(now);
				pool.setNextJuTime(now + Const.MINUTE * 30 + 41 * Const.SECOND);
				hasJudge = true;
			}
			// 到达出现时间附近 或者35min以上未出现
			if (Math.abs(seTime - now) < Const.SECOND * 70 || now - pool.getLastSeTime() > Const.MINUTE * 25) {
				pool.setLastSeTime(now);
				pool.setNextSeTime(now + Const.MINUTE * 20 + 31 * Const.SECOND);
				hasSe = true;
			}
			uc.setHasJu(hasJudge);
			uc.setHasSe(hasSe);
			if (hasJudge || hasSe) {
				super.getMongoTemplate().save(pool);
			}
			MapBody mb = new MapBody(SocketHandler.SAKURAN_ROTATE).append(SocketHandler.FN_ROOM_ID, room.get_id())
					.append(SocketHandler.FN_LIMIT, TIME_ROTATE).append("hasJudge", hasJudge).append("hasSe", hasSe);
			uc.setStatus(Sakuran.STATUS_ROTATE);
			super.pubRoomMsg(room.get_id(), MsgType.SAKURAN, mb);
			super.saveActivity(room.get_id(), uc);
			int protagonistIndex = makeLotteries(uc, room.get_id()); // 生成开奖号码
			settlement(room, uc, protagonistIndex);
		} catch (Exception e) {
			log.error("开盘error", e);
		} finally {
			addJob(room.get_id(), TIME_ROTATE);
		}
	}

	/** 重新开始 */
	private void sakuranNext(Room room, Sakuran sa) {
		try {
			// 加币
			Map<Long, Integer> coins = sa.getCoins();
			for (Entry<Long, Integer> entry : coins.entrySet()) {
				coinService.addCoin(entry.getKey(), CoinLog.SAKURAN, sa.get_id(), entry.getValue(), 0L, "花魁大乱斗赢得金币");
			}
			super.delActivity(room.get_id());
		} catch (Exception e) {
			log.error("restarterror", e);
		} finally {
			addJob(room.get_id(), TIME_NEXT);
		}
	}

	/** 获取开奖号 */
	private int nextBrodIndex(Sakuran uc, Long roomId, boolean first) {
		double[] rrates = new double[8];
		double[] rates = uc.getRateArr();
		if (first) {
			if (rates == null) {
				return RandomUtil.nextInt(8) + 1;
			}
			String arrStr = super.getRedisTemplate().opsForValue().get(RK.everyMoney(roomId));
			List<Integer> list = null;
			if (null == arrStr || JSONUtil.jsonToArray(arrStr, Integer.class).isEmpty()) {
				list = new ArrayList<>();
				for (int i = 0; i < 8; i++) {
					list.add(i, 0);
				}
			} else {
				list = JSONUtil.jsonToArray(arrStr, Integer.class);
			}
//			 List<Integer> list = new ArrayList<Integer>();
//			 for (int i = 0; i < 8; i++) {
//			 list.add(RandomUtil.nextInt(100000));
//			 }
			double sum = 0;
			double[] todds = new double[8];
			for (int i = 0; i < list.size(); i++) {
				double b = list.get(i);
				if (b < 100) {
					b = 100;
				}
				double h = 10000 / Math.sqrt(b) * rates[i];
				todds[i] = h;
				sum += h;
			}

			for (int i = 0; i < 8; i++) {
				rrates[i] = todds[i] / sum;
			}
			uc.setRateArr(rrates);
		} else {
			rrates = rates;
		}
		int n = RandomUtil.nextInt(rand_scope);
		int curRate = 0;
		for (int i = 0; i < 8; i++) {
			curRate += rrates[i] * rand_scope;
//			 System.out.println(
//			 i +  " |" +
//			 rrates[i] + "| " + curRate);
			if (curRate > n) {
				return i + 1;
			}
		}
		return 8;
	}

	public static void main(String[] args) {
		SakuranService ss = new SakuranService();
		Sakuran uc = new Sakuran();
		double[] rates = { 0.01, 0.02, 0.02, 0.03, 0.12, 0.20, 0.38, 0.22 };
		uc.setRateArr(rates);
		uc.setHasJu(false);
		ss.nextBrodIndex(uc, 1L, true);
	}

	/** 获取本局花魁 */
	List<Long> getIds() {
		List<Map<String, Object>> temp = getSakurans();
		List<Long> ids = new ArrayList<>();
		for (Map<String, Object> map : temp) {
			ids.add(MapUtil.getLong(map, "uid"));
		}
		return ids;
	}

	/** 查询花魁 */
	List<Map<String, Object>> getSakurans() {
		String json = super.getRedisTemplate().opsForValue().get(RK.nextGameInfo("SAKURAN"));
		if (null != json) {
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> temp = JSONUtil.jsonToArray(json);
			return temp;
		}
		cacheNextInfo();
		return getSakurans();
	}

	// public static void main(String[] args) {
	// SakuranService ss = new SakuranService();
	// Sakuran uc = new Sakuran();
	// uc.setHasJu(false);
	// ss.nextAction(1, uc, 1);
	// }

	private int befBlood(SakuranUser su, int idx) {
		int r = 3 * (100 - su.getBlood()) / idx / 2 + 3 - RandomUtil.nextInt(7);
		int blood = su.getBlood() + r;
		return blood > 100 ? 100 : blood;
	}

	private void setToPlays(int idx, Map<Long, SakuranUser> usersMap, Set<String> locs, SakuranUser w, SakuranUser l,
			Set<Long> curKds, Set<Long> curUpbs, Map<Long, Integer> hits) {
		curKds.add(l.getAuid());
		if (l.getBlood() > 0) {
			if (idx > 5 && idx < 13 && RandomUtil.nextInt(100) < 6 + idx) {
				l.setBlood(this.befBlood(l, idx));
				l.setAction(ACT_BAK);
				curUpbs.add(l.getAuid());
			} else {
				l.setAction(ACT_BEATEN);
			}
		} else {
			l.setAction(ACT_DIE);
		}
		w.setAction(ACT_HIT);
		Integer c = hits.get(l.getAuid());
		if (c == null) {
			c = 1;
		} else {
			c++;
		}
		hits.put(l.getAuid(), c);
		w.setActionTo(l.getAuid());
		l.setActionTo(w.getAuid());
		playTwoMove(usersMap, locs, w, l);
	}

	/** 初始化动画 */
	SakuranScene[] nextAction(int lottery, Sakuran uc, int protagonistIndex) {
		boolean hasJu = uc.isHasJu();
		boolean muchMoney = false;
		if (hasJu && lottery == 0) {
			muchMoney = true;// 法官出现，且死亡，触发大宝藏
		}
		SakuranScene[] actions = new SakuranScene[15];
		lottery--;
		SakuranScene a0 = new SakuranScene();
		List<Long> ids = getIds();
		Map<Long, SakuranUser> usersMap = new HashMap<>();
		for (int i = 0; i < HERO_NUM; i++) {
			long id = ids.get(i);
			SakuranUser vo = new SakuranUser(i);
			vo.setAuid(id);
			// 正常情况下的主角
			if (i == lottery) {
				vo.setProtagonist(true);
			} else if (-1 == lottery && i == protagonistIndex) {
				// 法官出现的时候 谁最后获胜
				vo.setProtagonist(true);
			}
			usersMap.put(id, vo);
		}

		if (hasJu) {// 法官参战
			ids.add(10000L);
			SakuranUser user = new SakuranUser();
			user.setAuid(10000);
			user.setBlood(100);
			user.setX(2);
			user.setY(2);
			usersMap.put(10000L, user);
		}
		a0.setUsers(getPlays(usersMap.values()));
		a0.setIndex(0);
		actions[0] = a0;

		List<Long> killed = new ArrayList<Long>();
		List<Long> toKillers = new ArrayList<Long>();
		Set<Long> curkds = new HashSet<Long>();
		Set<String> locs = new HashSet<String>();
		SakuranScene a14 = new SakuranScene();
		Map<Long, Integer> hits = new HashMap<Long, Integer>();

		for (SakuranUser su : usersMap.values()) {
			su.setBlood(0);
			if (su.isProtagonist() || (su.getAuid() == 10000 && !muchMoney)) {
				su.setAction(ACT_HIT);
				int cnt = killed.size();
				if (cnt > 0) {
					int idx = RandomUtil.nextInt(cnt);
					SakuranUser asu = usersMap.get(killed.get(idx));
					killed.remove(idx);
					locs.remove(asu.getX() + "" + asu.getY());
					setToPlays(14, usersMap, locs, su, asu, curkds, null, hits);
					su.setBlood(befBlood(su, 14));
				} else {
					toKillers.add(su.getAuid());
				}
			} else {
				su.setAction(ACT_DIE);
				if (toKillers.size() > 0) {
					SakuranUser asu = usersMap.get(toKillers.get(0));
					toKillers.remove(0);
					locs.remove(su.getX() + "" + su.getY());
					setToPlays(14, usersMap, locs, asu, su, curkds, null, hits);
					asu.setBlood(befBlood(su, 14));
				} else {
					killed.add(su.getAuid());
					if (su.getBlood() > 0) {
						su = neutralPlay(locs, su);
					}
				}
			}
		}

		a14.setUsers(getPlays(usersMap.values()));
		a14.setIndex(14);
		actions[14] = a14;

		Set<Long> curUpbs = new HashSet<Long>();
		for (int i = 13; i > 0; i--) {
			// System.out.println(i);
			Set<Long> befkds = curkds;
			Set<Long> befUpbs = curUpbs;
			killed = new ArrayList<Long>();// 被砍
			toKillers = new ArrayList<Long>();// 砍人
			curkds = new HashSet<Long>();
			curUpbs = new HashSet<Long>();
			locs = new HashSet<String>();
			Collections.shuffle(ids);
			int z = 0;
			for (int j = 0; j < ids.size(); j++) {
				Long id = ids.get(j);
				SakuranUser su = usersMap.get(id);
				su.setActionTo(0);
				if (befkds.contains(id)) {
					su.setBlood(this.befBlood(su, i));
				}
				if (befUpbs.contains(id)) {
					su.setBlood(this.befBlood(su, i));
				}
				if (su.getBlood() > 0) {
					su.setAction(ACT_MOV);
					z++;
				} else {
					su.setAction(ACT_DIE);
				}
				if (z % 2 == 1 && su.getBlood() > 0) {// 砍人
					if (killed.size() > 0) {
						SakuranUser asu = getMinSu(usersMap, killed);
						locs.remove(asu.getX() + "" + asu.getY());
						setToPlays(i, usersMap, locs, su, asu, curkds, curUpbs, hits);
					} else {
						toKillers.add(su.getAuid());
						if (su.getBlood() > 0) {
							su = neutralPlay(locs, su);
							// System.out.println("killer:" + su.getAuid() + "
							// x:" + su.getX() + " y:" + su.getY());
						}
					}
				} else {// 被砍
					if (toKillers.size() > 0) {
						SakuranUser asu = getMaxSu(usersMap, toKillers);
						locs.remove(asu.getX() + "" + asu.getY());
						setToPlays(i, usersMap, locs, asu, su, curkds, curUpbs, hits);
					} else {
						killed.add(su.getAuid());
						if (su.getBlood() > 0) {
							su = neutralPlay(locs, su);
							// System.out.println("killed:" + su.getAuid() + "
							// x:" + su.getX() + " y:" + su.getY());
						}
					}
				}

			}
			// System.out.println(i + " locs:" + locs.size());
			// for (String loc : locs) {
			// System.out.print(loc + " ");
			// }
			// System.out.println();
			SakuranScene a = new SakuranScene();
			a.setUsers(getPlays(usersMap.values()));
			a.setIndex(i);
			actions[i] = a;
		}
		Map<Long, Integer> ub = new HashMap<Long, Integer>();
		for (Long uid : ids) {
			ub.put(uid, 100);
		}
		for (int i = 1; i < 14; i++) {
			SakuranScene ss = actions[i];
			if (i == 1) {
				for (SakuranUser su : ss.getUsers()) {
					if (su.getAction() == ACT_HIT) {
						su.setBlood(100);
					} else if (su.getAction() == ACT_DIE) {
						su.setBlood(0);
					} else {
						int b = getRealBlood(hits, ub, su);
						su.setBlood(b);
						ub.put(su.getAuid(), b);
					}
				}
			} else {
				for (SakuranUser su : ss.getUsers()) {
					if (su.getAction() == ACT_DIE) {
						su.setBlood(0);
					} else if (su.getAction() == ACT_BEATEN) {
						int b = getRealBlood(hits, ub, su);
						su.setBlood(b);
						ub.put(su.getAuid(), b);
					} else if (su.getAction() == ACT_BAK) {
						int curB = ub.get(su.getAuid());
						int b = curB + (curB / hits.get(su.getAuid())) - RandomUtil.nextInt(10);
						if (b < curB) {
							b = curB + 10;
						}
						if (b > 100) {
							b = 92;
						}
						su.setBlood(b);
						ub.put(su.getAuid(), b);
					} else {
						su.setBlood(ub.get(su.getAuid()));
					}
				}
			}

		}

		// for (SakuranScene a : actions) {
		// System.out.println(a.getIndex());
		// for (SakuranUser su : a.getUsers()) {
		// System.out.println(su.getAuid() + "|t:" + su.getActionTo() + "|x:" +
		// su.getX() + "|Y:" + su.getY()
		// + "|A:" + su.getAction() + "|" + su.getBlood());
		// }
		// }
		return actions;
	}

	private int getRealBlood(Map<Long, Integer> hits, Map<Long, Integer> ub, SakuranUser su) {
		int blood = ub.get(su.getAuid()) - (100 / hits.get(su.getAuid())) - 5 + RandomUtil.nextInt(10);
		if (blood < 0) {
			blood = 5 + RandomUtil.nextInt(10);
		}
		return blood;
	}

	private SakuranUser getMinSu(Map<Long, SakuranUser> usersMap, List<Long> killed) {
		SakuranUser asu = null;
		int idx = 0;
		for (int i = 0; i < killed.size(); i++) {
			if (asu == null) {
				asu = usersMap.get(killed.get(idx));
			} else {
				SakuranUser su = usersMap.get(killed.get(i));
				if (asu.getBlood() > su.getBlood()) {
					asu = su;
					idx = i;
				}
			}
		}
		killed.remove(idx);
		return asu;
	}

	private SakuranUser getMaxSu(Map<Long, SakuranUser> usersMap, List<Long> killed) {
		SakuranUser asu = null;
		int idx = 0;
		for (int i = 0; i < killed.size(); i++) {
			if (asu == null) {
				asu = usersMap.get(killed.get(idx));
			} else {
				SakuranUser su = usersMap.get(killed.get(i));
				if (asu.getBlood() < su.getBlood()) {
					asu = su;
					idx = i;
				}
			}
		}
		killed.remove(idx);
		return asu;
	}

	private Collection<SakuranUser> getPlays(Collection<SakuranUser> sus) {
		Collection<SakuranUser> nsus = new HashSet<SakuranUser>();
		for (SakuranUser su : sus) {
			nsus.add(su.clone());
		}
		return nsus;
	}

	private SakuranUser neutralPlay(Set<String> locs, SakuranUser su) {
		su = playOneMove(locs, su);
		return su;
	}

	private SakuranUser playOneMove(Set<String> locs, SakuranUser su) {// 主动移动
		int x = getX(su.getX());
		int y = getY(su.getY());
		String loc = x + "" + y;
		for (int i = 0; i < 10; i++) {
			if (locs.contains(loc)) {
				x = getX(su.getX());
				y = getY(su.getY());
				loc = x + "" + y;
			} else {
				// System.out.println("one play:" + su.getAuid() + " " + loc);
				locs.add(loc);
				break;
			}
		}
		su.setX(x);
		su.setY(y);
		return su;
	}

	private void playTwoMove(Map<Long, SakuranUser> userMap, Set<String> locs, SakuranUser su, SakuranUser asu) {// 被动移动
		int x = getX(su.getX());
		int y = getY(su.getY());
		String loc = x + "" + y;
		int ax = getAx(su, asu, x);
		String aloc = ax + "" + y;
		for (int i = 0; i < 10; i++) {
			if (locs.contains(loc) || locs.contains(aloc)) {
				x = RandomUtil.nextInt(5);
				y = RandomUtil.nextInt(5);
				ax = getAx(su, asu, x);
				loc = x + "" + y;
				aloc = ax + "" + y;
			} else {
				locs.add(loc);
				locs.add(aloc);
				break;
			}
		}
		su.setX(x);
		su.setY(y);
		asu.setX(ax);
		asu.setY(y);
		userMap.put(asu.getAuid(), asu);
		userMap.put(su.getAuid(), su);
	}

	private int getAx(SakuranUser su, SakuranUser asu, int x) {
		int ax;
		if (su.getX() > asu.getX()) {
			if (x == 0) {
				ax = x + 1;
			} else {
				ax = x - 1;
			}
		} else {
			if (x == 4) {
				ax = x - 1;
			} else {
				ax = x + 1;
			}
		}
		return ax;
	}

	private int getX(int x) {
		int rx = RandomUtil.nextInt(2) + 1;
		if (RandomUtil.nextInt(10) % 2 == 0) {
			return rx + x < 5 ? rx + x : x - rx;
		} else {
			return x - rx > -1 ? x - rx : rx + x;
		}
	}

	private int getY(int y) {
		int ry = RandomUtil.nextInt(2) + 1;
		if (RandomUtil.nextInt(10) % 2 == 0) {
			return ry + y < 5 ? ry + y : y - ry;
		} else {
			return y - ry > -1 ? y - ry : ry + y;
		}
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
		Sakuran uc = super.getActivity(roomId, Sakuran.class);
		if (Sakuran.STATUS_BET == uc.getStatus() || Sakuran.STATUS_ROTATE == uc.getStatus()
				|| Sakuran.STATUS_SETTLEMENT == uc.getStatus()) {
			if (checkChoice(choice)) {
				if (ReCode.OK.reCode() == coinService
						.reduce(uid, CoinLog.SAKURAN, uc.get_id(), stakeCoin, 0L, "花魁大乱斗下注").reCode()) {
					SakuranActor ua = super.getRoomActor(roomId, uid, SakuranActor.class);
					if (null == ua) {
						return new ReMsg(ReCode.NET_WORK_ERROR);
					}
					List<Stake> stakes = ua.getStakes();
					int nowsum = getSum(stakes);
					int oneAll = stakeCoin + nowsum;
					if (oneAll > DICE_MAX) {
						return new ReMsg(ReCode.DICEMAX);
					}
					String acMoney = super.getRedisTemplate().opsForValue().get(RK.activityMoney(roomId));
					acMoney = null == acMoney ? "0" : acMoney;
					String arr = super.getRedisTemplate().opsForValue().get(RK.everyMoney(roomId));
					List<Integer> list = null;
					if (null == arr || JSONUtil.jsonToArray(arr, Integer.class).isEmpty()) {
						list = new ArrayList<>();
						for (int i = 0; i < 8; i++) {
							list.add(i, 0);
						}
					} else {
						list = JSONUtil.jsonToArray(arr, Integer.class);
						list.set(choice - 1, list.get(choice - 1) + stakeCoin);
					}
					super.getRedisTemplate().opsForValue().set(RK.everyMoney(roomId), JSONUtil.beanToJson(list));
					long aclMoney = Long.parseLong(acMoney) + stakeCoin;
					acMoney = Long.toString(aclMoney);
					super.getRedisTemplate().opsForValue().set(RK.activityMoney(roomId), acMoney);
					int lucky = getStar(stakeCoin, uid);
					long allCoin = getAll(aclMoney);
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
					MapBody mb = new MapBody(SocketHandler.SAKURAN_BETTING).append("allCoins", allCoin)
							.append("choice", choice).append("stakeCoin", stakeCoin).append("lucky", lucky)
							.append(SocketHandler.FN_USER_ID, uid).append("coin", coin);
					super.pubRoomMsg(roomId, MsgType.SAKURAN, mb);
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

	/** 获取积累幸运值 */
	private int getStar(int coin, long uid) {
		String all = (String) super.getRedisTemplate().opsForHash().get(RK.luckMoney(), "" + uid);
		all = null == all ? "0" : all;
		int ia = Integer.parseInt(all);
		ia += coin;
		if (coin != 0) {// 更新
			super.getRedisTemplate().opsForHash().put(RK.luckMoney(), "" + uid, ia + "");
		}
		return ia / 5000;
	}

	/** 获取所有金币 */
	private long getAll(long aclMoney) {
		SakuranModelPool pool = this.querySmPool();
		long coin = pool.getCoinPool();
		long buffer = pool.getBufferPool();
		long cache = pool.getCachePool();
		long incomePool = pool.getIncomePool();
		return incomePool + coin + buffer + cache + aclMoney + 1150000000L;// 11亿5千万
	}

	/** 判断下注有效性 */
	boolean checkChoice(int choice) {
		return SmType.canBet.contains(choice);
	}

	/** 结算 */
	void settlement(Room room, Sakuran uc, int protagonistIndex) {
		// 法官是否死亡
		boolean judgeDead = uc.getLottery() == 0 ? true : false;
		saveSakuran(uc, room.get_id(), protagonistIndex); // 保存对局信息
		uc.setStatus(Sakuran.STATUS_SETTLEMENT);
		super.saveActivity(room.get_id(), uc);
		Set<SakuranActor> as = super.getRoomActors(room.get_id(), SakuranActor.class);
		long dt = System.currentTimeMillis();
		List<String[]> winUserList = as.stream().filter(e -> e.getTotalWin() > 0)
				.sorted((e1, e2) -> e1.getTotalWin() - e2.getTotalWin())
				.map(e -> new String[] { e.getNickname(), Integer.toString(e.getTotalWin()) })
				.collect(Collectors.toList());
		SakuranScene[] ss = nextAction(uc.getLottery(), uc, protagonistIndex);
		List<DBObject> temp = cacheNextInfo();
		List<Map<String, Object>> slist = new ArrayList<>();
		slist = temp.stream().map(e -> {
			return new HashMap<String, Object>() {
				{
					put("auid", DboUtil.getLong(e, "uid"));
					put("aname", DboUtil.getString(e, "nickname"));
					put("avatar", DboUtil.getString(e, "avatar"));
					put("asex", DboUtil.getInt(e, "sex"));
					put("arate", DboUtil.getDouble(e, "arate"));
				}
			};
		}).collect(Collectors.toList());
		slist.add(new HashMap<String, Object>() {
			{
				put("auid", 10000L);
				put("aname", JUDGE_NAME);
				put("avatar", JUDGE_AVATAR);
				put("asex", JUDGE_SEX);
				put("arate", 0);
			}
		});
		if (judgeDead) {
			// 法官死了 清空幸运值
			super.getRedisTemplate().delete(RK.luckMoney());
			uc.setLottery(protagonistIndex);
			uc.setLotteryId(MapUtil.getLong(getHeroInfo(protagonistIndex), "uid"));
			super.saveActivity(room.get_id(), uc);
		}

		MapBody mb = new MapBody(SocketHandler.SAKURAN_SETTLEMENT).append(SocketHandler.FN_ROOM_ID, room.get_id())
				.append("winUserList", winUserList).append("lottery", uc.getLottery())
				.append("lotteryId", uc.getLotteryId()).append("actions", ss).append("allCoin", getAll(0))
				.append("slist", slist).append("hasJudge", uc.isHasJu()).append("hasSe", uc.isHasSe())
				.append("judgeDead", judgeDead);
		MapBody mb2 = new MapBody(SocketHandler.SAKURAN_REFRESH).append("slist", slist);
		super.pubRoomMsg(room.get_id(), MsgType.SAKURAN, mb2);
		if (uc.isHasSe()) {
			Map<Integer, Integer> map = new HashMap<>();
			for (int i = 0; i < 8; i++) {
				int num = RandomUtil.nextInt(2);
				if (num > 0) {
					map.put(i + 1, RandomUtil.nextInt(3) + 1);
				}
			}
			mb.append("buff", map);
		}
		String time = DateUtil.dateFormatyyyyMMdd(new Date());
		for (SakuranActor a : as) {
			long income = a.getTotalWin();
			int luckyCoin = a.getLuckyCoin();
			mb.append("income", income - luckyCoin).append("luckyCoin", luckyCoin);
			super.pubRoomUserMsg(room.get_id(), a.getUid(), MsgType.SAKURAN, mb, dt);
			if (income > 0) {
				winCoinRankService.saveWinLog(a.getUid(), time, income, ActivityType.SAKURAN.getType());
			}
		}

		// 重新修改房间用户信息
		Set<SakuranActor> actors = getRoomActors(room.get_id(), SakuranActor.class);
		Set<SakuranActor> initActors = new TreeSet<SakuranActor>();
		super.getRedisTemplate().opsForValue().set(RK.activityMoney(room.get_id()), "0");
		super.getRedisTemplate().opsForValue().set(RK.everyMoney(room.get_id()),
				JSONUtil.beanToJson(new ArrayList<Integer>(8)));
		int robitCount = 0;
		for (SakuranActor actor : actors) {
			if (Actor.STATUS_ONLINE != actor.getStatus()) {
				super.delRoomActor(room.get_id(), actor.getUid());
			} else {
				actor.setRole(SakuranActor.ROLE_EXECUTOR);
				actor.setStatus(SakuranActor.STATUS_ONLINE);
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
		super.updateRoom(room, RoomService.T_RESTART);
	}

	/** 遍历所有用户，组装model */
	SakuranModel assembleModel(Sakuran uc, final long roomId, int protagonistIndex) {
		SakuranModel model = querySm(uc.get_id());
		if (null == model) {
			int lottery = uc.getLottery();
			Map<String, Object> heroInfo = getHeroInfo(lottery == 0 ? protagonistIndex : lottery);
			String nickname = MapUtil.getStr(heroInfo, "nickname");
			String avatar = MapUtil.getStr(heroInfo, "avatar");
			model = new SakuranModel();
			Set<SakuranActor> actors = super.getRoomActors(roomId, SakuranActor.class);
			Set<SakuranUserVO> uas = new HashSet<>();
			long time = 0L;
			Double et = super.getJobEndTime(roomId);
			if (et != null) {
				time = T2TUtil.half_round(et);
			} else {
				time = System.currentTimeMillis();
			}

			// int allLuckyStars = 0;
			// int luckyStar = 0;
			Map<Long, Integer> luckyMap = new HashMap<Long, Integer>();
			// 押中杀死法官勇士的用户的幸运星
			if (uc.isHasJu() && lottery == 0) {
				int random = RandomUtil.nextInt(60);
				double temp = 0;
				for (SakuranActor actor : actors) {
					if (actor.isBet()) {
						for (Stake e : actor.getStakes()) {
							if (e.getChoice() == protagonistIndex) {
								// 每押注10000金币 随机数+1
								temp = getStar(0, actor.getUid()) + random;
								if (temp < 72) {// 60% 2-2.5倍
									temp = 1.1 + temp / 120;
								} else if (temp < 108) { // 30% 3-3.5倍
									temp = 2;
								} else { // 10% 4-4.5倍
									temp = 3;
								}
								luckyMap.put(actor.getUid(), (int) (temp * e.getCoin()));
							}
						}
					}
				}
			}

			// if (luckyMap.size() > 0) {
			// SakuranModelPool pool = querySmPool();
			// // 把对应的幸运星替换成金币
			// for (Entry<Long, Integer> entry : luckyMap.entrySet()) {
			// // 幸运星百分比
			// BigDecimal b = new BigDecimal(entry.getValue() / allLuckyStars);
			// double percentage = b.setScale(2,
			// BigDecimal.ROUND_HALF_UP).doubleValue();
			// // 放置大宝藏金币
			// luckyMap.put(entry.getKey(), (int) (percentage *
			// pool.getCoinPool()));
			// }
			// }

			// int luckyCoin;
			int allLuckCoins = 0;
			int luckyCoin = 0;
			for (SakuranActor actor : actors) {
				int winCoin = 0;
				int payCoin = actor.getTotalPay();
				if (actor.isBet()) {
					for (Stake e : actor.getStakes()) {
						// 押中 或者法官被杀
						if (lottery == e.getChoice() || lottery == 0) {
							winCoin += getMut(e.getChoice(), uc) * e.getCoin();
						}
					}
				}
				luckyCoin = luckyMap.get(actor.getUid()) == null ? 0 : luckyMap.get(actor.getUid());
				// 小秘书出现 基础奖励翻倍
				winCoin = uc.isHasSe() ? (int)(winCoin * RATE_SE) : winCoin;
				allLuckCoins = allLuckCoins + luckyCoin;
				SakuranUserVO u = new SakuranUserVO();
				u.setUid(actor.getUid());
				u.setStakes(actor.getStakes());
				u.setWinCoin(winCoin + luckyCoin);
				u.setPayCoin(payCoin);
				u.setLuckyCoin(luckyCoin);

				u.setIncome(winCoin + luckyCoin - payCoin);
				if (actor.isBet()) {
					uas.add(u);
				}
				actor.setTotalWin(winCoin + luckyCoin);
				actor.setLuckyCoin(luckyCoin);
				actor.setChangeCoin(u.getIncome());
				super.saveRoomActor(roomId, actor);
				if (winCoin + luckyCoin > 0) {
					// 存储加币信息
					uc.addCoins(actor.getUid(), winCoin + luckyCoin);
				}
			}

			long inCoin = uc.getInCoin();
			long outCoin = uc.getOutCoin() + allLuckCoins;
			model.setInCoin(inCoin);
			model.setOutCoin(outCoin);
			model.setTotalIncome(inCoin - outCoin);
			model.set_id(uc.get_id());
			model.setUas(uas);
			model.setHeroName(nickname);
			model.setDate(DateUtil.dateFormatyyyyMMdd(new Date()));
			model.setAvatar(avatar);
			model.setTime(time);
			model.setLotteryId(MapUtil.getLong(heroInfo, "uid"));
			model.setLottery(lottery == 0 ? protagonistIndex : lottery);
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
	public SakuranModel querySm(Long id) {
		DBObject dbo = getC(DocName.SAKURAN_MODEL).findOne(new BasicDBObject("_id", id));
		return DboUtil.toBean(dbo, SakuranModel.class);
	}

	/** 查询最新对局信息 */
	public SakuranModel querySm() {
		DBCursor cs = getC(DocName.SAKURAN_MODEL).find(new BasicDBObject()).sort(new BasicDBObject("_id", -1)).limit(1);
		if (cs.hasNext()) {
			DBObject dbo = cs.next();
			return DboUtil.toBean(dbo, SakuranModel.class);
		} else {
			return null;
		}
	}

	/** 查询最新碧池信息 */
	public SakuranModelPool querySmPool() {
		DBObject dbo = getC(DocName.SAKURAN_POOL).findOne(new BasicDBObject());
		if (null == dbo) {
			SakuranModelPool sakuranModelPool = new SakuranModelPool();
			sakuranModelPool.set_id(super.getNextId(DocName.SAKURAN_POOL));
			super.getMongoTemplate().save(sakuranModelPool);
			return sakuranModelPool;
		}
		return DboUtil.toBean(dbo, SakuranModelPool.class);
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
	void saveSakuran(Sakuran uc, final long roomId, int protagonistIndex) {
		SakuranModel model = assembleModel(uc, roomId, protagonistIndex);
		long inCoin = model.getInCoin();
		long outCoin = model.getOutCoin();
		exChange(model, inCoin, outCoin);
		output(model);
		super.getMongoTemplate().save(model);
		SakuranModelPool pool = querySmPool();
		if (null == pool) {
			pool = new SakuranModelPool();
			pool.set_id(uc.get_id());
		}
		pool.setUpdateTime(System.currentTimeMillis());
		pool.setCoinPool(model.getCoinPool() > 0 ? model.getCoinPool() : 0);
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
	void exChange(SakuranModel model, long inCoin, long outCoin) {
		long id = model.get_id();
		long coinPool = model.getCoinPool();//
		long incomePool = model.getIncomePool();
		long bufferPool = model.getBufferPool();// 交换池
		long cachePool = model.getCachePool();
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
		coinPool = coinPool + exchange > 0 ? coinPool + exchange : 100;
		bufferPool = bufferPool - exchange > 0 ? bufferPool - exchange : 100;
		model.setCoinPool(coinPool);
		model.setBufferPool(bufferPool);
		model.setIncomePool(incomePool);
		model.setCachePool(cachePool);
	}

	/** cache放钱 */
	void output(SakuranModel model) {
		long coinPool = model.getCoinPool();
		long cachePool = model.getCachePool();
		int nextCount = model.getNextGetCacheCount();
		if (nextCount < 1) {
			nextCount = RandomUtil.nextInt(100) + 10;
			model.setCoinPool(coinPool += cachePool * CACHE_GET);
			model.setCachePool(cachePool -= cachePool * CACHE_GET);
		}
		--nextCount;
		model.setNextGetCacheCount(nextCount);
	}

	/** 获取多次下注总和 */
	int getSum(List<Stake> a) {
		int ret = 0;
		for (Stake s : a) {
			ret += s.getCoin();
		}
		return ret;
	}

	/** 获取返还倍数 */
	private double getMut(int choice, Sakuran uc) {
		double[] odds = uc.getOdds();
		return odds[choice - 1];
	}

	/** 摇奖结果 */
	int makeLotteries(Sakuran uc, final long roomId) {
		SakuranModel model = querySm();
		SakuranModelPool pool = querySmPool();
		if (null == model) {
			model = new SakuranModel();
		}
		long outSum;
		long inSum;
		int index;
		int count = 0;
		boolean oneBig;
		if (blackUsers == null || System.currentTimeMillis() - qtime > 5 * Const.MINUTE) {
			blackUsers = queryBlackUserFromDB();
			qtime = System.currentTimeMillis();
		}
		int protagonistIndex = 1;
		boolean first = true;
		do {
			oneBig = false;
			outSum = 0L;
			inSum = 0L;
			if (count < 50) {
				count++;
			} else {
				index = 1;
				break;
			}
			if (uc.isHasJu() && RandomUtil.nextInt(100) > 50) {
				index = 0;
				protagonistIndex = nextBrodIndex(uc, roomId, first);
			} else {
				index = nextBrodIndex(uc, roomId, first);
			}
			first = false;
			Set<SakuranActor> actors = super.getRoomActors(roomId, SakuranActor.class);
			for (SakuranActor actor : actors) {
				if (!actor.isBet()) {
					continue;
				}
				int eachInSum = actor.getTotalPay(); // 每人花费金币
				List<Stake> list = actor.getStakes();
				int eachOutSum = 0;
				for (Stake e : list) {
					if (e.getChoice() == index || index == 0) {// 押中 或者法官死了
						eachOutSum += getMut(e.getChoice(), uc) * e.getCoin(); // 每人获得金币
						if (index == 0 && e.getChoice() == protagonistIndex) {// 押中了杀死法官的
																				// 宝藏奖励
							eachOutSum += e.getCoin() * 4;
						}
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
		} while (oneBig || checkEffectiveness(uc.isHasSe() ? (int)RATE_SE * outSum : outSum, inSum, pool.getCoinPool()));

		uc.setInCoin(inSum);
		uc.setOutCoin(uc.isHasSe() ? (int)RATE_SE * outSum : outSum);
		uc.setLottery(index);
		uc.setInning(model.getDate().equals(DateUtil.dateFormatyyyyMMdd(new Date())) ? model.getInning() + 1 : 1);
		uc.setCoinPool(pool.getCoinPool());
		uc.setCachePool(pool.getCachePool());
		uc.setBufferPool(pool.getBufferPool());
		uc.setNextGetCacheCount(pool.getNextGetCacheCount());
		uc.setIncomePool(pool.getIncomePool());
		uc.setLotteryId(MapUtil.getLong(getHeroInfo(index), "uid"));
		super.saveActivity(roomId, uc);
		return protagonistIndex;
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
		if (diff < 100000) {
			return false;
		}
		long rax = diff * 100 / coinPool;// 支出占总奖池的比的一半
		if (rax < 10) {// 中出总奖池的10%之内合理，直接开
			return false;
		} else if (rax < 50) {
			if (RandomUtil.nextInt(100) < rax / 2) {// 10%-25%的 重开几率
				log.error("收支差距过大 重新开");
				return true;
			}
		} else {
			if (RandomUtil.nextInt(100) < rax * 0.8) {// 40%-80%几率内 重新开
				log.error("收支差距过大 重新开");
				return true;
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

	@Override
	public ReMsg inRoom(long uid, Room r, int model, boolean robit) {
		SakuranRoomInfo ri = new SakuranRoomInfo(ActivityType.SAKURAN.getType());
		if (r == null || r.getType() != ActivityType.SAKURAN.getType()) {
			log.error(r.get_id() + " " + r.getType());
			ri.setCode(1);
			return new ReMsg(ReCode.FAIL, ri);
		}
		SakuranActor ua = super.getRoomActor(r.get_id(), uid, SakuranActor.class);
		if (ua != null) {
			ua.setStatus(Actor.STATUS_ONLINE);
			// ua.setExit(false);
		} else {
			DBObject user = userService.findById(uid);
			if (user == null) {
				ri.setCode(1);
				return new ReMsg(ReCode.FAIL, ri);
			}
			ua = new SakuranActor();
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
			ua.setGame("花魁大乱斗");
		}
		super.saveRoomActor(r.get_id(), ua);
		Set<SakuranActor> as = super.getRoomActors(r.get_id(), SakuranActor.class);
		super.changeRoomCount(r, as, T_NULL);
		// ri.setActors(as);
		Sakuran uc = super.getActivity(r.get_id(), Sakuran.class);
		Double t = super.getJobEndTime(r.get_id());
		if (null == t && Sakuran.STATUS_READY == uc.getStatus()) {
			r.setActivityDate(System.currentTimeMillis());
			this.updateRoom(r, T_NULL);
			sakuranStartBet(r, uc); // 游戏启动
		} else if (null == t && Sakuran.STATUS_READY != uc.getStatus()) {
			addJob(r.get_id(), TIME_COMMON);
		}
		ri.setStatus(Room.ACTIVITY);
		ri.setTimeType(uc.getStatus());
		long nextTime = 0;
		if (t != null) {
			nextTime = T2TUtil.half_round(t) - System.currentTimeMillis();
		}
		nextTime = nextTime < 0 ? 0 : nextTime;
		if (uc.getStatus() != Sakuran.STATUS_BET) {
			nextTime = nextTime + Const.MINUTE;
		}
		ri.setNextTime(nextTime / Const.SECOND); // 距离开盘时间
		// ri.setUcStatus(uc.getStatus());
		ri.setLottery(uc.getLottery());// 开奖结果
		ri.setStakes(ua.getStakes());
		String acMoney = super.getRedisTemplate().opsForValue().get(RK.activityMoney(r.get_id()));
		acMoney = null == acMoney ? "0" : acMoney;
		long allCoin = getAll(Long.parseLong(acMoney));
		ri.setPool(allCoin);
		String arr = super.getRedisTemplate().opsForValue().get(RK.everyMoney(r.get_id()));
		arr = null == arr ? JSONUtil.beanToJson(new ArrayList<Integer>(8)) : arr;
		List<Integer> list = JSONUtil.jsonToArray(arr, Integer.class);
		List<Map<String, Object>> slist = getSakurans();
		slist = slist.stream().map(e -> {
			return new HashMap<String, Object>() {
				{
					put("auid", MapUtil.getLong(e, "uid"));
					put("aname", MapUtil.getStr(e, "nickname"));
					put("avatar", MapUtil.getStr(e, "avatar"));
					put("asex", MapUtil.getInt(e, "sex"));
					put("arate", MapUtil.getDouble(e, "arate"));
				}
			};
		}).collect(Collectors.toList());
		slist.add(new HashMap<String, Object>() {
			{
				put("auid", 10000L);
				put("aname", JUDGE_NAME);
				put("avatar", JUDGE_AVATAR);
				put("asex", JUDGE_SEX);
				put("arate", 0);
			}
		});
		ri.setGrandTotal(list);
		ri.setSlist(slist);
		ri.setLucky(getStar(0, uid));
		SakuranModelPool pool = querySmPool();
		ri.setLastJuTime(pool.getLastJuTime());
		ri.setLastSeTime(pool.getLastSeTime());
		MapBody mb = new MapBody(SocketHandler.IN_ROOM, SocketHandler.FN_USER_ID, ua.getUid())
				.append("nickname", ua.getNickname()).append("sex", ua.getSex()).append("province", ua.getProvince())
				.append("city", ua.getCity()).append("role", ua.getRole());
		for (Actor a : as) {
			if (uid != a.getUid()) {
				super.pubRoomUserMsg(r.get_id(), a.getUid(), MsgType.ROOM, mb, System.currentTimeMillis());
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
		if (r.getType() != ActivityType.SAKURAN.getType()) {
			log.error(r.get_id() + " " + r.getType());
			return new ReMsg(ReCode.FAIL);
		}
		SakuranActor ua = super.getRoomActor(r.get_id(), uid, SakuranActor.class);
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
			Set<SakuranActor> as = super.getRoomActors(r.get_id(), SakuranActor.class);
			super.changeRoomCount(r, as, T_NULL);
		}
		return new ReMsg(ReCode.OK);
	}

	@Override
	public ReMsg getActorStatus(long uid, Room r) {
		return new ReMsg(ReCode.OK);
	}

	@Override
	public ReMsg canInRoom(int type, long actId, final long roomId) {
		Sakuran uc = super.getActivity(roomId, Sakuran.class);
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
	public List<DBObject> cacheNextInfo() {
		double[] rates = new double[8];//概率
		double sum = 0.0;
		List<DBObject> list = rankListService.get8Flower();
		List<Integer> flowers = new ArrayList<>();
		double allFlower = 0;
		for (int i = 0; i < list.size(); i++) {
			DBObject dbo = list.get(i);
			flowers.add(DboUtil.getInt(dbo, "count"));
		}
		allFlower = flowers.stream().reduce(0, Integer::sum);
		for (int i = 0; i < flowers.size(); i++) {
			BigDecimal b = new BigDecimal(0.01 + flowers.get(i) * 0.92 / allFlower);
			double rate = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			rates[i] = rate;
			sum = sum + (100 / rate);
		}
		for (int i = 0; i < flowers.size(); i++) {
			BigDecimal b = new BigDecimal((100 / rates[i] / sum) * 45);
			double temp = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			if(temp>10){
				temp = 8; 
			}
			list.get(i).put("rate", rates[i]);
			list.get(i).put("arate", temp < 1.5 ? 1.25 : temp);
		}

		super.getRedisTemplate().opsForValue().set(RK.nextGameInfo("SAKURAN"), JSONUtil.beanToJson(list));
		return list;
	}

	/** 获取本局英雄名称 */
	Map<String, Object> getHeroInfo(int index) {
		String json = super.getRedisTemplate().opsForValue().get(RK.nextGameInfo("SAKURAN"));
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> temp = JSONUtil.jsonToArray(json);
		Map<String, Object> map = null;
		if (index == 0) {
			map = new HashMap<>();
			map.put("uid", 10000L);
			map.put("nickname", JUDGE_NAME);
			map.put("avatar", JUDGE_AVATAR);
		}
		return index == 0 ? map : temp.get(index - 1);
	}

	/** 历史中奖纪录 */
	public List<DBObject> getGameHistory() {
		Sakuran sa = super.getActivity(99999901, Sakuran.class);
		int inning = sa.getInning();
		BasicDBObject q = new BasicDBObject();
		// if (inning > 0) {
		// q.put("inning", new BasicDBObject("$lt", inning));
		// }
		List<DBObject> list = getC(DocName.SAKURAN_MODEL).find(q).sort(new BasicDBObject("_id", -1))
				.limit(GAME_HISTORY_COUNT + 1).toArray();
		if (null != list && list.size() > 1) {
			if (DboUtil.getInt(list.get(0), "inning") == inning) {// 集合最顶端是当前局
				list.remove(0);
			} else {
				list.remove(list.size() - 1);
			}
		}
		return list;
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
		return getC(DocName.SAKURAN_MODEL).findOne(new BasicDBObject("_id", id));
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
		return getC(DocName.SAKURAN_MODEL).find(q).skip(super.getStart(page, size)).limit(getSize(size))
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
		return getC(DocName.SAKURAN_MODEL).find(q).count();
	}

	public void changePool(String type, Long change) {
		DBObject query = new BasicDBObject();
		DBObject update = new BasicDBObject("$inc", new BasicDBObject(type, change));
		getC(DocName.SAKURAN_POOL).findAndModify(query, update);
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
		List<DBObject> list = getC(DocName.SAKURAN_MODEL).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
		int count = getC(DocName.SAKURAN_MODEL).find(q).count();
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

	long qtime = 0L;
	Set<Long> blackUsers = null;

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
		BasicDBObject q = new BasicDBObject("_id", uid);
		super.getMongoTemplate().remove(q);
		return new ReMsg(ReCode.OK);
	}
}
