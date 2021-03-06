package com.we.web.view.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBObject;
import com.we.common.Constant.ReCode;
import com.we.core.Page;
import com.we.core.web.ReMsg;
import com.we.service.BusinessCooperationService;

@Controller
@RequestMapping("/admin")
public class AdminBusinessCooperationCtl extends AdminBaseCtl {

	static final Logger log = LoggerFactory.getLogger(AdminBusinessCooperationCtl.class);

	@Autowired
	BusinessCooperationService businessCooperationService;

	/** 所有的商务合作列表 */
	@RequestMapping("/businessCooperations")
	public ModelAndView resPacks(Integer status, Long uid, Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = businessCooperationService.query(status, uid, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		model.put("uid", uid);
		return new ModelAndView("admin/businessCooperation/businessCooperations", model);
	}

	/** 查看一条商务合作信息的详情 */
	@RequestMapping("/businessCooperation")
	public ModelAndView pack(Long _id) {
		DBObject dbo = businessCooperationService.findById(_id);
		return new ModelAndView("admin/businessCooperation/businessCooperation", "obj", dbo);
	}

	/** 更改商务合作信息的状态 */
	@ResponseBody
	@RequestMapping("/businessCooperation/status")
	public ReMsg status(Long _id, int status) {
		long adminId = businessCooperationService.getUid();
		if (adminId < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return businessCooperationService.status(_id, status, adminId);
	}
}
