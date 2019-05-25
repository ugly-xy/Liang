package com.zb.models;

import java.io.Serializable;


public class Keyword implements Serializable {
	
	public static final int RED_WORD = 1;
	public static final int BLACK_WORD = 2;

	private static final long serialVersionUID = 5096494720335652833L;


	private Integer type;
	private String _id;

	public Keyword() {

	}

	public Keyword(String id, Integer type) {
		this._id = id;
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}


}
