package com.fycstart.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author fyc
 * @description: TODO
 * @date 2019/5/22上午 11:11
 */
public class DateUtil {


    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";


    /**
     * 将日期转换为字符串
     *
     * @param date   DATE日期
     * @param format 转换格式
     * @return 字符串日期
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }
}
