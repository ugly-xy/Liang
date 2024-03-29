package com.we.models;

/** 推荐好友统计 */
public class UserShare extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6569446119108478690L;

	public static final String TOTAL_CNT = "totalCnt";

	private int totalCnt;// 总推荐人数
	private long amount;// 通过邀请网络获取的货币数
	private Long puid ;//上级用户id
	// 各级别推荐人数
	private int m1;
	private int m2;
	private int m3;

	public int getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}

	public int getM1() {
		return m1;
	}

	public void setM1(int m1) {
		this.m1 = m1;
	}

	public int getM2() {
		return m2;
	}

	public void setM2(int m2) {
		this.m2 = m2;
	}

	public int getM3() {
		return m3;
	}

	public void setM3(int m3) {
		this.m3 = m3;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public UserShare() {
		super();
	}

	public UserShare(long uid) {
		super();
		this._id = uid;
		this.updateTime = System.currentTimeMillis();
	}

	public UserShare(long uid, int totalCnt, int m1, int m2, int m3) {
		super();
		this._id = uid;
		this.updateTime = System.currentTimeMillis();
		this.totalCnt = totalCnt;
		this.m1 = m1;
		this.m2 = m2;
		this.m3 = m3;
	}

	public Long getPuid() {
		return puid;
	}

	public void setPuid(Long puid) {
		this.puid = puid;
	}
	

}
