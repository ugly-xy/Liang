package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class DaTiKaRenXingTool extends BaseTool implements OneDraw{
	public DaTiKaRenXingTool(StorageService storageService) {
		super(storageService);
	}

	public DaTiKaRenXingTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/576b930a0cf273bdeaac9fa1";
		StorageService storageService = new StorageService();
		DaTiKaRenXingTool tyt = new DaTiKaRenXingTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装逼", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.建刚静心楷;
	static int fontSize = 25;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpPath,String count) throws IOException {
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(28).buildVerticalOffset(10);
		BufferedImage nameBI = drawText(one,zbfont,fontSize, color,
				240, 100, 0.1, true,20,20);
		
		SimplePositions[] sp = { nameSP };

		BufferedImage[] bis = { nameBI };

		return super.getSaveFile(sp, bis, 0.6f, tmpPath);
	}

}
