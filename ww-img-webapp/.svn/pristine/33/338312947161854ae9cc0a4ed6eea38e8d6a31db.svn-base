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

public class BaoZhengShuTool extends BaseTool implements TwoDraw {

	public BaoZhengShuTool(StorageService storageService) {
		super(storageService);
	}

	public BaoZhengShuTool() {
	}

	public static void main(String[] args) throws IOException {
		String p0 = "bzs.jpg";
		StorageService storageService = new StorageService();
		BaoZhengShuTool tyt = new BaoZhengShuTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.draw("宝宝", "老公", p0,null));
	}

	static Color color = Color.black;

	@Override
	public String draw(String one, String two, String tmpPath,String count)
			throws IOException {

		SimplePositions oneSP = new SimplePositions();
		oneSP.buildHorizontalOffset(82).buildVerticalOffset(82);
		BufferedImage oneBI = drawText(one, ZbFont.新蒂小丸子小学版, 16, color, 300, 100,
				0, true);

		SimplePositions twoSP = new SimplePositions();
		twoSP.buildHorizontalOffset(271).buildVerticalOffset(472);
		BufferedImage twoBI = drawText(two, ZbFont.新蒂小丸子小学版, 20, color, 300, 100,
				0, true);

		SimplePositions[] sp = { oneSP, twoSP };
		BufferedImage[] bis = { oneBI, twoBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}

}
