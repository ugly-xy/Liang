package com.zb.service.image.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;

public class CFTuFuTool extends BaseTool {

	public CFTuFuTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath3 = "http://imgzb.zhuangdianbi.com/571471380cf2df2e739ec9e2";
		StorageService storageService = new StorageService();
		CFTuFuTool tyt = new CFTuFuTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.drawImg("屠夫", "玩家昵称", tmpPath3));
	}

	/***
	 * @param name
	 *            姓名
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String team, String name, String tmpPath)
			throws IOException {
		int fontSize = 18;
		Color color = new Color(184, 184, 184);
		String fontStyle = "华文仿宋";
		int fontType = Font.BOLD;

		// 姓名
		SimplePositions teamSP = new SimplePositions();
		teamSP.buildHorizontalOffset(284).buildVerticalOffset(453);
		BufferedImage teamBI = drawText(team, fontStyle, fontType, fontSize,
				color, 360, 140, 0, true);

		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(284).buildVerticalOffset(482);
		BufferedImage nameBI = drawText(name, fontStyle, fontType, fontSize,
				color, 360, 140, 0, true);

		SimplePositions[] sp = { teamSP,nameSP };

		BufferedImage[] bis = { teamBI ,nameBI };

		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
