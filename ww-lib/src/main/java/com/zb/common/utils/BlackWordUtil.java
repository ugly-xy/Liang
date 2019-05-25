package com.zb.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sohu.blackword.JianceManager;
import com.sohu.blackword.util.ErrorInfo;
import com.zb.common.Constant.ReCode;
import com.zb.core.web.ReMsg;

public class BlackWordUtil {

	static final Logger log = LoggerFactory.getLogger(BlackWordUtil.class);

	public static boolean isBlack(String str) {
		return JianceManager.getInstance().getBlackmanager().isBlackWord(str);
	}

	public static boolean isBlackNumber(String str) {
		return JianceManager.getInstance().getBlackmanager().isBlackNumber(str);
	}

	public static String blackWord(String str) {
		return JianceManager.getInstance().FlagRed(str,
				JianceManager.BLACK_WORD, new ErrorInfo());
	}

	public static ReMsg blackWordStatus(String str) {
		ErrorInfo ei = new ErrorInfo();
		String re = JianceManager.getInstance().FlagRed(str,
				JianceManager.BLACK_WORD, ei);
		if (ei.getErrorCode() == 0) {
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL, re);
	}

	public static ReMsg redWordStatus(String str) {
		ErrorInfo ei = new ErrorInfo();
		String re = JianceManager.getInstance().FlagRed(str,
				JianceManager.RED_WORD, ei);
		if (ei.getErrorCode() == 0) {
			return new ReMsg(ReCode.OK);
		}
		return new ReMsg(ReCode.FAIL, re);
	}

	public static String redWord(String str) {
		return JianceManager.getInstance().FlagRed(str, JianceManager.RED_WORD,
				new ErrorInfo());
	}

	public static void main(String[] args) {
		System.out.println(BlackWordUtil.isBlack("鲍文科"));
		System.out.println(BlackWordUtil.isBlack("d习近平a"));
		System.out.println(BlackWordUtil.isBlack("习金平"));
		System.out.println(BlackWordUtil.isBlack("李克强"));
		System.out.println(BlackWordUtil.blackWord("大习近平"));
		System.out.println(BlackWordUtil.blackWord("习精平"));
		System.out.println(BlackWordUtil.blackWord("习金平真牛逼"));
		System.out.println(BlackWordUtil.blackWord("啊啊啊真牛逼"));
		System.out.println(BlackWordUtil.redWord("li克强真牛逼"));
		System.out.println(BlackWordUtil.redWord("李克强真牛逼"));
		ReMsg rs = BlackWordUtil.blackWordStatus("习近平真牛逼");
		System.out.println(rs.getCode() + " " + rs.getData());

		ReMsg r1 = BlackWordUtil.blackWordStatus("真牛逼");
		System.out.println(r1.getCode() + " " + r1.getData());
	}
}
