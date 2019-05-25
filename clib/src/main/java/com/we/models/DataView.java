package com.we.models;

/** 首页的数据展示 */
public class DataView extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7334457466972440447L;

	private int projectCnt;// 总项目数
	private long regCnt;// 注册总人数
	private long sendCandyCnt;// 送出的candy总数量
	private long candyCnt;// candy总发行量

	public int getProjectCnt() {
		return projectCnt;
	}

	public void setProjectCnt(int projectCnt) {
		this.projectCnt = projectCnt;
	}

	public long getRegCnt() {
		return regCnt;
	}

	public void setRegCnt(long regCnt) {
		this.regCnt = regCnt;
	}

	public long getSendCandyCnt() {
		return sendCandyCnt;
	}

	public void setSendCandyCnt(long sendCandyCnt) {
		this.sendCandyCnt = sendCandyCnt;
	}

	public long getCandyCnt() {
		return candyCnt;
	}

	public void setCandyCnt(long candyCnt) {
		this.candyCnt = candyCnt;
	}

	public DataView(int projectCnt, long regCnt, long sendCandyCnt, long candyCnt) {
		super();
		this.projectCnt = projectCnt;
		this.regCnt = regCnt;
		this.sendCandyCnt = sendCandyCnt;
		this.candyCnt = candyCnt;
	}

	public DataView() {
		super();
	}

}
