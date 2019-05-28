package com.zb.service.image.certificate;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import com.zb.common.utils.DateUtil;
import com.zb.common.utils.PinYinUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class RiYuZhengTool extends BaseTool implements TwoDraw {

	public RiYuZhengTool(StorageService storageService) {
		super(storageService);
	}

	public RiYuZhengTool() {
	}

	public static void main(String[] args) throws IOException {
		// String tmpPath0 =
		// "http://imgzb.zhuangdianbi.com/5732a2a80cf2d5342be19ca8";
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5764dd830cf2cb9eefcc0375";
		StorageService storageService = new StorageService();
		RiYuZhengTool tyt = new RiYuZhengTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼", "" + System.currentTimeMillis(),
				tmpPath0,null));
	}

	static ZbFont zbfont = ZbFont.Times;
	static int fontSize = 18;
	static Color color = new Color(0, 0, 0);

	@Override
	public String draw(String one, String two, String tmpPath,String count)
			throws IOException {

		one = PinYinUtil.toPinyinAllFirstUp(one);

		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(146).buildVerticalOffset(235);
		BufferedImage nameBI = drawText(one, zbfont, fontSize, color, 240, 200,
				-0.07, true, 20, 20);

		// date
		// String date = DateUtil.dateFormat(new Date(), "yyyy年M月d日");
		// SimplePositions dateSP = new SimplePositions();
		// dateSP.buildHorizontalOffset(142).buildVerticalOffset(274);
		// BufferedImage dateBi = drawText(date, zbfont, fontSize,
		// color, 240, 200, -0.07, true,20,20);
		Long l = System.currentTimeMillis() - 20 * 365 * 24 * 3600 * 1000;
		try {
			l = Long.parseLong(two);
		} catch (Exception e) {
		}
		two = DateUtil.dateFormatDian(new Date(l));
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(l);

		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(151).buildVerticalOffset(275);
		BufferedImage nameBI2 = drawText(two, zbfont, fontSize, color, 240,
				200, -0.07, true, 20, 20);

		SimplePositions[] sp = { nameSP, nameSP2 };

		BufferedImage[] bis = { nameBI, nameBI2 };

		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
