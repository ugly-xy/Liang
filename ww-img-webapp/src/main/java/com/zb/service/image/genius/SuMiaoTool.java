package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

import com.jhlabs.image.*;
import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;

public class SuMiaoTool extends BaseTool {

	public SuMiaoTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "A6.jpg";
		StorageService storageService = new StorageService();
		SuMiaoTool tyt = new SuMiaoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.drawImg("装点逼", tmpPath0, tmpPath0));
	}

	public String drawImg(String name, String pic, String tmpPath)
			throws IOException {
		BufferedImage bi = super.getImg(pic);
		int w = bi.getWidth();
		int h = bi.getHeight();
		if (w > 720) {
			h = h * 720 / w;
			w = 720;
			bi = super.compressImage(pic, w, h);
		}

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
		BufferedImage nameBI = drawText(name, ZbFont.游狼近草体简, fontSize,
				new Color(78, 78, 78), mxW, 0, -0.15, true);

		GrayscaleFilter f = new GrayscaleFilter();
		bi = f.filter(bi, null);

		EdgeFilter dog = new EdgeFilter();
		dog.setHEdgeMatrix(EdgeFilter.FREI_CHEN_H);
		dog.setVEdgeMatrix(EdgeFilter.FREI_CHEN_V);
		bi = dog.filter(bi, null);

		InvertFilter ifl = new InvertFilter();
		bi = ifl.filter(bi, null);

		SimplePositions[] sp = { nameSP };

		BufferedImage[] bis = { nameBI };

		return super.getSaveFile(sp, bis, 0.85f, bi);
	}

}
