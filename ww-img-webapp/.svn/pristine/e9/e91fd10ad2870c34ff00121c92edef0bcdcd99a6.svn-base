package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;

public class ChongQiWaWaTool extends BaseTool {

	public ChongQiWaWaTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		// String tmpPath =
		// "http://imgzb.zhuangdianbi.com/56fb777b0cf2338af25a719d";
		String tmpPath = "http://imgzb.zhuangdianbi.com/56fbc7a80cf2a5e21d1ee12a";
		StorageService storageService = new StorageService();
		ChongQiWaWaTool tyt = new ChongQiWaWaTool(storageService);
		//tyt.isDebug(true);
		System.out.println(tyt.drawImg("北京", "北京", "朝阳", "大屯路123号", "装点逼",
				"韩红款", tmpPath));
	}

	String[] PHONES = { "130", "131", "132", "134", "133", "138", "137", "139",
			"135", "159", "177", "136", "150", "151", "152", "155", "182",
			"187", "188", "185", "186", "180" };
	static Random r = new Random();

	private int get2N() {
		return r.nextInt(90) + 10;
	}

	public String drawImg(String sheng, String shi, String qu, String lu,
			String name, String style, String tmpPath) throws IOException {
		int fontSize = 14;
		Color color = new Color(65, 105, 225);
		String fontStyle = "黑体";
		int fontType = Font.PLAIN;
		int rotate = -3;

		String fjr = "美日幻情趣店";
		SimplePositions fjrSP = new SimplePositions();
		fjrSP.buildHorizontalOffset(123).buildVerticalOffset(166);
		BufferedImage fjrBI = drawText(fjr, fontStyle, fontType, fontSize,
				color, 200, 20, 0, false);
		fjrBI = super.rotateImg(fjrBI, rotate, null, 200, 28);

		String jjr = "广东省东莞市八一路38号金鼎大厦5层520室";
		SimplePositions addSP = new SimplePositions();
		addSP.buildHorizontalOffset(83).buildVerticalOffset(180);
		BufferedImage addBI = drawText(jjr, fontStyle, fontType, fontSize,
				color, 480, 30, 0, false);
		addBI = super.rotateImg(addBI, rotate, null, 480, 45);
		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(250).buildVerticalOffset(214);
		BufferedImage nameBI = drawText(name, fontStyle, fontType, fontSize,
				color, 120, 30, 0, false);
		nameBI = super.rotateImg(nameBI, rotate, null, 120, 45);

		// 省
		SimplePositions shengSP = new SimplePositions();
		shengSP.buildHorizontalOffset(77).buildVerticalOffset(240);
		BufferedImage shengBI = drawText(sheng, fontStyle, fontType, fontSize,
				color, 120, 30, 0, false);
		shengBI = super.rotateImg(shengBI, rotate, null, 120, 45);

		// 市
		SimplePositions shiSP = new SimplePositions();
		shiSP.buildHorizontalOffset(140).buildVerticalOffset(237);
		BufferedImage shiBI = drawText(shi, fontStyle, fontType, fontSize,
				color, 120, 30, 0, false);
		shiBI = super.rotateImg(shiBI, rotate, null, 120, 45);

		// qu
		SimplePositions quSP = new SimplePositions();
		quSP.buildHorizontalOffset(200).buildVerticalOffset(234);
		BufferedImage quBI = drawText(qu, fontStyle, fontType, fontSize, color,
				120, 30, 0, false);
		quBI = super.rotateImg(quBI, rotate, null, 120, 45);

		// lu
		SimplePositions luSP = new SimplePositions();
		luSP.buildHorizontalOffset(77).buildVerticalOffset(253);
		BufferedImage luBI = drawText(lu, fontStyle, fontType, fontSize, color,
				420, 30, 0, false);
		luBI = super.rotateImg(luBI, rotate, null, 420, 45);

		// lu
		String phone = PHONES[r.nextInt(PHONES.length)] + get2N();
		SimplePositions phoneSP = new SimplePositions();
		phoneSP.buildHorizontalOffset(101).buildVerticalOffset(275);
		BufferedImage phoneBI = drawText(phone, fontStyle, fontType, fontSize,
				color, 420, 30, 0, false);
		phoneBI = super.rotateImg(phoneBI, rotate, null, 420, 45);

		SimplePositions phone2SP = new SimplePositions();
		phone2SP.buildHorizontalOffset(131).buildVerticalOffset(289);
		BufferedImage phone2BI = super
				.getImg("http://imgzb.zhuangdianbi.com/56fbb7560cf2338af25a7bae");

		String styleT = "充气娃娃（" + style + "）ZB-1568";
		SimplePositions styleSP = new SimplePositions();
		styleSP.buildHorizontalOffset(77).buildVerticalOffset(340);
		BufferedImage styleBI = drawText(styleT, fontStyle, fontType, fontSize,
				color, 420, 30, 0, false);
		styleBI = super.rotateImg(styleBI, rotate, null, 420, 45);

		SimplePositions name2SP = new SimplePositions();
		name2SP.buildHorizontalOffset(77).buildVerticalOffset(380);
		BufferedImage name2BI = drawText(fjr, fontStyle, fontType, fontSize,
				color, 420, 30, 0, false);
		name2BI = super.rotateImg(name2BI, rotate, null, 420, 45);

		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, -42);
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(77).buildVerticalOffset(398);
		BufferedImage dateBI = drawText(
				DateUtil.dateFormatShortCN(c.getTime()), fontStyle, fontType,
				fontSize, color, 420, 30, 0, false);
		dateBI = super.rotateImg(dateBI, rotate, null, 420, 45);

		String yj = "8521934597";
		SimplePositions yueSP = new SimplePositions();
		yueSP.buildHorizontalOffset(380).buildVerticalOffset(352);
		BufferedImage yueBI = drawText(yj, fontStyle, fontType, fontSize,
				color, 420, 30, 0, false);
		yueBI = super.rotateImg(yueBI, rotate, null, 420, 45);

		String mark = "亲，快递上不要写充气娃娃，你懂的！";
		SimplePositions mSP = new SimplePositions();
		mSP.buildHorizontalOffset(380).buildVerticalOffset(378);
		BufferedImage mBI = drawText(mark, fontStyle, fontType, fontSize,
				color, 420, 30, 0, false);
		mBI = super.rotateImg(mBI, rotate, null, 420, 45);

		SimplePositions[] sp = { addSP, fjrSP, nameSP, shengSP, shiSP, quSP,
				luSP, phoneSP, styleSP, phone2SP, name2SP, dateSP, yueSP, mSP };

		BufferedImage[] bis = { addBI, fjrBI, nameBI, shengBI, shiBI, quBI,
				luBI, phoneBI, styleBI, phone2BI, name2BI, dateBI, yueBI, mBI };

		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
