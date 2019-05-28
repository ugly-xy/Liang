package com.zb.service.image.certificate;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.ThreeDraw;

public class ShangHaiJiaoTongTool extends BaseTool implements ThreeDraw{
	
	public ShangHaiJiaoTongTool(StorageService storageService) {
		super(storageService);
	}

	public ShangHaiJiaoTongTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		// String tmpPath0 =
		// "http://imgzb.zhuangdianbi.com/5732a2a80cf2d5342be19ca8";
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/576b4a4a0cf2c50f87444cc3";
		StorageService storageService = new StorageService();
		ShangHaiJiaoTongTool tyt = new ShangHaiJiaoTongTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","装逼学院","装逼专业", tmpPath0,null));
	}

	static ZbFont zbfont = ZbFont.宋体;
	static int fontSize = 15;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	
	@Override
	public String draw(String one, String two, String three, String tmpPath,String count) throws IOException {
		//姓名
		ZbFont zbfont2 = ZbFont.宋体;
		int fontSize2 = 16;
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(47).buildVerticalOffset(120);
		BufferedImage nameBI = drawText(one,zbfont2.font(),fontType,fontSize2, color,
				240, 0, 0, true);
		
		//学院+专业名字
		int fontSize3 = 15;
		two = "祝贺你被我校"+two+"学院 "+three+"专业录取！";
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(75).buildVerticalOffset(150);
		BufferedImage nameBI2 = drawText(two, zbfont, fontSize3, new Color(47,26,23),
				600, 0, 0, true);
		
		
		// date
		String date = DateUtil.dateFormat(new Date(), "yyyy  9  2");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(104).buildVerticalOffset(171);
		BufferedImage dateBi = drawText(date, zbfont.font(), fontType, fontSize,
				new Color(47,26,23), 240, 0, 0, true);
		
		String date2 = DateUtil.dateFormat(new Date(), "yyyy   M  d");
		SimplePositions dateSP2 = new SimplePositions();
		dateSP2.buildHorizontalOffset(370).buildVerticalOffset(346);
		BufferedImage dateBi2 = drawText(date2, zbfont.font(), fontType, fontSize,
				new Color(47,26,23), 240, 0, 0, true);
		
		SimplePositions[] sp = { nameSP,nameSP2, dateSP,dateSP2 };

		BufferedImage[] bis = { nameBI,nameBI2, dateBi ,dateBi2 };

		return super.getSaveFile(sp, bis, 0.9f, tmpPath);
	}

}
