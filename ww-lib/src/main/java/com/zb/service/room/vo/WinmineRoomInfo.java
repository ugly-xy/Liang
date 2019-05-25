package com.zb.service.room.vo;

import java.util.Map;

public class WinmineRoomInfo extends RoomInfo {

	private static final long serialVersionUID = 2161501722037552994L;

	public WinmineRoomInfo(int type) {
		super(type);
	}

	private int[][][] map;// 棋盘节点以及对应棋子状态
	private Map<Long, Integer> scores;

	public void setMap(int[][][] map) {
		this.map = map;
	}

	public void setScores(Map<Long, Integer> scores) {
		this.scores = scores;
	}

}
