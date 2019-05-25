package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ChartGraphics {
	BufferedImage image;

	 void createImage(String fileLocation) {
	  try {
	   FileOutputStream fos = new FileOutputStream(fileLocation);
	   BufferedOutputStream bos = new BufferedOutputStream(fos);
	   JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
	   encoder.encode(image);
	   bos.close();
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	 }

	 public void graphicsGeneration(String name, String id, String classname,
	   String imgurl) {

	  int imageWidth = 500;// 图片的宽度

	  int imageHeight = 400;// 图片的高度

	  image = new BufferedImage(imageWidth, imageHeight,
	    BufferedImage.TYPE_INT_RGB);
	  Graphics graphics = image.getGraphics();
	  graphics.setColor(Color.blue);
	  graphics.fillRect(0, 0, imageWidth, imageHeight);
	  graphics.setColor(Color.ORANGE);
	  graphics.setFont(new Font("宋体",Font.BOLD,20));
	  graphics.drawString("姓名 : " + name, 50, 75);
	  graphics.drawString("学号 : " + id, 50, 150);
	  graphics.drawString("班级 : " + classname, 50, 225);
	  // ImageIcon imageIcon = new ImageIcon(imgurl);
	  // graphics.drawImage(imageIcon.getImage(), 230, 0, null);

	  // 改成这样:
	  BufferedImage bimg = null;
	  try {
	   bimg = javax.imageio.ImageIO.read(new java.io.File(imgurl));
	  } catch (Exception e) {
	  }

	  if (bimg != null)
	   graphics.drawImage(bimg, 230, 0, null);
	  graphics.dispose();

	  createImage(imgurl);

	 }
	 
	 /** 
      *  
      * @param souchFilePath 
      *            ：源图片路径 
      * @param targetFilePath 
      *            ：生成后的目标图片路径 
      * @param markContent 
      *            :要加的文字 
      * @param markContentColor 
      *            :文字颜色 
      * @param qualNum 
      *            :质量数字 
      * @param fontType 
      *            :字体类型 
      * @param fontSize 
      *            :字体大小 
      * @return 
      */  
     public static void createMark(String souchFilePath, String targetFilePath,  
                               String markContent, Color markContentColor, float qualNum,  
                               String fontType, int fontSize, int w, int h, Color color) {  
                       markContentColor = color;  
                       /* 构建要处理的源图片 */  
                       ImageIcon imageIcon = new ImageIcon(souchFilePath);  
                       /* 获取要处理的图片 */  
                       Image image = imageIcon.getImage();  
                       // Image可以获得图片的属性信息  
                       int width = image.getWidth(null);  
                       int height = image.getHeight(null);  
                       // 为画出与源图片的相同大小的图片（可以自己定义）  
                       BufferedImage bImage = new BufferedImage(width, height,  
                                       BufferedImage.TYPE_INT_RGB);  
                       // 构建2D画笔  
                       Graphics2D g = bImage.createGraphics();  
                       /* 设置2D画笔的画出的文字颜色 */  
                       g.setColor(markContentColor);  
                       /* 设置2D画笔的画出的文字背景色 */  
                       g.setBackground(Color.white);  
                       /* 画出图片 */  
                       g.drawImage(image, 0, 0, null);  
                       /* --------对要显示的文字进行处理-------------- */  
                       AttributedString ats = new AttributedString(markContent);  
                       Font font = new Font(fontType, Font.BOLD, fontSize);  
                       g.setFont(font);  
                      /* 消除java.awt.Font字体的锯齿 */  
                       g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  
                                                 RenderingHints.VALUE_ANTIALIAS_ON);  
                       /* 消除java.awt.Font字体的锯齿 */  
               // http://www.walkerjava.com/forum.php?mod=viewthread&tid=135  
                       // font = g.getFont().deriveFont(30.0f);  
                       ats.addAttribute(TextAttribute.FONT, font, 0, markContent.length());  
                       AttributedCharacterIterator iter = ats.getIterator();  
                       /* 添加水印的文字和设置水印文字出现的内容 ----位置 */  
                       g.drawString(iter, width - w, height - h);  
                       /* --------对要显示的文字进行处理-------------- www.it165.net */  
                       g.dispose();// 画笔结束  
                       try {  
                                     // 输出 文件 到指定的路径  
                                     FileOutputStream out = new FileOutputStream(targetFilePath);  
                                     JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
                                     JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bImage);  
                                     param.setQuality(qualNum, true);  
                                     encoder.encode(bImage, param);  
                                     out.close();  
                       } catch (Exception e) {  
                                     e.printStackTrace();  
                       }  
     }  

	 public static void main(String[] args) {
	  ChartGraphics cg = new ChartGraphics();
	  
	  createMark("/Users/lava/Downloads/pics/abcd.jpg", "/Users/lava/Downloads/pics/aaaa2.jpg",  
              "这是用java程序给图片添加的文字水印", null, 1, "方正楷体简体", 30, 700, 300,  
              Color.GRAY); 
	  
//	  try {
//	   cg.graphicsGeneration("ewew", "1", "12", "/Users/lava/Downloads/pics/aaaa2.jpg");
//	  } catch (Exception e) {
//	   e.printStackTrace();
//	  }
	  }
}
