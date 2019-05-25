package com.zb.service.image.love;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class DanGaoBiaoBaiTool extends BaseTool implements OneDraw{
	public DanGaoBiaoBaiTool(StorageService storageService) {
		super(storageService);
	}

	public DanGaoBiaoBaiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5784a7f90cf263fc7d0f0c3d";
		StorageService storageService = new StorageService();
		DanGaoBiaoBaiTool tyt = new DanGaoBiaoBaiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("比比我爱你", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.楷体常规;
	static int fontSize = 30;
	static Color color = new Color(250, 231, 102);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		
		for(int i =0;i<6;i++){
			if(one.length()<6){
				one+="!";
			}
		}
		
		SimplePositions nameSP0 = null;  
		SimplePositions nameSP1 = null;		
		SimplePositions nameSP2 = null;
		SimplePositions nameSP3 = null;
		SimplePositions nameSP4 = null;
		SimplePositions nameSP5 = null;
		BufferedImage nameBI0 =null;
		BufferedImage nameBI1 =null;
		BufferedImage nameBI2 =null;
		BufferedImage nameBI3 =null;
		BufferedImage nameBI4 =null;
		BufferedImage nameBI5 =null;
		int fontSize1 = 26; 
		nameSP0 = new SimplePositions();
		nameSP0.buildHorizontalOffset(176).buildVerticalOffset(45);
		nameBI0 = drawText(String.valueOf(one.charAt(0)), zbfont, fontSize1, color,
				240,0, 0, true);
		int fontSize2 = 27;
		nameSP1 = new SimplePositions();
		nameSP1.buildHorizontalOffset(182).buildVerticalOffset(130);
		nameBI1 = drawText(String.valueOf(one.charAt(1)), zbfont, fontSize2, color,
				240,0, 0, true);
		nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(180).buildVerticalOffset(218);
		nameBI2 = drawText(String.valueOf(one.charAt(2)), zbfont, fontSize, color,
				240,0, 0, true);
		nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(183).buildVerticalOffset(315);
		nameBI3 = drawText(String.valueOf(one.charAt(3)), zbfont, fontSize, color,
				240,0, 0, true);
		int fontSize5 = 32;
		nameSP4 = new SimplePositions();
		nameSP4.buildHorizontalOffset(180).buildVerticalOffset(410);
		nameBI4 = drawText(String.valueOf(one.charAt(4)), zbfont, fontSize5, color,
				240, 0, 0, true);
		int fontSize6 = 32;
		nameSP5 = new SimplePositions();
		nameSP5.buildHorizontalOffset(188).buildVerticalOffset(514);
		nameBI5 = drawText(String.valueOf(one.charAt(5)), zbfont, fontSize6, color,
				240,0, 0, true);
		
		
		SimplePositions[] sp = { nameSP0,nameSP1,nameSP2,nameSP3,nameSP4,nameSP5 };

		BufferedImage[] bis = { nameBI0, nameBI1, nameBI2, nameBI3,nameBI4,nameBI5};
		
		return super.getSaveFile(sp, bis, 0.8f, tmpBackPic);
	}

}
