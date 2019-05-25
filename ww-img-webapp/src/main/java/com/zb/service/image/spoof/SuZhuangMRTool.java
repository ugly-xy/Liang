package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class SuZhuangMRTool extends BaseTool implements OneDraw{
	public SuZhuangMRTool(StorageService storageService) {
		super(storageService);
	}

	public SuZhuangMRTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57b457420cf240677f6babfd";
		StorageService storageService = new StorageService();
		SuZhuangMRTool tyt = new SuZhuangMRTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("比比逼",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 50;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(464-one.length()*fontSize/2).buildVerticalOffset(610);
		BufferedImage nameBI = drawText(one, zbfont.font(),fontType, fontSize, color,
				180, 150, 0, true);
		
		SimplePositions[] sp = {nameSP};

		BufferedImage[] bis = { nameBI};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
