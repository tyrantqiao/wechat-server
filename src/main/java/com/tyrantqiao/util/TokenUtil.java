package com.tyrantqiao.util;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * @author tyrantqiao [tyrantqiao@gmail.com]
 * @see <a href="https://github.com/tyrantqiao">qiao's github</a>
 */
@Slf4j
public class TokenUtil {
	private static final String TOKEN = "tyrantqiao";

	public static boolean checkToken(String signature, String timestamp, String nonce) {

		String[] strings = new String[]{TOKEN, timestamp, nonce};
		//TODO 字符串排序是排什么的，可以写一下
		Arrays.sort(strings);

		//组合字符串再用sha1加密
		StringBuffer stringBuffer = new StringBuffer();
		for (String s : strings) {
			stringBuffer.append(s);
		}
		String sha1Strings = getSha1(stringBuffer.toString());
		log.info("字典加密后字符串为"+sha1Strings);
		log.info("签名为"+signature);
		return signature.equals(sha1Strings);
	}

	private static String getSha1(String str) {
		if (null == str || 0 == str.length()) {
			return null;
		}
		char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));

			byte[] md = mdTemp.digest();
			int j = md.length;
			char[] buf = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}

			return new String(buf);
		} catch (Exception e) {
			return null;
		}
	}
}
