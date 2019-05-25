package com.zb.service.image.certificate;

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
import com.zb.service.image.i.FourDraw;

public class QiMoKaoShiChengJiTool extends BaseTool implements FourDraw{
	public QiMoKaoShiChengJiTool(StorageService storageService) {
		super(storageService);
	}

	public QiMoKaoShiChengJiTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/576cd8750cf2d36930590013";
		StorageService storageService = new StorageService();
		QiMoKaoShiChengJiTool tyt = new QiMoKaoShiChengJiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装装","二年级","2班","701", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.建刚静心楷;
	static int fontSize = 26;
	static Color color = new Color(31, 38, 121);
	static int fontType = Font.BOLD;
	
	
	@Override
	public String draw(String one, String two,String three,String four, String tmpPath,String count) throws IOException {
		
		int len = two.length();
		SimplePositions nameSP2 =null;
		BufferedImage nameBI2 = null;
		//姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(187).buildVerticalOffset(90);
		BufferedImage nameBI = drawText(one,zbfont,fontSize, color,
				240, 0, 0, true);
		
		//年级
		ZbFont zbfont2 = ZbFont.宋体;
		int fontSize2 = 25;
		if(len==2){
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(80).buildVerticalOffset(10);
			nameBI2 = drawText(two,zbfont2,fontSize2, new Color(0,0,0),
					240, 0, 0, true);
		}else{
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(57).buildVerticalOffset(10);
			nameBI2 = drawText(two,zbfont2,fontSize2, new Color(0,0,0),
					240, 0, 0, true);
		}
		
		
		//班级
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(320).buildVerticalOffset(90);
		BufferedImage nameBI3 = drawText(three,zbfont,fontSize, color,
				240, 0, 0, true);
		//学号
		SimplePositions nameSP4 = new SimplePositions();
		nameSP4.buildHorizontalOffset(423).buildVerticalOffset(90);
		BufferedImage nameBI4 = drawText(four,zbfont,fontSize, color,
				240, 0, 0, true);
		SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4};

		BufferedImage[] bis = { nameBI,nameBI2,nameBI3,nameBI4};

		return super.getSaveFile(sp, bis, 0.8f, tmpPath);
	}

}
