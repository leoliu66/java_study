package com.leo.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils
        extends org.apache.commons.lang3.time.DateUtils
{
    private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM", "HH:mm", "yyyyMMddHHmmss" };

    public static String getDate()
    {
        return getDate("yyyy-MM-dd");
    }

    public static String getDate(String pattern)
    {
        return DateFormatUtils.format(new Date(), pattern);
    }

    public static String formatDate(Date date, Object... pattern)
    {
        String formatDate = null;
        if ((pattern != null) && (pattern.length > 0)) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    public static String formatDateTime(Date date)
    {
        return formatDate(date, new Object[] { "yyyy-MM-dd HH:mm:ss" });
    }

    public static String getTime()
    {
        return formatDate(new Date(), new Object[] { "HH:mm:ss" });
    }

    public static String getDateTime()
    {
        return formatDate(new Date(), new Object[] { "yyyy-MM-dd HH:mm:ss" });
    }

    public static String getYear()
    {
        return formatDate(new Date(), new Object[] { "yyyy" });
    }

    public static String getMonth()
    {
        return formatDate(new Date(), new Object[] { "MM" });
    }

    public static String getDay()
    {
        return formatDate(new Date(), new Object[] { "dd" });
    }

    public static String getWeek()
    {
        return formatDate(new Date(), new Object[] { "E" });
    }

    public static Date parseDate(Object str)
    {
        if (str == null) {
            return null;
        }
        try
        {
            return parseDate(str.toString(), parsePatterns);
        }
        catch (ParseException e) {}
        return null;
    }

    public static long pastDays(Date date)
    {
        long t = System.currentTimeMillis() - date.getTime();
        return t / 86400000L;
    }

    public static long pastHour(Date date)
    {
        long t = System.currentTimeMillis() - date.getTime();
        return t / 3600000L;
    }

    public static long pastMinutes(Date date)
    {
        long t = System.currentTimeMillis() - date.getTime();
        return t / 60000L;
    }

    public static String formatDateTime(long timeMillis)
    {
        long day = timeMillis / 86400000L;
        long hour = timeMillis / 3600000L - day * 24L;
        long min = timeMillis / 60000L - day * 24L * 60L - hour * 60L;
        long s = timeMillis / 1000L - day * 24L * 60L * 60L - hour * 60L * 60L - min * 60L;
        long sss = timeMillis - day * 24L * 60L * 60L * 1000L - hour * 60L * 60L * 1000L - min * 60L * 1000L - s * 1000L;
        return (day > 0L ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    public static double getDistanceOfTwoDate(Date before, Date after)
    {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / 86400000L;
    }

    public static Date formatStr2Date(String date, String pattern)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try
        {
            return formatter.parse(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Date strToDate(String strDate)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }
}
