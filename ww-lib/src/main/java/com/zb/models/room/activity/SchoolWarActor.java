package com.zb.models.room.activity;

import com.zb.models.room.Actor;

public class SchoolWarActor extends Actor {

	private static final long serialVersionUID = 3602573695310832686L;
	public static final int A_STATUS_START = 1;// 开始
	public static final int A_STATUS_FIGHT = 2;// 出手
	public static final int A_STATUS_STANDUP = 3;// 起立
	public static final int A_STATUS_LAUGH = 4;// 嘲讽

	public static final int SEAT_LEFT = 1;
	public static final int SEAT_RIGHT = 2;
	private int seat;

	public SchoolWarActor() {
		super();
	}

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	@Override
	public int compareTo(Object o) {
		SchoolWarActor a = (SchoolWarActor) o;
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
