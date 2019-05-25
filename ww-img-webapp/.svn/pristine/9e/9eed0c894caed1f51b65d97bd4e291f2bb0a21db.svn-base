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

public class QueRenKuangTool extends BaseTool implements OneDraw{
	public QueRenKuangTool(StorageService storageService) {
		super(storageService);
	}

	public QueRenKuangTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/576cfc2f0cf27682dd15c3ff";
		StorageService storageService = new StorageService();
		QueRenKuangTool tyt = new QueRenKuangTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装逼是不是很酷是不是啊", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 22;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpBackPic,String count) throws IOException {
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(190).buildVerticalOffset(160);
		BufferedImage nameBI = drawText(one+"？",zbfont,fontSize, color,
				240, 100, 0, true);
		SimplePositions[] sp = { nameSP};

		BufferedImage[] bis = { nameBI};

		return super.getSaveFile(sp, bis, 0.95f, tmpBackPic);
	}
}
