package com.zb.models.room.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zb.models.room.Activity;

public class Cow extends Activity {

	private static final long serialVersionUID = 8042464671208114025L;

	public static final int STATUS_DEAL = 2;// 发牌
	public static final int STATUS_ROBBANKER = 3;// 抢庄
	public static final int STATUS_ROBBANKER_END = 4;// 抢庄结束
	public static final int STATUS_FILLER = 5;// 加注
	public static final int STATUS_FILLER_END = 6;// 加注结束
	public static final int STATUS_SHOWDOWN_END = 7;// 摊牌结束 准备重启

	private int type; // 游戏类型
	private int baseCoin;// 基础金币
	private Set<Long> actors = new HashSet<Long>();//
	private long[] seatUserTable = new long[6];// 座次顺序
	private List<Long> speakerTable = new ArrayList<Long>(); // 游戏顺序
	private Map<Long, Integer> folds = new HashMap<Long, Integer>();// 每个人的倍数
	private long dealer;// 庄家uid
	private Map<Long, Integer> coinRes = new HashMap<Long, Integer>();// 所有人最终的金币结果
	private Map<Long, int[]> userPokers = new HashMap<Long, int[]>();// 每个人的扑克牌
	private List<Integer> pokers = new ArrayList<Integer>();// 所有的扑克牌
	private Map<Long, Object[]> pokerScores = new HashMap<Long, Object[]>();// 扑克牌型

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long[] getSeatUserTable() {
		return seatUserTable;
	}

	public void setSeatUserTable(long[] seatUserTable) {
		this.seatUserTable = seatUserTable;
	}

	public Set<Long> getActors() {
		return actors;
	}

	public void setActors(Set<Long> actors) {
		this.actors = actors;
	}

	public void putActor(Long aid) {
		actors.add(aid);
	}

	public void clearFolds() {
		this.folds.clear();
	}

	public void removeActor(Long aid) {
		actors.remove(aid);
	}

	public List<Long> getSpeakerTable() {
		return speakerTable;
	}

	public void setSpeakerTable(List<Long> speakerTable) {
		this.speakerTable = speakerTable;
	}

	public Map<Long, Integer> getFolds() {
		return folds;
	}

	public void putUserFold(long uid, int fold) {
		this.folds.put(uid, fold);
	}

	public int getUserFold(long uid) {
		return this.folds.get(uid);
	}
	
	public Map<Long, Integer> getUserFolds() {
		return this.folds;
	}

	public long getDealer() {
		return dealer;
	}

	public void setDealer(long dealer) {
		this.dealer = dealer;
	}

	public void putUserPoker(long uid, int[] poker) {
		this.userPokers.put(uid, poker);
	}

	public int[] getUserPoker(long uid) {
		return userPokers.get(uid);
	}

	public Map<Long, int[]> getUserPokers() {
		return userPokers;
	}

	public List<Integer> getPokers() {
		return pokers;
	}

	public void setPokers(List<Integer> pokers) {
		this.pokers = pokers;
	}

	public Map<Long, Integer> getCoinRes() {
		return coinRes;
	}

	public void setCoinRes(Map<Long, Integer> coinRes) {
		this.coinRes = coinRes;
	}

	public int getBaseCoin() {
		return baseCoin;
	}

	public void setBaseCoin(int baseCoin) {
		this.baseCoin = baseCoin;
	}

	public Map<Long, Object[]> getPokerScores() {
		return pokerScores;
	}

	public void setPokerScores(Map<Long, Object[]> pokerScores) {
		this.pokerScores = pokerScores;
	}

	public void setFolds(Map<Long, Integer> folds) {
		this.folds = folds;
	}

	public void setUserPokers(Map<Long, int[]> userPokers) {
		this.userPokers = userPokers;
	}

}
