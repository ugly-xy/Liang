package com.zb.models.res;


import com.zb.models.AbstractDocument;

public class ResPackCate extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3703398879028922117L;

	public ResPackCate() {

	}

	public ResPackCate(Long id, String name, Integer status, Integer sort) {
		this._id = id;
		this.name = name;
		this.updateTime = System.currentTimeMillis();
		this.sort = sort;
		this.status = status;
	}

	private String name;// 分类名
	private Integer status;
	private Integer sort;

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

}
