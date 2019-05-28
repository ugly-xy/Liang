package com.zb.service.image.tuhao;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class YiYuanDuoBaoTool extends BaseTool implements TwoDraw{
	public YiYuanDuoBaoTool(StorageService storageService) {
		super(storageService);
	}

	public YiYuanDuoBaoTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/578722730cf220090c5388aa";
		StorageService storageService = new StorageService();
		YiYuanDuoBaoTool tyt = new YiYuanDuoBaoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","华为荣耀v8 64G版", tmpPath0,null));
	}
	//iphone6s 128G  全网5000元充值卡 小米 智能平衡车 华为荣耀v8 64G版
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 38;
	static Color color = new Color(220, 187, 48);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//内容
		String one0 = "网易1元购恭喜"+one+"获得";
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(130).buildVerticalOffset(770);
		BufferedImage nameBI = drawText(one0, zbfont, fontSize, new Color(255,255,255),
				600, 0, 0, true);
		
		Random ran = new Random();
		int max = 99999;
		DecimalFormat df=new DecimalFormat("00000");
		int num = ran.nextInt(max);
		df.format(num);
		String two0 = "第"+num+"期";
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(265).buildVerticalOffset(856);
		BufferedImage nameBI2 = drawText(two0, zbfont, fontSize, color,
				600, 0, 0, true);
		
		int fontSize2 = 55;
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(130).buildVerticalOffset(925);
		BufferedImage nameBI3 = drawText(two, zbfont, fontSize2, color,
				600, 0, 0, true);
		
		SimplePositions[] sp = {nameSP,nameSP2,nameSP3 };

		BufferedImage[] bis = {nameBI,nameBI2,nameBI3 };

		return super.getSaveFile(sp, bis, 0.9f, tmpBackPic);
	}

}
