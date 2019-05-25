package com.zb.models;

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
		this.nickname = username;
		this.createTime = System.currentTimeMillis();
		this.updateTime = createTime;
	}

	public User(String username, String password, String nickname, String ip) {
		this.username = username;
		this.pwd = password;
		this.regIp = ip;
		this.nickname = nickname;
		this.createTime = System.currentTimeMillis();
		this.updateTime = createTime;
	}

	public User(String phone, String password, String ip, boolean phoneReg) {
		this.phone = phone;
		this.pwd = password;
		this.regIp = ip;
		this.phoneVeri = true;
		this.createTime = System.currentTimeMillis();
		this.updateTime = createTime;
		this.nickname = phone;
	}

	public User(int loginType, String openId, String ip, int ch, int childCh, String appCode) {
		this.regIp = ip;
		this.thirdType = loginType;
		this.openId = openId;
		this.ch = ch;
		this.childCh = childCh;
		this.createTime = System.currentTimeMillis();
		this.updateTime = createTime;
		this.appCode = appCode;
	}

	public User(String username, String password, String ip, int ch, int childCh) {
		this.username = username;
		this.pwd = password;
		this.regIp = ip;
		this.ch = ch;
		this.childCh = childCh;
		this.nickname = username;
		this.createTime = System.currentTimeMillis();
		this.updateTime = createTime;
	}

	private String username;

	private String pwd, email, phone;

	private Integer status = 1;
	private Long createTime;

	private int thirdType = 1;
	private String openId;
	private String unionId;
	private String avatar;

	private boolean emailVeri, phoneVeri;
	private int ch = 1;
	private int childCh = 1;
	private String regIp;
	private int sex = 2; // 2女 1男
	private int point = 0;
	private String nickname;
	private int role = 1;// 普通用户
	private int coin = 0;// 用户虚拟币金币
	private int silver = 0;

	private String lbs;
	private String province;
	private String city;
	private Long birthday;

	private int vip;
	// private int chatReg = 0;// 0 未注册 1 环信注册 2 阿里
	private boolean chatReg;// 默认false 未注册 环信
	private int friendWorth = 1;
	private String appCode;

	private Boolean modifyLabel;// 修改签名权限

	private String cover;// 封面
	private String[] photos;// 相册
	private String[] interests; // 兴趣标签
	private String personLabel; // 个性签名

	private Long guard;
	private Integer guardCnt;
	private Long guardExpiryTime;// 守护特效有效期

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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public int getVip() {
		return vip;
	}

	public void setVip(int vip) {
		this.vip = vip;
	}

	public int getSilver() {
		return silver;
	}

	public void setSilver(int silver) {
		this.silver = silver;
	}

	public int vipLevel() {
		return User.compVipLevel(vip);
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

	// 新版本
	// public int getChatReg() {
	// return chatReg;
	// }
	//
	// public void setChatReg(int chatReg) {
	// this.chatReg = chatReg;
	// }

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String[] getPhotos() {
		return photos;
	}

	public void setPhotos(String[] photos) {
		this.photos = photos;
	}

	public String[] getInterests() {
		return interests;
	}

	public void setInterests(String[] interests) {
		this.interests = interests;
	}

	public String getPersonLabel() {
		return personLabel;
	}

	public void setPersonLabel(String personLabel) {
		this.personLabel = personLabel;
	}

	public int getFriendWorth() {
		return friendWorth;
	}

	public void setFriendWorth(int friendWorth) {
		this.friendWorth = friendWorth;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public boolean isChatReg() {
		return chatReg;
	}

	public void setChatReg(boolean chatReg) {
		this.chatReg = chatReg;
	}

	public Boolean getModifyLabel() {
		return modifyLabel;
	}

	public void setModifyLabel(Boolean modifyLabel) {
		this.modifyLabel = modifyLabel;
	}

	public Long getGuard() {
		return guard;
	}

	public void setGuard(Long guard) {
		this.guard = guard;
	}

	public Integer getGuardCnt() {
		return guardCnt;
	}

	public void setGuardCnt(Integer guardCnt) {
		this.guardCnt = guardCnt;
	}

	public Long getGuardExpiryTime() {
		return guardExpiryTime;
	}

	public void setGuardExpiryTime(Long guardExpiryTime) {
		this.guardExpiryTime = guardExpiryTime;
	}

	public static int compVipLevel(int value) {
		if (value < 100) {
			return 0;
		} else if (value < 200) {
			return 1;
		} else if (value < 500) {
			return 2;
		} else if (value < 1000) {
			return 3;
		} else if (value < 3000) {
			return 4;
		} else if (value < 8000) {
			return 5;
		} else if (value < 15000) {
			return 6;
		} else if (value < 30000) {
			return 7;
		} else if (value < 60000) {
			return 8;
		} else if (value < 100000) {
			return 9;
		} else {
			return 10;
		}
	}
}
