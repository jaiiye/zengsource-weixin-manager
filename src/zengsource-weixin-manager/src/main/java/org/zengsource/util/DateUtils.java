/**
 * 
 */
package org.zengsource.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * @author snzeng
 * @since 6.0
 */
public abstract class DateUtils extends org.apache.commons.lang.time.DateUtils {

	public static final String FULL_CN = "yyyy年MM月dd日 HH:mm:ss SSS";
	public static final String FULL_CN2 = "yyyy-MM-dd HH:mm:ss SSS";
	public static final String FULL_CN3 = "yyyy-MM-dd HH:mm:ss";
	public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";

	public static Date now() {
		return new Date();
	}

	public static String format(Date date, String format) {
		if (date == null) {
			return null;
		}
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	public static String getDatemark() {
		return format(now(), "yyyyMMddHHmmssSSS");
	}

	public static Date parse(String string, String format) {
		if (StringUtils.isBlank(string)) {
			return null;
		}
		DateFormat df = new SimpleDateFormat(format);
		try {
			return df.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date add(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}

	public static boolean isSameDate(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return false;
		}
		return format(date1, "yyyy-MM-dd").equals(format(date2, "yyyy-MM-dd"));
	}

	// Test
	public static void main(String[] args) {
		Date now = new Date();
		System.out.println(now);
		System.out.println(add(now, 41));
	}
}
