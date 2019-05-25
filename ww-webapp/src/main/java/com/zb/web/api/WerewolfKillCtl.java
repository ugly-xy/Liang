package com.zb.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zb.common.Constant.ReCode;
import com.zb.core.web.ReMsg;
import com.zb.service.room.WerewolfKillService;

@Controller
@RequestMapping(value = "/werewolf")
public class WerewolfKillCtl extends BaseCtl {

	@Autowired
	WerewolfKillService werewolfKillService;

	@RequestMapping("/queryWinRate")
	public @ResponseBody ReMsg queryWinRate(int userCount, long id) {
		return werewolfKillService.queryWinRate(id, userCount);
	}

	@RequestMapping("/queryWholeWinRate")
	public @ResponseBody ReMsg queryWholeWinRate(long id) {
		return werewolfKillService.queryWholeWinRate(id);
	}

	@RequestMapping("/queryWinRateDetail")
	public @ResponseBody ReMsg queryWinRateDetail(int userCount, long id) {
		return werewolfKillService.queryWinRateDetail(id, userCount);
	}

	@RequestMapping("/synchronizeDate")
	public @ResponseBody ReMsg synchronizeDate(int roomId) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		return werewolfKillService.synchronizeDate(uid, roomId);
	}

	@RequestMapping("/gameRankList")
	public @ResponseBody ReMsg gameRankList(Integer actorCount, Integer rateType, Integer page, Integer size) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		return new ReMsg(ReCode.OK, werewolfKillService.gameRankList(uid, actorCount, rateType, page, size));
	}

	@RequestMapping("/queryRankList")
	public @ResponseBody ReMsg queryRankList(Integer actorCount, Integer rateType, Integer page, Integer size) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		return new ReMsg(ReCode.OK, werewolfKillService.queryRankList(actorCount, rateType, page, size));
	}

}
