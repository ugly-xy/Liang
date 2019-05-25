package com.zb.service.image.holiday;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.*;

public class FuQinJieTool extends BaseTool implements TwoDraw {

	public FuQinJieTool(StorageService storageService) {
		super(storageService);
	}

	public FuQinJieTool() {
	}

	public static void main(String[] args) throws IOException {
		StorageService storageService = new StorageService();
		FuQinJieTool tyt = new FuQinJieTool(storageService);
		tyt.isDebug(true);
		int i = 2;
		System.out.println(tyt.draw("宝宝", S[i], T[i],null));
	}

	static Color color = Color.black;
	static String T[] = {
			"http://imgzb.zhuangdianbi.com/576116580cf244b2006aa44d",
			"http://imgzb.zhuangdianbi.com/5761202c0cf244b2006aad42",
			"http://imgzb.zhuangdianbi.com/576120600cf244b2006aad79" };
	static String S[] = { "自行车", "拉手", "女孩亲脸" };

	@Override
	public String draw(String one, String two, String tmpPath,String count)
			throws IOException {

		SimplePositions oneSP = null;
		BufferedImage oneBI = null;

		if (two.equals(S[0])) {
			oneSP = new SimplePositions();
			oneSP.buildHorizontalOffset(486).buildVerticalOffset(336);
			oneBI = drawText(one, ZbFont.方正静蕾简体, 28, color, 300, 100, 0, true);
		} else if (two.equals(S[1])) {
			oneSP = new SimplePositions();
			oneSP.buildHorizontalOffset(579).buildVerticalOffset(255);
			oneBI = drawText(one, ZbFont.方正静蕾简体, 20, color, 300, 100, -0.18,
					true, 20, 20);
		} else {
			oneSP = new SimplePositions();
			oneSP.buildHorizontalOffset(250).buildVerticalOffset(350);
			oneBI = drawText(one, ZbFont.方正静蕾简体, 28, color, 300, 100, 0, true);
		}

		SimplePositions[] sp = { oneSP };
		BufferedImage[] bis = { oneBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}

}
