package com.zb.common.utils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PokerUtil {

	public static final int SPADE = 4;// 黑桃
	public static final int HEATS = 3;// 红桃
	public static final int CLUS = 2;// 梅花
	public static final int DIAMOND = 1;// 方块

	public static int[] poker;// 扑克牌 /10为大小 %10为颜色
	public static int[] poker2;// 扑克牌 /10为大小 %10为颜色
	static {
		poker = new int[] { // A最小的扑克

				11, 12, 13, 14, // A

				21, 22, 23, 24, // 2

				31, 32, 33, 34, // 3

				41, 42, 43, 44, // 4

				51, 52, 53, 54, // 5

				61, 62, 63, 64, // 6

				71, 72, 73, 74, // 7

				81, 82, 83, 84, // 8

				91, 92, 93, 94, // 9

				101, 102, 103, 104, // 10

				111, 112, 113, 114, // J

				121, 122, 123, 124, // Q

				131, 132, 133, 134// k
		};

		poker2 = new int[] { // A最大的扑克

				21, 22, 23, 24, // 2

				31, 32, 33, 34, // 3

				41, 42, 43, 44, // 4

				51, 52, 53, 54, // 5

				61, 62, 63, 64, // 6

				71, 72, 73, 74, // 7

				81, 82, 83, 84, // 8

				91, 92, 93, 94, // 9

				101, 102, 103, 104, // 10

				111, 112, 113, 114, // J

				121, 122, 123, 124, // Q

				131, 132, 133, 134, // k

				141, 142, 143, 144// A
		};
	}

	public static int[] getPoker() {
		return poker.clone();
	}

	public static List<Integer> getShufflePoker() {
		List<Integer> pokers = IntStream.of(poker).boxed().collect(Collectors.toList());
		Collections.shuffle(pokers);
		return pokers;
	}

	public static int[] getPoker2() {
		return poker2.clone();
	}

	public static List<Integer> getShufflePoker2() {
		List<Integer> pokers = IntStream.of(poker2).boxed().collect(Collectors.toList());
		Collections.shuffle(pokers);
		return pokers;
	}
}
