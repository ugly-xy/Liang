package com.zb.models.usertask;

import java.io.Serializable;

public class OneTaskLog implements Serializable {

	private static final long serialVersionUID = -3729002737870524625L;

	private String _id;
	private long tid;
	private long uid;
	private long createTime;


	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public long getTid() {
		return tid;
	}

	public void setTid(long tid) {
		this.tid = tid;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public OneTaskLog() {
		super();
	}

	public OneTaskLog(long tid,long uid) {
		this._id = buildId(tid,uid);
		this.tid = tid;
		this.uid = uid;
		this.createTime = System.currentTimeMillis();
	}

	public static String buildId(long tid,long uid){
		return tid+"-"+uid;
	}
}
