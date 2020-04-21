package com.hifo.dataoperation.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间工具
 *
 * @author jinzhichen
 */
public class DateTimeUtils {
    public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public final static String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    public final static String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public final static String DEFAULT_DATE_FORMAT_SHORT = "yyMMdd";
    public final static String DEFAULT_TIME_FORMAT_SHORT = "HHmmss";
    public final static String DEFAULT_DATETIME_FORMAT_SHORT = "yyMMddHHmmss";

    public static LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalDate();
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static LocalTime dateToLocalTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalTime();
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static Date localDateToDate(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static Date localTimeToDate(Date targetDate, LocalTime localTime) {
        LocalDate localDate = dateToLocalDate(targetDate);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static String localDateToStr(LocalDate value) {
        return value.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
    }

    public static String localDateTimeToStr(LocalDateTime value) {
        return value.format(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT));
    }

    public static String localTimeToStr(LocalTime value) {
        return value.format(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT));
    }

    public static LocalDate strToLocalDate(String value) {
        return LocalDate.parse(value, DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
    }

    public static LocalDateTime localDateToLocalDateTime(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return dateToLocalDateTime(Date.from(instant));
    }

    public static LocalDateTime strToLocalDateTime(String value) {
        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT));
    }

    public static LocalTime strToLocalTime(String value) {
        return LocalTime.parse(value, DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT));
    }

    public static Date strToDate(String value) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
        try {
            return sdf.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String dateToStr(Date value) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
        return sdf.format(value);
    }

    public static String localDateTimeToYmdStr(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
    }

    /**
     * 获取某天开始时间
     */
    public static LocalDateTime getDayBeginTime(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate().atStartOfDay();
    }

    /**
     * 获取某天结束时间
     */
    public static LocalDateTime getDayEndTime(LocalDateTime localDateTime) {
        localDateTime.atZone(ZoneId.systemDefault());
        return localDateTime.toLocalDate().atTime(23, 59, 59, 999);
    }

    /**
     * 获取某月开始时间
     */
    public static LocalDateTime getMonthBeginTime(LocalDateTime localDateTime) {
        YearMonth yearMonth = YearMonth.of(localDateTime.getYear(), localDateTime.getMonth());
        LocalDate localDate = yearMonth.atDay(1);
        return localDate.atStartOfDay();
    }

    /**
     * 获取某月结束时间
     */
    public static LocalDateTime getMonthEndTime(LocalDateTime localDateTime) {
        YearMonth yearMonth = YearMonth.of(localDateTime.getYear(), localDateTime.getMonth());
        LocalDate endOfMonth = yearMonth.atEndOfMonth();
        return endOfMonth.atTime(23, 59, 59, 999);
    }

}
