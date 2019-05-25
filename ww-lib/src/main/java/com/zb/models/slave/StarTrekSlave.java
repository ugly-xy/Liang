package com.zb.models.slave;

import com.zb.models.AbstractDocument;

public class StarTrekSlave extends AbstractDocument {

	private static final long serialVersionUID = 2971946399491352043L;

	private long worth; // 身价
	private long masterId; // 奴隶主id
	private long lastBuyTime; // 最近被卖时间
	private String nickname; // 赐名
	private boolean monster; // 是否是小怪兽
	private long beginWorkTime; // 开始工作时间
	private long endWorkTime; // 结束工作时间
	private int expectIncome; // 预计收益
	private int extIncome;// 星球额外收益
	private int attenuationIncome; // 衰减收益
	private int income; // 实际收益
	private int status; // 状态 0空闲 1工作
	private String work; // 工作
	private long processNumber; // 工序号

	public final static int SLEEPING = 0;
	public final static int WORKING = 1;
	public final static int JOBSDONE = 2;
	public final static int DEAD = 3;
	public final static int DEAD_WORKING = 4;

	public StarTrekSlave() {
	}

	public StarTrekSlave(long id, long masterId, boolean monster) {
		this._id = id;
		this.updateTime = System.currentTimeMillis();
		this.masterId = masterId;
		this.monster = monster;
		this.worth = 100L;
		this.income = 0;
		this.status = SLEEPING;
		this.work = "休息中";
		this.processNumber = 0L;
		this.lastBuyTime = System.currentTimeMillis();
		if (monster)
			this.nickname = "小撕鸡";
		else
			this.nickname = "";
	}

	public int getAttenuationIncome() {
		return attenuationIncome;
	}

	public void setAttenuationIncome(int attenuationIncome) {
		this.attenuationIncome = attenuationIncome;
	}

	public int getExtIncome() {
		return extIncome;
	}

	public void setExtIncome(int extIncome) {
		this.extIncome = extIncome;
	}

	public long getProcessNumber() {
		return processNumber;
	}

	public void setProcessNumber(long processNumber) {
		this.processNumber = processNumber;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getExpectIncome() {
		return expectIncome;
	}

	public void setExpectIncome(int expectIncome) {
		this.expectIncome = expectIncome;
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	}

	public long getBeginWorkTime() {
		return beginWorkTime;
	}

	public void setBeginWorkTime(long beginWorkTime) {
		this.beginWorkTime = beginWorkTime;
	}

	public long getEndWorkTime() {
		return endWorkTime;
	}

	public void setEndWorkTime(long endWorkTime) {
		this.endWorkTime = endWorkTime;
	}

	public boolean isMonster() {
		return monster;
	}

	public void setMonster(boolean monster) {
		this.monster = monster;
	}

	public long getLastBuyTime() {
		return lastBuyTime;
	}

	public void setLastBuyTime(long lastBuyTime) {
		this.lastBuyTime = lastBuyTime;
	}

	public long getWorth() {
		return worth;
	}

	public void setWorth(long worth) {
		this.worth = worth;
	}

	public long getMasterId() {
		return masterId;
	}

	public void setMasterId(long masterId) {
		this.masterId = masterId;
	}

}
