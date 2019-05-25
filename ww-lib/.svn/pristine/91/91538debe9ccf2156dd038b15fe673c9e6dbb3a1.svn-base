package com.zb.models;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SlotMachinesModel extends AbstractDocument {

	private static final long serialVersionUID = 473505023088292121L;
	private int inning = 0;// 第几局
	private String date = "";
	private long time;
	private long inCoin;
	private long outCoin;
	private long totalIncome;
	private int lottery;
	private String heroName; // 英雄名称
	private Set<SlotMachinesUser> uas = new HashSet<>();// 本轮用户
	private long coinPool;// 奖金池
	private long cachePool; // 缓存池
	private long incomePool; // 抽红池
	private long bufferPool; // 缓冲池
	private int nextGetCacheCount; // 下次释放缓存池剩余次数

	public SlotMachinesModel() {
	}

	public String getHeroName() {
		return heroName;
	}

	public void setHeroName(String heroName) {
		this.heroName = heroName;
	}

	public int getInning() {
		return inning;
	}

	public void setInning(int inning) {
		this.inning = inning;
	}

	public long getBufferPool() {
		return bufferPool;
	}

	public void setBufferPool(long bufferPool) {
		this.bufferPool = bufferPool;
	}

	public int getNextGetCacheCount() {
		return nextGetCacheCount;
	}

	public void setNextGetCacheCount(int nextGetCacheCount) {
		this.nextGetCacheCount = nextGetCacheCount;
	}

	public long getIncomePool() {
		return incomePool;
	}

	public long getCachePool() {
		return cachePool;
	}

	public void setCachePool(long cachePool) {
		this.cachePool = cachePool;
	}

	public void setIncomePool(long incomePool) {
		this.incomePool = incomePool;
	}

	public long getCoinPool() {
		return coinPool;
	}

	public void setCoinPool(long coinPool) {
		this.coinPool = coinPool;
	}

	public long getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(long totalIncome) {
		this.totalIncome = totalIncome;
	}

	public long getInCoin() {
		return inCoin;
	}

	public void setInCoin(long inCoin) {
		this.inCoin = inCoin;
	}

	public long getOutCoin() {
		return outCoin;
	}

	public void setOutCoin(long outCoin) {
		this.outCoin = outCoin;
	}

	public int getLottery() {
		return lottery;
	}

	public void setLottery(int lottery) {
		this.lottery = lottery;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Set<SlotMachinesUser> getUas() {
		return uas;
	}

	public void setUas(Set<SlotMachinesUser> uas) {
		this.uas = uas;
	}

	// public int getInning() {
	// return inning;
	// }
	//
	// public void setInning(int inning) {
	// this.inning = inning;
	// }

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
