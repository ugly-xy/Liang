package com.zb.models.room.activity;


import com.zb.models.AbstractDocument;

public class DrawGuessLog extends AbstractDocument {

	private static final long serialVersionUID = -3186466963193923470L;
	private String word;
	private String guess;
	private  int status = 1;

	public DrawGuessLog() {

	}

	public DrawGuessLog(long id, String word, String guess) {
		this._id = id;
		this.word = word;
		this.guess = guess;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getGuess() {
		return guess;
	}

	public void setGuess(String guess) {
		this.guess = guess;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
