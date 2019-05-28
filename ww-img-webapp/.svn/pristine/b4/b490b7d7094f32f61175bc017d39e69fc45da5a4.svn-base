package com.zb.service.image.spoof;

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
import com.zb.service.image.i.OneDraw;

public class QiCheTiaoFuTool extends BaseTool implements OneDraw{
	
	public QiCheTiaoFuTool(StorageService storageService) {
		super(storageService);
	}

	public QiCheTiaoFuTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/576a75cf0cf280ab0cd92b8b";
		StorageService storageService = new StorageService();
		QiCheTiaoFuTool tyt = new QiCheTiaoFuTool(storageService);
		tyt.isDebug(true);
		System.out
				.println(tyt.draw("睡你妈逼", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 30;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String tmpPath,String count) throws IOException {
		//字数小于11 补上！
		for(int i =0;i<10;i++){
			if(one.length()<10){
				one+="!";
			}
		}
		
		List<String> list = new ArrayList<String>();
		for(int t = 0;t<one.length();t++){
			list.add(String.valueOf(one.charAt(t)));
		}
		SimplePositions nameSP0 = null;  
		SimplePositions nameSP1 = null;		
		SimplePositions nameSP2 = null;
		SimplePositions nameSP3 = null;
		SimplePositions nameSP4 = null;
		SimplePositions nameSP5 = null;
		SimplePositions nameSP6 = null;
		BufferedImage nameBI0 =null;
		BufferedImage nameBI1 =null;
		BufferedImage nameBI2 =null;
		BufferedImage nameBI3 =null;
		BufferedImage nameBI4 =null;
		BufferedImage nameBI5 =null;
		BufferedImage nameBI6 =null;
		int fontSize = 32;
		nameSP0 = new SimplePositions();
		nameSP0.buildHorizontalOffset(114).buildVerticalOffset(134);
		nameBI0 = drawText(list.get(0), zbfont, fontSize, color,
				240, 100, 0, true,20,20);
		nameSP1 = new SimplePositions();
		nameSP1.buildHorizontalOffset(177).buildVerticalOffset(134);
		nameBI1 = drawText(list.get(1), zbfont, fontSize, color,
				240, 100, 0, true,20,20);
		nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(238).buildVerticalOffset(145);
		nameBI2 = drawText(list.get(2), zbfont, fontSize, color,
				240, 100, 0, true,20,20);
		nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(280).buildVerticalOffset(145);
		nameBI3 = drawText(list.get(3), zbfont, fontSize, color,
				240, 100, 0, true,20,20);
		nameSP4 = new SimplePositions();
		nameSP4.buildHorizontalOffset(323).buildVerticalOffset(145);
		nameBI4 = drawText(list.get(4), zbfont, fontSize, color,
				240, 100, 0, true,20,20);
		nameSP5 = new SimplePositions();
		nameSP5.buildHorizontalOffset(390).buildVerticalOffset(147);
		nameBI5 = drawText(list.get(5), zbfont, fontSize, color,
				240,100, 0, true,20,20);
		nameSP6 = new SimplePositions();
		nameSP6.buildHorizontalOffset(439).buildVerticalOffset(150);
		nameBI6 = drawText(list.get(6), zbfont, fontSize, color,
				240,100, 0, true,20,20);
		
		SimplePositions[] sp = { nameSP0,nameSP1,nameSP2,nameSP3,nameSP4,nameSP5,nameSP6 };

		BufferedImage[] bis = { nameBI0, nameBI1, nameBI2, nameBI3,nameBI4,nameBI5,nameBI6};
		
		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
