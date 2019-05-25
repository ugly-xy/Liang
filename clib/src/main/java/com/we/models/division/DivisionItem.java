package com.we.models.division;

import java.io.Serializable;


/** 用户段位进度表 */
public class DivisionItem implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8304498507542785333L;
	// 段位任务的进度
	private int cnt;//已经完成数量
	private int status;//1不可领取 2 可领取 3 已完成
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}
