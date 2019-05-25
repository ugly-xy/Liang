package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Random;


import com.zb.service.cloud.StorageService;

import net.coobird.thumbnailator.Thumbnails;

public class FangChanZhengTool extends BaseTool {

	public static void main(String[] args) throws IOException {
//		String tmpPath = "http://imgzb.zhuangdianbi.com/56ea75d393b03adfb7a20e9e";
		String tmpPath = "http://imgzb.zhuangdianbi.com/56ea762093b03adfb7a20ea1";

		if (tmpPath.contains("?")) {
			tmpPath = tmpPath.substring(0, tmpPath.indexOf("?"));
		}
		StorageService storageService = new StorageService();
		FangChanZhengTool tyt = new FangChanZhengTool(storageService);
		System.out.println(tyt.drawImg("赵鹏","订单", tmpPath));

	}
	
	public FangChanZhengTool(StorageService storageService) {
		super(storageService);
		// TODO Auto-generated constructor stub
	}
	
	/***
	 * 获取文字效果信息，位置，大小，颜色
	 * @param type
	 * @return x,y,size,颜色三原色码
	 */
	private int[] getPosition(String type){
		if("订单".equalsIgnoreCase(type)){
			int[] x = { 295, 890, 20, 387, 906, 67, 61, 58, 525, 283 };
			return x;
		}else if("房本".equalsIgnoreCase(type)){
			int[] x = { 154, 66, 16, 150, 181, 131, 127, 121};
			return x;
		}
		return null;
	}
	
	
	/***
	 * 
	 * @param name
	 *            姓名
	 * @param tmpPath
	 * @return
	 * @throws IOException
	 */
	public String drawImg(String name, String type, String tmpPath) throws IOException {
		int[] p = this.getPosition(type);
		int fontSize = p[2];
		String[] fontStyles = {"罗西钢笔行楷","徐金如硬笔行楷X","新蒂下午茶基本版"};
		String nameStyle = "订单".equals(type)?fontStyles[new Random().nextInt(3)]:"微软雅黑";
		String numberStyle = "订单".equals(type)?fontStyles[new Random().nextInt(2)]:"微软雅黑";
		
		Color color = new Color(p[5],p[6],p[7]);
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(p[0]).buildVerticalOffset(p[1]);
		BufferedImage nameBI = drawText(name, nameStyle, Font.PLAIN, fontSize, color, 260);

		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		String date = "" + year + (month < 10 ? ".0" + month : "." + month) + (day < 10 ? ".0" + day : "." + day);
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(p[3]).buildVerticalOffset(p[4]);
		BufferedImage dateBI = drawText(date, numberStyle, Font.PLAIN, 15, color, 260);
		
		BufferedImage bi = null;
		InputStream in = null;
		try{
			in = super.getFile(tmpPath);
			if("订单".equals(type)){
				SimplePositions signSP = new SimplePositions();
				signSP.buildHorizontalOffset(p[8]).buildVerticalOffset(p[9]);
				BufferedImage signBI = drawText(name, nameStyle, Font.BOLD, fontSize, color, 260);
				bi = Thumbnails.of(in).scale(1.0d).watermark(nameSP, nameBI, 1.0f)
						.watermark(dateSP, dateBI, 1.0f).watermark(signSP, signBI, 1.0f)
						.outputQuality(1.0d).outputFormat("png").asBufferedImage();
			}else if("房本".equals(type)){
				bi = Thumbnails.of(in).scale(1.0d).watermark(nameSP, nameBI, 1.0f)
						.watermark(dateSP, dateBI, 1.0f)
						.outputQuality(1.0d).outputFormat("png").asBufferedImage();
			}
		} finally {
			if(in != null){
				in.close();
			}
		}
		return super.saveFile(bi, getDefFormatName());
	}

}
