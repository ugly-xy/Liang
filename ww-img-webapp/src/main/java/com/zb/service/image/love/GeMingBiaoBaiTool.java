package com.zb.service.image.love;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class GeMingBiaoBaiTool extends BaseTool implements TwoDraw{
	public GeMingBiaoBaiTool(StorageService storageService) {
		super(storageService);
	}

	public GeMingBiaoBaiTool() {
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57fa29750cf26c8a8b8c85b2";
		StorageService storageService = new StorageService();
		GeMingBiaoBaiTool tyt = new GeMingBiaoBaiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("娱乐社区","逼格",tmpPath0,  
				null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 13;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one,String two, String tmpBackPic, String count) throws IOException {
		//昵称
 		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(84).buildVerticalOffset(4);
 		BufferedImage nameBi = drawText(one, zbfont, fontSize,
 				new Color(255,255,255), 600, 100, 0, true);
 		
 		//姓名
 		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(2).buildVerticalOffset(212);
 		BufferedImage nameBi2 = drawText(two, zbfont, 14,
 				new Color(255,0,0), 600, 100, 0, true);
 		
 		SimplePositions[] sp = {nameSP,nameSP2};

		BufferedImage[] bis = {nameBi,nameBi2};
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
