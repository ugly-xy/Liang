package com.zb.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jhlabs.image.CropFilter;
import com.jhlabs.image.ScaleFilter;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ChangeImage {

	/**
	 * 
	 * @param image
	 * @param cornerRadius //弧度
	 * @return
	 */
	public static BufferedImage makeRoundedCorner(BufferedImage image,
			int cornerRadius) {
		int w = image.getWidth();
		int h = image.getHeight();
		BufferedImage output = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = output.createGraphics();

		g2.setComposite(AlphaComposite.Src);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.WHITE);
		g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius,
				cornerRadius));
		g2.setComposite(AlphaComposite.SrcAtop);
		g2.drawImage(image, 0, 0, null);

		g2.dispose();
		return output;
	}

	/**
	 * 压缩图片成指定宽高
	 * 
	 * @param fromBi
	 * @param destWidth
	 *            目标宽度
	 * @param destHeight
	 *            目标高度
	 * @return
	 */
	public static BufferedImage resize(BufferedImage fromBi, int destWidth,
			int destHeight) {
		ScaleFilter sf = new ScaleFilter(destWidth, destHeight);
		return sf.filter(fromBi, null);
	}

	/**
	 * 按长度压缩图片成正方
	 * 
	 * @param fromBi
	 * @param len
	 *            目标宽度
	 * @return
	 */
	public static BufferedImage resize(BufferedImage fromBi, int len) {
		ScaleFilter sf = new ScaleFilter(len, len);
		return sf.filter(fromBi, null);
	}

	/**
	 * 按宽高最小长度等比压缩图
	 * 
	 * @param fromBi
	 * @param len
	 *            目标宽度
	 * @return
	 */
	public static BufferedImage resizeMin(BufferedImage fromBi, int len) {
		int w = fromBi.getWidth();
		int h = fromBi.getHeight();
		int dw = 0;
		int dh = 0;
		if (w > h) {
			dh = len;
			dw = w * dh / h;
		} else {
			dw = len;
			dh = h * dw / w;
		}
		return resize(fromBi, dw, dh);
	}

	/**
	 * 按宽高最小长度等比压缩图
	 * 
	 * @param fromBi
	 * @param len
	 *            目标宽度
	 * @return
	 */
	public static BufferedImage resizeMax(BufferedImage fromBi, int len) {
		int w = fromBi.getWidth();
		int h = fromBi.getHeight();
		int dw = 0;
		int dh = 0;
		if (w > h) {
			dw = len;
			dh = h * dw / w;
		} else {
			dh = len;
			dw = w * dh / h;
		}
		return resize(fromBi, dw, dh);
	}

	/**
	 * 按宽高最小长度等比压缩图
	 * 
	 * @param fromBi
	 * @param len
	 *            目标宽度
	 * @return
	 */
	public static BufferedImage resizeCrop(BufferedImage fromBi, int len) {
		int w = fromBi.getWidth();
		int h = fromBi.getHeight();
		int max = 0;
		if (w > h) {
			max = h;
		} else {
			max = w;
		}
		CropFilter cf = new CropFilter();
		cf.setX((w - max) / 2);
		cf.setY((h - max) / 2);
		cf.setWidth(max);
		cf.setHeight(max);
		fromBi = cf.filter(fromBi, null);

		return resize(fromBi, len, len);
	}

	// �Ĵ�С������png�к�ɫ����
	public static BufferedImage createResizedCopy(Image originalImage,
			int scaledWidth, int scaledHeight, boolean preserveAlpha) {
		int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB
				: BufferedImage.TYPE_INT_ARGB;
		BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight,
				imageType);
		Graphics2D g = scaledBI.createGraphics();
		if (preserveAlpha) {
			g.setComposite(AlphaComposite.Src);
		}
		g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
		g.dispose();
		return scaledBI;
	}

	//
	public static void reduceImg(String imgsrc, String imgdist, int widthdist,
			int heightdist) {
		try {
			File srcfile = new File(imgsrc);
			if (!srcfile.exists()) {
				return;
			}
			Image src = javax.imageio.ImageIO.read(srcfile);
			BufferedImage tag = new BufferedImage((int) widthdist,
					(int) heightdist, BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(
					src.getScaledInstance(widthdist, heightdist,
							Image.SCALE_SMOOTH), 0, 0, null);
			// tag.getGraphics().drawImage(src.getScaledInstance(widthdist,
			// heightdist, Image.SCALE_AREA_AVERAGING), 0, 0, null);

			FileOutputStream out = new FileOutputStream(imgdist);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(tag);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedImage fbi = ImageIO.read(new File(
				"/Users/lava/Downloads/pics/a10.jpg"));
		BufferedImage icon = resize(fbi, 190, 190);
		ImageIO.write(icon, "png", new File(
				"/Users/lava/Downloads/pics/a10.png"));

		BufferedImage rounded = makeRoundedCorner(icon, 180);
		ImageIO.write(rounded, "png", new File(
				"/Users/lava/Downloads/pics/a11.jpg"));
		
		BufferedImage fbi2 = ImageIO.read(new File(
				"/Users/lava/Downloads/pics/a10.jpg"));
		BufferedImage icon2 = resizeCrop(fbi, 190);
		ImageIO.write(icon2, "png", new File(
				"/Users/lava/Downloads/pics/a102.png"));

		BufferedImage rounded2 = makeRoundedCorner(icon2, 180);
		ImageIO.write(rounded2, "png", new File(
				"/Users/lava/Downloads/pics/a112.jpg"));
	}
	
//	// 根据需要是否使用 BufferedImage.TYPE_INT_ARGB
//			BufferedImage image = new BufferedImage(bi.getWidth(), bi.getHeight(),
//					BufferedImage.TYPE_INT_RGB);
//
//			Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi.getWidth(),
//					bi.getHeight());
//
//			Graphics2D g2 = image.createGraphics();
//			image = g2.getDeviceConfiguration().createCompatibleImage(
//					bi.getWidth(), bi.getHeight(), Transparency.TRANSLUCENT);
//			g2 = image.createGraphics();
//			g2.setBackground(Color.RED);
//			g2.fill(new Rectangle(image.getWidth(), image.getHeight()));
//			g2.setClip(shape);
//			// 使用 setRenderingHint 设置抗锯齿
//			g2.drawImage(bi, 0, 0, null);
//			g2.dispose();

}