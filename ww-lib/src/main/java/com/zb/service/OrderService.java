package com.zb.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.GiftForm;
import com.zb.common.Constant.Point;
import com.zb.common.Constant.ReCode;
import com.zb.common.Constant.Role;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.DateUtil;
import com.zb.common.utils.MapUtil;
import com.zb.common.utils.T2TUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.finance.CoinLog;
import com.zb.models.finance.Discount;
import com.zb.models.finance.GiftBag;
import com.zb.models.finance.Order;
import com.zb.models.finance.PayProduct;
import com.zb.models.goods.BaseGoods;
import com.zb.models.goods.GiftLog;
import com.zb.models.user.UserMonitor;
import com.zb.service.pay.AliPayService;
import com.zb.service.pay.CoinService;
import com.zb.service.pay.GiftBagService;
import com.zb.service.pay.MidasPayService;
import com.zb.service.pay.PayProductService;
import com.zb.service.pay.WeiXinPayService;
import com.zb.service.task.OrderAspect;
import com.zb.service.usertask.UserMonitorService;

@Service
public class OrderService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	UserService userService;

	@Autowired
	GoodsService goodsService;

	@Autowired
	WeiXinPayService weiXinPayService;

	@Autowired
	AliPayService aliPayService;

	@Autowired
	MidasPayService midasPayService;

	@Autowired
	DiscountService discountService;

	@Autowired
	UserPackService userPackService;

	@Autowired
	CoinService coinService;

	@Autowired
	MessageService messageService;

	@Autowired
	UserMonitorService userMonitorService;

	public DBObject findById(Long id) {
		return super.findById(DocName.ORDER, id);
	}

	public Order findByNo(String no) {
		DBObject dbo = super.getC(DocName.ORDER).findOne(new BasicDBObject("no", no));
		if (dbo != null) {
			return DboUtil.toBean(dbo, Order.class);
		}
		return null;
	}

	public Page<DBObject> query(String no, Long recUid, String payType, String thirdNo, Integer status, Long startTime,
			Long endTime, String appCode, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> os = find(no, recUid, payType, thirdNo, status, startTime, endTime, appCode, page, size);
		int count = count(no, recUid, payType, thirdNo, status, startTime, endTime, appCode);
		return new Page<DBObject>(count, size, page, os);
	}

	public long allAmount(String no, Long recUid, String payType, String thirdNo, Integer status, Long startTime,
			Long endTime, String appCode) {
		DBObject q = new BasicDBObject();
		if (status != null && status != 0) {
			q.put("status", Order.OPENED);
		}
		if (recUid != null && recUid != 0) {
			q.put("recUid", recUid);
		}
		if (StringUtils.isNotBlank(no)) {
			q.put("no", no);
		}
		if (StringUtils.isNotBlank(payType)) {
			q.put("payType", payType);
		}
		if (StringUtils.isNotBlank(thirdNo)) {
			q.put("thirdNo", thirdNo);
		}
		BasicDBObject t = new BasicDBObject();
		if (startTime != null && startTime != 0) {
			t.append("$gte", startTime);
		}
		if (endTime != null && endTime != 0) {
			t.append("$lt", endTime);
		}
		if (startTime != null && startTime != 0 || endTime != null && endTime != 0)
			q.put("createTime", t);
		if (StringUtils.isNotBlank(appCode)) {
			q.put("appCode", appCode);
		}
		List<DBObject> list = getC(DocName.ORDER).find(q).toArray();
		long allAmount = 0;
		for (DBObject dbo : list) {
			Order order = DboUtil.toBean(dbo, Order.class);
			int temp = order.getFinalAmount() * order.getCount();
			allAmount += temp;
		}
		return allAmount;
	}

	public List<DBObject> find(String no, Long recUid, String payType, String thirdNo, Integer status, Long startTime,
			Long endTime, String appCode, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (status != null && status != 0) {
			q.put("status", status);
		}
		if (recUid != null && recUid != 0) {
			q.put("recUid", recUid);
		}
		if (StringUtils.isNotBlank(no)) {
			q.put("no", no);
		}
		if (StringUtils.isNotBlank(payType)) {
			q.put("payType", payType);
		}
		if (StringUtils.isNotBlank(thirdNo)) {
			q.put("thirdNo", thirdNo);
		}
		BasicDBObject t = new BasicDBObject();
		if (startTime != null && startTime != 0) {
			t.append("$gte", startTime);
		}
		if (endTime != null && endTime != 0) {
			t.append("$lt", endTime);
		}
		if (startTime != null && startTime != 0 || endTime != null && endTime != 0)
			q.put("createTime", t);
		if (StringUtils.isNotBlank(appCode)) {
			q.put("appCode", appCode);
		}
		return getC(DocName.ORDER).find(q).sort(new BasicDBObject("_id", -1)).skip(getStart(page, size))
				.limit(getSize(size)).toArray();
	}

	public int count(String no, Long recUid, String payType, String thirdNo, Integer status, Long startTime,
			Long endTime, String appCode) {
		DBObject q = new BasicDBObject();
		if (status != null && status != 0) {
			q.put("status", status);
		}
		if (recUid != null && recUid != 0) {
			q.put("recUid", recUid);
		}
		if (StringUtils.isNotBlank(no)) {
			q.put("no", no);
		}
		if (StringUtils.isNotBlank(payType)) {
			q.put("payType", payType);
		}
		if (StringUtils.isNotBlank(thirdNo)) {
			q.put("thirdNo", thirdNo);
		}
		BasicDBObject t = new BasicDBObject();
		if (startTime != null && startTime != 0) {
			t.append("$gte", startTime);
		}
		if (endTime != null && endTime != 0) {
			t.append("$lt", endTime);
		}
		if (startTime != null && startTime != 0 || endTime != null && endTime != 0)
			q.put("createTime", t);
		if (StringUtils.isNotBlank(appCode)) {
			q.put("appCode", appCode);
		}
		return getC(DocName.ORDER).find(q).count();
	}

	private static final String COIN_NAME = "金币";

	/** 是否能买新手礼包 */
	public boolean canBuyNovice(DBObject user, long uid) {
		if (null == user) {
			user = userService.findByIdSafe(uid);
		}
		// 创建时间24h之外 不能购买
//		if (DboUtil.getLong(user, "createTime") < System.currentTimeMillis() - Const.DAY) {
//			return false;
//		}
		DBObject q = new BasicDBObject("productId", new BasicDBObject("$in", GiftBagService.getNoviceIds()))
				.append("recUid", uid).append("status", Order.OPENED);
		return getC(DocName.ORDER).find(q).count() == 0;
	}

	/** 是否弹出购买新手礼包界面 */
	public ReMsg noviceGiftBag() {
		long uid = super.getUid();
		if (uid > 0) {
			DBObject user = userService.findByIdSafe(uid);
			if (user != null) {
				boolean buy = canBuyNovice(user, uid);
				if (buy) {
					return new ReMsg(ReCode.OK, DboUtil.getLong(user, "createTime"));
				}
			}
			return new ReMsg(ReCode.FAIL);
		}
		return new ReMsg(ReCode.NOT_AUTHORIZED);
	}

	public ReMsg createOrder(String productId, Long recUid, String payType, String discountNo, String appCode,
			HttpServletRequest req) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (recUid == null) {
			recUid = uid;
		}
		int totalRmb = 0;
		PayProduct pp = PayProductService.getPayProduct(payType, productId);
		if (pp != null) {
			totalRmb = pp.getRmb();
		} else {
			GiftBag gb = GiftBagService.getPayProduct(payType, productId);
			if (gb != null) {// 如果是礼包类型的话 要检测一下是否能够购买
				int type = gb.getType();
				if (type == GiftBag.TYPE_NOVICE) {
					if (!canBuyNovice(null, uid)) {// 新手礼包 条件不符合
						return new ReMsg(ReCode.BUY_GIFTBAG_ERR);
					}
				} else if (type == GiftBag.TYPE_DAY) {// 每日购买一次
					DBObject q = new BasicDBObject("productId", productId).append("recUid", uid).append("createTime",
							new BasicDBObject("$gte", DateUtil.getTodayZeroTime()));
					if (getC(DocName.ORDER).find(q).count() != 0) {
						return new ReMsg(ReCode.BUY_GIFTBAG_ERR);
					}
				} else if (type == GiftBag.TYPE_WEEK) {// 每周购买一次
					DBObject q = new BasicDBObject("productId", productId).append("recUid", uid).append("createTime",
							new BasicDBObject("$gte", DateUtil.getWeekZeroTime()));
					if (getC(DocName.ORDER).find(q).count() != 0) {
						return new ReMsg(ReCode.BUY_GIFTBAG_ERR);
					}
				} else if (type == GiftBag.TYPE_MONTH) {// 每月购买一次
					DBObject q = new BasicDBObject("productId", productId).append("recUid", uid).append("createTime",
							new BasicDBObject("$gte", DateUtil.getMonthFirstDay().getTime()));
					if (getC(DocName.ORDER).find(q).count() != 0) {
						return new ReMsg(ReCode.BUY_GIFTBAG_ERR);
					}
				}
				totalRmb = gb.getRmb();
			}
		}
		if (totalRmb == 0) {
			return new ReMsg(ReCode.GOODS_NOT_SALE);
		}
		Order o = null;
		try {
			// int amount = 0;
			// if (PayProduct.SEND_TYPE_PROCENTTAGE == pp.getSendType()) {
			// amount = pp.getCoin() * pp.getSendAmount() / 100;
			// } else if (PayProduct.SEND_TYPE_AMOUNT == pp.getSendType()) {
			// amount = (pp.getCoin() + pp.getSendAmount());
			// } else {
			// amount = pp.getCoin();
			// }

			int disType = 0;
			int disAmount = 0;

			if (StringUtils.isNotBlank(discountNo)) {
				Discount disc = DboUtil.toBean(discountService.findById(discountNo), Discount.class);
				if (null != disc) {
					long curTime = System.currentTimeMillis();

					if (disc.getStatus() != Discount.DEF || totalRmb < disc.getLimitAmount()
							|| disc.getStartTime() > curTime || disc.getEndTime() < curTime) {
						return new ReMsg(ReCode.ORDER_DISCOUNT_NOT_USED);
					}
					disType = disc.getType();
					if (disType == Discount.TYPE_COUPON) {
						disAmount = disc.getAmount() * totalRmb / 100;
					} else if (disType == Discount.TYPE_REDUCTION) {
						disAmount = disc.getAmount();
					}
					ReCode r = discountService.userDiscount(discountNo);
					if (r.reCode() != ReCode.OK.reCode()) {
						return new ReMsg(ReCode.ORDER_DISCOUNT_USE_ERR);
					}
				}
			}

			long id = super.getNextId(DocName.ORDER);
			if (StringUtils.isBlank(appCode)) {
				appCode = Const.APP_CODE_BIBI;
			}
			o = new Order(id, productId, uid, recUid, payType, totalRmb, discountNo, disType, disAmount,
					totalRmb - disAmount, 1, "充值" + COIN_NAME, "充值" + COIN_NAME, appCode);

			if (o != null) {
				getMongoTemplate().save(o);
				if (Order.THIRD_WX_APP.equals(payType)) {
					return weiXinPayService.createUnifiedOrder(o.getBody(), o.getNo(), o.getAmount(), super.getIp(req),
							WeiXinPayService.TRADE_TYPE_DEF, appCode);
				} else if (Order.THIRD_ALI_APP.equals(payType)) {
					return new ReMsg(ReCode.OK, aliPayService.getOrderInfo(o));
				} else if (Order.THIRD_MIDAS_PAY_APP.equals(payType)) {
					// return midasPayService.buyGoods(req, openid,
					// trade_type, loc, appip, ts, payitem, goodsmeta,
					// goodsurl, sig, pf, pfkey, zoneid)
				}
				return new ReMsg(ReCode.OK, o);
			}
		} catch (Exception e) {

		}
		return new ReMsg(ReCode.FAIL);
	}

	public Order createAppleOrderAndPayed(long uid, String orderno, String productId, int count, Long recUid,
			String appCode, String thirdNo) {
		if (uid < 1) {
			return null;
		}
		if (recUid == null || recUid == 0) {
			recUid = uid;
		}
		Order o = null;
		PayProduct pp = PayProductService.getPayProduct(Order.THIRD_APPLE_PAY_APP, productId);
		if (pp == null) {
			return null;
		}
		try {
			long id = super.getNextId(DocName.ORDER);
			int disType = 0;
			int disAmount = 0;
			int rmb = pp.getRmb() * count;
			if (StringUtils.isBlank(appCode)) {
				appCode = Const.APP_CODE_BIBI;
			}
			o = new Order(id, orderno, productId, uid, recUid, Order.THIRD_APPLE_PAY_APP, rmb, null, disType, disAmount,
					rmb, count, "充值" + COIN_NAME, "充值" + COIN_NAME, appCode);
			if (o != null) {
				o.setStatus(Order.PADED);
				o.setPayTime(System.currentTimeMillis());
				o.setUpdateTime(o.getPayTime());
				o.setThirdNo(thirdNo);
				getMongoTemplate().save(o);
				return o;
			}
		} catch (Exception e) {

		}
		return null;
	}

	public Order payOrder(String no, String thirdNo) throws JsonParseException, JsonMappingException, IOException {
		Order o = findByNo(no);
		if (null != o && o.getStatus() == Order.CREATE) {
			o.setStatus(Order.PADED);
			o.setPayTime(System.currentTimeMillis());
			o.setUpdateTime(o.getPayTime());
			if (StringUtils.isNotBlank(thirdNo)) {
				o.setThirdNo(thirdNo);
			}
			getMongoTemplate().save(o);
		}
		return o;
	}

	// @TaskAspect(PAYMONEY)
	@OrderAspect
	public ReMsg openOrder(Order o, int amount) {
		return openOrder(o, amount, 0L);
	}

	// @TaskAspect(PAYMONEY)
	public ReMsg openOrder(Order o, int amount, long adminId) {
		if (o.getStatus() == Order.CREATE) {
			return new ReMsg(ReCode.ORDER_NOT_PAYED);
		}
		if (o.getStatus() == Order.OPENED) {
			return new ReMsg(ReCode.ORDER_OPENED);
		}
		if (o.getFinalAmount() != amount) {
			return new ReMsg(ReCode.ORDER_AMOUNT_ERR);
		}
		PayProduct pp = PayProductService.getPayProduct(o.getPayType(), o.getProductId());
		if (pp == null) {
			// 如果商品不存在 尝试开通礼包
			return openGiftBagOrder(o, amount, adminId);
		}
		if (Order.THIRD_APPLE_PAY_APP.equals(o.getPayType()) && (adminId == 0)) {
			// TODO 未测试
			UserMonitor mu = userMonitorService.upSet(o.getBuyUid(), o.getPayTime(), amount);
			// if(mu.getWeight()>=UserMonitor.W_BUG){
			// return new ReMsg(ReCode.ORDER_BUG);
			// }
		}
		int coin = 0;
		if (PayProduct.SEND_TYPE_PROCENTTAGE == pp.getSendType()) {
			coin = pp.getCoin() * (100 + pp.getSendAmount()) * o.getCount() / 100;
		} else if (PayProduct.SEND_TYPE_AMOUNT == pp.getSendType()) {
			coin = (pp.getCoin() + pp.getSendAmount()) * o.getCount();
		} else {
			coin = pp.getCoin() * o.getCount();
		}
		if (PayProduct.LIMIT_11 == pp.getLimit()) {
			List<DBObject> dbos = this.find(null, o.getRecUid(), null, null, Order.OPENED, DateUtil.getTodayZeroTime(),
					0L, null, 1, 200);
			for (DBObject ito : dbos) {
				if (o.getProductId().equals(DboUtil.getString(ito, "productId"))) {
					coin = pp.getCoin() * o.getCount();
					break;
				}
			}
		}
		int expAmount = pp.getExpAmount();
		if (expAmount != 0) {
			userService.changePoint(o.getRecUid(), Point.VIP_PAY.getType(), expAmount, 0);
		}
		int flAmount = pp.getFlAmount();
		if (flAmount != 0) {
			userPackService.addGoods(o.getRecUid(), BaseGoods.Gift.FLOWER.getV(), flAmount, GiftForm.ORDER, o.get_id());
		}

		o.setStatus(Order.OPENED);
		o.setOpenTime(System.currentTimeMillis());
		o.setUpdateTime(o.getOpenTime());
		if (adminId > 0) {
			o.setAdminId(adminId);
		}
		getMongoTemplate().save(o);
		userService.changeVip(o.getRecUid(), amount, 0L);// 增加vip值
		coinService.addCoin(o.getRecUid(), CoinLog.IN, o.get_id(), coin, 0, "金币充值");
		return new ReMsg(ReCode.OK, o);
	}

	/** 开通礼包订单 */
	public ReMsg openGiftBagOrder(Order o, int amount, long adminId) {
		log.error("尝试开通礼包订单:" + o.get_id());
		GiftBag gb = GiftBagService.getPayProduct(o.getPayType(), o.getProductId());
		if (gb == null) {
			return new ReMsg(ReCode.GOODS_NOT_SALE);
		}
		if (Order.THIRD_APPLE_PAY_APP.equals(o.getPayType()) && (adminId == 0)) {
			UserMonitor mu = userMonitorService.upSet(o.getBuyUid(), o.getPayTime(), amount);
		}
		int expAmount = gb.getExpAmount();
		if (expAmount != 0) {// 经验
			userService.changePoint(o.getRecUid(), Point.VIP_PAY.getType(), expAmount, 0);
		}
		int flAmount = gb.getFlAmount();
		if (flAmount != 0) {// 收花数
			ReMsg rm = goodsService.sendGift(20001, flAmount, GiftLog.USER_ZONE, o.getRecUid(), o.getRecUid(),
					Const.SYSTEM_ID);
			while (rm.getCode() == ReCode.COIN_BALANCE_LOW.reCode()) {// 金币不足
				coinService.addCoin(Const.SYSTEM_ID, CoinLog.IN_ROBIT_FLOWER, 0, 1000000, super.getUid(), "送花加币");
				rm = goodsService.sendGift(20001, flAmount, GiftLog.USER_ZONE, o.getRecUid(), o.getRecUid(),
						Const.SYSTEM_ID);
			}
		}
		// 金币
		coinService.addCoin(o.getRecUid(), CoinLog.IN, o.get_id(), gb.getCoin(), 0, "礼包充值");
		// vip
		userService.changeVip(o.getRecUid(), gb.getVipAmount(), 0L);// 增加vip值
		o.setStatus(Order.OPENED);
		o.setOpenTime(System.currentTimeMillis());
		o.setUpdateTime(o.getOpenTime());
		if (adminId > 0) {
			o.setAdminId(adminId);
		}
		getMongoTemplate().save(o);
		return new ReMsg(ReCode.OK, o);

	}

	// @TaskAspect(PAYMONEY)
	@OrderAspect
	public ReMsg openOrderByAdmin(Long id, String thirdNo) {
		long adminId = super.getUid();
		if (adminId < 0) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (Role.ADMIN.getRole() != T2TUtil.obj2Int(super.getUser("role"), 1)) {
			return new ReMsg(ReCode.ROOL_NO);
		}
		if (id == null) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		DBObject dbo = this.findById(id);
		if (dbo == null) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		Order o = DboUtil.toBean(dbo, Order.class);
		if (o.getStatus() == Order.CREATE) {
			o.setStatus(Order.PADED);
			o.setPayTime(System.currentTimeMillis());
		}
		if (StringUtils.isNotBlank(thirdNo)) {
			o.setThirdNo(thirdNo);
		}
		return openOrder(o, o.getFinalAmount(), adminId);
	}

	/** 补偿金币 */
	// public ReMsg coinRepay() {
	// long id = 0;
	// long uid = 0;
	// Map<Integer, Integer> coinMap = new HashMap<Integer, Integer>();
	// DBObject q = new BasicDBObject("status", Const.STATUS_OK);
	// PayProduct pp = null;
	// String body = null;
	// for (;;) {
	// q.put("_id", new BasicDBObject("$gt", id));
	// List<DBObject> dbos = getC(DocName.ORDER).find(q).sort(new
	// BasicDBObject("_id", 1)).limit(100).toArray();
	// if (dbos.size() < 1) {
	// break;
	// }
	// for (DBObject dbo : dbos) {
	// id = DboUtil.getLong(dbo, "_id");
	// uid = DboUtil.getLong(dbo, "recUid");
	// Integer coin = coinMap.get(DboUtil.getInt(dbo, "amount"));
	// if (null == coin) {
	// pp = PayProductService.getOldPayProduct(DboUtil.getString(dbo,
	// "payType"),
	// DboUtil.getString(dbo, "productId"));
	// if (pp == null) {
	// continue;
	// }
	// if (PayProduct.SEND_TYPE_PROCENTTAGE == pp.getSendType()) {
	// coin = pp.getCoin() * (100 + pp.getSendAmount()) / 100;
	// } else if (PayProduct.SEND_TYPE_AMOUNT == pp.getSendType()) {
	// coin = (pp.getCoin() + pp.getSendAmount());
	// } else {
	// coin = pp.getCoin();
	// }
	// coin = coin * 9 / 10;
	// coinMap.put(DboUtil.getInt(dbo, "amount"), coin);
	// }
	// coinService.addCoin(uid, CoinLog.IN, id, coin, 0, "金币充值补发");
	// body = "【金币差额补发通知】BiBi娱乐社区充值系统已进行升级，现针对以往已充值玩家进行差额补发。" + "您在"
	// + DateUtil.dateFormatShortCN(DateUtil.millisToDate(DboUtil.getLong(dbo,
	// "openTime"))) + "充值"
	// + DboUtil.getInt(dbo, "amount") / 100 + "元，将补发差额：" + coin +
	// "金币，已发放至您的账户中，请注意查收！祝您玩的开心哦！";
	// messageService.imMsgHandler(Const.SYSTEM_ID, uid, body, null, null);
	// }
	// }
	// return new ReMsg(ReCode.OK);
	// }
}
