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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.utils.RandomUtil;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.service.GameService;

@Controller
@RequestMapping("/admin")
public class AdminGameCtl {

	static final Logger log = LoggerFactory.getLogger(AdminGameCtl.class);

	@Autowired
	GameService gameService;

	@RequestMapping("/games")
	public ModelAndView games(Integer type, Integer status, Long provider,
			Integer page, Integer size, HttpServletRequest request) {
		Page<DBObject> curPage = gameService.queryGame(type, provider, status,
				page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		model.put("type", type);
		model.put("provider", provider);
		model.put("providers", gameService.findGameCP(Const.STATUS_UP, 0, 100));
		return new ModelAndView("admin/games/games", model);
	}

	@RequestMapping("/game")
	public ModelAndView game(Long id) {
		ModelAndView model = new ModelAndView("admin/games/game");
		model.addObject("providers",
				gameService.findGameCP(Const.STATUS_UP, 0, 100));
		if (id == null) {
			return model;
		}
		DBObject dbo = gameService.findGameById(id);
		model.addObject("obj", dbo);
		
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/game", method = RequestMethod.POST)
	public ReMsg saveGame(Long id, Integer type, Long provider, Integer status,
			String title, Integer sort, Integer ch, Integer childCh,
			String url, String pic, String description, Integer count) {
		return gameService.upsert(id, type, provider, status, title, sort, ch,
				childCh, url, pic, description, count);
	}

	@RequestMapping("/gameCPs")
	public ModelAndView gameCPs(Integer status, Integer page, Integer size,
			HttpServletRequest request) {
		Page<DBObject> curPage = gameService.queryGameCP(status, page, size);
		curPage.setUrl(request.getRequestURI());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("page", curPage);
		model.put("status", status);
		return new ModelAndView("admin/games/gameCPs", model);
	}

	@RequestMapping("/gameCP")
	public ModelAndView gameCP(Long id) {
		ModelAndView model = new ModelAndView("admin/games/gameCP");
		if (id == null) {
			return model;
		}
		DBObject dbo = gameService.findGameCpById(id);
		model.addObject("obj", dbo);
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/gameCP", method = RequestMethod.POST)
	public ReMsg saveGameCP(Long id, Integer status, Integer sort, String url,
			String pic, String description, String name, String signKey) {
		if (StringUtils.isBlank(signKey)) {
			signKey = RandomUtil.randomNoCase(12);
		}
		return gameService.upsertCP(id, status, sort, url, pic, description,
				name, signKey);
	}

}
