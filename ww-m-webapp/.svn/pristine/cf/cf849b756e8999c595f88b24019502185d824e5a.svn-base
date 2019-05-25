package com.zb.web.view.m;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.DBObject;
import com.zb.common.http.CookieUtil;
import com.zb.common.mongo.DboUtil;
import com.zb.common.utils.DateUtil;
import com.zb.core.web.ReMsg;
import com.zb.models.VipMap;
import com.zb.models.finance.CoinLog;
import com.zb.models.goods.BaseGoods;
import com.zb.service.FlowerRedeemService;
import com.zb.service.RankListService;
import com.zb.service.UserService;
import com.zb.service.jobs.RankListJob;
import com.zb.service.pay.CoinService;

@Controller
public class ActivityCtl {

	@Autowired
	CoinService coinService;
	@Autowired
	FlowerRedeemService redeemService;
	@Autowired
	RankListService rankListService;
	@Autowired
	UserService userService;
	//
	// @Autowired
	// BannerService bannerService;
	//
	// @Autowired
	// ResService resService;

	long start = (DateUtil.convertDate(2017, 1, 27, 0, 0, 0) / 1000) * 1000;
	private static String LINKEDKEY = "5436806990c729f8b520eb2817694215";
	private static String TYPE = "live";

	@RequestMapping("/activity/first")
	public ModelAndView article(Integer type, Integer len) {
		if (type == null || type < 130) {
			type = 131;
		}
		if (len == null) {
			len = 1;
		}
		Map<String, Object> rs = new HashMap<String, Object>();
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		if (System.currentTimeMillis() > start) {
			int i = 0;
			long startDate = 0L;
			long endDate = 0L;
			if (len == 1) {
				startDate = DateUtil.getTodayZeroTime();
				endDate = startDate + DateUtil.DAY;
			} else if (len == 2) {
				endDate = DateUtil.getTodayZeroTime();
				startDate = endDate - DateUtil.DAY;
			} else if (len == 3) {
				endDate = DateUtil.getTodayZeroTime();
				startDate = endDate - DateUtil.DAY * 7;
			} else {
				startDate = DateUtil.getWeekZeroTime();
				endDate = startDate + DateUtil.DAY * 7;
			}
			list = coinService.stCoinLog(CoinLog.IN, type, startDate, endDate, 20);
			if (len < 3) {
				for (HashMap<String, Object> m : list) {
					m.put("addM", prize[0][i]);
					i++;
					m.put("id", "" + i);
				}
			} else {
				for (HashMap<String, Object> m : list) {
					m.put("addM", prize[1][i]);
					i++;
					m.put("id", "" + i);
				}
			}
		}
		rs.put("list", list);
		rs.put("type", type);
		rs.put("len", len);
		return new ModelAndView("html/activity/first", rs);
	}

	long startT = (DateUtil.convertDate(2017, 5, 18, 0, 0, 0) / 1000) * 1000;

	@RequestMapping(value = "/activity/drawTop")
	public ModelAndView drawTop(Integer type, Integer len) {
		type = 131;
		if (len == null) {
			len = 1;
		}
		Map<String, Object> rs = new HashMap<String, Object>();
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		int i = 0;
		long startDate = 0L;
		long endDate = 0L;
		if (len == 1) {
			startDate = DateUtil.getTodayZeroTime();
			endDate = startDate + DateUtil.DAY;
		} else {
			endDate = DateUtil.getTodayZeroTime();
			startDate = endDate - DateUtil.DAY;
		}

		list = coinService.stCoinLog(CoinLog.IN, type, startDate, endDate, 10);
		for (HashMap<String, Object> m : list) {
			m.put("addM", 2000 - (i * 200));
			i++;
			m.put("id", i);
		}
		rs.put("list", list);
		rs.put("type", type);
		rs.put("len", len);
		return new ModelAndView("html/activity/drawTop", rs);
	}

	public static final int BASE_W = 10000;
	public static final int BASE_KW = 10000000;

	/** 情人节累计收花html */
	@RequestMapping(value = "/activity/valentine")
	public ModelAndView valentineFlower() {
		Map<String, Object> rs = new HashMap<String, Object>();
		List<DBObject> list = new ArrayList<DBObject>();
		int day = RankListService.getDay(1);
		if (day >= RankListJob.DAY_0214) {// 大于等于20180214才显示数据
			list = rankListService.getValentineFlowerTop(RankListService.CHARM, 10);
			int i = 0;
			for (DBObject dbo : list) {
				i++;
				dbo.put("top", i);
				dbo.put("lv", userService.getLevel(DboUtil.getInt(dbo, "point")));
				dbo.put("vipLv", VipMap.getLevel(DboUtil.getInt(dbo, "vip")));
				if (DboUtil.getInt(dbo, "count") >= BASE_W) {
					if (DboUtil.getInt(dbo, "count") >= BASE_KW) {
						dbo.put("count", format2Str(DboUtil.getInt(dbo, "count"), BASE_KW) + "kw");
					} else {
						dbo.put("count", format2Str(DboUtil.getInt(dbo, "count"), BASE_W) + "w");
					}
				}
				if (DboUtil.getString(dbo, "nickname").length() > 8) {
					dbo.put("nickname", DboUtil.getString(dbo, "nickname").substring(0, 8));
				}
			}
		}
		rs.put("list", list);
		return new ModelAndView("html/activity/valentine", rs);
	}

	@RequestMapping("/activity/inviteFriends")
	public ModelAndView drawTop(String type, String nickname, String avatar, String gameName, Long roomId) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("nickname", nickname);
		rs.put("avatar", avatar);
		rs.put("gameName", gameName);
		rs.put("roomId", roomId);
		rs.put("gameType", type);
		rs.put("key", LINKEDKEY);
		rs.put("type", TYPE);
		rs.put("date", DateUtil.dateFormatyyyyMMdd(new Date()));
		return new ModelAndView("html/activity/inviteFriends", rs);
	}

	// 界面
	@RequestMapping(value = "/activity/flower")
	public ModelAndView flower(HttpServletRequest req, HttpServletResponse res) {
		return new ModelAndView("html/activity/flower");
	}

	// 查询额度
	@ResponseBody
	@RequestMapping(value = "/activity/flowerCount", method = RequestMethod.POST)
	public String flowerCount(HttpServletRequest req) {
		return redeemService.queryRedeem(req) + "";
	}

	// 兑换礼品
	@ResponseBody
	@RequestMapping(value = "/activity/flower/redeem", method = RequestMethod.POST)
	public ReMsg redeem(Integer type, HttpServletRequest req) {
		return redeemService.getRedeemKey(type, req);
	}

	/** 领取金币界面html */
	@RequestMapping(value = "/activity/yearCoin")
	public ModelAndView yearCoinView(HttpServletRequest req, HttpServletResponse res) {
		return new ModelAndView("html/activity/yearCoin");
	}

	/** 获取昨日消费金币数目 */
	@ResponseBody
	@RequestMapping(value = "/activity/yearCoinCnt", method = RequestMethod.POST)
	public long yearCoinCnt(HttpServletRequest req) {
		return coinService.getYearCoin(req);
	}

	/** 是否已经领取昨天的红包 */
	@ResponseBody
	@RequestMapping(value = "/activity/isRecvYearCoin", method = RequestMethod.POST)
	public boolean isRecvYearCoin(HttpServletRequest req) {
		return coinService.isRecvYearCoin(req);
	}

	/** 领取金币红包 */
	@ResponseBody
	@RequestMapping(value = "/activity/recvYearCoin", method = RequestMethod.POST)
	public ReMsg recvYearCoin(Integer type, HttpServletRequest req) {
		return coinService.recvYearCoin(req);
	}

	static String[][] prize = {
			{ "25元", "20元", "15元", "10元", "10元", "10元", "5元", "5元", "5元", "5元", "2000币", "1800币", "1600币", "1400币",
					"1200币", "1000币", "800币", "600币", "400币", "200币" },
			{ "120元", "80元", "50元", "20元", "20元", "20元", "10元", "10元", "10元", "10元", "5000币", "4500币", "4000币", "3500币",
					"3000币", "2500币", "2000币", "1500币", "1000币", "500币" } };

	String format2Str(int cnt, int base) {
		return new DecimalFormat("0.00").format((double) cnt / base);
	}
}
