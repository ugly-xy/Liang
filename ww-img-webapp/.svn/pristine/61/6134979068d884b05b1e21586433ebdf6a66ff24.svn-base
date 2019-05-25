package com.zb.service.image.spoof;

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
import com.zb.service.image.i.FourDraw;

public class GaoKaoZhuangYuanTool extends BaseTool implements FourDraw {
	public GaoKaoZhuangYuanTool(StorageService storageService) {
		super(storageService);
	}

	public GaoKaoZhuangYuanTool() {
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/576d26fe0cf27682dd15f379";
		StorageService storageService = new StorageService();
		GaoKaoZhuangYuanTool tyt = new GaoKaoZhuangYuanTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("测试.jpg","装点逼","湖北省","理科", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 20;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String three, String four, String tmpPath,String count) throws IOException {
		
		//照片
		SimplePositions picSP = new SimplePositions();
		picSP.buildHorizontalOffset(16).buildVerticalOffset(241);
		BufferedImage picBI = super.compressImage(one, 453, 458);
		
		//姓名
		int fontSize2=30;
		String two0 = two+"以高考成绩726分一举获得"+three+four+"状元";
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(20).buildVerticalOffset(115);
		BufferedImage nameBI = drawText(two0, zbfont.font(),fontType, fontSize2, new Color(0,0,0),
				450, 0, 0, true);
		//下部姓名
		int fontSize4=22;
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(20).buildVerticalOffset(700);
		BufferedImage nameBI2 = drawText(two, zbfont.font(),fontType, fontSize4, new Color(131,134,134),
				600, 0, 0, true);
		// date
		int fontSize3 = 18;
		String date = DateUtil.dateFormat(new Date(), "MM-dd HH:mm");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(90).buildVerticalOffset(205);
		BufferedImage dateBi = drawText(date, zbfont.font(), fontType, fontSize3,
				new Color(131,134,134), 240, 0, 0, true);
		int fontSize5 = 24;
		String date2 = DateUtil.dateFormat(new Date(), "M  d");
		SimplePositions dateSP2 = new SimplePositions();
		dateSP2.buildHorizontalOffset(122).buildVerticalOffset(750);
		BufferedImage dateBi2 = drawText(date2, zbfont.font(), fontType, fontSize5,
				new Color(47,26,23), 240, 0, 0, true);
		
		SimplePositions[] sp = { nameSP,nameSP2,picSP, dateSP,dateSP2 };

		BufferedImage[] bis = { nameBI,nameBI2,picBI, dateBi ,dateBi2 };

		return super.getSaveFile(sp, bis, 0.9f, tmpPath);
	}

}
