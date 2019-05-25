package com.zb.models.third;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import com.zb.common.Constant.Const;

@Document
public class App implements Serializable {

	private String _id;
	private Integer type;// 1 h5 2Android 3 iOS
	private Long provider; // 提供商ID
	private String title; // 游戏名

	private Integer status = Const.STATUS_UP; // 游戏状态
	private Integer ch = 1;
	private Integer childCh = 1;
	private Integer urlStyle = 1;// 1 普通参数方式 2 Restful
	private String url;// app地址
	private String callback;// 回调地址
	private String showUrl;// 支付后显示地址
	private String blowfishKey;// blowfish加密密钥
	private String blowfishVector;// blowfish向量
	private String pic;// 游戏展示图
	private Double level = 5.0d;// 评价星际
	private Integer count;// 使用人数
	private String description;// 游戏描述

	private Long adminId;// 编辑人ID
	private Integer sort;// 排序ID

	private String appSecret;// app密码
	private Long updateTime;

	/**
	 * 
	 */
	private static final long serialVersionUID = 9191501442272983663L;

	public App(String id, Integer type, Long provider, Integer status,
			String title, Integer sort, long adminId, Integer ch,
			Integer childCh, String url, String pic, String description,
			Integer count, String appSecret, String callback,
			String blowfishKey, String blowfishVector, String showUrl,
			Integer urlStyle) {
		this.type = type;
		this.updateTime = System.currentTimeMillis();
		this.adminId = adminId;
		this.ch = ch;
		this.childCh = childCh;
		this._id = id;
		this.status = status;
		this.title = title;
		this.url = url;
		this.provider = provider;
		this.pic = pic;
		this.sort = sort;
		this.description = description;
		this.count = count;
		this.appSecret = appSecret;
		this.callback = callback;
		this.blowfishKey = blowfishKey;
		this.blowfishVector = blowfishVector;
		this.showUrl = showUrl;
		this.urlStyle = urlStyle;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getProvider() {
		return provider;
	}

	public void setProvider(Long provider) {
		this.provider = provider;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public int getCh() {
		return ch;
	}

	public void setCh(int ch) {
		this.ch = ch;
	}

	public int getChildCh() {
		return childCh;
	}

	public void setChildCh(int childCh) {
		this.childCh = childCh;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Double getLevel() {
		return level;
	}

	public void setLevel(Double level) {
		this.level = level;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public void setCh(Integer ch) {
		this.ch = ch;
	}

	public void setChildCh(Integer childCh) {
		this.childCh = childCh;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getBlowfishKey() {
		return blowfishKey;
	}

	public void setBlowfishKey(String blowfishKey) {
		this.blowfishKey = blowfishKey;
	}

	public String getBlowfishVector() {
		return blowfishVector;
	}

	public void setBlowfishVector(String blowfishVector) {
		this.blowfishVector = blowfishVector;
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

	public String getShowUrl() {
		return showUrl;
	}

	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}

	public Integer getUrlStyle() {
		return urlStyle;
	}

	public void setUrlStyle(Integer urlStyle) {
		this.urlStyle = urlStyle;
	}

}
