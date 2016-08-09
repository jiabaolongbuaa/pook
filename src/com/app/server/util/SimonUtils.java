package com.app.server.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.sf.json.JSONObject;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

public class SimonUtils {

	public static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");


	/**
	 * 判断两个日期是否为同一天
	 * 
	 * @param date1
	 * @param date2
	 * @return 同一天则返回true,反之false
	 */
	public static boolean isSameDay(Date date1, Date date2) {

		if (date1 == null || date2 == null) {
			return false;
		}
		if (date1 == date2) {
			return true;
		}
		String ds1 = sdf.format(date1);
		String ds2 = sdf.format(date2);
		if (ds1.equals(ds2)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 返回一个时间距其表示的日期0时的毫秒数
	 * 
	 * @param date1
	 * @return
	 */
	public static int msAfterMidNight(Date date) {
		if (date == null) {
			return 0;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DAY_OF_YEAR, day);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return (int) (date.getTime() - calendar.getTime().getTime());

	}

	/**
	 * 返回一个时间距其表示的日期的下一天0时的毫秒数
	 * 
	 * @param date1
	 * @return
	 */
	public static int msBeforeMidNight(Date date) {
		if (date == null) {
			return 0;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DAY_OF_YEAR, day);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return (int) (calendar.getTime().getTime() - date.getTime());

	}

	public static long getTodayZero() {
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_YEAR);

		calendar.set(Calendar.DAY_OF_YEAR, day);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return (calendar.getTimeInMillis());
	}

	public static String encryptString(final String content, final String three) {
		StringBuilder sb = new StringBuilder();
		for (char c : content.toCharArray()) {
			int idx = three.indexOf(c);
			if (idx >= 0) {
				if (idx % 2 == 0) {
					sb.append(three.charAt(idx + 1));
				} else {
					sb.append(three.charAt(idx - 1));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {

	String content = "eyJSHdHdUM5fQ0mPHSI6kTAz7CJSHdHdUM5fT0JKIqpwhWbsfQ==";
		String three = "RHL7uwVdxb";
		System.err.println(dateTimeToString(getDateDaysAgo(new Date(), 2)));
		System.err.println(getNowDate());
		System.err.println(getNowTime());

		System.err.println(getDayStart(getDateDaysAgo(new Date(), 2)));
		System.err.println(getDayEnd(getDateDaysAgo(new Date(), 2)));

		// String str =
		// ",.!，，D_NAME。！；‘’”“《》**dfs  #$%^&()-+1431221中国123漢字かどうかのjavaを決定";

		String str = "苍×井×空";
		str = removeAllPunctuationsAndWhitespaces(str);


		System.err.println(str);

		JSONObject object = JSONObject.fromObject("{\"key\":\"111\"}");

		String key = object.getString("key");

		System.err.println(key);

		String a = "1234.5";
		String b = "1111";
		System.err.println("here");
		for (String aa : a.split("\\.")) {
			System.err.println(aa);
		}
		for (String aa : b.split("\\.")) {
			System.err.println(aa);
		}


	}


	/**
	 * 对字符串进行MD5加密
	 * 
	 * @param sourceStr
	 * @return
	 */
	public static String MD5Encode(String sourceStr) {
		String signStr = null;
		try {
			byte[] bytes = sourceStr.getBytes("utf-8");
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(bytes);
			byte[] md5Byte = md5.digest();
			if (md5Byte != null) {
				signStr = HexBin.encode(md5Byte);
			}
		} catch (NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
		}
		return signStr;

	}

	/**
	 * 返回当前日期的格式化输出
	 * 
	 * @return
	 */
	public static String getNowDate() {
		return dateToString(new Date());
	}

	/**
	 * 返回当前时间的格式化输出
	 * 
	 * @return
	 */
	public static String getNowTime() {
		return dateTimeToString(new Date());
	}

	/**
	 * 将Date 格式化为 yyyy-MM-dd 输出
	 * 
	 * @param date
	 * @return 格式化后的字符串
	 */
	public static String dateToString(Date date) {
		return sdf.format(date);
	}

	/**
	 * 
	 * 将Date 格式化为yyyy-MM-dd hh:mm:ss输出
	 * 
	 * @param date
	 * @return 格式化后的字符串
	 */
	public static String dateTimeToString(Date date) {
		return dateTimeFormat.format(date);
	}

	/**
	 * 返回 days天前的Date
	 * 
	 * @param days
	 * @return
	 */
	public static Date getDateDaysAgo(final Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, -days);
		return cal.getTime();
	}

	/**
	 * 返回 days天后的Date
	 * 
	 * @param days
	 * @return
	 */
	public static Date getDateDaysAfter(final Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, days);
		return cal.getTime();
	}

	public static Date getDayEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.SECOND, -1);

		Date end = calendar.getTime();

		return end;
	}

	public static Date getDayStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		Date start = calendar.getTime();

		return start;
	}



	/**
	 * L：字母； M：标记符号（一般不会单独出现）； Z：分隔符（比如空格、换行等）； S：符号（比如数学符号、货币符号等）；
	 * N：数字（比如阿拉伯数字、罗马数字等）； C：其他字符
	 * 
	 * 删除所有标点，分隔符，空白符
	 * 
	 * @param src
	 * @return
	 */
	public static String removeAllPunctuationsAndWhitespaces(final String src) {

		String newSrc = src.replaceAll("[\\pP\\pS\\pM\\pZ‘’“”]", "");
		// src = src.replaceAll("[\\pS]", "");
		return newSrc;
	}
}
