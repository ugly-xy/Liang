package com.zb.service.image.certificate;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.common.utils.PinYinUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.holiday.HaoBaBaTool;
import com.zb.service.image.i.OneDraw;

public class PingGuoGongZuoZhengTool  extends BaseTool implements OneDraw {
	
	public PingGuoGongZuoZhengTool(StorageService storageService) {
		super(storageService);
	}

	public PingGuoGongZuoZhengTool() {
	}
	
	public static void main(String[] args) throws IOException {
		// String tmpPath0 =
		// "http://imgzb.zhuangdianbi.com/5732a2a80cf2d5342be19ca8";
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5769fc070cf2a4a08925a122";
		StorageService storageService = new StorageService();
		PingGuoGongZuoZhengTool tyt = new PingGuoGongZuoZhengTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼", tmpPath0,null));
	}
	//Adobe半粗体
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 22;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String tmpPath,String count) throws IOException {
		// 姓名
		
		one = "“"+PinYinUtil.toPinyinAllFirstUp(one)+"”";
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(125).buildVerticalOffset(327);
		BufferedImage nameBI = drawText(one, zbfont, fontSize, color,
				240, 0, 0, true);
		
		SimplePositions[] sp = { nameSP};

		BufferedImage[] bis = { nameBI };

		return super.getSaveFile(sp, bis, 0.7f, tmpPath);
	}

}
