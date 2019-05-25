package com.zb.service.pay;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.JSONUtil;
import com.zb.common.utils.MapUtil;
import com.zb.core.Page;
import com.zb.core.conf.Config;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.finance.AppleErrorOrder;
import com.zb.models.finance.Order;
import com.zb.service.EnvService;

@Service
public class ApplePayService extends PayService {
	// 购买游戏币
	private static final boolean TEST = false;

	@Autowired
	EnvService envService;

	public ApplePayService() {
		super();
	}

	// 购买道具下订单
	public ReMsg buyGoods(String receipt, String appCode, HttpServletRequest req) {
		long uid = super.getUid();
		return buyGoods(uid, receipt, appCode);
	}

	public ReMsg buyGoods(long uid, String receipt, String appCode) {
		log.error("receipt======uid:" + uid + "start," + receipt);
		try {
			Boolean isTest = envService.getBool("apple.pay.test");
			if (isTest == null) {
				isTest = Config.cur().getInt("istest", 0) != 0;
			}
			if (StringUtils.isNotBlank(appCode)) {
				appCode = "bibi";
			}
			String ret = AppleVerifyUtil.buyAppVerify(uid, receipt, isTest);
			int i = 0;
			while (ret.length() < 10 && i < 10) {
				ret = AppleVerifyUtil.buyAppVerify(uid, receipt, isTest);
				if (ret.length() < 10) {
					i++;
					Thread.sleep(2000);
				}
			}
			if (ret.length() < 10) {
				long id = super.getNextId(DocName.APPLE_ERROR_ORDER);
				AppleErrorOrder aro = new AppleErrorOrder(id, uid, receipt, appCode);
				super.getMongoTemplate().save(aro);
			}

			Map<String, Object> jsonMap = JSONUtil.jsonToMap(ret);
			int status = MapUtil.getInt(jsonMap, "status");
			if (status == 0) {
				Map m = (Map) jsonMap.get("receipt");
				String bundle_id = MapUtil.getStr(m, "bundle_id");
				if ("bibi".equals(appCode)) {
					if (bundle_id.contains("com.zhuangbi")) {
						List arr = (List) m.get("in_app");
						Map<String, String> m2 = (Map) arr.get(0);
						String quantity = m2.get("quantity");
						int count = Integer.parseInt(quantity);
						String product_id = m2.get("product_id");
						String transaction_id = m2.get("transaction_id");
						String orderNo = getAppleNo(transaction_id);
						Order o = orderService.findByNo(orderNo);
						System.out.println("orderNo:" + orderNo);
						if (o == null) {
							o = orderService.createAppleOrderAndPayed(uid, orderNo, product_id, count, null, appCode,
									transaction_id);
							if (o != null) {
								log.error("receipt======uid:" + uid + "OK," + receipt);
								return orderService.openOrder(o, o.getFinalAmount());
							}
						} else {
							return new ReMsg(ReCode.ORDER_OPENED);
						}
					}
				} else {
					if (bundle_id.contains("com.bibi")) {
						List arr = (List) m.get("in_app");
						Map<String, String> m2 = (Map) arr.get(0);
						String quantity = m2.get("quantity");
						int count = Integer.parseInt(quantity);
						String product_id = m2.get("product_id");
						String transaction_id = m2.get("transaction_id");
						String orderNo = getAppleNo(transaction_id);
						Order o = orderService.findByNo(orderNo);
						System.out.println("orderNo:" + orderNo);
						if (o == null) {
							o = orderService.createAppleOrderAndPayed(uid, orderNo, product_id, count, null, appCode,
									transaction_id);
							if (o != null) {
								log.error("receipt======uid:" + uid + "OK," + receipt);
								return orderService.openOrder(o, o.getFinalAmount());
							}
						} else {
							return new ReMsg(ReCode.ORDER_OPENED);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("apple pay err:", e);
		}
		return new ReMsg(ReCode.FAIL);
	}

	public DBObject findById(Long id) {
		DBObject q = new BasicDBObject("_id", id);
		return getC(DocName.APPLE_ERROR_ORDER).findOne(q);
	}

	public ReMsg openAppleOrderAdmin(long id) {
		DBObject dbo = findById(id);
		if (dbo != null) {
			if (DboUtil.getInt(dbo, "status") == AppleErrorOrder.ERROR) {
				long uid = DboUtil.getLong(dbo, "uid");
				String receipt = DboUtil.getString(dbo, "receipt");
				String appCode = DboUtil.getString(dbo, "appCode");
				ReMsg r = buyGoods(uid, receipt, appCode);
				if (r.getCode() == ReCode.OK.reCode()) {
					super.getC(DocName.APPLE_ERROR_ORDER).update(new BasicDBObject("_id", id),
							new BasicDBObject("$set", new BasicDBObject("status", AppleErrorOrder.OK)
									.append("updateTime", System.currentTimeMillis())));
				}
				return r;
			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	private String getAppleNo(String transaction_id) {
		return "i-" + transaction_id;
	}

	public List<DBObject> find(Long id, Long uid, Integer status, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (null != id && id != 0) {
			q.put("_id", id);
		}
		if (null != uid && uid != 0) {
			q.put("uid", uid);
		}
		if (null != status && status != 0) {
			q.put("status", status);
		}
		List<DBObject> dbos = getC(DocName.APPLE_ERROR_ORDER).find(q).skip(super.getStart(page, size))
				.limit(getSize(size)).sort(new BasicDBObject("_id", -1)).toArray();
		return dbos;
	}

	public int count(Long id, Long uid, Integer status) {
		DBObject q = new BasicDBObject();
		if (null != id && id != 0) {
			q.put("_id", id);
		}
		if (null != uid && uid != 0) {
			q.put("uid", uid);
		}
		if (null != status && status != 0) {
			q.put("status", status);
		}
		return getC(DocName.APPLE_ERROR_ORDER).find(q).count();
	}

	public Page<DBObject> query(Long id, Long uid, Integer status, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = this.find(id, uid, status, page, size);
		int count = count(id, uid, status);
		return new Page<DBObject>(count, size, page, dbos);
	}

}
