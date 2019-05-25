package com.zb.models.room.activity;

import java.util.ArrayList;
import java.util.List;

import com.zb.models.Stake;
import com.zb.models.room.Actor;

public class SakuranActor extends Actor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3073892332432177962L;

	public SakuranActor() {
	}

	private List<Stake> stakes = new ArrayList<>();
	List<Integer> points = new ArrayList<Integer>();
	int changeCoin;
	int curPoint = 0;
	boolean isBet;// 是否下注
	private int totalPay;
	private int totalWin;
	private int luckyCoin;

	public int getTotalWin() {
		return totalWin;
	}

	public void setTotalWin(int totalWin) {
		this.totalWin = totalWin;
	}

	public int getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(int totalPay) {
		this.totalPay = totalPay;
	}

	public boolean isBet() {
		return isBet;
	}

	public void setBet(boolean isBet) {
		this.isBet = isBet;
	}

	public int getChangeCoin() {
		return changeCoin;
	}

	public void setChangeCoin(int changeCoin) {
		this.changeCoin = changeCoin;
	}

	public int getCurPoint() {
		return curPoint;
	}

	public void setCurPoint(int curPoint) {
		this.curPoint = curPoint;
	}

	public List<Integer> getPoints() {
		return points;
	}

	public void setPoints(List<Integer> points) {
		this.points = points;
	}

	public List<Stake> getStakes() {
		return stakes;
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
