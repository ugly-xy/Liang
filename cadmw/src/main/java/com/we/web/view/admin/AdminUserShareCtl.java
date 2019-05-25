package com.we.web.view.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBObject;
import com.we.core.Page;
import com.we.service.UserShareService;

@Controller
@RequestMapping("/admin")
public class AdminUserShareCtl extends AdminBaseCtl {

	static final Logger log = LoggerFactory.getLogger(AdminUserShareCtl.class);

	@Autowired
	UserShareService userShareService;

	/** 用户邀请分级列表 */
	@RequestMapping("/userShares")
	public ModelAndView userShares(Long _id, Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = userShareService.query(_id, page, size);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("_id", _id);
		return new ModelAndView("admin/users/userShares", model);
	}

}
