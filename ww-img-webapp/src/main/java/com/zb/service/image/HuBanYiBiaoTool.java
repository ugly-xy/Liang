package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;

public class HuBanYiBiaoTool extends BaseTool {

	public HuBanYiBiaoTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath = "http://imgzb.zhuangdianbi.com/56f5f89e0cf23c9921eba6d5";
		StorageService storageService = new StorageService();
		HuBanYiBiaoTool tyt = new HuBanYiBiaoTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.drawImg("你哥傻逼", tmpPath));
	}

	/***
	 * @param name
	 *            姓名
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String name, String tmpPath) throws IOException {

		int fontSize = 72;
		Color color = new Color(10, 10, 10);
		String fontStyle = "黑体";
		int fontType = Font.BOLD;
		int left = 200 + (4 - name.length()) * 30;

		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(left).buildVerticalOffset(545);
		BufferedImage nameBI = drawText(name, fontStyle, fontType, fontSize,
				color, 360, 140, 0.14, true);

		nameBI = doTwist(nameBI, 4, 1.4, true);

		SimplePositions[] sp = { nameSP };

		BufferedImage[] bis = { nameBI };

		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
