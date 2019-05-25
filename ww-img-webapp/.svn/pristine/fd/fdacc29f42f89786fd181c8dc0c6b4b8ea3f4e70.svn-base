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

public class JianTaoShuTool extends BaseTool {

	public JianTaoShuTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/574290c30cf2abe345881f41";
		StorageService storageService = new StorageService();
		JianTaoShuTool tyt = new JianTaoShuTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.drawImg("装点逼", tmpPath0));
	}

	static ZbFont f = ZbFont.建刚静心楷;
	static int fs = 28;

	public String drawImg(String name, String tmpPath) throws IOException {

		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(414).buildVerticalOffset(802);
		BufferedImage nameBI = drawText(name, f.font(), f.type(), 48,
				new Color(0, 0, 0), 400, 140, 0, true);

		String date = DateUtil.dateFormatDianShort(new Date());
		SimplePositions titleSP = new SimplePositions();
		titleSP.buildHorizontalOffset(416).buildVerticalOffset(870);
		BufferedImage titleBI = drawText(date, f.font(), f.type(), 42,
				new Color(0, 0, 0), 400, 140, 0, true);

		SimplePositions[] sp = { nameSP, titleSP };

		BufferedImage[] bis = { nameBI, titleBI };

		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
