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

public class ZhaoAvNanZhuTool extends BaseTool implements TwoDraw{
	public ZhaoAvNanZhuTool(StorageService storageService) {
		super(storageService);
	}

	public ZhaoAvNanZhuTool() {
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57a5999b0cf2ecedbd25cc42";
		StorageService storageService = new StorageService();
		ZhaoAvNanZhuTool tyt = new ZhaoAvNanZhuTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("BIBI","1234567890", tmpPath0,  //
				null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 18;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(80).buildVerticalOffset(444);
		BufferedImage nameBI = drawText("联系人:"+one, zbfont, fontSize, color,
				600, 50, 0.05, true,5,5);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(78).buildVerticalOffset(484);
		BufferedImage nameBI2 = drawText("联系QQ:"+two, zbfont, fontSize, color,
				600, 100, 0.05, true,5,5);
		SimplePositions[] sp = { nameSP,nameSP2};
		
		BufferedImage[] bis = { nameBI,nameBI2};
		
		return super.getSaveFile(sp, bis, 0.8f, tmpBackPic);
	}

}
