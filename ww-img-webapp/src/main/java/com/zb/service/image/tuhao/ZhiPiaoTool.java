package com.zb.service.image.tuhao;

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

public class ZhiPiaoTool extends BaseTool {

	public ZhiPiaoTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5732a3210cf2d5342be19cce";
		StorageService storageService = new StorageService();
		ZhiPiaoTool tyt = new ZhiPiaoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.drawImg("装点逼", tmpPath0));
	}

	public String drawImg(String name, String tmpPath) throws IOException {
		int fontSize = 18;
		Color color = new Color(40, 40, 40);
		String fontStyle = ZbFont.钟齐山文丰手写体.font();
		int fontType = Font.PLAIN;

		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(92).buildVerticalOffset(165);
		BufferedImage nameBI = drawText(name, fontStyle, fontType, fontSize,
				color, 240, 0, 0, true);

		SimplePositions[] sp = { nameSP };

		BufferedImage[] bis = { nameBI };

		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
