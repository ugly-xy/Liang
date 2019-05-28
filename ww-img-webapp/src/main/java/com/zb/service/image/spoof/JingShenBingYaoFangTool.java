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
import com.zb.service.image.i.TwoDraw;

public class JingShenBingYaoFangTool extends BaseTool implements TwoDraw{
	public JingShenBingYaoFangTool(StorageService storageService) {
		super(storageService);
	}

	public JingShenBingYaoFangTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57baac3e0cf2c0403074f777";
		StorageService storageService = new StorageService();
		JingShenBingYaoFangTool tyt = new JingShenBingYaoFangTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装逼学","男", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.钟齐山文丰手写体;
	static int fontSize = 20;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(470-one.length()*fontSize/2).buildVerticalOffset(231);
		BufferedImage nameBI = drawText(one,zbfont,fontSize, color,
				240, 0, -0.1, true);
		
		//性别
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(570).buildVerticalOffset(220);
		BufferedImage nameBI2 = drawText(two,zbfont,fontSize, color,
				240, 0, -0.1, true);
		
		// date
		String date = DateUtil.dateFormat(new Date(), "yyyy   M  d");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(615).buildVerticalOffset(233);
		BufferedImage dateBi = drawText(date, zbfont, fontSize,
				color, 240, 0, -0.1, true);
		
		
		SimplePositions[] sp = { nameSP,nameSP2, dateSP };

		BufferedImage[] bis = { nameBI, nameBI2,dateBi };

		return super.getSaveFile(sp, bis, 0.9f, tmpBackPic);
	}

}
