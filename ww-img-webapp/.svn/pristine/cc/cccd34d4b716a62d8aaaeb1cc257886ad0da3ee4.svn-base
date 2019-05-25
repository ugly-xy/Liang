package com.zb.service.image.spoof;

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
import com.zb.service.image.i.TwoDraw;

public class NongCunXiaoMingTool extends BaseTool implements TwoDraw{
	public NongCunXiaoMingTool(StorageService storageService) {
		super(storageService);
	}

	public NongCunXiaoMingTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57ea3ec10cf2fcc7bbc57a98";
		StorageService storageService = new StorageService();
		NongCunXiaoMingTool tyt = new NongCunXiaoMingTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("素材0.png","523151121213",tmpPath0,null));
		/*Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);*/
		
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 50;
	static Color color = new Color(255, 78, 0);
	static int fontType = Font.BOLD;
	
	static String ditu = "http://imgzb.zhuangdianbi.com/57ea3eb60cf2fcc7bbc57a8b";
	//月份
	static String yue[] ={"大","来","狗","傻","二","胖","臭","愣","铁","黑","小","发"};
	//大
	static String da[] ={"毛","蛋","牛","顺","柱子"};
	//来
	static String lai[] = {"顺","钱"};
	//狗
	static String gou[] = {"蛋","剩","娃儿"};
	//傻
	static String sha[] = {"娃儿","毛","狗","蛋儿","柱子"};
	//二
	static String er[] = {"柱子","蛋","牛","狗","毛"};
	//胖
	static String pang[] = {"柱子","娃儿","牛","胖","狗儿"};
	//臭
	static String chou[] = {"娃儿","狗儿","柱子","毛儿"};
	//愣
	static String leng[] = {"柱子","娃儿","狗儿","蛋儿"};
	//铁
	static String tie[] = {"柱","蛋","牛"};
	//黑
	static String hei[] = {"牛","娃子","狗子","柱子"};
	//小
	static String xiao[] = {"狗儿","牛儿","壮","黑","蛋子"};
	//屎
	static String fa[] = {"财","黑","灰"};
	
	//static String ri[] = {"柱","坑","娃","毛","剩","蛋",
	//						"顺","狗","蛋","驴","牛"};
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		
		
		//玩家照片
		BufferedImage p = super.getImg(ditu);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		//玩家照片
		BufferedImage p2 = super.compressImage(one, 234, 234);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(244).buildVerticalOffset(345);
		
		long time = Long.parseLong(two);
        Date date = new Date(time);
		
        Calendar c = Calendar.getInstance();
		c.setTime(date);
        int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
        
		String name = null;
		int a = month%12;
		if(a==0){
			name = yue[0]+da[day%da.length];
		}else if(a==1){
			name = yue[1]+lai[day%lai.length];
		}else if(a==2){
			name = yue[2]+gou[day%gou.length];
		}else if(a==3){
			name = yue[3]+sha[day%gou.length];
		}else if(a==4){
			name = yue[4]+er[day%er.length];
		}else if(a==5){
			name = yue[5]+pang[day%pang.length];
		}else if(a==6){
			name = yue[6]+chou[day%chou.length];
		}else if(a==7){
			name = yue[7]+leng[day%leng.length];
		}else if(a==8){
			name = yue[8]+tie[day%tie.length];
		}else if(a==9){
			name = yue[9]+hei[day%hei.length];
		}else if(a==10){
			name = yue[10]+xiao[day%xiao.length];
		}else{
			name = yue[11]+fa[day%fa.length];
		}
		
		//String name = yue[month%11]+ri[day%11]+"";
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(360-name.length()*fontSize/2).buildVerticalOffset(720);
		BufferedImage nameBI2 = drawText(name,zbfont.font(),fontType,fontSize, color,
				600, 0, 0, true);
		
		SimplePositions[] sp = {pSP2,pSP, nameSP2};
		BufferedImage[] bis = {p2,p, nameBI2};
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
