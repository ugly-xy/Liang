package com.zb.models.room.activity;

import java.util.HashSet;
import java.util.Set;

import com.zb.models.room.ActivityCP;
import com.zb.service.room.cp.GomokuService;

public class Gomoku extends ActivityCP {

	private static final long serialVersionUID = -2883351832783257625L;

	public static final int STATUS_START = 2;// 开始
	public static final int STATUS_END = 3;// 结束
	
	public static final int STATUS_SETTLEMENT = 5;//结算状态

	private Set<Long> actors = new HashSet<>();

	private int[][] goMap = new int[GomokuService.GRID][GomokuService.GRID];
	private long curUid;
	private long blackUser;
	private long whiteUser;
	private int step;

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public Set<Long> getActors() {
		return actors;
	}

	public void setActors(Set<Long> actors) {
		this.actors = actors;
	}

	public long getCurUid() {
		return curUid;
	}

	public void setCurUid(long curUid) {
		this.curUid = curUid;
	}

	public long getBlackUser() {
		return blackUser;
	}

	public long getWhiteUser() {
		return whiteUser;
	}

	public void setBlackUser(long blackUser) {
		this.blackUser = blackUser;
	}

	public void setWhiteUser(long whiteUser) {
		this.whiteUser = whiteUser;
	}

	public int[][] getGoMap() {
		return goMap;
	}

	public void setGoMap(int[][] goMap) {
		this.goMap = goMap;
	}

	public void clearActors() {
		actors.clear();
	}

	public void putActor(Long aid) {
		actors.add(aid);
	}


	public void delActor(Long aid) {
		actors.remove(aid);
	}

	public int getColour(long uid) {
		if (this.blackUser == uid)
			return GomokuActor.BLACK;
		else
			return GomokuActor.WHITE;
	}

	public long nextUser(int colour) {
		if (colour == GomokuActor.BLACK)
			return this.whiteUser;
		else
			return this.blackUser;
	}

	public long nextUser(long user) {
		if (this.whiteUser == user)
			return this.blackUser;
		else
			return this.whiteUser;
	}

}
