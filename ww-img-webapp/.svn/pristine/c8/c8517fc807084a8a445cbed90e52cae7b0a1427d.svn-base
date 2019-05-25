package com.zb.service.image.genius;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.jhlabs.image.*;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;

public class PerspectiveFilterTool extends BaseTool {

	public PerspectiveFilterTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5733282f0cf27eadbc40ba22";
		StorageService storageService = new StorageService();
		PerspectiveFilterTool tyt = new PerspectiveFilterTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.drawImg("装点逼", "123.png", tmpPath0));
	}

	public String drawImg(String name, String pic, String tmpPath)
			throws IOException {
		BufferedImage bi = super.getImg(pic);
		int w = bi.getWidth();
		int h = bi.getHeight();
		PerspectiveFilter pf = new PerspectiveFilter();
		// pf.setClip(true);
		// pf.setEdgeAction(TransformFilter.ZERO);
		pf.setCorners(0, 0, w, 0, w - 6, h, 10, h);
		bi = pf.filter(bi, null);
		// pf.setInterpolation(TransformFilter.NEAREST_NEIGHBOUR);
		return super.saveFile(bi, "png");
	}

}
