package com.social.ttinviter.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	
	private DateUtil() {}
	
	/**
	 * 取得目前的日期時間
	 */
	public static Date getNow() {
		return GregorianCalendar.getInstance().getTime();
	}
	
	
	/**
	 * 根據輸入日期，加減時分秒
	 * @param date
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date addTimeHMS(final Date date, final int hour, final int minute, final int second) {
		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		if (hour != 0) {
			cal.add(Calendar.HOUR, hour);
		}
		if (minute != 0) {
			cal.add(Calendar.MINUTE, minute);
		}
		if (second != 0) {
			cal.add(Calendar.SECOND, second);
		}
		return cal.getTime();
	}
	
	public static String msDateFormat(long millis) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(millis);
		String formattedDateTime = dateFormat.format(date);
		
		return formattedDateTime;
	}
}
