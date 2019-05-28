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

public class GongShiPaiXieZiTool extends BaseTool implements TwoDraw{
	public GongShiPaiXieZiTool(StorageService storageService) {
		super(storageService);
	}

	public GongShiPaiXieZiTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57ada3110cf23ddb0667cf1b";
		StorageService storageService = new StorageService();
		GongShiPaiXieZiTool tyt = new GongShiPaiXieZiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","谈恋爱",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.德彪钢笔行书字库;
	static int fontSize = 50;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//姓名
 		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(297-one.length()*fontSize/2).buildVerticalOffset(460);
 		BufferedImage nameBi = drawText("— "+one, zbfont, fontSize,
 				color, 295, 100, 0, true);
 		
 		SimplePositions nameSP2 =null;
 		BufferedImage nameBi2 =null;
 		//内容
 		if(two.equals("上班")){
 			nameSP2 = new SimplePositions();
 	 		nameSP2.buildHorizontalOffset(132).buildVerticalOffset(180);
 	 		nameBi2 = drawText("我上班就是为了钱，不要跟我谈理想，我的理想就是不上班！", zbfont, fontSize,
 	 				color, 310, 500, 0, true);
 		}else if(two.equals("上学")){
 			nameSP2 = new SimplePositions();
 	 		nameSP2.buildHorizontalOffset(132).buildVerticalOffset(180);
 	 		nameBi2 = drawText("我上学就是为了玩，不要跟我谈理想，我的理想就是不上学！", zbfont, fontSize,
 	 				color, 310, 500, 0, true);
 		}else if(two.equals("谈恋爱")){
 			nameSP2 = new SimplePositions();
 	 		nameSP2.buildHorizontalOffset(132).buildVerticalOffset(180);
 	 		nameBi2 = drawText("我上学就是为了谈恋爱，不要跟我谈理想，我的理想就是谈恋爱！", zbfont, fontSize,
 	 				color, 310, 500, 0, true);
 		}else{
 			nameSP2 = new SimplePositions();
 	 		nameSP2.buildHorizontalOffset(132).buildVerticalOffset(180);
 	 		nameBi2 = drawText(two, zbfont, fontSize,
 	 				color, 310, 500, 0, true);
 		}
 		
 		
 		SimplePositions[] sp = {nameSP,nameSP2};

		BufferedImage[] bis = {nameBi,nameBi2};

		return super.getSaveFile(sp, bis, 0.9f, tmpBackPic);
	}

}
