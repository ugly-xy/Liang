package com.zb.service.image.love;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.jhlabs.image.*;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;

public class GongJiaoCheTool extends BaseTool {

	public GongJiaoCheTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String p0 = "http://imgzb.zhuangdianbi.com/573d9cab0cf2754ef3f104c0";// http://imgzb.zhuangdianbi.com/573c0f0a0cf2cc7298e6baaf";// http://imgzb.zhuangdianbi.com/573c077d0cf2cc7298e6b738";
		StorageService storageService = new StorageService();
		GongJiaoCheTool tyt = new GongJiaoCheTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.drawImg("装点逼我爱你！", p0));
	}

	// private final static String[] Names = { "美女锁骨", "美女后背", "美女后肩", "帅哥蝎子",
	// "帅哥五线谱", "帅哥鬼面" };

	private final static int FontSizes = 64;

	private final static int left = 320;

	private final static int top = 240;

	private final static Color color = new Color(255, 255, 0);

	private final static ZbFont Fs = ZbFont.方正像素24;

	private final static double theta = -0.02;


	/***
	 * @param name
	 *            姓名
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String content, String tmpPath) throws IOException {

		int curleft = left - content.length() * (FontSizes / 2);
		SimplePositions contentSP = new SimplePositions();
		contentSP.buildHorizontalOffset(curleft).buildVerticalOffset(top);
		BufferedImage contentBI = drawText(content, Fs.font(), Fs.type(),
				FontSizes, color, 2000, 0, theta, true);

		SimplePositions[] sp = { contentSP };
		BufferedImage[] bis = { contentBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);

	}

}
