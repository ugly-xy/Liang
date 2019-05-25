package com.zb.service.image.game;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class WangZheRongYaoTool extends BaseTool implements TwoDraw {

	public WangZheRongYaoTool(StorageService storageService) {
		super(storageService);
	}

	public WangZheRongYaoTool() {
	}

	public static void main(String[] args) throws IOException {
		String p0 = "http://imgzb.zhuangdianbi.com/575e412c0cf20419fb16bd45";
		String p1 = "http://imgzb.zhuangdianbi.com/575e41410cf20419fb16bd62";
		StorageService storageService = new StorageService();
		WangZheRongYaoTool tyt = new WangZheRongYaoTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.draw("宝宝", S[1], p1,null));
	}

	static String[] S = { "五杀", "胜利" };

	@Override
	public String draw(String one, String style, String tmpPath,String count)
			throws IOException {
		if (S[0].equals(style)) {
			int left = 407 - (one.length() * 11) / 2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(148);
			BufferedImage nameBI = drawText(one, ZbFont.黑体加粗, 11, Color.white,
					300, 100, 0, true);

			SimplePositions[] sp = { nameSP };
			BufferedImage[] bis = { nameBI };
			return super.getSaveFile(sp, bis, 0.85f, tmpPath);
		} else {
			Random r = ThreadLocalRandom.current();
			Date d = new Date();

			String len = 10 + r.nextInt(3) + ":" + (10 + r.nextInt(50))
					+ "    " + DateUtil.dateFormat(d, "yyyy/MM/dd HH:mm");
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(55).buildVerticalOffset(226);
			BufferedImage nameBI = drawText(one, ZbFont.黑体加粗, 12, Color.yellow,
					300, 100, 0, true);

			SimplePositions lenSP = new SimplePositions();
			lenSP.buildHorizontalOffset(131).buildVerticalOffset(26);
			BufferedImage lenBI = drawText(len, ZbFont.黑体加粗, 12, Color.white,
					300, 100, 0, true);

			SimplePositions[] sp = { nameSP, lenSP };
			BufferedImage[] bis = { nameBI, lenBI };
			return super.getSaveFile(sp, bis, 0.85f, tmpPath);
		}
	}

}
