package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class JuanJingBuZhuTool extends BaseTool implements OneDraw{
	public JuanJingBuZhuTool(StorageService storageService) {
		super(storageService);
	}

	public JuanJingBuZhuTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/576ba7310cf273bdeaacb156";
		StorageService storageService = new StorageService();
		JuanJingBuZhuTool tyt = new JuanJingBuZhuTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装逼学", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.钟齐山文丰手写体;
	static int fontSize = 15;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpPath,String count) throws IOException {
		
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(415).buildVerticalOffset(220);
		BufferedImage nameBI = drawText(one,zbfont,fontSize, color,
				240, 0, 0, true);
		
		// date
		String date = DateUtil.dateFormat(new Date(), "yyyy  MM   dd");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(228).buildVerticalOffset(65);
		BufferedImage dateBi = drawText(date, zbfont, fontSize,
				color, 240, 0, 0, true);
		SimplePositions[] sp = { nameSP, dateSP };

		BufferedImage[] bis = { nameBI, dateBi };

		return super.getSaveFile(sp, bis, 0.9f, tmpPath);
	}

}
