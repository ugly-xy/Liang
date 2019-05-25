package com.zb.models.third;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class OneBuy implements Serializable {

	String _id;
	String openId;
	String appKey;
	String product;
	String luckNo;
	String phase;
	Long updateTime;

	/**
	 * 
	 */
	private static final long serialVersionUID = 9191501442272983663L;

	public OneBuy() {

	}

	public OneBuy(String openId, String appKey, String product, String luckNo,
			String phase) {
		this._id = phase + "-" + luckNo;
		this.openId = openId;
		this.appKey = appKey;
		this.product = product;
		this.luckNo = luckNo;
		this.phase = phase;
		updateTime = System.currentTimeMillis();
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getLuckNo() {
		return luckNo;
	}

	public void setLuckNo(String luckNo) {
		this.luckNo = luckNo;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

}
