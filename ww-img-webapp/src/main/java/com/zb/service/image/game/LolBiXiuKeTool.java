package com.zb.service.image.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class LolBiXiuKeTool extends BaseTool implements TwoDraw{
	public LolBiXiuKeTool(StorageService storageService) {
		super(storageService);
	}

	public LolBiXiuKeTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5770b3f40cf2b9c850bbdcee";
		StorageService storageService = new StorageService();
		LolBiXiuKeTool tyt = new LolBiXiuKeTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装","小学生的逆袭之路", tmpPath0,null));
	}

	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 18;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpPath,String count) throws IOException {
		
		List<String> list = new ArrayList<String>();
		
		for(int t = 0;t<one.length();t++){
			list.add(String.valueOf(one.charAt(t)));
		}
		
		SimplePositions nameSP =null;
		BufferedImage nameBI =null;
		if(list.size()==1){
			//姓名
			one = one +" 制作";
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(200).buildVerticalOffset(260);
			nameBI = drawText(one,zbfont,fontSize, color,
					160, 0, 0, true);
		}else if(list.size()==2){
			//姓名
			one = one +" 制作";
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(190).buildVerticalOffset(260);
			nameBI = drawText(one,zbfont,fontSize, color,
					160, 0, 0, true);
		}else{
			//姓名
			one = one +" 制作";
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(180).buildVerticalOffset(260);
			nameBI = drawText(one,zbfont,fontSize, color,
					160, 0, 0, true);
		}
		
		
		//出发地
		int fontSize2 = 20;
		two = "论"+two;
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(135).buildVerticalOffset(222);
		BufferedImage nameBI2 = drawText(two, zbfont.font(),fontType, fontSize2, color,
				240, 0, 0, true);
		SimplePositions[] sp = { nameSP,nameSP2};

		BufferedImage[] bis = { nameBI,nameBI2};

		return super.getSaveFile(sp, bis, 0.95f, tmpPath);
	}

}
