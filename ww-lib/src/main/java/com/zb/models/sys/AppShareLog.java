package com.zb.models.sys;

import com.zb.models.AbstractDocument;

public class AppShareLog extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6016361050004072890L;

	private double ver;
	private long userId;
	private int via;

	public AppShareLog() {

	}

	public AppShareLog(long id, double ver, long uid, int via) {
		this._id = id;
		this.ver = ver;
		this.userId = uid;
		this.via = via;
		super.updateTime = System.currentTimeMillis();
	}

	public double getVer() {
		return ver;
	}

	public void setVer(double ver) {
		this.ver = ver;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getVia() {
		return via;
	}

	public void setVia(int via) {
		this.via = via;
	}

}