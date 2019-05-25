package com.zb.service.image.genius;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import com.jhlabs.image.*;
import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;

public class DiaoKeTool extends BaseTool {

	public DiaoKeTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "a7.jpg";
		StorageService storageService = new StorageService();
		DiaoKeTool tyt = new DiaoKeTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.drawImg("装点逼", tmpPath0, tmpPath0));
	}

	public String drawImg(String name, String pic, String tmpPath)
			throws IOException {

		BufferedImage bi = super.getImg(pic);
		if (bi.getWidth() > 720) {
			bi = super.compressImage(pic, 720,
					bi.getHeight() * 720 / bi.getWidth());
		}
		GrayscaleFilter f = new GrayscaleFilter();
		bi = f.filter(bi, null);

		EdgeFilter dog = new EdgeFilter();
		dog.setHEdgeMatrix(EdgeFilter.FREI_CHEN_H);
		dog.setVEdgeMatrix(EdgeFilter.FREI_CHEN_V);
		bi = dog.filter(bi, null);

		InvertFilter ifl = new InvertFilter();
		bi = ifl.filter(bi, null);
		
//		int fontSize = (int) (mxW / (name.length() * 1.1f));
//		int left = w - mxW - 10;
//		int height = (int) (h - fontSize * 3.8);
//		name = name + DateUtil.dateFormat(new Date(), "MM.dd");

		return super.saveFile(bi, "png");
	}

}
