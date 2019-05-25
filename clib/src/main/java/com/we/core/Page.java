package com.we.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<T> implements Serializable {

	public Page() {

	}

	public Page(int total, int size, int curPage, List<T> list) {
		this.total = total;
		this.size = size;
		this.curPage = curPage;
		this.list = list;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1413595526335534999L;

	private int total;

	private int size;

	private int curPage;

	private String url;

	private List<T> list = new ArrayList<T>();

	public int getTotal() {
		return total;
	}

	// public void setTotal(int total) {
	// this.total = total;
	// }

	public int getSize() {
		return size;
	}

	// public void setSize(int size) {
	// this.size = size;
	// }

	public int getCurPage() {
		return curPage;
	}


	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}


	public int getTotalPage() {
		return total % size == 0 ? total / size : total / size + 1;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
