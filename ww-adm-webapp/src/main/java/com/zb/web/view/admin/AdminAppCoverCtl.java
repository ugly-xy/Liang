package com.zb.web.view.admin;

import java.util.Calendar;
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
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.utils.DateUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.service.AppCoverService;

@Controller
@RequestMapping("/admin")
public class AdminAppCoverCtl {
	static final Logger log = LoggerFactory.getLogger(AdminAppCoverCtl.class);

	@Autowired
	AppCoverService appCoverService;

	@RequestMapping("/appCovers")
	public ModelAndView appCovers(Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = appCoverService.query(page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(DateUtil.getWeekZeroTime());
		model.put("nowTime", c.get(Calendar.YEAR) * 100 + c.get(Calendar.WEEK_OF_YEAR));
		c.setTimeInMillis(DateUtil.getWeekZeroTime() + Const.DAY * 7);
		model.put("nextTime", c.get(Calendar.YEAR) * 100 + c.get(Calendar.WEEK_OF_YEAR));
		return new ModelAndView("admin/appCover/appCovers", model);
	}

	@RequestMapping("/appCover")
	public ModelAndView appCover(Long id) {
		DBObject dbo = appCoverService.findById(id);
		return new ModelAndView("admin/appCover/appCover", "obj", dbo);
	}

	@ResponseBody
	@RequestMapping(value = "/appCover", method = RequestMethod.POST)
	public ReMsg update(long id, Long uid, String nickname, String url) {
		if (StringUtils.isBlank(url)) {
			return new ReMsg(ReCode.FAIL);
		}
		long adminId = appCoverService.getUid();
		if (adminId < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return appCoverService.adminUpSet(id, uid, nickname, url, adminId);
	}

	@ResponseBody
	@RequestMapping("/appCover/del")
	public ReMsg appCoverDel(Long id) {
		return appCoverService.del(id);
	}
}
