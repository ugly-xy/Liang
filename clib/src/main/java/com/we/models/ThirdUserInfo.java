package com.we.models;

public class ThirdUserInfo{
	public ThirdUserInfo() {
		
	}
	
	public ThirdUserInfo(com.we.service.userTask.model.User u) {
		this.username = u.getUsername();
		this.firstname = u.getFirst_name();
		this.lastName = u.getLast_name();
		this.openId = String.valueOf(u.getId());
		this.languageCode = u.getLanguage_code();
	}
	
	private String username;
	private String firstname;
	private String lastName;
	private String languageCode;
	private String openId;
	private String unionId;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getLanguageCode() {
		return languageCode;
	}
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
}
