package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.i.ThreeDraw;

public class YingChaoLianSaiTool extends BaseTool implements ThreeDraw{

	public YingChaoLianSaiTool(StorageService storageService) {
		super(storageService);
	}

	public YingChaoLianSaiTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		//String tmpPath0 = "209-成绩认证.png";
		StorageService storageService = new StorageService();
		YingChaoLianSaiTool tyt = new YingChaoLianSaiTool(storageService);
		tyt.isDebug(true);
		int i = 0;
		System.out
				.println(tyt.draw("装点逼","你怎么还不来一起装比", S[i], T[i],null));
	}
	
	static String T[] = {
			"http://imgzb.zhuangdianbi.com/57693b7c0cf2417b89da0c6b",
			"http://imgzb.zhuangdianbi.com/5768d8ad0cf2aa2d0ee84a16" };
	static String S[] = { "英超举牌男", "英超肚脐女" };
	
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 14;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one,String two,String style, String tmpPath,String count) throws IOException {
		SimplePositions nameSP = null;
		SimplePositions nameSP2 = null;
		BufferedImage nameBI = null;
		BufferedImage nameBI2 = null;
		if(style.equals(S[0])){
			// 姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(295).buildVerticalOffset(270);
			nameBI = drawText(one,zbfont, fontSize, color,
					200, 100, 0, true);
			
			// 一句话
			//String content ="欧洲杯开始了"+"\n"+"你怎么还不来";
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(295).buildVerticalOffset(300);
			nameBI2 = drawText(two,zbfont, fontSize, color,
					120, 100, 0, true);
			
			
			SimplePositions[] sp = { nameSP,nameSP2};

			BufferedImage[] bis = { nameBI,nameBI2};
			return super.getSaveFile(sp, bis, 0.8f, tmpPath);
		}else{
			int fontSize = 20 ;
			// 姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(160).buildVerticalOffset(350);
			nameBI = drawText(one,zbfont, fontSize, color,
					200, 100, 0.2, true,20,20);
			
			// 一句话
			//String content ="欧洲杯开始了"+"\n"+"你怎么还不来";
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(400).buildVerticalOffset(380);
			nameBI2 = drawText(two,zbfont, fontSize, color,
					160, 100, 0, true);
			
			
			SimplePositions[] sp = { nameSP,nameSP2};

			BufferedImage[] bis = { nameBI,nameBI2};
			return super.getSaveFile(sp, bis, 0.8f, tmpPath);
		}
	}

}
