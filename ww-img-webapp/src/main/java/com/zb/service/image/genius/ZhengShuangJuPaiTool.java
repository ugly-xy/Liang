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

public class ZhengShuangJuPaiTool extends BaseTool implements TwoDraw{
	public ZhengShuangJuPaiTool(StorageService storageService) {
		super(storageService);
	}

	public ZhengShuangJuPaiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57c906420cf2c4f2f2d18433";
		StorageService storageService = new StorageService();
		ZhengShuangJuPaiTool tyt = new ZhengShuangJuPaiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("BIBI","我真的喜欢你", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 28;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(180-one.length()*fontSize/2).buildVerticalOffset(310);
		BufferedImage nameBI = drawText(one,zbfont,fontSize, color,
				240, 100, 0, true);
		
		//一句话
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(180-two.length()*fontSize/2).buildVerticalOffset(380);
		BufferedImage nameBI2 = drawText(two,zbfont,fontSize, color,
				200, 200, 0, true);
				
		
		SimplePositions[] sp = { nameSP, nameSP2 };
		BufferedImage[] bis = { nameBI, nameBI2 };
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
