package com.zb.models.room.activity;

import com.zb.models.room.Actor;

public class GomokuActor extends Actor {

	private static final long serialVersionUID = -8926825943104401427L;

	public final static int BLACK = 1;
	public final static int WHITE = 2;

	public static final int A_STATUS_START = 1;

	private int colour;

	public int getColour() {
		return colour;
	}

	public void setColour(int colour) {
		this.colour = colour;
	}

	@Override
	public int compareTo(Object o) {
		GomokuActor a = (GomokuActor) o;
		return (int) (this.getInTime() - a.getInTime());
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Actor) {
			if (this.getUid() == ((GomokuActor) o).getUid())
				return true;
		}
		return false;
	}

}
