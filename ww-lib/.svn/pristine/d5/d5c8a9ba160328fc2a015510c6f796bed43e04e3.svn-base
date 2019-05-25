package com.zb.models.room.activity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.zb.models.room.Activity;

public class DrawSomething extends Activity {

	public static final int STATUS_START = 2;// 开始
	public static final int STATUS_SELECT_WORD = 10;// 选词
	public static final int STATUS_DRAWING = 11;// 绘画
	public static final int STATUS_SHOW = 12;// 展示阶段
	public static final int STATUS_END = 15;// 结束阶段
	// public static final int STATUS_TO_NEXT = 16;// 进入下一轮
	public static final int STATUS_PUNISH = 17;// 今日奖惩阶段

	public static final int DRAW_INIT = 1;
	public static final int DRAWING = 2;
	public static final int DRAWED = 3;
	public static final int NOT_DRAW = 4;
	/**
	 * 
	 */
	private static final long serialVersionUID = 6895731734124099392L;
	private int inning = 0;// 第几局
	private Set<Long> winner = new HashSet<Long>();
	private Set<Long> loser = new HashSet<Long>();
	private Set<String> words = new HashSet<String>();
	private Map<String, String> gws = new HashMap<String, String>();
	private Set<Long> rights = new HashSet<Long>();
	private String word;
	private String tip;
	private boolean job = false;
	private Map<Long, Integer> actors = new LinkedHashMap<Long, Integer>();//
	private int reWordCnt = 1;
	private int drawCnt = 0;
	private int dlCount = 0;

	public void clearActors() {
		actors.clear();
	}

	public Set<Long> getWinner() {
		return winner;
	}

	public void setWinner(Set<Long> winner) {
		this.winner = winner;
	}

	public void addWinner(long winner) {
		this.winner.add(winner);
	}

	public void clearWinner() {
		this.winner.clear();
	}

	public Set<Long> getLoser() {
		return loser;
	}

	public void setLoser(Set<Long> loser) {
		this.loser = loser;
	}

	public void addLoser(long loser) {
		this.loser.add(loser);
	}

	public void clearLoser() {
		this.loser.clear();
	}

	public int getInning() {
		return inning;
	}

	public void setInning(int inning) {
		this.inning = inning;
	}

	public void putActor(Long aid, Integer status) {
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

	public Set<Long> getRights() {
		return rights;
	}

	public void setRights(Set<Long> rights) {
		this.rights = rights;
	}

	public void addRight(long uid) {
		rights.add(uid);
	}

	public void clearRights() {
		rights.clear();
	}

	public int getReWordCnt() {
		return reWordCnt;
	}

	public void setReWordCnt(int reWordCnt) {
		this.reWordCnt = reWordCnt;
	}

	public int getDrawCnt() {
		return drawCnt;
	}

	public void setDrawCnt(int drawCnt) {
		this.drawCnt = drawCnt;
	}

	public int getDlCount() {
		return dlCount;
	}

	public void setDlCount(int dlCount) {
		this.dlCount = dlCount;
	}
	
}
