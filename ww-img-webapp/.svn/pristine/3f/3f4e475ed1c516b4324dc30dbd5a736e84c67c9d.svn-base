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

public class SanXingNote7Tool extends BaseTool implements OneDraw{
	public SanXingNote7Tool(StorageService storageService) {
		super(storageService);
	}

	public SanXingNote7Tool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57e0e6980cf22e02635e1085";
		StorageService storageService = new StorageService();
		SanXingNote7Tool tyt = new SanXingNote7Tool(storageService);
		tyt.isDebug(true);
		System.out
				.println(tyt.draw("装点逼",tmpPath0,null));//10
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 18;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//上一句话
 		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(370-(one.length()+9)*fontSize/2).buildVerticalOffset(446);
 		BufferedImage nameBi = drawText(one+",别惹我！小心我炸你", zbfont, fontSize,
 				color, 800, 100, -0.05, true);
 		
 		SimplePositions[] sp = { nameSP};

		BufferedImage[] bis = {nameBi};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
}
