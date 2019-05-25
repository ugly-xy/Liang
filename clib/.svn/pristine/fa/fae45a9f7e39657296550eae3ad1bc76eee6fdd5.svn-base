package com.we.models.sign;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SignMonth implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5096494720335652833L;

	public SignMonth() {

	}

	public SignMonth(long uid, int month) {
		this._id = month + "-" + uid;
		this.uid = uid;
		this.month = month;
		this.updateTime = System.currentTimeMillis();
	}

	private int month;// 201611
	private long uid;
	private List<Integer> retroactive = new ArrayList<Integer>();
	private String _id;
	private long updateTime;
	private List<Integer> days = new ArrayList<Integer>();
	private Map<Integer, Boolean> draws = new LinkedHashMap<Integer, Boolean>();

	public void addDay(Integer day) {
		days.add(day);
	}

	public void addRetroactive(Integer day) {
		int i = 0;
		for (int d : days) {
			if (day < d) {
				break;
			}
			i++;
		}
		days.add(i, day);
		retroactive.add(0, day);

	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
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
}
