package com.we.models;


import com.we.common.Constant.Const;

public class SmsLog extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5096494720335652833L;

	private String phoneNo;
	private String code;
	private Integer status;
	private Integer sendStatus = Const.STATUS_DEF;
	private String ip;

	public SmsLog() {

	}

	public SmsLog(Long id, String phoneNo, String code,String ip) {
		this._id = id;
		this.phoneNo = phoneNo;
		this.updateTime = System.currentTimeMillis();
		this.code = code;
		this.status = Const.STATUS_DEF;
		this.ip = ip;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
