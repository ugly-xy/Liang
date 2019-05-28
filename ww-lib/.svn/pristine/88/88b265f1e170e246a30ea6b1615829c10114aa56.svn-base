package com.zb.models.room.activity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zb.models.room.Activity;

// 游戏活动状态
public class SouthPenguin extends Activity {
	private static final long serialVersionUID = -4962950561439696455L;
	public static final int STATUS_LAUNCH_FIRST = 10;
	public static final int STATUS_START = 1;// 开始
	public static final int STATUS_LAUNCH = 2;// 发射阶段
	public static final int STATUS_CALCULATE = 4;// 计算阶段
	public static final int STATUS_SHOW = 6;// 展示阶段
	public static final int STATUS_INTONEXT = 7;// 下一阶段
	public static final int STATUS_END = 8;// 结束阶段
	private Set<Integer> colors = new HashSet<>();// 颜色
	private List<Penguin> penguins = new ArrayList<Penguin>();// 企鹅
	private Set<Integer[]> places = new HashSet<>();// 分配的初始坐标点
	private Set<Long> actors = new HashSet<Long>();// 玩家
	private int confirmNum;// 本回合已确定发射的企鹅数目
	private long winner = 0L;// 获胜者
	private long calculator = 0L;// 计算者
	// 第几回合
	private int inning = 0;
	// 当前冰面大小 int
	private int iceSize = 24;

	public SouthPenguin() {
	}

	public Set<Long> getActors() {
		return actors;
	}

	public void setActors(Set<Long> actors) {
		this.actors = actors;
	}

	public void clearActors() {
		actors.clear();
	}

	public void clearPenguins() {
		penguins.clear();
	}

	public void putActor(Long aid) {
		actors.add(aid);
	}

	public void removeActor(Long aid) {
		actors.remove(aid);
	}

	public Set<Integer> getColors() {
		return colors;
	}

	public void setColors(Set<Integer> colors) {
		this.colors = colors;
	}

	public void putColor(Integer color) {
		this.colors.add(color);
	}

	public List<Penguin> getPenguins() {
		return penguins;
	}

	public void setPenguins(List<Penguin> penguins) {
		this.penguins = penguins;
	}

	public void putPenguin(Penguin penguin) {
		this.penguins.add(penguin);
	}

	public void putPenguins(List<Penguin> penguin) {
		this.penguins.addAll(penguin);
	}

	public Set<Integer[]> getPlaces() {
		return places;
	}

	public void setPlaces(HashSet<Integer[]> places) {
		this.places = places;
	}

	public int getInning() {
		return inning;
	}

	public void setInning(int inning) {
		this.inning = inning;
	}

	public int getIceSize() {
		return iceSize;
	}

	public void setIceSize(int iceSize) {
		this.iceSize = iceSize;
	}

	public void setPlaces(Set<Integer[]> places) {
		this.places = places;
	}

	public long getWinner() {
		return winner;
	}

	public void setWinner(long winner) {
		this.winner = winner;
	}

	public long getCalculator() {
		return calculator;
	}

	public void setCalculator(long calculator) {
		this.calculator = calculator;
	}

	public void removeCalculator() {
		this.calculator = 0L;
	}

	public int getConfirmNum() {
		return confirmNum;
	}

	public void setConfirmNum(int confirm) {
		this.confirmNum = confirm;
	}

	public void addConfirmNum(int confirm) {
		this.confirmNum += confirm;
	}
}
