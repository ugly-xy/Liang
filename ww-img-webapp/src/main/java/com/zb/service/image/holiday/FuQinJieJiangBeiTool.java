package com.zb.service.image.holiday;

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

public class FuQinJieJiangBeiTool extends BaseTool implements TwoDraw{
	
	public FuQinJieJiangBeiTool(StorageService storageService) {
		super(storageService);
	}

	public FuQinJieJiangBeiTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		// String tmpPath0 =
		// "http://imgzb.zhuangdianbi.com/5732a2a80cf2d5342be19ca8";
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5764dc510cf2cb9eefcc01cf";
		StorageService storageService = new StorageService();
		FuQinJieJiangBeiTool tyt = new FuQinJieJiangBeiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","装逼",tmpPath0,null));
	}
	
	
	@Override
	public String draw(String one, String two, String tmpPath,String count) throws IOException {

		int fontSize = 18;
		Color color = new Color(255, 255, 255);
		String fontStyle = ZbFont.华文黑体.font();
		int fontType = Font.BOLD;
		
		
		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(285).buildVerticalOffset(390);
		BufferedImage nameBI = drawText(one, ZbFont.华文黑体, fontSize =24 , color,
				240, 0, 0, true);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(472).buildVerticalOffset(390);
		BufferedImage nameBI2 = drawText(two,ZbFont.华文黑体,  fontSize =24, color,
				240, 0, 0, true);

		// date
		String date = DateUtil.dateFormat(new Date(), "yyyy年M月d日");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(320).buildVerticalOffset(515);
		BufferedImage dateBi = drawText(date, fontStyle, fontType, fontSize=16,
				color, 240, 0, 0, true);
		
		SimplePositions[] sp = { nameSP,nameSP2, dateSP };

		BufferedImage[] bis = { nameBI,nameBI2, dateBi };

		return super.getSaveFile(sp, bis, 0.95f, tmpPath);
	}
	
}
