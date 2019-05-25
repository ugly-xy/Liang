package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;

public class XinWenLianBoTool extends BaseTool {

	public XinWenLianBoTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath = "http://imgzb.zhuangdianbi.com/56f0f30593b071409286d802";
		if (tmpPath.contains("?")) {
			tmpPath = tmpPath.substring(0, tmpPath.indexOf("?"));
		}
		System.out.println(tmpPath);
		StorageService ss = new StorageService();
		XinWenLianBoTool jzt = new XinWenLianBoTool(ss);
		System.out.println(jzt.drawImg("装逼是一项技术活，没有装逼的日子，这是多么无聊的日子！", tmpPath));
	}

	public String drawImg(String txt, String tmpPath) throws IOException {
		int fontSize = 16;
		int left = 100;
		if (txt.length() > 24) {
			txt = txt.substring(0, 24);
		}

		SimplePositions contentSP = new SimplePositions();
		contentSP.buildHorizontalOffset(left).buildVerticalOffset(340);
		BufferedImage contentBI = drawText(txt, "黑体", Font.PLAIN, fontSize,
				Color.BLACK, 400);

		SimplePositions[] sp = { contentSP };

		BufferedImage[] bis = { contentBI };

		return super.getSaveFile(sp, bis, 1.0f, tmpPath);
	}
}
