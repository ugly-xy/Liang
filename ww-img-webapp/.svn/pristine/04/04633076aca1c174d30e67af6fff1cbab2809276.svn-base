package com.zb.service.image.holiday;

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
import com.zb.service.image.i.OneDraw;
import com.zb.service.image.i.TwoDraw;

public class HaoBaBaTool extends BaseTool implements OneDraw {
	public HaoBaBaTool(StorageService storageService) {
		super(storageService);
	}

	public HaoBaBaTool() {
	}

	public static void main(String[] args) throws IOException {
		// String tmpPath0 =
		// "http://imgzb.zhuangdianbi.com/5732a2a80cf2d5342be19ca8";
		String tmpPath0 = "204.png";
		StorageService storageService = new StorageService();
		HaoBaBaTool tyt = new HaoBaBaTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼", tmpPath0,null));
	}

	@Override
	public String draw(String name, String tmpPath,String count) throws IOException {
		int fontSize = 18;
		Color color = new Color(0, 0, 0);
		String fontStyle = ZbFont.微软雅黑加粗.font();
		int fontType = Font.BOLD;

		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(515).buildVerticalOffset(240);
		BufferedImage nameBI = drawText(name, ZbFont.微软雅黑, fontSize, color,
				240, 0, 0, true);

		// date
		String date = DateUtil.dateFormat(new Date(), "yyyy年M月d日");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(465).buildVerticalOffset(410);
		BufferedImage dateBi = drawText(date, fontStyle, fontType, fontSize,
				color, 240, 0, 0, true);

		// 内容
		// SimplePositions contentSP = new SimplePositions();
		// contentSP.buildHorizontalOffset(343).buildVerticalOffset(204);
		// BufferedImage contentBI = drawText(content, fontStyle, fontType,
		// fontSize, color, 240, 0, 0, true);

		SimplePositions[] sp = { nameSP, dateSP };

		BufferedImage[] bis = { nameBI, dateBi };

		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
