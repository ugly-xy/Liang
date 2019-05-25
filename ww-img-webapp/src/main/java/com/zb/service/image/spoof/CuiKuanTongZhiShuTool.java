package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;
import com.zb.service.image.i.TwoDraw;

public class CuiKuanTongZhiShuTool extends BaseTool implements TwoDraw {

	public CuiKuanTongZhiShuTool(StorageService storageService) {
		super(storageService);
	}

	public CuiKuanTongZhiShuTool() {
	}

	public static void main(String[] args) throws IOException {
		String p0 = "http://imgzb.zhuangdianbi.com/575257f70cf280692a4dd96f";
		StorageService storageService = new StorageService();
		CuiKuanTongZhiShuTool tyt = new CuiKuanTongZhiShuTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.draw("装点逼", "20000", p0,null));
	}

	static Color c = new Color(0, 0, 0);

	@Override
	public String draw(String one, String two, String tmpPath,String count)
			throws IOException {

		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(80).buildVerticalOffset(135);
		BufferedImage nameBI = drawText(one, ZbFont.苹方, 20, c, 300, 100, 0,
				true);

		SimplePositions dSP = new SimplePositions();
		dSP.buildHorizontalOffset(275).buildVerticalOffset(189);
		BufferedImage dBI = drawText(DateUtil.dateFormatShortCN(new Date()),
				ZbFont.苹方, 18, c, 300, 100, 0, true);

		two = two + ".00元";
		SimplePositions yuanSP = new SimplePositions();
		yuanSP.buildHorizontalOffset(199).buildVerticalOffset(231);
		BufferedImage yuanBI = drawText(two, ZbFont.苹方, 18, c, 300, 100, 0,
				true);

		SimplePositions[] sp = { nameSP, yuanSP, dSP };
		BufferedImage[] bis = { nameBI, yuanBI, dBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}

}
