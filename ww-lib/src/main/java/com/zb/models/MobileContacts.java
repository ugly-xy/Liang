package com.zb.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class MobileContacts extends AbstractDocument {
	private static final long serialVersionUID = 1724621604547879695L;

	private long count;//联系人总数
	private List<People> peoples;


	public MobileContacts(long uid) {
		super();
		this._id = uid;
		this.peoples = new ArrayList<People>();
	}

	public MobileContacts() {
		super();
	}

	public List<People> getPeoples() {
		return peoples;
	}

	public void setPeoples(List<People> peoples) {
		this.peoples = peoples;
	}

	public long getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
