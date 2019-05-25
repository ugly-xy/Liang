package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class ZhiBoZiGeTool extends BaseTool implements OneDraw{
	public ZhiBoZiGeTool(StorageService storageService) {
		super(storageService);
	}

	public ZhiBoZiGeTool() {
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57f89feb0cf21bf18cd747f4";
		StorageService storageService = new StorageService();
		ZhiBoZiGeTool tyt = new ZhiBoZiGeTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("娱乐社区",tmpPath0,  
				null));
	}
	
	static ZbFont zbfont = ZbFont.隶书;
	static int fontSize = 28;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//姓名
 		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(56).buildVerticalOffset(325);
 		BufferedImage nameBi = drawText(one+"：", zbfont, fontSize,
 				color, 600, 100, 0, true);
 		
 		SimplePositions[] sp = {nameSP};

		BufferedImage[] bis = {nameBi};
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
