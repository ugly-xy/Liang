package com.zb.models.room.activity;

import java.io.Serializable;
import java.util.Map;
import java.util.List;


public class DrawLog implements Serializable {

	private static final long serialVersionUID = -3186466963193923470L;
	private String word;
	private int score;
	private String _id;
	private int status = 1;
	private long uid;
	private long updateTime;
	public DrawLog() {

	}

	public DrawLog(String id, String word, long uid) {
		this._id = id;
		this.word = word;
		this.uid = uid;
		this.updateTime = System.currentTimeMillis();
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}


	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	
}
