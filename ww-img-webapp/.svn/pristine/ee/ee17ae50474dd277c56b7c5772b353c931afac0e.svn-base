package com.zb.service.image.love;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.ThreeDraw;

public class JieHunYaoQingHanTool extends BaseTool implements ThreeDraw{
	public JieHunYaoQingHanTool(StorageService storageService) {
		super(storageService);
	}

	public JieHunYaoQingHanTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57762a240cf20718a814dde2";
		StorageService storageService = new StorageService();
		JieHunYaoQingHanTool tyt = new JieHunYaoQingHanTool(storageService);
		tyt.isDebug(true);
		System.out
				.println(tyt.draw("装点逼","装逼","月老",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 16;
	static Color color = new Color(169,26,28);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String three, String tmpBackPic, String count) throws IOException {
		// 受邀人
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(370).buildVerticalOffset(350);
		BufferedImage nameBI = drawText(one, zbfont.font(),fontType, fontSize, color,
				240, 100, 0, true);
		// 新郎姓名
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(360).buildVerticalOffset(440);
		BufferedImage nameBI2 = drawText(two,  zbfont.font(),fontType, fontSize, color,
				240, 100, 0, true);	
		
		// 新娘姓名
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(453).buildVerticalOffset(440);
		BufferedImage nameBI3 = drawText(three,  zbfont.font(),fontType, fontSize, color,
				240, 100, 0, true);
		
		SimplePositions[] sp = { nameSP,nameSP2,nameSP3 };

		BufferedImage[] bis = { nameBI,nameBI2,nameBI3};

		return super.getSaveFile(sp, bis, 0.8f, tmpBackPic);
	}

}
