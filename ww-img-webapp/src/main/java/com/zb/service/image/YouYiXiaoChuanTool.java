package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;

public class YouYiXiaoChuanTool extends BaseTool {

	public YouYiXiaoChuanTool(StorageService storageService) {
		super(storageService);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		String tmpPath = "http://imgzb.zhuangdianbi.com/570e32d40cf26eb28772440f";
		StorageService storageService = new StorageService();
		YouYiXiaoChuanTool tyt = new YouYiXiaoChuanTool(storageService);
		tyt.debug = true;
		tyt.drawImg("如果有一个方变胖变了", tmpPath);
	}

	/***
	 * @param ruguo
	 *            如果
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String ruguo, String tmpPath) throws IOException {
		int left = 140;
		int height = 400;
		if (ruguo.length() < 12) {
			left = left + (12 - ruguo.length()) * 16;
		} else if (ruguo.length() > 12) {
			int c = ruguo.length() % 12 == 0 ? ruguo.length()-1 / 12 : ruguo
					.length() / 12 ;
			height = height - 30 * c;
		}

		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(left).buildVerticalOffset(height);
		BufferedImage nameBI = drawText(ruguo, "微软雅黑", Font.PLAIN, 28,
				Color.BLACK, 360);
		SimplePositions[] sp = { nameSP };
		BufferedImage[] bis = { nameBI };
		return super.getSaveFile(sp, bis, 0.9f, tmpPath);
	}

}
