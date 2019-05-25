package com.zb.common.redis;

public class RK {

	public static String luckMoney() {
		return "luckMoney";
	}

	/**
	 * 一局押币
	 * 
	 * @param
	 * @return
	 */
	public static String everyMoney(long roomId) {
		return "everyMoney:" + roomId;
	}

	/**
	 * 一局押币
	 * 
	 * @param
	 * @return
	 */
	public static String activityMoney(long roomId) {
		return "activityMoney:" + roomId;
	}

	/**
	 * 匹配的人
	 * 
	 * @param
	 * @return
	 */
	public static String matchingUser(long roomId) {
		return "matchingUser:" + roomId;
	}

	/**
	 * 称号
	 * 
	 * @param
	 * @return
	 */
	public static String saveTitle(long uid) {
		return "saveTitle:" + uid;
	}

	/**
	 * 喊话
	 * 
	 * @param
	 * @return
	 */
	public static String worldShout(long uid) {
		return "worldShout:" + uid;
	}

	/**
	 * 福彩
	 * 
	 * @param roomId
	 * @return
	 */
	public static String nextQueryLotteryTime(int city) {
		return "nextQueryLotteryTime:" + city;
	}

	/**
	 * 房间用户列表
	 * 
	 * @param roomId
	 * @return
	 */
	public static String roomUser(Long roomId) {
		return "room:user:" + roomId;
	}

	/**
	 * 房间用户列表
	 * 
	 * @param roomId
	 * @return
	 */
	public static String roomUserState(Long roomId) {
		return "room:user:state:" + roomId;
	}

	/**
	 * 抢身份锁
	 * 
	 * @return
	 */
	public static String idenLock(long roomId, int iden) {
		return "room:iden:" + roomId + ":" + iden;
	}

	/**
	 * 退房锁
	 * 
	 * @return
	 */
	public static String outRoomLock(long roomId) {
		return "room:out:" + roomId;
	}

	/**
	 * 准备锁
	 * 
	 * @return
	 */
	public static String readyRoomLock(long roomId) {
		return "ready:out:" + roomId;
	}

	/**
	 * 座位队列
	 * 
	 * @return
	 */
	public static String seatList(long roomId) {
		return "seat:list:" + roomId;
	}

	/**
	 * 身份队列
	 * 
	 * @return
	 */
	public static String idenList(long roomId, int iden) {
		return "iden:list:" + roomId + ":" + iden;
	}

	/**
	 * 房主锁
	 * 
	 * @return
	 */
	public static String ownerLock(Long roomId) {
		return "owner:lock" + roomId;
	}

	/**
	 * 推荐池数据状态
	 * 
	 * @return
	 */
	public static String recommendFlag() {
		return "recommend:flag";
	}

	/**
	 * 推荐用户池
	 * 
	 * @return
	 */
	public static String recommendUsers(int type, int sex) {
		return "recommend:recommendUser:" + sex + ":" + type;
	}

	/**
	 * 狼人池
	 * 
	 * @return
	 */
	public static String wolfUsers() {
		return "recommend:wolfUser:";
	}
	
	/**
	 * 牛牛推荐池
	 * 
	 * @return
	 */
	public static String cowUsers() {
		return "recommend:cowUser:";
	}
	
	/**
	 * 德州
	 * 
	 * @return
	 */
	public static String texasUsers() {
		return "recommend:texasUser:";
	}

	/**
	 * 狼人杀榜单
	 * 
	 * @return
	 */
	public static String wolfRankList(int type) {
		return "wolfRankList:" + type;
	}

	/**
	 * 狼人杀榜单标识
	 * 
	 * @return
	 */
	public static String wolfRankListFlag() {
		return "wolfRankList:flag";
	}

	/**
	 * 狼人杀榜单锁
	 * 
	 * @return
	 */
	public static String wolfRankListLock() {
		return "lock:wolfRankList";
	}

	/**
	 * 好友列表第一页
	 * 
	 * @param uid
	 * @return
	 */
	public static String fristPageFriends(long uid, int page) {
		return "fristPageFriends:" + uid + ":" + page;
	}

	/**
	 * 奴隶主
	 * 
	 * @param uid
	 * @return
	 */
	public static String master(long uid) {
		return "master:" + uid;
	}
	// /**
	// * 房间用户列表
	// *
	// * @param roomId
	// * @return
	// */
	// public static String roomUsers() {
	// return "room:user:*";
	// }
	//
	// /**
	// * 在线用户列表
	// *
	// * @param roomId
	// * @return
	// */
	// public static String onlineUsers() {
	// return "user:online:*";
	// }

	/**
	 * 游戏关系
	 * 
	 * @param acctoken
	 * @return
	 */
	public static String everGame(Long uid, Integer type) {
		return "recommend:everGame:" + type + ":" + uid;
	}

	/**
	 * 黑名单用户
	 * 
	 * @return
	 */
	public static String blackUser() {
		return "user:black";
	}

	/**
	 * 查询黑名单用户时间
	 * 
	 * @return
	 */
	public static String blackUserQueryTime() {
		return "user:black:time";
	}

	/**
	 * 活动游戏信息
	 * 
	 * @param roomId
	 * @return
	 */
	public static String activity(Long roomId) {
		return "activity:" + roomId;
	}

	/**
	 * 可用房间列表
	 * 
	 * @param roomId
	 * @return
	 */
	public static String activityRoom(int type) {
		return "activity:room:" + type;
	}

	/**
	 * 房间定时器
	 * 
	 * @return
	 */
	public static String roomJob() {
		return "room:job";
	}

	/**
	 * gigtLog
	 * 
	 * @return
	 */
	public static String stLastId() {
		return "st:lastId";
	}

	/**
	 * propLog
	 * 
	 * @return
	 */
	public static String stPropLastId() {
		return "st:Prop:lastId";
	}

	/**
	 * propLog
	 * 
	 * @return
	 */
	public static String stCoinLastId() {
		return "st:coin:lastId";
	}

	/**
	 * 房间用户机器人
	 * 
	 * @return
	 */
	public static String roomUserRobitJob() {
		return "room:user:robit:job";
	}

	/**
	 * 用户机器人列表
	 * 
	 * @return
	 */
	public static String userRobitList() {
		return "user:robit:list";
	}

	/**
	 * 用户机器人列表索引页
	 * 
	 * @return
	 */
	public static String userRobitListPage() {
		return "user:robit:list:page";
	}

	/**
	 * 待绘画的笔数
	 * 
	 * @return
	 */
	public static String userRobitDraws() {
		return "user:robit:draws:";
	}

	/**
	 * 是否已经画完标记
	 * 
	 * @return
	 */
	public static String userRobitDrawFlag() {
		return "user:robit:draw:flag";
	}

	/**
	 * im 消息自增长Id
	 * 
	 * @return
	 */
	public static String imMsgId() {
		return "im:msg:id";
	}

	/**
	 * im聊天用户
	 * 
	 * @param id
	 * @return
	 */
	public static String imUser(Long id) {
		return "im:user:" + id;
	}

	/**
	 * 在线用户
	 * 
	 * @param id
	 * @return
	 */
	public static String imOnlineUser(Long id) {
		return "user:online:" + id;
	}

	/**
	 * 世界聊天
	 * 
	 * @param id
	 * @return
	 */
	public static String worldChatUser(Long id) {
		return "user:world:" + id;
	}

	/**
	 * im 最后用户ID
	 * 
	 * @param id
	 * @return
	 */
	public static String imLastMsg(Long id) {
		return "im:l:msg:" + id;
	}

	/**
	 * im消息
	 * 
	 * @param id
	 * @return
	 */
	public static String imMsg(Long id) {
		return "im:" + id + ":msg";
	}

	/**
	 * 用户
	 * 
	 * @param id
	 * @return
	 */
	public static String keyUser(Long id) {
		return "user:" + id;
	}

	/**
	 * 下局游戏信息
	 * 
	 * @param
	 * @return
	 */
	public static String nextGameInfo(String gameType) {
		return "game:".concat(gameType);
	}

	/**
	 * 用户登录失败次数
	 * 
	 * @param username
	 * @return
	 */
	public static String loginFailedCount(String username) {
		return "login.fail.cnt:" + username;
	}

	/**
	 * 每客户端注册数
	 * 
	 * @param client
	 * @return
	 */
	public static String regClientCount(String client) {
		return "reg.client.cnt:" + client;
	}

	/**
	 * 验证码
	 * 
	 * @param key
	 * @return
	 */
	public static String authCode(String key) {
		return "auth.code:" + key;
	}

	/**
	 * 访问token
	 * 
	 * @param acctoken
	 * @return
	 */
	public static String accessToken(String acctoken) {
		return "acctoken:" + acctoken;
	}

	/**
	 * 黑名单用户
	 * 
	 * @param devId
	 * @return
	 */
	public static String blackDev(String devId) {
		return "black.dev:" + devId;
	}

	/**
	 * 发送短信IP
	 * 
	 * @param ip
	 * @return
	 */
	public static String smsIp(String ip) {
		return "sms.ip:" + ip;
	}

	/**
	 * 发送短信手机号
	 * 
	 * @param phone
	 * @return
	 */
	public static String smsPhone(long phone) {
		return "sms.phone:" + phone;
	}

	/**
	 * 第三方登录token
	 * 
	 * @param acctoken
	 * @return
	 */
	public static String openToken(String acctoken) {
		return "open:" + acctoken;
	}

	/**
	 * 用户使用工具数
	 * 
	 * @param uid
	 * @return
	 */
	public static String toolUserUse(Long uid) {
		return "tool.use:" + uid;
	}

	// public static String toolUserDayUse(Long uid) {
	// return "tool.day.use:" + uid;
	// }

	/**
	 * 每IP工具使用数
	 * 
	 * @param ip
	 * @return
	 */
	public static String toolIpUse(String ip) {
		return "tool.use:" + ip;
	}

	// public static String toolIpDayUse(String ip) {
	// return "tool.day.use:" + ip;
	// }

	public static String activityId() {
		return "activity:id";
	}

	public static String roomUserGameLock(long uid) {
		return "room:user:lock:" + uid;
	}

	/**
	 * giftLog日志
	 * 
	 * @param
	 * @return
	 */
	public static String keyGiftLog(String key) {
		return "flower:" + key;
	}

	/**
	 * 双人游戏积分排行榜
	 * 
	 * @param
	 * @return
	 */
	public static String keyRankListGameCP(String key) {
		return "rankList:gameCp:" + key;
	}

	/**
	 * 收花排行榜
	 * 
	 * @param
	 * @return
	 */
	public static String keyRankListFlower(String key) {
		return "rankList:flower:" + key;
	}

	/**
	 * 赢金榜:花魁 牛牛
	 * 
	 * @param
	 * @return
	 */
	public static String rankListWinCoinLog(String key) {
		return "rankList:WinCoinLog:" + key;
	}

	/**
	 * 启动页封面
	 * 
	 * @param
	 * @return
	 */
	public static String keyAppCover(long day) {
		return "appCover:" + day;
	}

	/** 金币已领取标记 */
	public static String recvYearMoney(String key) {
		return "recvYearMoney:" + key;
	}

	// 排行榜奖励发放job锁
	public static String rankListLock() {
		return "lock:rankList";
	}

	/**
	 * 用户进入房间锁
	 * 
	 * @return
	 */
	public static String userSelectGame(long uid) {
		return "lock:user:select:game:" + uid;
	}

	/**
	 * 房间锁
	 * 
	 * @return
	 */
	public static String roomLock() {
		return "lock:room";
	}

	/**
	 * 统计锁
	 * 
	 * @return
	 */
	public static String stLock() {
		return "lock:st";
	}

	/**
	 * 称号锁
	 * 
	 * @return
	 */
	public static String titleLock() {
		return "lock:title";
	}

	/**
	 * 房间用户机器人锁
	 * 
	 * @return
	 */
	public static String roomUserRobitLock() {
		return "lock:room:user:robit";
	}

	/**
	 * 推荐锁
	 * 
	 * @return
	 */
	public static String recommendLock() {
		return "lock:recommend";
	}

	/**
	 * spiderLottery锁
	 * 
	 * @return
	 */
	public static String spiderLotteryLock() {
		return "lock:spiderLottery";
	}

	/**
	 * cleanTitle锁
	 * 
	 * @return
	 */
	public static String cleanTitleLock() {
		return "lock:cleanTitle";
	}

	/**
	 * cleanExpireRoom锁
	 * 
	 * @return
	 */
	public static String cleanExpireRoomLock() {
		return "lock:cleanExpireRoom";
	}

	/**
	 * 抢座锁
	 * 
	 * @return
	 */
	public static String seatLock(Long roomId) {
		return "lock:seat" + roomId;
	}

	/**
	 * 待匹配用户
	 * 
	 * @param type
	 * @param uid
	 * @return
	 */
	public static String matchUser(final long type) {
		return "activity:match:" + type;
	}

	/**
	 * 待匹配用户列表
	 * 
	 * @param type
	 * @param uid
	 * @return
	 */
	public static String matchUserList(final int sex, final int type) {
		return "activity:match:List:" + type + ":" + sex;
	}

	/**
	 * 邀请用户游戏
	 * 
	 * @param type
	 * @param uid
	 * @return
	 */
	public static String inviteActivity(final long activiyId) {
		return "activity:invite:" + activiyId;
	}

	/**
	 * 房间用户机器人锁
	 * 
	 * @return
	 */
	public static String userFriends(final String key) {
		return "user:friends:" + key;
	}

	/**
	 * 猜猜开奖锁
	 * 
	 * @return
	 */
	public static String guessDrawLock() {
		return "lock:guessDraw";
	}
}
