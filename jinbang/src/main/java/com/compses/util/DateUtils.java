package com.compses.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

	public static String formatDate(Date date, String formater) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat(formater);
		if (date == null) {
			return null;
		} else {
			String datestring = bartDateFormat.format(date);
			return datestring;
		}
	}
	
	public static String formatDate(Date date, String formater,Locale locale) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat(formater,locale);
		if (date == null) {
			return null;
		} else {
			String datestring = bartDateFormat.format(date);
			return datestring;
		}
	}

	public static Date getDate(String value, String formater) {
		Date date = null;
		if (value == null) {
			return new Date();
		}
		SimpleDateFormat fordate = new SimpleDateFormat(formater);
		try {
			date = fordate.parse(value);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return date;
	}
	
	public static Date nextMonth(Date date){
		Date next=new Date(date.getTime());
		int month=date.getMonth();
		if(month==11){
			next.setYear(date.getYear()+1);
			next.setMonth(0);
		}else{
			next.setMonth(date.getMonth()+1);
		}
		return next;
	}
	
	public static Date preMonth(Date date){
		Date pre=new Date(date.getTime());
		int month=date.getMonth();
		if(month==0){
			pre.setMonth(11);
			pre.setYear(date.getYear()-1);
		}else{
			pre.setMonth(date.getMonth()-1);
		}
		return pre;
	}
	
	
}
