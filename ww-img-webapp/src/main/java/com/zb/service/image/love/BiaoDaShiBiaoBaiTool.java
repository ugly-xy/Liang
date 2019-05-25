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

public class BiaoDaShiBiaoBaiTool extends BaseTool implements OneDraw{
	public BiaoDaShiBiaoBaiTool(StorageService storageService) {
		super(storageService);
	}

	public BiaoDaShiBiaoBaiTool() {
	}
	
	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/57a2e57b0cf2e34f4b550775";
		StorageService storageService = new StorageService();
		BiaoDaShiBiaoBaiTool tyt = new BiaoDaShiBiaoBaiTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("装点逼,我告诉你",  tmpPath0,
				null));
	}
	static String png1 = "http://imgzb.zhuangdianbi.com/57a2e5250cf2e34f4b55072c";
	static String png2 = "http://imgzb.zhuangdianbi.com/57a2e5320cf2e34f4b550730";
	static String png3 = "http://imgzb.zhuangdianbi.com/57a2e53e0cf2e34f4b550736";
	static String png4 = "http://imgzb.zhuangdianbi.com/57a2e54e0cf2e34f4b55074b";
	static String png5 = "http://imgzb.zhuangdianbi.com/57a2e5590cf2e34f4b550756";
	static String png6 = "http://imgzb.zhuangdianbi.com/57a2e5640cf2e34f4b55075e";
	static String png7 = "http://imgzb.zhuangdianbi.com/57a2e56f0cf2e34f4b550768";
	static String png8 = "http://imgzb.zhuangdianbi.com/57a2e57b0cf2e34f4b550775";
	
	static ZbFont zbfont = ZbFont.新蒂小丸子小学版;
	static int fontSize = 40;
	static Color color = new Color(255, 255, 255);
	static int fontType = Font.BOLD;
	
	@Override
	public String draw(String one, String tmpBackPic, String count) throws IOException {
		
		File outputDir = new File("." + File.separator);
		if (!outputDir.exists())
			outputDir.mkdirs();
		
		BufferedImage[] images = new BufferedImage[8];
		GifImage gifImage = null;
		try {

			String tmpPath0 = "http://imgzb.zhuangdianbi.com/57a2e57b0cf2e34f4b550775" ;
			StorageService storageService = new StorageService();
			BiaoDaShiBiaoBaiTool tyt = new BiaoDaShiBiaoBaiTool(storageService);
			
			String ul0 = tyt.d1(one, tmpPath0,null);
			
			String ul1 = tyt.d2(png1, ul0,null);
			String ul2 = tyt.d2(png2, ul0,null);
			String ul3 = tyt.d2(png3, ul0,null);
			String ul4 = tyt.d2(png4, ul0,null);
			String ul5 = tyt.d2(png5, ul0,null);
			String ul6 = tyt.d2(png6, ul0,null);
			String ul7 = tyt.d2(png7, ul0,null);
			//String ul8 = tyt.d2(png8, ul0,null);
	        
	        images[0]=  super.getImg(ul1);
	        images[1]=  super.getImg(ul2);
			images[2] = super.getImg(ul3);
			images[3] = super.getImg(ul4);
			images[4] = super.getImg(ul5);
			images[5] = super.getImg(ul6);
			images[6] = super.getImg(ul7);
			images[7] = super.getImg(ul0);
			
			gifImage = new GifImage(400, 240,
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
		nameSP.buildHorizontalOffset(200-one.length()*fontSize/2).buildVerticalOffset(-10);
		BufferedImage nameBI = drawText(one, ZbFont.新蒂小丸子小学版.font(),fontType, fontSize, color,
				600, 600, 0, true);
		
	
		SimplePositions[] sp = { nameSP};

		BufferedImage[] bis = {nameBI};

		return super.getSaveFile(sp, bis, 0.8f, png8);
	}
	
	public String d2(String one,String tmpBackPic,
			String count) throws IOException{
		
		
		BufferedImage p = super.getImg(one);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		SimplePositions[] sp = { pSP};
		
		BufferedImage[] bis = {p};
		
		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
}
