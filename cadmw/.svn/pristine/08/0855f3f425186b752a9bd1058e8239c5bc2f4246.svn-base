package com.we.web.view.admin;

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
import com.we.core.Page;
import com.we.core.web.ReMsg;
import com.we.service.userTask.UserTaskService;

@Controller
@RequestMapping("/admin")
public class AdminUserTaskCtl extends AdminBaseCtl {

	static final Logger log = LoggerFactory.getLogger(AdminUserTaskCtl.class);

	@Autowired
	UserTaskService userTaskService;

	/** 用户任务进度列表 */
	@RequestMapping("/userTasks")
	public ModelAndView userTasks(Long uid, Long taskId,Integer status, Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = userTaskService.query(uid, taskId,status, page, size);
		Map<String, Object> model = super.getImgMap();
		model.put("page", curPage);
		model.put("uid", uid);
		model.put("taskId", taskId);
		model.put("status", status);
		return new ModelAndView("admin/users/userTasks", model);
	}
	
	/** 用户任务进度列表 */
	@ResponseBody
	@RequestMapping(value = "/userTask", method = RequestMethod.POST)
	public ReMsg userTask(Long id,Integer status) {
		return userTaskService.audit(id, status);
	}

}
