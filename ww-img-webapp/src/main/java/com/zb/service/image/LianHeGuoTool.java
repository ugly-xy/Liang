package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import com.zb.service.cloud.StorageService;

import net.coobird.thumbnailator.Thumbnails;

public class LianHeGuoTool extends BaseTool {

	public static void main(String[] args) throws IOException {
		String tmpPath = "http://imgzb.zhuangdianbi.com/56ebbeba93b0cf9c1b60caa7";

		if (tmpPath.contains("?")) {
			tmpPath = tmpPath.substring(0, tmpPath.indexOf("?"));
		}
		StorageService storageService = new StorageService();
		LianHeGuoTool tyt = new LianHeGuoTool(storageService);
		System.out.println(tyt.drawImg("赵鹏","zhaopeng", tmpPath));
		

	}
	
	public LianHeGuoTool(StorageService storageService) {
		super(storageService);
		// TODO Auto-generated constructor stub
	}
	
	
	
	/***
	 * 
	 * @param name
	 *            姓名
	 * @param tmpPath
	 * @return
	 * @throws IOException
	 */
	public String drawImg(String name, String pingyin, String tmpPath) throws IOException {
		String fontStyle = "微软雅黑";
		int fontSize = 16;
		Color color = new Color(105,108,101);
		SimplePositions enSP = new SimplePositions();
		enSP.buildHorizontalOffset(80).buildVerticalOffset(151);
		BufferedImage enBI = drawText(pingyin, fontStyle, Font.PLAIN, fontSize, color, 260);
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(363).buildVerticalOffset(152);
		BufferedImage nameBI = drawText(name, fontStyle, Font.PLAIN, fontSize, color, 260);
		
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		SimplePositions yearSP = new SimplePositions();
		yearSP.buildHorizontalOffset(526).buildVerticalOffset(296);
		BufferedImage yearBI = drawText("" + year, null, Font.PLAIN, fontSize,
				color, 300);

		SimplePositions monthSP = new SimplePositions();
		monthSP.buildHorizontalOffset(578).buildVerticalOffset(296);
		BufferedImage monthBI = drawText("" + month, null, Font.PLAIN, fontSize,
				color, 300);

		SimplePositions daySP = new SimplePositions();
		daySP.buildHorizontalOffset(604).buildVerticalOffset(296);
		BufferedImage dayBI = drawText("" + day, null, Font.PLAIN, fontSize,
				color, 300);
		InputStream in = null;
		BufferedImage bi = null;
		try{
			in = super.getFile(tmpPath);
			bi = Thumbnails.of(in).scale(1.0d)
				.watermark(enSP, enBI, 0.85f)
				.watermark(nameSP, nameBI, 0.85f)
				.watermark(yearSP, yearBI, 1.0f)
				.watermark(monthSP, monthBI, 1.0f)
				.watermark(daySP, dayBI, 1.0f)
				.outputQuality(1.0d).outputFormat("png").asBufferedImage();
		} finally {
			if(in != null){
				in.close();
			}
		}
		return super.saveFile(bi, getDefFormatName());
	}

}
