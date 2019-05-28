package com.zb.models.slave;

import com.zb.models.AbstractDocument;

public class SlaveSellLog extends AbstractDocument {

	private static final long serialVersionUID = 7830414219410616093L;
	public final static int BUY_SUCCESS = 1; // 交易成功
	public final static int BUY_FAILURE = 2; // 交易失败
	public final static int BUY_ROLLBACK = 3; // 交易回滚
	public final static int RELEASE = 4; // 流放奴隶
	public final static int BLACK = 5; // 拉黑奴隶
	private long buyId; // 购买人
	private long beBuyId;
	private long sellId; // 出售人
	private long worth; // 交易价格
	private long buyTime; // 交易时间
	private int state; // 交易状态
	private long tax;// 交易税
	private long masterIncome; // 主人收益
	private long slaveIncome; // 奴隶收益

	public SlaveSellLog() {
	}

	public SlaveSellLog(long id, long buyId, long sellId, long beBuyId, long worth, int state, long masterIncome,
			long slaveIncome, long tax) {
		this._id = id;
		this.buyId = buyId;
		this.sellId = sellId;
		this.beBuyId = beBuyId;
		this.worth = worth;
		this.state = state;
		this.buyTime = System.currentTimeMillis();
		this.masterIncome = masterIncome;
		this.slaveIncome = slaveIncome;
		this.tax = tax;
	}

	public long getBeBuyId() {
		return beBuyId;
	}

	public void setBeBuyId(long beBuyId) {
		this.beBuyId = beBuyId;
	}

	public long getTax() {
		return tax;
	}

	public void setTax(long tax) {
		this.tax = tax;
	}

	public long getMasterIncome() {
		return masterIncome;
	}

	public void setMasterIncome(long masterIncome) {
		this.masterIncome = masterIncome;
	}

	public long getSlaveIncome() {
		return slaveIncome;
	}

	public void setSlaveIncome(long slaveIncome) {
		this.slaveIncome = slaveIncome;
	}

	public long getBuyId() {
		return buyId;
	}

	public void setBuyId(long buyId) {
		this.buyId = buyId;
	}

	public long getSellId() {
		return sellId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setSellId(long sellId) {
		this.sellId = sellId;
	}

	public long getWorth() {
		return worth;
	}

	public void setWorth(long worth) {
		this.worth = worth;
	}

	public long getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(long buyTime) {
		this.buyTime = buyTime;
	}

}
