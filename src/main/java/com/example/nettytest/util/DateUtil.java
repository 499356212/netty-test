package com.example.nettytest.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateUtil
 *
 * @author ljw
 * @since 2019/9/26 09:54:06
 */
public class DateUtil {

    /** 格式：yyyy-MM-dd HH:mm:ss */
    public static final String DATE_FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    /** 格式：yyyy-MM-dd HH:mm:ss.SSS */
    public static final String yyyyMMddHHmmssSSS = "yyyy-MM-dd HH:mm:ss.SSS";

    private static DateFormat format;

    public static String now(String format){
        return format(new Date(), format);
    }

    public static String format(Date date, String pattern){
        format = new SimpleDateFormat(pattern);
        return format.format(date);
    }
}
