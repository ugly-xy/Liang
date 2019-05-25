package com.zb.models;

import com.zb.common.Constant.Const;
import com.zb.common.http.UserAgent;

public class Feedback extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6766144157522404749L;

	private Long userId;// 用户id
	private String content; // 返回内容
	private String contact; // 联系方式
	private String[] pics; // 图片
	private Long createTime;
	private int status;// 状态
	private Double version; // 端版本号
	private Integer via;// 客户端类型
	private String devInfo; // 设备信息
	private UserAgent userAgent;// 头信息
	private String sysReply;//系统回复
	private String baseEnv;//手机app相关信息

	public Feedback(Long id, Long userId, String content, String contact,String baseEnv, String[] pics, Double version, Integer via,
			String devInfo, UserAgent userAgent) {
		this._id = id;
		this.updateTime = System.currentTimeMillis();
		this.contact = contact;
		this.content = content;
		this.pics = pics;
		this.devInfo = devInfo;
		this.createTime = updateTime;
		this.status = Const.STATUS_DEF;
		this.userAgent = userAgent;
		this.userId = userId;
		this.version = version;
		this.via = via;
		this.baseEnv=baseEnv;
	}

	public Feedback() {

	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String[] getPics() {
		return pics;
	}

	public void setPics(String[] pics) {
		this.pics = pics;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Double getVersion() {
		return version;
	}

	public void setVersion(Double version) {
		this.version = version;
	}

	public Integer getVia() {
		return via;
	}

	public void setVia(Integer via) {
		this.via = via;
	}

	public String getDevInfo() {
		return devInfo;
	}

	public void setDevInfo(String devInfo) {
		this.devInfo = devInfo;
	}

	public UserAgent getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(UserAgent userAgent) {
		this.userAgent = userAgent;
	}

	public String getSysReply() {
		return sysReply;
	}

	public void setSysReply(String sysReply) {
		this.sysReply = sysReply;
	}

	public String getBaseEnv() {
		return baseEnv;
	}

	public void setBaseEnv(String baseEnv) {
		this.baseEnv = baseEnv;
	}
}
