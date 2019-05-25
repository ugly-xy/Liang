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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBObject;
import com.zb.common.utils.DateUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.models.SlotMachinesModelPool;
import com.zb.service.usertask.OneTaskService;
import com.zb.service.usertask.TaskLogService;
import com.zb.service.usertask.TaskModelService;

@Controller
@RequestMapping("/admin")
public class AdminTaskCtl {

	static final Logger log = LoggerFactory.getLogger(AdminTaskCtl.class);

	@Autowired
	TaskLogService taskLogService;

	@Autowired
	TaskModelService taskModelService;;
	@Autowired
	OneTaskService oneTaskService;

	@RequestMapping("/taskLogs")
	public ModelAndView taskLogs(Long uid, Integer type, Integer page, Integer size, String st, String et,
			HttpServletRequest request) {
		int start = 0;
		int end = 0;
		if (StringUtils.isNotBlank(st)) {
			start = Integer.parseInt(st.replace("-", ""));
		}
		if (StringUtils.isNotBlank(et)) {
			end = Integer.parseInt(et.replace("-", ""));
		}
		Page<DBObject> curPage = taskLogService.findTaskLog(uid, type, page, size, start, end);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("st", st);
		model.put("et", et);
		model.put("type", type);
		model.put("uid", uid);
		return new ModelAndView("admin/task/taskLogs", model);
	}

	@RequestMapping("/taskLogs/exhibition/{id}")
	public ModelAndView taskLogs(@PathVariable String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("task", taskLogService.findTaskLog(id));
		return new ModelAndView("admin/task/taskLogsDetail", model);
	}

	@RequestMapping("/taskModels")
	public ModelAndView taskModels(Integer tmType, Integer status, Integer page, Integer size,
			HttpServletRequest request) {
		Page<DBObject> curPage = taskModelService.query(tmType, status, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("tmType", tmType);
		model.put("status", status);
		return new ModelAndView("admin/task/taskModels", model);
	}

	@RequestMapping("/taskModel")
	public ModelAndView taskModel(Long id) {
		DBObject dbo = taskModelService.findById(id);
		return new ModelAndView("admin/task/taskModel", "obj", dbo);
	}

	@ResponseBody
	@RequestMapping(value = "/taskModel", method = RequestMethod.POST)
	public ReMsg update(Long id, String tmPic, String tmTitle, String tmName, String tmReward, Integer tmType,
			Integer tmPlan, Integer tmEndCondition, Integer tmStatus, Integer status, String op, Integer opid,
			Integer sort, String rewards) {
		return taskModelService.upSet(id, tmPic, tmTitle, tmName, tmReward, tmType, tmPlan, tmEndCondition, tmStatus,
				status, rewards, op, opid, sort);
	}

	@ResponseBody
	@RequestMapping("/taskModeModel/del")
	public ReMsg goods(Long id) {
		return taskModelService.del(id);
	}

	@RequestMapping("/oneTasks")
	public ModelAndView coinGivens(Double version, Integer status, Integer type, Integer page, Integer size,
			HttpServletRequest request) throws ParseException {
		Page<DBObject> curPage = oneTaskService.findCoinGiven(status, version, type, page, size);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("version", version);
		model.put("status", status);
		return new ModelAndView("admin/task/oneTasks", model);
	}

	@RequestMapping("/oneTask")
	public ModelAndView coinGiven(Long id) {
		DBObject dbo = oneTaskService.findById(id);
		return new ModelAndView("admin/task/oneTask", "obj", dbo);
	}

	@ResponseBody
	@RequestMapping(value = "/oneTask", method = RequestMethod.POST)
	public ReMsg savecoinGiven(Long id, String name, String st, String et, double version, Integer coin, Integer point,
			String prop, int status, int type) throws ParseException {
		long startTime = 0;
		long endTime = 0;
		if (StringUtils.isNotBlank(st)) {
			startTime = DateUtil.getZeroTime(DateUtil.convertDate(st, "yyyy-MM-dd"));
		}
		if (StringUtils.isNotBlank(et)) {
			endTime = DateUtil.getZeroTime(DateUtil.convertDate(et, "yyyy-MM-dd")) + DateUtil.DAY;
		}
		coin = null == coin ? 0 : coin;
		point = null == point ? 0 : point;
		return oneTaskService.upsert(id, type, name, startTime, endTime, version, coin, point, prop, status);
	}
}
