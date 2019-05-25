package com.zb.web.api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.core.web.ReMsg;
import com.zb.models.GameCPScore;
import com.zb.models.room.Room;
import com.zb.service.GameCPScoreService;
import com.zb.service.RecommendService;
import com.zb.service.UserAttenRoomService;
import com.zb.service.room.DrawSomethingService;
import com.zb.service.room.RoomDefaultService;
import com.zb.service.room.UndercoverService;
import com.zb.service.room.UserActivityService;
import com.zb.socket.model.MsgType;

@Controller
@RequestMapping("/api")
public class ActivityCtl extends BaseCtl {
	static final Logger log = LoggerFactory.getLogger(ActivityCtl.class);

	@Autowired
	UserActivityService userActivityService;

	@Autowired
	RoomDefaultService roomDefaultService;

	@Autowired
	DrawSomethingService drawSomethingService;

	@Autowired
	UndercoverService undercoverService;

	@Autowired
	GameCPScoreService gameCPScoreService;

	@Autowired
	RecommendService recommendService;

	@Autowired
	UserAttenRoomService userAttenRoomService;

	// 用户自建房间
	@ResponseBody
	@RequestMapping("/creatRoomByUser")
	public ReMsg creatRoomByUser(int type) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (!MsgType.canBeCreateRoomType.contains(type)) {
			return new ReMsg(ReCode.ROOM_CREATE_TYPE_ERROR);
		}
		Room r = roomDefaultService.creatRoomByUser(uid, type);
		if (r != null) {
			return new ReMsg(ReCode.OK, r);
		} else {
			return new ReMsg(ReCode.FAIL);
		}
	}

	@ResponseBody
	@RequestMapping("/kickUser")
	public ReMsg kickUser(long kickUid, long roomId) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return userActivityService.kick(kickUid, roomId);
	}

	// @ResponseBody
	// @RequestMapping("/play")
	// public ReMsg play(long roomId) {
	// long uid = super.getUid();
	// if (uid < 1) {
	// return new ReMsg(ReCode.NOT_AUTHORIZED);
	// }
	// return drawSomethingService.play(uid, roomId);
	// }

	@ResponseBody
	@RequestMapping("/activity")
	public ReMsg activity(int type, String historyRoomIds, HttpServletRequest req) {
		// return userActivityService.userInActivityV2(type, historyRoomIds);
		return userActivityService.userInActivity(type, historyRoomIds);
	}

	/** 取消二人游戏匹配 */
	@ResponseBody
	@RequestMapping("/activity/unMatchingActivity")
	public ReMsg use(int type, HttpServletRequest req) {
		// return userActivityService.userInActivityV2(type, historyRoomIds);
		return roomDefaultService.unMatchingActivity(type);
	}

	/** 邀请好友玩普通游戏 */
	@ResponseBody
	@RequestMapping("/activity/inviteFriends")
	public ReMsg inviteFriends(long roomId, String uids, int type, HttpServletRequest req) {
		return roomDefaultService.inviteFriends(roomId, uids, type);
	}

	/** 邀请好友玩二人游戏 */
	@ResponseBody
	@RequestMapping("/activity/inviteCP")
	public ReMsg inviteTwoGame(int type, long uid, HttpServletRequest req) {
		return userActivityService.inviteActivity(type, uid);
	}

	/** 再来一局二人游戏 */
	@ResponseBody
	@RequestMapping("/activity/playAgainCP")
	public ReMsg playAgainCP(int type, long uid, HttpServletRequest req) {
		return userActivityService.playAgainActivity(type, uid);
	}

	/** 取消二人游戏邀请 */
	@ResponseBody
	@RequestMapping("/activity/cancelCP")
	public ReMsg cancelTwoGame(long activityId, HttpServletRequest req) {
		return userActivityService.cancelInviteActivity(activityId);
	}

	/** 接受二人游戏邀请 */
	@ResponseBody
	@RequestMapping("/activity/agreeCP")
	public ReMsg agreeTwoGame(long activityId, HttpServletRequest req) {
		return userActivityService.invitedActivity(activityId);
	}

	/** 拒绝二人游戏邀请 */
	@ResponseBody
	@RequestMapping("/activity/refuseCP")
	public ReMsg refuseTwoGame(long activityId, HttpServletRequest req) {
		return userActivityService.refuseInviteActivity(activityId);
	}

	@ResponseBody
	@RequestMapping("/activity/list")
	public ReMsg list(HttpServletRequest req) {
		return userActivityService.getActivityList(req);
	}

	@ResponseBody
	@RequestMapping("/canInRoom")
	public ReMsg canInRoom(int type, long actId, long roomId, HttpServletRequest req) {
		return userActivityService.canInRoom(type, actId, roomId, req);
	}

	// @ResponseBody
	// @RequestMapping("/gameCond")
	// public ReMsg use(long activityType) {
	// return userActivityService.getCondition(activityType);
	// }

	@ResponseBody
	@RequestMapping("/room/search")
	public ReMsg searchRoom(String room, HttpServletRequest req) {
		long rid = 0L;
		try {
			rid = Long.parseLong(room);
		} catch (Exception e) {
		}
		if (rid < 1) {
			return new ReMsg(ReCode.FAIL);
		}
		return userActivityService.searchRoom(rid, req);
	}

	@ResponseBody
	@RequestMapping(value = "/drawSomething/addWrod")
	public ReMsg guessword(String word, String tip) {
		String regex = "[\u4E00-\u9FA5]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher1 = pattern.matcher(word);
		Matcher matcher2 = pattern.matcher(tip);
		if (matcher1.find() && matcher2.find())
			return drawSomethingService.userAddWord(word, tip);
		else
			return new ReMsg(ReCode.ONLY_CHINEASE);
	}

	@ResponseBody
	@RequestMapping(value = "/undercover/addWrod")
	public ReMsg undercoverWord(String w1, String w2) {
		String regex = "[\u4E00-\u9FA5]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher1 = pattern.matcher(w1);
		Matcher matcher2 = pattern.matcher(w2);
		if (matcher1.find() && matcher2.find())
			return undercoverService.add(w1, w2);
		else
			return new ReMsg(ReCode.ONLY_CHINEASE);
	}

	/** 查询游戏子页面 */
	@ResponseBody
	@RequestMapping(value = "/findActivityChildrenList")
	public ReMsg findActivityChildrenList(int parentId, HttpServletRequest req) {
		return new ReMsg(ReCode.OK, userActivityService.findActivityChildrenList(Const.STATUS_UP, parentId, req));
	}

	/** 游戏举报 */
	@RequestMapping("/reportSomebody")
	public @ResponseBody ReMsg reportSomebody(long roomId, long rid, long actId, int rType, int playerCount) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		if (actId < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return userActivityService.reportSomebody(roomId, uid, rid, actId, rType, playerCount);
	}

	/** 点赞 */
	@RequestMapping("/praiseSomebody")
	public @ResponseBody ReMsg praiseSomebody(long roomId, long pid, long actId) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		if (pid < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return userActivityService.praiseSomebody(roomId, uid, pid, actId);
	}

	/** 查询点赞 */
	@RequestMapping("/queryPraise")
	public @ResponseBody ReMsg queryPraise(int type) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		if (type < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return userActivityService.queryPraise(type, uid);
	}

	/** 查询资源包 */
	@RequestMapping("/getGameZip")
	public @ResponseBody ReMsg getGameZip(int gameType, int via, double ver, String appCode) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		return userActivityService.getGameZip(gameType, via, ver, appCode);
	}

	/** CP游戏个人排名和积分 */
	@ResponseBody
	@RequestMapping("/cp/userRank")
	public ReMsg userRank(int gameType, Integer timeType) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (null == timeType) {
			timeType = GameCPScore.TIMETYPE_WEEK;
		}
		return gameCPScoreService.queryUserRank(uid, gameType, timeType);
	}

	/** CP游戏积分榜单 */
	@ResponseBody
	@RequestMapping("/cp/rankListByType")
	public ReMsg rankListByType(int gameType, Integer timeType, Integer page, Integer size) {
		return gameCPScoreService.queryRankByGameType(gameType, timeType, page, size);
	}

	/** CP游戏对战记录 */
	@ResponseBody
	@RequestMapping("/cp/gameCpUserHistory")
	public ReMsg gameCpUserHistory(Integer gameType, Integer sex, Integer page, Integer size) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return gameCPScoreService.gameCpUsersHistory(uid, gameType, sex, page, size);
	}

	/** 查询和某个人的对战胜负 */
	@ResponseBody
	@RequestMapping("/cp/gameCpOne")
	public ReMsg gameCpOne(long rid) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return gameCPScoreService.gameCpOneHistory(uid, rid);
	}

	/** 抽取房间 */
	@ResponseBody
	@RequestMapping("/room/lotteryRoom")
	public ReMsg lotteryRoom(Long lastRoomId) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return roomDefaultService.lotteryRoom(uid, lastRoomId);
	}

	/**
	 * 购买房间 time:购买时间长度 30 90 180
	 */
	@ResponseBody
	@RequestMapping("/room/buyRoom")
	public ReMsg buyRoom(long roomId, int type) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return roomDefaultService.buyRoom(uid, roomId, type);
	}

	/** 用户放弃该房间或放弃付款 */
	@ResponseBody
	@RequestMapping("/room/waiveRoom")
	public ReMsg waiveRoom(long roomId) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return roomDefaultService.waiveRoom(uid, roomId);
	}

	/**
	 * 我的包间 sellStatus:已购买或者 待付款
	 */
	@ResponseBody
	@RequestMapping("/room/myRoom")
	public ReMsg myRoom(Integer sellStatus, Integer page, Integer size) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return roomDefaultService.queryMyRoom(uid, sellStatus, page, size);
	}

	/**
	 * 查询未付款包间
	 */
	@ResponseBody
	@RequestMapping("/room/queryNoPayRoom")
	public ReMsg queryNPRoom() {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return roomDefaultService.queryMyNotPayRoom(uid);
	}

	/** 房主设置房间信息 */
	@ResponseBody
	@RequestMapping("/room/upSetRoomInfo")
	public ReMsg openActive(long roomId, Boolean pub, String pwd, String roomName, Integer type) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return roomDefaultService.upSetRoomInfo(uid, roomId, pub, pwd, roomName, type);
	}

	// /** 房主切换房间游戏类型 */
	// @ResponseBody
	// @RequestMapping("/room/openActive")
	// public ReMsg openActive(long roomId, int type) {
	// long uid = super.getUid();
	// if (uid < 1) {
	// return new ReMsg(ReCode.NOT_AUTHORIZED);
	// }
	// return roomDefaultService.openActive(uid, roomId, type);
	// }

	/** 用户关注或取消关注包间 */
	@ResponseBody
	@RequestMapping("/room/attenRoom")
	public ReMsg attenRoom(long roomId, boolean attenRoom) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return roomDefaultService.saveUserAttenRoom(uid, roomId, attenRoom);
	}

	/** 最近包间列表 */
	@ResponseBody
	@RequestMapping("/room/userInRooms")
	public ReMsg userInRooms(Integer page, Integer size) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return roomDefaultService.userInRooms(uid, page, size);
	}

	/** 好友包间列表 */
	@ResponseBody
	@RequestMapping("/room/friendsRooms")
	public ReMsg friendsRooms(Integer page, Integer size) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return roomDefaultService.friendsRooms(uid, page, size);
	}

	/** 热门包间列表 */
	@ResponseBody
	@RequestMapping("/room/hotRooms")
	public ReMsg hotRooms(Integer page, Integer size) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return roomDefaultService.hotRooms(page, size);
	}

	/** 我关注的包间列表 */
	@ResponseBody
	@RequestMapping("/room/attenRooms")
	public ReMsg userAttenRooms(Integer page, Integer size) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return roomDefaultService.userAttenRooms(uid, page, size);
	}

	/** 校验密码 */
	@ResponseBody
	@RequestMapping("/room/checkPwd")
	public ReMsg checkPwd(long roomId, String pwd) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return roomDefaultService.checkPwd(roomId, pwd);
	}

	/** 是否已经关注此房间 */
	@ResponseBody
	@RequestMapping("/room/isAttenRoom")
	public ReMsg isAttenRoom(long roomId) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return roomDefaultService.isAttenRoom(uid, roomId);
	}

}
