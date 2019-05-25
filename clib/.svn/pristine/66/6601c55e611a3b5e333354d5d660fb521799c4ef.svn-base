package com.we.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.we.models.sign.SignMonth;

public class SignMonthVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5316516632724026671L;

	public SignMonthVO() {

	}

	public SignMonthVO(SignMonth sm, int coin) {
		this._id = sm.get_id();
		this.uid = sm.getUid();
		this.month = sm.getMonth();
		this.coin = coin;
		this.retroactive = sm.getRetroactive();
		this.days = sm.getDays();
		this.draws = sm.getDraws();
	}

	public SignMonthVO(SignMonth sm, int coin, int reSignCnt) {
		this._id = sm.get_id();
		this.uid = sm.getUid();
		this.month = sm.getMonth();
		this.coin = coin;
		this.retroactive = sm.getRetroactive();
		this.days = sm.getDays();
		this.draws = sm.getDraws();
		this.reSignCnt = reSignCnt;
	}

	private int month;// 201611
	private long uid;
	private List<Integer> retroactive = new ArrayList<Integer>();
	private String _id;
	private List<Integer> days = new ArrayList<Integer>();
	private Map<Integer, Boolean> draws = new LinkedHashMap<Integer, Boolean>();
	private int coin;
	private int reSignCnt = 3;

	public void addDay(Integer day) {
		days.add(day);
	}

	public void addRetroactive(Integer day) {
		int i = 0;
		for (int d : days) {
			if (day < d) {
				days.add(i, day);
				retroactive.add(day);
			}
			i++;
		}
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public List<Integer> getDays() {
		return days;
	}

	public void setDays(List<Integer> days) {
		this.days = days;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public List<Integer> getRetroactive() {
		return retroactive;
	}

	public void setRetroactive(List<Integer> retroactive) {
		this.retroactive = retroactive;
	}

	public Map<Integer, Boolean> getDraws() {
		return draws;
	}

	public void setDraws(Map<Integer, Boolean> draws) {
		this.draws = draws;
	}

	public void putDraw(Integer draw, boolean isDraw) {
		draws.put(draw, isDraw);
	}

	public boolean hasDraw(Integer draw) {
		return draws.containsKey(draw);
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public int getReSignCnt() {
		return reSignCnt;
	}

	public void setReSignCnt(int reSignCnt) {
		this.reSignCnt = reSignCnt;
	}

}
