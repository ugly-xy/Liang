package com.zb.models.finance;

import com.zb.models.AbstractDocument;

public class AppleErrorOrder extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7187701026816865792L;
	public static final int ERROR = 1;
	public static final int OK = 3;


	private long uid;// 购买人
	private int status = ERROR;// 订单状态
	private long createTime;// 创建时间
	private String receipt;// 商品名
	private Long adminId;
	private String appCode;

	public AppleErrorOrder() {

	}

	public AppleErrorOrder(Long id,long uid, String receipt,String appCode) {
		this._id = id;
		this.createTime = System.currentTimeMillis();
		this.updateTime = createTime;
		this.uid = uid;
		this.receipt = receipt;
		this.appCode = appCode;
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

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
}
