package com.zb.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zb.common.Constant.ActivityType;
import com.zb.common.Constant.ReCode;
import com.zb.core.web.ReMsg;
import com.zb.service.WinCoinRankService;
import com.zb.service.room.CowService;

@Controller
@RequestMapping("/api")
public class CowCtl extends BaseCtl {
	@Autowired
	CowService cowService;

	@Autowired
	WinCoinRankService winCoinRankService;

	/** 个人战绩信息 */
	@ResponseBody
	@RequestMapping("/cow/getUserCowLog")
	public ReMsg getGameHistoy(long uid) {
		return new ReMsg(ReCode.OK, cowService.getCowLog(uid));
	}

	/** 赢金榜 */
	@ResponseBody
	@RequestMapping("/cow/queryWinLog")
	public ReMsg queryWinLog(int type) {
		return new ReMsg(ReCode.OK, winCoinRankService.queryWinLog(type, ActivityType.COW_FATHER.getType()));
	}

}
