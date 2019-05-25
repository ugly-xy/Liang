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
import com.zb.common.Constant.Const;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.service.FeedbackService;

@Controller
@RequestMapping("/admin")
public class AdminFeedbackCtl {

	static final Logger log = LoggerFactory.getLogger(AdminFeedbackCtl.class);

	@Autowired
	FeedbackService feedbackService;

	// tools
	@RequestMapping("/feedbacks")
	public ModelAndView feedbacks(Integer status, Integer page, Integer size,
			HttpServletRequest request) {
		Page<DBObject> curPage = feedbackService.query(status, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		return new ModelAndView("admin/sys/feedbacks", model);
	}

	@RequestMapping("/feedback")
	public ModelAndView feedback(Long id) {

		DBObject dbo = feedbackService.findById(id);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("obj", dbo);
		return new ModelAndView("admin/sys/feedback", m);
	}

	@ResponseBody
	@RequestMapping(value = "/feedback", method = RequestMethod.POST)
	public ReMsg update(Long id,String sysReply) {
		return feedbackService.update(id, sysReply,Const.STATUS_OK);
	}

}
