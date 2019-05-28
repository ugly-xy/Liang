package com.zb.service.image.love;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class PingZiBiaoBaiTool extends BaseTool implements TwoDraw{

	public PingZiBiaoBaiTool(StorageService storageService) {
		super(storageService);
	}

	public PingZiBiaoBaiTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57887fcc0cf22217f1fdf221";
		StorageService storageService = new StorageService();
		PingZiBiaoBaiTool tyt = new PingZiBiaoBaiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("BIBI","BIBI", tmpPath0,null));
		
		
	}
	
	static ZbFont zbfont = ZbFont.德彪钢笔行书字库;
	static int fontSize = 38;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		
		int len1 = one.length();
		int len2 = two.length();
		
		SimplePositions nameSP =null;
		SimplePositions nameSP2 =null;
		BufferedImage nameBI = null;
		BufferedImage nameBI2 = null;
		
		if(len1==1){
			// 对方姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(178).buildVerticalOffset(240);
			nameBI = drawText(one+":", zbfont, fontSize, color,
					240, 80, -0.32, true);
		}else if(len1 ==2){
			// 对方姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(178).buildVerticalOffset(235);
			nameBI = drawText(one+":", zbfont, fontSize, color,
					240, 80, -0.32, true);
		}else{
			// 对方姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(170).buildVerticalOffset(228);
			nameBI = drawText(one+":", zbfont, fontSize, color,
					240, 80, -0.32, true);
		}
		
		if(len2==1){
			// 己方姓名
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(443).buildVerticalOffset(325);
			nameBI2 = drawText(two, zbfont,fontSize, color,
					170, 80, -0.32, true);
		}else if(len2==2){
			// 己方姓名
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(440).buildVerticalOffset(322);
			nameBI2 = drawText(two, zbfont,fontSize, color,
					170, 80, -0.32, true);
		}else{
			// 己方姓名
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(440).buildVerticalOffset(315);
			nameBI2 = drawText(two, zbfont,fontSize, color,
					170, 80, -0.32, true);
		}
		
		
		
		SimplePositions[] sp = { nameSP,nameSP2};

		BufferedImage[] bis = { nameBI,nameBI2};

		return super.getSaveFile(sp, bis, 0.9f, tmpBackPic);
	}

}
