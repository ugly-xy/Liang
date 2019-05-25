package com.zb.models.lottery;

import java.util.HashMap;
import java.util.Map;

import com.zb.models.AbstractDocument;

public class Lottery extends AbstractDocument{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 360042609746714461L;
	
	public Lottery(){
		
	}
	
	public Lottery(Long id,String name, Long startTime, Long endTime,
			Integer status, Integer denominator){
		this._id = id;
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.denominator = denominator;
	}
	
	private String name;
	private Long startTime;//开始时间
	private Long endTime;//结束时间
	private Integer status;//状态
	private Integer denominator;//中奖率分母
	
	private Map<String,Item> items = new HashMap<String,Item>();
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Long getStartTime() {
		return startTime;
	}


	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}


	public Long getEndTime() {
		return endTime;
	}


	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public Integer getDenominator() {
		return denominator;
	}


	public void setDenominator(Integer denominator) {
		this.denominator = denominator;
	}


	public Map<String, Item> getItems() {
		return items;
	}


	public void setItems(Map<String, Item> items) {
		this.items = items;
	}
	
	public class Item{
		/**
		 * 
		 */
		private Integer type;//奖品类型
		private Long linkId;//对应的Id
		private String name;//奖品名
		private String pic;
		private Integer amount; //单词中奖的物品数
		private int rate;//中奖率
		private int limit;//奖品限制个数
		private int winners;//已经个数
		public Integer getType() {
			return type;
		}
		public void setType(Integer type) {
			this.type = type;
		}
		public Long getLinkId() {
			return linkId;
		}
		public void setLinkId(Long linkId) {
			this.linkId = linkId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPic() {
			return pic;
		}
		public void setPic(String pic) {
			this.pic = pic;
		}
		public Integer getAmount() {
			return amount;
		}
		public void setAmount(Integer amount) {
			this.amount = amount;
		}
		public int getRate() {
			return rate;
		}
		public void setRate(int rate) {
			this.rate = rate;
		}
		public int getLimit() {
			return limit;
		}
		public void setLimit(int limit) {
			this.limit = limit;
		}
		public int getWinners() {
			return winners;
		}
		public void setWinners(int winners) {
			this.winners = winners;
		}
		
	}

}
