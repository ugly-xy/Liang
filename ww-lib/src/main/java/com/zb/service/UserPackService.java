package com.zb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.GiftForm;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.UserPack;
import com.zb.models.UserPackLog;
import com.zb.models.goods.BaseGoods;

@Service
public class UserPackService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(UserPackService.class);

	@Autowired
	GoodsService goodsService;

	public DBObject findById(Long id) {
		return super.findById(DocName.USER_PACK, id);
	}
    //后台查询用户背包
	public Page<DBObject> query(Integer type, Long baseId, Long uid, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		DBObject q = new BasicDBObject();
		if (type != null && type > -1) {
			q.put("type", type);
		}
		if (baseId != null && baseId != 0) {
			q.put("baseId", baseId);
		}
		if (uid != null && uid != 0) {
			q.put("uid", uid);
		}
		List<DBObject> dbos = getC(DocName.USER_PACK).find(q).sort(new BasicDBObject("expires", 1).append("_id", 1))
				.skip(getStart(page, size)).limit(getSize(size)).toArray();
		Integer count = getC(DocName.USER_PACK).find(q).count();
		return new Page<DBObject>(count, size, page, dbos);
	}

	// 查询非限时用户道具的数量
	public int queryPropCount(Integer type, Integer baseId, Long uid) {
		DBObject q = new BasicDBObject();
		if (type != null && type != 0) {
			q.put("type", type);
		}
		if (baseId != null) {
			q.put("baseId", baseId);
		}

		if (uid != null && uid != 0) {
			q.put("uid", uid);
		}
		try {
			Integer count = DboUtil.getInt(getC(DocName.USER_PACK).findOne(q), "amount");
			if (null == count) {
				return 0;
			} else {
				return count;
			}
		} catch (Exception e) {
			return 0;
		}
	}

	public List<DBObject> find(Integer type, Long baseId, Long uid, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (type != null && type != 0) {
			q.put("type", type);
		}
		if (baseId != null && baseId != 0) {
			q.put("baseId", baseId);
		}

		if (uid != null && uid != 0) {
			q.put("uid", uid);
		}

		return getC(DocName.USER_PACK).find(q).sort(new BasicDBObject("expires", 1).append("_id", 1))
				.skip(getStart(page, size)).limit(getSize(size)).toArray();
	}

	public ReCode addGoods(long uid, BaseGoods bg, long amount,GiftForm gf,long opId) {
		if(amount<1){
			return ReCode.PARAMETER_ERR;
		}
		UserPack up = null;
		if (bg.getAmountType() == BaseGoods.AmountType.AMOUNT.getT()) {
			up = getUserPack(uid, bg.getId(), bg.getType());
			up.setAmount(up.getAmount() + amount);
			up.setUpdateTime(System.currentTimeMillis());
		} else if (bg.getAmountType() == BaseGoods.AmountType.TIME.getT()) {
			up = getUserPack(uid, bg.getId(), bg.getType());
			long t = System.currentTimeMillis();
			up.setAmount(1);
			if (up.getExpires() < t) {
				up.setExpires(t + amount * BaseGoods.Unit.valueOf(bg.getUnit()).getTimes());
			} else {
				up.setExpires(up.getExpires() + amount * BaseGoods.Unit.valueOf(bg.getUnit()).getTimes());
			}
			up.setUpdateTime(t);
		} else if (bg.getAmountType() == BaseGoods.AmountType.ONLY_TIME.getT()) {
			up = getUserPack(uid, bg.getId(), bg.getType());
			long t = System.currentTimeMillis();
			up.setAmount(1);
			up.setExpires(t + amount * BaseGoods.Unit.valueOf(bg.getUnit()).getTimes());
			up.setUpdateTime(t);
		} else {
			up = getUserPack(uid, bg.getId(), bg.getType());
			up.setAmount(1);
			up.setExpires(0);
			up.setUpdateTime(System.currentTimeMillis());
		}
		super.getMongoTemplate().save(up);
		super.getMongoTemplate().save(new UserPackLog(super.getNextId(DocName.USER_PACKLOG), uid,
				BaseGoods.Gift.FLOWER.getV().getId(), amount, gf.getType(), opId));
		return ReCode.OK;
	}

	public ReCode addGoodsByBaseId(long uid, String goodsType, int baseGoodsId, long amount) {
		if(amount<1){
			return ReCode.PARAMETER_ERR;
		}
		BaseGoods bg = goodsService.getBaseGoodsById(goodsType, baseGoodsId);
		return addGoods(uid, bg, amount,GiftForm.ADMIN,0);
	}

	public ReMsg useGoodsByBaseId(long uid, String goodsType, int baseGoodsId, long amount) {
		BaseGoods bg = goodsService.getBaseGoodsById(goodsType, baseGoodsId);
		return useGoods(uid, bg, amount);
	}

	public ReMsg useGoods(long uid, BaseGoods bg, long amount) {
		UserPack up = null;
		if(amount<1){
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		if (bg.getAmountType() == BaseGoods.AmountType.AMOUNT.getT()) {
			up = getUserPack(uid, bg.getId(), bg.getType());
			long c = 0;
			if (up.getAmount() > 0) {
				if (amount >= up.getAmount()) {
					c = up.getAmount();
					getC(DocName.USER_PACK).remove(new BasicDBObject("_id", up.get_id()));
				} else {
					up.setAmount(up.getAmount() - amount);
					c = amount;
					up.setUpdateTime(System.currentTimeMillis());
					super.getMongoTemplate().save(up);
				}
			}
			return new ReMsg(ReCode.OK, c);
		} else if (bg.getAmountType() == BaseGoods.AmountType.ONLY_TIME.getT()) {
			List<DBObject> dbos = getUserPacks(uid, bg.getId(), bg.getType(), amount);
			int count = 0;
			long t = System.currentTimeMillis();
			for (DBObject dbo : dbos) {
				if (DboUtil.getLong(dbo, "expires") >= t) {
					count++;
				}
				getC(DocName.USER_PACK).remove(new BasicDBObject("_id", DboUtil.getLong(dbo, "_id")));
			}
			return new ReMsg(ReCode.OK, count);
		} else if (bg.getAmountType() == BaseGoods.AmountType.ONLY.getT()) {
			List<DBObject> dbos = getUserPacks(uid, bg.getId(), bg.getType(), amount);
			int count = 0;
			for (DBObject dbo : dbos) {
				count++;
				getC(DocName.USER_PACK).remove(new BasicDBObject("_id", DboUtil.getLong(dbo, "_id")));
			}
			return new ReMsg(ReCode.OK, count);
		} else {
			return new ReMsg(ReCode.FAIL);
		}
	}

	private UserPack getUserPack(long uid, long baseGoodsId, int type) {
		List<DBObject> dbos = this.find(0, baseGoodsId, uid, 0, 1);
		if (dbos.size() > 0) {
			return DboUtil.toBean(dbos.get(0), UserPack.class);
		}
		return new UserPack(super.getNextId(DocName.USER_PACK), uid, type, baseGoodsId);
	}

	private List<DBObject> getUserPacks(long uid, long baseGoodsId, int type, long amount) {
		return this.find(type, baseGoodsId, uid, 0, (int) amount);
	}
	
//	public void savePacklog(UserPackLog log) {
//		getMongoTemplate().save(log);
//	}

	public DBObject findPackLogById(Long id) {
		return getC(DocName.USER_PACKLOG).findOne(new BasicDBObject("_id", id));
	}

	public List<DBObject> findUserPackLog(Long uid, Integer bgId, Integer op, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (null != uid && uid != 0) {
			q.put("uid", uid);
		}

		if (null != bgId && bgId != 0) {
			q.put("bgId", bgId);
		}
		if (null != op && op != 0) {
			q.put("op", op);
		}

		return getC(DocName.USER_PACKLOG).find(q).skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
	}

	public int countUserPackLog(Long uid, Integer bgId, Integer op) {
		DBObject q = new BasicDBObject();
		if (null != uid && uid != 0) {
			q.put("uid", uid);
		}

		if (null != bgId && bgId != 0) {
			q.put("bgId", bgId);
		}
		if (null != op && op != 0) {
			q.put("op", op);
		}
		return getC(DocName.USER_PACKLOG).find(q).count();
	}

	public Page<DBObject> queryUserPackLog(Long userId, Integer bgId, Integer op, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = this.findUserPackLog(userId, bgId, op, page, size);
		int count = this.countUserPackLog(userId, bgId, op);
		return new Page<DBObject>(count, size, page, dbos);
	}

	// public ReCode addGoods(Long uid, Long goodsId, Integer count) {
	// // Goods g = DboUtil.toBean(findById(goodsId), Goods.class);
	// // if (g != null) {
	// // BaseGoods bg = BaseGoods.of(g.getBaseId());
	// // UserPack up = null;
	// // if (BaseGoods.GoodsType.TIME.equals(bg.getGoodsType())) {
	// // List<DBObject> ups = find(g.getBaseId(), uid, 0, 0, 1, null);
	// // long curTime = System.currentTimeMillis();
	// // long expires = curTime + g.getAmount() * count;
	// // if (ups.size() != 0) {
	// // up = DboUtil.toBean(ups.get(0), UserPack.class);
	// // if (up.getExpires() > System.currentTimeMillis()
	// // && up.getStatus() == Const.STATUS_OK) {
	// // expires = up.getExpires() + g.getAmount() * count;
	// // }
	// // } else {
	// // long id = super.getNextId(DocName.USER_PACK);
	// // up = new UserPack(id, uid, g.getBaseId(), 1L);
	// // }
	// // up.setExpires(expires);
	// // } else if (BaseGoods.GoodsType.AMOUNT.equals(bg.getGoodsType())) {
	// // List<DBObject> ups = find(g.getBaseId(), uid, 0, 0, 1, null);
	// // if (ups.size() != 0) {
	// // up = DboUtil.toBean(ups.get(0), UserPack.class);
	// // if (up.getStatus() == Const.STATUS_OK) {
	// // up.setAmount(up.getAmount() + g.getAmount() * count);
	// // } else {
	// // up.setAmount(g.getAmount() * count);
	// // }
	// // } else {
	// // long id = super.getNextId(DocName.USER_PACK);
	// // up = new UserPack(id, uid, g.getBaseId(), g.getAmount()
	// // * count);
	// // }
	// // } else if (BaseGoods.GoodsType.ONLY.equals(bg.getGoodsType())) {
	// // long id = super.getNextId(DocName.USER_PACK);
	// // up = new UserPack(id, uid, g.getBaseId(), g.getAmount() * count);
	// // } else {
	// // return ReCode.FAIL;
	// // }
	// // up.setStatus(Const.STATUS_OK);
	// // up.setUpdateTime(System.currentTimeMillis());
	// // super.getMongoTemplate().save(up);
	// // return ReCode.OK;
	// // }
	// return ReCode.FAIL;
	// }
	//
	// public ReCode addPack(Long uid, Long packId, Integer count) {
	// // GoodsPack gp = DboUtil.toBean(get, c)
	// return ReCode.FAIL;
	// }

	// public ReCode update(long id, String name, String img, Integer listPrice,
	// Integer price, long amount, long startTime, long endTime, Integer status,
	// Integer sort) {
	//
	// DBObject a = findById(id);
	// if (null == a) {
	// return ReCode.FAIL;
	// }
	//
	// DBObject u = new BasicDBObject("updateTime", System.currentTimeMillis());
	// if (StringUtils.isNotBlank(name)) {
	// u.put("name", name);
	// }
	// if (StringUtils.isNotBlank(img)) {
	// u.put("img", img);
	// }
	//
	// if (listPrice != 0) {
	// u.put("listPrice", listPrice);
	// }
	// if (status != 0) {
	// u.put("status", status);
	// }
	// if (price != 0) {
	// u.put("price", price);
	// }
	// if (amount != 0) {
	// u.put("amount", amount);
	// }
	// if (sort != 0) {
	// u.put("sort", sort);
	// }
	// if (startTime != 0) {
	// u.put("startTime", startTime);
	// }
	// if (endTime != 0) {
	// u.put("endTime", endTime);
	// }
	//
	// if (getC(DocName.GOODS).update(new BasicDBObject("_id", id),
	// new BasicDBObject("$set", u)).getN() > 0) {
	// return ReCode.OK;
	// }
	// return ReCode.FAIL;
	//
	// }
	//
	// public long save(String name, String img, Integer baseId, Integer
	// listPrice,
	// Integer price, long amount, long startTime, long endTime, Integer status,
	// Integer sort) {
	// long id = super.getNextId(DocName.GOODS);
	// Goods g = new Goods(id, name, img, baseId, listPrice, price, amount,
	// startTime, endTime, status, sort);
	// getMongoTemplate().save(g);
	// return id;
	// }

}
