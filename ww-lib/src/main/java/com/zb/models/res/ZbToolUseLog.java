package com.zb.models.res;

import com.zb.models.AbstractDocument;

public class ZbToolUseLog extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3703398879028922117L;

	public ZbToolUseLog() {

	}

	public ZbToolUseLog(Long id,  String mark,Long tid,Long uid,Integer model) {
		this._id = id;
		this.mark = mark;
	}

	private Integer status;
	private Integer model; // 1 正式版 2 试用版
	private Integer sort;
	private String mark;
	private Long tid;
	private Long uid;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getModel() {
		return model;
	}

	public void setModel(Integer model) {
		this.model = model;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}
}
