package com.zb.models.finance;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.zb.common.utils.DateUtil;
import com.zb.models.AbstractDocument;

public class ThirdOrderExt implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5907095875831448168L;
	public static final int FAILED = -1;
	public static final int CREATE = 1;
	public static final int OK = 2;



	private String appId;
	private Long mchId;
	private String outTradeNo;// 订单号
	private String callbackUrl;//通知地址
	private String attach;//附加信息

	private int status = CREATE;// 订单状态

	private Long lastCallbackTime;// 创建时间
	private int calledCount = 0;//回调次数


	public ThirdOrderExt() {

	}

	public ThirdOrderExt(Long mchId,String appId,String outTradeNo,String callbackUrl,String attach) {
		this.mchId = mchId;
		this.appId = appId;
		this.outTradeNo = outTradeNo;
		this.callbackUrl = callbackUrl;
		this.attach = attach;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Long getMchId() {
		return mchId;
	}

	public void setMchId(Long mchId) {
		this.mchId = mchId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getLastCallbackTime() {
		return lastCallbackTime;
	}

	public void setLastCallbackTime(Long lastCallbackTime) {
		this.lastCallbackTime = lastCallbackTime;
	}

	public int getCalledCount() {
		return calledCount;
	}

	public void setCalledCount(int calledCount) {
		this.calledCount = calledCount;
	}


}
