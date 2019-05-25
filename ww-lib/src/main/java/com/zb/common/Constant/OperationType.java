package com.zb.common.Constant;

public enum OperationType {

	URL("url"), // 网页 opId 为网页地址
	TOOL("tool"), // 工具 opId 为工具id
	EIF("eif"), // 表情 opId 为 表情id
	GAME("game"), // 游戏 opId 为游戏id （不用了！）
	TOOL_TAG("toolTag"), // 工具标签 opId 为一个工具标签，根据标签查找相应的工具
	TOOL_HOT("toolHot"), // 热门工具，opId为热门类型 1，总体热门 2，月热门 3 周热门 （暂时没有）
	GROUP("group"), // 圈子 opId为圈子Id，暂时没有
	TOPIC("topic"), // 话题 opId为话题id
	ARTICLE("article"), // 文章 opId 为文章Id
	ARTICLE_TAG("articleTag"), // 文章标签 opId 标签，根据标签查找文章（暂时没有）
	COMMENT("comment"), // 评论 opId 为 评论Id
	CHANNEL("channal"), // 频道 opId 为频道Id 1 首页 2斗图 3圈子 4站内信 5游戏。。。
	USERSPACE("userSpace"), // 到用户空间 opId 为用户Id
	ROOM("room"), // 进入某一个房间 opId 为房间Id subOp:子操作类型（1弹对话框，有确认，取消按钮）
					// subBody：对话框内容
	// MESSAGE("message"), // 进入和某人对话界面 opId 消息Id
	CHAT("chat"), // 点对点聊天消息，opId为对方用户Id
	ADD_FRIEND("add_friend"), // 添加好友请求，opId为对方用户Id
	THIRD_LOGIN("third_login"), // 给第三方应用提供登录
	INVITE_FRIENDS("invite_friends"), // 邀请好友
	NULL("null"), // 只做展示，不做具体跳转
	SEND_GIFT("send_gift"), // 赠送礼物 opid为礼物id
	SHARE_APP("share_app"), // 分享 opid为空
	APPSTORE("appStore"), // 跳转商店 opid为空
	PAY("pay"), // 跳转充值界面 opid为空
	RANK("rank"),
	SLAVE("slave"), // 跳转奴隶界面 opid为空
	STAR("star"), // 星球升级 跳转新星球 opid为空
	LEVELUP("level_up"), // 用户升级 opid为空
	GUESS("guess"), // 到用户空间 opId 为用户Id
	;
	static String[] ops = { URL.op, TOOL.op, EIF.op, GAME.getOp(), TOOL_TAG.getOp(), TOOL_HOT.getOp(), TOPIC.getOp(),
			ARTICLE.getOp(), ARTICLE_TAG.getOp(), COMMENT.getOp(), GROUP.getOp(), CHANNEL.getOp(), THIRD_LOGIN.getOp(),
			NULL.getOp(), INVITE_FRIENDS.getOp(), RANK.getOp() };
	private String op;

	private OperationType(String op) {
		this.op = op;
	}

	public String getOp() {
		return op;
	}

	public static String[] getOps() {
		return ops;
	}

}
