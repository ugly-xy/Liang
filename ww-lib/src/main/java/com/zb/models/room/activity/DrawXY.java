package com.zb.models.room.activity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.zb.models.room.Activity;

public class DrawXY implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6895731734124099392L;
	private int inning = 0;// 第几局
	private int winner;
	private Set<String> words = new HashSet<String>();
	private Map<String, String> gws = new HashMap<String, String>();
	private String word;
	private String tip;
	private boolean job = false;
	private Map<Long, Integer> actors = new LinkedHashMap<Long, Integer>();//

	public void clearActors() {
		actors.clear();
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public int getInning() {
		return inning;
	}

	public void setInning(int inning) {
		this.inning = inning;
	}

	public void addActor(Long aid, Integer status) {
		actors.put(aid, status);
	}

	public void removeActor(Long aid) {
		actors.remove(aid);
	}

	public void delActor(Long aid) {
		actors.remove(aid);
	}

	public Map<Long, Integer> getActors() {
		return actors;
	}

	public void setActors(Map<Long, Integer> actors) {
		this.actors = actors;
	}

	public boolean isJob() {
		return job;
	}

	public void setJob(boolean job) {
		this.job = job;
	}

	public Set<String> getWords() {
		return words;
	}

	public void setWords(Set<String> words) {
		this.words = words;
	}

	public Map<String, String> getGws() {
		return gws;
	}

	public void setGws(Map<String, String> gws) {
		this.gws = gws;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
		this.words.add(word);
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

}
