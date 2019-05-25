package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;


import com.zb.service.cloud.StorageService;


public class BeiJingDaXueTool extends BaseTool {

	public BeiJingDaXueTool(StorageService storageService) {
		super(storageService);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		String tmpPath = "http://imgzb.zhuangdianbi.com/56f38c1593b055c5f4a88772";
		if (tmpPath.contains("?")) {
			tmpPath = tmpPath.substring(0, tmpPath.indexOf("?"));
		}
		//String pic = "/16/0315/13/small23.png";
		StorageService storageService = new StorageService(); 
		BeiJingDaXueTool tyt = new BeiJingDaXueTool(storageService);
		System.out.println(tyt.drawImg("赵鹏", "装逼学院", "装逼研发", tmpPath));
	}

	/***
	 * @param name
	 *            姓名
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String name, String xueyuan, String zhuanye, String tmpPath) throws IOException {
		
		int fontSize = 30;
		Color color = new Color(69,79,89);
		String fontStyle = "微软雅黑";
		int fontType = Font.PLAIN;
		
		int left = 198;
		left = left - name.length() * fontSize; 
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(left).buildVerticalOffset(485);
		BufferedImage nameBI = drawText(name, fontStyle, fontType, fontSize,
				color, 260);
		//学院
		SimplePositions xueyuanSP = new SimplePositions();
		xueyuanSP.buildHorizontalOffset(421).buildVerticalOffset(536);
		BufferedImage xueyuanBI = drawText(xueyuan, fontStyle, fontType, fontSize,
				color, 260);
		
		//专业
		SimplePositions zhuanyeSP = new SimplePositions();
		zhuanyeSP.buildHorizontalOffset(255).buildVerticalOffset(590);
		BufferedImage zhuanyeBI = drawText(zhuanye, fontStyle, fontType, fontSize,
				color, 260);
		//日期
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		int height = 884;
		int numberType = Font.PLAIN;
		SimplePositions yearSP = new SimplePositions();
		yearSP.buildHorizontalOffset(367).buildVerticalOffset(height);
		BufferedImage yearBI = drawText("" + year, fontStyle, numberType, fontSize,
				color, 300);

		SimplePositions monthSP = new SimplePositions();
		monthSP.buildHorizontalOffset(478).buildVerticalOffset(height);
		BufferedImage monthBI = drawText("" + month, fontStyle, numberType, fontSize,
				color, 300);

		SimplePositions daySP = new SimplePositions();
		daySP.buildHorizontalOffset(549).buildVerticalOffset(height);
		BufferedImage dayBI = drawText("" + day, fontStyle, numberType, fontSize,
				color, 300);
		SimplePositions[] sp = {nameSP, xueyuanSP, zhuanyeSP, yearSP, monthSP, daySP};
		
		BufferedImage[] bis = {nameBI, xueyuanBI, zhuanyeBI, yearBI, monthBI, dayBI};
		
		return super.getSaveFile(sp, bis, 0.9f, tmpPath);
	}

}
