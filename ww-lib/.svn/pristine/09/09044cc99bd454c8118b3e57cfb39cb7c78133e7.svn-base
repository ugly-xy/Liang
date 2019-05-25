package com.zb.models.room.activity;

import java.io.Serializable;

import com.zb.models.room.Actor;

public class DrawSomethingView implements Serializable, Comparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DrawSomethingView() {

	}

	public DrawSomethingView(DrawSomethingActor ua) {
		this.role = ua.getRole();
		this.status = ua.getStatus();
		this.ucStatus = ua.getUcStatus();
		this.uid = ua.getUid();
		this.talk = ua.getTalk();
		this.curPoint = ua.getCurPoint();
		this.inTime = ua.getInTime();
	}

	String talk;// 描述语言
	private long uid;
	private int status;
	private int ucStatus;
	private int role;// 房间内角色 是否观众
	private int curPoint;
	private long inTime ;

	public String getTalk() {
		return talk;
	}

	public void setTalk(String talk) {
		this.talk = talk;
	}



	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getUcStatus() {
		return ucStatus;
	}

	public void setUcStatus(int ucStatus) {
		this.ucStatus = ucStatus;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getCurPoint() {
		return curPoint;
	}

	public void setCurPoint(int curPoint) {
		this.curPoint = curPoint;
	}
	
	
	public long getInTime() {
		return inTime;
	}

	public void setInTime(long inTime) {
		this.inTime = inTime;
	}

	@Override
	public int compareTo(Object o) {
		DrawSomethingView a = (DrawSomethingView) o;
		return (int) (this.uid - a.uid);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Actor) {
			if (this.getUid() == ((DrawSomethingActor) o).getUid())
				return true;
		}
		return false;
	}


}
