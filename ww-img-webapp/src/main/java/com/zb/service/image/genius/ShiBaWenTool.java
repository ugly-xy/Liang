package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class ShiBaWenTool extends BaseTool implements TwoDraw{
	public ShiBaWenTool(StorageService storageService) {
		super(storageService);
	}

	public ShiBaWenTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "295-吻.png";
		StorageService storageService = new StorageService();
		ShiBaWenTool tyt = new ShiBaWenTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("素材.jpg","请接收我的祖传18吻！", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 12;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//玩家照片
		BufferedImage p = super.compressImage(one, 145, 123);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(151).buildVerticalOffset(147);
		
		//一句话
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(164).buildVerticalOffset(271);
		BufferedImage nameBI = drawText(two, zbfont, fontSize, color,
				250, 150, 0, true);
		SimplePositions[] sp = {nameSP,pSP};

		BufferedImage[] bis = { nameBI,p};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
