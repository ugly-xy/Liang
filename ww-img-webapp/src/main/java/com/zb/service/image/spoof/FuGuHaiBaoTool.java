package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.ThreeDraw;

public class FuGuHaiBaoTool extends BaseTool implements ThreeDraw{
	public FuGuHaiBaoTool(StorageService storageService) {
		super(storageService);
	}

	public FuGuHaiBaoTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/576b94700cf273bdeaaca0f0";
		StorageService storageService = new StorageService();
		FuGuHaiBaoTool tyt = new FuGuHaiBaoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装逼学","装逼学","呵呵", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.隶书;
	static int fontSize = 25;
	static Color color = new Color(127, 49, 29);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String two, String three, String tmpPath,String count) throws IOException {
		
		one = one + ",不对"+two+"表白,就快回来"+three+"!";
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(25).buildVerticalOffset(333);
		BufferedImage nameBI = drawText(one,zbfont.font(),fontType,fontSize, color,
				600, 0, 0, true);	
		
		SimplePositions[] sp = { nameSP };

		BufferedImage[] bis = { nameBI};

		return super.getSaveFile(sp, bis, 0.9f, tmpPath);
	}
	
}
