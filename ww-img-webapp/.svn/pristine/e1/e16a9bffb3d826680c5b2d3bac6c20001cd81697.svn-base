package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


import com.zb.service.cloud.StorageService;

import net.coobird.thumbnailator.Thumbnails;

public class JiPiaoTool extends BaseTool {

	public static void main(String[] args) throws IOException {
		String tmpPath = "http://imgzb.zhuangdianbi.com/56ebb40693b0cf9c1b60ca9e";

		if (tmpPath.contains("?")) {
			tmpPath = tmpPath.substring(0, tmpPath.indexOf("?"));
		}
		StorageService storageService = new StorageService();
		JiPiaoTool tyt = new JiPiaoTool(storageService);
		System.out.println(tyt.drawImg("BAO WENKE", tmpPath));

	}
	
	public JiPiaoTool(StorageService storageService) {
		super(storageService);
		// TODO Auto-generated constructor stub
	}
	
	
	/***
	 * 
	 * @param name
	 *            姓名
	 * @param content 内容
	 * @param tmpPath
	 * @return
	 * @throws IOException
	 */
	public String drawImg(String name, String tmpPath) throws IOException {
		String fontStyle = "黑体";
		Color color = new Color(90,88,81);
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(94).buildVerticalOffset(222);
		BufferedImage nameBI = drawText(name, fontStyle, Font.BOLD, 14, color, 260);
		//nameBI = doTwist(nameBI, 3, 2.5, true);
//		 AffineTransform affineTransform= new AffineTransform();;
//		 double shearx = 1.0, sheary = 1.0;
//		affineTransform.setToShear(shearx, sheary);
//		  AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, null);
//		  bufferedImage
//		  Graphics2D G2D =  bufferedImage2.createGraphics();
//	        G2D.clearRect(0, 0, bufferedImage2.getWidth(this), bufferedImage2.getHeight(this));
	        
		InputStream in = null;
		BufferedImage bi = null;
		try{
			in = super.getFile(tmpPath);
			bi = Thumbnails.of(in).scale(1.0d).watermark(nameSP, nameBI, 0.8f)
					.outputQuality(1.0d).outputFormat("png").asBufferedImage();
		} finally {
			if(in != null){
				in.close();
			}
		}
		return super.saveFile(bi, getDefFormatName());
	}

}
