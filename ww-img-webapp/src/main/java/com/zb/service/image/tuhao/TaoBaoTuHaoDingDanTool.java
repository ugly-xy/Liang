package com.zb.service.image.tuhao;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class TaoBaoTuHaoDingDanTool extends BaseTool implements TwoDraw{
	
	public TaoBaoTuHaoDingDanTool(StorageService storageService) {
		super(storageService);
	}

	public TaoBaoTuHaoDingDanTool() {
	}
	public static void main(String[] args) throws IOException {
		//String tmpPath0 = "209-成绩认证.png";
		StorageService storageService = new StorageService();
		TaoBaoTuHaoDingDanTool tyt = new TaoBaoTuHaoDingDanTool(storageService);
		tyt.isDebug(true);
		int i = 2;
		System.out
				.println(tyt.draw("装点逼", S[i], T[i],null));
	}
	
	static String T[] = {
			"http://imgzb.zhuangdianbi.com/578090d80cf2c2119f84706c",
			"http://imgzb.zhuangdianbi.com/578090e70cf2c2119f847085",
			"http://imgzb.zhuangdianbi.com/578090f30cf2c2119f847091"
			};
	static String S[] = { "买飞机轮船","买羊驼","买充气娃娃" };
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 28;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one,String two, String tmpBackPic, String count) throws IOException {
		
		SimplePositions nameSP = null;
		BufferedImage nameBI = null;
		if(two.equals(S[0])){
			int fontSize2 = 20;
			// date
			String date = DateUtil.dateFormat(new Date(), "M月d日 HH:mm:ss");
			String one0 = "尊敬的"+one+"您的运通百夫长黑金卡于"+date+"消费44650000元人民币...";
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(75).buildVerticalOffset(40);
			nameBI = drawText(one0,zbfont, fontSize2, new Color(255,255,255),
					400, 100, 0, true);
		}else if(two.equals(S[1])){
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(168).buildVerticalOffset(166);
			nameBI = drawText(one,zbfont, fontSize, color,
					400, 100, 0, true);
		}else{
			int fontSize2 = 20;
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(123).buildVerticalOffset(221);
			nameBI = drawText(one,zbfont, fontSize2, color,
					400, 100, 0, true);
		}
		
		
		SimplePositions[] sp = { nameSP};
		BufferedImage[] bis = { nameBI};
		return super.getSaveFile(sp, bis, 0.8f, tmpBackPic);
	}

}
