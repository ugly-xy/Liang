package com.zb.service.image.tuhao;

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

public class QianDaDaDeTool extends BaseTool {

	public QianDaDaDeTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String t0 = "http://imgzb.zhuangdianbi.com/574e60670cf2c92990ad0613";
		String t1 = "http://imgzb.zhuangdianbi.com/574e60ac0cf2c92990ad0699";
		StorageService storageService = new StorageService();
		QianDaDaDeTool tyt = new QianDaDaDeTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.drawImg("装点逼", "零花钱放在这里了，宝宝，下次一定陪你一起玩！", "爱装逼",
				STYLES[1], t1));
	}

	static final String[] STYLES = { "一箱钱", "一摞钱" };
	static final ZbFont f = ZbFont.钟齐陈伟勋硬笔行书字库;
	Color color = new Color(0, 0, 0);
	int[] fs = { 28, 20 };

	static final int[][] lefts = { { 300, 300, 490 }, { 145, 145, 320 } };
	static final int[][] tops = { { 570, 0, 0 }, { 390, 0, 0 } };
	static final double[] thetas = { 0, -0.1 };
	static final int[] maxs = { 340, 260 };
	static final double[] hs = { 1.3, 0.86 };

	public String drawImg(String to, String content, String from, String style,
			String tmpPath) throws IOException {
		int idx = 0;
		for (int i = 0; i < STYLES.length; i++) {
			if (STYLES[i].equals(style)) {
				idx = i;
				break;
			}
		}

		int max = maxs[idx];

		// 接受人
		SimplePositions toSP = new SimplePositions();
		toSP.buildHorizontalOffset(lefts[idx][0]).buildVerticalOffset(
				tops[idx][0]);
		BufferedImage toBI = drawText(to, f, fs[idx], color, max, 0,
				thetas[idx], true);

		// 内容
		content = "    " + content;

		int t = idx * 22;
		int top = (int) (tops[idx][0] + fs[idx] * hs[idx] - t);
		SimplePositions contentSP = new SimplePositions();
		contentSP.buildHorizontalOffset(lefts[idx][1]).buildVerticalOffset(top);
		BufferedImage contentBI = drawText(content, f, fs[idx], color, max, 0,
				thetas[idx], true);

		// 赠送人
		int w = fs[idx] * content.length();
		int lines = 1;
		if (w > max) {
			lines = w % max == 0 ? w / max : w / max + 1;
		}
		top = (int) (top + lines * fs[idx] * hs[idx]) + t;
		SimplePositions fromSP = new SimplePositions();
		fromSP.buildHorizontalOffset(lefts[idx][2]).buildVerticalOffset(top);
		BufferedImage fromBI = drawText(from, f, fs[idx], color, max, 0,
				thetas[idx], true);

		SimplePositions[] sp = { toSP, contentSP, fromSP };

		BufferedImage[] bis = { toBI, contentBI, fromBI };

		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}
}
