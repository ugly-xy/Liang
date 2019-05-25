package com.zb.common.crypto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class DESUtil {

	private final static String ALGORITHM = "DES";

	private static final String ALGORITHM_3DES = "DESede";

	public static String decryptHex(String key, String textHex)
			throws DecoderException, Exception {
		return new String(decrypt(Hex.decodeHex(textHex.toCharArray()), key));
	}

	public static String encryptHex(String key, String data) throws Exception {
		return Hex.encodeHexString(encrypt(data.getBytes(), key));
	}

	public static byte[] encrypt(byte[] data, String key) throws Exception {
		return encrypt(data, key.getBytes());
	}

	public static byte[] decrypt(byte[] data, String key) throws Exception {
		return decrypt(data, key.getBytes());
	}

	/**
	 * 加密
	 * 
	 * @param data
	 *            明文(字节)
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 密文(字节)
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		return encrypt(data, key, ALGORITHM);
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            密文(字节)
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 明文(字节)
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		return decrypt(data, key, ALGORITHM);
	}

	/**
	 * 加密
	 * 
	 * @param data
	 *            明文(字节)
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 密文(字节)
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key, String algorithm)
			throws Exception {
		SecretKeySpec securekey = new SecretKeySpec(key, algorithm);
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, securekey);
		return cipher.doFinal(data);
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            密文(字节)
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 明文(字节)
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key, String algorithm)
			throws Exception {
		SecretKeySpec securekey = new SecretKeySpec(key, algorithm);
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, securekey);
		return cipher.doFinal(data);
	}

	

	public static void main(String[] args) throws Exception {
		String data = "1231231231ddfdbnaa";
		String key = "abcd1234";
		String encode = encryptHex(key, data);
		System.out.println(encode);
		String decode = decryptHex(key, encode);
		System.out.println(decode);
	}
}
