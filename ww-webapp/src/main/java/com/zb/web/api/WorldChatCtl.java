package com.zb.web.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zb.common.Constant.ReCode;
import com.zb.core.web.ReMsg;
import com.zb.service.room.DiceService;
import com.zb.service.room.WorldChatService;

@Controller
@RequestMapping("/api")
public class WorldChatCtl extends BaseCtl {

	@Autowired
	WorldChatService worldChatService;
	@Autowired
	DiceService diceService;

//	/** 拉取聊天历史 */
//	@RequestMapping("worldChat/pullHistory")
//	public @ResponseBody ReMsg pullHistory(Integer type) {
//		boolean logType = false;
//		if (type != null && type == 4) {
//			logType = true;
//		}
//		return new ReMsg(ReCode.OK, worldChatService.pullHistory(logType));
//	}

//	/** 校验发言频率 */
//	@RequestMapping("worldChat/checkLastChat")
//	public @ResponseBody ReMsg checkLastChat() {
//		long uid = super.getUid();
//		if (uid < 1) {
//			return new ReMsg(ReCode.NOT_AUTHORIZED);
//		}
//		return worldChatService.checkLastChat(uid);
//	}

//	/** 查询@记录 */
//	@RequestMapping("worldChat/queryAtHistory")
//	public @ResponseBody ReMsg queryAtHistory() {
//		long uid = super.getUid();
//		if (uid < 1) {
//			return new ReMsg(ReCode.NOT_AUTHORIZED);
//		}
//		List<Map> list = worldChatService.queryAtHistory(uid);
//		return new ReMsg(ReCode.OK, list);
//	}
	
	/** 切换前后台 */
	@RequestMapping("/sleepOrWake") // 1 sleep 0 wake
	public @ResponseBody ReMsg sleepOrWake(Integer type) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (type == null) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return worldChatService.sleepOrWake(uid, type);
	}
	
	/** 邀请 */
	@RequestMapping("/inviteWorldChat") 
	public @ResponseBody ReMsg inviteWorldChat(Long roomId) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (roomId == null) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return worldChatService.inviteWorldChat(uid, roomId);
	}
	
	
	

}
