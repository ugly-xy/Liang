package com.zb.models.finance;

public class GiftBag {

	public static final int TYPE_0 = 0;// 无限制
	public static final int TYPE_DAY = 1;// 每天购买一次
	public static final int TYPE_NOVICE = 2;// 新手礼包
	public static final int TYPE_WEEK = 7;// 每周购买一次
	public static final int TYPE_MONTH = 30;// 每月购买一次

	private String id;// 商品id
	private String name;// 名字
	private int type;// 类型
	private int rmb;// 价格
	private int coin;// 赠送金币数
	private int vipAmount;// 赠送vip钱数
	private int flAmount;// 赠送收花数
	private int expAmount;// 赠送经验数

	public GiftBag() {
		super();
	}

	public GiftBag(String id, String name, int type, int rmb, int coin, int vipAmount, int flAmount, int expAmount) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.rmb = rmb;
		this.coin = coin;
		this.vipAmount = vipAmount;
		this.flAmount = flAmount;
		this.expAmount = expAmount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getRmb() {
		return rmb;
	}

	public void setRmb(int rmb) {
		this.rmb = rmb;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public int getVipAmount() {
		return vipAmount;
	}

	public void setVipAmount(int vipAmount) {
		this.vipAmount = vipAmount;
	}

	public int getFlAmount() {
		return flAmount;
	}

	public void setFlAmount(int flAmount) {
		this.flAmount = flAmount;
	}

	public int getExpAmount() {
		return expAmount;
	}

	public void setExpAmount(int expAmount) {
		this.expAmount = expAmount;
	}

}
