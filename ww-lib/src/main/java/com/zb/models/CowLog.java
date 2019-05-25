package com.zb.models;

public class CowLog extends AbstractDocument {

	private static final long serialVersionUID = 5637355915316701579L;

	private int allCnt;// 总局数
	private int pokersScore;// 牌型分数
	private int[] pokers;// 最大牌型
	private String pokersStr;// 最大牌型文字描述
	private int winCnt; // 赢金场次
	private int loseCnt;// 输金场次
	private long maxPokerTime;// 最大牌型获得时间

	public int getAllCnt() {
		return allCnt;
	}

	public void setAllCnt(int allCnt) {
		this.allCnt = allCnt;
	}

	public int[] getPokers() {
		return pokers;
	}

	public void setPokers(int[] pokers) {
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

	public CowLog(int allCnt, int pokersScore, int[] pokers, String pokersStr, int winCnt, int loseCnt) {
		super();
		this.allCnt = allCnt;
		this.pokersScore = pokersScore;
		this.pokers = pokers;
		this.pokersStr = pokersStr;
		this.winCnt = winCnt;
		this.loseCnt = loseCnt;
	}

	public CowLog() {
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

	public int getAllRate() {
		int count = getAllCnt();
		int win = getWinCnt();
		int rate = count == 0 ? 0 : (win * 100 / count);
		return rate;
	}

	public long getMaxPokerTime() {
		return maxPokerTime;
	}

	public void setMaxPokerTime(long maxPokerTime) {
		this.maxPokerTime = maxPokerTime;
	}
	
}
