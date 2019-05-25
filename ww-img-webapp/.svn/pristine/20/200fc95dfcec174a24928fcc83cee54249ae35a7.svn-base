package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;

import com.zb.service.cloud.StorageService;

public class QianShuiZhengTool extends BaseTool {

	public static void main(String[] args) throws IOException {
		String tmpPath = "http://imgzb.zhuangdianbi.com/56f0f16693b071409286d7f5";

		if (tmpPath.contains("?")) {
			tmpPath = tmpPath.substring(0, tmpPath.indexOf("?"));
		}
		StorageService storageService = new StorageService();
		QianShuiZhengTool tyt = new QianShuiZhengTool(storageService);
		System.out.println(tyt.drawImg("赵鹏","zhaopeng", tmpPath));
		

	}
	
	public QianShuiZhengTool(StorageService storageService) {
		super(storageService);
		// TODO Auto-generated constructor stub
	}
	
	
	
	/***
	 * 
	 * @param name
	 *            姓名
	 * @param tmpPath
	 * @return
	 * @throws IOException
	 */
	public String drawImg(String name, String pingyin, String tmpPath) throws IOException {
		String fontStyle = "微软雅黑";
		int fontSize = 20;
		Color color = new Color(24,29,28);
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(78).buildVerticalOffset(67);
		BufferedImage nameBI = drawText(name, fontStyle, Font.BOLD, fontSize, color, 260);
		
		String f = pingyin.substring(0,1).toUpperCase();
		String en = f + pingyin.substring(1);
		SimplePositions enSP = new SimplePositions();
		enSP.buildHorizontalOffset(329).buildVerticalOffset(68);
		BufferedImage enBI = drawText(en, fontStyle, Font.BOLD, fontSize, color, 260);
		
		//日期
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		String date = "" + year + (month < 10 ? ".0" + month : "." + month)
				+ (day < 10 ? ".0" + day : "." + day);
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(428).buildVerticalOffset(92);
		BufferedImage dateBI = drawText(date, fontStyle, Font.BOLD, fontSize,
				color, 260);
		SimplePositions[] sp = {nameSP, enSP, dateSP};
		
		BufferedImage[] bis = {nameBI, enBI, dateBI};
		
		return super.getSaveFile(sp, bis, 0.9f, tmpPath);
	}

}
