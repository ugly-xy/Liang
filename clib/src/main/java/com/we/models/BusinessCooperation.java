package com.we.models;

import com.we.common.Constant.Const;

/** 商务合作 */
public class BusinessCooperation extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 107970692358307292L;

	private long uid;
	private String name;
	private String introduction;//项目介绍
	private String officialAddr;
	private String contact; //联系人
	private String phone;// 
	private String contactInfo;// 联系方式
	private String email;// 
	private String cooperation;// 合作意向
	private long createTime;
	private int status;// 状态 已处理 忽略 待处理
	private Long adminId;
	
	public long getUid() {
		return uid;
	}


	public void setUid(long uid) {
		this.uid = uid;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getOfficialAddr() {
		return officialAddr;
	}

	public void setOfficialAddr(String officialAddr) {
		this.officialAddr = officialAddr;
	}



	public String getContact() {
		return contact;
	}



	public void setContact(String contact) {
		this.contact = contact;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getContactInfo() {
		return contactInfo;
	}



	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCooperation() {
		return cooperation;
	}



	public void setCooperation(String cooperation) {
		this.cooperation = cooperation;
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

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public BusinessCooperation(long uid, String name, String introduction, String officialAddr, String contact, String phone,
			String contactInfo,String email,String cooperation) {
		super();
		this.uid = uid;
		this.name = name;
		this.introduction = introduction;
		this.officialAddr = officialAddr;
	    this.contact = contact;
	    this.phone = phone;
	    this.contactInfo = contactInfo;
	    this.email = email;
	    this.cooperation = cooperation;
	    this.createTime = System.currentTimeMillis();
	    this.status =  Const.STATUS_DEF;
	}

}
