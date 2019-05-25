package com.zb.models.st;

import org.springframework.data.mongodb.core.mapping.Document;

import com.zb.models.AbstractDocument;

@Document
public class GiftRecvTop extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9191501442272983663L;

	public GiftRecvTop() {

	}

	public GiftRecvTop(long uid, int bgId, int type, int day, long count, long amount, String avatar, String nickname,
			String personLabel,int sex) {
		this.type = type;
		this.amount = amount;
		this.bgId = bgId;
		this.day = day;
		this.count = count;
		this.uid = uid;
		this.avatar = avatar;
		this.nickname = nickname;
		this.personLabel = personLabel;
		this.updateTime = System.currentTimeMillis();
		this.sex = sex;
	}

	private long uid;// 用户id
	private int bgId;// 基础物品Id
	private int type;// 类型 0 总，1天，7周，30月
	private int day;// 时间 总 0| 天：20170314|周 201701 |月 201701
	private long count;// 礼物数量
	private long amount;// 礼物总价值
	private String avatar;
	private String nickname;
	private String personLabel;
	private int sex = 2;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getBgId() {
		return bgId;
	}

	public void setBgId(int bgId) {
		this.bgId = bgId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPersonLabel() {
		return personLabel;
	}

	public void setPersonLabel(String personLabel) {
		this.personLabel = personLabel;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}
	
}
