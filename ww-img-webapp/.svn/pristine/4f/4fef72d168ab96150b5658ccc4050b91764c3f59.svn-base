package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class ChuGuiZhiShuCeShiTool extends BaseTool implements OneDraw{
	public ChuGuiZhiShuCeShiTool(StorageService storageService) {
		super(storageService);
	}

	public ChuGuiZhiShuCeShiTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/582d999d0cf2807db7bc75b2";
		StorageService storageService = new StorageService();
		ChuGuiZhiShuCeShiTool tyt = new ChuGuiZhiShuCeShiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装装装",tmpPath0,null));
	}

	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 45;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	static String yi[] = {"一心一意爱一个人","从不花心，眼里只有爱人一个","用情至深，专情一人",
						"喜欢可以很多，但爱只能一个","定力极强，不会花心","遇到真爱就不再花心","家中红旗不倒，外面彩旗飘飘"};
	
	static String er[] = {"钟情一生","根本容不下其他的异性","简直可以说是世上最专情的人",
						"喜欢就会放肆，但爱就是克制","点10086个赞","一心一意只为真爱保留","绩优股是不可能被一人持有"};
	
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		
		Random ran = new Random();
		int a = ran.nextInt(100);
		int b = 0;
		if(a<11){
			b=0;
		}else if(a<20){
			b=1;
		}else if(a<30){
			b=2;
		}else if(a<40){
			b=3;
		}else if(a<50){
			b=4;
		}else if(a<80){
			b=5;
		}else{
			b=6;
		}
		
		
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(375-one.length()*60/2).buildVerticalOffset(30);
		BufferedImage nameBI = drawText(one,zbfont,60, color,
				500, 100, 0, true);
		
		//指数
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(450-((a+"").length()+1)*120/2).buildVerticalOffset(210);
		BufferedImage nameBI2 = drawText(a+"%",zbfont.font(),fontType,120, new Color(255,40,40),
				500, 500, 0, true);
		
		//姓名
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(375-yi[b].length()*fontSize/2).buildVerticalOffset(390);
		BufferedImage nameBI3 = drawText(yi[b],zbfont,fontSize, color,
				750, 100, 0, true);
		
		//姓名
		SimplePositions nameSP4 = new SimplePositions();
		nameSP4.buildHorizontalOffset(375-er[b].length()*fontSize/2).buildVerticalOffset(480);
		BufferedImage nameBI4 = drawText(er[b],zbfont,fontSize, color,
				750, 100, 0, true);
		
		SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4};

		BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4};

		return super.getSaveFile(sp, bis, 0.95f, tmpBackPic);
	}

}
