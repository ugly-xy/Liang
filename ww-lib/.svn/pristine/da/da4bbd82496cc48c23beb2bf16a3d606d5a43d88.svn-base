package com.zb.models.third;

import org.springframework.data.mongodb.core.mapping.Document;

import com.zb.common.Constant.Const;
import com.zb.models.AbstractDocument;

@Document
public class Merchant extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9191501442272983663L;

	private String name; // 提供商
	private int union; // 1 统一 2各个App分别使用自己的独立签名
	private String unionSecret; // 提供商统一Secret

	private Integer status = Const.STATUS_UP; // 游戏状态
	private String url;// 提供商官网
	private String pic;// logo
	private String description;// 游戏描述

	private Long adminId;// 编辑人ID

	public Merchant(Long id, Integer status, Integer sort, long adminId,
			String url, String pic, String description, String name,
			String unionSecret) {
		this.updateTime = System.currentTimeMillis();
		this.adminId = adminId;
		this._id = id;
		this.status = status;
		this.url = url;
		this.pic = pic;
		this.description = description;
		this.name = name;
		this.unionSecret = unionSecret;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public String getUnionSecret() {
		return unionSecret;
	}

	public void setUnionSecret(String unionSecret) {
		this.unionSecret = unionSecret;
	}

	public int getUnion() {
		return union;
	}

	public void setUnion(int union) {
		this.union = union;
	}

}
