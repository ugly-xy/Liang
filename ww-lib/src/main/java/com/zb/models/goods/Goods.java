package com.zb.models.goods;

import java.util.ArrayList;
import java.util.List;

import com.zb.models.AbstractDocument;

public class Goods extends AbstractDocument {

	public static final int CAT_FIRST = 1;

	/**
	 * 
	 */
	private static final long serialVersionUID = -4777758398948069096L;

	public Goods() {

	}

//	public Goods(Long id, String name, String img, Integer listPrice, Integer price, Long startTime, Long endTime,
//			Integer status, Integer sort, int limit) {
//		this(id, name, img, listPrice, price, startTime, endTime, status, sort, limit, null);
//	}

	public Goods(Long id, String name, String img, Integer listPrice, Integer price, Long startTime, Long endTime,
			Integer status, Integer sort, int limit, List<GoodsItem> items) {
		this._id = id;
		this.name = name;
		this.img = img;
		this.listPrice = listPrice;
		this.price = price;
		this.startTime = startTime;
		this.endTime = endTime;
		this.updateTime = System.currentTimeMillis();
		this.sort = sort;
		this.items = items;
		this.limit = limit;
	}

	private String name;
	private String img;
	private List<GoodsItem> items = new ArrayList<GoodsItem>();// 1Coin,2 vip
	private Integer listPrice;// 订价
	private Integer price;// 最终价格
	private Long startTime;
	private Long endTime;
	private Integer sort;
	private Integer status;// 1下限 2 隐藏 3 上线
	private int[] vias;
	private int limit = 0;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getListPrice() {
		return listPrice;
	}

	public void setListPrice(Integer listPrice) {
		this.listPrice = listPrice;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<GoodsItem> getItems() {
		return items;
	}

	public void setItems(List<GoodsItem> items) {
		this.items = items;
	}

	public int[] getVias() {
		return vias;
	}

	public void setVias(int[] vias) {
		this.vias = vias;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public enum LIMIT {
		ZORE, ONE_ONLY, ONE_EVERYDAY;
	}
}
