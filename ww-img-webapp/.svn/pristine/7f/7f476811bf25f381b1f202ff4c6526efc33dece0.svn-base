package com.zb.service.image.spoof;

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

public class LvYouZhaoTool extends BaseTool {

	public LvYouZhaoTool(StorageService storageService) {
		super(storageService);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		String t0 = "http://imgzb.zhuangdianbi.com/5746fba10cf2801314ea28b2";//埃菲尔铁塔
		String t1 = "http://imgzb.zhuangdianbi.com/5747c2560cf2bd70c26ae03d";//比萨斜塔
		String t2 = "http://imgzb.zhuangdianbi.com/5747c2af0cf2bd70c26ae094";//罗马斗兽场
		String t3 = "http://imgzb.zhuangdianbi.com/5747c30a0cf2bd70c26ae0db";//狮身人面像
		String t4 = "http://imgzb.zhuangdianbi.com/5747c3490cf2bd70c26ae10e";//悉尼歌剧院
		String t5 = "http://imgzb.zhuangdianbi.com/5747c3500cf2bd70c26ae113";//自由女神像
		StorageService storageService = new StorageService();
		LvYouZhaoTool tyt = new LvYouZhaoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.drawImg("abc1.png", t0));
	}

	static String[] STYLES = { "巴黎铁搭", "男生" };
	static ZbFont f = ZbFont.土肥圆;
	static int fs = 20;
	static Color c = new Color(0, 0, 0);
	static String phone = "http://imgzb.zhuangdianbi.com/5746fc030cf2801314ea292e";

	public String drawImg(String pic, String tmpPath) throws IOException {

		SimplePositions picSP = new SimplePositions();
		picSP.buildHorizontalOffset(0).buildVerticalOffset(372);
		BufferedImage bi = super.getImg(phone);

		SimplePositions picSP2 = new SimplePositions();
		picSP2.buildHorizontalOffset(111).buildVerticalOffset(471);
		BufferedImage bi2 = super.compressImage(pic, 246, 306);

		PerspectiveFilter pf = new PerspectiveFilter();
		pf.setCorners(0, 0, 247, -2, 234, 307, 9, 304);
		bi2 = pf.filter(bi2, null);

		SimplePositions[] sp = { picSP, picSP2 };
		BufferedImage[] bis = { bi, bi2 };
		return super.getSaveFile(sp, bis, 1.0f, tmpPath);

	}

}
