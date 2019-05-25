package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;

import com.zb.service.cloud.StorageService;

public class JieHunZhengTool extends BaseTool {

	public static void main(String[] args) throws IOException {
		String tmpPath = "http://imgzb.zhuangdianbi.com/56f0ed2293b071409286d7ef";

		if (tmpPath.contains("?")) {
			tmpPath = tmpPath.substring(0, tmpPath.indexOf("?"));
		}
		StorageService storageService = new StorageService();
		JieHunZhengTool tyt = new JieHunZhengTool(storageService);
		System.out.println(tyt.drawImg("鲍金莹", tmpPath));

	}
	
	public JieHunZhengTool(StorageService storageService) {
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
	public String drawImg(String name, String tmpPath) throws IOException {
		String fontStyle = "仿宋";
		int fontSize = 32;
		Color color = new Color(122,108,97);
		
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(292).buildVerticalOffset(160);
		BufferedImage nameBI = drawText(name, fontStyle, Font.PLAIN, fontSize, color, 260);
		
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		String date = "" + year + (month < 10 ? ".0" + month : "." + month) + (day < 10 ? ".0" + day : "." + day);
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(280).buildVerticalOffset(277);
		BufferedImage dateBI = drawText(date, fontStyle, Font.PLAIN, fontSize, color, 260);

		SimplePositions[] sp = {nameSP, dateSP};
		
		BufferedImage[] bis = {nameBI, dateBI};
		
		return super.getSaveFile(sp, bis, 0.9f, tmpPath);
	}

}
