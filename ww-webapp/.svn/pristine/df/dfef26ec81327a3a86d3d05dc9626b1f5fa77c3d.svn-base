package com.zb.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.DBObject;
import com.zb.common.Constant.ReCode;
import com.zb.common.redis.RK;
import com.zb.common.utils.JSONUtil;
import com.zb.core.web.ReMsg;
import com.zb.service.room.SlotMachinesService;

@Controller
@RequestMapping("/api")
public class SlotMachinesCtl extends BaseCtl {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private SlotMachinesService slotMachinesService;

	@ResponseBody
	@RequestMapping("/slotMachines/getSlotInfo")
	public ReMsg getSlotInfo(HttpServletRequest req) {
		Map<String, List<DBObject>> map = new HashMap<>();
		List<DBObject> list = slotMachinesService.getSlotInfo4Game();
		if (null != list && !list.isEmpty()) {
			map.put("list", list);
			return new ReMsg(ReCode.OK, map);
		} else {
			return new ReMsg(ReCode.FAIL, map);
		}
	}

	@ResponseBody
	@RequestMapping("/slotMachines/getSlotHero")
	public ReMsg getSlotHero(HttpServletRequest req) {
		Map<String, List<Map<String, String>>> map = new HashMap<>();
		String json = redisTemplate.opsForValue().get(RK.nextGameInfo("SLOTMACHINES"));
		if (null == json) {
			slotMachinesService.cacheNextInfo();
		}
		json = redisTemplate.opsForValue().get(RK.nextGameInfo("SLOTMACHINES"));
		@SuppressWarnings("unchecked")
		List<Map<String, String>> temp = JSONUtil.jsonToArray(json);
		if (null != temp && !temp.isEmpty()) {
			map.put("list", temp);
			return new ReMsg(ReCode.OK, map);
		} else {
			return new ReMsg(ReCode.FAIL, map);
		}
	}

	@ResponseBody
	@RequestMapping("/slotMachines/getGameHistoy")
	public ReMsg getGameHistoy(HttpServletRequest req) {
		Map<String, List<DBObject>> map = new HashMap<>();
		List<DBObject> list = slotMachinesService.getGameHistory();
		if (null != list && !list.isEmpty()) {
			map.put("list", list);
			return new ReMsg(ReCode.OK, map);
		} else {
			return new ReMsg(ReCode.FAIL, map);
		}
	}

}
