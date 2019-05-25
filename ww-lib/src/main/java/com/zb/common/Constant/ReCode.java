package com.zb.common.Constant;

public enum ReCode {
	OK(0, "OK"),

	FAIL(1, "FAILED"),

	SYS_ERR(2, "ERR,系统错误"),

	USERNAME_ERR(10001, "输入的用户有误，用户名必须为2-16位中文，字母，数字"),

	PASSWORD_ERR(10002, "输入密码有误，密码必须为6-20位"),

	USERNAME_EXISTS(10003, "用户名已经存在"),

	LOGIN_FAILED_ERR_USERNAME(10004, "登录失败，用户名不存在"),

	LOGIN_FAILED_ERR_PASSWORD(10005, "登录失败，用户名或密码错误"),

	LOGIN_FAILED_ERR_TOO_OFTEN(10006, "多次登录失败，5分钟后再登录"),

	AUTHCODE_ERR(10007, "验证码错误，重新获取验证码！"),

	NICKNAME_EXISTS(10008, "昵称已经存在"),

	PASSWORD_VER_ERR(10009, "密码验证失败"),

	HAVE_BEEN_SIGNED(10010, "已经签到"),

	THIRD_LOGIN_FAILED_ERR(10011, "第三方登陆失败"),

	THIRD_LOGIN_FAILED_ERR_NO_INFO(10012, "第三方登陆失败，无返回用户信息"),

	LOGIN_FAILED_ERR_THIRD(10013, "第三方登录失败"),

	COIN_BALANCE_LOW(10014, "余额不足！"),

	REDUCE_COIN_FAIL(10015, "扣币失败️"),

	ADD_COIN_FAIL(10016, "加币失败️"),

	NO_SIGN_CARD(10017, "补签卡已经用完了"),

	NO_RESIGN(10018, "无需补签"),

	PARAMETER_ERR(10100, "参数错误"),

	SIGN_ERR(10101, " 签名错误"),

	NOT_AUTHORIZED(11000, "没有登录或登录已失效，请重新登录"),
	// NOT_LOGINED(11001,"用户没有登录"),
	LOGIN_NOT_ALLOWED(11002, "账户已禁用"),

	REG_TOO_OFTEN(11003, "注册太频繁"),

	BANNED(11004, "恶意访问，ip，设备被禁"),

	USER_ID_ERR(11005, "用户Id错误"),

	USER_LOCKED(11006, "账户已禁用"),

	SMS_IP_TOO_OFTEN(11101, "当前IP超过短信验证码此数"),

	SMS_PHONE_TOO_OFTEN(11102, "当前手机号超过短信验证码次数"),

	SMS_PHONE_TOO_OFTEN_60(11103, "当前手机号调用太频繁，1分钟后再尝试"),

	SMS_SEND_ERR(11104, "验证码发送失败，稍后重试"),

	SMS_SEND_OUTTIME_ERR(11105, "发送超时！"),

	SMS_DRAW_ERR(11106, "滑动验证码有误，重新滑动！"),

	ALREADY_FRIENDS(11110, "添加失败,你们早就是好友啦"),

	HAVE_BLACK(11111, "拉黑过的好友48小时之后才能添加!"),

	ADD_YOURSELF(11112, "不能添加自己为好友!"),

	ROOL_NO(12000, "没有权限"),

	GOODS_NOT_SALE(13000, "商品不可购买"),

	ORDER_DISCOUNT_NOT_USED(13100, "优惠券不可用"),

	ORDER_DISCOUNT_USE_ERR(13101, "优惠券使用失败，请联系官方"),

	ORDER_NOT_PAYED(13102, "订单未支付"),

	ORDER_OPENED(13103, "订单已开通"),

	ORDER_BUG(13105, "代充嫌疑，订单暂缓开通，请联系管理员！"),

	ORDER_AMOUNT_ERR(13104, "订单金额与实际不一致"),

	BUY_GIFTBAG_ERR(13106, "购买条件不符合，购买失败！"),

	TOOL_NON_EXISTENT_ERR(14000, "该工具不存在"),

	TOOL_EXPIRED_ERR(14001, "该工具已失效"),

	TOOL_USE_MAX_ERR(14002, "使用太频繁，稍后再用"),

	TOOL_USE_IP_MAX_ERR(14003, "该IP使用太频繁"),

	CONTACT_HEAD_CATE_NAME_ERR(14501, "联系人头像分类错误"),

	SECURITY_SIGN_ERR(20001, "签名错误！"),

	BLACK_WORD_ERR(21001, "含有违禁词！"),

	FEEDBACK_ERR(22001, "您的反馈我们已经收到，休息一会儿吧！"),

	ACCESS_TOKEN_ERR(30000, "访问失败,AccessToken无效"),

	ARTICLE_COMMENT_PRAISE_EXIST(40001, "已经赞过了"),

	BU_YAO_LIAN(40002, "不可以赞自己"),

	ROOM_CREATED(50001, "房间已经创建"),

	ROOM_IN_NOT_MATCH(50002, "进入的房间不匹配"),

	ROLE_NOT_OP(50003, "当前角色不可操作"),

	ROOM_STATUS_NOT_OP(50004, "当前房间状态不可进行此操作"),

	CAN_NOT_USE_FAST_CARD(50005, "当前状态不可用快进卡"),

	DICE_OVERFLOW(50006, "牛吹大了，没那么多骰子"),

	INVALID_OPTION(50007, "无效选项"),

	INSUFFICIENT_HORN(50008, "喇叭数量不足"),

	ONLY_CHINEASE(50009, "请输入汉字"),

	TOO_FREQUENTLY(50010, "发言太频繁"),

	PHONE_NUMBER_FORMAT_ERROR(50011, "手机号格式错误"),

	ADD_COIN_ERROR(50012, "加币失败"),

	USER_NOT_EXISTS(50013, "用户不存在"),

	GPS_POSITION_ERROR(50014, "定位出错"),

	NET_WORK_ERROR(50015, "网络异常"),

	// 绑定
	USER_HAVEBINDTHIRD(60001, "用户已绑定第三方帐号"),

	THIRD_HAVEBIND(60002, "该第三方帐号已被绑定"),

	PHONE_HAVEBIND(60003, "该手机号已经被绑定"),
	// 圈子
	TOO_MANY_PIC(31000, "一次最多上传9张照片"),
	// 过于频繁发言
	TOO_COMMENT_FREQUENTLY(32001, "您的发言太过频繁,请稍后再试"),
	// 短期内发言相同
	TOO_COMMENT_SIMILARITY(32002, "您近期内已有相同回复,请重试"),

	// DEL_ARTICLE_OK_FAIL(32003, "精选文章不可被删除"),

	// INVITE_EXPIRED(70000, "吹牛游戏已开局,中途不能进入哦！"),

	SLAVE_UNAVAILABLE(51010, "奴隶不可用"),

	NO_GAIN(51011, "手慢了，被主人收走了"),

	TIME_NOT_ENOUGH(51012, "还没到时间呢"),

	LET_ME_OFF(51013, "给主人留点吧"),

	STEAL_YET(51014, "已经偷过了"),

	MAX_LEVEL(51015, "您已经满级"),

	ROLL_BACK(51016, "操作失败"),

	CANNOT_ABANDON_UR_DOG(51017, "你不能抛弃你的狗"),

	SLAVE_BOUGHT(51018, "TA已经是你的奴隶了"),

	SLAVE_PROTECT(51019, "交易保护中"),

	SLAVE_MAX(51020, "奴隶数量上限"),

	GUARD_FAIL(51021, "该玩家正在被守护,暂时无法抢夺!"),

	ROOM_CREATE_TYPE_ERROR(52000, "该房间类型不可创建"),

	KICK_ERROR(52001, "游戏已经开始，无法踢人"),

	ACTOR_ERROR(52003, "此人已经不在了"),

	IS_MONSTER(52002, "人人都有守护兽，不用买"),

	CANNOT_BUY_URSELF(52004, "不能买自己"),

	HAS_SAME_WORK(52005, "已经有过相同词汇"),

	NOT_OPEN_GAME(52006, "请先开通游戏"),

	TOO_MANY_PEOPLE(53000, "进入房间人数过多，请重试"),

	SOMEONE_NOT_READY(53001, "所有人准备后才能开始游戏"),

	ADD_FRIEND_FIRST(53002, "加TA好友才能进行该操作"),

	DIRTY_WORDS(53003, "内容包含敏感词"),

	CALLBACK_YET(53004, "已经召唤过了,明天再来吧"),

	CALLBACK_YET_HOUR(53005, "已经召唤过了,过一小时再来吧"),

	WAS_CHECKED(53006, "你已经用完了验人资格"),

	WAS_SAVED(53007, "你已经没有解药了"),

	WAS_POISONED(53008, "你已经没有毒药了"),

	CANNOT_USE_TOGETHER(53009, "不能在同一局使用毒药和解药"),

	WAS_VOTED(53010, "已经投过票了"),

	CANNOT_VOTE(53011, "你已经无法投票了"),

	SEATNO_ERROR(53012, "错误的座位号"),

	SEAT_BE_USE(53013, "座位被占了"),

	CANNOT_SAVEYOURSELF(53014, "无法救自己"),

	CANNOT_CLIMB_TREE(53015, "已准备,无法进入观众席"),

	ON_TREE_YET(53016, "已经在观众席了"),

	BUY_FAIL(53017, "被人抢走了"),

	BUY_YET(53018, "重复购买"),

	START_YET(53019, "游戏开始,无法退出"),

	SOMEBODY_OUT(53020, "有人退出了房间"),

	REPORT_YET(53021, "已经举报过了"),

	IN_FORBIDDEN(53022, "无法进入游戏"),

	INVITE_INVALID(53023, "邀请已经失效"),

	PRAISE_YET(53024, "已经赞过了"),

	WINCOUNTNOTENOUGH(53025, "胜场不足,无法进入高级场"),

	INGAME(53026, "有未完成游戏"),

	NODE_NO_CHESS(54001, "该坐标没有棋子,不能操作"),

	NODE_CHESS_WALK(54002, "该坐标棋子可以直接行走"),

	NOT_YOUR_PLAYTIME(54003, "不该你走"),

	NOT_YOUR_CHESS(54005, "不是你的棋子"),

	NOT_INNING_GIVEUP(54006, "步数不足,不能认输"),

	COLOR_SAME(54007, "颜色相同,不能行走"),

	TAG_COUNT_MAX(54008, "标签数量上限"),

	GUESS_GUESSTIME_ERR(54009, "截止下注时间必须在十分钟以上"),

	GUESS_ITEMS_ERR(54010, "猜猜选项数量有误"),

	GUESS_MAX3(54013, "用户每天最多发起3次猜猜"),

	GUESS_VIP3_ERR(54011, "VIP3才能发起猜猜"),

	GUESS_VIP1_ERR(54012, "VIP1才能进入猜猜"),

	GUESS_ITEM_MAX_ERR(54013, "单注最大金额不能超20000"),

	GUESS_POINT30_ERR(54014, "用户等级LV30才能发起猜猜"),

	GUESS_DISMISS_ERR(54015, "不是你发起的猜猜，解散失败"),

	GUESS_MODIFY_ERR(54016, "开奖时间超过一分钟，不允许修改！"),

	GUESS_ITEM_MIN_ERR(54013, "单注最低下注金额1000"),

	CANNOT_COMMENT(54020, "不允许评论"),

	MODIFY_LABEL_ERR(54030, "没有修改签名的资格,无法修改！"),

	CANNOTDICEWITHOUTVIP(54031, "vip才可以加入私人吹牛！"),

	DICEMAX(54032, "每局最多押注20000金币"),

	PUTDOWNWRONG(54033, "请把棋子下在棋盘上"),

	HASCHESS(54034, "此位置已经有子了"),

	NOTYOURTURN(54035, "不是你的回合"),

	WAIT_NEXT_ROUND(54036, "您已选择,请等待下一回合"),

	ALRAEDY_SWEEP(54037, "这个地方已经被扫过了, 换个地方吧"),

	CANNOT_PUTLEVER(54038, "此处不能放置杆"),

	PALCE_HAVELEVER(54039, "此处已经放置过杆了"),

	CANNOT_BLOCKED_ROAD(54040, "不能把路堵死哦"),

	COORDINATE_ERROR(54041, "坐标有误,无法行走"),
	
	LEVERCNT_ERROR(54042, "杆已经没有了,不能再放了"),

	HAVE_OBLIGATION_ROOM(60000, "存在未付款房间,无法进行抽取!"),

	NO_SALES_ROOM(60001, "房间都被抽走完了  等会儿再过来看吧!"),

	CANNOT_UPSET_ROOMINFO(60002, "房间类型修改失败,请稍后重试!"),

	DICE_VIP_LEVEL_LOW(60003, "vip等级不足,无法切换为吹牛游戏!"),

	ROOM_COUNT_MAX(60004, "每人最多可拥有3个包间，超出无法再次购买或抽取!"),;

	private int recode = 0;
	private String msg;

	private ReCode(int recode) {
		this.recode = recode;
	}

	private ReCode(int recode, String msg) {
		this.recode = recode;
		this.msg = msg;
	}

	public int reCode() {
		return recode;
	}

	public String getMsg() {
		return msg;
	}
}
