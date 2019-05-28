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
import com.zb.service.image.i.TwoDraw;

public class BanZhuanShangXiaoSanTool extends BaseTool implements TwoDraw{
	public BanZhuanShangXiaoSanTool(StorageService storageService) {
		super(storageService);
	}

	public BanZhuanShangXiaoSanTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/576ba5d10cf273bdeaacb028";
		StorageService storageService = new StorageService();
		BanZhuanShangXiaoSanTool tyt = new BanZhuanShangXiaoSanTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装","装逼学", tmpPath0,null));
	}

	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 14;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one, String two, String tmpPath,String count) throws IOException {
		int fontSize2 = 20;
		List<String> list = new ArrayList<String>();
		
		for(int t = 0;t<one.length();t++){
			list.add(String.valueOf(one.charAt(t)));
		}
		SimplePositions nameSP = null;
		BufferedImage nameBI = null;
		if(list.size()==1){
			//地址
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(70).buildVerticalOffset(455);
			nameBI = drawText(one,zbfont.font(),fontType,fontSize2, new Color(246,1,40),
					240, 0, 0, true);
		}else if(list.size()==2){
			//地址
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(60).buildVerticalOffset(455);
			nameBI = drawText(one,zbfont.font(),fontType,fontSize2, new Color(246,1,40),
					240, 0, 0, true);
		}else if(list.size()==3){
			//地址
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(49).buildVerticalOffset(455);
			nameBI = drawText(one,zbfont.font(),fontType,fontSize2, new Color(246,1,40),
					240, 0, 0, true);
		}else{
			//地址
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(40).buildVerticalOffset(455);
			nameBI = drawText(one,zbfont.font(),fontType,fontSize2, new Color(246,1,40),
					240, 0, 0, true);
		}
		
		
		//小三名字
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(458).buildVerticalOffset(525);
		BufferedImage nameBI2 = drawText(two, zbfont, fontSize, color,
				240, 0, 0, true);
		
		
		
		SimplePositions[] sp = { nameSP,nameSP2 };

		BufferedImage[] bis = { nameBI,nameBI2 };

		return super.getSaveFile(sp, bis, 1f, tmpPath);
	}

}
