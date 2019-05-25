package com.zb.models.st;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import com.zb.models.AbstractDocument;

@Document
public class UserGift implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9191501442272983663L;

	public UserGift() {

	}

	public UserGift(Long id, long uid, long sendUid, int bgId, int type, int day, long count, long amount) {
		this.type = type;
		this.amount = amount;
		this.bgId = bgId;
		this.day = day;
		this.count = count;
		this.uid = uid;
		this.sendUid = sendUid;
	}

	private long uid;// 接受用户id
	private long sendUid;// 赠送用户Id
	private int bgId;// 基础物品Id
	private int type;// 类型 0 总，1天，7周，30月
	private int day;// 时间 总 0| 天：20170314|周 201701 |月 201701
	private long count;// 礼物数量
	private long amount;// 礼物总价值

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

	public long getSendUid() {
		return sendUid;
	}

	public void setSendUid(long sendUid) {
		this.sendUid = sendUid;
	}

}
