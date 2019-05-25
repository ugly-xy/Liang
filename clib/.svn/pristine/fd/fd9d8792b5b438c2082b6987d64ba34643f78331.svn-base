package com.we.common.utils;

/** 邀请码类 */
public class ShareCodeUtil {

	public static int SCALE_57 = 57;

	public static String toSerialCode(long uid) {
		return RadixUtil._10_to_N(uid, SCALE_57);
	}

	public static long codeToId(String code) {
		return RadixUtil.N_to_10(code, SCALE_57);
	}

	public static void main(String[] args) {
		long a = 100001;
		String _10_to_N = toSerialCode(a);
		long n_to_10 = codeToId(_10_to_N);
		System.out.println(a + "---" + _10_to_N + "---" + n_to_10);

		a = 100000000002L;
		_10_to_N = toSerialCode(a);
		n_to_10 = codeToId(_10_to_N);
		System.out.println(a + "---" + _10_to_N + "---" + n_to_10);

	}

}