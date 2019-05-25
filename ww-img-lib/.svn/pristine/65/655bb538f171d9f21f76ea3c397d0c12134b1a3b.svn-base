package com.zb.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageAlpha {

	private static final int MIN_ALPHA = 0;
	private static final int MAX_ALPHA = 254;

	private int sX;
	private int sY;
	private int eX;
	private int eY;
	private int sXA;
	private int eXA;
	private int sYA;
	private int eYA;

	private int xType;
	private int yType;

	public BufferedImage setAlphaX(ByteArrayOutputStream os, int startX,
			int endX, int startAlpha) throws IOException {
		return setAlphaX(os, startX, endX, startAlpha, MAX_ALPHA);
	}

	public BufferedImage setAlphaX(ByteArrayOutputStream os, int startX,
			int endX, int startAlpha, int endAlpha) throws IOException {
		// assert (startX == endX);
		// assert (startAlpha == endAlpha);
		if (startAlpha < MIN_ALPHA) {
			startAlpha = MIN_ALPHA;
		}
		if (startAlpha > MAX_ALPHA) {
			startAlpha = MAX_ALPHA;
		}
		if (endAlpha < MIN_ALPHA) {
			endAlpha = MIN_ALPHA;
		}
		if (endAlpha > MAX_ALPHA) {
			endAlpha = MAX_ALPHA;
		}
		if (startAlpha > endAlpha) {
			int t = startAlpha;
			startAlpha = endAlpha;
			endAlpha = t;
		}
		ImageIcon imageIcon = new ImageIcon(os.toByteArray());
		int width = imageIcon.getIconWidth();
		int height = imageIcon.getIconHeight();
		BufferedImage bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_4BYTE_ABGR);
		int diffAlpha = Math.abs(endAlpha - startAlpha);

		int diffX = Math.abs(endX - startX);
		if (startX < endX) {
			xType = 1;
			if (startX < 0) {
				startX = 0;
				sX = 0;
				sXA = startAlpha;
			} else {
				sX = startX - (startAlpha * diffX) / diffAlpha;
				if (sX >= 0) {
					sXA = 0;
				} else {
					sX = 0;
					sXA = startAlpha - diffAlpha * startX / diffX;
				}
			}
			if (endX > width) {
				eX = width;
				eXA = endAlpha;
				endX = width;
			} else {
				eXA = endAlpha + (width - endX) * diffAlpha / diffX;
				if (eXA <= MAX_ALPHA) {
					eX = width;
				} else {
					eXA = MAX_ALPHA;
					eX = endX + diffX * (MAX_ALPHA - endAlpha) / diffAlpha;
				}
			}
		} else if (startX > endX) {
			xType = 2;// 2递减
			if (startX > width) {
				startX = width;
				sX = width;
				sXA = startAlpha;
			} else {
				sX = startX + (startAlpha * diffX) / diffAlpha;
				if (sX <= width) {
					sXA = 0;
				} else {
					sX = width;
					sXA = startAlpha - diffAlpha * (width - startX) / diffX;
				}
			}
			if (endX < 0) {
				eX = 0;
				eXA = endAlpha;
				endX = 0;
			} else {
				eXA = endAlpha + endX * diffAlpha / diffX;
				if (eXA <= MAX_ALPHA) {
					eX = 0;
				} else {
					eXA = MAX_ALPHA;
					eX = endX - diffX * (MAX_ALPHA - endAlpha) / diffAlpha;
				}
			}
		}

//		System.out.println("sX:" + sX + ",eX:" + eX + ",sXA:" + sXA + ",eXA:"
//				+ eXA + ",xType:" + xType);

		Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
		g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());
		int alpha = 0;
		int d = Math.abs(eX - sX);
		int da = Math.abs(eXA - sXA);
		int add = 1;
		int mod = 1;
		if (d > da) {
			mod = d / da;
		} else {
			add = da / d;
		}

		for (int j1 = bufferedImage.getMinY(); j1 < height; j1++) {
			if (xType == 1) {
				alpha = sXA;
			} else {
				alpha = eXA;
			}
			for (int j2 = bufferedImage.getMinX(); j2 < width; j2++) {
				if (xType == 1) {
					if (j2 > sX && j2 < eX && (j2 + 1) % mod == 0
							&& alpha < eXA) {
						alpha = alpha + add;
					}
				} else {
					if (j2 < sX && j2 > eX && (j2 + 1) % mod == 0
							&& alpha > sXA) {
						alpha = alpha - add;
					}
				}

				// System.out.print(" :" + alpha);

				int rgb = bufferedImage.getRGB(j2, j1);
				rgb = ((alpha + 1) << 24) | (rgb & 0x00ffffff);
				bufferedImage.setRGB(j2, j1, rgb);
			}
			// System.out.println();
		}
		g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());

		return bufferedImage;
	}

	public static void main(String[] args) throws IOException {
		File f = new File("/Users/lava/Downloads/abc.png");
		BufferedImage bi = ImageIO.read(f);

		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ImageIO.write(bi, "png", bout);

		ImageAlpha ia2 = new ImageAlpha();
		BufferedImage bi2 = ia2.setAlphaX(bout,30, 400, 20);
		ImageIO.write(bi2, "png", new File(
				"/Users/lava/Downloads/pics/fxg2.png"));

	}

}
