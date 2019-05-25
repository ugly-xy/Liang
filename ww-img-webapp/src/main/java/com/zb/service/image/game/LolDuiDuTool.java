package com.zb.service.image.game;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.FiveDraw;
import com.zb.service.image.i.OneDraw;
import com.zb.service.image.i.ThreeDraw;
import com.zb.service.image.i.TwoDraw;

public class LolDuiDuTool extends BaseTool implements FiveDraw {

	public LolDuiDuTool(StorageService storageService) {
		super(storageService);
	}

	public LolDuiDuTool() {
	}

	public static void main(String[] args) throws IOException {
		String p0 = "169.png";
		StorageService storageService = new StorageService();
		LolDuiDuTool tyt = new LolDuiDuTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.draw("装点逼", "超级剑", S[0], "疾风剑豪·亚瑟", "迅捷伺候·提莫",
				p0,null));
	}

	static Color c = new Color(0, 0, 0);
	static String[] S = { "爷孙局", "父子局" };
	static int fs = 18;
	static ZbFont f = ZbFont.微软雅黑;

	@Override
	public String draw(String one, String two, String three, String four,
			String five, String tmpPath,String count) throws IOException {

		SimplePositions oneSP = new SimplePositions();
		oneSP.buildHorizontalOffset(85).buildVerticalOffset(200);
		BufferedImage oneBi = drawText("甲方：" + one, f, fs, c, 300, 100, 0, true);

		SimplePositions oneSP2 = new SimplePositions();
		oneSP2.buildHorizontalOffset(480).buildVerticalOffset(435);

		SimplePositions twoSP = new SimplePositions();
		twoSP.buildHorizontalOffset(85).buildVerticalOffset(240);
		BufferedImage twoBi = drawText("乙方：" + two, f, fs, c, 300, 100, 0, true);

		SimplePositions twoSP2 = new SimplePositions();
		twoSP2.buildHorizontalOffset(480).buildVerticalOffset(475);

		String w = "父亲";
		String l = "儿子";
		if (three.equals(S[0])) {
			w = "爷爷";
			l = "孙子";
		}

		StringBuilder sb = new StringBuilder("      两人经约定，与");
		sb.append(DateUtil.dateFormatShortCN(new Date()))
				.append("，在网吧 solo（单挑）英雄联盟，").append(three)
				.append("采用三局两胜制。甲方使用英雄（").append(four).append("），乙方使用英雄（")
				.append(five).append("），胜利的一方为").append(w).append("，失败的一方为")
				.append(l).append("。\n\n\n此合同双方签字画押即生效！");

		SimplePositions duSP = new SimplePositions();
		duSP.buildHorizontalOffset(85).buildVerticalOffset(287);
		BufferedImage duBI = drawText(sb.toString(), f, fs - 2, c, 560, 200, 0,
				true);

		SimplePositions threeSP = new SimplePositions();
		threeSP.buildHorizontalOffset(294).buildVerticalOffset(151);
		BufferedImage threeBI = drawText(three + "合同", f, fs + 8, c, 300, 100,
				0, true);

		SimplePositions threeSP2 = new SimplePositions();
		threeSP2.buildHorizontalOffset(221).buildVerticalOffset(435);
		BufferedImage threeBI2 = drawText("扫描左侧二维码\n\n观看" + three + "直播", f,
				fs, c, 300, 100, 0, true);

		SimplePositions[] sp = { oneSP, oneSP2, twoSP, twoSP2, threeSP,
				threeSP2, duSP };
		BufferedImage[] bis = { oneBi, oneBi, twoBi, twoBi, threeBI, threeBI2,
				duBI };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}
}
