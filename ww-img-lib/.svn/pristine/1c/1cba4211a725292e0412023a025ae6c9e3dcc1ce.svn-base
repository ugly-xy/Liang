package com.zb.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageMerge {

	public static BufferedImage getBufferedImage(String fileUrl)
			throws IOException {
		File f = new File(fileUrl);
		return ImageIO.read(f);
	}

	public static boolean saveImage(BufferedImage savedImg, String saveDir,
			String fileName, String format) {
		boolean flag = false;

		String[] legalFormats = { "jpg", "JPG", "png", "PNG", "bmp", "BMP" };
		int i = 0;
		for (i = 0; i < legalFormats.length; i++) {
			if (format.equals(legalFormats[i])) {
				break;
			}
		}
		if (i == legalFormats.length) {
			return false;
		}

		String postfix = fileName.substring(fileName.lastIndexOf('.') + 1);
		if (!postfix.equalsIgnoreCase(format)) {
			return false;
		}

		String fileUrl = saveDir + fileName;
		File file = new File(fileUrl);
		try {
			flag = ImageIO.write(savedImg, format, file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return flag;
	}

	public static BufferedImage mergeImage(BufferedImage img1,
			BufferedImage img2, boolean isHorizontal) throws IOException {
		int w1 = img1.getWidth();
		int h1 = img1.getHeight();
		int w2 = img2.getWidth();
		int h2 = img2.getHeight();

		int[] ImageArrayOne = new int[w1 * h1];
		ImageArrayOne = img1.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1);
		int[] ImageArrayTwo = new int[w2 * h2];
		ImageArrayTwo = img2.getRGB(0, 0, w2, h2, ImageArrayTwo, 0, w2);

		BufferedImage DestImage = null;
		if (isHorizontal) {
			DestImage = new BufferedImage(w1 + w2, h1,
					BufferedImage.TYPE_INT_RGB);
			DestImage.setRGB(0, 0, w1, h1, ImageArrayOne, 0, w1);
			DestImage.setRGB(w1, 0, w2, h2, ImageArrayTwo, 0, w2);
		} else {
			DestImage = new BufferedImage(w1, h1 + h2,
					BufferedImage.TYPE_INT_RGB);
			DestImage.setRGB(0, 0, w1, h1, ImageArrayOne, 0, w1);
			DestImage.setRGB(0, h1, w2, h2, ImageArrayTwo, 0, w2);
		}

		return DestImage;
	}

	public static void main(String[] args) {
		BufferedImage bi1 = null;
		BufferedImage bi2 = null;
		BufferedImage destImg = null;

		try {
			bi1 = getBufferedImage("D:\\data\\3.jpg");
			bi2 = getBufferedImage("D:\\data\\2.jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			destImg = mergeImage(bi1, bi2, true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		saveImage(destImg, "D:\\data\\", "luguo.png", "png");
		try {
			destImg = mergeImage(bi1, bi2, false);
		} catch (IOException e) {
			e.printStackTrace();
		}

		saveImage(destImg, "D:\\data\\", "luguo1.png", "png");

	}

}
