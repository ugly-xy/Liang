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
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.service.othergames.StarTrekService;

@Controller
@RequestMapping("/admin")
public class AdminSlaveCtl {

	static final Logger log = LoggerFactory.getLogger(AdminSlaveCtl.class);

	@Autowired
	StarTrekService starTrekService;

	@RequestMapping("/slave/slaveJobs")
	public ModelAndView slaveJobs(Integer state, Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = starTrekService.queryJobs(state, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("state", state);
		return new ModelAndView("admin/activitys/slaveJobs", model);
	}

	@RequestMapping("/slave/addJob")
	public ModelAndView addJob() {
		return new ModelAndView("admin/activitys/addJob");
	}

	@ResponseBody
	@RequestMapping(value = "/slave/addJob", method = RequestMethod.POST)
	public ReMsg addJob(String work) {
		return starTrekService.addJob(work);
	}

	@ResponseBody
	@RequestMapping(value = "/slave/slaveReview", method = RequestMethod.POST)
	public ReMsg slaveReview(Long id) {
		return starTrekService.slaveReview(id);
	}

	@ResponseBody
	@RequestMapping(value = "/slave/slaveReject", method = RequestMethod.POST)
	public ReMsg slaveReject(Long id) {
		return starTrekService.slaveReject(id);
	}

	@RequestMapping("/slave/cowSlave")
	public ModelAndView cowSlave(Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = starTrekService.queryCowSlave( page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		return new ModelAndView("admin/st/cowSlave", model);
	}
}
