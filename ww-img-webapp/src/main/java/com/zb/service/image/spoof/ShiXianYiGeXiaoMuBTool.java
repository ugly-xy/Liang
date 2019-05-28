package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class ShiXianYiGeXiaoMuBTool extends BaseTool implements OneDraw{
	public ShiXianYiGeXiaoMuBTool(StorageService storageService) {
		super(storageService);
	}

	public ShiXianYiGeXiaoMuBTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57c6d0b10cf20938fadbb7a3";
		StorageService storageService = new StorageService();
		ShiXianYiGeXiaoMuBTool tyt = new ShiXianYiGeXiaoMuBTool(storageService);
		tyt.isDebug(true);
		System.out
				.println(tyt.draw("装个逼",tmpPath0,null));//10
	}
	
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 26;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//上一句话
 		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(188-11*fontSize/2).buildVerticalOffset(300);
 		BufferedImage nameBi = drawText("先定一个能实现的小目标", zbfont, fontSize,
 				color, 800, 100, 0, true);
 		//目标
 		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(188-(3+one.length())*fontSize/2).buildVerticalOffset(648);
 		BufferedImage nameBi2 = drawText("比如说"+one, zbfont, fontSize,
 				color, 800, 100, 0, true);
 		
 		
 		SimplePositions[] sp = { nameSP,nameSP2};

		BufferedImage[] bis = {nameBi,nameBi2};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
