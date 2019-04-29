package com.unis.db.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xuli
 * @date 2019/3/29
 */
public class DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    /**
     * date类型转换为String类型
     *
     * @param date       date Date类型的时间
     * @param formatType formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
     * @return String
     */
    public static String dateToString(Date date, String formatType) {
        return new SimpleDateFormat(formatType).format(date);
    }

    /**
     * long类型转换为String类型
     *
     * @param currentTime currentTime要转换的long类型的时间
     * @param formatType  formatType要转换的string类型的时间格式
     * @return String
     */
    public static String longToString(long currentTime, String formatType) {
        // long类型转成Date类型
        Date date = longToDate(currentTime, formatType);
        // date类型转成String
        return dateToString(date, formatType);

    }

    /**
     * string类型转换为date类型
     *
     * @param strTime    strTime要转换的string类型的时间
     * @param formatType formatType要转换的格式
     *  注意: strTime的时间格式必须要与formatType的时间格式相同
     * @return Date
     */
    public static Date stringToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        try {
            return formatter.parse(strTime);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * long转换为Date类型
     *
     * @param currentTime currentTime要转换的long类型的时间
     * @param formatType  formatType要转换的时间格式
     * @return Date
     */
    public static Date longToDate(long currentTime, String formatType) {
        // 根据long类型的毫秒数生命一个date类型的时间
        Date dateOld = new Date(currentTime);
        // 把date类型的时间转换为string
        String sDateTime = dateToString(dateOld, formatType);
        // 把String类型转换为Date类型
        return stringToDate(sDateTime, formatType);
    }

    /**
     * string类型转换为long类型
     *
     * @param strTime    strTime要转换的String类型的时间
     * @param formatType formatType时间格式
     *                   注意:  strTime的时间格式和formatType的时间格式必须相同
     * @return long
     */
    public static long stringToLong(String strTime, String formatType) {
        // String类型转成date类型
        Date date = stringToDate(strTime, formatType);
        if (date == null) {
            return 0;
        } else {
            // date类型转成long类型
            return dateToLong(date);
        }
    }

    /**
     * date类型转换为long类型
     *
     * @param date date要转换的date类型的时间
     * @return long
     */
    public static long dateToLong(Date date) {
        return date.getTime();
    }
}
