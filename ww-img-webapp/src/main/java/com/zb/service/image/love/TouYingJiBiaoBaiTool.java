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

public class TouYingJiBiaoBaiTool extends BaseTool implements TwoDraw{

	public TouYingJiBiaoBaiTool(StorageService storageService) {
		super(storageService);
	}

	public TouYingJiBiaoBaiTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57887ed00cf22217f1fdf13a";
		StorageService storageService = new StorageService();
		TouYingJiBiaoBaiTool tyt = new TouYingJiBiaoBaiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("BIBI","我喜欢你很久了，你感觉的到吗？", tmpPath0,null));
		
		
	}
	//我喜欢你很久了，你感觉的到吗？   我爱你最真，你却伤我最深！    
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 18;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(355).buildVerticalOffset(80);
		BufferedImage nameBI = drawText(one+":", zbfont, fontSize, color,
				240, 100, 0, true);
		// 内容
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(355).buildVerticalOffset(120);
		BufferedImage nameBI2 = drawText(two, zbfont, fontSize, color,
				170, 100, 0, true);
		
		SimplePositions[] sp = { nameSP,nameSP2};

		BufferedImage[] bis = { nameBI,nameBI2};

		return super.getSaveFile(sp, bis, 0.6f, tmpBackPic);
	}

}
