package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.love.GuangZhouTaTool;

import net.coobird.thumbnailator.Thumbnails;

public class MuQinJieTool extends BaseTool {

	public MuQinJieTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/572b4c550cf2a585f88f1aa5";
		StorageService storageService = new StorageService();
		MuQinJieTool tyt = new MuQinJieTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.drawImg("装点逼", "妈妈我好爱你，亲一个我爱你！", tmpPath0));
	}

	public String drawImg(String name, String content, String tmpPath)
			throws IOException {
		int fontSize = 36;
		Color color = new Color(58, 73, 68);
		String fontStyle = ZbFont.土肥圆.font();
		int fontType = Font.PLAIN;

		// 内容
		SimplePositions contentSP = new SimplePositions();
		contentSP.buildHorizontalOffset(880).buildVerticalOffset(480);
		BufferedImage contentBI = drawText(content, fontStyle, fontType,
				fontSize, color, 240, 0, -0.05, true);

		int len = fontSize * content.length();
		int line = len % 240 == 0 ? len / 240 + 1 : len / 240 + 2;
		int h = 32 * line;
		//System.out.println(h);
		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(980).buildVerticalOffset(480+h);
		BufferedImage nameBI = drawText(name, fontStyle, fontType, fontSize,
				color, 240, 0, -0.05, true);

		SimplePositions[] sp = { nameSP, contentSP };

		BufferedImage[] bis = { nameBI, contentBI };

		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
