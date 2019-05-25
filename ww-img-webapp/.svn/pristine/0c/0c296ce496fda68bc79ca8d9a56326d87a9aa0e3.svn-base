package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import com.zb.service.cloud.StorageService;

import net.coobird.thumbnailator.Thumbnails;

public class TongYongTool extends BaseTool {

	public TongYongTool(StorageService storageService) {
		super(storageService);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		String tmpPath = "/16/0308/12/1457410443052550.png?v=1457410446933";
		if (tmpPath.contains("?")) {
			tmpPath = tmpPath.substring(0, tmpPath.indexOf("?"));
		}
		String headPath = "http://imgt.zhuangdianbi.com/56ea62490cf28c16a3371560";
		StorageService storageService = new StorageService(); 
		TongYongTool tyt = new TongYongTool(storageService);
		tyt.drawImg("苍井空", "女", "18", tmpPath, headPath, "" + 10000001);

		// BufferedImage b =
		// tyt.compressImage("D:\\Users\\workspace-sts-3.7.1.RELEASE\\zb-web\\resources\\images\\cang.jpg",
		// 96, 111);
		// ImageIO.write(b, "png", new File(
		// "D:\\Users\\workspace-sts-3.7.1.RELEASE\\zb-web\\resources\\images\\small23.png"));

	}

	/***
	 * @param name
	 *            姓名
	 * @param sex
	 *            性别
	 * @param age
	 *            年龄
	 * @param tmpPath
	 *            背景图路径
	 * @param headPath
	 *            头像路径
	 * @param serialNumber
	 *            编号
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String name, String sex, String age, String tmpPath,
			String headPath, String serialNumber) throws IOException {
		int left = 470;
		int height = 54;

		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(left).buildVerticalOffset(height);
		BufferedImage nameBI = drawText(name, "微软雅黑", Font.BOLD, 18,
				Color.BLACK, 260);

		SimplePositions imageSP = new SimplePositions();
		imageSP.buildHorizontalOffset(571).buildVerticalOffset(52);
		BufferedImage imageBI = compressImage(headPath, 96, 111);

		SimplePositions sexSP = new SimplePositions();
		sexSP.buildHorizontalOffset(left).buildVerticalOffset(88);
		BufferedImage sexBI = drawText(sex, "微软雅黑", Font.BOLD, 18, Color.BLACK,
				260);

		SimplePositions ageSP = new SimplePositions();
		ageSP.buildHorizontalOffset(left).buildVerticalOffset(123);
		BufferedImage ageBI = drawText(age, "微软雅黑", Font.BOLD, 18, Color.BLACK,
				260);

		SimplePositions snameSP = new SimplePositions();
		snameSP.buildHorizontalOffset(515 - name.length() * 9)
				.buildVerticalOffset(174);
		BufferedImage snameBI = drawText(name, "微软雅黑", Font.ITALIC, 16,
				Color.BLACK, 260);

		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		String date = "" + year + (month < 10 ? ".0" + month : "." + month)
				+ (day < 10 ? ".0" + day : "." + day);
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(515).buildVerticalOffset(369);
		BufferedImage dateBI = drawText(date, "微软雅黑", Font.ITALIC, 15,
				Color.BLACK, 260);

		SimplePositions numberSP = new SimplePositions();
		numberSP.buildHorizontalOffset(573).buildVerticalOffset(390);
		BufferedImage numberBI = drawText(serialNumber, "微软雅黑", Font.ITALIC,
				15, Color.BLACK, 260);
		
		InputStream in = null;
		BufferedImage bi = null;
		try{
			in = super.getFile(tmpPath);
			bi = Thumbnails
				.of(in)
				.scale(1.0d)
				.watermark(nameSP, nameBI, 1.0f)
				.watermark(sexSP, sexBI, 1.0f)
				.watermark(ageSP, ageBI, 1.0f)
				.watermark(snameSP, snameBI, 1.0f)
				.watermark(dateSP, dateBI, 1.0f)
				.watermark(imageSP, imageBI, 1.0f)
				.watermark(numberSP, numberBI, 1.0f).outputQuality(1.0d)
				.asBufferedImage();
		} finally {
			if(in != null){
				in.close();
			}
		}
		return super.saveFile(bi, getDefFormatName());
	}

}
