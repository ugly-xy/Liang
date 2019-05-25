package com.zb.service.room.vo;

import java.util.LinkedHashMap;
import java.util.Map;

public class SchoolWarRoomInfo extends RoomInfo {

	private static final long serialVersionUID = 9208424114919047079L;

	public SchoolWarRoomInfo(int type) {
		super(type);
	}

	private int time;// 距离结束时间
	private Map<Long, Integer> actorsStatus = new LinkedHashMap<>();// 玩家状态
	private Map<Long, Integer> actorsScores = new LinkedHashMap<>();// 玩家积分

	public void setTime(int time) {
		this.time = time;
	}

	public void setActorsStatus(Map<Long, Integer> actorsStatus) {
		this.actorsStatus = actorsStatus;
	}

	public void setActorsScores(Map<Long, Integer> actorsScores) {
		this.actorsScores = actorsScores;
	}

}
