package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.zb.image.ChangeImage;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class WeiLaiCeShiJiTool extends BaseTool implements TwoDraw{
	
	public WeiLaiCeShiJiTool(StorageService storageService) {
		super(storageService);
	}

	public WeiLaiCeShiJiTool() {
	}

	public static void main(String[] args) throws IOException {
		// String tmpPath0 =
		// "http://imgzb.zhuangdianbi.com/5732a2a80cf2d5342be19ca8";
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57778fa50cf20de9530ec694";
		StorageService storageService = new StorageService();
		WeiLaiCeShiJiTool tyt = new WeiLaiCeShiJiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","测试风景.png", tmpPath0,null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 45;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.ITALIC;
	
	static final String[] S1 ={"http://imgzb.zhuangdianbi.com/57778eb70cf20de9530ec557",
								"http://imgzb.zhuangdianbi.com/57778ec80cf20de9530ec56f",
								"http://imgzb.zhuangdianbi.com/57778ef00cf20de9530ec5a2",
								"http://imgzb.zhuangdianbi.com/57778f120cf20de9530ec5c8",
								"http://imgzb.zhuangdianbi.com/57778f200cf20de9530ec5dd",
								"http://imgzb.zhuangdianbi.com/57778f2a0cf20de9530ec5eb",
								"http://imgzb.zhuangdianbi.com/5779c5c20cf26acda3d8d16e",
								"http://imgzb.zhuangdianbi.com/57778f490cf20de9530ec612",
								"http://imgzb.zhuangdianbi.com/57778f690cf20de9530ec639",
								"http://imgzb.zhuangdianbi.com/57778f7d0cf20de9530ec65a"};
	static final String[] S2 ={"http://imgzb.zhuangdianbi.com/57778ea90cf20de9530ec53e",
								"http://imgzb.zhuangdianbi.com/57778ed80cf20de9530ec581",
								"http://imgzb.zhuangdianbi.com/57778ee50cf20de9530ec594",
								"http://imgzb.zhuangdianbi.com/57778efb0cf20de9530ec5ad",
								"http://imgzb.zhuangdianbi.com/57778f070cf20de9530ec5b9",
								"http://imgzb.zhuangdianbi.com/57778f350cf20de9530ec5fe",
								"http://imgzb.zhuangdianbi.com/57778f560cf20de9530ec61b",
								"http://imgzb.zhuangdianbi.com/57778f5f0cf20de9530ec629",
								"http://imgzb.zhuangdianbi.com/57778f730cf20de9530ec649",
								"http://imgzb.zhuangdianbi.com/57778f860cf20de9530ec667"};
	@Override
	public String draw(String one,String two, String tmpBackPic, String count) throws IOException {
		
		int S1Len = S1.length;
		int S2Len = S2.length;
		
		
		int length = one.length();
		SimplePositions nameSP = null;
		BufferedImage nameBI = null;
		if(length==1){
			// 姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(420).buildVerticalOffset(168);
			nameBI = drawText(one, zbfont, fontSize, color,
					240, 200, -0.5, true,30,30);
		}else if(length==2){
			// 姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(420).buildVerticalOffset(158);
			nameBI = drawText(one, zbfont, fontSize, color,
					240, 200, -0.5, true,30,30);
		}else{
			// 姓名
			nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(420).buildVerticalOffset(143);
			nameBI = drawText(one, zbfont, fontSize, color,
					240, 200, -0.5, true,30,30);
		}
		Random r = ThreadLocalRandom.current();
		int max = 100;
		int v = r.nextInt(max);
		int ps = 0;
		int ps2 =0;
		if (v < 10) {
			ps = r.nextInt(41);
			ps2 = r.nextInt(41);
		} else if (v < 20) {
			ps = r.nextInt(31) + 40;
			ps2 = r.nextInt(31) + 40;
		} else if (v < 50) {
			ps = r.nextInt(21) + 70;
			ps2 = r.nextInt(21) + 70;
		} else {
			ps = r.nextInt(11) + 90;
			ps2 = r.nextInt(11) + 90;
		}
		
		// 花心指数
		SimplePositions toSP =null;
		BufferedImage toBI =null;
		if(ps==100){
			toSP = new SimplePositions();
			toSP.buildHorizontalOffset(550).buildVerticalOffset(194);
			toBI = drawText(ps+"%", zbfont, fontSize, color, 400,
					200, -0.5, true,30,30);
		}else{
			toSP = new SimplePositions();
			toSP.buildHorizontalOffset(550).buildVerticalOffset(208);
			toBI = drawText(ps+"%", zbfont, fontSize, color, 400,
					200, -0.5, true,30,30);
		}
		
		
		// 幸运指数
		SimplePositions toSP2 =null;
		BufferedImage toBI2 =null;
		if(ps2==100){
			toSP2 = new SimplePositions();
			toSP2.buildHorizontalOffset(590).buildVerticalOffset(303);
			toBI2 = drawText(ps2+"%", zbfont, fontSize, color, 400,
					200, -0.5, true,30,30);
		}else{
			toSP2 = new SimplePositions();
			toSP2.buildHorizontalOffset(590).buildVerticalOffset(315);
			toBI2 = drawText(ps2+"%", zbfont, fontSize, color, 400,
					200, -0.5, true,30,30);
		}
		
		
		//玩家照片
		BufferedImage p = super.getImg(two);
		p = ChangeImage.resizeCrop(p, 488);
		p = ChangeImage.makeRoundedCorner(p, 488);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(-60).buildVerticalOffset(256);
		
		
		Random rand = new Random();
		//未来身价
		int fontSize3 = 58;
		DecimalFormat df=new DecimalFormat("00000000");
		SimplePositions count0 = new SimplePositions();
		count0.buildHorizontalOffset(330).buildVerticalOffset(800);
		BufferedImage countBI0 = drawText(df.format(Integer.parseInt(Integer.toString(rand.nextInt(88888888))))+"$", ZbFont.HelveticaNeue加粗.font(),fontType, fontSize3, color,
				300, 0, 0, true);
		
		
		//座驾选择
		int num1 = rand.nextInt(S1Len);
		SimplePositions s1 = new SimplePositions();
		s1.buildHorizontalOffset(333).buildVerticalOffset(906);
		BufferedImage sp1 = super.getImg(S1[num1]);
		
		//国家选择
		int num2 = rand.nextInt(S2Len);
		SimplePositions s2 = new SimplePositions();
		s2.buildHorizontalOffset(333).buildVerticalOffset(1010);
		BufferedImage sp2 = super.getImg(S2[num2]);
		
		
		
		
		SimplePositions[] sp = { nameSP, pSP ,s1,s2,toSP,toSP2,count0};

		BufferedImage[] bis = { nameBI, p,sp1 ,sp2,toBI,toBI2,countBI0};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
