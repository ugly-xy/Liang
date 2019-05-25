package com.zb.models.article;

import com.zb.models.AbstractDocument;

public class ArticleCommentPraiseLog extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6016361050004072890L;

	private long cid;
	private long userId;

	public ArticleCommentPraiseLog() {

	}

	public ArticleCommentPraiseLog(long id, long cid, long uid) {
		this._id = id;
		this.cid = cid;
		this.userId = uid;
		super.updateTime = System.currentTimeMillis();
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCid() {
		return cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

}