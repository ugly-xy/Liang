package com.zb.models.room.machines;

import com.zb.models.room.Activity;

public class SlotMachines extends Activity {

	private static final long serialVersionUID = 6895731734124099392L;
	public static final int STATUS_START = 2;// 开始
	public static final int STATUS_BET = 10;// 下注阶段
	public static final int STATUS_ROTATE = 11;// 转盘旋转阶段
	public static final int STATUS_SETTLEMENT = 12;// 结算阶段

	private int inning;// 第几局
	private String date;
	private int lottery = 10; // 本轮开奖
	private long outCoin;
	private long inCoin;
	private long coinPool;
	private long bufferPool;
	private long cachePool;
	private int nextGetCacheCount;
	private long incomePool;
	private String betNo;

	public String getBetNo() {
		return betNo;
	}

	public void setBetNo(String betNo) {
		this.betNo = betNo;
	}

	public long getIncomePool() {
		return incomePool;
	}

	public void setIncomePool(long incomePool) {
		this.incomePool = incomePool;
	}

	public int getNextGetCacheCount() {
		return nextGetCacheCount;
	}

	public void setNextGetCacheCount(int nextGetCacheCount) {
		this.nextGetCacheCount = nextGetCacheCount;
	}

	public long getCachePool() {
		return cachePool;
	}

	public void setCachePool(long cachePool) {
		this.cachePool = cachePool;
	}

	public long getOutCoin() {
		return outCoin;
	}

	public void setOutCoin(long outCoin) {
		this.outCoin = outCoin;
	}

	public long getInCoin() {
		return inCoin;
	}

	public void setInCoin(long inCoin) {
		this.inCoin = inCoin;
	}

	public long getCoinPool() {
		return coinPool;
	}

	public void setCoinPool(long coinPool) {
		this.coinPool = coinPool;
	}

	public long getBufferPool() {
		return bufferPool;
	}

	public void setBufferPool(long bufferPool) {
		this.bufferPool = bufferPool;
	}

	public int getLottery() {
		return lottery;
	}

	public void setLottery(int lottery) {
		this.lottery = lottery;
	}

	public int getInning() {
		return inning;
	}

	public void setInning(int inning) {
		this.inning = inning;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
