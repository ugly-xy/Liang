package com.we.models.sign;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SignWeek implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5096494720335652833L;

	public SignWeek() {

	}

	public SignWeek(long uid, int week) {
		this._id = week + "-" + uid;
		this.uid = uid;
		this.week = week;
		this.updateTime = System.currentTimeMillis();
	}

	private int week;// 201611
	private long uid;
	//第几天补签的  
	private List<Integer> retroactive = new ArrayList<Integer>();
	private String _id;
	private long updateTime;
	//签到的次数 以及是 第几天签到 
	private List<Integer> days = new ArrayList<Integer>();

	public void addDay(Integer day) {
		days.add(day);
	}

	public void addRetroactive(Integer day) {
		retroactive.add(day);
		days.add(day);
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

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
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
}
