package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;

public class XiaoYouZaoAnTool extends BaseTool {

	public XiaoYouZaoAnTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5732b4550cf2d5342be1a50b";
		String pic = "http://imgzb.zhuangdianbi.com/5732b44b0cf2d5342be1a502";
		StorageService storageService = new StorageService();
		XiaoYouZaoAnTool tyt = new XiaoYouZaoAnTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.drawImg("装点逼", "金泉广场", pic, tmpPath0));
	}

	public String drawImg(String name, String addr, String pic, String tmpPath)
			throws IOException {
		int fontSize = 30;
		int fontSize2 = 24;
		Color color = new Color(112, 112, 112);
		
		Color color2 = new Color(87, 107, 149);
		String fontStyle = ZbFont.苹方.font();
		int fontType = Font.PLAIN;

		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(122).buildVerticalOffset(327);
		BufferedImage nameBI = drawText(name, fontStyle, Font.PLAIN, fontSize,
				color2, 240, 0, 0, true);

		// 地址
		SimplePositions addrSP = new SimplePositions();
		addrSP.buildHorizontalOffset(122).buildVerticalOffset(749);
		BufferedImage addrBI = drawText(addr, fontStyle, fontType, fontSize2,
				color, 240, 0, 0, true);

		// 头像
		SimplePositions imageSP = new SimplePositions();
		imageSP.buildHorizontalOffset(20).buildVerticalOffset(330);
		BufferedImage imageBI = compressImage(pic, 85, 85);

		SimplePositions[] sp = { nameSP, addrSP, imageSP };

		BufferedImage[] bis = { nameBI, addrBI, imageBI };

		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
