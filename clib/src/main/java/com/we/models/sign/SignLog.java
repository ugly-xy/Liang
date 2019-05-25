package com.we.models.sign;

import java.io.Serializable;


public class SignLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5096494720335652833L;

	private String _id;// day_userId

	private Long userId;
	private Integer day;
	private String ip;
	private Long updateTime;
	private String devId;
	private Integer via;

	public SignLog() {

	}

	public SignLog(Long userId, Integer day, String ip, 
			String devId, int via) {
		this._id = day + "_" + userId;
		this.userId = userId;
		this.day = day;
		this.ip = ip;
		this.updateTime = System.currentTimeMillis();
		this.devId = devId;
		this.via = via;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}


	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public Integer getVia() {
		return via;
	}

	public void setVia(Integer via) {
		this.via = via;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}


}
