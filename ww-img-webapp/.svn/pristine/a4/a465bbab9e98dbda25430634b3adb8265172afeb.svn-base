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

public class HaErBinFoXueYuanTool  extends BaseTool implements TwoDraw {

	public HaErBinFoXueYuanTool(StorageService storageService) {
		super(storageService);
	}

	public HaErBinFoXueYuanTool() {
		super();
	}
	

	public static void main(String[] args) throws IOException {
		// String tmpPath0 =
		// "http://imgzb.zhuangdianbi.com/5732a2a80cf2d5342be19ca8";
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/576a728d0cf280ab0cd92747";
		StorageService storageService = new StorageService();
		HaErBinFoXueYuanTool tyt = new HaErBinFoXueYuanTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","装点逼", tmpPath0,null));
		
		
	}
	
	static ZbFont zbfont = ZbFont.游狼近草体简;
	static int fontSize = 18;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	
	@Override
	public String draw(String one, String two, String tmpPath,String count) throws IOException {
		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(80).buildVerticalOffset(177);
		BufferedImage nameBI = drawText(one, zbfont.font(),fontType, fontSize, color,
				240, 0, 0, true);
		//专业
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(520).buildVerticalOffset(177);
		BufferedImage nameBI2 = drawText(two,zbfont.font(),fontType, fontSize, color,
				240, 0, 0, true);
		
		SimplePositions[] sp = { nameSP,nameSP2};

		BufferedImage[] bis = { nameBI,nameBI2 };

		return super.getSaveFile(sp, bis, 0.9f, tmpPath);
			
	}
}
