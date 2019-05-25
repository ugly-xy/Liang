package com.zb.service.image.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class QiuQiuDiYiMingTool extends BaseTool implements TwoDraw{
	public QiuQiuDiYiMingTool(StorageService storageService) {
		super(storageService);
	}

	public QiuQiuDiYiMingTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57e24a480cf2ab0389280200";
		StorageService storageService = new StorageService();
		QiuQiuDiYiMingTool tyt = new QiuQiuDiYiMingTool(storageService);
		tyt.isDebug(true);
		System.out
				.println(tyt.draw("装点逼","北京",tmpPath0,null));//10
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 28;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//昵称1
 		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(86-one.length()*43/2).buildVerticalOffset(33);
 		BufferedImage nameBi = drawText(one, zbfont, 43,
 				color, 800, 600, 0, true);
 		//地区
 		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(88).buildVerticalOffset(93);
 		BufferedImage nameBi2 = drawText(two, zbfont, 10,
 				color, 800, 600, 0, true);
 		
 		//昵称2
 		SimplePositions nameSP3 = new SimplePositions();
 		nameSP3.buildHorizontalOffset(474).buildVerticalOffset(27);
 		BufferedImage nameBi3 = drawText(one, zbfont, 12,
 				color, 800, 600, 0, true);
 		
 		SimplePositions[] sp = { nameSP,nameSP2, nameSP3 };
		BufferedImage[] bis = { nameBi,nameBi2, nameBi3 };
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
