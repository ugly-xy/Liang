package com.zb.web.view.admin;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.common.utils.DateUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.finance.AgentOrder;
import com.zb.service.AgentOrderService;
import com.zb.service.AgentService;

@Controller
@RequestMapping("/admin")
public class AdminOrderCtl extends AdminBaseCtl {

	static final Logger log = LoggerFactory.getLogger(AdminOrderCtl.class);

	@Autowired
	AgentOrderService agentOrderService;

	@Autowired
	AgentService agentService;

	@RequestMapping("/toOrder")
	public ModelAndView order(HttpServletRequest request) {
		DBObject agent = agentService.findById(super.getUid());
		return new ModelAndView("admin/finance/createOrder", "agent", agent);
	}

	@RequestMapping("/toPack")
	public ModelAndView toPack(HttpServletRequest request) {
		DBObject agent = agentService.findById(super.getUid());
		return new ModelAndView("admin/finance/createPackOrder", "agent", agent);
	}

	@RequestMapping("/createOrder")
	public @ResponseBody ReMsg createOrder(int type, long recUid, String productId, int count, String payType,
			Integer all, String content, Integer reply, HttpServletRequest req) {
		return agentOrderService.createOrder(type, productId, count, recUid, payType, all, content, reply, req);
	}

	@RequestMapping("/createOrderAli")
	public ModelAndView createOrder(int type, long recUid, String productId, int count, Integer all, String content,
			Integer reply, HttpServletRequest req,HttpServletResponse resp) {
		ReMsg rm = agentOrderService.createOrder(type, productId, count, recUid, AgentOrder.THIRD_ALI_APP, all, content,
				reply, req);
		return new ModelAndView("admin/finance/alipayReq", "data", rm.getData());
	}

	@RequestMapping("/orders")
	public ModelAndView orders(String no, Long recUid, String payType, String thirdNo, Integer status, String startTime,
			String endTime, Integer page, String appCode, Integer size, HttpServletRequest request)
			throws ParseException {
		long uid = super.getUid();
		long startDate = 0;
		long endDate = 0;
		if (StringUtils.isNotBlank(startTime)) {
			startDate = DateUtil.getZeroTime(DateUtil.convertDate(startTime, "yyyy-MM-dd"));
		}
		if (StringUtils.isNotBlank(endTime)) {
			endDate = DateUtil.getZeroTime(DateUtil.convertDate(endTime, "yyyy-MM-dd")) + DateUtil.DAY;
		}
		Page<DBObject> curPage = agentOrderService.query(uid, no, recUid, payType, thirdNo, status, startDate, endDate,
				page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		if (startDate > 0 && endDate > 0) {
			long allAmount = agentOrderService.allAmount(uid, no, recUid, payType, thirdNo, status, startDate, endDate);
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
		DBObject dbo = agentOrderService.findById(id);
		return new ModelAndView("admin/finance/order", "obj", dbo);
	}

}
