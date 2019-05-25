package com.zb.models;

import java.io.Serializable;

public class UserAttenRoom implements Serializable {

	private static final long serialVersionUID = -6988369518721490596L;
	private String _id;// uid-roomId
	private long uid;
	private long roomId;
	private long updateTime;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public UserAttenRoom(String _id, long uid, long roomId, long updateTime) {
		super();
		this._id = _id;
		this.uid = uid;
		this.roomId = roomId;
		this.updateTime = updateTime;
	}

	public UserAttenRoom() {
		super();
	}

}
