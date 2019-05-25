package com.zb.common.http;

import java.io.IOException;

public class HttpStatusException extends IOException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 589779899333150089L;
	int code;

	public HttpStatusException() {
	}

	public HttpStatusException(int code, String message) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}
}