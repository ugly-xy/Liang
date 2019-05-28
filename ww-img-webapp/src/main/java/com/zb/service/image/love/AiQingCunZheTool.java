package com.zb.service.image.love;

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
import com.zb.service.image.i.ThreeDraw;

public class AiQingCunZheTool extends BaseTool implements ThreeDraw{
	
	public AiQingCunZheTool(StorageService storageService) {
		super(storageService);
	}

	public AiQingCunZheTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/577629110cf20718a814dca9";
		StorageService storageService = new StorageService();
		AiQingCunZheTool tyt = new AiQingCunZheTool(storageService);
		tyt.isDebug(true);
		System.out
				.println(tyt.draw("装点逼","装逼","月老",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 12;
	static Color color = new Color(77,39,18);
	static int fontType = Font.BOLD;
	
	
	@Override
	public String draw(String one, String two, String three, String tmpBackPic, String count) throws IOException {
		// 姓名1
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(627).buildVerticalOffset(300);
		BufferedImage nameBI = drawText(one, zbfont, fontSize, new Color(77,39,18),
				240, 100, 0, true);
		// 姓名2
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(627).buildVerticalOffset(320);
		BufferedImage nameBI2 = drawText(two, zbfont, fontSize, color,
				240, 100, 0, true);	
		
		// 见证人
		int fontSize2 = 18;
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(425).buildVerticalOffset(430);
		BufferedImage nameBI3 = drawText(three, ZbFont.新蒂小丸子小学版, fontSize2, new Color(77,39,18),
				240, 100, 0, true);
		
		// date
		String date = DateUtil.dateFormat(new Date(), "yyyy年M月d日");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(440).buildVerticalOffset(482);
		BufferedImage dateBi = drawText(date, zbfont.font(), fontType, fontSize,
				color, 240, 0, 0, true);
		
		SimplePositions[] sp = { nameSP,nameSP2,nameSP3,dateSP };

		BufferedImage[] bis = { nameBI,nameBI2,nameBI3 ,dateBi};

		return super.getSaveFile(sp, bis, 0.8f, tmpBackPic);
	}

}
