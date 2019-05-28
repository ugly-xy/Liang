package com.we.models.server;

import java.io.Serializable;

public class ServerInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2856730391132858449L;
	
	private String _id;//ip:port
	private String type;
	private int connectCnt;//连接数
	private int maxCnt;//最大连接数
	private int availableCnt;//可用连接数 
	private long updateTime;//
	private int status;
	
	public enum ServerType{
		socket,websocket
	}
	
	public ServerInfo(String host,int port,String type,int connectCnt,int maxCnt,int status){
		this._id = host+":"+port;
		this.type = type;
		this.connectCnt = connectCnt;
		this.maxCnt = maxCnt;
		this.availableCnt = maxCnt - connectCnt;
		this.updateTime = System.currentTimeMillis();
		this.status = status;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
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

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getMaxCnt() {
		return maxCnt;
	}

	public void setMaxCnt(int maxCnt) {
		this.maxCnt = maxCnt;
	}

	public int getAvailableCnt() {
		return availableCnt;
	}

	public void setAvailableCnt(int availableCnt) {
		this.availableCnt = availableCnt;
	}
	
}
