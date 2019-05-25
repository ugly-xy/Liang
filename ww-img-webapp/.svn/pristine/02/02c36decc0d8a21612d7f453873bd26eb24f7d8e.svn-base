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
import com.zb.service.image.i.TwoDraw;

public class FuHeiZhiShuTool extends BaseTool implements TwoDraw{
	public FuHeiZhiShuTool(StorageService storageService) {
		super(storageService);
	}

	public FuHeiZhiShuTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/581c644b0cf2faf3c97f6930";
		StorageService storageService = new StorageService();
		FuHeiZhiShuTool tyt = new FuHeiZhiShuTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装逼王","QQ图片20161011200731.jpg", tmpPath0,
				null));
	}
	
	static String ditu[] = {"http://imgzb.zhuangdianbi.com/581d9abf0cf2a9222c6c35f2",
								"http://imgzb.zhuangdianbi.com/581d9ad30cf2a9222c6c35f6",
								"http://imgzb.zhuangdianbi.com/581d9afa0cf2a9222c6c3601",
								"http://imgzb.zhuangdianbi.com/581d9b050cf2a9222c6c3607",
								"http://imgzb.zhuangdianbi.com/581d9b0e0cf2a9222c6c360e",
								"http://imgzb.zhuangdianbi.com/581d9b160cf2a9222c6c3611",
								"http://imgzb.zhuangdianbi.com/581d9b3f0cf2a9222c6c3622",
								"http://imgzb.zhuangdianbi.com/581d9b490cf2a9222c6c362b",
								"http://imgzb.zhuangdianbi.com/581d9b530cf2a9222c6c3631",
								"http://imgzb.zhuangdianbi.com/581d9b5d0cf2a9222c6c3635"};
	
	static String pingyu[] = {"幼儿园",
								"小学生",
								"讨厌,能不能不要这么高调",
								"就差领证了,还不快去"};
	
	
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize =28;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		
		String three = "";
		
		Random ran= new Random();
		int a = ran.nextInt(101);
		
		//随机底图
		int b = ran.nextInt(ditu.length);
		if(a<1){
			a = a+1;
		}
		
		if(a<=10){
			three = "幼儿园";
		}else if(a<=20){
			three = "小学生";
		}else if(a<=30){
			three = "萌比一枚";
		}else if(a<=40){
			three = "小清新";
		}else if(a<=50){
			three = "还很幼稚";
		}else if(a<=60){
			three = "真闷骚";
		}else if(a<=70){
			three = "不正经";
		}else if(a<=80){
			three = "太邪恶";
		}else if(a<=90){
			three = "大流氓";
		}else{
			three = "腹黑王";
		}
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(105-one.length()*fontSize/2).buildVerticalOffset(158);
		BufferedImage nameBI = drawText(one, zbfont.font(),fontType, fontSize, color,
				700, 100, 0, true);
		//指数
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(410).buildVerticalOffset(35);
		BufferedImage nameBI2 = drawText(a+"°", ZbFont.华康少女.font(),fontType, 50, new Color(0,0,0),
				700, 100, 0, true);
		//评价
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(355-(three.length()+5)*30/2).buildVerticalOffset(120);
		BufferedImage nameBI3 = drawText("你属于： "+three, ZbFont.华康少女.font(),fontType, 30, new Color(0,0,0),
				700, 100, 0, true);
		
		
		//左侧
		BufferedImage p = super.compressImage(two, 120, 120);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(48).buildVerticalOffset(28);
		
		tmpBackPic = ditu[b];
		
		SimplePositions[] sp = {pSP,nameSP,nameSP2,nameSP3};

		BufferedImage[] bis = { p,nameBI,nameBI2,nameBI3};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
