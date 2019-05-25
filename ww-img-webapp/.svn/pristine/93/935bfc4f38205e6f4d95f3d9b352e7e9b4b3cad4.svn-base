package com.zb.service.image.tuhao;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;

public class JiPiaoGuoJiTool extends BaseTool {

	public JiPiaoGuoJiTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String p0 = "http://imgzb.zhuangdianbi.com/5735865f0cf2b4c11a221bef";
		String p1 = "http://imgzb.zhuangdianbi.com/573587180cf2b4c11a221c62";
		String p2 = "http://imgzb.zhuangdianbi.com/5735b6a80cf2b4c11a22425b";
		String p3 = "http://imgzb.zhuangdianbi.com/5735878f0cf2b4c11a221cba";
		String p4 = "http://imgzb.zhuangdianbi.com/5736a1320cf26d98ca1afeb1";
		String p5 = "http://imgzb.zhuangdianbi.com/5737083a0cf200dde23c34d3";//"http://imgzb.zhuangdianbi.com/573588090cf2b4c11a221d03";
		String p6 = "http://imgzb.zhuangdianbi.com/573708760cf200dde23c351a";//"http://imgzb.zhuangdianbi.com/5736c3820cf26d98ca1b2206";
		String p7 = "http://imgzb.zhuangdianbi.com/572506f70cf23a55bdf3dab0";
		String p8 = "http://imgzb.zhuangdianbi.com/573707c20cf200dde23c3458";// "http://imgzb.zhuangdianbi.com/573588be0cf2b4c11a221d7c";
		String p9 = "http://imgzb.zhuangdianbi.com/573588ea0cf2b4c11a221d90";
		String p10 = "http://imgzb.zhuangdianbi.com/5737072e0cf200dde23c33bf";
		String p11 = "http://imgzb.zhuangdianbi.com/573708c10cf200dde23c3571";//"http://imgzb.zhuangdianbi.com/5735891e0cf2b4c11a221dae";
		StorageService storageService = new StorageService();
		JiPiaoGuoJiTool tyt = new JiPiaoGuoJiTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.drawImg("装点逼", "ZHUANGDIANBI", "北京", "BEIJING",
				"PKE", "上海", "SHANGHAI", "SHA", names[10], p10));
	}

	private final static String[] names = { "东方航空", "港龙航空", "国航", "国泰航空",
			"南方航空", "厦门航空", "深圳航空", "中国国际航空", "阿联酋航空", "伊朗航空", "越南航空", "日本航空" };

	Color color = new Color(44, 44, 44);

	ZbFont f = ZbFont.SourceHanSansCNBold;

	/***
	 * @param name
	 *            姓名
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String name, String enName, String fCity,
			String enFCity, String fc3, String tCity, String enTCity,
			String tc3, String style, String tmpPath) throws IOException {
		if (names[0].equals(style)) {
			return this.drawDF(name, enName, fCity, enFCity, fc3, tCity,
					enTCity, tc3, style, tmpPath);
		} else if (names[1].equals(style)) {
			return this.drawGL(name, enName, fCity, enFCity, fc3, tCity,
					enTCity, tc3, style, tmpPath);
		} else if (names[2].equals(style)) {
			return this.drawGH(name, enName, fCity, enFCity, fc3, tCity,
					enTCity, tc3, style, tmpPath);
		} else if (names[3].equals(style)) {
			return this.drawGT(name, enName, fCity, enFCity, fc3, tCity,
					enTCity, tc3, style, tmpPath);
		} else if (names[4].equals(style)) {
			return this.drawNF(name, enName, fCity, enFCity, fc3, tCity,
					enTCity, tc3, style, tmpPath);
		} else if (names[5].equals(style)) {
			return this.drawXM(name, enName, fCity, enFCity, fc3, tCity,
					enTCity, tc3, style, tmpPath);
		} else if (names[6].equals(style)) {
			return this.drawSZ(name, enName, fCity, enFCity, fc3, tCity,
					enTCity, tc3, style, tmpPath);
		} else if (names[7].equals(style)) {
			return this.drawGHGJ(name, enName, fCity, enFCity, fc3, tCity,
					enTCity, tc3, style, tmpPath);
		} else if (names[8].equals(style)) {
			return this.drawALQ(name, enName, fCity, enFCity, fc3, tCity,
					enTCity, tc3, style, tmpPath);
		} else if (names[9].equals(style)) {
			return this.drawEL(name, enName, fCity, enFCity, fc3, tCity,
					enTCity, tc3, style, tmpPath);
		} else if (names[10].equals(style)) {
			return this.drawYN(name, enName, fCity, enFCity, fc3, tCity,
					enTCity, tc3, style, tmpPath);
		} else if (names[11].equals(style)) {
			return this.drawJP(name, enName, fCity, enFCity, fc3, tCity,
					enTCity, tc3, style, tmpPath);
		}
		return null;
	}

	private String[] getDate() {
		String[] date = new String[2];
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMM", Locale.ENGLISH);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, 1);
		date[0] = sdf.format(c.getTime()).toUpperCase();
		Random random = ThreadLocalRandom.current();
		if (c.get(Calendar.HOUR_OF_DAY) == 0) {
			date[1] = "00" + MS[random.nextInt(MS.length)];
		} else if (c.get(Calendar.HOUR_OF_DAY) < 10) {
			date[1] = "0" + c.get(Calendar.HOUR_OF_DAY)
					+ MS[random.nextInt(MS.length)];
		} else {
			date[1] = c.get(Calendar.HOUR_OF_DAY) + ""
					+ MS[random.nextInt(MS.length)];
		}

		return date;
	}

	private static final String[] MS = { "00", "05", "10", "15", "20", "25",
			"30", "35", "40", "45", "50", "55" };

	private String drawDF(String name, String enName, String fCity,
			String enFCity, String fc3, String tCity, String enTCity,
			String tc3, String style, String tmpPath) throws IOException {
		int fs = 14;
		SimplePositions hbSP = new SimplePositions();
		hbSP.buildHorizontalOffset(83).buildVerticalOffset(261);
		BufferedImage hbBI = drawText("MU 5939D", f.font(), f.type(), fs,
				color, 200, 0, 0, true);

		String[] dates = getDate();
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(83).buildVerticalOffset(298);
		BufferedImage dateBI = drawText(dates[0], f.font(), f.type(), fs,
				color, 200, 0, 0, true);

		SimplePositions nameEnSP = new SimplePositions();
		nameEnSP.buildHorizontalOffset(83).buildVerticalOffset(330);
		BufferedImage nameEnBI = drawText(enName, f.font(), f.type(), fs,
				color, 200, 0, 0, true);

		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(83).buildVerticalOffset(348);
		BufferedImage nameBI = drawText(name, f.font(), f.type(), fs + 2,
				color, 200, 0, 0, true);

		SimplePositions toESP = new SimplePositions();
		toESP.buildHorizontalOffset(390).buildVerticalOffset(283);
		BufferedImage toEBI = drawText(enTCity, f.font(), f.type(), fs, color,
				200, 0, 0, true);

		SimplePositions toSP = new SimplePositions();
		toSP.buildHorizontalOffset(390).buildVerticalOffset(302);
		BufferedImage toBI = drawText(tCity, f.font(), f.type(), fs + 2, color,
				200, 0, 0, true);

		SimplePositions hb2SP = new SimplePositions();
		hb2SP.buildHorizontalOffset(525).buildVerticalOffset(257);
		BufferedImage hb2BI = drawText("MU5939D", f.font(), f.type(), fs,
				color, 200, 0, 0, true);
		SimplePositions hb3SP = new SimplePositions();
		hb3SP.buildHorizontalOffset(642).buildVerticalOffset(257);

		SimplePositions date2SP = new SimplePositions();
		date2SP.buildHorizontalOffset(525).buildVerticalOffset(298);

		SimplePositions date3SP = new SimplePositions();
		date3SP.buildHorizontalOffset(642).buildVerticalOffset(298);

		SimplePositions[] sp = { hbSP, dateSP, nameEnSP, nameSP, toESP, toSP,
				hb2SP, hb3SP, date2SP, date3SP };
		BufferedImage[] bis = { hbBI, dateBI, nameEnBI, nameBI, toEBI, toBI,
				hb2BI, hb2BI, dateBI, dateBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}

	private String drawGL(String name, String enName, String fCity,
			String enFCity, String fc3, String tCity, String enTCity,
			String tc3, String style, String tmpPath) throws IOException {
		int fs = 12;
		Color color = new Color(88, 88, 88);
		String[] dates = getDate();
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(148).buildVerticalOffset(206);
		BufferedImage dateBI = drawText(dates[0], f.font(), f.type(), fs,
				color, 200, 0, 0, true);

		SimplePositions nameEnSP = new SimplePositions();
		nameEnSP.buildHorizontalOffset(104).buildVerticalOffset(184);
		BufferedImage nameEnBI = drawText(enName, f.font(), f.type(), fs,
				color, 200, 0, 0, true);

		SimplePositions nameEn2SP = new SimplePositions();
		nameEn2SP.buildHorizontalOffset(511).buildVerticalOffset(191);

		SimplePositions fESP = new SimplePositions();
		fESP.buildHorizontalOffset(501).buildVerticalOffset(213);
		BufferedImage fEBI = drawText(enFCity, f.font(), f.type(), fs, color,
				200, 0, 0, true);

		SimplePositions to2ESP = new SimplePositions();
		to2ESP.buildHorizontalOffset(501).buildVerticalOffset(238);

		SimplePositions toESP = new SimplePositions();
		toESP.buildHorizontalOffset(99).buildVerticalOffset(230);
		BufferedImage toEBI = drawText(enTCity, f.font(), f.type(), fs, color,
				200, 0, 0, true);

		SimplePositions date2SP = new SimplePositions();
		date2SP.buildHorizontalOffset(565).buildVerticalOffset(264);

		SimplePositions[] sp = { dateSP, nameEnSP, nameEn2SP, fESP, toESP,
				to2ESP, date2SP };
		BufferedImage[] bis = { dateBI, nameEnBI, nameEnBI, fEBI, toEBI, toEBI,
				dateBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}

	private String drawGH(String name, String enName, String fCity,
			String enFCity, String fc3, String tCity, String enTCity,
			String tc3, String style, String tmpPath) throws IOException {
		int fs = 14;
		double theta = 0.04;
		ZbFont f = ZbFont.HelveticaNeue;
		Color color = new Color(99, 99, 99);
		String[] dates = getDate();
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(320).buildVerticalOffset(222);
		BufferedImage dateBI = drawText(dates[0], f.font(), f.type(), fs,
				color, 200, 0, theta, true);

		SimplePositions timeSP = new SimplePositions();
		timeSP.buildHorizontalOffset(250).buildVerticalOffset(300);
		BufferedImage timeBI = drawText(dates[1], f.font(), f.type(), fs,
				color, 200, 0, theta, true);

		SimplePositions nameEnSP = new SimplePositions();
		nameEnSP.buildHorizontalOffset(90).buildVerticalOffset(200);
		BufferedImage nameEnBI = drawText(enName, f.font(), f.type(), fs,
				color, 200, 0, theta, true);

		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(90).buildVerticalOffset(217);
		BufferedImage nameBI = drawText(name, f.font(), f.type(), fs, color,
				200, 0, theta, true);

		SimplePositions name2SP = new SimplePositions();
		name2SP.buildHorizontalOffset(520).buildVerticalOffset(235);

		SimplePositions nameEn2SP = new SimplePositions();
		nameEn2SP.buildHorizontalOffset(520).buildVerticalOffset(219);

		SimplePositions fESP = new SimplePositions();
		fESP.buildHorizontalOffset(547).buildVerticalOffset(258);
		BufferedImage fEBI = drawText(enFCity, f.font(), f.type(), fs, color,
				200, 0, theta, true);

		SimplePositions toESP = new SimplePositions();
		toESP.buildHorizontalOffset(547).buildVerticalOffset(276);
		BufferedImage toEBI = drawText(enTCity, f.font(), f.type(), fs, color,
				200, 0, theta, true);

		SimplePositions toSP = new SimplePositions();
		toSP.buildHorizontalOffset(252).buildVerticalOffset(250);
		BufferedImage toBI = drawText(tCity, f.font(), f.type(), fs, color,
				200, 0, theta, true);

		SimplePositions toCSP = new SimplePositions();
		toCSP.buildHorizontalOffset(252).buildVerticalOffset(270);
		BufferedImage toCBI = drawText(tc3, f.font(), f.type(), fs, color, 200,
				0, theta, true);

		SimplePositions fSP = new SimplePositions();
		fSP.buildHorizontalOffset(110).buildVerticalOffset(245);
		BufferedImage fBI = drawText(fCity, f.font(), f.type(), fs, color, 200,
				0, theta, true);

		SimplePositions fcSP = new SimplePositions();
		fcSP.buildHorizontalOffset(110).buildVerticalOffset(265);
		BufferedImage fcBI = drawText(fc3, f.font(), f.type(), fs, color, 200,
				0, theta, true);

		SimplePositions date2SP = new SimplePositions();
		date2SP.buildHorizontalOffset(575).buildVerticalOffset(310);

		SimplePositions hbSP = new SimplePositions();
		hbSP.buildHorizontalOffset(261).buildVerticalOffset(219);
		BufferedImage hbBI = drawText("FM9112", f.font(), f.type(), fs, color,
				200, 0, theta, true);

		SimplePositions hb2SP = new SimplePositions();
		hb2SP.buildHorizontalOffset(517).buildVerticalOffset(308);

		SimplePositions[] sp = { dateSP, nameEnSP, nameEn2SP, fESP, toESP,
				date2SP, timeSP, nameSP, name2SP, toSP, toCSP, fSP, fcSP, hbSP,
				hb2SP };
		BufferedImage[] bis = { dateBI, nameEnBI, nameEnBI, fEBI, toEBI,
				dateBI, timeBI, nameBI, nameBI, toBI, toCBI, fBI, fcBI, hbBI,
				hbBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}

	private String drawGT(String name, String enName, String fCity,
			String enFCity, String fc3, String tCity, String enTCity,
			String tc3, String style, String tmpPath) throws IOException {
		int fs = 11;
		double theta = -0.09;
		ZbFont f = ZbFont.黑体加粗;
		Color color = new Color(33, 33, 33);
		// String[] dates = getDate();

		SimplePositions nameEnSP = new SimplePositions();
		nameEnSP.buildHorizontalOffset(130).buildVerticalOffset(216);
		BufferedImage nameEnBI = drawText(enName, f.font(), f.type(), fs,
				color, 200, 0, theta, true);

		SimplePositions nameEn2SP = new SimplePositions();
		nameEn2SP.buildHorizontalOffset(515).buildVerticalOffset(192);

		SimplePositions fESP = new SimplePositions();
		fESP.buildHorizontalOffset(524).buildVerticalOffset(216);
		BufferedImage fEBI = drawText(enFCity, f.font(), f.type(), fs, color,
				200, 0, theta, true);

		SimplePositions toESP = new SimplePositions();
		toESP.buildHorizontalOffset(534).buildVerticalOffset(235);
		BufferedImage toEBI = drawText(enTCity, f.font(), f.type(), fs, color,
				200, 0, theta, true);

		SimplePositions to2ESP = new SimplePositions();
		to2ESP.buildHorizontalOffset(131).buildVerticalOffset(260);

		SimplePositions[] sp = { nameEnSP, nameEn2SP, fESP, toESP, to2ESP };
		BufferedImage[] bis = { nameEnBI, nameEnBI, fEBI, toEBI, toEBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}

	private String drawNF(String name, String enName, String fCity,
			String enFCity, String fc3, String tCity, String enTCity,
			String tc3, String style, String tmpPath) throws IOException {
		int fs = 14;
		double theta = -0.02;
		ZbFont f = ZbFont.黑体加粗;
		Color color = new Color(66, 66, 66);
		String[] dates = getDate();
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(112).buildVerticalOffset(230);
		BufferedImage dateBI = drawText(dates[0], f.font(), f.type(), fs,
				color, 200, 0, theta, true);

		SimplePositions date2SP = new SimplePositions();
		date2SP.buildHorizontalOffset(533).buildVerticalOffset(224);

		SimplePositions date3SP = new SimplePositions();
		date3SP.buildHorizontalOffset(650).buildVerticalOffset(224);

		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(233).buildVerticalOffset(225);
		BufferedImage nameBI = drawText(name, f.font(), f.type(), fs, color,
				200, 0, theta, true);

		SimplePositions name2SP = new SimplePositions();
		name2SP.buildHorizontalOffset(533).buildVerticalOffset(289);

		SimplePositions name3SP = new SimplePositions();
		name3SP.buildHorizontalOffset(650).buildVerticalOffset(289);

		SimplePositions nameEnSP = new SimplePositions();
		nameEnSP.buildHorizontalOffset(233).buildVerticalOffset(240);
		BufferedImage nameEnBI = drawText(enName, f.font(), f.type(), fs,
				color, 200, 0, theta, true);

		// SimplePositions name2EnSP = new SimplePositions();
		// name2EnSP.buildHorizontalOffset(544).buildVerticalOffset(315);
		//
		// SimplePositions name3EnSP = new SimplePositions();
		// name3EnSP.buildHorizontalOffset(653).buildVerticalOffset(315);

		SimplePositions toESP = new SimplePositions();
		toESP.buildHorizontalOffset(233).buildVerticalOffset(204);
		BufferedImage toEBI = drawText(enTCity, f.font(), f.type(), fs, color,
				200, 0, theta, true);

		// SimplePositions to2ESP = new SimplePositions();
		// to2ESP.buildHorizontalOffset(548).buildVerticalOffset(265);
		//
		// SimplePositions to3ESP = new SimplePositions();
		// to3ESP.buildHorizontalOffset(666).buildVerticalOffset(265);

		SimplePositions toSP = new SimplePositions();
		toSP.buildHorizontalOffset(233).buildVerticalOffset(190);
		BufferedImage toBI = drawText(tCity, f.font(), f.type(), fs, color,
				200, 0, theta, true);

		SimplePositions to2SP = new SimplePositions();
		to2SP.buildHorizontalOffset(533).buildVerticalOffset(250);

		SimplePositions to3SP = new SimplePositions();
		to3SP.buildHorizontalOffset(650).buildVerticalOffset(251);

		SimplePositions[] sp = { dateSP, date2SP, date3SP, nameEnSP, toESP,
				nameSP, name2SP, name3SP, toSP, to2SP, to3SP };
		BufferedImage[] bis = { dateBI, dateBI, dateBI, nameEnBI, toEBI,
				nameBI, nameBI, nameBI, toBI, toBI, toBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}

	private String drawXM(String name, String enName, String fCity,
			String enFCity, String fc3, String tCity, String enTCity,
			String tc3, String style, String tmpPath) throws IOException {
		int fs = 18;
		double theta = -0.04;
		ZbFont f = ZbFont.HelveticaNeue;
		Color color = new Color(33, 33, 33);
		String[] dates = getDate();
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(112).buildVerticalOffset(230);
		BufferedImage dateBI = drawText(dates[0], f.font(), f.type(), fs,
				color, 200, 0, theta, true);

		SimplePositions date2SP = new SimplePositions();
		date2SP.buildHorizontalOffset(409).buildVerticalOffset(224);

		SimplePositions timeSP = new SimplePositions();
		timeSP.buildHorizontalOffset(271).buildVerticalOffset(285);
		BufferedImage timeBI = drawText(dates[1], f.font(), f.type(), fs,
				color, 200, 0, theta, true);

		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(287).buildVerticalOffset(192);
		BufferedImage nameBI = drawText(name, f.font(), f.type(), fs, color,
				200, 0, theta, true);

		// SimplePositions name2SP = new SimplePositions();
		// name2SP.buildHorizontalOffset(533).buildVerticalOffset(289);
		//
		// SimplePositions name3SP = new SimplePositions();
		// name3SP.buildHorizontalOffset(650).buildVerticalOffset(289);

		SimplePositions nameEnSP = new SimplePositions();
		nameEnSP.buildHorizontalOffset(287).buildVerticalOffset(165);
		BufferedImage nameEnBI = drawText(enName, f.font(), f.type(), fs,
				color, 200, 0, theta, true);

		// SimplePositions name2EnSP = new SimplePositions();
		// name2EnSP.buildHorizontalOffset(544).buildVerticalOffset(315);
		//
		// SimplePositions name3EnSP = new SimplePositions();
		// name3EnSP.buildHorizontalOffset(653).buildVerticalOffset(315);

		SimplePositions toESP = new SimplePositions();
		toESP.buildHorizontalOffset(571).buildVerticalOffset(215);
		BufferedImage toEBI = drawText(enTCity, f.font(), f.type(), fs, color,
				200, 0, theta, true);

		// SimplePositions to2ESP = new SimplePositions();
		// to2ESP.buildHorizontalOffset(548).buildVerticalOffset(265);
		//
		// SimplePositions to3ESP = new SimplePositions();
		// to3ESP.buildHorizontalOffset(666).buildVerticalOffset(265);

		SimplePositions toSP = new SimplePositions();
		toSP.buildHorizontalOffset(571).buildVerticalOffset(240);
		BufferedImage toBI = drawText(tCity, f.font(), f.type(), fs, color,
				200, 0, theta, true);

		SimplePositions toCSP = new SimplePositions();
		toCSP.buildHorizontalOffset(112).buildVerticalOffset(277);
		BufferedImage toCBI = drawText(tc3, f.font(), f.type(), fs, color, 200,
				0, theta, true);

		SimplePositions[] sp = { dateSP, date2SP, nameEnSP, toESP, nameSP,
				toSP, toCSP, timeSP };
		BufferedImage[] bis = { dateBI, dateBI, nameEnBI, toEBI, nameBI, toBI,
				toCBI, timeBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}

	private String drawSZ(String name, String enName, String fCity,
			String enFCity, String fc3, String tCity, String enTCity,
			String tc3, String style, String tmpPath) throws IOException {
		int fs = 18;
		int fs2 = 12;

		double theta = 0.02;
		ZbFont f = ZbFont.黑体加粗;
		ZbFont f2 = ZbFont.宋体;
		Color color = new Color(33, 33, 33);
		String[] dates = getDate();
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(85).buildVerticalOffset(180);
		BufferedImage dateBI = drawText(dates[0], f.font(), f.type(), fs,
				color, 200, 0, theta, true);

		SimplePositions date2SP = new SimplePositions();
		date2SP.buildHorizontalOffset(566).buildVerticalOffset(235);

		SimplePositions timeSP = new SimplePositions();
		timeSP.buildHorizontalOffset(105).buildVerticalOffset(260);
		BufferedImage timeBI = drawText(dates[1], f.font(), f.type(), fs,
				color, 200, 0, theta, true);

		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(248).buildVerticalOffset(222);
		BufferedImage nameBI = drawText(name, f2.font(), f2.type(), fs2, color,
				200, 0, theta, true);

		SimplePositions name2SP = new SimplePositions();
		name2SP.buildHorizontalOffset(570).buildVerticalOffset(176);

		SimplePositions nameEnSP = new SimplePositions();
		nameEnSP.buildHorizontalOffset(84).buildVerticalOffset(220);
		BufferedImage nameEnBI = drawText(enName, f2.font(), f2.type(), fs2,
				color, 200, 0, theta, true);

		SimplePositions name2EnSP = new SimplePositions();
		name2EnSP.buildHorizontalOffset(518).buildVerticalOffset(210);

		SimplePositions toESP = new SimplePositions();
		toESP.buildHorizontalOffset(213).buildVerticalOffset(154);
		BufferedImage toEBI = drawText(enTCity, f2.font(), f2.type(), fs2,
				color, 200, 0, theta, true);

		SimplePositions toSP = new SimplePositions();
		toSP.buildHorizontalOffset(213).buildVerticalOffset(138);
		BufferedImage toBI = drawText(tCity, f2.font(), f2.type(), fs2, color,
				200, 0, theta, true);

		SimplePositions to2SP = new SimplePositions();
		to2SP.buildHorizontalOffset(623).buildVerticalOffset(260);

		SimplePositions toCSP = new SimplePositions();
		toCSP.buildHorizontalOffset(580).buildVerticalOffset(260);
		BufferedImage toCBI = drawText(tc3, f2.font(), f2.type(), fs2, color,
				200, 0, theta, true);

		SimplePositions[] sp = { dateSP, date2SP, timeSP, nameEnSP, name2EnSP,
				toESP, nameSP, name2SP, toSP, to2SP, toCSP, timeSP };
		BufferedImage[] bis = { dateBI, dateBI, timeBI, nameEnBI, nameEnBI,
				toEBI, nameBI, nameBI, toBI, toBI, toCBI, timeBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}

	private String drawGHGJ(String name, String enName, String fCity,
			String enFCity, String fc3, String tCity, String enTCity,
			String tc3, String style, String tmpPath) throws IOException {
		int fs = 11;
		double theta = 0;
		double theta2 = -0.08;
		ZbFont f = ZbFont.仿宋;
		Color color = new Color(22, 22, 22);
		String[] dates = getDate();
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(308).buildVerticalOffset(160);
		BufferedImage dateBI = drawText(dates[0], f.font(), f.type(), fs,
				color, 200, 0, theta, true);

		BufferedImage date2BI = drawText(dates[0], f.font(), f.type(), fs,
				color, 200, 0, theta2, true);
		SimplePositions date2SP = new SimplePositions();
		date2SP.buildHorizontalOffset(482).buildVerticalOffset(199);

		SimplePositions nameEnSP = new SimplePositions();
		nameEnSP.buildHorizontalOffset(142).buildVerticalOffset(150);
		BufferedImage nameEnBI = drawText(enName, f.font(), f.type(), fs,
				color, 200, 0, theta, true);

		SimplePositions name2EnSP = new SimplePositions();
		name2EnSP.buildHorizontalOffset(444).buildVerticalOffset(144);
		BufferedImage name2EnBI = drawText(enName, f.font(), f.type(), fs,
				color, 200, 0, theta2, true);

		SimplePositions toESP = new SimplePositions();
		toESP.buildHorizontalOffset(473).buildVerticalOffset(174);
		BufferedImage toEBI = drawText(enTCity, f.font(), f.type(), fs, color,
				200, 0, theta2, true);

		SimplePositions fESP = new SimplePositions();
		fESP.buildHorizontalOffset(473).buildVerticalOffset(162);
		BufferedImage fEBI = drawText(enFCity, f.font(), f.type(), fs, color,
				200, 0, theta2, true);

		SimplePositions toSP = new SimplePositions();
		toSP.buildHorizontalOffset(270).buildVerticalOffset(180);
		BufferedImage toBI = drawText(tCity, f.font(), f.type(), fs, color,
				200, 0, theta, true);

		SimplePositions toCSP = new SimplePositions();
		toCSP.buildHorizontalOffset(270).buildVerticalOffset(190);
		BufferedImage toCBI = drawText(tc3, f.font(), f.type(), fs, color, 200,
				0, theta, true);

		SimplePositions fSP = new SimplePositions();
		fSP.buildHorizontalOffset(176).buildVerticalOffset(180);
		BufferedImage fBI = drawText(fCity, f.font(), f.type(), fs, color, 200,
				0, theta, true);

		SimplePositions fCSP = new SimplePositions();
		fCSP.buildHorizontalOffset(176).buildVerticalOffset(190);
		BufferedImage fCBI = drawText(fc3, f.font(), f.type(), fs, color, 200,
				0, theta, true);

		SimplePositions[] sp = { dateSP, date2SP, nameEnSP, name2EnSP, toESP,
				toSP, toCSP, fSP, fCSP, fESP };
		BufferedImage[] bis = { dateBI, date2BI, nameEnBI, name2EnBI, toEBI,
				toBI, toCBI, fBI, fCBI, fEBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}

	private String drawALQ(String name, String enName, String fCity,
			String enFCity, String fc3, String tCity, String enTCity,
			String tc3, String style, String tmpPath) throws IOException {
		int fs = 14;
		double theta = 0;
		// double theta2 = -0.08;
		ZbFont f = ZbFont.仿宋;
		Color color = new Color(22, 22, 22);
		String[] dates = getDate();
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(320).buildVerticalOffset(206);
		BufferedImage dateBI = drawText(dates[0], f.font(), f.type(), fs,
				color, 200, 0, theta, true);

		SimplePositions date2SP = new SimplePositions();
		date2SP.buildHorizontalOffset(615).buildVerticalOffset(240);

		SimplePositions nameEnSP = new SimplePositions();
		nameEnSP.buildHorizontalOffset(34).buildVerticalOffset(156);
		BufferedImage nameEnBI = drawText(enName, f.font(), f.type(), fs,
				color, 200, 0, theta, true);

		SimplePositions name2EnSP = new SimplePositions();
		name2EnSP.buildHorizontalOffset(544).buildVerticalOffset(190);

		SimplePositions toESP = new SimplePositions();
		toESP.buildHorizontalOffset(34).buildVerticalOffset(240);
		BufferedImage toEBI = drawText(enTCity, f.font(), f.type(), fs, color,
				200, 0, theta, true);

		SimplePositions fESP = new SimplePositions();
		fESP.buildHorizontalOffset(34).buildVerticalOffset(207);
		BufferedImage fEBI = drawText(enFCity, f.font(), f.type(), fs, color,
				200, 0, theta, true);

		SimplePositions toCSP = new SimplePositions();
		toCSP.buildHorizontalOffset(632).buildVerticalOffset(207);
		BufferedImage toCBI = drawText(tc3, f.font(), f.type(), fs, color, 200,
				0, theta, true);

		// SimplePositions fSP = new SimplePositions();
		// fSP.buildHorizontalOffset(176).buildVerticalOffset(180);
		// BufferedImage fBI = drawText(fCity, f.font(), f.type(), fs, color,
		// 200,
		// 0, theta, true);

		SimplePositions fCSP = new SimplePositions();
		fCSP.buildHorizontalOffset(571).buildVerticalOffset(207);
		BufferedImage fCBI = drawText(fc3, f.font(), f.type(), fs, color, 200,
				0, theta, true);

		SimplePositions[] sp = { dateSP, date2SP, nameEnSP, name2EnSP, toESP,
				toCSP, fCSP, fESP };
		BufferedImage[] bis = { dateBI, dateBI, nameEnBI, nameEnBI, toEBI,
				toCBI, fCBI, fEBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}

	private String drawEL(String name, String enName, String fCity,
			String enFCity, String fc3, String tCity, String enTCity,
			String tc3, String style, String tmpPath) throws IOException {
		int fs = 12;
		double theta = 0;
		// double theta2 = -0.08;
		ZbFont f = ZbFont.黑体加粗;
		Color color = new Color(22, 22, 22);
		String[] dates = getDate();
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(363).buildVerticalOffset(289);
		BufferedImage dateBI = drawText(dates[0], f.font(), f.type(), fs,
				color, 200, 0, theta, true);

		// SimplePositions date2SP = new SimplePositions();
		// date2SP.buildHorizontalOffset(615).buildVerticalOffset(240);

		SimplePositions nameEnSP = new SimplePositions();
		nameEnSP.buildHorizontalOffset(86).buildVerticalOffset(257);
		BufferedImage nameEnBI = drawText(enName, f.font(), f.type(), fs,
				color, 200, 0, theta, true);

		SimplePositions name2EnSP = new SimplePositions();
		name2EnSP.buildHorizontalOffset(566).buildVerticalOffset(240);

		SimplePositions toESP = new SimplePositions();
		toESP.buildHorizontalOffset(86).buildVerticalOffset(320);
		BufferedImage toEBI = drawText(enTCity, f.font(), f.type(), fs, color,
				200, 0, theta, true);

		SimplePositions to2ESP = new SimplePositions();
		to2ESP.buildHorizontalOffset(573).buildVerticalOffset(320);

		SimplePositions fESP = new SimplePositions();
		fESP.buildHorizontalOffset(86).buildVerticalOffset(286);
		BufferedImage fEBI = drawText(enFCity, f.font(), f.type(), fs, color,
				200, 0, theta, true);

		SimplePositions fE2SP = new SimplePositions();
		fE2SP.buildHorizontalOffset(573).buildVerticalOffset(298);
		//
		// SimplePositions toCSP = new SimplePositions();
		// toCSP.buildHorizontalOffset(632).buildVerticalOffset(207);
		// BufferedImage toCBI = drawText(tc3, f.font(), f.type(), fs, color,
		// 200,
		// 0, theta, true);

		SimplePositions timeSP = new SimplePositions();
		timeSP.buildHorizontalOffset(412).buildVerticalOffset(289);
		BufferedImage timeBI = drawText(dates[1], f.font(), f.type(), fs,
				color, 200, 0, theta, true);

		SimplePositions time2SP = new SimplePositions();
		time2SP.buildHorizontalOffset(571).buildVerticalOffset(376);

		// SimplePositions fCSP = new SimplePositions();
		// fCSP.buildHorizontalOffset(571).buildVerticalOffset(207);
		// BufferedImage fCBI = drawText(fc3, f.font(), f.type(), fs, color,
		// 200,
		// 0, theta, true);

		SimplePositions[] sp = { dateSP, nameEnSP, name2EnSP, toESP, to2ESP,
				fE2SP, fESP, timeSP, time2SP };
		BufferedImage[] bis = { dateBI, nameEnBI, nameEnBI, toEBI, toEBI, fEBI,
				fEBI, timeBI, timeBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}

	private String drawYN(String name, String enName, String fCity,
			String enFCity, String fc3, String tCity, String enTCity,
			String tc3, String style, String tmpPath) throws IOException {
		int fs = 18;
		double theta = -0.01;
		// double theta2 = -0.08;
		ZbFont f = ZbFont.黑体加粗;
		Color color = new Color(77, 77, 77);
		String[] dates = getDate();
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(452).buildVerticalOffset(236);
		BufferedImage dateBI = drawText(dates[0], f.font(), f.type(), fs,
				color, 200, 0, theta, true);

		SimplePositions date2SP = new SimplePositions();
		date2SP.buildHorizontalOffset(664).buildVerticalOffset(285);

		SimplePositions nameEnSP = new SimplePositions();
		nameEnSP.buildHorizontalOffset(75).buildVerticalOffset(238);
		BufferedImage nameEnBI = drawText(enName, f.font(), f.type(), fs,
				color, 200, 0, theta, true);

		SimplePositions name2EnSP = new SimplePositions();
		name2EnSP.buildHorizontalOffset(544).buildVerticalOffset(26);

		SimplePositions toESP = new SimplePositions();
		toESP.buildHorizontalOffset(75).buildVerticalOffset(348);
		BufferedImage toEBI = drawText(enTCity, f.font(), f.type(), fs, color,
				200, 0, theta, true);

		SimplePositions to2ESP = new SimplePositions();
		to2ESP.buildHorizontalOffset(574).buildVerticalOffset(200);

		SimplePositions fESP = new SimplePositions();
		fESP.buildHorizontalOffset(75).buildVerticalOffset(298);
		BufferedImage fEBI = drawText(enFCity, f.font(), f.type(), fs, color,
				200, 0, theta, true);

		SimplePositions fE2SP = new SimplePositions();
		fE2SP.buildHorizontalOffset(574).buildVerticalOffset(182);
		//
		// SimplePositions toCSP = new SimplePositions();
		// toCSP.buildHorizontalOffset(632).buildVerticalOffset(207);
		// BufferedImage toCBI = drawText(tc3, f.font(), f.type(), fs, color,
		// 200,
		// 0, theta, true);

		SimplePositions timeSP = new SimplePositions();
		timeSP.buildHorizontalOffset(412).buildVerticalOffset(289);
		BufferedImage timeBI = drawText(dates[1], f.font(), f.type(), fs,
				color, 200, 0, theta, true);

		// SimplePositions time2SP = new SimplePositions();
		// time2SP.buildHorizontalOffset(571).buildVerticalOffset(376);

		// SimplePositions fCSP = new SimplePositions();
		// fCSP.buildHorizontalOffset(571).buildVerticalOffset(207);
		// BufferedImage fCBI = drawText(fc3, f.font(), f.type(), fs, color,
		// 200,
		// 0, theta, true);

		SimplePositions[] sp = { dateSP, date2SP, nameEnSP, name2EnSP, toESP,
				to2ESP, fE2SP, fESP, timeSP };
		BufferedImage[] bis = { dateBI, dateBI, nameEnBI, nameEnBI, toEBI,
				toEBI, fEBI, fEBI, timeBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}

	private String drawJP(String name, String enName, String fCity,
			String enFCity, String fc3, String tCity, String enTCity,
			String tc3, String style, String tmpPath) throws IOException {
		int fs = 14;
		double theta = 0;
		// double theta2 = -0.08;
		ZbFont f = ZbFont.黑体加粗;
		Color color = new Color(20, 20, 20);
		String[] dates = getDate();
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(204).buildVerticalOffset(102);
		BufferedImage dateBI = drawText(dates[0], f.font(), f.type(), fs,
				color, 200, 0, theta, true);

		SimplePositions date2SP = new SimplePositions();
		date2SP.buildHorizontalOffset(610).buildVerticalOffset(193);

		SimplePositions nameEnSP = new SimplePositions();
		nameEnSP.buildHorizontalOffset(109).buildVerticalOffset(86);
		BufferedImage nameEnBI = drawText(enName, f.font(), f.type(), 12,
				color, 200, 0, theta, true);

		SimplePositions name2EnSP = new SimplePositions();
		name2EnSP.buildHorizontalOffset(519).buildVerticalOffset(105);
		BufferedImage name2EnBI = drawText(enName, f.font(), f.type(), fs,
				color, 200, 0, -0.01, true);

		SimplePositions toESP = new SimplePositions();
		toESP.buildHorizontalOffset(534).buildVerticalOffset(154);
		BufferedImage toEBI = drawText(enTCity, f.font(), f.type(), fs, color,
				200, 0, theta, true);

		// SimplePositions to2ESP = new SimplePositions();
		// to2ESP.buildHorizontalOffset(573).buildVerticalOffset(320);

		SimplePositions fESP = new SimplePositions();
		fESP.buildHorizontalOffset(534).buildVerticalOffset(138);
		BufferedImage fEBI = drawText(enFCity, f.font(), f.type(), fs, color,
				200, 0, theta, true);
		//
		// SimplePositions fE2SP = new SimplePositions();
		// fE2SP.buildHorizontalOffset(573).buildVerticalOffset(298);
		//
		// SimplePositions toCSP = new SimplePositions();
		// toCSP.buildHorizontalOffset(632).buildVerticalOffset(207);
		// BufferedImage toCBI = drawText(tc3, f.font(), f.type(), fs, color,
		// 200,
		// 0, theta, true);

		SimplePositions timeSP = new SimplePositions();
		timeSP.buildHorizontalOffset(202).buildVerticalOffset(172);
		BufferedImage timeBI = drawText(dates[1], f.font(), f.type(), 24,
				color, 200, 0, theta, true);

		// SimplePositions time2SP = new SimplePositions();
		// time2SP.buildHorizontalOffset(571).buildVerticalOffset(376);

		// SimplePositions fCSP = new SimplePositions();
		// fCSP.buildHorizontalOffset(571).buildVerticalOffset(207);
		// BufferedImage fCBI = drawText(fc3, f.font(), f.type(), fs, color,
		// 200,
		// 0, theta, true);

		SimplePositions[] sp = { dateSP, date2SP, nameEnSP, name2EnSP, toESP,
				fESP, timeSP };
		BufferedImage[] bis = { dateBI, dateBI, nameEnBI, name2EnBI, toEBI,
				fEBI, timeBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}
}
