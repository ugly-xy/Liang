package com.zb.service.image.love;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.jhlabs.image.*;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;

public class WenShenTool extends BaseTool {

	public WenShenTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String p0 = "http://imgzb.zhuangdianbi.com/573d66430cf2cf438c9e1f25";// http://imgzb.zhuangdianbi.com/573c0f0a0cf2cc7298e6baaf";// http://imgzb.zhuangdianbi.com/573c077d0cf2cc7298e6b738";
		String p1 = "http://imgzb.zhuangdianbi.com/573d4f8c0cf2cf438c9e0fe8";
		String p2 = "http://imgzb.zhuangdianbi.com/573d6c7c0cf2cf438c9e22be";
		String p3 = "http://imgzb.zhuangdianbi.com/573d6f0a0cf2cf438c9e2466";
		String p4 = "http://imgzb.zhuangdianbi.com/573d7ac00cf2cf438c9e2b0c";
		String p5 = "http://imgzb.zhuangdianbi.com/573d73ed0cf2cf438c9e277b";
		StorageService storageService = new StorageService();
		WenShenTool tyt = new WenShenTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.drawImg("BaoWenKe", Names[4], p4));
	}

	private final static String[] Names = { "美女锁骨", "美女后背", "美女后肩", "帅哥蝎子",
			"帅哥五线谱", "帅哥鬼面" };

	private final static int[] FontSizes = { 36, 28, 22, 30, 36, 28 };

	private final static int[] Lefts = { 325, 280, 270, 155, 410, 138 };

	private final static int[] Tops = { 375, 462, 240, 500, 252, 440 };

	private final static Color[] Colors = { new Color(56, 33, 15),
			new Color(19, 12, 12), new Color(65, 45, 30),
			new Color(56, 33, 15), new Color(19, 12, 12), new Color(55, 55, 62) };

	private final static ZbFont[] Fs = { ZbFont.CAI160, ZbFont.CAI178,
			ZbFont.CAI185, ZbFont.CAI160, ZbFont.CAI185, ZbFont.CAI157 };

	double[] thetas = { -0.02, 0, -0.02, 0.09, 0.03, -0.03 };

	boolean[] isHs = { false, false, false, false, false, false };

	/***
	 * @param name
	 *            姓名
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String name, String style, String tmpPath)
			throws IOException {
		int idx = 0;
		for (int i = 0; i < Names.length; i++) {
			if (Names[i].equals(style)) {
				idx = i;
				break;
			}
		}
		int nameLen = name.length();
		if (idx == 4) {
			name = name.toLowerCase();
		} else if (nameLen < 8) {
			name = name + " Love";
			nameLen = nameLen + 5;
		}
		int left = Lefts[idx];

		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(left).buildVerticalOffset(Tops[idx]);
		BufferedImage nameBI = drawText(name, Fs[idx].font(), Fs[idx].type(),
				FontSizes[idx], Colors[idx], nameLen * (FontSizes[idx] + 1), 0,
				thetas[idx], false);

		LensBlurFilter df = new LensBlurFilter();
		df.setBloom(8);
		df.setRadius(3.2f);
		BufferedImage name2BI = df.filter(nameBI, null);

		BufferedImage nameBI2 = drawText(name, Fs[idx].font(), Fs[idx].type(),
				FontSizes[idx] - 1, Colors[idx], nameLen * (FontSizes[idx]), 0,
				thetas[idx], true);

		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(left + 1).buildVerticalOffset(
				Tops[idx] + 1);

		// SimplePositions contentSP = new SimplePositions();
		// contentSP.buildHorizontalOffset(left2)
		// .buildVerticalOffset(Tops[idx][1]);
		//
		// SimplePositions contentSP2 = new SimplePositions();
		// contentSP2.buildHorizontalOffset(left2 + 1).buildVerticalOffset(
		// Tops[idx][1] + 1);
		//
		// BufferedImage contentBI = drawText(content, Fs[idx].font(),
		// Fs[idx].type(), contentLen * (FontSizes[idx] + 5), Colors[idx],
		// 200, 0, thetas[idx], false);
		//
		// BufferedImage content2BI = df.filter(contentBI, null);
		//
		// BufferedImage contentBI2 = drawText(content, Fs[idx].font(),
		// Fs[idx].type(), FontSizes[idx] - 1, Colors[idx], contentLen
		// * (FontSizes[idx] + 5), 0, thetas[idx], true);

		SimplePositions[] sp = { nameSP, nameSP2 };
		BufferedImage[] bis = { name2BI, nameBI2 };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);

	}

}
