package com.zb.models.room.activity;

import com.zb.models.room.Actor;

/** 扫雷 */
public class WinmineActor extends Actor {

	private static final long serialVersionUID = 5510341939761301167L;

	@Override
	public int compareTo(Object o) {
		WinmineActor a = (WinmineActor) o;
		return (int) (this.getInTime() - a.getInTime());
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Actor) {
			if (this.getUid() == ((WinmineActor) o).getUid())
				return true;
		}
		return false;
	}

}
