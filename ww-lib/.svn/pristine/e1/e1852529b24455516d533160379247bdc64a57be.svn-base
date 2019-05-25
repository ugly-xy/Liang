package com.zb.service.pay;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.crypto.MDUtil;
import com.zb.common.http.HttpsClientUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.finance.AgentOrder;
import com.zb.models.finance.Order;
import com.zb.service.AgentOrderService;
import com.zb.service.OrderService;

@Service
public class WeiXinPayService extends PayService {

	static final Logger log = LoggerFactory.getLogger(WeiXinPayService.class);
	private static final String UNIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	private static final String NOTIFYURL = "http://api.zhuangdianbi.com/pay/weixin/callback";

	private static final String AGENT_NOTIFYURL = "http://agent.zhuangdianbi.com/pay/weixin/callback/";

	private static final String APPID = "wxcf1fc45ab87508c1";// "wxcf1fc45ab87508c1";
	private static final String MCH_ID = "1309589601";
	private static final String APPKEY = "de1dHO12d1d1axl82sd21d13d1dsd2d3";

	private static final String APPID_DRAWSOMETHING = "wx4cc313b50960d83a";// "wxcf1fc45ab87508c1";
	private static final String MCH_ID_DRAWSOMETHING = "1483628012";
	private static final String APPKEY_DRAWSOMETHING = "3HXjCOAm9135FwA3Oo94OgQUFCE2X7lh";

	private static final String APPID_UNDERCOVER = "wx5dbd01d0b4f31ddb";// "wxcf1fc45ab87508c1";
	private static final String MCH_ID_UNDERCOVER = "1483627322";
	private static final String APPKEY_UNDERCOVER = "15EfWPKw5XPfpErMx3SLp5XH2Ayum66l";

	private static final String MP_APPID = "wxb01925720a0e07f9";
	private static final String MP_MCH_ID = "1303420001";
	private static final String MP_APPKEY = "NlHxZphSdBYJbwMIZ4ULevJaLei5DWl9";

	private static final String SUCCESS = "SUCCESS";

	public static final String TRADE_TYPE_DEF = "APP";

	public static final String TRADE_TYPE_DEF_NATIVE = "NATIVE";// 扫码

	public static void main(String[] args) throws IOException, URISyntaxException, JDOMException {

		// HttpClientUtil.postXml(
		// "http://test2.api.zhuangdianbi.com:8080/pay/weixin/callback",
		// "<xml><a>ok</a><b>c</b></xml>");

		WeiXinPayService wps = new WeiXinPayService();
		ReMsg rm = wps.createUnifiedOrder("金币10000", "xxxxxxx", 100, "127.0.0.1", "APP", "bibi");
		System.out.println(rm);

		String xml = "<xml><appid><![CDATA[wxcf1fc45ab87508c1]]></appid>" + "<bank_type><![CDATA[CFT]]></bank_type>"
				+ "<cash_fee><![CDATA[100]]></cash_fee>" + "<device_info><![CDATA[WEB]]></device_info>"
				+ "<fee_type><![CDATA[CNY]]></fee_type>" + "<is_subscribe><![CDATA[N]]></is_subscribe>"
				+ "<mch_id><![CDATA[1309589601]]></mch_id>" + "<nonce_str><![CDATA[WWY7TG]]></nonce_str>"
				+ "<openid><![CDATA[oJPcMuJ0HYsbDKLNAFB5eel28c3o]]></openid>"
				+ "<out_trade_no><![CDATA[201608298860409274]]></out_trade_no>"
				+ "<result_code><![CDATA[SUCCESS]]></result_code>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
				+ "<sign><![CDATA[077543FEE9ECBE120EE51264BDD34871]]></sign>"
				+ "<time_end><![CDATA[20160829134328]]></time_end>" + "<total_fee>100</total_fee>"
				+ "<trade_type><![CDATA[APP]]></trade_type>"
				+ "<transaction_id><![CDATA[4008442001201608292529048794]]></transaction_id>" + "</xml>";
		wps.callback(xml);
	}

	public ReMsg createUnifiedOrder(String body, String out_trade_no, int total_fee, String spbill_create_ip,
			String trade_type, String appCode) throws IOException, URISyntaxException, JDOMException {
		if (StringUtils.isBlank(trade_type)) {
			trade_type = TRADE_TYPE_DEF;
		}
		Req uo = null;
		String appKey = null;
		String appId = null;
		String mchId = null;
		if (appCode.equals(Const.APP_CODE_BIBI)) {
			appKey = APPKEY;
			mchId = MCH_ID;
			appId = APPID;
		} else if (appCode.equals(Const.APP_CODE_DRAWSOMETHING)) {
			appKey = APPKEY_DRAWSOMETHING;
			mchId = MCH_ID_DRAWSOMETHING;
			appId = APPID_DRAWSOMETHING;
		} else if (appCode.equals(Const.APP_CODE_UNDERCOVER)) {
			appKey = APPKEY_UNDERCOVER;
			mchId = MCH_ID_UNDERCOVER;
			appId = APPID_UNDERCOVER;
		} else {
			return new ReMsg(ReCode.FAIL);
		}
		uo = new Req(appKey);
		String params = uo.append("appid", appId).append("mch_id", mchId).append("device_info", "WEB")
				.append("nonce_str", RandomUtil.random(6)).append("body", body).append("out_trade_no", out_trade_no)
				.append("total_fee", "" + total_fee).append("spbill_create_ip", spbill_create_ip)
				.append("notify_url", NOTIFYURL).append("trade_type", trade_type).append("attach", appCode).getXml();
		// System.out.println("xml:::::::::::::::" + params);
		Map<String, String> heads = new HashMap<String, String>();
		heads.put("Content-Type", "text/xml");
		String result = HttpsClientUtil.postData(UNIFIEDORDER, params, heads);
		System.out.println(result);
		Document doc = getDoc(result);
		Element root = doc.getRootElement();
		String return_code = root.getChildText("return_code");
		System.out.println("return_code:" + return_code);
		if (SUCCESS.equals(return_code)) {
			String result_code = root.getChildText("result_code");
			if (SUCCESS.equals(result_code)) {
				Map<String, String> data = new HashMap<String, String>();
				data.put("appid", appId);
				data.put("partnerid", mchId);
				data.put("package", "Sign=WXPay");
				data.put("noncestr", RandomUtil.random(6));
				data.put("timestamp", System.currentTimeMillis() / 1000 + "");
				data.put("prepayid", root.getChildText("prepay_id"));

				List<String> keys = new ArrayList<String>(data.keySet());
				Collections.sort(keys);
				StringBuilder sb = new StringBuilder();
				for (String key : keys) {
					String v = data.get(key);
					sb.append(key).append("=").append(v).append("&");
				}
				sb.append("key=").append(appKey);
				String sign = MDUtil.MD5.digest2HEX(sb.toString()).toUpperCase();
				data.put("sign", sign);
				return new ReMsg(ReCode.OK, data);
			}
			return new ReMsg(ReCode.FAIL);
		} else {
			return new ReMsg(ReCode.FAIL);
		}

	}

	public ReMsg createUnifiedOrderScan(String body, String out_trade_no, int total_fee, String spbill_create_ip,
			String product_id) throws IOException, URISyntaxException, JDOMException {
		String trade_type = TRADE_TYPE_DEF_NATIVE;
		Req uo = null;
		String appKey = null;
		String appId = null;
		String mchId = null;
		appKey = MP_APPKEY;
		mchId = MP_MCH_ID;
		appId = MP_APPID;
		uo = new Req(appKey);
		String params = uo.append("appid", appId).append("mch_id", mchId).append("device_info", "WEB")
				.append("nonce_str", RandomUtil.random(6)).append("body", body).append("out_trade_no", out_trade_no)
				.append("total_fee", "" + total_fee).append("spbill_create_ip", spbill_create_ip)
				.append("notify_url", AGENT_NOTIFYURL).append("trade_type", trade_type).append("product_id", product_id)
				.getXml();
		// System.out.println("xml:::::::::::::::" + params);
		Map<String, String> heads = new HashMap<String, String>();
		heads.put("Content-Type", "text/xml");
		String result = HttpsClientUtil.postData(UNIFIEDORDER, params, heads);
		System.out.println(result);
		Document doc = getDoc(result);
		Element root = doc.getRootElement();
		String return_code = root.getChildText("return_code");
		System.out.println("return_code:" + return_code);
		if (SUCCESS.equals(return_code)) {
			String result_code = root.getChildText("result_code");
			if (SUCCESS.equals(result_code)) {
				Map<String, String> data = new HashMap<String, String>();
				data.put("appid", appId);
				data.put("partnerid", mchId);
				data.put("package", "Sign=WXPay");
				data.put("noncestr", RandomUtil.random(6));
				data.put("timestamp", System.currentTimeMillis() / 1000 + "");
				data.put("prepayid", root.getChildText("prepay_id"));
				data.put("code_url", root.getChildText("code_url"));
				List<String> keys = new ArrayList<String>(data.keySet());
				Collections.sort(keys);
				StringBuilder sb = new StringBuilder();
				for (String key : keys) {
					String v = data.get(key);
					sb.append(key).append("=").append(v).append("&");
				}
				sb.append("key=").append(appKey);
				String sign = MDUtil.MD5.digest2HEX(sb.toString()).toUpperCase();
				data.put("sign", sign);
				return new ReMsg(ReCode.OK, data);
			}
			return new ReMsg(ReCode.FAIL);
		} else {
			return new ReMsg(ReCode.FAIL);
		}

	}
	
	@Autowired
	AgentOrderService agentOrderService;
	
	public String agentCallback(String xml) {
		Resp op;
		try {
			op = new Resp(xml);
			// System.out.println("out_trade_no:::::::" +
			// op.get("out_trade_no"));
			String appKey = MP_APPKEY;
			if (op.resultOk(appKey)) {
				AgentOrder o = agentOrderService.payOrder(op.get("out_trade_no"), op.get("transaction_id"));
				ReMsg r = agentOrderService.openOrder(o, Integer.parseInt(op.get("total_fee")));
				if (r.getCode() == ReCode.OK.reCode()) {
					return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
				}
			} else {
				return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[签名失败]]></return_msg></xml>";
			}
		} catch (JDOMException | IOException e) {
			log.error("WeiXinPay Err:", e);
		}
		return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[失败]]></return_msg></xml>";
	}


	public String callback(String xml) {
		Resp op;
		try {
			op = new Resp(xml);
			// System.out.println("out_trade_no:::::::" +
			// op.get("out_trade_no"));
			String appCode = op.get("attach");
			String appKey = null;
			if (appCode.equals(Const.APP_CODE_BIBI)) {
				appKey = APPKEY;
			} else if (appCode.equals(Const.APP_CODE_DRAWSOMETHING)) {
				appKey = APPKEY_DRAWSOMETHING;
			} else if (appCode.equals(Const.APP_CODE_UNDERCOVER)) {
				appKey = APPKEY_UNDERCOVER;
			}
			if (appKey != null && op.resultOk(appKey)) {
				Order o = orderService.payOrder(op.get("out_trade_no"), op.get("transaction_id"));
				ReMsg r = orderService.openOrder(o, Integer.parseInt(op.get("total_fee")));
				if (r.getCode() == ReCode.OK.reCode()) {
					return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
				}
			} else {
				return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[签名失败]]></return_msg></xml>";
			}
		} catch (JDOMException | IOException e) {
			log.error("WeiXinPay Err:", e);
		}
		return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[失败]]></return_msg></xml>";
	}

	private static Document getDoc(String xml) throws UnsupportedEncodingException, JDOMException, IOException {
		return getDoc(xml, "utf-8");
	}

	private static Document getDoc(String xml, String encode)
			throws UnsupportedEncodingException, JDOMException, IOException {
		SAXBuilder saxBuilder = new SAXBuilder();
		return saxBuilder.build(new InputSource(new ByteArrayInputStream(xml.getBytes(encode))));
	}

	class Req {

		public Req(String appkey) {
			this.appkey = appkey;
		}

		Map<String, String> order = new HashMap<String, String>();
		String appkey = null;

		public String get(String key) {
			return order.get(key);
		}

		public Req append(String key, String value) {
			if ("".equals(key)) {
				return this;
			}
			order.put(key, value);
			return this;
		}

		public String getXml() {
			StringBuilder sb = new StringBuilder("<xml>");
			StringBuilder signSb = new StringBuilder();
			String[] arr = new String[order.size()];
			order.keySet().toArray(arr);
			Arrays.sort(arr);
			for (String s : arr) {
				String value = order.get(s);
				if (StringUtils.isNotBlank(value)) {
					sb.append("<").append(s).append(">").append(value).append("</").append(s).append(">");
					signSb.append(s).append("=").append(value).append("&");
				}
			}
			String s = signSb.append("key=").append(appkey).toString();
			// System.out.println("sign bbbbbbbbbbbbbbbbb " + s);
			String sign = MDUtil.MD5.digest2HEX(s).toUpperCase();
			sb.append("<sign>").append(sign).append("</sign></xml>");
			return sb.toString();
		}

	}

	class Resp {

		public Resp(String xml) throws UnsupportedEncodingException, JDOMException, IOException {
			// this.appkey = appkey;
			init(xml);
		}

		Map<String, String> order = new HashMap<String, String>();
		// String appkey = null;

		public String get(String key) {
			return order.get(key);
		}

		private Resp append(String key, String value) {
			if ("".equals(key)) {
				return this;
			}
			order.put(key, value);
			return this;
		}

		public boolean resultOk(String appKey) {
			// System.out.println(order.get("return_code") + " " +
			// order.get("result_code") + " " + validSign(appKey));
			return SUCCESS.equals(order.get("return_code")) && SUCCESS.equals(order.get("result_code"))
					&& validSign(appKey);
		}

		private boolean validSign(String appKey) {
			StringBuilder signSb = new StringBuilder();
			String[] arr = new String[order.size()];
			order.keySet().toArray(arr);
			Arrays.sort(arr);
			for (String s : arr) {
				String value = order.get(s);
				if (StringUtils.isNotBlank(value) && !"sign".equals(s)) {
					signSb.append(s).append("=").append(value).append("&");
				}
			}
			String s = signSb.append("key=").append(appKey).toString();
			String sign = MDUtil.MD5.digest2HEX(s).toUpperCase();
			return sign.equals(order.get("sign"));
		}

		private Resp init(String xml) throws UnsupportedEncodingException, JDOMException, IOException {
			Document doc = getDoc(xml);
			Element root = doc.getRootElement();
			List ls = root.getChildren();
			for (Iterator iter = ls.iterator(); iter.hasNext();) {
				Element el = (Element) iter.next();
				this.append(el.getName(), el.getText());
				if (el.getName().equals("coupon_count")) {
					int count = Integer.parseInt(el.getText());
					for (int i = 0; i < count; i++) {
						this.append("coupon_id_" + i, root.getChildText("coupon_id_" + i));
						this.append("coupon_fee_" + i, root.getChildText("coupon_fee_" + i));
					}
				}
			}
			return this;
		}
	}

}
