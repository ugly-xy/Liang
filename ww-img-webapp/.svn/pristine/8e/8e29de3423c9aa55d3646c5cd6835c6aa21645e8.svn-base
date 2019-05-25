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

public class AiQingMoShuTool extends BaseTool implements OneDraw{
	public AiQingMoShuTool(StorageService storageService) {
		super(storageService);
	}

	public AiQingMoShuTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57762b180cf20718a814deea";
		StorageService storageService = new StorageService();
		AiQingMoShuTool tyt = new AiQingMoShuTool(storageService);
		tyt.isDebug(true);
		System.out
				.println(tyt.draw("装点逼",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 15;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		// 姓名1
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(250).buildVerticalOffset(125);
		BufferedImage nameBI = drawText(one, zbfont, fontSize, color,
				240, 100, 0, true);
		// 姓名2
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(260).buildVerticalOffset(315);
		BufferedImage nameBI2 = drawText(one, zbfont, fontSize, color,
				240, 100, 0, true);
		SimplePositions[] sp = { nameSP,nameSP2 };

		BufferedImage[] bis = { nameBI,nameBI2 };

		return super.getSaveFile(sp, bis, 0.9f, tmpBackPic);
	}

}
