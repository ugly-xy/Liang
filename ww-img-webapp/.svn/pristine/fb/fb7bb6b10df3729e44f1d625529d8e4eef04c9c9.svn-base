package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import com.zb.service.cloud.StorageService;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

public class LouBiaoBaiTool extends BaseTool {

	public static void main(String[] args) throws IOException {
		String tmpPath = "http://imgzb.zhuangdianbi.com/56efebfd93b0dd0897fe2252";
		if (tmpPath.contains("?")) {
			tmpPath = tmpPath.substring(0, tmpPath.indexOf("?"));
		}
		StorageService storageService = new StorageService();
		LouBiaoBaiTool lbb = new LouBiaoBaiTool(storageService);
		System.out.println(lbb.drawImg("算了吧", tmpPath));
	}

	public LouBiaoBaiTool(StorageService storageService) {
		super(storageService);
		// TODO Auto-generated constructor stub
	}

	public String drawImg(String txt, String tmpPath) throws IOException {
		int left = 285;
		int height = 179;
		char[] names = txt.toCharArray();
		int len = names.length;
		SimplePositions[] sp = new SimplePositions[len + 4];
		BufferedImage[] bis = new BufferedImage[len + 4];
		for (int i = 0; i < len; i++) {
			String name = "" + names[i];
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(left).buildVerticalOffset(height);
			sp[i] = nameSP;
			BufferedImage nameBI = drawText(name, "微软雅黑", Font.PLAIN, 52,
					new Color(158, 150, 132), 300, 0, 0.08, true);
			bis[i] = nameBI;
			left -= 7;
			height += 66;
		}
		InputStream in = null;
		BufferedImage bi = null;
		try {
			in = super.getFile(tmpPath);
			Builder<?> builder = Thumbnails.of(in)
					.scale(1.0d).outputQuality(1.0d);
			for (int i = 0; i < len; i++) {
				builder.watermark(sp[i], bis[i], 1.0f);
			}

			bi = builder.outputFormat("png").asBufferedImage();
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return super.saveFile(bi, getDefFormatName());

	}

}
