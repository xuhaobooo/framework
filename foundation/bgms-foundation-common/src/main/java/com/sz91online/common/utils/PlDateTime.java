package com.sz91online.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期、时间工具类
 *
 * @author Paul Xu.
 * @since 1.0
 */
public class PlDateTime {
	private static final Logger LOG = LoggerFactory.getLogger(PlDateTime.class);

	private static Map<String, SimpleDateFormat> dateFormats = new HashMap<>();

	public static final long MILISECONDS_IN_A_DAY = 1000 * 60 * 60 * 24;

	public static final String FORMAT_FUL = "yyyy-MM-dd HH:mm:ss";
	
	public static final String FORMAT_SHORT = "yyyy-MM-dd";

	/**
	 * public static Date convertDateByString(String strDate, String format) { if
	 * (!StringUtils.isBlank(strDate)) { try { SimpleDateFormat formatter = new
	 * SimpleDateFormat(format); return formatter.parse(strDate); } catch
	 * (ParseException e) { LOG.error("Error while parse date", e); } } return new
	 * Date(); }
	 * 
	 * /**
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date parseDateByW3C(String strDate) {
		String formatW3C = "yyyy-MM-dd'T'HH:mm:ss";
		if (strDate != null && !strDate.equals("")) {
			SimpleDateFormat formatter = new SimpleDateFormat(formatW3C);
			try {
				return formatter.parse(strDate);
			} catch (ParseException e) {
				LOG.error("Error while parse date", e);
			}
		}
		return null;
	}

	public static Date parseDateFull(String strDate) {
		String formatW3C = "yyyy-MM-dd HH:mm:ss";
		if (strDate != null && !strDate.equals("")) {
			SimpleDateFormat formatter = new SimpleDateFormat(formatW3C);
			try {
				return formatter.parse(strDate);
			} catch (ParseException e) {
				LOG.error("Error while parse date", e);
			}
		}
		return null;
	}

	public static Date parseDate(String strDate, String dateFormat) {
		if (strDate != null && !strDate.equals("")) {
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
			try {
				return formatter.parse(strDate);
			} catch (ParseException e) {
				LOG.error("Error while parse date", e);
			}
		}
		return null;
	}

	public static String formatDate(Date date, String dateFormat) {
		return formatDate(date, dateFormat, null);
	}

	public static String formatDate(Date date, String dateFormat, TimeZone timezone) {
		if (date == null) {
			return "";
		}

		SimpleDateFormat simpleDateFormat = getDateFormat(dateFormat);
		if (timezone != null) {
			simpleDateFormat.setTimeZone(timezone);
		}

		return simpleDateFormat.format(date);
	}

	private static SimpleDateFormat getDateFormat(String format) {
		SimpleDateFormat dateFormat = dateFormats.get(format);
		if (dateFormat != null) {
			return dateFormat;
		} else {
			dateFormat = new SimpleDateFormat(format);
			dateFormats.put(format, dateFormat);
			return dateFormat;
		}
	}

	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	public static String getMonthDay(Date date) {
		String p = "MM-dd";
		SimpleDateFormat format = new SimpleDateFormat(p);
		return format.format(date);
	}

	// 将日期格式转换为星期
	public String dateToWeek(String str) {
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFm.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		dateFm = new SimpleDateFormat("EEEE");
		return dateFm.format(date);

	}

}
