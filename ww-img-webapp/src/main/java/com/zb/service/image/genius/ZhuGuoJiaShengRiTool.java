package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class ZhuGuoJiaShengRiTool extends BaseTool implements OneDraw{
	public ZhuGuoJiaShengRiTool(StorageService storageService) {
		super(storageService);
	}

	public ZhuGuoJiaShengRiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57eb85240cf233e2d3e0d7dc";
		StorageService storageService = new StorageService();
		ZhuGuoJiaShengRiTool tyt = new ZhuGuoJiaShengRiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼", tmpPath0,"44545"));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 80;
	static Color color = new Color(254, 40, 9);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(360-one.length()*fontSize/2).buildVerticalOffset(278);
		BufferedImage nameBI = drawText(one, zbfont, fontSize, color,
				400, 100, 0, true);
		
		DecimalFormat df=new DecimalFormat("00000000");
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(347).buildVerticalOffset(974);
		BufferedImage nameBI2 = drawText(df.format(Integer.parseInt(count)+15123), zbfont, 40, color,
				240, 100, 0, true);
		SimplePositions[] sp = { nameSP,nameSP2};

		BufferedImage[] bis = { nameBI,nameBI2};

		return super.getSaveFile(sp, bis, 0.9f, tmpBackPic);
	}

}
