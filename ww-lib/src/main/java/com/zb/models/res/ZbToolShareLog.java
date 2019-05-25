package com.zb.models.res;

import com.zb.models.AbstractDocument;

public class ZbToolShareLog extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6016361050004072890L;

	private long tid;
	private long userId;
	private String platform;

	public ZbToolShareLog() {

	}

	public ZbToolShareLog(long id, long tid, long uid, String platform) {
		this._id = id;
		this.tid = tid;
		this.userId = uid;
		this.platform = platform;
		super.updateTime = System.currentTimeMillis();
	}

	public long getTid() {
		return tid;
	}

	public void setTid(long tid) {
		this.tid = tid;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

}