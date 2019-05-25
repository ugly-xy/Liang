package com.zb.models.room;

import java.io.Serializable;

//import com.fasterxml.jackson.annotation.JsonSubTypes;
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import com.zb.models.room.activity.Undercover;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "typeName")  
//@JsonSubTypes({ @JsonSubTypes.Type(value = Undercover.class, name = "Undercover") })  
public class Activity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8671353845056763564L;
	public static final int STATUS_READY = 1;

	private int status = STATUS_READY;
	private long _id;
	private Long updateTime;
	private Long owner;// 房管
	public Long getOwner() {
		return owner;
	}

	public void setOwner(Long owner) {
		this.owner = owner;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}
}
