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
import com.zb.service.image.i.TwoDraw;

public class DengPaoBiaoBaiGifTool extends BaseTool implements TwoDraw{
	public DengPaoBiaoBaiGifTool(StorageService storageService) {
		super(storageService);
	}

	public DengPaoBiaoBaiGifTool() {
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57a586870cf2ecedbd259f20";
		StorageService storageService = new StorageService();
		DengPaoBiaoBaiGifTool tyt = new DengPaoBiaoBaiGifTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼","我爱你！", tmpPath0,  //
				null));
	}
	
	static ZbFont zbfont = ZbFont.黑体;
	static int fontSize = 12;
	static Color color = new Color(82, 175, 170);
	static int fontType = Font.BOLD;
	
	static String png1 = "http://imgzb.zhuangdianbi.com/57a585ca0cf2ecedbd259dea";
	static String png2 = "http://imgzb.zhuangdianbi.com/57a585eb0cf2ecedbd259e29";
	static String png3 = "http://imgzb.zhuangdianbi.com/57a585f70cf2ecedbd259e2e";
	static String png4 = "http://imgzb.zhuangdianbi.com/57a586030cf2ecedbd259e40";
	static String png5 = "http://imgzb.zhuangdianbi.com/57a586130cf2ecedbd259e49";
	static String png6 = "http://imgzb.zhuangdianbi.com/57a5861f0cf2ecedbd259e53";
	static String png7 = "http://imgzb.zhuangdianbi.com/57a5862a0cf2ecedbd259e57";
	static String png8 = "http://imgzb.zhuangdianbi.com/57a586370cf2ecedbd259e78";
	static String png9 = "http://imgzb.zhuangdianbi.com/57a586430cf2ecedbd259ebd";
	static String png10 = "http://imgzb.zhuangdianbi.com/57a586500cf2ecedbd259ecc";
	static String png11 = "http://imgzb.zhuangdianbi.com/57a586620cf2ecedbd259eef";
	static String png12 = "http://imgzb.zhuangdianbi.com/57a5866d0cf2ecedbd259eff";
	static String png13 = "http://imgzb.zhuangdianbi.com/57a586790cf2ecedbd259f03";
	
	@Override
	public String draw(String one, String two, String tmpBackPic, String count) throws IOException {
		File outputDir = new File("." + File.separator);
		if (!outputDir.exists())
			outputDir.mkdirs();

		BufferedImage[] images = new BufferedImage[13];
		
		
		GifImage gifImage = null;
		try {

			//String tmpPath0 = "未标题-1.png" ;
			StorageService storageService = new StorageService();
			DengPaoBiaoBaiGifTool tyt = new DengPaoBiaoBaiGifTool(storageService);
			
			String url1 = tyt.d(one,1f, png1,null);
	        images[0]= super.getImg(tyt.d(two,0f, url1,null));
	        
	        String url2 = tyt.d(one,1f, png2,null);
	        images[1]= super.getImg(tyt.d(two,0.1f, url2,null));
	        
	        String url3 = tyt.d(one,0.9f, png3,null);
	        images[2]= super.getImg(tyt.d(two,0.2f, url3,null));
	        
	        String url4 = tyt.d(one,0.8f, png4,null);
	        images[3]= super.getImg(tyt.d(two,0.3f, url4,null));
	        
	        String url5 = tyt.d(one,0.7f, png5,null);
	        images[4]= super.getImg(tyt.d(two,0.4f, url5,null));
	        
	        String url6 = tyt.d(one,0.6f, png6,null);
	        images[5]= super.getImg(tyt.d(two,0.5f, url6,null));
	        
	        String url7 = tyt.d(one,0.5f, png7,null);
	        images[6]= super.getImg(tyt.d(two,0.6f, url7,null));
	        
	        String url8 = tyt.d(one,0.4f, png8,null);
	        images[7]= super.getImg(tyt.d(two,0.7f, url8,null));
	        
	        String url9 = tyt.d(one,0.3f, png9,null);
	        images[8]= super.getImg(tyt.d(two,0.8f, url9,null));
	        
	        String url10 = tyt.d(one,0.2f, png10,null);
	        images[9]= super.getImg(tyt.d(two,0.9f, url10,null));
	        
	        String url11 = tyt.d(one,0.1f, png11,null);
	        images[10]= super.getImg(tyt.d(two,0.9f, url11,null));
	        
	        String url12 = tyt.d(one,0f, png12,null);
	        images[11]= super.getImg(tyt.d(two,1f, url12,null));
	        
	        String url13 = tyt.d(one,0f, png13,null);
	        images[12]= super.getImg(tyt.d(two,1f, url13,null));
	        
	        
			gifImage = new GifImage(400, 400,
					GifImage.RESIZE_STRATEGY_CROP_TO_FIT_IMAGE_SIZE);
			
			// set indefinite looping
			gifImage.setLoopNumber(0);
			
			
			// create and add GifFrames in loop
			for (int i = 0; i < images.length; i++) {
				gifImage.setDefaultDelay(10);
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
	public String d(String one,float x,String tmpBackPic,
			String count) throws IOException{
		
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(200-one.length()*25/2).buildVerticalOffset(280);
		BufferedImage nameBI = drawText(one, zbfont, 25, color,
				600, 50, 0, true);
		
		
		SimplePositions[] sp = { nameSP};
		
		BufferedImage[] bis = { nameBI};
		
		return super.getSaveFile(sp, bis, x, tmpBackPic);
	}
}
