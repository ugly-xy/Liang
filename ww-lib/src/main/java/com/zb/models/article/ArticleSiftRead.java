package com.zb.models.article;

import java.util.ArrayList;
import java.util.List;

import com.zb.models.AbstractDocument;

public class ArticleSiftRead extends AbstractDocument {

	private static final long serialVersionUID = -1273462403187249716L;

	private long uid;
	private List<Long[]> record=new ArrayList<Long[]>();
	
	@Override
	public String toString() {
		return "ArticleSiftRead [uid=" + uid + ", record=" + record + "]";
	}
	public ArticleSiftRead() {
		super();
	}
	public ArticleSiftRead(long uid, List<Long[]> record) {
		super();
		this.uid = uid;
		this.record = record;
	}
	public ArticleSiftRead(long id,long uid) {
		super();
		this._id=id;
		this.uid = uid;
		this.record =new ArrayList<Long[]>();
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public List<Long[]> getRecord() {
		return record;
	}
	public void setRecord(List<Long[]> record) {
		this.record = record;
	}
}
