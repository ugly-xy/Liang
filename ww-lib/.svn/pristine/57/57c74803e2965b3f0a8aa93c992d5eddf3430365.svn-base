package com.zb.models.room.machines;

import java.util.ArrayList;
import java.util.List;

import com.zb.models.Stake;

public class SakuranUserVO implements Cloneable {

	public SakuranUserVO() {
	}

	private long uid;

	private int payCoin;
	private int winCoin;
	private int income;
	private int luckyCoin;
	private List<Stake> stakes = new ArrayList<>();

	@Override
	public boolean equals(Object o) {
		if (o instanceof SakuranUserVO) {
			if (this.getUid() == ((SakuranUserVO) o).getUid())
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Long.toString(this.uid).hashCode();
	}

	public long getUid() {
		return uid;
	}

	public int getPayCoin() {
		return payCoin;
	}

	public int getWinCoin() {
		return winCoin;
	}

	public int getIncome() {
		return income;
	}

	public List<Stake> getStakes() {
		return stakes;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public void setPayCoin(int payCoin) {
		this.payCoin = payCoin;
	}

	public void setWinCoin(int winCoin) {
		this.winCoin = winCoin;
	}

	public void setIncome(int income) {
		this.income = income;
	}

	public void setStakes(List<Stake> stakes) {
		this.stakes = stakes;
	}

	public int getLuckyCoin() {
		return luckyCoin;
	}

	public void setLuckyCoin(int luckyCoin) {
		this.luckyCoin = luckyCoin;
	}

}
