package com.we.models.article;

import com.we.models.AbstractDocument;

public class ArticlePraiseLog extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6016361050004072890L;

	private long aid;
	private long userId;
	private String nickname;
	private int type;

	public ArticlePraiseLog() {

	}

	public ArticlePraiseLog(long id, long aid, long uid, String nickname,
			int type) {
		this._id = id;
		this.aid = aid;
		this.userId = uid;
		this.nickname = nickname;
		super.updateTime = System.currentTimeMillis();
		this.type = type;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	

}