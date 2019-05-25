package com.zb.web.view.admin;

import java.lang.reflect.InvocationTargetException;
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
import com.zb.common.Constant.ReCode;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.service.room.DrawSomethingService;
import com.zb.service.room.PunishService;
import com.zb.service.room.UndercoverService;
import com.zb.service.room.UserActivityService;

@Controller
@RequestMapping("/admin")
public class AdminDrawSomethingCtl {

	static final Logger log = LoggerFactory.getLogger(AdminDrawSomethingCtl.class);

	@Autowired
	UndercoverService undercoverService;

	@Autowired
	PunishService punishService;

	@Autowired
	DrawSomethingService drawSomethingService;

	@Autowired
	UserActivityService userActivityService;

	@RequestMapping("/guess/words")
	public ModelAndView guesswords(Integer status, String id, Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = drawSomethingService.query(status, id, page, size);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		model.put("id", id);
		return new ModelAndView("admin/activitys/guesswords", model);
	}

	@RequestMapping("/guess/word")
	public ModelAndView guessword(String id) {
		ModelAndView model = new ModelAndView("admin/activitys/guessword");
		if (id == null) {
			return model;
		}
		DBObject dbo = drawSomethingService.findWordById(id);
		model.addObject("obj", dbo);
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/guess/word", method = RequestMethod.POST)
	public ReMsg guessword(String words, String tip, String provider,Long uid) {
		return drawSomethingService.adds(words, tip, provider, uid);
	}

	@ResponseBody
	@RequestMapping(value = "/guess/word/del")
	public ReMsg delGuessword(String word) {
		return drawSomethingService.delGressword(word);
	}

	@ResponseBody
	@RequestMapping(value = "/guess/word/valid")
	public ReMsg validGuessword(String word, Integer status) {
		return drawSomethingService.validGressword(word, status);
	}

	@RequestMapping("/drawSomething/drawLogs")
	public ModelAndView drawLogs(Long uid, String word, Integer status, Integer page, Integer size,
			HttpServletRequest request) {
		Page<DBObject> curPage = drawSomethingService.queryDrawLogs(uid, word, status, page, size);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("word", word);
		model.put("uid", uid);
		model.put("status", status);
		return new ModelAndView("admin/activitys/drawLogs", model);
	}

	@ResponseBody
	@RequestMapping(value = "/drawSomething/drawLog")
	public DBObject drawLog(String id) {
		return drawSomethingService.findDrawDataById(id);
	}

	@RequestMapping(value = "/drawSomething/drawGuessLogs")
	public ModelAndView drawGuessLogs(String word, Integer status, Integer page, Integer size,
			HttpServletRequest request) {
		size = 50;
		Page<DBObject> curPage = drawSomethingService.queryDrawGuessLogs(word, status, page, size);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("status", status);
		model.put("page", curPage);
		model.put("word", word);
		return new ModelAndView("admin/activitys/drawGuessLogs", model);
	}

	@ResponseBody
	@RequestMapping(value = "/drawSomething/drawGuessLog")
	public DBObject drawGuessLog(String id) {
		return drawSomethingService.findDrawLogById(id);
	}

	@ResponseBody
	@RequestMapping(value = "/drawSomething/drawGuessLog/del")
	public ReMsg delDrawGuessLog(Long id) {
		drawSomethingService.delDrawGuessLogById(id);
		return new ReMsg(ReCode.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/drawSomething/drawGuessLog/valid")
	public ReMsg drawGuessLogValid(Long id, int status) {
		drawSomethingService.drawGuessLogValid(id, status);
		return new ReMsg(ReCode.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/drawSomething/drawGuessLog/checkbox", method = RequestMethod.POST)
	public ReMsg guessLogCheckbox(String ids, int status) {
		return drawSomethingService.guessLogSift(ids, status);
	}

	@ResponseBody // 绘画通过
	@RequestMapping(value = "/drawSomething/drawLog/valid")
	public ReMsg drawLogValid(String id) throws Exception {
		drawSomethingService.drawLogValidOK(id);
		return new ReMsg(ReCode.OK);
	}

	@ResponseBody // 删除绘画
	@RequestMapping(value = "/drawSomething/drawLog/del")
	public ReMsg deldrawLog(String id) {
		drawSomethingService.delDrawLogById(id);
		return new ReMsg(ReCode.OK);
	}

	@ResponseBody // 批量删除
	@RequestMapping(value = "/drawSomething/drawLogs/bitchRemove", method = RequestMethod.POST)
	public ReMsg bitchRemove(String ids) throws Exception {
		return drawSomethingService.bitchRemove(ids);
	}

	@ResponseBody // 批量通过
	@RequestMapping(value = "/drawSomething/drawLogs/bitchValid", method = RequestMethod.POST)
	public ReMsg bitchValid(String ids) throws Exception {
		return drawSomethingService.bitchValidOK(ids);
	}
}
