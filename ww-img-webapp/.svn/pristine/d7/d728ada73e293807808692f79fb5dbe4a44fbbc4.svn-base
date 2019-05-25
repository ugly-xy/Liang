package com.zb.service.image.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

import com.zb.image.ChangeImage;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.FourDraw;

public class QiuQiuPaiHangTool extends BaseTool implements FourDraw{
	public QiuQiuPaiHangTool(StorageService storageService) {
		super(storageService);
	}

	public QiuQiuPaiHangTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57e233660cf2ab038927fdbf";
		StorageService storageService = new StorageService();
		QiuQiuPaiHangTool tyt = new QiuQiuPaiHangTool(storageService);
		tyt.isDebug(true);
		System.out
				.println(tyt.draw("装点逼","素材0.png","女","北京",tmpPath0,null));//10
	}
	
	static ZbFont zbfont = ZbFont.华康少女;
	static int fontSize = 28;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	static String nan = "http://imgzb.zhuangdianbi.com/57e233490cf2ab038927fdbb";
	static String nv = "http://imgzb.zhuangdianbi.com/57e233360cf2ab038927fdb8";
	static String ditu = "http://imgzb.zhuangdianbi.com/57e268140cf2ab03892809cf";
	
	static String xingming[] = {"大鱼吃小鱼","只玩一会儿","婉儿","欧巴桑o","风一直吹","东墙","新伟",
									"贪吃小猫","隔壁老王","我是谁","北北啊","肥猪流","偶来取经","昵称无敌"};
	
	static String diqu[] = {"北京","河北","河南","广东","西安","浙江","江西","福建","广西","云南"};
	@Override
	public String draw(String one, String two, String three,String four, String tmpBackPic, String count) throws IOException {
		//玩家昵称
 		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(356).buildVerticalOffset(177);
 		BufferedImage nameBi = drawText(one, zbfont, fontSize,
 				new Color(237,53,52), 800, 600, 0, true);
 		Random ran = new Random();
 		List<String> listxingming = new ArrayList<String>();
 		Set<String> list = new HashSet<String>();
 		while(list.size()<6){
 			list.add(xingming[ran.nextInt(xingming.length)]);
 		}
 		Iterator<String> nannan = list.iterator();
		while (nannan.hasNext()) {
			listxingming.add((String) nannan.next());
		   
		}
 		
 		//玩家昵称
 		SimplePositions nameSP3 = new SimplePositions();
 		nameSP3.buildHorizontalOffset(356).buildVerticalOffset(252);
 		BufferedImage nameBi3 = drawText(listxingming.get(0), zbfont, fontSize,
 				new Color(237,53,52), 800, 600, 0, true);
 		
 		//玩家昵称
 		SimplePositions nameSP4 = new SimplePositions();
 		nameSP4.buildHorizontalOffset(356).buildVerticalOffset(330);
 		BufferedImage nameBi4 = drawText(listxingming.get(1), zbfont, fontSize,
 				new Color(237,53,52), 800, 600, 0, true);
 		
 		//玩家昵称
 		SimplePositions nameSP5 = new SimplePositions();
 		nameSP5.buildHorizontalOffset(356).buildVerticalOffset(408);
 		BufferedImage nameBi5 = drawText(listxingming.get(2), zbfont, fontSize,
 				new Color(237,53,52), 800, 600, 0, true);
 		
 		//玩家昵称
 		SimplePositions nameSP6 = new SimplePositions();
 		nameSP6.buildHorizontalOffset(356).buildVerticalOffset(480);
 		BufferedImage nameBi6 = drawText(listxingming.get(3), zbfont, fontSize,
 				new Color(237,53,52), 800, 600, 0, true);
 		
 		//玩家昵称
 		SimplePositions nameSP7 = new SimplePositions();
 		nameSP7.buildHorizontalOffset(356).buildVerticalOffset(554);
 		BufferedImage nameBi7 = drawText(listxingming.get(4), zbfont, fontSize,
 				new Color(237,53,52), 800, 600, 0, true);
 		
 		//玩家昵称
 		SimplePositions nameSP8 = new SimplePositions();
 		nameSP8.buildHorizontalOffset(356).buildVerticalOffset(627);
 		BufferedImage nameBi8 = drawText(listxingming.get(5), zbfont, fontSize,
 				new Color(237,53,52), 800, 600, 0, true);
 		
 		
 		List<String> listxingming2 = new ArrayList<String>();
 		Set<String> list2 = new HashSet<String>();
 		while(list2.size()<6){
 			list2.add(diqu[ran.nextInt(diqu.length)]);
 		}
 		Iterator<String> nannan2 = list2.iterator();
		while (nannan2.hasNext()) {
			listxingming2.add((String) nannan2.next());
		   
		}
 		
		//玩家地区
 		SimplePositions nameSP33 = new SimplePositions();
 		nameSP33.buildHorizontalOffset(657).buildVerticalOffset(252);
 		BufferedImage nameBi33 = drawText(listxingming2.get(0), zbfont, fontSize,
 				new Color(226,242,210), 800, 600, 0, true);
 		
 		//玩家地区
 		SimplePositions nameSP44 = new SimplePositions();
 		nameSP44.buildHorizontalOffset(657).buildVerticalOffset(330);
 		BufferedImage nameBi44 = drawText(listxingming2.get(1), zbfont, fontSize,
 				new Color(226,242,210), 800, 600, 0, true);
 		
 		//玩家地区
 		SimplePositions nameSP55 = new SimplePositions();
 		nameSP55.buildHorizontalOffset(657).buildVerticalOffset(408);
 		BufferedImage nameBi55 = drawText(listxingming2.get(2), zbfont, fontSize,
 				new Color(226,242,210), 800, 600, 0, true);
 		
 		//玩家地区
 		SimplePositions nameSP66 = new SimplePositions();
 		nameSP66.buildHorizontalOffset(657).buildVerticalOffset(480);
 		BufferedImage nameBi66 = drawText(listxingming2.get(3), zbfont, fontSize,
 				new Color(226,242,210), 800, 600, 0, true);
 		
 		//玩家地区
 		SimplePositions nameSP77 = new SimplePositions();
 		nameSP77.buildHorizontalOffset(657).buildVerticalOffset(554);
 		BufferedImage nameBi77 = drawText(listxingming2.get(4), zbfont, fontSize,
 				new Color(226,242,210), 800, 600, 0, true);
 		
 		//玩家地区
 		SimplePositions nameSP88 = new SimplePositions();
 		nameSP88.buildHorizontalOffset(657).buildVerticalOffset(627);
 		BufferedImage nameBi88 = drawText(listxingming2.get(5), zbfont, fontSize,
 				new Color(226,242,210), 800, 600, 0, true);
 		
 		
 		//玩家照片
		BufferedImage p = super.compressImage(two, 70, 70);
		p = ChangeImage.resizeCrop(p, 70);
		p = ChangeImage.makeRoundedCorner(p, 70);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(236).buildVerticalOffset(165);
 		
		BufferedImage p2 = null;
		SimplePositions pSP2 = null;
		
		if(three.equals("男")){
			p2 = super.getImg(nan);
			pSP2 = new SimplePositions();
			pSP2.buildHorizontalOffset(326).buildVerticalOffset(185);
		}else{
		
			p2 = super.getImg(nv);
			pSP2 = new SimplePositions();
			pSP2.buildHorizontalOffset(326).buildVerticalOffset(185);
		
		}
		
		//地区
 		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(682-four.length()*fontSize/2).buildVerticalOffset(180);
 		BufferedImage nameBi2 = drawText(four, zbfont, fontSize,
 				new Color(226,242,210), 800, 600, 0, true);
 		
 		//玩家照片
		BufferedImage p3 = super.getImg(ditu);
		SimplePositions pSP3 = new SimplePositions();
		pSP3.buildHorizontalOffset(0).buildVerticalOffset(0);
 		
 		
 		SimplePositions[] sp = { pSP,pSP3,nameSP,nameSP2, pSP2,nameSP3,nameSP4,nameSP5,nameSP6,nameSP7,nameSP8,nameSP33,nameSP44,nameSP55,nameSP66,nameSP77,nameSP88};
		BufferedImage[] bis = { p,p3,nameBi,nameBi2, p2 ,nameBi3,nameBi4,nameBi5,nameBi6,nameBi7,nameBi8,nameBi33,nameBi44,nameBi55,nameBi66,nameBi77,nameBi88};
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
