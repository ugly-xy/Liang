package com.zb.models.article;

import com.zb.models.AbstractDocument;

public class ArticleReadLog extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6016361050004072890L;

	private long aid;
	private long userId;
	private String nickname;

	public ArticleReadLog() {

	}

	public ArticleReadLog(long id, long aid, long uid,String nickname) {
		this._id = id;
		this.aid = aid;
		this.userId = uid;
		this.nickname = nickname;
		updateTime = System.currentTimeMillis();
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}