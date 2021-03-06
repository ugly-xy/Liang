package com.we.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.we.common.Constant.ReCode;
import com.we.common.mongo.DboUtil;
import com.we.common.redis.RK;
import com.we.common.utils.DateUtil;
import com.we.core.Page;
import com.we.core.web.ReMsg;
import com.we.models.DocName;
import com.we.models.UserWallet;
import com.we.models.finance.CoinLog;
import com.we.service.userTask.TokenPrjService;

@Service
public class UserWalletService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(UserWalletService.class);

	@Autowired
	UserService userService;

	@Autowired
	TokenPrjService coinTypeService;

	@Autowired
	UserShareService userShareService;

	public Page<DBObject> queryCoinLog(Long startDate, Long endDate, Integer io, Integer type, Long uid,
			String coinType, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = findCoinLog(startDate, endDate, io, type, uid, coinType, page, size);
		int count = countCoinLog(startDate, endDate, io, type, uid, coinType);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int countCoinLog(Long startDate, Long endDate, Integer io, Integer type, Long uid, String coinType) {
		DBObject q = new BasicDBObject();
		if (io != null && io != 0) {
			q.put("io", io);
		}
		if (uid != null && uid != 0) {
			q.put("uid", uid);
		}
		if (type != null && type != 0) {
			q.put("type", type);
		}
		if (StringUtils.isNotBlank(coinType)) {
			q.put("coinType", coinType);
		}
		if (startDate != null && startDate != 0 && endDate != null && endDate != 0) {
			q.put("updateTime", new BasicDBObject("$gte", startDate).append("$lt", endDate));
		} else if (startDate != null && startDate != 0) {
			q.put("updateTime", new BasicDBObject("$gte", startDate));
		} else if (endDate != null && endDate != 0) {
			q.put("updateTime", new BasicDBObject("$lt", endDate));
		}
		return getC(DocName.COIN_LOG).find(q).count();
	}

	public List<DBObject> findCoinLog(Long startDate, Long endDate, Integer io, Integer type, Long uid, String coinType,
			Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (io != null && io != 0) {
			q.put("io", io);
		}
		if (uid != null && uid != 0) {
			q.put("uid", uid);
		}
		if (type != null && type != 0) {
			q.put("type", type);
		}
		if (StringUtils.isNotBlank(coinType)) {
			q.put("coinType", coinType);
		}
		if (startDate != null && startDate != 0 && endDate != null && endDate != 0) {
			q.put("updateTime", new BasicDBObject("$gte", startDate).append("$lt", endDate));
		} else if (startDate != null && startDate != 0) {
			q.put("updateTime", new BasicDBObject("$gte", startDate));
		} else if (endDate != null && endDate != 0) {
			q.put("updateTime", new BasicDBObject("$lt", endDate));
		}
		return getC(DocName.COIN_LOG).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
	}

	public Page<DBObject> queryUserWallet(Long _id, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = findUserWallet(_id, page, size);
		int count = countUserWallet(_id);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int countUserWallet(Long _id) {
		DBObject q = new BasicDBObject();
		if (_id != null && _id != 0) {
			q.put("_id", _id);
		}
		return getC(DocName.USER_WALLET).find(q).count();
	}

	public List<DBObject> findUserWallet(Long _id, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (_id != null && _id != 0) {
			q.put("_id", _id);
		}
		return getC(DocName.USER_WALLET).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
	}

	public DBObject findById(long uid) {
		return getC(DocName.USER_WALLET).findOne(new BasicDBObject("_id", uid));
	}

	/** 后台增减货币 */
	public ReMsg adminChangeCoin(long adminId, int io, long uid, String coinType, double coin, String detail) {
		ReCode r = null;
		if (io == 1) {
			r = addCoin(uid, CoinLog.IN_ADMIN, uid, coinType, coin, adminId, detail);
		} else {
			r = reduce(uid, CoinLog.OUT_ADMIN, uid, coinType, coin, adminId, detail);
		}
		return new ReMsg(r);
	}

	public static final String COINS = "coins";

	/** 增加货币 */
	public ReCode addCoin(long uid, int type, long linkId, String coinType, double amount, long adminId,
			String detail) {
		if (amount < 1) {
			return ReCode.PARAMETER_ERR;
		}
		String coinKey = COINS + "." + coinType;

		DBObject incSeq = new BasicDBObject("$inc", new BasicDBObject(coinKey, amount)).append("$set",
				new BasicDBObject("updateTime", System.currentTimeMillis()));

		Object obj = getMongoTemplate().getCollection(DocName.USER_WALLET).findAndModify(new BasicDBObject("_id", uid),
				new BasicDBObject(coinKey, Integer.valueOf(1)), null, false, incSeq, true, true).get(COINS);

		HashMap<String, Double> res = (HashMap<String, Double>) obj;
		CoinLog coinLog = new CoinLog(super.getNextId(DocName.COIN_LOG), CoinLog.IN, type, linkId, coinType, uid,
				amount, res.get(coinType), adminId, detail);
		super.getMongoTemplate().save(coinLog);
		if (CoinLog.DEF_COIN_TYPE.equals(coinType)) {
			super.getRedisTemplate().delete(RK.userToday(uid));
		}
		return ReCode.OK;
	}

	// 奖励提成 m1 m2 m3
	double[] unit = new double[] { 0.05, 0.03, 0.02 };

	/** 根据任务奖励给上级也发奖励 使用地点:任务奖励/段位任务奖励 未接入 */
	// TODO
	public ReCode recursionAddCoin(long uid, int type, long linkId, String coinType, long amount, long adminId,
			String detail) {
		// 给自己加金币
		this.addCoin(uid, type, linkId, coinType, amount, adminId, detail);
		DBObject dbo = userService.findById(uid);
		DBObject shareDbo;
		// 最多往上加3级
		for (int i = 0; i < 3; i++) {
			if (dbo != null && DboUtil.getLong(dbo, "shareUid") != null) {
				// 我的上级
				shareDbo = userService.findById(DboUtil.getLong(dbo, "shareUid"));
				if (shareDbo != null) {
					// 我的上级对应的段位对应的加成
					// 奖励措施 amount*段位的提成奖励百分比*每级m奖励的提成
					int coinAmount = (int) (amount * unit[i]);
					this.addCoin(DboUtil.getLong(dbo, "shareUid"), type, linkId, coinType, coinAmount, adminId,
							"나의 친구M" + (i + 1) + detail);// 我的m？级下级
					// 增加邀请网络获取金币数
					userShareService.addUserShareCoin(DboUtil.getLong(dbo, "shareUid"), coinAmount);
					dbo = shareDbo;
					continue;
				}
				break;
			}
		}
		return ReCode.OK;
	}

	/** 扣除货币 */
	public ReCode reduce(long uid, int type, long linkId, String coinType, Double amount, long adminId, String detail) {
		if (amount < 1) {
			return ReCode.PARAMETER_ERR;
		}
		double change = -amount;
		String coinKey = COINS + "." + coinType;

		DBObject incSeq = new BasicDBObject("$inc", new BasicDBObject(coinKey, change)).append("$set",
				new BasicDBObject("updateTime", System.currentTimeMillis()));

		Object obj = getMongoTemplate().getCollection(DocName.USER_WALLET).findAndModify(
				new BasicDBObject("_id", uid).append(coinKey, new BasicDBObject("$gte", amount)),
				new BasicDBObject(coinKey, Integer.valueOf(1)), null, false, incSeq, true, false);

		if (null == obj) {
			// 扣除货币失败 余额不足
			return ReCode.COIN_BALANCE_LOW;
		} else {
			// 取到返回结果
			HashMap<String, Object> res = (HashMap<String, Object>) obj;
			// 取到coins
			res = (HashMap<String, Object>) res.get(COINS);
			CoinLog coinLog = new CoinLog(super.getNextId(DocName.COIN_LOG), CoinLog.OUT, type, linkId, coinType, uid,
					amount, (Double) res.get(coinType), adminId, detail);
			super.getMongoTemplate().save(coinLog);
			return ReCode.OK;
		}
	}

	/** 强制扣除货币 */
	public ReCode forceReduce(long uid, int type, long linkId, String coinType, Double amount, long adminId,
			String detail) {
		if (amount < 1) {
			return ReCode.PARAMETER_ERR;
		}
		double change = -amount;
		String coinKey = COINS + "." + coinType;

		DBObject incSeq = new BasicDBObject("$inc", new BasicDBObject(coinKey, change)).append("$set",
				new BasicDBObject("updateTime", System.currentTimeMillis()));

		Object obj = getMongoTemplate().getCollection(DocName.USER_WALLET).findAndModify(new BasicDBObject("_id", uid),
				new BasicDBObject(coinKey, Integer.valueOf(1)), null, false, incSeq, true, true).get(COINS);

		HashMap<String, Double> res = (HashMap<String, Double>) obj;

		CoinLog coinLog = new CoinLog(super.getNextId(DocName.COIN_LOG), CoinLog.IN, type, linkId, coinType, uid,
				amount, res.get(coinType), adminId, detail);
		super.getMongoTemplate().save(coinLog);
		return ReCode.OK;
	}

	public DBObject queryUserCoin(long uid) {
		DBObject userWallet = this.findById(uid);
		if (userWallet != null) {
			List<DBObject> coinTypes = coinTypeService.findAllCoinType(null);
			Map<String, String> coinPics = new HashMap<String, String>();
			for (DBObject dbo : coinTypes) {
				coinPics.put(DboUtil.getString(dbo, "_id"), DboUtil.getString(dbo, "pic"));
			}
			userWallet.put("coinPics", coinPics);
		}
		return userWallet;
	}
	
	public Map<String, String> queryCoinPics() {
		List<DBObject> coinTypes = coinTypeService.findAllCoinType(null);
		Map<String, String> coinPics = new HashMap<String, String>();
		for (DBObject dbo : coinTypes) {
			coinPics.put(DboUtil.getString(dbo, "_id"), DboUtil.getString(dbo, "pic"));
		}
		return coinPics;
	}

	public Double getMyUserCoinsAmount(long uid) {
		DBObject userWallet = this.findById(uid);
		UserWallet uw = DboUtil.toBean(userWallet, UserWallet.class);
		if (uw != null) {
			return uw.getCoin(CoinLog.DEF_COIN_TYPE);
		}
		return 0.0d;
	}

	public Double getTodayCoinIn(long uid) {
		String amount = super.getRedisTemplate().opsForValue().get(RK.userToday(uid));
		if (amount != null) {
			return Double.valueOf(amount);
		}
		List<DBObject> dbos = this.findCoinLog(DateUtil.getTodayZeroTime(), System.currentTimeMillis(), CoinLog.IN, 0,
				uid, CoinLog.DEF_COIN_TYPE, 0, 10000);
		Double total = 0d;
		for (DBObject dbo : dbos) {
			total += DboUtil.getDouble(dbo, "amount");
		}
		super.getRedisTemplate().opsForValue().set(RK.userToday(uid), "" + total, 2, TimeUnit.HOURS);
		return total;
	}

	//
	public List<DBObject> queryUserAllCoins(Long uid) {
		DBObject obj = queryUserCoin(uid);
		List<DBObject> all = new ArrayList<DBObject>();
		Map<String, Object> pics = (Map<String, Object>) obj.get("coinPics");
		if (obj.containsField("coins")) {
			DBObject coins = (DBObject) obj.get("coins");
			if (coins != null) {
				for (String key : coins.keySet()) {
					DBObject db = new BasicDBObject();
					db.put("key", key);
					db.put("coin", coins.get(key));
					db.put("pic", pics.get(key));
					all.add(db);
				}
			}
		}
		return all;
	}

	public ReMsg queryUserContainsCoins(Long uid, String coinsType) {
		DBObject obj = this.findById(uid);
		if (obj.containsField("coins")) {
			DBObject coins = (DBObject) obj.get("coins");
			if (coins != null) {
				for (String key : coins.keySet()) {
					if (key.equals(coinsType)) {
						return new ReMsg(ReCode.OK);
					}
				}
			}
		}
		return new ReMsg(ReCode.PARAMETER_ERR);
	}

	// 该用户coinlog
	public Page<DBObject> queryCoinLogByUid(Long uid, Integer page, String coinType, Integer size) {
		return queryCoinLog(null, null, null, null, uid, coinType, page, size);
	}

}
