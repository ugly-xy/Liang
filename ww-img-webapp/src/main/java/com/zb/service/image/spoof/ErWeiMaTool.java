package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.zb.service.barcode.QRCodeUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;

public class ErWeiMaTool extends BaseTool {

	public ErWeiMaTool(StorageService storageService) {
		super(storageService);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		String tmpPath = "http://imgzb.zhuangdianbi.com/57132dc20cf2df2e739ea731";
		StorageService storageService = new StorageService();
		ErWeiMaTool tyt = new ErWeiMaTool(storageService);
		tyt.isDebug(true);
		tyt.drawImg("陆家嘴视频，要的赶紧下，你懂的", "外表平静的你，那颗骚动的心又在澎湃！");
	}

	/***
	 * @param ruguo
	 *            如果
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws Exception
	 */
	public String drawImg(String title, String content) throws Exception {

		int width = 600;
		BufferedImage cbi = super.createImage(width, 600, Color.WHITE);

		BufferedImage conbi = QRCodeUtil.encodeBI(content + "[下载“装点逼”玩！]");
		SimplePositions conSP = new SimplePositions();
		conSP.buildHorizontalOffset(145).buildVerticalOffset(200);

		ZbFont zbf = ZbFont.苹方;
		int fs = 28;

		int left = width / 2 - title.length() * fs / 2;
		if (left < 80) {
			left = 80;
		}
		BufferedImage titlebi = super.drawText(title, zbf.font(), zbf.type(),
				fs, new Color(0, 0, 0), 440);
		SimplePositions titleSP = new SimplePositions();
		titleSP.buildHorizontalOffset(left).buildVerticalOffset(110);

		SimplePositions[] sp = { titleSP, conSP, };
		BufferedImage[] bis = { titlebi, conbi };
		return super.getSaveFile(sp, bis, 0.9f, cbi);
	}

}
