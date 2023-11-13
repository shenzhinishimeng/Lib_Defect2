package com.catail.lib_commons.utils;

import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Crypto {
	static final String LOG_TAG = MD5Crypto.class.getSimpleName();

	// MD5计算
	public static byte[] encryptForByte(byte[] src) throws Exception {
		MessageDigest mD = MessageDigest.getInstance("MD5");
		byte[] re = mD.digest(src);

		// 打印MD5结果
		StringBuffer sb = new StringBuffer();
		for (byte b : re) {
			sb.append(String.format("%X", b));
		}
		Log.d(LOG_TAG, "MD5处理结果＿" + sb.toString());
		Log.d(LOG_TAG, "length: " + re.length);
		return re;
	}

	public static String md5(String str) {

		if (str == null) {
			return null;
		}

		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e) {

			return str;
		}

        byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}
}
