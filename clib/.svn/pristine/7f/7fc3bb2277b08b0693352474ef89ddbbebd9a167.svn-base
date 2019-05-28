package com.we.models.permission;

import java.util.HashSet;
import java.util.Set;

import com.we.models.AbstractDocument;

public class UserPerms extends AbstractDocument {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8685131879851488540L;
	Set<String> perms = new HashSet<String>();
	private long adminId;
	
	public UserPerms(){
		this.updateTime = System.currentTimeMillis();
	}
	
	public UserPerms(Long uid,long adminId){
		this._id = uid;
		this.updateTime = System.currentTimeMillis();
		this.adminId = adminId;
	}
	
	public UserPerms(Long uid,Set<String> perms,long adminId){
		this._id = uid;
		this.perms = perms;
		this.updateTime = System.currentTimeMillis();
		this.adminId = adminId;
	}
	
	public Set<String> getPerms() {
		return perms;
	}

	public void setPerms(Set<String> perms) {
		this.perms = perms;
	}

	public void addPerm(String uri){
		this.perms.add(uri);
	}
	
	public void addPerm(Permission perm){
		this.perms.add(perm.getUri());
	}
	
	public void clear(){
		this.perms.clear();
	}
	
	public boolean contains(String uri){
		return this.perms.contains(uri);
	}

	public long getAdminId() {
		return adminId;
	}

	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}
	
}
