package com.zb.service.image.love;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class AiDeWenShenTool extends BaseTool implements TwoDraw {

	public AiDeWenShenTool(StorageService storageService) {
		super(storageService);
	}

	public AiDeWenShenTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		// String tmpPath0 =
		// "http://imgzb.zhuangdianbi.com/5732a2a80cf2d5342be19ca8";
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5769f83d0cf2a4a089259e6f";
		StorageService storageService = new StorageService();
		AiDeWenShenTool tyt = new AiDeWenShenTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","装", tmpPath0,null));
		
		
	}
	
	static ZbFont zbfont = ZbFont.方正藏体简体;
	static int fontSize = 22;
	static Color color = new Color(26, 4, 71);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String two, String tmpPath,String count) throws IOException {
		
		List<String> list = new ArrayList<String>();
		for(int t = 0;t<one.length();t++){
			list.add(String.valueOf(one.charAt(t)));
		}
		List<String> list2 = new ArrayList<String>();
		for(int t = 0;t<two.length();t++){
			list2.add(String.valueOf(two.charAt(t)));
		}
		
		SimplePositions nameSP0 = null;  
		SimplePositions nameSP1 = null;		
		
		BufferedImage nameBI0 =null;
		BufferedImage nameBI1 =null;
		
		if(list.size()==1){
			nameSP0 = new SimplePositions();
			nameSP0.buildHorizontalOffset(91).buildVerticalOffset(289);
			nameBI0 = drawText(one, zbfont, fontSize, color,
					240, 100, -0.03, true,20,20);
		}else if(list.size()==2){
			nameSP0 = new SimplePositions();
			nameSP0.buildHorizontalOffset(81).buildVerticalOffset(289);
			nameBI0 = drawText(one, zbfont, fontSize, color,
					240, 100, -0.03, true,20,20);
		}else{
			nameSP0 = new SimplePositions();
			nameSP0.buildHorizontalOffset(73).buildVerticalOffset(289);
			nameBI0 = drawText(one, zbfont, fontSize, color,
					240, 100, -0.03, true,20,20);
		}
		
		
		if(list2.size()==1){
			nameSP1 = new SimplePositions();
			nameSP1.buildHorizontalOffset(162).buildVerticalOffset(301);
			nameBI1 = drawText(two, zbfont, fontSize, color,
					240, 100, 0.3, true,20,20);
		}else if(list2.size()==2){
			nameSP1 = new SimplePositions();
			nameSP1.buildHorizontalOffset(153).buildVerticalOffset(301);
			nameBI1 = drawText(two, zbfont, fontSize, color,
					240, 100, 0.3, true,20,20);
		}else{
			nameSP1 = new SimplePositions();
			nameSP1.buildHorizontalOffset(144).buildVerticalOffset(301);
			nameBI1 = drawText(two, zbfont, fontSize, color,
					240, 100, 0.3, true,20,20);
		}
			
			
			
			
			SimplePositions[] sp = { nameSP0,nameSP1};

			BufferedImage[] bis = { nameBI0, nameBI1};
			
			return super.getSaveFile(sp, bis, 0.65f, tmpPath);
		
		
		
		
	}

}
