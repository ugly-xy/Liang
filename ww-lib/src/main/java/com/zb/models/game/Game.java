package com.zb.models.game;

import org.springframework.data.mongodb.core.mapping.Document;

import com.zb.common.Constant.Const;
import com.zb.models.AbstractDocument;

@Document
public class Game extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9191501442272983663L;

	public Game(Long id, Integer type, Long provider, Integer status,
			String title, Integer sort, long adminId, Integer ch,
			Integer childCh, String url, String pic, String description,
			Integer count,String appId,String appSecret) {
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
		this.appId = appId;
		this.appSecret = appSecret;
	}

	private Integer type;// 1 h5 2Android 3 iOS
	private Long provider; // 提供商ID
	private String title; // 游戏名

	private Integer status = Const.STATUS_UP; // 游戏状态
	private Integer ch = 1;
	private Integer childCh = 1;
	private String url;// 游戏地址
	private String pic;// 游戏展示图
	private Double level = 5.0d;// 评价星际
	private Integer count;// 使用人数
	private String description;// 游戏描述

	private Long adminId;// 编辑人ID
	private Integer sort;// 排序ID
	
	private String appId ;
	private String appSecret;

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

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
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

}
