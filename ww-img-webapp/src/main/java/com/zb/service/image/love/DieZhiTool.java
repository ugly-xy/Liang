package com.zb.service.image.love;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;

public class DieZhiTool extends BaseTool {

	public DieZhiTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String p0 = "http://imgzb.zhuangdianbi.com/574fe03b0cf28eea2530d06b";// http://imgzb.zhuangdianbi.com/573c0f0a0cf2cc7298e6baaf";// http://imgzb.zhuangdianbi.com/573c077d0cf2cc7298e6b738";

		StorageService storageService = new StorageService();
		DieZhiTool tyt = new DieZhiTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.drawImg("鲍文科", p0));
	}

	static final ZbFont f = ZbFont.土肥圆;
	static final int fs = 16;
	Color c = new Color(150,66,188);

	/***
	 * @param name
	 *            姓名
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String name, String tmpPath) throws IOException {

		name = "to:" + name;
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(61).buildVerticalOffset(132);
		BufferedImage nameBI = drawText(name, f, fs, c, 200, 0, 0, true);

		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(324).buildVerticalOffset(140);
		BufferedImage nameBI2 = drawText(name, f, fs-1, c, 200, 0, 0, true);

		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(568).buildVerticalOffset(500);

		SimplePositions[] sp = { nameSP, nameSP2, nameSP3 };
		BufferedImage[] bis = { nameBI, nameBI2, nameBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}

}
