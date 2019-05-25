package com.we.models.article;

import com.we.models.AbstractDocument;

public class Topic extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6016361050004072890L;

	public static int TOPIC_DOWN=-1;
	public static int TOPIC_UP=3;
	private String name;
	private int count;
	private double sort;
	private long createTime;
	private int status;
	private long groupId;

	public Topic() {

	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public Topic(long id, String name, int count, double sort, Long updateTime, int status, long groupId) {
		this._id = id;
		this.name = name;
		this.count = count;
		this.sort = sort;
		super.updateTime = updateTime;
		this.createTime = updateTime;
		this.status = status;
		this.groupId = groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getSort() {
		return sort;
	}

	public void setSort(double sort) {
		this.sort = sort;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}