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

public class GaoJiTool extends BaseTool implements OneDraw {
	
	public GaoJiTool(StorageService storageService) {
		super(storageService);
	}

	public GaoJiTool() {
	}
	
	public static void main(String[] args) throws IOException {
		// String tmpPath0 =
		// "http://imgzb.zhuangdianbi.com/5732a2a80cf2d5342be19ca8";
		String tmpPath0 = "搞基.png";
		StorageService storageService = new StorageService();
		GaoJiTool tyt = new GaoJiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼", tmpPath0,null));
	}
	
	@Override
	public String draw(String one, String tmpPath,String count) throws IOException {
		int fontSize = 25;
		Color color = new Color(0, 0, 0);
		String fontStyle = ZbFont.华文中宋.font();
		int fontType = Font.BOLD;

		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(600).buildVerticalOffset(533);
		BufferedImage nameBI = drawText(one, ZbFont.华文中宋, fontSize, color,
				240, 200, -0.5, true,30,30);
		
		SimplePositions[] sp = { nameSP};

		BufferedImage[] bis = { nameBI };

		return super.getSaveFile(sp, bis, 0.6f, tmpPath);
	}

}
