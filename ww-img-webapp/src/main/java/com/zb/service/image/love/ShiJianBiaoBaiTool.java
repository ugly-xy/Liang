package com.zb.service.image.love;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.ThreeDraw;

public class ShiJianBiaoBaiTool extends BaseTool implements ThreeDraw{
	public ShiJianBiaoBaiTool(StorageService storageService) {
		super(storageService);
	}

	public ShiJianBiaoBaiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "294-时间表白.png";
		StorageService storageService = new StorageService();
		ShiJianBiaoBaiTool tyt = new ShiJianBiaoBaiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("都点逼","都点比","204093494011", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体加粗;
	static int fontSize = 12;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String three,String tmpBackPic, String count) throws IOException {
		//姓名1
		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(91).buildVerticalOffset(46);
 		BufferedImage nameBi = drawText(one, zbfont, fontSize,
 				new Color(240,136,136), 240, 0, 0, true);
 		//姓名2
 		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(287).buildVerticalOffset(295);
 		BufferedImage nameBi2 = drawText(two, zbfont, fontSize,
 				new Color(48,168,192), 240, 0, 0, true);
 		
 		String cen = one+" 与 "+two+" 已经相识了";
 		
 		SimplePositions nameSP7 = new SimplePositions();
 		nameSP7.buildHorizontalOffset(72).buildVerticalOffset(320);
 		BufferedImage nameBi7 = drawText(cen, zbfont, 24,
 				new Color(248,72,216), 600, 0, 0, true);
 		
 		Date date = new Date();
 		//获取传输时间
 		long time = Long.parseLong(three);
        Date date2 = new Date(time);
        
        Calendar cal=Calendar.getInstance();//使用日历类 
        cal.setTime(date);
        
        String hour = cal.get(cal.HOUR_OF_DAY)+""; //获得时
        String mini = cal.get(cal.MINUTE)+""; //获得分
        String second = cal.get(cal.SECOND)+""; //获得秒 
        int days = (int) ((date.getTime()-date2.getTime())/(1000 * 60 * 60 * 24));
        String day = (date.getTime()-date2.getTime())/(1000 * 60 * 60 * 24)+"";
 		
        SimplePositions nameSP3 =null;
        BufferedImage nameBi3 = null;
        //天数
        if(days>=10000){
        	nameSP3 = new SimplePositions();
     		nameSP3.buildHorizontalOffset(60).buildVerticalOffset(360);
     		nameBi3 = drawText(day, zbfont, 32,
     				color, 240, 0, 0, true);
        }else if(days>=1000){
        	nameSP3 = new SimplePositions();
     		nameSP3.buildHorizontalOffset(80).buildVerticalOffset(360);
     		nameBi3 = drawText(day, zbfont, 32,
     				color, 240, 0, 0, true);
        }else if(days>=100){
        	nameSP3 = new SimplePositions();
     		nameSP3.buildHorizontalOffset(90).buildVerticalOffset(360);
     		nameBi3 = drawText(day, zbfont, 32,
     				color, 240, 0, 0, true);
        }else{
        	nameSP3 = new SimplePositions();
     		nameSP3.buildHorizontalOffset(100).buildVerticalOffset(360);
     		nameBi3 = drawText(day, zbfont, 32,
     				color, 240, 0, 0, true);
        }
 		
 		//小时
 		SimplePositions nameSP4 = new SimplePositions();
 		nameSP4.buildHorizontalOffset(180).buildVerticalOffset(360);
 		BufferedImage nameBi4 = drawText(hour, zbfont, 32,
 				color, 240, 0, 0, true);
 		//分钟
 		SimplePositions nameSP5 = new SimplePositions();
 		nameSP5.buildHorizontalOffset(275).buildVerticalOffset(360);
 		BufferedImage nameBi5 = drawText(mini, zbfont, 32,
 				color, 240, 0, 0, true);
 		//秒
 		SimplePositions nameSP6 = new SimplePositions();
 		nameSP6.buildHorizontalOffset(360).buildVerticalOffset(360);
 		BufferedImage nameBi6 = drawText(second, zbfont, 32,
 				color, 240, 0, 0, true);
        
 		SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSP5,nameSP6,nameSP7};

		BufferedImage[] bis = { nameBi,nameBi2,nameBi3,nameBi4,nameBi5,nameBi6,nameBi7};

		return super.getSaveFile(sp, bis, 0.9f, tmpBackPic);
	}
	
}
