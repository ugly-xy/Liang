package com.zb.web.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.service.BannerService;
import com.zb.service.EnvService;
import com.zb.service.GameService;

@SuppressWarnings("deprecation")
@Controller
@RequestMapping("/game")
public class GameCtl extends BaseCtl {

	@Autowired
	EnvService envService;

	@Autowired
	GameService gameService;

	@Autowired
	BannerService bannerService;

	private static final String GAME_DISPLAY = "game.center.display.";

	@ResponseBody
	@RequestMapping("/display")
	public ReMsg display(Integer via, Double ver) {
//		Double d = envService.getDouble(GAME_DISPLAY + via);
//		if (ver == null || ver >= d) {
//			return new ReMsg(ReCode.OK, false);
//		}
		return new ReMsg(ReCode.OK, false);
	}

	@ResponseBody
	@RequestMapping("/index")
	public ReMsg index(Integer page, Integer size) {
		Page<DBObject> p = gameService.queryGame(0, 0L, Const.STATUS_UP, page,
				size);
		return new ReMsg(ReCode.OK, p);
	}

	@ResponseBody
	@RequestMapping("/banners")
	public ReMsg banner(Integer via) {
		List<DBObject> p = bannerService.findBanner(BannerService.TYPES[2],
				Const.STATUS_UP, via, 0, 5);
		return new ReMsg(ReCode.OK, p);
	}

	@ResponseBody
	@RequestMapping("/loginGame")
	public ReMsg getGameUrl(Long id, HttpServletRequest req) throws Exception {
		return gameService.loginGame(id, req);
	}

	@ResponseBody
	@RequestMapping("/userCenter")
	public ReMsg getGameUserCenter(HttpServletRequest req) throws Exception {
		return gameService.getGameUserCenter(req);
	}

	@ResponseBody
	@RequestMapping("/userinfo")
	public ReMsg getUserinfo(String accessToken, String appKey,
			HttpServletRequest req) throws Exception {
		return gameService.getUserInfo(accessToken, appKey);
	}

}
