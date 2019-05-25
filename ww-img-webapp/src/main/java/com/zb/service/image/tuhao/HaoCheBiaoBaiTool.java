package com.zb.service.image.tuhao;

import java.awt.Color;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.ZbFont;

public class HaoCheBiaoBaiTool extends BaseTool {

	public HaoCheBiaoBaiTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String p0 = "http://imgzb.zhuangdianbi.com/571c8d750cf2a62e4b3b0761";
		StorageService storageService = new StorageService();
		HaoCheBiaoBaiTool tyt = new HaoCheBiaoBaiTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.drawImg("装点逼", "我爱你", p0));
	}

	// private final static String[] Names = { "苍井空", "黄子韬", "陈冠希", "鹿晗" };

	private final static int[][] FontSizes = { { 42, 42 }, };

	private final static int[][] Lefts = { { 165, 168 } };

	private final static int[][] Tops = { { 361, 420 } };

	private final static Color[][] Colors = { { new Color(55, 48, 48),
			new Color(55, 48, 48) } };

	private final static ZbFont[][] Fs = { { ZbFont.徐金如硬笔行楷X, ZbFont.徐金如硬笔行楷X } };

	double[][] thetas = { { -0.01, -0.01 } };

	boolean[][] isHs = { { false, false } };

	/***
	 * @param name
	 *            姓名
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String name, String content, String tmpPath)
			throws IOException {
		int index = 0;
		// for (int i = 0; i < Names.length; i++) {
		// if (Names[i].equals(style)) {
		// index = i;
		// break;
		// }
		// }
		String[] txts = { name, content };
		boolean[] isRenderingHints = { true, true };
		return super.drawTextImg(txts, FontSizes[index], Lefts[index],
				Tops[index], Fs[index], isHs[index], Colors[index],
				thetas[index], isRenderingHints, tmpPath);

	}
}
