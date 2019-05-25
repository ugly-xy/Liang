package com.zb.models.room.activity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.zb.models.room.Activity;

public class Dice extends Activity {

	public static final int STATUS_START = 2;// 开始
	public static final int STATUS_DICING = 10;// 摇骰子阶段
	public static final int STATUS_GUESSING = 11;// 猜阶段
	public static final int STATUS_SHOW = 12;// 展示阶段
	public static final int STATUS_DICED =13; //已经确认阶段
	public static final int STATUS_END = 3;// 结束阶段
	// public static final int STATUS_TO_NEXT = 16;// 进入下一轮
	public static final int STATUS_PUNISH = 17;// 奖惩阶段
	public static final int DICE_NUM = 5;// 每人骰子数量
	public static final int MINBRAGNUM = 3; // 最小叫骰子数
	public static final int MINBRAGCOUNT = 2; // 最小叫骰子点数(最小骰子数时)

	private static final long serialVersionUID = 6895731734124099392L;
	private long winner = 0;
	private long loser = 0;
	private boolean job = false;
	private Map<Long, int[]> dices = new HashMap<>();// 骰子
	private Map<Long, Integer> actors = new LinkedHashMap<>();// 玩家
	private Map<Long, Integer> confirmUid = new LinkedHashMap<>();// 确定骰子的玩家
	private long[][] stateMap = new long[2][16];// 叫状态标识
	private Map<Long, Integer> reDiceTimeMap = new HashMap<>();// 重摇次数
	private long curUid = 0;
	private int curNo;
	private int count;
	private int coin;
	private int gradient = 10;
	private int diceTotalNum;// 叫骰子数量
	private int diceCount;// 叫骰子点数
	private int totalCoinPool;// 对局金币池
	private int inning = 0;// 第几局
	private boolean oneCalled = false; // 叫过1
	private boolean beFull = false; // 骰子叫光了

	public boolean isBeFull() {
		return beFull;
	}

	public void setBeFull(boolean beFull) {
		this.beFull = beFull;
	}

	public boolean isOneCalled() {
		return oneCalled;
	}

	public void setOneCalled(boolean oneCalled) {
		this.oneCalled = oneCalled;
	}

	public Map<Long, Integer> getReDiceTimeMap() {
		return reDiceTimeMap;
	}

	public void setReDiceTimeMap(Map<Long, Integer> reDiceTimeMap) {
		this.reDiceTimeMap = reDiceTimeMap;
	}

	public int getInning() {
		return inning;
	}

	public void setInning(int inning) {
		this.inning = inning;
	}

	public int getTotalCoinPool() {
		return totalCoinPool;
	}

	public void setTotalCoinPool(int totalCoinPool) {
		this.totalCoinPool = totalCoinPool;
	}

	public Map<Long, Integer> getConfirmUid() {
		return confirmUid;
	}

	public void setConfirmUid(Map<Long, Integer> confirmUid) {
		this.confirmUid = confirmUid;
	}

	public int getDiceCount() {
		return diceCount;
	}

	public void setDiceCount(int diceCount) {
		this.diceCount = diceCount;
	}

	public int getDiceTotalNum() {
		return diceTotalNum;
	}

	public void setDiceTotalNum(int diceTotalNum) {
		this.diceTotalNum = diceTotalNum;
	}

	public long[][] getStateMap() {
		return stateMap;
	}

	public void setStateMap(long[][] stateMap) {
		this.stateMap = stateMap;
	}

	public void clearActors() {
		actors.clear();
	}

	public void clearDices() {
		dices.clear();
	}

	public void removeActor(Long aid) {
		actors.remove(aid);
	}

	public void delActor(Long aid) {
		actors.remove(aid);
	}

	public void putActor(Long aid, Integer status) {
		actors.put(aid, status);
	}

	public void setActors(Map<Long, Integer> actors) {
		this.actors = actors;
	}

	public Map<Long, int[]> getDices() {
		return dices;
	}

	public void setDices(Map<Long, int[]> dices) {
		this.dices = dices;
	}

	public Map<Long, Integer> getActors() {
		return actors;
	}

	public boolean isJob() {
		return job;
	}

	public void setJob(boolean job) {
		this.job = job;
	}

	public long getWinner() {
		return winner;
	}

	public void setWinner(long winner) {
		this.winner = winner;
	}

	public long getLoser() {
		return loser;
	}

	public void setLoser(long loser) {
		this.loser = loser;
	}

	public long getCurUid() {
		return curUid;
	}

	public void setCurUid(long curUid) {
		this.curUid = curUid;
	}

	public int getCurNo() {
		return curNo;
	}

	public void setCurNo(int curNo) {
		this.curNo = curNo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public int getGradient() {
		return gradient;
	}

	public void setGradient(int gradient) {
		this.gradient = gradient;
	}

}
