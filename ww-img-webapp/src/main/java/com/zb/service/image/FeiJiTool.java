package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;


import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;


public class FeiJiTool extends BaseTool {

	public FeiJiTool(StorageService storageService) {
		super(storageService);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		String tmpPath = "http://imgzb.zhuangdianbi.com/56f0f1c193b071409286d7f8";
		if (tmpPath.contains("?")) {
			tmpPath = tmpPath.substring(0, tmpPath.indexOf("?"));
		}
		String pic = "123.png";
		StorageService storageService = new StorageService(); 
		FeiJiTool tyt = new FeiJiTool(storageService);
		System.out.println(tyt.drawImg("苍井空", 1458546430458L, pic, "女", tmpPath));
	}

	/***
	 * @param name
	 *            姓名
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String name, Long timestamp, String pic, String sex, String tmpPath) throws IOException {
		
		int fontSize = 14;
		Color color = new Color(74,74,74);
		String fontStyle = "仿宋";
		int fontType = Font.BOLD;
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(97).buildVerticalOffset(200);
		BufferedImage nameBI = drawText(name, fontStyle, fontType, fontSize,
				color, 260);
		//性别
		SimplePositions sexSP = new SimplePositions();
		sexSP.buildHorizontalOffset(97).buildVerticalOffset(220);
		BufferedImage sexBI = drawText(sex, fontStyle, fontType, fontSize,
				color, 260);
		//出生日期
		String birthday = DateUtil.dateFormatNull(new Date(timestamp));
		SimplePositions birthdaySP = new SimplePositions();
		birthdaySP.buildHorizontalOffset(121).buildVerticalOffset(240);
		BufferedImage birthdayBI = drawText(birthday, fontStyle, fontType, fontSize,
				color, 260);
		//头像
		SimplePositions imageSP = new SimplePositions();
		imageSP.buildHorizontalOffset(228).buildVerticalOffset(71);
		BufferedImage imageBI = compressImage(pic, 112, 151);
		
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		String date = "" + year + (month < 10 ? " 0" + month : " " + month)
				+ (day > 10 ? " " + day : " 0" + day);
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(465).buildVerticalOffset(151);
		BufferedImage dateBI = drawText(date, fontStyle, fontType, fontSize,
				color, 260);

		SimplePositions[] sp = {nameSP, dateSP, sexSP, birthdaySP, imageSP};
		
		BufferedImage[] bis = {nameBI, dateBI, sexBI, birthdayBI, imageBI};
		
		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
