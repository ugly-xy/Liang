package com.zb.service.image.genius;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import com.gif4j.*;
import com.zb.service.cloud.StorageService;
import com.zb.service.image.BaseTool;
import com.zb.service.image.SimplePositions;
import com.zb.service.image.i.OneDraw;

public class GifTool extends BaseTool implements OneDraw {

	public GifTool(StorageService storageService) {
		super(storageService);
	}

	public GifTool() {
	}

	public static void main(String[] args) throws IOException {
		String tmpPath0 = "http://imgzb.zhuangdianbi.com/579973390cf25965646100a5";
		StorageService storageService = new StorageService();
		GifTool tyt = new GifTool(storageService);
		tyt.isDebug(true);
		System.out.println(tyt.draw("素材4.png",  tmpPath0,
				null));
	}
	static String png1 = "http://imgzb.zhuangdianbi.com/57998b610cf2596564611306";
	static String png2 = "http://imgzb.zhuangdianbi.com/57998b710cf2596564611314";
	static String png3 = "http://imgzb.zhuangdianbi.com/57998b7b0cf2596564611319";
	static String png4 = "http://imgzb.zhuangdianbi.com/57998b860cf2596564611327";
	static String png5 = "http://imgzb.zhuangdianbi.com/57998b920cf2596564611334";
	static String png6 = "http://imgzb.zhuangdianbi.com/57998b9d0cf259656461133a";
	static String png7 = "http://imgzb.zhuangdianbi.com/57998ba70cf2596564611348";
	static String png8 = "http://imgzb.zhuangdianbi.com/57998bb30cf2596564611351";
	static String png9 = "http://imgzb.zhuangdianbi.com/57998bc40cf2596564611361";
	static String png10 = "http://imgzb.zhuangdianbi.com/57998bd00cf259656461136d";
	@Override
	public String draw(String one, String tmpBackPic,
			String count) throws IOException {

		File outputDir = new File("." + File.separator);
		if (!outputDir.exists())
			outputDir.mkdirs();

		BufferedImage[] images = new BufferedImage[10];
		
		
		GifImage gifImage = null;
		try {

			images[0] = super.getImg(png1);
			images[1] = super.getImg(png2);
			images[2] = super.getImg(png3);
			images[3] = super.getImg(png4);
			images[4] = super.getImg(png5);
			images[5] = super.getImg(png6);
			images[6] = super.getImg(png7);
			images[7] = super.getImg(png8);
			
			
			
			String tmpPath0 = "http://imgzb.zhuangdianbi.com/579973390cf25965646100a5" ;
			StorageService storageService = new StorageService();
			GifTool tyt = new GifTool(storageService);
			String ul1 = tyt.d(one, png9, tmpPath0,null);
			String ul2 = tyt.d(one, png10, tmpPath0,null);
			/*URL url2 = new URL(tyt.d(one, png9, tmpPath0,null)); //声明url对象      
	        URLConnection connection2 = url2.openConnection(); //打开连接  
	        connection2.setDoOutput(true);  */
	        
	        images[8]= super.getImg(ul1);
	        		//ImageIO.read(connection2.getInputStream()); //读取连接的流，赋值给BufferedImage对象
			
			//tyt.isDebug(true);
	        /*URL url = new URL(tyt.d(one, png10, tmpPath0,null)); //声明url对象      
	        URLConnection connection = url.openConnection(); //打开连接  
	        connection.setDoOutput(true);  */
	        images[9]=  super.getImg(ul2);
	        //ImageIO.read(connection.getInputStream()); //读取连接的流，赋值给BufferedImage对象
			
			
			gifImage = new GifImage(500, 375,
					GifImage.RESIZE_STRATEGY_CROP_TO_FIT_IMAGE_SIZE);
			
			// set indefinite looping
			gifImage.setLoopNumber(0);
			// create and add GifFrames in loop
			for (int i = 0; i < images.length; i++) {
				//gifImage.addGifFrame(new GifFrame(images[i]),new MozaicFilter(10,10,10));
				gifImage.setDefaultDelay(50);
				GifFrame nextFrame = new GifFrame(images[i]);
				gifImage.addGifFrame(nextFrame);
			}
			// set longer delay (5 seconds) for the last frame
			gifImage.getLastFrame().setDelay(100);
			
			
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			GifEncoder.encode(gifImage, out, true);
			return super.saveFile(out, "gif");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String d(String one,String two,String tmpBackPic,
			String count){
		
		//玩家照片
		BufferedImage p = super.compressImage(one, 170, 170);
		SimplePositions pSP = new SimplePositions();
		pSP.buildHorizontalOffset(172).buildVerticalOffset(100);
		
		;
		BufferedImage p2 = super.getImg(two);
		SimplePositions pSP2 = new SimplePositions();
		pSP2.buildHorizontalOffset(0).buildVerticalOffset(0);
		
		
		
		SimplePositions[] sp = { pSP,pSP2};

		BufferedImage[] bis = { p,p2};

		return super.getSaveFile(sp, bis, 1f, tmpBackPic);
	}
}
