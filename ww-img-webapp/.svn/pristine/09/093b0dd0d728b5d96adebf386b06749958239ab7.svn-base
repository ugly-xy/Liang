package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import com.zb.service.cloud.StorageService;

import net.coobird.thumbnailator.Thumbnails;

public class YiMingTool extends BaseTool {

	public static void main(String[] args) throws IOException {
		String tmpPath = "http://imgzb.zhuangdianbi.com/56ebb82c93b0cf9c1b60caa4";

		if (tmpPath.contains("?")) {
			tmpPath = tmpPath.substring(0, tmpPath.indexOf("?"));
		}
		StorageService storageService = new StorageService();
		YiMingTool tyt = new YiMingTool(storageService);
		System.out.println(tyt.drawImg("鲍金莹", tmpPath));

	}
	
	public YiMingTool(StorageService storageService) {
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
		String[] fontStyles = {"罗西钢笔行楷","徐金如硬笔行楷X","新蒂下午茶基本版"};
		String fontStyle = fontStyles[new Random().nextInt(3)];
		Color color = new Color(76,76,76);
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(253).buildVerticalOffset(168);
		BufferedImage nameBI = drawText(name, fontStyle, Font.PLAIN, 30, color, 260);
		InputStream in = null;
		BufferedImage bi = null;
		try{
			in = super.getFile(tmpPath);
			bi = Thumbnails.of(in).scale(1.0d).watermark(nameSP, nameBI, 1.0f)
					.outputQuality(1.0d).outputFormat("png").asBufferedImage();	
		} finally {
			if(in != null){
				in.close();
			}
		}
		return super.saveFile(bi, getDefFormatName());
	}

}
