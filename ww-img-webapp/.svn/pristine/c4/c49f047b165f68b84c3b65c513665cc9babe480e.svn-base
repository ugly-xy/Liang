package com.zb.service.image.tuhao;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.zb.common.utils.DateUtil;
import com.zb.image.ChangeImage;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.*;

public class YingKeZhiBoTool extends BaseTool implements FiveDraw {

	public YingKeZhiBoTool(StorageService storageService) {
		super(storageService);
	}
	
	public YingKeZhiBoTool() {
	}

	public static void main(String[] args) throws IOException {
		String t0 = "1234.jpg";
		StorageService storageService = new StorageService();
		YingKeZhiBoTool tyt = new YingKeZhiBoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("12345678", "1234567", "720_1280.png",
				"A6.jpg", S[0], t0,null));
	}

	static String[] S = { "保时捷", "法拉利", "皇家游轮" };
	static String[] T = {
			"http://imgzb.zhuangdianbi.com/5757ec0c0cf21d3da53c7905",
			"http://imgzb.zhuangdianbi.com/5757ec210cf21d3da53c7927",
			"http://imgzb.zhuangdianbi.com/5757ec360cf21d3da53c794a" };
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
		pSP.buildHorizontalOffset(22).buildVerticalOffset(51);

		BufferedImage bg = super.getImg(T[idx]);
		SimplePositions bgSP = new SimplePositions();
		bgSP.buildHorizontalOffset(0).buildVerticalOffset(0);

		BufferedImage d = super.getImg(D[r.nextInt(2)]);
		SimplePositions dSP = new SimplePositions();
		dSP.buildHorizontalOffset(10).buildVerticalOffset(10);

		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(563).buildVerticalOffset(135);
		BufferedImage nameBI = drawText(":" + one, f, fs, c, 240, 0, 0, true);

		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(578).buildVerticalOffset(165);
		BufferedImage dateBI = drawText(
				DateUtil.dateFormat(new Date(), "yyyy.M.d"), f, fs, c, 240, 0,
				0, true);

		SimplePositions countSP = new SimplePositions();
		countSP.buildHorizontalOffset(84).buildVerticalOffset(148);
		BufferedImage countBI = drawText(two, f, fs - 2, c, 240, 0, 0, true);

		int po = r.nextInt(500000) + 100000;
		SimplePositions poSP = new SimplePositions();
		poSP.buildHorizontalOffset(98).buildVerticalOffset(78);
		BufferedImage poBI = drawText("" + po, f, fs - 8, c, 240, 0, 0, true);

		SimplePositions[] sp = { bgSP, pSP, countSP, poSP, nameSP, dateSP, dSP };
		BufferedImage[] bis = { bg, p, countBI, poBI, nameBI, dateBI, d };

		return super.getSaveFile(sp, bis, 0.85f, bi);

	}

}
