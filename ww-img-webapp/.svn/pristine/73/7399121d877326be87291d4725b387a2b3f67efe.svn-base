package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class KaoShiQiuQianTool extends BaseTool implements OneDraw {

	public KaoShiQiuQianTool(StorageService storageService) {
		super(storageService);
	}

	public KaoShiQiuQianTool() {
	}

	public static void main(String[] args) throws IOException {
		String p0 = "http://imgzb.zhuangdianbi.com/57557a530cf23cb774470794";
		StorageService storageService = new StorageService();
		KaoShiQiuQianTool tyt = new KaoShiQiuQianTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.draw("装点逼", p0,null));
	}

	static String[] daxue = { "北京大学", "清华大学", "复旦大学", "武汉大学", "浙江大学", "南京大学",
			"中山大学", "吉林大学", "四川大学", "南开大学", "中南大学", "同济大学", "天津大学", "山东大学",
			"厦门大学", "东南大学", "蓝翔技校", "重庆大学", "香港大学" };

	static String[] s = { "高步云衢", "首屈一指", "连战皆捷", "人镜芙蓉", "朱衣点额", "得意门生",
			"秉烛夜读", "异路功名", "独占鳌头", "锲而不舍", "春风报罢", "开科取士", "天子门生", "大魁天下",
			"及第成名", "出类拔萃", "超群绝伦", "鹤立鸡群", "连中三元", "昆山片玉" };

	ZbFont f = ZbFont.华文隶体;

	@Override
	public String draw(String one, String tmpPath,String count) throws IOException {

		SimplePositions oneSP = new SimplePositions();
		oneSP.buildHorizontalOffset(342).buildVerticalOffset(890);
		BufferedImage oneBI = drawText(one, f, 52, Color.black, 300, 100, 0,
				true);
		Random r = ThreadLocalRandom.current();
		String tag1 = daxue[r.nextInt(daxue.length)];
		tag1 = tag1 + "    " + s[r.nextInt(s.length)];
		String tag2 = s[r.nextInt(s.length)] + "    " + s[r.nextInt(s.length)];

		SimplePositions tagSP = new SimplePositions();
		tagSP.buildHorizontalOffset(281).buildVerticalOffset(698);
		BufferedImage tagBI = drawText(tag1, f, 32, Color.red, 300, 200, 0,
				true);

		SimplePositions tagSP2 = new SimplePositions();
		tagSP2.buildHorizontalOffset(281).buildVerticalOffset(778);
		BufferedImage tagBI2 = drawText(tag2, f, 32, Color.red, 300, 200, 0,
				true);

		SimplePositions[] sp = { oneSP, tagSP, tagSP2 };
		BufferedImage[] bis = { oneBI, tagBI, tagBI2 };
		return super.getSaveFile(sp, bis, 0.85f, tmpPath);
	}
}
