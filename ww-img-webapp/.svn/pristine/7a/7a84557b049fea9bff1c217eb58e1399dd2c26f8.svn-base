package com.zb.service.image.genius;

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
import com.zb.service.image.i.FourDraw;

public class ZhangXinTongZhiTool extends BaseTool implements FourDraw{
	public ZhangXinTongZhiTool(StorageService storageService) {
		super(storageService);
	}

	public ZhangXinTongZhiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/581adf0d0cf2faf3c97f2728";
		StorageService storageService = new StorageService();
		ZhangXinTongZhiTool tyt = new ZhangXinTongZhiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","装逼部","5000","6000", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 29;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String three, String four, String tmpBackPic, String count)
			throws IOException {
		//姓名
		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(237-one.length()*17/2).buildVerticalOffset(243);
 		BufferedImage nameBi = drawText(one, zbfont, 17,
 				color, 380, 0, 0, true);
 		
		//部门
		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(520-two.length()*15/2).buildVerticalOffset(238);
 		BufferedImage nameBi2 = drawText(two, zbfont, 15,
 				color, 380, 0, 0, true);

 		//之前薪水
 		SimplePositions nameSP3 = new SimplePositions();
 		nameSP3.buildHorizontalOffset(213).buildVerticalOffset(267);
 		BufferedImage nameBi3 = drawText(three+" RMB/月", zbfont, 26,
 				color, 380, 0, 0, true);
 		
 		//之后薪水
 		SimplePositions nameSP4 = new SimplePositions();
 		nameSP4.buildHorizontalOffset(213).buildVerticalOffset(300);
 		BufferedImage nameBi4 = drawText(four+" RMB/月", zbfont, 26,
 				color, 380, 0, 0, true);
 		//签字
		SimplePositions nameSP5 = new SimplePositions();
 		nameSP5.buildHorizontalOffset(186).buildVerticalOffset(744);
 		BufferedImage nameBi5 = drawText(one, ZbFont.书体坊赵九江钢笔行书, 23,
 				color, 380, 0, 0, true);
 		
	 	// date
		String date = DateUtil.dateFormat(new Date(), "yyyy年MM月dd日");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(212).buildVerticalOffset(358);
		BufferedImage dateBi = drawText(date, zbfont.font(), fontType, 17,
				color, 240, 0, 0, true);
		
		String date2 = DateUtil.dateFormat(new Date(), "yyyy年MM月dd日");
		SimplePositions dateSP2 = new SimplePositions();
		dateSP2.buildHorizontalOffset(180).buildVerticalOffset(818);
		BufferedImage dateBi2 = drawText(date2, zbfont.font(), fontType, 17,
				color, 240, 0, 0, true);
		
		SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSP5, dateSP,dateSP2 };

		BufferedImage[] bis = { nameBi,nameBi2,nameBi3,nameBi4,nameBi5, dateBi ,dateBi2 };

		return super.getSaveFile(sp, bis, 0.8f, tmpBackPic);
	}

}
