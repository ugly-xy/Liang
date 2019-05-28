package com.zb.service.image.spoof;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import com.gif4j.GifEncoder;
import com.gif4j.GifFrame;
import com.gif4j.GifImage;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.ZbFont;
import com.zb.service.image.i.TwoDraw;

public class WoYuanYiDongTaiTool extends BaseTool implements TwoDraw{
	public WoYuanYiDongTaiTool(StorageService storageService) {
		super(storageService);
	}

	public WoYuanYiDongTaiTool() {
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57a308170cf2e34f4b5526db";
		StorageService storageService = new StorageService();
		WoYuanYiDongTaiTool tyt = new WoYuanYiDongTaiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","从今晚你就是我的人了哈哈哈",  tmpPath0,
				null));
	}
	static String png1 = "http://imgzb.zhuangdianbi.com/57a306910cf2e34f4b55257c";
	static String png2 = "http://imgzb.zhuangdianbi.com/57a3069f0cf2e34f4b552585";
	static String png3 = "http://imgzb.zhuangdianbi.com/57a306b70cf2e34f4b552598";
	static String png4 = "http://imgzb.zhuangdianbi.com/57a306ca0cf2e34f4b5525af";
	static String png5 = "http://imgzb.zhuangdianbi.com/57a306dc0cf2e34f4b5525b9";
	static String png6 = "http://imgzb.zhuangdianbi.com/57a306ea0cf2e34f4b5525c7";
	static String png7 = "http://imgzb.zhuangdianbi.com/57a306f40cf2e34f4b5525cd";
	static String png8 = "http://imgzb.zhuangdianbi.com/57a307050cf2e34f4b5525d8";
	static String png9 = "http://imgzb.zhuangdianbi.com/57a307120cf2e34f4b5525e3";
	static String png10 = "http://imgzb.zhuangdianbi.com/57a3071d0cf2e34f4b5525ef";
	static String png11 = "http://imgzb.zhuangdianbi.com/57a307290cf2e34f4b5525fd";
	static String png12 = "http://imgzb.zhuangdianbi.com/57a307340cf2e34f4b552603";
	static String png13 = "http://imgzb.zhuangdianbi.com/57a307410cf2e34f4b55260c";
	static String png14 = "http://imgzb.zhuangdianbi.com/57a307510cf2e34f4b55262a";
	static String png15 = "http://imgzb.zhuangdianbi.com/57a3075c0cf2e34f4b552632";
	static String png16 = "http://imgzb.zhuangdianbi.com/57a3076d0cf2e34f4b55263e";
	static String png17 = "http://imgzb.zhuangdianbi.com/57a307780cf2e34f4b55264e";
	static String png18 = "http://imgzb.zhuangdianbi.com/57a307bf0cf2e34f4b552682";
	static String png19 = "http://imgzb.zhuangdianbi.com/57a307ca0cf2e34f4b552694";
	static String png20 = "http://imgzb.zhuangdianbi.com/57a307d70cf2e34f4b55269c";
	static String png21 = "http://imgzb.zhuangdianbi.com/57a307e10cf2e34f4b5526a6";
	static String png22 = "http://imgzb.zhuangdianbi.com/57a307f10cf2e34f4b5526b4";
	static String png23 = "http://imgzb.zhuangdianbi.com/57a307fd0cf2e34f4b5526be";
	static String png24 = "http://imgzb.zhuangdianbi.com/57a3080a0cf2e34f4b5526ce";
	
	static ZbFont zbfont = ZbFont.黑体加粗;
	static int fontSize = 18;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	@Override
	public String draw(String one,String two, String tmpBackPic, String count) throws IOException {
		File outputDir = new File("." + File.separator);
		if (!outputDir.exists())
			outputDir.mkdirs();
		
		BufferedImage[] images = new BufferedImage[24];
		GifImage gifImage = null;
		try {

			//String tmpPath0 = "我愿意底图.png";
			StorageService storageService = new StorageService();
			WoYuanYiDongTaiTool tyt = new WoYuanYiDongTaiTool(storageService);
			
			String ul1 = tyt.d(one,two, png1,null);
			String ul2 = tyt.d(one,two, png2,null);
			String ul3 = tyt.d(one,two, png3,null);
			String ul4 = tyt.d(one,two,png4,null);
			String ul5 = tyt.d(one,two, png5,null);
			String ul6 = tyt.d(one,two, png6,null);
			String ul7 = tyt.d(one,two, png7,null);
			String ul8 = tyt.d(one,two, png8,null);
			String ul9 = tyt.d(one,two, png9,null);
			String ul10 = tyt.d(one,two, png10,null);
			String ul11 = tyt.d(one,two, png10,null);
			String ul12 = tyt.d(one,two, png12,null);
			String ul13 = tyt.d(one,two, png13,null);
			String ul14 = tyt.d(one,two, png14,null);
			String ul15 = tyt.d(one,two, png15,null);
			String ul16 = tyt.d(one,two, png16,null);
			String ul17 = tyt.d(one,two, png17,null);
			String ul18 = tyt.d(one,two, png18,null);
			String ul19 = tyt.d(one,two, png19,null);
			String ul20 = tyt.d(one,two, png20,null);
			String ul21 = tyt.d(one,two, png21,null);
			String ul22 = tyt.d(one,two, png22,null);
			String ul23 = tyt.d(one,two, png23,null);
			String ul24 = tyt.d(one,two, png24,null);
			
	        
	        images[0]=  super.getImg(ul1);
	        images[1]=  super.getImg(ul2);
			images[2] = super.getImg(ul3);
			images[3] = super.getImg(ul4);
			images[4] = super.getImg(ul5);
			images[5] = super.getImg(ul6);
			images[6] = super.getImg(ul7);
			images[7] = super.getImg(ul8);
			images[8] = super.getImg(ul9);
			images[9] = super.getImg(ul10);
			images[10] = super.getImg(ul11);
			images[11] = super.getImg(ul12);
			images[12] = super.getImg(ul13);
			images[13] = super.getImg(ul14);
			images[14] = super.getImg(ul15);
			images[15] = super.getImg(ul16);
			images[16] = super.getImg(ul17);
			images[17] = super.getImg(ul18);
			images[18] = super.getImg(ul19);
			images[19] = super.getImg(ul20);
			images[20] = super.getImg(ul21);
			images[21] = super.getImg(ul22);
			images[22] = super.getImg(ul23);
			images[23] = super.getImg(ul24);
			
			gifImage = new GifImage(278, 290,
					GifImage.RESIZE_STRATEGY_CROP_TO_FIT_IMAGE_SIZE);
			
			// set indefinite looping
			gifImage.setLoopNumber(0);
			
			
			// create and add GifFrames in loop
			for (int i = 0; i < images.length; i++) {
				gifImage.setDefaultDelay(20);
				GifFrame nextFrame = new GifFrame(images[i]);
				gifImage.addGifFrame(nextFrame);
			}
			// set longer delay (5 seconds) for the last frame
			gifImage.getLastFrame().setDelay(200);
			
			
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			GifEncoder.encode(gifImage, out, true);
			return super.saveFile(out, "gif");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String d(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(88-one.length()*fontSize/2).buildVerticalOffset(60);
		BufferedImage nameBI = drawText(one, ZbFont.黑体.font(),fontType, fontSize, color,
				600, 600, 0, true);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(35).buildVerticalOffset(100);
		BufferedImage nameBI2 = drawText(two, ZbFont.黑体.font(),fontType, fontSize, color,
				120, 600, 0, true);
		
		
	
		SimplePositions[] sp = { nameSP,nameSP2};

		BufferedImage[] bis = {nameBI,nameBI2};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
}
