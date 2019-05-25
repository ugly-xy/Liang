package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class JianJiaoTiTool extends BaseTool implements OneDraw{
	public JianJiaoTiTool(StorageService storageService) {
		super(storageService);
	}

	public JianJiaoTiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5847f25b0cf22d05d4066a5d";
		StorageService storageService = new StorageService();
		JianJiaoTiTool tyt = new JianJiaoTiTool(storageService);
		tyt.isDebug(true); 
		System.out.println(tyt.draw("你起来幢啊", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 120;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		int len = one.length();

		int fonts = fontSize;
		int left =(fonts-20)*(len-1);
		
		
		
		SimplePositions[] sp = new SimplePositions[len];
		BufferedImage[] bis = new BufferedImage[len];
		
		
		for (int i = len; i > 0; i--) {
			
			SimplePositions nameSP1 = new SimplePositions();
			nameSP1.buildHorizontalOffset(left).buildVerticalOffset(0);
			BufferedImage nameBI1 = drawText("" + one.charAt(i-1), zbfont,
					fonts, color, 240, 238, 0, true);

			sp[i-1] = nameSP1;
			bis[i-1] = nameBI1;
			fonts = fonts-10;
			left = left-fonts;
			
		}

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
}
