package com.zb.models.room.activity;

import com.zb.models.room.ActorState;

public class WerewolfKillActorState extends ActorState {

	private static final long serialVersionUID = 2803526501484641021L;
	private Integer identity;
	private boolean death;
	private Integer deathType;
	private Integer deathDay;
	private boolean vote;
	private Long vid;
	private int winCoin;
	private int winPoint;
	private boolean deadLater;

	public boolean isDeadLater() {
		return deadLater;
	}

	public void setDeadLater(boolean deadLater) {
		this.deadLater = deadLater;
	}

	public int getWinPoint() {
		return winPoint;
	}

	public void setWinPoint(int winPoint) {
		this.winPoint = winPoint;
	}

	public Integer getDeathType() {
		return deathType;
	}

	public int getWinCoin() {
		return winCoin;
	}

	public void setWinCoin(int winCoin) {
		this.winCoin = winCoin;
	}

	public boolean getVote() {
		return vote;
	}

	public Long getVid() {
		return vid;
	}

	public void setVote(boolean vote) {
		this.vote = vote;
	}

	public void setVid(Long vid) {
		this.vid = vid;
	}

	public Integer getDeathDay() {
		return deathDay;
	}

	public void setDeathDay(Integer deathDay) {
		this.deathDay = deathDay;
	}

	public void setDeathType(Integer deathType) {
		this.deathType = deathType;
	}

	public Integer getIdentity() {
		return identity;
	}

	public void setIdentity(Integer identity) {
		this.identity = identity;
	}

	public boolean getDeath() {
		return death;
	}

	public void setDeath(boolean death) {
		this.death = death;
	}

}
