package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class ShangMenNvXuTool  extends BaseTool implements TwoDraw{
	public ShangMenNvXuTool(StorageService storageService) {
		super(storageService);
	}

	public ShangMenNvXuTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5845241e0cf24a945fcef233";
		StorageService storageService = new StorageService();
		ShangMenNvXuTool tyt = new ShangMenNvXuTool(storageService);
		tyt.isDebug(true);
		System.out
				.println(tyt.draw("装点逼","装点逼",tmpPath0,null));//10
	}
	
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 24;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		
		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(104).buildVerticalOffset(85);
 		BufferedImage nameBi = drawText(one, zbfont, fontSize,
 				color, 800, 100, 0, true);
 		
 		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(104).buildVerticalOffset(123);
 		BufferedImage nameBi2 = drawText(two, zbfont, fontSize,
 				color, 800, 100, 0, true);
 		
 		SimplePositions nameSP3 = new SimplePositions();
 		nameSP3.buildHorizontalOffset(482).buildVerticalOffset(785);
 		BufferedImage nameBi3 = drawText(one, zbfont, fontSize,
 				color, 800, 100, 0, true);
 		
 		SimplePositions nameSP4 = new SimplePositions();
 		nameSP4.buildHorizontalOffset(482).buildVerticalOffset(828);
 		BufferedImage nameBi4 = drawText(two, zbfont, fontSize,
 				color, 800, 100, 0, true);
 		
 		
 		SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4};

		BufferedImage[] bis = {nameBi,nameBi2,nameBi3,nameBi4};

		return super.getSaveFile(sp, bis, 0.7f, tmpBackPic);
	}

}
