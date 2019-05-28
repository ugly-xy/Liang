package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.FourDraw;

public class ZhongJinQiuZiTool extends BaseTool implements FourDraw{
	public ZhongJinQiuZiTool(StorageService storageService) {
		super(storageService);
	}

	public ZhongJinQiuZiTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57776a770cf20de9530e9bc1";
		StorageService storageService = new StorageService();
		ZhongJinQiuZiTool tyt = new ZhongJinQiuZiTool(storageService);
		tyt.isDebug(true);
		System.out
				.println(tyt.draw("装点逼","16", "12345678901","145220.png", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 16;
	static Color color = new Color(187, 44, 38);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String two, String three,String four, String tmpBackPic, String count) throws IOException {
		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(210).buildVerticalOffset(235);
		BufferedImage nameBI = drawText(one, zbfont, fontSize, color,
				240, 0, 0, true);
		// 年龄
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(281).buildVerticalOffset(235);
		BufferedImage nameBI2 = drawText(two, zbfont, fontSize, color,
				240, 0, 0, true);
		
		// 联系方式
		int fontSize2 = 28;
		String three0 = "联系QQ："+three;
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(60).buildVerticalOffset(460);
		BufferedImage nameBI3 = drawText(three0, zbfont, fontSize2, color,
				320, 0, 0, true);
		
		//图片
		SimplePositions picSP = new SimplePositions();
		picSP.buildHorizontalOffset(39).buildVerticalOffset(240);
		BufferedImage picBI = super.compressImage(four, 145, 220);
		
		SimplePositions[] sp = { nameSP,picSP,nameSP2,nameSP3 };

		BufferedImage[] bis = { nameBI,picBI,nameBI2,nameBI3};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
