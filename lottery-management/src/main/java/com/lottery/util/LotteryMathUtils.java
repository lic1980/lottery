package com.lottery.util;

public class LotteryMathUtils {
	private static int factorial(int max, int min) {
		if (min > max) {
            return 0;
        }
        int result = max;
        for (int i = min; i< max; i++){
            result = i * result;
        }
        return result;
	}
	
	public static int getFeeUnit(int selected, int base) {
		return factorial(base, 1) == 0? 0 : factorial(selected, selected - base + 1)/factorial(base, 1);
	}
	
	public static void main(String[] args) {
		System.out.println(getFeeUnit(7,6));
	}
}
