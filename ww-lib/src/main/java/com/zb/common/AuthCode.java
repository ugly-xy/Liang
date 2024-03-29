package com.zb.common;

//import com.temesoft.security.image.SkewImageSimple;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 验证码生成类
 */
public abstract class AuthCode {

	static final Logger logger = LoggerFactory.getLogger(AuthCode.class);
	static final Color COLORS[] = { new Color(113, 31, 71),
			new Color(37, 0, 37), new Color(111, 33, 36),
			new Color(116, 86, 88), new Color(14, 51, 16), new Color(1, 1, 1),
			new Color(0, 0, 112), new Color(72, 14, 73), new Color(65, 67, 29),
			new Color(41, 75, 71) };

//	static final char[] SEEDS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"
//			.toCharArray();
//
//	public static String random(int size) {
//		if (size <= 0) {
//			throw new IllegalArgumentException("size must > 0");
//		}
//		char[] result = new char[size];
//		Random random = ThreadLocalRandom.current();
//		int len = SEEDS.length;
//		for (int i = 0; i < size; i++) {
//			result[i] = SEEDS[random.nextInt(len)];
//		}
//		return new String(result);
//	}

	static final GraphicsConfiguration GC = getGraphicsConfiguration();

	private static GraphicsConfiguration getGraphicsConfiguration() {
		Graphics2D _2D = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)
				.createGraphics();

		GraphicsConfiguration config = _2D.getDeviceConfiguration();

		return config;
	}


	public static void draw(String code, int width, int height, OutputStream out)
			throws IOException {
		char[] chars = code.toCharArray();
		int len = chars.length;
		int xx = (int) (width / (len + 0.618));
		int codeY = height - 5;

		BufferedImage buffImg = GC.createCompatibleImage(width, height,
				Transparency.TRANSLUCENT);
		Graphics2D graphics = buffImg.createGraphics();
		// graphics.setColor(new Color(255,255,255));
		// graphics.fillRect(0, 0, width, height);
		// graphics.setFont(new Font("Fixedsys", Font.BOLD, height - 2));
		graphics.setFont(new Font("MV Boli", Font.BOLD, height - 3));
		// graphics.setFont(new Font("Comic Sans MS", Font.BOLD, height - 6));

		// graphics.setStroke(new BasicStroke(1));
		// graphics.drawRect(0, 0, width - 1, height - 1);
		Random random = ThreadLocalRandom.current();

		for (int i = 0; i < len; i++) {
			graphics.setColor(COLORS[random.nextInt(10)]);
			graphics.drawString(String.valueOf(chars[i]),
					(float) ((i + 1 - 0.618) * xx), codeY);
		}
		// 绘画之前扭曲 BufferedOutputStream FIX EOFException Broken Pipe
		try (OutputStream buff = new BufferedOutputStream(out)) {
			ImageIO.write(doTwist(buffImg, graphics), "png", buff);
		}

		// ByteArrayOutputStream out_arr = new ByteArrayOutputStream(20480);
		// AuthCode.draw(code, 160, 48, out_arr);
		// ImageIO.write(doTwist(buffImg,graphics), "png", out_arr);
		// out.write(out_arr.toByteArray());
		// out.close();
	}

	/**
	 * 
	 * 正弦曲线Wave扭曲图片 & 干扰线
	 */
	static BufferedImage doTwist(BufferedImage buffImg, Graphics2D graphics) {

		int width = buffImg.getWidth();
		int height = buffImg.getHeight();
		graphics.setColor(Color.BLACK);
		Random random = ThreadLocalRandom.current();
		for (int i = 0; i < 31; i++) { // 干扰线
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(55);
			int yl = random.nextInt(33);
			graphics.drawLine(x, y, x + xl, y + yl);
		}
		graphics.dispose();

		double dMultValue = random.nextInt(4) + Math.PI;// 波形的幅度倍数，越大扭曲的程序越高，一般为3
		double dPhase = random.nextInt(6);// 波形的起始相位，取值区间（0-2＊PI）

		BufferedImage destBi = GC.createCompatibleImage(width, height,
				Transparency.TRANSLUCENT);
		for (int i = 0; i < destBi.getWidth(); i++) {
			for (int j = 0; j < destBi.getHeight(); j++) {
				if (buffImg.getRGB(i, j) == 0) {
					continue;
				}
				int nOldX = getXPosition4Twist(dPhase, dMultValue,
						destBi.getHeight(), i, j);
				int nOldY = j;
				if (nOldX >= 0 && nOldX < destBi.getWidth() && nOldY >= 0
						&& nOldY < destBi.getHeight()) {
					destBi.setRGB(nOldX, nOldY, buffImg.getRGB(i, j));
				}
			}
		}
		return destBi;
	}

	static final double ROUND = Math.PI * Math.E / Math.sqrt(3);

	/**
	 * 获取扭曲后的x轴位置
	 */
	static int getXPosition4Twist(double dPhase, double dMultValue, int height,
			int xPosition, int yPosition) {
		// double PI = 3.1415926535897932384626433832799; // 此值越大，扭曲程度越大
		double dx = ROUND * yPosition / height + dPhase;
		double dy = Math.sin(dx);
		return xPosition + (int) (dy * dMultValue);
	}

	public static BufferedImage cutImage(BufferedImage bi, int left, int top,
			int width, int height) {
		height = Math.min(height, bi.getHeight());
		width = Math.min(width, bi.getWidth());
		if (height <= 0)
			height = bi.getHeight();
		if (width <= 0)
			width = bi.getWidth();
		top = Math.min(Math.max(0, top), bi.getHeight() - height);
		left = Math.min(Math.max(0, left), bi.getWidth() - width);
		return bi.getSubimage(left, top, width, height);
		// .getScaledInstance()
		// ImageIO.write(, "jpeg", out);
		// out.close();
	}

	public static BufferedImage compressImage(BufferedImage srcImage,
			int maxWidth, int maxHeight) {
		int srcWidth = srcImage.getWidth(null);
		int srcHeight = srcImage.getHeight(null);
		if (srcWidth <= maxWidth && srcHeight <= maxHeight) {
			return srcImage;
		}
		Image scaledImage = srcImage.getScaledInstance(srcWidth, srcHeight,
				Image.SCALE_SMOOTH);
		double ratio = Math.min((double) maxWidth / srcWidth,
				(double) maxHeight / srcHeight);
		AffineTransformOp op = new AffineTransformOp(
				AffineTransform.getScaleInstance(ratio, ratio), null);
		return op.filter(toBufferedImage(scaledImage), null);
	}

	public static void compressImageNew(BufferedImage srcImage, int maxWidth,
			int maxHeight, File file) throws Exception {
//		int srcWidth = srcImage.getWidth(null);
//		int srcHeight = srcImage.getHeight(null);
//		double ratio = Math.min((double) maxWidth / srcWidth,
//				(double) maxHeight / srcHeight);
		Thumbnails.of(srcImage).size(maxWidth, maxHeight)
				.keepAspectRatio(false).outputQuality(0.95).toFile(file);
	}

	public static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}

		// This code ensures that all the pixels in the image are loaded
		image = new ImageIcon(image).getImage();

		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		try {
			// Determine the type of transparency of the new buffered image
			int transparency = Transparency.OPAQUE;
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null),
					image.getHeight(null), transparency);
		} catch (HeadlessException e) {
			// The system does not have a screen
		}

		if (bimage == null) {
			// Create a buffered image using the default color model
			int type = BufferedImage.TYPE_INT_RGB;
			// int type = BufferedImage.TYPE_3BYTE_BGR;//by wang
			/*
			 * if (hasAlpha) { type = BufferedImage.TYPE_INT_ARGB; }
			 */
			bimage = new BufferedImage(image.getWidth(null),
					image.getHeight(null), type);
		}

		// Copy image to buffered image
		Graphics g = bimage.createGraphics();

		// Paint the image onto the buffered image
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bimage;
	}

	public static void writeJpeg(BufferedImage srcImage, OutputStream out)
			throws IOException {
		try (OutputStream buff = new BufferedOutputStream(out)) {
			ImageIO.write(srcImage, "jpeg", buff); // BufferedOutputStream FIX
													// EOFException Broken Pipe
		}
	}

	public static void main(String[] args) throws Exception {

		// x=43&y=133&w=1003&h=752&rw=200&rh=150
		int x = 43;
		int y = 133;
		int w = 1003;
		int h = 752;
		int rw = 200;
		int rh = 150;
		BufferedImage img = ImageIO.read(new java.io.FileInputStream(
				"/Users/lava/Downloads/1.jpg"));
		File file1 = new File("/Users/lava/Downloads", "/test1.jpg");
		File file2 = new File("/Users/lava/Downloads", "/test2.jpg");
		File file3 = new File("/Users/lava/Downloads", "/test3.jpg");
		File file4 = new File("/Users/lava/Downloads", "/test4.jpg");
		BufferedImage cutImg = AuthCode.cutImage(img, x, y, w, h);
		// Thumbnails.of(cutImg).toFile(file1);
		AuthCode.writeJpeg(cutImg, new FileOutputStream(file1));
		AuthCode.writeJpeg(AuthCode.compressImage(cutImg, rw, rh),
				new FileOutputStream(file2));
		Thumbnails.of(cutImg).size(rw, rh).keepAspectRatio(false)
				.outputQuality(0.95).toFile(file2);

		int srcWidth = cutImg.getWidth(null);
		int srcHeight = cutImg.getHeight(null);
		if (srcWidth <= rw && srcHeight <= rh) {
			return;
		}
		double ratio = Math
				.min((double) rw / srcWidth, (double) rh / srcHeight);
		System.out.println(ratio);
		Thumbnails.of(cutImg).scale(ratio).outputQuality(0.98).toFile(file3);
		Thumbnails.of(cutImg).scale(ratio).outputQuality(1).toFile(file4);
		// draw("xhgl",160,48,new java.io.FileOutputStream("c:/authcode.png"));

		// drawSkew("X12B",160,48,new
		// java.io.FileOutputStream("c:/authcode2.png"));

	}

}
