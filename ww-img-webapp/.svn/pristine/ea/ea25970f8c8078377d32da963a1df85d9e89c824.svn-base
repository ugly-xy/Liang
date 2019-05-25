package com.zb.service.image.genius;

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

public class FuQiXiangCeShiTool extends BaseTool implements TwoDraw{
	public FuQiXiangCeShiTool(StorageService storageService) {
		super(storageService);
	}

	public FuQiXiangCeShiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/581c644b0cf2faf3c97f6930";
		StorageService storageService = new StorageService();
		FuQiXiangCeShiTool tyt = new FuQiXiangCeShiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("1.png","1.png", tmpPath0,
				null));
	}
	
	static String pingyu[] = {"别想天长地久,至少曾经拥有","感觉有戏,加油哇","好般配的感脚,给跪了","讨厌,能不能不要这么高调","就差领证了,还不快去"};
	
	static String ditu = "http://imgzb.zhuangdianbi.com/581c642f0cf2faf3c97f6921";
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize =56;
	static Color color = new Color(255, 103, 115);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		
		String three = "";
		
		Random ran= new Random();
		int a = ran.nextInt(101);
		
		if(a<20){
			a = a+20;
		}
		
		if(a<=40){
			three = pingyu[0];
		}else if(a<=70){
			three = pingyu[1];
		}else if(a<=80){
			three = pingyu[2];
		}else if(a<=90){
			three = pingyu[3];
		}else{
			three = pingyu[4];
		}
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(146).buildVerticalOffset(300);
		BufferedImage nameBI = drawText("夫妻相指数："+a+"%", zbfont.font(),fontType, fontSize, color,
				700, 100, 0, true);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(360-three.length()*35/2).buildVerticalOffset(390);
		BufferedImage nameBI2 = drawText(three, zbfont, 35, new Color(0,0,0),
				700, 100, 0, true);
		
		
		//左侧
		BufferedImage p = super.compressImage(one, 256, 303);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(80).buildVerticalOffset(480);
		
		//右侧256 303
		BufferedImage p2 = super.compressImage(two, 256, 303);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(380).buildVerticalOffset(480);
		
		//ditu
		BufferedImage p3 = super.getImg(ditu);
		SimplePositions pSP3 = new SimplePositions();
		pSP3.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		SimplePositions[] sp = {pSP,pSP2,pSP3,nameSP,nameSP2};

		BufferedImage[] bis = { p,p2,p3,nameBI,nameBI2};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
