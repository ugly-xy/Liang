package com.zb.models.room.machines;

public class SakuranUser implements Cloneable {

	public SakuranUser() {
	}

	public SakuranUser(int index) {
		this.blood = 100;
		switch (index) {
		case 0:
			this.x = 0;
			this.y = 4;
			break;
		case 1:
			this.x = 2;
			this.y = 4;
			break;
		case 2:
			this.x = 4;
			this.y = 4;
			break;
		case 3:
			this.x = 4;
			this.y = 2;
			break;
		case 4:
			this.x = 4;
			this.y = 0;
			break;
		case 5:
			this.x = 2;
			this.y = 0;
			break;
		case 6:
			this.x = 0;
			this.y = 0;
			break;
		case 7:
			this.x = 0;
			this.y = 2;
			break;
		}
	}

	public static int calDistance(int x1, int x2, int y1, int y2) {
		return (int) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	private long auid;
	private boolean protagonist;
	private int x;
	private int y;
	private int blood;
	private int action;
	private long actionTo;
	// private int mx;
	// private int my;
	private int v = -1;

	@Override
	public SakuranUser clone() {
		SakuranUser vo = null;
		try {
			vo = (SakuranUser) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return vo;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof SakuranUser) {
			if (this.getAuid() == ((SakuranUser) o).getAuid())
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Long.toString(this.auid).hashCode();
	}

	public long getActionTo() {
		return actionTo;
	}

	public void setActionTo(long actionTo) {
		this.actionTo = actionTo;
	}

	public int getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}

	// public int getMx() {
	// return mx;
	// }
	//
	// public int getMy() {
	// return my;
	// }
	//
	// public void setMx(int mx) {
	// this.mx = mx;
	// }
	//
	// public void setMy(int my) {
	// this.my = my;
	// }

	public void setActionTo(int actionTo) {
		this.actionTo = actionTo;
	}

	public boolean isProtagonist() {
		return protagonist;
	}

	public void setProtagonist(boolean protagonist) {
		this.protagonist = protagonist;
	}

	public int getBlood() {
		return blood;
	}

	public long getAuid() {
		return auid;
	}

	public void setAuid(long auid) {
		this.auid = auid;
	}

	public void setBlood(int blood) {
		this.blood = blood;
	}

	public int getAction() {
		return action;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setAction(int action) {
		this.action = action;
	}

}
