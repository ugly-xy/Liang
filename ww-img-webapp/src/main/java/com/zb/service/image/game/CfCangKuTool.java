package com.zb.service.image.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;

public class CfCangKuTool extends BaseTool {

	public CfCangKuTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath1 = "http://imgzb.zhuangdianbi.com/57145d310cf2df2e739ec605";
		String tmpPath2 = "http://imgzb.zhuangdianbi.com/57145d590cf2df2e739ec609";
		String tmpPath3 = "http://imgzb.zhuangdianbi.com/57146f050cf2df2e739ec971";
		StorageService storageService = new StorageService();
		CfCangKuTool tyt = new CfCangKuTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.drawImg("玩家昵称", "CF土豪3", tmpPath3));
	}

	/***
	 * @param name
	 *            姓名
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String name, String tmp, String tmpPath)
			throws IOException {
		int fontSize = 14;
		Color color = new Color(89, 181, 80);
		String fontStyle = "华文仿宋";
		int fontType = Font.BOLD;
		int left = 88;
		int height = 92;
		if ("CF土豪2".equals(tmp)) {
			left = 152;
			height = 120;
			color = new Color(29, 171, 9);
			fontSize = 16;
		} else if ("CF土豪3".equals(tmp)) {
			left = 108;
			height = 477;
			color = new Color(234, 6, 21);
			fontSize = 16;
		}

		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(left).buildVerticalOffset(height);
		BufferedImage nameBI = drawText(name, fontStyle, fontType, fontSize,
				color, 360, 140, 0, true);

		SimplePositions[] sp = { nameSP };

		BufferedImage[] bis = { nameBI };

		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
