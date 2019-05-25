package com.zb.service.image.certificate;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;
import com.zb.service.image.spoof.QueRenKuangTool;

public class ZhongKaoChengJiChaXunTool extends BaseTool implements OneDraw{
	public ZhongKaoChengJiChaXunTool(StorageService storageService) {
		super(storageService);
	}

	public ZhongKaoChengJiChaXunTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "中考成绩查询.png";
		StorageService storageService = new StorageService();
		ZhongKaoChengJiChaXunTool tyt = new ZhongKaoChengJiChaXunTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装逼", tmpPath0,null));
	}
	
	//生成随机考生号
	public static String getRandomCode() {
		int length = 6;
		Random r = new Random();
		StringBuilder randomCode = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int num = r.nextInt(10);
			randomCode.append(num);
		}
		return randomCode.toString();
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 9;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpBackPic,String count) throws IOException {
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(354).buildVerticalOffset(129);
		BufferedImage nameBI = drawText(one,zbfont,fontSize, color,
				240, 100, 0, true);
		
		
		//考号
		String two= this.getRandomCode();
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(228).buildVerticalOffset(129);
		BufferedImage nameBI2 = drawText("070"+two,zbfont,fontSize, color,
				240, 100, 0, true);
		
		// date
		int fontSize2 = 14;
		String date = DateUtil.dateFormat(new Date(), "yyyy");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(212).buildVerticalOffset(54);
		BufferedImage dateBi = drawText(date, zbfont.font(), fontType, fontSize2,
				new Color(47,26,23), 240, 0, 0, true);
		
		
		SimplePositions[] sp = { nameSP,nameSP2,dateSP};

		BufferedImage[] bis = { nameBI,nameBI2,dateBi};

		return super.getSaveFile(sp, bis, 0.95f, tmpBackPic);
	}
}
