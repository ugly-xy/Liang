package com.zb.models.usertask;

import com.zb.models.AbstractDocument;

public class OneTask extends AbstractDocument {

	public static final int UP = 1; // 版本升级赠送
	public static final int ALL = 2; // 只要大于版本赠送
	private static final long serialVersionUID = 8725005323295802173L;
	private String name;
	private int status;
	private long st;
	private long et;
	private double version;
	private int coin;
	private int point;
	private int type=ALL;
	private String prop;

	public OneTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OneTask(String name,int type, int status, long st, long et, double version, int coin, int point, String prop) {
		super();
		this.name = name;
		this.status = status;
		this.st = st;
		this.et = et;
		this.version = version;
		this.coin = coin;
		this.point = point;
		this.prop = prop;
		this.type=type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getSt() {
		return st;
	}

	public void setSt(long st) {
		this.st = st;
	}

	public long getEt() {
		return et;
	}

	public void setEt(long et) {
		this.et = et;
	}

	public double getVersion() {
		return version;
	}

	public void setVersion(double version) {
		this.version = version;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getProp() {
		return prop;
	}

	public void setProp(String prop) {
		this.prop = prop;
	}
    
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "CoinGiven [name=" + name + ", status=" + status + ", st=" + st + ", et=" + et + ", version=" + version
				+ ", coin=" + coin + ", point=" + point + ", prop=" + prop + "]";
	}
}
