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

public class XinWenTool extends BaseTool {

	public XinWenTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String t0 = "http://imgzb.zhuangdianbi.com/574d77a10cf2949bb0194802";
		String t1 = "http://imgzb.zhuangdianbi.com/574d7e7c0cf2e62a044d2535";
		StorageService storageService = new StorageService();
		XinWenTool tyt = new XinWenTool(storageService);
		tyt.isDebug(true);
		System.out
				.println(tyt.drawImg("a1.jpg", "强奸我，一开始我是反抗的", STYLES[1], t1));
	}

	static String[] STYLES = { "全频版", "主持人版本" };

	static ZbFont f = ZbFont.黑体加粗;
	static int fs = 32;

	public String drawImg(String myPic, String content, String style,
			String tmpPath) throws IOException {
		if (STYLES[0].equals(style)) {

			BufferedImage bBI = super.compressImage(myPic, 720, 540);

			BufferedImage cctvBI = super.getImg(tmpPath);
			SimplePositions ccvtSP = new SimplePositions();
			ccvtSP.buildHorizontalOffset(0).buildVerticalOffset(0);

			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(155).buildVerticalOffset(461);
			BufferedImage nameBI = drawText(content, f.font(), f.type(), fs,
					new Color(0, 0, 0), 700, 140, 0, true);

			SimplePositions[] sp = { ccvtSP, nameSP };

			BufferedImage[] bis = { cctvBI, nameBI };

			return super.getSaveFile(sp, bis, 0.85f, bBI);
		} else {
			BufferedImage bBI = super.compressImage(myPic, 336, 249);

			BufferedImage cctvBI = super.getImg(tmpPath);
			SimplePositions ccvtSP = new SimplePositions();
			ccvtSP.buildHorizontalOffset(10).buildVerticalOffset(65);

			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(155).buildVerticalOffset(423);
			BufferedImage nameBI = drawText(content, f.font(), f.type(), fs,
					new Color(255, 255, 255), 700, 140, 0, true);

			SimplePositions[] sp = { ccvtSP, nameSP };

			BufferedImage[] bis = { bBI, nameBI };

			return super.getSaveFile(sp, bis, 0.85f, cctvBI);
		}
	}

}
