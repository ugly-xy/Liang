package com.we.models;

import java.util.HashMap;

/** 用户第三方账号的id集合 */
public class ThirdUser extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9164431888305972220L;
	private HashMap<Integer, ThirdUserInfo> ids = new HashMap<Integer, ThirdUserInfo>();

	public HashMap<Integer, ThirdUserInfo> getIds() {
		return ids;
	}

	public void setIds(HashMap<Integer, ThirdUserInfo> ids) {
		this.ids = ids;
	}

	public ThirdUser() {
		super();
	}
	
	public ThirdUser(Long uid) {
		this._id = uid;
	}

	public ThirdUser(HashMap<Integer, ThirdUserInfo> ids) {
		super();
		this.ids = ids;
	}
	
	public void put(Integer type,ThirdUserInfo userInfo) {
		this.ids.put(type, userInfo);
	}
	
	public ThirdUserInfo userInfo(Integer type) {
		return this.ids.get(type);
	}

}



