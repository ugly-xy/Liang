package com.zb.web.view.admin;

import java.text.ParseException;
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
import com.zb.models.UserTitle;
import com.zb.models.finance.AgentOrder;
import com.zb.service.AgentOrderService;
import com.zb.service.AgentService;
import com.zb.service.MessageService;
import com.zb.service.RechargeShoutService;
import com.zb.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminAgentAppCtl {

	static final Logger log = LoggerFactory.getLogger(AdminAgentAppCtl.class);

	@Autowired
	AgentService agentService;

	@Autowired
	AgentOrderService agentOrderService;
	@Autowired
	MessageService messageService;

	@Autowired
	UserService userService;
	@Autowired
	RechargeShoutService rechargeShoutService;

	@RequestMapping("/agents")
	public ModelAndView agents(Long id, String name, String phone, Integer page, Integer size,
			HttpServletRequest request) {
		Page<DBObject> curPage = agentService.query(id, name, phone, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("id", id);
		model.put("name", name);
		model.put("phone", phone);
		return new ModelAndView("admin/third/agents", model);
	}

	@RequestMapping("/agent")
	public ModelAndView game(Long id) {
		ModelAndView model = new ModelAndView("admin/third/agent");
		if (id == null) {
			return model;
		}
		DBObject dbo = agentService.findById(id);
		model.addObject("obj", dbo);

		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/agent", method = RequestMethod.POST)
	public ReMsg saveGame(Long id, String name, String phone, String pwd, Double rate, Double coinRate,
			Integer status) {
		return agentService.upsert(id, name, phone, pwd, rate, coinRate, status);
	}

	@RequestMapping("/agentOrders")
	public ModelAndView orders(Long buyUid, String no, Long recUid, String payType, String thirdNo, Integer status,
			String startTime, String endTime, Integer page, Integer size, HttpServletRequest request)
			throws ParseException {
		long startDate = 0;
		long endDate = 0;
		if (StringUtils.isNotBlank(startTime)) {
			startDate = DateUtil.getZeroTime(DateUtil.convertDate(startTime, "yyyy-MM-dd"));
		}
		if (StringUtils.isNotBlank(endTime)) {
			endDate = DateUtil.getZeroTime(DateUtil.convertDate(endTime, "yyyy-MM-dd")) + DateUtil.DAY;
		}
		Page<DBObject> curPage = agentOrderService.query(buyUid, no, recUid, payType, thirdNo, status, startDate,
				endDate, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		if (startDate > 0 && endDate > 0) {
			long allAmount = agentOrderService.allAmount(buyUid, no, recUid, payType, thirdNo, status, startDate,
					endDate);
			model.put("allFinalAmount", allAmount);
		}
		model.put("page", curPage);
		model.put("status", status);
		model.put("no", no);
		model.put("buyUid", buyUid);
		model.put("recUid", recUid);
		model.put("payType", payType);
		model.put("thirdNo", thirdNo);
		model.put("startTime", startTime);
		model.put("endTime", endTime);

		return new ModelAndView("admin/third/agentOrders", model);
	}

	@RequestMapping("/agentOrder")
	public ModelAndView orders(long id) {
		DBObject dbo = agentOrderService.findById(id);
		return new ModelAndView("admin/third/agentOrder", "obj", dbo);
	}

	@ResponseBody
	@RequestMapping(value = "/agentOrder/open", method = RequestMethod.POST)
	public ReMsg order(Long id, String thirdNo) {
		return agentOrderService.openOrderByAdmin(id, thirdNo);
	}
}
