package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.jhlabs.image.*;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;

public class DanShenGouTool extends BaseTool {

	public DanShenGouTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String p0 = "http://imgzb.zhuangdianbi.com/573ebc7a0cf2f63f5c3bf214";// http://imgzb.zhuangdianbi.com/573c0f0a0cf2cc7298e6baaf";// http://imgzb.zhuangdianbi.com/573c077d0cf2cc7298e6b738";
		StorageService storageService = new StorageService();
		DanShenGouTool tyt = new DanShenGouTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.drawImg(
				"http://imgzb.zhuangdianbi.com/571f136f0cf213c35990a5f8", p0));
	}

	private final static String[] Names = { "嫁给他", "要娶她" };

	/***
	 * @param name
	 *            姓名
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String myPic, String tmpPath) throws IOException {

		SimplePositions contentSP = new SimplePositions();
		contentSP.buildHorizontalOffset(0).buildVerticalOffset(0);
		BufferedImage contentBI = super.compressImage(myPic, 230, 230);

		SimplePositions[] sp = { contentSP };
		BufferedImage[] bis = { contentBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);

	}

}
