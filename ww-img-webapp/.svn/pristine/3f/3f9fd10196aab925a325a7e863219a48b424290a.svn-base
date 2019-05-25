package com.zb.service.image.love;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;

public class GuangZhouTaTool extends BaseTool {

	public GuangZhouTaTool(StorageService storageService) {
		super(storageService);
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/571b2fad0cf2a62e4b3acea0";
		String tmpPath1 = "http://imgzb.zhuangdianbi.com/571b33b10cf2a62e4b3acf2e";
		StorageService storageService = new StorageService();
		GuangZhouTaTool tyt = new GuangZhouTaTool(storageService);
		tyt.isDebug(true);

		System.out.println(tyt.drawImg("装点逼", "我爱你", names[0], tmpPath0));
	}

	private final static String[] names = { "蓝调", "粉调" };

	private final static String[] pics = {
			"http://imgzb.zhuangdianbi.com/571edff50cf2b215c5a3233f",
			"http://imgzb.zhuangdianbi.com/571edfb80cf2b215c5a32336" };

	private final static int[] fontSizes = { 60, 26 };

	private final static int[] lefts = { 285, 228 };

	private final static int[] tops = { 318, 170 };

	private final static int[] ilefts = { 252, 212 };

	private final static int[] itops = { 243, 142 };

	/***
	 * @param name
	 *            姓名
	 * @param tmpPath
	 *            背景图路径
	 * @return 目标图片地址
	 * @throws IOException
	 */
	public String drawImg(String name, String content, String style,
			String tmpPath) throws IOException {
		int index = 0;
		for (int i = 0; i < names.length; i++) {
			if (names[i].equals(style)) {
				index = i;
				break;
			}
		}
		Color color = new Color(255, 255, 255);

		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(lefts[index]).buildVerticalOffset(
				tops[index]);
		BufferedImage nameBI = drawText(name + content,
				ZbFont.SourceHanSansCNBold.font(),
				ZbFont.SourceHanSansCNBold.type(), fontSizes[index], color,
				fontSizes[index] + 10, 0, 0, false);

		SimplePositions imageSP = new SimplePositions();
		imageSP.buildHorizontalOffset(ilefts[index]).buildVerticalOffset(
				itops[index]);
		BufferedImage imageBI = super.getImg(pics[index]);

		// return super.drawTextImg(name + content, fontSizes[index],
		// lefts[index], tops[index], ZbFont.SourceHanSansCNBold, true,
		// color, 0, tmpPath);

		SimplePositions[] sp = { nameSP, imageSP };
		BufferedImage[] bis = { nameBI, imageBI };
		return super.getSaveFile(sp, bis, 0.9f, tmpPath);
	}
}
