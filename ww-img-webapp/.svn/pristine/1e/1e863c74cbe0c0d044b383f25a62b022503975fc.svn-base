package com.zb.service.image.certificate;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class QiuXingQianMingTool extends BaseTool implements TwoDraw{
	public QiuXingQianMingTool(StorageService storageService) {
		super(storageService);
	}

	public QiuXingQianMingTool() {
	}
	public static void main(String[] args) throws IOException {
		//String tmpPath0 = "209-成绩认证.png";
		StorageService storageService = new StorageService();
		QiuXingQianMingTool tyt = new QiuXingQianMingTool(storageService);
		tyt.isDebug(true);
		int i = 2;
		System.out
				.println(tyt.draw("装点逼", S[i], T[i],null));
	}
	
	static String T[] = {
			"http://imgzb.zhuangdianbi.com/57832a850cf2e16741cfcda7",
			"http://imgzb.zhuangdianbi.com/57832a6d0cf2e16741cfcd74",
			"http://imgzb.zhuangdianbi.com/57832a600cf2e16741cfcd55"
			};
	static String S[] = { "C罗","贝克汉姆","梅西"};
	
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 20;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one,String two, String tmpBackPic, String count) throws IOException {
		SimplePositions nameSP = null;
		BufferedImage nameBI = null;
		SimplePositions nameSP2 = null;
		BufferedImage nameBI2 = null;
		String zeng = "赠:";
		if(two.equals(S[0])){
			int fontSize2 = 20;
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(385).buildVerticalOffset(346);
			nameBI = drawText(one,zbfont, fontSize2, new Color(0,0,0),
					400, 100, 0, true);
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(385).buildVerticalOffset(300);
			nameBI2 = drawText(zeng,zbfont, fontSize2, new Color(0,0,0),
					400, 100, 0, true);
		}else if(two.equals(S[1])){
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(410).buildVerticalOffset(407);
			nameBI = drawText(one,zbfont, fontSize, color,
					400, 100, 0, true);
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(410).buildVerticalOffset(367);
			nameBI2 = drawText(zeng,zbfont, fontSize, color,
					400, 100, 0, true);
		}else{
			int fontSize2 = 20;
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(90).buildVerticalOffset(100);
			nameBI = drawText(one,zbfont, fontSize2, new Color(255,255,255),
					400, 100, 0, true);
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(90).buildVerticalOffset(60);
			nameBI2 = drawText(zeng,zbfont, fontSize2, new Color(255,255,255),
					400, 100, 0, true);
		}
		
		
		SimplePositions[] sp = { nameSP,nameSP2};
		BufferedImage[] bis = { nameBI,nameBI2};
		return super.getSaveFile(sp, bis, 0.9f, tmpBackPic);
	}

}
