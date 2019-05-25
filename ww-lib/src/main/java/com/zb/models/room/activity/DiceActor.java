package com.zb.models.room.activity;

import com.zb.models.room.Actor;

public class DiceActor extends Actor {

	private static final long serialVersionUID = -3186466963193923470L;
	public static final int A_STATUS_START = 5;
	public static final int A_STATUS_DICING = 6;
	public static final int A_STATUS_GUESSED = 7;
	public static final int A_STATUS_GUESSING = 8;

	boolean ok;
	int changeCoin;
	int curPoint = 0;
	int[] dices; // 用户骰子 掉线重连用

	public int[] getDices() {
		return dices;
	}

	public void setDices(int[] dices) {
		this.dices = dices;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public int getChangeCoin() {
		return changeCoin;
	}

	public void setChangeCoin(int changeCoin) {
		this.changeCoin = changeCoin;
	}

	public int getCurPoint() {
		return curPoint;
	}

	public void setCurPoint(int curPoint) {
		this.curPoint = curPoint;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Actor) {
			if (this.getUid() == ((DiceActor) o).getUid())
				return true;
		}
		return false;
	}

	@Override
	public int compareTo(Object o) {
		DiceActor a = (DiceActor) o;
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
