package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.certificate.GaoKaoChengJiRenZhengTool;
import com.zb.service.image.i.TwoDraw;

public class DaShengHanChuLaiTool extends BaseTool implements TwoDraw {

	public DaShengHanChuLaiTool(StorageService storageService) {
		super(storageService);
	}

	public DaShengHanChuLaiTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		//String tmpPath0 = "209-成绩认证.png";
		StorageService storageService = new StorageService();
		DaShengHanChuLaiTool tyt = new DaShengHanChuLaiTool(storageService);
		tyt.isDebug(true);
		int i = 1;
		System.out
				.println(tyt.draw("睡你妈逼", S[i], T[i],null));
		
		
	}
	
	static String T[] = {
			"http://imgzb.zhuangdianbi.com/57690fc70cf23b07acda914b",
			"http://imgzb.zhuangdianbi.com/576911670cf23b07acda9337"};
	static String S[] = { "球场举牌1", "球场举牌2" };
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 30;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String style, String tmpPath,String count) throws IOException {
		//字数小于11 补上！
		for(int i =0;i<10;i++){
			if(one.length()<10){
				one+="!";
			}
		}
		
		List<String> list = new ArrayList<String>();
		
		for(int t = 0;t<one.length();t++){
			list.add(String.valueOf(one.charAt(t)));
		}
		
		SimplePositions nameSP0 = null;  
		SimplePositions nameSP1 = null;		
		SimplePositions nameSP2 = null;
		SimplePositions nameSP3 = null;
		SimplePositions nameSP4 = null;
		SimplePositions nameSP5 = null;
		SimplePositions nameSP6 = null;
		SimplePositions nameSP7 = null;
		SimplePositions nameSP8 = null;
		SimplePositions nameSP9 = null;
		BufferedImage nameBI0 =null;
		BufferedImage nameBI1 =null;
		BufferedImage nameBI2 =null;
		BufferedImage nameBI3 =null;
		BufferedImage nameBI4 =null;
		BufferedImage nameBI5 =null;
		BufferedImage nameBI6 =null;
		BufferedImage nameBI7 =null;
		BufferedImage nameBI8 =null;
		BufferedImage nameBI9 =null;
		if(style.equals(S[0])){
			nameSP0 = new SimplePositions();
			nameSP0.buildHorizontalOffset(6).buildVerticalOffset(44);
			nameBI0 = drawText(list.get(0), zbfont, fontSize, color,
					240, 100, -0.08, true,20,20);
			nameSP1 = new SimplePositions();
			nameSP1.buildHorizontalOffset(50).buildVerticalOffset(40);
			nameBI1 = drawText(list.get(1), zbfont, fontSize, color,
					240, 100, -0.08, true,20,20);
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(95).buildVerticalOffset(35);
			nameBI2 = drawText(list.get(2), zbfont, fontSize, color,
					240, 100, 0.08, true,20,20);
			nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(161).buildVerticalOffset(58);
			nameBI3 = drawText(list.get(3), zbfont, fontSize, color,
					240, 0, 0, true);
			nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(195).buildVerticalOffset(58);
			nameBI4 = drawText(list.get(4), zbfont, fontSize, color,
					240, 0, 0, true);
			nameSP5 = new SimplePositions();
			nameSP5.buildHorizontalOffset(223).buildVerticalOffset(63);
			nameBI5 = drawText(list.get(5), zbfont, fontSize, color,
					240, 0, 0, true);
			nameSP6 = new SimplePositions();
			nameSP6.buildHorizontalOffset(253).buildVerticalOffset(58);
			nameBI6 = drawText(list.get(6), zbfont, fontSize, color,
					240, 0, 0, true);
			nameSP7 = new SimplePositions();
			nameSP7.buildHorizontalOffset(290).buildVerticalOffset(58);
			nameBI7 = drawText(list.get(7), zbfont, fontSize, color,
					240, 0, 0, true);
			nameSP8 = new SimplePositions();
			nameSP8.buildHorizontalOffset(320).buildVerticalOffset(57);
			nameBI8 = drawText(list.get(8), zbfont, fontSize, color,
					240, 0, 0, true);
			nameSP9 = new SimplePositions();
			nameSP9.buildHorizontalOffset(332).buildVerticalOffset(40);
			nameBI9 = drawText(list.get(9), zbfont, fontSize, color,
					240, 100, -0.08, true,20,20);
			SimplePositions[] sp = { nameSP0,nameSP1,nameSP2,nameSP3,nameSP4,nameSP5,nameSP6,nameSP7,nameSP8,nameSP9 };

			BufferedImage[] bis = { nameBI0, nameBI1, nameBI2, nameBI3,nameBI4,nameBI5,nameBI6,nameBI7,nameBI8,nameBI9};
			
			return super.getSaveFile(sp, bis, 0.8f, tmpPath);
		}else{
			int fontSize = 55;
			nameSP0 = new SimplePositions();
			nameSP0.buildHorizontalOffset(15).buildVerticalOffset(155);
			nameBI0 = drawText(list.get(0), zbfont, fontSize, color,
					240, 100, 0.1, true,20,20);
			nameSP1 = new SimplePositions();
			nameSP1.buildHorizontalOffset(90).buildVerticalOffset(140);
			nameBI1 = drawText(list.get(1), zbfont, fontSize, color,
					240, 100, 0, true,20,20);
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(170).buildVerticalOffset(142);
			nameBI2 = drawText(list.get(2), zbfont, fontSize, color,
					240, 100, 0.02, true,20,20);
			nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(260).buildVerticalOffset(165);
			nameBI3 = drawText(list.get(3), zbfont, fontSize, color,
					240, 100, 0.02, true);
			nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(328).buildVerticalOffset(158);
			nameBI4 = drawText(list.get(4), zbfont, fontSize, color,
					240, 100, -0.12, true,20,20);
			nameSP5 = new SimplePositions();
			nameSP5.buildHorizontalOffset(408).buildVerticalOffset(152);
			nameBI5 = drawText(list.get(5), zbfont, fontSize, color,
					240,100, -0.12, true,20,20);
			nameSP6 = new SimplePositions();
			nameSP6.buildHorizontalOffset(499).buildVerticalOffset(132);
			nameBI6 = drawText(list.get(6), zbfont, fontSize, color,
					240,100, 0.08, true,20,20);
			
			SimplePositions[] sp = { nameSP0,nameSP1,nameSP2,nameSP3,nameSP4,nameSP5,nameSP6 };

			BufferedImage[] bis = { nameBI0, nameBI1, nameBI2, nameBI3,nameBI4,nameBI5,nameBI6};
			
			return super.getSaveFile(sp, bis, 0.8f, tmpPath);
		}
	}

}
