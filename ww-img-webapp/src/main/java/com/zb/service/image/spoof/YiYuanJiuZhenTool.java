package com.zb.service.image.spoof;

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
import com.zb.service.image.i.ThreeDraw;

public class YiYuanJiuZhenTool extends BaseTool implements ThreeDraw{
	public YiYuanJiuZhenTool(StorageService storageService) {
		super(storageService);
	}

	public YiYuanJiuZhenTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5805b6270cf2c3942dec75f6";
		StorageService storageService = new StorageService();
		YiYuanJiuZhenTool tyt = new YiYuanJiuZhenTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("西藏自治区武警总医院","装点逼","男", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 32;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	static String buwei[] = {"肚子","脖子","头部","眼睛","肚子","脚部","肚子"};

	static String miaoshu[] = {"这几天朋友聚餐吃多了，感觉消化不良，肚子不舒服","今天压力很大，一直感觉有一种无形的力量扼柱了喉咙，感觉呼吸非常的困难，脖子后面老有风",
								"这几天头痛，流鼻涕四肢无力，路也看不清楚了，说话语无伦次","我觉得我眼睛出了问题，打开钱包看不到钱了",
								"这几天肚子一直变大，身体越来越胖，衬衫的扣子扣不住体重一直在上升","今天走路的时候脚丫很冷，踩到硬物还很疼","一天到晚，一直觉得自己很饿，为什么呢"};
	
	static String jianyi[] = {"剖腹看看是不是消化不良！","你的秋衣穿反了！","来的还算早，建议快去停尸房床位！趁现在还没满！","我眼睛没问题，我也看不到你钱包里的钱，根本就没有！",
								"建议绝食，会有显著改善！","你就一直没发现你没穿鞋子吗！","给你检查了一下肚子，两天没吃饭了吧，骚年！"};
	
	
	@Override
	public String draw(String one, String two, String three, String tmpBackPic, String count) throws IOException {
		Random ran = new Random();
		int a = ran.nextInt(buwei.length);
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(399-(one.length()+5)*fontSize/2).buildVerticalOffset(145);
		BufferedImage nameBI = drawText(one+"诊断报告单",zbfont.font(),fontType,34, color,
				600, 0, 0, true);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(134).buildVerticalOffset(272);
		BufferedImage nameBI2 = drawText(two,zbfont,16, color,
				600, 0, 0, true);
		
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(328).buildVerticalOffset(276);
		BufferedImage nameBI3 = drawText(three,zbfont,16, color,
				600, 0, 0, true);
		
		SimplePositions nameSP4 = new SimplePositions();
		nameSP4.buildHorizontalOffset(168).buildVerticalOffset(317);
		BufferedImage nameBI4 = drawText(buwei[a],zbfont,16, color,
				600, 0, 0, true);
		
		String date =  DateUtil.dateFormat(new Date(), "yyyy-MM-dd");
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(362).buildVerticalOffset(307);
		BufferedImage dateBi = drawText(date,zbfont, 16, 
				color, 240,0, 0, true);
		
		String date2 =  DateUtil.dateFormat(new Date(), "yyyy-MM-dd");
		SimplePositions dateSP2 = new SimplePositions();
		dateSP2.buildHorizontalOffset(653).buildVerticalOffset(245);
		BufferedImage dateBi2 = drawText(date2,zbfont, 14, 
				color, 240,0, 0, true);
		
		SimplePositions nameSP5 = new SimplePositions();
		nameSP5.buildHorizontalOffset(130).buildVerticalOffset(430);
		BufferedImage nameBI5 = drawText(miaoshu[a],zbfont,24, color,
				560, 0, 0, true);
		
		SimplePositions nameSP6 = new SimplePositions();
		nameSP6.buildHorizontalOffset(130).buildVerticalOffset(690);
		BufferedImage nameBI6 = drawText(jianyi[a],zbfont,24, color,
				560, 0, 0, true);
		
		
		SimplePositions[] sp = { nameSP ,nameSP2,nameSP3,nameSP4,dateSP,dateSP2,nameSP5,nameSP6};

		BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,dateBi,dateBi2,nameBI5,nameBI6};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
