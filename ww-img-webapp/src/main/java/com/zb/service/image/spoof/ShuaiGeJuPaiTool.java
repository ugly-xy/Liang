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

public class ShuaiGeJuPaiTool extends BaseTool implements ThreeDraw{
	public ShuaiGeJuPaiTool(StorageService storageService) {
		super(storageService);
	}

	public ShuaiGeJuPaiTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		//String tmpPath0 = "209-成绩认证.png";
		StorageService storageService = new StorageService();
		ShuaiGeJuPaiTool tyt = new ShuaiGeJuPaiTool(storageService);
		tyt.isDebug(true);
		int i = 7;
		System.out
				.println(tyt.draw("装点逼","我好喜欢你，凹凸Kei~", S[i], T[i],null));
	}
	
	static String T[] = {
			"http://imgzb.zhuangdianbi.com/576cac870cf24fe2e4ece724",
			"http://imgzb.zhuangdianbi.com/576cac9d0cf24fe2e4ece732",
			"http://imgzb.zhuangdianbi.com/576cacaa0cf24fe2e4ece73b",
			"http://imgzb.zhuangdianbi.com/576cacb90cf24fe2e4ece744",
			"http://imgzb.zhuangdianbi.com/576cacc70cf24fe2e4ece74f",
			"http://imgzb.zhuangdianbi.com/576cacd40cf24fe2e4ece75b",
			"http://imgzb.zhuangdianbi.com/576cace50cf24fe2e4ece76d",
			"http://imgzb.zhuangdianbi.com/576cacf00cf24fe2e4ece777"};
	static String S[] = { "帅哥举牌1", "帅哥举牌2", "帅哥举牌3", "帅哥举牌4", "帅哥举牌5", "帅哥举牌6", "帅哥举牌7", "帅哥举牌8" };
	
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 18;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String two, String style, String tmpPath,String count) throws IOException {
		SimplePositions nameSP = null;
		SimplePositions nameSP2 = null;
		BufferedImage nameBI = null;
		BufferedImage nameBI2 = null;
		
		if(style.equals(S[0])){
			// 姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(90).buildVerticalOffset(205);
			nameBI = drawText(one,zbfont, fontSize, color,
					200, 100, 0.1, true,20,20);
			
			// 一句话
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(80).buildVerticalOffset(248);
			nameBI2 = drawText(two,zbfont, fontSize, color,
					180, 100, 0.1, true,20,20);
		}else if(style.equals(S[1])){
			// 姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(90).buildVerticalOffset(120);
			nameBI = drawText(one,zbfont, fontSize, color,
					200, 100, 0.25, true,20,20);
			
			// 一句话
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(80).buildVerticalOffset(170);
			nameBI2 = drawText(two,zbfont, fontSize, color,
					180, 100, 0.25, true,20,20);
		}else if(style.equals(S[2])){
			// 姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(90).buildVerticalOffset(200);
			nameBI = drawText(one,zbfont, fontSize, color,
					200, 100, 0.25, true,20,20);
			
			// 一句话
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(80).buildVerticalOffset(260);
			nameBI2 = drawText(two,zbfont, fontSize, color,
					180, 100, 0.25, true,20,20);
		}else if(style.equals(S[3])){
			// 姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(175).buildVerticalOffset(90);
			nameBI = drawText(one,zbfont, fontSize, color,
					200, 100, 0.1, true,20,20);
			
			// 一句话
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(175).buildVerticalOffset(130);
			nameBI2 = drawText(two,zbfont, fontSize, color,
					160, 100, 0.1, true,20,20);
		}else if(style.equals(S[4])){
			// 姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(15).buildVerticalOffset(340);
			nameBI = drawText(one,zbfont, fontSize, color,
					200, 100, 0, true,20,20);
			
			// 一句话
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(25).buildVerticalOffset(370);
			nameBI2 = drawText(two,zbfont, fontSize, color,
					180, 100, 0, true,20,20);
		}else if(style.equals(S[5])){
			// 姓名
			int fontSize2 = 16;
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(10).buildVerticalOffset(75);
			nameBI = drawText(one,zbfont, fontSize2, color,
					200, 100, 0.1, true,20,20);
			
			// 一句话
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(5).buildVerticalOffset(113);
			nameBI2 = drawText(two,zbfont, fontSize2, color,
					160, 100, 0.1, true,20,20);
		}else if(style.equals(S[6])){
			// 姓名
			int fontSize3 = 14;
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(170).buildVerticalOffset(162);
			nameBI = drawText(one,zbfont, fontSize3, color,
					200, 100, 0.1, true,20,20);
			
			// 一句话
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(158).buildVerticalOffset(200);
			nameBI2 = drawText(two,zbfont, fontSize3, color,
					150, 100, 0.1, true,20,20);
		}else{
			// 姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(80).buildVerticalOffset(330);
			nameBI = drawText(one,zbfont, fontSize, color,
					200, 100, -0.37, true,20,20);
			
			// 一句话
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(100).buildVerticalOffset(325);
			nameBI2 = drawText(two,zbfont, fontSize, color,
					220, 160, -0.37, true,20,20);
		}
		
		SimplePositions[] sp = { nameSP,nameSP2};
		BufferedImage[] bis = { nameBI,nameBI2};
		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
