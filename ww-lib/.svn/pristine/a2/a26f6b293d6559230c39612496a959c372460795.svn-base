package com.zb.socket.model;

import java.io.Serializable;

public class SocketUserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2463331480052960452L;

	public static final int S_OK = 1;
	public static final int S_REC_OTHER = 2;
	public static final int S_REC_SELF = 3;

	public SocketUserInfo() {

	}

	// public SocketUserInfo(String sessionId, String host, int port) {
	// this.sessionId = sessionId;
	// this.host = host;
	// this.port = port;
	// }

	public SocketUserInfo(String sid, long time, int st, boolean self) {
		this.sid = sid;
		this.time = time;
		this.st = st;
		this.self = self;
	}

	private String sid;
	private long rid;//房间ID
	private int t;//房间类型
	private long time;
	private int st;
	private boolean self;
	private boolean sleep;

	// private String lbs;
	// private String city;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public boolean isSleep() {
		return sleep;
	}

	public void setSleep(boolean sleep) {
		this.sleep = sleep;
	}

	public long getRid() {
		return rid;
	}

	public void setRid(long rid) {
		this.rid = rid;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getSt() {
		return st;
	}

	public void setSt(int st) {
		this.st = st;
	}

	public boolean isSelf() {
		return self;
	}

	public void setSelf(boolean self) {
		this.self = self;
	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}
}
