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

public class TaiReHuiFeiZhouTool extends BaseTool implements TwoDraw{
	public TaiReHuiFeiZhouTool(StorageService storageService) {
		super(storageService);
	}

	public TaiReHuiFeiZhouTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		// String tmpPath0 =
		// "http://imgzb.zhuangdianbi.com/5732a2a80cf2d5342be19ca8";
		//String tmpPath0 = "http://imgzb.zhuangdianbi.com/576cb8540cf24fe2e4ecf100";
		StorageService storageService = new StorageService();
		TaiReHuiFeiZhouTool tyt = new TaiReHuiFeiZhouTool(storageService);
		tyt.isDebug(true);
		int i = 1;
		System.out.println(tyt.draw("北京",S[i], T[i],null));
	}

	static ZbFont zbfont = ZbFont.黑体加粗;
	static int fontSize = 18;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	static String S[] = {"太热1",
						"太热2"};
	static String T[] = {"http://imgzb.zhuangdianbi.com/5790ae350cf20bc2179f49ae",
						"http://imgzb.zhuangdianbi.com/5790ae480cf20bc2179f49c4"};
	
	
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		
		SimplePositions nameSP  = null;
		BufferedImage nameBI =null;
		SimplePositions nameSP2  = null;
		BufferedImage nameBI2 =null;
		
		
		if(two.equals(S[0])){
			String one0 = one+"太热了，我要回非洲！";
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(55).buildVerticalOffset(10);
			nameBI = drawText(one0,zbfont.font(),fontType,38, color,
					600, 100, 0, true);
			SimplePositions[] sp ={nameSP};
			BufferedImage[] bis ={nameBI};
			return super.getSaveFile(sp, bis, 0.95f, T[0]);
		}else{
			
			String one0 = "这位朋友你能说说是"+one+"热还是非洲热吗？";
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(60).buildVerticalOffset(15);
			nameBI = drawText(one0,zbfont,22, color,
					290, 100, 0, true);
			
			String one1 = "我再强调一遍，我不是非洲的，我是在"+one+"晒黑的";
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(200).buildVerticalOffset(250);
			nameBI2 = drawText(one1,zbfont,fontSize, new Color(255,255,255),
					230, 100, 0, true);
			
			
			SimplePositions[] sp ={nameSP,nameSP2};
			BufferedImage[] bis ={nameBI,nameBI2};
			
			return super.getSaveFile(sp, bis, 0.95f, T[1]);
		}
		

		
	}
}
