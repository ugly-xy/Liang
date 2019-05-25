package com.zb.models.goods;

import com.zb.models.AbstractDocument;

public class PropLog extends AbstractDocument {

	public static final int ROOM = 1;
	public static final int USER_ZONE = 2;
	public static final int CIRCLE = 3;
	private static final long serialVersionUID = -4777758398948069096L;

	public PropLog() {

	}

	public PropLog(Long id, long uid, Long toUid, int local, long localId, int bgId, int count, int amount,
			int packCount) {
		this._id = id;
		this.uid = uid;
		this.toUid = toUid;
		this.local = local;
		this.bgId = bgId;
		this.count = count;
		this.amount = amount;
		this.packCount = packCount;
		this.localId = localId;
		this.updateTime = System.currentTimeMillis();
	}

	private long uid; // 使用者id
	private Long toUid;// 对谁使用
	private long localId;// 地址id 如房间号
	private int local;// 使用的地方
	private int bgId;// 基础物品ID
	private int count;// 使用数量
	private int amount;// 总金额
	private int packCount;// 从背包消耗数量

	public long getLocalId() {
		return localId;
	}

	public void setLocalId(long localId) {
		this.localId = localId;
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

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public Long getToUid() {
		return toUid;
	}

	public void setToUid(Long toUid) {
		this.toUid = toUid;
	}
}
