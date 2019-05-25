package com.zb.service.image.certificate;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;


public class WuShuDuanWeiTool extends BaseTool {

	public WuShuDuanWeiTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57398b1d0cf24cb3ea701d85";
		StorageService storageService = new StorageService();
		WuShuDuanWeiTool tyt = new WuShuDuanWeiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.drawImg("装点逼", tmpPath0));
	}

	public String drawImg(String name,  String tmpPath)
			throws IOException {
		int fontSize = 22;
		Color color = new Color(0, 0, 0);
		String fontStyle = ZbFont.书体坊赵九江钢笔行书.font();
		int fontType = Font.PLAIN;
		int left = 110 + (5-name.length())*12;
		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(left).buildVerticalOffset(304);
		BufferedImage nameBI = drawText(name, fontStyle, fontType, fontSize,
				color, 240, 0, 0, true);
		SimplePositions[] sp = { nameSP };

		BufferedImage[] bis = { nameBI };

		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
