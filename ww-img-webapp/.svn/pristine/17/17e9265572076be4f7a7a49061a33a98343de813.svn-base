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

public class QunXiongYaoQingHanTool extends BaseTool implements OneDraw{
	public QunXiongYaoQingHanTool(StorageService storageService) {
		super(storageService);
	}

	public QunXiongYaoQingHanTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57d7e0580cf29e55ef4986fa";
		StorageService storageService = new StorageService();
		QunXiongYaoQingHanTool tyt = new QunXiongYaoQingHanTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 42;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//姓名
 		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(200-one.length()*fontSize/2).buildVerticalOffset(570);
 		BufferedImage nameBi = drawText(one, zbfont.font(),fontType, fontSize,
 				color, 300, 100, 0, true);
 		SimplePositions[] sp = {nameSP};

		BufferedImage[] bis = {nameBi};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
