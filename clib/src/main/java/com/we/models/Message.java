package com.we.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Message extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9191501442272983663L;

	public static final int TYPE_NOTICE = 1;
	public static final int TYPE_USER_MESSAGE = 2;

	public static final int STATUS_DEL = -1;
	public static final int STATUS_UNREAD = 2;
	public static final int STATUS_READ = 1;

	public Message() {

	}

	public Message(Long id, int type, String sendNickname, Long sendUid,
			Integer status, Long recvUid, String sendAvatar, String content,
			Long baseId, String op, String opId) {
		this._id = id;
		this.sendAvatar = sendAvatar;
		this.content = content;
		this.createTime = System.currentTimeMillis();
		this.recvUid = recvUid;
		this.sendUid = sendUid;
		this.sendNickname = sendNickname;
		this.status = status;
		this.updateTime = this.createTime;
		this.type = type;
		this.baseId = baseId;
		this.op = op;
		this.opId = opId;
	}

	private String sendNickname;
	private Long sendUid;
	private String sendAvatar;
	private Integer status = STATUS_UNREAD;
	private Long createTime;
	private int type = 1;
	private Long recvUid;
	// private String title;
	private String content;
	private Long baseId;
	private String op;
	private String opId;

	public String getSendNickname() {
		return sendNickname;
	}

	public void setSendNickname(String sendNickname) {
		this.sendNickname = sendNickname;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Long getRecvId() {
		return recvUid;
	}

	// public String getTitle() {
	// return title;
	// }
	//
	// public void setTitle(String title) {
	// this.title = title;
	// }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getBaseId() {
		return baseId;
	}

	public void setBaseId(Long baseId) {
		this.baseId = baseId;
	}

	public Long getSendUid() {
		return sendUid;
	}

	public void setSendUid(Long sendUid) {
		this.sendUid = sendUid;
	}

	public Long getRecvUid() {
		return recvUid;
	}

	public void setRecvUid(Long recvUid) {
		this.recvUid = recvUid;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getOpId() {
		return opId;
	}

	public void setOpId(String opId) {
		this.opId = opId;
	}

	public String getSendAvatar() {
		return sendAvatar;
	}

	public void setSendAvatar(String sendAvatar) {
		this.sendAvatar = sendAvatar;
	}

}
