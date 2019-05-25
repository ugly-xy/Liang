package com.zb.models.push;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import com.zb.common.Constant.Const;

public class PushTag implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4556362916404360634L;
	@Id
	private String _id;
	private Long createTime;
	private Integer status;
	private Integer devType;
	
	public PushTag(){
		
	}

	public PushTag(String name,Integer devType){
		this._id = name;
		this.createTime = System.currentTimeMillis();
		this.status = Const.STATUS_OK;
		this.devType = devType;
	}


	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDevType() {
		return devType;
	}

	public void setDevType(Integer devType) {
		this.devType = devType;
	}
	
}
