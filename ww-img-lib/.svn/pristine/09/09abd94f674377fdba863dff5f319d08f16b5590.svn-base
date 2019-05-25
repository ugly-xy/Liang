package com.zb.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jhlabs.image.*;

/**
 * 水平垂直变换
 * 
 * @author lava
 *
 */
public class TestFilter {

	public static void main(String[] args) throws IOException {

		File s = new File("/Users/lava/Downloads/pics/fxg.png");
		BufferedImage src = ImageIO.read(s);

		File d = new File("/Users/lava/Downloads/pics/fxg0.png");
		InvertAlphaFilter iaf = new InvertAlphaFilter();
		BufferedImage dest = iaf.filter(src, null);
		ImageIO.write(dest, "png", d);
	}

}