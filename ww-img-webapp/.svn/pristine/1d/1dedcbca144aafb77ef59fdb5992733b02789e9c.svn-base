package com.zb.service.image.holiday;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class DingZhiYueBingTool extends BaseTool implements OneDraw{
	public DingZhiYueBingTool(StorageService storageService) {
		super(storageService);
	}

	public DingZhiYueBingTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57d78f320cf29e55ef4971ad";
		StorageService storageService = new StorageService();
		DingZhiYueBingTool tyt = new DingZhiYueBingTool(storageService);
		tyt.isDebug(true);
		System.out
				.println(tyt.draw("装点逼",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 32;
	static Color color = new Color(147,65,51);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		// 姓名1
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(466-one.length()*fontSize/2).buildVerticalOffset(105);
		BufferedImage nameBI = drawText(one, zbfont.font(),Font.ITALIC, fontSize, color,
				300, 100, 0, true);
		SimplePositions[] sp = { nameSP};

		BufferedImage[] bis = { nameBI};

		return super.getSaveFile(sp, bis, 0.8f, tmpBackPic);
	}

}
