package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class ShuJiaDaiXieZuoYeTool extends BaseTool implements TwoDraw{
	public ShuJiaDaiXieZuoYeTool(StorageService storageService) {
		super(storageService);
	}

	public ShuJiaDaiXieZuoYeTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57b584580cf234b87f8cc235";
		StorageService storageService = new StorageService();
		ShuJiaDaiXieZuoYeTool tyt = new ShuJiaDaiXieZuoYeTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装逼点","1234567890123",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 30;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		//标题
 		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(190).buildVerticalOffset(160);
 		BufferedImage nameBi = drawText("代写暑假作业", zbfont, 50,
 				color, 480, 500, 0, true);
 		
 		//内容
 		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(120).buildVerticalOffset(280);
 		BufferedImage nameBi2 = drawText("        你还为暑假作业头痛么？！违逆解决暑假烦恼，"
 				+ "给自己一个快乐轻松是暑假。现我等代写中小学暑假作业！只需要一周的零花钱 88元，就可以畅玩整个暑假生活.心动了吗？赶快联系我吧！", zbfont, fontSize,
 				color, 480, 500, 0, true);
 		
 		
 		
 		//姓名
 		SimplePositions nameSP3 = new SimplePositions();
 		nameSP3.buildHorizontalOffset(237).buildVerticalOffset(680);
 		BufferedImage nameBi3 = drawText("联系人："+one, zbfont, fontSize,
 				color, 480, 300, 0, true);
 		
 		//qq
 		SimplePositions nameSP4 = new SimplePositions();
 		nameSP4.buildHorizontalOffset(237).buildVerticalOffset(730);
 		BufferedImage nameBi4 = drawText("QQ："+two, zbfont, 35,
 				color, 480, 300, 0, true);
 		
 		SimplePositions[] sp = { nameSP,nameSP2,nameSP3,nameSP4};

		BufferedImage[] bis = { nameBi,nameBi2,nameBi3,nameBi4};

		return super.getSaveFile(sp, bis, 0.8f, tmpBackPic);
	}

}
