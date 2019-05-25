package com.zb.models.user;

import java.util.ArrayList;
import java.util.List;

import com.zb.models.AbstractDocument;

public class UserTag extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7652729365195678826L;

	public UserTag() {

	}

	public UserTag(ArrayList<UserTagItem> tags) {
		this.tags = tags;
	}

	public UserTag(String key, String value) {
		add(key, value);
	}

	ArrayList<UserTagItem> tags;

	public List<UserTagItem> getTags() {
		return tags;
	}

	public void setTags(ArrayList<UserTagItem> tags) {
		this.tags = tags;
	}
	
	public void replace(int idx ,String key,String value){
		if (tags == null) {
			tags = new ArrayList<UserTagItem>();
		}
		tags.remove(idx);
		tags.add(idx,new UserTagItem(key,value));
	}

	public void insert(int idx ,String key,String value){
		tags.add(idx,new UserTagItem(key,value));
	}
	
	public void insert(int idx ,UserTagItem it){
		if (tags == null) {
			tags = new ArrayList<UserTagItem>();
		}
		tags.add(idx,it);
	}
	public void add(UserTagItem item) {
		if (tags == null) {
			tags = new ArrayList<UserTagItem>();
		}
		tags.add(0,item);
	}

	public void add(String key, String value) {
		add(new UserTagItem(key, value));
	}
	
	public void del(int idx){
		this.tags.remove(idx);
	}
	
}
