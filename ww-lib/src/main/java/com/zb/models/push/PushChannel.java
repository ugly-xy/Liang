package com.zb.models.push;

import java.util.ArrayList;
import java.util.List;

import com.zb.common.Constant.Const;
import com.zb.models.AbstractDocument;

public class PushChannel extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7212961329689902213L;

	public PushChannel() {

	}

	public PushChannel(Long userId, Integer devType, String channelId, Integer channelType, String appCode) {
		this._id = userId;
		if (channelType == null)
			channelType = 10;
		this.channelType = channelType;
		this.devType = devType;
		this.channelId = channelId;
		this.createTime = System.currentTimeMillis();
		this.updateTime = this.createTime;
		this.status = Const.STATUS_OK;
		this.appCode = appCode;
	}

	private int devType;

	private String channelId;

	private Integer channelType;

	private List<String> tags = null;

	private Long createTime;

	private int status;

	private String appCode;

	public int getDevType() {
		return devType;
	}

	public void setDevType(int devType) {
		this.devType = devType;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public PushChannel addTag(String tag) {
		if (tags == null) {
			tags = new ArrayList<String>();
		}
		tags.add(tag);
		return this;
	}

	public PushChannel removeTag(String tag) {
		if (tags != null) {
			tags.remove(tag);
		}
		return this;
	}

	public PushChannel removeAll() {
		if (tags != null) {
			tags = null;
		}
		return this;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	public Integer getChannelType() {
		return channelType;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
}
