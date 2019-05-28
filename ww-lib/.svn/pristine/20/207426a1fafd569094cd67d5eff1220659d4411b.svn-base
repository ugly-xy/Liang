package com.zb.models;

import java.io.Serializable;

public class GameCPScore implements Serializable {

	private static final long serialVersionUID = -4613533457908218835L;

	public static final int DEFAULT_SCORE = 1000;

	public static final int TIMETYPE_ALL = 0;
	public static final int TIMETYPE_WEEK = 7;
	public static final int TIMETYPE_LASTWEEK = -7;
	public static final int TIMETYPE_MONTH = 30;

	private String _id;// uid-gameType-dateType
	private long uid;
	private int gameType;
	private int timeType;
	private long time;
	private int score;
	private long updateTime;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getGameType() {
		return gameType;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}

	public int getTimeType() {
		return timeType;
	}

	public void setTimeType(int timeType) {
		this.timeType = timeType;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public GameCPScore(long uid, int gameType, int timeType, long time, int score, long updateTime) {
		super();
		this._id = get_Id(uid, gameType, timeType, time);
		this.uid = uid;
		this.gameType = gameType;
		this.timeType = timeType;
		this.time = time;
		this.score = score;
		this.updateTime = updateTime;
	}

	public GameCPScore() {
		super();
	}

	public static String get_Id(long uid, int gameType, int timeType, long time) {
		return uid + "-" + gameType + "-" + timeType + "-" + time;
	}
}
