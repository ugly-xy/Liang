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
import com.zb.service.image.i.TwoDraw;

public class ChengDuLiGongDaXueZiKaoTool extends BaseTool implements TwoDraw {
	public ChengDuLiGongDaXueZiKaoTool(StorageService storageService) {
		super(storageService);
	}

	public ChengDuLiGongDaXueZiKaoTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		// String tmpPath0 =
		// "http://imgzb.zhuangdianbi.com/5732a2a80cf2d5342be19ca8";
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/576b4bb80cf2c50f87444d9f";
		StorageService storageService = new StorageService();
		ChengDuLiGongDaXueZiKaoTool tyt = new ChengDuLiGongDaXueZiKaoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","装逼学院", tmpPath0,null));
	}

	static ZbFont zbfont = ZbFont.宋体;
	static int fontSize = 12;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	
	@Override
	public String draw(String one, String two, String tmpPath,String count) throws IOException {
		//姓名
		ZbFont zbfont2 = ZbFont.宋体;
		int fontSize2 = 16;
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(600).buildVerticalOffset(345);
		BufferedImage nameBI = drawText(one,zbfont2.font(),fontType,fontSize2, color,
				240, 0, 0, true);
		
		//专业名字
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(590).buildVerticalOffset(397);
		BufferedImage nameBI2 = drawText(two, zbfont, fontSize, color,
				240, 0, 0, true);
		
		// date
		String date = DateUtil.dateFormat(new Date(), "yyyy  MM  dd");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(805).buildVerticalOffset(538);
		BufferedImage dateBi = drawText(date, zbfont.font(), fontType, fontSize,
				color, 240, 0, 0, true);
		
		SimplePositions[] sp = { nameSP,nameSP2, dateSP };

		BufferedImage[] bis = { nameBI,nameBI2, dateBi };

		return super.getSaveFile(sp, bis, 0.9f, tmpPath);
	}
	
	
}
