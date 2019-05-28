package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class QiuBaoYangTool extends BaseTool implements TwoDraw{
	public QiuBaoYangTool(StorageService storageService) {
		super(storageService);
	}

	public QiuBaoYangTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57e3b7850cf2cce64d805abe";
		StorageService storageService = new StorageService();
		QiuBaoYangTool tyt = new QiuBaoYangTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","女", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 32;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	static String ditu[] = {"http://imgzb.zhuangdianbi.com/57e3b7690cf2cce64d805ab1",
							"http://imgzb.zhuangdianbi.com/57e3b7790cf2cce64d805aba"};
	
	@Override
	public String draw(String one,String two, String tmpBackPic, String count) throws IOException {
		
		SimplePositions nameSP =null;
		BufferedImage nameBi = null;
		if(two.equals("男")){
			//姓名
			nameSP = new SimplePositions();
	 		nameSP.buildHorizontalOffset(315-(one.length()+2)*fontSize/2).buildVerticalOffset(215);
	 		nameBi = drawText("我是:"+one, zbfont.font(),fontType, 28, 
	 				color, 300, 0, 0, true);
			tmpBackPic = ditu[0];
		}else{
			//姓名
			nameSP = new SimplePositions();
	 		nameSP.buildHorizontalOffset(290-(one.length()+2)*fontSize/2).buildVerticalOffset(190);
	 		nameBi = drawText("我是:"+one, zbfont.font(),fontType, 28, 
	 				color, 300, 0, -0.1, true);
			tmpBackPic = ditu[1];
		}
		
		
		
 		SimplePositions[] sp = { nameSP};

		BufferedImage[] bis = { nameBi};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
