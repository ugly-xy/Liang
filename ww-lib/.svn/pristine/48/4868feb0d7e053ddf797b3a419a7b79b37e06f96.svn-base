package com.zb.models.res;

import com.zb.models.AbstractDocument;

public class ContactsHead extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3703398879028922117L;

	public ContactsHead() {

	}

	public ContactsHead(Long id, String name, Integer status, Integer sort,
			String headPic, Integer pri, Long uid, String cateName) {
		this._id = id;
		this.name = name;
		this.updateTime = System.currentTimeMillis();
		this.sort = sort;
		this.status = status;
		this.headPic = headPic;
		this.pri = pri;
		this.uid = uid;
		this.cateName = cateName;
	}

	private String name;
	private String headPic;
	private Integer status;
	private Integer pri; // 1公共 2私人
	private Long uid;// 用户id
	private String cateName;// 分类名
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

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public Integer getPri() {
		return pri;
	}

	public void setPri(Integer pri) {
		this.pri = pri;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
}
