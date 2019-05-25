package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class XueZhiQianJuPaiTool extends BaseTool implements TwoDraw {
	public XueZhiQianJuPaiTool(StorageService storageService) {
		super(storageService);
	}

	public XueZhiQianJuPaiTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57aeefc70cf2ebdb1bdda3ba";
		StorageService storageService = new StorageService();
		XueZhiQianJuPaiTool tyt = new XueZhiQianJuPaiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点","装点你",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 40;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//姓名
 		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(316-one.length()*fontSize/2).buildVerticalOffset(520);
 		BufferedImage nameBi = drawText(one, zbfont, fontSize,
 				color, 240, 100, 0, true);
 	
 		//一句话
 		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(316-two.length()*fontSize/2).buildVerticalOffset(620);
 		BufferedImage nameBi2 = drawText(two, zbfont, fontSize,
 				color, 240, 100, 0, true);
 		
 		SimplePositions[] sp = {nameSP,nameSP2};

		BufferedImage[] bis = {nameBi,nameBi2};

		return super.getSaveFile(sp, bis, 0.9f, tmpBackPic);
	}

	

}
