package com.zb.models.usertask;

import java.util.ArrayList;
import java.util.List;

import com.zb.models.AbstractDocument;

public class Login7Day extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = -386661081275141106L;

	private List<Long> finishedTime = new ArrayList<Long>();

	public List<Long> getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(List<Long> finishedTime) {
		this.finishedTime = finishedTime;
	}

	public void addFinishedTime(long time) {
		finishedTime.add(time);
		super.updateTime = time;
	}

}
