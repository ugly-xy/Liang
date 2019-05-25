package com.we.models;

/** 提现Log */
public class WithdrawLog extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1593700338493985545L;
	private long uid;// 用户
	private Double amount;// 数量
	private String coinType;// 货币类型
	private String walletAddress;// 提现地址
	private Long adminId;
	private int status;// 状态
	private long createTime;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCoinType() {
		return coinType;
	}

	public void setCoinType(String coinType) {
		this.coinType = coinType;
	}

	public String getWalletAddress() {
		return walletAddress;
	}

	public void setWalletAddress(String walletAddress) {
		this.walletAddress = walletAddress;
	}

	public WithdrawLog() {
		super();
	}

	public WithdrawLog(long uid, Double amount, Long adminId, int status, String coinType, String walletAddress) {
		super();
		this.uid = uid;
		this.amount = amount;
		this.adminId = adminId;
		this.status = status;
		this.walletAddress = walletAddress;
		this.coinType = coinType;
		this.createTime = System.currentTimeMillis();
		this.updateTime = this.createTime;

	}

}
