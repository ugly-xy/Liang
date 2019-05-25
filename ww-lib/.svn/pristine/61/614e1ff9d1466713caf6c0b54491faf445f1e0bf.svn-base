package com.zb.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserPack extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9191501442272983663L;

	public UserPack() {

	}
	
	public UserPack(long id, long uid, int type, long baseId) {
		this._id = id;
		this.uid = uid;
		this.type = type;
		this.baseId = baseId;
	}

	public UserPack(long id, long uid, int type, int baseId, long amount,
			long expires) {
		this._id = id;
		this.uid = uid;
		this.type = type;
		this.baseId = baseId;
		this.amount = amount;
		this.expires = expires;
	}

	private long uid;//用户Id
	private int type;//物品类型
	private long baseId;// 基础物品id
	private long amount; // 数量
	private long expires = 0L;// 到期时间 0 没有到期时间

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getBaseId() {
		return baseId;
	}

	public void setBaseId(int baseId) {
		this.baseId = baseId;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public long getExpires() {
		return expires;
	}

	public void setExpires(long expires) {
		this.expires = expires;
	}

}
