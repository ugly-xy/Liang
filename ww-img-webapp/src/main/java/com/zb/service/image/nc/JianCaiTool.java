package com.zb.service.image.nc;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.FourDraw;

public class JianCaiTool extends BaseTool implements FourDraw{
	public JianCaiTool(StorageService storageService) {
		super(storageService);
	}

	public JianCaiTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/579b3fe70cf22eb0f6f8830d";
		StorageService storageService = new StorageService();
		JianCaiTool tyt = new JianCaiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("素材6.png","执念" ,"静静的等待着，择一人而终老。","白色",tmpPath0,null));
	}
	//14字
	static ZbFont zbfont = ZbFont.微软雅黑;
	static int fontSize = 12;
	//黄 红 粉 黑 白 橙 绿  浅蓝
	static Color color[] = {new Color(255, 255, 0),new Color(255, 0, 0),
							new Color(255, 0, 255),new Color(0, 0, 0),
							new Color(255, 255, 255),new Color(255, 99, 0),
							new Color(0, 255, 0),new Color(87, 176, 227)};
	
	static int fontType = Font.BOLD;
	
	static String ming1 = "http://imgzb.zhuangdianbi.com/579b3fcb0cf22eb0f6f882f4";
	static String ming2 = "http://imgzb.zhuangdianbi.com/579b3fd60cf22eb0f6f882fd";
	
	@Override
	public String draw(String one, String two, String three,String four, String tmpBackPic, String count) throws IOException {
		//玩家照片
		BufferedImage p = super.compressImage(one, 450, 650);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		
		StorageService storageService = new StorageService();
		JianCaiTool tyt = new JianCaiTool(storageService);
		
		int i = 0;
		
		if(four.equals("黄色")){
			i=0;
		}else if(four.equals("红色")){
			i=1;
		}else if(four.equals("粉色")){
			i=2;
		}else if(four.equals("黑色")){
			i=3;
		}else if(four.equals("白色")){
			i=4;
		}else if(four.equals("橙色")){
			i=5;
		}else if(four.equals("绿色")){
			i=6;
		}else{
			i=7;
		}
		
		String ul1 = tyt.d1(two,i,ming1,null);
		BufferedImage p2= super.getImg(ul1);
		BufferedImage p5 = p2.getSubimage(0, 10, 450, 40);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(0).buildVerticalOffset(252);
		
		BufferedImage p6 = p2.getSubimage(0, 68, 450, 40);
		
		SimplePositions pSP3 = new SimplePositions();
		pSP3.buildHorizontalOffset(0).buildVerticalOffset(315);
		
		
		String ul2 = tyt.d2(three,i,ming1,null);
		BufferedImage p4= super.getImg(ul2);
		SimplePositions pSP4 = new SimplePositions();
		pSP4.buildHorizontalOffset(0).buildVerticalOffset(286);
		
		SimplePositions[] sp = { pSP,pSP2,pSP3,pSP4};

		BufferedImage[] bis = { p,p5,p6,p4};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	
	public String d1(String one,int two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(225-one.length()*85/2).buildVerticalOffset(0);
		BufferedImage nameBI = drawText(one, ZbFont.汉仪菱心体简, 85, color[two],
				600, 600, 0, true);
		
		
		SimplePositions[] sp = { nameSP};

		BufferedImage[] bis = { nameBI};

		return super.getSaveFile(sp, bis, 1f, ming1);
	}
	public String d2(String one,int two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(225-one.length()*18/2).buildVerticalOffset(0);
		BufferedImage nameBI = drawText(one, zbfont.font(),fontType, 18, color[two],
				600, 300, 0, true);
		
		
		SimplePositions[] sp = { nameSP};
		
		BufferedImage[] bis = { nameBI};
		
		return super.getSaveFile(sp, bis, 1f, ming2);
	}
}
