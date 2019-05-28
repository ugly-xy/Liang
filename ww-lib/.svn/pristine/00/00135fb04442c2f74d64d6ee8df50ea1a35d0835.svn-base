package com.zb.models;

import java.util.List;

public class TexasLog extends AbstractDocument {

	private static final long serialVersionUID = -7805589325034372636L;
	private int allCnt;// 总局数
	private int pokersScore;// 牌型分数
	private List<Integer> pokers;// 最大牌型
	private String pokersStr;// 最大牌型文字描述
	private int winCnt; // 赢金场次
	private int loseCnt;// 输金场次
	private int betCnt;// 下注回合数
	private int boutCnt;// 总回合数
	private int showdownCnt;// 摊牌次数

	public int getAllCnt() {
		return allCnt;
	}

	public void setAllCnt(int allCnt) {
		this.allCnt = allCnt;
	}

	public List<Integer> getPokers() {
		return pokers;
	}

	public void setPokers(List<Integer> pokers) {
		this.pokers = pokers;
	}

	public String getPokersStr() {
		return pokersStr;
	}

	public void setPokersStr(String pokersStr) {
		this.pokersStr = pokersStr;
	}

	public int getPokersScore() {
		return pokersScore;
	}

	public void setPokersScore(int pokersScore) {
		this.pokersScore = pokersScore;
	}

	public TexasLog(int pokersScore, List<Integer> pokers, String pokersStr, int winCnt, int loseCnt, int showdownCnt,
			int allCnt, int betCnt, int boutCnt) {
		super();
		this.allCnt = allCnt;
		this.pokersScore = pokersScore;
		this.pokers = pokers;
		this.pokersStr = pokersStr;
		this.winCnt = winCnt;
		this.loseCnt = loseCnt;
		this.betCnt = betCnt;
		this.boutCnt = boutCnt;
		this.showdownCnt = showdownCnt;
	}

	public TexasLog() {
		super();
	}

	public int getWinCnt() {
		return winCnt;
	}

	public void setWinCnt(int winCnt) {
		this.winCnt = winCnt;
	}

	public int getLoseCnt() {
		return loseCnt;
	}

	public void setLoseCnt(int loseCnt) {
		this.loseCnt = loseCnt;
	}

	public int getBetCnt() {
		return betCnt;
	}

	public void setBetCnt(int betCnt) {
		this.betCnt = betCnt;
	}

	public int getBoutCnt() {
		return boutCnt;
	}

	public void setBoutCnt(int boutCnt) {
		this.boutCnt = boutCnt;
	}

	public int getShowdownCnt() {
		return showdownCnt;
	}

	public void setShowdownCnt(int showdownCnt) {
		this.showdownCnt = showdownCnt;
	}

	/** 胜率 */
	public int getWinRate() {
		int count = getAllCnt();
		int win = getWinCnt();
		int rate = count == 0 ? 0 : (win * 100 / count);
		return rate;
	}

	/** 入池率 */
	public int getBetRate() {
		int betCnt = getBetCnt();
		int boutCnt = getBoutCnt();
		int rate = boutCnt == 0 ? 0 : (betCnt * 100 / boutCnt);
		return rate;
	}

	/** 摊牌率 */
	public int getShowdownRate() {
		int count = getAllCnt();
		int showdown = getShowdownCnt();
		int rate = count == 0 ? 0 : (showdown * 100 / count);
		return rate;
	}

}
