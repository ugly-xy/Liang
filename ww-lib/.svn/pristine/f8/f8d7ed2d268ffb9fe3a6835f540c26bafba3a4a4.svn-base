package com.zb.common.crypto;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public enum MDUtil {

	MD2("MD2"), MD5("MD5"), SHA("SHA-1"), SHA256("SHA-256"), SHA384("SHA-384"), SHA512(
			"SHA-512");

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

	private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	final String name;
	
	public static void main(String [] args){
		Long timestamp=1459745144286L;
				String ptoken="33721134C7F189CC3C7766F425FD453C";
		String MD5_KEY = "zbda12ddcc";
		String sign = MDUtil.MD5.digest2HEX(MD5_KEY+timestamp);
		System.out.print(MDUtil.MD5.digest2HEX(MD5_KEY+timestamp));
		System.out.println(sign.equals(ptoken));
		
		System.out.println(MDUtil.SHA.digest2HEX("123456"));
	}

}
