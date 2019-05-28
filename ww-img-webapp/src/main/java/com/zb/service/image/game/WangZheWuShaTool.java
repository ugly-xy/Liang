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

public class WangZheWuShaTool extends BaseTool implements TwoDraw {
	public WangZheWuShaTool(StorageService storageService) {
		super(storageService);
	}

	public WangZheWuShaTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57c8267f0cf2ac844efa870d";
		StorageService storageService = new StorageService();
		WangZheWuShaTool tyt = new WangZheWuShaTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("BIBI","ceshi.png", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 17;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//游戏ID
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(82).buildVerticalOffset(376);
		BufferedImage nameBI = drawText(one,zbfont.font(),fontType,fontSize, color,
				240, 0, 0, true);
		
		//玩家照片
		BufferedImage p = super.compressImage(two, 61, 61);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(16).buildVerticalOffset(378);
		
		SimplePositions[] sp = { nameSP, pSP };
		BufferedImage[] bis = { nameBI, p };
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
