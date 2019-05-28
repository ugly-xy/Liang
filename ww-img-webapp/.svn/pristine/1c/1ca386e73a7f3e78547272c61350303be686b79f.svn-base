package com.zb.service.image.holiday;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.ThreeDraw;

public class JuJiEGaoTool extends BaseTool implements ThreeDraw{
	public JuJiEGaoTool(StorageService storageService) {
		super(storageService);
	}

	public JuJiEGaoTool() {
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57cfca300cf21220b6a3bf17";
		StorageService storageService = new StorageService();
		JuJiEGaoTool tyt = new JuJiEGaoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点","呵呵哒","a.png", tmpPath0,  //486
				null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 16;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	static String ditu = "http://imgzb.zhuangdianbi.com/57cfca1c0cf21220b6a3bf0b";
	
	@Override
	public String draw(String one, String two, String three, String tmpBackPic, String count) throws IOException {
		//狙击人
 		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(485).buildVerticalOffset(14);
 		BufferedImage nameBi = drawText(one+":", zbfont, fontSize,
 				new Color(23,147,223), 240, 100, 0, true);
 		
 		//被狙击人
 		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(495+one.length()*fontSize).buildVerticalOffset(14);
 		BufferedImage nameBi2 = drawText("已狙击"+two, zbfont, fontSize,
 				new Color(255,255,255), 240, 100, 0, true);
 		
 		//玩家照片
		BufferedImage p = super.compressImage(three, 486, 486);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(117).buildVerticalOffset(27);
		
		//玩家照片
		BufferedImage p2 = super.getImg(ditu);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		
 		SimplePositions[] sp = {pSP,pSP2,nameSP,nameSP2};

		BufferedImage[] bis = {p,p2,nameBi,nameBi2};
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
