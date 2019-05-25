package com.zb.models.room.machines;

import java.util.HashMap;
import java.util.Map;

import com.zb.models.room.Activity;

public class Sakuran extends Activity {

	private static final long serialVersionUID = 6895731734124099392L;
	public static final int STATUS_START = 2;// 开始
	public static final int STATUS_BET = 10;// 下注阶段
	public static final int STATUS_ROTATE = 11;// 转盘旋转阶段
	public static final int STATUS_SETTLEMENT = 12;// 结算阶段

	public static final int HIT_RUN = 0; // 走位
	public static final int HIT_KNIFING = 1; // 挥刀
	public static final int HIT_BLOCK = 2; // 格挡
	public static final int HIT_MEDICINE = 3; // 吃药
	public static final int HIT_DEAD = 4; // 死亡
	public static final int HIT_BLEED = 5; // 掉血

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
	private long winner;
	private double[] rateArr = null; // 开奖概率
	private double[] odds = null;// 赔率
	private long lotteryId; // 本轮开奖用户ID
	private boolean hasJu; // 法官
	private boolean hasSe; // 秘书
	private Map<Long, Integer> coins = new HashMap<Long, Integer>();

	public boolean isHasJu() {
		return hasJu;
	}

	public boolean isHasSe() {
		return hasSe;
	}

	public void setHasJu(boolean hasJu) {
		this.hasJu = hasJu;
	}

	public void setHasSe(boolean hasSe) {
		this.hasSe = hasSe;
	}

	public long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public double[] getRateArr() {
		return rateArr;
	}

	public void setRateArr(double[] rateArr) {
		this.rateArr = rateArr;
	}

	// public List<Integer> getGrandTotal() {
	// return grandTotal;
	// }
	//
	// public void setGrandTotal(List<Integer> grandTotal) {
	// this.grandTotal = grandTotal;
	// }

	// public Map<Long, Integer> getActors() {
	// return actors;
	// }
	//
	// public void setActors(Map<Long, Integer> actors) {
	// this.actors = actors;
	// }

	public long getWinner() {
		return winner;
	}

	public void setWinner(long winner) {
		this.winner = winner;
	}

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

	public double[] getOdds() {
		return odds;
	}

	public void setOdds(double[] odds) {
		this.odds = odds;
	}

	public Map<Long, Integer> getCoins() {
		return coins;
	}

	public void addCoins(long uid, int coin) {
		this.coins.put(uid, coin);
	}

}
