package com.gamgyul.gams.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TIME_MAXIMUM {
	public static final int SEC = 60;
	public static final int MIN = 60;
	public static final int HOUR = 24;
	public static final int DAY = 30;
	public static final int MONTH = 12;
	
	static SimpleDateFormat kor_format = new SimpleDateFormat("yyyy년 M월 d일 E");
	static SimpleDateFormat origin_format = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat StoDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String ConvertTime(Date date) {
		long curTime = System.currentTimeMillis();
		long regTime = date.getTime();
		long diffTime = (curTime - regTime) / 1000;
		
		String time = null;
		
		if(diffTime < TIME_MAXIMUM.SEC) {
			time = "방금 전";
		}else if((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
			time = diffTime+"분 전";
		}else if((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
			time = diffTime+"시간 전";
		}else if((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
			time = diffTime+"일 전";
		}else {
			time = origin_format.format(date);
		}
		return time;
	}
	
	public static String ConvertTime(String sdate) throws ParseException {
		Date date = StoDate.parse(sdate);
		long curTime = System.currentTimeMillis();
		long regTime = date.getTime();
		long diffTime = (curTime - regTime) / 1000;
		
		String time = null;
		
		if(diffTime < TIME_MAXIMUM.SEC) {
			time = "방금 전";
		}else if((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
			time = diffTime+"분 전";
		}else if((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
			time = diffTime+"시간 전";
		}else if((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
			time = diffTime+"일 전";
		}else {
			time = origin_format.format(date);
		}
		return time;
	}
}
