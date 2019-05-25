package com.zb.service.third;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.common.crypto.BlowFishUtil;
import com.zb.common.crypto.MDUtil;
import com.zb.common.http.HttpClientUtil;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.JSONUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.common.utils.T2TUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.DocName;
import com.zb.models.finance.CoinLog;
import com.zb.models.third.*;
import com.zb.service.BaseService;
import com.zb.service.pay.CoinService;

@Service
public class OpenOrderService extends BaseService {
	static final Logger log = LoggerFactory.getLogger(OpenOrderService.class);

	@Autowired
	CoinService coinService;

	@Autowired
	ThirdService thirdService;

	public DBObject findById(Long id) {
		return getC(DocName.OPEN_ORDER).findOne(new BasicDBObject("_id", id));
	}

	public List<DBObject> find(String appId, String openId, Long uid,
			Long provider, Integer status, String outOrderNo,
			Integer callStatus, Integer page, Integer size) {
		DBObject q = new BasicDBObject();
		if (null != status && status != 0) {
			q.put("status", status);
		}
		if (StringUtils.isNotBlank(appId)) {
			q.put("appId", appId);
		}
		if (StringUtils.isNotBlank(openId)) {
			q.put("openId", openId);
		}
		if (StringUtils.isNotBlank(outOrderNo)) {
			q.put("outOrderNo", outOrderNo);
		}
		if (null != provider && provider != 0) {
			q.put("provider", provider);
		}
		if (null != uid && uid != 0) {
			q.put("uid", uid);
		}
		if (null != callStatus && callStatus != 0) {
			q.put("callStatus", callStatus);
		}
		return getC(DocName.OPEN_ORDER).find(q)
				.skip(super.getStart(page, size)).limit(getSize(size))
				.sort(new BasicDBObject("_id", -1)).toArray();
	}

	public int count(String appId, String openId, Long uid, Long provider,
			Integer status, String outOrderNo, Integer callStatus) {
		DBObject q = new BasicDBObject();
		if (null != status && status != 0) {
			q.put("status", status);
		}
		if (StringUtils.isNotBlank(appId)) {
			q.put("appId", appId);
		}
		if (StringUtils.isNotBlank(openId)) {
			q.put("openId", openId);
		}
		if (StringUtils.isNotBlank(outOrderNo)) {
			q.put("outOrderNo", outOrderNo);
		}
		if (null != provider && provider != 0) {
			q.put("provider", provider);
		}
		if (null != uid && uid != 0) {
			q.put("uid", uid);
		}
		if (null != callStatus && callStatus != 0) {
			q.put("callStatus", callStatus);
		}
		return getC(DocName.OPEN_ORDER).find(q).count();
	}

	public Page<DBObject> query(String appId, String openId, Long uid,
			Long provider, Integer status, String outOrderNo,
			Integer callStatus, Integer page, Integer size) {
		size = getSize(size);
		page = getPage(page);
		List<DBObject> dbos = this.find(appId, openId, uid, provider, status,
				outOrderNo, callStatus, page, size);
		int count = count(appId, openId, uid, provider, status, outOrderNo,
				callStatus);
		return new Page<DBObject>(count, size, page, dbos);
	}

	public ReMsg buyProduct(String appId, String openId, int coin,
			String outOrderNo, String product, String detail) {
		if (coin < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		DBObject dbo = thirdService.findAppById(appId);
		if (dbo == null) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		DBObject openUser = thirdService.findOpenUserById(openId);
		if (null == openUser) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		long curUid = DboUtil.getLong(openUser, "uid");
		long uid = super.getUid();
		if (curUid != uid) {
			return new ReMsg(ReCode.FAIL);
		}
		if (super.getUser("coin") == null
				|| T2TUtil.obj2Int(super.getUser("coin")) < coin) {
			return new ReMsg(ReCode.COIN_BALANCE_LOW);
		}
		long id = super.getNextId(DocName.OPEN_ORDER);
		OpenOrder oo = new OpenOrder(id, appId, openId, uid, DboUtil.getLong(
				openUser, "provider"), coin, outOrderNo, product, detail);
		super.getMongoTemplate().save(oo);
		ReCode rc = coinService.reduce(curUid, CoinLog.OUT_OPEN_ORDER, id,
				coin, 0, "91夺宝：" + product);
		String showUrl = DboUtil.getString(dbo, "showUrl");

		showUrl = showUrl + "('" + outOrderNo + "');";
		// int urlStyle = DboUtil.getInt(dbo, "urlStyle");
		// if (urlStyle == 1) {
		// if (showUrl.contains("?")) {
		// if (showUrl.endsWith("?")) {
		// showUrl = showUrl + "outOrderNo=" + outOrderNo;
		// } else {
		// showUrl = showUrl + "&outOrderNo=" + outOrderNo;
		// }
		// } else {
		// showUrl = showUrl + "?outOrderNo=" + outOrderNo;
		// }
		// } else {
		// if (showUrl.endsWith("/")) {
		// showUrl = showUrl + outOrderNo;
		// } else {
		// showUrl = showUrl + "/" + outOrderNo;
		// }
		// }
		if (rc.reCode() == ReCode.OK.reCode()) {
			super.getC(DocName.OPEN_ORDER).update(
					new BasicDBObject("_id", id),
					new BasicDBObject("$set", new BasicDBObject("status",
							OpenOrder.STATUS_PAYED)));
			try {
				this.callback(dbo, outOrderNo, coin, oo.getCreateTime(), id);
			} catch (InvalidKeyException | NoSuchAlgorithmException
					| NoSuchPaddingException
					| InvalidAlgorithmParameterException
					| IllegalBlockSizeException | BadPaddingException
					| IOException e) {
				log.error("callback err :", e);
			}
			return new ReMsg(ReCode.OK, showUrl);
		}
		return new ReMsg(rc, showUrl);
	}

	public void callback(DBObject dbo, String outOrderNo, int coin, long time,
			long openOrderId) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException, IOException {
		String appId = DboUtil.getString(dbo, "_id");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appKey", appId);
		map.put("outOrderNo", outOrderNo);
		map.put("coin", coin);
		map.put("timestamp", time);
		map.put("status", 0);
		String json = JSONUtil.beanToJson(map);
		String key = DboUtil.getString(dbo, "blowfishKey");
		String vector = DboUtil.getString(dbo, "blowfishVecter");
		// System.out
		// .println("key:" + key + ",vector:" + vector + ",json:" + json);
		String data = BlowFishUtil.encryptHex(key, json, vector);
		String appSecret = DboUtil.getString(dbo, "appSecret");
		String noise = RandomUtil.random(5);
		String sign = MDUtil.SHA.digest2HEX(
				data + noise + appSecret + coin + 0, true);
		String callurl = DboUtil.getString(dbo, "callback");
		Map<String, String> params = new HashMap<String, String>();
		params.put("data", data);
		params.put("noise", noise);
		params.put("sign", sign);
		String ret = HttpClientUtil.post(callurl, params, null);
		int s = OpenOrder.CALL_FAIL;
		if (StringUtils.isNotBlank(ret)) {
			Map<String, Object> jm = JSONUtil.jsonToMap(ret);
			if (jm.get("status") != null) {
				String status = jm.get("status").toString();
				if ("ok".equals(status)) {
					s = OpenOrder.CALL_OK;
				}
			}
		}
		super.getC(DocName.OPEN_ORDER).update(
				new BasicDBObject("_id", openOrderId),
				new BasicDBObject("$inc", new BasicDBObject("callCount", 1)
						.append("$set", new BasicDBObject("callStatus", s))));

	}

	public ReMsg oneBuy(String openId, String appKey, String product,
			String luckNo, String phase) {
		OneBuy ob = new OneBuy(openId, appKey, product, luckNo, phase);
		super.getMongoTemplate().save(ob);
		return new ReMsg(ReCode.OK);
	}
}
