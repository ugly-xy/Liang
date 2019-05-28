package com.zb.service.room.vo;

public class ActivityReportSt {

	public ActivityReportSt(long uid, int rType, int gType, long actId) {
		this._id = uid + "_" + actId + "_" + gType + "_" + ActivityReportSt.TYPE_ACT + "_" + rType;
		this.updateTime = System.currentTimeMillis();
		this.uid = uid;
		this.rType = rType;
		this.gType = gType;
		this.type = ActivityReportSt.TYPE_ACT;
		this.actId = actId;
	}

	public ActivityReportSt(long uid, int rType, int gType, int date) {
		this._id = uid + "_" + date + "_" + gType + "_" + ActivityReportSt.TYPE_DAY + "_" + rType;
		this.updateTime = System.currentTimeMillis();
		this.uid = uid;
		this.rType = rType;
		this.gType = gType;
		this.date = date;
		this.type = ActivityReportSt.TYPE_DAY;
	}

	public ActivityReportSt(long uid, int rType, int gType) {
		this._id = uid + "_" + date + "_" + gType + "_" + ActivityReportSt.TYPE_ALL + "_" + rType;
		this.updateTime = System.currentTimeMillis();
		this.uid = uid;
		this.rType = rType;
		this.gType = gType;
		this.type = ActivityReportSt.TYPE_ALL;
	}

	public static final int TYPE_ACT = 1;
	public static final int TYPE_DAY = 2;
	public static final int TYPE_ALL = 3;
	private String _id;
	private long updateTime;
	private int rType;
	private int gType;
	private long uid;
	private int count;
	private int date;
	private int type;
	private long actId;

	public long getActId() {
		return actId;
	}

	public void setActId(long actId) {
		this.actId = actId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String get_id() {
		return _id;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public int getrType() {
		return rType;
	}

	public int getgType() {
		return gType;
	}

	public long getUid() {
		return uid;
	}

	public void setrType(int rType) {
		this.rType = rType;
	}

	public void setgType(int gType) {
		this.gType = gType;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

}
