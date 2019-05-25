package com.zb.service.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.model.OSSObject;
import com.gif4j.light.GifImage;
import com.zb.core.conf.Config;
import com.zb.service.cloud.StorageService;
import com.zb.util.RotatedImage;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

public abstract class BaseTool {

	static final Logger log = LoggerFactory.getLogger(BaseTool.class);

	protected StorageService storageService;

	public BaseTool(StorageService storageService) {
		this.storageService = storageService;
	}

	public BaseTool() {
	}

	public StorageService getStorageService() {
		return storageService;
	}

	public void setStorageService(StorageService storageService) {
		this.storageService = storageService;
	}

	public static final String DEF_IMG_FORMAT_NAME = "png";

	public String getDefFormatName() {
		return DEF_IMG_FORMAT_NAME;
	}

	public InputStream getFile(String path) throws FileNotFoundException {
		
		if (path.startsWith("http")) {
			OSSObject obj = storageService.getObject(path);
			return obj.getObjectContent();
		} else {
			if (path.contains("?")) {
				path = path.substring(0, path.indexOf("?"));
			}else if(path.startsWith("C:") || path.startsWith("/var")){
				return new FileInputStream(new File(path));
			}
			path = this.getRootPath() + path;
			return new FileInputStream(new File(path));
		}
	}

	public BufferedImage getImg(String path) {
		InputStream in = null;
		try {
			in = getFile(path);
			return Thumbnails.of(in).scale(1.0d).asBufferedImage();
		} catch (FileNotFoundException e) {
			log.error("ERROR:", e);
		} catch (IOException e) {
			log.error("ERROR:", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					log.error("ERROR:", e);
				}
			}
		}
		return null;
	}

	// TODO is debug;
	Boolean debug = false;

	public void isDebug(boolean debug) {
		this.debug = debug;
	}

	public String saveFile(BufferedImage bi, String formatName)
			throws IOException {
		if (debug) {
			File f = new File(this.getRootPath() + "aaaaa.png");
			Thumbnails.of(bi).scale(1d).toFile(f);
			return null;
		} else {
			return storageService.saveFile(bi, true, formatName);
		}
	}
	public String saveFile(BufferedImage bi, String formatName,String outPath)
			throws IOException {
			File f = new File(outPath);
			Thumbnails.of(bi).scale(1d).toFile(f);
			return null;
		
	}

	public String saveFile(ByteArrayOutputStream out, String formatName)
			throws IOException {
		formatName = formatName.toLowerCase();
		if (debug) {
			FileOutputStream fos = new FileOutputStream(new File(
					this.getRootPath() + "aaaaa." + formatName));
			out.writeTo(fos);
			return null;
		} else {
			return storageService.saveFile(out, true, formatName);
		}
	}
	public String saveVideoFile(ByteArrayOutputStream out, String formatName)
			throws IOException {
		formatName = formatName.toLowerCase();
		if (debug) {
			FileOutputStream fos = new FileOutputStream(new File(
					this.getRootPath() + "aaaaa." + formatName));
			out.writeTo(fos);
			return null;
		} else {
			return storageService.saveVideoFile(out,formatName);
		}
	}

	protected static final GraphicsConfiguration GC = getGraphicsConfiguration();

	private static GraphicsConfiguration getGraphicsConfiguration() {
		Graphics2D _2D = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)
				.createGraphics();
		GraphicsConfiguration config = _2D.getDeviceConfiguration();
		_2D.dispose();// TODO add dispose
		return config;
	}

	private String rootPahtDef = "/opt/";

	public String getRootPath() {
		return Config.cur().get("img.root.path", rootPahtDef);
	}

	public void setRootPathDef(String path) {
		rootPahtDef = path;
	}

	public String tmpsPath() {
		return Config.cur().get("img.tmps.path", "tmps/");
	}

	public String imgDomain() {
		return Config.cur().get("img.domain", "img.zhuangdianbi.com");
	}

	public static String simplePath() {
		SimpleDateFormat fmt = new SimpleDateFormat("yy/MMdd/HH/");
		return fmt.format(new Date());
	}

	static Random r = new Random();

	public static String simpleFilename() {
		int i = r.nextInt(1000);
		if (i < 10) {
			i = i * 100;
		} else if (i < 100) {
			i = i * 10;
		}
		return "" + System.currentTimeMillis() + i;
	}

	public static BufferedImage drawText(String txt, ZbFont zbf, int fontSize,
			Color color, int maxWidth) throws IOException {
		txt=txt.replaceAll("\n|\r","");
		return drawText(txt, zbf.font(), zbf.type(), fontSize, color, maxWidth,
				0, 0, true);
	}

	public static BufferedImage drawText(String txt, String fontStyle,
			int fontType, int fontSize, Color color, int maxWidth)
			throws IOException {
		txt=txt.replaceAll("\n|\r","");
		return drawText(txt, fontStyle, fontType, fontSize, color, maxWidth, 0,
				0, true);
	}

	public static BufferedImage drawText(String txt, ZbFont zbf, int fontSize,
			Color color, int maxWidth, int maxHeight, double theta,
			boolean isRenderingHint) throws IOException {
		
		txt=txt.replaceAll("\n|\r","");
		return drawText(txt, zbf.font(), zbf.type(), fontSize, color, maxWidth,
				maxHeight, theta, isRenderingHint);
	}

	/***
	 * 
	 * @param txt
	 * @param fontStyle
	 * @param fontType
	 * @param fontSize
	 * @param color
	 * @param maxWidth
	 * @param theta
	 *            字体旋转角度
	 * @param isRenderingHint
	 *            字体是否去除锯齿
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage drawText(String txt, String fontStyle,
			int fontType, int fontSize, Color color, int maxWidth,
			int maxHeight, double theta, boolean isRenderingHint)
			throws IOException {
		txt=txt.replaceAll("\n|\r","");
		Font f = new Font(fontStyle, fontType, fontSize);
		int textWeight = maxWidth;
		int tmpTextWeight = txt.length() * (fontSize + 2);
		int textHeight = 0;

		if (maxHeight != 0) {
			textHeight = maxHeight;
		} else {
			textHeight = fontSize * 2;
			if (tmpTextWeight > textWeight) {
				int line = tmpTextWeight % textWeight == 0 ? (tmpTextWeight / textWeight)
						: tmpTextWeight / textWeight + 1;
				textHeight = line * textHeight;
				// System.out.println("aaa:" + textHeight);
			}
		}
		// System.out.println("sss:" + textHeight);
		BufferedImage buffImg = GC.createCompatibleImage(textWeight,
				textHeight, Transparency.TRANSLUCENT);
		Graphics2D graphics = buffImg.createGraphics();
		graphics.clipRect(1, 1, textWeight - 2, textHeight - 2);
		graphics.setColor(color);
		graphics.setFont(f);
		if (isRenderingHint) {
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}
		if (theta != 0) {
			// 旋转中心
			double x = tmpTextWeight / 2;
			double y = textHeight / 2;
			// 旋转角度
			graphics.setTransform(AffineTransform
					.getRotateInstance(theta, x, y));
		}
		FontMetrics fm = graphics.getFontMetrics(f);
		int fontHeight = fm.getHeight();
		int offsetLeft = 0;
		int rowIndex = 1;
		for (int i = 0; i < txt.length(); i++) {
			char c = txt.charAt(i);
			int charWidth = fm.charWidth(c); // 字符的宽度
			// 另起一行
			if (Character.isISOControl(c)) {
				rowIndex++;
				offsetLeft = 0;
				continue;
			}
			if (offsetLeft >= (textWeight - charWidth)) {
				rowIndex++;
				offsetLeft = 0;
			}
			graphics.drawString(String.valueOf(c), offsetLeft, rowIndex
					* fontHeight);
			offsetLeft += charWidth;
		}
		graphics.dispose();// TODO add dispose
		return buffImg;
	}

	public static BufferedImage drawText(String txt, ZbFont f, int fontSize,
			Color color, int maxWidth, int maxHeight, double theta,
			boolean isRenderingHint, int left, int top) throws IOException {
		txt=txt.replaceAll("\n|\r","");
		return drawText(txt, f.font(), f.type(), fontSize, color, maxWidth,
				maxHeight, theta, isRenderingHint, left, top);

	}

	public static BufferedImage drawText(String txt, String fontStyle,
			int fontType, int fontSize, Color color, int maxWidth,
			int maxHeight, double theta, boolean isRenderingHint, int left,
			int top) throws IOException {
		txt=txt.replaceAll("\n|\r","");
		Font f = new Font(fontStyle, fontType, fontSize);
		int textWeight = maxWidth;
		int tmpTextWeight = txt.length() * (fontSize + 2);
		int textHeight = 0;

		if (maxHeight != 0) {
			textHeight = maxHeight;
		} else {
			textHeight = fontSize * 2;
			if (tmpTextWeight > textWeight) {
				int line = tmpTextWeight % textWeight == 0 ? (tmpTextWeight / textWeight)
						: tmpTextWeight / textWeight + 1;
				textHeight = line * textHeight;
				// System.out.println("aaa:" + textHeight);
			}
		}
		// System.out.println("sss:" + textHeight);
		BufferedImage buffImg = GC.createCompatibleImage(textWeight,
				textHeight, Transparency.TRANSLUCENT);
		Graphics2D graphics = buffImg.createGraphics();
		graphics.clipRect(1, 1, textWeight - 2, textHeight - 2);
		graphics.setColor(color);
		graphics.setFont(f);
		if (isRenderingHint) {
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}
		if (theta != 0) {
			// 旋转中心
			double x = tmpTextWeight / 2;
			double y = textHeight / 2;
			// 旋转角度
			graphics.setTransform(AffineTransform
					.getRotateInstance(theta, x, y));
		}
		FontMetrics fm = graphics.getFontMetrics(f);
		int fontHeight = fm.getHeight();
		int offsetLeft = left;
		int rowIndex = 1;
		for (int i = 0; i < txt.length(); i++) {
			char c = txt.charAt(i);
			int charWidth = fm.charWidth(c); // 字符的宽度
			// 另起一行
			if ('\n' == c) {
				rowIndex++;
				offsetLeft = left;
				continue;
			}
			if (offsetLeft >= (textWeight - charWidth)) {
				rowIndex++;
				offsetLeft = left;
			}
			graphics.drawString(String.valueOf(c), offsetLeft, top + rowIndex
					* fontHeight);
			offsetLeft += charWidth;
		}
		graphics.dispose();// TODO add dispose
		return buffImg;
	}

	/***
	 * 压缩图片
	 * 
	 * @param imgPath
	 *            源图片路径
	 * @param width
	 *            目标图片宽
	 * @param height
	 *            目标图片高
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public BufferedImage compressImage(String imgPath, int width, int height) {
		BufferedImage newImg = GC.createCompatibleImage(width, height,
				Transparency.TRANSLUCENT);
		InputStream is = null;
		BufferedImage org = null;
		try {
			is = getFile(imgPath);
			org = ImageIO.read(is);
			newImg.getGraphics().drawImage(
					org.getScaledInstance(width, height, Image.SCALE_SMOOTH),
					0, 0, null);
		} catch (IOException e) {
			log.error("ERROR:", e);
		} finally {
			newImg.flush();
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					log.error("ERROR:", e);
				}
			if (org != null)
				org.flush();
		}
		return newImg;
	}

	public BufferedImage createImage(int width, int height, Color color) {
		BufferedImage newImg = GC.createCompatibleImage(width, height,
				Transparency.TRANSLUCENT);
		Graphics2D g2 = newImg.createGraphics();
		g2.setBackground(color);
		g2.clearRect(0, 0, width, height);
		return newImg;
	}

	/***
	 * 
	 * @param sp
	 *            图片位置数组
	 * @param bis
	 *            图片数组
	 * @param opacity
	 *            透明度值
	 * @param tmpPath
	 *            模板路径
	 * @return 返回生成图片的地址
	 */
	public String getSaveFile(SimplePositions[] sp, BufferedImage[] bis,
			float opacity, String tmpPath) {
		InputStream in = null;
		BufferedImage bi = null;
		try {
			in = getFile(tmpPath);
			Builder<?> builder = Thumbnails.of(in).scale(1.0d)
					.outputQuality(1.0d);
			for (int i = 0; i < sp.length; i++) {
				builder.watermark(sp[i], bis[i], opacity);
			}
			bi = builder.outputFormat("png").asBufferedImage();
			return saveFile(bi, getDefFormatName());
		} catch (FileNotFoundException e) {
			log.error("ERROR:" + e);
		} catch (IOException e) {
			log.error("ERROR:" + e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (bi != null) {
					bi.flush();
				}
				for (BufferedImage cbi : bis) {
					cbi.flush();
				}
			} catch (IOException e) {
				log.error("ERROR:" + e);
			}
		}
		return null;
	}
	public String getSaveFile(SimplePositions[] sp, BufferedImage[] bis,
			float opacity, String tmpPath,String outPath) {
		InputStream in = null;
		BufferedImage bi = null;
		try {
			in = getFile(tmpPath);
			Builder<?> builder = Thumbnails.of(in).scale(1.0d)
					.outputQuality(1.0d);
			for (int i = 0; i < sp.length; i++) {
				builder.watermark(sp[i], bis[i], opacity);
			}
			bi = builder.outputFormat("png").asBufferedImage();
			return saveFile(bi, getDefFormatName(),outPath);
		} catch (FileNotFoundException e) {
			log.error("ERROR:" + e);
		} catch (IOException e) {
			log.error("ERROR:" + e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (bi != null) {
					bi.flush();
				}
				for (BufferedImage cbi : bis) {
					cbi.flush();
				}
			} catch (IOException e) {
				log.error("ERROR:" + e);
			}
		}
		return null;
	}

	public String getSaveFile(SimplePositions[] sp, BufferedImage[] bis,
			float opacity, BufferedImage bi) {
		BufferedImage bi2 = null;
		try {
			Builder<?> builder = Thumbnails.of(bi).scale(1.0d)
					.outputQuality(1.0d);
			for (int i = 0; i < sp.length; i++) {
				builder.watermark(sp[i], bis[i], opacity);
			}
			bi2 = builder.outputFormat("png").asBufferedImage();
			return saveFile(bi2, getDefFormatName());
		} catch (FileNotFoundException e) {
			log.error("ERROR:" + e);
		} catch (IOException e) {
			log.error("ERROR:" + e);
		} finally {
			if (bi != null) {
				bi.flush();
			}
			if (bi2 != null) {
				bi2.flush();
			}
			for (BufferedImage cbi : bis) {
				cbi.flush();
			}
		}
		return null;
	}

	/**
	 * 正弦曲线Wave扭曲图片
	 * 
	 * @param buffImg
	 * @param graphics
	 * @param multValue
	 *            波形的幅度倍数，越大扭曲的程序越高，一般为3
	 * @param dPhase
	 *            波形的起始相位，取值区间（0-2＊PI）
	 * @return
	 */
	static BufferedImage doTwist(BufferedImage buffImg, double multValue,
			double phase, boolean isRenderingHint) {
		int width = buffImg.getWidth();
		int height = buffImg.getHeight();
		// Random random = ThreadLocalRandom.current();
		double dMultValue = multValue + Math.PI;// 波形的幅度倍数，越大扭曲的程序越高，一般为3
		double dPhase = phase;// 波形的起始相位，取值区间（0-2＊PI）

		BufferedImage destBi = GC.createCompatibleImage(width, height,
				Transparency.TRANSLUCENT);
		if (isRenderingHint) {
			Graphics2D graphics = destBi.createGraphics();
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			// graphics.dispose();
		}
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

	static BufferedImage rotateImg(BufferedImage image, int degree,
			Color bgcolor) throws IOException {
		return RotatedImage.rotateImg(image, degree, bgcolor, 0, 0);
	}

	static BufferedImage rotateImg(BufferedImage image, int degree,
			Color bgcolor, int width, int height) throws IOException {
		return RotatedImage.rotateImg(image, degree, bgcolor, width, height);
	}

	public String drawTextImg(String name, int fontSize, int left, int top,
			ZbFont f, boolean isH, Color c, double theta, String tmpPath)
			throws IOException {
		int w = (int) (1.5 * fontSize);
		int h = w * name.length();
		if (isH) {
			return this.drawTextImg(name, fontSize, left, top, f, c, theta, h,
					w, tmpPath);
		} else {
			return this.drawTextImg(name, fontSize, left, top, f, c, theta, w,
					h, tmpPath);
		}

	}

	public String drawTextImg(String[] txts, int[] fontSizes, int[] lefts,
			int[] tops, ZbFont[] fs, boolean[] isHs, Color[] cs,
			double[] thetas, boolean[] isRenderingHints, String tmpPath)
			throws IOException {
		int len = txts.length;
		int[] maxWidths = new int[len];
		int[] maxHeights = new int[len];
		for (int i = 0; i < len; i++) {
			int w = (int) (1.5 * fontSizes[i]);
			int h = w * txts[i].length() * 2;
			if (isHs[i]) {
				maxWidths[i] = w;
				maxHeights[i] = h;
			} else {
				maxWidths[i] = h;
				maxHeights[i] = w;
			}
		}
		return this.drawTextImg(txts, fontSizes, lefts, tops, fs, cs, thetas,
				isRenderingHints, maxWidths, maxHeights, tmpPath);

	}

	public String drawTextImg(String name, int fontSize, int left, int top,
			ZbFont f, Color c, double theta, int maxWidth, int maxHeight,
			String tmpPath) throws IOException {
		String fontStyle = f.font();
		int fontType = f.type();
		// 姓名
		SimplePositions nameSP = new SimplePositions();
		nameSP.buildHorizontalOffset(left).buildVerticalOffset(top);
		BufferedImage nameBI = drawText(name, fontStyle, fontType, fontSize, c,
				maxWidth, maxHeight, theta, true);
		SimplePositions[] sp = { nameSP };
		BufferedImage[] bis = { nameBI };

		return getSaveFile(sp, bis, 0.8f, tmpPath);
	}

	public String drawTextImg(String[] txts, int[] fontSizes, int[] lefts,
			int[] tops, ZbFont[] fs, Color[] cs, double[] thetas,
			boolean[] isRenderingHints, int[] maxWidths, int[] maxHeights,
			String tmpPath) throws IOException {
		int len = txts.length;
		SimplePositions[] sp = new SimplePositions[len];
		BufferedImage[] bis = new BufferedImage[len];

		for (int i = 0; i < len; i++) {

			String fontStyle = fs[i].font();
			int fontType = fs[i].type();
			// 姓名
			SimplePositions nameSP = new SimplePositions();
			nameSP.buildHorizontalOffset(lefts[i]).buildVerticalOffset(tops[i]);
			BufferedImage nameBI = drawText(txts[i], fontStyle, fontType,
					fontSizes[i], cs[i], maxWidths[i], maxHeights[i],
					thetas[i], isRenderingHints[i]);
			sp[i] = nameSP;
			bis[i] = nameBI;
		}

		return getSaveFile(sp, bis, 0.85f, tmpPath);
	}

}
