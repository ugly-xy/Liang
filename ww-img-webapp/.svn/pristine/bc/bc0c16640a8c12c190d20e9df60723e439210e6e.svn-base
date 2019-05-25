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
import com.zb.service.image.love.GuaGuaBiaoBai;

public class SongZhongJiLianAiTool  extends BaseTool implements OneDraw{
	
	public SongZhongJiLianAiTool(StorageService storageService) {
		super(storageService);
	}

	public SongZhongJiLianAiTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/576a6dcb0cf280ab0cd9210d";
		StorageService storageService = new StorageService();
		SongZhongJiLianAiTool tyt = new SongZhongJiLianAiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 12;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpPath,String count) throws IOException {
		// 姓名
		one = "宋仲基自爆恋情,与中国女孩"+one+"交往两年";
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(26).buildVerticalOffset(357);
		BufferedImage nameBI = drawText(one,zbfont, fontSize, color,
				700, 0, 0, true);
		SimplePositions[] sp = { nameSP};

		BufferedImage[] bis = { nameBI};

		return super.getSaveFile(sp, bis, 1f, tmpPath);
	}

}
