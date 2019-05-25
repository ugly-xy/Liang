package com.we.view;

import java.io.Serializable;
import java.util.List;

import com.we.models.sign.SignWeek;

public class SignWeekVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 747596052475703800L;
	public static int reSign_ok = 0;
	public static int reSign_err = 1;
	public static int todaySign_yes = 0;
	public static int todaySign_no = 1;

	public SignWeekVO() {

	}

	public SignWeekVO(int day, SignWeek sw, String reward, int reSignCnt) {
		this._id = sw.get_id();
		this.uid = sw.getUid();
		this.week = sw.getWeek();
		this.reward = reward;
		this.retroactive = sw.getRetroactive().size();
		this.days = sw.getDays().size();
		this.reSignCnt = reSignCnt;
		this.haveReSign = getHaveSign(day, sw);
		this.todaySign = getTodaySign(day, sw);
	}

	// 第几年的第几周
	private int week;// 201611
	private long uid;
	// 本周补签的天数
	private int retroactive;
	private String _id;
	// 本周签到天数
	private int days ;
	// 签到奖励
	private String reward; // "type" + "-"+ "count" 1玫瑰 2金币 三经验 4铜宝箱5银宝箱6金宝箱
	// 默认可补签次数
	private int reSignCnt = 1;
	// 是否拥有可以补签的天
	private int haveReSign;
	// 今天是否已经签到
	private int todaySign;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getRetroactive() {
		return retroactive;
	}

	public void setRetroactive(int retroactive) {
		this.retroactive = retroactive;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	public int getReSignCnt() {
		return reSignCnt;
	}

	public void setReSignCnt(int reSignCnt) {
		this.reSignCnt = reSignCnt;
	}

	public int getHaveReSign() {
		return haveReSign;
	}

	public void setHaveReSign(int haveReSign) {
		this.haveReSign = haveReSign;
	}

	public int getTodaySign() {
		return todaySign;
	}

	public void setTodaySign(int todaySign) {
		this.todaySign = todaySign;
	}

	// 判断是否有补签天
	private int getHaveSign(int day, SignWeek week) {
		List<Integer> list = week.getDays();
		if (!list.contains(day)) {
			// 如果今天没签到
			if (day - list.size() > 1) {
				return reSign_ok;
			}
			return reSign_err;
		} else {
			// 如果今天签到了
			if (day - list.size() > 0) {
				return reSign_ok;
			}
		}
		// 不能补签
		return reSign_err;
	}

	private int getTodaySign(int day, SignWeek week) {
		List<Integer> list = week.getDays();
		if (list.contains(day)) {
			return todaySign_yes;
		}
		return todaySign_no;
	}
}
