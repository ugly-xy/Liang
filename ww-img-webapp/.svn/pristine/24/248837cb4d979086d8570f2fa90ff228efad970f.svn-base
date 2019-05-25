package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class YangYangJuPaiTool extends BaseTool implements TwoDraw{
	public YangYangJuPaiTool(StorageService storageService) {
		super(storageService);
	}

	public YangYangJuPaiTool() {
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57c422b50cf215cac9f084a9";
		StorageService storageService = new StorageService();
		YangYangJuPaiTool tyt = new YangYangJuPaiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点比","啊", tmpPath0,  
				null));
	}
	
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 25;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(490-one.length()*fontSize/2).buildVerticalOffset(430);
		BufferedImage nameBI = drawText(one, zbfont, fontSize, color,
				720, 150, 0.4, true);
		//一句话
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(480-6*fontSize/2).buildVerticalOffset(496);
		BufferedImage nameBI2 = drawText(two, zbfont, fontSize, color,
				150, 150, 0.4, true);
		SimplePositions[] sp = {nameSP,nameSP2};

		BufferedImage[] bis = { nameBI,nameBI2};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
