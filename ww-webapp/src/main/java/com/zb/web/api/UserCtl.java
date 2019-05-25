package com.zb.web.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.zb.common.Constant.ReCode;
import com.zb.common.utils.RegexUtil;
import com.zb.core.web.ReMsg;
import com.zb.service.AuthService;
import com.zb.service.FlowerRedeemService;
import com.zb.service.NotificationService;
import com.zb.service.ReNameLogService;
import com.zb.service.SignService;
import com.zb.service.UserService;
import com.zb.service.UserTagService;
import com.zb.service.room.RoomDefaultService;

@Controller
@RequestMapping("/user")
public class UserCtl extends BaseCtl {

	@Autowired
	UserService userService;

	@Autowired
	SignService signService;

	@Autowired
	AuthService authService;

	@Autowired
	FlowerRedeemService redeemService;

	@Autowired
	NotificationService notificationService;

	@Autowired
	ReNameLogService reNameLogService;

	@Autowired
	UserTagService userTagService;

	@Autowired
	RoomDefaultService roomDefaultService;

	@ResponseBody
	@RequestMapping("/checkNickname")
	public ReMsg checkUsername(String nickname) {
		if (!RegexUtil.isNickname(nickname)) {
			return new ReMsg("用户昵称2-16位！");
		}
		return userService.checkNickname(nickname, super.getUid());
	}

	@ResponseBody
	@RequestMapping("/updateUser")
	public ReMsg updateUser(String nickname, Integer sex, String avatar) {
		if (null != nickname) {
			nickname = nickname.trim();
			if (!RegexUtil.isNickname(nickname)) {
				return new ReMsg("用户昵称2-16位！");
			}
		}
		return userService.updateUser(sex, nickname, avatar);
	}

	@ResponseBody
	@RequestMapping("/updateUserNew")
	public ReMsg updateUserNew(String nickname, Integer sex, String interests, String personLabel, String avatar,
			String cover, String photos, String phone) {
		if (null != nickname) {
			nickname = nickname.trim();
			if (!RegexUtil.isNickname(nickname)) {
				return new ReMsg("用户昵称2-16位！");
			}
		}
		return userService.updateUser(nickname, sex, interests, personLabel, avatar, cover, photos, phone);
	}

	@ResponseBody
	@RequestMapping("/updateLbs")
	public ReMsg updateUserLbs(String lbs, HttpServletRequest req) {
		return userService.updateUserLbs(lbs, req);
	}

	@ResponseBody
	@RequestMapping("/top")
	public ReMsg topUser(int size) {
		return new ReMsg(ReCode.OK, userService.findTopUser(size));
	}

	@ResponseBody
	@RequestMapping("/updatePwd")
	public ReMsg updatePwd(String newPwd, String oldPwd, HttpServletRequest req) {
		if (!RegexUtil.isPassword(newPwd)) {
			return new ReMsg("密码为6－20位！");
		}
		return userService.updatePwd(oldPwd, newPwd, req);
	}

	@ResponseBody
	@RequestMapping("/set/worth")
	public ReMsg change(int worth, HttpServletRequest req) {
		return userService.changeFriendWorth(worth);
	}

	@ResponseBody
	@RequestMapping("/myinfo")
	public ReMsg getMyInfo(HttpServletRequest req) throws JsonParseException, JsonMappingException, IOException {
		return userService.getMyInfo(req);
	}

	@ResponseBody
	@RequestMapping("/{id}")
	public ReMsg getBy(HttpServletRequest request, @PathVariable Long id) {
		return userService.getUserInfo(id);
	}

	@ResponseBody
	@RequestMapping("/simple/{id}")
	public ReMsg getUserSimpleInfo(HttpServletRequest request, @PathVariable Long id) {
		return new ReMsg(ReCode.OK, userService.findByIdSafe(id));
	}

	@ResponseBody
	@RequestMapping("/sign")
	public ReMsg sign(HttpServletRequest req) {
		return signService.sign(req);
	}

	@ResponseBody
	@RequestMapping("/resign")
	public ReMsg resign(HttpServletRequest req) {
		return signService.reSign(req);
	}

	@ResponseBody
	@RequestMapping("/isSign")
	public ReMsg isSign(HttpServletRequest req) {
		return signService.isSign(req);
	}

	@ResponseBody
	@RequestMapping("/signDraw")
	public ReMsg signDraw(int month, int cycle, HttpServletRequest req) {
		return signService.draw(month, cycle);
	}

	/*
	 * 按周签到API
	 */

	@ResponseBody
	@RequestMapping("/isSignWeek")
	public ReMsg isSignWeek(HttpServletRequest req) {
		return signService.isSignWeek(req);
	}

	@ResponseBody
	@RequestMapping("/signWeek")
	public ReMsg signWeek(HttpServletRequest req) {
		return signService.signWeek(req);
	}

	@ResponseBody
	@RequestMapping("/reSignWeek")
	public ReMsg resignWeek(HttpServletRequest req) {
		return signService.reSignWeek(req);
	}

	// // 根据id或昵称查询用户
	// @ResponseBody
	// @RequestMapping("/searchUser")
	// public ReMsg searchUser(String data, HttpServletRequest req) {
	// return userService.searchUser(data);
	// }

	// 签到小红点
	@ResponseBody
	@RequestMapping("/sign/getRedPoint")
	public ReMsg getRedPoint(HttpServletRequest req) {
		return signService.getSignRedPoint(req);
	}

	// 兴趣标签
	@ResponseBody
	@RequestMapping("/userSpace/interest")
	public ReMsg getInterest(HttpServletRequest req) {
		return userService.getInterestsList();
	}

	// 查询可用额度
	@ResponseBody
	@RequestMapping("/resCount")
	public ReMsg getResCount(HttpServletRequest req) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.ACCESS_TOKEN_ERR);
		}
		return new ReMsg(ReCode.OK, redeemService.resCount(uid));
	}

	// 鲜花额度兑换金币
	@ResponseBody
	@RequestMapping("/redeemCoin")
	public ReMsg redeemCoin(HttpServletRequest req, Integer count) {
		return redeemService.redeemCoin(req, count);
	}

	// 设置免打扰
	@ResponseBody
	@RequestMapping("/setNotifi")
	public ReMsg setDisturb(Boolean disturb, Boolean message, Integer st, Integer et) {
		return notificationService.update(disturb, message, st, et);
	}

	// 获取免打扰设置
	@ResponseBody
	@RequestMapping("/getNotifi")
	public ReMsg getDisturb() {
		return notificationService.queryNotification();
	}

	/** 获取改名所需金币 */
	@ResponseBody
	@RequestMapping("/queryReNameCoin")
	public ReMsg queryReNameCoin() {
		return reNameLogService.queryNeedCoin();
	}

	/** 获取金币 */
	@ResponseBody
	@RequestMapping("/findMyCoin")
	public ReMsg findMyCoin() {
		return userService.findMyCoin();
	}

	/** 改名 */
	@ResponseBody
	@RequestMapping("/updateName")
	public ReMsg updateNickname(String nickname) {
		if (StringUtils.isBlank(nickname)) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return reNameLogService.changeNickname(nickname);
	}

	@ResponseBody
	@RequestMapping("/tag/template")
	public ReMsg tagTemplate() {
		return new ReMsg(ReCode.OK, userTagService.getTagsTemplate());
	}

	// @ResponseBody
	// @RequestMapping("/tag/get")
	// public ReMsg userTags(long uid) {
	// return new ReMsg(ReCode.OK,userTagService.findById(uid));
	// }

	@ResponseBody
	@RequestMapping("/tag/add")
	public ReMsg tagAdd(String key, String value) {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return userTagService.createTag(key, value);
	}

	@ResponseBody
	@RequestMapping("/tag/del")
	public ReMsg tagDel(int idx) {
		return userTagService.delTag(idx);
	}

	@ResponseBody
	@RequestMapping("/tag/update")
	public ReMsg tagAdd(int idx, String key, String value) {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return userTagService.updateTag(idx, key, value);
	}

	@ResponseBody
	@RequestMapping("/tag/change")
	public ReMsg tagAdd(int srcIdx, int descIdx) {
		return userTagService.changeIdx(srcIdx, descIdx);
	}

	@ResponseBody
	@RequestMapping("/friends/dynamic")
	public ReMsg dynamic(Integer size, Integer page) {
		return userService.getFriendsDynamic(size, page);
	}

	/** 获取所有上线称号 */
	@ResponseBody
	@RequestMapping("/titleModel")
	public ReMsg titleModel(Integer ttype) {
		return userService.getAllTitleModels(ttype);
	}

	/** 移除守护 */
	@ResponseBody
	@RequestMapping("/removeGuard")
	public ReMsg removeGuard(long uid) {
		long myId = super.getUid();
		if (myId < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return userService.removeGuard(myId, uid);
	}

	/** ta的包间 */
	@ResponseBody
	@RequestMapping("/userSpaceRoom")
	public ReMsg userSpaceRoom(Long uid) {
		long myId = super.getUid();
		if (myId < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (null == uid || uid < 1) {
			return new ReMsg(ReCode.PARAMETER_ERR);
		}
		return roomDefaultService.userSpaceRoom(uid);
	}
}
