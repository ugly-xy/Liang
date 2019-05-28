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

public class YuePaoTool  extends BaseTool implements TwoDraw{
	
	public YuePaoTool(StorageService storageService) {
		super(storageService);
	}

	public YuePaoTool() {
	}
	
	public static void main(String[] args) throws IOException {
		// String tmpPath0 =
		// "http://imgzb.zhuangdianbi.com/5732a2a80cf2d5342be19ca8";
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/5767d7950cf2ce4bd899cfd5";
		StorageService storageService = new StorageService();
		YuePaoTool tyt = new YuePaoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","今日可否一战？", tmpPath0,null));
	}
	static ZbFont zbfont = ZbFont.潘婉琼字体;
	static int fontSize = 22;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one,String two, String tmpPath,String count) throws IOException {
		
		
		List<String> list = new ArrayList<String>();
		
		for(int t = 0;t<two.length();t++){
			list.add(String.valueOf(two.charAt(t)));
		}
			//one = one +"\n "+two;
			// 姓名
		if(list.size()>2){
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(155).buildVerticalOffset(370);
			BufferedImage nameBI = drawText(one, zbfont, fontSize, color,
					240, 200, -0.4, true,30,30);
			
			// 姓名
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(170).buildVerticalOffset(388);
			BufferedImage nameBI2 = drawText(two, zbfont, fontSize, color,
					240, 100, -0.4, true,20,20);
			
			SimplePositions[] sp = { nameSP,nameSP2};

			BufferedImage[] bis = { nameBI, nameBI2};
			return super.getSaveFile(sp, bis, 0.6f, tmpPath);
		}else{
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(150).buildVerticalOffset(360);
			BufferedImage nameBI = drawText(one, zbfont, fontSize, color,
					240, 200, -0.4, true,30,30);
			
			// 姓名
			SimplePositions nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(170).buildVerticalOffset(398);
			BufferedImage nameBI2 = drawText(two, zbfont, fontSize, color,
					240, 100, -0.4, true,20,20);
			
			SimplePositions[] sp = { nameSP,nameSP2};

			BufferedImage[] bis = { nameBI, nameBI2};
			return super.getSaveFile(sp, bis, 0.6f, tmpPath);
		}
			
		
		
	}


}
