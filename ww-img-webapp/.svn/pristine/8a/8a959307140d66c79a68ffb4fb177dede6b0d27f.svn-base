package com.zb.service.image.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;

public class QQFeiCheTool extends BaseTool {

	public QQFeiCheTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath2 = "http://imgzb.zhuangdianbi.com/57146c990cf2df2e739ec908";
		StorageService storageService = new StorageService();
		QQFeiCheTool tyt = new QQFeiCheTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.drawImg("玩家昵称", tmpPath2));
	}

	/***
	 * @param name
	 *            姓名
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String name,  String tmpPath)
			throws IOException {
		int fontSize = 14;
		Color color = new Color(255, 218, 28);
		String fontStyle = "华文仿宋";
		int fontType = Font.BOLD;
		int left = 475;
		int height = 74;
		

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
