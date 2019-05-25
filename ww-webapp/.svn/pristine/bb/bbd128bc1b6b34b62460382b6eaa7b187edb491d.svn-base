package com.zb.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zb.common.Constant.ActivityType;
import com.zb.common.Constant.ReCode;
import com.zb.core.web.ReMsg;
import com.zb.service.WinCoinRankService;
import com.zb.service.room.TexasService;

@Controller
@RequestMapping("/api")
public class TexasCtl extends BaseCtl {
	@Autowired
	TexasService texasService;

	@Autowired
	WinCoinRankService winCoinRankService;

	/** 个人战绩信息 */
	@ResponseBody
	@RequestMapping("/texas/getUserTexasLog")
	public ReMsg getGameHistoy(long uid) {
		return new ReMsg(ReCode.OK, texasService.getTexasLog(uid));
	}

	/** 赢金榜 */
	@ResponseBody
	@RequestMapping("/texas/queryWinLog")
	public ReMsg queryWinLog(int type) {
		return new ReMsg(ReCode.OK, winCoinRankService.queryWinLog(type, ActivityType.TEXAX_POKER_FATHER.getType()));
	}

}
