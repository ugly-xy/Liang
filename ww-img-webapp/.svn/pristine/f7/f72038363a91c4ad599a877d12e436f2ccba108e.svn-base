package com.zb.service.image.tuhao;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class HaoDuYiCiTool extends BaseTool implements OneDraw{
	public HaoDuYiCiTool(StorageService storageService) {
		super(storageService);
	}

	public HaoDuYiCiTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57722f690cf2ebe0ca87e8c0";
		StorageService storageService = new StorageService();
		HaoDuYiCiTool tyt = new HaoDuYiCiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 23;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	
	@Override
	public String draw(String one, String tmpPath,String count) throws IOException {
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(167).buildVerticalOffset(547);
		BufferedImage nameBI = drawText(one+"总",zbfont.font(),fontType,fontSize, color,
				240, 100, 0, true);
		SimplePositions[] sp = { nameSP};

		BufferedImage[] bis = { nameBI};

		return super.getSaveFile(sp, bis, 0.95f, tmpPath);
	}

}
