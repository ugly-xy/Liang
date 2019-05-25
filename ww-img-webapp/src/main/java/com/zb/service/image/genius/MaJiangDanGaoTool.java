package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class MaJiangDanGaoTool extends BaseTool implements TwoDraw{
	public MaJiangDanGaoTool(StorageService storageService) {
		super(storageService);
	}

	public MaJiangDanGaoTool() {
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57fb346d0cf24bc79b620a22";
		StorageService storageService = new StorageService();
		MaJiangDanGaoTool tyt = new MaJiangDanGaoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装逼","逢赌必赢",tmpPath0,  
				null));
	}
	
	static ZbFont zbfont = ZbFont.楷体常规;
	static int fontSize = 22;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//姓名
 		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(250-one.length()*fontSize/2).buildVerticalOffset(214);
 		BufferedImage nameBi = drawText(one, zbfont, fontSize,
 				color, 600, 100, -0.08, true);
 		
 		//祝福语
 		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(250-two.length()*fontSize/2).buildVerticalOffset(238);
 		BufferedImage nameBi2 = drawText(two, zbfont, fontSize,
 				color, 600, 100, -0.08, true);
 		
 		SimplePositions[] sp = {nameSP,nameSP2};

		BufferedImage[] bis = {nameBi,nameBi2};
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
