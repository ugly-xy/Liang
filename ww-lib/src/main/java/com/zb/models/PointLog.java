package com.zb.models;

public class PointLog extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5096494720335654833L;

	private Long userId;
	private Integer opType;
	private int point;
	private int balance;
	private Long adminId;

	public PointLog() {

	}

	public PointLog(Long id, Long userId, int opType, int point, int balance,
			Long adminId) {
		super._id = id;
		this.userId = userId;
		updateTime = System.currentTimeMillis();
		this.opType = opType;
		this.point = point;
		this.balance = balance;
		this.adminId = adminId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getOpType() {
		return opType;
	}

	public void setOpType(Integer opType) {
		this.opType = opType;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}
}
