package com.we.web.view.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.we.service.NoticeService;

@Controller
@RequestMapping("/admin")
public class AdminNoticeCtl extends AdminBaseCtl {

	@Autowired
	NoticeService noticeService;

	@RequestMapping("/notices")
	public ModelAndView notices(Integer status, Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = noticeService.query(status, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		return new ModelAndView("admin/notice/notices", model);
	}

	@ResponseBody
	@RequestMapping(value = "/notice", method = RequestMethod.POST)
	public ReMsg notice(Long id, String title, String content, int status, String prerogative) {
		long adminId = noticeService.getUid();
		if (adminId < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return new ReMsg(ReCode.OK, noticeService.upsetNotice(id, title, content, status, adminId));
	}

	@RequestMapping("/notice")
	public ModelAndView notice(Long id) {
		return new ModelAndView("admin/notice/notice", "obj", noticeService.findById(id));
	}
	
	//
	@RequestMapping("/notice/activitys")
	public ModelAndView noticeActivitys(Integer status, Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = noticeService.queryNoticeActivitys(status, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		return new ModelAndView("admin/notice/activitys", model);
	}
	
	@ResponseBody
	@RequestMapping(value = "/notice/activity", method = RequestMethod.POST)
	public ReMsg updateNoticeActivity(Long id, String title, String content, int status, String actLink,String actEndTime,
			Integer lkFlag,String pic,Double sort) {
		long adminId = noticeService.getUid();
		if (adminId < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return new ReMsg(ReCode.OK, noticeService.updateNoticeActivity(id, title, content, status, adminId,actLink,actEndTime,lkFlag,pic,sort));
	}
	
	@RequestMapping("/notice/activity")
	public ModelAndView noticeActivity(Long id) {
		Map<String, Object> model  =getImgMap();
		model.put("obj", noticeService.findNoticeActivityById(id));
		
		return new ModelAndView("admin/notice/activity", model);
	}

}
