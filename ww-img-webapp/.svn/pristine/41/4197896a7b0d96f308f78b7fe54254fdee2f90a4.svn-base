package com.zb.service.image.genius;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class WeiLaiLingYiBanTool extends BaseTool implements TwoDraw{
	public WeiLaiLingYiBanTool(StorageService storageService) {
		super(storageService);
	}

	public WeiLaiLingYiBanTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/581811900cf2828dc371721c";
		StorageService storageService = new StorageService();
		WeiLaiLingYiBanTool tyt = new WeiLaiLingYiBanTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装逼","男",tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 26;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	
	static String nanyou[] = {"http://imgzb.zhuangdianbi.com/581811900cf2828dc371721c",
								"http://imgzb.zhuangdianbi.com/5818119a0cf2828dc371721e",
								"http://imgzb.zhuangdianbi.com/581811a40cf2828dc3717221",
								"http://imgzb.zhuangdianbi.com/581811ae0cf2828dc3717223",
								"http://imgzb.zhuangdianbi.com/581811b90cf2828dc3717224",
								"http://imgzb.zhuangdianbi.com/581811c30cf2828dc3717229",
								"http://imgzb.zhuangdianbi.com/581811cf0cf2828dc371722d",
								"http://imgzb.zhuangdianbi.com/581811da0cf2828dc371722e",
								"http://imgzb.zhuangdianbi.com/581811e40cf2828dc3717230",
								"http://imgzb.zhuangdianbi.com/581811ed0cf2828dc3717231",
								"http://imgzb.zhuangdianbi.com/581811f70cf2828dc3717234",
								"http://imgzb.zhuangdianbi.com/581812000cf2828dc3717235"};
	static String nvyou[] = {"http://imgzb.zhuangdianbi.com/581810e30cf2828dc37171f6",
							"http://imgzb.zhuangdianbi.com/581810f00cf2828dc37171f8",
							"http://imgzb.zhuangdianbi.com/581810fd0cf2828dc37171fd",
							"http://imgzb.zhuangdianbi.com/5818110a0cf2828dc3717200",
							"http://imgzb.zhuangdianbi.com/581811150cf2828dc3717203",
							"http://imgzb.zhuangdianbi.com/581811240cf2828dc3717206",
							"http://imgzb.zhuangdianbi.com/5818112e0cf2828dc371720a",
							"http://imgzb.zhuangdianbi.com/581812b50cf2828dc371724a",
							"http://imgzb.zhuangdianbi.com/581811560cf2828dc371720d",
							"http://imgzb.zhuangdianbi.com/581811610cf2828dc371720e",
							"http://imgzb.zhuangdianbi.com/5818116d0cf2828dc3717210",
							"http://imgzb.zhuangdianbi.com/5818117e0cf2828dc3717217"};
	
	static String nanMing[] = {"赵志远","赵雨博","赵文岳","赵建伟","张子辰","张江","张旭辉","李川",
								"李洋","李斌","孙炜程","孙彦云","孙晨皓","孟一凡","孟凡翔","孟雷","周红杰","周兴旺","周子建",
								"王思卓","王伟","王锐","郑少臣","郑清华","郑鹏远","郑子航"};
	
	static String nvMing[] = {"张晨曦","张雨菡","张萍","李慧","李倩雪","李诗琪","赵艺丹","赵梦婕",
								"赵家芸","孙雨彤","孙豆豆","孙美伊","孟思佳","孟盈盈","孟圆圆","周睿","周梦瑶","周紫怡",
								"王语萱","王绮梦","王雯","郑颖","郑智佳","郑双双","郑多多"};
	
	static String juzhu[] = {"北京","上海","南京","广州","武汉","重庆","深圳","成都",
							"济南","厦门","昆明","新加坡","威尼斯","伦敦","西班牙","维也纳","德国",
							"芬兰","法国","美国","巴西","波兰","瑞士","瑞典","苏丹","印度尼西亚"
							};
	
	static String xiangyu[] = {"美国大峡谷","印度金庙","埃及金字塔","印度泰姬陵","英国巨石阵","拉什莫尔山","圣托里尼岛","美国好莱坞",
								"圣家族大教堂","勃兰登堡门","日本富士山","北京故宫","中国长城 ","喜马拉雅山 ","艾菲尔铁塔","巴黎圣母院","白金汉宫"};
	
	static String shijian[] = {"2017","2018","2019","2020","2021","2022","2023","2024","2025"};
	
	static String nianling[] = {"15","14","12","13","16","17","18","19","20"};
	
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		
		Random ran = new Random();
		
		int yue = ran.nextInt(12)+1;
		int ri = ran.nextInt(28)+1;
		if(two.equals("女")){
			
			//自己姓名
			SimplePositions nameSP = new SimplePositions();
	 		nameSP.buildHorizontalOffset(250-(one.length()+10)*fontSize/2).buildVerticalOffset(18);
	 		BufferedImage nameBi = drawText(one+"  未来老公的个人信息", zbfont, fontSize,
	 				new Color(255,255,255), 380, 0, 0, true);
			String nanming = nanMing[ran.nextInt(nanMing.length)];
	 		//另一半姓名
	 		SimplePositions nameSP2 = new SimplePositions();
	 		nameSP2.buildHorizontalOffset(250-(nanming.length()+3)*fontSize/2).buildVerticalOffset(290);
	 		BufferedImage nameBi2 = drawText("老公  "+nanming, zbfont, fontSize,
	 				new Color(255,255,255), 380, 0, 0, true);
	 		
	 		//年龄
	 		String nian = nianling[ran.nextInt(nianling.length)];
	 		SimplePositions nameSP8 = new SimplePositions();
	 		nameSP8.buildHorizontalOffset(340).buildVerticalOffset(210);
	 		BufferedImage nameBi8 = drawText(nian+"岁", zbfont, 30,
	 				new Color(0,0,0), 380, 0, 0, true);
	 		//居住地
	 		SimplePositions nameSP4 = new SimplePositions();
	 		nameSP4.buildHorizontalOffset(241).buildVerticalOffset(344);
	 		BufferedImage nameBi4 = drawText("现居住地", zbfont, 20,
	 				new Color(0,0,0), 380, 0, 0, true);
	 		//居住地
	 		String juzhudi = juzhu[ran.nextInt(juzhu.length)];
	 		SimplePositions nameSP3 = new SimplePositions();
	 		nameSP3.buildHorizontalOffset(325).buildVerticalOffset(346);
	 		BufferedImage nameBi3 = drawText(juzhudi, ZbFont.华康少女, 20,
	 				new Color(254,133,45), 380, 0, 0, true);
	 		//时间
	 		String shi = shijian[ran.nextInt(shijian.length)]+"年"+yue+"月"+ri+"日你们将会在";
	 		SimplePositions nameSP5 = new SimplePositions();
	 		nameSP5.buildHorizontalOffset(12).buildVerticalOffset(420);
	 		BufferedImage nameBi5 = drawText(shi, zbfont, 20,
	 				new Color(0,0,0), 380, 0, 0, true);
	 		//相遇
	 		String xiang = xiangyu[ran.nextInt(xiangyu.length)]+" 相遇";
	 		SimplePositions nameSP6 = new SimplePositions();
	 		nameSP6.buildHorizontalOffset(250-(xiang.length()+2)*20/2).buildVerticalOffset(446);
	 		BufferedImage nameBi6 = drawText(xiang, zbfont, 20,
	 				new Color(255,255,255), 380, 0, 0, true);
	 		//我是
	 		
	 		SimplePositions nameSP7 = new SimplePositions();
	 		nameSP7.buildHorizontalOffset(418-(one.length()+3)*20/2).buildVerticalOffset(444);
	 		BufferedImage nameBi7 = drawText("我是 "+one, zbfont, 20,
	 				new Color(254,133,45), 380, 0, 0, true);
	 		
	 		tmpBackPic = nanyou[ran.nextInt(nanyou.length)];
	 		
	 		SimplePositions[] sp = {nameSP,nameSP2,nameSP3,nameSP4,nameSP5,nameSP6,nameSP7,nameSP8};

			BufferedImage[] bis = { nameBi,nameBi2,nameBi3,nameBi4,nameBi5,nameBi6,nameBi7,nameBi8};

			return super.getSaveFile(sp, bis, 1f, tmpBackPic);
			
		}
		
		//自己姓名
		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(250-(one.length()+10)*fontSize/2).buildVerticalOffset(18);
 		BufferedImage nameBi = drawText(one+"  未来老婆的个人信息", zbfont, fontSize,
 				new Color(255,255,255), 380, 0, 0, true);
		String nanming = nvMing[ran.nextInt(nvMing.length)];
 		//另一半姓名
 		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(250-(nanming.length()+3)*fontSize/2).buildVerticalOffset(290);
 		BufferedImage nameBi2 = drawText("老婆  "+nanming, zbfont, fontSize,
 				new Color(255,255,255), 380, 0, 0, true);
 		
 		//年龄
 		String nian = nianling[ran.nextInt(nianling.length)];
 		SimplePositions nameSP8 = new SimplePositions();
 		nameSP8.buildHorizontalOffset(340).buildVerticalOffset(210);
 		BufferedImage nameBi8 = drawText(nian+"岁", zbfont, 30,
 				new Color(0,0,0), 380, 0, 0, true);
 		
 		//居住地
 		SimplePositions nameSP4 = new SimplePositions();
 		nameSP4.buildHorizontalOffset(241).buildVerticalOffset(344);
 		BufferedImage nameBi4 = drawText("现居住地", zbfont, 20,
 				new Color(0,0,0), 380, 0, 0, true);
 		//居住地
 		String juzhudi = juzhu[ran.nextInt(juzhu.length)];
 		SimplePositions nameSP3 = new SimplePositions();
 		nameSP3.buildHorizontalOffset(325).buildVerticalOffset(346);
 		BufferedImage nameBi3 = drawText(juzhudi, ZbFont.华康少女, 20,
 				new Color(254,133,45), 380, 0, 0, true);
 		//时间
 		String shi = shijian[ran.nextInt(shijian.length)]+"年"+yue+"月"+ri+"日你们将会在";
 		SimplePositions nameSP5 = new SimplePositions();
 		nameSP5.buildHorizontalOffset(12).buildVerticalOffset(420);
 		BufferedImage nameBi5 = drawText(shi, zbfont, 20,
 				new Color(0,0,0), 380, 0, 0, true);
 		//相遇
 		String xiang = xiangyu[ran.nextInt(xiangyu.length)]+" 相遇";
 		SimplePositions nameSP6 = new SimplePositions();
 		nameSP6.buildHorizontalOffset(250-(xiang.length()+2)*20/2).buildVerticalOffset(446);
 		BufferedImage nameBi6 = drawText(xiang, zbfont, 20,
 				new Color(255,255,255), 380, 0, 0, true);
 		//我是
 		
 		SimplePositions nameSP7 = new SimplePositions();
 		nameSP7.buildHorizontalOffset(418-(one.length()+3)*20/2).buildVerticalOffset(444);
 		BufferedImage nameBi7 = drawText("我是 "+one, zbfont, 20,
 				new Color(254,133,45), 380, 0, 0, true);
 		
 		tmpBackPic = nvyou[ran.nextInt(nvyou.length)];
 		
 		SimplePositions[] sp = {nameSP,nameSP2,nameSP3,nameSP4,nameSP5,nameSP6,nameSP7,nameSP8};

		BufferedImage[] bis = { nameBi,nameBi2,nameBi3,nameBi4,nameBi5,nameBi6,nameBi7,nameBi8};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
		
	}

}
