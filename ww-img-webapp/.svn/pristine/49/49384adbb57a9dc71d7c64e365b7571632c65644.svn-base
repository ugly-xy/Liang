package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class KeAiDongWuZhangZuiTool extends BaseTool implements TwoDraw{
	public KeAiDongWuZhangZuiTool(StorageService storageService) {
		super(storageService);
	}

	public KeAiDongWuZhangZuiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "1.png";
		StorageService storageService = new StorageService();
		KeAiDongWuZhangZuiTool tyt = new KeAiDongWuZhangZuiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","就算这个世界如此污浊，但不要悲伤，你可以比它更污啊！你可以比它更污啊", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.宋体加粗;
	static int fontSize = 28;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	
	static String ditu[] = {"http://imgzb.zhuangdianbi.com/578f358e0cf215a728ce3462",
							"http://imgzb.zhuangdianbi.com/578f359f0cf215a728ce347d",
							"http://imgzb.zhuangdianbi.com/578f35ac0cf215a728ce348b",
							"http://imgzb.zhuangdianbi.com/578f35b90cf215a728ce3496",
							"http://imgzb.zhuangdianbi.com/578f35ca0cf215a728ce34a2",
							"http://imgzb.zhuangdianbi.com/578f35d60cf215a728ce34b7",
							"http://imgzb.zhuangdianbi.com/578f35e10cf215a728ce34c5"};
	
	
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		
		Random ran = new Random();
		int i = ditu.length;
		int num = ran.nextInt(i);
		//姓名
		String one0 = "by:"+one;
		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(560-one0.length()*fontSize-40).buildVerticalOffset(500);
 		BufferedImage nameBI = drawText(one0, zbfont, fontSize,
 				color, 300, 0, 0, true);
 		//名言
 		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(180).buildVerticalOffset(400);
 		BufferedImage nameBI2 = drawText(two, zbfont, fontSize,
 				color, 350, 0, 0, true);
 		SimplePositions[] sp = { nameSP,nameSP2};

		BufferedImage[] bis = { nameBI,nameBI2};

		return super.getSaveFile(sp, bis, 1f, ditu[num]);
	}

}
