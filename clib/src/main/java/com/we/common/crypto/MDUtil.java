package com.we.common.crypto;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public enum MDUtil {

	MD2("MD2"), MD5("MD5"), SHA("SHA-1"), SHA256("SHA-256"), SHA384("SHA-384"), SHA512("SHA-512");

	private MDUtil(String name) {
		this.name = name;
	}

	final ThreadLocal<MessageDigest> mdLocal = new ThreadLocal<MessageDigest>();

	public byte[] digest(byte[] targets) {
		MessageDigest md = (MessageDigest) this.mdLocal.get();
		if (md == null) {
			try {
				md = MessageDigest.getInstance(this.name);
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException("该算法不存在:" + this.name, e);
			}
			this.mdLocal.set(md);
		} else {
			md.reset();
		}
		return md.digest(targets);
	}

	public String digest2HEX(byte[] targets, boolean toLowerCase) {
		return new String(encodeHex(digest(targets), toLowerCase));
	}

	public String digest2HEX(String targets, boolean toLowerCase) {
		try {
			return digest2HEX(targets.getBytes("UTF-8"), toLowerCase);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public String digest2HEX(String targets) {
		return digest2HEX(targets, true);
	}

	public static final char[] encodeHex(byte[] data, boolean toLowerCase) {
		char[] toDigits = toLowerCase ? DIGITS_LOWER : DIGITS_UPPER;
		int l = data.length;
		char[] out = new char[l << 1];

		int i = 0;
		for (int j = 0; i < l; i++) {
			out[(j++)] = toDigits[((0xF0 & data[i]) >>> 4)];
			out[(j++)] = toDigits[(0xF & data[i])];
		}
		return out;
	}

	private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };
	private static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F' };
	final String name;

	/** 获取32位md5密码 */
	public static String getMD5(String str) {
		try {
			// 生成一个MD5加密计算摘要
			MessageDigest md = MessageDigest.getInstance(MD5.name);
			// 计算md5函数
			md.update(str.getBytes());
			// digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
			// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
			String md5 = new BigInteger(1, md.digest()).toString(16);
			// BigInteger会把0省略掉，需补全至32位
			return fillMD5(md5);
		} catch (Exception e) {
			throw new RuntimeException("MD5加密错误:" + e.getMessage(), e);
		}
	}

	public static String fillMD5(String md5) {
		return md5.length() == 32 ? md5 : fillMD5("0" + md5);
	}

	public static void main(String [] args) {
		System.out.println(MDUtil.SHA.digest2HEX("123456"));
	}
}
