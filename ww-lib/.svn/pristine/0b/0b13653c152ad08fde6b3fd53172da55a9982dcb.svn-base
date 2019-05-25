package com.zb.models.room.activity;

import com.zb.models.AbstractDocument;

public class UndercoverWord extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6895731734124099392L;
	

	public UndercoverWord() {

	}

	public UndercoverWord(Long id, String []words, int status,long uid,String provider) {
		this._id = id;
		this.words = words;
		this.status = status;
		this.updateTime = System.currentTimeMillis();
		this.uid = uid;
		this.provider = provider;
	}

	private String[] words;
	private int status;
	private long uid;
	private String provider;


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String[] getWords() {
		return words;
	}

	public void setWords(String[] words) {
		this.words = words;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

}
