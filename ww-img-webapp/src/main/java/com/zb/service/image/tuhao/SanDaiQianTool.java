package com.zb.service.image.tuhao;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;

public class SanDaiQianTool extends BaseTool {

	public SanDaiQianTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath2 = "http://imgzb.zhuangdianbi.com/57148e980cf2df2e739eccc6";
		StorageService storageService = new StorageService();
		SanDaiQianTool tyt = new SanDaiQianTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.drawImg("装点逼", tmpPath2));
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
		int fontSize = 36;
		Color color = new Color(0, 0, 0);
		String fontStyle = "罗西钢笔行楷 常规";
		int fontType = Font.PLAIN;
		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(80).buildVerticalOffset(320);
		BufferedImage nameBI = drawText(name, fontStyle, fontType, fontSize,
				color, 240, 68, 0.22, true);

		SimplePositions[] sp = { nameSP };

		BufferedImage[] bis = { nameBI };

		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
