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

public class FengYouJingTaoTaoTool extends BaseTool implements TwoDraw{
	public FengYouJingTaoTaoTool(StorageService storageService) {
		super(storageService);
	}

	public FengYouJingTaoTaoTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57b55e030cf234b87f8c987f";
		StorageService storageService = new StorageService();
		FengYouJingTaoTaoTool tyt = new FengYouJingTaoTaoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装逼点啊","男人女人",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 28;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//姓名
 		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(269).buildVerticalOffset(310);
 		BufferedImage nameBi = drawText(one+"，用了它，你就能感觉到全世界的"+two+"都来过！", zbfont.font(),fontType, fontSize,
 				color, 440, 500, -0.16, true,50,50);
 		
 		SimplePositions[] sp = { nameSP};

		BufferedImage[] bis = { nameBi};

		return super.getSaveFile(sp, bis, 0.8f, tmpBackPic);
	}

}
