package com.zb.service.image.love;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jhlabs.image.*;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;

public class ShiZiLuKouTool extends BaseTool {

	public ShiZiLuKouTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String p0 = "http://imgzb.zhuangdianbi.com/573d95280cf2754ef3f0fa54";// http://imgzb.zhuangdianbi.com/573c0f0a0cf2cc7298e6baaf";// http://imgzb.zhuangdianbi.com/573c077d0cf2cc7298e6b738";
		StorageService storageService = new StorageService();
		ShiZiLuKouTool tyt = new ShiZiLuKouTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.drawImg("装點", "我爱你", p0));
	}

	// private final static String[] Names = { "美女锁骨", "美女后背", "美女后肩", "帅哥蝎子",
	// "帅哥五线谱", "帅哥鬼面" };

	private final static int fontSize = 56;

	private final static Color color = new Color(171, 206, 200);

	private final static ZbFont Fs = ZbFont.黑体加粗;

	/***
	 * @param name
	 *            姓名
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String name, String content, String tmpPath)
			throws IOException {
		if (name.length() == 2) {
			name = name.charAt(0) + "❤" + name.charAt(1);
		} else if (name.length() == 1) {
			name = "❤" + name + "❤";
		}
		name = name + "\r" + content;
		// Font f = new Font(Fs.font(), Fs.type(), fontSize);
		int textWeight = 240;
		int textHeight = 160;

		SimplePositions fromSP = new SimplePositions();
		fromSP.buildHorizontalOffset(95).buildVerticalOffset(136);
		// BufferedImage bi = drawText(name, Fs.font(), Fs.type(), fontSize,
		// color, textWeight, textHeight, 0, true);

		Font f = new Font(Fs.font(), Fs.type(), fontSize);

		// System.out.println("sss:" + textHeight);
		BufferedImage buffImg = GC.createCompatibleImage(textWeight,
				textHeight, Transparency.TRANSLUCENT);
		Graphics2D graphics = buffImg.createGraphics();
		int left = 31;
		int top = 20;
		graphics.clipRect(left, top, textWeight - 2, textHeight - 2);
		graphics.setColor(color);
		graphics.setFont(f);
		// graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);

		FontMetrics fm = graphics.getFontMetrics(f);
		int fontHeight = fm.getHeight() - 10;
		int offsetLeft = left;
		int rowIndex = 1;
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			int charWidth = fm.charWidth(c); // 字符的宽度
			// 另起一行
			if (Character.isISOControl(c)) {
				rowIndex++;
				offsetLeft = left;
				continue;
			}
			if (offsetLeft >= (textWeight - charWidth)) {
				rowIndex++;
				offsetLeft = left;
			}
			graphics.drawString(String.valueOf(c), offsetLeft, rowIndex
					* fontHeight + top);
			offsetLeft += charWidth + 10;
		}
		BufferedImage picBi = null;

		PerspectiveFilter pf = new PerspectiveFilter();
		pf.setCorners(40, 0, buffImg.getWidth() - 1, 0, buffImg.getWidth(),
				buffImg.getHeight() - 20, -50, buffImg.getHeight() - 33);
		picBi = pf.filter(buffImg, null);

		// FieldWarpFilter fwf = new FieldWarpFilter();
		// FieldWarpFilter.Line[] ins = {
		// new FieldWarpFilter.Line(left, 0, 0, textHeight),
		// new FieldWarpFilter.Line(textWeight - left, 0, textWeight,
		// textHeight) };
		// FieldWarpFilter.Line[] ots = {
		// new FieldWarpFilter.Line(left, 0, -40, textHeight),
		// new FieldWarpFilter.Line(textWeight - left, 0, textWeight,
		// textHeight ) };
		// // fwf.setAmount(0.7f);
		// fwf.setInLines(ins);
		// fwf.setOutLines(ots);
		// picBi = fwf.filter(buffImg, null);

		RotateFilter rf = new RotateFilter();
		rf.setAngle(-0.18f);
		picBi = rf.filter(picBi, null);

		SimplePositions[] sp = { fromSP };
		BufferedImage[] bis = { picBi };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);

	}

}
