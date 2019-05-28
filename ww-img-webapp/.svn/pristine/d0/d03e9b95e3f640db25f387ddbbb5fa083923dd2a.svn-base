package com.zb.service.image.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class LolQuanPiFuTool extends BaseTool implements TwoDraw{
	public LolQuanPiFuTool(StorageService storageService) {
		super(storageService);
	}

	public LolQuanPiFuTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		// String tmpPath0 =
		// "http://imgzb.zhuangdianbi.com/5732a2a80cf2d5342be19ca8";
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/576cb6680cf24fe2e4ecef76";
		StorageService storageService = new StorageService();
		LolQuanPiFuTool tyt = new LolQuanPiFuTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","比尔吉沃特", tmpPath0,null));
	}

	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 10;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	
	@Override
	public String draw(String one, String two, String tmpPath,String count) throws IOException {
		//游戏ID
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(40).buildVerticalOffset(32);
		BufferedImage nameBI = drawText(one,zbfont,fontSize, color,
				240, 0, 0, true);
		
		//大区名字
		int fontSize2 = 10;
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(108).buildVerticalOffset(2);
		BufferedImage nameBI2 = drawText(two, zbfont.font(),fontType, fontSize2, color,
				240, 0, 0, true);
		
		SimplePositions[] sp = { nameSP,nameSP2};

		BufferedImage[] bis = { nameBI,nameBI2};

		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}
}
