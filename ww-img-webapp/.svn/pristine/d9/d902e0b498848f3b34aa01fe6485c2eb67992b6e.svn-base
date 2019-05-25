package com.zb.service.image.love;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;

public class MingXingShiPinTool extends BaseTool {

	public MingXingShiPinTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String p0 = "http://imgzb.zhuangdianbi.com/5739c2e90cf237494838d1e2";
		String p1 = "http://imgzb.zhuangdianbi.com/5739c31d0cf237494838d22d";
		StorageService storageService = new StorageService();
		MingXingShiPinTool tyt = new MingXingShiPinTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.drawImg(p1, p0));
	}

	private final static String[] Names = { "蒋欣", "刘涛" };

	private final static String DOWN = "http://imgzb.zhuangdianbi.com/5739c45b0cf237494838d3be";

	/***
	 * @param name
	 *            姓名
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String myPic, String tmpPath) throws IOException {
		// int index = 0;
		// for (int i = 0; i < Names.length; i++) {
		// if (Names[i].equals(style)) {
		// index = i;
		// break;
		// }
		// }

		SimplePositions downSP = new SimplePositions();
		downSP.buildHorizontalOffset(0).buildVerticalOffset(0);
		BufferedImage downbi = super.compressImage(DOWN, 720, 1245);

		SimplePositions msp = new SimplePositions();
		msp.buildHorizontalOffset(538).buildVerticalOffset(6);
		BufferedImage mbi = super.compressImage(myPic, 178, 320);

		Random random = ThreadLocalRandom.current();
		String time = random.nextInt(3) + "" + random.nextInt(10) + ":"
				+ random.nextInt(6) + random.nextInt(10);
		SimplePositions timeSP = new SimplePositions();
		timeSP.buildHorizontalOffset(320).buildVerticalOffset(948);
		BufferedImage timeBI = drawText(time, ZbFont.苹方.font(),
				ZbFont.苹方.type(), 30, Color.white, 240, 0, 0, true);
		SimplePositions[] sp = { downSP, msp, timeSP };

		BufferedImage[] bis = { downbi, mbi, timeBI };

		return super.getSaveFile(sp, bis, 0.85f, tmpPath);

	}
}
