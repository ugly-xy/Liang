package com.zb.service.room.vo;

public class ActivityReportLog {

	public ActivityReportLog(long uid, long rid, long roomId, long actId, int type) {
		this._id = rid + "_" + actId + "_" + uid;
		this.updateTime = System.currentTimeMillis();
		this.uid = uid;
		this.rid = rid;
		this.roomId = roomId;
		this.actId = actId;
		this.type = type;
	}

	private String _id;
	private long updateTime;
	private long uid;
	private long rid;
	private long roomId;
	private long actId;
	private int month;
	private int day;
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String get_id() {
		return _id;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public long getUid() {
		return uid;
	}

	public long getRid() {
		return rid;
	}

	public long getRoomId() {
		return roomId;
	}

	public long getActId() {
		return actId;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public void setRid(long rid) {
		this.rid = rid;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public void setActId(long actId) {
		this.actId = actId;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public void setDay(int day) {
		this.day = day;
	}

}
