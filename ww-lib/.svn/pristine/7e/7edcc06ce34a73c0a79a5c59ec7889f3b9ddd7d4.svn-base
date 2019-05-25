package com.zb.models.third;

import org.springframework.data.mongodb.core.mapping.Document;

import com.zb.models.AbstractDocument;

@Document
public class AppLog extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9191501442272983663L;

	public AppLog(Long id, Long userId, String appId) {
		this.updateTime = System.currentTimeMillis();
		this.appId = appId;
		this.userId = userId;
		this._id = id;
	}

	private String appId;
	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

}