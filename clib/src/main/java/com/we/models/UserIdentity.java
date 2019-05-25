package com.we.models;

/** 用户身份图片 */
public class UserIdentity extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1910589179277698977L;

	private String realname;// 真实姓名
	private String number;// 证件号码
	private String frontPic;
	private String backPic;
	private String inHandPic;
	private int status;

	public String getFrontPic() {
		return frontPic;
	}

	public void setFrontPic(String frontPic) {
		this.frontPic = frontPic;
	}

	public String getBackPic() {
		return backPic;
	}

	public void setBackPic(String backPic) {
		this.backPic = backPic;
	}

	public String getInHandPic() {
		return inHandPic;
	}

	public void setInHandPic(String inHandPic) {
		this.inHandPic = inHandPic;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public UserIdentity() {
		super();
	}

	public UserIdentity(long _id, String realname, String number, String frontPic, String backPic, String inHandPic,
			int status) {
		super();
		this._id = _id;
		this.realname = realname;
		this.number = number;
		this.frontPic = frontPic;
		this.backPic = backPic;
		this.inHandPic = inHandPic;
		this.status = status;
	}

}
