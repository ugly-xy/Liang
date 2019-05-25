package com.zb.service.room.vo;

import java.io.Serializable;
import java.util.Set;

import com.zb.models.room.Actor;

public class RoomInfo implements Serializable {

	private static final long serialVersionUID = -7076674575002067006L;

	private int code = 0;

	private Set<? extends Actor> actors;

	private int status;
	private String word;
	private int type;
	private boolean pub; // 公开true私有false
	private boolean hasOwner; // 有true没false
	private boolean sys;// 系统房间true 包间 false
	private long actId;

	public long getActId() {
		return actId;
	}

	public void setActId(long actId) {
		this.actId = actId;
	}

	public RoomInfo() {
	}

	public boolean isHasOwner() {
		return hasOwner;
	}

	public void setHasOwner(boolean hasOwner) {
		this.hasOwner = hasOwner;
	}

	public boolean isPub() {
		return pub;
	}

	public void setPub(boolean pub) {
		this.pub = pub;
	}

	public RoomInfo(int type) {
		this.type = type;
	}

	public Set<? extends Actor> getActors() {
		return actors;
	}

	public void setActors(Set<? extends Actor> actors) {
		this.actors = actors;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isSys() {
		return sys;
	}

	public void setSys(boolean sys) {
		this.sys = sys;
	}

}
