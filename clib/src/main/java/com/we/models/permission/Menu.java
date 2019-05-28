package com.we.models.permission;


public enum Menu  {
	用户管理(1),
    资金管理(2),
	轮播管理(3),
	新闻动态(4),
	任务管理(5),
	商务合作(6),
	系统管理(7)
	;
	
	private Menu(int id){
		this.id = id;
	}
	private int  id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
