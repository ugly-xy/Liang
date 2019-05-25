package com.we.web.view.admin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.we.service.DataViewService;
import com.we.service.EnvService;
import com.we.service.userTask.TelegramService;

@Controller
@RequestMapping("/admin")
public class AdminSysCtl extends AdminBaseCtl {

	static final Logger log = LoggerFactory.getLogger(AdminSysCtl.class);

	
	@Autowired
	EnvService envService;
	
	
	@Autowired
	TelegramService telegramService;

	@Autowired
	DataViewService dataViewService;

	/** 转到telegram设置界面 */
	@RequestMapping("/telegrams")
	public ModelAndView userWallets(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("botName", TelegramService.BOT_NAME);
		model.put("botToken", TelegramService.BOT_TOKEN);
		return new ModelAndView("admin/sys/telegram", model);
	}

//	/** dataView */
//	@RequestMapping("/dataView")
//	public ModelAndView dataView(HttpServletRequest request) {
//		return new ModelAndView("admin/sys/dataView", "obj", dataViewService.findOne());
//	}

//	@ResponseBody
//	@RequestMapping(value = "/dataView", method = RequestMethod.POST)
//	public ReMsg dataView(Long id, Integer projectCnt, Long regCnt, Long sendCandyCnt, Long candyCnt)
//			throws ParseException {
//		return dataViewService.upSetDataView(id, projectCnt, regCnt, sendCandyCnt, candyCnt);
//	}

	@RequestMapping("/envs")
	public ModelAndView envs(String key, Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = envService.query(key, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		return new ModelAndView("admin/sys/envs", model);
	}

	@RequestMapping("/env")
	public ModelAndView env(String id, String value, Integer page, Integer size, HttpServletRequest request) {
		envService.save(id, value);
		Page<DBObject> curPage = envService.query(null, page, size);
		curPage.setUrl("/admin/envs");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		return new ModelAndView("admin/sys/envs", model);
	}

	@RequestMapping("/delenv")
	public ModelAndView delEnv(String id, Integer page, Integer size, HttpServletRequest request) {
		envService.del(id);
		Page<DBObject> curPage = envService.query(null, page, size);
		curPage.setUrl("/admin/envs");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		return new ModelAndView("admin/sys/envs", model);
	}

}
