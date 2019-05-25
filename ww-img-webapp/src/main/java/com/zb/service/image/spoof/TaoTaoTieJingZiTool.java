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

public class TaoTaoTieJingZiTool extends BaseTool implements TwoDraw{
	public TaoTaoTieJingZiTool(StorageService storageService) {
		super(storageService);
	}

	public TaoTaoTieJingZiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57b7f7dc0cf275c1694b1080";
		StorageService storageService = new StorageService();
		TaoTaoTieJingZiTool tyt = new TaoTaoTieJingZiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点比","今晚可否一战！", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 22;
	static Color color = new Color(255, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(200-one.length()*fontSize/2).buildVerticalOffset(130);
		BufferedImage nameBI = drawText(one, zbfont, fontSize, color,
				180, 100, 0, true);
		
		//一句话
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(200-7*fontSize/2).buildVerticalOffset(170);
		BufferedImage nameBI2 = drawText(two, zbfont, fontSize, color,
				180, 200, 0, true);
		
		SimplePositions[] sp = {nameSP,nameSP2};

		BufferedImage[] bis = { nameBI,nameBI2 };

		return super.getSaveFile(sp, bis, 0.8f, tmpBackPic);
	}

}
