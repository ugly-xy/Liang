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
import com.zb.service.image.i.OneDraw;

public class WeiShenMeDanShenTool extends BaseTool implements OneDraw{
	public WeiShenMeDanShenTool(StorageService storageService) {
		super(storageService);
	}

	public WeiShenMeDanShenTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5814676b0cf248fbe96d022e";
		StorageService storageService = new StorageService();
		WeiShenMeDanShenTool tyt = new WeiShenMeDanShenTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static ZbFont zbfont2 = ZbFont.方正兰亭特黑简体;
	static int fontSize = 80;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	
	static String ditu[] = {"http://imgzb.zhuangdianbi.com/581466910cf248fbe96d01a7",
							"http://imgzb.zhuangdianbi.com/581466a30cf248fbe96d01b5",
							"http://imgzb.zhuangdianbi.com/581466af0cf248fbe96d01ba",
							"http://imgzb.zhuangdianbi.com/581466c30cf248fbe96d01c5",
							"http://imgzb.zhuangdianbi.com/581466cf0cf248fbe96d01c9",
							"http://imgzb.zhuangdianbi.com/581466da0cf248fbe96d01d6",
							"http://imgzb.zhuangdianbi.com/581466e30cf248fbe96d01dc",
							"http://imgzb.zhuangdianbi.com/581466ed0cf248fbe96d01e1",
							"http://imgzb.zhuangdianbi.com/581466f60cf248fbe96d01e5",
							"http://imgzb.zhuangdianbi.com/581467050cf248fbe96d01f0",
							"http://imgzb.zhuangdianbi.com/581467100cf248fbe96d01f8",
							"http://imgzb.zhuangdianbi.com/5814671b0cf248fbe96d01fe",
							"http://imgzb.zhuangdianbi.com/581467260cf248fbe96d0206",
							"http://imgzb.zhuangdianbi.com/581467300cf248fbe96d020e",
							"http://imgzb.zhuangdianbi.com/5814673a0cf248fbe96d0216",
							"http://imgzb.zhuangdianbi.com/581467440cf248fbe96d0219",
							"http://imgzb.zhuangdianbi.com/5814674e0cf248fbe96d0220",
							"http://imgzb.zhuangdianbi.com/581467580cf248fbe96d0227",
							"http://imgzb.zhuangdianbi.com/581467610cf248fbe96d022b",
							"http://imgzb.zhuangdianbi.com/5814676b0cf248fbe96d022e"};
	
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		Random ran = new Random();
		int a = ran.nextInt(ditu.length);
		
		if(a == 0){
			
			//姓名
			int left = 450-6*50/2-one.length()*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(37);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*70).buildVerticalOffset(60);
			BufferedImage nameBI2 = drawText("为什么单身？", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			int left2 = 450-3*50/2-one.length()*70/2;;
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(355);
			BufferedImage nameBI3 = drawText("机智的", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(left2+(3)*50).buildVerticalOffset(332);
			BufferedImage nameBI4 = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			tmpBackPic = ditu[a];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4};
			
			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(a==1){
			//姓名
			int left = 450-6*50/2-one.length()*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(37);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*70).buildVerticalOffset(60);
			BufferedImage nameBI2 = drawText("为什么单身？", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			int left2 = 450-7*50/2-one.length()*70/2;;
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(382);
			BufferedImage nameBI3 = drawText("刚拿短跑冠军的", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(left2+(7)*50).buildVerticalOffset(362);
			BufferedImage nameBI4 = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			tmpBackPic = ditu[a];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4};
			
			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(a==2){
			//姓名
			int left = 450-6*50/2-one.length()*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(27);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*70).buildVerticalOffset(50);
			BufferedImage nameBI2 = drawText("为什么单身？", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			int left2 = 450-10*50/2-one.length()*70/2;;
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(320);
			BufferedImage nameBI3 = drawText("结果她转身给了", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(left2+(7)*50).buildVerticalOffset(297);
			BufferedImage nameBI4 = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP5 = new SimplePositions();
			nameSP5.buildHorizontalOffset(left2+(7)*50+one.length()*70).buildVerticalOffset(320);
			BufferedImage nameBI5 = drawText("一巴掌", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP6 = new SimplePositions();
			nameSP6.buildHorizontalOffset(450-one.length()*50/2).buildVerticalOffset(380);
			BufferedImage nameBI6 = drawText(one, ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			tmpBackPic = ditu[a];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSP5,nameSP6};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBI5,nameBI6};
			
			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(a==3){
			//姓名
			int left = 450-6*50/2-one.length()*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(37);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*70).buildVerticalOffset(60);
			BufferedImage nameBI2 = drawText("为什么单身？", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			int left2 = 450-5*50/2-one.length()*70/2;;
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(495);
			BufferedImage nameBI3 = drawText("擅长牌技的", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(left2+(5)*50).buildVerticalOffset(475);
			BufferedImage nameBI4 = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			tmpBackPic = ditu[a];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4};
			
			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(a==4){
			//姓名
			int left = 450-6*50/2-one.length()*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(27);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*70).buildVerticalOffset(50);
			BufferedImage nameBI2 = drawText("为什么单身？", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			int left2 = 450-6*45/2-one.length()*70/2;;
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(415);
			BufferedImage nameBI3 = drawText("耿直的", ZbFont.微软雅黑, 45, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(left2+(3)*45).buildVerticalOffset(387);
			BufferedImage nameBI4 = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP5 = new SimplePositions();
			nameSP5.buildHorizontalOffset(left2+(3)*45+one.length()*70).buildVerticalOffset(415);
			BufferedImage nameBI5 = drawText("回答道：", ZbFont.微软雅黑, 45, color,
					800, 200, 0, true);
			
			tmpBackPic = ditu[a];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSP5};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBI5};
			
			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(a==5){
			//姓名
			int left = 450-6*50/2-one.length()*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(37);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*70).buildVerticalOffset(60);
			BufferedImage nameBI2 = drawText("为什么单身？", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(450-one.length()*60/2).buildVerticalOffset(390);
			BufferedImage nameBI3 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			tmpBackPic = ditu[a];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3};
			
			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(a==6){
			//姓名
			int left = 450-6*50/2-one.length()*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(57);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*70).buildVerticalOffset(80);
			BufferedImage nameBI2 = drawText("为什么单身？", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			int left2 = 450-2*50/2-one.length()*70/2;;
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(455);
			BufferedImage nameBI3 = drawText("于是", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(left2+(2)*50).buildVerticalOffset(435);
			BufferedImage nameBI4 = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			tmpBackPic = ditu[a];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4};
			
			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(a==7){
			//姓名
			int left = 450-6*50/2-one.length()*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(37);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*70).buildVerticalOffset(60);
			BufferedImage nameBI2 = drawText("为什么单身？", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			int left2 = 450-2*50/2-one.length()*70/2;;
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(395);
			BufferedImage nameBI3 = drawText("于是", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(left2+(2)*50).buildVerticalOffset(375);
			BufferedImage nameBI4 = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			tmpBackPic = ditu[a];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4};
			
			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(a==8){
			//姓名
			int left = 450-6*50/2-one.length()*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(37);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*70).buildVerticalOffset(60);
			BufferedImage nameBI2 = drawText("为什么单身？", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			int left2 = 450-4*50/2-one.length()*70/2;;
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(395);
			BufferedImage nameBI3 = drawText("不信邪的", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(left2+(4)*50).buildVerticalOffset(375);
			BufferedImage nameBI4 = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			tmpBackPic = ditu[a];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4};
			
			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(a==9){
			//姓名
			int left = 450-6*50/2-one.length()*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(37);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*70).buildVerticalOffset(60);
			BufferedImage nameBI2 = drawText("为什么单身？", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			int left2 = 450-5*50/2-one.length()*70/2;;
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(360);
			BufferedImage nameBI3 = drawText("并不宽裕的", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(left2+(5)*50).buildVerticalOffset(340);
			BufferedImage nameBI4 = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			tmpBackPic = ditu[a];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4};
			
			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(a==10){
			//姓名
			int left = 450-6*50/2-one.length()*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(37);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*70).buildVerticalOffset(60);
			BufferedImage nameBI2 = drawText("为什么单身？", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			int left2 = 450-7*50/2-one.length()*70/2;;
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(350);
			BufferedImage nameBI3 = drawText("求知欲望很强的", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(left2+(7)*50).buildVerticalOffset(330);
			BufferedImage nameBI4 = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			tmpBackPic = ditu[a];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4};
			
			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(a==11){
			//姓名
			int left = 450-6*50/2-one.length()*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(37);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*70).buildVerticalOffset(60);
			BufferedImage nameBI2 = drawText("为什么单身？", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			int left2 = 450-4*50/2-one.length()*70/2;;
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(400);
			BufferedImage nameBI3 = drawText("讲道理的", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(left2+(4)*50).buildVerticalOffset(380);
			BufferedImage nameBI4 = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			tmpBackPic = ditu[a];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4};
			
			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(a==12){
			//姓名
			int left = 450-6*50/2-one.length()*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(37);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*70).buildVerticalOffset(60);
			BufferedImage nameBI2 = drawText("为什么单身？", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			int left2 = 450-8*50/2-one.length()*70/2;;
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(400);
			BufferedImage nameBI3 = drawText("并没有接吻经历的", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(left2+(8)*50).buildVerticalOffset(380);
			BufferedImage nameBI4 = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			tmpBackPic = ditu[a];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4};
			
			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(a==13){
			//姓名
			int left = 450-6*50/2-one.length()*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(37);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*70).buildVerticalOffset(60);
			BufferedImage nameBI2 = drawText("为什么单身？", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			int left2 = 450-5*50/2-one.length()*70/2;;
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(400);
			BufferedImage nameBI3 = drawText("爱开玩笑的", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(left2+(5)*50).buildVerticalOffset(380);
			BufferedImage nameBI4 = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			tmpBackPic = ditu[a];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4};
			
			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(a==14){
			//姓名
			int left = 450-6*50/2-one.length()*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(37);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*70).buildVerticalOffset(60);
			BufferedImage nameBI2 = drawText("为什么单身？", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			int left2 = 450-(one.length())*70/2;;
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(470);
			BufferedImage nameBI3 = drawText(one+"说：", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			
			tmpBackPic = ditu[a];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3};
			
			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(a==15){
			//姓名
			int left = 450-6*50/2-one.length()*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(37);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*70).buildVerticalOffset(60);
			BufferedImage nameBI2 = drawText("为什么单身？", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			int left2 = 450-(one.length())*70/2;;
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(400);
			BufferedImage nameBI3 = drawText(one+"说：", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			
			tmpBackPic = ditu[a];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3};
			
			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(a==16){
			//姓名
			int left = 450-6*50/2-one.length()*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(37);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*70).buildVerticalOffset(60);
			BufferedImage nameBI2 = drawText("为什么单身？", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			int left2 = 450-8*40/2-one.length()*60/2;;
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(360);
			BufferedImage nameBI3 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(left2+one.length()*60).buildVerticalOffset(380);
			BufferedImage nameBI4 = drawText("顿时火气就上来了", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			tmpBackPic = ditu[a];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4};
			
			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(a==17){
			//姓名
			int left = 450-6*50/2-one.length()*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(37);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*70).buildVerticalOffset(60);
			BufferedImage nameBI2 = drawText("为什么单身？", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			int left2 = 450-8*40/2-one.length()*60/2;;
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(405);
			BufferedImage nameBI3 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(left2+one.length()*60).buildVerticalOffset(425);
			BufferedImage nameBI4 = drawText("顿时火气就上来了", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			tmpBackPic = ditu[a];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4};
			
			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(a==18){
			//姓名
			int left = 450-6*50/2-one.length()*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(27);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*70).buildVerticalOffset(50);
			BufferedImage nameBI2 = drawText("为什么单身？", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			int left2 = 450-11*50/2-one.length()*50/2;;
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(345);
			BufferedImage nameBI3 = drawText(one, ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(left2+(one.length())*50).buildVerticalOffset(341);
			BufferedImage nameBI4 = drawText("看她", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP5 = new SimplePositions();
			nameSP5.buildHorizontalOffset(left2+(2)*50+one.length()*50).buildVerticalOffset(345);
			BufferedImage nameBI5 = drawText("左边脸上", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP6 = new SimplePositions();
			nameSP6.buildHorizontalOffset(left2+(6)*50+one.length()*50).buildVerticalOffset(341);
			BufferedImage nameBI6 = drawText("有几个痘痘", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			tmpBackPic = ditu[a];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSP5,nameSP6};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBI5,nameBI6};
			
			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(a==19){
			//姓名
			int left = 450-6*50/2-one.length()*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(37);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*70).buildVerticalOffset(60);
			BufferedImage nameBI2 = drawText("为什么单身？", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			int left2 = 450-(one.length())*70/2;;
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(365);
			BufferedImage nameBI3 = drawText(one+"说：", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			
			tmpBackPic = ditu[a];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3};
			
			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}
		
		return null;
	}

}
