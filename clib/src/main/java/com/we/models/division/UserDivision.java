package com.we.models.division;

import java.util.HashMap;

import com.we.models.AbstractDocument;

/** 用户段位进度表 */
public class UserDivision extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8304498507542785333L;
	// 段位任务的进度
	private HashMap<Integer, DivisionItem> conditions = new HashMap<Integer, DivisionItem>();

	public HashMap<Integer, DivisionItem> getConditions() {
		return conditions;
	}

	public void setConditions(HashMap<Integer, DivisionItem> conditions) {
		this.conditions = conditions;
	}

	public DivisionItem getConditionsVal(Integer key) {
		return this.conditions.get(key) ;
	}
	
	public void put(Integer id, DivisionItem di) {
		conditions.put(id, di);
	}


	public UserDivision() {
		super();
	}

	public UserDivision(long _id) {
		super();
		this._id = _id;
	}

}
