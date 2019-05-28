package com.zb.models.room.activity;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.zb.models.room.ActivityCP;

/** 神经猫 */
public class NeuroCat extends ActivityCP {

	private static final long serialVersionUID = 7473451731641402350L;

	public static final int STATUS_PLAY = 2;// 开始
	public static final int STATUS_END = 3;// 结束

	public static final int STATUS_SETTLEMENT = 5;// 结算状态

	private Map<Long, Integer> levers = new LinkedHashMap<>();// 玩家剩余杠杆数
	private Set<Long> actors = new HashSet<Long>();
	private NeuroCatBox[][] map = new NeuroCatBox[9][9];
	private boolean play = false;// 本局有没有行动 如果是false 即60s未行动 结束游戏
	private long player;// 本局行走者
	private long colorRed;// 红方
	private long colorBlue;// 蓝方
	private int[] redCatLbs;// 红猫坐标
	private int[] blueCatLbs;// 蓝猫坐标

	public NeuroCatBox[][] getMap() {
		return map;
	}

	public void setMap(NeuroCatBox[][] map) {
		this.map = map;
	}

	public long getPlayer() {
		return player;
	}

	public void setPlayer(long player) {
		this.player = player;
	}

	public long getColorRed() {
		return colorRed;
	}

	public void setColorRed(long colorRed) {
		this.colorRed = colorRed;
	}

	public long getColorBlue() {
		return colorBlue;
	}

	public void setColorBlue(long colorBlue) {
		this.colorBlue = colorBlue;
	}

	public boolean isPlay() {
		return play;
	}

	public void setPlay(boolean play) {
		this.play = play;
	}

	public Map<Long, Integer> getLevers() {
		return levers;
	}

	public int getUserLever(long uid) {
		return this.levers.get(uid);
	}

	public void putUserLever(long uid, int leverCnt) {
		this.levers.put(uid, leverCnt);
	}

	public void setLevers(Map<Long, Integer> levers) {
		this.levers = levers;
	}

	public void putActor(long uid) {
		this.actors.add(uid);
	}

	public Set<Long> getActors() {
		return actors;
	}

	public void setActors(Set<Long> actors) {
		this.actors = actors;
	}

	public int[] getRedCatLbs() {
		return redCatLbs;
	}

	public void setRedCatLbs(int[] redCatLbs) {
		this.redCatLbs = redCatLbs;
	}

	public int[] getBlueCatLbs() {
		return blueCatLbs;
	}

	public void setBlueCatLbs(int[] blueCatLbs) {
		this.blueCatLbs = blueCatLbs;
	}
	
}
