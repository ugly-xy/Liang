package com.zb.service.pay;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.qq.open.SnsSigCheck;
import com.qq.open.pay.QQConfig;
import com.zb.common.Constant.ReCode;
import com.zb.common.crypto.MDUtil;
import com.zb.common.http.HttpClientUtil;
import com.zb.common.http.HttpsClientUtil;
import com.zb.common.utils.JSONUtil;
import com.zb.common.utils.MapUtil;
import com.zb.common.utils.RandomUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.finance.Order;

@Service
public class MidasPayService extends PayService {
	// 购买游戏币
	private static final String MPAYBUY = "http://openapi.tencentyun.com/mpay/buy_goods_m";
	private static final String MPAYBUY_TEST = "http://119.147.19.43/mpay/buy_goods_m";
	// 查询游戏币
	private static final String GETBANLANCE = "http://openapi.tencentyun.com/mapy/get_balance_m";
	// 扣除游戏币
	private static final String PAY_M = "http://openapi.tencentyun.com/mpay/pay_m";
	// 取消支付接口
	private static final String CANCEL_PAY_M = "http://openapi.tencentyun.com/mpay/cancel_pay_m";

	// 待定
	private static final String NOTIFYURL = "http://api.zhuangdianbi.com/pay/weixin/callback";

	private static final String APPID = "1105127046";
	private static final String MCH_ID = "1105127046";

	private static final String APPKEY = "5wz6tEmnPlImPXHR";

	public static final String Midas_STATUS_TRADE_CLOSED = "TRADE_CLOSED";// 在指定时间段内未支付时关闭的交易;在交易完成全额退款成功时关闭的交易。
	public static final String Midas_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";// 交易创建，等待买家付款。
	public static final String Midas_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";// 交易成功，且可对该交易做操作，如：多级分润、退款等。
	public static final String Midas_STATUS_TRADE_FINISHED = "TRADE_FINISHED";// 交易成功且结束，即不可再做任何操作。private

	static String uri_path = "/pay/midas/callbacak";

	private static final String SUCCESS = "SUCCESS";
	public static final String TRADE_TYPE_DEF = "APP";

	public MidasPayService() {
		super();
	}

	public static void main(String[] args) throws IOException,
			URISyntaxException, JDOMException {
		HttpClientUtil.postXml(
				"http://test2.api.zhuangdianbi.com:8080/pay/weixin/callback",
				"<xml><a>ok</a><b>c</b></xml>");
		MidasPayService wps = new MidasPayService();
	}

	private String getParamters(Map<String, String> map) {
		// Map.Entry<String, BasicDBObject> entry : taskmap?.entrySet()
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			sb.append(entry.getKey() + "=" + entry.getValue() + "&");
		}
		String str = sb.toString().replace(" ", "");
		return str.substring(0, str.length() - 1);
	}

	// 购买道具下订单
	public ReMsg buyGoods(HttpServletRequest req, String openid,
			String openkey, String pay_token, int goodId, int count, String pf,
			String pfkey) throws Exception {
		// pf平台-注册渠道-系统运行平台-安装渠道-业务自定义。
		String appid = APPID;
		long ts = System.currentTimeMillis() / 1000;
		String zoneid = "1";
		String payitem = goodId + "*" + QQConfig.QPOINT_CONVERT + "*" + count;

		String goodsmeta = "充值金币*充值金币" + count;
		String goodsurl;

		// cookie信息
		String session_id = "session_id";
		String session_type = "session_type";

		// 组装参数数据格式
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("openid", openid);
		map.put("openkey", openkey);
		map.put("pay_token", pay_token);
		map.put("appid", APPID);
		map.put("ts", "" + ts);
		map.put("payitem", payitem);
		map.put("goodsmeta", goodsmeta);
		// map.put("goodsurl",goodsurl);
		map.put("pf", pf);
		map.put("zoneid", zoneid);
		map.put("pfkey", pfkey);
		// map.put("app_metadata",orderId);
		// map.put("amt",""+(cny?.toInteger() * QQConfig.CONVERT));
		// map.put("appmode",appmode)
		String sig = SnsSigCheck.makeSig("get", uri_path, map,
				QQConfig.QQ_SECRET);
		map.put("sig", sig);
		String params = getParamters(map);
		// 请求参数加密
		// String param_map =
		// BlowfishUtil.encryptHex(KEY,JSONUtil.beanToJson(map),INIT_VECTOR);
		// 组装cookie数据
		Map<String, String> cookie = new HashMap<String, String>();
		cookie.put("session_id", session_id);
		cookie.put("session_type", session_type);
		// String encookie =
		// BlowfishUtil.encryptHex(KEY,JSONUtil.beanToJson(cookie),INIT_VECTOR);
		try {
			// 请求腾讯云服务器，通过云服务器获取米大师服务器下订单token
			String json = HttpClientUtil
					.get("${TEN_URL}midashiandroidorder/get_ten_token?param_map=${param_map}&cookies=${encookie}",
							null, HttpClientUtil.UTF8);
			Map res = JSONUtil.jsonToMap(json);
			if (res != null
					&& (1 == MapUtil.getInt(res, "code") || "1".equals(MapUtil
							.getStr(res, "code")))) {
				return new ReMsg(ReCode.OK);// [code:1,url_params:res?.get("url_params"),token:res?.get("token")]
			} else {
				// logger.info("腾讯云服务器返回结果："+json)
				return new ReMsg(ReCode.FAIL);// [code:0,msg:res?.get("msg")]
			}
		} catch (Exception e) {
			// logger.debug("请求云服务器异常",e)
			return new ReMsg(ReCode.FAIL);// [code:0,msg:"请求云服务器异常"]
		}

		// if (StringUtils.isBlank(trade_type)) {
		// trade_type = TRADE_TYPE_DEF;
		// }
		// Req uo = new Req(APPKEY);
		// String params = uo.append("appid", APPID).append("mch_id", MCH_ID)
		// .append("nonce_str", RandomUtil.random(6))
		// .append("trade_type", trade_type).append("loc", loc)
		// .append("ts", ts).append("payitem", payitem)
		// .append("goodsmeta", goodsmeta).append("goodsurl", goodsurl)
		// .append("sig", sig).append("pf", pf).append("pfkey", pfkey)
		// .append("zoneid", zoneid).append("notify_url", NOTIFYURL)
		// .getXml();
		// String result = HttpsClientUtil.postXml(MPAYBUY, params);
		// System.out.println(result);
		// Document doc = getDoc(result);
		// Element root = doc.getRootElement();
		// String return_code = root.getChildText("return_code");
		// System.out.println("return_code:" + return_code);
		// if (SUCCESS.equals(return_code)) {
		// String result_code = root.getChildText("result_code");
		// if (SUCCESS.equals(result_code)) {
		// // String trade_typeR = root.getChildText("trade_type")
		// // ;
		// String prepay_id = root.getChildText("prepay_id");
		// System.out.println(prepay_id);
		// return new ReMsg(ReCode.OK, prepay_id);
		// }
		// return new ReMsg(ReCode.FAIL);
		// } else {
		// return new ReMsg(ReCode.FAIL);
		// }
	}

	// 查询用户游戏币余额
	public ReMsg getBanlance(HttpServletRequest req, String openid,
			String trade_type, String loc, String appip, String ts, String sig,
			String pf, String pfkey, String zoneid) throws Exception {
		if (StringUtils.isBlank(trade_type)) {
			trade_type = TRADE_TYPE_DEF;
		}
		Req uo = new Req(APPKEY);
		String params = uo.append("appid", APPID).append("mch_id", MCH_ID)
				.append("nonce_str", RandomUtil.random(6)).append("loc", loc)
				.append("ts", ts).append("sig", sig).append("pf", pf)
				.append("pfkey", pfkey).append("zoneid", zoneid)
				.append("trade_type", trade_type)
				.append("notify_url", NOTIFYURL).getXml();
		Map<String, String> heads = new HashMap<String, String>();
		heads.put("Content-Type", "text/xml");
		String result = HttpsClientUtil.postData(GETBANLANCE, params, heads);
		System.out.println(result);
		Document doc = getDoc(result);
		Element root = doc.getRootElement();
		String return_code = root.getChildText("return_code");
		System.out.println("return_code:" + return_code);
		if (SUCCESS.equals(return_code)) {
			String result_code = root.getChildText("result_code");

			if (SUCCESS.equals(result_code)) {
				// String trade_typeR = root.getChildText("trade_type")
				// ;
				String prepay_id = root.getChildText("prepay_id");
				System.out.println(prepay_id);
				return new ReMsg(ReCode.OK, prepay_id);
			}
			return new ReMsg(ReCode.FAIL);
		} else {
			return new ReMsg(ReCode.FAIL);
		}
	}

	// 扣除用户游戏币余额
	public ReMsg pay(HttpServletRequest req, String openid, String trade_type,
			String appip, String ts, String sig, String pf, String pfkey,
			String zoneid, String amt) throws Exception {
		if (StringUtils.isBlank(trade_type)) {
			trade_type = TRADE_TYPE_DEF;
		}
		Req uo = new Req(APPKEY);
		String params = uo.append("appid", APPID).append("mch_id", MCH_ID)
				.append("nonce_str", RandomUtil.random(6)).append("ts", ts)
				.append("sig", sig).append("pf", pf).append("pfkey", pfkey)
				.append("zoneid", zoneid).append("trade_type", trade_type)
				.append("amt", amt).append("notify_url", NOTIFYURL).getXml();
		Map<String, String> heads = new HashMap<String, String>();
		heads.put("Content-Type", "text/xml");
		String result = HttpsClientUtil.postData(PAY_M, params, heads);
		System.out.println(result);
		Document doc = getDoc(result);
		Element root = doc.getRootElement();
		String return_code = root.getChildText("return_code");
		System.out.println("return_code:" + return_code);
		if (SUCCESS.equals(return_code)) {
			String result_code = root.getChildText("result_code");

			if (SUCCESS.equals(result_code)) {
				// String trade_typeR = root.getChildText("trade_type")
				// ;
				String prepay_id = root.getChildText("prepay_id");
				System.out.println(prepay_id);
				return new ReMsg(ReCode.OK, prepay_id);
			}
			return new ReMsg(ReCode.FAIL);
		} else {
			return new ReMsg(ReCode.FAIL);
		}
	}

	// 取消支付游戏币余额
	public ReMsg cancelPay(HttpServletRequest req, String openid,
			String trade_type, String appip, String ts, String sig, String pf,
			String pfkey, String zoneid, String amt, String billno)
			throws Exception {
		if (StringUtils.isBlank(trade_type)) {
			trade_type = TRADE_TYPE_DEF;
		}
		Req uo = new Req(APPKEY);
		String params = uo.append("appid", APPID).append("mch_id", MCH_ID)
				.append("nonce_str", RandomUtil.random(6)).append("ts", ts)
				.append("sig", sig).append("pf", pf).append("pfkey", pfkey)
				.append("zoneid", zoneid).append("trade_type", trade_type)
				.append("amt", amt).append("billno", billno)
				.append("notify_url", NOTIFYURL).getXml();
		Map<String, String> heads = new HashMap<String, String>();
		heads.put("Content-Type", "text/xml");
		String result = HttpsClientUtil.postData(CANCEL_PAY_M, params, heads);
		System.out.println(result);
		Document doc = getDoc(result);
		Element root = doc.getRootElement();
		String return_code = root.getChildText("return_code");
		System.out.println("return_code:" + return_code);
		if (SUCCESS.equals(return_code)) {
			String result_code = root.getChildText("result_code");

			if (SUCCESS.equals(result_code)) {
				// String trade_typeR = root.getChildText("trade_type")
				// ;
				String prepay_id = root.getChildText("prepay_id");
				System.out.println(prepay_id);
				return new ReMsg(ReCode.OK, prepay_id);
			}
			return new ReMsg(ReCode.FAIL);
		} else {
			return new ReMsg(ReCode.FAIL);
		}
	}

	public String callback(String xml) {
		Resp op;
		try {
			op = new Resp(xml, APPKEY);
			if (op.resultOk()) {
				Order o = orderService.payOrder(op.get("out_trade_no"),
						op.get("transaction_id"));
				System.out.println("ok");
			} else {
				System.out.println("fail");
			}
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private static Document getDoc(String xml)
			throws UnsupportedEncodingException, JDOMException, IOException {
		return getDoc(xml, "utf-8");
	}

	private static Document getDoc(String xml, String encode)
			throws UnsupportedEncodingException, JDOMException, IOException {
		SAXBuilder saxBuilder = new SAXBuilder();
		return saxBuilder.build(new InputSource(new ByteArrayInputStream(xml
				.getBytes(encode))));
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
					sb.append("<").append(s).append(">").append(value)
							.append("</").append(s).append(">");
					signSb.append(s).append("=").append(value).append("&");
				}
			}
			String s = signSb.append("key=").append(appkey).toString();
			String sign = MDUtil.MD5.digest2HEX(s).toUpperCase();
			sb.append("<sign>").append(sign).append("</sign></xml>");
			return sb.toString();
		}
	}

	class Resp {

		public Resp(String xml, String appkey)
				throws UnsupportedEncodingException, JDOMException, IOException {
			this.appkey = appkey;
			init(xml);
		}

		Map<String, String> order = new HashMap<String, String>();
		String appkey = null;

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

		public boolean resultOk() {
			System.out.println(order.get("return_code") + " "
					+ order.get("result_code") + " " + validSign());
			return SUCCESS.equals(order.get("return_code"))
					&& SUCCESS.equals(order.get("result_code")) && validSign();
		}

		private boolean validSign() {
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
			String s = signSb.append("key=").append(appkey).toString();
			String sign = MDUtil.MD5.digest2HEX(s).toUpperCase();
			System.out.println(sign);
			return sign.equals(order.get("sign"));
		}

		private Resp init(String xml) throws UnsupportedEncodingException,
				JDOMException, IOException {
			Document doc = getDoc(xml);
			Element root = doc.getRootElement();
			this.append("return_code", root.getChildText("return_code"));
			System.out.println(this.get("return_code"));
			if (SUCCESS.equals(this.get("return_code"))) {
				this.append("appid", root.getChildText("appid"));
				this.append("mch_id", root.getChildText("mch_id"));

				this.append("loc", root.getChildText("loc"));
				this.append("goodsurl", root.getChildText("goodsurl"));
				this.append("sig", root.getChildText("sig"));
				this.append("pf", root.getChildText("pf"));
				this.append("pfkey", root.getChildText("pfkey"));
				this.append("zoneid", root.getChildText("zoneid"));
				this.append("zoneid", root.getChildText("zoneid"));
				this.append("trade_type", root.getChildText("trade_type"));

				this.append("device_info", root.getChildText("device_info"));
				this.append("nonce_str", root.getChildText("nonce_str"));
				this.append("sign", root.getChildText("sign"));
				this.append("result_code", root.getChildText("result_code"));
				this.append("err_code", root.getChildText("err_code"));
				this.append("err_code_des", root.getChildText("err_code_des"));
				this.append("openid", root.getChildText("openid"));
				this.append("is_subscribe", root.getChildText("is_subscribe"));
				this.append("bank_type", root.getChildText("bank_type"));
				this.append("total_fee", root.getChildText("total_fee"));
				this.append("fee_type", root.getChildText("fee_type"));
				this.append("cash_fee", root.getChildText("cash_fee"));
				this.append("cash_fee_type", root.getChildText("cash_fee_type"));
				this.append("coupon_fee", root.getChildText("coupon_fee"));

				String coupon_count = root.getChildText("coupon_count");
				if (coupon_count != null) {
					this.append("coupon_count", coupon_count);
					int count = Integer.parseInt(coupon_count);
					for (int i = 0; i < count; i++) {
						this.append("coupon_id_" + i,
								root.getChildText("coupon_id_" + i));
						this.append("coupon_fee_" + i,
								root.getChildText("coupon_fee_" + i));
					}
				}

				this.append("transaction_id",
						root.getChildText("transaction_id"));
				this.append("out_trade_no", root.getChildText("out_trade_no"));
				this.append("attach", root.getChildText("attach"));
				this.append("time_end", root.getChildText("time_end"));
			} else {
				this.append("return_msg", root.getChildText("return_msg"));
			}

			return this;
		}
	}
}
