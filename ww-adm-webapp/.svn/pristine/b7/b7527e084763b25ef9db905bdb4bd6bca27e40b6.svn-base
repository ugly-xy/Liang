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
import com.zb.common.Constant.ReCode;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.service.room.UndercoverService;

@Controller
@RequestMapping("/admin")
public class AdminUndercoverCtl {

	static final Logger log = LoggerFactory.getLogger(AdminUndercoverCtl.class);

	@Autowired
	UndercoverService undercoverService;

	@RequestMapping("/undercover/words")
	public ModelAndView games(String key,Integer status, Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = undercoverService.query(key,status, page, size);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		model.put("key", key);
		return new ModelAndView("admin/activitys/words", model);
	}

	@RequestMapping("/undercover/word")
	public ModelAndView game(Long id) {
		ModelAndView model = new ModelAndView("admin/activitys/word");
		if (id == null) {
			return model;
		}
		DBObject dbo = undercoverService.findWordById(id);
		model.addObject("obj", dbo);
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/undercover/word", method = RequestMethod.POST)
	public ReMsg saveGame(String words) {
		return undercoverService.adds(words);
	}
	
	@ResponseBody
	@RequestMapping(value ="/undercover/word/valid")
	public ReMsg drawLogValid(Long id, int status) {
		undercoverService.undercoverValid(id, status);
		return new ReMsg(ReCode.OK);
	}

	@ResponseBody
	@RequestMapping(value ="/undercover/word/del")
	public ReMsg deldrawLog(Long id) {
		System.out.println(id);
		undercoverService.delUndercoverById(id);
		return new ReMsg(ReCode.OK);
	}


	@RequestMapping("/undercover/speakLogs")
	public ModelAndView speakLogs(String word, Integer status, Integer page, Integer size, HttpServletRequest request) {
		size=50;
		Page<DBObject> curPage = undercoverService.queryUndercoverSpeakLogs(word, status, page, size);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("word", word);
		model.put("status", status);
		return new ModelAndView("admin/activitys/undercoverSpeakLogs", model);
	}

	@RequestMapping(value = "/undercover/speakLog")
	public ModelAndView speakLog(String word) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (word != null) {
			model.put("word", word);
			model.put("speaks", undercoverService.findUndercoverSpeakLogs(word, 0, 0, 20));
		}
		return new ModelAndView("admin/activitys/undercoverSpeakLog", model);
	}

	@ResponseBody
	@RequestMapping(value = "/undercover/speakLog/del")
	public ReMsg dedrawLog(long id) {
		undercoverService.delUndercoverSpeakLogById(id);
		return new ReMsg(ReCode.OK);
	}
	
	@ResponseBody
	@RequestMapping(value = "/undercover/speakLog/valid")
	public ReMsg dedrawLog(long id,int status) {
		undercoverService.validUndercoverSpeakLogById(id, status);
		return new ReMsg(ReCode.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/undercover/speakLog", method = RequestMethod.POST)
	public ReMsg deldrawLog(String word, String speak) {
		undercoverService.saveUndercoverSpeakLogs(speak, word);
		return new ReMsg(ReCode.OK);
	}
	
	//复选操作
	@ResponseBody
	@RequestMapping(value = "/undercover/speakLog/checkbox", method = RequestMethod.POST)
	public ReMsg checkbox(String ids, int status) {
		return undercoverService.checkboxSift(ids, status);
	}
}
