package com.zb.core.web;

import java.io.Serializable;

import com.zb.common.Constant.ReCode;
import com.zb.core.Page;

public class ReMsg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8501281600044462358L;

	public ReMsg() {

	}

	@Override
	public String toString() {
		return "ReMsg [code=" + code + ", msg=" + msg + ", data=" + data + ", size=" + size + ", totalPage=" + totalPage
				+ ", total=" + total + "]";
	}

	public ReMsg(String msg) {
		this.msg = msg;
	}

	public ReMsg(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public ReMsg(int code, Object data) {
		this.code = code;
		this.data = data;
	}

	public ReMsg(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public ReMsg(ReCode rc, Object data) {
		this.code = rc.reCode();
		this.msg = rc.getMsg();
		this.data = data;
	}

	public ReMsg(ReCode rc, int size, int totalPage, Object data) {
		this.size = size;
		this.totalPage = totalPage;
		this.code = rc.reCode();
		this.msg = rc.getMsg();
		this.data = data;
	}

	public ReMsg(ReCode rc, Page<?> page) {
		this.size = page.getSize();
		this.totalPage = page.getTotalPage();
		this.code = rc.reCode();
		this.msg = rc.getMsg();
		this.data = page.getList();
		this.total = page.getTotal();
	}

	public ReMsg(ReCode rc) {
		this.code = rc.reCode();
		this.msg = rc.getMsg();
	}

	private int code = 0;// 1 代表成功
	private String msg = "";// 消息文本
	private Object data;// 消息体
	private int size = 0; // 分页大小
	private int totalPage = 0;// 总页数
	private int total = 0;//

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
