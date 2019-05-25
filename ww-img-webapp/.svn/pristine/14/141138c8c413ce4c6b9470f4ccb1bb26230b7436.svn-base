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

public class JiuGongGeYuJianTool extends BaseTool implements OneDraw{
	public JiuGongGeYuJianTool(StorageService storageService) {
		super(storageService);
	}

	public JiuGongGeYuJianTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/584547710cf24a945fcef562";
		StorageService storageService = new StorageService();
		JiuGongGeYuJianTool tyt = new JiuGongGeYuJianTool(storageService);
		tyt.isDebug(true);
		System.out
				.println(tyt.draw("装点逼",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 42;
	static Color color = new Color(255, 115, 15);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//上一句话
 		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(104-one.length()*fontSize/2).buildVerticalOffset(493);
 		BufferedImage nameBi = drawText(one, zbfont.font(),fontType, fontSize,
 				color, 800, 100, 0, true);
 		
 		SimplePositions[] sp = { nameSP};

		BufferedImage[] bis = {nameBi};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
}
