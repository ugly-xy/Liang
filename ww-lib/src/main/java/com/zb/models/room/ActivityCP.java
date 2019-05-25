package com.zb.models.room;

import com.zb.common.Constant.Const;

public class ActivityCP extends Activity {
	

	/**
	 * 
	 */
	public ActivityCP(){
		super.setStatus(STATUS_WAIT);
	}
	
	private static final long serialVersionUID = -3863880840981170308L;
	public static final int STATUS_WAIT = 4;// 等狗
	
	private long winner;
	private long loser;
	private int model = Const.ACTIVITY_MATCH;
	private long st = System.currentTimeMillis();// 开始时间

	public long getSt() {
		return st;
	}

	public void setSt(long st) {
		this.st = st;
	}

	public long getWinner() {
		return winner;
	}

	public void setWinner(long winner) {
		this.winner = winner;
	}

	public long getLoser() {
		return loser;
	}

	public void setLoser(long loser) {
		this.loser = loser;
	}

	public int getModel() {
		return model;
	}

	public void setModel(int model) {
		this.model = model;
	}

}
