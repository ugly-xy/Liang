package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class ZuChanXiangJiTool extends BaseTool implements OneDraw{
	public ZuChanXiangJiTool(StorageService storageService) {
		super(storageService);
	}

	public ZuChanXiangJiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5785d2010cf2483acfb023b6";
		StorageService storageService = new StorageService();
		ZuChanXiangJiTool tyt = new ZuChanXiangJiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装逼", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.微软雅黑加粗;
	static int fontSize =61;
	static Color color = new Color(197, 54, 74);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		
		int len = one.length();
		if(len<=1){
			one = one+" 鸡";
		}else if(len==2){
			one = one+"鸡";
		}
		
		// 姓名
		int fontSize2 = 50;
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(238).buildVerticalOffset(211);
		BufferedImage nameBI = drawText(String.valueOf(one.charAt(0)), zbfont.font(),fontType, fontSize2, color,
				240, 100, 0, true);
		// 姓名
		int fontSize3 = 57;
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(304).buildVerticalOffset(200);
		BufferedImage nameBI2 = drawText(String.valueOf(one.charAt(1)), zbfont.font(),fontType, fontSize3, color,
				240, 100, 0, true);
		// 姓名
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(380).buildVerticalOffset(191);
		BufferedImage nameBI3 = drawText(String.valueOf(one.charAt(2)), zbfont.font(),fontType, fontSize, color,
				240, 100, 0, true);
		SimplePositions[] sp = { nameSP,nameSP2,nameSP3};

		BufferedImage[] bis = { nameBI,nameBI2,nameBI3};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
