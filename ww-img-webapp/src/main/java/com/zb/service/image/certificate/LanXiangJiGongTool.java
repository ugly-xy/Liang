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

public class LanXiangJiGongTool extends BaseTool implements ThreeDraw {

	public LanXiangJiGongTool(StorageService storageService) {
		super(storageService);
	}

	public LanXiangJiGongTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		// String tmpPath0 =
		// "http://imgzb.zhuangdianbi.com/5732a2a80cf2d5342be19ca8";
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/576a734a0cf280ab0cd92857";
		StorageService storageService = new StorageService();
		LanXiangJiGongTool tyt = new LanXiangJiGongTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","装逼学院","装逼", tmpPath0,null));
		
		
	}
	
	static ZbFont zbfont = ZbFont.华文新魏加粗;
	static int fontSize = 18;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one,String two,String three, String tmpPath,String count) throws IOException {
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(110).buildVerticalOffset(180);
		BufferedImage nameBI = drawText(one,zbfont, fontSize, color,
				240, 0, 0, true);
		
		//学院名字
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(275).buildVerticalOffset(210);
		BufferedImage nameBI2 = drawText(two, zbfont, fontSize, color,
				240, 0, 0, true);
		
		//专业
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(150).buildVerticalOffset(240);
		BufferedImage nameBI3 = drawText(three, zbfont, fontSize, color,
				240, 0, 0, true);
		
		//颁发时间
		String date =  DateUtil.dateFormat(new Date(), "yyyy年MM月dd日");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(330).buildVerticalOffset(350);
		BufferedImage dateBi = drawText(date,zbfont.font(),fontType, fontSize, 
				color, 240,0, 0, true);
		
		SimplePositions[] sp = { nameSP,nameSP2,nameSP3,dateSP};

		BufferedImage[] bis = { nameBI,nameBI2,nameBI3,dateBi };

		return super.getSaveFile(sp, bis, 0.6f, tmpPath);
	}

}
