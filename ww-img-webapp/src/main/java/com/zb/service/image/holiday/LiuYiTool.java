package com.zb.service.image.holiday;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import com.jhlabs.image.FieldWarpFilter;
import com.jhlabs.image.GrayscaleFilter;
import com.jhlabs.image.PerspectiveFilter;
import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;

public class LiuYiTool extends BaseTool {

	public LiuYiTool(StorageService storageService) {
		super(storageService);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		String t0 = "http://imgzb.zhuangdianbi.com/574d7da80cf2e62a044d2450";// 曾经版
		String t1 = "http://imgzb.zhuangdianbi.com/574d7e170cf2e62a044d24c9";// 勿忘版
		String t2 = "http://imgzb.zhuangdianbi.com/574d7e2b0cf2e62a044d24d7";// 六一版
		StorageService storageService = new StorageService();
		LiuYiTool tyt = new LiuYiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.drawImg("喜羊羊", "小灰灰", STYLES[2], t2));
	}

	static String[] STYLES = { "曾经版", "勿忘版", "六一版" };
	static ZbFont f = ZbFont.新蒂小丸子小学版;
	static int fs = 20;
	static Color c = new Color(0, 0, 0);

	public String drawImg(String to, String from, String style, String tmpPath)
			throws IOException {
		SimplePositions picSP = null;
		BufferedImage bi = null;
		SimplePositions picSP2 = null;
		BufferedImage bi2 = null;

		if (STYLES[0].equals(style)) {
			picSP = new SimplePositions();
			picSP.buildHorizontalOffset(149).buildVerticalOffset(246);
			bi = super.drawText(to, f, fs, c, 300, 60, 0, true);
			picSP2 = new SimplePositions();
			picSP2.buildHorizontalOffset(317).buildVerticalOffset(620);
			bi2 = super.drawText(from, f, fs, c, 300, 60, 0, true);
		} else if (STYLES[1].equals(style)) {
			picSP = new SimplePositions();
			picSP.buildHorizontalOffset(114).buildVerticalOffset(114);
			bi = super.drawText(to, f, fs, c, 300, 60, 0, true);
			picSP2 = new SimplePositions();
			picSP2.buildHorizontalOffset(476).buildVerticalOffset(330);
			bi2 = super.drawText(from, f, fs, c, 300, 60, 0, true);
		} else {
			picSP = new SimplePositions();
			picSP.buildHorizontalOffset(326).buildVerticalOffset(96);
			bi = super.drawText(to, f, fs, c, 300, 60, 0, true);
			picSP2 = new SimplePositions();
			picSP2.buildHorizontalOffset(462).buildVerticalOffset(200);
			bi2 = super.drawText(from, f, fs, c, 300, 60, 0, true);
		}

		SimplePositions[] sp = { picSP, picSP2 };
		BufferedImage[] bis = { bi, bi2 };
		return super.getSaveFile(sp, bis, 1.0f, tmpPath);

	}

}
