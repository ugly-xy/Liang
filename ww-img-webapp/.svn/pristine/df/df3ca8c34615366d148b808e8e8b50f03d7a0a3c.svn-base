package com.zb.service.image.love;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.FourDraw;

public class QQingLvTool extends BaseTool implements FourDraw{
	public QQingLvTool(StorageService storageService) {
		super(storageService);
	}

	public QQingLvTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "293-情侣.png";
		StorageService storageService = new StorageService();
		QQingLvTool tyt = new QQingLvTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点一逼","装点二逼","1.png","999", tmpPath0,null));
		
		
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 30;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	
	@Override
	public String draw(String one, String two, String three, String four, String tmpBackPic, String count)
			throws IOException {
		// TODO Auto-generated method stub
		//444 297
		// 甲方姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(270-one.length()*20).buildVerticalOffset(480);
		BufferedImage nameBI = drawText(one, zbfont.font(),fontType, 20, new Color(0,157,142),
				240, 100, 0, true);
		// 乙方姓名
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(300).buildVerticalOffset(480);
		BufferedImage nameBI2 = drawText(two, zbfont.font(),fontType, 20,  new Color(0,157,142),
				240, 100, 0, true);
		//玩家照片
		BufferedImage p = super.compressImage(three, 444, 297);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(61).buildVerticalOffset(169);
		
		// 天数
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(295+(4-four.length())*5).buildVerticalOffset(545);
		BufferedImage nameBI3 = drawText(four, zbfont.font(),fontType, 26, new Color(251,77,144),
				240, 100, 0, true);
		SimplePositions[] sp = { pSP,nameSP,nameSP2,nameSP3};

		BufferedImage[] bis = { p,nameBI,nameBI2,nameBI3};

		return super.getSaveFile(sp, bis, 0.9f, tmpBackPic);
	}
}
