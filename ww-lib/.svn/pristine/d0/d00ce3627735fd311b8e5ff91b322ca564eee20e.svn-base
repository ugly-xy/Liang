package com.zb.models.room;

import java.io.Serializable;

public class ActorState implements Serializable, Comparable<Object> {
	private static final long serialVersionUID = 2005697170812492885L;
	private long uid;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	@Override
	public int compareTo(Object o) {
		ActorState a = (ActorState) o;
		return (int) (this.uid - a.uid);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof ActorState) {
			if (this.uid == ((ActorState) o).uid)
				return true;
		}
		return false;
	}
}
