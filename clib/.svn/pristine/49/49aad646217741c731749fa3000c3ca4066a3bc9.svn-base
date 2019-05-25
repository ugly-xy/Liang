package com.we.socket.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public enum SmType {

	SLAM(0, 0), PRIZE1(1, 2), PRIZE2(2, 5), PRIZE3(3, 3), PRIZE4(4, 10), PRIZE5(5, 4), PRIZE6(6, 20), PRIZE7(7,
			6), PRIZE8(8, 50),
	// 1-8key为开奖号 value为中奖返还倍数 0为大满贯
	;

	SmType(int t) {
		this.t = t;
	}

	SmType(int t, int multiple) {
		this.t = t;
		this.multiple = multiple;
	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	private int t;
	private int multiple;

	public static final Map<Integer, Integer> eMap = new HashMap<>();
	@SuppressWarnings("all")
	public static final Set<Integer> canBet = new HashSet() {
		{
			add(PRIZE2.getT());
			add(PRIZE4.getT());
			add(PRIZE6.getT());
			add(PRIZE8.getT());
			add(PRIZE1.getT());
			add(PRIZE3.getT());
			add(PRIZE5.getT());
			add(PRIZE7.getT());
		}
	};
	static {
		for (SmType e : EnumSet.allOf(SmType.class)) {
			eMap.put(e.getT(), e.getMultiple());
		}
	}

}
