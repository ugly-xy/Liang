package com.zb.models;

import java.io.Serializable;

public class People implements Serializable{
	private static final long serialVersionUID = 8895436238873219700L;
	private String rName; // 通讯录备注名
	private String rPhone; // 联系方式

	public String getrName() {
		return rName;
	}

	public void setrName(String rName) {
		this.rName = rName;
	}

	public String getrPhone() {
		return rPhone;
	}

	public void setrPhone(String rPhone) {
		this.rPhone = rPhone;
	}

	public People(String rName, String rPhone) {
		super();
		this.rName = rName;
		this.rPhone = rPhone;
	}

	public People() {
		super();
	}

	@Override
	public String toString() {
		return "People [rName=" + rName + ", rPhone=" + rPhone + "]";
	}

}
