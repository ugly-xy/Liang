package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang.time.DateUtils;

import com.google.zxing.BinaryBitmap;
import com.jhlabs.image.*;
import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;

public class YouHuaTool extends BaseTool {

	public YouHuaTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5729e2eb0cf2ab70746ff0a6";
		StorageService storageService = new StorageService();
		YouHuaTool tyt = new YouHuaTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.drawImg("装点逼", tmpPath0));
	}

	static ZbFont zbf = ZbFont.游狼近草体简;

	public String drawImg(String name, String pic)
			throws IOException {
		BufferedImage bi = super.getImg(pic);

		int w = bi.getWidth();
		int h = bi.getHeight();
		if (w > 720) {
			h = h * 720 / w;
			w = 720;
			bi = super.compressImage(pic, w, h);
		}

		int l1 = (int) Math.ceil(w / 2.0);
		int r1 = (int) Math.ceil(w / 140);
		OilFilter sf = new OilFilter();
		sf.setLevels(l1);
		sf.setRange(r1);
		bi = sf.filter(bi, null);

		float ra = (int) Math.ceil(w / 120f);
		GaussianFilter gf = new GaussianFilter();
		gf.setRadius(ra);
		bi = gf.filter(bi, null);

		int l2 = (int) Math.ceil(w * 10 / 72);
		int r2 = (int) Math.ceil(w / 240);

		sf.setLevels(l2);
		sf.setRange(r2);
		bi = sf.filter(bi, null);

		int mxW = (int) (bi.getWidth() / 2);
		if (mxW > 220) {
			mxW = 220;
		}
		name = name + "画\n";
		int fontSize = (int) (mxW / (name.length() * 1.1f));
		int left = w - mxW - 10;
		int height = (int) (h - fontSize * 3.8);
		name = name + DateUtil.dateFormat(new Date(), "MM.dd");

		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(left).buildVerticalOffset(height);
		BufferedImage nameBI = drawText(name, zbf.font(), zbf.type(), fontSize,
				new Color(105, 55, 14), mxW, 0, -0.15, true);

		LensBlurFilter df = new LensBlurFilter();
		df.setBloom(8);
		df.setRadius(3.2f);
		BufferedImage name2BI = df.filter(nameBI, null);

		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(left + 1).buildVerticalOffset(height + 1);

		nameBI = drawText(name, zbf.font(), zbf.type(), fontSize, new Color(
				169, 141, 130), mxW, 0, -0.15, true);

		SimplePositions[] sp = { nameSP2, nameSP };

		BufferedImage[] bis = { name2BI, nameBI };

		return super.getSaveFile(sp, bis, 0.85f, bi);
	}

}
