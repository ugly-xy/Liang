package com.zb.models.room.activity;

import java.io.Serializable;
import java.util.Set;

import com.zb.models.room.Actor;

public class UaView implements Serializable, Comparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UaView() {

	}

	public UaView(UndercoverActor ua) {
		this.dead = ua.isDead();
		this.diceNumber = ua.diceNumber;
		this.role = ua.getRole();
		this.status = ua.getStatus();
		this.ucStatus = ua.getUcStatus();
		this.uid = ua.getUid();
		this.votes = ua.getVotes();
		this.talk = ua.getTalk();
		this.inTime = ua.getInTime();
	}

	String talk;// 描述语言
	Set<Long> votes;
	int diceNumber;
	private long uid;
	private int status;
	private int ucStatus;
	private int role;// 房间内角色 是否观众
	// private long no;
	private boolean dead = false;
	private long inTime;

	public String getTalk() {
		return talk;
	}

	public void setTalk(String talk) {
		this.talk = talk;
	}

	public Set<Long> getVotes() {
		return votes;
	}

	public void setVotes(Set<Long> votes) {
		this.votes = votes;
	}

	public int getDiceNumber() {
		return diceNumber;
	}

	public void setDiceNumber(int diceNumber) {
		this.diceNumber = diceNumber;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getUcStatus() {
		return ucStatus;
	}

	public void setUcStatus(int ucStatus) {
		this.ucStatus = ucStatus;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	public long getInTime() {
		return inTime;
	}

	public void setInTime(long inTime) {
		this.inTime = inTime;
	}

	@Override
	public int compareTo(Object o) {
		UaView a = (UaView) o;
		return (int) (this.uid - a.uid);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Actor) {
			if (this.uid == ((UaView) o).uid)
				return true;
		}
		return false;
	}
}
