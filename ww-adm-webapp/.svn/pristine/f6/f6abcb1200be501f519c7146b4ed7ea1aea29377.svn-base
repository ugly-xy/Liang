package com.zb.web.view.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBObject;
import com.zb.common.Constant.Point;
import com.zb.common.Constant.ReCode;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.service.PointLogService;
import com.zb.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminPointLogCtl {

	static final Logger log = LoggerFactory.getLogger(AdminPointLogCtl.class);

	@Autowired
	PointLogService pointLogService;

	@Autowired
	UserService userService;

	@RequestMapping("/pointLogs")
	public ModelAndView banner(Long userId, Integer opType, Integer page,
			Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = pointLogService.queryPointLog(userId, opType,
				page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("userId", userId);
		model.put("opType", opType);
		return new ModelAndView("admin/point/pointLogs", model);
	}

	@RequestMapping("/pointLog")
	public ModelAndView banner(HttpServletRequest request) {
		return new ModelAndView("admin/point/pointLog");
	}

	@ResponseBody
	@RequestMapping(value = "/pointLog", method = RequestMethod.POST)
	public ReMsg banner(int io,Long userId, Integer count, String detail, boolean push, HttpServletRequest request) {
		long adminId = userService.getUid();
		if (adminId < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return userService.adminChangePoint(io,userId, Point.ADMIN.getType(), count,
				adminId,detail,push);
	}

}
