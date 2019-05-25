package com.zb.service.room.vo;

import java.util.List;

import com.zb.models.Stake;

public class SlotMachinesRoomInfo extends RoomInfo {

	public SlotMachinesRoomInfo(int type) {
		super(type);
	}

	private static final long serialVersionUID = -6511969793822488024L;
	private long timeType;
	private long nextTime;
	private int lottery;
	private String betNo;
	private List<Stake> stakes;

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

}
