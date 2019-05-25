package com.zb.web.view.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.service.EnvService;
import com.zb.service.KeywordService;
import com.zb.service.server.ServerMngService;

@Controller
@RequestMapping("/admin")
public class AdminSysCtl {

	static final Logger log = LoggerFactory.getLogger(AdminSysCtl.class);

	@Autowired
	EnvService envService;

	@Autowired
	KeywordService keywordService;

	@Autowired
	ServerMngService serverMngService;

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

	@RequestMapping("/keywords")
	public ModelAndView keywords(Integer type, Integer page, HttpServletRequest request) {
		if (type == null) {
			type = 2;
		}
		Page<DBObject> curPage = keywordService.query(type, page, 200);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("type", type);
		return new ModelAndView("admin/sys/keywords", model);
	}

	@RequestMapping("/keyword")
	public ModelAndView keyword(Integer type, String word, Integer page, HttpServletRequest request) {
		keywordService.save(type, word);
		Page<DBObject> curPage = keywordService.query(type, page, 200);
		curPage.setUrl("/admin/keywords");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("type", type);
		return new ModelAndView("admin/sys/keywords", model);
	}

	@ResponseBody
	@RequestMapping("/delkeyword")
	public ReMsg delKeyword(String word, HttpServletRequest request) {
		keywordService.del(word);
		return new ReMsg(ReCode.OK);
	}

	@RequestMapping("/servers")
	public ModelAndView socketServers(String type, Integer status, Integer page, Integer size,
			HttpServletRequest request) {
		Page<DBObject> curPage = serverMngService.query(type, null, status, page, size);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("type", type);
		model.put("status", status);
		return new ModelAndView("admin/sys/servers", model);
	}

}
