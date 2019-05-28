package com.zb.models.slave;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class StarEnum {

	public enum MasterEnum {

		LV1(1, 0.00, 4, 2000), LV2(2, 0.00, 6, 6000), LV3(3, 0.00, 8, 10000), LV4(4, 0.05, 8, 30000), LV5(5, 0.05, 10,
				40000), LV6(6, 0.10, 10, 100000), LV7(7, 0.10, 12,
						120000), LV8(8, 0.15, 12, 250000), LV9(9, 0.15, 14, 500000), LV10(10, 0.20, 16, 999999999),;

		MasterEnum(int level, double buffer, int slaveLimit, int upGradePrice) {
			this.level = level;
			this.buffer = buffer;
			this.upGradePrice = upGradePrice;
			this.slaveLimit = slaveLimit;
		}

		private int level; // 星球等级
		private double buffer; // 额外收益
		private int slaveLimit; // 奴隶上限
		private int upGradePrice; // 升级价格

		public Integer getLevel() {
			return level;
		}

		public void setLevel(Integer level) {
			this.level = level;
		}

		public double getBuffer() {
			return buffer;
		}

		public void setBuffer(double buffer) {
			this.buffer = buffer;
		}

		public int getSlaveLimit() {
			return slaveLimit;
		}

		public void setSlaveLimit(int slaveLimit) {
			this.slaveLimit = slaveLimit;
		}

		public int getUpGradePrice() {
			return upGradePrice;
		}

		public void setUpGradePrice(int upGradePrice) {
			this.upGradePrice = upGradePrice;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		@SuppressWarnings("all")
		public static final Map<Integer, MasterEnum> masterMap = new HashMap() {
			{
				for (MasterEnum e : EnumSet.allOf(MasterEnum.class)) {
					put(e.getLevel(), e);
				}
			}
		};
	}

	public enum SlaveEnum {
		ORDINARY(1, 20, 20, 5, 2.0, 120, 60), EXCELLENT(2, 40, 20, 5, 1.0, 60, 75), SENIOR(3, 60, 20, 5, 0.5, 60,
				90), EPIC(4, 80, 20, 5, 0.2, 15, 120), LEGENDARY(5, 100, 20, 5, 0.0, 5, 150),;

		private int level; // 奴隶等级 12345
		private int bibiLevelMax; // 玩家最大等级
		private int income; // 打工初始收益
		private int exIncome; // 打工扩展收益
		private double expBuffer; // 经验增益
		private int protectMinutes; // 被保护小时数=>改为分钟数 2017/8/3
		private int maxWorthInc; // 每次最大身价增长值

		public int getMaxWorthInc() {
			return maxWorthInc;
		}

		public void setMaxWorthInc(int maxWorthInc) {
			this.maxWorthInc = maxWorthInc;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public int getBibiLevelMax() {
			return bibiLevelMax;
		}

		public void setBibiLevelMax(int bibiLevelMax) {
			this.bibiLevelMax = bibiLevelMax;
		}

		public int getIncome() {
			return income;
		}

		public void setIncome(int income) {
			this.income = income;
		}

		public int getExIncome() {
			return exIncome;
		}

		public void setExIncome(int exIncome) {
			this.exIncome = exIncome;
		}

		public double getExpBuffer() {
			return expBuffer;
		}

		public void setExpBuffer(double expBuffer) {
			this.expBuffer = expBuffer;
		}

		public int getProtectMinutes() {
			return protectMinutes;
		}

		public void setProtectMinutes(int protectMinutes) {
			this.protectMinutes = protectMinutes;
		}

		SlaveEnum(int level, int bibiLevelMax, int income, int exIncome, double expBuffer, int protectMinutes,
				int maxWorthInc) {
			this.level = level;
			this.bibiLevelMax = bibiLevelMax;
			this.income = income;
			this.exIncome = exIncome;
			this.expBuffer = expBuffer;
			this.protectMinutes = protectMinutes;
			this.maxWorthInc = maxWorthInc;
		}

		@SuppressWarnings("all")
		public final static Map<Integer, SlaveEnum> slaveMap = new HashMap() {
			{
				for (SlaveEnum e : EnumSet.allOf(SlaveEnum.class)) {
					put(e.getLevel(), e);
				}
			}
		};

	}

	public enum BuyEnum {

		RATE(0.07, 70, 0.03, 30, 0.05, 50),;

		BuyEnum(double masterIncomePercent, int masterIncomeMax, double slaveIncomePercent, int slaveIncomeMax,
				double taxPercent, int taxMax) {
			this.masterIncomePercent = masterIncomePercent;
			this.masterIncomeMax = masterIncomeMax;
			this.slaveIncomePercent = slaveIncomePercent;
			this.slaveIncomeMax = slaveIncomeMax;
			this.taxPercent = taxPercent;
			this.taxMax = taxMax;
		}

		private double masterIncomePercent;
		private int masterIncomeMax;
		private double slaveIncomePercent;
		private int slaveIncomeMax;
		private double taxPercent;
		private int taxMax;

		public double getMasterIncomePercent() {
			return masterIncomePercent;
		}

		public void setMasterIncomePercent(double masterIncomePercent) {
			this.masterIncomePercent = masterIncomePercent;
		}

		public int getMasterIncomeMax() {
			return masterIncomeMax;
		}

		public void setMasterIncomeMax(int masterIncomeMax) {
			this.masterIncomeMax = masterIncomeMax;
		}

		public double getSlaveIncomePercent() {
			return slaveIncomePercent;
		}

		public void setSlaveIncomePercent(double slaveIncomePercent) {
			this.slaveIncomePercent = slaveIncomePercent;
		}

		public int getSlaveIncomeMax() {
			return slaveIncomeMax;
		}

		public void setSlaveIncomeMax(int slaveIncomeMax) {
			this.slaveIncomeMax = slaveIncomeMax;
		}

		public double getTaxPercent() {
			return taxPercent;
		}

		public void setTaxPercent(double taxPercent) {
			this.taxPercent = taxPercent;
		}

		public int getTaxMax() {
			return taxMax;
		}

		public void setTaxMax(int taxMax) {
			this.taxMax = taxMax;
		}

	}

}
