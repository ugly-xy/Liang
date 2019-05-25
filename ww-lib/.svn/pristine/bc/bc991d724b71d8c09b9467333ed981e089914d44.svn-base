package com.zb.models.user;

public class MonitorLog {
	public MonitorLog() {

	}

	public MonitorLog(long loginTime, String ip, String lbs,int loginType) {
		this.loginCnt = 1;
		this.loginTime = loginTime;
		this.ip = ip;
		this.lbs = lbs;
		this.loginType = loginType;
	}

	int loginCnt;
	long loginTime;
	Integer rechargeCnt;
	Long rechargeTime;
	int loginType ;
	Integer amount;
	String ip;
	String lbs;
	int status;

	public void newLogin(long time, String ip, String lbs,int loginType) {
		this.loginCnt++;
		this.loginTime = time;
		this.ip = ip;
		this.lbs = lbs;
		this.loginType = loginType;
	}

	public void newRechange(long time, int amount) {
		if (this.rechargeCnt == null) {
			this.rechargeCnt = 1;
			this.amount = amount;
		} else {
			this.rechargeCnt++;
			this.amount += amount;
		}
		this.rechargeTime = time;
	}

	public int getLoginCnt() {
		return loginCnt;
	}

	public void setLoginCnt(int loginCnt) {
		this.loginCnt = loginCnt;
	}

	public long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}

	public Long getRechargeTime() {
		return rechargeTime;
	}

	public void setRechargeTime(Long rechargeTime) {
		this.rechargeTime = rechargeTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLbs() {
		return lbs;
	}

	public void setLbs(String lbs) {
		this.lbs = lbs;
	}

	public Integer getRechargeCnt() {
		return rechargeCnt;
	}

	public void setRechargeCnt(Integer rechargeCnt) {
		this.rechargeCnt = rechargeCnt;
	}


	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getLoginType() {
		return loginType;
	}

	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}

}
