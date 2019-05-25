package com.zb.models.room.activity;

import java.util.HashSet;
import java.util.Set;

import com.zb.models.room.Actor;

public class UndercoverActor extends Actor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int ROLE_UNDERCOVER = 1;
	public static final int ROLE_CIVILIAN = 2;

	public static final int A_STATUS_SPEAK_BEGIN = 5;
	public static final int A_STATUS_SPEAK = 6;
	public static final int A_STATUS_VOTE_BEGIN = 7;
	public static final int A_STATUS_VOTE = 8;
	// public static final int A_STATUS_PK_BEGIN = 9;
	// public static final int A_STATUS_PK = 10;
	public static final int A_STATUS_PK_SPEAK_BEGIN = 11;
	public static final int A_STATUS_PK_SPEAK = 12;
	public static final int A_STATUS_PK_VOTE_BEGIN = 13;
	public static final int A_STATUS_PK_VOTE = 14;
	public static final int A_STATUS_PK_DICE_BEGIN = 15;
	public static final int A_STATUS_PK_DICE = 16;

	String talk;// 描述语言
	Set<Long> votes = new HashSet<Long>();
	int diceNumber;
	int changeCoin;
	int changePoint;
	boolean winder = false;

	public int getChangeCoin() {
		return changeCoin;
	}

	public void setChangeCoin(int changeCoin) {
		this.changeCoin = changeCoin;
	}

	public int getChangePoint() {
		return changePoint;
	}

	public void setChangePoint(int changePoint) {
		this.changePoint = changePoint;
	}

	public boolean isWinder() {
		return winder;
	}

	public void setWinder(boolean winder) {
		this.winder = winder;
	}

	public String getTalk() {
		return talk;
	}

	public void setTalk(String talk) {
		this.talk = talk;
	}

	public int getDiceNumber() {
		return diceNumber;
	}

	public void setDiceNumber(int diceNumber) {
		this.diceNumber = diceNumber;
	}

	public Set<Long> getVotes() {
		return votes;
	}

	public void setVotes(Set<Long> votes) {
		this.votes = votes;
	}

	public void addVote(Long uid) {
		votes.add(uid);
	}

	public void clearVotes() {
		votes.clear();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Actor) {
			if (this.getUid() == ((UndercoverActor) o).getUid())
				return true;
		}
		return false;
	}

	@Override
	public int compareTo(Object o) {
		UndercoverActor a = (UndercoverActor) o;
		long c = this.getInTime() - a.getInTime();
		if (c != 0) {
			return (int) c;
		}
		return (int) (this.getInTime() - a.getInTime());
	}

	@Override
	public int hashCode() {
		return ((Long) this.getUid()).hashCode();
	}

}
