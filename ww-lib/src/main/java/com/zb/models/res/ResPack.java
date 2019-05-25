package com.zb.models.res;

import java.util.List;

import com.zb.models.AbstractDocument;

public class ResPack extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3703398879028922117L;

	public ResPack() {

	}

	public ResPack(Long id, Integer type, String name, String cover,
			Integer status, Integer sort, Long cateId, List<Res> reses,
			Long uid, String[] tags) {
		this._id = id;
		this.name = name;
		this.updateTime = System.currentTimeMillis();
		this.sort = sort;
		this.status = status;
		this.cover = cover;
		this.type = type;
		this.cateId = cateId;
		this.reses = reses;
		this.uid = uid;
		this.tags = tags;
	}

	public static final int TYPE_GIF = 2;
	public static final int TYPE_DEF = 1;

	private int type; // 1 普通 2GIF
	private String name;// 资源包名
	private String cover;
	private Integer status;
	private Integer sort;
	private Long cateId;// 分类名
	private List<Res> reses = null;
	private Long uid;
	private String[] tags;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Long getCateId() {
		return cateId;
	}

	public void setCateId(Long cateId) {
		this.cateId = cateId;
	}

	public List<Res> getReses() {
		return reses;
	}

	public void setReses(List<Res> reses) {
		this.reses = reses;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

}
