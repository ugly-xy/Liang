package com.zb.models;

import com.zb.common.http.UserAgent;

public class LoginLog extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5096494720335654833L;

	private Long userId;
	private Integer day;
	private String ip;
	private boolean status;
	private UserAgent userAgent ;
	private String devId;
	private Double version;
	private Integer via;
	private Integer loginType;
	private String lbs;

	public LoginLog() {

	}

	public LoginLog(Long id, Long userId, Integer day, String ip,
			Long createTime, boolean status, UserAgent userAgent, String devId,
			Double version,int via,int loginType,String lbs) {
		super._id = id;
		this.userId = userId;
		this.day = day;
		this.ip = ip;
		updateTime = createTime;
		this.status = status;
		this.userAgent = userAgent;
		this.devId = devId;
		this.version = version;
		this.via = via;
		this.loginType = loginType;
		this.lbs = lbs;
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


	public UserAgent getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(UserAgent userAgent) {
		this.userAgent = userAgent;
	}

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Double getVersion() {
		return version;
	}

	public void setVersion(Double version) {
		this.version = version;
	}

	public Integer getVia() {
		return via;
	}

	public void setVia(Integer via) {
		this.via = via;
	}

	public Integer getLoginType() {
		return loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}

	public String getLbs() {
		return lbs;
	}

	public void setLbs(String lbs) {
		this.lbs = lbs;
	}

}
