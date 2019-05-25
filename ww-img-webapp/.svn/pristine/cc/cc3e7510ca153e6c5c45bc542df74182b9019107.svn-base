package com.zb.service.image.holiday;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class GuoQingYaoQingHanTool extends BaseTool implements OneDraw{
	public GuoQingYaoQingHanTool(StorageService storageService) {
		super(storageService);
	}

	public GuoQingYaoQingHanTool() {
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57dfaedd0cf2f6c28939ebee";
		StorageService storageService = new StorageService();
		GuoQingYaoQingHanTool tyt = new GuoQingYaoQingHanTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("娱乐社区",tmpPath0,  
				null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 48;
	static Color color = new Color(178, 75, 79);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//狙击人
 		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(378-one.length()*fontSize/2).buildVerticalOffset(447);
 		BufferedImage nameBi = drawText(one, zbfont, fontSize,
 				color, 600, 100, 0.03, true);
 		
 		SimplePositions[] sp = {nameSP};

		BufferedImage[] bis = {nameBi};
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
