package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;

public class YanZhiTool extends BaseTool {

	public YanZhiTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String t0 = "http://imgzb.zhuangdianbi.com/57501ff70cf28eea25311406";
		StorageService storageService = new StorageService();
		YanZhiTool tyt = new YanZhiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.drawImg("123a.jpg", "0000001", t0));
	}

	static final String[] S1 = { "贼眉鼠眼", "一表蠢才", "庞眉皓发", "鼻青脸肿", "令人作呕",
			"一大坨屎", "灰头土脸", "衣不蔽体", "邋遢成性", "魑魅魍魉", "口是心非", "自以为是", "朝三暮四",
			"恶贯满盈", "子虚乌有" };
	static final String[] S2 = { "不同流俗", "暗室不欺", "深恶痛疾", "狗嘴象牙", "狐假虎威",
			"阴险狡诈", "好吃懒做", "爱慕虚荣", "财迷心窍", "纵虎归山", "粉墨登场", "穷形象相", "独善其身",
			"绵里藏针", "心怀鬼胎" };
	static final String[] S3 = { "酒色财气", "八面玲珑", "盛气凌人", "趾高气扬", "一掷千金",
			"风华正茂", "蛾眉皓齿", "沉鱼落雁", "出水芙蓉", "齿如齐贝", "冰肌玉骨", "闭月羞花", "一表人才",
			"艳如桃李", "一顾倾城", "一顾倾城", "天生丽质", "倾国倾城", "龙眉凤目", "绝代佳人", "才高八斗",
			"文武双全", "才思敏捷", "见多识广", "出类拔萃", "发愤图强" };
	static final String[] S4 = { "沉鱼落雁", "闭月羞花", "善解人意", "韩笑盈盈", "炯炯有神",
			"一身正气", "和蔼可亲", "眉清目秀", "不骄不躁", "力争上游", "闻鸡起舞", "良金美玉", "高情远致",
			"风华正茂", "盖世无双", "一代风流", "风云人物", "宽大为怀", "功成名就", "顶天立地", "志在四方",
			"大显身手", "海枯石烂", "威武不屈", "一寸丹心", "必有一得", "两袖清风", "正大光明", "浩然之气",
			"眉开眼笑", "目若秋水", "朱唇素手", "秀外慧中", "心旷神怡", "温婉可人", "谈笑风声", "孜孜不倦",
			"长相骏雅", "高谈阔论", "完美无缺", "雄韬伟略", "秀而不媚", "优雅大方", "端庄优雅", "意气风发",
			"神采奕奕", "气似幽兰" };
	static final String[] LOGO = { "狂拽吊炸天", "奔放老死机", "简约国际范", "矫情无下限", "卖萌剪刀手",
			"洋气有深度", "奢华有内涵", "乡村非主流", "贵族杀马特", "深沉无所谓", "高贵接地气", "高端上档次",
			"团战必须死", "装逼无节操", "懵逼小屌丝", "坑爹矮穷矬" };

	static final String point = "http://imgzb.zhuangdianbi.com/575020a20cf28eea25311502";

	static final String[] BLOCKS = {
			"http://imgzb.zhuangdianbi.com/575020760cf28eea253114ba",
			"http://imgzb.zhuangdianbi.com/575020400cf28eea2531146c",
			"http://imgzb.zhuangdianbi.com/575020520cf28eea25311484",
			"http://imgzb.zhuangdianbi.com/575020650cf28eea2531149d",
			"http://imgzb.zhuangdianbi.com/5750202a0cf28eea25311451" };
	static final ZbFont f = ZbFont.苹方粗;
	Color color = new Color(255, 255, 255);
	Color color2 = Color.red;
	Color color3 = new Color(255, 88, 88);
	int fs = 42;

	static final DecimalFormat df1 = new DecimalFormat("##.00%");
	static final String ink = "http://imgzb.zhuangdianbi.com/575020870cf28eea253114d8";

	public String drawImg(String myPic, String use, String tmpPath)
			throws IOException {

		Random r = ThreadLocalRandom.current();

		SimplePositions inkSP = new SimplePositions();
		inkSP.buildHorizontalOffset(465).buildVerticalOffset(85);
		BufferedImage inkBI = super.getImg(ink);

		SimplePositions inkTSP = new SimplePositions();
		inkTSP.buildHorizontalOffset(450).buildVerticalOffset(110);
		BufferedImage inkTBI = drawText(LOGO[r.nextInt(LOGO.length)], f.font(),
				f.type(), fs + 4, color3, 400, 200, -0.38, true, 50, 50);

		SimplePositions pointSP = new SimplePositions();
		pointSP.buildHorizontalOffset(114).buildVerticalOffset(633);
		BufferedImage pointBI = super.getImg(point);

		SimplePositions picSP = new SimplePositions();
		picSP.buildHorizontalOffset(120).buildVerticalOffset(154);
		BufferedImage picBI = super.compressImage(myPic, 483, 545);

		int max = 100;
		int v = r.nextInt(max);
		String[] S = null;
		double d = 0;
		int ps = 0;
		if (v < 10) {
			S = S1;
			ps = r.nextInt(41);

			d = ps / (100.0 + r.nextInt(10) - 5);
		} else if (v < 20) {
			S = S2;
			ps = r.nextInt(31) + 40;
			d = ps / (100.0 + r.nextInt(8) - 4);
			;
		} else if (v < 50) {
			S = S3;
			ps = r.nextInt(21) + 70;
			d = ps / (100.0 + r.nextInt(4) - 2);
			;
		} else {
			ps = r.nextInt(11) + 90;
			S = S4;
			d = ps * 100 / (10000.0 + r.nextInt(4));
		}

		SimplePositions useSP = new SimplePositions();
		useSP.buildHorizontalOffset(335).buildVerticalOffset(1200);
		BufferedImage useBI = drawText(use, f, 50, color2, 400, 0, 0, true);

		// 分数
		SimplePositions toSP = new SimplePositions();
		toSP.buildHorizontalOffset(400).buildVerticalOffset(585);
		BufferedImage toBI = drawText("" + ps, ZbFont.CAI167, 155, color2, 400,
				0, 0, true);

		String point = df1.format(d);
		SimplePositions fromSP = new SimplePositions();
		fromSP.buildHorizontalOffset(340).buildVerticalOffset(780);
		BufferedImage fromBI = drawText(point, ZbFont.CAI167, fs + 8, color2,
				400, 0, 0, true);

		SimplePositions[] tagsSP = new SimplePositions[5];
		BufferedImage[] tagsBI = new BufferedImage[5];

		SimplePositions[] ttagsSP = new SimplePositions[5];
		BufferedImage[] ttagsBI = new BufferedImage[5];

		int left = 0;
		int top = 0;
		String[] B = BLOCKS;
		for (int i = 0; i < 5; i++) {
			int m = r.nextInt(B.length);
			int n = r.nextInt(B.length);
			String t = B[m];
			B[m] = B[n];
			B[n] = t;
			if (i < 2) {
				top = 920;
				left = 170 + 210 * i;
			} else {
				top = 1010;
				left = 65 + 210 * (i - 2);
			}

			tagsSP[i] = new SimplePositions();
			tagsSP[i].buildHorizontalOffset(left).buildVerticalOffset(top);
			tagsBI[i] = drawText(S[r.nextInt(S.length)], f, fs - 2, color,
					1000, 0, 0, true);
		}

		for (int i = 0; i < 5; i++) {
			if (i < 2) {
				top = 908;
				left = 156 + 211 * i;
			} else {
				top = 998;
				left = 50 + 211 * (i - 2);
			}
			ttagsSP[i] = new SimplePositions();
			ttagsSP[i].buildHorizontalOffset(left).buildVerticalOffset(top);
			ttagsBI[i] = super.getImg(B[i]);
		}

		SimplePositions[] sp = { picSP, pointSP, toSP, fromSP, ttagsSP[0],
				ttagsSP[1], ttagsSP[2], ttagsSP[3], ttagsSP[4], tagsSP[0],
				tagsSP[1], tagsSP[2], tagsSP[3], tagsSP[4], inkSP, inkTSP,
				useSP };

		BufferedImage[] bis = { picBI, pointBI, toBI, fromBI, ttagsBI[0],
				ttagsBI[1], ttagsBI[2], ttagsBI[3], ttagsBI[4], tagsBI[0],
				tagsBI[1], tagsBI[2], tagsBI[3], tagsBI[4], inkBI, inkTBI,
				useBI };

		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}

}
