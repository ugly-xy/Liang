package com.zb.models.paper;

import java.util.ArrayList;
import java.util.List;

public class Question {
	private Long id;
	private Long pId;
	private String title;
	private String content;
	private int type;//0 没有content，1图片 ，2文本
	
	private List<Item> items = new ArrayList<Item>();

	
	public Question(){
		
	}
	
	public Question(String title,String content,List<Item> items,Integer type){
		this.title =title;
		this.content = content;
		this.items = items;
		this.type =type;
	}
	
	public Question(Long id,Long pId,String title,String content,Integer type){
		this.id = id;
		this.pId = pId;
		this.title =title;
		this.content = content;
		this.type =type;
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	

}
