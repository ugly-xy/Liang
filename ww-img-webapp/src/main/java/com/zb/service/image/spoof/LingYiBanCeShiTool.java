package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

import com.zb.common.utils.PinYinUtil;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.ThreeDraw;

public class LingYiBanCeShiTool extends BaseTool implements ThreeDraw{
	public LingYiBanCeShiTool(StorageService storageService) {
		super(storageService);
	}

	public LingYiBanCeShiTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/578e0b860cf28afffe4dcdc5";
		StorageService storageService = new StorageService();
		LingYiBanCeShiTool tyt = new LingYiBanCeShiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("都点","男","卧ca", tmpPath0,"8888"));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 22;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	static String zhangDe[] = {"长的很像","长的就是"};
	
	static String nanMing[] = {"钟汉良",
								"奥特曼",
								"猪八戒",
								"竟然马赛克",
								"其中一个",
								"陈冠希",
								"拉登",
								"哟，神秘",
								"王思聪",
								"宋仲基",
								"胡歌"};
	static String nanTu[] = {"http://imgzb.zhuangdianbi.com/578deac20cf26600d9b1e54b",
							"http://imgzb.zhuangdianbi.com/578dead80cf26600d9b1e560",
							"http://imgzb.zhuangdianbi.com/578deae70cf26600d9b1e56e",
							"http://imgzb.zhuangdianbi.com/578deaf70cf26600d9b1e582",
							"http://imgzb.zhuangdianbi.com/578deb010cf26600d9b1e594",
							"http://imgzb.zhuangdianbi.com/578deb0d0cf26600d9b1e5a2",
							"http://imgzb.zhuangdianbi.com/578deb180cf26600d9b1e5ae",
							"http://imgzb.zhuangdianbi.com/578deb230cf26600d9b1e5bf",
							"http://imgzb.zhuangdianbi.com/578deb2e0cf26600d9b1e5cc",
							"http://imgzb.zhuangdianbi.com/578df02f0cf26600d9b1eb71",
							"http://imgzb.zhuangdianbi.com/578df0450cf26600d9b1eb85"};
	static String nanPing[] = {"虽然他已经37岁了！",
								"不要外貌协会了，他很能打！",
								"温柔体贴，感情专业，懂得情趣，做老公再适合不过了！",
								"为了保护你的眼睛不受到严重创伤，系统将不显示结果。",
								"你的另一半太过于庞大，系统无法准确找出结果！",
								"请问这相机是什么牌子的？",
								"随他一起殉葬吧！",
								"奥，手机，你真调皮！",
								"此人是谁，看起来好面熟啊，是个演员吧？",
								"你先别激动，让我先激动一会儿！",
								"测到他的可是为数不多哟，你心动了吗？"};
	
	static String nvMing[] = {"烈火奶奶",
							"贞子",
							"POY",
							"樱冢澈",
							"女奥特曼",
							"憨豆版白娘子",
							"如花版白娘子",
							"某非主流MM",
							"樱桃小丸子",
							"如花",
							"包租婆",
							"郭芙蓉",
							"某电影中的莫文蔚",
							"益达广告美女",
							"苍井空",
							"其中一个",
							"林志玲"};
	static String nvTu[] = {"http://imgzb.zhuangdianbi.com/578deb3f0cf26600d9b1e5e8",
							"http://imgzb.zhuangdianbi.com/578deb4c0cf26600d9b1e5f5",
							"http://imgzb.zhuangdianbi.com/578deb720cf26600d9b1e628",
							"http://imgzb.zhuangdianbi.com/578deb7d0cf26600d9b1e63b",
							"http://imgzb.zhuangdianbi.com/578deb890cf26600d9b1e64a",
							"http://imgzb.zhuangdianbi.com/578deb940cf26600d9b1e659",
							"http://imgzb.zhuangdianbi.com/578deb9f0cf26600d9b1e669",
							"http://imgzb.zhuangdianbi.com/578deba90cf26600d9b1e673",
							"http://imgzb.zhuangdianbi.com/578debb40cf26600d9b1e683",
							"http://imgzb.zhuangdianbi.com/578debbf0cf26600d9b1e694",
							"http://imgzb.zhuangdianbi.com/578debcc0cf26600d9b1e6a3",
							"http://imgzb.zhuangdianbi.com/578debd90cf26600d9b1e6bc",
							"http://imgzb.zhuangdianbi.com/578debe30cf26600d9b1e6cd",
							"http://imgzb.zhuangdianbi.com/578debed0cf26600d9b1e6da",
							"http://imgzb.zhuangdianbi.com/578defa90cf26600d9b1eaf0",
							"http://imgzb.zhuangdianbi.com/578df2810cf26600d9b1edf9",
							"http://imgzb.zhuangdianbi.com/578df2a00cf26600d9b1ee19"};
	static String nvPing[] = {"其实...有些东西比生命更重要！",
							"什么，你说你想看清她长什么样？",
							"他是网友公认的最美泰国人妖！",
							"此人性别为男！他是网友公认的最美伪娘之一！",
							"不要理会世俗的眼光，外星人怎么了，眼睛大，胸也大！",
							"怎么看都觉得怪怪的",
							"白娘子为什么长了胡子",
							"哦，我的眼睛！我的眼睛！",
							"这还是婴儿版的小丸子，不怕，你可以等她长大嘛！",
							"此人乃人间难得极品，另外，她可以帮你抠鼻屎哦！",
							"她一定不会让你在外头受欺负的！",
							"兄弟，保重！",
							"你最好从了她！",
							"恭喜你，全国只有0.01%的人能抽到她。",
							"拣到便宜了吧，她可是许多男人心中的偶像！",
							"机缘太多了，说不定都是呢！",
							"看来你小子有福气了！"};
	
	@Override
	public String draw(String one, String two,String three, String tmpBackPic, String count) throws IOException {
	
		//姓名
		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(240-(one.length()*30/2)).buildVerticalOffset(8);
 		BufferedImage nameBi = drawText(one, zbfont, 30,
 				new Color(102,0,0), 240, 0, 0, true);
 		
 		//未来身价
		DecimalFormat df=new DecimalFormat("00000000");
		SimplePositions count0 = new SimplePositions();
		count0.buildHorizontalOffset(228).buildVerticalOffset(698);
		BufferedImage countBI0 = drawText(df.format(Integer.parseInt(count)), ZbFont.黑体, 33, color,
				300, 0, 0, true);
 		
 		Random ran = new Random();
 		String one0 = PinYinUtil.toPinyin(three);
 		int n = one0.length()+34;
 		//选定长得像 还是长的就是
 		int m = zhangDe.length;
 		int m2 = ran.nextInt(m);
 		//选定某个图
 		int num =2;
 		SimplePositions nameSP1 =null;
 		SimplePositions nameSP2 =null;
 		SimplePositions nameSP3 =null;
 		SimplePositions nameSP4 =null;
 		
 		BufferedImage nameBi1 = null;
 		BufferedImage nameBi2 = null;
 		BufferedImage nameBi3 = null;
 		BufferedImage nameBi4 = null;
 		if(two.equals("男")){
 			num = (n+1314)%nvMing.length;
 			
 			nameSP1 = new SimplePositions();
 	 		nameSP1.buildHorizontalOffset(240-((zhangDe[m2].length()+nvMing[num].length())*fontSize/2)-30).buildVerticalOffset(180);
 	 		nameBi1 = drawText(zhangDe[m2], ZbFont.黑体, fontSize,
 	 				new Color(0,0,0), 240, 0, 0, true);
 			
 			//生成姓名
 			nameSP2 = new SimplePositions();
 	 		nameSP2.buildHorizontalOffset(240-((zhangDe[m2].length()+nvMing[num].length())*fontSize/2)+70).buildVerticalOffset(172);
 	 		nameBi2 = drawText(nvMing[num], ZbFont.楷体常规, 30,
 	 				new Color(127,15,13), 300, 0, 0, true);
 	 		
 	 		nameBi3 = super.getImg(nvTu[num]);
 	 		nameSP3 = new SimplePositions();
 	 		nameSP3.buildHorizontalOffset(53).buildVerticalOffset(219);
 	 		
 	 		nameSP4 = new SimplePositions();
 	 		nameSP4.buildHorizontalOffset(60).buildVerticalOffset(570);
 	 		nameBi4 = drawText(nvPing[num], ZbFont.黑体, 20,
 	 				new Color(0,0,0), 350, 0, 0, true);
 	 		
 		}else {
 			
 			num = (n+1313)%((nanMing.length));
 			
 			nameSP1 = new SimplePositions();
 	 		nameSP1.buildHorizontalOffset(240-((zhangDe[m2].length()+nvMing[num].length())*fontSize/2)-30).buildVerticalOffset(180);
 	 		nameBi1 = drawText(zhangDe[m2], ZbFont.黑体, fontSize,
 	 				new Color(0,0,0), 240, 0, 0, true);
 			
 			//生成姓名
 			nameSP2 = new SimplePositions();
 	 		nameSP2.buildHorizontalOffset(240-((zhangDe[m2].length()+nvMing[num].length())*fontSize/2)+70).buildVerticalOffset(172);
 	 		nameBi2 = drawText(nanMing[num], ZbFont.楷体常规, 30,
 	 				new Color(127,15,13), 300, 0, 0, true);
 	 		
 	 		nameBi3 = super.getImg(nanTu[num]);
 	 		nameSP3 = new SimplePositions();
 	 		nameSP3.buildHorizontalOffset(53).buildVerticalOffset(219);
 	 		
 	 		nameSP4 = new SimplePositions();
 	 		nameSP4.buildHorizontalOffset(60).buildVerticalOffset(570);
 	 		nameBi4 = drawText(nanPing[num], ZbFont.黑体, 20,
 	 				new Color(0,0,0), 350, 0, 0, true);
 		}
 		
 		SimplePositions[] sp = { nameSP,nameSP1,nameSP2,nameSP3,nameSP4,count0};

		BufferedImage[] bis = { nameBi,nameBi1,nameBi2,nameBi3,nameBi4,countBI0};

		return super.getSaveFile(sp, bis, 0.9f, tmpBackPic);
	}

}
