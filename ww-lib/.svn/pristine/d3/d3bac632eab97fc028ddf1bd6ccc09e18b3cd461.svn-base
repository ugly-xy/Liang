package com.zb.service.othergames;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.IMType;
import com.zb.common.Constant.OperationType;
import com.zb.common.Constant.Point;
import com.zb.common.Constant.PushConst;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.redis.RK;
import com.zb.common.utils.JSONUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.finance.CoinLog;
import com.zb.models.slave.SlaveJobs;
import com.zb.models.slave.SlaveSellLog;
import com.zb.models.slave.SlaveStealLog;
import com.zb.models.slave.StarEnum;
import com.zb.models.slave.StarEnum.MasterEnum;
import com.zb.models.slave.StarEnum.SlaveEnum;
import com.zb.models.slave.StarTrekMaster;
import com.zb.models.slave.StarTrekSlave;
import com.zb.models.user.RelationShipExt;
import com.zb.models.user.Relationship;
import com.zb.service.BaseService;
import com.zb.service.LoginLogService;
import com.zb.service.MessageService;
import com.zb.service.PushService;
import com.zb.service.RelationshipService;
import com.zb.service.UserService;
import com.zb.service.pay.CoinService;
import com.zb.service.room.vo.BuySlaveVO;
import com.zb.service.room.vo.GainSlaveVO;
import com.zb.service.room.vo.MyStarInfo;

@Service
public class StarTrekService extends BaseService {

	static final Logger LOGGER = LoggerFactory.getLogger(StarTrekService.class);
	private static final long WORK_TIME = 6 * Const.HOUR;
	// private static final long WORK_TIME = 30*Const.SECOND;
	private final static int MINE = 1; // 收自己的
	private final static int OTHERS = 2; // 偷别人的
	private final static int STAR_MAX_LEVEL = 10; // 星球满级
	private final static double WORTH_RELEASE = 0.5; // 释放后身价
	private final static double CAN_STEAL_RATE = 0.6; // 可偷金币占比
	private final static double ALL_STEAL_RATE = 0.2; // 剩余低于此百分比金币则全偷走
	private final static long MIN_WORTH = 100L; // 最低身价
	private final static String DEFAULT_WORK = "休息中";
	private final static int MONSTER_SORT = 1;
	private final static int CAN_GAIN_SORT = 2;
	private final static int CAN_SEND_SORT = 3;
	private final static int WORKING_SORT = 4;
	private final static int SHOULD_CALLBACK_SORT = 5;

	@Autowired
	CoinService coinService;

	@Autowired
	UserService userService;

	@Autowired
	MessageService messageService;

	@Autowired
	RelationshipService relationshipService;

	// @Autowired
	// EasemobService easemobService;

	@Autowired
	PushService pushService;

	@Autowired
	LoginLogService loginLogService;

	/**
	 * 查询好友列表
	 */
	public ReMsg queryFriends(long uid, int page) {
		int pageSize = 10;
		long now = System.currentTimeMillis();
		PageWithTime<DBObject> model = new PageWithTime<>();
		List<DBObject> friends = null;
		Page<DBObject> dbPage = null;
		String fjson = getRedisTemplate().opsForValue().get(RK.fristPageFriends(uid, page));
		if (null != fjson) {
			friends = JSONUtil.jsonToArray(fjson, RelationShipExt.class).stream().map(e -> DboUtil.beanToDBO(e))
					.collect(Collectors.toList());
		}
		if (null == friends || friends.isEmpty()) {
			dbPage = relationshipService.query(uid, null, Relationship.FRIENDS, page, pageSize, null);
			friends = dbPage.getList();
			getRedisTemplate().opsForValue().set(RK.fristPageFriends(uid, page), JSONUtil.beanToJson(friends), 10,
					TimeUnit.MINUTES);
		}
		for (DBObject dbo : friends) {
			long rid = DboUtil.getLong(dbo, "rid");
			DBObject star = null;
			String json = getRedisTemplate().opsForValue().get(RK.master(rid));
			if (null != json) {
				StarTrekMaster temp = JSONUtil.jsonToBean(json, StarTrekMaster.class);
				if (null != temp) {
					star = DboUtil.beanToDBO(temp);
				}
			} else {
				star = queryMyStar(rid);
				getRedisTemplate().opsForValue().set(RK.master(rid), JSONUtil.beanToJson(star), 1, TimeUnit.HOURS);
			}
			if (null == star) {
				dbo.put("canSteal", false);
				continue;
			}
			Long lastWorkTime = DboUtil.getLong(star, "lastWorkTime");
			boolean canSteal;
			Boolean beNew = DboUtil.getBool(star, "beNew");
			Long lastQueryTime = DboUtil.getLong(star, "lastQueryTime");
			beNew = null == beNew ? false : beNew;
			lastQueryTime = null == lastQueryTime ? 0 : lastQueryTime;
			if (beNew && now - lastQueryTime > 5 * Const.MINUTE) {
				beNew = false;
			}
			if (beNew) {
				canSteal = DboUtil.getBool(star, "canSteal");
			} else {
				BasicDBObject u = new BasicDBObject("beNew", true);
				lastWorkTime = null == lastWorkTime ? 0L : lastWorkTime;
				List<DBObject> slaves = queryJobsDoneSlaveByMasterId(rid);
				if (null == slaves) {
					canSteal = false;
				} else {
					slaves = slaves.stream().filter(e -> {
						int expectIncome = DboUtil.getInt(e, "expectIncome");
						int income = DboUtil.getInt(e, "income");
						Integer extIncome = DboUtil.getInt(e, "extIncome");
						return canBeSteal(expectIncome, income, extIncome);
					}).collect(Collectors.toList());
					if (slaves.isEmpty()) {
						canSteal = false;
					} else {
						canSteal = true;
						long endWorkTime = DboUtil.getLong(slaves.get(0), "endWorkTime");
						lastWorkTime = endWorkTime;
						u.append("lastWorkTime", lastWorkTime);
					}
				}
				u.append("canSteal", canSteal).append("lastQueryTime", now);
				super.getC(DocName.STAR_TREK_MASTER).update(new BasicDBObject("_id", rid),
						new BasicDBObject("$set", u));
			}
			dbo.put("lastWorkTime", lastWorkTime);
			dbo.put("canSteal", canSteal);
		}
		if (null == dbPage) {
			dbPage = new Page<>(500, pageSize, page, friends);
		}
		model.setPage(dbPage);
		model.setNow(System.currentTimeMillis());
		return new ReMsg(ReCode.OK, model);
	}

	/**
	 * 查询用户的工作完成奴隶
	 */
	public List<DBObject> queryJobsDoneSlaveByMasterId(long id) {
		long now = System.currentTimeMillis();
		List<Integer> ss = new ArrayList<>();
		ss.add(StarTrekSlave.WORKING);
		ss.add(StarTrekSlave.DEAD_WORKING);
		return getC(DocName.STAR_TREK_SLAVE)
				.find(new BasicDBObject("masterId", id).append("status", new BasicDBObject("$in", ss))
						.append("endWorkTime", new BasicDBObject("$lt", now)))
				.sort(new BasicDBObject("endWorkTime", -1)).toArray();
	}

	/** 校验是否是好友 */
	public boolean isNotFriend(long rid) {
		return Relationship.FRIENDS != (int) relationshipService.getRelation(rid).getData();
	}

	/** 查询是否有待收金币 */
	public ReMsg checkGain(long id) {
		int ret = 0;
		long now = System.currentTimeMillis();
		try {
			List<DBObject> list = queryAllSlaveByMasterId(id);
			if (!list.isEmpty()) {
				ret = list.parallelStream().map(e -> {
					if ((DboUtil.getLong(e, "status") == StarTrekSlave.WORKING
							|| DboUtil.getLong(e, "status") == StarTrekSlave.DEAD_WORKING)
							&& DboUtil.getLong(e, "endWorkTime") < now) {
						return 1;
					} else {
						return 0;
					}
				}).reduce((s, e) -> s + e).get();
			}
		} catch (Exception e) {
			LOGGER.error("查询是否有待收金币", e);
		}
		return new ReMsg(ReCode.OK, ret);
	}

	/** 个人信息 */
	public ReMsg userCenter(long id, Long uid) {
		long now = System.currentTimeMillis();
		id = null != uid ? uid : id;
		List<DBObject> list = mySlaves(id);
		DBObject muser = userService.findById(id);
		int ulevel = UserService.getLevel(DboUtil.getInt(muser, "point"));
		int slevel = getSlaveLevel(ulevel);
		if (!list.isEmpty()) {
			list = list.stream().map(e -> {
				long sid = DboUtil.getLong(e, "_id");
				long endWorkTime = DboUtil.getLong(e, "endWorkTime");
				int status = DboUtil.getInt(e, "status");
				if (DboUtil.getBool(e, "monster")) {
					int expectIncome = DboUtil.getInt(e, "expectIncome");
					int income = DboUtil.getInt(e, "income");
					Integer extIncome = DboUtil.getInt(e, "extIncome");
					boolean canGain = status == StarTrekSlave.WORKING && now >= endWorkTime
							&& canBeSteal(expectIncome, income, extIncome);
					return ((BasicDBObject) e).append("slaveLevel", slevel).append("canGain", canGain).append("sort",
							MONSTER_SORT);
				} else {
					DBObject user = userService.findById(sid);
					int ulevele = UserService.getLevel(DboUtil.getInt(user, "point"));
					int slevele = getSlaveLevel(ulevele);
					int expectIncome = DboUtil.getInt(e, "expectIncome");
					int income = DboUtil.getInt(e, "income");
					Integer extIncome = DboUtil.getInt(e, "extIncome");
					int sort = SHOULD_CALLBACK_SORT;
					if ((status == StarTrekSlave.WORKING || status == StarTrekSlave.DEAD_WORKING)
							&& now >= endWorkTime) {
						sort = CAN_GAIN_SORT;
					} else if (status == StarTrekSlave.SLEEPING) {
						sort = CAN_SEND_SORT;
					} else if (status == StarTrekSlave.WORKING) {
						sort = WORKING_SORT;
					}
					boolean canGain = sort == CAN_GAIN_SORT && canBeSteal(expectIncome, income, extIncome);
					BasicDBObject dbo = ((BasicDBObject) e).append("avatar", DboUtil.getString(user, "avatar"))
							.append("slaveLevel", slevele).append("username", DboUtil.getString(user, "nickname"))
							.append("canGain", canGain).append("sort", sort);
					if (status == StarTrekSlave.DEAD) {
						String ret = calculateUnloginDays(DboUtil.getLong(e, "_id"));
						dbo.append("work", "最近未登录，今天打工减少" + ret + "收益。");
					}
					return dbo;
				}
			}).sorted((e1, e2) -> {
				return (int) (DboUtil.getLong(e1, "sort") - DboUtil.getLong(e2, "sort"));
			}).collect(Collectors.toList());
		}
		DBObject master = queryMyStar(id); // 查询id作为主人
		if (null == master) {
			return new ReMsg(ReCode.NOT_OPEN_GAME);
		}
		DBObject slave = queryBySlaveId(id); // 查询id作为奴隶
		MyStarInfo si = new MyStarInfo();
		if (null != slave) {
			si.setWorth(DboUtil.getLong(slave, "worth"));
			si.setMasterId(DboUtil.getLong(slave, "masterId"));
			si.setMasterHead(DboUtil.getString(userService.findById(DboUtil.getLong(slave, "masterId")), "avatar"));
			si.setMasterNickname(
					DboUtil.getString(userService.findById(DboUtil.getLong(slave, "masterId")), "nickname"));
		}
		si.setStarLevel(DboUtil.getInt(master, "starLevel"));
		si.setAvailableBalance(DboUtil.getInt(master, "availableBalance"));
		si.setSlaveLevel(slevel);
		si.setSlave(list);
		final List<DBObject> flist = list;
		final long fid = id;
		CompletableFuture.runAsync(() -> {
			boolean canSteal = false;
			if (!flist.isEmpty() && flist.stream().filter(c -> true == DboUtil.getBool(c, "canGain")).count() > 0) {
				canSteal = true;
			}
			DBObject mdbo = getC(DocName.STAR_TREK_MASTER).findAndModify(
					new BasicDBObject("_id", fid), null, null, false, new BasicDBObject("$set", new BasicDBObject()
							.append("canSteal", canSteal).append("beNew", true).append("lastQueryTime", now)),
					true, false);
			getRedisTemplate().opsForValue().set(RK.master(fid), JSONUtil.beanToJson(mdbo), 1, TimeUnit.HOURS);
		});
		return new ReMsg(ReCode.OK, si);
	}

	/** 计算未登陆天数 */
	private String calculateUnloginDays(long sid) {
		return 100 * calculateAttenuationRate(sid) + "%";
	}

	/** 查询我的星球 */
	public DBObject queryMyStar(long id) {
		return getC(DocName.STAR_TREK_MASTER).findOne(new BasicDBObject("_id", id));
	};

	/** 查询用户的奴隶数量 */
	public int querySlaveCount(long id) {
		return getC(DocName.STAR_TREK_SLAVE).find(new BasicDBObject("masterId", id)).size();
	};

	/** 查询用户的所有奴隶 */
	public List<DBObject> queryAllSlaveByMasterId(long id) {
		checkSlave(id);
		return getC(DocName.STAR_TREK_SLAVE).find(new BasicDBObject("masterId", id)).toArray();
	}

	/** 查询奴隶详情 */
	public DBObject queryBySlaveId(long id) {
		return getC(DocName.STAR_TREK_SLAVE).findOne(new BasicDBObject("_id", id));
	}

	/** 查询我的奴隶 */
	public List<DBObject> mySlaves(long id) {
		if (0 == getC(DocName.STAR_TREK_SLAVE).find(new BasicDBObject("masterId", id)).count()) {
			StarTrekMaster master = new StarTrekMaster(id);
			super.getMongoTemplate().save(master);
			StarTrekSlave slave = new StarTrekSlave(getNextId(DocName.STAR_TREK_SLAVE), id, true);
			super.getMongoTemplate().save(slave);
			List<DBObject> list = new ArrayList<DBObject>();
			list.add(DboUtil.beanToDBO(slave));
			return list;
		}
		return queryAllSlaveByMasterId(id);
	}

	/** 赐名 */
	public ReMsg giveNickname(long uid, long sid, String name) {
		DBObject dbo = getC(DocName.STAR_TREK_SLAVE).findOne(new BasicDBObject("_id", sid));
		if (null != dbo && DboUtil.getLong(dbo, "masterId") == uid) {
			getC(DocName.STAR_TREK_SLAVE).update(new BasicDBObject("_id", sid),
					new BasicDBObject("$set", new BasicDBObject("nickname", name)));
			return new ReMsg(ReCode.OK, name);
		} else {
			return new ReMsg(ReCode.FAIL);
		}
	}

	/** 获取工作内容 */
	public ReMsg getWork() {
		BasicDBObject q = new BasicDBObject("state", SlaveJobs.REVIEWED);
		int count = getC(DocName.SLAVE_JOBS).find(q).size();
		int index = RandomUtil.nextInt(count > 20 ? count - 10 : count);
		List<DBObject> list = getC(DocName.SLAVE_JOBS).find(q).skip(index).limit(10).toArray();
		return new ReMsg(ReCode.OK, list);
	}

	/** 派遣奴隶工作 */
	public ReMsg sendSlave(long mid, long sid, String work) {
		DBObject slave = getC(DocName.STAR_TREK_SLAVE).findOne(new BasicDBObject("_id", sid));
		DBObject master = getC(DocName.STAR_TREK_MASTER).findOne(new BasicDBObject("_id", mid));
		if (null == slave || null == master || StarTrekSlave.WORKING == DboUtil.getInt(slave, "status")
				|| StarTrekSlave.DEAD_WORKING == DboUtil.getInt(slave, "status")) {// 不可用
			return new ReMsg(ReCode.SLAVE_UNAVAILABLE);
		} else {
			long expectIncome = calculateExpectIncome(sid, slave, mid);
			long extIncome = calculateExtIncome(master, expectIncome);
			long attenuationIncome = calculateAttenuationIncome(sid, slave, expectIncome);
			long now = System.currentTimeMillis();
			long endTimme = now + WORK_TIME;
			BasicDBObject u = new BasicDBObject("beginWorkTime", now).append("endWorkTime", endTimme)
					.append("status", attenuationIncome > 0 ? StarTrekSlave.DEAD_WORKING : StarTrekSlave.WORKING)
					.append("work", work).append("expectIncome", expectIncome)
					.append("income",
							attenuationIncome > 0 ? expectIncome - attenuationIncome : expectIncome + extIncome)
					.append("extIncome", extIncome).append("attenuationIncome", attenuationIncome);
			getC(DocName.STAR_TREK_SLAVE).update(new BasicDBObject("_id", sid),
					new BasicDBObject("$set", u).append("$inc", new BasicDBObject("processNumber", 1)), false, true);
			Map<String, Object> map = new HashMap<>();
			map.put("expectIncome", expectIncome);
			map.put("extIncome", extIncome);
			map.put("attenuationIncome", attenuationIncome);
			map.put("endWorkTime", endTimme);
			map.put("beginWorkTime", now);
			map.put("work", work);
			if (getC(DocName.SLAVE_JOBS).find(new BasicDBObject("word", work)).count() == 0) {
				SlaveJobs sj = new SlaveJobs(super.getNextId(DocName.SLAVE_JOBS), System.currentTimeMillis(),
						SlaveJobs.UNREVIEW, work);
				getMongoTemplate().save(sj);
			}
			Map<String, Object> ext = new HashMap<String, Object>();

			if (null != DboUtil.getBool(slave, "monster") && !DboUtil.getBool(slave, "monster")) {// bibiji
				ext.put("type", IMType.STAR.getType());
				ext.put("op", OperationType.SLAVE.getOp());
				ext.put("opId", mid + "");
				messageService.imMsgHandler(mid, sid, "[" + mid + "] 和 [" + sid + "] 一起 " + work, ext,
						IMType.STAR_TO_WORK.getType(), "我和你一起 " + work);
				// messageService.easeMsgHandler(mid, sid, "我派你去 " + work, ext,
				// null);
			}

			return new ReMsg(ReCode.OK, map);
		}
	}

	/** 计算预期收入 */
	private long calculateExpectIncome(long sid, DBObject slave, long mid) {
		int level;
		if (DboUtil.getBool(slave, "monster")) {
			level = UserService.getLevel(DboUtil.getInt(userService.findById(mid), "point"));
		} else {
			level = UserService.getLevel(DboUtil.getInt(userService.findById(sid), "point"));
		}
		SlaveEnum slaveEnum = StarEnum.SlaveEnum.slaveMap.get(getSlaveLevel(level));
		return level + slaveEnum.getIncome() + randomNum(slaveEnum.getExIncome());
	}

	/** 计算星球附加收益 */
	private long calculateExtIncome(DBObject master, long expectIncome) {
		Integer starLevel = DboUtil.getInt(master, "starLevel");
		MasterEnum masterEnum = StarEnum.MasterEnum.masterMap.get(starLevel);
		double incomeBuffer = masterEnum.getBuffer();
		return Math.round(expectIncome * incomeBuffer);
	}

	/** 计算衰减的值 */
	private long calculateAttenuationIncome(long sid, DBObject slave, long expectIncome) {
		int status = DboUtil.getInt(slave, "status");
		long ret = 0;
		if (status == StarTrekSlave.DEAD) {
			ret = Math.round(expectIncome * calculateAttenuationRate(sid));
		}
		return ret;
	}

	public static void main(String[] args) {
		int a = Math.round((Const.WEEK + Const.DAY + 443434888 - Const.WEEK) / Const.DAY + 1);
		BigDecimal bd = new BigDecimal(a);
		System.out.println(bd);
		double b = a * 0.1;
		BigDecimal bd2 = new BigDecimal(0.1);
		System.out.println(b);
		System.out.println(bd.multiply(bd2));
		System.out.println(bd.multiply(bd2));
		System.out.println(bd.multiply(bd2).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());
	}

	/** 计算衰减比例 */
	private double calculateAttenuationRate(long sid) {
		BigDecimal rate = new BigDecimal(0);
		List<DBObject> list = loginLogService.queryLastLogByUser(sid);
		long notLogin = lastLoginTime(list, sid);
		if (notLogin >= Const.WEEK) {
			if (notLogin >= 16 * Const.DAY) {
				rate = new BigDecimal(0.9);
			} else {
				rate = new BigDecimal(0.1 * ((notLogin - Const.WEEK) / Const.DAY + 1));
			}
		}
		return rate.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/** 最近登陆天数 */
	public long lastLoginTime(List<DBObject> list, long sid) {
		long now = System.currentTimeMillis();
		Long time;
		if (list.isEmpty()) {
			DBObject dbo = super.findById(DocName.USER, sid);
			if (DboUtil.getInt(dbo, "role") < 3) {
				time = DboUtil.getLong(dbo, "createTime");
			} else {
				time = DboUtil.getLong(dbo, "updateTime");
			}
		} else {
			time = DboUtil.getLong(list.get(0), "updateTime");
		}
		return now - time;
	}

	/** 随机数值 */
	private int randomNum(int num) {
		return RandomUtil.nextInt(++num);
	}

	/** 计算是剩余是否可偷 */
	private boolean canBeSteal(int expectIncome, int income, Integer extIncome) {
		if (expectIncome == 0) {
			return false;
		} else if (income == 0) {
			return false;
		} else {
			extIncome = null == extIncome ? 0 : extIncome;
			if ((expectIncome + extIncome) * (1 - CAN_STEAL_RATE) - income >= 0) {
				return false;
			}
		}
		return true;
	}

	/** 计算奴隶品质 */
	private int getSlaveLevel(int level) {
		return (level > 1 ? level - 1 : 0) / 20 + 1;
	}

	/** 收取收益 */
	public ReMsg gain(long uid, long sid) {
		// FIXME 暂无并发控制
		GainSlaveVO gs = new GainSlaveVO();
		DBObject slave = getC(DocName.STAR_TREK_SLAVE).findOne(new BasicDBObject("_id", sid));
		if (null == slave || DboUtil.getInt(slave, "status") != StarTrekSlave.WORKING
				&& DboUtil.getInt(slave, "status") != StarTrekSlave.DEAD_WORKING) {
			return new ReMsg(ReCode.NO_GAIN);
		} else if (DboUtil.getLong(slave, "endWorkTime") > System.currentTimeMillis()) {
			return new ReMsg(ReCode.TIME_NOT_ENOUGH);
		} else if (DboUtil.getLong(slave, "masterId") == uid) {
			int myIncome;
			boolean monster = DboUtil.getBool(slave, "monster");
			int expectIncome = DboUtil.getInt(slave, "expectIncome");
			Integer extIncome = DboUtil.getInt(slave, "extIncome");
			Integer attenuationIncome = DboUtil.getInt(slave, "attenuationIncome");
			extIncome = null == extIncome ? 0 : extIncome;
			attenuationIncome = null == attenuationIncome ? 0 : attenuationIncome;
			long processNumber = DboUtil.getLong(slave, "processNumber");
			myIncome = DboUtil.getInt(slave, "income");
			SlaveStealLog stealLog = new SlaveStealLog(getNextId(DocName.SLAVE_STEAL_LOG), System.currentTimeMillis(),
					sid, uid, processNumber, myIncome, System.currentTimeMillis(), MINE);
			super.getMongoTemplate().save(stealLog);
			coinService.addCoin(uid, CoinLog.SLAVE_STEAL, sid, myIncome, 0, "主人收金币");
			if (!monster) {
				userService.changePoint(sid, Point.WORK.getType(), expectIncome, 0);
			}
			BasicDBObject u = new BasicDBObject("income", 0).append("expectIncome", 0).append("extIncome", 0)
					.append("attenuationIncome", 0).append("work", DEFAULT_WORK);
			if (attenuationIncome > 0) {
				u.append("status", StarTrekSlave.DEAD);
			} else {
				u.append("status", StarTrekSlave.SLEEPING);
			}
			getC(DocName.STAR_TREK_SLAVE).update(new BasicDBObject("_id", sid), new BasicDBObject("$set", u));
			BasicDBObject q = new BasicDBObject("_id", uid);
			DBObject master = getC(DocName.STAR_TREK_MASTER).findOne(q);
			int availableBalance = DboUtil.getInt(master, "availableBalance");
			int starLevel = DboUtil.getInt(master, "starLevel");
			MasterEnum masterEnum = StarEnum.MasterEnum.masterMap.get(starLevel);
			int upGradePrice = masterEnum.getUpGradePrice();
			int newBalance = availableBalance + myIncome;
			if (newBalance >= upGradePrice && starLevel < STAR_MAX_LEVEL) {
				gs.setCanUp(true);
				int newAvailableBalance = newBalance - upGradePrice;
				int newStarLevel = starLevel + 1;
				getC(DocName.STAR_TREK_MASTER).update(q,
						new BasicDBObject("$set", new BasicDBObject("starLevel", newStarLevel)
								.append("availableBalance", newAvailableBalance).append("beNew", false)));
			} else {
				getC(DocName.STAR_TREK_MASTER).update(q, new BasicDBObject("$set",
						new BasicDBObject().append("availableBalance", newBalance).append("beNew", false)));
			}
			getRedisTemplate().opsForValue().getOperations().delete(RK.master(uid));
			gs.setType(GainSlaveVO.OWN);
			gs.setGain(myIncome);
			String work;
			if (attenuationIncome > 0) {
				String ret = calculateUnloginDays(sid);
				work = "最近未登录，今天打工减少" + ret + "收益。";
			} else {
				work = "正在休息~";
			}
			gs.setNextWork(work);
			return new ReMsg(ReCode.OK, gs);
		} else {
			long masterId = DboUtil.getLong(slave, "masterId");
			if (isNotFriend(masterId)) {
				return new ReMsg(ReCode.ADD_FRIEND_FIRST);
			}
			int expectIncome = DboUtil.getInt(slave, "expectIncome");
			Integer attenuationIncome = DboUtil.getInt(slave, "attenuationIncome");
			Integer extIncome = DboUtil.getInt(slave, "extIncome");
			int income = DboUtil.getInt(slave, "income");
			long processNumber = DboUtil.getLong(slave, "processNumber");
			if (getC(DocName.SLAVE_STEAL_LOG).find(
					new BasicDBObject("stealId", uid).append("slaveId", sid).append("processNumber", processNumber))
					.toArray().size() > 0) {
				return new ReMsg(ReCode.STEAL_YET);
			}
			int thisSteal;
			if (canBeSteal(expectIncome, income, extIncome)) { // 可偷
				if (null != attenuationIncome && attenuationIncome > 0) {
					expectIncome -= attenuationIncome;
				}
				int canSteal = (int) Math.round(expectIncome * CAN_STEAL_RATE); // 可偷取金币
				double mb = (RandomUtil.nextInt(16) + 10) * 0.01; // 每人偷取可偷金币的比例
				int surplus = income - expectIncome + canSteal; // 剩余可以偷取金币
				if (surplus <= ALL_STEAL_RATE * canSteal) {
					thisSteal = surplus;
				} else {
					thisSteal = (int) Math.round(expectIncome * mb); // 本次偷取金币
				}
				if (thisSteal >= surplus) {
					thisSteal = surplus;
					getC(DocName.STAR_TREK_MASTER).update(new BasicDBObject("_id", masterId).append("beNew", true),
							new BasicDBObject("$set", new BasicDBObject("beNew", false)), false, false);
					getRedisTemplate().opsForValue().getOperations().delete(RK.master(masterId));
				}
				income -= thisSteal; // 偷
				BasicDBObject u = new BasicDBObject("income", income);
				getC(DocName.STAR_TREK_SLAVE).update(new BasicDBObject("_id", sid), new BasicDBObject("$set", u));
				SlaveStealLog stealLog = new SlaveStealLog(getNextId(DocName.SLAVE_STEAL_LOG),
						System.currentTimeMillis(), sid, uid, processNumber, thisSteal, System.currentTimeMillis(),
						OTHERS);
				super.getMongoTemplate().save(stealLog);
				coinService.addCoin(uid, CoinLog.SLAVE_STEAL, sid, thisSteal, 0, "偷奴隶金币");
			} else {
				return new ReMsg(ReCode.LET_ME_OFF); // 给我留点
			}
			String slaveName = DboUtil.getBool(slave, "monster") ? "小撕鸡"
					: DboUtil.getString(userService.findById(sid), "nickname");
			Map<String, Object> ext = new HashMap<String, Object>();
			ext.put("type", IMType.STAR.getType());
			ext.put("op", OperationType.SLAVE.getOp());
			ext.put("opId", super.getUid() + "");
			messageService.imMsgHandler(Const.SYSTEM_ID, masterId,
					"你的奴隶 " + slaveName + " 打工收获的金币被 " + super.getUser("nickname") + " 偷走了一部分。", ext, null);
			// messageService.easeMsgHandler(Const.SYSTEM_ID, masterId,
			// "你的奴隶 " + slaveName + " 打工收获的金币被 " + super.getUser("nickname") +
			// " 偷走了一部分。", ext, null);

			gs.setType(GainSlaveVO.STEAL);
			gs.setGain(thisSteal);
			return new ReMsg(ReCode.OK, gs);
		}
	}

	/** 升级星球 */
	public ReMsg upGradeStar(long uid) {
		BasicDBObject q = new BasicDBObject("_id", uid);
		DBObject dbo = getC(DocName.STAR_TREK_MASTER).findOne(q);
		if (null == dbo) {
			return new ReMsg(ReCode.FAIL);
		} else {
			int starLevel = DboUtil.getInt(dbo, "starLevel");
			if (starLevel < STAR_MAX_LEVEL) {
				int upGradePrice = StarEnum.MasterEnum.masterMap.get(starLevel).getUpGradePrice();
				int availableBalance = DboUtil.getInt(dbo, "availableBalance");
				int needBalance = upGradePrice - availableBalance;
				ReCode code;
				if (needBalance <= 0) {
					code = ReCode.OK;
				} else {
					code = coinService.reduce(uid, CoinLog.STAR_UP_GRADE, ++starLevel, needBalance, 0, "升级星球");
				}
				if (code == ReCode.OK) {
					try {
						getC(DocName.STAR_TREK_MASTER).update(q,
								new BasicDBObject("$set", new BasicDBObject("starLevel", starLevel).append(
										"availableBalance", needBalance <= 0 ? availableBalance - upGradePrice : 0)));
					} catch (Exception e) {
						if (needBalance > 0) {
							coinService.addCoin(uid, CoinLog.STAR_UP_GRADE, starLevel, needBalance, 0, "升级失败");
							code = ReCode.ROLL_BACK;
						}
					}
				}
				return new ReMsg(code);
			} else {
				return new ReMsg(ReCode.MAX_LEVEL);
			}
		}
	}

	/** 买奴隶 */
	public ReMsg buySlave(long uid, long sid) {
		if (uid == sid) {
			return new ReMsg(ReCode.CANNOT_BUY_URSELF);
		}
		DBObject slave = this.queryBySlaveId(sid);
		if (null == slave) {
			StarTrekSlave ts = new StarTrekSlave(sid, 0L, false);
			ts.setLastBuyTime(0L);
			super.getMongoTemplate().save(ts);
			slave = DboUtil.beanToDBO(ts);
		}
		long worth = DboUtil.getLong(slave, "worth");
		Long masterId = DboUtil.getLong(slave, "masterId");
		if (DboUtil.getBool(slave, "monster")) {
			return new ReMsg(ReCode.IS_MONSTER);
		}
		if (isNotFriend(sid)) {
			return new ReMsg(ReCode.ADD_FRIEND_FIRST);
		}
		if (null != masterId && masterId - uid == 0) {
			return new ReMsg(ReCode.SLAVE_BOUGHT);
		}
		DBObject user = userService.findById(sid);
		if (null == user) {
			return new ReMsg(ReCode.FAIL);
		}
		if (null != DboUtil.getLong(user, "guard") && DboUtil.getLong(user, "guard") - masterId == 0) {
			return new ReMsg(ReCode.GUARD_FAIL);
		}
		int level = UserService.getLevel(DboUtil.getInt(user, "point"));
		DBObject master = getC(DocName.STAR_TREK_MASTER).findOne(new BasicDBObject("_id", uid));
		if (null == master) {
			return new ReMsg(ReCode.NOT_OPEN_GAME);
		}
		int slaveCount = querySlaveCount(uid);
		MasterEnum masterEnum = StarEnum.MasterEnum.masterMap.get(DboUtil.getInt(master, "starLevel"));
		if (masterEnum.getSlaveLimit() <= slaveCount) {
			return new ReMsg(ReCode.SLAVE_MAX);
		}
		SlaveEnum slaveEnum = StarEnum.SlaveEnum.slaveMap.get(getSlaveLevel(level));
		int protectMinutes = slaveEnum.getProtectMinutes();
		long lastBuyTime = DboUtil.getLong(slave, "lastBuyTime");
		long proTime = protectMinutes * Const.MINUTE - System.currentTimeMillis() + lastBuyTime;
		if (proTime >= 0) {
			return new ReMsg(ReCode.SLAVE_PROTECT, proTime);
		}
		int masterIncomeMax = StarEnum.BuyEnum.RATE.getMasterIncomeMax();
		int slaveIncomeMax = StarEnum.BuyEnum.RATE.getSlaveIncomeMax();
		int taxMax = StarEnum.BuyEnum.RATE.getTaxMax();
		long masterIncome = Math.round(worth * StarEnum.BuyEnum.RATE.getMasterIncomePercent());
		long slaveIncome = Math.round(worth * StarEnum.BuyEnum.RATE.getSlaveIncomePercent());
		long tax = Math.round(worth * StarEnum.BuyEnum.RATE.getTaxPercent());
		masterIncome = masterIncomeMax < masterIncome ? masterIncomeMax : masterIncome;
		slaveIncome = slaveIncomeMax < slaveIncome ? slaveIncomeMax : slaveIncome;
		tax = taxMax < tax ? taxMax : tax;
		long inc = masterIncome + slaveIncome + tax;
		long maxWorthInc = slaveEnum.getMaxWorthInc();
		inc = maxWorthInc > inc ? inc : maxWorthInc;
		long newWorth = masterId == 0 ? worth : worth + inc;
		ReCode code = coinService.reduce(uid, CoinLog.BUY_SLAVE, sid, (int) newWorth, 0, "购买奴隶");
		BuySlaveVO bs = new BuySlaveVO();
		if (code == ReCode.OK) {
			int swit;
			try {
				swit = SlaveSellLog.BUY_SUCCESS;
				BasicDBObject u = new BasicDBObject();
				int status = StarTrekSlave.DEAD == DboUtil.getInt(slave, "status")
						|| StarTrekSlave.DEAD_WORKING == DboUtil.getInt(slave, "status") ? StarTrekSlave.DEAD
								: StarTrekSlave.SLEEPING;
				u.append("masterId", uid).append("lastBuyTime", System.currentTimeMillis()).append("worth", newWorth)
						.append("status", status).append("work", DEFAULT_WORK);
				getC(DocName.STAR_TREK_SLAVE).update(new BasicDBObject("_id", sid), new BasicDBObject("$set", u));
			} catch (Exception e) {
				swit = SlaveSellLog.BUY_FAILURE;
				coinService.addCoin(uid, CoinLog.BUY_SLAVE, sid, (int) newWorth, 0, "购买失败");
				code = ReCode.ROLL_BACK;
				swit = SlaveSellLog.BUY_ROLLBACK;
			}
			if (swit == SlaveSellLog.BUY_SUCCESS) {
				if (null != masterId && masterId != 0) {
					getC(DocName.STAR_TREK_MASTER).update(new BasicDBObject("_id", masterId).append("beNew", true),
							new BasicDBObject("$set", new BasicDBObject("beNew", false)), false, false);
					getRedisTemplate().opsForValue().getOperations().delete(RK.master(masterId));
					coinService.addCoin(masterId, CoinLog.BUY_SLAVE, sid, (int) (masterIncome + worth), 0, "主人收益");
				}
				coinService.addCoin(sid, CoinLog.BUY_SLAVE, masterId, (int) slaveIncome, 0, "奴隶收益");
				userService.changePoint(sid, Point.SELL.getType(), (int) slaveIncome, 0);
				if (newWorth > MIN_WORTH) {
					bs.setFisrt(false);
				}
				bs.setNewMaster(uid);
				bs.setSlaveCoin((int) slaveIncome);
				bs.setOldMaster(masterId);
				bs.setOldMasterIncome((int) masterIncome);
				DBObject newMaster = userService.findById(uid);

				Map<String, Object> ext = new HashMap<String, Object>();
				ext.put("type", IMType.STAR.getType());
				ext.put("op", OperationType.SLAVE.getOp());
				ext.put("opId", uid + "");
				// 全部跳转新主人界面
				if (masterId == 0) {
					// 自己被抢
					messageService.imMsgHandler(Const.SYSTEM_ID, sid,
							"你被 " + DboUtil.getString(newMaster, "nickname") + " 抢走了！从此有了大靠山", ext,
							IMType.STAR_SLAVE_BUY.getType());
					// messageService.easeMsgHandler(Const.SYSTEM_ID, sid,
					// "你被 " + DboUtil.getString(newMaster, "nickname") + "
					// 抢走了！从此有了大靠山", ext,
					// IMType.STAR_SLAVE_BUY.getType());
				} else {
					// 奴隶被抢
					messageService.imMsgHandler(Const.SYSTEM_ID, masterId,
							"你的奴隶 " + DboUtil.getString(user, "nickname") + " 被 "
									+ DboUtil.getString(newMaster, "nickname") + " 抢走了！你获得了" + (masterIncome + worth)
									+ "金币转会费。",
							ext, null);
					// messageService.easeMsgHandler(Const.SYSTEM_ID, masterId,
					// "你的奴隶 " + DboUtil.getString(user, "nickname") + " 被 "
					// + DboUtil.getString(newMaster, "nickname") + " 抢走了！你获得了"
					// + (masterIncome + worth)
					// + "金币转会费。",
					// ext, null);
					// 自己被抢
					messageService.imMsgHandler(Const.SYSTEM_ID, sid,
							"你被 " + DboUtil.getString(newMaster, "nickname") + " 抢走了！主人赏你" + slaveIncome + "金币", ext,
							IMType.STAR_SLAVE_BUY.getType());
					// messageService.easeMsgHandler(Const.SYSTEM_ID, sid,
					// "你被 " + DboUtil.getString(newMaster, "nickname") + "
					// 抢走了！主人赏你" + slaveIncome + "金币", ext,
					// IMType.STAR_SLAVE_BUY.getType());
				}
			}
			SlaveSellLog sellLog = new SlaveSellLog(getNextId(DocName.SLAVE_SELL_LOG), uid, masterId, sid, newWorth,
					swit, masterIncome, slaveIncome, tax);
			super.getMongoTemplate().save(sellLog);
		}
		if (code == ReCode.OK) {
			return new ReMsg(ReCode.OK, bs);
		} else {
			return new ReMsg(code);
		}
	}

	/** 释放奴隶 */
	public ReMsg releaseSlave(long uid, long sid) {
		DBObject slave = this.queryBySlaveId(sid);
		if (null == slave) {
			return new ReMsg(ReCode.FAIL);
		} else {
			Long masterId = DboUtil.getLong(slave, "masterId");
			if (null != masterId && masterId - uid == 0) {
				if (DboUtil.getBool(slave, "monster")) {
					return new ReMsg(ReCode.CANNOT_ABANDON_UR_DOG);
				} else {
					long worth = DboUtil.getLong(slave, "worth");
					double temp = worth * WORTH_RELEASE;
					long newWorth = Math.round(temp);
					newWorth = newWorth < MIN_WORTH ? MIN_WORTH : newWorth;
					super.getC(DocName.STAR_TREK_SLAVE).update(new BasicDBObject("_id", sid), new BasicDBObject("$set",
							new BasicDBObject("worth", newWorth).append("masterId", 0L).append("lastBuyTime", 0L)));
					SlaveSellLog sellLog = new SlaveSellLog(getNextId(DocName.SLAVE_SELL_LOG), 0L, masterId, sid,
							newWorth, SlaveSellLog.RELEASE, 0L, 0L, 0L);
					super.getMongoTemplate().save(sellLog);

					Map<String, Object> ext = new HashMap<String, Object>();
					ext.put("type", IMType.STAR.getType());
					ext.put("op", OperationType.SLAVE.getOp());
					ext.put("opId", uid + "");
					messageService.imMsgHandler(Const.SYSTEM_ID, sid,
							"你被 " + DboUtil.getString(userService.findById(uid), "nickname") + " 抛弃了", ext, null);
					// messageService.easeMsgHandler(Const.SYSTEM_ID, sid,
					// "你被 " + DboUtil.getString(userService.findById(uid),
					// "nickname") + " 抛弃了", ext, null);
				}
				return new ReMsg(ReCode.OK);
			} else {
				return new ReMsg(ReCode.FAIL);
			}
		}
	}

	/** 拉黑奴隶 */
	public void blackSlave(long uid, long sid) {
		DBObject slave = this.queryBySlaveId(sid);
		if (null != slave) {
			Long masterId = DboUtil.getLong(slave, "masterId");
			if (masterId - uid == 0) {
				long worth = DboUtil.getLong(slave, "worth");
				super.getC(DocName.STAR_TREK_SLAVE).update(new BasicDBObject("_id", sid), new BasicDBObject("$set",
						new BasicDBObject().append("masterId", 0L).append("lastBuyTime", 0L)));
				SlaveSellLog sellLog = new SlaveSellLog(getNextId(DocName.SLAVE_SELL_LOG), 0L, masterId, sid, worth,
						SlaveSellLog.BLACK, 0L, 0L, 0L);
				super.getMongoTemplate().save(sellLog);
			}
		}
	}

	/** 检查奴隶状态 */
	private void checkSlave(long id) {
		DBObject master = getC(DocName.STAR_TREK_MASTER).findOne(new BasicDBObject("_id", id));
		if (null != master) {
			Long checkSlaveTime = DboUtil.getLong(master, "checkSlaveTime");
			if (0 == checkSlaveTime || System.currentTimeMillis() - checkSlaveTime > Const.HOUR) {
				List<DBObject> list = getC(DocName.STAR_TREK_SLAVE)
						.find(new BasicDBObject("masterId", id).append("monster", false)).toArray();
				for (DBObject slave : list) {
					try {
						deadOrRevive(slave);
					} catch (Exception e) {
						LOGGER.error("检查奴隶状态失败", e);
					}
				}
				getC(DocName.STAR_TREK_MASTER).update(new BasicDBObject("_id", id),
						new BasicDBObject("$set", new BasicDBObject("checkSlaveTime", System.currentTimeMillis())));
			}
		}
	}

	/** 死亡或者复活 */
	private void deadOrRevive(DBObject slave) {
		Long sid = DboUtil.getLong(slave, "_id");
		int status = DboUtil.getInt(slave, "status");
		long now = System.currentTimeMillis();
		List<DBObject> list = super.getC(DocName.LOGIN_LOG).find(new BasicDBObject("userId", sid))
				.sort(new BasicDBObject("updateTime", -1)).limit(1).toArray();
		Long time;
		if (list.isEmpty()) {
			DBObject dbo = super.findById(DocName.USER, sid);
			if (DboUtil.getInt(dbo, "role") < 3) {
				time = DboUtil.getLong(dbo, "createTime");
			} else {
				time = DboUtil.getLong(dbo, "updateTime");
			}
		} else {
			time = DboUtil.getLong(list.get(0), "updateTime");
		}
		if (status != StarTrekSlave.DEAD && status != StarTrekSlave.DEAD_WORKING && now - time >= Const.WEEK) {
			super.getC(DocName.STAR_TREK_SLAVE).update(new BasicDBObject("_id", sid),
					new BasicDBObject("$set", new BasicDBObject("status", StarTrekSlave.DEAD)));
		}
		if (status == StarTrekSlave.DEAD && now - time < Const.WEEK) {
			super.getC(DocName.STAR_TREK_SLAVE).update(new BasicDBObject("_id", sid),
					new BasicDBObject("$set", new BasicDBObject("status", StarTrekSlave.SLEEPING)));
		}
		if (status == StarTrekSlave.DEAD_WORKING && now - time < Const.WEEK) {
			super.getC(DocName.STAR_TREK_SLAVE).update(new BasicDBObject("_id", sid),
					new BasicDBObject("$set", new BasicDBObject("status", StarTrekSlave.WORKING)));
		}
	}

	/** 召回奴隶 */
	public ReMsg callBack(long sid) {
		long mid = super.getUid();
		if (mid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		Map<String, Object> ext = new HashMap<String, Object>();
		ext.put("type", IMType.STAR.getType());
		ext.put("op", OperationType.SLAVE.getOp());
		ext.put("opId", sid + "");
		Integer res = messageService.imMsgHandler(mid, sid, "你在干什么？快回来玩啊~~我需要你！", ext,
				IMType.STAR_SLAVE_CALLBACK.getType());
		// messageService.easeMsgHandler(mid, sid, "你在干什么？快回来玩啊~~我需要你！", ext,
		// IMType.STAR_SLAVE_CALLBACK.getType());
		if (null != res) {
			if (res == PushConst.FAIL_HOUR) {
				return new ReMsg(ReCode.CALLBACK_YET_HOUR, PushConst.STAR_SLAVE_CALLBACK.getEhour());
			} else if (res == PushConst.FAIL_DAY) {
				return new ReMsg(ReCode.CALLBACK_YET, PushConst.STAR_SLAVE_CALLBACK.getEday());
			}
		}
		return new ReMsg(ReCode.OK);
	}

	/** 查询工作词 */
	public Page<DBObject> queryJobs(Integer state, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		BasicDBObject q = new BasicDBObject();
		if (null != state && state != 2) {
			q.append("state", state);
		}
		List<DBObject> dbos = getC(DocName.SLAVE_JOBS).find(q).skip(super.getStart(page, size))
				.limit(super.getSize(size)).toArray();
		int count = getC(DocName.SLAVE_JOBS).find(q).count();
		return new Page<DBObject>(count, size, page, dbos);
	}

	/** 新增工作词 */
	public ReMsg addJob(String work) {
		String[] works = work.split(",");
		if (null == works || works.length == 0) {
			return new ReMsg(ReCode.FAIL);
		}
		for (String e : works) {
			e = e.trim();
			if (StringUtils.isBlank(e) || getC(DocName.SLAVE_JOBS).find(new BasicDBObject("word", e)).count() > 0) {
				continue;
			}
			SlaveJobs sj = new SlaveJobs(super.getNextId(DocName.SLAVE_JOBS), System.currentTimeMillis(),
					SlaveJobs.REVIEWED, e);
			super.getMongoTemplate().save(sj);
		}
		return new ReMsg(ReCode.OK);
	}

	/** 审核工作词 */
	public ReMsg slaveReview(Long id) {
		getC(DocName.SLAVE_JOBS).update(new BasicDBObject("_id", id),
				new BasicDBObject("$set", new BasicDBObject("state", SlaveJobs.REVIEWED)));
		return new ReMsg(ReCode.OK);
	}

	/** 下架工作词 */
	public ReMsg slaveReject(Long id) {
		getC(DocName.SLAVE_JOBS).update(new BasicDBObject("_id", id),
				new BasicDBObject("$set", new BasicDBObject("state", SlaveJobs.REJECTED)));
		return new ReMsg(ReCode.OK);
	}

	/** 最牛奴隶榜单 */
	public Page<DBObject> queryCowSlave(Integer page, Integer size) {
		page = super.getPage(page);
		size = super.getSize(size);
		List<DBObject> slaves = cowSlave(page, size);
		int count = cowSlaveCount();
		return new Page<>(count, size, page, slaves);
	}

	public List<DBObject> cowSlave(Integer page, Integer size) {
		BasicDBObject q = new BasicDBObject("monster", false);
		return getC(DocName.STAR_TREK_SLAVE).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("worth", -1)).toArray();
	}

	public int cowSlaveCount() {
		BasicDBObject q = new BasicDBObject("monster", false);
		return getC(DocName.STAR_TREK_SLAVE).find(q).count();
	}

	class PageWithTime<T> {
		private long now;
		private Page<T> page;

		public Page<T> getPage() {
			return page;
		}

		public void setPage(Page<T> page) {
			this.page = page;
		}

		public long getNow() {
			return now;
		}

		public void setNow(long now) {
			this.now = now;
		}

	}
}
