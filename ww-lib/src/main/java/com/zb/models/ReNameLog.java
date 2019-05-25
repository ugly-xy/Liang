package com.zb.models;

public class ReNameLog extends AbstractDocument {
	private static final long serialVersionUID = 7179588554173497462L;
	public static int DEFAULT_MONEY = 50;
	private int count = 0;

	public ReNameLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public ReNameLog(long uid, int count) {
		super();
		this._id = uid;
		this.count = count;
	}
}