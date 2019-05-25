package com.zb.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UnreadMessage extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9191501442272983663L;

	public UnreadMessage() {

	}

	public UnreadMessage(Long id, int count) {
		this._id = id;
		this.count = count;
	}

	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
