package com.zb.models.finance;

import com.zb.models.AbstractDocument;

public class CoinLog extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1253943538773764911L;
	public static final int IN = 1;
	public static final int OUT = 2;

	public static final int IN_CASH = 1;
	public static final int IN_SIGN = 2;// 签到收入
	public static final int IN_ADMIN = 3;// 管理员加币
	public static final int IN_LOGIN3DAY = 5;// 注册前三天送币
	public static final int IN_ROBIT_FLOWER = 10;// 机器人加币送花

	public static final int OUT_OPEN_ORDER = 1;
	public static final int OUT_RESIGN = 2;// 补签支出
	public static final int OUT_ADMIN = 3;// 管理员扣币
	// public static final int OUT_FAST_CARD = 3;
	public static final int OUT_PROP = 6;// 使用道具
	public static final int OUT_GIFT = 7;// 送礼物
	public static final int OUT_RENAME = 8; // 改名
	public static final int OUT_ADD_TAG = 11; // 增加标签
	public static final int OUT_BUYROOM = 12; // 购买房间

	public static final int TASK_DONE = 101;
	public static final int REDEEM_COIN = 102;// 兑换金币
	public static final int RANKLIST = 111;
	public static final int VALIDWORD_OK = 112;// 词语审核通过加币
	public static final int GROUP = 113; // 圈子奖励
	public static final int ONETASK = 114;// 活动加金币
	// public static final int LEVELUP = 115;// 升级奖励
	public static final int GUESS = 116;// 猜猜

	public static final int UNDERCOVER = 130;
	public static final int DRAW_SOMETHING = 131;
	public static final int DICE = 132;
	public static final int SLOT_MACHINES = 133;
	public static final int WEREWOLF = 136;
	public static final int SOUTHPENGUIN = 139;
	public static final int SCHOOLWAR = 140;
	public static final int ANIMAL_CHESS = 141;
	public static final int GOMOKU = 142;
	public static final int SAKURAN = 143;
	public static final int WINMINE = 145;// 扫雷
	public static final int NEURO_CAT = 146;// 神经猫
	public static final int COW=147;//牛牛
	public static final int TEXAS=152;//德州

	public static final int SLAVE_WORK = 150;
	public static final int SLAVE_STEAL = 151;
	public static final int STAR_UP_GRADE = 154;
	public static final int BUY_SLAVE = 153;

	private int io;// 1收入 2 支出
	private int type;// 收入类型 1充值
						// 支出类型 购买商品200
	private long linkId;
	private long uid;// 用户
	private int amount;// 数量
	private int balance;// 余额
	private long adminId;
	private String detail;// 详情

	public CoinLog() {

	}

	public CoinLog(Long id, int io, int type, long linkId, long uid, int amount, int balance, String detail,
			long adminId) {
		this._id = id;
		this.io = io;
		this.type = type;
		this.uid = uid;
		this.amount = amount;
		this.updateTime = System.currentTimeMillis();
		this.balance = balance;
		this.detail = detail;
		this.linkId = linkId;
		this.adminId = adminId;
	}

	public int getIo() {
		return io;
	}

	public void setIo(int io) {
		this.io = io;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public long getLinkId() {
		return linkId;
	}

	public void setLinkId(long linkId) {
		this.linkId = linkId;
	}

	public long getAdminId() {
		return adminId;
	}

	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}

}
