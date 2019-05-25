package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import com.zb.service.cloud.StorageService;

import net.coobird.thumbnailator.Thumbnails;

public class YaoQingHanTool extends BaseTool {

	public static void main(String[] args) throws IOException {
		String tmpPath = "http://imgzb.zhuangdianbi.com/56ebcc9993b0cf9c1b60caae";

		if (tmpPath.contains("?")) {
			tmpPath = tmpPath.substring(0, tmpPath.indexOf("?"));
		}
		StorageService storageService = new StorageService();
		YaoQingHanTool tyt = new YaoQingHanTool(storageService);
		System.out.println(tyt.drawImg("赵鹏", tmpPath));
		

	}
	
	public YaoQingHanTool(StorageService storageService) {
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
	public String drawImg(String name, String tmpPath) throws IOException {
		String fontStyle = "微软雅黑";
		int fontSize = 30;
		Color color = new Color(69,81,90);
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(190).buildVerticalOffset(340);
		BufferedImage nameBI = drawText(name, fontStyle, Font.PLAIN, fontSize, color, 260);
		InputStream in = null;
		BufferedImage bi = null;
		try{
			in = super.getFile(tmpPath);
			bi = Thumbnails.of(in).scale(1.0d)
				.watermark(nameSP, nameBI, 0.85f)
				.outputQuality(1.0d).outputFormat("png").asBufferedImage();
		} finally {
			if(in != null){
				in.close();
			}
		}
		return super.saveFile(bi, getDefFormatName());
	}

}
