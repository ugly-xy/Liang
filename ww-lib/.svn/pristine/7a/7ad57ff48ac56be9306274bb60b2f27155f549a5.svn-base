package com.zb.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SlotMachinesModelPool extends AbstractDocument {

	private static final long serialVersionUID = 3471050593431525531L;
	private long coinPool;// 奖金池
	private long cachePool; // 缓存池
	private long incomePool; // 抽红池
	private long bufferPool; // 缓冲池
	private int nextGetCacheCount; // 下次释放缓存池剩余次数

	public SlotMachinesModelPool(long coinPool, long cachePool, long bufferPool, int nextGetCacheCount) {
		this.coinPool = coinPool;
		this.cachePool = cachePool;
		this.bufferPool = bufferPool;
		this.nextGetCacheCount = nextGetCacheCount;
	}

	public SlotMachinesModelPool() {
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

}
