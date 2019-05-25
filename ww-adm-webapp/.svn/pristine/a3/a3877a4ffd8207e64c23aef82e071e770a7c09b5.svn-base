package com.zb.web.view.admin;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.common.utils.DateUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.service.OrderService;
import com.zb.service.pay.ApplePayService;
import com.zb.service.pay.CoinService;

@Controller
@RequestMapping("/admin")
public class AdminFinanceCtl {

	static final Logger log = LoggerFactory.getLogger(AdminFinanceCtl.class);

	@Autowired
	OrderService orderService;

	@Autowired
	CoinService coinService;
	
	@Autowired
	ApplePayService applePayService;

	@RequestMapping("/orders")
	public ModelAndView orders(String no, Long recUid, String payType, String thirdNo, Integer status, String startTime,
			String endTime, Integer page, String appCode, Integer size, HttpServletRequest request)
			throws ParseException {
		long startDate = 0;
		long endDate = 0;
		if (StringUtils.isNotBlank(startTime)) {
			startDate = DateUtil.getZeroTime(DateUtil.convertDate(startTime, "yyyy-MM-dd"));
		}
		if (StringUtils.isNotBlank(endTime)) {
			endDate = DateUtil.getZeroTime(DateUtil.convertDate(endTime, "yyyy-MM-dd")) + DateUtil.DAY;
		}
		Page<DBObject> curPage = orderService.query(no, recUid, payType, thirdNo, status, startDate, endDate, appCode,
				page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		if (startDate > 0 && endDate > 0) {
			long allAmount = orderService.allAmount(no, recUid, payType, thirdNo, status, startDate, endDate, appCode);
			model.put("allFinalAmount", allAmount);
		}
		model.put("page", curPage);
		model.put("status", status);
		model.put("no", no);
		model.put("recUid", recUid);
		model.put("payType", payType);
		model.put("appCode", appCode);
		model.put("thirdNo", thirdNo);
		model.put("startTime", startTime);
		model.put("endTime", endTime);

		return new ModelAndView("admin/finance/orders", model);
	}

	@RequestMapping("/order")
	public ModelAndView orders(long id) {
		DBObject dbo = orderService.findById(id);
		return new ModelAndView("admin/finance/order", "obj", dbo);
	}

	@ResponseBody
	@RequestMapping(value = "/order/open", method = RequestMethod.POST)
	public ReMsg order(Long id, String thirdNo) {
		return orderService.openOrderByAdmin(id, thirdNo);
	}

	@RequestMapping("/coinlogs")
	public ModelAndView coinlogs(String st, String et, Integer io, Integer type, Long uid, Integer page, Integer size,
			HttpServletRequest request) throws ParseException {
		long startDate = 0;
		long endDate = 0;
		if (StringUtils.isNotBlank(st)) {
			startDate = DateUtil.getZeroTime(DateUtil.convertDate(st, "yyyy-MM-dd"));
		}
		if (StringUtils.isNotBlank(et)) {
			endDate = DateUtil.getZeroTime(DateUtil.convertDate(et, "yyyy-MM-dd")) + DateUtil.DAY;
		}
		Page<DBObject> curPage = coinService.queryCoinLog(startDate, endDate, io, type, uid, page, size);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("io", io);
		model.put("uid", uid);
		model.put("type", type);
		model.put("st", st);
		model.put("et", et);
		return new ModelAndView("admin/finance/coinLogs", model);
	}

	@RequestMapping("/changeCoin")
	public ModelAndView changeCoin() {
		return new ModelAndView("admin/finance/coinLog");
	}

	@ResponseBody
	@RequestMapping(value = "/changeCoin", method = RequestMethod.POST)
	public ReMsg coinlogs(int io, long uid, int coin, String detail, boolean push, boolean changeVip,
			HttpServletRequest request) {
		long adminId = coinService.getUid();
		if (adminId < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return coinService.adminChangeCoin(adminId, io, uid, coin, detail, push, changeVip);
	}

	/** 金币统计 */
	@RequestMapping("/stCoinIO")
	public ModelAndView rankList(String st, Integer type, Integer page, Integer size, HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		Integer day = null;
		Calendar c = null;
		if (StringUtils.isNotBlank(st)) {
			c = Calendar.getInstance();
			try {
				c.setTimeInMillis(DateUtil.convertDate(st, "yyyy-MM-dd").getTime());
			} catch (ParseException e) {
				c.setTimeInMillis(System.currentTimeMillis());
				log.error("date--err", e);
			}
		} else {
			if (null == type) {
				c = Calendar.getInstance();
				c.setTimeInMillis(System.currentTimeMillis());
			}
		}
		if (c != null) {
			int y = c.get(Calendar.YEAR);
			int m = c.get(Calendar.MONTH) + 1;
			int d = c.get(Calendar.DAY_OF_MONTH);
			day = y * 10000 + m * 100 + d;
		}
		Page<DBObject> curPage = coinService.queryStCoinIO(day, type, page, size);
		model.put("page", curPage);
		model.put("st", st);
		model.put("type", type);
		model.put("balance", coinService.queryCoinBalance(day));
		return new ModelAndView("admin/finance/stCoinIO", model);
	}
	
	@RequestMapping("/appleErrorOrders")
	public ModelAndView appleErrorOrders(Long id, Long uid, Integer status,Integer page, Integer size,
			HttpServletRequest request) {
		Page<DBObject> curPage = applePayService.query(id, uid, status, page, size);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("id", id);
		model.put("uid", uid);
		model.put("status", status);
		return new ModelAndView("admin/finance/appleErrorOrders", model);
	}
	
	@ResponseBody
	@RequestMapping("/openAEO")
	public ReMsg openAppleErrorOrders(Long id,
			HttpServletRequest request) {
		return applePayService.openAppleOrderAdmin(id);
	}


	// // 补偿金币
	// @ResponseBody
	// @RequestMapping(value = "/coinRepay")
	// public ReMsg coinRepay() {
	// long adminId = coinService.getUid();
	// if (adminId < 1) {
	// return new ReMsg(ReCode.PARAMETER_ERR);
	// }
	// return orderService.coinRepay();
	// }

	// // static int[] vias = { 1, 2, 5 };
	// @RequestMapping("/banner")
	// public ModelAndView pack(Long id) {
	// ModelAndView model = new ModelAndView("admin/banner/banner");
	// model.addObject("types", BannerService.TYPES);
	// model.addObject("ops", OperationType.getOps());
	//
	// // model.addObject("vias", vias);
	// if (id == null) {
	// return model;
	// }
	// DBObject dbo = bannerService.findBannerById(id);
	// model.addObject("obj", dbo);
	// return model;
	// }
	//
	// @ResponseBody
	// @RequestMapping(value = "/banner", method = RequestMethod.POST)
	// public ReMsg saveResPack(Long id, String type, String title, String pic,
	// String op, String opId, Integer status, Integer[] vias, Integer sort) {
	// // Integer[] vias = null;
	// // if (via == 0) {
	// // vias = new Integer[] { Via.Android.getVia(), Via.IPhone.getVia(),
	// // Via.Web.getVia() };
	// // } else {
	// // vias = new Integer[] { via };
	// // }
	// return bannerService.upsert(id, type, title, pic, op, opId, status,
	// vias, sort);
	// }
}
