package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Random;


import com.zb.service.cloud.StorageService;

import net.coobird.thumbnailator.Thumbnails;

public class CarOrderTool extends BaseTool {

	public static void main(String[] args) throws IOException {
//		String tmpPath = "http://imgzb.zhuangdianbi.com/56ea272393b056189a94cb44";
		String tmpPath = "http://imgzb.zhuangdianbi.com/56ea27cf93b056189a94cb4a";
//		String tmpPath = "http://imgzb.zhuangdianbi.com/56ea2af593b056189a94cb4d";

		if (tmpPath.contains("?")) {
			tmpPath = tmpPath.substring(0, tmpPath.indexOf("?"));
		}
		StorageService storageService = new StorageService();
		CarOrderTool tyt = new CarOrderTool(storageService);
		System.out.println(tyt.drawImg("赵鹏","保时捷", tmpPath));
		

	}
	
	public CarOrderTool(StorageService storageService) {
		super(storageService);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * 获取文字效果信息，位置，大小，颜色
	 * @param type
	 * @return x,y,size,颜色三原色码
	 */
	private int[] getPosition(String type){
		if("法拉利".equalsIgnoreCase(type)){
			int[] x = { 309, 819, 20, 304, 856 };
			return x;
		}else if("奔驰".equalsIgnoreCase(type)){
			int[] x = { 127, 888, 20, 106, 918 };
			return x;
		}else if("保时捷".equalsIgnoreCase(type)){
			int[] x = { 318, 839, 20, 418, 849 };
			return x;
		}
		return null;
	}
	
	
	/***
	 * 
	 * @param name
	 *            姓名
	 * @param tmpPath
	 * @return
	 * @throws IOException
	 */
	public String drawImg(String name, String type, String tmpPath) throws IOException {
		int[] p = this.getPosition(type);
		int left = p[0], height = p[1], fontSize = p[2];
		String[] fontStyles = {"罗西钢笔行楷","徐金如硬笔行楷X","新蒂下午茶基本版"};
		String nameStyle = fontStyles[new Random().nextInt(3)];
		String numberStyle = fontStyles[new Random().nextInt(2)];
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(left).buildVerticalOffset(height);
		BufferedImage nameBI = drawText(name, nameStyle, Font.BOLD, fontSize, new Color(101,96,88), 260);

		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		String date = "" + year + (month < 10 ? "-0" + month : "-" + month) + (day < 10 ? "-0" + day : "-" + day);
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(p[3]).buildVerticalOffset(p[4]);
		BufferedImage dateBI = drawText(date, numberStyle, Font.BOLD, fontSize, new Color(101,96,88), 260);
		InputStream in = null;
		BufferedImage bi = null;
		try{
			in = super.getFile(tmpPath);
			bi = Thumbnails.of(in).scale(1.0d).watermark(nameSP, nameBI, 1.0f)
				.watermark(dateSP, dateBI, 1.0f)
				.outputQuality(1.0d).outputFormat("png").asBufferedImage();
		} finally {
			if(in != null){
				in.close();
			}
		}
		return super.saveFile(bi, getDefFormatName());
	}

}
