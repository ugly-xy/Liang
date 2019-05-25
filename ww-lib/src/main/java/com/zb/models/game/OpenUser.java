package com.zb.models.game;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import com.zb.common.Constant.Const;
import com.zb.models.AbstractDocument;

@Document
public class OpenUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9191501442272983663L;

	public OpenUser(String id, String appKey, Long uid, Long appId,
			Long provider) {
		this.updateTime = System.currentTimeMillis();
		this._id = id;
		this.uid = uid;
		this.appKey = appKey;
		this.appId = appId;
		this.provider = provider;
	}

	private String _id;//
	private Long appId;
	private Long provider;
	private String appKey; //
	private Long uid;
	private Long updateTime;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
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

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Long getProvider() {
		return provider;
	}

	public void setProvider(Long provider) {
		this.provider = provider;
	}

}
