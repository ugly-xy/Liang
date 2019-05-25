package com.zb.common.Constant;

public enum FlowerRedeem {
	NO_1(179800, "iPhone7 Plus (128GB)"), 
	NO_2(150000, "卡西欧CASIO EX-TR750"), 
	NO_3(129800, "iPad Pro (9.7英寸)"),
	NO_4(100000, "Meitu 美图T8(4GB+128GB)"), 
	NO_5(88000, "OPPO R9s Plus (4G+64G)"), 
	NO_6(72800,"Apple Watch Series2 (38毫米)"), 
	NO_7(51800, "小米5s (3G+64G)"), 
	NO_8(42800,"小米 红米Note4 (4G+64G)"),
	NO_9(38800,"富士INSTAX MINI90"),
	NO_10(26800, "富士INSTAX MINI25 (Kitty白)"),;
	
	private int price;
	private String name;

	private FlowerRedeem(int price, String name) {
		this.price = price;
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static FlowerRedeem getRedeem(Integer type) {
		if (null == type || type < 1) {
			return null;
		} else if (type == 1) {
			return NO_1;
		} else if (type == 2) {
			return NO_2;
		} else if (type == 3) {
			return NO_3;
		} else if (type == 4) {
			return NO_4;
		} else if (type == 5) {
			return NO_5;
		} else if (type == 6) {
			return NO_6;
		} else if (type == 7) {
			return NO_7;
		} else if (type == 8) {
			return NO_8;
		} else if (type == 9) {
			return NO_9;
		} else if (type == 10) {
			return NO_10;
		} else {
			return null;
		}
	}

}
