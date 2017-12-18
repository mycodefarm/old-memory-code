package com.jimo.util;

import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyUtils {
	public static String getMD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {

			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
	}

	public static String getDateTime() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(d);
	}

	/**
	 * 获得当前时间之后/前（day为负数）day天的时间
	 * 
	 * @param day
	 * @return
	 */
	public static String getDateTimeAfterDay(Date daybase, int dayNum) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(daybase);
		cd.add(Calendar.DATE, dayNum);
		return (new SimpleDateFormat("yyyy-MM-dd")).format(cd.getTime());
	}

	/**
	 * 取得date和当前时间相隔的天数
	 * 
	 * @param date
	 * @return
	 */
	public static int getDaysBetweenNow(String date) {
		Date d;
		try {
			d = (new SimpleDateFormat("yyyy-MM-dd")).parse(date);
			long now = new Date().getTime();
			if (now < d.getTime()) {
				return -1;
			}
			int days = (int) ((now - d.getTime()) / (1000 * 3600 * 24));
			return days;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static boolean isEmpty(Object obj) {
		if (obj == null || "".equals(obj)) {
			return true;
		}
		return false;
	}
}
