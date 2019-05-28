package com.zb.models.room.activity;

import com.zb.models.room.Actor;

/** 神经猫 */
public class NeuroCatActor extends Actor {

	private static final long serialVersionUID = -6852973317556271032L;
	private int color;

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	@Override
	public int compareTo(Object o) {
		NeuroCatActor a = (NeuroCatActor) o;
		return (int) (this.getInTime() - a.getInTime());
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Actor) {
			if (this.getUid() == ((NeuroCatActor) o).getUid())
				return true;
		}
		return false;
	}

}
