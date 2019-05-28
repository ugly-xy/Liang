package com.zb.service.image.holiday;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class JiNian918Tool extends BaseTool implements OneDraw {
	public JiNian918Tool(StorageService storageService) {
		super(storageService);
	}

	public JiNian918Tool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57de75e40cf2b6e871cbfc84";
		StorageService storageService = new StorageService();
		JiNian918Tool tyt = new JiNian918Tool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼", tmpPath0,"15000"));
	}

	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 42;
	static Color color = new Color(221, 47, 47);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(360-one.length()*58/2).buildVerticalOffset(560);
		BufferedImage nameBI = drawText(one,zbfont.font(),fontType,58, color,
				240, 0, 0, true);
		
		//次数
		DecimalFormat df=new DecimalFormat("00000000");
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(350).buildVerticalOffset(1084);
		BufferedImage nameBI2 = drawText(df.format(Integer.parseInt(count)+15125), zbfont, fontSize, color,
				240, 100, 0, true);
		
		SimplePositions[] sp = { nameSP,nameSP2};
		BufferedImage[] bis = { nameBI,nameBI2};
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
