package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;


import com.zb.service.cloud.StorageService;


public class QingHuaDaXueTool extends BaseTool {

	public QingHuaDaXueTool(StorageService storageService) {
		super(storageService);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		String tmpPath = "http://imgzb.zhuangdianbi.com/56f38c8b93b055c5f4a88778";
		if (tmpPath.contains("?")) {
			tmpPath = tmpPath.substring(0, tmpPath.indexOf("?"));
		}
		StorageService storageService = new StorageService(); 
		QingHuaDaXueTool tyt = new QingHuaDaXueTool(storageService);
		System.out.println(tyt.drawImg("苍井空", "装逼研发", tmpPath));
	}

	/***
	 * @param name
	 *            姓名
	 * @param zhuanye
	 * 			专业
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String name, String zhuanye, String tmpPath) throws IOException {
		
		int fontSize = 16;
		Color color = new Color(9,47,50);
		String fontStyle = "微软雅黑";
		int fontType = Font.PLAIN;
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(116).buildVerticalOffset(194);
		BufferedImage nameBI = drawText(name, fontStyle, fontType, fontSize,
				color, 260);
		
		//专业
		SimplePositions zhuanyeSP = new SimplePositions();
		zhuanyeSP.buildHorizontalOffset(257).buildVerticalOffset(218);
		BufferedImage zhuanyeBI = drawText(zhuanye, fontStyle, fontType, fontSize,
				color, 260);
		//日期
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		int left = 308;
		int height = 342;
		int numberType = Font.PLAIN;
		SimplePositions yearSP = new SimplePositions();
		yearSP.buildHorizontalOffset(left).buildVerticalOffset(height);
		BufferedImage yearBI = drawText("" + year, fontStyle, numberType, 14,
				color, 300);

		SimplePositions monthSP = new SimplePositions();
		monthSP.buildHorizontalOffset(left + 52).buildVerticalOffset(height);
		BufferedImage monthBI = drawText("" + month, fontStyle, numberType, 14,
				color, 300);

		SimplePositions daySP = new SimplePositions();
		daySP.buildHorizontalOffset(left + 72).buildVerticalOffset(height);
		BufferedImage dayBI = drawText("" + day, fontStyle, numberType, 14,
				color, 300);
		SimplePositions[] sp = {nameSP, zhuanyeSP, yearSP, monthSP, daySP};
		
		BufferedImage[] bis = {nameBI, zhuanyeBI, yearBI, monthBI, dayBI};
		
		return super.getSaveFile(sp, bis, 0.9f, tmpPath);
	}

}
