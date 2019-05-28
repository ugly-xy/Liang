package com.zb.service.image.love;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class LvJianBiaoBaiTool extends BaseTool implements TwoDraw{
	public LvJianBiaoBaiTool(StorageService storageService) {
		super(storageService);
	}

	public LvJianBiaoBaiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5785bcf10cf2483acfb003d6";
		StorageService storageService = new StorageService();
		LvJianBiaoBaiTool tyt = new LvJianBiaoBaiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","我爱你", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize =21;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		SimplePositions nameSP =null;
		BufferedImage nameBI =null;
		SimplePositions nameSP2 =null;
		BufferedImage nameBI2 =null;
		SimplePositions nameSP3 =null;
		BufferedImage nameBI3 =null;
		
		int len = one.length();
		
		if(len==1){
			// 姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(160).buildVerticalOffset(415);
			nameBI = drawText(one+two, zbfont, fontSize, color,
					240, 100, 0, true);
			// 姓名
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(130).buildVerticalOffset(295);
			nameBI2 = drawText(one+two, zbfont, fontSize, color,
					240, 100, -0.45, true,20,20);
			// 姓名
			nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(80).buildVerticalOffset(190);
			nameBI3 = drawText(one+two, zbfont, fontSize, color,
					300, 260, -0.95, true,60,60);
		}else if(len==2){
			// 姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(150).buildVerticalOffset(415);
			nameBI = drawText(one+two, zbfont, fontSize, color,
					240, 100, 0, true);
			// 姓名
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(120).buildVerticalOffset(295);
			nameBI2 = drawText(one+two, zbfont, fontSize, color,
					240, 100, -0.45, true,20,20);
			// 姓名
			nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(70).buildVerticalOffset(190);
			nameBI3 = drawText(one+two, zbfont, fontSize, color,
					300, 260, -0.95, true,60,60);
		}else{
			// 姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(140).buildVerticalOffset(415);
			nameBI = drawText(one+two, zbfont, fontSize, color,
					240, 100, 0, true);
			// 姓名
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(105).buildVerticalOffset(295);
			nameBI2 = drawText(one+two, zbfont, fontSize, color,
					240, 100, -0.45, true,20,20);
			// 姓名
			nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(80).buildVerticalOffset(180);
			nameBI3 = drawText(one+two, zbfont, fontSize, color,
					300, 260, -0.95, true,50,50);
		}
		
		
		SimplePositions[] sp = { nameSP,nameSP2,nameSP3};

		BufferedImage[] bis = { nameBI,nameBI2,nameBI3};

		return super.getSaveFile(sp, bis, 0.7f, tmpBackPic);
	}

}
