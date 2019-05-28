package com.zb.web.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zb.common.Constant.Const;
import com.zb.common.Constant.ReCode;
import com.zb.common.utils.IpUtil;
import com.zb.core.web.ReMsg;
import com.zb.service.RecommendService;
import com.zb.service.room.vo.GameFriendsList;

@Controller
@RequestMapping("/api")
public class RecommendCtl extends BaseCtl {

	@Autowired
	RecommendService recommendService;

	/** 狼人-推荐 */
	@RequestMapping("/recommend/recommendWolfUsers")
	public @ResponseBody ReMsg recommendWolfUsers(Integer index) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return recommendService.recommendWolfUsers(uid, index);
	}

	/** 牛牛-推荐 */
	@RequestMapping("/recommend/recommendCowUsers")
	public @ResponseBody ReMsg recommendCowUsers(Integer index) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return recommendService.recommendCowUsers(uid, index);
	}

	/** 德州-推荐 */
	@RequestMapping("/recommend/recommendTexasUsers")
	public @ResponseBody ReMsg recommendTexasUsers(Integer index) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		return recommendService.recommendTexasUsers(uid, index);
	}

	/** 首页-推荐用户 */
	@RequestMapping("/recommend/recommendUsers")
	public @ResponseBody ReMsg recommendUsers(Integer index, Integer gameIndex) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (uid == Const.SYSTEM_ID || uid == Const.SYSTEM_ID2) {
			return new ReMsg(ReCode.OK, new GameFriendsList());
		}
		return recommendService.recommendUsers(uid, index, gameIndex);
	}

	/** 好友推荐 */
	@RequestMapping("/recommend/recommendFriends")
	public @ResponseBody ReMsg recommendFriends(Integer maleIndex, Integer femaleIndex, Integer maleBook,
			Integer femaleBook) {
		long uid = super.getUid();

		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (uid == Const.SYSTEM_ID || uid == Const.SYSTEM_ID2) {
			return new ReMsg(ReCode.OK, new GameFriendsList());
		}
		return recommendService.recommendFriends(uid, maleIndex, femaleIndex, maleBook, femaleBook);
	}

	/** 附近的人 */
	@RequestMapping("/recommend/nearBy")
	public @ResponseBody ReMsg findNearByBiBi(Double latitude, Double longitude, Integer sursor1, Integer size,
			Integer sursor2, Integer searchSex, HttpServletRequest request) {
		long uid = super.getUid();
		if (uid < 1) {
			return new ReMsg(ReCode.NOT_AUTHORIZED);
		}
		if (uid == Const.SYSTEM_ID || uid == Const.SYSTEM_ID2) {
			return new ReMsg(ReCode.OK, new GameFriendsList());
		}
		String ip = IpUtil.getIpAddr(request);
		return recommendService.findNearByBiBi(uid, latitude, longitude, sursor1, size, sursor2, searchSex, ip);
	}

	// /** 测试-推送 */
	// @RequestMapping("/pushDate2Cache")
	// public @ResponseBody ReMsg pushDate2Cache(HttpServletRequest request) {
	// String ip = IpUtil.getIpAddr(request);
	// AddressComponent a = LbsUtil.getIpLbs("218.12.41.179");
	// System.out.println(ip);
	// // recommendService.statisticsGameLog();
	// // recommendService.pushDate2Cache(1);
	// //
	// recommendService.getRedisTemplate().opsForValue().set(RK.recommendFlag(),
	// // "2");
	// // String flag =
	// //
	// recommendService.getRedisTemplate().opsForValue().get(RK.recommendFlag());
	// // if ("1".equals(flag)) {
	// // 1缓存使用中 ,刷新数据到2缓存
	// // recommendService.pushDate2Cache(2);
	// // recommendService.pushGameDate2Cache(2);
	// // 切换2缓存为可用
	// //
	// recommendService.getRedisTemplate().opsForValue().set(RK.recommendFlag(),
	// // "2");
	// // } else if ("2".equals(flag)) {
	// // recommendService.pushDate2Cache(1);
	// // recommendService.pushGameDate2Cache(1);
	// //
	// recommendService.getRedisTemplate().opsForValue().set(RK.recommendFlag(),
	// // "1");
	// // }
	// // recommendService.pushGameDate2Cache(1);
	// // recommendService.pushDate2Cache(1);
	// // recommendService.pushGameDate2Cache(2);
	// // recommendService.pushDate2Cache(2);
	// return new ReMsg(ReCode.OK);
	// }

}
