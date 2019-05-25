package com.zb.service.image.holiday;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.*;

public class FuQinJie2Tool extends BaseTool implements ThreeDraw {

	public FuQinJie2Tool(StorageService storageService) {
		super(storageService);
	}

	public FuQinJie2Tool() {

	}

	public static void main(String[] args) throws IOException {
		// String tmpPath0 = "205.png";
		StorageService storageService = new StorageService();
		FuQinJie2Tool tyt = new FuQinJie2Tool(storageService);
		tyt.isDebug(true);
		int i = 1;
		System.out
				.println(tyt.draw("爸爸我好爱你，今天是父亲节，祝你节日快乐！", "装点逼", S[i], T[i],null));
	}

	static String T[] = {
			"http://imgzb.zhuangdianbi.com/57627c7a0cf2c103b264d2cc",
			"http://imgzb.zhuangdianbi.com/57627c9c0cf2c103b264d2e9" };
	static String S[] = { "卡片", "贺卡" };

	@Override
	public String draw(String one, String two, String three, String tmpPath,String count)
			throws IOException {
		int fontSize = 30;
		Color color = new Color(0, 0, 0);
		String fontStyle = ZbFont.新蒂小丸子小学版.font();
		int fontType = Font.PLAIN;

		SimplePositions contentSP = null;
		BufferedImage contentBI = null;
		if (three.equals(S[0])) {
			one = one + "\n             " + two;
			// 内容
			contentSP = new SimplePositions();
			contentSP.buildHorizontalOffset(350).buildVerticalOffset(340);
			contentBI = drawText(one, fontStyle, fontType, fontSize, color,
					300, 300, 0, true);

		} else if (three.equals(S[1])) {
			fontSize = 18;
			// 内容
			one = one + "\n                          " + two;
			contentSP = new SimplePositions();
			contentSP.buildHorizontalOffset(410).buildVerticalOffset(260);
			contentBI = drawText(one, ZbFont.新蒂小丸子小学版, fontSize, color, 210,
					400, -0.04, true, 10, 5);
		}

		SimplePositions[] sp = { contentSP };

		BufferedImage[] bis = { contentBI };

		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
