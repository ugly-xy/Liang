package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import com.zb.common.utils.PinYinUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class YinYuanCeShiTool extends BaseTool implements TwoDraw	{
	public YinYuanCeShiTool(StorageService storageService) {
		super(storageService);
	}

	public YinYuanCeShiTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57ac5c920cf20378729485fd";
		StorageService storageService = new StorageService();
		YinYuanCeShiTool tyt = new YinYuanCeShiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点","装点你",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.宋体加粗;
	static int fontSize = 46;
	static Color color = new Color(180, 22, 136);
	static int fontType = Font.BOLD;
	
	
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//姓名1
		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(250-one.length()*fontSize/2).buildVerticalOffset(187);
 		BufferedImage nameBi = drawText(one, zbfont, fontSize,
 				color, 240, 0, 0, true);
 		
 		SimplePositions nameSP5 = new SimplePositions();
 		nameSP5.buildHorizontalOffset(370-50/2).buildVerticalOffset(183);
 		BufferedImage nameBi5 = drawText("和", zbfont, 50,
 				color, 240, 0, 0, true);
 		
 		//姓名2
		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(486-two.length()*fontSize/2).buildVerticalOffset(187);
 		BufferedImage nameBi2 = drawText(two, zbfont, fontSize,
 				color, 240, 0, 0, true);
 		
 		Random ran = new Random();
 		
 		int q = ran.nextInt(888);
 		int w = ran.nextInt(888);
 		
 		int a = (PinYinUtil.toPinyin(one).length()*q + PinYinUtil.toPinyin(two).length()*w+1314)%95;
 		
 		SimplePositions nameSP3 = new SimplePositions();
 		nameSP3.buildHorizontalOffset(215).buildVerticalOffset(260);
 		BufferedImage nameBi3 = drawText("姻缘值："+a+"分", zbfont, fontSize,
 				color, 600, 0, 0, true);
 		
 		if(a<=20){
 			
 			SimplePositions nameSP4 = new SimplePositions();
 	 		nameSP4.buildHorizontalOffset(148).buildVerticalOffset(360);
 	 		BufferedImage nameBi4 = drawText("你俩幽怨无份，注定无法走到一起！"+"\n"+"        放手吧，对彼此都好。", zbfont, 27,
 	 				color, 475, 0, 0, true);
 			
 			SimplePositions[] sp = {nameSP,nameSP2,nameSP3,nameSP4,nameSP5};

 			BufferedImage[] bis = { nameBi,nameBi2,nameBi3,nameBi4,nameBi5};

 			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
 		}else if(a<=30 && a>20){
 			SimplePositions nameSP4 = new SimplePositions();
 	 		nameSP4.buildHorizontalOffset(148).buildVerticalOffset(360);
 	 		BufferedImage nameBi4 = drawText("此生得不到，余生放不下，你们的姻"+"\n"+"           缘严重不足。", zbfont, 27,
 	 				color, 475, 0, 0, true);
 			
 			SimplePositions[] sp = {nameSP,nameSP2,nameSP3,nameSP4,nameSP5};

 			BufferedImage[] bis = { nameBi,nameBi2,nameBi3,nameBi4,nameBi5};

 			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
 		}else if(a<=40 && a>30){
 			SimplePositions nameSP4 = new SimplePositions();
 	 		nameSP4.buildHorizontalOffset(148).buildVerticalOffset(360);
 	 		BufferedImage nameBi4 = drawText("喜你为疾，药石无医。你俩姻缘值一"+"\n"+"  般只要相信爱情，就能走到一起。", zbfont, 27,
 	 				color, 475, 0, 0, true);
 			
 			SimplePositions[] sp = {nameSP,nameSP2,nameSP3,nameSP4,nameSP5};

 			BufferedImage[] bis = { nameBi,nameBi2,nameBi3,nameBi4,nameBi5};

 			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
 		}else if(a<=50 && a>40){
 			SimplePositions nameSP4 = new SimplePositions();
 	 		nameSP4.buildHorizontalOffset(148).buildVerticalOffset(360);
 	 		BufferedImage nameBi4 = drawText("除了TA，你最爱的是钱，可见你俩"+"\n"+"     姻缘不错，可以走到一起哦！", zbfont, 27,
 	 				color, 475, 0, 0, true);
 			
 			SimplePositions[] sp = {nameSP,nameSP2,nameSP3,nameSP4,nameSP5};

 			BufferedImage[] bis = { nameBi,nameBi2,nameBi3,nameBi4,nameBi5};

 			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
 		}else if(a<=60 && a>50){
 			SimplePositions nameSP4 = new SimplePositions();
 	 		nameSP4.buildHorizontalOffset(148).buildVerticalOffset(360);
 	 		BufferedImage nameBi4 = drawText("你俩姻缘不错，好好保持，一定会幸"+"\n"+"             福美满！", zbfont, 27,
 	 				color, 475, 0, 0, true);
 			
 			SimplePositions[] sp = {nameSP,nameSP2,nameSP3,nameSP4,nameSP5};

 			BufferedImage[] bis = { nameBi,nameBi2,nameBi3,nameBi4,nameBi5};

 			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
 		}else if(a<=70 && a>60){
 			SimplePositions nameSP4 = new SimplePositions();
 	 		nameSP4.buildHorizontalOffset(148).buildVerticalOffset(360);
 	 		BufferedImage nameBi4 = drawText("你们好好准备一下结婚的事吧，姻缘"+"\n"+"             值超高！", zbfont, 27,
 	 				color, 475, 0, 0, true);
 			
 			SimplePositions[] sp = {nameSP,nameSP2,nameSP3,nameSP4,nameSP5};

 			BufferedImage[] bis = { nameBi,nameBi2,nameBi3,nameBi4,nameBi5};

 			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
 		}else if(a<=80 && a>70){
 			SimplePositions nameSP4 = new SimplePositions();
 	 		nameSP4.buildHorizontalOffset(148).buildVerticalOffset(360);
 	 		BufferedImage nameBi4 = drawText("这姻缘值，可以抵御一切小三小四。"+"\n"+"           好好相爱吧！", zbfont, 27,
 	 				color, 475, 0, 0, true);
 			
 			SimplePositions[] sp = {nameSP,nameSP2,nameSP3,nameSP4,nameSP5};

 			BufferedImage[] bis = { nameBi,nameBi2,nameBi3,nameBi4,nameBi5};

 			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
 		}else{
 			SimplePositions nameSP4 = new SimplePositions();
 	 		nameSP4.buildHorizontalOffset(148).buildVerticalOffset(360);
 	 		BufferedImage nameBi4 = drawText("姻缘值爆表，没结婚的赶紧结婚，没"+"\n"+"          交往的赶紧表白！", zbfont, 27,
 	 				color, 475, 0, 0, true);
 			
 			SimplePositions[] sp = {nameSP,nameSP2,nameSP3,nameSP4,nameSP5};

 			BufferedImage[] bis = { nameBi,nameBi2,nameBi3,nameBi4,nameBi5};

 			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
 		}
 		
 		
	}

}
