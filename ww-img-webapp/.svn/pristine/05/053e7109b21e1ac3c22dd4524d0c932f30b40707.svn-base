package com.zb.service.image.game;

import java.awt.Color;
import java.io.IOException;
import java.util.Date;

import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.ZbFont;

public class QBiChongZhiTool extends BaseTool {

	public QBiChongZhiTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/571c2da10cf2a62e4b3aef9d";
		// String tmpPath1 =
		// "http://imgzb.zhuangdianbi.com/571b33b10cf2a62e4b3acf2e";
		StorageService storageService = new StorageService();
		QBiChongZhiTool tyt = new QBiChongZhiTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.drawImg("装点逼", "123211", "2000", tmpPath0));
	}

	//private final static String[] Names = { "苍井空" };

	private final static int[][] FontSizes = { { 20, 20, 20, 20 } };

	private final static int[][] Lefts = { { 76, 252, 0, 252 } };

	private final static int[][] Tops = { { 57, 156, 156, 181 } };

	private final static Color[][] Colors = { { new Color(190, 15, 7),
			new Color(190, 15, 7), new Color(67, 77, 71), new Color(67, 77, 71) } };

	private final static ZbFont[][] Fs = { { ZbFont.微软雅黑, ZbFont.微软雅黑,
			ZbFont.微软雅黑, ZbFont.微软雅黑 } };

	/***
	 * @param name
	 *            姓名
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String name, String content, String amount,
			String tmpPath) throws IOException {
		int index = 0;
		Color[] colors = Colors[index];
		String[] txts = { name + "(" + content + ")", amount, "Q币",
				DateUtil.dateFormatLongCN(new Date()) };
		int[] curFSs = FontSizes[index];
		int[] curCLs = Lefts[index];
		int[] curTops = Tops[index];
		ZbFont[] curfs = Fs[index];
		int l3 = amount.length() * 14 + Lefts[index][1];
		Lefts[index][2] = l3;
		boolean[] isHs = { false, false, false, false };
		double[] thetas = { 0, 0, 0, 0 };
		boolean[] isRenderingHints = { true, true, true, true };
		return super.drawTextImg(txts, curFSs, curCLs, curTops, curfs, isHs,
				colors, thetas, isRenderingHints, tmpPath);
	}
}
