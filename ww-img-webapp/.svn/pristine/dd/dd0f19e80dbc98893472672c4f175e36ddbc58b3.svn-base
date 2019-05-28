package com.zb.service.image.genius;

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

public class ShuangShiYiTingDianTool extends BaseTool implements OneDraw{
	public ShuangShiYiTingDianTool(StorageService storageService) {
		super(storageService);
	}

	public ShuangShiYiTingDianTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/58245cc10cf20a752e984b29";
		StorageService storageService = new StorageService();
		ShuangShiYiTingDianTool tyt = new ShuangShiYiTingDianTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装逼学院",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static ZbFont zbfont2 = ZbFont.宋体;
	static int fontSize = 20;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//学院
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(226-one.length()*fontSize/2).buildVerticalOffset(207);
		BufferedImage nameBI = drawText(one, zbfont2.font(),fontType,fontSize, color,
				800, 200, 0, true);
		// date
		String date = DateUtil.dateFormat(new Date(), "yyyy年MM月");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(327).buildVerticalOffset(280);
		BufferedImage dateBi = drawText(date, zbfont, fontSize,
				color, 300, 0, 0, true);
		
		SimplePositions[] sp = { nameSP,dateSP};

		BufferedImage[] bis = { nameBI,dateBi};

		return super.getSaveFile(sp, bis, 0.42f, tmpBackPic);
	}
}
