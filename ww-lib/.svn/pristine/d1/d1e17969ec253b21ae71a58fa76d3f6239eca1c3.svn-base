package com.zb.service.room.vo;

import com.zb.models.AbstractDocument;

public class ActivityForbiddenLog extends AbstractDocument {

	private static final long serialVersionUID = 1150806204335711853L;

	public ActivityForbiddenLog(long uid, long beginForbiddenTime, long endForbiddenTime) {
		this._id = uid;
		this.updateTime = System.currentTimeMillis();
		this.beginForbiddenTime = beginForbiddenTime;
		this.endForbiddenTime = endForbiddenTime;
	}

	private long beginForbiddenTime;
	private long endForbiddenTime;

	public long getBeginForbiddenTime() {
		return beginForbiddenTime;
	}

	public long getEndForbiddenTime() {
		return endForbiddenTime;
	}

	public void setBeginForbiddenTime(long beginForbiddenTime) {
		this.beginForbiddenTime = beginForbiddenTime;
	}

	public void setEndForbiddenTime(long endForbiddenTime) {
		this.endForbiddenTime = endForbiddenTime;
	}

}
