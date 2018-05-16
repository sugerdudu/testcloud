package com.sixi.oaplatform.common.kits;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA
 *
 * @author 喵♂呜
 * Created on 2017/6/13 10:52.
 */
public class DateKit {
    private DateKit() {}

    public static final String SIMPLE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static ThreadLocal<SimpleDateFormat> time = ThreadLocal
            .withInitial(() -> new SimpleDateFormat(SIMPLE_FORMAT));
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    private static ThreadLocal<SimpleDateFormat> date = ThreadLocal
            .withInitial(() -> new SimpleDateFormat(DATE_FORMAT));

    public static final String SIMPLE_FORMAT_WITHOUT_YEAR = "MM-dd";
    public static final String SIMPLE_FORMAT_WITHOUT_DAY = "yyyy-MM";
    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATETIME_MINUTE = "yyyy-MM-dd HH:mm";

    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";

    public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final long ONE_DAY = 60 * 60 * 24;

    public static final DateTimeFormatter LOCAL_DATE_YEAR_MONTH = DateTimeFormatter.ofPattern("yyyy-MM", Locale.CHINA);

    /**
     * 格式化日期格式为字符串
     *
     * @param date
     * @param formate
     * @return
     */
    public static String formate(Date date, String formate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formate);
        return dateFormat.format(date);
    }

    /**
     * 格式化当前日期格式为字符串
     *
     * @param formate
     * @return
     */
    public static String formateNow(String formate) {
        return formate(new Date(), formate);
    }


    /**
     * 解析日期
     *
     * @param str
     *         解析字符串为Date
     * @return {@link Date}
     * @throws ParseException
     *         解析异常
     */
    public static Date parseTime(String str) throws ParseException {
        return time.get().parse(str);
    }

    /**
     * 解析日期
     *
     * @param str
     *         解析字符串为Date
     * @return {@link Date}
     * @throws ParseException
     *         解析异常
     */
    public static Date parseDate(String str) throws ParseException {
        return date.get().parse(str);
    }

    /**
     * 解析日期
     *
     * @param str
     *         解析字符串为Date
     * @return {@link Date}
     */
    public static Date parseLong(String str) throws ParseException {
        return new Date(Long.parseLong(str));
    }

    @SneakyThrows
    public static Date parseNothrows(String str) {
        return parse(str);
    }

    /**
     * 解析日期
     *
     * @param str
     *         解析字符串为Date
     * @return {@link Date}
     */
    public static Date parse(String str) throws ParseException {
        if (StrKit.isBlank(str)) {return null;}
        try {
            return parseTime(str);
        } catch (ParseException e) {
            try {
                return parseDate(str);
            } catch (ParseException ex) {
                try {
                    return parseLong(str);
                } catch (NumberFormatException exc) {
                    throw new IllegalArgumentException(String.format("日期 %s 格式错误",str));
                }
            }
        }
    }

    /**
     * 转字符串到日期
     * 异常返回null
     * @param date
     * @return
     */
    public static Date stringToDate(String date) {
        try {
            if(StringUtils.isBlank(date)) {
                return null;
            }
            if(date.length() == 10) {
                return new SimpleDateFormat(SIMPLE_FORMAT).parse(date);
            }
            else if(date.length() == 7) {
                return new SimpleDateFormat(SIMPLE_FORMAT_WITHOUT_DAY).parse(date);
            }
            else if(date.length() == 5) {
                return new SimpleDateFormat(SIMPLE_FORMAT_WITHOUT_YEAR).parse(date);
            }
            else {
                return new SimpleDateFormat(FORMAT_DATETIME).parse(date);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 格式化日期
     *
     * @param date
     *         可接受{@link Date}和{@link Long}
     * @return {@link String}
     */
    public static String format(Object date) {
        return time.get().format(date);
    }

    /**
     * 格式化日期
     *
     * @param date
     *         数值型字符串
     * @return {@link String}
     */
    public static String formatLong(Object date) {
        if (date instanceof String) {
            return format(Long.valueOf(date.toString()));
        }
        return format(date);
    }

    public static Integer diffMonth(Date beginTime, Date endTime) {
        return (DateUtils.toCalendar(endTime).get(Calendar.YEAR) - DateUtils.toCalendar(beginTime).get(Calendar.YEAR)) * 12
                + DateUtils.toCalendar(endTime).get(Calendar.MONTH) - DateUtils.toCalendar(beginTime).get(Calendar.MONTH);
    }
}