package com.zb.service.image.certificate;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.ThreeDraw;

public class RinMinDaXueTool  extends BaseTool implements ThreeDraw{
	public RinMinDaXueTool(StorageService storageService) {
		super(storageService);
	}

	public RinMinDaXueTool() {
		super();
	}
	

	public static void main(String[] args) throws IOException {
		// String tmpPath0 =
		// "http://imgzb.zhuangdianbi.com/5732a2a80cf2d5342be19ca8";
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/576a74ee0cf280ab0cd92a74";
		StorageService storageService = new StorageService();
		RinMinDaXueTool tyt = new RinMinDaXueTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","装逼学院","装点逼专业", tmpPath0,null));
		
		
	}
	
	static ZbFont zbfont = ZbFont.宋体;
	static int fontSize = 11;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	
	 // 日期转化为大小写
    public static String dataToUpper(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH) + 1;
        int day = ca.get(Calendar.DAY_OF_MONTH);
        return numToUpper(year) + "年" + monthToUppder(month) + "月" + dayToUppder(day) + "日";
    }
    public static String numToUpper(int num) {
        // String u[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
        //String u[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String u[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        char[] str = String.valueOf(num).toCharArray();
        String rstr = "";
        for (int i = 0; i < str.length; i++) {
            rstr = rstr + u[Integer.parseInt(str[i] + "")];
        }
        return rstr;
    }
    /***
     * <b>function:</b> 月转化为大写
     * @createDate 2016-6-17 
     * @param month 月份
     * @return 返回转换后大写月份
     */
    public static String monthToUppder(int month) {
        if (month < 10) {
            return numToUpper(month);
        } else if (month == 10) {
            return "十";
        } else {
            return "十" + numToUpper(month - 10);
        }
    }
    public static String dayToUppder(int day) {
        if (day < 20) {
            return monthToUppder(day);
        } else {
            char[] str = String.valueOf(day).toCharArray();
            if (str[1] == '0') {
                return numToUpper(Integer.parseInt(str[0] + "")) + "十";
            } else {
                return numToUpper(Integer.parseInt(str[0] + "")) + "十" + numToUpper(Integer.parseInt(str[1] + ""));
            }
        }
    }
	
	@Override
	public String draw(String one, String two, String three,String tmpPath,String count) throws IOException {
		// 姓名1
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(77).buildVerticalOffset(9);
		BufferedImage nameBI = drawText(one, zbfont, fontSize, color,
				240, 0, 0, true);
		//学院
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(122).buildVerticalOffset(83);
		BufferedImage nameBI2 = drawText(two, zbfont, fontSize, color,
				240, 0, 0, true);
		//专业
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(122).buildVerticalOffset(107);
		BufferedImage nameBI3 = drawText(three, zbfont, fontSize, color,
				240, 0, 0, true);
		
		// date this.dataToUpper(new Date())
		ZbFont zbfont2 = ZbFont.宋体加粗;
		int fontSize2 = 14;
		String date =  DateUtil.dateFormat(new Date(), this.dataToUpper(new Date()));
		SimplePositions dateSP = new SimplePositions();
		dateSP.buildHorizontalOffset(270).buildVerticalOffset(632);
		BufferedImage dateBi = drawText(date,zbfont2, fontSize2, 
				color, 240,0, 0, true);
		
		// 姓名2
		ZbFont zbfont3 = ZbFont.隶书;
		int fontSize3 = 19;
		SimplePositions nameSP4 = new SimplePositions();
		nameSP4.buildHorizontalOffset(40).buildVerticalOffset(398);
		BufferedImage nameBI4 = drawText(one, zbfont3, fontSize3, color,
				240, 0, 0, true);
		SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,dateSP};

		BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,dateBi };

		return super.getSaveFile(sp, bis, 0.9f, tmpPath);
			
	}
}
