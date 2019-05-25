package com.zb.models.room.activity;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.zb.models.room.ActivityCP;
import com.zb.service.room.cp.WinmineService;

/** 扫雷 */
public class Winmine extends ActivityCP {

	private static final long serialVersionUID = 5790128501981259119L;
	public static final int STATUS_START = 2;// 开始
	public static final int STATUS_END = 3;// 结束

	public static final int STATUS_SETTLEMENT = 5;// 结算状态

	private Set<Long> actors = new HashSet<Long>();// 玩家状态
	private Map<Long, int[]> temp = new LinkedHashMap<>();// 玩家本局操作
	private Map<Long, Integer> scores = new LinkedHashMap<>();// 积分
	// 地图 第三纬 0位棋子 1位状态
	private int[][][] map = new int[WinmineService.GRID_X][WinmineService.GRID_Y][2];
	private int init = 0;// 局数
	private int mineCnt;// 本局剩余地雷数 初始化是随机的

	public Set<Long> getActors() {
		return actors;
	}

	public void setActors(Set<Long> actors) {
		this.actors = actors;
	}

	public void putActor(long uid) {
		this.actors.add(uid);
	}

	public Map<Long, int[]> getTemp() {
		return temp;
	}
	
	public int[] getUserTemp(Long uid) {
		return temp.get(uid);
	}

	public void setTemp(Map<Long, int[]> temp) {
		this.temp = temp;
	}

	public void putUserTemp(Long uid, int[] lbs) {
		this.temp.put(uid, lbs);
	}

	public void clearTemp() {
		this.temp.clear();
	}

	public Map<Long, Integer> getScores() {
		return scores;
	}

	public int getUserScore(Long uid) {
		return this.scores.get(uid) == null ? 0 : this.scores.get(uid);
	}

	public void putUserScore(Long uid, int score) {
		this.scores.put(uid, score);
	}

	public void setScores(Map<Long, Integer> scores) {
		this.scores = scores;
	}

	public int[][][] getMap() {
		return map;
	}

	public void setMap(int[][][] map) {
		this.map = map;
	}

	public int getInit() {
		return init;
	}

	public void setInit(int init) {
		this.init = init;
	}

	public int getMineCnt() {
		return mineCnt;
	}

	public void setMineCnt(int mineCnt) {
		this.mineCnt = mineCnt;
	}

}
