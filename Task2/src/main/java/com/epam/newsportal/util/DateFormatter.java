package com.epam.newsportal.util;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.Formatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter implements Formatter<Date> {

    private final ReloadableResourceBundleMessageSource messageSource;
    private final String format;

    public DateFormatter(ReloadableResourceBundleMessageSource messageSource, String format) {
        this.messageSource = messageSource;
        this.format = format;
    }

    @Override
    public Date parse(String s, Locale locale) throws ParseException {
        return getDateFormatter(locale).parse(s);
    }

    @Override
    public String print(Date date, Locale locale) {
        return getDateFormatter(locale).format(date);
    }

    private DateFormat getDateFormatter(Locale locale) {
        DateFormat dateFormat = new SimpleDateFormat(messageSource.getMessage(format, null, locale), locale);
        dateFormat.setLenient(false);
        return dateFormat;
    }
}
