package com.zb.models.room.activity;

import com.zb.models.room.Actor;

//游戏参与者
public class SouthPenguinActor extends Actor {
	private static final long serialVersionUID = 7284414881183520454L;
	public static final int A_STATUS_START = 5;
	public static final int A_LAUNCH_INIT = 1; // 等待发射
	public static final int A_LAUNCH_END = 2; // 发射完毕
	private Integer color; // 颜色

	public SouthPenguinActor() {
		super();
	}

	public Integer getColor() {
		return color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}
}
