package com.zb.service.image.certificate;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class FuDanDaXueTool  extends BaseTool implements TwoDraw {

	public FuDanDaXueTool(StorageService storageService) {
		super(storageService);
	}

	public FuDanDaXueTool() {
		super();
	}
	

	public static void main(String[] args) throws IOException {
		// String tmpPath0 =
		// "http://imgzb.zhuangdianbi.com/5732a2a80cf2d5342be19ca8";
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/576a78150cf280ab0cd92df8";
		StorageService storageService = new StorageService();
		FuDanDaXueTool tyt = new FuDanDaXueTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","装点逼睡你麻痹起来装逼", tmpPath0,null));
		
		
	}
	
	static ZbFont zbfont = ZbFont.宋体;
	static int fontSize = 14;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	
	@Override
	public String draw(String one, String two, String tmpPath,String count) throws IOException {
		// 姓名
		int fontSize2 = 16;
		one = one + " 同学：";
		ZbFont zbfont2 = ZbFont.黑体加粗;
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(42).buildVerticalOffset(167);
		BufferedImage nameBI = drawText(one, zbfont2.font(),fontType, fontSize2, color,
				240, 0, 0, true);
		two = "你被我校 "+two+" 专业录取，请于2016年9月1日凭此证前来报道。";
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(118).buildVerticalOffset(195);
		BufferedImage nameBI2 = drawText(two, zbfont, fontSize, color,
				520, 0, 0, true);
		
		SimplePositions[] sp = { nameSP,nameSP2};

		BufferedImage[] bis = { nameBI,nameBI2 };

		return super.getSaveFile(sp, bis, 0.7f, tmpPath);
	}

}
