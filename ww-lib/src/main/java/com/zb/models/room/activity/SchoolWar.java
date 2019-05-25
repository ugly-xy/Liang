package com.zb.models.room.activity;

import java.util.LinkedHashMap;
import java.util.Map;

import com.zb.models.room.ActivityCP;

public class SchoolWar extends ActivityCP {
	private static final long serialVersionUID = -4994458192582364392L;
	public static final int STATUS_START = 2;// 开始
	public static final int STATUS_END = 3;// 结束

	private int turnBack = 0;// 老师回头
	private int standup = 0;// 罚站
	private Map<Long, Integer> actors = new LinkedHashMap<>();// 玩家状态
	private Map<Long, Integer> scores = new LinkedHashMap<>();// 积分

	public void clearActors() {
		actors.clear();
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

	public Map<Long, Integer> getScores() {
		return scores;
	}

	public int getScore(long uid) {
		return this.scores.get(uid) == null ? 0 : this.scores.get(uid);
	}

	public void putScore(long uid, int score) {
		this.scores.put(uid, score);
	}

	public int getTurnBack() {
		return turnBack;
	}

	public void setTurnBack(int turnBack) {
		this.turnBack = turnBack;
	}

	public int getStandup() {
		return standup;
	}

	public void setStandup(int standup) {
		this.standup = standup;
	}

}
