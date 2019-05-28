package com.zb.service.image.certificate;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class G20FengHuiTool extends BaseTool implements TwoDraw{
	public G20FengHuiTool(StorageService storageService) {
		super(storageService);
	}

	public G20FengHuiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57cceb9a0cf2c4f2f2d34755";
		StorageService storageService = new StorageService();
		G20FengHuiTool tyt = new G20FengHuiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点比","ceshi.png", tmpPath0,null));//174 223
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 24;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//姓名1
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(224-one.length()*fontSize/2).buildVerticalOffset(490);
		BufferedImage nameBI = drawText(one,zbfont,fontSize, color,
				240, 100, 0, true);
		
		//姓名2
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(340-one.length()*20/2).buildVerticalOffset(733);
		BufferedImage nameBI2 = drawText(one,zbfont,20, color,
				240, 100, -0.1, true,5,5);
		
		//玩家照片
		BufferedImage p = super.compressImage(two, 174, 223);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(136).buildVerticalOffset(260);
		
		
		SimplePositions[] sp = { nameSP,nameSP2, pSP };
		BufferedImage[] bis = { nameBI,nameBI2, p };
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
