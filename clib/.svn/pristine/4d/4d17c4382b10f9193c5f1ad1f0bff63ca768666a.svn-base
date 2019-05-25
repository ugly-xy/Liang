package com.we.common.Constant;

public enum OperationType {

	NULL("null"), // 只做展示，不做具体跳转
	URL("url"), // 网页 opId 为网页地址
	ARTICLE("article"), // 文章 opId 为文章Id
	USERSPACE("userSpace"), // 到用户空间 opId 为用户Id
	;
	static String[] ops = { NULL.op, URL.op, USERSPACE.op, ARTICLE.op };
	private String op;

	private OperationType(String op) {
		this.op = op;
	}

	public String getOp() {
		return op;
	}

	public static String[] getOps() {
		return ops;
	}

}
