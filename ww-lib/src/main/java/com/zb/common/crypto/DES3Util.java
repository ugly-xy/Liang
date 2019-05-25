package com.zb.common.crypto;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DES3Util {

	static final Logger log = LoggerFactory.getLogger(DES3Util.class);

	/**
	 * 
	 * @param value
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String Decrypt3DES(String value, String key) throws Exception {
		byte[] b = decryptMode(key.getBytes(), Base64.decodeBase64(value));
		return new String(b);
	}

	/**
	 * 
	 * @param value
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String Encrypt3DES(String value, String key) throws Exception {
		String str = byte2Base64(encryptMode(key.getBytes(), value.getBytes()));
		return str;
	}

	private static final String Algorithm = "DESede"; // 定义 加密算法,可用

	/**
	 * 
	 * @param keybyte
	 * @param src
	 * @return
	 */
	public static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); // 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			log.error("ERR 3des encrypt:", e1);
		} catch (javax.crypto.NoSuchPaddingException e2) {
			log.error("ERR 3des encrypt:", e2);
		} catch (java.lang.Exception e3) {
			log.error("ERR 3des encrypt:", e3);
		}
		return null;
	}

	/**
	 * 
	 * @param keybyte
	 * @param src
	 * @return
	 */
	public static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try { // 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			log.error("ERR 3des encrypt:", e1);
		} catch (javax.crypto.NoSuchPaddingException e2) {
			log.error("ERR 3des encrypt:", e2);
		} catch (java.lang.Exception e3) {
			log.error("ERR 3des encrypt:", e3);
		}
		return null;
	}

	// 转换成base64编码

	public static String byte2Base64(byte[] b) {
		return Base64.encodeBase64String(b);
	}

	// 转换成十六进制字符串

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}

	public static void main(String[] args) throws Exception {
		String key = "F6011F0398F3461BA53D3A9B";
		String password = "13812341234;test;F;;;";
		String result = "BUaQuX6YkXw2Vh9ANbxnQLPXGMpzeIbh";
		System.out.println("key=" + key + ",password=" + password);
		System.out.println("----------示例开始：使用java写的算法加密解密-----------");
		try {

			String encrypt = "";

			String decrypt = "";

			// byte[] bkey = DES3Util.GetKeyBytes(key);

			encrypt = DES3Util.byte2Base64(DES3Util.encryptMode(key.getBytes(),
					password.getBytes()));

			System.out.println("用预转换密钥算加密结果=" + encrypt);

			System.out.println("加密后base64表示="
					+ DES3Util.byte2hex(Base64.decodeBase64(encrypt)));

			System.out.println("调用原始密钥算加密结果="
					+ DES3Util.Encrypt3DES(password, key));

			try {

				decrypt = new String(DES3Util.decryptMode(key.getBytes(),
						Base64.decodeBase64(encrypt)));

				System.out.println("用预转换密钥算解密结果=" + decrypt);

				System.out.println("调用原始密钥算解密结果="
						+ DES3Util.Decrypt3DES(encrypt, key));

			} catch (Exception ex) {

				System.out.println("Exception:" + ex.getMessage());

			}

		} catch (Exception ex) {

			System.out.println("Exception:" + ex.getMessage());

		}

		System.out.println("----------示例结束：使用java写的算法加密解密-----------");

	}
}
