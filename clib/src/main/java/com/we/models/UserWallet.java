package com.we.models;

import java.util.HashMap;

/** 用户的的所有货币 种类以及数量 */
public class UserWallet extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5151149231595960117L;

	private HashMap<String, Double> coins = new HashMap<String, Double>();

	public HashMap<String, Double> getCoins() {
		return coins;
	}

	public void setCoins(HashMap<String, Double> coins) {
		this.coins = coins;
	}

	public Double getCoin(String coinType) {
		return coins.get(coinType) == null ? 0d : coins.get(coinType);
	}

	public void putCoin(String coinType, Double coinAmount) {
		this.coins.put(coinType, coinAmount);
	}

	public void addCoin(String coinType, Double coinAmount) {
		this.coins.put(coinType, this.getCoin(coinType) + coinAmount);
	}

	public UserWallet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserWallet(long uid) {
		super();
		this._id = uid;
	}

}
