package com.zb.models.room.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zb.models.room.Activity;

public class Texas extends Activity {

	private static final long serialVersionUID = 8042464671208114025L;

	public static final int STATUS_DEAL = 2;// 发牌
	public static final int STATUS_BET = 3;// 下注
	public static final int STATUS_BETEND = 4;// 下注结束
	public static final int STATUS_SHOWDOWN_END = 6;// 摊牌结束
	public static final int STATUS_RESTART = 7;// 准备重启

	private int init = 1;// 回合数
	private int type; // 游戏类型
	private int coinPool;// 底池
	private int baseCoin;// 基础金币
	private Map<Long, Integer> actors = new HashMap<Long, Integer>();// 玩家及玩家状态
	private long[] seatUserTable = new long[6];// 座次顺序
	private List<Long> speakerTable = new ArrayList<Long>(); // 游戏顺序
	private int seat = -1;// 庄家座位号
	private Map<Long, int[]> userPokers = new HashMap<Long, int[]>();// 每个人的扑克牌
	private List<Integer> pubPokers = new ArrayList<Integer>();// 底池中的扑克牌
	private List<Integer> allPokers = new ArrayList<Integer>();// 所有的扑克牌
	private int boutCoin = 0;// 当前回合的最大金钱
	private Map<Long, Object[]> pokerScores = new HashMap<Long, Object[]>();// 手里的牌能组成的最大牌型
	private int playUserCnt;// 还能玩的用户人数
	private Set<Long> winner = new HashSet<Long>();
	private Map<Long, Integer> userAllCoins = new HashMap<Long, Integer>();// 每个人剩余的筹码数量
	private Map<Long, Integer> userBetCoins = new HashMap<Long, Integer>();// 已押的筹码
	// 每回合allIn的临时存储
	private List<Long[]> tempAllIn = new ArrayList<Long[]>();
	// 每个堆对应的金钱
	private List<Integer> pileCoins = new ArrayList<Integer>();
	// 堆的金钱来源
	private Map<Integer, Map<Long, Integer>> pileCoinsFrom = new HashMap<Integer, Map<Long, Integer>>();
	// 本局筹码最终输赢结果
	private Map<Long, Integer> coinRes = new HashMap<Long, Integer>();

	private Set<Long> tempBetUser = new HashSet<Long>();// 本回合下注的人
	private Map<Long, Integer> userBetCnt = new HashMap<Long, Integer>();// 用户参与下注的回合数

	public void putTempBetUser(long uid) {
		this.tempBetUser.add(uid);
	}

	public void clearTempBetUser() {
		this.tempBetUser.clear();
	}

	public Set<Long> getTempBetUser() {
		return tempBetUser;
	}

	public Map<Long, Integer> getUserBetCnt() {
		return userBetCnt;
	}

	public int getUserBetCnt(long uid) {
		return userBetCnt.get(uid) == null ? 0 : userBetCnt.get(uid);
	}

	public void addUserBetCnt(long uid) {
		this.userBetCnt.put(uid, userBetCnt.get(uid) == null ? 1 : userBetCnt.get(uid) + 1);
	}

	public void clearUserBetCoins() {
		this.userBetCoins.clear();
	}

	public void clearTempAllIn() {
		this.tempAllIn.clear();
	}

	public void clearSpeakTable() {
		this.speakerTable.clear();
	}

	public Map<Long, Integer> getUserBetCoins() {
		return userBetCoins;
	}

	public void setUserBetCoins(Map<Long, Integer> userBetCoins) {
		this.userBetCoins = userBetCoins;
	}

	public List<Long[]> getTempAllIn() {
		return tempAllIn;
	}

	public void setTempAllIn(List<Long[]> tempAllIn) {
		this.tempAllIn = tempAllIn;
	}

	public List<Integer> getPileCoins() {
		return pileCoins;
	}

	public void setPileCoins(List<Integer> pileCoins) {
		this.pileCoins = pileCoins;
	}

	public Map<Integer, Map<Long, Integer>> getPileCoinsFrom() {
		return pileCoinsFrom;
	}

	public void setPileCoinsFrom(Map<Integer, Map<Long, Integer>> pileCoinsFrom) {
		this.pileCoinsFrom = pileCoinsFrom;
	}

	public void putActor(long uid, int status) {
		this.actors.put(uid, status);
	}

	public void removeActor(long uid) {
		this.actors.remove(uid);
	}

	public void putActorCoin(long uid, int coin) {
		this.userAllCoins.put(uid, coin);
	}

	public int getActorCoin(long uid) {
		return this.userAllCoins.get(uid) == null ? 0 : this.userAllCoins.get(uid);
	}

	public void removeActorCoin(long uid) {
		this.userAllCoins.remove(uid);
	}

	public void putUserPoker(long uid, int[] poker) {
		this.userPokers.put(uid, poker);
	}

	public void putUserScore(long uid, Object[] score) {
		this.pokerScores.put(uid, score);
	}

	public int[] getUserPoker(long uid) {
		return this.userPokers.get(uid);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCoinPool() {
		return coinPool;
	}

	public void setCoinPool(int coinPool) {
		this.coinPool = coinPool;
	}

	public Map<Long, Integer> getActors() {
		return actors;
	}

	public int getActorStatus(long uid) {
		return actors.get(uid);
	}

	public void setActors(Map<Long, Integer> actors) {
		this.actors = actors;
	}

	public long[] getSeatUserTable() {
		return seatUserTable;
	}

	public void setSeatUserTable(long[] seatUserTable) {
		this.seatUserTable = seatUserTable;
	}

	public List<Long> getSpeakerTable() {
		return speakerTable;
	}

	public void setSpeakerTable(List<Long> speakerTable) {
		this.speakerTable = speakerTable;
	}

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
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

	public List<Integer> getAllPokers() {
		return allPokers;
	}

	public void setAllPokers(List<Integer> allPokers) {
		this.allPokers = allPokers;
	}

	public int getBoutCoin() {
		return boutCoin;
	}

	public void setBoutCoin(int boutCoin) {
		this.boutCoin = boutCoin;
	}

	public Map<Long, Object[]> getPokerScores() {
		return pokerScores;
	}

	public void setPokerScores(Map<Long, Object[]> pokerScores) {
		this.pokerScores = pokerScores;
	}

	public Map<Long, Integer> getUserAllCoins() {
		return userAllCoins;
	}

	public void setUserAllCoins(Map<Long, Integer> userAllCoins) {
		this.userAllCoins = userAllCoins;
	}

	public int getBaseCoin() {
		return baseCoin;
	}

	public void setBaseCoin(int baseCoin) {
		this.baseCoin = baseCoin;
	}

	public int getPlayUserCnt() {
		return playUserCnt;
	}

	public void setPlayUserCnt(int playUserCnt) {
		this.playUserCnt = playUserCnt;
	}

	public Set<Long> getWinner() {
		return winner;
	}

	public void setWinner(Set<Long> winner) {
		this.winner = winner;
	}

	public void addWinner(long winner) {
		this.winner.add(winner);
	}

	public void clearWinner() {
		this.winner.clear();
	}

	public Map<Long, Integer> getCoinRes() {
		return coinRes;
	}

	public void setCoinRes(Map<Long, Integer> coinRes) {
		this.coinRes = coinRes;
	}

	public int getInit() {
		return init;
	}

	public void setInit(int init) {
		this.init = init;
	}

}
