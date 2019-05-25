package com.zb.service.image.love;

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

public class ShouJiAppBiaoBaiTool extends BaseTool implements TwoDraw {
	
	public ShouJiAppBiaoBaiTool(StorageService storageService) {
		super(storageService);
	}

	public ShouJiAppBiaoBaiTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		// String tmpPath0 =
		// "http://imgzb.zhuangdianbi.com/5732a2a80cf2d5342be19ca8";
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/576bde750cf282c5552cd8a8";
		StorageService storageService = new StorageService();
		ShouJiAppBiaoBaiTool tyt = new ShouJiAppBiaoBaiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装","装", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 35;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one,String two,String tmpPath,String count) throws IOException {
		//统计上方名字字数
		List<String> list = new ArrayList<String>();
		for(int t = 0;t<one.length();t++){
			list.add(String.valueOf(one.charAt(t)));
		}
		//统计下方名字字数
		List<String> list2 = new ArrayList<String>();
		for(int t = 0;t<two.length();t++){
			list2.add(String.valueOf(two.charAt(t)));
		}
		
		SimplePositions nameSP =null;
		SimplePositions nameSP2 =null;
		SimplePositions nameSP3 =null;
		SimplePositions nameSP4=null;
		SimplePositions nameSP5 =null;
		SimplePositions nameSP6 =null;
		BufferedImage nameBI =null;
		BufferedImage nameBI2 =null;
		BufferedImage nameBI3 =null;
		BufferedImage nameBI4 =null;
		BufferedImage nameBI5 =null;
		BufferedImage nameBI6 =null;
		if(list.size()==1){
			// 上侧姓名
			String one1 = list.get(0);
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(79).buildVerticalOffset(226);
			nameBI = drawText(one1,zbfont, fontSize, color,
					400, 0, 0, true);
			
			String one2 = "";
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(185).buildVerticalOffset(226);
			nameBI2 = drawText(one2,zbfont, fontSize, color,
					400, 0, 0, true);
			
			String one3 = "";
			nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(288).buildVerticalOffset(226);
			nameBI3 = drawText(one3,zbfont, fontSize, color,
					400, 0, 0, true);
		}else if(list.size()==2){
			// 上侧姓名
			String one1 = list.get(0);
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(79).buildVerticalOffset(226);
			nameBI = drawText(one1,zbfont, fontSize, color,
					400, 0, 0, true);
			
			String one2 = "";
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(185).buildVerticalOffset(226);
			nameBI2 = drawText(one2,zbfont, fontSize, color,
					400, 0, 0, true);
			
			String one3 = list.get(1);
			nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(288).buildVerticalOffset(226);
			nameBI3 = drawText(one3,zbfont, fontSize, color,
					400, 0, 0, true);
		}else{
			// 上侧姓名
			String one1 = list.get(0);
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(79).buildVerticalOffset(227);
			nameBI = drawText(one1,zbfont, fontSize, color,
					400, 0, 0, true);
			
			String one2 = list.get(1);
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(185).buildVerticalOffset(227);
			nameBI2 = drawText(one2,zbfont, fontSize, color,
					400, 0, 0, true);
			
			String one3 = list.get(2);
			nameSP3 = new SimplePositions();
			nameSP3.buildHorizontalOffset(288).buildVerticalOffset(227);
			nameBI3 = drawText(one3,zbfont, fontSize, color,
					400, 0, 0, true);
			
		}
		
		
		if(list2.size()==1){
			//下侧姓名
			String two1 = "";
			nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(79).buildVerticalOffset(421);
			nameBI4 = drawText(two1, zbfont, fontSize, color,
					350, 0, 0, true);
			String two2 = "";
			nameSP5 = new SimplePositions();
			nameSP5.buildHorizontalOffset(185).buildVerticalOffset(421);
			nameBI5 = drawText(two2, zbfont, fontSize, color,
					350, 0, 0, true);
			String two3 = list2.get(0);
			nameSP6 = new SimplePositions();
			nameSP6.buildHorizontalOffset(288).buildVerticalOffset(421);
			nameBI6 = drawText(two3, zbfont, fontSize, color,
					350, 0, 0, true);
		}else if(list2.size()==2){
			//下侧姓名
			String two1 = list2.get(0);
			nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(79).buildVerticalOffset(421);
			nameBI4 = drawText(two1, zbfont, fontSize, color,
					350, 0, 0, true);
			String two2 = "";
			nameSP5 = new SimplePositions();
			nameSP5.buildHorizontalOffset(185).buildVerticalOffset(421);
			nameBI5 = drawText(two2, zbfont, fontSize, color,
					350, 0, 0, true);
			String two3 = list2.get(1);
			nameSP6 = new SimplePositions();
			nameSP6.buildHorizontalOffset(288).buildVerticalOffset(421);
			nameBI6 = drawText(two3, zbfont, fontSize, color,
					350, 0, 0, true);
		}else{
			//下侧姓名
			String two1 = list2.get(0);
			nameSP4 = new SimplePositions();
			nameSP4.buildHorizontalOffset(79).buildVerticalOffset(421);
			nameBI4 = drawText(two1, zbfont, fontSize, color,
					350, 0, 0, true);
			String two2 = list2.get(1);
			nameSP5 = new SimplePositions();
			nameSP5.buildHorizontalOffset(185).buildVerticalOffset(421);
			nameBI5 = drawText(two2, zbfont, fontSize, color,
					350, 0, 0, true);
			String two3 = list2.get(2);
			nameSP6 = new SimplePositions();
			nameSP6.buildHorizontalOffset(288).buildVerticalOffset(421);
			nameBI6 = drawText(two3, zbfont, fontSize, color,
					350, 0, 0, true);
		}
		
		
		SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4,nameSP5,nameSP6};

		BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4,nameBI5,nameBI6};

		return super.getSaveFile(sp, bis, 0.9f, tmpPath);
			
	}

}
