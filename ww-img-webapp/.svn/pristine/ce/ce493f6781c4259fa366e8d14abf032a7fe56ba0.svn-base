package com.zb.service.image.certificate;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jhlabs.image.*;
import com.zb.common.utils.PinYinUtil;
import com.zb.image.ImageAlpha;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;

public class JiangBeiTool extends BaseTool {

	public JiangBeiTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5742759e0cf261daa525728e";
		StorageService storageService = new StorageService();
		JiangBeiTool tyt = new JiangBeiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.drawImg("中国外貌协会", "装点逼", "最佳颜值奖", tmpPath0));
	}

	static ZbFont f = ZbFont.苹方;
	static int fs = 28;

	public String drawImg(String org, String name, String title, String tmpPath)
			throws IOException {

		int nleft = 490 - 12 * name.length();
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(nleft).buildVerticalOffset(598);
		BufferedImage nameBI = drawText(name, f.font(), f.type(), 24,
				new Color(0, 0, 0), 360, 140, 0, true);

		int tleft = 490 - 12 * title.length();
		SimplePositions titleSP = new SimplePositions();
		titleSP.buildHorizontalOffset(tleft).buildVerticalOffset(633);
		BufferedImage titleBI = drawText(title, f.font(), f.type(), 24,
				new Color(0, 0, 0), 360, 140, 0, true);

		int oLeft = 195 - org.length() * 14;
		ZbFont of = ZbFont.报隶简;
		SimplePositions orgSP = new SimplePositions();
		orgSP.buildHorizontalOffset(oLeft).buildVerticalOffset(505);
		BufferedImage orgBi = drawText(org, of.font(), of.type(), 28,
				new Color(232, 201, 158), 360, 140, 0, true);

		SimplePositions[] sp = { nameSP, orgSP, titleSP };

		BufferedImage[] bis = { nameBI, orgBi, titleBI };

		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
