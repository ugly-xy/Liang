package com.zb.service.image.spoof;

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

public class PinYongJingJiRenTool extends BaseTool implements TwoDraw{
	public PinYongJingJiRenTool(StorageService storageService) {
		super(storageService);
	}

	public PinYongJingJiRenTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57b30b1f0cf26738f702e80b";
		StorageService storageService = new StorageService();
		PinYongJingJiRenTool tyt = new PinYongJingJiRenTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("逼","卖",tmpPath0,null));//480 434 
	}
	
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 20;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		
		//姓名
 		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(192).buildVerticalOffset(160);
 		BufferedImage nameBi = drawText(one, zbfont, fontSize,
 				color, 800, 100, -0.18, true);
 		
 		
 		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(425).buildVerticalOffset(218);
 		BufferedImage nameBi2 = drawText(two, ZbFont.钟齐陈伟勋硬笔行书字库, 23,
 				color, 800, 100, -0.2, true);
 		
 	// date
		String date = DateUtil.dateFormat(new Date(), "yyyy.M.d");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(385).buildVerticalOffset(224);
		BufferedImage dateBi = drawText(date, zbfont, 23,
				color, 240, 100, -0.2, true);
		
		SimplePositions[] sp = {nameSP,nameSP2,dateSP };

		BufferedImage[] bis = { nameBi,nameBi2,dateBi };

		return super.getSaveFile(sp, bis, 0.8f, tmpBackPic);
 		
	}

}
