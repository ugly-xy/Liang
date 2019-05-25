package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import com.zb.service.cloud.StorageService;

import net.coobird.thumbnailator.Thumbnails;

public class MingXingTool extends BaseTool {

	public static void main(String[] args) throws IOException {
//		String tmpPath = "/16/0314/15/1457941981183223.png?v=1457941994125";
		String tmpPath = "/16/0315/13/1458019921894953.png?v=1458019930528";
		if (tmpPath.contains("?")) {
			tmpPath = tmpPath.substring(0, tmpPath.indexOf("?"));
		}
		StorageService storageService = new StorageService();
		MingXingTool tyt = new MingXingTool(storageService);
		System.out.println(tyt.drawImg("张涛","权志龙","恭喜你，打败壮壮，赢得胜利！加油！",tmpPath));

	}
	
	public MingXingTool(StorageService storageService) {
		super(storageService);
	}
	
	/***
	 * 获取文字效果信息，位置，大小，颜色
	 * @param type
	 * @return x,y,size,颜色三原色码
	 */
	private int[][] getPosition(String type){
		if("鹿晗".equalsIgnoreCase(type)){
			int[][] x = {{100,590,42,43,46,65},{150,690,34,43,46,65,540}};
			return x;
		}else if("权志龙".equalsIgnoreCase(type)){
			int[][] x = {{311,334,34,74,85,69},{345,392,28,74,85,69,228}};
			return x;
		}
		return null;
	}
	
	/***
	 * 
	 * @param name 姓名
	 * @param type "luhan","quanzhilong"
	 * @param content 内容
	 * @param tmpPath
	 * @return
	 * @throws IOException
	 */
	public String drawImg(String name, String type, String content, String tmpPath) throws IOException {
		int[][] p = this.getPosition(type); 
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(p[0][0]).buildVerticalOffset(p[0][1]);
		BufferedImage nameBI = drawText(name, "微软雅黑", Font.PLAIN, p[0][2],
				new Color(p[0][3],p[0][4],p[0][5]), 300);
		
		SimplePositions contentSP = new SimplePositions();
		contentSP.buildHorizontalOffset(p[1][0]).buildVerticalOffset(p[1][1]);
		BufferedImage contentBI = drawText(content, "微软雅黑", Font.PLAIN, p[1][2],
				new Color(p[1][3],p[1][4],p[1][5]), p[1][6]);
		InputStream in = null;
		BufferedImage bi = null;
		try{
			in = super.getFile(tmpPath);
			bi = Thumbnails
				.of(in).scale(1.0d)
				.watermark(nameSP, nameBI, 1.0f)
				.watermark(contentSP, contentBI, 1.0f)
				.outputQuality(1.0d).asBufferedImage();
		} finally {
			if(in != null){
				in.close();
			}
		}
		return super.saveFile(bi, getDefFormatName());
	}

	

}
