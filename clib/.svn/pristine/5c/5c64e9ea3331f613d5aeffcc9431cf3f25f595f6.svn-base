package com.we.models.article;

import com.we.models.AbstractDocument;

public class TipOff extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6016361050004072890L;
	
	public static int ARTICLE = 1;
	public static int USER = 2;
	
	private String content;
	private Long aid;
	private Long uid;
	private int type = 1;

	public TipOff() {

	}

	public TipOff(long id, String content, Long aid, Long uid) {
		this._id = id;
		this.content = content;
		this.aid = aid;
		this.uid = uid;
		this.type=ARTICLE;
		super.updateTime = System.currentTimeMillis();
	}

	public TipOff(long id, String content, Long aid, Long uid,Integer type) {
		this._id = id;
		this.content = content;
		this.aid = aid;
		this.uid = uid;
		this.type=type;
		super.updateTime = System.currentTimeMillis();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getAid() {
		return aid;
	}

	public void setAid(Long aid) {
		this.aid = aid;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}