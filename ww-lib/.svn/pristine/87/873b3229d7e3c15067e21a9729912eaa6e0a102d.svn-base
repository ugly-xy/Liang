package com.zb.models.third;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class OpenUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9191501442272983663L;

	public OpenUser(String id, Long uid, String appId, Long provider) {
		this.updateTime = System.currentTimeMillis();
		this._id = id;
		this.uid = uid;
		this.appId = appId;
		this.provider = provider;
	}

	private String _id;// OpenId
	private String appId;// 对应App中的_id
	private Long provider;// 对应商户id
	private Long uid;// 用户id
	private Long updateTime;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public Long getProvider() {
		return provider;
	}

	public void setProvider(Long provider) {
		this.provider = provider;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

}
