package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

public class WeChat {

	private static final int logoSize = 52;
	private static final int left = 11;
	private static final int height = 16;
	private static final int txtLeft = 74;
	private static final int TXT_MAX = 300;
	
	static Builder<File> b = null;
	static {
		b = Thumbnails.of(new File("/Users/lava/Downloads/pics/twechat2.png"))
				.scale(1.0d);
	}
	
	/**
	 * 
	 * @param json
	 * 
	 * {	
	 * 		"tmp":1
	 * 		"title":"title",
	 * 		"net":1,
	 * 		"mePic":"pic",
	 * 		"meName":"name",
	 * 		"youPic":"pic",
	 * 		"youName":"name",
	 * 		"chats":[
	 * 			{	"speaker":"me",
	 * 				"cType":1,
	 * 				"data":"txt"
	 * 			}，
	 * 			{	"speaker":"you",
	 * 				"cType":1,
	 * 				"data":"txt"
	 * 			}，
	 * 		]
	 */
	
	public void drawWechat(String json){
		
		;
		
	}

	public static void main(String[] args) throws IOException {

		int nexty = 120;
		SimplePositions op1 = new SimplePositions();
		op1.buildHorizontalOffset(left).buildVerticalOffset(nexty);

		SimplePositions ot1 = new SimplePositions();
		ot1.buildHorizontalOffset(txtLeft).buildVerticalOffset(nexty);
		
		BufferedImage otx1 = drawText("abcdefg");
		if(otx1.getHeight()<logoSize){
			nexty = nexty + height + logoSize;
		}else{
			nexty = nexty + height + otx1.getHeight();
		}

		SimplePositions mp1 = new SimplePositions();
		mp1.buildHorizontalOffset(left).buildVerticalOffset(nexty).buildType(1);

		SimplePositions mt1 = new SimplePositions();
		mt1.buildHorizontalOffset(txtLeft).buildVerticalOffset(nexty)
				.buildType(1);
		BufferedImage mtx1 = drawText("a的发生13首发dfasdfadsfasdfds短发的师傅答舒服短发");
		
		if(mtx1.getHeight()<logoSize){
			nexty = nexty + height + logoSize;
		}else{
			nexty = nexty + height + mtx1.getHeight();
		}

		SimplePositions op2 = new SimplePositions();
		op2.buildHorizontalOffset(left).buildVerticalOffset(nexty);

		SimplePositions ot2 = new SimplePositions();
		ot2.buildHorizontalOffset(txtLeft).buildVerticalOffset(nexty);

		BufferedImage otx2 = drawText("abcdfasd短，发似懂,非懂撒但双方都dfd,defg多发点啥对的");

		BufferedImage mePic = Thumbnails
				.of(new File("/Users/lava/Downloads/pics/watermark.jpg"))
				.size(logoSize, logoSize).asBufferedImage();

		BufferedImage otherPic = Thumbnails
				.of(new File("/Users/lava/Downloads/pics/other.png"))
				.size(logoSize, logoSize).asBufferedImage();

		Thumbnails.of(new File("/Users/lava/Downloads/pics/twechat2.png"))
				.scale(1.0d).watermark(mp1, mePic, 1f)
				.watermark(mt1, mtx1, 1.0f).watermark(op1, otherPic, 1f)
				.watermark(ot1, otx1, 1.0f).watermark(op2, otherPic, 1f)
				.watermark(ot2, otx2, 1.0f).outputQuality(1.0d)
				// .outputFormat("jpg")
				.toFile(new File("/Users/lava/Downloads/pics/abcd3.png"));
	}

	static final GraphicsConfiguration GC = getGraphicsConfiguration();

	private static GraphicsConfiguration getGraphicsConfiguration() {
		Graphics2D _2D = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)
				.createGraphics();

		GraphicsConfiguration config = _2D.getDeviceConfiguration();

		return config;
	}

	private static BufferedImage drawText(String txt) {
		
		Font f = new Font("Helvetica", Font.PLAIN, 24);
		
		
		
		int txtLen = txt.length();

		int textWeight = TXT_MAX;
		int textHeight = logoSize;
		if(txtLen<20){
			textWeight = txt.length() * 20;
		}else{
			int line = txtLen%20==0?txtLen/20:txtLen/20+1;
			textHeight = logoSize+(line-1)*32; 
		}
		
		
		BufferedImage buffImg = GC.createCompatibleImage(textWeight,
				textHeight, Transparency.TRANSLUCENT);
		
		Graphics2D graphics = buffImg.createGraphics();
		graphics.setBackground(Color.GREEN);
		graphics.clipRect(1, 1, textWeight - 2, textHeight - 2);
		graphics.setColor(Color.BLACK);
		
		// System.out.println(f.getFamily());
		// graphics.fillRect(0, 0, textWeight, textHeight);
		graphics.setFont(f);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		FontMetrics fm = graphics.getFontMetrics(f); 
		int fontHeight = fm.getHeight();
		int offsetLeft = 0;  
        int rowIndex = 1;  
        for(int i=0;i<txt.length();i++){  
            char c = txt.charAt(i);  
            int charWidth = fm.charWidth(c); //字符的宽度  
              
            //另起一行  
            if(Character.isISOControl(c) || offsetLeft >= (textWeight-charWidth)){  
                rowIndex++;  
                offsetLeft = 0;  
            }  
              
            graphics.drawString(String.valueOf(c), offsetLeft, rowIndex * fontHeight);  
            offsetLeft += charWidth;  
        }  
		return buffImg;
	}

}
