package com.zb.service.image.tuhao;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class PingGuoDingDanTool extends BaseTool implements OneDraw  {

	public PingGuoDingDanTool(StorageService storageService) {
		super(storageService);
	}

	public PingGuoDingDanTool() {
	}
	
	public static void main(String[] args) throws IOException {
		// String tmpPath0 =
		// "http://imgzb.zhuangdianbi.com/5732a2a80cf2d5342be19ca8";
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5769fd5e0cf2a4a08925a1eb";
		StorageService storageService = new StorageService();
		PingGuoDingDanTool tyt = new PingGuoDingDanTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 20;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String tmpPath,String count) throws IOException {
		//姓名
		int fontSize2=16;
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(113).buildVerticalOffset(252);
		BufferedImage nameBI = drawText(one, zbfont.font(),fontType, fontSize2, new Color(100,188,219),
				240, 0, 0, true);
		
		// 订单date
		String date = DateUtil.dateFormat(new Date(), "yyyy/MM/dd");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(325).buildVerticalOffset(167);
		BufferedImage dateBi = drawText(date, zbfont, fontSize,
				color, 240, 100, 0, true);
		
		// 发货date
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		gc.add(Calendar.DAY_OF_YEAR, 5);
		String date2 = sdf.format(gc.getTime());
		SimplePositions dateSP2 = new SimplePositions();
		dateSP2.buildHorizontalOffset(187).buildVerticalOffset(458);
		BufferedImage dateBi2 = drawText(date2, zbfont, fontSize,
				color, 240, 100, 0, true);
		
		SimplePositions[] sp = { nameSP, dateSP , dateSP2 };

		BufferedImage[] bis = { nameBI, dateBi, dateBi2 };
		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
