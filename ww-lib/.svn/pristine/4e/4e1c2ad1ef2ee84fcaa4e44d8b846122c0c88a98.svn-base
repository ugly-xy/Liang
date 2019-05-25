package com.zb.models.room.activity;

import java.util.ArrayList;
import java.util.List;

import com.zb.models.room.Actor;

public class DrawSomethingActor extends Actor {

	private static final long serialVersionUID = -9221403430564925439L;
	public static final int ROLE_UNDERCOVER = 1;
	public static final int ROLE_CIVILIAN = 2;

	public static final int A_STATUS_START = 5;
	public static final int A_STATUS_SELECT_WORD = 6;
	public static final int A_STATUS_DRAWING = 7;
	public static final int A_STATUS_GUESS = 8;

	String talk;// 描述语言
	boolean ok;
	List<Integer> points = new ArrayList<Integer>();
	int changeCoin;
	int curPoint = 0;
	boolean draw;
	String word;

	public String getTalk() {
		return talk;
	}

	public void setTalk(String talk) {
		this.talk = talk;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public List<Integer> getPoints() {
		return points;
	}

	// public void setPoints(List<Integer> points) {
	// this.points = points;
	// }

	public void clearPoints() {
		this.points.clear();
		this.curPoint = 0;
	}

	public int addPoint(int point) {
		this.curPoint = this.curPoint + point;
		points.add(point);
		return curPoint;
	}

	public boolean isDraw() {
		return draw;
	}

	public void setDraw(boolean draw) {
		this.draw = draw;
	}

	public int getChangeCoin() {
		return changeCoin;
	}

	public void setChangeCoin(int changeCoin) {
		this.changeCoin = changeCoin;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
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
			if (this.getUid() == ((DrawSomethingActor) o).getUid())
				return true;
		}
		return false;
	}

	@Override
	public int compareTo(Object o) {
		DrawSomethingActor a = (DrawSomethingActor) o;
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
