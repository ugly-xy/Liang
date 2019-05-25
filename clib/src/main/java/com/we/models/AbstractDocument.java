package com.we.models;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

public class AbstractDocument implements Serializable {
	@Id
	protected Long _id;

	protected Long updateTime;

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (this._id == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
			return false;
		}

		AbstractDocument that = (AbstractDocument) obj;

		return this._id.equals(that.get_id()); 
	}

	@Override
	public int hashCode() {
		return _id == null ? 0 : _id.hashCode();
	}

	public Long get_id() {
		return _id;
	}

	public void set_id(Long _id) {
		this._id = _id;
	}

	// @Override
	// public String toString() {
	// ObjectMapper objectMapper = new ObjectMapper();
	// objectMapper.writeValue(System.out, this);
	// return ;
	// }
}
