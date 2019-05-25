package com.zb.service.pay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.zb.common.Constant.ReCode;
import com.zb.core.web.ReMsg;
import com.zb.models.finance.AgentOrder;
import com.zb.models.finance.Order;
import com.zb.service.AgentOrderService;
import com.zb.service.pay.ali.config.AlipayConfig;
import com.zb.service.pay.ali.sign.RSA;
import com.zb.service.pay.ali.util.AlipayNotify;
import com.zb.service.pay.ali.util.OrderInfoUtil2_0;

@Service
public class AliPayService extends PayService {

	static final Logger log = LoggerFactory.getLogger(AliPayService.class);

	@Autowired
	AgentOrderService agentOrderService;

	public static final String ALI_STATUS_TRADE_CLOSED = "TRADE_CLOSED";// 在指定时间段内未支付时关闭的交易;在交易完成全额退款成功时关闭的交易。
	public static final String ALI_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";// 交易创建，等待买家付款。
	public static final String ALI_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";// 交易成功，且可对该交易做操作，如：多级分润、退款等。
	public static final String ALI_STATUS_TRADE_FINISHED = "TRADE_FINISHED";// 交易成功且结束，即不可再做任何操作。
	public static final String CALLBACK = "http://api.zhuangdianbi.com/pay/ali/callback";
	public static final String AGENT_CALLBACK = "http://agent.zhuangdianbi.com/pay/ali/callback";
	public static final String AGENT_RETURN = "http://agent.zhuangdianbi.com/pay/ali/result";
	public static final String GATEWAY = "https://mapi.alipay.com/gateway.do?";

	public String getOrderInfo(Order o) {
		String total = (double) o.getFinalAmount() / 100 + "";
		Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(AlipayConfig.appId, total, o.getTitle(),
				o.getBody(), o.getNo(), CALLBACK);
		String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
		String sign = OrderInfoUtil2_0.getSign(params, AlipayConfig.private_key);
		final String orderInfo = orderParam + "&" + sign;
		// System.out.println(orderInfo);
		return orderInfo;
	}

	public String getOrderInfo(AgentOrder o) throws AlipayApiException {
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.appId,
				AlipayConfig.private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key,
				AlipayConfig.sign_type);
		// 设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AGENT_RETURN);
		alipayRequest.setNotifyUrl(AGENT_CALLBACK);
		String total = (double) o.getRmbAmount() / 100 + "";
		alipayRequest.setBizContent("{\"out_trade_no\":\"" + o.getNo() + "\"," + "\"total_amount\":\"" + total + "\","
				+ "\"subject\":\"" + o.getTitle() + "\"," + "\"body\":\"" + o.getTitle() + "\","
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		String result = alipayClient.pageExecute(alipayRequest).getBody();
		return result;
	}

	public String alipayClient(HttpServletRequest request, HttpServletResponse response) {
		// 获取支付宝POST过来反馈信息
		response.setContentType("text/html");// 注意加上
		String result = null;
		java.io.BufferedReader reader = null;
		String success = null;
		String sign = null;
		String partner = null;
		String service = null;
		String prestr = "";

		String ret = "";
		// post获取字符串
		try {
			reader = request.getReader();// 获得字符流
			StringBuffer content = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line + "\r\n");
			}
			result = content.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				reader = null;
			} catch (Exception e) {

			}
		}

		// 客户端同步参数建议urlencode之后传输到此服务端，防止字符“＋”变成空格。
		try {
			result = URLDecoder.decode(result, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 利用“&”分割
		String[] array = result.split("&");

		// 除去数组中的空值和签名参数,且把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串,不需要排序
		for (int i = 0; i < array.length; i++) {
			String key = array[i].split("=", 2)[0];
			String value = array[i].split("=", 2)[1];
			if (key.equals("sign") || key.equals("sign_type")) {
				if (key.equals("sign")) {
					sign = value.replace("\"", "");
				}
				continue;
			} else {
				if (key.equals("success")) {
					success = value.replace("\"", "");
				}
				if (key.equals("partner")) {
					partner = value.replace("\"", "");
				}
				if (key.equals("service")) {
					service = value.replace("\"", "");
				}
				prestr = prestr + key + "=" + value + "&";
			}
		}

		prestr = prestr.substring(0, prestr.length() - 1);// 去掉最后一个&符号。

		// 调试用，判断取值是否正确。
		// AlipayCore.logResult(success+"\r\n"+sign+"\r\n"+partner+"\r\n"+prestr,"temp");

		if (prestr != "") {
			// 注意：在客户端把返回参数请求过来的时候务必要把sign做一次urlencode,保证"+"号字符不会变成空格。
			if (success.equals("true"))// 判断success是否为true.
			{
				// 验证参数是否匹配
				if (partner.equals(AlipayConfig.appId) && service.equals(AlipayConfig.service)) {
					// 调试用，判断取值是否正确。
					// AlipayCore.logResult("prestr:"+prestr+"\r\n"+"sign:"+sign,"dataandsign");

					boolean issign = false;

					// 获得验签结果
					issign = RSA.verify(prestr, sign, AlipayConfig.alipay_public_key, AlipayConfig.input_charset);
					if (issign) {
						// 此处可做商家业务逻辑，建议商家以异步通知为准。
						ret = "return success";
					} else {
						ret = "return fail";
					}
				} else {
					ret = "客户端信息与服务端配置信息有误！";
				}
			} else {
				ret = "此同步返回无效!";
			}
		} else {
			ret = "无客户端参数!";
		}
		return ret;
	}

	public String agentCallback(HttpServletRequest request, HttpServletResponse response) {
		String result = "fail";
		try {
			Map<String, String> params = new HashMap<String, String>();
			Map<String, String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用
			//	log.error(name+"="+valueStr);
//				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
//				log.error(name+"="+valueStr);
				params.put(name, valueStr);
			}
			
			String charset = params.get("charset");
			if(StringUtils.isBlank(charset)){
				charset = AlipayConfig.charset;
			}
			
			String sign_type = params.get("sign_type");
			if(StringUtils.isBlank(charset)){
				sign_type = AlipayConfig.sign_type;
			}		
					

			boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key,
					charset, sign_type); // 调用SDK验证签名
			log.error("signVerified:" + signVerified);
			// ——请在这里编写您的程序（以下代码仅作参考）——

			/*
			 * 实际验证过程建议商户务必添加以下校验： 1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
			 * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
			 * 3、校验通知中的seller_id（或者seller_email)
			 * 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
			 * 4、验证app_id是否为该商户本身。
			 */
			if (signVerified) {// 验证成功
				// 商户订单号
				String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
				// 支付宝交易号
				String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
				// 交易状态
				String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
				log.error(
						"out_trade_no:" + out_trade_no + " | trade_no:" + trade_no + " | trade_status:" + trade_status);
				if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
					// 判断该笔订单是否在商户网站中已经做过处理
					// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					// 如果有做过处理，不执行商户的业务程序
					// 注意：
					// 退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
					int total_fee = (int) (Double.parseDouble(
							new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8")) * 100);
					log.error("total_fee:" + total_fee);
					AgentOrder o = agentOrderService.payOrder(out_trade_no, trade_no);
					ReMsg r = agentOrderService.openOrder(o, total_fee);
					if (ReCode.ORDER_OPENED.reCode() == r.getCode() || ReCode.OK.reCode() == r.getCode()) {
						result = "success";// 请不要修改或删除
					}
				}
				result = "success";
			}

		} catch (Exception e) {
			log.error("open ali order err:", e);
		}
		return result;
	}

	public String alipayCallback(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// System.out.println(name + "=" + valueStr);
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		String result = "fail";
		// 商户订单号
		try {
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
			// 支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
			// 交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
			// 异步通知ID
			String notify_id = request.getParameter("notify_id");
			// sign
			String sign = request.getParameter("sign");
			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			if (notify_id != "" && notify_id != null) {// 判断接受的post通知中有无notify_id，如果有则是异步通知。
				if (AlipayNotify.verifyResponse(notify_id).equals("true")) {// 判断成功之后使用getResponse方法判断是否是支付宝发来的异步通知。
					if (AlipayNotify.getSignVeryfy(params, sign)) {// 使用支付宝公钥验签
						if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
							// 判断该笔订单是否在商户网站中已经做过处理
							// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
							// 如果有做过处理，不执行商户的业务程序
							// 注意：退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
							// 请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
							int total_fee = (int) (Double.parseDouble(
									new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8"))
									* 100);
							Order o = orderService.payOrder(out_trade_no, trade_no);
							ReMsg r = orderService.openOrder(o, total_fee);
							if (ReCode.ORDER_OPENED.reCode() == r.getCode() || ReCode.OK.reCode() == r.getCode()) {
								result = "success";// 请不要修改或删除
							}
						}
						// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
						// result = "success";// 请不要修改或删除

						// 调试打印log
						// AlipayCore.logResult("notify_url
						// success!","notify_url");
					} else {// 验证签名失败
						result = "sign fail";
					}
				} else {// 验证是否来自支付宝的通知失败
					result = "response fail";
				}
			} else {
				result = "no notify message";
			}
		} catch (IOException e) {
			log.error("open ali order err:", e);
		}
		return result;
	}

	public static void main(String[] args) throws IOException, URISyntaxException, JDOMException, AlipayApiException {
//		AliPayService wps = new AliPayService();
//		// Long id, int type,String orderNo, String productId, long buyUid, long
//		// recUid, String payType,int rmbAmount, int amount,
//		// int count, String title, String body
//		AgentOrder o = new AgentOrder(1L, 1, "122121", "1", 10000002, 10000002, "ali", 100, 100, 1, "金币", "金币100");
//
//		System.out.println(wps.getOrderInfo(o));
		
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("gmt_create","2017-12-09 21:49:35");
			params.put("charset","utf-8");
			params.put("subject","充值金币");
			params.put("sign","cq/qOEheTAUcYnCcJd1fcBwHY932IjuwpJKMVliCdi4tTsYNloBrA3cxuOpHqtmwjugBhgX308PhAU/PHtwAe3ynVuiLLQGa4hpIeLNBNa2oA90DDFk1uHTiEz6XZfuyB9JxvJxRDEMZMuJi7iP0rya82a+xukRZ/Ugp1OQXapI=");
			params.put("buyer_id","2088012012394518");
			params.put("body","充值金币");
			params.put("invoice_amount","0.01");
			params.put("notify_id","6f7584b4e1df27abe75865caf354947jxt");
			params.put("fund_bill_list","[{\"amount\":\"0.01\",\"fundChannel\":\"ALIPAYACCOUNT\"}]");
			params.put("notify_type","trade_status_sync");
			params.put("trade_status","TRADE_SUCCESS");
			params.put("receipt_amount","0.01");
			params.put("app_id","2016081101735370");
			params.put("buyer_pay_amount","0.01");
			params.put("sign_type","RSA");
			params.put("seller_id","2088121635617952");
			params.put("gmt_payment","2017-12-09 21:49:38");
			params.put("notify_time","2017-12-09 21:49:38");
			params.put("version","1.0");
			params.put("out_trade_no","201712093732182222");
			params.put("total_amount","0.01");
			params.put("trade_no","2017120921001004510550540450");
			params.put("auth_app_id","2016081101735370");
			params.put("point_amount","0.00");
			boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key,
					AlipayConfig.charset, AlipayConfig.sign_type); // 调用SDK验证签名
			System.out.println(signVerified);
		} catch (Exception e) {
			log.error("open ali order err:", e);
		}

	}

}
