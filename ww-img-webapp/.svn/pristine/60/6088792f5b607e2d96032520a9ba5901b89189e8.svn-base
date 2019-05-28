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
import com.zb.service.image.i.ThreeDraw;

public class BeiJingDianYingXueYuanTool extends BaseTool implements ThreeDraw{
	
	public BeiJingDianYingXueYuanTool(StorageService storageService) {
		super(storageService);
	}

	public BeiJingDianYingXueYuanTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/577119a60cf24f3a07d16e3a";
		StorageService storageService = new StorageService();
		BeiJingDianYingXueYuanTool tyt = new BeiJingDianYingXueYuanTool(storageService);
		tyt.isDebug(true);
		int i =0;
		System.out.println(tyt.draw("小学生","经济","国贸" ,tmpPath0,null));
	}
	//生成随机6位数
	public static String getRandomCode() {
		int length = 6;
		Random r = new Random();
		StringBuilder randomCode = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int num = r.nextInt(10);
			randomCode.append(num);
		}
		return randomCode.toString();
	}
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 12;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String three, String tmpBackPic, String count) throws IOException {
		
		String numb = this.getRandomCode();
		
		// 姓名
		numb = "B"+numb;
		SimplePositions num = new SimplePositions();
		num.buildHorizontalOffset(153).buildVerticalOffset(230);
		BufferedImage numBI = drawText(numb, ZbFont.微软雅黑, fontSize, color,
				240, 0, 0, true);
		
		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(125).buildVerticalOffset(267);
		BufferedImage nameBI = drawText(one, ZbFont.微软雅黑, fontSize, color,
				240, 0, 0, true);
		
		String two0 = two+"学院"+three+"专业";
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(250).buildVerticalOffset(302);
		BufferedImage nameBI2 = drawText(two0, ZbFont.微软雅黑, fontSize, color,
				600, 0, 0, true);
		
		
		// date
		String date = DateUtil.dateFormat(new Date(), "yyyy年M月d日");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(416).buildVerticalOffset(420);
		BufferedImage dateBi = drawText(date, zbfont.font(), fontType, fontSize,
				color, 240, 0, 0, true);
		SimplePositions[] sp = { nameSP,nameSP2, dateSP,num };

		BufferedImage[] bis = { nameBI,nameBI2, dateBi,numBI };

		return super.getSaveFile(sp, bis, 0.8f, tmpBackPic);
	}

}
