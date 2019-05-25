package com.zb.service.image.love;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class DaShuQingShuTool extends BaseTool implements OneDraw{
	public DaShuQingShuTool(StorageService storageService) {
		super(storageService);
	}

	public DaShuQingShuTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "292-情书.png";
		StorageService storageService = new StorageService();
		DaShuQingShuTool tyt = new DaShuQingShuTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点点", tmpPath0,null));
		
		
	}
	
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 20;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		// 姓名
		String one0 = one+":";
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(95+(4-one.length())*fontSize).buildVerticalOffset(105);
		BufferedImage nameBI = drawText(one0, zbfont, fontSize, color,
				240, 100, 0, true);
		SimplePositions[] sp = { nameSP};

		BufferedImage[] bis = { nameBI};

		return super.getSaveFile(sp, bis, 0.5f, tmpBackPic);
	}

}
