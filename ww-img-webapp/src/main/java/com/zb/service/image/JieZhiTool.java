package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import com.zb.service.cloud.StorageService;

import net.coobird.thumbnailator.Thumbnails;

public class JieZhiTool extends BaseTool {

	public static void main(String[] args) throws IOException {
		// String tmpPath =
		// "http://imgzb.zhuangdianbi.com/56ea75d393b03adfb7a20e9e";
		String tmpPath = "http://imgzb.zhuangdianbi.com/56ebac2193b0cf9c1b60ca9b";

		if (tmpPath.contains("?")) {
			tmpPath = tmpPath.substring(0, tmpPath.indexOf("?"));
		}
		StorageService storageService = new StorageService();
		JieZhiTool tyt = new JieZhiTool(storageService);
		System.out.println(tyt.drawImg("鲍金莹", "真的，输了你，赢得了世界又如何？", tmpPath));

	}

	public JieZhiTool(StorageService storageService) {
		super(storageService);
		// TODO Auto-generated constructor stub
	}

	/***
	 * 
	 * @param name
	 *            姓名
	 * @param content
	 *            内容
	 * @param tmpPath
	 * @return
	 * @throws IOException
	 */
	public String drawImg(String name, String content, String tmpPath)
			throws IOException {
		String[] fontStyles = { "书体坊赵九江钢笔行书", "华文行楷", "陈继世-硬笔行书" };
		String fontStyle = fontStyles[new Random().nextInt(3)];
		Color color = new Color(53, 36, 39);
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(99).buildVerticalOffset(216);
		BufferedImage nameBI = drawText(name, fontStyle, Font.BOLD, 29, color,
				260);

		SimplePositions contentSP = new SimplePositions();
		contentSP.buildHorizontalOffset(104).buildVerticalOffset(274);
		BufferedImage contentBI = drawText(content, fontStyle, Font.PLAIN, 28,
				color, 334);
		InputStream in = null;
		BufferedImage bi = null;
		try {
			in = super.getFile(tmpPath);
			bi = Thumbnails.of(in).scale(1.0d).watermark(nameSP, nameBI, 1.0f)
					.watermark(contentSP, contentBI, 1.0f).outputQuality(1.0d)
					.outputFormat("png").asBufferedImage();
		} finally {
			if (in != null) {
				in.close();
			}
		}

		return super.saveFile(bi, getDefFormatName());
	}

}
