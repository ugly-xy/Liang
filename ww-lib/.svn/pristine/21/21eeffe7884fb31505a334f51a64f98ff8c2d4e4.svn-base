package com.zb.models;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author brannZ
 *
 */
@Document
public class WerewolfKillLogDetail extends AbstractDocument {

	private static final long serialVersionUID = -133475452152369429L;

	public WerewolfKillLogDetail() {
	}

	public WerewolfKillLogDetail(long id, long uid, int actorCount, boolean win) {
		this._id = id;
		this.actorCount = actorCount;
		this.win = win;
		this.uid = uid;
		super.updateTime = System.currentTimeMillis();
	}

	private long uid;
	private Integer actorCount;
	private boolean win;

	public long getUid() {
		return uid;
	}

	public Integer getActorCount() {
		return actorCount;
	}

	public boolean isWin() {
		return win;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public void setActorCount(Integer actorCount) {
		this.actorCount = actorCount;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

}
