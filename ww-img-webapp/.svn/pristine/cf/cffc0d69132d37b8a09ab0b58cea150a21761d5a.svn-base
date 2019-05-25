package com.zb.service.image.love;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.*;

public class BiYunTaoYuePaoTool extends BaseTool implements ThreeDraw {

	public BiYunTaoYuePaoTool(StorageService storageService) {
		super(storageService);
	}

	public BiYunTaoYuePaoTool() {
	}

	public static void main(String[] args) throws IOException {
		String p0 = "201.png";
		StorageService storageService = new StorageService();
		BiYunTaoYuePaoTool tyt = new BiYunTaoYuePaoTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.draw("宝宝", "话不多说今晚今日可否一战啊啊啊", "老公", p0,null));
	}

	static Color color = Color.black;

	@Override
	public String draw(String one, String two, String three, String tmpPath,String count)
			throws IOException {

		int top = 472;
		StringBuilder sb = new StringBuilder("          ");
		sb.append(one);

		// top = top - (23 - two.length()) ;
		sb.append("\n       ").append(two)
				.append("\n                                    ")
				.append(three);
		double th = 0.22;
		int max = 325;
		int size = 20;
		SimplePositions oneSP = new SimplePositions();
		oneSP.buildHorizontalOffset(130).buildVerticalOffset(top);
		BufferedImage oneBI = drawText(sb.toString(), ZbFont.方正静蕾简体, size,
				color, max, 300, th, true, 20, 200);

		SimplePositions[] sp = { oneSP };
		BufferedImage[] bis = { oneBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}
}
