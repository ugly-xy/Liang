package com.zb.models.othergames;

import java.io.Serializable;
import java.util.Map;

public class GuessItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1755560636172547211L;
	private String _id;
	private String name;
	private long amount;// 当前押注的总额
	private double rate;// 当前赔率
	private int count;//下注人数

	public String getId() {
		return _id;
	}

	public void setId(String id) {
		this._id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public GuessItem(String id, String name) {
		super();
		this._id = id;
		this.name = name;
		this.amount = 0;
		this.count = 0;
		this.rate = 100;
	}

	public GuessItem() {
		super();
	}

}
