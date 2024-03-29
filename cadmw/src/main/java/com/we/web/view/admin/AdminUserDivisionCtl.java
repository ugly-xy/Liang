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
import com.we.models.division.DivisionTask;
import com.we.service.UserDivisionService;

@Controller
@RequestMapping("/admin")
public class AdminUserDivisionCtl extends AdminBaseCtl {

	static final Logger log = LoggerFactory.getLogger(AdminUserDivisionCtl.class);

	@Autowired
	UserDivisionService userDivisionService;


	/** 用户段位进度 */
	@RequestMapping("/userDivisions")
	public ModelAndView userDivisions(Long _id, Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = userDivisionService.query(_id, page, size);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("_id", _id);
		model.put("conditionTypes", DivisionTask.DivisionTaskType.values());
		return new ModelAndView("admin/users/userDivisions", model);
	}

}
