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
import com.zb.service.image.i.OneDraw;

public class GuaGuaDongTaiTool extends BaseTool implements OneDraw {
	
	public GuaGuaDongTaiTool(StorageService storageService) {
		super(storageService);
	}

	public GuaGuaDongTaiTool() {
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57a1bec00cf240d5e8eb2bdd";
		StorageService storageService = new StorageService();
		GuaGuaDongTaiTool tyt = new GuaGuaDongTaiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点", tmpPath0,
				null));
	}
	static String png1 = "http://imgzb.zhuangdianbi.com/57a1be560cf240d5e8eb2b9f";
	static String png2 = "http://imgzb.zhuangdianbi.com/57a1be480cf240d5e8eb2b99";
	static String png3 = "http://imgzb.zhuangdianbi.com/57a333640cf2e34f4b5548a9";
	static String png4 = "http://imgzb.zhuangdianbi.com/57a333700cf2e34f4b5548b0";
	static String png5 = "http://imgzb.zhuangdianbi.com/57a1be040cf240d5e8eb2b6d";
	static String png6 = "http://imgzb.zhuangdianbi.com/57a3337b0cf2e34f4b5548bb";
	static String png7 = "http://imgzb.zhuangdianbi.com/57a333880cf2e34f4b5548c4";
	static String png8 = "http://imgzb.zhuangdianbi.com/57a333940cf2e34f4b5548d2";
	static String png9 = "http://imgzb.zhuangdianbi.com/57a1bdd10cf240d5e8eb2b4d";
	static String png10 = "http://imgzb.zhuangdianbi.com/57a1bdc30cf240d5e8eb2b44";
	
	
	static String ditu2 = "http://imgzb.zhuangdianbi.com/57a3334e0cf2e34f4b55489b";
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 28;
	static Color color = new Color(0, 0, 0);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		File outputDir = new File("." + File.separator);
		if (!outputDir.exists())
			outputDir.mkdirs();

		BufferedImage[] images = new BufferedImage[10];
		
		
		GifImage gifImage = null;
		try {

			String tmpPath0 = "http://imgzb.zhuangdianbi.com/57a1bec00cf240d5e8eb2bdd" ;
			StorageService storageService = new StorageService();
			GuaGuaDongTaiTool tyt = new GuaGuaDongTaiTool(storageService);
			String ul1 = tyt.d1(one, tmpPath0,null);
			
			String ul2 = tyt.d2(one, tmpPath0,null);
	        
			String ul3 = tyt.d3(one,png3, ditu2, count);
			String ul4 = tyt.d4(one,png4, ditu2, count);
			String ul6 = tyt.d6(one,png6, ditu2, count);
			String ul7 = tyt.d7(one,png7, ditu2, count);
			String ul8 = tyt.d8(one,png8, ditu2, count);
			
	        images[0]= super.getImg(ul1);
			
	        images[1]=  super.getImg(ul2);
			
			images[2] = super.getImg(ul3);
			images[3] = super.getImg(ul4);
			images[4] = super.getImg(png5);
			images[5] = super.getImg(ul6);
			images[6] = super.getImg(ul7);
			images[7] = super.getImg(ul8);
			images[8] = super.getImg(png9);
			images[9] = super.getImg(png10);
			
			gifImage = new GifImage(400, 400,
					GifImage.RESIZE_STRATEGY_CROP_TO_FIT_IMAGE_SIZE);
			
			// set indefinite looping
			gifImage.setLoopNumber(0);
			
			
			// create and add GifFrames in loop
			for (int i = 0; i < images.length; i++) {
				gifImage.setDefaultDelay(50);
				GifFrame nextFrame = new GifFrame(images[i]);
				gifImage.addGifFrame(nextFrame);
			}
			// set longer delay (5 seconds) for the last frame
			gifImage.getFrame(0).setDelay(100);
			gifImage.getFrame(1).setDelay(100);
			gifImage.getLastFrame().setDelay(200);
			
			
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			GifEncoder.encode(gifImage, out, true);
			return super.saveFile(out, "gif");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String d1(String one,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(170-one.length()*45/2).buildVerticalOffset(115);
		BufferedImage nameBI = drawText(one, ZbFont.新蒂小丸子小学版.font(),fontType, 45, color,
				600, 600, -0.05, true,5,5);
		
		
		SimplePositions[] sp = { nameSP};

		BufferedImage[] bis = { nameBI};

		return super.getSaveFile(sp, bis, 1f, png1);
	}
	public String d2(String one,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(160-one.length()*45/2).buildVerticalOffset(145);
		BufferedImage nameBI = drawText(one,ZbFont.新蒂小丸子小学版.font(),fontType, 45, color,
				600, 300, -0.25, true,10,10);
		
		
		SimplePositions[] sp = { nameSP};
		
		BufferedImage[] bis = { nameBI};
		
		return super.getSaveFile(sp, bis, 1f, png2);
	}
	public String d3(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(200-one.length()*45/2).buildVerticalOffset(170);
		BufferedImage nameBI = drawText(one,ZbFont.新蒂小丸子小学版.font(),fontType, 45, new Color(58,47,53),
				600, 300, 0.15, true,10,10);
		
		BufferedImage p = super.getImg(two);
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		SimplePositions[] sp = { nameSP,nameSP2};
		
		BufferedImage[] bis = { nameBI,p};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	public String d4(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(160-one.length()*45/2).buildVerticalOffset(160);
		BufferedImage nameBI = drawText(one,ZbFont.新蒂小丸子小学版.font(),fontType, 45, new Color(58,47,53),
				600, 300, 0.15, true,10,10);
		
		BufferedImage p = super.getImg(two);
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		SimplePositions[] sp = { nameSP,nameSP2};
		
		BufferedImage[] bis = { nameBI,p};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	public String d6(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(160-one.length()*45/2).buildVerticalOffset(160);
		BufferedImage nameBI = drawText(one,ZbFont.新蒂小丸子小学版.font(),fontType, 45, new Color(58,47,53),
				600, 300, 0.15, true,10,10);
		
		BufferedImage p = super.getImg(two);
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		SimplePositions[] sp = { nameSP,nameSP2};
		
		BufferedImage[] bis = { nameBI,p};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	public String d7(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(200-one.length()*45/2).buildVerticalOffset(165);
		BufferedImage nameBI = drawText(one,ZbFont.新蒂小丸子小学版.font(),fontType, 45, new Color(58,47,53),
				600, 300, 0.15, true,10,10);
		
		BufferedImage p = super.getImg(two);
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		SimplePositions[] sp = { nameSP,nameSP2};
		
		BufferedImage[] bis = { nameBI,p};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
	public String d8(String one,String two,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(160-one.length()*45/2).buildVerticalOffset(160);
		BufferedImage nameBI = drawText(one,ZbFont.新蒂小丸子小学版.font(),fontType, 45, new Color(58,47,53),
				600, 300, 0.15, true,10,10);
		
		BufferedImage p = super.getImg(two);
		SimplePositions nameSP2 = new SimplePositions();
		nameSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		SimplePositions[] sp = { nameSP,nameSP2};
		
		BufferedImage[] bis = { nameBI,p};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
}
