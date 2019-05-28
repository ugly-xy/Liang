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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBObject;
import com.zb.common.utils.DateUtil;
import com.zb.core.Page;
import com.zb.service.LoginLogService;
@Controller
@RequestMapping("/admin")
public class AdminLoginLogCtl {
	
	static final Logger log = LoggerFactory.getLogger(AdminLoginLogCtl.class);
	
	@Autowired
	LoginLogService loginLogService;
	
	@RequestMapping("/loginLogs")
	public ModelAndView banner(Long userId, Integer page,
			Integer size, String st, String et,Double version, HttpServletRequest request) {
		//List<DBObject> list = AuthService.findByLoginLogId(userId, page, size);
		Integer start=null;
		Integer end=null;
		if(StringUtils.isNotBlank(st)){
			try {
				start=DateUtil.toDayyyyMMdd(DateUtil.convertDate(st, "yyyy-MM-dd"));
			} catch (ParseException e) {
			}
		}
		if(StringUtils.isNotBlank(st)){
			try {
				end = DateUtil.toDayyyyMMdd(DateUtil.convertDate(et, "yyyy-MM-dd"));
			} catch (ParseException e) {
			}
		}
		Page<DBObject> curPage = loginLogService.queryLoginLog(start,end,version,userId, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("st", st);
		model.put("et", et);
		model.put("userId", userId);
		model.put("version", version);
		//model.put("list", list);
		return new ModelAndView("admin/login/loginLogs", model);
	}
}
