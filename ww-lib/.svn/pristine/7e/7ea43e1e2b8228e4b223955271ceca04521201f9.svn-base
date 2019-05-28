package com.zb.models.room;

import com.zb.models.AbstractDocument;

public class GameResource extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1414094602137538172L;

	public GameResource() {
	}

	public GameResource(long id, int via, double ver, int gameType, String url, String appCode) {
		this._id = id;
		this.updateTime = System.currentTimeMillis();
		this.via = via;
		this.ver = ver;
		this.gameType = gameType;
		this.url = url;
		this.appCode = appCode;
	}

	private int via;
	private double ver;
	private int gameType;
	private String url;
	private String appCode;

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getVia() {
		return via;
	}

	public double getVer() {
		return ver;
	}

	public int getGameType() {
		return gameType;
	}

	public void setVia(int via) {
		this.via = via;
	}

	public void setVer(double ver) {
		this.ver = ver;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}

}
