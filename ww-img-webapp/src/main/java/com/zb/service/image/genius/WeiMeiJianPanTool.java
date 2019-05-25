package com.zb.service.image.genius;

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

public class WeiMeiJianPanTool extends BaseTool {

	public WeiMeiJianPanTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57404a350cf2ab693082a3d6";
		StorageService storageService = new StorageService();
		WeiMeiJianPanTool tyt = new WeiMeiJianPanTool(storageService);
		tyt.isDebug(true);
		tyt.setRootPathDef("");
		System.out.println(tyt.drawImg("装点逼就是很牛逼，不信你试试装点逼", "美的的传说",
				"abc.png", tmpPath0));
	}

	static ZbFont f = ZbFont.苹方;
	static int fs = 28;

	public String drawImg(String content, String text, String pic,
			String tmpPath) throws IOException {

		if (text.length() < 3) {
			text = text + " good";
		}
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(25).buildVerticalOffset(200);
		BufferedImage nameBI = drawText(content, f.font(), f.type(), fs,
				new Color(0, 0, 0), 360, 140, 0, true);

		String text2 = text.substring(2);
		text = text.substring(0, 2) + PinYinUtil.toPinyin(text2);
		SimplePositions textSP = new SimplePositions();
		textSP.buildHorizontalOffset(30).buildVerticalOffset(398);
		BufferedImage textBI = drawText(text, f.font(), f.type(), fs - 6,
				new Color(0, 0, 0), 360, 140, 0, true);

		SimplePositions textSP2 = new SimplePositions();
		textSP2.buildHorizontalOffset(30).buildVerticalOffset(452);
		BufferedImage textBI2 = drawText(text2, f.font(), f.type(), fs,
				new Color(243, 101, 31), 360, 140, 0, true);

		SimplePositions picSp = new SimplePositions();
		picSp.buildHorizontalOffset(220).buildVerticalOffset(0);

		BufferedImage bi = super.compressImage(pic, 500, 900);

		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ImageIO.write(bi, "png", bout);

		ImageAlpha ia = new ImageAlpha();
		bi = ia.setAlphaX(bout, 140, 350, 30, 190);

		SimplePositions[] sp = { nameSP, textSP, textSP2, picSp };

		BufferedImage[] bis = { nameBI, textBI, textBI2, bi };

		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
