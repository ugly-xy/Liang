package com.zb.models;

import java.util.EnumSet;

public enum VipMap {

	// Vip1 6元 10%
	// Vip2 30元 20%
	// Vip3 100元 30%
	// Vip4 500元 40%
	// Vip5 2000元 50%
	// Vip6 6000元 60%
	// Vip7 15000元 70%
	// Vip8 30000元 80%
	// Vip9 60000元 90%
	// Vip10 100000元 100%

	VIP1(600, 0.1), VIP2(3000, 0.2), VIP3(10000, 0.3), VIP4(50000, 0.4), VIP5(200000, 0.5), VIP6(600000,
			0.6), VIP7(1500000, 0.7), VIP8(3000000, 0.8), VIP9(6000000, 0.9), VIP10(10000000, 1.0),;
	// 充值金额 经验增益
	VipMap(int money, double buff) {
		this.money = money;
		this.buff = buff;
	}

	private int money;
	private double buff;

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public double getBuff() {
		return buff;
	}

	public void setBuff(double buff) {
		this.buff = buff;
	}

	public static double getBuff(int money) {
		return getLevel(money) * 0.1;
	}

	public static int getLevel(Integer money) {
		if (null == money) {
			return 0;
		}
		int count = 0;
		for (VipMap e : EnumSet.allOf(VipMap.class)) {
			if (e.getMoney() <= money) {
				count++;
				continue;
			} else {
				return count;
			}
		}
		return count;
	}

	// 检测vip等级是否有提升
	public static int countVip(Integer vip, int count) {
		if (null == vip) {
			return getLevel(count);
		}
		int vipOld = getLevel(vip);
		int vipNew = getLevel(vip + count);
		if (vipNew > vipOld) {
			return vipNew;
		}
		return 0;
	}
}
