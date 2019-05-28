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
import com.zb.service.image.i.FiveDraw;

public class HuoChePiaoTool extends BaseTool implements FiveDraw{
	public HuoChePiaoTool(StorageService storageService) {
		super(storageService);
	}

	public HuoChePiaoTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/579873aa0cf28748f04ff1db";
		StorageService storageService = new StorageService();
		HuoChePiaoTool tyt = new HuoChePiaoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("北京", "南京","9999","55555","装点逼", tmpPath0,
				null));
	}
	
	static ZbFont zbfont = ZbFont.黑体加粗;
	static int fontSize = 35;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String two, String three, String four, String five, String tmpBackPic, String count)
			throws IOException {
		SimplePositions nameSP4 =null;
		BufferedImage nameBI4 = null;
		//起点
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(282-one.length()*fontSize).buildVerticalOffset(350);
		BufferedImage nameBI = drawText(one, zbfont, fontSize, color,
				200, 0, 0, true);
		//终点
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(580-two.length()*fontSize).buildVerticalOffset(353);
		BufferedImage nameBI2 = drawText(two, zbfont, fontSize, color,
				200, 0, 0, true);
		//车次
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(385).buildVerticalOffset(360);
		BufferedImage nameBI3 = drawText(three, zbfont, 25, color,
				200, 0, 0, true);
		//价格
		int yuan = Integer.parseInt(four);
		if(yuan<10000){
			nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(211).buildVerticalOffset(440);
			nameBI4 = drawText(four, zbfont, 28, color,
					200, 0, 0, true);
		}else {
			nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(194).buildVerticalOffset(440);
			nameBI4 = drawText(four, zbfont, 28, color,
					200, 0, 0, true);
		}
		
		
		SimplePositions nameSP6 = new SimplePositions();
		nameSP6.buildHorizontalOffset(260).buildVerticalOffset(444);
		BufferedImage nameBI6 = drawText(" 元", ZbFont.宋体, 22, color,
				200, 0, 0, true);
		
		//姓名
		SimplePositions nameSP5 = new SimplePositions();
		nameSP5.buildHorizontalOffset(410).buildVerticalOffset(520);
		BufferedImage nameBI5 = drawText(five, ZbFont.宋体, 30, color,
				200, 0, 0, true);
		// date
		String date = DateUtil.dateFormat(new Date(), "yyyy  MM d");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(165).buildVerticalOffset(410);
		BufferedImage dateBi = drawText(date, zbfont, 26,
				color, 300, 0, 0, true);
		String date2 = DateUtil.dateFormat(new Date(), "HH:mm");
		SimplePositions dateSP2 = new SimplePositions();
		dateSP2.buildHorizontalOffset(340).buildVerticalOffset(410);
		BufferedImage dateBi2 = drawText(date2, zbfont, 26,
				color, 300, 0, 0, true);
		
		
		SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSP5,dateSP,dateSP2,nameSP6};

		BufferedImage[] bis = { nameBI,nameBI2 ,nameBI3,nameBI4,nameBI5,dateBi,dateBi2,nameBI6};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
