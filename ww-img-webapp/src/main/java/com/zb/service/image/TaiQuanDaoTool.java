package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import com.zb.service.cloud.StorageService;

import net.coobird.thumbnailator.Thumbnails;

public class TaiQuanDaoTool extends BaseTool {

	public TaiQuanDaoTool(StorageService storageService) {
		super(storageService);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		String tmpPath = "http://imgzb.zhuangdianbi.com/56ebcc5293b0cf9c1b60caab";
		if (tmpPath.contains("?")) {
			tmpPath = tmpPath.substring(0, tmpPath.indexOf("?"));
		}
		StorageService storageService = new StorageService();
		TaiQuanDaoTool tyt = new TaiQuanDaoTool(storageService);
		System.out.println(tyt.drawImg("苍井空", "CN" + 100001, tmpPath));

	}

	/***
	 * @param name
	 *            姓名
	 * @param tmpPath
	 *            背景图路径
	 * @param serialNumber
	 *            编号
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String name, String serialNumber, String tmpPath)
			throws IOException {
		int fontSize = 18;
		String fontStyle = "黑体";
		Color color = new Color(51, 52, 49);
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(354).buildVerticalOffset(397);
		BufferedImage nameBI = drawText(name, fontStyle, Font.PLAIN, fontSize,
				color, 260);

		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		String date = "" + year + (month < 10 ? "-0" + month : "-" + month)
				+ (day < 10 ? "-0" + day : "-" + day);
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(324).buildVerticalOffset(636);
		BufferedImage dateBI = drawText(date, fontStyle, Font.PLAIN, fontSize,
				color, 260);

		SimplePositions numberSP = new SimplePositions();
		numberSP.buildHorizontalOffset(354).buildVerticalOffset(355);
		BufferedImage numberBI = drawText(serialNumber, fontStyle, Font.PLAIN,
				fontSize, color, 260);
		InputStream in = null;
		BufferedImage bi = null;
		try {
			in = super.getFile(tmpPath);
			bi = Thumbnails.of(in).scale(1.0d).watermark(nameSP, nameBI, 1.0f)
					.watermark(dateSP, dateBI, 1.0f)
					.watermark(numberSP, numberBI, 1.0f).outputQuality(1.0d)
					.asBufferedImage();
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return super.saveFile(bi, getDefFormatName());
	}

}
