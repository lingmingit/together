package com.together.common.security;

import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import com.together.common.StringUtils;
import com.together.common.base.CommonConstants;


/**
 * 系统常用加密工具类<p>
 * @author LingMin 
 * @date 2014-11-03<br>
 * @version 1.0<br>
 */
public class EncryptUtils {
	/**
	 * 构造函数<p>
	 */
	private EncryptUtils() {}
	
	/**
	 * 对字符串进行BASE64加密<p>
	 * @param str 字符串<br>
	 * @return 加密字符串<br>
	 */
	public static String base64Encode(String str) {
		return StringUtils.isNotEmpty(str) ? new sun.misc.BASE64Encoder().encode(str.getBytes()) : "";
	}

	/**
	 * 对字符串进行BASE64解密<p>
	 * @param str 加密字符串<br>
	 * @return 字符串<br>
	 */
	public static String base64Decode(String str) {
		try {
			return StringUtils.isNotEmpty(str) ? new String(new sun.misc.BASE64Decoder().decodeBuffer(str)) : "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 对字符串进行md5加密<p>
	 * @param str 字符串<br>
	 * @return 加密字符串<br>
	 */
	public static String md5Encode(String str) {
		if (StringUtils.isNotEmpty(str)) {
			try {
				MessageDigest mdTemp = MessageDigest.getInstance("MD5");
				mdTemp.update(str.getBytes());
				byte[] md = mdTemp.digest();
				int j = md.length;
				char strA[] = new char[j * 2];
				int k = 0;
				for (int i = 0; i < j; i++) {
					byte byte0 = md[i];
					strA[k++] = CommonConstants.MD5HexDigits[byte0 >>> 4 & 0xf];
					strA[k++] = CommonConstants.MD5HexDigits[byte0 & 0xf];
				}
				return new String(strA);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	/**
	 * 获取字符串的SHA1的信息摘要HEX字符串<p>
	 * @param str 字符串<br>
	 * @return SHA1信息摘要HEX字符串<br>
	 */
	public static String hexStrMessageDigestForSHA1(String str) {
		return StringUtils.isNotEmpty(str) ? EncryptUtils.byteArrayToHexString(messageDigestForSHA1(str)) : "";
	}
	
	/**
	 * 根据SHA1信息摘要HEX字符串验证字符串是否正常<p>
	 * @param hexStr SHA1信息摘要HEX字符串<br>
	 * @param str 检验字符串<br>
	 * @return boolean true:正常 false:异常<br>
	 */
	public static boolean hexStrMessageDigestIsEqualForSHA1(String hexStr, String str) {
		if (StringUtils.isNotEmpty(str) && StringUtils.isNotEmpty(hexStr)) {
			try {
				java.security.MessageDigest messageDigestSHA = java.security.MessageDigest.getInstance("SHA-1");
				messageDigestSHA.update(str.getBytes());
				return java.security.MessageDigest.isEqual(EncryptUtils.hexStringToByteArray(hexStr), messageDigestSHA.digest());
			}  catch (java.security.NoSuchAlgorithmException ex) {
				ex.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 获取字符串的SHA1的信息摘要字节数组<p>
	 * @param str 字符串<br>
	 * @return SHA1信息摘要字节数组<br>
	 */
	public static byte[] messageDigestForSHA1(String str) {
		try {
			// 获取信息摘要实例对象
			java.security.MessageDigest messageDigestSHA = java.security.MessageDigest.getInstance("SHA-1");
			messageDigestSHA.update(str.getBytes());
			// 返回信息摘要字节数组
			return messageDigestSHA.digest();
		} catch (java.security.NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据SHA1信息摘要字节数组验证字符串是否正常<p>
	 * @param bytes SHA1信息摘要字符数组<br>
	 * @param str 检验字符串<br>
	 * @return boolean true:正常 false:异常<br>
	 */
	public static boolean messageDigestIsEqualForSHA1(byte[] bytes, String str) {
		try {
			java.security.MessageDigest messageDigestSHA = java.security.MessageDigest.getInstance("SHA-1");
			messageDigestSHA.update(str.getBytes());
			return java.security.MessageDigest.isEqual(bytes, messageDigestSHA.digest());
		}  catch (java.security.NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 以DES加密方式对字符串进行加密<p>
	 * @param str 字符串<br>
	 * @param key DES密匙<br>
	 * @return 加密字符串<br>
	 */
	public static String desEncode(String str, String key) {
		return StringUtils.isNotEmpty(str) && StringUtils.isNotEmpty(key) ? EncryptUtils.byteArrayToHexString(EncryptUtils.getDESEncode(str.getBytes(), key)) : "";
	}
	
	/**
	 * 以DES加密方式对字符串进行解密<p>
	 * @param str 字符串<br>
	 * @param key DES密匙<br>
	 * @return 解密字符串<br>
	 */
	public static String desDecode(String str, String key) {
		return StringUtils.isNotEmpty(str) && StringUtils.isNotEmpty(key) ? new String(EncryptUtils.getDESDecode(EncryptUtils.hexStringToByteArray(str), key)) : "";
	}
	
	/**
	 * 根据参数生成DES的Key对象<p>
	 * @param str 字符串<br>
	 * @return Key对象<br>
	 */
	private static Key getDESKey(String key) {
		// 初始化返回值
		Key rtnK = null;
		try {
			KeyGenerator generator = KeyGenerator.getInstance("DES");
			generator.init(new SecureRandom(key.getBytes()));
			rtnK = generator.generateKey();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rtnK;
	}
	
	/**
	 * 对byte数组进行DES加密<p>
	 * @param bytes byte数组<br>
	 * @param key DES密匙<br>
	 * @return DES加密后BYTE数组<br>
	 */
	private static byte[] getDESEncode(byte[] bytes, String key) {
		// 初始化返回对象
		byte[] rtnBt = null;
		try {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, EncryptUtils.getDESKey(key));
			rtnBt = cipher.doFinal(bytes);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rtnBt;
	}
	
	/**
	 * 将DES加密后的BYTE数组进行解密<p>
	 * @param bytes DES数组<br>
	 * @param key DES密匙<br>
	 * @return DES解密后BYTE数组<br>
	 */
	private static byte[] getDESDecode(byte[] bytes, String key) {
		// 初始化返回对象
		byte[] rtnBt = null;
		try {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, EncryptUtils.getDESKey(key));
			rtnBt = cipher.doFinal(bytes);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rtnBt;
	}
	
	/**
	 * 将字节数组转换为HEX进制字符串<p>
	 * @param bytes 字节数组<br>
	 * @return HEX字符串<br>
	 */
	private static String byteArrayToHexString(byte[] bytes) {
		// 初始化返回字符串
		String hexStr = "";
		
		// 获取字符串的BYTE数组
		for(int i = 0; i < bytes.length; i ++) {
			String temp = (java.lang.Integer.toHexString(bytes[i] & 0XFF));
			if(temp.length() == 1)
				hexStr += "0".concat(temp);
			else
				hexStr += temp;
		}
		return hexStr.toUpperCase();
	}
	
	/**
	 * HEX字符串转换为BYTE数组<p>
	 * @param hex 字符串<br>
	 * @return BYTE数组<br>
	 */
	private static byte[] hexStringToByteArray(String hex) {
		byte[] rtnBt = new byte[hex.length() / 2];
		for (int i = 0; i < rtnBt.length; i ++) {
			rtnBt[i] = (byte)Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
		}
		return rtnBt;
	}
	
	/***
	 * 测试方法<p>
	 * @param args <p>
	 * void
	 */
	public static void main(String[] args) {
		String pwd = "123456";
		String jmPwd = EncryptUtils.md5Encode(pwd);
		System.out.println("pwd="+pwd+" \t jmPwd="+jmPwd);
		//pwd=123456 	 jmPwd=e10adc3949ba59abbe56e057f20f883e
	}
}
