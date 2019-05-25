package com.zb.service.image.certificate;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class GaoKaoChengJiChaXunTool extends BaseTool implements TwoDraw{
	public GaoKaoChengJiChaXunTool(StorageService storageService) {
		super(storageService);
	}

	public GaoKaoChengJiChaXunTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/576cafae0cf24fe2e4ece997";
		StorageService storageService = new StorageService();
		GaoKaoChengJiChaXunTool tyt = new GaoKaoChengJiChaXunTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装装装","河北", tmpPath0,null));
	}

	//生成随机2以下的位数
	public static String getRandomCode() {
		int length = 1;
		Random r = new Random();
		StringBuilder randomCode = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int num = r.nextInt(3);
			randomCode.append(num);
		}
		return randomCode.toString();
	}
	
	//生成随机10以下的位数
	public static String getRandomCode2() {
		int length = 1;
		Random r = new Random();
		StringBuilder randomCode = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int num = r.nextInt(10);
			randomCode.append(num);
		}
		return randomCode.toString();
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 32;
	static Color color = new Color(140, 140, 140);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpPath,String count) throws IOException {
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(275).buildVerticalOffset(518);
		BufferedImage nameBI = drawText(one,zbfont,fontSize, color,
				240, 0, 0, true);
		
		//省份1
		int fontSize2 = 40;
		String two0 = two+"教育考试院";
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(213).buildVerticalOffset(62);
		BufferedImage nameBI2 = drawText(two0, zbfont.font(),fontType, fontSize2, new Color(255,255,255),
				600, 0, 0, true);
		//省份2
		int fontSize3= 36;
		String two1 = "2016年"+two+"普通高校招生考试";
		SimplePositions nameSP3= new SimplePositions();
		nameSP3.buildHorizontalOffset(115).buildVerticalOffset(150);
		BufferedImage nameBI3 = drawText(two1, zbfont.font(),fontType, fontSize3, new Color(53,161,210),
				600, 0, 0, true);
		
		
		
		//语文分数
		String yuWen = "1"+this.getRandomCode()+this.getRandomCode2();
		SimplePositions nameSP4 = new SimplePositions();
		nameSP4.buildHorizontalOffset(275).buildVerticalOffset(718);
		BufferedImage nameBI4 = drawText(yuWen,zbfont,fontSize, color,
				240, 0, 0, true);
		//数学分数
		String shuXue = "1"+this.getRandomCode()+this.getRandomCode2();
		SimplePositions nameSP5 = new SimplePositions();
		nameSP5.buildHorizontalOffset(275).buildVerticalOffset(799);
		BufferedImage nameBI5 = drawText(shuXue,zbfont,fontSize, color,
				240, 0, 0, true);
		//外语分数
		String waiYu = "1"+this.getRandomCode()+this.getRandomCode2();
		SimplePositions nameSP6 = new SimplePositions();
		nameSP6.buildHorizontalOffset(275).buildVerticalOffset(882);
		BufferedImage nameBI6 = drawText(waiYu,zbfont,fontSize, color,
				240, 0, 0, true);
		//综合分数
		String zongHe = "2"+"5"+this.getRandomCode2();
		SimplePositions nameSP7 = new SimplePositions();
		nameSP7.buildHorizontalOffset(275).buildVerticalOffset(965);
		BufferedImage nameBI7 = drawText(zongHe,zbfont,fontSize, color,
				240, 0, 0, true);
		//总分数
		String zongFenShu =Integer.toString(Integer.parseInt(yuWen)+Integer.parseInt(shuXue)+Integer.parseInt(waiYu)+Integer.parseInt(zongHe)) ;
		SimplePositions nameSP8 = new SimplePositions();
		nameSP8.buildHorizontalOffset(275).buildVerticalOffset(1047);
		BufferedImage nameBI8 = drawText(zongFenShu,zbfont,fontSize, new Color(0,0,0),
				240, 0, 0, true);
		  
		SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSP5,nameSP6,nameSP7,nameSP8};

		BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4 ,nameBI5,nameBI6,nameBI7,nameBI8};

		return super.getSaveFile(sp, bis, 0.95f, tmpPath);
	}

}
