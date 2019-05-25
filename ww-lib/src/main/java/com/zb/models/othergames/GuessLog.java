package com.zb.models.othergames;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class GuessLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6270351820298057009L;

	private String _id ;
	private long guessId;// 猜猜id
	private long uid;// 竞猜用户
	// 猜猜项id 押注金额
	private Map<String, Integer> items = new HashMap<String, Integer>();
	private Integer drawAmount;
	private int status = Guess.STATUS_GUESSING;
	private long updateTime;

	public long getGuessId() {
		return guessId;
	}

	public void setGuessId(long guessId) {
		this.guessId = guessId;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	

	public Map<String, Integer> getItems() {
		return items;
	}

	public void setItems(Map<String,Integer> items) {
		this.items = items;
	}

	public GuessLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getAmount(String guessItemId) {
		return null == this.items.get(guessItemId) ? 0 : this.items.get(guessItemId);
	}

	public void setAmount(String guessItemId, int amount) {
		this.items.put(guessItemId, amount);
	}
	
	

	public GuessLog(long guessId, long uid, String guessItemId, int amount) {
		super();
		this._id = guessId+"-"+uid;
		this.guessId = guessId;
		this.uid = uid;
		this.items.put(guessItemId, amount);
	}

	public Integer getDrawAmount() {
		return drawAmount;
	}

	public void setDrawAmount(Integer drawAmount) {
		this.drawAmount = drawAmount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	
}
