package com.we.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User extends AbstractDocument {

	public static final int STATUS_OK = 1;
	public static final int STATUS_LOCK = -1;

	// 默认封面
	public static final String DEFAULT_AVATAR = "http://zim.zhuangdianbi.com/default_avata.png";
	/**
	 * 
	 */
	private static final long serialVersionUID = 9191501442272983663L;

	public User() {

	}

	public User(String username, String password, String ip) {
		this.username = username;
		this.pwd = password;
		this.regIp = ip;
		this.createTime = System.currentTimeMillis();
		this.updateTime = createTime;
	}

	public User(String phone, String password, String walletAddress, String email, String ip, String encode) {
		this.username = phone;
		this.phone = phone;
		this.pwd = password;
		this.walletAddress = walletAddress;
		this.email = email;
		this.regIp = ip;
		this.phoneVeri = true;
		this.createTime = System.currentTimeMillis();
		this.updateTime = createTime;
		this.encode = encode;
	}

	public User(String phone, String password, String ip, String encode) {
		this.username = phone;
		this.phone = phone;
		this.pwd = password;
		this.regIp = ip;
		this.phoneVeri = true;
		this.createTime = System.currentTimeMillis();
		this.updateTime = createTime;
		this.encode = encode;
	}

	public User(int loginType, String openId, String ip, int ch, int childCh) {
		this.regIp = ip;
		this.thirdType = loginType;
		this.openId = openId;
		this.ch = ch;
		this.childCh = childCh;
		this.createTime = System.currentTimeMillis();
		this.updateTime = createTime;
	}

	public User(String username, String password, String ip, int ch, int childCh) {
		this.username = username;
		this.pwd = password;
		this.regIp = ip;
		this.ch = ch;
		this.childCh = childCh;
		this.createTime = System.currentTimeMillis();
		this.updateTime = createTime;
	}

	private String username;

	private String pwd, email, phone;
	private String encode;// 国码 用于区分手机号的国家
	private Integer status = 1;
	private Long createTime;

	private int thirdType = 1;
	private String openId;
	private String unionId;

	private boolean emailVeri, phoneVeri;
	private int ch = 1;
	private int childCh = 1;
	private String regIp;
	private int sex = 0; // 2女 1男
	private int role = 1;// 普通用户

	private String lbs;
	private String province;
	private String city;
	private Long birthday;

	private String walletAddress;// 钱包地址
	private String shareCode;// 推荐码
	private Long shareUid;// 推荐人uid
	private Integer divisionId = 1;// 用户当前段位ID

	private Long telegramUid;// 对应的telegram用户id
	private String photo;

	public String getWalletAddress() {
		return walletAddress;
	}

	public void setWalletAddress(String walletAddress) {
		this.walletAddress = walletAddress;
	}

	public Long getBirthday() {
		return birthday;
	}

	public void setBirthday(Long birthday) {
		this.birthday = birthday;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getStatus() {
		return status;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public int getThirdType() {
		return thirdType;
	}

	public void setThirdType(int thirdType) {
		this.thirdType = thirdType;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public boolean isEmailVeri() {
		return emailVeri;
	}

	public void setEmailVeri(boolean emailVeri) {
		this.emailVeri = emailVeri;
	}

	public boolean isPhoneVeri() {
		return phoneVeri;
	}

	public void setPhoneVeri(boolean phoneVeri) {
		this.phoneVeri = phoneVeri;
	}

	public int getCh() {
		return ch;
	}

	public void setCh(int ch) {
		this.ch = ch;
	}

	public int getChildCh() {
		return childCh;
	}

	public void setChildCh(int childCh) {
		this.childCh = childCh;
	}

	public String getRegIp() {
		return regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getLbs() {
		return lbs;
	}

	public void setLbs(String lbs) {
		this.lbs = lbs;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getShareCode() {
		return shareCode;
	}

	public void setShareCode(String shareCode) {
		this.shareCode = shareCode;
	}

	public Long getShareUid() {
		return shareUid;
	}

	public void setShareUid(Long shareUid) {
		this.shareUid = shareUid;
	}


	public Integer getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Integer divisionId) {
		this.divisionId = divisionId;
	}

	public Long getTelegramUid() {
		return telegramUid;
	}

	public void setTelegramUid(Long telegramUid) {
		this.telegramUid = telegramUid;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

}
