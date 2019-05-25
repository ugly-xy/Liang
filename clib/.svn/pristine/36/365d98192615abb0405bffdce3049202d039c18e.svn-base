package com.we.models.server;

import com.we.models.AbstractDocument;

public class ServerInfoLog extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2856730391132858449L;

	private String sid;// ip:port
	private String type;
	private int connectCnt;// 连接数
	private int maxCnt;// 连接数
	private int status;

	public ServerInfoLog(long id, ServerInfo si) {
		this._id = id;
		this.sid = si.get_id();
		this.type = si.getType();
		this.connectCnt = si.getConnectCnt();
		this.updateTime = si.getUpdateTime();
		this.maxCnt = si.getMaxCnt();
		this.status = si.getStatus();
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public int getMaxCnt() {
		return maxCnt;
	}

	public void setMaxCnt(int maxCnt) {
		this.maxCnt = maxCnt;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getConnectCnt() {
		return connectCnt;
	}

	public void setConnectCnt(int connectCnt) {
		this.connectCnt = connectCnt;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
