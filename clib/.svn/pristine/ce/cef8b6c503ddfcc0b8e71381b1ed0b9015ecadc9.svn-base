package com.we.models;


import com.we.common.Constant.Const;

public class MailLog extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8652706221066181221L;

	private String mailNo;
	private String code;
	private Integer status;
	private Integer sendStatus = Const.STATUS_DEF;
	private Long uid;
	private String ip;

	public MailLog() {

	}

	public MailLog(Long id, String mailNo, String code,Long uid,String ip) {
		this._id = id;
		this.mailNo = mailNo;
		this.updateTime = System.currentTimeMillis();
		this.code = code;
		this.status = Const.STATUS_DEF;
		this.uid = uid;
		this.ip = ip;
	}

	public String getMailNo() {
		return mailNo;
	}

	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}
	
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
