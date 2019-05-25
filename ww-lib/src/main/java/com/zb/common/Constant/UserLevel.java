package com.zb.common.Constant;

public enum UserLevel {
	
//	 LV0(0, 0, "小黑户", "/images/lv1.png"), LV1(1, 1, "初涉逼湖",
//	 "/images/lv1.png"), LV2(
//	 2, 5, "无名之逼", "/images/lv1.png"), LV3(3, 15, "锋芒逼露",
//	 "/images/lv1.png"), LV4(4, 30, "人海孤逼", "/images/lv1.png"), LV5(5,
//	 50, "撕逼少侠", "/images/lv1.png"), LV6(6, 100, "逼湖少侠",
//	 "/images/lv1.png"), LV7(7, 200, "后起之逼", "/images/lv1.png"), LV8(8,
//	 500, "逼林新贵", "/images/lv1.png"), LV9(9, 1000, "逼林高手",
//	 "/images/lv1.png"), LV10(10, 2000, "逼雄豪杰", "/images/lv1.png"), LV11(
//	 11, 3000, "逼中龙凤", "/images/lv1.png"), LV12(12, 6000, "逼成一派",
//	 "/images/lv1.png"), LV13(13, 10000, "名镇逼湖", "/images/lv1.png"), LV14(
//	 14, 18000, "武林逼主", "/images/lv1.png"), LV15(15, 30000, "一代逼宗",
//	 "/images/lv1.png"), LV16(16, 60000, "笑傲逼湖", "/images/lv1.png"), LV17(
//	 17, 100000, "逼然贵隐", "/images/lv1.png"), LV18(18, 300000, "逼孤求败",
//	 "/images/lv1.png");
//	 public int getLevel() {
//	 return level;
//	 }
//	
//	 public int getPoint() {
//	 return point;
//	 }
//	
//	 public String getTitle() {
//	 return title;
//	 }
//	
//	 public String getPic() {
//	 return pic;
//	 }
//	
//	 private int level = 0;
//	 private int point = 0;
//	 private String title;
//	 private String pic;
	
//	 public static UserLevel of(int point) {
//	 if (point < LV1.getPoint()) {
//	 return LV1;
//	 } else if (point < LV2.getPoint()) {
//	 return LV2;
//	 } else if (point < LV3.getPoint()) {
//	 return LV3;
//	 } else if (point < LV4.getPoint()) {
//	 return LV4;
//	 } else if (point < LV5.getPoint()) {
//	 return LV5;
//	 } else if (point < LV6.getPoint()) {
//	 return LV6;
//	 } else if (point < LV7.getPoint()) {
//	 return LV7;
//	 } else if (point < LV8.getPoint()) {
//	 return LV8;
//	 } else if (point < LV9.getPoint()) {
//	 return LV9;
//	 } else if (point < LV10.getPoint()) {
//	 return LV10;
//	 } else if (point < LV11.getPoint()) {
//	 return LV11;
//	 } else if (point < LV12.getPoint()) {
//	 return LV12;
//	 } else if (point < LV13.getPoint()) {
//	 return LV13;
//	 } else if (point < LV14.getPoint()) {
//	 return LV14;
//	 } else if (point < LV15.getPoint()) {
//	 return LV15;
//	 } else if (point < LV16.getPoint()) {
//	 return LV16;
//	 } else if (point < LV17.getPoint()) {
//	 return LV17;
//	 } else {
//	 return LV18;
//	 }
//	 }
	
//	 private UserLevel(int level, int point, String title, String pic) {
//	 this.level = level;
//	 this.point = point;
//	 this.title = title;
//	 this.pic = pic;
//	 }
	
//	LV0(0, 0), LV1(1, 100), LV2(2, 200), LV3(3, 600), LV4(4, 1200), LV5(5, 2000), LV6(6, 3300), LV7(7, 4900), LV8(8,
//			7000), LV9(9, 9600), LV10(10, 12800), LV11(11, 16700), LV12(12, 21200), LV13(13, 26500), LV14(14,
//					32600), LV15(15, 39600), LV16(16, 47500), LV17(17, 56400), LV18(18, 66400), LV19(19, 77500), LV20(
//							20, 89700), LV21(21, 103100), LV22(22, 117900), LV23(23, 134000), LV24(24, 151400), LV25(25,
//									170400), LV26(26, 190800), LV27(27, 212800), LV28(28, 236500), LV29(29,
//											261800), LV30(30, 288900), LV31(31, 317800), LV32(32, 348500), LV33(33,
//													381200), LV34(34, 415800), LV35(35, 452500), LV36(36, 491300), LV37(
//															37, 532300), LV38(38, 575400), LV39(39, 620900), LV40(40,
//																	668600), LV41(41, 718800), LV42(42, 771300), LV43(
//																			43, 826400), LV44(44, 884100), LV45(45,
//																					944400), LV46(46, 1007300), LV47(47,
//																							1073000), LV48(48,
//																									1141500), LV49(49,
//																											1212900), LV50(
//																													50,
//																													1287100), LV51(
//																															51,
//																															1364400), LV52(
//																																	52,
//																																	1444600), LV53(
//																																			53,
//																																			1528000), LV54(
//																																					54,
//																																					1614400), LV55(
//																																							55,
//																																							1704100), LV56(
//																																									56,
//																																									1797100), LV57(
//																																											57,
//																																											1893400), LV58(
//																																													58,
//																																													1993000), LV59(
//																																															59,
//																																															2096100), LV60(
//																																																	60,
//																																																	2202700), LV61(
//																																																			61,
//																																																			2312800), LV62(
//																																																					62,
//																																																					2426600), LV63(
//																																																							63,
//																																																							2544000), LV64(
//																																																									64,
//																																																									2665100), LV65(
//																																																											65,
//																																																											2790000), LV66(
//																																																													66,
//																																																													2918800), LV67(
//																																																															67,
//																																																															3051500), LV68(
//																																																																	68,
//																																																																	3188100), LV69(
//																																																																			69,
//																																																																			3328800), LV70(
//																																																																					70,
//																																																																					3473500), LV71(
//																																																																							71,
//																																																																							3622400), LV72(
//																																																																									72,
//																																																																									3775400), LV73(
//																																																																											73,
//																																																																											3932700), LV74(
//																																																																													74,
//																																																																													4094400), LV75(
//																																																																															75,
//																																																																															4260300), LV76(
//																																																																																	76,
//																																																																																	4430800), LV77(
//																																																																																			77,
//																																																																																			4605600), LV78(
//																																																																																					78,
//																																																																																					4785100), LV79(
//																																																																																							79,
//																																																																																							4969100), LV80(
//																																																																																									80,
//																																																																																									5157800), LV81(
//																																																																																											81,
//																																																																																											5351300), LV82(
//																																																																																													82,
//																																																																																													5549400), LV83(
//																																																																																															83,
//																																																																																															5752500), LV84(
//																																																																																																	84,
//																																																																																																	5960400), LV85(
//																																																																																																			85,
//																																																																																																			6173300), LV86(
//																																																																																																					86,
//																																																																																																					6391100), LV87(
//																																																																																																							87,
//																																																																																																							6614100), LV88(
//																																																																																																									88,
//																																																																																																									6842200), LV89(
//																																																																																																											89,
//																																																																																																											7075400), LV90(
//																																																																																																													90,
//																																																																																																													7313900), LV91(
//																																																																																																															91,
//																																																																																																															7557700), LV92(
//																																																																																																																	92,
//																																																																																																																	7806900), LV93(
//																																																																																																																			93,
//																																																																																																																			8061400), LV94(
//																																																																																																																					94,
//																																																																																																																					8321500), LV95(
//																																																																																																																							95,
//																																																																																																																							8587100), LV96(
//																																																																																																																									96,
//																																																																																																																									8858200), LV97(
//																																																																																																																											97,
//																																																																																																																											9135100), LV98(
//																																																																																																																													98,
//																																																																																																																													9417600), LV99(
//																																																																																																																															99,
//																																																																																																																															9705900), LV100(
//																																																																																																																																	100,
//																																																																																																																																	10000000);
//	public int getLevel() {
//		return level;
//	}
//
//	public int getPoint() {
//		return point;
//	}
//
//	private int level = 0;
//	private int point = 0;
//
//	public static UserLevel of(int point) {
//		if (point < LV1.getPoint()) {
//			return LV0;
//		} else if (point < LV2.getPoint()) {
//			return LV1;
//		} else if (point < LV3.getPoint()) {
//			return LV2;
//		} else if (point < LV4.getPoint()) {
//			return LV3;
//		} else if (point < LV5.getPoint()) {
//			return LV4;
//		} else if (point < LV6.getPoint()) {
//			return LV5;
//		} else if (point < LV7.getPoint()) {
//			return LV6;
//		} else if (point < LV8.getPoint()) {
//			return LV7;
//		} else if (point < LV9.getPoint()) {
//			return LV8;
//		} else if (point < LV10.getPoint()) {
//			return LV9;
//		} else if (point < LV11.getPoint()) {
//			return LV10;
//		} else if (point < LV12.getPoint()) {
//			return LV11;
//		} else if (point < LV13.getPoint()) {
//			return LV12;
//		} else if (point < LV14.getPoint()) {
//			return LV13;
//		} else if (point < LV15.getPoint()) {
//			return LV14;
//		} else if (point < LV16.getPoint()) {
//			return LV15;
//		} else if (point < LV17.getPoint()) {
//			return LV16;
//		} else if (point < LV18.getPoint()) {
//			return LV17;
//		} else if (point < LV19.getPoint()) {
//			return LV18;
//		} else if (point < LV20.getPoint()) {
//			return LV19;
//		} else if (point < LV21.getPoint()) {
//			return LV20;
//		} else if (point < LV22.getPoint()) {
//			return LV21;
//		} else if (point < LV23.getPoint()) {
//			return LV22;
//		} else if (point < LV24.getPoint()) {
//			return LV23;
//		} else if (point < LV25.getPoint()) {
//			return LV24;
//		} else if (point < LV26.getPoint()) {
//			return LV25;
//		} else if (point < LV27.getPoint()) {
//			return LV26;
//		} else if (point < LV28.getPoint()) {
//			return LV27;
//		} else if (point < LV29.getPoint()) {
//			return LV28;
//		} else if (point < LV30.getPoint()) {
//			return LV29;
//		} else if (point < LV31.getPoint()) {
//			return LV30;
//		} else if (point < LV32.getPoint()) {
//			return LV31;
//		} else if (point < LV33.getPoint()) {
//			return LV32;
//		} else if (point < LV34.getPoint()) {
//			return LV33;
//		} else if (point < LV35.getPoint()) {
//			return LV34;
//		} else if (point < LV36.getPoint()) {
//			return LV35;
//		} else if (point < LV37.getPoint()) {
//			return LV36;
//		} else if (point < LV38.getPoint()) {
//			return LV37;
//		} else if (point < LV39.getPoint()) {
//			return LV38;
//		} else if (point < LV40.getPoint()) {
//			return LV39;
//		} else if (point < LV41.getPoint()) {
//			return LV40;
//		} else if (point < LV42.getPoint()) {
//			return LV41;
//		} else if (point < LV43.getPoint()) {
//			return LV42;
//		} else if (point < LV44.getPoint()) {
//			return LV43;
//		} else if (point < LV45.getPoint()) {
//			return LV44;
//		} else if (point < LV46.getPoint()) {
//			return LV45;
//		} else if (point < LV47.getPoint()) {
//			return LV46;
//		} else if (point < LV48.getPoint()) {
//			return LV47;
//		} else if (point < LV49.getPoint()) {
//			return LV48;
//		} else if (point < LV50.getPoint()) {
//			return LV49;
//		} else if (point < LV51.getPoint()) {
//			return LV50;
//		} else if (point < LV52.getPoint()) {
//			return LV51;
//		} else if (point < LV53.getPoint()) {
//			return LV52;
//		} else if (point < LV54.getPoint()) {
//			return LV53;
//		} else if (point < LV55.getPoint()) {
//			return LV54;
//		} else if (point < LV56.getPoint()) {
//			return LV55;
//		} else if (point < LV57.getPoint()) {
//			return LV56;
//		} else if (point < LV58.getPoint()) {
//			return LV57;
//		} else if (point < LV59.getPoint()) {
//			return LV58;
//		} else if (point < LV60.getPoint()) {
//			return LV59;
//		} else if (point < LV61.getPoint()) {
//			return LV60;
//		} else if (point < LV62.getPoint()) {
//			return LV61;
//		} else if (point < LV63.getPoint()) {
//			return LV62;
//		} else if (point < LV64.getPoint()) {
//			return LV63;
//		} else if (point < LV65.getPoint()) {
//			return LV64;
//		} else if (point < LV66.getPoint()) {
//			return LV65;
//		} else if (point < LV67.getPoint()) {
//			return LV66;
//		} else if (point < LV68.getPoint()) {
//			return LV67;
//		} else if (point < LV69.getPoint()) {
//			return LV68;
//		} else if (point < LV70.getPoint()) {
//			return LV69;
//		} else if (point < LV71.getPoint()) {
//			return LV70;
//		} else if (point < LV72.getPoint()) {
//			return LV71;
//		} else if (point < LV73.getPoint()) {
//			return LV72;
//		} else if (point < LV74.getPoint()) {
//			return LV73;
//		} else if (point < LV75.getPoint()) {
//			return LV74;
//		} else if (point < LV76.getPoint()) {
//			return LV75;
//		} else if (point < LV77.getPoint()) {
//			return LV76;
//		} else if (point < LV78.getPoint()) {
//			return LV77;
//		} else if (point < LV79.getPoint()) {
//			return LV78;
//		} else if (point < LV80.getPoint()) {
//			return LV79;
//		} else if (point < LV81.getPoint()) {
//			return LV80;
//		} else if (point < LV82.getPoint()) {
//			return LV81;
//		} else if (point < LV83.getPoint()) {
//			return LV82;
//		} else if (point < LV84.getPoint()) {
//			return LV83;
//		} else if (point < LV85.getPoint()) {
//			return LV84;
//		} else if (point < LV86.getPoint()) {
//			return LV85;
//		} else if (point < LV87.getPoint()) {
//			return LV86;
//		} else if (point < LV88.getPoint()) {
//			return LV87;
//		} else if (point < LV89.getPoint()) {
//			return LV88;
//		} else if (point < LV90.getPoint()) {
//			return LV89;
//		} else if (point < LV91.getPoint()) {
//			return LV90;
//		} else if (point < LV92.getPoint()) {
//			return LV91;
//		} else if (point < LV93.getPoint()) {
//			return LV92;
//		} else if (point < LV94.getPoint()) {
//			return LV93;
//		} else if (point < LV95.getPoint()) {
//			return LV94;
//		} else if (point < LV96.getPoint()) {
//			return LV95;
//		} else if (point < LV97.getPoint()) {
//			return LV96;
//		} else if (point < LV98.getPoint()) {
//			return LV97;
//		} else if (point < LV99.getPoint()) {
//			return LV98;
//		} else if (point < LV100.getPoint()) {
//			return LV99;
//		} else {
//			return LV100;
//		}
//	}
//
//	private UserLevel(int level, int point) {
//		this.level = level;
//		this.point = point;
//
//	}
//
//	// 检测用户等级是否有提升
//	public static int countLevel(int point, int count) {
//		UserLevel l1 = of(point);
//		UserLevel l2 = of(point + count);
//		if (l2.getLevel() > l1.getLevel()) {
//			return l2.getLevel();
//		}
//		return 0;
//	}
}
