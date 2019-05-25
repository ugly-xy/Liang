package com.we.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.we.common.Constant.Const;

public class DateUtil {

	public static final long MINUTE = 60 * 1000;
	public static final long HOUR = 60 * MINUTE;
	public static final long DAY = 24 * HOUR;
	public static final long MONTH = 30 * DAY;
	public static final long YEAR = 365 * DAY;

	private static final SimpleDateFormat SDF_DATE_LONG = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat SDF_DATE_FULL_N = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	private static final SimpleDateFormat SDF_DATE_SHORT_CN = new SimpleDateFormat("yyyy年MM月dd日");
	private static final SimpleDateFormat SDF_DATE_LONG_CN = new SimpleDateFormat("yyyy年MM月dd日HH点mm分ss秒");
	public static final SimpleDateFormat SDF_DATE_SHORT = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat SDF_DATE_DIAN = new SimpleDateFormat("yyyy.MM.dd");
	private static final SimpleDateFormat SDF_DATE_DIAN_SHART = new SimpleDateFormat("MM.dd");
	private static final SimpleDateFormat SDF_DATE_NULL = new SimpleDateFormat("yyyy MM dd");
	private static final SimpleDateFormat SDF_DATE_ZPIE = new SimpleDateFormat("dd/MM/yyyy");
	private static final SimpleDateFormat SDF_DATE_DPIE = new SimpleDateFormat("yyyy/MM/dd");
	private static final SimpleDateFormat SDF_NO_SPLIT_DATE_LONG_NO_YEAR = new SimpleDateFormat("MMddHHmmss");

	private static final SimpleDateFormat SDF_NO_SPLIT_DATE_SHORT = new SimpleDateFormat("yyyyMMdd");

	public static Date millisToDate(long millis) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(millis);
		return c.getTime();
	}

	/**
	 * Date to format: yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatShort(Date date) {
		return SDF_DATE_SHORT.format(date);
	}

	/**
	 * @param date
	 * @return formate: yyyy.MM.dd
	 */
	public static String dateFormatDian(Date date) {
		return SDF_DATE_DIAN.format(date);
	}

	/**
	 * @param date
	 * @return formate: yyyy.MM.dd
	 */
	public static String dateFormatDianShort(Date date) {
		return SDF_DATE_DIAN_SHART.format(date);
	}

	/**
	 * @param date
	 * @return formate: yyyy MM dd
	 */
	public static String dateFormatNull(Date date) {
		return SDF_DATE_NULL.format(date);
	}

	/**
	 * @param date
	 * @return formate: yyyy/MM/dd
	 */
	public static String dateFormatZPie(Date date) {
		return SDF_DATE_ZPIE.format(date);
	}

	/**
	 * @param date
	 * @return formate: yyyy/MM/dd
	 */
	public static String dateFormatDPie(Date date) {
		return SDF_DATE_DPIE.format(date);
	}

	/**
	 * Date to format: yyyyMMddHHmmssSSS
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatyyyyMMddHHmmssSSS(Date date) {
		return SDF_DATE_FULL_N.format(date);
	}

	/**
	 * Date to format: yyyyMMdd
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatyyyyMMdd(Date date) {
		return SDF_NO_SPLIT_DATE_SHORT.format(date);
	}

	public static int toYearWeekyyyyWeek(Calendar c) {
		return c.get(Calendar.YEAR) * 100 + c.get(Calendar.WEEK_OF_YEAR);
	}

	public static int toYearMonthyyyyMM(Calendar c) {
		return c.get(Calendar.YEAR) * 100 + c.get(Calendar.MONTH) + 1;
	}

	public static int toDayyyyMMdd(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return toDayyyyMMdd(c);
	}

	public static int toDayyyyMMdd(Long millis) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(millis);
		return toDayyyyMMdd(c);
	}

	public static int toDayyyyMMdd(Calendar c) {
		return c.get(Calendar.YEAR) * 10000 + (c.get(Calendar.MONTH) + 1) * 100 + c.get(Calendar.DAY_OF_MONTH);
	}

	public static int curDay() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR) * 10000 + (c.get(Calendar.MONTH) + 1) * 100 + c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Date to format: MMddHHmmss
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatMMddHHmmss(Date date) {
		return SDF_NO_SPLIT_DATE_LONG_NO_YEAR.format(date);
	}

	/**
	 * Date to format: yyyy年MM月dd日
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatShortCN(Date date) {
		return SDF_DATE_SHORT_CN.format(date);
	}

	/**
	 * Date to format: yyyy年MM月dd日HH点mm分ss秒
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatLongCN(Date date) {
		return SDF_DATE_LONG_CN.format(date);
	}

	/**
	 * Date to format: yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatLong(Date date) {
		return SDF_DATE_LONG.format(date);
	}

	public static String dateFormat(Date date, String format) {
		if (null == format)
			throw new IllegalArgumentException("format is null");
		SimpleDateFormat dateFm = new SimpleDateFormat(format);
		return dateFm.format(date);
	}

	public static Date convertDate(String strDate, String format) throws ParseException {
		if ("MMddHHmmss".equals(format)) {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.MONTH, Integer.parseInt(strDate.substring(0, 2)) - 1);
			c.set(Calendar.DATE, Integer.parseInt(strDate.substring(2, 4)));
			c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(strDate.substring(4, 6)));
			c.set(Calendar.MINUTE, Integer.parseInt(strDate.substring(6, 8)));
			c.set(Calendar.SECOND, Integer.parseInt(strDate.substring(8, 10)));
			return c.getTime();
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.parse(strDate);
	}

	public static long convertDate(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(year, month, day);
		return c.getTimeInMillis();
	}

	public static long convertDate(int year, int month, int day, int hour, int minute) {
		Calendar c = Calendar.getInstance();
		c.set(year, month, day, hour, minute);
		return c.getTimeInMillis();
	}

	public static long convertDate(int year, int month, int day, int hour, int minute, int second) {
		Calendar c = Calendar.getInstance();
		c.set(year, month, day, hour, minute, second);
		return c.getTimeInMillis();
	}

	public static Date getMonthFirstDay() {
		Calendar c = Calendar.getInstance();
		return getMonthFirstDay(c.getTime());
	}

	public static Date getLastMonthFirstDay() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		return getMonthFirstDay(c.getTime());
	}

	public static Date getMonthFirstDay(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 0, 0, 0);
		return c.getTime();
	}

	public static long getTodayZeroTime() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTimeInMillis();
	}

	public static long getWeekZeroTime() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return c.getTimeInMillis();
	}

	public static long getZeroTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTimeInMillis();
	}

	/** 时间戳转时分秒 */
	public static String getNomalString(long timestamp) {
		long days = timestamp / Const.DAY;
		long hours = timestamp % Const.DAY;
		long minutes = hours % Const.HOUR;
		long seconds = minutes % Const.MINUTE;
		String res = "";
		if (days > 0) {
			res = res.concat(Long.toString(days)).concat("天");
		}
		if (hours / Const.HOUR > 0) {
			res = res.concat(Long.toString(hours / Const.HOUR)).concat("时");
		}
		if (minutes / Const.MINUTE > 0) {
			res = res.concat(Long.toString(minutes / Const.MINUTE)).concat("分");
		}
		if (seconds / Const.SECOND > 0) {
			res = res.concat(Long.toString(seconds / Const.SECOND)).concat("秒");
		}
		return res;
	}

	public static void main(String[] args) {
		// Date date = convertDate("2012-05-10", "yyyy-MM-dd");
		// Long time = DateUtil.getTodayZeroTime();
		// System.out.println(DateUtil.dateFormatLong(DateUtil.millisToDate(time)));
		// System.out.println(DateUtil.toDayyyyMMdd(date));
		// System.out.println(date);
		// date = convertDate("0405000000", "MMddHHmmss");
		// System.out.println(date.getTime());
		// System.out.println(DateUtil.getZeroTime(new Date()));
		// System.out.println(DateUtil.getWeekZeroTime());
		//
		// System.out.println(DateUtil.convertDate(2017, 1, 17, 0, 0, 0));
		// System.out.println(getNomalString(432534423));

		String date = DateUtil.dateFormatyyyyMMdd(DateUtil.getMonthFirstDay());
		String stDate = DateUtil.dateFormatShortCN(new Date());
		System.out.println(date);
		System.out.println(stDate);
	}
}
