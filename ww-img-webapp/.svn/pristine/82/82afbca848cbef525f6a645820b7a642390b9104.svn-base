package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;

import com.zb.service.cloud.StorageService;

public class XingshiZhengTool extends BaseTool {

	public static void main(String[] args) throws IOException {
		String tmpPath = "http://imgzb.zhuangdianbi.com/56e951d293b01f25b8c13689";
//		String tmpPath = "http://imgzb.zhuangdianbi.com/56e9516393b01f25b8c13683";
//		 String tmpPath = "http://imgzb.zhuangdianbi.com/56e951a093b01f25b8c13686";
//		 String tmpPath = "/16/0315/18/1458039250494627.png?v=1458039289934";
//		 String tmpPath = "/16/0315/14/1458022119868138.png?v=1458022127156";
//		String tmpPath = "/16/0315/14/1458022048784486.png?v=1458022057504";
		if (tmpPath.contains("?")) {
			tmpPath = tmpPath.substring(0, tmpPath.indexOf("?"));
		}
		StorageService storageService = new StorageService();
		XingshiZhengTool tyt = new XingshiZhengTool(storageService);
		System.out.println(tyt.drawImg("赵鹏","兰博基尼", tmpPath));

	}
	
	public XingshiZhengTool(StorageService storageService) {
		super(storageService);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * 获取文字效果信息，位置，大小，颜色
	 * @param type
	 * @return x,y,size,颜色三原色码
	 */
	private int[] getPosition(String type){
		if("奥迪".equalsIgnoreCase(type)){
			int[] x = { 204, 625, 16, 293,771 };
			return x;
		}else if("迈巴赫".equalsIgnoreCase(type)){
			int[] x = { 204, 800, 20, 318, 985 };
			return x;
		}else if("宾利".equalsIgnoreCase(type)){
			int[] x = { 222, 681, 16, 308, 813 };
			return x;
		}else if("卡宴".equalsIgnoreCase(type)){
			int[] x = { 278, 632, 16, 370,776 };
			return x;
		}else if("揽胜".equalsIgnoreCase(type)){
			int[] x = { 252, 649, 16, 345, 800 };
			return x;
		}else if("兰博基尼".equalsIgnoreCase(type)){
			int[] x = { 274, 646, 16, 363, 793 };
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
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(left).buildVerticalOffset(height);
		BufferedImage nameBI = drawText(name, "宋体", Font.BOLD, fontSize, new Color(71, 81, 89), 260);

		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		String date = "" + year + (month < 10 ? "-0" + month : "-" + month) + (day < 10 ? "-0" + day : "-" + day);
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(p[3]).buildVerticalOffset(p[4]);
		BufferedImage dateBI = drawText(date, "Colonel Regular", Font.BOLD + Font.ITALIC, fontSize, new Color(71, 81, 89), 260);
		SimplePositions[] sp = {nameSP, dateSP};
		
		BufferedImage[] bis = {nameBI, dateBI};
		
		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
