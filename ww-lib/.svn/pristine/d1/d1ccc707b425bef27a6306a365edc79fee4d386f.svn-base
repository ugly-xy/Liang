package com.zb.models.st;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class StCoinIO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9191501442272983663L;

	public StCoinIO() {

	}

	private String _id;//day-type
	private int type;// 支付类型
	private int day;// 时间 总 0| 天：20170314
	private long inAmount;// 收入总值
	private long outAmount;// 支付总值
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public long getInAmount() {
		return inAmount;
	}

	public void setInAmount(long inAmount) {
		this.inAmount = inAmount;
	}

	public long getOutAmount() {
		return outAmount;
	}

	public void setOutAmount(long outAmount) {
		this.outAmount = outAmount;
	}
}
