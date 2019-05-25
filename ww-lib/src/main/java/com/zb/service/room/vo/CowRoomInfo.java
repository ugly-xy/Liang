package com.zb.service.room.vo;

import java.util.Map;

public class CowRoomInfo extends RoomInfo {

	private static final long serialVersionUID = 4151704626309519569L;

	public CowRoomInfo(int type) {
		super(type);
	}

	private int cowStatus;  //游戏状态
	private Long speaker; //当前的活动者
	private Map<Long, int[]> userPokers;// 每个人的扑克牌
	private Map<Long, Integer> folds;// 每个人的倍数
	private Long dealer;// 庄家uid
	private int baseCoin;// 底分

	public int getCowStatus() {
		return cowStatus;
	}

	public void setCowStatus(int cowStatus) {
		this.cowStatus = cowStatus;
	}

	public Long getSpeaker() {
		return speaker;
	}

	public void setSpeaker(Long speaker) {
		this.speaker = speaker;
	}

	public Map<Long, int[]> getUserPokers() {
		return userPokers;
	}

	public void setUserPokers(Map<Long, int[]> userPokers) {
		this.userPokers = userPokers;
	}

	public Map<Long, Integer> getFolds() {
		return folds;
	}

	public void setFolds(Map<Long, Integer> folds) {
		this.folds = folds;
	}

	public Long getDealer() {
		return dealer;
	}

	public void setDealer(long dealer) {
		this.dealer = dealer;
	}

	public int getBaseCoin() {
		return baseCoin;
	}

	public void setBaseCoin(int baseCoin) {
		this.baseCoin = baseCoin;
	}

}
