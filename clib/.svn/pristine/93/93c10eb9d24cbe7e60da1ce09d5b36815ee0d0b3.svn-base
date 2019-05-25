package com.we.common.utils;

//import com.temesoft.security.image.SkewImageSimple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 验证码生成类
 */
public abstract class RandomUtil {

	static final Logger logger = LoggerFactory.getLogger(RandomUtil.class);

	static final char[] SEEDS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"
			.toCharArray();

	static final char[] NOSEEDS = "AaBbCcDdEeFfGgHhJjKkLMmNnPpQqRrSsTtUuVvWwXxYyZz23456789"
			.toCharArray();
	
	static final char[] NUMBERS = "0123456789"
			.toCharArray();


	public static int nextInt(int max) {
		return ThreadLocalRandom.current().nextInt(max);
	}
	
	public static double nextDouble() {
		return ThreadLocalRandom.current().nextDouble();
	}

	public static String random(int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("size must > 0");
		}
		char[] result = new char[size];
		Random random = ThreadLocalRandom.current();
		int len = SEEDS.length;
		for (int i = 0; i < size; i++) {
			result[i] = SEEDS[random.nextInt(len)];
		}
		return new String(result);
	}
	
	public static String randomNum(int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("size must > 0");
		}
		char[] result = new char[size];
		Random random = ThreadLocalRandom.current();
		int len = NUMBERS.length;
		for (int i = 0; i < size; i++) {
			result[i] = NUMBERS[random.nextInt(len)];
		}
		return new String(result);
	}

	public static String randomNoCase(int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("size must > 0");
		}
		char[] result = new char[size];
		Random random = ThreadLocalRandom.current();
		int len = NOSEEDS.length;
		for (int i = 0; i < size; i++) {
			result[i] = NOSEEDS[random.nextInt(len)];
		}
		return new String(result);
	}

}
