package com.zb.service.image.certificate;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class XingQiZhengTool extends BaseTool implements OneDraw{
	
	public XingQiZhengTool(StorageService storageService) {
		super(storageService);
	}

	public XingQiZhengTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		// String tmpPath0 =
		// "http://imgzb.zhuangdianbi.com/5732a2a80cf2d5342be19ca8";
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/576a6fac0cf280ab0cd923b3";
		StorageService storageService = new StorageService();
		XingQiZhengTool tyt = new XingQiZhengTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼", tmpPath0,null));
		
		
	}
	
	static ZbFont zbfont = ZbFont.楷体常规;
	static int fontSize = 13;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	//生成随机4位数
	public static String getRandomCode() {
		int length = 4;
		Random r = new Random();
		StringBuilder randomCode = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int num = r.nextInt(10);
			randomCode.append(num);
		}
		return randomCode.toString();
	}
	
	@Override
	public String draw(String one,String tmpPath,String count) throws IOException {
		// 左侧姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(150).buildVerticalOffset(235);
		BufferedImage nameBI = drawText(one,zbfont.font(),fontType, fontSize, color,
				240, 0, 0, true);
		//右侧姓名
		int fontSize2 = 11;
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(325).buildVerticalOffset(77);
		BufferedImage nameBI2 = drawText(one, zbfont, fontSize2, color,
				240, 0, 0, true);
		//证书编号
		String two = "XQZ"+this.getRandomCode()+"号";
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(145).buildVerticalOffset(275);
		BufferedImage nameBI3 = drawText(two, zbfont, fontSize, color,
				240, 0, 0, true);
		//颁发时间
		int fontSize3 = 12;
		String date =  DateUtil.dateFormat(new Date(), "yyyy年MM月dd日");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(315).buildVerticalOffset(234);
		BufferedImage dateBi = drawText(date,zbfont.font(),fontType, fontSize3, 
				color, 240,0, 0, true);
		SimplePositions[] sp = { nameSP,nameSP2,nameSP3,dateSP};

		BufferedImage[] bis = { nameBI,nameBI2,nameBI3, dateBi};

		return super.getSaveFile(sp, bis, 0.9f, tmpPath);
			
	}
}
