package com.zb.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.DBObject;
import com.zb.common.Constant.ActivityType;
import com.zb.common.Constant.ReCode;
import com.zb.core.web.ReMsg;
import com.zb.service.WinCoinRankService;
import com.zb.service.room.SakuranService;

@Controller
@RequestMapping("/api")
public class SakuranCtl extends BaseCtl {
	@Autowired
	private SakuranService sakuranService;

	@Autowired
	WinCoinRankService winCoinRankService;

	// @ResponseBody
	// @RequestMapping("/sakuran/get8Flower")
	// public ReMsg getSlotHero(HttpServletRequest req) {
	// Map<String, List<Map<String, String>>> map = new HashMap<>();
	// String json =
	// redisTemplate.opsForValue().get(RK.nextGameInfo("SAKURAN"));
	// if (null == json) {
	// sakuranService.cacheNextInfo();
	// }
	// json = redisTemplate.opsForValue().get(RK.nextGameInfo("SAKURAN"));
	// @SuppressWarnings("unchecked")
	// List<Map<String, String>> temp = JSONUtil.jsonToArray(json);
	// if (null != temp && !temp.isEmpty()) {
	// map.put("list", temp);
	// return new ReMsg(ReCode.OK, map);
	// } else {
	// return new ReMsg(ReCode.FAIL, map);
	// }
	// }

	@ResponseBody
	@RequestMapping("/sakuran/getGameHistoy")
	public ReMsg getGameHistoy(HttpServletRequest req) {
		Map<String, List<DBObject>> map = new HashMap<>();
		List<DBObject> list = sakuranService.getGameHistory();
		if (null != list && !list.isEmpty()) {
			map.put("list", list);
			return new ReMsg(ReCode.OK, map);
		} else {
			return new ReMsg(ReCode.FAIL, map);
		}
	}

	@ResponseBody
	@RequestMapping("/sakuran/queryWinLog")
	public ReMsg queryWinLog(int type) {
		return new ReMsg(ReCode.OK, winCoinRankService.queryWinLog(type, ActivityType.SAKURAN.getType()));
	}

}
