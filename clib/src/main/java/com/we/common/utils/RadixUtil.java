package com.we.common.utils;

import java.util.Stack;

/** 进制转换类 */
public class RadixUtil {
	private static char[] array = "23456789ABCDEFGHIJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz".toCharArray();
	private static String numStr = "23456789ABCDEFGHIJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";

	// 10进制转为其他进制，除留取余，逆序排列
	public static String _10_to_N(long number, int N) {
		Long rest = number;
		Stack<Character> stack = new Stack<Character>();
		StringBuilder result = new StringBuilder(0);
		while (rest != 0) {
			stack.add(array[new Long((rest % N)).intValue()]);
			rest = rest / N;
		}
		for (; !stack.isEmpty();) {
			result.append(stack.pop());
		}
		return result.length() == 0 ? "0" : result.toString();

	}

	// 其他进制转为10进制，按权展开
	public static long N_to_10(String number, int N) {
		char ch[] = number.toCharArray();
		int len = ch.length;
		long result = 0;
		if (N == 10) {
			return Long.parseLong(number);
		}
		long base = 1;
		for (int i = len - 1; i >= 0; i--) {
			int index = numStr.indexOf(ch[i]);
			result += index * base;
			base *= N;
		}

		return result;
	}

}
