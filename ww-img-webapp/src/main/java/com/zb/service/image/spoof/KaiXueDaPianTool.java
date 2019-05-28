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

public class KaiXueDaPianTool extends BaseTool implements TwoDraw{
	public KaiXueDaPianTool(StorageService storageService) {
		super(storageService);
	}

	public KaiXueDaPianTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57c54a180cf27b518ba86d75";
		StorageService storageService = new StorageService();
		KaiXueDaPianTool tyt = new KaiXueDaPianTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","cshi.png", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体加粗;
	static int fontSize = 42;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.TYPE1_FONT;//480 550   96 170
	
	static String ditu = "http://imgzb.zhuangdianbi.com/57c54a050cf27b518ba86d63";
	
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		// 主演
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(290-(2+one.length())*fontSize/2).buildVerticalOffset(750);
		BufferedImage nameBI = drawText("主演:"+one, zbfont.font(),fontType, fontSize, color,
				400, 0, 0, true);
		
		//玩家照片
		BufferedImage p = super.compressImage(two, 480, 550);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(96).buildVerticalOffset(170);
		
		//覆盖图
		BufferedImage p2 = super.getImg(ditu);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		SimplePositions[] sp = { pSP,pSP2,nameSP};

		BufferedImage[] bis = { p,p2,nameBI};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
