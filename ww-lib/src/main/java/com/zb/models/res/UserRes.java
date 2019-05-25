package com.zb.models.res;

import java.io.Serializable;

public class UserRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3703398879028922117L;


	public UserRes() {

	}

	public UserRes( String name, String path) {
		this.name = name;
		this.path = path;
	}

	private String name;
	private String path;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}