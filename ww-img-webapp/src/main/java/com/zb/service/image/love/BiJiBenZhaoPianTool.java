package com.zb.service.image.love;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.jhlabs.image.PerspectiveFilter;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class BiJiBenZhaoPianTool extends BaseTool implements TwoDraw{
	public BiJiBenZhaoPianTool(StorageService storageService) {
		super(storageService);
	}

	public BiJiBenZhaoPianTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/577a3d0b0cf26acda3d944ab";
		StorageService storageService = new StorageService();
		BiJiBenZhaoPianTool tyt = new BiJiBenZhaoPianTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("每个人，擦肩而过，就是一次缘分。","测试2.png", tmpPath0,null));
	}
	
	static final String beijing ="http://imgzb.zhuangdianbi.com/577a3cbf0cf26acda3d94472";
	
	static ZbFont zbfont = ZbFont.德彪钢笔行书字库;
	static int fontSize = 24;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.TYPE1_FONT;
	
	
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		
		
		//内容
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(180).buildVerticalOffset(310);
		BufferedImage nameBI = drawText(one, zbfont, fontSize, color,
				180, 300, -0.10, true,20,20);
		
		//玩家照片
		BufferedImage p = super.compressImage(two, 220, 350);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(351).buildVerticalOffset(113);
		
		PerspectiveFilter pf = new PerspectiveFilter();
		pf.setCorners(-47, 0, p.getWidth() - 52, -35, p.getWidth()+78,
				p.getHeight() - 75, 18, p.getHeight() - 23);
		p = pf.filter(p, null);
		
		BufferedImage p2 = super.getImg(beijing);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);


		SimplePositions[] sp = {pSP, pSP2,nameSP };

		BufferedImage[] bis = { p, p2,nameBI };

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
