package com.zb.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UpdatePwdLog extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9191501442272983663L;

	public UpdatePwdLog() {

	}

	public UpdatePwdLog(Long userId, String ip, int status, int type,
			long adminId) {
		this.updateTime = System.currentTimeMillis();
		this.userId = userId;
		this.ip = ip;
		this.status = status;
		this.type = type;
		this.adminId = adminId;
	}

	private Long userId;

	private String ip;

	private Integer status = 1;
	private int type;
	private long adminId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getAdminId() {
		return adminId;
	}

	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}

}
