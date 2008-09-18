package org.gameye.psp.image.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: feipy
 * </p>
 * 
 * @author Majian
 */
public class DateHelper {
	
	public static String getCurrDateStr() {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		StringBuffer datestr = new StringBuffer("");
		datestr.append(ts.toString().substring(0, 4));
		datestr.append(ts.toString().substring(5, 7));
		datestr.append(ts.toString().substring(8, 10));
		return datestr.toString();
	}

	public static Timestamp getCurrentTimestamp() {
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(Calendar.getInstance().getTime());
		Timestamp time = Timestamp.valueOf(date);
		return time;
	}

	/**
	 * 将String类型日期转化成java.sql.Date类型"2003-01-01"格式
	 * 
	 * @param str
	 *            String
	 * @return Date
	 */
	public static java.sql.Date stringToSqlDate(String str) {
		if (str == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		try {
			date = sdf.parse(str);
		} catch (Exception e) {
			return null;
		}
		return new java.sql.Date(date.getTime());
	}

	// 返回格式化的日期
	public static String getFullDateTime(String sDate) {
		try {
			String formater = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat format = new SimpleDateFormat(formater);
			Date date = format.parse(sDate);
			return format.format(date);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}
	
	public static String getDateTime(String sDate) {
		try {
			String formater = "yyyy-MM-dd";
			SimpleDateFormat format = new SimpleDateFormat(formater);
			Date date = format.parse(sDate);
			return format.format(date);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	public static java.util.Date parseDate(String str, String format) {

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");

		if (!StringUtils.isEmpty(format))
			sdf.applyPattern(format);

		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 把日期值转换成 long 类型值
	 * 
	 * @author y.nie
	 * @param dateStr
	 *            类似于 2008-2-23
	 * @return
	 */
	public static long parseDate2Long(String dateStr) {
		try {
			Date date = new Date(dateStr);
			return date.getTime();
		} catch (Exception e) {
		}
		return 0L;
	}

	public static String formatDate(java.util.Date date, String format) {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		if (!StringUtils.isEmpty(format))
			sdf.applyPattern(format);

		return sdf.format(date);
	}

	public static long getTimeToNum() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = df.format(new Date());
		return Long.valueOf(time);
	}

	public static Date getNumToTime(long date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			return df.parse(Long.toString(date));
		} catch (ParseException e) {
			return null;
		}
	}

	public static void main(String args[]) throws Exception {

	}
}
