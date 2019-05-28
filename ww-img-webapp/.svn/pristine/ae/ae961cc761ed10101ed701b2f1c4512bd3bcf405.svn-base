package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class HuoRuQingTieTool  extends BaseTool implements OneDraw{
	public HuoRuQingTieTool(StorageService storageService) {
		super(storageService);
	}

	public HuoRuQingTieTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/579c38fb0cf2ef699f6189cb";
		StorageService storageService = new StorageService();
		HuoRuQingTieTool tyt = new HuoRuQingTieTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.宋体;
	static int fontSize = 16;
	static Color color = new Color(143, 140, 142);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//姓名
		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(170).buildVerticalOffset(284);
 		BufferedImage nameBi = drawText("嘉宾:", zbfont, fontSize,
 				color, 240, 0, -0.3, true);
 		
 		SimplePositions nameSP2 =null;
 		BufferedImage nameBi2 = null;
 		if(one.length()==1){
 			nameSP2 = new SimplePositions();
 	 		nameSP2.buildHorizontalOffset(210).buildVerticalOffset(277);
 	 		nameBi2 = drawText(one, zbfont, fontSize,
 	 				color, 240, 0, -0.3, true);
 		}else if(one.length()==2){
 			nameSP2 = new SimplePositions();
 	 		nameSP2.buildHorizontalOffset(210).buildVerticalOffset(275);
 	 		nameBi2 = drawText(one, zbfont, fontSize,
 	 				color, 240, 0, -0.3, true);
 		}else if(one.length()==3){
 			nameSP2 = new SimplePositions();
 	 		nameSP2.buildHorizontalOffset(210).buildVerticalOffset(272);
 	 		nameBi2 = drawText(one, zbfont, fontSize,
 	 				color, 240, 0, -0.3, true);
 		}else{
 			nameSP2 = new SimplePositions();
 	 		nameSP2.buildHorizontalOffset(210).buildVerticalOffset(267);
 	 		nameBi2 = drawText(one, zbfont, fontSize,
 	 				color, 240, 0, -0.3, true,2,2);
 		}
 		
 		
 		SimplePositions[] sp = {nameSP,nameSP2 };

		BufferedImage[] bis = {nameBi,nameBi2 };

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
