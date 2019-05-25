package com.zb.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Banner extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9191501442272983663L;

	public Banner() {

	}

	public Banner(Long id, String type, String title, String pic, String op,
			String opId, int status, Integer[] vias, Long adminId, Integer sort) {
		this.type = type;
		this.title = title;
		this.pic = pic;
		this.op = op;
		this._id = id;
		this.opId = opId;
		this.status = status;
		this.updateTime = System.currentTimeMillis();
		this.adminId = adminId;
		this.sort = sort;
		this.vias = vias;
	}
	public Banner(Long id, String type, String title, String pic, String op,
			String opId, int status, Integer[] vias, Long adminId, Integer sort,Long startTime,Long endTime) {
		this.type = type;
		this.title = title;
		this.pic = pic;
		this.op = op;
		this._id = id;
		this.opId = opId;
		this.status = status;
		this.updateTime = System.currentTimeMillis();
		this.adminId = adminId;
		this.sort = sort;
		this.vias = vias;
		this.startTime=startTime;
		this.endTime=endTime;
	}

	private String type;
	private String title;
	private String pic;
	private String op;
	private Long createTime;
	private String opId;
	private int status;
	private Integer via;
	private Integer[] vias;
	private Long adminId;
	private Integer sort;
    private Long startTime;   //上线时间
    private Long endTime;     //下线时间
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getVia() {
		return via;
	}

	public void setVia(int via) {
		this.via = via;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOpId() {
		return opId;
	}

	public void setOpId(String opId) {
		this.opId = opId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer[] getVias() {
		return vias;
	}

	public void setVias(Integer[] vias) {
		this.vias = vias;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
}
