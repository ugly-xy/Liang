package com.zb.models.third;

import org.springframework.data.mongodb.core.mapping.Document;

import com.zb.models.AbstractDocument;

@Document
public class OpenOrder extends AbstractDocument {

	public static final int STATUS_CREATE = 1;
	public static final int STATUS_PAYED = 2;

	public static final int CALL_DEF = 1;
	public static final int CALL_FAIL = 2;
	public static final int CALL_OK = 3;

	private String appId;// 对应App中的_id;
	private String openId;// openId
	private Long uid;// 用户ID
	private Long provider;// 提供上ID
	private int coin;// 金币数量
	private String outOrderNo;// 第三方订单号
	private int status = 1;// 状态 创建 1 已支付2
	private int callStatus = 1;// 回调状态 1未回调 2回调失败 3 回调成功
	private int callCount = 0;// 回调次数
	private Long createTime;
	private String product;// 商品名
	private String detail;// 详情

	/**
	 * 
	 */
	private static final long serialVersionUID = 9191501442272983663L;

	public OpenOrder(long id, String appId, String openId, Long uid,
			Long provider, int coin, String outOrderNo, String product,
			String detail) {
		this.updateTime = System.currentTimeMillis();
		this.createTime = this.updateTime;
		this._id = id;
		this.uid = uid;
		this.appId = appId;
		this.provider = provider;
		this.coin = coin;
		this.openId = openId;
		this.outOrderNo = outOrderNo;
		this.product = product;
		this.detail = detail;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getProvider() {
		return provider;
	}

	public void setProvider(Long provider) {
		this.provider = provider;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public String getOutOrderNo() {
		return outOrderNo;
	}

	public void setOutOrderNo(String outOrderNo) {
		this.outOrderNo = outOrderNo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCallStatus() {
		return callStatus;
	}

	public void setCallStatus(int callStatus) {
		this.callStatus = callStatus;
	}

	public int getCallCount() {
		return callCount;
	}

	public void setCallCount(int callCount) {
		this.callCount = callCount;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

}
