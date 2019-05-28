package com.zb.models.slave;

import com.zb.models.AbstractDocument;

public class SlaveJobs extends AbstractDocument {

	private static final long serialVersionUID = -1955279390927580608L;
	private int state = UNREVIEW; // 0 未审核 1审核过 2审核未过
	private String word;
	public static final int UNREVIEW = 0;
	public static final int REVIEWED = 1;
	public static final int REJECTED = -1;

	public SlaveJobs() {
	}

	public SlaveJobs(long id, long updateTime, int state, String word) {
		this._id = id;
		this.updateTime = updateTime;
		this.state = state;
		this.word = word;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word.trim();
	}

}
