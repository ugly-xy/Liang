package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;

import com.zb.service.cloud.StorageService;

public class WuRenJiaShiTool extends BaseTool {

	public WuRenJiaShiTool(StorageService storageService) {
		super(storageService);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		String tmpPath = "http://imgzb.zhuangdianbi.com/56f0f1f193b071409286d7fb";
		if (tmpPath.contains("?")) {
			tmpPath = tmpPath.substring(0, tmpPath.indexOf("?"));
		}
		StorageService storageService = new StorageService(); 
		WuRenJiaShiTool tyt = new WuRenJiaShiTool(storageService);
		System.out.println(tyt.drawImg("苍井空", tmpPath));
	}

	/***
	 * @param name
	 *            姓名
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String name, String tmpPath) throws IOException {
		
		int fontSize = 20;
		Color color = new Color(53,88,113);
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(201).buildVerticalOffset(224);
		BufferedImage nameBI = drawText(name, "微软雅黑", Font.PLAIN, fontSize,
				color, 260);
		
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		String date = "" + year + (month < 10 ? "年" + month : "年" + month)
				+ (day < 10 ? "月" + day : "月" + day);
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(224).buildVerticalOffset(356);
		BufferedImage dateBI = drawText(date, "微软雅黑", Font.PLAIN, fontSize,
				color, 260);

		SimplePositions[] sp = {nameSP, dateSP};
		
		BufferedImage[] bis = {nameBI, dateBI};
		
		return super.getSaveFile(sp, bis, 0.9f, tmpPath);
	}

}
