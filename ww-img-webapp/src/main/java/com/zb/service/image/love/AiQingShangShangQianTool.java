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

public class AiQingShangShangQianTool extends BaseTool implements OneDraw{
	public AiQingShangShangQianTool(StorageService storageService) {
		super(storageService);
	}

	public AiQingShangShangQianTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57b1820e0cf2464bcace9fe2";
		StorageService storageService = new StorageService();
		AiQingShangShangQianTool tyt = new AiQingShangShangQianTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装逼",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.华文行楷;
	static int fontSize = 12;
	static Color color = new Color(147, 80, 85);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//姓名
 		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(237).buildVerticalOffset(152);
 		BufferedImage nameBi = drawText("最爱你的人是", zbfont, fontSize,
 				color, 20, 500, 0, true);
 		//姓名
 		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(237).buildVerticalOffset(260-one.length()*fontSize/2);
 		BufferedImage nameBi2 = drawText(one, zbfont, fontSize,
 				color, 20, 300, 0, true);
 		
 		SimplePositions[] sp = { nameSP,nameSP2};

		BufferedImage[] bis = { nameBi,nameBi2};

		return super.getSaveFile(sp, bis, 0.8f, tmpBackPic);
	}

}
