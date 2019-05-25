package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import com.jhlabs.image.*;
import com.zb.common.utils.DateUtil;
import com.zb.common.utils.PinYinUtil;
import com.zb.image.ImageAlpha;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;

public class ChouNvXinWenTool extends BaseTool {

	public ChouNvXinWenTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5745a21f0cf2b872e927180e";
		StorageService storageService = new StorageService();
		ChouNvXinWenTool tyt = new ChouNvXinWenTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.drawImg("装点逼", "强奸我，一开始我是反抗的", tmpPath0));
	}

	static ZbFont f = ZbFont.黑体加粗;
	static int fs =32;

	public String drawImg(String name, String content, String tmpPath)
			throws IOException {
		name = name + content;
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(155).buildVerticalOffset(461);
		BufferedImage nameBI = drawText(name, f.font(), f.type(), fs,
				new Color(0, 0, 0), 700, 140, 0, true);

		SimplePositions[] sp = { nameSP };

		BufferedImage[] bis = { nameBI };

		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
