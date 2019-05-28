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

public class GouGouEGaoTool extends BaseTool implements ThreeDraw{
	public GouGouEGaoTool(StorageService storageService) {
		super(storageService);
	}

	public GouGouEGaoTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5788583d0cf22217f1fdc3c1";
		StorageService storageService = new StorageService();
		GouGouEGaoTool tyt = new GouGouEGaoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","我变成狗了了了了","装点逼", tmpPath0,null));
		
		
	}
	
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 18;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two,String three, String tmpBackPic, String count) throws IOException {
		// 姓名1
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(140).buildVerticalOffset(430);
		BufferedImage nameBI = drawText(one+"    救命", zbfont, fontSize, color,
				240, 100, 0, true);
		// 内容
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(155).buildVerticalOffset(460);
		BufferedImage nameBI2 = drawText(two, zbfont, fontSize, color,
				240, 100, 0, true);
		// 姓名3
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(175).buildVerticalOffset(490);
		BufferedImage nameBI3 = drawText("我是"+three+"啊", zbfont, fontSize, color,
				240, 100, 0, true);
		SimplePositions[] sp = { nameSP,nameSP2,nameSP3};

		BufferedImage[] bis = { nameBI,nameBI2,nameBI3};

		return super.getSaveFile(sp, bis, 0.90f, tmpBackPic);
	}

}
