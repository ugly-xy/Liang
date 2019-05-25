package com.zb.models.third;

import com.zb.common.Constant.Const;
import com.zb.models.AbstractDocument;

public class Agent extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3108437538621958463L;

	public Agent() {

	}

	public Agent(Long id, String name, String phone, String pwd, Double rate, Double coinRate, Long adminId) {
		this._id = id;
		this.name = name;
		this.phone = phone;
		this.pwd = pwd;
		this.rate = rate;
		this.coinRate = coinRate;
		this.adminId = adminId;
		this.updateTime = System.currentTimeMillis();
	}

	private String name;
	private String phone;
	private String pwd;
	private double rate;
	private double coinRate;
	private int status = Const.STATUS_OK;
	private Long adminId;
	private long amount;//代理商点，预留

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getCoinRate() {
		return coinRate;
	}

	public void setCoinRate(double coinRate) {
		this.coinRate = coinRate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}
}
