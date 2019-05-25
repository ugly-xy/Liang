package com.we.web.view.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBObject;
import com.we.common.Constant.Const;
import com.we.common.Constant.ReCode;
import com.we.common.utils.DateUtil;
import com.we.core.Page;
import com.we.core.web.ReMsg;
import com.we.service.WithdrawLogService;
import com.we.service.userTask.TokenPrjService;

@Controller
@RequestMapping("/admin/withDrawLog")
public class AdminWithDrawLogCtl {

	static final Logger log = LoggerFactory.getLogger(AdminWithDrawLogCtl.class);

	@Autowired
	WithdrawLogService withdrawLogService;

	@Autowired
	TokenPrjService tokenPrjService;

	/** 列表 */
	@RequestMapping("/list")
	public ModelAndView list(Long uid, Integer status, Integer searchAmount, String coinType, String searchDate,
			Integer page, Integer size, HttpServletRequest request) {
		if (size == null || size == 0) {
			size = 100;
		}
		Page<DBObject> curPage = withdrawLogService.query(status, uid, searchAmount, coinType, searchDate, page, size);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("uid", uid);
		model.put("status", status);
		model.put("searchAmount", searchAmount);
		model.put("searchDate", searchDate);
		model.put("coinTypes", tokenPrjService.findAllCoinType(Const.STATUS_OK));
		model.put("coinType", coinType);
		model.put("date", DateUtil.dateFormatShort(new Date()));
		return new ModelAndView("admin/withDrawLog/list", model);
	}

	/** 审核一个 */
	@ResponseBody
	@RequestMapping(value = "/valid", method = RequestMethod.POST)
	public ReMsg valid(Long id, int status, HttpServletRequest request) {
		long adminId = withdrawLogService.getUid();
		if (adminId < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return withdrawLogService.validWithdraw(id, status, adminId);
	}

	/**
	 * 导出
	 * 
	 * @param amount
	 * @param request
	 * @param resp
	 */
	@RequestMapping(value = "/export")
	public void export(Integer amount, String date, HttpServletRequest request, HttpServletResponse resp) {
		withdrawLogService.export(amount, date, request, resp);
	}

	/**
	 * 导出
	 * 
	 * @param amount
	 * @param request
	 * @param resp
	 */
	@ResponseBody
	@RequestMapping(value = "/toAllOk", method = RequestMethod.POST)
	public ReMsg validAll(Integer amount, HttpServletRequest request, HttpServletResponse resp) {
		return withdrawLogService.validAll(amount);
	}

}
