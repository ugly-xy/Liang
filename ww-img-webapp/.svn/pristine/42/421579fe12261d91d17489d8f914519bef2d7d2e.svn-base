package com.zb.service.image.love;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;
import com.zb.service.image.i.TwoDraw;

public class YueQiuBiaoBaiTool extends BaseTool implements TwoDraw {

	public YueQiuBiaoBaiTool(StorageService storageService) {
		super(storageService);
	}

	public YueQiuBiaoBaiTool() {
	}

	public static void main(String[] args) throws IOException {
		String p0 = "165.png";
		StorageService storageService = new StorageService();
		YueQiuBiaoBaiTool tyt = new YueQiuBiaoBaiTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.draw("宝宝", "我爱你", p0,null));
	}

	@Override
	public String draw(String one, String two, String tmpPath,String count)
			throws IOException {
		one = one + "\n" + two;
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(215).buildVerticalOffset(200);
		BufferedImage nameBI = drawText(one, ZbFont.苹方, 20, new Color(255, 255,
				255), 300, 100, 0.23, true, 20, 20);

		SimplePositions[] sp = { nameSP };
		BufferedImage[] bis = { nameBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}

}
