package com.zb.models.game;

import org.springframework.data.mongodb.core.mapping.Document;

import com.zb.models.AbstractDocument;

@Document
public class GameLog extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9191501442272983663L;

	public GameLog(Long id, Long userId, Long gameId) {
		this.updateTime = System.currentTimeMillis();
		this.gameId = gameId;
		this.userId = userId;
		this._id = id;
	}

	private Long gameId;
	private Long userId;

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}