package com.zb.models.room.activity;


import com.zb.common.Constant.Const;
import com.zb.models.AbstractDocument;

public class UndercoverSpeakLog extends AbstractDocument {

	private static final long serialVersionUID = -3186466963193923470L;
	private String word;
	private String speak;
	private int status = Const.STATUS_DEF;
	

	public UndercoverSpeakLog() {

	}

	public UndercoverSpeakLog(long id, String word, String speak,int status) {
		this._id = id;
		this.word = word;
		this.speak = speak;
		this.status = status;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSpeak() {
		return speak;
	}

	public void setSpeak(String speak) {
		this.speak = speak;
	}

}