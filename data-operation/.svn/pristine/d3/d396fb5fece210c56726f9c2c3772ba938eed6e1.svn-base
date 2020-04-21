package com.hifo.dataoperation.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理工具类
 *
 * @author whc
 * @date 2019/3/19 9:32
 */
public class DateUtil {

    /**
     * 默认的展示格式
     */
    private final static String FORMAT = "yyyy-MM-dd hh:mm:ss";

    /**
     * 把日期改成默认格式
     *
     * @param date 日期
     * @return 2019-03-18 07:54
     */
    public static String defaultStr(Date date) {
        return new SimpleDateFormat(FORMAT).format(date);
    }

    /**
     * 把MySQL数据库的日期转换成默认格式
     *
     * @param str 2019-03-18 07:54:38
     * @return 2019-03-18 07:54:38
     */
    public static String defaultStr(String str) {
        try {
            return defaultStr(new Date(Long.parseLong(str)));
        } catch (Exception e) {
            return str.substring(0, str.lastIndexOf("."));
        }
    }
}
