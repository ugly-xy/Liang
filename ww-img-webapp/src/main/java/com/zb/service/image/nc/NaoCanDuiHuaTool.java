package com.zb.service.image.nc;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;

public class NaoCanDuiHuaTool extends BaseTool {

	public NaoCanDuiHuaTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String p0 = "http://imgzb.zhuangdianbi.com/5729bb660cf2a444f8eab61a";
		String p1 = "http://imgzb.zhuangdianbi.com/5729c43e0cf2a444f8eabb0f";
		String p2 = "http://imgzb.zhuangdianbi.com/5729c50f0cf2a444f8eabbaf";
		StorageService storageService = new StorageService();
		NaoCanDuiHuaTool tyt = new NaoCanDuiHuaTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.drawImg(new String[] { "你傻逼", "你是傻逼",
				"世上有一个傻逼就是你就是你就是你重要的事情我说扁", "世上有一个傻逼就是你就是你就是你重要的事情我说扁你是傻逼" },
				p2, 4));
	}

	// private final static String[] Names = { "苍井空", "黄子韬", "陈冠希", "鹿晗" };

	private final static int FontSizes = 28;

	private final static ZbFont Fs = ZbFont.微软雅黑加粗;

	/***
	 * @param name
	 *            姓名
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String[] talks, String tmpPath, int count)
			throws IOException {
		BufferedImage tbi = super.getImg(tmpPath);
		int th = tbi.getHeight();
		int w = tbi.getWidth();
		int eh = th / count;
		int maxw = w - 60;
		SimplePositions[] sp = new SimplePositions[talks.length];
		BufferedImage[] bis = new BufferedImage[talks.length];
		for (int i = 0; i < talks.length; i++) {
			int curLen = (FontSizes) * talks[i].length();
			int curLeft = 30;
			int curH = (i + 1) * eh - FontSizes * 2;
			if (curLen < maxw) {
				curLeft = curLeft + (maxw - curLen) / 2;
			} else if (curLen > maxw) {
				int line = curLen % maxw == 0 ? curLen / maxw : curLen / maxw
						+ 1;
				curH = (i + 1) * eh - FontSizes * (line + 1);
			}

			SimplePositions signSP = new SimplePositions();
			signSP.buildHorizontalOffset(curLeft).buildVerticalOffset(curH);
			bis[i] = super.drawText(talks[i], Fs.font(), Fs.type(), FontSizes,
					Color.WHITE, maxw);
			sp[i] = signSP;

		}

		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}

}
