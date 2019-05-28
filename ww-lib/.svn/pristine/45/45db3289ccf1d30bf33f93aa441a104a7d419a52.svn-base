package com.zb.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MobileVO implements Serializable{
	private static final long serialVersionUID = -6198182444180315834L;
	public class Mobile implements Serializable{
		private static final long serialVersionUID = 6582576946846670829L;
		private String name;  //通讯录姓名
		private String nickName; //昵称
		private long uid; //uid
		private String avatar;  //头像
		private int r;  //关系
	    private String phone;  //手机号
	    private int friendWorth;//身价
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getNickName() {
			return nickName;
		}
		public void setNickName(String nickName) {
			this.nickName = nickName;
		}
		public long getUid() {
			return uid;
		}
		public void setUid(long uid) {
			this.uid = uid;
		}
		public String getAvatar() {
			return avatar;
		}
		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}
		public int getR() {
			return r;
		}
		public void setR(int r) {
			this.r = r;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public Mobile(String name, String nickName, long uid, String avatar, int r, String phone,int friendWorth) {
			super();
			this.name = name;
			this.nickName = nickName;
			this.uid = uid;
			this.avatar = avatar;
			this.r = r;
			this.phone = phone;
			this.friendWorth=friendWorth;
		}
		public Mobile(String name, String phone) {
			super();
			this.name = name;
			this.phone = phone;
			this.nickName="";
			this.avatar="";
		}
		public Mobile() {
			super();
		}
	}
    private int isReg=0;
    private int noReg=0;
    private List<Mobile> isRegs=new ArrayList<Mobile>();
    private List<Mobile> noRegs=new ArrayList<Mobile>();
	public int getIsReg() {
		return isReg;
	}
	public void setIsReg(int isReg) {
		this.isReg = isReg;
	}
	public int getNoReg() {
		return noReg;
	}
	public void setNoReg(int noReg) {
		this.noReg = noReg;
	}
	public List<Mobile> getIsRegs() {
		return isRegs;
	}
	public void setIsRegs(List<Mobile> isRegs) {
		this.isRegs = isRegs;
	}
	public List<Mobile> getNoRegs() {
		return noRegs;
	}
	public void setNoRegs(List<Mobile> noRegs) {
		this.noRegs = noRegs;
	}
	public MobileVO(int isReg, int noReg, List<Mobile> isRegs, List<Mobile> noRegs) {
		super();
		this.isReg = isReg;
		this.noReg = noReg;
		this.isRegs = isRegs;
		this.noRegs = noRegs;
	}
	public MobileVO() {
		super();
	}
}
