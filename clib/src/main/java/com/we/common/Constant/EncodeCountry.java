package com.we.common.Constant;

public enum EncodeCountry {
	CHINA("86", "中国"), SOUTH_KOREA("82", "韩国");

	private String encode;
	private String country;

	private EncodeCountry(String encode, String country) {
		this.encode = encode;
		this.country = country;
	}

	public String getEncode() {
		return encode;
	}

	public String getCountry() {
		return country;
	}

}
