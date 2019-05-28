package com.zb.service.image.certificate;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class SongZhongJiJianMianTool extends BaseTool implements OneDraw{
	public SongZhongJiJianMianTool(StorageService storageService) {
		super(storageService);
	}

	public SongZhongJiJianMianTool() {
	}
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/578365170cf2e16741d014a5";
		StorageService storageService = new StorageService();
		SongZhongJiJianMianTool tyt = new SongZhongJiJianMianTool(storageService);
		tyt.isDebug(true);
		System.out
				.println(tyt.draw("装点逼", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 24;
	//static Color color = new Color(181, 130, 42);
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		// 姓名
		String one0 = "恭喜你:"+one+",获得宋仲基粉丝见面会巡演门票一张！";
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(25).buildVerticalOffset(580);
		BufferedImage nameBI = drawText(one0, zbfont, fontSize, color,
				350, 0, 0, true);
		SimplePositions[] sp = { nameSP};

		BufferedImage[] bis = { nameBI};

		return super.getSaveFile(sp, bis, 0.8f, tmpBackPic);
	}
}
