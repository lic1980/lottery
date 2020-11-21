package com.lottery.util;

public class SecurityUtils {
	private final static String SUFFIX = "WAN";
    private final static String PREFIX = "2500";
    
	public static String encryptPassword(String original) {
		return MD5Utils.encryptWithMD5(PREFIX + original + SUFFIX);
	}
}
