package com.zb.service.image.certificate;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class YuZuiDiSanJiTool extends BaseTool implements OneDraw{
	public YuZuiDiSanJiTool(StorageService storageService) {
		super(storageService);
	}

	public YuZuiDiSanJiTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/577649bb0cf20de9530dd556";
		StorageService storageService = new StorageService();
		YuZuiDiSanJiTool tyt = new YuZuiDiSanJiTool(storageService);
		tyt.isDebug(true);
		System.out
				.println(tyt.draw("装点逼",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 24;
	static Color color = new Color(255,255,255);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		// 姓名
		String one0 = one+"：";
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(10).buildVerticalOffset(580);
		BufferedImage nameBI = drawText(one0, zbfont, fontSize, color,
				240, 100, 0, true);
		SimplePositions[] sp = { nameSP };

		BufferedImage[] bis = { nameBI };

		return super.getSaveFile(sp, bis, 0.97f, tmpBackPic);
	}

}
