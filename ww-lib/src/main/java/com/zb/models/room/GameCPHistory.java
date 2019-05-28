package com.zb.models.room;

import java.io.Serializable;

public class GameCPHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2353109122871810627L;

	private String _id;
	private Long myid;
	private Long uid;
	private Integer gameType;
	private Integer sex;
	private int win;
	private int lost;
	private int peace;
	private long updateTime;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public Long getMyid() {
		return myid;
	}

	public void setMyid(Long myid) {
		this.myid = myid;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getGameType() {
		return gameType;
	}

	public void setGameType(Integer gameType) {
		this.gameType = gameType;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getLost() {
		return lost;
	}

	public void setLost(int lost) {
		this.lost = lost;
	}

	public int getPeace() {
		return peace;
	}

	public void setPeace(int peace) {
		this.peace = peace;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
}
