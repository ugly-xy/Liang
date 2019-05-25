package com.zb.service.image.tuhao;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.zb.image.ChangeImage;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.*;

public class HuaJiaoZhiBoTool extends BaseTool implements FiveDraw {

	public HuaJiaoZhiBoTool(StorageService storageService) {
		super(storageService);
	}

	public HuaJiaoZhiBoTool() {

	}

	public static void main(String[] args) throws IOException {
		String t0 = "1234.jpg";
		StorageService storageService = new StorageService();
		HuaJiaoZhiBoTool tyt = new HuaJiaoZhiBoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼", "123456", "720_1280.png", "A6.jpg",
				S[4], t0,null));
	}

	static String[] S = { "带我回家", "土豪带你飞", "兰博基尼", "梦幻水晶鞋", "求婚钻戒" };
	static String[] T = {
			"http://imgzb.zhuangdianbi.com/5757eafc0cf21d3da53c7772",
			"http://imgzb.zhuangdianbi.com/5757eb190cf21d3da53c779b",
			"http://imgzb.zhuangdianbi.com/5757eb260cf21d3da53c77a9",
			"http://imgzb.zhuangdianbi.com/5757eb360cf21d3da53c77bf",
			"http://imgzb.zhuangdianbi.com/5757eb4b0cf21d3da53c77df" };

	static String[] D = {
			"http://imgzb.zhuangdianbi.com/5757eb600cf21d3da53c77fa",
			"http://imgzb.zhuangdianbi.com/5757eb960cf21d3da53c7855" };

	static ZbFont f = ZbFont.苹方;
	static int fs = 26;
	static Color c = Color.white;

	@Override
	public String draw(String one, String two, String three, String four,
			String five, String tmpPath,String count) throws IOException {
		Random r = ThreadLocalRandom.current();
		int idx = 0;
		for (int i = 0; i < S.length; i++) {
			if (S[i].equals(five)) {
				idx = i;
			}
		}
		BufferedImage bi = super.compressImage(three, 720, 1280);

		BufferedImage p = super.getImg(four);
		p = ChangeImage.resizeCrop(p, 62);
		p = ChangeImage.makeRoundedCorner(p, 180);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(20).buildVerticalOffset(48);

		BufferedImage bg = super.getImg(T[idx]);
		SimplePositions bgSP = new SimplePositions();
		bgSP.buildHorizontalOffset(0).buildVerticalOffset(0);

		BufferedImage d = super.getImg(D[r.nextInt(2)]);
		SimplePositions dSP = new SimplePositions();
		dSP.buildHorizontalOffset(10).buildVerticalOffset(10);

		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(90).buildVerticalOffset(41);
		BufferedImage nameBI = drawText(one, f, fs, c, 240, 0, 0, true);

		SimplePositions countSP = new SimplePositions();
		countSP.buildHorizontalOffset(102).buildVerticalOffset(140);
		BufferedImage countBI = drawText(two, f, fs - 2, c, 240, 0, 0, true);

		int po = r.nextInt(500000) + 5000;
		SimplePositions poSP = new SimplePositions();
		poSP.buildHorizontalOffset(90).buildVerticalOffset(80);
		BufferedImage poBI = drawText("" + po, f, fs - 4, c, 240, 0, 0, true);

		SimplePositions[] sp = { bgSP, pSP, nameSP, countSP, poSP, dSP };
		BufferedImage[] bis = { bg, p, nameBI, countBI, poBI, d };

		return super.getSaveFile(sp, bis, 0.85f, bi);

	}

}
