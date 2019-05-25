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

public class YouDuDuanZiTool extends BaseTool implements TwoDraw{
	public YouDuDuanZiTool(StorageService storageService) {
		super(storageService);
	}

	public YouDuDuanZiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57ecdec90cf21040d5ba44ea";
		StorageService storageService = new StorageService();
		YouDuDuanZiTool tyt = new YouDuDuanZiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","男", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 80;
	static Color color = new Color(50, 47, 64);
	static int fontType = Font.BOLD;
							
	static String nan[] = {	"http://imgzb.zhuangdianbi.com/57ecdd0a0cf21040d5ba444e",//0
							"http://imgzb.zhuangdianbi.com/57ecdd190cf21040d5ba4450",
							"http://imgzb.zhuangdianbi.com/57ecdd240cf21040d5ba4453",
							"http://imgzb.zhuangdianbi.com/57ecdd320cf21040d5ba4458",
							"http://imgzb.zhuangdianbi.com/57ecdd3d0cf21040d5ba445b",
							"http://imgzb.zhuangdianbi.com/57ecdd4b0cf21040d5ba445f",
							"http://imgzb.zhuangdianbi.com/57ecdd590cf21040d5ba4463",
							"http://imgzb.zhuangdianbi.com/57ecdd650cf21040d5ba4466",
							"http://imgzb.zhuangdianbi.com/57ecdd710cf21040d5ba4468",
							"http://imgzb.zhuangdianbi.com/57ecdd7e0cf21040d5ba446c",
							
							
							"http://imgzb.zhuangdianbi.com/57ecdd8b0cf21040d5ba4471",//1
							"http://imgzb.zhuangdianbi.com/57ecdd990cf21040d5ba4479",
							"http://imgzb.zhuangdianbi.com/57ecdda50cf21040d5ba447d",
							"http://imgzb.zhuangdianbi.com/57ecddaf0cf21040d5ba4482",
							"http://imgzb.zhuangdianbi.com/57ecddbb0cf21040d5ba4488",
							"http://imgzb.zhuangdianbi.com/57ecddc70cf21040d5ba448a",
							"http://imgzb.zhuangdianbi.com/57ecddd30cf21040d5ba448d",
							"http://imgzb.zhuangdianbi.com/57ecdde10cf21040d5ba4491",
							"http://imgzb.zhuangdianbi.com/57ecddeb0cf21040d5ba4492",
							"http://imgzb.zhuangdianbi.com/57ecddf60cf21040d5ba4495",
							
							
							"http://imgzb.zhuangdianbi.com/57ecde050cf21040d5ba449b",//2
							"http://imgzb.zhuangdianbi.com/57ecde110cf21040d5ba449f",
							"http://imgzb.zhuangdianbi.com/57ecde1f0cf21040d5ba44a4",
							"http://imgzb.zhuangdianbi.com/57ecde2b0cf21040d5ba44a7",
							"http://imgzb.zhuangdianbi.com/57ecde380cf21040d5ba44ab",
							"http://imgzb.zhuangdianbi.com/57ecde490cf21040d5ba44ae",
							"http://imgzb.zhuangdianbi.com/57ecde540cf21040d5ba44b4",
							"http://imgzb.zhuangdianbi.com/57ecde600cf21040d5ba44bd",
							"http://imgzb.zhuangdianbi.com/57ecde710cf21040d5ba44cb",
							"http://imgzb.zhuangdianbi.com/57ecde7d0cf21040d5ba44ce",
							
							"http://imgzb.zhuangdianbi.com/57ecde890cf21040d5ba44d2",//3
							"http://imgzb.zhuangdianbi.com/57ecde950cf21040d5ba44d5",
							"http://imgzb.zhuangdianbi.com/57ecdea20cf21040d5ba44da",
							"http://imgzb.zhuangdianbi.com/57ecdeae0cf21040d5ba44de",
							"http://imgzb.zhuangdianbi.com/57ecdebc0cf21040d5ba44e2",
							
							"http://imgzb.zhuangdianbi.com/57edf91d0cf2aa4d7a78a4cc",
							"http://imgzb.zhuangdianbi.com/57edf92d0cf2aa4d7a78a4cf",
							"http://imgzb.zhuangdianbi.com/57edf9480cf2aa4d7a78a4d7",
							"http://imgzb.zhuangdianbi.com/57edf9540cf2aa4d7a78a4dd",
							"http://imgzb.zhuangdianbi.com/57edf9610cf2aa4d7a78a4e1",
							
							"http://imgzb.zhuangdianbi.com/57edf9730cf2aa4d7a78a4e4",//4
							"http://imgzb.zhuangdianbi.com/57edf9800cf2aa4d7a78a4ea",
							"http://imgzb.zhuangdianbi.com/57edf98e0cf2aa4d7a78a4ec",
							"http://imgzb.zhuangdianbi.com/57edf99d0cf2aa4d7a78a4ef",
							"http://imgzb.zhuangdianbi.com/57edf9d20cf2aa4d7a78a503",
							"http://imgzb.zhuangdianbi.com/57edf9df0cf2aa4d7a78a50c",
							"http://imgzb.zhuangdianbi.com/57edf9ed0cf2aa4d7a78a512",
							"http://imgzb.zhuangdianbi.com/57edfa260cf2aa4d7a78a526",
							"http://imgzb.zhuangdianbi.com/57edfa340cf2aa4d7a78a52a",
							"http://imgzb.zhuangdianbi.com/57edfa410cf2aa4d7a78a52e",
							
							"http://imgzb.zhuangdianbi.com/57edfa4f0cf2aa4d7a78a532",//5
							"http://imgzb.zhuangdianbi.com/57edfa600cf2aa4d7a78a53a",
							"http://imgzb.zhuangdianbi.com/57edfa6f0cf2aa4d7a78a540",
							"http://imgzb.zhuangdianbi.com/57edfa7d0cf2aa4d7a78a548",
							"http://imgzb.zhuangdianbi.com/57edfa960cf2aa4d7a78a54e",
							"http://imgzb.zhuangdianbi.com/57edfaa70cf2aa4d7a78a555",
							"http://imgzb.zhuangdianbi.com/57edfab50cf2aa4d7a78a559",
							"http://imgzb.zhuangdianbi.com/57edfac90cf2aa4d7a78a561",
							"http://imgzb.zhuangdianbi.com/57edfad70cf2aa4d7a78a566",
							"http://imgzb.zhuangdianbi.com/57edfae30cf2aa4d7a78a56c",
							
							"http://imgzb.zhuangdianbi.com/57edfaf10cf2aa4d7a78a571",//6
							"http://imgzb.zhuangdianbi.com/57edfb080cf2aa4d7a78a574",
							"http://imgzb.zhuangdianbi.com/57edfb150cf2aa4d7a78a578",
							"http://imgzb.zhuangdianbi.com/57edfb230cf2aa4d7a78a57c",
							"http://imgzb.zhuangdianbi.com/57edfb300cf2aa4d7a78a581",
							"http://imgzb.zhuangdianbi.com/57edfb3d0cf2aa4d7a78a58b",
							//51
							"http://imgzb.zhuangdianbi.com/57edfb4b0cf2aa4d7a78a592",//66
							"http://imgzb.zhuangdianbi.com/57edfb600cf2aa4d7a78a597",
							"http://imgzb.zhuangdianbi.com/57edfb710cf2aa4d7a78a5a5",
							"http://imgzb.zhuangdianbi.com/57edfb7e0cf2aa4d7a78a5a8",
							
							
							"http://imgzb.zhuangdianbi.com/57edfb8e0cf2aa4d7a78a5a9",//7
							"http://imgzb.zhuangdianbi.com/57edfb9e0cf2aa4d7a78a5b0",
							"http://imgzb.zhuangdianbi.com/57edfbaa0cf2aa4d7a78a5bc",
							"http://imgzb.zhuangdianbi.com/57edfbb80cf2aa4d7a78a5be",
							"http://imgzb.zhuangdianbi.com/57edfbc60cf2aa4d7a78a5bf",
							"http://imgzb.zhuangdianbi.com/57edfbd40cf2aa4d7a78a5c0"
							};
	
	static String nv[] = {"http://imgzb.zhuangdianbi.com/57ecdd0a0cf21040d5ba444e",//0
							"http://imgzb.zhuangdianbi.com/57ecdd190cf21040d5ba4450",
							"http://imgzb.zhuangdianbi.com/57ecdd240cf21040d5ba4453",
							"http://imgzb.zhuangdianbi.com/57ecdd320cf21040d5ba4458",
							"http://imgzb.zhuangdianbi.com/57ecdd3d0cf21040d5ba445b",
							"http://imgzb.zhuangdianbi.com/57ecdd4b0cf21040d5ba445f",
							"http://imgzb.zhuangdianbi.com/57ecdd590cf21040d5ba4463",
							"http://imgzb.zhuangdianbi.com/57ecdd650cf21040d5ba4466",
							"http://imgzb.zhuangdianbi.com/57ecdd710cf21040d5ba4468",
							"http://imgzb.zhuangdianbi.com/57ecdd7e0cf21040d5ba446c",
							
							
							"http://imgzb.zhuangdianbi.com/57ecdd8b0cf21040d5ba4471",//1
							"http://imgzb.zhuangdianbi.com/57ecdd990cf21040d5ba4479",
							"http://imgzb.zhuangdianbi.com/57ecdda50cf21040d5ba447d",
							"http://imgzb.zhuangdianbi.com/57ecddaf0cf21040d5ba4482",
							"http://imgzb.zhuangdianbi.com/57ecddbb0cf21040d5ba4488",
							"http://imgzb.zhuangdianbi.com/57ecddc70cf21040d5ba448a",
							"http://imgzb.zhuangdianbi.com/57ecddd30cf21040d5ba448d",
							"http://imgzb.zhuangdianbi.com/57ecdde10cf21040d5ba4491",
							"http://imgzb.zhuangdianbi.com/57ecddeb0cf21040d5ba4492",
							"http://imgzb.zhuangdianbi.com/57ecddf60cf21040d5ba4495",
							
							
							"http://imgzb.zhuangdianbi.com/57ecde050cf21040d5ba449b",//2
							"http://imgzb.zhuangdianbi.com/57ecde110cf21040d5ba449f",
							"http://imgzb.zhuangdianbi.com/57ecde1f0cf21040d5ba44a4",
							"http://imgzb.zhuangdianbi.com/57ecde2b0cf21040d5ba44a7",
							"http://imgzb.zhuangdianbi.com/57ecde380cf21040d5ba44ab",
							"http://imgzb.zhuangdianbi.com/57ecde490cf21040d5ba44ae",
							"http://imgzb.zhuangdianbi.com/57ecde540cf21040d5ba44b4",
							"http://imgzb.zhuangdianbi.com/57ecde600cf21040d5ba44bd",
							"http://imgzb.zhuangdianbi.com/57ecde710cf21040d5ba44cb",
							"http://imgzb.zhuangdianbi.com/57ecde7d0cf21040d5ba44ce",
							
							"http://imgzb.zhuangdianbi.com/57ecde890cf21040d5ba44d2",//3
							"http://imgzb.zhuangdianbi.com/57ecde950cf21040d5ba44d5",
							"http://imgzb.zhuangdianbi.com/57ecdea20cf21040d5ba44da",
							"http://imgzb.zhuangdianbi.com/57ecdeae0cf21040d5ba44de",
							"http://imgzb.zhuangdianbi.com/57ecdebc0cf21040d5ba44e2",
							
							"http://imgzb.zhuangdianbi.com/57edf91d0cf2aa4d7a78a4cc",
							"http://imgzb.zhuangdianbi.com/57edf92d0cf2aa4d7a78a4cf",
							"http://imgzb.zhuangdianbi.com/57edf9480cf2aa4d7a78a4d7",
							"http://imgzb.zhuangdianbi.com/57edf9540cf2aa4d7a78a4dd",
							"http://imgzb.zhuangdianbi.com/57edf9610cf2aa4d7a78a4e1",
							
							"http://imgzb.zhuangdianbi.com/57edf9730cf2aa4d7a78a4e4",//4
							"http://imgzb.zhuangdianbi.com/57edf9800cf2aa4d7a78a4ea",
							"http://imgzb.zhuangdianbi.com/57edf98e0cf2aa4d7a78a4ec",
							"http://imgzb.zhuangdianbi.com/57edf99d0cf2aa4d7a78a4ef",
							"http://imgzb.zhuangdianbi.com/57edf9d20cf2aa4d7a78a503",
							"http://imgzb.zhuangdianbi.com/57edf9df0cf2aa4d7a78a50c",
							"http://imgzb.zhuangdianbi.com/57edf9ed0cf2aa4d7a78a512",
							"http://imgzb.zhuangdianbi.com/57edfa260cf2aa4d7a78a526",
							"http://imgzb.zhuangdianbi.com/57edfa340cf2aa4d7a78a52a",
							"http://imgzb.zhuangdianbi.com/57edfa410cf2aa4d7a78a52e",
							
							"http://imgzb.zhuangdianbi.com/57edfa4f0cf2aa4d7a78a532",//5
							"http://imgzb.zhuangdianbi.com/57edfa600cf2aa4d7a78a53a",
							"http://imgzb.zhuangdianbi.com/57edfa6f0cf2aa4d7a78a540",
							"http://imgzb.zhuangdianbi.com/57edfa7d0cf2aa4d7a78a548",
							"http://imgzb.zhuangdianbi.com/57edfa960cf2aa4d7a78a54e",
							"http://imgzb.zhuangdianbi.com/57edfaa70cf2aa4d7a78a555",
							"http://imgzb.zhuangdianbi.com/57edfab50cf2aa4d7a78a559",
							"http://imgzb.zhuangdianbi.com/57edfac90cf2aa4d7a78a561",
							"http://imgzb.zhuangdianbi.com/57edfad70cf2aa4d7a78a566",
							"http://imgzb.zhuangdianbi.com/57edfae30cf2aa4d7a78a56c",
							
							"http://imgzb.zhuangdianbi.com/57edfaf10cf2aa4d7a78a571",//6
							"http://imgzb.zhuangdianbi.com/57edfb080cf2aa4d7a78a574",
							"http://imgzb.zhuangdianbi.com/57edfb150cf2aa4d7a78a578",
							"http://imgzb.zhuangdianbi.com/57edfb230cf2aa4d7a78a57c",
							"http://imgzb.zhuangdianbi.com/57edfb300cf2aa4d7a78a581",
							"http://imgzb.zhuangdianbi.com/57edfb3d0cf2aa4d7a78a58b",
							
							
			
			
							"http://imgzb.zhuangdianbi.com/57ee0c3a0cf2aa4d7a78aa22",//66
							"http://imgzb.zhuangdianbi.com/57ee0c4b0cf2aa4d7a78aa30",
							"http://imgzb.zhuangdianbi.com/57ee0c5b0cf2aa4d7a78aa34",
							"http://imgzb.zhuangdianbi.com/57ee0c690cf2aa4d7a78aa37",
							
							
							"http://imgzb.zhuangdianbi.com/57ee0c790cf2aa4d7a78aa39",//7
							"http://imgzb.zhuangdianbi.com/57ee0c870cf2aa4d7a78aa40",
							"http://imgzb.zhuangdianbi.com/57ee0c970cf2aa4d7a78aa45",
							"http://imgzb.zhuangdianbi.com/57ee0ca60cf2aa4d7a78aa49",
							"http://imgzb.zhuangdianbi.com/57ee0cb90cf2aa4d7a78aa4f",
							"http://imgzb.zhuangdianbi.com/57ee0cc60cf2aa4d7a78aa54",
							"http://imgzb.zhuangdianbi.com/57ee0cd30cf2aa4d7a78aa57",
							"http://imgzb.zhuangdianbi.com/57ee0ce60cf2aa4d7a78aa62",
							"http://imgzb.zhuangdianbi.com/57ee0cf50cf2aa4d7a78aa67",
							"http://imgzb.zhuangdianbi.com/57ee0d020cf2aa4d7a78aa6a",
							
							"http://imgzb.zhuangdianbi.com/57ee0d110cf2aa4d7a78aa6f",//8
							"http://imgzb.zhuangdianbi.com/57ee0d200cf2aa4d7a78aa70",
							"http://imgzb.zhuangdianbi.com/57ee0d2d0cf2aa4d7a78aa74",
							"http://imgzb.zhuangdianbi.com/57ee0d380cf2aa4d7a78aa76"};
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		
		//2016
		SimplePositions nameSPdi = new SimplePositions();
		nameSPdi.buildHorizontalOffset(347-(one.length()+7)*20/2).buildVerticalOffset(665);
		BufferedImage nameBIdi = drawText("——2016 "+one +" 的故事", ZbFont.方正兰亭特黑简体, 20, color,
				800, 300, 0, true);
		
		
		Random ran = new Random();
		
		int nanren = ran.nextInt(nan.length);
				
				
		
		int nvren =ran.nextInt(nv.length);
		if(two.equals("男")){
			if(nanren ==0){
				//姓名
				int left = 347-one.length()*65/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(90);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 65, color,
						800, 300, 0, true);
				
				//说
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*65+5).buildVerticalOffset(120);
				BufferedImage nameBI2 = drawText("说:", ZbFont.微软雅黑, 40, color,
						800, 300, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 1){
				//姓名
				int left = 347-(one.length())*45/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(300);
				BufferedImage nameBI = drawText(one+" :", ZbFont.方正兰亭特黑简体, 45, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 2){
				//姓名
				int left = 347-(one.length()+9)*40/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(355);
				BufferedImage nameBI = drawText("回家的路上"+one+"一直在想", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 3){
				//姓名
				int left = 374-(one.length()+4)*50/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(60);
				BufferedImage nameBI = drawText(one+"长这么大", ZbFont.方正兰亭特黑简体, 50, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 4){
				//姓名
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(63).buildVerticalOffset(70);
				BufferedImage nameBI = drawText(one+" :", ZbFont.方正兰亭特黑简体, 50, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 5){
				//姓名
				int left = 374-(one.length()+3)*50/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(50);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 50, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+(one.length())*50).buildVerticalOffset(74);
				BufferedImage nameBI2 = drawText(" 哭着说", ZbFont.微软雅黑, 30, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 6){
				//姓名
				int left = 374-(one.length()+4)*50/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(210);
				BufferedImage nameBI = drawText("传销骨干", ZbFont.微软雅黑, 50, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+(4)*50).buildVerticalOffset(215);
				BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 50, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 7){
				//姓名
				int left = 374-4*50/2-one.length()*70/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(270);
				BufferedImage nameBI = drawText("送", ZbFont.微软雅黑, 50, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+(1)*50).buildVerticalOffset(247);
				BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
						800, 200, 0, true);
				
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(left+(1)*50+one.length()*70).buildVerticalOffset(270);
				BufferedImage nameBI3 = drawText("上月球", ZbFont.微软雅黑, 50, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 8){
				//姓名
				int left = 374-2*50/2-(one.length()+4)*70/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(110);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*70).buildVerticalOffset(130);
				BufferedImage nameBI2 = drawText("也曾", ZbFont.微软雅黑, 50, color,
						800, 200, 0, true);
				
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(left+one.length()*70+2*50).buildVerticalOffset(110);
				BufferedImage nameBI3 = drawText("青春逼人", ZbFont.方正兰亭特黑简体, 70, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 9){
				//姓名
				int left = 374-one.length()*60/2-4*40/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(340);
				BufferedImage nameBI = drawText("人们都说 ", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+4*40).buildVerticalOffset(312);
				BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 10){
				//姓名
				int left = 374-one.length()*90/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(80);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 90, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 11){
				//姓名
				int left = 374-6*50/2-one.length()*70/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(60);
				BufferedImage nameBI = drawText("所有人都觉得", ZbFont.微软雅黑, 50, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+(6)*50).buildVerticalOffset(37);
				BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
						800, 200, 0, true);
				int left2 = 374-3*50/2-one.length()*70/2;;
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(270);
				BufferedImage nameBI3 = drawText("其实...", ZbFont.微软雅黑, 50, color,
						800, 200, 0, true);
				
				SimplePositions nameSP4 = new SimplePositions();
				nameSP4.buildHorizontalOffset(left2+(3)*50).buildVerticalOffset(247);
				BufferedImage nameBI4 = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 12){
				//姓名
				int left = 374-(one.length()+2)*50/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(35);
				BufferedImage nameBI = drawText(one+"学车", ZbFont.方正兰亭特黑简体, 50, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 13){
				//姓名
				int left = 374-(one.length()+7)*50/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(35);
				BufferedImage nameBI = drawText("来自"+one+"的温馨提醒", ZbFont.方正兰亭特黑简体, 50, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 14){
				//姓名
				int left = 374-(one.length()+3)*30/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(46);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 30, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+(one.length())*30).buildVerticalOffset(44);
				BufferedImage nameBI2 = drawText("问电扇", ZbFont.微软雅黑, 30, color,
						800, 200, 0, true);
				int left2 = 374-(one.length()+4)*30/2;
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(258);
				BufferedImage nameBI3 = drawText(one, ZbFont.方正兰亭特黑简体, 30, color,
						800, 200, 0, true);
				
				SimplePositions nameSP4 = new SimplePositions();
				nameSP4.buildHorizontalOffset(left2+(one.length())*30).buildVerticalOffset(256);
				BufferedImage nameBI4 = drawText("如释重负", ZbFont.微软雅黑, 30, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 15){
				//姓名
				int left = 374-2*50/2-(one.length()+3)*70/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(40);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*70).buildVerticalOffset(60);
				BufferedImage nameBI2 = drawText("是个", ZbFont.微软雅黑, 50, color,
						800, 200, 0, true);
				
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(left+one.length()*70+2*50).buildVerticalOffset(40);
				BufferedImage nameBI3 = drawText("热心人", ZbFont.方正兰亭特黑简体, 70, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 16){
				//姓名
				int left = 374-10*50/2-(one.length())*70/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(60);
				BufferedImage nameBI = drawText("当", ZbFont.微软雅黑, 50, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+1*50).buildVerticalOffset(40);
				BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
						800, 200, 0, true);
				
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(left+one.length()*70+1*50).buildVerticalOffset(60);
				BufferedImage nameBI3 = drawText("走到人生的十字路口", ZbFont.微软雅黑, 50, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 17){
				//姓名
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(374-(one.length()+1)*70/2).buildVerticalOffset(70);
				BufferedImage nameBI = drawText(one+" :", ZbFont.方正兰亭特黑简体, 70, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 18){
				//姓名
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(374-(one.length()+6)*60/2).buildVerticalOffset(70);
				BufferedImage nameBI = drawText(one+"，上帝很公平", ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 19){
				//姓名
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(374-(one.length()+4)*50/2).buildVerticalOffset(70);
				BufferedImage nameBI = drawText("对于"+one+"来说", ZbFont.方正兰亭特黑简体, 50, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 20){
				//姓名
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(374-(one.length()+12)*30/2).buildVerticalOffset(115);
				BufferedImage nameBI = drawText(one+"看见小偷偷了一位女士的包", ZbFont.方正兰亭特黑简体, 30, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(374-(one.length()+11)*30/2).buildVerticalOffset(380);
				BufferedImage nameBI2 = drawText("下车的时候，女士拉着"+one+"下车", ZbFont.方正兰亭特黑简体, 30, color,
						800, 200, 0, true);
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(374-(one.length()+10)*30/2).buildVerticalOffset(433);
				BufferedImage nameBI3 = drawText(one+"急了说：她不是我妈妈！", ZbFont.方正兰亭特黑简体, 30, color,
						800, 200, 0, true);
				
				SimplePositions nameSP4 = new SimplePositions();
				nameSP4.buildHorizontalOffset(374-(one.length()+9)*30/2).buildVerticalOffset(560);
				BufferedImage nameBI4 = drawText("结果"+one+"被卖到外地挖煤。", ZbFont.方正兰亭特黑简体, 30, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 21){
				//姓名
				int left = 374-one.length()*60/2-4*40/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(60);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(80);
				BufferedImage nameBI2 = drawText("近日便秘", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 22){
				//姓名
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(374-(one.length()+4)*40/2).buildVerticalOffset(110);
				BufferedImage nameBI = drawText(one+"：。。。", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(374-(one.length()+4)*40/2).buildVerticalOffset(270);
				BufferedImage nameBI2 = drawText(one+"：？？？", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(374-(one.length()+1)*40/2-2*70/2).buildVerticalOffset(470);
				BufferedImage nameBI3 = drawText(one+"：", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP4 = new SimplePositions();
				nameSP4.buildHorizontalOffset(374+(one.length())*40/2-(one.length()+1)*40/2).buildVerticalOffset(433);
				BufferedImage nameBI4 = drawText("妈妈！", ZbFont.方正兰亭特黑简体, 70, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 23){
				//姓名
				int left = 374-5*40/2-(one.length())*70/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(40);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*70).buildVerticalOffset(75);
				BufferedImage nameBI2 = drawText("借了很多钱，", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(374-one.length()*50/2-8*50/2).buildVerticalOffset(490);
				BufferedImage nameBI3 = drawText("儿子愤怒地对"+one+"喊道", ZbFont.方正兰亭特黑简体, 50, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 24){
				//姓名
				int left = 374-one.length()*60/2-5*40/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(173);
				BufferedImage nameBI = drawText("请好好珍惜", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+5*40).buildVerticalOffset(150);
				BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 25){
				//姓名
				int left = 374-6*50/2-(one.length())*70/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(138);
				BufferedImage nameBI = drawText("忽然听到", ZbFont.微软雅黑, 50, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+4*50).buildVerticalOffset(115);
				BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
						800, 200, 0, true);
				
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(left+one.length()*70+4*50).buildVerticalOffset(138);
				BufferedImage nameBI3 = drawText("说：", ZbFont.微软雅黑, 50, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 26){
				//姓名
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(380-(one.length()+7)*50/2).buildVerticalOffset(130);
				BufferedImage nameBI = drawText(one+"因为比较害羞，", ZbFont.方正兰亭特黑简体, 50, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 27){
				//姓名
				int left = 374-(one.length()+2)*70/2-5*40/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(40);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*70).buildVerticalOffset(75);
				BufferedImage nameBI2 = drawText("总在办公室", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(left+one.length()*70+5*40).buildVerticalOffset(40);
				BufferedImage nameBI3 = drawText("放屁", ZbFont.方正兰亭特黑简体, 70, color,
						800, 200, 0, true);
				
				SimplePositions nameSP4 = new SimplePositions();
				nameSP4.buildHorizontalOffset(374-(one.length()+6)*40/2).buildVerticalOffset(320);
				BufferedImage nameBI4 = drawText("然后"+one+"摇了起来", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 28){
				//姓名
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(374-(one.length()+1)*70/2).buildVerticalOffset(310);
				BufferedImage nameBI = drawText(one+" ：", ZbFont.方正兰亭特黑简体, 70, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 29){
				//姓名
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(374-(one.length())*80/2).buildVerticalOffset(80);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 80, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 30){
				//姓名
				int left = 374-(one.length()+3)*60/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(40);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*40).buildVerticalOffset(36);
				BufferedImage nameBI2 = drawText("问辅导员：", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 31){
				//姓名
				int left = 374-(one.length())*60/2-5*40/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(25);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(50);
				BufferedImage nameBI2 = drawText("长得像砖头", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 32){
				//姓名
				int left = 374-(one.length()+4)*70/2-2*40/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(130);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*70).buildVerticalOffset(165);
				BufferedImage nameBI2 = drawText("总是", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(left+one.length()*70+2*40).buildVerticalOffset(130);
				BufferedImage nameBI3 = drawText("闭着眼睛", ZbFont.方正兰亭特黑简体, 70, color,
						800, 200, 0, true);
				
				SimplePositions nameSP4 = new SimplePositions();
				nameSP4.buildHorizontalOffset(374-(one.length()+9)*40/2).buildVerticalOffset(500);
				BufferedImage nameBI4 = drawText("那一瞬间，"+one+"还是哭了", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 33){
				//姓名
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(374-(one.length())*60/2).buildVerticalOffset(230);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 34){
				//姓名
				int left = 374-one.length()*60/2-3*40/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(149);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(167);
				BufferedImage nameBI2 = drawText("很专一", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 35){
				//姓名
				int left = 374-one.length()*60/2-5*40/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(95);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(115);
				BufferedImage nameBI2 = drawText("过去问道：", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(380-(one.length()+10)*40/2).buildVerticalOffset(400);
				BufferedImage nameBI3 = drawText(one+"：好嘞，谢谢大爷！", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 36){
				//姓名
				int left = 374-one.length()*80/2-3*50/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(80);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 80, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*80).buildVerticalOffset(120);
				BufferedImage nameBI2 = drawText("曾获得", ZbFont.方正兰亭特黑简体, 50, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 37){
				//姓名
				int left = 374-one.length()*80/2-6*40/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(10);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 80, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*80).buildVerticalOffset(60);
				BufferedImage nameBI2 = drawText("已经不是那个", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 38){
				//姓名
				int left = 374-4*40/2-one.length()*60/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(330);
				BufferedImage nameBI = drawText("对于", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+(2)*40).buildVerticalOffset(307);
				BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(left+(2)*40+one.length()*60).buildVerticalOffset(330);
				BufferedImage nameBI3 = drawText("来说", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 39){
				//姓名
				int left = 374-(one.length()+2)*80/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(70);
				BufferedImage nameBI = drawText("昨晚"+one, ZbFont.方正兰亭特黑简体, 80, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 40){
				//姓名
				int left = 374-one.length()*60/2-2*40/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(40);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(63);
				BufferedImage nameBI2 = drawText("说：", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 41){
				//姓名
				int left = 374-4*40/2-one.length()*60/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(103);
				BufferedImage nameBI = drawText("对于", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+(2)*40).buildVerticalOffset(80);
				BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(left+(2)*40+one.length()*60).buildVerticalOffset(103);
				BufferedImage nameBI3 = drawText("来说", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 42){
				//姓名
				int left = 374-7*40/2-one.length()*60/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(103);
				BufferedImage nameBI = drawText("每次别人找", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+(5)*40).buildVerticalOffset(80);
				BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(left+(5)*40+one.length()*60).buildVerticalOffset(103);
				BufferedImage nameBI3 = drawText("问路", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP4 = new SimplePositions();
				nameSP4.buildHorizontalOffset(374-(one.length()+5)*40/2).buildVerticalOffset(167);
				BufferedImage nameBI4 = drawText(one+"都是瞎指的", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 43){
				//姓名
				int left = 374-5*40/2-one.length()*60/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(53);
				BufferedImage nameBI = drawText("在公厕看到", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+(5)*40).buildVerticalOffset(28);
				BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(374-(one.length()+11)*40/2).buildVerticalOffset(370);
				BufferedImage nameBI3 = drawText("“并没有”", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP4 = new SimplePositions();
				nameSP4.buildHorizontalOffset(374-(4)*40/2).buildVerticalOffset(373);
				BufferedImage nameBI4 = drawText(one+"甩了甩头发，", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 44){
				//姓名
				int left = 374-one.length()*60/2-10*40/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(210);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(231);
				BufferedImage nameBI2 = drawText("临危不惧，沉着冷静", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 45){
				//姓名
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(374-one.length()*60/2).buildVerticalOffset(30);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(374-(one.length()+11)*40/2).buildVerticalOffset(376);
				BufferedImage nameBI2 = drawText("有人问"+one+"，7岁干嘛去了？", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 46){
				//姓名
				int left = 374-one.length()*60/2-2*40/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(30);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(57);
				BufferedImage nameBI2 = drawText("说：", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 47){
				//姓名
				int left = 374-6*40/2-(one.length())*70/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(40);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*70).buildVerticalOffset(78);
				BufferedImage nameBI2 = drawText("从小就有", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(left+one.length()*70+4*40).buildVerticalOffset(80);
				BufferedImage nameBI3 = drawText("梦想", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 48){
				//姓名
				int left = 374-(one.length()+4)*40/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(129);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*40).buildVerticalOffset(127);
				BufferedImage nameBI2 = drawText("画着画着", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 49){
				//姓名
				int left = 374-(4)*40/2-one.length()*60/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(60);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(83);
				BufferedImage nameBI2 = drawText("开始相信", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 50){
				//姓名
				int left = 374-(one.length()+11)*40/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(46);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+(one.length())*40).buildVerticalOffset(44);
				BufferedImage nameBI2 = drawText("到菜市场买鸡蛋，问老板：", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				int left2 = 374-(one.length()+4)*40/2;
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(310);
				BufferedImage nameBI3 = drawText(one, ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP4 = new SimplePositions();
				nameSP4.buildHorizontalOffset(left2+(one.length())*40).buildVerticalOffset(306);
				BufferedImage nameBI4 = drawText("想了想：", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 51){
				//姓名
				int left = 374-(one.length()+11)*40/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(161);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+(one.length())*40).buildVerticalOffset(158);
				BufferedImage nameBI2 = drawText("赶紧买了两盒", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(left+(one.length()+6)*40).buildVerticalOffset(161);
				BufferedImage nameBI3 = drawText("中华", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP4 = new SimplePositions();
				nameSP4.buildHorizontalOffset(left+(one.length()+8)*40).buildVerticalOffset(158);
				BufferedImage nameBI4 = drawText("送领导", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 52){
				//姓名
				int left = 374-(one.length())*60/2-8*40/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(161);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+(one.length())*60).buildVerticalOffset(187);
				BufferedImage nameBI2 = drawText("就把同桌打了一顿", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(374-(one.length()+8)*40/2).buildVerticalOffset(300);
				BufferedImage nameBI3 = drawText(one+"，教你家长一起来", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP4 = new SimplePositions();
				nameSP4.buildHorizontalOffset(374-(one.length()+6)*40/2).buildVerticalOffset(365);
				BufferedImage nameBI4 = drawText(one+"笑着说：没事，", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 53){
				//姓名
				int left = 374-9*40/2-one.length()*60/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(160);
				BufferedImage nameBI = drawText("都以为", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+(3)*40).buildVerticalOffset(136);
				BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(left+(3)*40+one.length()*60).buildVerticalOffset(160);
				BufferedImage nameBI3 = drawText("混的风生水起", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP4 = new SimplePositions();
				nameSP4.buildHorizontalOffset(374-(one.length()+2)*40/2).buildVerticalOffset(240);
				BufferedImage nameBI4 = drawText(one+"只是", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 54){
				//姓名
				int left = 374-9*40/2-one.length()*60/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(440);
				BufferedImage nameBI = drawText("说完就往", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+(4)*40).buildVerticalOffset(415);
				BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(left+(4)*40+one.length()*60).buildVerticalOffset(440);
				BufferedImage nameBI3 = drawText("嘴里灌...", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 55){
				//姓名
				int left = 374-one.length()*60/2-4*40/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(350);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(372);
				BufferedImage nameBI2 = drawText("站在窗口", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 56){
				//姓名
				int left = 374-one.length()*60/2-3*40/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(165);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(185);
				BufferedImage nameBI2 = drawText("也没用", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				//姓名
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(374-(one.length()+2)*40/2).buildVerticalOffset(255);
				BufferedImage nameBI3 = drawText("因为"+one, ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 57){
				//姓名
				int left = 374-one.length()*60/2-5*40/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(55);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(78);
				BufferedImage nameBI2 = drawText("要好好工作", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 58){
				//姓名
				int left = 374-(one.length()+4)*65/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(200);
				BufferedImage nameBI = drawText(one+"“ 妈！”", ZbFont.方正兰亭特黑简体, 65, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 59){
				//姓名
				int left = 374-5*40/2-one.length()*60/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(25);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(48);
				BufferedImage nameBI2 = drawText("以前是网管", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(374-(one.length()+9)*40/2).buildVerticalOffset(180);
				BufferedImage nameBI3 = drawText(one+"灵机一动把网线弄断", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP4 = new SimplePositions();
				nameSP4.buildHorizontalOffset(374-(one.length()+6)*40/2).buildVerticalOffset(460);
				BufferedImage nameBI4 = drawText("每次"+one+"说到这儿", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 60){
				//姓名
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(63).buildVerticalOffset(50);
				BufferedImage nameBI = drawText(one+"：", ZbFont.方正兰亭特黑简体, 65, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 61){
				//姓名
				int left = 374-10*40/2-one.length()*60/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(25);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(48);
				BufferedImage nameBI2 = drawText("一直觉得自己是穷二代", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(374-(one.length()+6)*40/2).buildVerticalOffset(120);
				BufferedImage nameBI3 = drawText("某天老爸对"+one+"说", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP4 = new SimplePositions();
				nameSP4.buildHorizontalOffset(374-(one.length()+2)*40/2).buildVerticalOffset(265);
				BufferedImage nameBI4 = drawText(one+"狂喜", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 62){
				//姓名
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(374-(one.length()+2)*40/2).buildVerticalOffset(30);
				BufferedImage nameBI = drawText("“"+one+"！”", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 63){
				//姓名
				int left = 374-one.length()*60/2-5*50/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(93);
				BufferedImage nameBI = drawText("心狠手辣的", ZbFont.方正兰亭特黑简体, 50, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+5*50).buildVerticalOffset(78);
				BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 64){
				//姓名
				int left = 374-one.length()*60/2-5*50/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(63);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(72);
				BufferedImage nameBI2 = drawText("乔装成海螺", ZbFont.微软雅黑, 50, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 65){
				//姓名
				int left = 374-(one.length())*50/2-9*40/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(100);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 50, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+(one.length())*50).buildVerticalOffset(112);
				BufferedImage nameBI2 = drawText("吗，请问您的信用卡", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(60).buildVerticalOffset(350);
				BufferedImage nameBI3 = drawText(one, ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP4 = new SimplePositions();
				nameSP4.buildHorizontalOffset(60+(one.length())*40).buildVerticalOffset(335);
				BufferedImage nameBI4 = drawText(":“是的！”", ZbFont.方正兰亭特黑简体, 50, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 66){
				//姓名
				int left = 347-(one.length()+6)*56/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(114);
				BufferedImage nameBI = drawText("问"+one+"这男的是谁?!", ZbFont.方正兰亭特黑简体, 56, color,
						800, 200, 0, true);
				
				//姓名
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(347-(one.length()+6)*36/2).buildVerticalOffset(204);
				BufferedImage nameBI2 = drawText(one+"他头也不抬,说:", ZbFont.微软雅黑, 36, color,
						800, 300, 0, true);
				
				//姓名
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(347-(one.length()+5)*55/2).buildVerticalOffset(410);
				BufferedImage nameBI3 = drawText("至今"+one+"还不明白", ZbFont.方正兰亭特黑简体, 55, color,
						800, 300, 0, true);
				
				tmpBackPic = nan[66];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 67){
				//姓名
				int left = 374-one.length()*60/2-5*40/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(123);
				BufferedImage nameBI = drawText("人类的王子", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+5*40).buildVerticalOffset(95);
				BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 68){
				//姓名
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(374-(one.length())*90/2).buildVerticalOffset(60);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 90, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 69){
				//姓名
				int left = 374-one.length()*60/2-3*40/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(60);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(83);
				BufferedImage nameBI2 = drawText("和患者", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 70){
				//姓名
				int left = 374-one.length()*60/2-7*40/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(100);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(123);
				BufferedImage nameBI2 = drawText("去楼下买完东西", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 71){
				//姓名
				int left = 374-4*40/2-one.length()*60/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(37);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+(one.length())*60).buildVerticalOffset(60);
				BufferedImage nameBI2 = drawText("走在路上", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				int left2 = 374-(one.length()+9)*40/2;
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(247);
				BufferedImage nameBI3 = drawText(one, ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP4 = new SimplePositions();
				nameSP4.buildHorizontalOffset(left2+one.length()*40).buildVerticalOffset(242);
				BufferedImage nameBI4 = drawText("伸脚正要踢它的时候", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 72){
				//姓名
				int left = 374-9*40/2-one.length()*60/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(390);
				BufferedImage nameBI = drawText("于是", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+(2)*40).buildVerticalOffset(367);
				BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(left+(2)*40+one.length()*60).buildVerticalOffset(390);
				BufferedImage nameBI3 = drawText("默默看了看裆部", ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 73){
				//姓名
				int left = 374-8*40/2-one.length()*40/2;
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(left).buildVerticalOffset(183);
				BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(left+(one.length())*40).buildVerticalOffset(180);
				BufferedImage nameBI2 = drawText("忙说：没有没有", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				int left2 = 374-(one.length()+4)*40/2;
				SimplePositions nameSP3 = new SimplePositions();
				nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(314);
				BufferedImage nameBI3 = drawText(one, ZbFont.方正兰亭特黑简体, 40, color,
						800, 200, 0, true);
				
				SimplePositions nameSP4 = new SimplePositions();
				nameSP4.buildHorizontalOffset(left2+one.length()*40).buildVerticalOffset(312);
				BufferedImage nameBI4 = drawText("连连点头", ZbFont.微软雅黑, 40, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else if(nanren == 74){
				//姓名
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(374-(one.length()+3)*60/2).buildVerticalOffset(280);
				BufferedImage nameBI = drawText(one+"哭着说", ZbFont.方正兰亭特黑简体, 60, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}else{
				//姓名
				SimplePositions nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(374-(one.length()+9)*50/2).buildVerticalOffset(40);
				BufferedImage nameBI = drawText(one+"：一两个人夸我帅时", ZbFont.方正兰亭特黑简体, 50, color,
						800, 200, 0, true);
				
				tmpBackPic = nan[nanren];
				
				SimplePositions[] sp = { nameSP,nameSPdi};

				BufferedImage[] bis = { nameBI,nameBIdi};

				return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			}
		
		}
		if(nvren ==0){
			//姓名
			int left = 347-one.length()*65/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(90);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 65, color,
					800, 300, 0, true);
			
			//说
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*65+5).buildVerticalOffset(120);
			BufferedImage nameBI2 = drawText("说:", ZbFont.微软雅黑, 40, color,
					800, 300, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 1){
			//姓名
			int left = 347-(one.length())*45/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(300);
			BufferedImage nameBI = drawText(one+" :", ZbFont.方正兰亭特黑简体, 45, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 2){
			//姓名
			int left = 347-(one.length()+9)*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(355);
			BufferedImage nameBI = drawText("回家的路上"+one+"一直在想", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 3){
			//姓名
			int left = 374-(one.length()+4)*50/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(60);
			BufferedImage nameBI = drawText(one+"长这么大", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 4){
			//姓名
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(63).buildVerticalOffset(70);
			BufferedImage nameBI = drawText(one+" :", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 5){
			//姓名
			int left = 374-(one.length()+3)*50/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(50);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*50).buildVerticalOffset(74);
			BufferedImage nameBI2 = drawText(" 哭着说", ZbFont.微软雅黑, 30, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 6){
			//姓名
			int left = 374-(one.length()+4)*50/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(210);
			BufferedImage nameBI = drawText("传销骨干", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(4)*50).buildVerticalOffset(215);
			BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 7){
			//姓名
			int left = 374-4*50/2-one.length()*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(270);
			BufferedImage nameBI = drawText("送", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(1)*50).buildVerticalOffset(247);
			BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left+(1)*50+one.length()*70).buildVerticalOffset(270);
			BufferedImage nameBI3 = drawText("上月球", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 8){
			//姓名
			int left = 374-2*50/2-(one.length()+4)*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(110);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*70).buildVerticalOffset(130);
			BufferedImage nameBI2 = drawText("也曾", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left+one.length()*70+2*50).buildVerticalOffset(110);
			BufferedImage nameBI3 = drawText("青春逼人", ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 9){
			//姓名
			int left = 374-one.length()*60/2-4*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(340);
			BufferedImage nameBI = drawText("人们都说 ", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+4*40).buildVerticalOffset(312);
			BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 10){
			//姓名
			int left = 374-one.length()*90/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(80);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 90, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 11){
			//姓名
			int left = 374-6*50/2-one.length()*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(60);
			BufferedImage nameBI = drawText("所有人都觉得", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(6)*50).buildVerticalOffset(37);
			BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			int left2 = 374-3*50/2-one.length()*70/2;;
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(270);
			BufferedImage nameBI3 = drawText("其实...", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(left2+(3)*50).buildVerticalOffset(247);
			BufferedImage nameBI4 = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 12){
			//姓名
			int left = 374-(one.length()+2)*50/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(35);
			BufferedImage nameBI = drawText(one+"学车", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 13){
			//姓名
			int left = 374-(one.length()+7)*50/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(35);
			BufferedImage nameBI = drawText("来自"+one+"的温馨提醒", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 14){
			//姓名
			int left = 374-(one.length()+3)*30/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(46);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 30, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*30).buildVerticalOffset(44);
			BufferedImage nameBI2 = drawText("问电扇", ZbFont.微软雅黑, 30, color,
					800, 200, 0, true);
			int left2 = 374-(one.length()+4)*30/2;
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(258);
			BufferedImage nameBI3 = drawText(one, ZbFont.方正兰亭特黑简体, 30, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(left2+(one.length())*30).buildVerticalOffset(256);
			BufferedImage nameBI4 = drawText("如释重负", ZbFont.微软雅黑, 30, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 15){
			//姓名
			int left = 374-2*50/2-(one.length()+3)*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(40);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*70).buildVerticalOffset(60);
			BufferedImage nameBI2 = drawText("是个", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left+one.length()*70+2*50).buildVerticalOffset(40);
			BufferedImage nameBI3 = drawText("热心人", ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 16){
			//姓名
			int left = 374-10*50/2-(one.length())*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(60);
			BufferedImage nameBI = drawText("当", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+1*50).buildVerticalOffset(40);
			BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left+one.length()*70+1*50).buildVerticalOffset(60);
			BufferedImage nameBI3 = drawText("走到人生的十字路口", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 17){
			//姓名
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(374-(one.length()+1)*70/2).buildVerticalOffset(70);
			BufferedImage nameBI = drawText(one+" :", ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 18){
			//姓名
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(374-(one.length()+6)*60/2).buildVerticalOffset(70);
			BufferedImage nameBI = drawText(one+"，上帝很公平", ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 19){
			//姓名
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(374-(one.length()+4)*50/2).buildVerticalOffset(70);
			BufferedImage nameBI = drawText("对于"+one+"来说", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 20){
			//姓名
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(374-(one.length()+12)*30/2).buildVerticalOffset(115);
			BufferedImage nameBI = drawText(one+"看见小偷偷了一位女士的包", ZbFont.方正兰亭特黑简体, 30, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(374-(one.length()+11)*30/2).buildVerticalOffset(380);
			BufferedImage nameBI2 = drawText("下车的时候，女士拉着"+one+"下车", ZbFont.方正兰亭特黑简体, 30, color,
					800, 200, 0, true);
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(374-(one.length()+10)*30/2).buildVerticalOffset(433);
			BufferedImage nameBI3 = drawText(one+"急了说：她不是我妈妈！", ZbFont.方正兰亭特黑简体, 30, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(374-(one.length()+9)*30/2).buildVerticalOffset(560);
			BufferedImage nameBI4 = drawText("结果"+one+"被卖到外地挖煤。", ZbFont.方正兰亭特黑简体, 30, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 21){
			//姓名
			int left = 374-one.length()*60/2-4*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(60);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(80);
			BufferedImage nameBI2 = drawText("近日便秘", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 22){
			//姓名
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(374-(one.length()+4)*40/2).buildVerticalOffset(110);
			BufferedImage nameBI = drawText(one+"：。。。", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(374-(one.length()+4)*40/2).buildVerticalOffset(270);
			BufferedImage nameBI2 = drawText(one+"：？？？", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(374-(one.length()+1)*40/2-2*70/2).buildVerticalOffset(470);
			BufferedImage nameBI3 = drawText(one+"：", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(374+(one.length())*40/2-(one.length()+1)*40/2).buildVerticalOffset(433);
			BufferedImage nameBI4 = drawText("妈妈！", ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 23){
			//姓名
			int left = 374-5*40/2-(one.length())*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(40);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*70).buildVerticalOffset(75);
			BufferedImage nameBI2 = drawText("借了很多钱，", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(374-one.length()*50/2-8*50/2).buildVerticalOffset(490);
			BufferedImage nameBI3 = drawText("儿子愤怒地对"+one+"喊道", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 24){
			//姓名
			int left = 374-one.length()*60/2-5*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(173);
			BufferedImage nameBI = drawText("请好好珍惜", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+5*40).buildVerticalOffset(150);
			BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 25){
			//姓名
			int left = 374-6*50/2-(one.length())*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(138);
			BufferedImage nameBI = drawText("忽然听到", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+4*50).buildVerticalOffset(115);
			BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left+one.length()*70+4*50).buildVerticalOffset(138);
			BufferedImage nameBI3 = drawText("说：", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 26){
			//姓名
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(380-(one.length()+7)*50/2).buildVerticalOffset(130);
			BufferedImage nameBI = drawText(one+"因为比较害羞，", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 27){
			//姓名
			int left = 374-(one.length()+2)*70/2-5*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(40);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*70).buildVerticalOffset(75);
			BufferedImage nameBI2 = drawText("总在办公室", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left+one.length()*70+5*40).buildVerticalOffset(40);
			BufferedImage nameBI3 = drawText("放屁", ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(374-(one.length()+6)*40/2).buildVerticalOffset(320);
			BufferedImage nameBI4 = drawText("然后"+one+"摇了起来", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 28){
			//姓名
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(374-(one.length()+1)*70/2).buildVerticalOffset(310);
			BufferedImage nameBI = drawText(one+" ：", ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 29){
			//姓名
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(374-(one.length())*80/2).buildVerticalOffset(80);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 80, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 30){
			//姓名
			int left = 374-(one.length()+3)*60/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(40);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*40).buildVerticalOffset(36);
			BufferedImage nameBI2 = drawText("问辅导员：", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 31){
			//姓名
			int left = 374-(one.length())*60/2-5*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(25);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(50);
			BufferedImage nameBI2 = drawText("长得像砖头", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 32){
			//姓名
			int left = 374-(one.length()+4)*70/2-2*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(130);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*70).buildVerticalOffset(165);
			BufferedImage nameBI2 = drawText("总是", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left+one.length()*70+2*40).buildVerticalOffset(130);
			BufferedImage nameBI3 = drawText("闭着眼睛", ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(374-(one.length()+9)*40/2).buildVerticalOffset(500);
			BufferedImage nameBI4 = drawText("那一瞬间，"+one+"还是哭了", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 33){
			//姓名
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(374-(one.length())*60/2).buildVerticalOffset(230);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 34){
			//姓名
			int left = 374-one.length()*60/2-3*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(149);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(167);
			BufferedImage nameBI2 = drawText("很专一", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 35){
			//姓名
			int left = 374-one.length()*60/2-5*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(95);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(115);
			BufferedImage nameBI2 = drawText("过去问道：", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(380-(one.length()+10)*40/2).buildVerticalOffset(400);
			BufferedImage nameBI3 = drawText(one+"：好嘞，谢谢大爷！", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 36){
			//姓名
			int left = 374-one.length()*80/2-3*50/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(80);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 80, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*80).buildVerticalOffset(120);
			BufferedImage nameBI2 = drawText("曾获得", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 37){
			//姓名
			int left = 374-one.length()*80/2-6*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(10);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 80, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*80).buildVerticalOffset(60);
			BufferedImage nameBI2 = drawText("已经不是那个", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 38){
			//姓名
			int left = 374-4*40/2-one.length()*60/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(330);
			BufferedImage nameBI = drawText("对于", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(2)*40).buildVerticalOffset(307);
			BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left+(2)*40+one.length()*60).buildVerticalOffset(330);
			BufferedImage nameBI3 = drawText("来说", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 39){
			//姓名
			int left = 374-(one.length()+2)*80/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(70);
			BufferedImage nameBI = drawText("昨晚"+one, ZbFont.方正兰亭特黑简体, 80, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 40){
			//姓名
			int left = 374-one.length()*60/2-2*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(40);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(63);
			BufferedImage nameBI2 = drawText("说：", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 41){
			//姓名
			int left = 374-4*40/2-one.length()*60/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(103);
			BufferedImage nameBI = drawText("对于", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(2)*40).buildVerticalOffset(80);
			BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left+(2)*40+one.length()*60).buildVerticalOffset(103);
			BufferedImage nameBI3 = drawText("来说", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 42){
			//姓名
			int left = 374-7*40/2-one.length()*60/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(103);
			BufferedImage nameBI = drawText("每次别人找", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(5)*40).buildVerticalOffset(80);
			BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left+(5)*40+one.length()*60).buildVerticalOffset(103);
			BufferedImage nameBI3 = drawText("问路", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(374-(one.length()+5)*40/2).buildVerticalOffset(167);
			BufferedImage nameBI4 = drawText(one+"都是瞎指的", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 43){
			//姓名
			int left = 374-5*40/2-one.length()*60/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(53);
			BufferedImage nameBI = drawText("在公厕看到", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(5)*40).buildVerticalOffset(28);
			BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(374-(one.length()+11)*40/2).buildVerticalOffset(370);
			BufferedImage nameBI3 = drawText("“并没有”", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(374-(4)*40/2).buildVerticalOffset(373);
			BufferedImage nameBI4 = drawText(one+"甩了甩头发，", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 44){
			//姓名
			int left = 374-one.length()*60/2-10*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(210);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(231);
			BufferedImage nameBI2 = drawText("临危不惧，沉着冷静", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 45){
			//姓名
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(374-one.length()*60/2).buildVerticalOffset(30);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(374-(one.length()+11)*40/2).buildVerticalOffset(376);
			BufferedImage nameBI2 = drawText("有人问"+one+"，7岁干嘛去了？", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 46){
			//姓名
			int left = 374-one.length()*60/2-2*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(30);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(57);
			BufferedImage nameBI2 = drawText("说：", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 47){
			//姓名
			int left = 374-6*40/2-(one.length())*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(40);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*70).buildVerticalOffset(78);
			BufferedImage nameBI2 = drawText("从小就有", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left+one.length()*70+4*40).buildVerticalOffset(80);
			BufferedImage nameBI3 = drawText("梦想", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 48){
			//姓名
			int left = 374-(one.length()+4)*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(129);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*40).buildVerticalOffset(127);
			BufferedImage nameBI2 = drawText("画着画着", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 49){
			//姓名
			int left = 374-(4)*40/2-one.length()*60/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(60);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(83);
			BufferedImage nameBI2 = drawText("开始相信", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 50){
			//姓名
			int left = 374-(one.length()+11)*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(46);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*40).buildVerticalOffset(44);
			BufferedImage nameBI2 = drawText("到菜市场买鸡蛋，问老板：", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			int left2 = 374-(one.length()+4)*40/2;
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(310);
			BufferedImage nameBI3 = drawText(one, ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(left2+(one.length())*40).buildVerticalOffset(306);
			BufferedImage nameBI4 = drawText("想了想：", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 51){
			//姓名
			int left = 374-(one.length()+11)*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(161);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*40).buildVerticalOffset(158);
			BufferedImage nameBI2 = drawText("赶紧买了两盒", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left+(one.length()+6)*40).buildVerticalOffset(161);
			BufferedImage nameBI3 = drawText("中华", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(left+(one.length()+8)*40).buildVerticalOffset(158);
			BufferedImage nameBI4 = drawText("送领导", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 52){
			//姓名
			int left = 374-(one.length())*60/2-8*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(161);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*60).buildVerticalOffset(187);
			BufferedImage nameBI2 = drawText("就把同桌打了一顿", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(374-(one.length()+8)*40/2).buildVerticalOffset(300);
			BufferedImage nameBI3 = drawText(one+"，教你家长一起来", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(374-(one.length()+6)*40/2).buildVerticalOffset(365);
			BufferedImage nameBI4 = drawText(one+"笑着说：没事，", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 53){
			//姓名
			int left = 374-9*40/2-one.length()*60/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(160);
			BufferedImage nameBI = drawText("都以为", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(3)*40).buildVerticalOffset(136);
			BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left+(3)*40+one.length()*60).buildVerticalOffset(160);
			BufferedImage nameBI3 = drawText("混的风生水起", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(374-(one.length()+2)*40/2).buildVerticalOffset(240);
			BufferedImage nameBI4 = drawText(one+"只是", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 54){
			//姓名
			int left = 374-9*40/2-one.length()*60/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(440);
			BufferedImage nameBI = drawText("说完就往", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(4)*40).buildVerticalOffset(415);
			BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left+(4)*40+one.length()*60).buildVerticalOffset(440);
			BufferedImage nameBI3 = drawText("嘴里灌...", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 55){
			//姓名
			int left = 374-one.length()*60/2-4*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(350);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(372);
			BufferedImage nameBI2 = drawText("站在窗口", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 56){
			//姓名
			int left = 374-one.length()*60/2-3*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(165);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(185);
			BufferedImage nameBI2 = drawText("也没用", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			//姓名
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(374-(one.length()+2)*40/2).buildVerticalOffset(255);
			BufferedImage nameBI3 = drawText("因为"+one, ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 57){
			//姓名
			int left = 374-one.length()*60/2-5*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(55);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(78);
			BufferedImage nameBI2 = drawText("要好好工作", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 58){
			//姓名
			int left = 374-(one.length()+4)*65/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(200);
			BufferedImage nameBI = drawText(one+"“ 妈！”", ZbFont.方正兰亭特黑简体, 65, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 59){
			//姓名
			int left = 374-5*40/2-one.length()*60/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(25);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(48);
			BufferedImage nameBI2 = drawText("以前是网管", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(374-(one.length()+9)*40/2).buildVerticalOffset(180);
			BufferedImage nameBI3 = drawText(one+"灵机一动把网线弄断", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(374-(one.length()+6)*40/2).buildVerticalOffset(460);
			BufferedImage nameBI4 = drawText("每次"+one+"说到这儿", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 60){
			//姓名
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(63).buildVerticalOffset(50);
			BufferedImage nameBI = drawText(one+"：", ZbFont.方正兰亭特黑简体, 65, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 61){
			//姓名
			int left = 374-10*40/2-one.length()*60/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(25);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(48);
			BufferedImage nameBI2 = drawText("一直觉得自己是穷二代", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(374-(one.length()+6)*40/2).buildVerticalOffset(120);
			BufferedImage nameBI3 = drawText("某天老爸对"+one+"说", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(374-(one.length()+2)*40/2).buildVerticalOffset(265);
			BufferedImage nameBI4 = drawText(one+"狂喜", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 62){
			//姓名
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(374-(one.length()+2)*40/2).buildVerticalOffset(30);
			BufferedImage nameBI = drawText("“"+one+"！”", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 63){
			//姓名
			int left = 374-one.length()*60/2-5*50/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(93);
			BufferedImage nameBI = drawText("心狠手辣的", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+5*50).buildVerticalOffset(78);
			BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 64){
			//姓名
			int left = 374-one.length()*60/2-5*50/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(63);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(72);
			BufferedImage nameBI2 = drawText("乔装成海螺", ZbFont.微软雅黑, 50, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 65){
			//姓名
			int left = 374-(one.length())*50/2-9*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(100);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(one.length())*50).buildVerticalOffset(112);
			BufferedImage nameBI2 = drawText("吗，请问您的信用卡", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(60).buildVerticalOffset(350);
			BufferedImage nameBI3 = drawText(one, ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(60+(one.length())*40).buildVerticalOffset(335);
			BufferedImage nameBI4 = drawText(":“是的！”", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 66){
			//姓名
			int left = 374-one.length()*50/2-3*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(82);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*50).buildVerticalOffset(53);
			BufferedImage nameBI2 = drawText("很可爱", ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 67){
			//姓名
			int left = 374-one.length()*60/2-4*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(320);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(342);
			BufferedImage nameBI2 = drawText("：不好吧", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 68){
			//姓名
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(374-(one.length()+5)*40/2).buildVerticalOffset(350);
			BufferedImage nameBI = drawText(one+"也想这样做", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 69){
			//姓名
			int left = 374-(one.length())*60/2-12*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(48);
			BufferedImage nameBI = drawText("在超市，一小孩拉住", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+(9)*40).buildVerticalOffset(25);
			BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left+(9)*40+one.length()*60).buildVerticalOffset(48);
			BufferedImage nameBI3 = drawText("的手：", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(374-(one.length()+3)*50/2).buildVerticalOffset(255);
			BufferedImage nameBI4 = drawText(one+"问TA", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 70){
			//姓名
			int left = 374-one.length()*60/2-6*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(45);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(67);
			BufferedImage nameBI2 = drawText("说：你要是说", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 71){
			//姓名
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(374-(one.length()+5)*40/2).buildVerticalOffset(250);
			BufferedImage nameBI = drawText(one+"的作业是：", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 72){
			//姓名
			int left = 374-(one.length())*50/2-2*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(225);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*50).buildVerticalOffset(235);
			BufferedImage nameBI2 = drawText("心想", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			int left2 = 374-(one.length())*50/2-6*40/2;
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(430);
			BufferedImage nameBI3 = drawText(one, ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(left2+one.length()*50).buildVerticalOffset(440);
			BufferedImage nameBI4 = drawText("的车因为没油", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 73){
			//姓名
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(60).buildVerticalOffset(42);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(60+one.length()*40).buildVerticalOffset(40);
			BufferedImage nameBI2 = drawText("夜间走在小道上", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(60).buildVerticalOffset(190);
			BufferedImage nameBI3 = drawText(one+"：大哥，你是劫钱还是劫色啊？", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(60).buildVerticalOffset(313);
			BufferedImage nameBI4 = drawText(one+"大哥，我没钱，你就劫色吧！", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 74){
			//姓名
			int left = 374-one.length()*60/2-6*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(40);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(62);
			BufferedImage nameBI2 = drawText("不会沮丧", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 75){
			//姓名
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(374-(one.length())*90/2).buildVerticalOffset(280);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 90, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 76){
			//姓名
			int left = 374-(one.length())*60/2-5*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(42);
			BufferedImage nameBI = drawText("妈妈这样说", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+5*40).buildVerticalOffset(18);
			BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			int left2 = 374-(one.length())*60/2-11*40/2;
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(100);
			BufferedImage nameBI3 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(left2+one.length()*60).buildVerticalOffset(122);
			BufferedImage nameBI4 = drawText("，我说你都胖成啥样了？", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 77){
			//姓名
			int left = 374-(one.length())*60/2-8*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(22);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*60).buildVerticalOffset(46);
			BufferedImage nameBI2 = drawText("在家上厕所的时候", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			int left2 = 374-(one.length())*60/2-9*40/2;
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left2).buildVerticalOffset(315);
			BufferedImage nameBI3 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			
			SimplePositions nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(left2+one.length()*60).buildVerticalOffset(338);
			BufferedImage nameBI4 = drawText("忍不住坐在马桶上哭", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 78){
			//姓名
			int left = 374-one.length()*50/2-3*70/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(82);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*50).buildVerticalOffset(53);
			BufferedImage nameBI2 = drawText("很可爱", ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 79){
			//姓名
			int left = 374-one.length()*70/2-5*50/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(53);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 70, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*70).buildVerticalOffset(82);
			BufferedImage nameBI2 = drawText("的特质就是", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 80){
			//姓名
			int left = 374-one.length()*40/2-4*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(175);
			BufferedImage nameBI = drawText(one, ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+one.length()*40).buildVerticalOffset(170);
			BufferedImage nameBI2 = drawText("裤子刚脱", ZbFont.微软雅黑, 40, color,
					800, 200, 0, true);
			//姓名
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(374-(one.length()+5)*40/2).buildVerticalOffset(335);
			BufferedImage nameBI3 = drawText("托住"+one+"的臀：", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 81){
			//姓名
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(374-(one.length()+9)*50/2).buildVerticalOffset(250);
			BufferedImage nameBI = drawText("不像我"+one+"，活了这么久", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else if(nvren == 82){
			//姓名
			int left = 374-one.length()*40/2-3*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(90);
			BufferedImage nameBI = drawText("高中时"+one, ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(374-(one.length()+8)*40/2).buildVerticalOffset(220);
			BufferedImage nameBI2 = drawText("老师劝"+one+"想他的时候", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			//姓名
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(374-(one.length()+8)*50/2).buildVerticalOffset(370);
			BufferedImage nameBI3 = drawText(one+"做到第二题的时候", ZbFont.方正兰亭特黑简体, 50, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}else{
			//姓名
			int left = 374-one.length()*60/2-8*40/2;
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(261);
			BufferedImage nameBI = drawText("至今还没有", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(left+5*40).buildVerticalOffset(235);
			BufferedImage nameBI2 = drawText(one, ZbFont.方正兰亭特黑简体, 60, color,
					800, 200, 0, true);
			//姓名
			SimplePositions nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(left+5*40+one.length()*60).buildVerticalOffset(261);
			BufferedImage nameBI3 = drawText("的微信", ZbFont.方正兰亭特黑简体, 40, color,
					800, 200, 0, true);
			
			tmpBackPic = nv[nvren];
			
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSPdi};

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBIdi};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		}
		
	}

}
