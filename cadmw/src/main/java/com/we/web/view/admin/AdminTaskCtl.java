package com.we.web.view.admin;

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
import com.we.common.Constant.Const;
import com.we.common.Constant.ReCode;
import com.we.common.utils.DateUtil;
import com.we.core.Page;
import com.we.core.web.ReMsg;
import com.we.models.userTask.Task;
import com.we.service.ApplyForTaskService;
import com.we.service.userTask.TaskService;
import com.we.service.userTask.TokenPrjService;

@Controller
@RequestMapping("/admin")
public class AdminTaskCtl extends AdminBaseCtl {

	static final Logger log = LoggerFactory.getLogger(AdminTaskCtl.class);

	@Autowired
	TaskService taskService;

	@Autowired
	TokenPrjService tokenPrjService;

	@Autowired
	ApplyForTaskService applyForTaskService;

	/** 任务 */
	@RequestMapping("/tasks")
	public ModelAndView task(Long _id, Integer type, Integer status, Integer page, Integer size,
			HttpServletRequest request) {
		Page<DBObject> curPage = taskService.query(_id, type, status, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = super.getImgMap();
		model.put("page", curPage);
		model.put("_id", _id);
		model.put("status", status);
		model.put("type", type);
		model.put("templates", Task.TastTemplate.values());
		model.put("types", Task.TastType.values());
		return new ModelAndView("admin/task/tasks", model);
	}

	@RequestMapping("/task")
	public ModelAndView task(Long _id) {
		Map<String, Object> model = super.getImgMap();
		if (_id != null && _id > 0) {
			model.put("obj", taskService.findById(_id));
		}
		model.put("templates", Task.TastTemplate.values());
		model.put("types", Task.TastType.values());
		model.put("tokens", tokenPrjService.findAllCoinType(Const.STATUS_OK));
		return new ModelAndView("admin/task/task", model);
	}

	@ResponseBody
	@RequestMapping(value = "/task", method = RequestMethod.POST)
	public ReMsg update(Long _id, Integer type, String title, String[] coinType, Double[] coinAmount, Integer template,
			Integer status, Integer sort, String data, String qrCode, String st, String et, String detail,
			String symbol, Long total, Integer count,String uniqueId,String pic) throws ParseException {
		long startTime = 0;
		long endTime = 0;
		if (StringUtils.isNotBlank(st)) {
			startTime = DateUtil.getZeroTime(DateUtil.convertDate(st, "yyyy-MM-dd"));
		}
		if (StringUtils.isNotBlank(et)) {
			endTime = DateUtil.getZeroTime(DateUtil.convertDate(et, "yyyy-MM-dd")) + DateUtil.DAY;
		}
		return taskService.upSet(_id, title, detail, type, template, data, qrCode, total, count, sort, startTime,
				endTime, symbol, coinType, coinAmount, status,uniqueId,pic);
	}

	@ResponseBody
	@RequestMapping("/task/del")
	public ReMsg del(Long _id) {
		return new ReMsg(taskService.downTask(_id));
	}

	// 任务申请
	/** 列表 */
	@RequestMapping("/applyForTasks")
	public ModelAndView applyForTasks(Integer status, Long uid, Integer page, Integer size,
			HttpServletRequest request) {
		Page<DBObject> curPage = applyForTaskService.query(status, uid, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		model.put("uid", uid);
		return new ModelAndView("admin/task/applyForTasks", model);
	}

	/** 详情 */
	@RequestMapping("/applyForTask")
	public ModelAndView pack(Long _id) {
		DBObject dbo = applyForTaskService.findById(_id);
		return new ModelAndView("admin/task/applyForTask", "obj", dbo);
	}

	/** 更改状态 */
	@ResponseBody
	@RequestMapping("/applyForTask/status")
	public ReMsg status(Long _id, int status) {
		long adminId = applyForTaskService.getUid();
		if (adminId < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return applyForTaskService.status(_id, status, adminId);
	}

}
