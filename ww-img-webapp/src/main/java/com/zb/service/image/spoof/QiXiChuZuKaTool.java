package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.jhlabs.image.PerspectiveFilter;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.ThreeDraw;

public class QiXiChuZuKaTool extends BaseTool implements ThreeDraw{
	public QiXiChuZuKaTool(StorageService storageService) {
		super(storageService);
	}

	public QiXiChuZuKaTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57a192c20cf240d5e8eb0d39";
		StorageService storageService = new StorageService();
		QiXiChuZuKaTool tyt = new QiXiChuZuKaTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","1234567890","素材1.png", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 20;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String three, String tmpBackPic, String count) throws IOException {
		
		//姓名
		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(328).buildVerticalOffset(204);
 		BufferedImage nameBi = drawText(one, zbfont.font(),fontType, 28, 
 				color, 240, 0, -0.03, true);
		
 		//姓名
 		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(300).buildVerticalOffset(239);
 		BufferedImage nameBi2 = drawText(two, zbfont, fontSize,
 				color, 240, 0, -0.03, true);
 		//玩家照片
		BufferedImage p = super.compressImage(three, 198, 278);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(41).buildVerticalOffset(147);
		
		PerspectiveFilter pf = new PerspectiveFilter();
		pf.setCorners(0, 0, p.getWidth() , -6, p.getWidth()+6,
				p.getHeight() -6, 6, p.getHeight() - 2);
		p = pf.filter(p, null);
		
		
		SimplePositions[] sp = { pSP,nameSP,nameSP2};

		BufferedImage[] bis = { p,nameBi,nameBi2};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
