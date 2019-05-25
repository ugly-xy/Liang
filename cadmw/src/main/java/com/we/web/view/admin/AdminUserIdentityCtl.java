package com.we.web.view.admin;

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
import com.we.common.Constant.ReCode;
import com.we.core.Page;
import com.we.core.web.ReMsg;
import com.we.service.UserIdentityService;

@Controller
@RequestMapping("/admin")
public class AdminUserIdentityCtl extends AdminBaseCtl{

	static final Logger log = LoggerFactory.getLogger(AdminUserIdentityCtl.class);

	@Autowired
	UserIdentityService userIdentityService;

	/** 列表 */
	@RequestMapping("/userIdentitys")
	public ModelAndView userIdentitys(Long uid, Integer status, Integer page, Integer size,
			HttpServletRequest request) {
		if (size == null || size == 0) {
			size = 100;
		}
		Page<DBObject> curPage = userIdentityService.query(uid, status, page, size);
		Map<String, Object> model = getImgMap();
		model.put("page", curPage);
		model.put("uid", uid);
		model.put("status", status);
		return new ModelAndView("admin/userIdentity/userIdentitys", model);
	}

	/** 审核一个 */
	@ResponseBody
	@RequestMapping(value = "/validUserIdentity", method = RequestMethod.POST)
	public ReMsg valid(Long id, int status, HttpServletRequest request) {
		long adminId = userIdentityService.getUid();
		if (adminId < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return userIdentityService.validUseridentity(id, status, adminId);
	}

	@RequestMapping("/userIdentity")
	public ModelAndView userIdentity(Long _id) {
		return new ModelAndView("admin/userIdentity/userIdentity", "obj", userIdentityService.findById(_id));
	}

}
