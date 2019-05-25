package com.zb.models.othergames;

import java.io.Serializable;
import java.util.List;


public class GuessTemplate  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6270351820298057009L;
	
	private String _id;
	private String title;//竞猜标题
	private List<GuessItem> items;//选项
	private long updateTime;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<GuessItem> getItems() {
		return items;
	}
	public void setItems(List<GuessItem> items) {
		this.items = items;
	}
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
