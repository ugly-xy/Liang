package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class HanWeiZhuQuanTool extends BaseTool implements OneDraw{
	public HanWeiZhuQuanTool(StorageService storageService) {
		super(storageService);
	}

	public HanWeiZhuQuanTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5784d46b0cf213d8953f68e3";
		StorageService storageService = new StorageService();
		HanWeiZhuQuanTool tyt = new HanWeiZhuQuanTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼", tmpPath0,"44545"));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 33;
	static Color color = new Color(164, 0, 3);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		//我是
		String name = "我是:";
		SimplePositions nameSP3 = new SimplePositions();
		nameSP3.buildHorizontalOffset(160).buildVerticalOffset(598);
		BufferedImage nameBI3 = drawText(name, zbfont, fontSize, new Color(0,0,0),
				240, 100, 0, true);
		// 姓名
		
		SimplePositions nameSP= null;
		BufferedImage nameBI =null;
		if(one.length()==1){
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(265).buildVerticalOffset(598);
			nameBI = drawText(one, zbfont, fontSize, color,
					240, 100, 0, true);
		}else if(one.length()==2){
			one =String.valueOf(one.charAt(0))+" "+String.valueOf(one.charAt(1));
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(245).buildVerticalOffset(598);
			nameBI = drawText(one, zbfont, fontSize, color,
					240, 100, 0, true);
		}else{
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(237).buildVerticalOffset(598);
			nameBI = drawText(one, zbfont, fontSize, color,
					240, 100, 0, true);
		}
		
		
		DecimalFormat df=new DecimalFormat("00000000000");
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(155).buildVerticalOffset(724);
		BufferedImage nameBI2 = drawText(df.format(Integer.parseInt(count)), zbfont, fontSize, color,
				240, 100, 0, true);
		SimplePositions[] sp = { nameSP,nameSP2,nameSP3};

		BufferedImage[] bis = { nameBI,nameBI2,nameBI3};

		return super.getSaveFile(sp, bis, 0.9f, tmpBackPic);
	}

}
