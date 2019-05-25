package com.we.models.userTask;

import java.io.Serializable;


/** 币种 */
public class TokenPrj implements Serializable {

	private static final long serialVersionUID = -3640846137182685437L;

	private String _id;// 货币简写
	private String tokenName;// 全称
	private String pic;// 图片
	private int status;// 状态 上线 下线
	private Long adminId;
	private int sort;// 货币排序
	private String summary;// 项目方介绍
	private String url;// 项目方官网
	private Double price;// 货币单价 美元
	private String coinUnit;// 货币单位
	private String stage;// 融资阶段
	private String platform;
	private String country;
	private Boolean candy;
	private Long initialAmount;
	private Long updateTime;

	public TokenPrj(String symbol, String tokenName, String pic, int status, Long adminId, int sort, String summary,
			String url, Double price, String stage, String platform, String country, Boolean candy, String coinUnit,
			Long initialAmount) {
		super();
		this._id = symbol.toUpperCase();
		this.tokenName = tokenName;
		this.pic = pic;
		this.status = status;
		this.adminId = adminId;
		this.sort = sort;
		this.summary = summary;
		this.url = url;
		this.price = price;
		this.stage = stage;
		this.platform = platform;
		this.country = country;
		this.candy = candy;
		this.coinUnit = coinUnit;
		this.initialAmount = initialAmount;
	}

	public TokenPrj() {
		super();
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}


	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Boolean getCandy() {
		return candy;
	}

	public void setCandy(Boolean candy) {
		this.candy = candy;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCoinUnit() {
		return coinUnit;
	}

	public void setCoinUnit(String coinUnit) {
		this.coinUnit = coinUnit;
	}

	public Long getInitialAmount() {
		return initialAmount;
	}

	public void setInitialAmount(Long initialAmount) {
		this.initialAmount = initialAmount;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String getTokenName() {
		return tokenName;
	}

	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

}
