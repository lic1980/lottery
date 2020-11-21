package com.lottery.util;

import xyz.downgoon.snowflake.Snowflake;

public class IdUtils {
	public static long	 generateId() {
		Snowflake snowflake = new Snowflake(0, 0);
		return snowflake.nextId();
	}
	
	public static void main(String[] args) {
		System.out.println(IdUtils.generateId());
	}
}
