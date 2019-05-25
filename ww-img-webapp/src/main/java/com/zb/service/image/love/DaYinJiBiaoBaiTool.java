package com.zb.service.image.love;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import com.jhlabs.image.FieldWarpFilter;
import com.jhlabs.image.GrayscaleFilter;
import com.jhlabs.image.PerspectiveFilter;
import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;

public class DaYinJiBiaoBaiTool extends BaseTool {


	public DaYinJiBiaoBaiTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5742bc7a0cf28d2e900fdb48";
		StorageService storageService = new StorageService();
		DaYinJiBiaoBaiTool tyt = new DaYinJiBiaoBaiTool(storageService);
		tyt.isDebug(true);
		System.out
				.println(tyt.drawImg("装点逼", "美女", "最美了", "123.png", tmpPath0));
	}

	static ZbFont f = ZbFont.苹方;
	static int fs = 20;
	static Color c = new Color(0, 0, 0);

	public String drawImg(String from, String to, String content, String pic,
			String tmpPath) throws IOException {

		SimplePositions fromSP = new SimplePositions();
		fromSP.buildHorizontalOffset(291).buildVerticalOffset(110);
		BufferedImage formBI = drawText(from, f.font(), f.type(), fs, c, 360,
				140, 0, true);

		SimplePositions toSP = new SimplePositions();
		toSP.buildHorizontalOffset(260).buildVerticalOffset(136);
		BufferedImage toBI = drawText(to, f.font(), f.type(), fs, c, 360, 140,
				0, true);

		SimplePositions contentSP = new SimplePositions();
		contentSP.buildHorizontalOffset(218).buildVerticalOffset(163);
		BufferedImage contentBi = drawText(content, f.font(), f.type(), fs, c,
				360, 140, 0, true);

		String date = DateUtil.dateFormatShortCN(new Date());
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(218).buildVerticalOffset(71);
		BufferedImage dateBi = drawText(date, f.font(), f.type(), fs, c, 360,
				140, 0, true);

		SimplePositions picSP = new SimplePositions();
		picSP.buildHorizontalOffset(220).buildVerticalOffset(208);

		BufferedImage bi = super.compressImage(pic, 257, 254);
		GrayscaleFilter f = new GrayscaleFilter();
		bi = f.filter(bi, null);

		BufferedImage buffImg = GC.createCompatibleImage(261, 260,
				Transparency.TRANSLUCENT);
		Graphics2D g = buffImg.createGraphics();
		g.setBackground(new Color(255, 255, 255));
		g.setColor(new Color(255, 255, 255));
		g.drawImage(bi, 1, 0, 257, 254, null);
		// g.clipRect(0,);
		// SimplePositions curSP = new SimplePositions();

		// curSP.buildHorizontalOffset(8).buildVerticalOffset(0);
		// bi = Thumbnails.of(buffImg).watermark(curSP, bi, 1.0f).scale(1.0)
		// .asBufferedImage();
		PerspectiveFilter pf = new PerspectiveFilter();
		pf.setCorners(-20, -3, bi.getWidth() + 13, -5, bi.getWidth(),
				bi.getHeight(), 0, bi.getHeight());

		BufferedImage picBi = pf.filter(buffImg, null);

		SimplePositions[] sp = { fromSP, toSP, contentSP, dateSP, picSP };

		BufferedImage[] bis = { formBI, toBI, contentBi, dateBi, picBi };

		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
