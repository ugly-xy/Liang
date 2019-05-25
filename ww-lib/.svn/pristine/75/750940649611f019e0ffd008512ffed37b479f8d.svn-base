package com.zb.models.finance;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import com.zb.common.Constant.Const;

public class Discount  implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2209726791574379339L;
	
	public static final int DEF = 1;
	public static final int USED = 2;
	
	public static final int TYPE_COUPON = 1;
	public static final int TYPE_REDUCTION = 2;
	
	
	@Id
	private String _id;
	private long userId;
	private int type;
	private int amount;
	private int limitAmount;
	private long startTime;
	private long endTime;
	private int status = DEF;//1未使用  2已使用
	private long createTime;
	private long updateTime;
	private long configId;
	private String name;
	
	public Discount(){
		
	}
	public Discount(long userId,DiscountConfig dc){
		this.userId = userId;
		this.type = dc.getType();
		this.amount = dc.getAmount();
		this.limitAmount = dc.getLimitAmount();
		this.startTime = dc.getStartTime();
		this.endTime = dc.getEndTime();
		this.configId = dc.get_id();
		this.updateTime =  System.currentTimeMillis();
		this.createTime = updateTime;
		this.name = dc.getName();
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getLimitAmount() {
		return limitAmount;
	}
	public void setLimitAmount(int limitAmount) {
		this.limitAmount = limitAmount;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	public long getConfigId() {
		return configId;
	}
	public void setConfigId(long configId) {
		this.configId = configId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}

}
