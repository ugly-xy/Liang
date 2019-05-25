package com.zb.service;

import java.io.IOException;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
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
import com.zb.common.utils.RandomUtil;
import com.zb.common.utils.T2TUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.TitleModel;
import com.zb.models.UserTitle;
import com.zb.models.finance.AgentOrder;
import com.zb.models.finance.CoinLog;
import com.zb.models.finance.PayProduct;
import com.zb.models.goods.BaseGoods;
import com.zb.models.third.Agent;
import com.zb.service.pay.AliPayService;
import com.zb.service.pay.CoinService;
import com.zb.service.pay.PayProductService;
import com.zb.service.pay.WeiXinPayService;

@Service
public class AgentOrderService extends BaseService {

	static final Logger log = LoggerFactory.getLogger(AgentOrderService.class);

	@Autowired
	UserService userService;

	@Autowired
	GoodsService goodsService;

	@Autowired
	WeiXinPayService weiXinPayService;

	@Autowired
	AliPayService aliPayService;

	@Autowired
	AgentService agentService;

	@Autowired
	CoinService coinService;

	@Autowired
	UserPackService userPackService;

	@Autowired
	MessageService messageService;

	@Autowired
	RechargeShoutService rechargeShoutService;

	public DBObject findById(Long id) {
		return super.findById(DocName.AGENTORDER, id);
	}

	public AgentOrder findByNo(String no) {
		DBObject dbo = super.getC(DocName.AGENTORDER).findOne(new BasicDBObject("no", no));
		if (dbo != null) {
			return DboUtil.toBean(dbo, AgentOrder.class);
		}
		return null;
	}

	public Page<DBObject> query(Long uid, String no, Long recUid, String payType, String thirdNo, Integer status,
			Long startTime, Long endTime, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> os = find(uid, no, recUid, payType, thirdNo, status, startTime, endTime, page, size);
		int count = count(uid, no, recUid, payType, thirdNo, status, startTime, endTime);
		return new Page<DBObject>(count, size, page, os);
	}

	public long allAmount(Long uid, String no, Long recUid, String payType, String thirdNo, Integer status,
			Long startTime, Long endTime) {
		DBObject q = new BasicDBObject();
		if (status != null && status != 0) {
			q.put("status", AgentOrder.OPENED);
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
		List<DBObject> list = getC(DocName.AGENTORDER).find(q).toArray();
		long allAmount = 0;
		for (DBObject dbo : list) {
			AgentOrder order = DboUtil.toBean(dbo, AgentOrder.class);
			int temp = order.getRmbAmount();
			allAmount += temp;
		}
		return allAmount;
	}

	public List<DBObject> find(Long uid, String no, Long recUid, String payType, String thirdNo, Integer status,
			Long startTime, Long endTime, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (status != null && status != 0) {
			q.put("status", status);
		}
		if (recUid != null && recUid != 0) {
			q.put("recUid", recUid);
		}
		if (uid != null && uid != 0) {
			q.put("buyUid", uid);
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
		return getC(DocName.AGENTORDER).find(q).sort(new BasicDBObject("_id", -1)).skip(getStart(page, size))
				.limit(getSize(size)).toArray();
	}

	public int count(Long uid, String no, Long recUid, String payType, String thirdNo, Integer status, Long startTime,
			Long endTime) {
		DBObject q = new BasicDBObject();
		if (status != null && status != 0) {
			q.put("status", status);
		}
		if (recUid != null && recUid != 0) {
			q.put("recUid", recUid);
		}
		if (uid != null && uid != 0) {
			q.put("buyUid", uid);
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
		return getC(DocName.AGENTORDER).find(q).count();
	}

	private static final String COIN_NAME = "金币";

	public ReMsg createOrder(int type, String productId, int count, Long recUid, String payType, Integer all,
			String content, Integer reply, HttpServletRequest req) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (recUid == null) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		Agent a = agentService.getAgentById(uid);
		if (a == null) {
			return new ReMsg(ReCode.FAIL);
		}
		// 存储喊话
		if (null != productId && all != null && all == 1) {
			rechargeShoutService.shout(recUid, content, all, reply);
		}
		AgentOrder o = null;
		if (type == 2) {
			int totalRmb = (int) (count / 5 * a.getCoinRate());
			Long id = super.getNextId(DocName.AGENTORDER);
			int len = id.toString().length();
			String nos = RandomUtil.randomNum(10 - len);
			String no = DateUtil.dateFormatyyyyMMdd(new Date()) + nos + id;
			o = new AgentOrder(id, type, no, productId, uid, recUid, payType, totalRmb, count, 1, "充值" + COIN_NAME,
					"充值" + COIN_NAME);

		} else {
			PayProduct pp = PayProductService.getPayProduct(payType, productId);
			if (pp == null) {
				return new ReMsg(ReCode.GOODS_NOT_SALE);
			}
			int totalRmb = (int) (pp.getRmb() * count * a.getRate());

			Long id = super.getNextId(DocName.AGENTORDER);

			int coin = 0;
			if (PayProduct.SEND_TYPE_PROCENTTAGE == pp.getSendType()) {
				coin = pp.getCoin() * (100 + pp.getSendAmount()) / 100;
			} else if (PayProduct.SEND_TYPE_AMOUNT == pp.getSendType()) {
				coin = pp.getCoin() + pp.getSendAmount();
			} else {
				coin = pp.getCoin();
			}

			int len = id.toString().length();
			String nos = RandomUtil.randomNum(10 - len);
			String no = DateUtil.dateFormatyyyyMMdd(new Date()) + nos + id;

			o = new AgentOrder(id, type, no, productId, uid, recUid, payType, totalRmb, coin, count, "购买礼包" + COIN_NAME,
					"购买礼包" + COIN_NAME);

		}

		if (o != null) {
			try {
				getMongoTemplate().save(o);
				if (AgentOrder.THIRD_WX_APP.equals(payType)) {
					String pid = o.getType() == 1 ? o.getProductId() + "礼包" : o.getAmount() + "*" + o.getCount();
					return weiXinPayService.createUnifiedOrderScan(o.getBody(), o.getNo(), o.getRmbAmount(),
							super.getIp(req), pid);
				} else if (AgentOrder.THIRD_ALI_APP.equals(payType)) {
					return new ReMsg(ReCode.OK, aliPayService.getOrderInfo(o));
				}
				return new ReMsg(ReCode.OK, o);
			} catch (Exception e) {

			}
		}
		return new ReMsg(ReCode.FAIL);
	}

	public AgentOrder payOrder(String no, String thirdNo) throws JsonParseException, JsonMappingException, IOException {
		AgentOrder o = findByNo(no);
		if (null != o && o.getStatus() == AgentOrder.CREATE) {
			o.setStatus(AgentOrder.PADED);
			o.setPayTime(System.currentTimeMillis());
			o.setUpdateTime(o.getPayTime());
			if (StringUtils.isNotBlank(thirdNo)) {
				o.setThirdNo(thirdNo);
			}
			getMongoTemplate().save(o);
		}
		return o;
	}

	public ReMsg openOrder(AgentOrder o, int amount) {
		return openOrder(o, amount, 0L);
	}

	public ReMsg openOrder(AgentOrder o, int amount, long adminId) {
		if (o.getStatus() == AgentOrder.CREATE) {
			return new ReMsg(ReCode.ORDER_NOT_PAYED);
		}
		if (o.getStatus() == AgentOrder.OPENED) {
			return new ReMsg(ReCode.ORDER_OPENED);
		}
		if (o.getRmbAmount() != amount) {
			return new ReMsg(ReCode.ORDER_AMOUNT_ERR);
		}
		o.setStatus(AgentOrder.OPENED);
		o.setOpenTime(System.currentTimeMillis());
		o.setUpdateTime(o.getOpenTime());
		if (adminId > 0) {
			o.setAdminId(adminId);
		}
		getMongoTemplate().save(o);
		if (o.getType() == 1) {
			PayProduct pp = PayProductService.getPayProduct(o.getPayType(), o.getProductId());
			if (pp == null) {
				return new ReMsg(ReCode.GOODS_NOT_SALE);
			}
			int coin = o.getAmount() * o.getCount();
			int expAmount = pp.getExpAmount();
			if (expAmount != 0) {
				userService.changePoint(o.getRecUid(), Point.VIP_PAY.getType(), expAmount * o.getCount(), 0);
			}
			int flAmount = pp.getFlAmount();
			if (flAmount != 0) {
				userPackService.addGoods(o.getRecUid(), BaseGoods.Gift.FLOWER.getV(), flAmount * o.getCount(),
						GiftForm.ORDER, o.get_id());
			}
			userService.changeVip(o.getRecUid(), pp.getRmb() * o.getCount(), 0L);// 增加vip值
			coinService.addCoin(o.getRecUid(), CoinLog.IN, o.get_id(), coin, 0, "金币充值");
			return realShout(o, pp);
		} else {
			coinService.addCoin(o.getRecUid(), CoinLog.IN, o.get_id(), o.getAmount(), 0, "金币充值");
			return new ReMsg(ReCode.OK, o);
		}

	}

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
		AgentOrder o = DboUtil.toBean(dbo, AgentOrder.class);
		if (o.getStatus() == AgentOrder.CREATE) {
			o.setStatus(AgentOrder.PADED);
			o.setPayTime(System.currentTimeMillis());
		}
		if (StringUtils.isNotBlank(thirdNo)) {
			o.setThirdNo(thirdNo);
		}
		return openOrder(o, o.getRmbAmount(), adminId);
	}

	public ReMsg realShout(AgentOrder o, PayProduct pp) {
		if (StringUtils.isNotBlank(o.getProductId())) {
			long uid = o.getRecUid();
			int realAmount = pp.getRmb() / 100;
			int count = o.getCount();
			if (realAmount < 168) {
				messageService.imMsgHandler(Const.SYSTEM_ID, uid,
						"恭喜您成功充值" + realAmount + "元游戏礼包 " + (count > 1 ? count + "个" : "") + "，已发送您的至账户，请注意查收！", null,
						null);
			} else {
				TitleModel model = TitleModel.getCoinModel(realAmount);
				if (null != model) {
					messageService.imMsgHandler(Const.SYSTEM_ID, uid,
							"恭喜您成功充值" + realAmount + "元游戏礼包 " + (count > 1 ? count + "个" : "") + "，额外赠送您"
									+ pp.getExpAmount() * count + "经验，" + pp.getFlAmount() * count + "朵鲜花，\""
									+ model.getName() + "\"称号，称号有效期为" + model.getVal() + "天，已发送您的至账户，请注意查收！",
							null, null);
				}
			}
			if (realAmount >= 168) {
				for (int i = 0; i < count; i++) {
					userService.saveTitle(uid, realAmount, UserTitle.COIN);
				}
				if (realAmount >= 388) {
					rechargeShoutService.realShout(uid, realAmount, count);
				}
			}
		}
		return new ReMsg(ReCode.OK, o);
	}
}
