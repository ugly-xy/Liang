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
import com.zb.service.image.i.OneDraw;

public class Mai8TaiIPhoneTool extends BaseTool implements OneDraw{
	public Mai8TaiIPhoneTool(StorageService storageService) {
		super(storageService);
	}

	public Mai8TaiIPhoneTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57e0ef760cf22e02635e1282";
		StorageService storageService = new StorageService();
		Mai8TaiIPhoneTool tyt = new Mai8TaiIPhoneTool(storageService);
		tyt.isDebug(true);
		System.out
				.println(tyt.draw("装点逼",tmpPath0,null));//10
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 50;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//标题
 		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(42).buildVerticalOffset(137);
 		BufferedImage nameBi = drawText("好友圈尽晒iPhone7，"+one+"一口气给自家狗狗买了8台！", zbfont.font(),fontType, fontSize,
 				color, 800, 600, 0, true);
 		
 		
 		//时间
 		String date = DateUtil.dateFormat(new Date(), "yyyy-MM-dd");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(38).buildVerticalOffset(285);
		BufferedImage dateBi = drawText(date+"  腾讯新闻", zbfont.font(), fontType, 24,
				new Color(199,199,199), 600, 0, 0, true);
 		
		//内容
 		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(40).buildVerticalOffset(1050);
 		BufferedImage nameBi2 = drawText("    iPhone7 9月16日开卖，"+one+"的好友圈尽晒新到手的苹果。这一晒可是引起了平日里最喜欢炫富的"
 				+one+"的不满，iPhone有啥牛的?我能给狗买8台！", zbfont, 38,
 				color, 800, 600, 0, true);
 		SimplePositions[] sp = { nameSP,nameSP2,dateSP};

		BufferedImage[] bis = { nameBi,nameBi2,dateBi};

		return super.getSaveFile(sp, bis, 0.95f, tmpBackPic);
	}

}
