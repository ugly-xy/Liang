package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class ZaZhiFengMianTool extends BaseTool implements TwoDraw{
	public ZaZhiFengMianTool(StorageService storageService) {
		super(storageService);
	}

	public ZaZhiFengMianTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/579f39730cf20c9032aed56b";
		StorageService storageService = new StorageService();
		ZaZhiFengMianTool tyt = new ZaZhiFengMianTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("素材1.png","封面3", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.华康少女;
	static int fontSize = 29;
	static Color color = new Color(61, 19, 38);
	static int fontType = Font.BOLD;
	
	static String S[]  ={"封面1","封面3","封面2"};
	
	static String fengmian[] = {"http://imgzb.zhuangdianbi.com/579f39650cf20c9032aed564",
			"http://imgzb.zhuangdianbi.com/579f39590cf20c9032aed55f",
			"http://imgzb.zhuangdianbi.com/579f39460cf20c9032aed556"};
	
	@Override
	public String draw(String one,String two, String tmpBackPic, String count) throws IOException {
		//玩家照片13917485313
		BufferedImage p = super.compressImage(one, 720, 1280);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		BufferedImage p2 = null;
		SimplePositions pSP2 =null;
		int i =0;
		//封面
		if(two.equals("封面1")){
			i=0;
		}else if(two.equals("封面2")){
			i=1;
		}else{
			i=2;
		}
		
		p2 = super.compressImage(fengmian[i], 720, 1280);
		pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		
 		
 		SimplePositions[] sp = { pSP, pSP2};

		BufferedImage[] bis = { p,p2};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
