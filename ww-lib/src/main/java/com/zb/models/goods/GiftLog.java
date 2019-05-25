package com.zb.models.goods;

import com.zb.models.AbstractDocument;

public class GiftLog extends AbstractDocument {

	public static final int ROOM = 1;
	public static final int USER_ZONE = 2;
	public static final int CIRCLE = 3;
	public static final int FRIEND = 4;

	/**
	 * 
	 */
	private static final long serialVersionUID = -4777758398948069096L;

	public GiftLog() {

	}

	public GiftLog(Long id, long sendUid, long recvUid, int local, long localId, int bgId, int count, int amount,
			int packCount) {
		this._id = id;
		this.sendUid = sendUid;
		this.recvUid = recvUid;
		this.local = local;
		this.bgId = bgId;
		this.count = count;
		this.amount = amount;
		this.packCount = packCount;
		this.localId = localId;
		this.updateTime = System.currentTimeMillis();
	}

	private long sendUid;// 赠送者id
	private long recvUid;// 接收者id
	private long localId;
	private int local;// 赠送的地方
	private int bgId;// 基础物品ID
	private int count;// 赠送数量
	private int amount;// 总金额
	private int packCount;// 从背包消耗数量
	
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

	public long getRecvUid() {
		return recvUid;
	}

	public void setRecvUid(long recvUid) {
		this.recvUid = recvUid;
	}

	public int getBgId() {
		return bgId;
	}

	public void setBgId(int bgId) {
		this.bgId = bgId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getPackCount() {
		return packCount;
	}

	public void setPackCount(int packCount) {
		this.packCount = packCount;
	}

	public int getLocal() {
		return local;
	}

	public void setLocal(int local) {
		this.local = local;
	}
}
