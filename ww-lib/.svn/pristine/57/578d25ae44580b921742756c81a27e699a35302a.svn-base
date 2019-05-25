package com.zb.models.finance;



import com.zb.models.AbstractDocument;

public class DiscountConfig  extends AbstractDocument{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2887953861274607883L;
	private Integer type;//1折扣券 2 减免券
	private Integer amount; //金额
	private Integer limitAmount;//消费金额 0为无限制，精确到分
	private Long startTime;
	private Long endTime;
	private Integer status;
	private Integer total;//总数
	private Integer drawCount;
	private Long createTime;
	private String name;
	public DiscountConfig(){
		
	}
	
	public DiscountConfig(Long id,String name,Integer type,Integer amount,Integer limitAmount,
			Long startTime,Long endTime,Integer status,Integer total){
		this._id = id;
		this.type = type;
		this.amount = amount;
		this.limitAmount = limitAmount;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.total = total;
		this.createTime = System.currentTimeMillis();
		this.updateTime = createTime;
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(Integer limitAmount) {
		this.limitAmount = limitAmount;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getDrawCount() {
		return drawCount;
	}

	public void setDrawCount(Integer drawCount) {
		this.drawCount = drawCount;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
