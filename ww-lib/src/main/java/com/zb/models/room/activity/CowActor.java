package com.zb.models.room.activity;

import com.zb.models.room.Actor;

public class CowActor extends Actor {

	private static final long serialVersionUID = -7397788326773332729L;

	private int seat = -1;// 座位号
	private int playCnt = 0;// 在此房间玩的场次

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	public int getPlayCnt() {
		return playCnt;
	}

	public void setPlayCnt(int playCnt) {
		this.playCnt = playCnt;
	}

	@Override
	public int compareTo(Object o) {
		CowActor a = (CowActor) o;
		return (int) (this.getInTime() - a.getInTime());
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Actor) {
			if (this.getUid() == ((CowActor) o).getUid())
				return true;
		}
		return false;
	}

}
