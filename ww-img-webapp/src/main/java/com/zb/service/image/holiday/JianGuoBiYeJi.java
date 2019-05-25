package com.zb.service.image.holiday;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.common.utils.RandomUtil;
import com.zb.image.ChangeImage;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.*;

public class JianGuoBiYeJi extends BaseTool implements ThreeDraw {

	public JianGuoBiYeJi(StorageService storageService) {
		super(storageService);
	}

	public JianGuoBiYeJi() {

	}

	public static void main(String[] args) throws IOException {
		// String tmpPath0 = "205.png";
		StorageService storageService = new StorageService();
		JianGuoBiYeJi tyt = new JianGuoBiYeJi(storageService);
		tyt.isDebug(true);
		int i = 1;
		System.out.println(tyt.draw("坚果","北京傻逼大学","2017", T[i], null));
	}

	String pic = "http://imgzb.zhuangdianbi.com/59364cd40cf20b2cc848088b";
	static String T[] = { "http://imgzb.zhuangdianbi.com/593620400cf295b5ccc782df",
			"http://imgzb.zhuangdianbi.com/593623730cf295b5ccc782e0",
			"http://imgzb.zhuangdianbi.com/593624850cf295b5ccc782e1",
			"http://imgzb.zhuangdianbi.com/593624cd0cf295b5ccc782e2",
			"http://imgzb.zhuangdianbi.com/593624e40cf295b5ccc782e4",
			"http://imgzb.zhuangdianbi.com/593624fc0cf295b5ccc782e5",
			"http://imgzb.zhuangdianbi.com/593625140cf295b5ccc782e6",
			"http://imgzb.zhuangdianbi.com/5936252d0cf295b5ccc782e8",
			"http://imgzb.zhuangdianbi.com/593625480cf295b5ccc782ea",
			"http://imgzb.zhuangdianbi.com/5936256a0cf295b5ccc782eb",
			"http://imgzb.zhuangdianbi.com/5936258c0cf295b5ccc782ec",
			"http://imgzb.zhuangdianbi.com/593625ae0cf295b5ccc782ed",
			"http://imgzb.zhuangdianbi.com/593625ea0cf295b5ccc782ee",
			"http://imgzb.zhuangdianbi.com/5936264b0cf295b5ccc782ef",
			"http://imgzb.zhuangdianbi.com/593626730cf295b5ccc782f0",
			"http://imgzb.zhuangdianbi.com/5936268e0cf295b5ccc782f1",
			"http://imgzb.zhuangdianbi.com/5936269a0cf295b5ccc782f2",
			"http://imgzb.zhuangdianbi.com/593626b10cf295b5ccc782f3",
			"http://imgzb.zhuangdianbi.com/593626c00cf295b5ccc782f4",
			"http://imgzb.zhuangdianbi.com/593626d30cf295b5ccc782f5",
			"http://imgzb.zhuangdianbi.com/593626ea0cf295b5ccc782f6",
			"http://imgzb.zhuangdianbi.com/593627020cf295b5ccc782f7",
			"http://imgzb.zhuangdianbi.com/5936271b0cf295b5ccc782f8",
			"http://imgzb.zhuangdianbi.com/593627410cf295b5ccc782fa",
			"http://imgzb.zhuangdianbi.com/5936274f0cf295b5ccc782fb",
			"http://imgzb.zhuangdianbi.com/593627700cf295b5ccc782fc",
			"http://imgzb.zhuangdianbi.com/593627920cf295b5ccc782fd",
			"http://imgzb.zhuangdianbi.com/593627a90cf295b5ccc782fe",
			"http://imgzb.zhuangdianbi.com/593627b40cf295b5ccc782ff",
			"http://imgzb.zhuangdianbi.com/593627c30cf295b5ccc78301" };

	@Override
	public String draw(String one,String two,String three, String tmpPath, String count) throws IOException {
		int fontSize = 22;
		Color color = new Color(0, 0, 0);
		String fontStyle = ZbFont.黑体加粗.font();
		int fontType = Font.PLAIN;

		SimplePositions contentSP = null;
		BufferedImage contentBI = null;
		// 内容
	
		String tp = T[RandomUtil.nextInt(T.length)];
		
		BufferedImage p = super.getImg(pic);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(0).buildVerticalOffset(180);
		
		String txt = two+" "+one+" 毕业于"+three+"年";
		int left = 360 - (txt.length()) * 9;
		int top = 200;
		
		contentSP = new SimplePositions();
		contentSP.buildHorizontalOffset(left).buildVerticalOffset(top);
		contentBI = drawText(txt, fontStyle, fontType, fontSize, color, 720,300, 0, true);

		SimplePositions[] sp = { pSP ,contentSP };

		BufferedImage[] bis = {p, contentBI };
		return super.getSaveFile(sp, bis, 1f, tp);
	}

}
