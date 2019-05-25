package com.zb.web.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.DBObject;
import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.core.Page;
import com.zb.core.web.ReMsg;
import com.zb.service.AuthService;
import com.zb.service.BannerService;
import com.zb.service.EnvService;
import com.zb.service.third.OpenOrderService;
import com.zb.service.third.ThirdService;

@Controller
@RequestMapping("/third")
public class ThirdCtl extends BaseCtl {
	static final Logger log = LoggerFactory.getLogger(ThirdCtl.class);

	@Autowired
	AuthService authService;

	@Autowired
	EnvService envService;

	@Autowired
	ThirdService thirdService;

	@Autowired
	OpenOrderService openOrderService;

	@Autowired
	BannerService bannerService;

	@ResponseBody
	@RequestMapping("/oneBuy")
	public ReMsg oneBuy(String openId, String appKey, String product,
			String luckNo, String phase, HttpServletRequest req)
			throws IOException {
		return openOrderService.oneBuy(openId, appKey, product, luckNo, phase);
	}

	@ResponseBody
	@RequestMapping("/buyProduct")
	public ReMsg createOrder(String openId, String appKey, int coin,
			String outOrderNo, String product, String detail,
			HttpServletRequest req) throws IOException {
		return openOrderService.buyProduct(appKey, openId, coin, outOrderNo,
				product, detail);
	}

	private static final String GAME_DISPLAY = "game.center.display.";

	@ResponseBody
	@RequestMapping("/display")
	public ReMsg display(Integer via, Double ver) {
		Double d = envService.getDouble(GAME_DISPLAY + via);
		if (ver == null || ver >= d) {
			return new ReMsg(ReCode.OK, false);
		}
		return new ReMsg(ReCode.OK, true);
	}

	@ResponseBody
	@RequestMapping("/index")
	public ReMsg index(Integer page, Integer size) {
		Page<DBObject> p = thirdService.queryApp(0, 0L, Const.STATUS_UP, page,
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
	@RequestMapping("/appLogin")
	public ReMsg getGameUrl(String id, HttpServletRequest req) throws Exception {
		return thirdService.loginApp(id, req);
	}

	@ResponseBody
	@RequestMapping("/userCenter")
	public ReMsg getGameUserCenter(HttpServletRequest req) throws Exception {
		return thirdService.getAppUserCenter(req);
	}

	@ResponseBody
	@RequestMapping("/userinfo")
	public ReMsg getUserinfo(String accessToken, String appKey,
			HttpServletRequest req) throws Exception {
		return thirdService.getUserInfo(accessToken, appKey);
	}

}
