package com.zb.common.Constant;

public enum Point {
	// SIGN_ONE(14, 100), // 第1天签到
	// SIGN_TWO(15, 200), // 第2天签到
	// SIGN_THREE(16, 300), // 第3天签到
	// SIGN_FOUR(17, 400), // 第4天签到
	// SIGN_FIVE(18, 500), // 第5天签到
	// SIGN_SIX(19, 600), // 第6天签到
	// SIGN_SEVER(20, 700), // 第7天签到
	SIGN(21, 0), // 签到
	SHARE_APP(3, 2), // 分享APP
	SHARE_TOOL_FIRST(4, 2), // 每天首次分享
	SHARE_TOOL(5, 1), // 分享工具
	// PUBLIC_ARTICLE_FIRST(6, 4), // 每天首次发表文章
	// PUBLIC_ARTICLE_SECOND(7, 2), // 每天第二次发表文章
	PUBLIC_ARTICLE(8, 1), // 每天发表文章
	// COMMENT_ARTICLE_FIRST(9, 2), // 每天首次评论文章
	COMMENT_ARTICLE(10, 1), // 每天评论文章
	SHARE_ARTICLE(11, 1), // 分享文章
	COMMENTED_ARTICLE(12, 1), // 被评论
	// ESSENCE_ARTICLE(13, 10), // 文章加精
	ADMIN(14, 10), // 后台添加
	SENDGIFT(15, 0), // 赠送礼物加经验
	COINGIVEN(16, 0), // 版本更新活动
	VIP_PAY(17, 0), // 充值奖励

	TASK_LOGIN3DAY(22, 100), // 7天登录壕礼
	UNDERCOVER(130, 200), // 谁是卧底胜利
	DRAW_SOMETHING(131, 100), // 你画我猜
	DICE(132, 100), // 吹牛
	TASK(101, 0), // 完成任务
	SELL(135, 0), // 奴隶买卖
	WORK(136, 0), // 奴隶打工
	WEREWOLF(138, 0), // 狼人杀
	SOUTHPENGUIN(139, 0), // 推推乐
	SCHOOLWAR(140, 0), // 教室大作战
	ANIMAL_CHESS(141, 0), // 斗兽棋
	GOMOKU(142, 0), // 五子棋
	WINMINE(145, 0), // 扫雷
	NEURO_CAT(146, 0), // 神经猫
	;
	private int point = 0;
	private int type = 0;

	private Point(int type, int point) {
		this.point = point;
		this.type = type;
	}

	public int getPoint() {
		return point;
	}

	public int getType() {
		return type;
	}

}
