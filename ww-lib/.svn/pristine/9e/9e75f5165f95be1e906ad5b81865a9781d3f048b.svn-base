package com.zb.service.pay;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.http.CookieUtil;
import com.zb.common.mongo.DboUtil;
import com.zb.common.redis.RK;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.finance.CoinLog;
import com.zb.service.BaseService;
import com.zb.service.GoodsService;
import com.zb.service.MessageService;
import com.zb.service.RankListService;
import com.zb.service.UserService;
import com.zb.service.jobs.RankListJob;

@Service
public class CoinService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(CoinService.class);

	@Autowired
	UserService userService;
	@Autowired
	MessageService messageService;
	@Autowired
	GoodsService goodsService;

	public DBObject findById(Long id) {
		ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();
		String s = opsv.get(RK.keyUser(id));
		if (null == s) {
			DBObject user = getC(DocName.USER).findOne(new BasicDBObject("_id", id));
			if (null != user)
				opsv.set(RK.keyUser(id), JSON.serialize(user), 1, TimeUnit.HOURS);
			return user;
		} else {
			return (DBObject) JSON.parse(s);
		}
	}

	public long findLastCoin(Long id) {
		DBObject user = getC(DocName.USER).findOne(new BasicDBObject("_id", id),
				new BasicDBObject("coin", 1).append("_id", 0));
		return DboUtil.tryGetInt(user, "coin");
	}

	public Page<DBObject> queryCoinLog(Long startDate, Long endDate, Integer io, Integer type, Long uid, Integer page,
			Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = findCoinLog(startDate, endDate, io, type, uid, page, size);
		int count = countCoinLog(startDate, endDate, io, type, uid);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public int countCoinLog(Long startDate, Long endDate, Integer io, Integer type, Long uid) {
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
		if (startDate != null && startDate != 0 && endDate != null && endDate != 0) {
			q.put("updateTime", new BasicDBObject("$gte", startDate).append("$lt", endDate));
		} else if (startDate != null && startDate != 0) {
			q.put("updateTime", new BasicDBObject("$gte", startDate));
		} else if (endDate != null && endDate != 0) {
			q.put("updateTime", new BasicDBObject("$lt", endDate));
		}
		return getC(DocName.COIN_LOG).find(q).count();
	}

	public List<DBObject> findCoinLog(Long startDate, Long endDate, Integer io, Integer type, Long uid, Integer page,
			Integer size) {
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

	public List<DBObject> findCoinLog(Long lastId, Integer size) {
		DBObject q = new BasicDBObject("_id", new BasicDBObject("$gt", lastId));
		return getC(DocName.COIN_LOG).find(q).limit(getSize(size)).sort(new BasicDBObject("_id", 1)).toArray();
	}

	private static final String COIN = "coin";
	private static final DBObject seqField = new BasicDBObject(COIN, Integer.valueOf(1));

	// FIXME 后台增减金币
	public ReMsg adminChangeCoin(long adminId, int io, long uid, int coin, String detail, boolean push,
			boolean changeVip) {
		ReCode r = null;
		String addOrRed = "赠送";
		if (io == 1) {
			r = addCoin(uid, CoinLog.IN_ADMIN, uid, coin, adminId, detail);

		} else {
			addOrRed = "扣除";
			r = reduce(uid, CoinLog.OUT_ADMIN, uid, coin, adminId, detail);
		}
		if (r == ReCode.OK) {
			if (push) {
				String body = "系统" + addOrRed + "您" + coin + "金币";
				if (StringUtils.isNotBlank(detail)) {
					body = body + "," + addOrRed + "原因:" + detail + "。";
				}
				messageService.imMsgHandler(Const.SYSTEM_ID, uid, body, null, null);
			}
			if (io == 1 && changeVip) {
				userService.changeVip(uid, coin, 0L);// 增加vip值
			}
		}
		return new ReMsg(r);
	}

	public ReCode addCoin(long uid, int type, long linkId, int amount, long adminId, String detail) {
		if (amount < 1) {
			return ReCode.PARAMETER_ERR;
		}

		DBObject incSeq = new BasicDBObject("$inc", new BasicDBObject(COIN, amount)).append("$set",
				new BasicDBObject("updateTime", System.currentTimeMillis()));
		Object obj = getMongoTemplate().getCollection(DocName.USER)
				.findAndModify(new BasicDBObject("_id", uid), seqField, null, false, incSeq, true, true).get(COIN);
		if (obj == null) {
			return ReCode.ADD_COIN_FAIL;
		} else {
			int balance = 0;
			if (obj instanceof Double) {
				double s = (double) obj;
				balance = (int) s;
			} else if (obj instanceof Long) {
				long s = (long) obj;
				balance = (int) s;
			} else {
				balance = Integer.parseInt(obj.toString());
			}
			CoinLog pl = new CoinLog(super.getNextId(DocName.COIN_LOG), CoinLog.IN, type, linkId, uid, amount, balance,
					detail, adminId);
			super.getMongoTemplate().save(pl);
			super.getRedisTemplate().delete(RK.keyUser(uid));
			return ReCode.OK;
		}
	}

	public ReCode forceReduce(long uid, int type, long linkId, int amount, long adminId, String detail) {
		if (amount < 1) {
			return ReCode.PARAMETER_ERR;
		}
		int change = -amount;
		DBObject dbo = userService.findById(uid);
		if (dbo == null) {
			return ReCode.USER_ID_ERR;
		}

		DBObject incSeq = new BasicDBObject("$inc", new BasicDBObject(COIN, change)).append("$set",
				new BasicDBObject("updateTime", System.currentTimeMillis()));
		Object obj = getMongoTemplate().getCollection(DocName.USER)
				.findAndModify(new BasicDBObject("_id", uid), seqField, null, false, incSeq, true, true).get(COIN);
		if (obj == null) {
			return ReCode.REDUCE_COIN_FAIL;
		} else {
			int balance = 0;
			if (obj instanceof Double) {
				double s = (double) obj;
				balance = (int) s;
			} else if (obj instanceof Long) {
				long s = (long) obj;
				balance = (int) s;
			} else {
				balance = Integer.parseInt(obj.toString());
			}
			CoinLog pl = new CoinLog(super.getNextId(DocName.COIN_LOG), CoinLog.OUT, type, linkId, uid, amount, balance,
					detail, adminId);
			super.getMongoTemplate().save(pl);
			super.getRedisTemplate().delete(RK.keyUser(uid));
			return ReCode.OK;
		}
	}

	public static void main(String[] args) {
	}

	public ReCode reduce(long uid, int type, long linkId, int amount, long adminId, String detail) {
		if (amount < 1) {
			return ReCode.PARAMETER_ERR;
		}
		int change = -amount;
		DBObject dbo = userService.findById(uid);
		if (dbo == null) {
			return ReCode.USER_ID_ERR;
		}
		int curCoin = DboUtil.tryGetInt(dbo, "coin");
		if (amount > curCoin) {
			return ReCode.COIN_BALANCE_LOW;
		}
		DBObject incSeq = new BasicDBObject("$inc", new BasicDBObject(COIN, change)).append("$set",
				new BasicDBObject("updateTime", System.currentTimeMillis()));
		Object obj = getMongoTemplate().getCollection(DocName.USER)
				.findAndModify(new BasicDBObject("_id", uid).append("coin", new BasicDBObject("$gte", amount)),
						seqField, null, false, incSeq, true, true)
				.get(COIN);
		if (obj == null) {
			return ReCode.REDUCE_COIN_FAIL;
		} else {
			int balance = 0;
			if (obj instanceof Double) {
				double s = (double) obj;
				balance = (int) s;
			} else if (obj instanceof Long) {
				long s = (long) obj;
				balance = (int) s;
			} else {
				balance = Integer.parseInt(obj.toString());
			}
			CoinLog pl = new CoinLog(super.getNextId(DocName.COIN_LOG), CoinLog.OUT, type, linkId, uid, amount, balance,
					detail, adminId);
			super.getMongoTemplate().save(pl);
			super.getRedisTemplate().delete(RK.keyUser(uid));
			return ReCode.OK;
		}
	}

	public List<HashMap<String, Object>> stCoinLog(Integer io, Integer type, Long startDate, Long endDate,
			Integer size) {
		BasicDBObject q = new BasicDBObject();
		if (io != null && io != 0) {
			q.put("io", io);
		}
		if (type != null && type != 0) {
			q.put("type", type);
		}

		if (startDate != null && startDate != 0 && endDate != null && endDate != 0) {
			q.put("updateTime", new BasicDBObject("$gte", startDate).append("$lt", endDate));
		} else if (startDate != null && startDate != 0) {
			q.put("updateTime", new BasicDBObject("$gte", startDate));
		} else if (endDate != null && endDate != 0) {
			q.put("updateTime", new BasicDBObject("$lt", endDate));
		}
		List<BasicDBObject> pipeline = new ArrayList<BasicDBObject>();
		pipeline.add(new BasicDBObject("$match", q));
		pipeline.add(new BasicDBObject("$group",
				new BasicDBObject("_id", "$uid").append("sum", new BasicDBObject("$sum", "$amount"))));
		pipeline.add(new BasicDBObject("$sort", new BasicDBObject("sum", -1).append("_id", 1)));
		pipeline.add(new BasicDBObject("$limit", size));
		Iterable<DBObject> dbos = getC(DocName.COIN_LOG).aggregate(pipeline).results();
		List<HashMap<String, Object>> rs = new ArrayList<HashMap<String, Object>>();
		for (Iterator<DBObject> it = dbos.iterator(); it.hasNext();) {
			DBObject cur = it.next();
			HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("uid", DboUtil.getString(cur, "_id"));
			long uid = DboUtil.getLong(cur, "_id");
			DBObject user = userService.findById(uid);
			m.put("name", DboUtil.getString(user, "nickname") != null ? DboUtil.getString(user, "nickname") : "" + uid);
			m.put("img", DboUtil.getString(user, "avatar"));
			m.put("money", DboUtil.getString(cur, "sum"));
			m.put("r", DboUtil.getString(user, "role"));
			m.put("lv", userService.getLevel(DboUtil.getInt(user, "point")) + "");
			rs.add(m);
		}
		return rs;
	}

	public ReCode changeCoin(long uid, int type, long linkId, int amount, long adminId, String detail) {
		DBObject incSeq = new BasicDBObject("$inc", new BasicDBObject(COIN, amount)).append("$set",
				new BasicDBObject("updateTime", System.currentTimeMillis()));
		Object obj = getMongoTemplate().getCollection(DocName.USER)
				.findAndModify(new BasicDBObject("_id", uid), seqField, null, false, incSeq, true, true).get(COIN);
		if (obj == null) {
			return ReCode.USER_ID_ERR;
		} else {
			int balance = 0;
			if (obj instanceof Double) {
				double s = (double) obj;
				balance = (int) s;
			} else if (obj instanceof Long) {
				long s = (long) obj;
				balance = (int) s;
			} else {
				balance = Integer.parseInt(obj.toString());
			}
			CoinLog pl = new CoinLog(super.getNextId(DocName.COIN_LOG), amount >= 0 ? CoinLog.IN : CoinLog.OUT, type,
					linkId, uid, amount, balance, detail, adminId);
			super.getMongoTemplate().save(pl);
			super.getRedisTemplate().delete(RK.keyUser(uid));
			return ReCode.OK;
		}
	}

	public Page<DBObject> queryStCoinIO(Integer day, Integer type, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = findStCoinIO(day, type, page, size);
		int count = countStCoinIO(day, type);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public Long queryCoinBalance(Integer day) {
		DBObject dbo = super.getC(DocName.COIN_BALANCE).findOne(new BasicDBObject("_id", day));
		return null == dbo ? null : new BigDecimal(DboUtil.getDouble(dbo, "balance")).longValue();
	}

	public int countStCoinIO(Integer day, Integer type) {
		DBObject q = new BasicDBObject();
		if (day != null) {
			q.put("day", day);
		}
		if (type != null) {
			q.put("type", type);
		}
		return getC(DocName.ST_COIN_IO).find(q).count();
	}

	public List<DBObject> findStCoinIO(Integer day, Integer type, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (day != null) {
			q.put("day", day);
		}
		if (type != null) {
			q.put("type", type);
		}
		return getC(DocName.ST_COIN_IO).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("day", -1).append("type", 1)).toArray();
	}

	static final String TOKEN = "token";

	/** 获取昨日送花花费的金币数目 */
	public long getYearCoin(HttpServletRequest req) {
		int day = RankListService.getDay(-1);// 昨天
		if (day >= RankListJob.COIN_START && day <= RankListJob.COIN_END) {
			String token = CookieUtil.getCookieValue(TOKEN, req);
			if (null != token) {
				Long uid = super.getUid(token);
				if (uid > 0) {
					DBObject dbo = goodsService.findSendTotal(uid, day);
					if (null != dbo) {
						return DboUtil.getLong(dbo, "amount");
					}
				}
			}
		}
		return 0;
	}

	/** 是否已经领取过昨天的新年红包 */
	public boolean isRecvYearCoin(HttpServletRequest req) {
		String token = CookieUtil.getCookieValue(TOKEN, req);
		if (null != token) {
			Long uid = super.getUid(token);
			if (uid > 0) {
				ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();
				String key = uid + "-" + RankListService.getDay(-1);
				String objects = opsv.get(RK.recvYearMoney(key));
				if (objects == null) {// 没有领取过
					return false;
				}
			}
		}
		// 已经领过了
		return true;
	}

	/** 领取昨天的金币并设置状态 */
	public ReMsg recvYearCoin(HttpServletRequest req) {
		String token = CookieUtil.getCookieValue(TOKEN, req);
		if (null == token) {
			return new ReMsg(ReCode.FAIL, "您还没有登录,请先登录");
		}
		long uid = super.getUid(token);
		if (uid < 1) {
			return new ReMsg(ReCode.FAIL, "您还没有登录,请先登录");
		}
		ValueOperations<String, String> opsv = getRedisTemplate().opsForValue();
		int day = RankListService.getDay(-1);
		String key = uid + "-" + day;
		String objects = opsv.get(RK.recvYearMoney(key));
		if (objects == null) {// 未领取
			DBObject dbo = goodsService.findSendTotal(uid, RankListService.getDay(-1));
			if (null != dbo) {
				Long amount = DboUtil.getLong(dbo, "amount");
				int coin = (int) (amount / 10);
				addCoin(uid, CoinLog.ONETASK, day, coin, 0, "领取昨日消耗金币[" + amount + "]的10%");
				messageService.imMsgHandler(Const.SYSTEM_ID, uid, "恭喜您成功领取金币红包" + coin + "金币", null, null);
				opsv.set(RK.recvYearMoney(key), System.currentTimeMillis() + "", 1, TimeUnit.DAYS);
				return new ReMsg(ReCode.OK, coin);
			}
		}
		return new ReMsg(ReCode.FAIL);
	}
}
