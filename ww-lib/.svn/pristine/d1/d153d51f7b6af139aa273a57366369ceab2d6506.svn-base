package com.zb.service.room.vo;

public class ActivityPraiseLog {

	public ActivityPraiseLog(long uid, long pid, long roomId, long actId, int type) {
		this._id = pid + "_" + actId + "_" + uid;
		this.updateTime = System.currentTimeMillis();
		this.uid = uid;
		this.pid = pid;
		this.roomId = roomId;
		this.actId = actId;
		this.type = type;
	}

	private String _id;
	private long updateTime;
	private long uid;
	private long pid;
	private long roomId;
	private long actId;
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

	public long getRoomId() {
		return roomId;
	}

	public long getActId() {
		return actId;
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

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public void setActId(long actId) {
		this.actId = actId;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

}
