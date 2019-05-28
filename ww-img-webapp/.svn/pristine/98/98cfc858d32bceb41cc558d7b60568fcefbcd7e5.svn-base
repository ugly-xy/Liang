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

public class JueQingDanTool extends BaseTool implements OneDraw{
	public JueQingDanTool(StorageService storageService) {
		super(storageService);
	}

	public JueQingDanTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57c9253d0cf2c4f2f2d19183";
		StorageService storageService = new StorageService();
		JueQingDanTool tyt = new JueQingDanTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.钟齐陈伟勋硬笔行书字库;
	static int fontSize = 38;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(300-one.length()*fontSize/2).buildVerticalOffset(544);
		BufferedImage nameBI = drawText(one,zbfont,fontSize, color,
				240, 100, -0.03, true);
		
		SimplePositions[] sp = { nameSP};
		BufferedImage[] bis = { nameBI};
		return super.getSaveFile(sp, bis, 0.75f, tmpBackPic);
	}

}
