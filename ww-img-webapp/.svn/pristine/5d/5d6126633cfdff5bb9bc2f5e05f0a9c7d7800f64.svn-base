package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import com.zb.service.cloud.StorageService;

import net.coobird.thumbnailator.Thumbnails;

public class JiangZhuangTool extends BaseTool {

	public JiangZhuangTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath = "/16/0304/19/1457092714841560.png?v=1457421913743";
		if (tmpPath.contains("?")) {
			tmpPath = tmpPath.substring(0, tmpPath.indexOf("?"));
		}
		System.out.println(tmpPath);
		StorageService ss = new StorageService();
		JiangZhuangTool jzt = new JiangZhuangTool(ss);
		System.out.println(jzt.drawImg("赵鹏", tmpPath));
	}

	public String drawImg(String txt, String tmpPath) throws IOException {
		int left = 175;
		int height = 149;
		left = left - txt.length() * 24;

		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(left).buildVerticalOffset(height);
		BufferedImage nameBI = drawText(txt, "微软雅黑", Font.PLAIN, 24,
				Color.BLACK, 300);

		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);

		SimplePositions yearSP = new SimplePositions();
		yearSP.buildHorizontalOffset(440).buildVerticalOffset(353);
		BufferedImage yearBI = drawText("" + year, null, Font.ITALIC, 24,
				Color.BLACK, 300);

		SimplePositions monthSP = new SimplePositions();
		monthSP.buildHorizontalOffset(535).buildVerticalOffset(353);
		BufferedImage monthBI = drawText("" + month, null, Font.ITALIC, 24,
				Color.BLACK, 300);

		SimplePositions daySP = new SimplePositions();
		daySP.buildHorizontalOffset(590).buildVerticalOffset(353);
		BufferedImage dayBI = drawText("" + day, null, Font.ITALIC, 24,
				Color.BLACK, 300);
		InputStream in = null;
		BufferedImage bi = null;
		try{
			in = super.getFile(tmpPath);
			bi = Thumbnails.of(in).scale(1.0d)
					.watermark(nameSP, nameBI, 1.0f)
					.watermark(yearSP, yearBI, 1.0f)
					.watermark(monthSP, monthBI, 1.0f)
					.watermark(daySP, dayBI, 1.0f).outputQuality(1.0d)
					.asBufferedImage();
		} finally {
			if(in != null){
				in.close();
			}
		}
		
		return super.saveFile(bi, getDefFormatName());
	}
}
