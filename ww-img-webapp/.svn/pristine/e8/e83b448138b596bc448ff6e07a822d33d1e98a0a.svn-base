package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.OneDraw;

public class QiangTiGuangGaoTool extends BaseTool implements OneDraw{
	public QiangTiGuangGaoTool(StorageService storageService) {
		super(storageService);
	}

	public QiangTiGuangGaoTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57ab10950cf2ce6b642e0c99";
		StorageService storageService = new StorageService();
		QiangTiGuangGaoTool tyt = new QiangTiGuangGaoTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.微软雅黑;
	static int fontSize = 70;
	static Color color = new Color(217, 204, 204);
	static int fontType = Font.BOLD;
	
	static String mian = "http://imgzb.zhuangdianbi.com/57ab107a0cf2ce6b642e0c3a";
	
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		
		Random ran = new Random();
		
		int i = ran.nextInt(5);
		
		SimplePositions nameSP =null;
		SimplePositions nameSP2 =null;
		BufferedImage nameBI = null;
		BufferedImage nameBI2 = null;
		if(i==0){
			
				nameSP = new SimplePositions();
				nameSP.buildHorizontalOffset(250-one.length()*fontSize/2).buildVerticalOffset(120);
				nameBI = drawText(one+"  牌包谷", zbfont.font(),fontType, fontSize, color,
						600, 600, 0, true);
				
				nameSP2 = new SimplePositions();
				nameSP2.buildHorizontalOffset(90).buildVerticalOffset(220);
				nameBI2 = drawText("棒大 秆硬 粒多 产量高", zbfont.font(),fontType, 55, color,
						600, 600, 0, true);
			
		}else if(i==1){
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(210-one.length()*fontSize/2).buildVerticalOffset(100);
			nameBI = drawText(one+"  牌猪三宝", zbfont.font(),fontType, fontSize, color,
					600, 600, 0, true);
			
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(90).buildVerticalOffset(200);
			nameBI2 = drawText("母猪吃了都说好", zbfont.font(),fontType, 80, color,
					600, 600, 0, true);
		}else if(i==2){
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(210-one.length()*fontSize/2).buildVerticalOffset(100);
			nameBI = drawText(one+"  牌猪三宝", zbfont.font(),fontType, fontSize, color,
					600, 600, 0, true);
			
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(90).buildVerticalOffset(210);
			nameBI2 = drawText("科技含量高  养殖效益好", zbfont.font(),fontType, 55, color,
					600, 600, 0, true);
		}else if(i==3){
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(190-one.length()*fontSize/2).buildVerticalOffset(100);
			nameBI = drawText(one+" 教你来致富", zbfont.font(),fontType, fontSize, color,
					600, 600, 0, true);
			
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(90).buildVerticalOffset(200);
			nameBI2 = drawText("家庭事业两不误", zbfont.font(),fontType, 80, color,
					600, 600, 0, true);
		}else{
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(190-one.length()*fontSize/2).buildVerticalOffset(100);
			nameBI = drawText(one+" 家里啥都有", zbfont.font(),fontType, fontSize, color,
					600, 600, 0, true);
			
			nameSP2 = new SimplePositions();
			nameSP2.buildHorizontalOffset(90).buildVerticalOffset(200);
			nameBI2 = drawText("快递送到家门口", zbfont.font(),fontType, 80, color,
					600, 600, 0, true);
		}
		
		//蒙版
		BufferedImage p = super.getImg(mian);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		SimplePositions[] sp = { nameSP,nameSP2,pSP};

		BufferedImage[] bis = { nameBI,nameBI2,p};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
