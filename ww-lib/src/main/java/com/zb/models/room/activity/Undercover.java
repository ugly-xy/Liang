package com.zb.models.room.activity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.zb.models.room.Activity;

public class Undercover extends Activity {

	public static final int STATUS_START = 2;// 开始
	public static final int STATUS_SPEAK = 10;// 发言阶段
	public static final int STATUS_VOTE = 11;// 投票阶段
	public static final int STATUS_PK_SPEAK = 12;// PK发言阶段
	public static final int STATUS_PK_VOTE = 13;// PK投票阶段
	public static final int STATUS_PK_DICE = 14;// PK摇骰子阶段
	public static final int STATUS_END = 15;// 结束阶段
	public static final int STATUS_TO_NEXT = 16;// 进入下一轮
	public static final int STATUS_PUNISH = 17;// 今日奖惩阶段

	/**
	 * 
	 */
	private static final long serialVersionUID = 6895731734124099392L;
	private int inning = 0;// 第几局
	private int winner;
	private String ucWord, cWord;// ucWord 卧底词
	private boolean job = false;
	private long undercover;// 卧底ID
	private Set<Long> actors = new HashSet<Long>();//
	private Set<Long> pks = new HashSet<Long>();// pk的用户
	private Set<Long> speaks = new HashSet<Long>();// 已经发言的用户
	private Set<Long> votes = new HashSet<Long>();// 已经投票的用户
	private Set<Long> deaths = new HashSet<Long>();// 已死亡的人
	// private Set<Long> outs;// 离开房间的人
	private Map<Integer, Long> dices = new HashMap<Integer, Long>();

	public void clearActors() {
		actors.clear();
	}

	public void clearPks() {
		pks.clear();
	}

	public void clearSpeaks() {
		speaks.clear();
	}

	public void clearVotes() {
		votes.clear();
	}

	public void clearDices() {
		dices.clear();
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

	public Set<Long> getPks() {
		return pks;
	}

	public void setPks(Set<Long> pks) {
		this.pks = pks;
	}

	public Set<Long> getSpeaks() {
		return speaks;
	}

	public void setSpeaks(Set<Long> speaks) {
		this.speaks = speaks;
	}

	public String getUcWord() {
		return ucWord;
	}

	public void setUcWord(String ucWord) {
		this.ucWord = ucWord;
	}

	public String getcWord() {
		return cWord;
	}

	public void setcWord(String cWord) {
		this.cWord = cWord;
	}

	public void addPkActor(Long aid) {
		pks.add(aid);
	}

	public void addActor(Long aid) {
		actors.add(aid);
	}

	public void removeActor(Long aid) {
		actors.remove(aid);
	}

	public void addDice(Integer d, Long aid) {
		dices.put(d, aid);
	}

	public void delActor(Long aid) {
		actors.remove(aid);
	}

	public void addSpeak(Long aid) {
		speaks.add(aid);
	}

	public void addVote(Long aid) {
		votes.add(aid);
	}

	public void addDie(Long aid) {
		deaths.add(aid);
	}

	public Set<Long> getActors() {
		return actors;
	}

	public void setActors(Set<Long> actors) {
		this.actors = actors;
	}

	public Set<Long> getVotes() {
		return votes;
	}

	public void setVotes(Set<Long> votes) {
		this.votes = votes;
	}

	public Set<Long> getDeaths() {
		return deaths;
	}

	public void setDeaths(Set<Long> deaths) {
		this.deaths = deaths;
	}

	public Map<Integer, Long> getDices() {
		return dices;
	}

	public void setDices(Map<Integer, Long> dices) {
		this.dices = dices;
	}

	public long getUndercover() {
		return undercover;
	}

	public void setUndercover(long undercover) {
		this.undercover = undercover;
	}

	public boolean isJob() {
		return job;
	}

	public void setJob(boolean job) {
		this.job = job;
	}

}
