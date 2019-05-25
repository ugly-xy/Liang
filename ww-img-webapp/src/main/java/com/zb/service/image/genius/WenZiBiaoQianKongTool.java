package com.zb.service.image.genius;

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
import com.zb.service.image.i.TwoDraw;

public class WenZiBiaoQianKongTool extends BaseTool implements TwoDraw{
	public WenZiBiaoQianKongTool(StorageService storageService) {
		super(storageService);
	}

	public WenZiBiaoQianKongTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		//String tmpPath0 = "1.png";
		StorageService storageService = new StorageService();
		WenZiBiaoQianKongTool tyt = new WenZiBiaoQianKongTool(storageService);
		tyt.isDebug(true);
		int  i =0;
		Date date = new Date();
		Long a = date.getTime();
		String b = a.toString();
		System.out.println(tyt.draw("装",b, D[i][1],null));
		
		
	}
	
	static ZbFont zbfont = ZbFont.华康少女;
	static int fontSize = 58;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	
	static String baiYang[] ={"http://imgzb.zhuangdianbi.com/578b213a0cf2cd587c554752",
							"http://imgzb.zhuangdianbi.com/578b21480cf2cd587c554770",
							"http://imgzb.zhuangdianbi.com/578b21530cf2cd587c554780",
							"http://imgzb.zhuangdianbi.com/578b215e0cf2cd587c554797",
							"http://imgzb.zhuangdianbi.com/578b21690cf2cd587c5547ac",
							"http://imgzb.zhuangdianbi.com/578b21750cf2cd587c5547c4"};
	
	static String chuNv[] ={"http://imgzb.zhuangdianbi.com/578b21850cf2cd587c5547e6"};
	
	static String jinNiu[] ={"http://imgzb.zhuangdianbi.com/578b21950cf2cd587c554807",
							"http://imgzb.zhuangdianbi.com/578b21a00cf2cd587c55481f",
							"http://imgzb.zhuangdianbi.com/578b21ab0cf2cd587c554836",
							"http://imgzb.zhuangdianbi.com/578b21b50cf2cd587c554849",
							"http://imgzb.zhuangdianbi.com/578b21c00cf2cd587c554864",
							"http://imgzb.zhuangdianbi.com/578b21cb0cf2cd587c554878",
							"http://imgzb.zhuangdianbi.com/578b21d50cf2cd587c554887"};
	
	static String juXie[]  ={"http://imgzb.zhuangdianbi.com/578b22130cf2cd587c5548f2",
							"http://imgzb.zhuangdianbi.com/578b221e0cf2cd587c554906",
							"http://imgzb.zhuangdianbi.com/578b222a0cf2cd587c554918",
							"http://imgzb.zhuangdianbi.com/578b22330cf2cd587c55492c",
							"http://imgzb.zhuangdianbi.com/578b223d0cf2cd587c55493e",
							"http://imgzb.zhuangdianbi.com/578b22470cf2cd587c55494c",
							"http://imgzb.zhuangdianbi.com/578b22510cf2cd587c554960"};
	
	static String moJie[]  ={"http://imgzb.zhuangdianbi.com/578b225f0cf2cd587c554977",
							"http://imgzb.zhuangdianbi.com/578b22690cf2cd587c554986",
							"http://imgzb.zhuangdianbi.com/578b22740cf2cd587c554995",
							"http://imgzb.zhuangdianbi.com/578b227d0cf2cd587c5549a5",
							"http://imgzb.zhuangdianbi.com/578b22880cf2cd587c5549bb"};
	
	static String sheShou[]  ={"http://imgzb.zhuangdianbi.com/578b22950cf2cd587c5549cf",
							"http://imgzb.zhuangdianbi.com/578b229f0cf2cd587c5549e2",
							"http://imgzb.zhuangdianbi.com/578b22aa0cf2cd587c5549ef",
							"http://imgzb.zhuangdianbi.com/578b22b40cf2cd587c5549fe",
							"http://imgzb.zhuangdianbi.com/578b22bf0cf2cd587c554a18"};
	
	static String shiZi[]  ={"http://imgzb.zhuangdianbi.com/578b22cd0cf2cd587c554a3d",
							"http://imgzb.zhuangdianbi.com/578b22d90cf2cd587c554a58",
							"http://imgzb.zhuangdianbi.com/578b22e20cf2cd587c554a6e",
							"http://imgzb.zhuangdianbi.com/578b22ed0cf2cd587c554a7a",
							"http://imgzb.zhuangdianbi.com/578b22f70cf2cd587c554a8f"};
	
	static String shuangZi[]  ={"http://imgzb.zhuangdianbi.com/578b23060cf2cd587c554abd",
							"http://imgzb.zhuangdianbi.com/578b23110cf2cd587c554ad3",
							"http://imgzb.zhuangdianbi.com/578b232b0cf2cd587c554b03",
							"http://imgzb.zhuangdianbi.com/578b23350cf2cd587c554b16",
							"http://imgzb.zhuangdianbi.com/578b233f0cf2cd587c554b27",
							"http://imgzb.zhuangdianbi.com/578b23480cf2cd587c554b34"};
	
	static String tianPing[]  ={"http://imgzb.zhuangdianbi.com/578b23570cf2cd587c554b55",
							"http://imgzb.zhuangdianbi.com/578b23620cf2cd587c554b6b",
							"http://imgzb.zhuangdianbi.com/578b236c0cf2cd587c554b7a",
							"http://imgzb.zhuangdianbi.com/578b23760cf2cd587c554b8b",
							"http://imgzb.zhuangdianbi.com/578b23800cf2cd587c554b9a",
							"http://imgzb.zhuangdianbi.com/578b23890cf2cd587c554bb0"};
	
	static String tianXie[]  ={"http://imgzb.zhuangdianbi.com/578b239d0cf2cd587c554bd7",
							"http://imgzb.zhuangdianbi.com/578b23a90cf2cd587c554be7",
							"http://imgzb.zhuangdianbi.com/578b23b40cf2cd587c554c03",
							"http://imgzb.zhuangdianbi.com/578b23bd0cf2cd587c554c1d"};
	
	static String shuiPing[]  ={"http://imgzb.zhuangdianbi.com/578b23cd0cf2cd587c554c40",
							"http://imgzb.zhuangdianbi.com/578b23d80cf2cd587c554c60",
							"http://imgzb.zhuangdianbi.com/578b23e10cf2cd587c554c77",
							"http://imgzb.zhuangdianbi.com/578b23ed0cf2cd587c554c8c",
							"http://imgzb.zhuangdianbi.com/578b23f60cf2cd587c554c9b",
							"http://imgzb.zhuangdianbi.com/578b24000cf2cd587c554cae"};
	
	static String shuangYu[]  ={"http://imgzb.zhuangdianbi.com/578b240e0cf2cd587c554ccb",
							"http://imgzb.zhuangdianbi.com/578b24190cf2cd587c554ce3",
							"http://imgzb.zhuangdianbi.com/578b24310cf2cd587c554d19",
							"http://imgzb.zhuangdianbi.com/578b243d0cf2cd587c554d2e",
							"http://imgzb.zhuangdianbi.com/578b244a0cf2cd587c554d4b",
							"http://imgzb.zhuangdianbi.com/578b24550cf2cd587c554d5e",
							"http://imgzb.zhuangdianbi.com/578b245f0cf2cd587c554d6b",
							"http://imgzb.zhuangdianbi.com/578b24690cf2cd587c554d78"};
	
	
	
	static String D[][] = {baiYang,jinNiu,shuangZi,juXie,shiZi,chuNv,tianPing,
							tianXie,sheShou,moJie,
							shuiPing,shuangYu};
	
	@Override
	public String draw(String one,String two,String tmpBackPic, String count) throws IOException {
		
		
//		Random ran = new Random();
		
		
	    long time = Long.parseLong(two);
        Date date = new Date(time);
        //处理星座
        int  y =0;
        String shengRi = DateUtil.dateFormat(date, "Mdd");
        int sr = Integer.parseInt(shengRi);
        if(321<=sr&&sr<= 419){
        	y=0;//白羊
        	
        }else if(420<=sr&&sr<= 520){
        	y=1;//金牛
        	
        }else if(521<=sr&&sr<= 621){
        	y=2;//双子
        	
        }else if(622<=sr&&sr<= 722){
        	y=3;//巨蟹
        	
        }else if(723<=sr&&sr<= 822){
        	y=4;//狮子
        	
        }else if(823<=sr&&sr<= 922){
        	y=5;//处女
        	
        }else if(923<=sr&&sr<= 1023){
        	y=6;//天秤
        	
        }else if(1024<=sr&&sr<= 1122){
        	y=7;//天蝎
        	
        }else if(1123<=sr&&sr<= 1221){
        	y=8;//射手
        	
        }else if(sr>=1222||sr<= 119){
        	y=9;//摩羯
        
        }else if(120<=sr&&sr<= 218){
        	y=10;//水瓶
        
        }else{
        	y=11;//双鱼
        	
        }
        
        int len = one.length();
		int max = D[y].length;
		
        int num = date.getDate()%max;
       // System.out.println(num);
     // date
 		String date2 = DateUtil.dateFormat(date, "M月d日");
 		
 		
 		SimplePositions dateSP = new SimplePositions();
 		dateSP.buildHorizontalOffset(103).buildVerticalOffset(135);
 		BufferedImage dateBi = drawText(date2, zbfont, 18,
 				new Color(210,158,158), 240, 0, 0, true);
        
		SimplePositions nameSP =null;
		BufferedImage nameBI = null;
		if(len ==1){
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(45).buildVerticalOffset(205);
			nameBI = drawText("我  是     "+one, zbfont, fontSize, color,
					500, 0, 0, true);
		}else if(len ==2){
			String one0 = String.valueOf(one.charAt(0))+"     "+String.valueOf(one.charAt(1));
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(45).buildVerticalOffset(205);
			nameBI = drawText("我  是 "+one0, zbfont, fontSize, color,
					500, 0, 0, true);
		}else{
			String one0 = String.valueOf(one.charAt(0))+" "+String.valueOf(one.charAt(1))+" "+String.valueOf(one.charAt(2));
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(45).buildVerticalOffset(205);
			nameBI = drawText("我  是 "+one0+"", zbfont, fontSize, color,
					500, 0, 0, true);
		}
		
		
		SimplePositions[] sp = { nameSP,dateSP};

		BufferedImage[] bis = { nameBI,dateBi};

		return super.getSaveFile(sp, bis, 0.9f, D[y][num]);
	}

}
