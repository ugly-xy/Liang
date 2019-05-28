package com.zb.service.image.love;

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
import com.zb.service.image.i.ThreeDraw;

public class ShuangXinHuanRaoGifTool extends BaseTool implements ThreeDraw{
	public ShuangXinHuanRaoGifTool(StorageService storageService) {
		super(storageService);
	}

	public ShuangXinHuanRaoGifTool() {
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57a442e20cf2c80ac7a38249";
		StorageService storageService = new StorageService();
		ShuangXinHuanRaoGifTool tyt = new ShuangXinHuanRaoGifTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","装","我爱你！", tmpPath0,  //
				null));
	}
	static String png1 = "http://imgzb.zhuangdianbi.com/57a441cf0cf2c80ac7a37de4";
	static String png2 = "http://imgzb.zhuangdianbi.com/57a441dc0cf2c80ac7a37df7";
	static String png3 = "http://imgzb.zhuangdianbi.com/57a441ee0cf2c80ac7a37e6b";
	static String png4 = "http://imgzb.zhuangdianbi.com/57a441f80cf2c80ac7a37e9d";
	static String png5 = "http://imgzb.zhuangdianbi.com/57a442030cf2c80ac7a37ee3";
	static String png6 = "http://imgzb.zhuangdianbi.com/57a442150cf2c80ac7a37f0c";
	static String png7 = "http://imgzb.zhuangdianbi.com/57a442200cf2c80ac7a37f14";
	static String png8 = "http://imgzb.zhuangdianbi.com/57a4422b0cf2c80ac7a37f29";
	static String png9 = "http://imgzb.zhuangdianbi.com/57a442380cf2c80ac7a37f86";
	static String png10 = "http://imgzb.zhuangdianbi.com/57a442460cf2c80ac7a37fb0";
	static String png11 = "http://imgzb.zhuangdianbi.com/57a442520cf2c80ac7a37fe8";
	static String png12 = "http://imgzb.zhuangdianbi.com/57a4425c0cf2c80ac7a38012";
	static String png13 = "http://imgzb.zhuangdianbi.com/57a442720cf2c80ac7a3809e";
	static String png14 = "http://imgzb.zhuangdianbi.com/57a4427e0cf2c80ac7a380de";
	static String png15 = "http://imgzb.zhuangdianbi.com/57a442880cf2c80ac7a38114";
	static String png16 = "http://imgzb.zhuangdianbi.com/57a442930cf2c80ac7a38129";
	static String png17 = "http://imgzb.zhuangdianbi.com/57a442a10cf2c80ac7a3813c";
	static String png18 = "http://imgzb.zhuangdianbi.com/57a442ac0cf2c80ac7a3816d";
	static String png19 = "http://imgzb.zhuangdianbi.com/57a442bb0cf2c80ac7a381bc";
	static String png20 = "http://imgzb.zhuangdianbi.com/57a442c50cf2c80ac7a381dd";
	static String png21 = "http://imgzb.zhuangdianbi.com/57a442d20cf2c80ac7a38216";
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 12;
	static Color color = new Color(6, 242, 245);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String two,String three, String tmpBackPic, String count) throws IOException {
		File outputDir = new File("." + File.separator);
		if (!outputDir.exists())
			outputDir.mkdirs();

		BufferedImage[] images = new BufferedImage[21];
		
		
		GifImage gifImage = null;
		try {

			//String tmpPath0 = "未标题-1.png" ;
			StorageService storageService = new StorageService();
			ShuangXinHuanRaoGifTool tyt = new ShuangXinHuanRaoGifTool(storageService);
			
			
	        images[0]= super.getImg(tyt.d1(one,two, png1,null));
	        images[1]= super.getImg(tyt.d2(one,two, png2,null));
	        images[2]= super.getImg(tyt.d3(one,two, png3,null));
	        images[3]= super.getImg(tyt.d4(one,two, png4,null));
	        images[4]= super.getImg(tyt.d5(one,two, png5,null));
	        images[5]= super.getImg(tyt.d6(one,two, png6,null));
	        images[6]= super.getImg(tyt.d7(one,two, png7,null));
	        images[7]= super.getImg(tyt.d8(one,two, png8,null));
	        images[8]= super.getImg(tyt.d9(one,two, png9,null));
	        images[9]= super.getImg(tyt.d10(one,two, png10,null));
	        images[10]= super.getImg(tyt.d11(one,two, png11,null));
	        images[11]= super.getImg(tyt.d12(one,two, png12,null));
	        images[12]= super.getImg(tyt.d13(one,two, png13,null));
	        images[13]= super.getImg(tyt.d14(one,two, png14,null));
	        images[14]= super.getImg(tyt.d15(one,two, png15,null));
	        images[15]= super.getImg(tyt.d16(one,two, png16,null));
	        images[16]= super.getImg(tyt.d17(one,two, png17,null));
	        images[17]= super.getImg(tyt.d18(one,two, png18,null));
	        images[18]= super.getImg(tyt.d19(one,two, png19,null));
	        images[19]= super.getImg(tyt.d20(one,two, png20,null));
	        images[20]= super.getImg(tyt.d21(three, png21,null));
	        
			
			gifImage = new GifImage(182, 156,
					GifImage.RESIZE_STRATEGY_CROP_TO_FIT_IMAGE_SIZE);
			
			// set indefinite looping
			gifImage.setLoopNumber(0);
			
			
			// create and add GifFrames in loop
			for (int i = 0; i < images.length; i++) {
				gifImage.setDefaultDelay(4);
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
	public String d1(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(37-one.length()*12/2).buildVerticalOffset(110);
		BufferedImage nameBI = drawText(one, zbfont, 12, color,
				600, 50, -0.6, true,5,5);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(145-two.length()*12/2).buildVerticalOffset(5);
		BufferedImage nameBI2 = drawText(two, zbfont, 12, color,
				600, 50, -0.6, true,5,5);
		
		
		SimplePositions[] sp = { nameSP,nameSP2};

		BufferedImage[] bis = { nameBI,nameBI2};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	public String d2(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(37-one.length()*12/2).buildVerticalOffset(105);
		BufferedImage nameBI = drawText(one, zbfont, 12, color,
				600, 50, -0.6, true,5,5);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(145-two.length()*12/2).buildVerticalOffset(7);
		BufferedImage nameBI2 = drawText(two, zbfont, 12, color,
				600, 50, -0.6, true,5,5);
		
		
		SimplePositions[] sp = { nameSP,nameSP2};
		
		BufferedImage[] bis = { nameBI,nameBI2};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	public String d3(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(37-one.length()*12/2).buildVerticalOffset(102);
		BufferedImage nameBI = drawText(one, zbfont, 12, color,
				600, 50, -0.6, true,5,5);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(147-two.length()*12/2).buildVerticalOffset(9);
		BufferedImage nameBI2 = drawText(two, zbfont, 12, color,
				600, 50, -0.6, true,5,5);
		
		
		SimplePositions[] sp = { nameSP,nameSP2};
		
		BufferedImage[] bis = { nameBI,nameBI2};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	public String d4(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(37-one.length()*12/2).buildVerticalOffset(100);
		BufferedImage nameBI = drawText(one, zbfont, 12, color,
				600, 50, -0.6, true,5,5);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(150-two.length()*12/2).buildVerticalOffset(10);
		BufferedImage nameBI2 = drawText(two, zbfont, 12, color,
				600, 50, -0.6, true,5,5);
		
		
		SimplePositions[] sp = { nameSP,nameSP2};
		
		BufferedImage[] bis = { nameBI,nameBI2};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	public String d5(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(37-one.length()*12/2).buildVerticalOffset(98);
		BufferedImage nameBI = drawText(one, zbfont, 12, color,
				600, 50, -0.6, true,5,5);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(148-two.length()*12/2).buildVerticalOffset(12);
		BufferedImage nameBI2 = drawText(two, zbfont, 12, color,
				600, 50, -0.6, true,5,5);
		
		
		SimplePositions[] sp = { nameSP,nameSP2};
		
		BufferedImage[] bis = { nameBI,nameBI2};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	public String d6(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(37-one.length()*12/2).buildVerticalOffset(92);
		BufferedImage nameBI = drawText(one, zbfont, 12, color,
				600, 50, -0.6, true,5,5);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(148-two.length()*12/2).buildVerticalOffset(15);
		BufferedImage nameBI2 = drawText(two, zbfont, 12, color,
				600, 50, -0.6, true,5,5);
		
		
		SimplePositions[] sp = { nameSP,nameSP2};
		
		BufferedImage[] bis = { nameBI,nameBI2};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	public String d7(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(37-one.length()*12/2).buildVerticalOffset(90);
		BufferedImage nameBI = drawText(one, zbfont, 12, color,
				600, 50, -0.6, true,5,5);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(148-two.length()*12/2).buildVerticalOffset(17);
		BufferedImage nameBI2 = drawText(two, zbfont, 12, color,
				600, 50, -0.6, true,5,5);
		
		
		SimplePositions[] sp = { nameSP,nameSP2};
		
		BufferedImage[] bis = { nameBI,nameBI2};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	public String d8(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(37-one.length()*12/2).buildVerticalOffset(87);
		BufferedImage nameBI = drawText(one, zbfont, 12, color,
				600, 50, -0.6, true,5,5);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(148-two.length()*12/2).buildVerticalOffset(21);
		BufferedImage nameBI2 = drawText(two, zbfont, 12, color,
				600, 50, -0.6, true,5,5);
		
		
		SimplePositions[] sp = { nameSP,nameSP2};
		
		BufferedImage[] bis = { nameBI,nameBI2};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	public String d9(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(37-one.length()*12/2).buildVerticalOffset(85);
		BufferedImage nameBI = drawText(one, zbfont, 12, color,
				600, 50, -0.55, true,5,5);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(145-two.length()*12/2).buildVerticalOffset(24);
		BufferedImage nameBI2 = drawText(two, zbfont, 12, color,
				600, 50, -0.5, true,5,5);
		
		
		SimplePositions[] sp = { nameSP,nameSP2};
		
		BufferedImage[] bis = { nameBI,nameBI2};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	public String d10(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(39-one.length()*12/2).buildVerticalOffset(80);
		BufferedImage nameBI = drawText(one, zbfont, 12, color,
				600, 50, -0.52, true,5,5);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(142-two.length()*12/2).buildVerticalOffset(27);
		BufferedImage nameBI2 = drawText(two, zbfont, 12, color,
				600, 50, -0.5, true,5,5);
		
		
		SimplePositions[] sp = { nameSP,nameSP2};
		
		BufferedImage[] bis = { nameBI,nameBI2};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	public String d11(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(42-one.length()*12/2).buildVerticalOffset(76);
		BufferedImage nameBI = drawText(one, zbfont, 12, color,
				600, 50, -0.52, true,5,5);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(140-two.length()*12/2).buildVerticalOffset(32);
		BufferedImage nameBI2 = drawText(two, zbfont, 12, color,
				600, 50, -0.5, true,5,5);
		
		
		SimplePositions[] sp = { nameSP,nameSP2};
		
		BufferedImage[] bis = { nameBI,nameBI2};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	public String d12(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(45-one.length()*12/2).buildVerticalOffset(72);
		BufferedImage nameBI = drawText(one, zbfont, 12, color,
				600, 50, -0.48, true,5,5);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(139-two.length()*12/2).buildVerticalOffset(34);
		BufferedImage nameBI2 = drawText(two, zbfont, 12, color,
				600, 50, -0.47, true,5,5);
		
		
		SimplePositions[] sp = { nameSP,nameSP2};
		
		BufferedImage[] bis = { nameBI,nameBI2};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	public String d13(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(48-one.length()*12/2).buildVerticalOffset(70);
		BufferedImage nameBI = drawText(one, zbfont, 12, color,
				600, 50, -0.48, true,5,5);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(137-two.length()*12/2).buildVerticalOffset(36);
		BufferedImage nameBI2 = drawText(two, zbfont, 12, color,
				600, 50, -0.43, true,5,5);
		
		
		SimplePositions[] sp = { nameSP,nameSP2};
		
		BufferedImage[] bis = { nameBI,nameBI2};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	public String d14(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(52-one.length()*12/2).buildVerticalOffset(68);
		BufferedImage nameBI = drawText(one, zbfont, 12, color,
				600, 50, -0.43, true,5,5);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(134-two.length()*12/2).buildVerticalOffset(39);
		BufferedImage nameBI2 = drawText(two, zbfont, 12, color,
				600, 50, -0.37, true,5,5);
		
		
		SimplePositions[] sp = { nameSP,nameSP2};
		
		BufferedImage[] bis = { nameBI,nameBI2};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	public String d15(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(54-one.length()*12/2).buildVerticalOffset(65);
		BufferedImage nameBI = drawText(one, zbfont, 12, color,
				600, 50, -0.37, true,5,5);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(130-two.length()*12/2).buildVerticalOffset(42);
		BufferedImage nameBI2 = drawText(two, zbfont, 12, color,
				600, 50, -0.33, true,5,5);
		
		
		SimplePositions[] sp = { nameSP,nameSP2};
		
		BufferedImage[] bis = { nameBI,nameBI2};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	public String d16(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(56-one.length()*12/2).buildVerticalOffset(63);
		BufferedImage nameBI = drawText(one, zbfont, 12, color,
				600, 50, -0.30, true,5,5);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(125-two.length()*12/2).buildVerticalOffset(45);
		BufferedImage nameBI2 = drawText(two, zbfont, 12, color,
				600, 50, -0.27, true,5,5);
		
		
		SimplePositions[] sp = { nameSP,nameSP2};
		
		BufferedImage[] bis = { nameBI,nameBI2};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	public String d17(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(60-one.length()*12/2).buildVerticalOffset(60);
		BufferedImage nameBI = drawText(one, zbfont, 12, color,
				600, 50, -0.27, true,5,5);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(125-two.length()*12/2).buildVerticalOffset(47);
		BufferedImage nameBI2 = drawText(two, zbfont, 12, color,
				600, 50, -0.22, true,5,5);
		
		
		SimplePositions[] sp = { nameSP,nameSP2};
		
		BufferedImage[] bis = { nameBI,nameBI2};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	public String d18(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(65-one.length()*12/2).buildVerticalOffset(60);
		BufferedImage nameBI = drawText(one, zbfont, 12, color,
				600, 50, -0.27, true,5,5);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(115-two.length()*12/2).buildVerticalOffset(47);
		BufferedImage nameBI2 = drawText(two, zbfont, 12, color,
				600, 50, -0.22, true,5,5);
		
		
		SimplePositions[] sp = { nameSP,nameSP2};
		
		BufferedImage[] bis = { nameBI,nameBI2};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	public String d19(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(70-one.length()*12/2).buildVerticalOffset(57);
		BufferedImage nameBI = drawText(one, zbfont, 12, color,
				600, 50, -0.27, true,5,5);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(110-two.length()*12/2).buildVerticalOffset(50);
		BufferedImage nameBI2 = drawText(two, zbfont, 12, color,
				600, 50, -0.22, true,5,5);
		
		
		SimplePositions[] sp = { nameSP,nameSP2};
		
		BufferedImage[] bis = { nameBI,nameBI2};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	public String d20(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(70-one.length()*12/2).buildVerticalOffset(57);
		BufferedImage nameBI = drawText(one, zbfont, 12, color,
				600, 50, -0.27, true,5,5);
		
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(110-two.length()*12/2).buildVerticalOffset(50);
		BufferedImage nameBI2 = drawText(two, zbfont, 12, color,
				600, 50, -0.22, true,5,5);
		
		
		SimplePositions[] sp = { nameSP,nameSP2};
		
		BufferedImage[] bis = { nameBI,nameBI2};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	public String d21(String one,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(95-one.length()*12/2).buildVerticalOffset(48);
		BufferedImage nameBI = drawText(one, zbfont, 12, color,
				600, 50, -0.27, true,5,5);
		
		
		SimplePositions[] sp = { nameSP};
		
		BufferedImage[] bis = { nameBI};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
}
