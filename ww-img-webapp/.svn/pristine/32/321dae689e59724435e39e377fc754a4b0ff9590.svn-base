package com.zb.service.image.love;

import java.awt.Color;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.ZbFont;

public class QianMingZhaoTool extends BaseTool {

	public QianMingZhaoTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String p0 = "http://imgzb.zhuangdianbi.com/571c436d0cf2a62e4b3af4c3";
		String p1 = "http://imgzb.zhuangdianbi.com/571c47950cf2a62e4b3af5ea";
		String p2 = "http://imgzb.zhuangdianbi.com/571c500d0cf2a62e4b3af807";
		String p3 = "http://imgzb.zhuangdianbi.com/571c5cad0cf2a62e4b3afb7f";
		StorageService storageService = new StorageService();
		QianMingZhaoTool tyt = new QianMingZhaoTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.drawImg("装点逼", "你是最棒的！", Names[3], p3));
	}

	private final static String[] Names = { "杨幂", "刘诗诗", "Angelababy", "TFBOYS" };

	private final static int[][] FontSizes = { { 20, 20 }, { 20, 20 },
			{ 20, 20 }, { 28, 28 } };

	private final static int[][] Lefts = { { 117, 107 }, { 198, 187 },
			{ 420, 420 }, { 273, 273 } };

	private final static int[][] Tops = { { 325, 360 }, { 452, 480 },
			{ 369, 397 }, { 458, 493 } };

	private final static Color[][] Colors = {
			{ new Color(0, 0, 0), new Color(0, 0, 0) },
			{ new Color(0, 0, 0), new Color(0, 0, 0) },
			{ new Color(0, 0, 0), new Color(0, 0, 0) },
			{ new Color(0, 0, 0), new Color(0, 0, 0) } };

	private final static ZbFont[][] Fs = {
			{ ZbFont.书体坊赵九江钢笔行书, ZbFont.书体坊赵九江钢笔行书 },
			{ ZbFont.书体坊赵九江钢笔行书, ZbFont.书体坊赵九江钢笔行书 },
			{ ZbFont.书体坊赵九江钢笔行书, ZbFont.书体坊赵九江钢笔行书 },
			{ ZbFont.书体坊赵九江钢笔行书, ZbFont.书体坊赵九江钢笔行书 } };

	double[][] thetas = { { -0.13, -0.09 }, { -0.20, -0.11 }, { 0, 0 },
			{ 0, 0 } };

	/***
	 * @param name
	 *            姓名
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String name, String content, String style,
			String tmpPath) throws IOException {
		int index = 0;
		for (int i = 0; i < Names.length; i++) {
			if (Names[i].equals(style)) {
				index = i;
				break;
			}
		}
		Color[] colors = Colors[index];
		String[] txts = { name, content };
		int[] curFSs = FontSizes[index];
		int[] curCLs = Lefts[index];
		int[] curTops = Tops[index];
		ZbFont[] curfs = Fs[index];
		boolean[] isHs = { false, false, false, false };
		boolean[] isRenderingHints = { true, true, true, true };
		return super.drawTextImg(txts, curFSs, curCLs, curTops, curfs, isHs,
				colors, thetas[index], isRenderingHints, tmpPath);
	}
}
