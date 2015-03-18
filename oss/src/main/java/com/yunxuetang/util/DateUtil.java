package com.yunxuetang.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static long  toLongtime(String time) throws ParseException{
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd");
		Date date=dateformat.parse(time);
		return date.getTime();
	}
}
