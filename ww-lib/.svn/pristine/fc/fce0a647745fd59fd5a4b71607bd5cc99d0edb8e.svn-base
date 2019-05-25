package com.zb.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BlackUsers extends AbstractDocument {

	private static final long serialVersionUID = 3207815169369012630L;
	private int gameType;

	public BlackUsers(long uid) {
		this._id = uid;
		this.updateTime = System.currentTimeMillis();
	}

	public int getGameType() {
		return gameType;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}

}
