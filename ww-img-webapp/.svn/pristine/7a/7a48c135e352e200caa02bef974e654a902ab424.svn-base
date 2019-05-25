package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import com.zb.image.ChangeImage;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.ThreeDraw;

public class MingXingGuanXiCeShiTool extends BaseTool implements ThreeDraw{
	public MingXingGuanXiCeShiTool(StorageService storageService) {
		super(storageService);
	}

	public MingXingGuanXiCeShiTool() {
		super();
	}
	
	public static void main(String[] args) throws IOException {
		
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/576e3b950cf2c7bcb2a5cb26";
		StorageService storageService = new StorageService();
		MingXingGuanXiCeShiTool tyt = new MingXingGuanXiCeShiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装","测试.jpg","女", tmpPath0,"223"));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 24;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	static String zheZhao = "http://imgzb.zhuangdianbi.com/576dfe550cf2c7bcb2a5750e";
	static String sex[] = {"男","女"};
	@Override
	public String draw(String one, String two,String three, String tmpBackPic,String count) throws IOException {
		Random rand = new Random();
		
		List<String> listNan = new ArrayList<String>();
		List<String> listNan0 = new ArrayList<String>();
		listNan.add("http://imgzb.zhuangdianbi.com/576e3beb0cf2c7bcb2a5cb9e");   listNan.add("http://imgzb.zhuangdianbi.com/576e3c1d0cf2c7bcb2a5cbd5");   
		listNan.add("http://imgzb.zhuangdianbi.com/576e3c110cf2c7bcb2a5cbc6");   listNan.add("http://imgzb.zhuangdianbi.com/576e3bfc0cf2c7bcb2a5cbaf");   
		listNan.add("http://imgzb.zhuangdianbi.com/576e3c2a0cf2c7bcb2a5cbef");   listNan.add("http://imgzb.zhuangdianbi.com/576e3c350cf2c7bcb2a5cbff");   
		listNan.add("http://imgzb.zhuangdianbi.com/576e3c410cf2c7bcb2a5cc11");   listNan.add("http://imgzb.zhuangdianbi.com/576e3c4d0cf2c7bcb2a5cc1f");   
		listNan.add("http://imgzb.zhuangdianbi.com/576e3c5b0cf2c7bcb2a5cc35");    listNan.add("http://imgzb.zhuangdianbi.com/576e3c660cf2c7bcb2a5cc48");   
		listNan.add("http://imgzb.zhuangdianbi.com/576e3c740cf2c7bcb2a5cc7a");   listNan.add("http://imgzb.zhuangdianbi.com/576e3c820cf2c7bcb2a5cc91");   
		listNan.add("http://imgzb.zhuangdianbi.com/576e3c8d0cf2c7bcb2a5cc9f");   listNan.add("http://imgzb.zhuangdianbi.com/576e3c970cf2c7bcb2a5ccaf");   
		listNan.add("http://imgzb.zhuangdianbi.com/576e3ca60cf2c7bcb2a5ccc8");   listNan.add("http://imgzb.zhuangdianbi.com/576e3cb10cf2c7bcb2a5ccdb");   
		listNan.add("http://imgzb.zhuangdianbi.com/576e3cbe0cf2c7bcb2a5ccf3");   listNan.add("http://imgzb.zhuangdianbi.com/576e3cc90cf2c7bcb2a5cd0e");   
		listNan.add("http://imgzb.zhuangdianbi.com/576e3cd40cf2c7bcb2a5cd1a");   listNan.add("http://imgzb.zhuangdianbi.com/576e3cdf0cf2c7bcb2a5cd2e");   
		listNan.add("http://imgzb.zhuangdianbi.com/576e3cea0cf2c7bcb2a5cd45"); 
		//随机5位男星
		Set<String> nanXingSet = new HashSet<String>();
		while(nanXingSet.size() < 5)
        {
			nanXingSet.add(listNan.get(rand.nextInt(listNan.size())));
        }
		
		Iterator<String> nanxing = nanXingSet.iterator();
		while (nanxing.hasNext()) {
		   listNan0.add((String) nanxing.next());
		   
		}
		//System.out.println(listNan0);
		
		List<String> listNv = new ArrayList<String>();
		List<String> listNv0 = new ArrayList<String>();
		listNv.add("http://imgzb.zhuangdianbi.com/576e3cfe0cf2c7bcb2a5cd61");    listNv.add("http://imgzb.zhuangdianbi.com/576e3d070cf2c7bcb2a5cd73");    
		listNv.add("http://imgzb.zhuangdianbi.com/576e3d160cf2c7bcb2a5cd81");   listNv.add("http://imgzb.zhuangdianbi.com/576e3d210cf2c7bcb2a5cd89");    
		listNv.add("http://imgzb.zhuangdianbi.com/576e3d2d0cf2c7bcb2a5cd9c");  listNv.add("http://imgzb.zhuangdianbi.com/576e3d380cf2c7bcb2a5cdab");   
		listNv.add("http://imgzb.zhuangdianbi.com/576e3d440cf2c7bcb2a5cdc1");  listNv.add("http://imgzb.zhuangdianbi.com/576e3d4e0cf2c7bcb2a5cdd3");   
		listNv.add("http://imgzb.zhuangdianbi.com/576e3d620cf2c7bcb2a5cdf6");   listNv.add("http://imgzb.zhuangdianbi.com/576e3d6c0cf2c7bcb2a5ce01");   
		listNv.add("http://imgzb.zhuangdianbi.com/576e3d790cf2c7bcb2a5ce15");   listNv.add("http://imgzb.zhuangdianbi.com/576e3d860cf2c7bcb2a5ce26");   
		listNv.add("http://imgzb.zhuangdianbi.com/576e3d960cf2c7bcb2a5ce41");   listNv.add("http://imgzb.zhuangdianbi.com/576e3da10cf2c7bcb2a5ce4e");   
		listNv.add("http://imgzb.zhuangdianbi.com/576e3dac0cf2c7bcb2a5ce81");   listNv.add("http://imgzb.zhuangdianbi.com/576e3db70cf2c7bcb2a5ce94");   
		listNv.add("http://imgzb.zhuangdianbi.com/576e3dc30cf2c7bcb2a5cea5");
		//随机5为女星
		
		Set<String> nvXingSet = new HashSet<String>();
		while(nvXingSet.size() < 3)
        {
			nvXingSet.add(listNv.get(rand.nextInt(listNv.size())));
        }
		
		Iterator<String> nvxing = nvXingSet.iterator();
		while (nvxing.hasNext()) {
			listNv0.add((String) nvxing.next());
		   
		}
		List<String> listNanGuanNan = new ArrayList<String>();
		List<String> listNanGuanNan0 = new ArrayList<String>();
		listNanGuanNan.add("搭档");  listNanGuanNan.add("好友");
		listNanGuanNan.add("死党");  listNanGuanNan.add("兄弟");
		listNanGuanNan.add("哥们");  listNanGuanNan.add("情敌");
		listNanGuanNan.add("发小");
		//随机5男男关系
		Set<String> nanNan = new HashSet<String>();
		while(nanNan.size() < 5)
        {
			nanNan.add(listNanGuanNan.get(rand.nextInt(listNanGuanNan.size())));
        }
		
		Iterator<String> nannan = nanNan.iterator();
		while (nannan.hasNext()) {
			listNanGuanNan0.add((String) nannan.next());
		   
		}
		
		List<String> listNanGuanNv = new ArrayList<String>();
		List<String> listNanGuanNv0 = new ArrayList<String>();
		listNanGuanNv.add("前女友");  listNanGuanNv.add("备胎");
		listNanGuanNv.add("绯闻");  listNanGuanNv.add("现女友");
		listNanGuanNv.add("密友");  listNanGuanNv.add("发小");
		//随机3为男女关系
		Set<String> nanNv = new HashSet<String>();
		while(nanNv.size() < 3)
        {
			nanNv.add(listNanGuanNv.get(rand.nextInt(listNanGuanNv.size())));
        }
		Iterator<String> nannv = nanNv.iterator();
		while (nannv.hasNext()) {
			listNanGuanNv0.add((String) nannv.next());
		   
		}
		
		List<String> listNvGuanNan = new ArrayList<String>();
		List<String> listNvGuanNan0 = new ArrayList<String>();
		listNvGuanNan.add("前男友");  listNvGuanNan.add("现男友");
		listNvGuanNan.add("绯闻");  listNvGuanNan.add("搭档");
		listNvGuanNan.add("密友");  listNvGuanNan.add("备胎");
		listNvGuanNan.add("发小");
		
		//随机5女男关系
		Set<String> nvNan = new HashSet<String>();
		while(nvNan.size() < 5)
        {
			nvNan.add(listNvGuanNan.get(rand.nextInt(listNvGuanNan.size())));
        }
		Iterator<String> nvnan = nvNan.iterator();
		while (nvnan.hasNext()) {
			listNvGuanNan0.add((String) nvnan.next());
		   
		}
		List<String> listNvGuanNv = new ArrayList<String>();
		List<String> listNvGuanNv0 = new ArrayList<String>();
		listNvGuanNv.add("死党");  listNvGuanNv.add("闺蜜");
		listNvGuanNv.add("发小");  listNvGuanNv.add("姐妹");
		listNvGuanNv.add("搭档");  listNvGuanNv.add("情敌");
		
		//随机3为女女关系
		Set<String> nvNv = new HashSet<String>();
		while(nvNv.size() < 3)
        {
			nvNv.add(listNvGuanNv.get(rand.nextInt(listNvGuanNv.size())));
        }
		Iterator<String> nvnv = nvNv.iterator();
		while (nvnv.hasNext()) {
			listNvGuanNv0.add((String) nvnv.next());
		   
		}
		
		List<String> list = new ArrayList<String>();
		for(int t = 0;t<one.length();t++){
			list.add(String.valueOf(one.charAt(t)));
		}
		SimplePositions nameSP =null;
		BufferedImage nameBI =null;
		if(list.size()==1){
			//姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(347).buildVerticalOffset(733);
			nameBI = drawText(one,zbfont,fontSize, color,
					240, 100, 0, true);
		}else if(list.size()==2){
			//姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(335).buildVerticalOffset(733);
			nameBI = drawText(one,zbfont,fontSize, color,
					240, 100, 0, true);
		}else{
			//姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(324).buildVerticalOffset(733);
			nameBI = drawText(one,zbfont,fontSize, color,
					240, 100, 0, true);
		}
		
		//遮罩
		BufferedImage zZhao = super.getImg(zheZhao);
		SimplePositions zSP = new SimplePositions();
		zSP.buildHorizontalOffset(283).buildVerticalOffset(728);
		
		//玩家传的照片
		
		BufferedImage p = super.getImg(two);
		p = ChangeImage.resizeCrop(p, 176);
		p = ChangeImage.makeRoundedCorner(p, 180);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(273).buildVerticalOffset(600);	
		
		// count
		int fontSize3 = 50;
		DecimalFormat df=new DecimalFormat("00000000");
		SimplePositions count0 = new SimplePositions();
		count0.buildHorizontalOffset(320).buildVerticalOffset(1182);
		BufferedImage countBI0 = drawText(df.format(Integer.parseInt(count)), ZbFont.黑体.font(),fontType, fontSize3, new Color(0,0,0),
				240, 0, 0, true);
		
		
		int fontSize2 = 24;
		
		SimplePositions nannang1 =null;
		BufferedImage nannang11 = null;
		SimplePositions nannant1 =null;
		BufferedImage nann1 =null;
		
		SimplePositions nannang2 =null;
		BufferedImage nannang22 = null;
		SimplePositions nannant2 =null;
		BufferedImage nann2 =null;
		
		SimplePositions nannang3 =null;
		BufferedImage nannang33 = null;
		SimplePositions nannant3 =null;
		BufferedImage nann3 =null;
		
		SimplePositions nannang4 =null;
		BufferedImage nannang44 = null;
		SimplePositions nannant4 =null;
		BufferedImage nann4 =null;
		
		SimplePositions nannang5 =null;
		BufferedImage nannang55 = null;
		SimplePositions nannant5 =null;
		BufferedImage nann5 =null;
		
		SimplePositions youg6 =null;
		BufferedImage youg66 = null;
		SimplePositions yout6 =null;
		BufferedImage yout66 =null;
		SimplePositions youg7 =null;
		BufferedImage youg77 = null;
		SimplePositions yout7 =null;
		BufferedImage yout77=null;
		SimplePositions youg8 =null;
		BufferedImage youg88 = null;
		SimplePositions yout8 =null;
		BufferedImage yout88 =null;
		
		if(three.equals(sex[0])){
			
			
			nannang1 = new SimplePositions();
			nannang1.buildHorizontalOffset(334).buildVerticalOffset(502);
			nannang11 = drawText(listNanGuanNan0.get(0), zbfont, fontSize2, new Color(0,0,0), 200, 100,
					0, true);
			
			// 男男图1
			nann1 = super.getImg(listNan0.get(0));
			nannant1 = new SimplePositions();
			nannant1.buildHorizontalOffset(300).buildVerticalOffset(330);
			
			// 男男关系2
			nannang2 = new SimplePositions();
			nannang2.buildHorizontalOffset(232).buildVerticalOffset(550);
			nannang22 = drawText(listNanGuanNan0.get(1), zbfont, fontSize2, new Color(0,0,0), 200, 100,
					0, true);
			// 男男图2
			nann2 = super.getImg(listNan0.get(1));
			nannant2 = new SimplePositions();
			nannant2.buildHorizontalOffset(103).buildVerticalOffset(417);
			
			// 男男关系3
			nannang3 = new SimplePositions();
			nannang3.buildHorizontalOffset(180).buildVerticalOffset(668);
			nannang33 = drawText(listNanGuanNan0.get(2), zbfont, fontSize2, new Color(0,0,0), 200, 100,
					0, true);
			// 男男图3
			nann3 = super.getImg(listNan0.get(2));
			nannant3 = new SimplePositions();
			nannant3.buildHorizontalOffset(18).buildVerticalOffset(631);
			
			// 男男关系4
			nannang4 = new SimplePositions();
			nannang4.buildHorizontalOffset(232).buildVerticalOffset(785);
			nannang44 = drawText(listNanGuanNan0.get(3), zbfont, fontSize2, new Color(0,0,0), 200, 100,
					0, true);
			// 男男图4
			nann4 = super.getImg(listNan0.get(3));
			nannant4 = new SimplePositions();
			nannant4.buildHorizontalOffset(102).buildVerticalOffset(841);
			
			// 男男关系5
			nannang5 = new SimplePositions();
			nannang5.buildHorizontalOffset(334).buildVerticalOffset(837);
			nannang55 = drawText(listNanGuanNan0.get(4), zbfont, fontSize2, new Color(0,0,0), 200, 100,
					0, true);
			// 男男图5
			nann5 = super.getImg(listNan0.get(4));
			nannant5 = new SimplePositions();
			nannant5.buildHorizontalOffset(300).buildVerticalOffset(931);
			//判断印象字数
			List<String> listy6 = new ArrayList<String>();
			
			for(int t = 0;t<listNanGuanNv0.get(0).length();t++){
				listy6.add(String.valueOf(listNanGuanNv0.get(0).charAt(0)));
			}
			if(listy6.size()==2){
				// 男女关系6
				youg6 = new SimplePositions();
				youg6.buildHorizontalOffset(442).buildVerticalOffset(551);
				youg66 = drawText(listNanGuanNv0.get(0), zbfont, fontSize2, new Color(0,0,0), 200, 100,
						0, true);
			}else{
				// 男女关系6
				youg6 = new SimplePositions();
				youg6.buildHorizontalOffset(429).buildVerticalOffset(551);
				youg66 = drawText(listNanGuanNv0.get(0), zbfont, fontSize2, new Color(0,0,0), 200, 100,
						0, true);
			}
			
			// 男女图6
			yout66 = super.getImg(listNv0.get(0));
			yout6 = new SimplePositions();
			yout6.buildHorizontalOffset(502).buildVerticalOffset(417);
			//判断印象字数
			List<String> listy7 = new ArrayList<String>();
			
			for(int t = 0;t<listNanGuanNv0.get(1).length();t++){
				listy7.add(String.valueOf(listNanGuanNv0.get(1).charAt(0)));
			}
			if(listy7.size()==2){
				// 男女关系7
				youg7 = new SimplePositions();
				youg7.buildHorizontalOffset(495).buildVerticalOffset(670);
				youg77 = drawText(listNanGuanNv0.get(1), zbfont, fontSize2, new Color(0,0,0), 200, 100,
						0, true);
			}else{
				// 男女关系7
				youg7 = new SimplePositions();
				youg7.buildHorizontalOffset(483).buildVerticalOffset(670);
				youg77 = drawText(listNanGuanNv0.get(1), zbfont, fontSize2, new Color(0,0,0), 200, 100,
						0, true);
			}
			
			// 男女图7
			yout77 = super.getImg(listNv0.get(1));
			yout7 = new SimplePositions();
			yout7.buildHorizontalOffset(583).buildVerticalOffset(631);
			
			
			//判断印象字数
			List<String> listy8 = new ArrayList<String>();
			
			for(int t = 0;t<listNanGuanNv0.get(2).length();t++){
				listy8.add(String.valueOf(listNanGuanNv0.get(2).charAt(0)));
			}
			if(listy8.size()==2){
				// 男女关系8
				youg8 = new SimplePositions();
				youg8.buildHorizontalOffset(450).buildVerticalOffset(786);
				youg88 = drawText(listNanGuanNv0.get(2), zbfont, fontSize2, new Color(0,0,0), 200, 100,
						0, true);
			}else{
				// 男女关系8
				youg8 = new SimplePositions();
				youg8.buildHorizontalOffset(439).buildVerticalOffset(786);
				youg88 = drawText(listNanGuanNv0.get(2), zbfont, fontSize2, new Color(0,0,0), 200, 100,
						0, true);
			}
			
			// 男女图8
			yout88 = super.getImg(listNv0.get(2));
			yout8 = new SimplePositions();
			yout8.buildHorizontalOffset(508).buildVerticalOffset(841);
			
			
		}else{
			//判断印象字数
			List<String> listy1 = new ArrayList<String>();
			
			for(int t = 0;t<listNvGuanNan0.get(0).length();t++){
				listy1.add(String.valueOf(listNvGuanNan0.get(0).charAt(0)));
			}
			if(listy1.size()==2){
				// 男男关系1
				nannang1 = new SimplePositions();
				nannang1.buildHorizontalOffset(335).buildVerticalOffset(502);
				nannang11 = drawText(listNvGuanNan0.get(0), zbfont, fontSize2, new Color(0,0,0), 200, 100,
						0, true);
			}else{
				// 男男关系1
				nannang1 = new SimplePositions();
				nannang1.buildHorizontalOffset(325).buildVerticalOffset(502);
				nannang11 = drawText(listNvGuanNan0.get(0), zbfont, fontSize2, new Color(0,0,0), 200, 100,
						0, true);
			}
			
			// 男男图1
			nann1 = super.getImg(listNan0.get(0));
			nannant1 = new SimplePositions();
			nannant1.buildHorizontalOffset(300).buildVerticalOffset(330);
			
			//判断印象字数
			List<String> listy2 = new ArrayList<String>();
			
			for(int t = 0;t<listNvGuanNan0.get(1).length();t++){
				listy2.add(String.valueOf(listNvGuanNan0.get(1).charAt(0)));
			}
			if(listy2.size()==2){
				// 男男关系2
				nannang2 = new SimplePositions();
				nannang2.buildHorizontalOffset(230).buildVerticalOffset(550);
				nannang22 = drawText(listNvGuanNan0.get(1), zbfont, fontSize2, new Color(0,0,0), 200, 100,
						0, true);
			}else{
				// 男男关系2
				nannang2 = new SimplePositions();
				nannang2.buildHorizontalOffset(221).buildVerticalOffset(550);
				nannang22 = drawText(listNvGuanNan0.get(1), zbfont, fontSize2, new Color(0,0,0), 200, 100,
						0, true);
			}
			
			// 男男图2
			nann2 = super.getImg(listNan0.get(1));
			nannant2 = new SimplePositions();
			nannant2.buildHorizontalOffset(103).buildVerticalOffset(417);
			
			//判断印象字数
			List<String> listy3 = new ArrayList<String>();
			
			for(int t = 0;t<listNvGuanNan0.get(2).length();t++){
				listy3.add(String.valueOf(listNvGuanNan0.get(2).charAt(0)));
			}
			if(listy3.size()==2){
				// 男男关系3
				nannang3 = new SimplePositions();
				nannang3.buildHorizontalOffset(182).buildVerticalOffset(669);
				nannang33 = drawText(listNvGuanNan0.get(2), zbfont, fontSize2, new Color(0,0,0), 200, 100,
						0, true);
			}else{
				// 男男关系3
				nannang3 = new SimplePositions();
				nannang3.buildHorizontalOffset(172).buildVerticalOffset(669);
				nannang33 = drawText(listNvGuanNan0.get(2), zbfont, fontSize2, new Color(0,0,0), 200, 100,
						0, true);
			}
			
			// 男男图3
			nann3 = super.getImg(listNan0.get(2));
			nannant3 = new SimplePositions();
			nannant3.buildHorizontalOffset(18).buildVerticalOffset(631);
			//判断印象字数
			List<String> listy4 = new ArrayList<String>();
			
			for(int t = 0;t<listNvGuanNan0.get(3).length();t++){
				listy4.add(String.valueOf(listNvGuanNan0.get(3).charAt(0)));
			}
			if(listy4.size()==2){
				// 男男关系4
				nannang4 = new SimplePositions();
				nannang4.buildHorizontalOffset(230).buildVerticalOffset(785);
				nannang44 = drawText(listNvGuanNan0.get(3), zbfont, fontSize2, new Color(0,0,0), 200, 100,
						0, true);
			}else{
				// 男男关系4
				nannang4 = new SimplePositions();
				nannang4.buildHorizontalOffset(220).buildVerticalOffset(785);
				nannang44 = drawText(listNvGuanNan0.get(3), zbfont, fontSize2, new Color(0,0,0), 200, 100,
						0, true);
			}
			
			// 男男图4
			nann4 = super.getImg(listNan0.get(3));
			nannant4 = new SimplePositions();
			nannant4.buildHorizontalOffset(102).buildVerticalOffset(841);
			//判断印象字数
			List<String> listy5 = new ArrayList<String>();
			
			for(int t = 0;t<listNvGuanNan0.get(4).length();t++){
				listy5.add(String.valueOf(listNvGuanNan0.get(4).charAt(0)));
			}
			if(listy5.size()==2){
				// 男男关系5
				nannang5 = new SimplePositions();
				nannang5.buildHorizontalOffset(335).buildVerticalOffset(838);
				nannang55 = drawText(listNvGuanNan0.get(4), zbfont, fontSize2, new Color(0,0,0), 200, 100,
						0, true);
			}else{
				// 男男关系5
				nannang5 = new SimplePositions();
				nannang5.buildHorizontalOffset(324).buildVerticalOffset(838);
				nannang55 = drawText(listNvGuanNan0.get(4), zbfont, fontSize2, new Color(0,0,0), 200, 100,
						0, true);
			}
			
			// 男男图5
			nann5 = super.getImg(listNan0.get(4));
			nannant5 = new SimplePositions();
			nannant5.buildHorizontalOffset(300).buildVerticalOffset(931);
			
			// 男女关系6
			youg6 = new SimplePositions();
			youg6.buildHorizontalOffset(443).buildVerticalOffset(551);
			youg66 = drawText(listNvGuanNv0.get(0), zbfont, fontSize2, new Color(0,0,0), 200, 100,
					0, true);
			// 男女图6
			yout66 = super.getImg(listNv0.get(0));
			yout6 = new SimplePositions();
			yout6.buildHorizontalOffset(502).buildVerticalOffset(417);
			
			// 男女关系7
			youg7 = new SimplePositions();
			youg7.buildHorizontalOffset(495).buildVerticalOffset(670);
			youg77 = drawText(listNvGuanNv0.get(1), zbfont, fontSize2, new Color(0,0,0), 200, 100,
					0, true);
			// 男女图7
			yout77 = super.getImg(listNv0.get(1));
			yout7 = new SimplePositions();
			yout7.buildHorizontalOffset(583).buildVerticalOffset(631);
			
			// 男女关系8
			youg8 = new SimplePositions();
			youg8.buildHorizontalOffset(450).buildVerticalOffset(785);
			youg88 = drawText(listNvGuanNv0.get(2), zbfont, fontSize2, new Color(0,0,0), 200, 100,
					0, true);
			// 男女图8
			yout88 = super.getImg(listNv0.get(2));
			yout8 = new SimplePositions();
			yout8.buildHorizontalOffset(508).buildVerticalOffset(841);
		}
		SimplePositions[] sp = {pSP,zSP, nameSP,nannang1,nannant1,
				nannang2,nannant2,nannang3,nannant3,nannang4,nannant4,nannang5,nannant5,
				youg6,yout6,youg7,yout7,youg8,yout8,count0};

		BufferedImage[] bis = {p,zZhao, nameBI,nannang11,nann1,
				nannang22,nann2,nannang33,nann3,nannang44,nann4,nannang55,nann5,
				youg66,yout66,youg77,yout77,youg88,yout88,countBI0};

		return super.getSaveFile(sp, bis, 0.95f, tmpBackPic);
	}

}
