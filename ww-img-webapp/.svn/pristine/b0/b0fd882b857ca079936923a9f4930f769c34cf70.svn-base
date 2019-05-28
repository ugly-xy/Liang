package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.i.BaseDraw;
import com.zb.service.image.i.TwoDraw;

public class DemoTool extends BaseTool implements BaseDraw{
	public DemoTool(StorageService storageService) {
		super(storageService);
	}

	public DemoTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/576ba5d10cf273bdeaacb028";
		StorageService storageService = new StorageService();
		DemoTool tyt = new DemoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw(new String[]{"装","装逼学", tmpPath0}));
	}

	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 14;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;

	/**
	 * 参数规则 先 one two ... 之后为 tmpPath count style
	 * count 会自动加上，不用在mark配置
	 * style 未必有
	 */
	@Override
	public String draw(String[] args) throws IOException {
		String one = args[0];
		String two = args[1];
		String tmpPath = args[2];
//		String count = args[4];
//		String style = args[3];

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
