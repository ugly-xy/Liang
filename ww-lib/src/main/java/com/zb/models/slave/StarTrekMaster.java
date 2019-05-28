package com.zb.models.slave;

import com.zb.models.AbstractDocument;

public class StarTrekMaster extends AbstractDocument {

	private static final long serialVersionUID = -2733500439493812989L;

	private int starLevel = 1; // 星球等级 1-10
	private long checkSlaveTime; // 检查奴隶身价
	private int availableBalance; // 可用收益
	private boolean canSteal; // 可偷取
	private boolean beNew; // 是否需要更新
	private long lastWorkTime; // 最近工作时间
	private long lastQueryTime;

	public StarTrekMaster() {
	}

	public StarTrekMaster(long id) {
		this._id = id;
		this.updateTime = System.currentTimeMillis();
		this.starLevel = 1;
		this.availableBalance = 0;
	}

	public long getLastQueryTime() {
		return lastQueryTime;
	}

	public void setLastQueryTime(long lastQueryTime) {
		this.lastQueryTime = lastQueryTime;
	}

	public boolean isBeNew() {
		return beNew;
	}

	public void setBeNew(boolean beNew) {
		this.beNew = beNew;
	}

	public long getLastWorkTime() {
		return lastWorkTime;
	}

	public void setLastWorkTime(long lastWorkTime) {
		this.lastWorkTime = lastWorkTime;
	}

	public boolean isCanSteal() {
		return canSteal;
	}

	public void setCanSteal(boolean canSteal) {
		this.canSteal = canSteal;
	}

	public long getCheckSlaveTime() {
		return checkSlaveTime;
	}

	public void setCheckSlaveTime(long checkSlaveTime) {
		this.checkSlaveTime = checkSlaveTime;
	}

	public int getStarLevel() {
		return starLevel;
	}

	public int getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(int availableBalance) {
		this.availableBalance = availableBalance;
	}

	public void setStarLevel(int starLevel) {
		this.starLevel = starLevel;
	}

}
