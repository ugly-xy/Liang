package com.zb.service.room.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TexasRoomInfo extends RoomInfo {

	private static final long serialVersionUID = -836786922271927302L;

	public TexasRoomInfo(int type) {
		super(type);
	}

	private Long speaker; // 当前的活动者
	private Map<Long, String> pokerScores = new HashMap<Long, String>();// 手里的牌能组成的最大牌型
	private Map<Long, int[]> userPokers = new HashMap<Long, int[]>();// 每个人的扑克牌
	private List<Integer> pubPokers = new ArrayList<Integer>();// 公共牌
	private Map<Long, Integer> userAllCoins = new HashMap<Long, Integer>();// 每个人剩余的筹码数量
	private Map<Long, Integer> userBetCoins = new HashMap<Long, Integer>();// 用户已押的筹码
	private List<Integer> pileCoins = new ArrayList<Integer>();// 每个堆对应的金钱
	private int seat = -1;// 庄家座位号
	private int baseCoin;// 底分
	private int coinPool;// 底池

	public Long getSpeaker() {
		return speaker;
	}

	public void setSpeaker(Long speaker) {
		this.speaker = speaker;
	}

	public Map<Long, String> getPokerScores() {
		return pokerScores;
	}

	public void setPokerScores(Map<Long, String> pokerScores) {
		this.pokerScores = pokerScores;
	}

	public Map<Long, int[]> getUserPokers() {
		return userPokers;
	}

	public void setUserPokers(Map<Long, int[]> userPokers) {
		this.userPokers = userPokers;
	}

	public List<Integer> getPubPokers() {
		return pubPokers;
	}

	public void setPubPokers(List<Integer> pubPokers) {
		this.pubPokers = pubPokers;
	}

	public Map<Long, Integer> getUserAllCoins() {
		return userAllCoins;
	}

	public void setUserAllCoins(Map<Long, Integer> userAllCoins) {
		this.userAllCoins = userAllCoins;
	}

	public Map<Long, Integer> getUserBetCoins() {
		return userBetCoins;
	}

	public void setUserBetCoins(Map<Long, Integer> userBetCoins) {
		this.userBetCoins = userBetCoins;
	}

	public List<Integer> getPileCoins() {
		return pileCoins;
	}

	public void setPileCoins(List<Integer> pileCoins) {
		this.pileCoins = pileCoins;
	}

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	public int getBaseCoin() {
		return baseCoin;
	}

	public void setBaseCoin(int baseCoin) {
		this.baseCoin = baseCoin;
	}

	public int getCoinPool() {
		return coinPool;
	}

	public void setCoinPool(int coinPool) {
		this.coinPool = coinPool;
	}

}
