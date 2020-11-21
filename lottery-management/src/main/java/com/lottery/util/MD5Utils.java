package com.lottery.util;

import java.security.MessageDigest;

public class MD5Utils {
	private static final String CHARSET = "UTF-8";
	private static final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f' };
	private static final String ALGORITHM = "MD5";
	
	public static String encryptWithMD5(String initialString){
        MessageDigest md5 = null;
        
        try {
            byte[] strTemp = initialString.getBytes(CHARSET);//得到字节数组
            md5 = MessageDigest.getInstance(ALGORITHM);//得到MessageDigest 对象
            md5.reset();
            md5.update(strTemp);
            byte[] md = md5.digest();
            int k = md.length;
            char[] chars = new char[k*2];
            int n = 0;
            for(int i = 0; i < k ;i++){
                byte bytes0 = md[i];
                chars[n++] = hexDigits[bytes0 >>> 4 & 0xf];
                chars[n++] = hexDigits[bytes0 & 0xf];
            }
            return new String(chars);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
