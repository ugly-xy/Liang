package com.zb.models.room.activity;

import com.zb.models.room.Actor;

public class TexasActor extends Actor {

	private static final long serialVersionUID = -7397788326773332729L;

	public static final int A_STATUS_START = 0;// 游戏中

	public static final int A_STATUS_GIVEUP = 3;// 弃牌

	public static final int A_STATUS_PUTALL = 5;// 已梭哈

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
		TexasActor a = (TexasActor) o;
		return (int) (this.getInTime() - a.getInTime());
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Actor) {
			if (this.getUid() == ((TexasActor) o).getUid())
				return true;
		}
		return false;
	}

}
