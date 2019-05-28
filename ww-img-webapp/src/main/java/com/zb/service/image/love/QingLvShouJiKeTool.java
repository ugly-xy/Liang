package com.zb.service.image.love;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class QingLvShouJiKeTool extends BaseTool implements TwoDraw{
	public QingLvShouJiKeTool(StorageService storageService) {
		super(storageService);
	}

	public QingLvShouJiKeTool() {
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57bc149c0cf27297e4142544";
		StorageService storageService = new StorageService();
		QingLvShouJiKeTool tyt = new QingLvShouJiKeTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("比比", "装点", tmpPath0,
				null));
	}
	
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 25;
	static Color color = new Color(241, 40, 50);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		// 南方姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(120-(one.length()+3)*fontSize/2).buildVerticalOffset(335);
		BufferedImage nameBI = drawText(one+"的男人", zbfont.font(),fontType, fontSize, color,
				300, 100, 0, true);
		// 女方姓名
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(330-(two.length()+3)*fontSize/2).buildVerticalOffset(335);
		BufferedImage nameBI2 = drawText(two+"的女人", zbfont.font(),fontType, fontSize, color,
				300, 100, 0, true);

		SimplePositions[] sp = { nameSP,nameSP2 };

		BufferedImage[] bis = { nameBI,nameBI2};

		return super.getSaveFile(sp, bis, 0.8f, tmpBackPic);
	}

}
