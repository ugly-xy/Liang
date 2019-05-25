package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import com.zb.common.utils.RegexUtil;
import com.zb.service.cloud.StorageService;

import net.coobird.thumbnailator.Thumbnails;

public class DuoShouDangTool extends BaseTool {

	public static void main(String[] args) throws IOException {
		String tmpPath = "http://imgzb.zhuangdianbi.com/56eff3f80cf216b95344c52d";

		if (tmpPath.contains("?")) {
			tmpPath = tmpPath.substring(0, tmpPath.indexOf("?"));
		}
		StorageService storageService = new StorageService();
		DuoShouDangTool tyt = new DuoShouDangTool(storageService);
		System.out.println(tyt.drawImg("1", "56534576.4565", "98", tmpPath));

	}

	public DuoShouDangTool(StorageService storageService) {
		super(storageService);
		// TODO Auto-generated constructor stub
	}

	/***
	 * 
	 * @param name
	 *            姓名
	 * @param tmpPath
	 * @return
	 * @throws IOException
	 */
	public String drawImg(String date, String money, String per, String tmpPath)
			throws IOException {
		// String[] fontStyles = {"罗西钢笔行楷","徐金如硬笔行楷X","新蒂下午茶基本版"};
		String fontStyle = "Helvetica Neue";
		Color color = new Color(219, 14, 20);
		// 时间
		int fontSize = 40;
		if (!RegexUtil.isInteger(date) || date.length() >= 2) {
			int year = new Random().nextInt(8) + 1;
			date = "" + year;
		}
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(151).buildVerticalOffset(526);
		BufferedImage dateBI = drawText(date, fontStyle, Font.BOLD, fontSize,
				color, 260);

		// 花钱多少
		if (!RegexUtil.isNumeric(money)) {
			money = "634568.68";
		} else if (money.contains(".")) {
			String s = money.substring(money.indexOf(".") + 1);
			if (s.length() < 2) {
				int i = new Random().nextInt(8) + 1;
				money = money + "" + i;
			} else {
				money = money.substring(0, money.indexOf(".") + 3);
			}
		} else {
			int i = 10 + (int) (Math.random() * 90);
			money = money + "." + i;
		}
		SimplePositions moneySP = new SimplePositions();
		moneySP.buildHorizontalOffset(140).buildVerticalOffset(600);
		BufferedImage moneyBI = drawText(money, fontStyle, Font.BOLD, 56,
				color, 400);

		int yl = 140 + (fontSize - 10) * (money.length());

		SimplePositions yuanSP = new SimplePositions();
		yuanSP.buildHorizontalOffset(yl).buildVerticalOffset(611);
		BufferedImage yuanBI = drawText("元", "华文黑体", Font.BOLD, fontSize,
				new Color(54, 30, 0), 400);

		// 超越百分比
		int pl = 280;
		if (!RegexUtil.isNumeric(per)) {
			per = "88.88";
		} else if (Double.parseDouble(per) > 100) {
			per = "99.99";
		} else if (per.length() > 5) {
			per = per.substring(0, 5);
		} else if (2 < per.length() && 5 > per.length()) {
			int i = new Random().nextInt(8) + 1;
			per = per + "" + i;
		} else if (3 > per.length()) {
			int i = 10 + (int) (Math.random() * 90);
			per = per + "." + i;
		}
		SimplePositions perSP = new SimplePositions();
		perSP.buildHorizontalOffset(pl).buildVerticalOffset(700);
		BufferedImage perBI = drawText(per, fontStyle, Font.BOLD, fontSize,
				color, 260);

		InputStream in = null;
		BufferedImage bi = null;
		try {
			in = super.getFile(tmpPath);
			bi = Thumbnails.of(in).scale(1.0d).watermark(dateSP, dateBI, 0.9f)
					.watermark(moneySP, moneyBI, 0.9f)
					.watermark(perSP, perBI, 0.9f)
					.watermark(yuanSP, yuanBI, 0.9f).outputQuality(1.0d)
					.outputFormat("png").asBufferedImage();
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return super.saveFile(bi, getDefFormatName());
	}

}
