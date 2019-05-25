package com.zb.service.image.certificate;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;


public class ZhiYePeiXunTool extends BaseTool {

	public ZhiYePeiXunTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5732a2a80cf2d5342be19ca8";
		StorageService storageService = new StorageService();
		ZhiYePeiXunTool tyt = new ZhiYePeiXunTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.drawImg("装点逼", "妈妈我好爱", tmpPath0));
	}

	public String drawImg(String name, String content, String tmpPath)
			throws IOException {
		int fontSize = 16;
		Color color = new Color(0, 0, 0);
		String fontStyle = ZbFont.宋体.font();
		int fontType = Font.PLAIN;

		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(180).buildVerticalOffset(175);
		BufferedImage nameBI = drawText(name, fontStyle, fontType, fontSize,
				color, 240, 0, 0, true);

		// date
		String date = DateUtil.dateFormat(new Date(), "yyyy年M月");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(256).buildVerticalOffset(175);
		BufferedImage dateBi = drawText(date, fontStyle, fontType, fontSize,
				color, 240, 0, 0, true);

		// 内容
		SimplePositions contentSP = new SimplePositions();
		contentSP.buildHorizontalOffset(343).buildVerticalOffset(204);
		BufferedImage contentBI = drawText(content, fontStyle, fontType,
				fontSize, color, 240, 0, 0, true);

		SimplePositions[] sp = { nameSP, dateSP, contentSP };

		BufferedImage[] bis = { nameBI, dateBi, contentBI };

		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
