package com.we.models.finance;

import java.util.HashMap;
import java.util.Map;

import com.we.models.AbstractDocument;
import com.we.models.division.DivisionTask;

public class CoinLog extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1253943538773764911L;
	public static final int IN = 1;
	public static final int OUT = 2;
	
	public static final String DEF_COIN_TYPE = "CCC";

	public static final int IN_ADMIN = 103;// 管理员加币
	public static final int OUT_ADMIN = 104;// 管理员扣币
	public static final int IN_CHEST = 105;// 开宝箱
	public static final int IN_SIGN = 106;// 签到
	public static final int OUT_RESIGN = 107;// 补签
	public static final int IN_TASK = 108;// 任务
	public static final int IN_DIVISION = 109;// 段位任务
	public static final int IN_REG = 111;// 新用户注册
	public static final int IN_INVITE = 112;// 邀请用户
	public static final int OUT_WITH_DRAW = 110;// 提币

	private int io;// 1收入 2 支出
	private int type;// 收入类型 1充值
	private String typeDes;// 类型描述
	private long linkId;
	private String coinType;
	private long uid;// 用户
	private double amount;// 数量
	private Double balance;// 余额
	private long adminId;
	private String detail;// 详情

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

	public long getLinkId() {
		return linkId;
	}

	public void setLinkId(long linkId) {
		this.linkId = linkId;
	}

	public String getCoinType() {
		return coinType;
	}

	public void setCoinType(String coinType) {
		this.coinType = coinType;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public long getAdminId() {
		return adminId;
	}

	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getTypeDes() {
		return typeDes;
	}

	public void setTypeDes(String typeDes) {
		this.typeDes = typeDes;
	}

	public CoinLog(long _id, int io, int type, long linkId, String coinType, long uid, Double amount, Double balance,
			long adminId, String detail) {
		super();
		this._id = _id;
		this.io = io;
		this.type = type;
		this.linkId = linkId;
		this.coinType = coinType;
		this.uid = uid;
		this.amount = amount;
		this.balance = balance;
		this.adminId = adminId;
		this.detail = detail;
		this.updateTime = System.currentTimeMillis();
		this.typeDes = map.containsKey(type)?map.get(type):"";
	}



	@Override
	public String toString() {
		return "CoinLog [io=" + io + ", type=" + type + ", typeDes=\" + typeDes + \", linkId=" + linkId + ", coinType=" + coinType + ", uid=" + uid
				+ ", amount=" + amount + ", balance=" + balance + ", adminId=" + adminId + ", detail=" + detail + "]";
	}
	
	public static  Map<Integer,String> map  = new HashMap<Integer,String>();
	static {
		map.put(CoinLog.IN_ADMIN,"管理员扣币");
		map.put(CoinLog.IN_CHEST,"보물상자");
		map.put(CoinLog.IN_SIGN,"출석 체크");
		map.put(CoinLog.OUT_RESIGN,"补签");
		map.put(CoinLog.IN_TASK,"미션");
		map.put(CoinLog.IN_DIVISION,"레벨 미션");
		map.put(CoinLog.IN_REG,"신규회원 가입");
		map.put(CoinLog.IN_INVITE,"친구 초대");
		map.put(CoinLog.OUT_WITH_DRAW,"코인 출금");
		map.put(DivisionTask.DivisionTaskType.EMAIL.getCode(),DivisionTask.DivisionTaskType.EMAIL.getDes());
		map.put(DivisionTask.DivisionTaskType.USERINFO.getCode(), DivisionTask.DivisionTaskType.USERINFO.getDes());
		map.put(DivisionTask.DivisionTaskType.INVITE_3.getCode(),DivisionTask.DivisionTaskType.INVITE_3.getDes());
		map.put(DivisionTask.DivisionTaskType.FINISH_USER_TASK_5.getCode(),DivisionTask.DivisionTaskType.FINISH_USER_TASK_5.getDes());
		map.put(DivisionTask.DivisionTaskType.INVITE_5.getCode(), DivisionTask.DivisionTaskType.INVITE_5.getDes());
		map.put(DivisionTask.DivisionTaskType.SIGN_WEEK.getCode(), DivisionTask.DivisionTaskType.SIGN_WEEK.getDes());
		map.put(DivisionTask.DivisionTaskType.KYC.getCode(), DivisionTask.DivisionTaskType.KYC.getDes());
		map.put(DivisionTask.DivisionTaskType.FINISH_USER_TASK_10.getCode(),DivisionTask.DivisionTaskType.FINISH_USER_TASK_10.getDes() );
		map.put(DivisionTask.DivisionTaskType.SIGN_30.getCode(), DivisionTask.DivisionTaskType.SIGN_30.getDes());
		map.put(DivisionTask.DivisionTaskType.INVITE_10.getCode(), DivisionTask.DivisionTaskType.INVITE_10.getDes());
		map.put(DivisionTask.DivisionTaskType.SUB_PLATINUM_10.getCode(), DivisionTask.DivisionTaskType.SUB_PLATINUM_10.getDes());
		map.put(DivisionTask.DivisionTaskType.SUB_PLATINUM_30.getCode(), DivisionTask.DivisionTaskType.SUB_PLATINUM_30.getDes());
	}

}
