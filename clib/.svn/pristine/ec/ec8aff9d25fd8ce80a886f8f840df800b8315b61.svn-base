package com.we.common.crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class BlowFishUtil {

	// 转换模式
	public static final String TRANSFORMATION = "Blowfish/CBC/PKCS5Padding";
	// 密钥算法名称
	public static final String ALGORITHM = "Blowfish";

	/**
	 * 加密
	 * 
	 * @param key
	 *            密钥
	 * @param text
	 *            明文
	 * @param initializationVector
	 *            初始向量
	 * @return 密文
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws Exception
	 */

	public static byte[] encrypt(byte[] key, byte[] data,
			byte[] initializationVector) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException {

		// 根据给定的字节数组构造一个密钥 Blowfish-与给定的密钥内容相关联的密钥算法的名称
		SecretKeySpec sksSpec = new SecretKeySpec(key, ALGORITHM);
		// 使用 initializationVector 中的字节作为 IV 来构造一个 IvParameterSpec 对象
		AlgorithmParameterSpec iv = new IvParameterSpec(initializationVector);
		// 返回实现指定转换的 Cipher 对象
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		// 用密钥和随机源初始化此 Cipher
		cipher.init(Cipher.ENCRYPT_MODE, sksSpec, iv);
		// 加密文本
		return cipher.doFinal(data);
	}

	/**
	 * 加密返回base64
	 * 
	 * @param key
	 *            密钥
	 * @param text
	 *            明文
	 * @param initializationVector
	 *            初始向量
	 * @return base64编码后的密文
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws Exception
	 */
	public static String encryptHex(String key, String data,
			String initializationVector) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException {
		return Hex.encodeHexString(encrypt(key.getBytes(),
				data.getBytes(), initializationVector.getBytes()));
	}

	/**
	 * 解密base64编码后的密文
	 * 
	 * @param key
	 *            密钥
	 * @param textBase64
	 *            base64编码后的密文
	 * @param initializationVector
	 *            初始偏移量
	 * @return 明文
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws DecoderException 
	 * @throws Exception
	 */
	public static String decryptHex(String key, String textHex,
			String initializationVector) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException, DecoderException {
		
		return new String(decrypt(key.getBytes(),
				Hex.decodeHex(textHex.toCharArray()),
				initializationVector.getBytes()));
	}

	/**
	 * 解密
	 * 
	 * @param key
	 *            密钥
	 * @param data
	 *            密文
	 * @param initializationVector
	 *            初始偏移量
	 * @return 明文
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] key, byte[] data,
			byte[] initializationVector) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException {
		SecretKeySpec skeSpect = new SecretKeySpec(key, ALGORITHM);
		AlgorithmParameterSpec iv = new IvParameterSpec(initializationVector);
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(Cipher.DECRYPT_MODE, skeSpect, iv);
		return cipher.doFinal(data);
	}
	
	public static void main(String [] args){
		try {
			String key = "12d332d1s1dw123d";
			String initializationVector= "431238ad";
			String aaa = BlowFishUtil.encryptHex(key, "test1111122222333334444455", initializationVector);
			System.out.println(aaa);
			String cenc = "37431a9357d8068ff7059f12993a07e386e48335023b2555";
			
			System.out.println(BlowFishUtil.decryptHex(key, aaa, initializationVector));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
