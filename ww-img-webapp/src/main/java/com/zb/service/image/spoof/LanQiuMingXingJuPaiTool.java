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

public class LanQiuMingXingJuPaiTool extends BaseTool implements ThreeDraw{
	public LanQiuMingXingJuPaiTool(StorageService storageService) {
		super(storageService);
	}

	public LanQiuMingXingJuPaiTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		//String tmpPath0 = "http://imgzb.zhuangdianbi.com/577119a60cf24f3a07d16e3a";
		StorageService storageService = new StorageService();
		LanQiuMingXingJuPaiTool tyt = new LanQiuMingXingJuPaiTool(storageService);
		tyt.isDebug(true);
		int i =0;
		System.out.println(tyt.draw("装点逼","我看好你哦看我看好你哦看我看",S[i] ,T[i],null));
	}
	//14字
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 18;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	static String S[] = {"奥尼尔","林书豪","麦迪","詹姆斯","张伯伦"};
	static String T[] = {"http://imgzb.zhuangdianbi.com/579ae5fb0cf24516f7c8f157",
						"http://imgzb.zhuangdianbi.com/579ae60c0cf24516f7c8f16e",
						"http://imgzb.zhuangdianbi.com/579ae6210cf24516f7c8f186",
						"http://imgzb.zhuangdianbi.com/579ae63f0cf24516f7c8f1a9",
						"http://imgzb.zhuangdianbi.com/579ae64f0cf24516f7c8f1c6"};
	
	@Override
	public String draw(String one, String two, String three,String tmpBackPic, String count) throws IOException {
		
		SimplePositions nameSP = null;
		SimplePositions nameSP2 = null;
		BufferedImage nameBI = null;
		BufferedImage nameBI2 = null;
		
		if(three.equals(S[0])){
			
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(162).buildVerticalOffset(240);
			nameBI = drawText(one, zbfont, fontSize, color,
					240, 50, 0, true);
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(162).buildVerticalOffset(285);
			nameBI2 = drawText(two, zbfont, fontSize, color,
					150,150, 0, true);
			SimplePositions[] sp = { nameSP,nameSP2};

			BufferedImage[] bis = { nameBI,nameBI2};

			return super.getSaveFile(sp, bis, 0.8f, T[0]);
			
		}else if(three.equals(S[1])){
			
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(125).buildVerticalOffset(227);
			nameBI = drawText(one, zbfont, fontSize, color,
					240, 50, 0, true);
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(125).buildVerticalOffset(270);
			nameBI2 = drawText(two, zbfont, fontSize, color,
					150, 150, 0, true);
			SimplePositions[] sp = { nameSP,nameSP2};

			BufferedImage[] bis = { nameBI,nameBI2};

			return super.getSaveFile(sp, bis, 0.8f, T[1]);
		}else if(three.equals(S[2])){
			
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(201).buildVerticalOffset(511);
			nameBI = drawText(one, zbfont, 22, color,
					240, 120, 0.3, true);
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(140).buildVerticalOffset(557);
			nameBI2 = drawText(two, zbfont, 22, color,
					240, 120, 0.3, true,20,20);
			
			SimplePositions[] sp = { nameSP,nameSP2};

			BufferedImage[] bis = { nameBI,nameBI2};

			return super.getSaveFile(sp, bis, 0.8f, T[2]);
		}else if(three.equals(S[3])){
			
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(140).buildVerticalOffset(577);
			nameBI = drawText(one, zbfont, 24, color,
					240, 100, -0.05, true);
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(140).buildVerticalOffset(640);
			nameBI2 = drawText(two, zbfont, 24, color,
					240, 150, -0.05, true);
			
			SimplePositions[] sp = { nameSP,nameSP2};

			BufferedImage[] bis = { nameBI,nameBI2};

			return super.getSaveFile(sp, bis, 0.8f, T[3]);
			
		}else{
			
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(291).buildVerticalOffset(91);
			nameBI = drawText(one, zbfont, fontSize, color,
					240, 50, 0.15, true);
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(245).buildVerticalOffset(130);
			nameBI2 = drawText(two, zbfont, fontSize, color,
					240, 120, 0.15, true,20,20);
			
			SimplePositions[] sp = { nameSP,nameSP2};

			BufferedImage[] bis = { nameBI,nameBI2};

			return super.getSaveFile(sp, bis, 0.8f, T[4]);
			
		}
		
		
	}

}
