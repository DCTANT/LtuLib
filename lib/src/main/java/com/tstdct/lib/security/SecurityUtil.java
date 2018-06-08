package com.tstdct.lib.security;

import android.util.Base64;

public class SecurityUtil {
	public static byte[] encodeDes(String str, String secretkey) {
		return DES.encrypt(str.getBytes(), secretkey);
	}

	public static byte[] decodeDes(byte[] bytes, String secretkey) {
		try {
			return DES.decrypt(bytes, secretkey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new byte[]{};
	}

	public static String encodeBase64(byte[] bytes) {
		return new String(Base64.encodeToString(bytes, Base64.DEFAULT));
	}

	public static byte[] decodeBase64(String str) {
		return Base64.decode(str, Base64.DEFAULT);
	}

	public static String encodeMD5(String str) {
		return MD5Util.encode(str);
	}

	public static String encodePass(String phone, String password)
	{
		long timeStamp = System.currentTimeMillis();
		String encrypted1 = MD5Util.encode(phone + "," + MD5Util.encode(password) + "," + timeStamp);
		String encrypted2 = Base64.encodeToString((phone + "," + timeStamp + "," + encrypted1).getBytes(), 0);

		return encrypted2;
	}
}
