package com.we.models.permission;

import java.util.List;

import com.we.models.AbstractDocument;

public class Permission  extends AbstractDocument{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4923987416438335604L;
	
	public Permission(){
		
	}
	
	public Permission(Long id ,String name,String uri,int menuId,long adminId,List<Integer> roles){
		this._id = id;
		this.uri = uri;
		this.name = name;
		this.menuId = menuId;
		this.updateTime = System.currentTimeMillis();
		this.adminId = adminId;
		this.roles = roles;
	}
	
	private String name;
	private String uri;
	private int menuId;
	private long adminId;
	private List<Integer> roles = null;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public long getAdminId() {
		return adminId;
	}

	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}

	public List<Integer> getRoles() {
		return roles;
	}

	public void setRoles(List<Integer> roles) {
		this.roles = roles;
	}
	
}
