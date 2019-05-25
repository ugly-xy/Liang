package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.FourDraw;

public class QinZiJianDingTool extends BaseTool implements FourDraw{
	public QinZiJianDingTool(StorageService storageService) {
		super(storageService);
	}

	public QinZiJianDingTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57b43ee90cf240677f6b9b5b";
		StorageService storageService = new StorageService();
		QinZiJianDingTool tyt = new QinZiJianDingTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("比比", "装逼","非亲生","儿子",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.宋体;
	static int fontSize = 12;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	static String ditu[] = {"http://imgzb.zhuangdianbi.com/57b43ecc0cf240677f6b9b4c",
							"http://imgzb.zhuangdianbi.com/57b43edc0cf240677f6b9b57"};
	
	@Override
	public String draw(String one, String two, String three, String four,String tmpBackPic, String count) throws IOException {
		
		if(three.equals("亲生")){
			//姓名
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(65).buildVerticalOffset(250);
			BufferedImage nameBI = drawText("经过我中心鉴定，"+two+"是"+one+"的亲生"+four+".", zbfont, fontSize, color,
					600, 100, 0, true);
			
			SimplePositions[] sp = {nameSP };

			BufferedImage[] bis = { nameBI };

			return super.getSaveFile(sp, bis, 0.85f, ditu[0]);
		}else{
			//姓名
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(65).buildVerticalOffset(250);
			BufferedImage nameBI = drawText("经过我中心鉴定，"+two+"不是"+one+"的亲生"+four+".", zbfont, 14, color,
					600, 100, 0, true);
			
			SimplePositions[] sp = {nameSP };

			BufferedImage[] bis = { nameBI };

			return super.getSaveFile(sp, bis, 0.9f, ditu[1]);
		}
		
		
	}

}
