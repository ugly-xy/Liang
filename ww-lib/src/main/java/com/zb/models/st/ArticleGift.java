package com.zb.models.st;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ArticleGift implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5172151287025117714L;
	private long localId;// 接受的文章id
	private long sendUid;// 赠送用户Id
	private int bgId;// 基础物品Id
	private long count;// 礼物数量
	private long amount;// 礼物总价值

	public ArticleGift() {
		super();
	}

	public ArticleGift(long localId, long sendUid, int bgId, long count, long amount) {
		super();
		this.localId = localId;
		this.sendUid = sendUid;
		this.bgId = bgId;
		this.count = count;
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "CircleGift [localId=" + localId + ", sendUid=" + sendUid + ", bgId=" + bgId + ", count=" + count
				+ ", amount=" + amount + "]";
	}

	public long getLocalId() {
		return localId;
	}

	public void setLocalId(long localId) {
		this.localId = localId;
	}

	public long getSendUid() {
		return sendUid;
	}

	public void setSendUid(long sendUid) {
		this.sendUid = sendUid;
	}

	public int getBgId() {
		return bgId;
	}

	public void setBgId(int bgId) {
		this.bgId = bgId;
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
}
