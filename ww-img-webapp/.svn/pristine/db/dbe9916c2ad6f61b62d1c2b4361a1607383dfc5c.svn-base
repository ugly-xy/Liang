package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.ThreeDraw;

public class TaoBaoReMaiTool extends BaseTool implements ThreeDraw{
	public TaoBaoReMaiTool(StorageService storageService) {
		super(storageService);
	}

	public TaoBaoReMaiTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57b2fd960cf26738f702db7e";
		StorageService storageService = new StorageService();
		TaoBaoReMaiTool tyt = new TaoBaoReMaiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装逼","ceshi.png","售罄",tmpPath0,null));//480 434 
	}
	
	static ZbFont zbfont = ZbFont.黑体加粗;
	static int fontSize = 26;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	static String shoumai[] = {"http://imgzb.zhuangdianbi.com/57b2fd680cf26738f702db64",
							"http://imgzb.zhuangdianbi.com/57b2fd790cf26738f702db6c",
							"http://imgzb.zhuangdianbi.com/57b3da960cf2de8289eff4af"};
	static String one0[] = {"66","68","70","73","76","64","60","71","79","78"};
	static String two0[] = {"80","84","81","82","83","85","86","87","88","89"};
	static String three0[] = {"90","91","92","93","94","95","96","97","98","99"};
	
	
	@Override
	public String draw(String one, String two,String three, String tmpBackPic, String count) throws IOException {
		
		//姓名
 		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(18).buildVerticalOffset(500);
 		BufferedImage nameBi = drawText(one, zbfont, fontSize,
 				color, 800, 100, 0, true);
		
		//玩家照片
		BufferedImage p = super.compressImage(two, 480, 434);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		Random ran = new Random();
		
		int price = ran.nextInt(100000);
		
		if(price < 5000){
			price = price+5000;
		}
		
		//价钱
 		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(69).buildVerticalOffset(532);
 		BufferedImage nameBi2 = drawText(price+"", zbfont, 60,
 				new Color(245,117,52), 295, 100, 0, true);
		
 		BufferedImage p2 = null;
 		SimplePositions pSP2 = null;
 		
		if(three.equals("新品")){
			p2 = super.getImg(shoumai[0]);
			pSP2 = new SimplePositions();
			pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		}else if(three.equals("热卖")){
			p2 = super.getImg(shoumai[1]);
			pSP2 = new SimplePositions();
			pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		}else{
			p2 = super.getImg(shoumai[2]);
			pSP2 = new SimplePositions();
			pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		}
		
		SimplePositions nameSP3 =null;
		BufferedImage nameBi3 = null;
		int a = ran.nextInt(one0.length);
		
		if(price<20000){
			
			nameSP3 = new SimplePositions();
	 		nameSP3.buildHorizontalOffset(18).buildVerticalOffset(640);
	 		nameBi3 = drawText(one0[a]+"%",ZbFont.微软雅黑.font(),fontType, 29,
	 				new Color(236,89,19), 295, 100, 0, true);
		}else if(price<50000){
			nameSP3 = new SimplePositions();
	 		nameSP3.buildHorizontalOffset(18).buildVerticalOffset(640);
	 		nameBi3 = drawText(two0[a]+"%",ZbFont.微软雅黑.font(),fontType, 29,
	 				new Color(236,89,19), 295, 100, 0, true);
		}else{
			nameSP3 = new SimplePositions();
	 		nameSP3.buildHorizontalOffset(18).buildVerticalOffset(640);
	 		nameBi3 = drawText(three0[a]+"%",  ZbFont.微软雅黑.font(),fontType, 29,
	 				new Color(236,89,19), 295, 100, 0, true);
		}
		
		
		
		SimplePositions[] sp = { pSP,pSP2,nameSP,nameSP2,nameSP3};

		BufferedImage[] bis = {p,p2,nameBi,nameBi2,nameBi3};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
