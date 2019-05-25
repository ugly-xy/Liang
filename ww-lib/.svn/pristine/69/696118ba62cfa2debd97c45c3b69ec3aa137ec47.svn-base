package com.zb.models.res;

import com.zb.models.AbstractDocument;

public class ZbToolCate extends AbstractDocument {

	public static final int TOOL_V1 = 1;
	public static final int TOOL_V2 = 2;

	public static final int HAS_NOT_SUB = 1;
	public static final int HAS_SUB = 2;

	/**
	 * 
	 */
	private static final long serialVersionUID = -6228438531507066513L;

	public ZbToolCate() {

	}

	public ZbToolCate(Long id, String name, String cover, int status, int sort,
			int type, Long parentId, Integer hasSub, String namePic, String mark) {
		this._id = id;
		this.name = name;
		this.namePic = namePic;
		this.cover = cover;
		this.updateTime = System.currentTimeMillis();
		this.status = status;
		this.sort = sort;
		this.type = type;
		this.parentId = parentId;
		this.hasSub = hasSub;
		this.mark = mark;
	}

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	private int type;// 1 装逼 2 暴走
	private String name;
	private String cover;
	private int status;
	private int sort;
	private Long parentId;
	private Integer hasSub;// 是否有子分类
	private String namePic;
	private String mark;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getHasSub() {
		return hasSub;
	}

	public void setHasSub(Integer hasSub) {
		this.hasSub = hasSub;
	}

	public String getNamePic() {
		return namePic;
	}

	public void setNamePic(String namePic) {
		this.namePic = namePic;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

}
