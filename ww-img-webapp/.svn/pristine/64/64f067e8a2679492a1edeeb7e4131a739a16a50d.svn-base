package com.zb.service.image.genius;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.jhlabs.image.*;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;

public class ImgFilterTool extends BaseTool {

	public ImgFilterTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/573aa88a0cf2cc7298e5f461";
		StorageService storageService = new StorageService();
		ImgFilterTool tyt = new ImgFilterTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.drawImg(tmpPath0, STYLES[1]));
	}

	private static final String[] STYLES = { "胶片", "旧照片", "黑白照" };

	public String drawImg(String pic, String style) throws IOException {
		if (STYLES[0].equals(style)) {
			InvertFilter f = new InvertFilter();
			BufferedImage bi = super.getImg(pic);
			bi = f.filter(bi, null);
			return super.saveFile(bi, "png");
		} else if (STYLES[1].equals(style)) {
			BufferedImage bi = super.getImg(pic);
			StampFilter sf = new StampFilter();
			sf.setBlack(0xff333333);
			sf.setWhite(0xfff7f7f7);
			sf.setSoftness(0.3f);
			sf.setThreshold(0.55f);
			bi = sf.filter(bi, null);
			return super.saveFile(bi, "png");
		} else if (STYLES[2].equals(style)) {
			GrayscaleFilter f = new GrayscaleFilter();
			BufferedImage bi = super.getImg(pic);
			bi = f.filter(bi, null);
			return super.saveFile(bi, "png");
		}
		return null;

	}

}
