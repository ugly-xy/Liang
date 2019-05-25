package com.zb.models.article;

import com.zb.models.AbstractDocument;

public class ArticleShareLog extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6016361050004072890L;

	private long aid;
	private long userId;
	private String platform;

	public ArticleShareLog() {

	}

	public ArticleShareLog(long id, long aid, long uid, String platform) {
		this._id = id;
		this.aid = aid;
		this.userId = uid;
		this.platform = platform;
		super.updateTime = System.currentTimeMillis();
	}

	public long getAid() {
		return aid;
	}

	public void setAid(long aid) {
		this.aid = aid;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}


}