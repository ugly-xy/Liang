package com.zb.models.user;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Relationship implements Serializable {

	public static final int FRIENDS = 4;// 成为好友
	public static final int BLACK = 3;// 黑名单
	public static final int DESC_FRIENDS = 2;// 好友接收者
	public static final int SRC_FRIENDS = 1;// 好友发起者
	public static final int NULL = 0;// 陌生人

	/**
	 * 
	 */
	private static final long serialVersionUID = 9191501442272983663L;

	public Relationship(){}
	
	public Relationship(String id, Long myId, Long rid, Integer r) {
		this._id = id;
		this.myId = myId;
		this.rid = rid;
		this.r = r;
		this.updateTime = System.currentTimeMillis();
	}

	private Long updateTime;
	
	private String _id;

	private Long myId; // 我的ID

	private Long rid;// 关注人ID

	private Integer r = 0;

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public Long getMyId() {
		return myId;
	}

	public void setMyId(Long myId) {
		this.myId = myId;
	}

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public Integer getR() {
		return r;
	}

	public void setR(Integer r) {
		this.r = r;
	}

}
