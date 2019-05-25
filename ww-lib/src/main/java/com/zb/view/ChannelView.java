package com.zb.view;

import java.io.Serializable;
import java.util.List;


public class ChannelView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6238819875389348865L;

	private List<?> list = null;

	private String title = null;
	private String logo = null;
	private Long id = null;

	public ChannelView() {

	}

	public ChannelView(List<?> cates, String logo, String title) {
		this.list = cates;
		this.title = title;
		this.logo = logo;
	}
	
	public ChannelView(List<?> cates, String title,Long id) {
		this.list = cates;
		this.title = title;
		this.id = id;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
