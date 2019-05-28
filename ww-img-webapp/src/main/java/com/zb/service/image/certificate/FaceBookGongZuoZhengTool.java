package com.zb.service.image.certificate;

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

public class FaceBookGongZuoZhengTool extends BaseTool implements TwoDraw{
	public FaceBookGongZuoZhengTool(StorageService storageService) {
		super(storageService);
	}

	public FaceBookGongZuoZhengTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/577bac530cf20246dc1f69b2";
		StorageService storageService = new StorageService();
		FaceBookGongZuoZhengTool tyt = new FaceBookGongZuoZhengTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("都","装逼工程师", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 22;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(175).buildVerticalOffset(475);
		BufferedImage nameBI = drawText(one, zbfont, fontSize, color,
				180, 100, -0.07, true);
		
		//职位
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(177).buildVerticalOffset(525);
		BufferedImage nameBI2 = drawText(two, zbfont, fontSize, color,
				180, 100, -0.07, true);
		
		// date
		String date = DateUtil.dateFormat(new Date(), "M/d/yyyy");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(190).buildVerticalOffset(629);
		BufferedImage dateBi = drawText(date, zbfont, fontSize,
				color, 240, 0, -0.07, true);
		
		SimplePositions[] sp = {nameSP,nameSP2,dateSP };

		BufferedImage[] bis = { nameBI,nameBI2,dateBi };

		return super.getSaveFile(sp, bis, 0.8f, tmpBackPic);
	}

}
