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

public class LiaoJieZiJiTool extends BaseTool implements TwoDraw{
	public LiaoJieZiJiTool(StorageService storageService) {
		super(storageService);
	}

	public LiaoJieZiJiTool() {
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57fa14020cf26c8a8b8c7f4a";
		StorageService storageService = new StorageService();
		LiaoJieZiJiTool tyt = new LiaoJieZiJiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("娱乐社区","天蝎座",tmpPath0,  
				null));
	}
	
	static ZbFont zbfont = ZbFont.隶书;
	static int fontSize = 30;
	static int fontSize2 = 45;
	static Color color = new Color(251,228, 0);
	static int fontType = Font.BOLD;
	
	static String baiyang[] = {"http://imgzb.zhuangdianbi.com/57fa14020cf26c8a8b8c7f4a",
								"http://imgzb.zhuangdianbi.com/57fa143d0cf26c8a8b8c7f5a",
								"http://imgzb.zhuangdianbi.com/57fa144a0cf26c8a8b8c7f5b"};
	
	static String chunv[] ={"http://imgzb.zhuangdianbi.com/57fa145c0cf26c8a8b8c7f62",
							"http://imgzb.zhuangdianbi.com/57fa14670cf26c8a8b8c7f65",
							"http://imgzb.zhuangdianbi.com/57fa14730cf26c8a8b8c7f67"};
	
	static String jinniu[] = {"http://imgzb.zhuangdianbi.com/57fa14a00cf26c8a8b8c7f74",
								"http://imgzb.zhuangdianbi.com/57fa14ad0cf26c8a8b8c7f7b",
								"http://imgzb.zhuangdianbi.com/57fa14ba0cf26c8a8b8c7f7d"};
	
	static String jvxie[] = {"http://imgzb.zhuangdianbi.com/57fa14c60cf26c8a8b8c7f82",
								"http://imgzb.zhuangdianbi.com/57fa14d00cf26c8a8b8c7f86",
								"http://imgzb.zhuangdianbi.com/57fa14da0cf26c8a8b8c7f8b"};
	
	static String mojie[] = {"http://imgzb.zhuangdianbi.com/57fa15570cf26c8a8b8c7faf",
								"http://imgzb.zhuangdianbi.com/57fa15680cf26c8a8b8c7fb1",
								"http://imgzb.zhuangdianbi.com/57fa15810cf26c8a8b8c7fbb"};
	
	static String sheshou[] = {"http://imgzb.zhuangdianbi.com/57fa164f0cf26c8a8b8c7fe5",
								"http://imgzb.zhuangdianbi.com/57fa16600cf26c8a8b8c7feb",
								"http://imgzb.zhuangdianbi.com/57fa166e0cf26c8a8b8c7ff1"};
	
	static String shizi[] = {"http://imgzb.zhuangdianbi.com/57fa16d70cf26c8a8b8c800d",
								"http://imgzb.zhuangdianbi.com/57fa16e20cf26c8a8b8c8012",
								"http://imgzb.zhuangdianbi.com/57fa16ee0cf26c8a8b8c8018"};
	
	static String shuangyu[] = {"http://imgzb.zhuangdianbi.com/57fa16fd0cf26c8a8b8c801d",
								"http://imgzb.zhuangdianbi.com/57fa17080cf26c8a8b8c8027",
								"http://imgzb.zhuangdianbi.com/57fa17180cf26c8a8b8c802b"};
	
	static String shuangzi[] = {"http://imgzb.zhuangdianbi.com/57fa17260cf26c8a8b8c8046",
								"http://imgzb.zhuangdianbi.com/57fa17470cf26c8a8b8c805d",
								"http://imgzb.zhuangdianbi.com/57fa17560cf26c8a8b8c8067"};
	
	static String shuiping[] = {"http://imgzb.zhuangdianbi.com/57fa17640cf26c8a8b8c806d",
								"http://imgzb.zhuangdianbi.com/57fa176f0cf26c8a8b8c8076",
								"http://imgzb.zhuangdianbi.com/57fa177b0cf26c8a8b8c807a"};
	
	static String tiancheng[] = {"http://imgzb.zhuangdianbi.com/57fa17860cf26c8a8b8c807f",
								"http://imgzb.zhuangdianbi.com/57fa17900cf26c8a8b8c8082",
								"http://imgzb.zhuangdianbi.com/57fa179d0cf26c8a8b8c8088"};
	
	static String tianxie[] = {"http://imgzb.zhuangdianbi.com/57fa17a80cf26c8a8b8c808e",
								"http://imgzb.zhuangdianbi.com/57fa17b30cf26c8a8b8c8097",
								"http://imgzb.zhuangdianbi.com/57fa17bd0cf26c8a8b8c809c"};
	
	@Override
	public String draw(String one,String two, String tmpBackPic, String count) throws IOException {
		
		Random ran = new Random();
		int a = ran.nextInt(3);

		//我叫
		int left = 250-one.length()*fontSize2/2-3*fontSize/2;
 		SimplePositions nameSP = new SimplePositions();
 		nameSP.buildHorizontalOffset(left).buildVerticalOffset(250);
 		BufferedImage nameBi = drawText("我叫：", ZbFont.黑体, fontSize,
 				color, 600, 100, 0, true);
 		
 		//姓名
 		SimplePositions nameSP2 = new SimplePositions();
 		nameSP2.buildHorizontalOffset(left+6*fontSize/2).buildVerticalOffset(235);
 		BufferedImage nameBi2 = drawText(one, zbfont, 45,
 				color, 600, 100, 0, true);
 		
		if(two.equals("白羊座")){
			tmpBackPic = baiyang[a];
		}else if(two.equals("处女座")){
			tmpBackPic = chunv[a];
		}else if(two.equals("金牛座")){
			tmpBackPic = jinniu[a];
		}else if(two.equals("巨蟹座")){
			tmpBackPic = jvxie[a];
		}else if(two.equals("摩羯座")){
			tmpBackPic = mojie[a];
		}else if(two.equals("射手座")){
			tmpBackPic = sheshou[a];
		}else if(two.equals("狮子座")){
			tmpBackPic = shizi[a];
		}else if(two.equals("双鱼座")){
			tmpBackPic = shuangyu[a];
		}else if(two.equals("双子座")){
			tmpBackPic = shuangzi[a];
		}else if(two.equals("水瓶座")){
			tmpBackPic = shuiping[a];
		}else if(two.equals("天秤座")){
			tmpBackPic = tiancheng[a];
		}else{
			tmpBackPic = tianxie[a];
		}
		
		SimplePositions[] sp = {nameSP,nameSP2};

		BufferedImage[] bis = {nameBi,nameBi2};
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}

}
