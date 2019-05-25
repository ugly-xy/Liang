package com.we.socket.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum RoomId4Type {

	SLOTMACHINES(133, 99999900L), 
	WORLDCHAT(134, 99999999L),
	SAKURAN(143, 99999901L), 
	;

	RoomId4Type(int t) {
		this.t = t;
	}

	RoomId4Type(int t, Long id) {
		this.t = t;
		this.id = id;
	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private int t;
	private Long id;

	public static final Map<Integer, Long> map = new HashMap<>();
	static {
		for (RoomId4Type e : EnumSet.allOf(RoomId4Type.class)) {
			map.put(e.getT(), e.getId());
		}
	}

}
