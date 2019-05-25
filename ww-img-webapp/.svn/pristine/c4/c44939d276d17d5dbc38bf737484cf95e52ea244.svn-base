package com.zb.service.image.love;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;

public class HunLiTool extends BaseTool {

	public HunLiTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String p0 = "http://imgzb.zhuangdianbi.com/573c16ac0cf2b73d428754d6";// http://imgzb.zhuangdianbi.com/573c0f0a0cf2cc7298e6baaf";// http://imgzb.zhuangdianbi.com/573c077d0cf2cc7298e6b738";
		StorageService storageService = new StorageService();
		HunLiTool tyt = new HunLiTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.drawImg("黄子韬", "苍井空", p0));
	}

	// private final static String[] Names = { "苍井空", "黄子韬", "陈冠希", "鹿晗" };

	private final static int FontSizes = 20;

	private final static int[] Lefts = { 52, 75, 56 };

	private final static int[] Tops = { 432, 450, 467 };

	private final static Color[] Colors = { new Color(51, 85, 77),
			new Color(255, 0, 0), new Color(51, 85, 77) };

	private final static ZbFont[] Fs = { ZbFont.宋体, ZbFont.宋体, ZbFont.宋体 };

	double thetas = -0.07;

	boolean[] isHs = { false, false, false };

	/***
	 * @param name
	 *            姓名
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String women, String man, String tmpPath)
			throws IOException {

		if (man.length() == 2) {
			man = man.substring(0, 1) + "   " + man.substring(1, 2);
		}
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(Lefts[0]).buildVerticalOffset(Tops[0]);
		BufferedImage nameBI = drawText(man, Fs[0].font(), Fs[0].type(),
				FontSizes, Colors[0], 200, 0, thetas, true);

		SimplePositions loveSP = new SimplePositions();
		loveSP.buildHorizontalOffset(Lefts[1]).buildVerticalOffset(Tops[1]);
		BufferedImage loveBI = drawText("❤", Fs[1].font(), Fs[1].type(),
				FontSizes, Colors[1], 200, 0, thetas, true);

		if (women.length() == 2) {
			women = women.substring(0, 1) + "   " + women.substring(1, 2);
		}
		SimplePositions womenSP = new SimplePositions();
		womenSP.buildHorizontalOffset(Lefts[2]).buildVerticalOffset(Tops[2]);
		BufferedImage womenBI = drawText(women, Fs[2].font(), Fs[2].type(),
				FontSizes, Colors[2], 200, 0, thetas, true);

		SimplePositions[] sp = { nameSP, loveSP, womenSP };
		BufferedImage[] bis = { nameBI, loveBI, womenBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);

	}
}
