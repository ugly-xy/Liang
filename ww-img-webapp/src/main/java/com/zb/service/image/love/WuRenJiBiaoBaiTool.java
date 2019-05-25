package com.zb.service.image.love;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;

public class WuRenJiBiaoBaiTool extends BaseTool {

	public WuRenJiBiaoBaiTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath2 = "http://imgzb.zhuangdianbi.com/571477d50cf2df2e739eca85";
		StorageService storageService = new StorageService();
		WuRenJiBiaoBaiTool tyt = new WuRenJiBiaoBaiTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.drawImg("装点", "我爱你", tmpPath2));
	}

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
		int fontSize = 32;
		Color color = new Color(255, 255, 255);
		String fontStyle = "黑体";
		int fontType = Font.BOLD;
		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(560).buildVerticalOffset(295);
		BufferedImage nameBI = drawText(name, fontStyle, fontType, fontSize,
				color, 40, 240, 0.1, true);

		// 姓名
		SimplePositions contentSP = new SimplePositions();
		contentSP.buildHorizontalOffset(520).buildVerticalOffset(295);
		BufferedImage contentBI = drawText(content, fontStyle, fontType,
				fontSize, color, 40, 240, 0.1, true);

		SimplePositions[] sp = { nameSP, contentSP };

		BufferedImage[] bis = { nameBI, contentBI };

		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
