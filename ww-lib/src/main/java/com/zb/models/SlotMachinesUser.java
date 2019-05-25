package com.zb.models;

import java.util.ArrayList;
import java.util.List;

public class SlotMachinesUser {

	private Long uid;
	private int payCoin;
	private int winCoin;
	private int income;
	private List<Stake> stakes = new ArrayList<>();

	public int getPayCoin() {
		return payCoin;
	}

	public void setPayCoin(int payCoin) {
		this.payCoin = payCoin;
	}

	public int getWinCoin() {
		return winCoin;
	}

	public void setWinCoin(int winCoin) {
		this.winCoin = winCoin;
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public List<Stake> getStakes() {
		return stakes;
	}

	public void setStakes(List<Stake> stakes) {
		this.stakes = stakes;
	}

}
