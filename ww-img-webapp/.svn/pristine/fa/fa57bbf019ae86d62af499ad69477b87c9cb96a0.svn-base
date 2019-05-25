package com.zb.service.image.love;

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

public class TShirtBiaoBaiTool extends BaseTool {

	public TShirtBiaoBaiTool(StorageService storageService) {
		super(storageService);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		String t0 = "http://imgzb.zhuangdianbi.com/574553eb0cf243af7a4f75bc";
		String t1 = "http://imgzb.zhuangdianbi.com/574554200cf243af7a4f75ee";
		StorageService storageService = new StorageService();
		TShirtBiaoBaiTool tyt = new TShirtBiaoBaiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.drawImg("你是我的最爱", "fxg.png", STYLES[1], t1));
	}

	static String[] STYLES = { "女生", "男生" };
	static ZbFont f = ZbFont.土肥圆;
	static int fs = 20;
	static Color c = new Color(0, 0, 0);

	public String drawImg(String content, String pic, String style,
			String tmpPath) throws IOException {

		if (STYLES[0].equals(style)) {
			int left = 290 - 10 * content.length();
			SimplePositions contentSP = new SimplePositions();
			contentSP.buildHorizontalOffset(left).buildVerticalOffset(348);
			BufferedImage contentBi = drawText(content, f.font(), f.type(), fs,
					c, 360, 140, 0, true);
			SimplePositions picSP = new SimplePositions();
			picSP.buildHorizontalOffset(231).buildVerticalOffset(170);
			BufferedImage bi = super.compressImage(pic, 142, 176);
			GrayscaleFilter f = new GrayscaleFilter();
			bi = f.filter(bi, null);
			FieldWarpFilter fwf = new FieldWarpFilter();
			FieldWarpFilter.Line[] ins = {
					new FieldWarpFilter.Line(0, 33, 0, 63),
					new FieldWarpFilter.Line(3, 0, 39, 0),
					new FieldWarpFilter.Line(142, 34, 142, 65) };
			FieldWarpFilter.Line[] ots = {
					new FieldWarpFilter.Line(1, 33, 3, 63),
					new FieldWarpFilter.Line(4, 3, 40, 8),
					new FieldWarpFilter.Line(143, 33, 139, 66) };
			fwf.setAmount(0.9f);
			fwf.setInLines(ins);
			fwf.setOutLines(ots);
			bi = fwf.filter(bi, null);
			SimplePositions[] sp = { contentSP, picSP };
			BufferedImage[] bis = { contentBi, bi };
			return super.getSaveFile(sp, bis, 0.8f, tmpPath);
		} else {
			int left = 305 - 10 * content.length();

			SimplePositions contentSP = new SimplePositions();
			contentSP.buildHorizontalOffset(left).buildVerticalOffset(310);
			BufferedImage contentBi = drawText(content, f.font(), f.type(), fs,
					c, 360, 140, 0, true);

			SimplePositions picSP = new SimplePositions();
			picSP.buildHorizontalOffset(234).buildVerticalOffset(125);

			BufferedImage bi = super.compressImage(pic, 156, 176);
			GrayscaleFilter f = new GrayscaleFilter();
			bi = f.filter(bi, null);

			FieldWarpFilter fwf = new FieldWarpFilter();
			FieldWarpFilter.Line[] ins = {
					new FieldWarpFilter.Line(0, 33, 0, 63),
					new FieldWarpFilter.Line(3, 0, 39, 0),
					new FieldWarpFilter.Line(142, 34, 142, 65) };
			FieldWarpFilter.Line[] ots = {
					new FieldWarpFilter.Line(1, 33, 3, 63),
					new FieldWarpFilter.Line(4, 3, 40, 6),
					new FieldWarpFilter.Line(143, 33, 139, 66) };
			fwf.setAmount(0.7f);
			fwf.setInLines(ins);
			fwf.setOutLines(ots);
			bi = fwf.filter(bi, null);
			SimplePositions[] sp = { contentSP, picSP };
			BufferedImage[] bis = { contentBi, bi };
			return super.getSaveFile(sp, bis, 0.8f, tmpPath);
		}
	}

}
