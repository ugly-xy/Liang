package com.zb.web.view.admin;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.zb.common.Constant.ReCode;
import com.zb.core.web.ReMsg;
import com.zb.models.finance.Order;
import com.zb.service.OrderService;
import com.zb.service.pay.AliPayService;
import com.zb.service.pay.ApplePayService;
import com.zb.service.pay.MidasPayService;
import com.zb.service.pay.PayProductService;
import com.zb.service.pay.WeiXinPayService;

@Controller
@RequestMapping("/pay")
public class PayCtl {

	static final Logger log = LoggerFactory.getLogger(PayCtl.class);

//	@Autowired
//	OrderService orderService;

	@Autowired
	WeiXinPayService weiXinPayService;

//	@Autowired
//	MidasPayService midasPayService;
//
//	@Autowired
//	ApplePayService applePayService;

	@Autowired
	AliPayService aliPayService;

//	@ResponseBody
//	@RequestMapping("/products")
//	public ReMsg getProducts(String payType, HttpServletRequest req) {
//		if (Order.THIRD_APPLE_PAY_APP.equals(payType)) {
//			return new ReMsg(ReCode.OK, PayProductService.getApples());
//		} else {
//			return new ReMsg(ReCode.OK, PayProductService.getOthers());
//		}
//	}

//	/**
//	 * 创建订单
//	 * 
//	 * @param type
//	 *            类型 1 金币 2 物品 3礼包
//	 * @param linkId
//	 *            物品 礼包的时候用对应 金币为0
//	 * @param count
//	 *            购买金币数量
//	 * @param recUid
//	 *            接受者Id
//	 * @param payType
//	 *            支付类型
//	 * @param discountNo
//	 *            优惠券
//	 * @param request
//	 * @return
//	 * @throws JsonParseException
//	 * @throws JsonMappingException
//	 * @throws IOException
//	 */
//	@ResponseBody
//	@RequestMapping("/createOrder")
//	public ReMsg createOrder(String productId, Long recUid, String payType, String discountNo, String appCode,
//			HttpServletRequest req) {
//		return orderService.createOrder(productId, recUid, payType, discountNo, appCode, req);
//	}

	@RequestMapping("/weixin/callback/")
	public void weixinPay(HttpServletRequest request, HttpServletResponse response) {
		log.error("/weixin/callback/");
		byte[] buffer = new byte[64 * 1024];
		InputStream in;
		try {
			in = request.getInputStream();
			int length = in.read(buffer);
			String encode = request.getCharacterEncoding();
			if (StringUtils.isBlank(encode)) {
				encode = "utf-8";
			}
			byte[] data = new byte[length];
			System.arraycopy(buffer, 0, data, 0, length);
			String context = new String(data, encode);
			System.out.println(context);
			String s = weiXinPayService.agentCallback(context);
			response.getOutputStream().write(s.getBytes("utf-8"));
		} catch (IOException e) {
			log.error("/weixin/callback err:", e);
		}
	}

	@RequestMapping("/ali/callback")
	public void alipay(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// System.out.println("Ali callback");
		String ret = aliPayService.agentCallback(request, response);
		response.getOutputStream().print(ret);
	}
	
	@RequestMapping("/ali/result")
	public String alipayReturn(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// System.out.println("Ali callback");
		return "admin/finance/alipayRet";
	}

//	@ResponseBody
//	@RequestMapping("/midas/callback")
//	public String midaspay(String discount, String payment_type, String subject, String trade_no, String buyer_email,
//			String gmt_create, String notify_type, int quantity, String out_trade_no, String seller_id,
//			String notify_time, String body, String trade_status, String is_total_fee_adjust, double total_fee,
//			String gmt_payment, String seller_email, double price, String buyer_id, String notify_id, String use_coupon,
//			String sign_type, String sign, HttpServletRequest request) {
//		byte[] buffer = new byte[64 * 1024];
//		InputStream in;
//		try {
//			in = request.getInputStream();
//			int length = in.read(buffer);
//			String encode = request.getCharacterEncoding();
//			if (StringUtils.isBlank(encode)) {
//				encode = "utf-8";
//			}
//			byte[] data = new byte[length];
//			System.arraycopy(buffer, 0, data, 0, length);
//			String context = new String(data, encode);
//			System.out.println(context);
//			midasPayService.callback(context);
//			return "";
//		} catch (IOException e) {
//			log.error("/weixin/callback err:", e);
//		}
//		return "";
//	}
//
//	@ResponseBody
//	@RequestMapping("/apple")
//	public ReMsg applePay(String receipt,String appCode, HttpServletRequest request) {
//		return applePayService.buyGoods(receipt,appCode, request);
//	}

}
