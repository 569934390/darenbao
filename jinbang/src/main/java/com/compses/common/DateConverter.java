package com.compses.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class DateConverter implements Converter<String, Date> {
	private static final DateFormat DATEFORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static final DateFormat TIMEFORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private DateFormat dateFormat;
	private boolean allowEmpty = true;


	public DateConverter(String pattern, boolean allowEmpty) {
		this.dateFormat = StringUtils.hasText(pattern)?new SimpleDateFormat(pattern):null;
		this.allowEmpty = allowEmpty;
	}

	@Override
	public Date convert(String text) {
		if (this.allowEmpty && !StringUtils.hasText(text)) {
			// Treat empty String as null value.
			return null;
		} else {
			try {
				if (this.dateFormat != null)
					return this.dateFormat.parse(text);
				else {
					if (text.contains(":"))
						return TIMEFORMAT.parse(text);
					else
						return DATEFORMAT.parse(text);
				}
			} catch (ParseException ex) {
				throw new IllegalArgumentException("Could not parse date: "
						+ ex.getMessage(), ex);
			}
		}
	}

}
