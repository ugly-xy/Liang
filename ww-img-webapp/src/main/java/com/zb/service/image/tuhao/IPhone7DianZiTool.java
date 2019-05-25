package com.zb.service.image.tuhao;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class IPhone7DianZiTool extends BaseTool implements TwoDraw{
	public IPhone7DianZiTool(StorageService storageService) {
		super(storageService);
	}

	public IPhone7DianZiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57ce79650cf26889c0a30eb9";
		StorageService storageService = new StorageService();
		IPhone7DianZiTool tyt = new IPhone7DianZiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","北京市", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.宋体;
	static int fontSize = 17;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//姓名
		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(128).buildVerticalOffset(169);
 		BufferedImage nameBi = drawText(one, zbfont, fontSize,
 				color, 700, 0, 0, true);
 		//地域
 		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(128).buildVerticalOffset(213);
 		BufferedImage nameBi2 = drawText(two, zbfont, fontSize,
 				color, 700, 0, 0, true);
 		
 		//姓名2
 		SimplePositions nameSP3 = new SimplePositions();
 		nameSP3.buildHorizontalOffset(610).buildVerticalOffset(169);
 		BufferedImage nameBi3 = drawText(one, zbfont, fontSize,
 				new Color(0,0,0), 700, 0, 0, true);
 		//地域2
 		SimplePositions nameSP4 = new SimplePositions();
 		nameSP4.buildHorizontalOffset(610).buildVerticalOffset(213);
 		BufferedImage nameBi4 = drawText(two, zbfont, fontSize,
 				new Color(0,0,0), 700, 0, 0, true);
 		
 		SimplePositions[] sp = {nameSP,nameSP2,nameSP3,nameSP4};

		BufferedImage[] bis = { nameBi,nameBi2,nameBi3,nameBi4};

		return super.getSaveFile(sp, bis, 0.85f, tmpBackPic);
	}

}
