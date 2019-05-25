package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;


import com.zb.service.cloud.StorageService;


public class CaiKuangZhengTool extends BaseTool {

	public CaiKuangZhengTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath = "http://imgzb.zhuangdianbi.com/56f38ae993b055c5f4a8876f";
		if (tmpPath.contains("?")) {
			tmpPath = tmpPath.substring(0, tmpPath.indexOf("?"));
		}
		System.out.println(tmpPath);
		StorageService ss = new StorageService();
		CaiKuangZhengTool jzt = new CaiKuangZhengTool(ss);
		System.out.println(jzt.drawImg("赵鹏", tmpPath));
	}

	public String drawImg(String name, String tmpPath) throws IOException {
		String[] fontStyles = {"徐金如硬笔行楷X","罗西钢笔行楷","新蒂下午茶基本版"};
		int i = new Random().nextInt(3);
		String nameStyle = fontStyles[i];
		String numberStyle = fontStyles[new Random().nextInt(2)];
		System.out.println(nameStyle+","+numberStyle);
		Color color = new Color(95,95,95);
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(129).buildVerticalOffset(146-4*(i+2));
		BufferedImage nameBI = drawText(name, nameStyle, Font.BOLD, 20,
				color, 300);

		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		int height = 414;
		int numberType = Font.BOLD;
		SimplePositions yearSP = new SimplePositions();
		yearSP.buildHorizontalOffset(187).buildVerticalOffset(height);
		BufferedImage yearBI = drawText("" + year, numberStyle, numberType, 14,
				color, 300);

		SimplePositions monthSP = new SimplePositions();
		monthSP.buildHorizontalOffset(237).buildVerticalOffset(height);
		BufferedImage monthBI = drawText("" + month, numberStyle, numberType, 14,
				color, 300);

		SimplePositions daySP = new SimplePositions();
		daySP.buildHorizontalOffset(277).buildVerticalOffset(height);
		BufferedImage dayBI = drawText("" + day, numberStyle, numberType, 14,
				color, 300);
		SimplePositions[] sp = {nameSP, yearSP, monthSP, daySP};
		
		BufferedImage[] bis = {nameBI, yearBI, monthBI, dayBI};
		
		return super.getSaveFile(sp, bis, 0.9f, tmpPath);
	}
}
