package com.zb.models.slave;

import com.zb.models.AbstractDocument;

public class SlaveStealLog extends AbstractDocument {

	private static final long serialVersionUID = -1994046675121411596L;
	private long slaveId; // 奴隶id
	private long stealId; // 偷取人
	private long processNumber; // 奴隶工序号
	private int stealMoney; // 偷取金额
	private long stealTime; // 偷取时间
	private int stealType; // 偷取类型

	public SlaveStealLog(long id, long updateTime, long slaveId, long stealId, long processNumber, int stealMoney,
			long stealTime, int stealType) {
		this._id = id;
		this.slaveId = slaveId;
		this.stealId = stealId; // 偷取人
		this.processNumber = processNumber; // 奴隶工序号
		this.stealMoney = stealMoney; // 偷取金额
		this.stealTime = stealTime;
		this.stealType = stealType;
	}

	public SlaveStealLog() {
	}

	public int getStealType() {
		return stealType;
	}

	public void setStealType(int stealType) {
		this.stealType = stealType;
	}

	public long getProcessNumber() {
		return processNumber;
	}

	public void setProcessNumber(long processNumber) {
		this.processNumber = processNumber;
	}

	public long getSlaveId() {
		return slaveId;
	}

	public void setSlaveId(long slaveId) {
		this.slaveId = slaveId;
	}

	public long getStealId() {
		return stealId;
	}

	public void setStealId(long stealId) {
		this.stealId = stealId;
	}

	public int getStealMoney() {
		return stealMoney;
	}

	public void setStealMoney(int stealMoney) {
		this.stealMoney = stealMoney;
	}

	public long getStealTime() {
		return stealTime;
	}

	public void setStealTime(long stealTime) {
		this.stealTime = stealTime;
	}

}
