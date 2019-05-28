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

public class ZuanJieQiuHunTool extends BaseTool implements TwoDraw{
	public ZuanJieQiuHunTool(StorageService storageService) {
		super(storageService);
	}

	public ZuanJieQiuHunTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/577b519b0cf267a801c88983";
		StorageService storageService = new StorageService();
		ZuanJieQiuHunTool tyt = new ZuanJieQiuHunTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("都比","装点逼", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 30;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//男方名字
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(186).buildVerticalOffset(260);
		BufferedImage nameBI = drawText(one, zbfont, fontSize, color,
				180, 100, 0, true);
		
		//女方
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(300).buildVerticalOffset(335);
		BufferedImage nameBI2 = drawText(two, zbfont, fontSize, color,
				180, 100, 0, true);
		
		SimplePositions[] sp = {nameSP,nameSP2 };

		BufferedImage[] bis = { nameBI,nameBI2 };

		return super.getSaveFile(sp, bis, 0.8f, tmpBackPic);
	}

}
