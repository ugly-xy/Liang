package com.zb.models.res;

import java.io.Serializable;

public class Airport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3703398879028922117L;

	public Airport() {

	}

	public Airport(Long id, String name, String city, String enCity,
			String code3, String code4) {
		this._id = id;
		this.name = name;
		this.city = city;
		this.enCity = enCity;
		this.code3 = code3;
		this.code4 = code4;
	}

	private Long _id;
	private String city;
	private String code3;
	private String code4;
	private String enCity;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long get_id() {
		return _id;
	}

	public void set_id(Long _id) {
		this._id = _id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCode3() {
		return code3;
	}

	public void setCode3(String code3) {
		this.code3 = code3;
	}

	public String getCode4() {
		return code4;
	}

	public void setCode4(String code4) {
		this.code4 = code4;
	}

	public String getEnCity() {
		return enCity;
	}

	public void setEnCity(String enCity) {
		this.enCity = enCity;
	}

}