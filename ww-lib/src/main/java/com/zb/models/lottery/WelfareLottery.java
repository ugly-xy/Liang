package com.zb.models.lottery;

import com.zb.models.AbstractDocument;

public class WelfareLottery extends AbstractDocument {

	private static final long serialVersionUID = 8551982465478516955L;
	private int issue;
	private String no;
	private String date;
	private int sum;
	private int city; // 1bj 2sh

	public WelfareLottery() {
	}

	public WelfareLottery(int issue, String no, String date, int city, int sum) {
		this.updateTime = System.currentTimeMillis();
		this.issue = issue;
		this.no = no;
		this.date = date;
		this.city = city;
		this.sum = sum;
	}

	public int getIssue() {
		return issue;
	}

	public void setIssue(int issue) {
		this.issue = issue;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public String getNo() {
		return no;
	}

	public String getDate() {
		return date;
	}

	public int getSum() {
		return sum;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

}