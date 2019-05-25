package com.zb.models.lottery;

import com.zb.models.AbstractDocument;

public class LotteryLog extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3892339642091938286L;

	public static final int WIN = 1;
	public static final int FAIL = 2;

	public LotteryLog() {

	}

	public LotteryLog(Long id, long lotteryId, long uid) {
		this._id = id;
		this.lotteryId = lotteryId;
		this.uid = uid;
		this.status = FAIL;
		this.updateTime = System.currentTimeMillis();

	}

	public LotteryLog(Long id, long lotteryId, long uid, String itemId,
			Integer type, Long linkId, Integer amount) {
		this._id = id;
		this.lotteryId = lotteryId;
		this.uid = uid;
		this.status = WIN;
		this.itemId = itemId;
		this.type = type;
		this.linkId = linkId;
		this.amount = amount;
		this.updateTime = System.currentTimeMillis();
	}

	private long lotteryId;
	private long uid;
	private int status;
	private String itemId;// itemId
	private Integer type;// 奖品类型
	private Long linkId;// 对应的Id
	private Integer amount; // 单词中奖的物品数

	public long getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(long lotteryId) {
		this.lotteryId = lotteryId;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getLinkId() {
		return linkId;
	}

	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}
