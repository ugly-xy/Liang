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

public class GuaGuaBiaoBai extends BaseTool implements TwoDraw {
	public GuaGuaBiaoBai(StorageService storageService) {
		super(storageService);
	}

	public GuaGuaBiaoBai() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		// String tmpPath0 =
		// "http://imgzb.zhuangdianbi.com/5732a2a80cf2d5342be19ca8";
		String tmpPath0 = "刮刮表白.png";
		StorageService storageService = new StorageService();
		GuaGuaBiaoBai tyt = new GuaGuaBiaoBai(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装逼","装点逼", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 26;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpPath,String count) throws IOException {
		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(150).buildVerticalOffset(100);
		BufferedImage nameBI = drawText(one,zbfont, fontSize, color,
				240, 200, 0.5, true,20,20);
		// 要说的话
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(40).buildVerticalOffset(115);
		BufferedImage nameBI2 = drawText(two,zbfont, fontSize, color,
				240,200, 0.5, true,20,20);
		SimplePositions[] sp = { nameSP,nameSP2};

		BufferedImage[] bis = { nameBI,nameBI2};

		return super.getSaveFile(sp, bis, 0.5f, tmpPath);
	}

}
