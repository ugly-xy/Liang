package com.zb.service.room.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zb.models.Stake;

public class SakuranRoomInfo extends RoomInfo {

	public SakuranRoomInfo(int type) {
		super(type);
	}

	private static final long serialVersionUID = -6511969793822488024L;
	private long timeType;
	private int lottery;
	private String betNo; // 期号
	private List<Stake> stakes;
	private long pool; // 当前奖池
	private List<Integer> grandTotal; // 英雄当前 下注表
	private int limitCoin = 20000;
	// private int ucStatus;
	private List<Map<String, Object>> slist = new ArrayList<>(); // 下一局英雄
	private long nextJuTime; // 下次法官
	private long nextSeTime; // 下次秘书
	private long lastJuTime; // 上次法官
	private long lastSeTime; // 上次秘书
	private long nextTime;// 下次开盘时间
	private long lucky;// 用户幸运值

	// private long now;
	//
	// public long getNow() {
	// return now;
	// }
	//
	// public void setNow(long now) {
	// this.now = now;
	// }

	public long getNextJuTime() {
		return nextJuTime;
	}

	public long getNextSeTime() {
		return nextSeTime;
	}

	public void setNextJuTime(long nextJuTime) {
		this.nextJuTime = nextJuTime;
	}

	public void setNextSeTime(long nextSeTime) {
		this.nextSeTime = nextSeTime;
	}

	public int getLimitCoin() {
		return limitCoin;
	}

	// public int getUcStatus() {
	// return ucStatus;
	// }
	//
	// public void setUcStatus(int ucStatus) {
	// this.ucStatus = ucStatus;
	// }

	public List<Map<String, Object>> getSlist() {
		return slist;
	}

	public void setSlist(List<Map<String, Object>> slist) {
		this.slist = slist;
	}

	public void setLimitCoin(int limitCoin) {
		this.limitCoin = limitCoin;
	}

	public List<Integer> getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(List<Integer> grandTotal) {
		this.grandTotal = grandTotal;
	}

	public long getPool() {
		return pool;
	}

	public void setPool(long pool) {
		this.pool = pool;
	}

	public List<Stake> getStakes() {
		return stakes;
	}

	public void setStakes(List<Stake> stakes) {
		this.stakes = stakes;
	}

	public String getBetNo() {
		return betNo;
	}

	public void setBetNo(String betNo) {
		this.betNo = betNo;
	}

	public long getTimeType() {
		return timeType;
	}

	public void setTimeType(long timeType) {
		this.timeType = timeType;
	}

	public long getNextTime() {
		return nextTime;
	}

	public void setNextTime(long nextTime) {
		this.nextTime = nextTime;
	}

	public int getLottery() {
		return lottery;
	}

	public void setLottery(int lottery) {
		this.lottery = lottery;
	}

	public long getLastJuTime() {
		return lastJuTime;
	}

	public void setLastJuTime(long lastJuTime) {
		this.lastJuTime = lastJuTime;
	}

	public long getLastSeTime() {
		return lastSeTime;
	}

	public void setLastSeTime(long lastSeTime) {
		this.lastSeTime = lastSeTime;
	}

	public long getLucky() {
		return lucky;
	}

	public void setLucky(long lucky) {
		this.lucky = lucky;
	}
}
