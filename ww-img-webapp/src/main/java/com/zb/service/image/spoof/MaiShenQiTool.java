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

public class MaiShenQiTool extends BaseTool implements TwoDraw{
	public MaiShenQiTool(StorageService storageService) {
		super(storageService);
	}

	public MaiShenQiTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57ad76360cf23ddb0667920c";
		StorageService storageService = new StorageService();
		MaiShenQiTool tyt = new MaiShenQiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点","装点你",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 30;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
 		//姓名1
 		SimplePositions nameSP4 = new SimplePositions();
 		nameSP4.buildHorizontalOffset(280-one.length()*28/2).buildVerticalOffset(200);
 		BufferedImage nameBi4 = drawText(one, ZbFont.新蒂小丸子小学版, 28,
 				color, 240, 100, 0, true);
 		
 		//姓名1
 		SimplePositions nameSP5 = new SimplePositions();
 		nameSP5.buildHorizontalOffset(342-one.length()*28/2).buildVerticalOffset(483);
 		BufferedImage nameBi5 = drawText(one, ZbFont.新蒂小丸子小学版, 28,
 				color, 240, 100, 0, true);
 		
 		//姓名2
 		SimplePositions nameSP6 = new SimplePositions();
 		nameSP6.buildHorizontalOffset(234-two.length()*28/2).buildVerticalOffset(248);
 		BufferedImage nameBi6 = drawText(two, ZbFont.新蒂小丸子小学版, 28,
 				color, 240, 100, 0, true);
 		//姓名2
 		SimplePositions nameSP7 = new SimplePositions();
 		nameSP7.buildHorizontalOffset(342-two.length()*28/2).buildVerticalOffset(565);
 		BufferedImage nameBi7 = drawText(two, ZbFont.新蒂小丸子小学版, 28,
 				color, 240, 100, 0, true);
 	
 		
 		
	 	// date
		String date = DateUtil.dateFormat(new Date(), "yyyy年M月d日");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(137).buildVerticalOffset(668);
		BufferedImage dateBi = drawText(date, zbfont, fontSize,
				color, 240, 0, 0, true);
 		
 		SimplePositions[] sp = {dateSP,nameSP4,nameSP5,nameSP6,nameSP7};

		BufferedImage[] bis = {dateBi,nameBi4,nameBi5,nameBi6,nameBi7};

		return super.getSaveFile(sp, bis, 0.9f, tmpBackPic);
	}

}
