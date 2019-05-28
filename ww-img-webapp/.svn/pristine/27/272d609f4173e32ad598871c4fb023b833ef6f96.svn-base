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

public final class MeiNvJuPaiTool extends BaseTool implements ThreeDraw{
	public MeiNvJuPaiTool(StorageService storageService) {
		super(storageService);
	}

	public MeiNvJuPaiTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		//String tmpPath0 = "209-成绩认证.png";
		StorageService storageService = new StorageService();
		MeiNvJuPaiTool tyt = new MeiNvJuPaiTool(storageService);
		tyt.isDebug(true);
		int i = 7;
		System.out
				.println(tyt.draw("装点逼","我好喜欢你，凹凸Kei~", S[i], T[i],null));
	}
	
	static String T[] = {
			"http://imgzb.zhuangdianbi.com/5775d91a0cf20718a814897d",
			"http://imgzb.zhuangdianbi.com/5775d92e0cf20718a8148991",
			"http://imgzb.zhuangdianbi.com/5775dd9c0cf20718a8148dde",
			"http://imgzb.zhuangdianbi.com/5775e0b00cf20718a81490e6",
			"http://imgzb.zhuangdianbi.com/5775d96a0cf20718a81489bc",
			"http://imgzb.zhuangdianbi.com/5775d97f0cf20718a81489ca",
			"http://imgzb.zhuangdianbi.com/5775d9900cf20718a81489d7",
			"http://imgzb.zhuangdianbi.com/5775d99d0cf20718a81489e5"};
	static String S[] = { "美女举牌1", "美女举牌2", "美女举牌3", "美女举牌4", "美女举牌5", "美女举牌6", "美女举牌7", "美女举牌8" };
	
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
			int fontSize2 = 24;
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(135).buildVerticalOffset(300);
			nameBI = drawText(one,zbfont, fontSize2, color,
					200, 100, 0, true);
			
			// 一句话
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(135).buildVerticalOffset(360);
			nameBI2 = drawText(two,zbfont, fontSize2, color,
					240, 200, 0, true);
		}else if(style.equals(S[1])){
			// 姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(180).buildVerticalOffset(280);
			nameBI = drawText(one,zbfont, fontSize, color,
					200, 100, -0.5, true,20,20);
			
			// 一句话
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(240).buildVerticalOffset(260);
			nameBI2 = drawText(two,zbfont, fontSize, color,
					180, 300, -0.5, true,40,40);
		}else if(style.equals(S[2])){
			// 姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(170).buildVerticalOffset(350);
			nameBI = drawText(one,zbfont, fontSize, color,
					200, 100, 0, true);
			
			// 一句话
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(170).buildVerticalOffset(390);
			nameBI2 = drawText(two,zbfont, fontSize, color,
					400, 100, 0, true);
		}else if(style.equals(S[3])){
			// 姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(175).buildVerticalOffset(130);
			nameBI = drawText(one,zbfont, fontSize, color,
					200, 100, 0, true);
			
			// 一句话
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(175).buildVerticalOffset(170);
			nameBI2 = drawText(two,zbfont, fontSize, color,
					160, 100, 0, true);
		}else if(style.equals(S[4])){
			// 姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(170).buildVerticalOffset(200);
			nameBI = drawText(one,zbfont, fontSize, color,
					200, 100, -0.5, true,20,20);
			
			// 一句话
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(185).buildVerticalOffset(220);
			nameBI2 = drawText(two,zbfont, fontSize, color,
					180, 300, -0.5, true,40,40);
		}else if(style.equals(S[5])){
			// 姓名
			int fontSize2 = 16;
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(20).buildVerticalOffset(190);
			nameBI = drawText(one,zbfont, fontSize2, color,
					200, 100, -0.3, true,20,20);
			
			// 一句话
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(10).buildVerticalOffset(215);
			nameBI2 = drawText(two,zbfont, fontSize2, color,
					200, 200, -0.3, true,40,40);
		}else if(style.equals(S[6])){
			// 姓名
			int fontSize3 = 14;
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(15).buildVerticalOffset(162);
			nameBI = drawText(one,zbfont, fontSize3, color,
					200, 100, -0.1, true,20,20);
			
			// 一句话
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(15).buildVerticalOffset(185);
			nameBI2 = drawText(two,zbfont, fontSize3, color,
					150, 100, -0.1, true,20,20);
		}else{
			// 姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(50).buildVerticalOffset(170);
			nameBI = drawText(one,zbfont, fontSize, color,
					200, 100, 0.37, true,20,20);
			
			// 一句话
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(0).buildVerticalOffset(195);
			nameBI2 = drawText(two,zbfont, fontSize, color,
					180, 200, 0.37, true,40,40);
		}
		
		SimplePositions[] sp = { nameSP,nameSP2};
		BufferedImage[] bis = { nameBI,nameBI2};
		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}
}
