package com.zb.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class VersionUpdate extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9191501442272983663L;

	public VersionUpdate() {

	}

	public VersionUpdate(Long id, Double curVersion, Double mustVersion, Integer status, String title,
			String discription, long adminId, Integer ch, Integer childCh, String url, Integer via, String appName) {
		this.updateTime = System.currentTimeMillis();
		this.adminId = adminId;
		this.ch = ch;
		this.childCh = childCh;
		this.curVersion = curVersion;
		this.discription = discription;
		this._id = id;
		this.mustVersion = mustVersion;
		this.status = status;
		this.title = title;
		this.url = url;
		this.via = via;
		this.appName = appName;
	}

	private Double curVersion;
	private Double mustVersion;
	private String title;
	private String discription;

	private Integer status = 1;
	private int ch = 1;
	private int childCh = 1;
	private String url;
	private Integer via;
	private long adminId;
	private String appName;

	public Double getCurVersion() {
		return curVersion;
	}

	public void setCurVersion(Double curVersion) {
		this.curVersion = curVersion;
	}

	public Double getMustVersion() {
		return mustVersion;
	}

	public void setMustVersion(Double mustVersion) {
		this.mustVersion = mustVersion;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public int getCh() {
		return ch;
	}

	public void setCh(int ch) {
		this.ch = ch;
	}

	public int getChildCh() {
		return childCh;
	}

	public void setChildCh(int childCh) {
		this.childCh = childCh;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getVia() {
		return via;
	}

	public void setVia(Integer via) {
		this.via = via;
	}

	public long getAdminId() {
		return adminId;
	}

	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
}
