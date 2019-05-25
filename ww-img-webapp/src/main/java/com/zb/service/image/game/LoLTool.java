package com.zb.service.image.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;

import net.coobird.thumbnailator.Thumbnails;

public class LoLTool extends BaseTool {

	public LoLTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		// String tmpPath = "/16/0314/15/1457941981183223.png?v=1457941994125";
		// String tmpPath = "/16/0314/15/1457941877415779.png?v=1457941963210";
		// String tmpPath = "/16/0314/16/1457945307159429.png?v=1457945314685";
		String tmpPath = "/16/0314/17/1457946752508709.png?v=1457946780450";
		if (tmpPath.contains("?")) {
			tmpPath = tmpPath.substring(0, tmpPath.indexOf("?"));
		}
		// LoLTool tyt = new LoLTool();
		// tyt.drawImg("笑着杀死你","pifu",tmpPath);

	}

	/***
	 * 获取文字效果信息，位置，大小，颜色
	 * 
	 * @param type
	 * @return x,y,size,颜色三原色码
	 */
	private int[] getPosition(String type) {
		if ("封号".equalsIgnoreCase(type)) {
			int[] x = { 43, 62, 18, 255, 255, 255 };
			return x;
		} else if ("超凡大师".equalsIgnoreCase(type)) {
			int[] x = { 68, 204, 9, 56, 53, 44 };
			return x;
		} else if ("皮肤".equalsIgnoreCase(type)) {
			int[] x = { 50, 44, 12, 109, 109, 109 };
			return x;
		} else if ("最强王者".equalsIgnoreCase(type)) {
			int[] x = { 460, 50, 14, 173, 186, 181 };
			return x;
		}
		return null;
	}

	/***
	 * 
	 * @param name
	 *            LOL昵称
	 * @param type
	 *            "fenghao","chaofan","pifu","wangzhe"
	 * @param tmpPath
	 * @return
	 * @throws IOException
	 */
	public String drawImg(String name, String type, String tmpPath)
			throws IOException {
		int[] p = this.getPosition(type);
		int left = p[0], height = p[1], fontSize = p[2];
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(left).buildVerticalOffset(height);
		BufferedImage nameBI = drawText(name, "微软雅黑", Font.BOLD, fontSize,
				new Color(p[3], p[4], p[5]), 260);
		InputStream in = null;
		BufferedImage bi = null;
		try{
			in = super.getFile(tmpPath);
			bi = Thumbnails.of(in).scale(1.0d)
				.watermark(nameSP, nameBI, 1.0f).outputQuality(1.0d)
				.asBufferedImage();
		} finally {
			if(in != null){
				in.close();
			}
		}
		return super.saveFile(bi, getDefFormatName());
	}

}
