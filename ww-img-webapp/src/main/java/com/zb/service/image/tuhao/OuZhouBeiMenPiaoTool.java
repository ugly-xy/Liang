package com.zb.service.image.tuhao;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.common.utils.PinYinUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class OuZhouBeiMenPiaoTool extends BaseTool implements OneDraw {

	public OuZhouBeiMenPiaoTool(StorageService storageService) {
		super(storageService);
	}

	public OuZhouBeiMenPiaoTool() {

	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5764d70d0cf2cb9eefcbfa93";
		StorageService storageService = new StorageService();
		OuZhouBeiMenPiaoTool tyt = new OuZhouBeiMenPiaoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼", tmpPath0,null));
	}

	static ZbFont zbfont = ZbFont.宋体;
	static int fontSize = 10;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;

	@Override
	public String draw(String one, String tmpPath,String count) throws IOException {

		one = PinYinUtil.toPinyinUp(one);
		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(305).buildVerticalOffset(136);
		BufferedImage nameBI = drawText(one, zbfont, fontSize, color, 200, 100,
				0, true);
		// 姓名2
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(305).buildVerticalOffset(362);
		BufferedImage nameBI2 = drawText(one, zbfont, fontSize, color, 200,
				100, 0, true);

		SimplePositions[] sp = { nameSP, nameSP2 };

		BufferedImage[] bis = { nameBI, nameBI2 };

		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
