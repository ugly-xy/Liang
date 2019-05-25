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

public class TianKongBiaoBaiTool extends BaseTool implements TwoDraw{
	public TianKongBiaoBaiTool(StorageService storageService) {
		super(storageService);
	}

	public TianKongBiaoBaiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/577b2dc20cf267a801c85e14";
		StorageService storageService = new StorageService();
		TianKongBiaoBaiTool tyt = new TianKongBiaoBaiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("都比","I Love You", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.翩翩体简;
	static int fontSize = 30;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//名字
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(156).buildVerticalOffset(45);
		BufferedImage nameBI = drawText(one, zbfont.font(),fontType, fontSize, color,
				180, 0, 0, true);
		
		//内容
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(160).buildVerticalOffset(83);
		BufferedImage nameBI2 = drawText(two, zbfont.font(),fontType, fontSize, color,
				180, 0, 0, true);
		
		SimplePositions[] sp = {nameSP,nameSP2 };

		BufferedImage[] bis = { nameBI,nameBI2 };

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
