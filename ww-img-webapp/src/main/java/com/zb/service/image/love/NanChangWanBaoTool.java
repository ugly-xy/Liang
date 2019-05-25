package com.zb.service.image.love;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.zb.common.utils.DateUtil;
import com.zb.image.ChangeImage;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.*;

public class NanChangWanBaoTool extends BaseTool implements FourDraw {

	public NanChangWanBaoTool(StorageService storageService) {
		super(storageService);
	}

	public NanChangWanBaoTool() {

	}

	public static void main(String[] args) throws IOException {
		String t0 = "185.png";
		StorageService storageService = new StorageService();
		NanChangWanBaoTool tyt = new NanChangWanBaoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("宝宝", "我爱你", "不知道为什么，突然就想这么对你说",
				"a10.jpg", t0,null));
	}

	static ZbFont f = ZbFont.宋体加粗;
	static int fs = 48;
	static Color c = Color.black;

	// static double r = -0.01;

	@Override
	public String draw(String one, String two, String three, String four,
			String tmpPath,String count) throws IOException {

		BufferedImage p = super.getImg(four);
		p = ChangeImage.resizeCrop(p, 122);
		p = ChangeImage.makeRoundedCorner(p, 180);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(209).buildVerticalOffset(176);

		int left = 270 - one.length() * fs / 2;
		SimplePositions oneSP = new SimplePositions();
		oneSP.buildHorizontalOffset(left).buildVerticalOffset(290);
		BufferedImage oneBI = drawText(one, f, fs, c, 240, 0, 0, true);

		left = 270 - two.length() * (100) / 2;
		SimplePositions twoSP = new SimplePositions();
		twoSP.buildHorizontalOffset(left).buildVerticalOffset(380);
		BufferedImage twoBI = drawText(two, f, 100, c, 360, 0, 0, true);

		SimplePositions threeSP = new SimplePositions();
		threeSP.buildHorizontalOffset(90).buildVerticalOffset(520);
		BufferedImage threeBI = drawText(three, f, 45, c, 370, 0, 0, true);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日\n\n       E",
				Locale.CHINA);
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(53).buildVerticalOffset(40);
		BufferedImage dateBI = drawText(sdf.format(new Date()), ZbFont.宋体, 10,
				c, 240, 100, -0.02, true);

		SimplePositions[] sp = { pSP, oneSP, twoSP, threeSP, dateSP };
		BufferedImage[] bis = { p, oneBI, twoBI, threeBI, dateBI };

		return super.getSaveFile(sp, bis, 0.85f, tmpPath);

	}

}
