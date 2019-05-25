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

public class DaZiWeiXinShuaPingTool extends BaseTool implements TwoDraw{
	public DaZiWeiXinShuaPingTool(StorageService storageService) {
		super(storageService);
	}

	public DaZiWeiXinShuaPingTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57b818a40cf275c1694b324f";
		StorageService storageService = new StorageService();
		DaZiWeiXinShuaPingTool tyt = new DaZiWeiXinShuaPingTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("BIBI装点逼是一款好玩到爆的软件啊！","小黄猫", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 22;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	static String ditu[]  ={"http://imgzb.zhuangdianbi.com/57b81bad0cf275c1694b35ba",
							"http://imgzb.zhuangdianbi.com/57b81bba0cf275c1694b35cf",
							"http://imgzb.zhuangdianbi.com/57b81bcc0cf275c1694b35eb"};
	
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		
		int len = one.length();
		
		SimplePositions nameSP = null;
		BufferedImage nameBI = null;
		
		//姓名
		if(len<=8){
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(110).buildVerticalOffset(180);
			nameBI = drawText(one, zbfont, 135, color,
					1200, 400, 0, true);
			
		}else if(len<=16){
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(110).buildVerticalOffset(120);
			nameBI = drawText(one, zbfont, 135, color,
					1200, 400, 0, true);
		}else if(len<=22){
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(110).buildVerticalOffset(140);
			nameBI = drawText(one, zbfont, 100, color,
					1200, 400, 0, true);
		}else{
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(110).buildVerticalOffset(140);
			nameBI = drawText(one, zbfont, 80, color,
					1200, 500, 0, true);
		}
		
		
		
		SimplePositions[] sp = {nameSP};

		BufferedImage[] bis = { nameBI };
		
		if(two.equals("正常")){
			tmpBackPic = ditu[0];
		}else if(two.equals("小黄猫")){
			tmpBackPic = ditu[1];
		}else{
			tmpBackPic = ditu[2];
		}
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
