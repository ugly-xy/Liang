package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.*;

public class BianXingShouShuTool extends BaseTool implements TwoDraw {

	public BianXingShouShuTool(StorageService storageService) {
		super(storageService);
	}

	public BianXingShouShuTool() {
	}

	public static void main(String[] args) throws IOException {

		StorageService storageService = new StorageService();
		BianXingShouShuTool tyt = new BianXingShouShuTool(storageService);
		tyt.isDebug(true);
		int i = 1;

		System.out.println(tyt.draw("宝宝", S[i], T[i],null));
	}

	static String T[] = { "http://imgzb.zhuangdianbi.com/575f9b6c0cf2b0cf740207eb", "http://imgzb.zhuangdianbi.com/575f9b3f0cf2b0cf740207be" };
	static String S[] = { "女变男", "男变女" };

	static Color color = Color.black;

	static int[][] TOPS = { { 133, 810 }, { 148, 850 } };
	static int[][] LEFTS = { { 178, 550 }, { 168, 543 } };

	@Override
	public String draw(String one, String two, String tmpPath,String count)
			throws IOException {

		int i = 0;
		if (two.equals(S[1])) {
			i = 1;
		}

		SimplePositions oneSP = new SimplePositions();
		oneSP.buildHorizontalOffset(LEFTS[i][0])
				.buildVerticalOffset(TOPS[i][0]);
		BufferedImage oneBI = drawText(one, ZbFont.宋体, 11, color, 100, 100, 0,
				true, 0, 9);

		SimplePositions twoSP = new SimplePositions();
		twoSP.buildHorizontalOffset(LEFTS[i][1])
				.buildVerticalOffset(TOPS[i][1]);
		BufferedImage twoBI = drawText(one, ZbFont.新蒂小丸子小学版, 24, color, 100,
				100, 0, true, 0, 0);

		SimplePositions[] sp = { oneSP, twoSP };
		BufferedImage[] bis = { oneBI, twoBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}
}
