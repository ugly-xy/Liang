package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class LuoNvQiuYueTool extends BaseTool implements TwoDraw{
	public LuoNvQiuYueTool(StorageService storageService) {
		super(storageService);
	}

	public LuoNvQiuYueTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5785ed6d0cf2483acfb0463f";
		StorageService storageService = new StorageService();
		LuoNvQiuYueTool tyt = new LuoNvQiuYueTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装逼", "我和你缠缠绵绵永不改变!",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.书体坊赵九江钢笔行书;
	static int fontSize =36;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(213).buildVerticalOffset(360);
		BufferedImage nameBI = drawText(one, zbfont.font(),fontType, fontSize, color,
				240, 100, 0, true);
		// 姓名
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(213).buildVerticalOffset(425);
		BufferedImage nameBI2 = drawText(two, zbfont.font(),fontType, fontSize, color,
				270, 100, 0, true);
		SimplePositions[] sp = { nameSP,nameSP2};

		BufferedImage[] bis = { nameBI,nameBI2,};

		return super.getSaveFile(sp, bis, 0.8f, tmpBackPic);
	}

}
