package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;


public class JianQiaoTool extends BaseTool {

	public JianQiaoTool(StorageService storageService) {
		super(storageService);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		String tmpPath = "http://imgzb.zhuangdianbi.com/56f38c5693b055c5f4a88775";
		if (tmpPath.contains("?")) {
			tmpPath = tmpPath.substring(0, tmpPath.indexOf("?"));
		}
		String pic = "/16/0315/13/small23.png";
		StorageService storageService = new StorageService(); 
		JianQiaoTool tyt = new JianQiaoTool(storageService);
		System.out.println(tyt.drawImg("苍井空", "cangjingkong", "技术研发部", pic, tmpPath));
	}

	/***
	 * @param name
	 *            姓名
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String name, String pingyin, String gangwei, String pic, String tmpPath) throws IOException {
		
		int fontSize = 16;
		Color color = new Color(99,94,91);
		String fontStyle = "微软雅黑";
		int fontType = Font.PLAIN;
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(129).buildVerticalOffset(154);
		BufferedImage nameBI = drawText(name, fontStyle, fontType, fontSize,
				color, 260);
		//英文名
		String f = pingyin.substring(0,1).toUpperCase();
		String en = f + pingyin.substring(1);
		SimplePositions pingyinSP = new SimplePositions();
		pingyinSP.buildHorizontalOffset(127).buildVerticalOffset(182);
		BufferedImage pingyinBI = drawText(en, fontStyle, fontType, fontSize,
				color, 260);
		
		//岗位
		SimplePositions gangweiSP = new SimplePositions();
		gangweiSP.buildHorizontalOffset(352).buildVerticalOffset(228);
		BufferedImage gangweiBI = drawText(gangwei, fontStyle, fontType, fontSize,
				color, 260);
		//头像
		SimplePositions imageSP = new SimplePositions();
		imageSP.buildHorizontalOffset(536).buildVerticalOffset(82);
		BufferedImage imageBI = compressImage(pic, 99, 126);
		//日期
		String date = DateUtil.dateFormatShortCN(new Date());
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(283).buildVerticalOffset(385);
		BufferedImage dateBI = drawText(date, fontStyle, fontType, fontSize,
				color, 260);

		SimplePositions[] sp = {nameSP, dateSP, pingyinSP, gangweiSP, imageSP};
		
		BufferedImage[] bis = {nameBI, dateBI, pingyinBI, gangweiBI, imageBI};
		
		return super.getSaveFile(sp, bis, 0.9f, tmpPath);
	}

}
