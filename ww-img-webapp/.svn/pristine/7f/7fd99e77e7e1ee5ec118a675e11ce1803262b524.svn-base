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

public class DuoGeMeiNvShuaiGeJuPaiTool extends BaseTool implements TwoDraw{
	
	public DuoGeMeiNvShuaiGeJuPaiTool(StorageService storageService) {
		super(storageService);
	}

	public DuoGeMeiNvShuaiGeJuPaiTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		//String tmpPath0 = "209-成绩认证.png";
		StorageService storageService = new StorageService();
		DuoGeMeiNvShuaiGeJuPaiTool tyt = new DuoGeMeiNvShuaiGeJuPaiTool(storageService);
		tyt.isDebug(true);
		int i =1;
		System.out
				.println(tyt.draw("装点逼", S[i],T[i],null));
	}
	static String T[] = {
			"http://imgzb.zhuangdianbi.com/5775f8370cf20718a814a644",
			"http://imgzb.zhuangdianbi.com/5775f8470cf20718a814a651"
			};
	static String S[] = { "多个美女举牌", "多个帅哥举牌" };
	
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 22;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		
		SimplePositions nameSP = null;
		SimplePositions nameSP2 = null;
		SimplePositions nameSP3 = null;
		SimplePositions nameSP4= null;
		SimplePositions nameSP5 = null;
		BufferedImage nameBI = null;
		BufferedImage nameBI2 = null;
		BufferedImage nameBI3 = null;
		BufferedImage nameBI4 = null;
		BufferedImage nameBI5 = null;
		
		if(two.equals(S[0])){
			
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(180).buildVerticalOffset(240);
			nameBI = drawText(one,zbfont, fontSize, color,
					200, 100, -0.9, true,30,30);
			
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(70).buildVerticalOffset(670);
			nameBI2 = drawText(one,zbfont, fontSize, color,
					200, 100, -0.1, true);
			
			nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(480).buildVerticalOffset(120);
			nameBI3 = drawText(one,zbfont, fontSize, color,
					200, 100, -0.5, true,20,20);
			
			nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(460).buildVerticalOffset(420);
			nameBI4 = drawText(one,zbfont, fontSize, color,
					200, 100, 0.5, true,20,20);
			
			nameSP5 = new SimplePositions();
			nameSP5.buildHorizontalOffset(520).buildVerticalOffset(730);
			nameBI5 = drawText(one,zbfont, fontSize, color,
					200, 100, 0, true);
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSP5};
			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4 ,nameBI5};
			return super.getSaveFile(sp, bis, 0.7f, tmpBackPic);
		}else{
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(121).buildVerticalOffset(265);
			nameBI = drawText(one,zbfont, fontSize, color,
					200, 100, 0, true);
			
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(125).buildVerticalOffset(690);
			nameBI2 = drawText(one,zbfont, fontSize, color,
					200,100, 0, true);
			
			nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(445).buildVerticalOffset(250);
			nameBI3 = drawText(one,zbfont, fontSize, color,
					200, 100, 0, true);
			
			nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(390).buildVerticalOffset(610);
			nameBI4 = drawText(one,zbfont, fontSize, color,
					200, 100, 0, true);
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4};
			BufferedImage[] bis = { nameBI,nameBI2 ,nameBI3 ,nameBI4 };
			return super.getSaveFile(sp, bis, 0.6f, tmpBackPic);
		}
		
		
	}

}
