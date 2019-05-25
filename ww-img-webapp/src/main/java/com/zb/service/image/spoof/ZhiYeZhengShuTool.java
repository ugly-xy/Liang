package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;

public class ZhiYeZhengShuTool extends BaseTool {

	public ZhiYeZhengShuTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String p0 = "http://imgzb.zhuangdianbi.com/571d90cd0cf25d670fc7d68b";
		StorageService storageService = new StorageService();
		ZhiYeZhengShuTool tyt = new ZhiYeZhengShuTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.drawImg("刘国强", p0));
	}

	// private final static String[] Names = { "苍井空", "黄子韬", "陈冠希", "鹿晗" };

	private final static int[][] FontSizes = { { 14, 14, 14, 14 }, };

	private final static int[][] Lefts = { { 100, 100, 293, 474 } };

	private final static int[][] Tops = { { 326, 345, 345, 345 } };

	private final static Color[][] Colors = { { new Color(26, 26, 26),
			new Color(33, 33, 33), new Color(44, 44, 44), new Color(55, 55, 55) } };

	private final static ZbFont[][] Fs = { { ZbFont.宋体, ZbFont.宋体, ZbFont.宋体,
			ZbFont.宋体 } };

	double[][] thetas = { { 0, 0, 0, 0 } };

	boolean[][] isHs = { { false, false, false, false } };

	/***
	 * @param name
	 *            姓名
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String name, String tmpPath) throws IOException {
		int index = 0;
		// for (int i = 0; i < Names.length; i++) {
		// if (Names[i].equals(style)) {
		// index = i;
		// break;
		// }
		// }
		String date = DateUtil.dateFormatDian(new Date());
		String[] txts = { name, date, date, date };
		boolean[] isRenderingHints = { true, true, true, true };
		return super.drawTextImg(txts, FontSizes[index], Lefts[index],
				Tops[index], Fs[index], isHs[index], Colors[index],
				thetas[index], isRenderingHints, tmpPath);

	}
}
