package com.zb.service.room.vo;

import java.util.HashMap;
import java.util.Map;

import com.zb.models.room.activity.NeuroCatBox;

public class NeuroCatRoomInfo extends RoomInfo {

	private static final long serialVersionUID = 7156744962873014771L;

	public NeuroCatRoomInfo(int type) {
		super(type);
	}

	private long player;// 本局行走者
	private long colorRed;// 红方
	private long colorBlue;// 蓝方
	private NeuroCatBox[][] map;// 地图
	private Map<Long, Integer> levers = new HashMap<Long, Integer>();// 当前双方剩余杠杆数

	public void setPlayer(long player) {
		this.player = player;
	}

	public void setColorRed(long colorRed) {
		this.colorRed = colorRed;
	}

	public void setColorBlue(long colorBlue) {
		this.colorBlue = colorBlue;
	}

	public void setMap(NeuroCatBox[][] neuroCatBoxs) {
		this.map = neuroCatBoxs;
	}

	public void setLevers(Map<Long, Integer> levers) {
		this.levers = levers;
	}

}
