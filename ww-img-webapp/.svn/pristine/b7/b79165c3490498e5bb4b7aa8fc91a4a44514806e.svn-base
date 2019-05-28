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

public class BaiDuSouSuoTool extends BaseTool implements TwoDraw{
	public BaiDuSouSuoTool(StorageService storageService) {
		super(storageService);
	}

	public BaiDuSouSuoTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/577a12470cf26acda3d9208c";
		StorageService storageService = new StorageService();
		BaiDuSouSuoTool tyt = new BaiDuSouSuoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("最有趣的装逼神器是什么？","bibi-装逼神器", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.宋体;
	static int fontSize = 18;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.TYPE1_FONT;
	
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		// 问句
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(25).buildVerticalOffset(54);
		BufferedImage nameBI = drawText(one, zbfont, fontSize, color,
				400, 0, 0, true);
		
		int fontSize2 =16;
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(130).buildVerticalOffset(98);
		BufferedImage nameBI2 = drawText(two, zbfont.font(),fontType, fontSize2, new Color(255,0,0),
				240, 0, 0, true);
		
		SimplePositions[] sp = { nameSP, nameSP2 };

		BufferedImage[] bis = { nameBI, nameBI2 };

		return super.getSaveFile(sp, bis, 0.8f, tmpBackPic);
	}

}
