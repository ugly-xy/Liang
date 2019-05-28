package com.zb.service.image.certificate;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.zb.common.utils.DateUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class GaoKaoChengJiRenZhengTool extends BaseTool implements TwoDraw {

	public GaoKaoChengJiRenZhengTool(StorageService storageService) {
		super(storageService);
	}

	public GaoKaoChengJiRenZhengTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		//String tmpPath0 = "209-成绩认证.png";
		StorageService storageService = new StorageService();
		GaoKaoChengJiRenZhengTool tyt = new GaoKaoChengJiRenZhengTool(storageService);
		tyt.isDebug(true);
		int i = 0;
		System.out
				.println(tyt.draw("装点逼", S[i], T[i],null));
	}
	
	static String T[] = {
			"http://imgzb.zhuangdianbi.com/5764db030cf2cb9eefcc0021",
			"http://imgzb.zhuangdianbi.com/5764da460cf2cb9eefcbff4b" };
	static String S[] = { "高考1", "高考2" };
	
	static ZbFont zbfont = ZbFont.宋体加粗;
	static int fontSize = 13;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	//生成随机6位数
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
        String u[] = {"○", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
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
	public String draw(String one, String two, String tmpPath,String count) throws IOException {
		
		SimplePositions nameSP = null;
		SimplePositions nameSP2 = null;
		SimplePositions nameSP3 = null;
		SimplePositions dateSP = null;
		String date = null;
		String date2 = null;
		
		BufferedImage nameBI = null;
		BufferedImage dateBi =null;
		BufferedImage nameBI3 =null;
		BufferedImage nameBI2 = null;
		if(two.equals(S[0])){
			// 姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(142).buildVerticalOffset(274);
			nameBI = drawText(one,zbfont, fontSize, color,
					200, 0, 0, true);
			
			// 省份
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(142).buildVerticalOffset(337);
			nameBI2 = drawText(two,zbfont, fontSize, color,
					200, 0, 0, true);
			
			// date
			date = DateUtil.dateFormat(new Date(), "yyyy-MM-dd");
			dateSP = new SimplePositions();
			dateSP.buildHorizontalOffset(472).buildVerticalOffset(254);
			dateBi = drawText(date, zbfont, fontSize,
					color, 240, 100, 0, true);
			
			// 编号
			date2 = DateUtil.dateFormat(new Date(), "yyyyMMdd");
			date2 = date2+this.getRandomCode();
			nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(472).buildVerticalOffset(232);
			nameBI3 = drawText(date2,zbfont, fontSize, color,
					200, 0, 0, true);
			SimplePositions[] sp = { nameSP,nameSP2,nameSP3, dateSP };

			BufferedImage[] bis = { nameBI,nameBI2,nameBI3, dateBi };
			return super.getSaveFile(sp, bis, 0.8f, tmpPath);
		}else{
			fontSize = 18;
			// 姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(146).buildVerticalOffset(215);
			nameBI = drawText(one,zbfont.font(),fontType, fontSize, color,
					200, 100, -0.06, true,25,10);
			
			
			// date this.dataToUpper(new Date())
			date =  DateUtil.dateFormat(new Date(), this.dataToUpper(new Date()));
			dateSP = new SimplePositions();
			dateSP.buildHorizontalOffset(290).buildVerticalOffset(640);
			dateBi = drawText(date,zbfont.font(),fontType, fontSize, 
					color, 240,100, -0.05, true,20,20);
			SimplePositions[] sp = { nameSP, dateSP };

			BufferedImage[] bis = { nameBI, dateBi };
			
			return super.getSaveFile(sp, bis, 0.95f, tmpPath);
		}
		
		

		
	}

}
