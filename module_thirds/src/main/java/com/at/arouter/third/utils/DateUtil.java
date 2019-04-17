package com.at.arouter.third.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Joker on 2017/6/1.
 */

public class DateUtil {

    /**
     * yyyy-MM-dd KK:mm aa
     * Locale.ENGLISH，am/pm
     * Locale.CHINESE，上午/下午
     */
    public static String longToDateAs12(String time, String formatDate, int type) {
        SimpleDateFormat aa = new SimpleDateFormat(formatDate, type == 0 ? Locale.ENGLISH : Locale.CHINESE);
        try {
            Date date = stringToDate(time,"yyyy-MM-dd HH:mm:ss");
            return aa.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    //formatDate 日期格式
    public static String getStringDate(String formatDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatDate);
        Date curDate = new Date(System.currentTimeMillis());
        String date = formatter.format(curDate);
        return date;
    }

    //获取long date
    public long getLongDate() {
        Date date = new Date();
        Long currentTime = date.getTime();
        return currentTime;
    }

    // date类型转换为String类型
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    // long类型转换为String类型
    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public String longToString(long currentTime, String formatType) {
        String strTime = null;
        try {
            Date date = longToDate(currentTime, formatType); // long类型转成Date类型
            strTime = dateToString(date, formatType); // date类型转成String
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strTime;
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    // long转换为Date类型
    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    // string类型转换为long类型
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType) {
        long currentTime = 0;
        try {
            Date date = stringToDate(strTime, formatType); // String类型转成date类型
            if (date == null) {
                return 0;
            } else currentTime = dateToLong(date); // date类型转成long类型
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentTime;
    }

    // date类型转换为long类型
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    public String getWeekName(String date) {
        String weekName = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                weekName = "星期日";
                break;
            case 2:
                weekName = "星期一";
                break;
            case 3:
                weekName = "星期二";
                break;
            case 4:
                weekName = "星期三";
                break;
            case 5:
                weekName = "星期四";
                break;
            case 6:
                weekName = "星期五";
                break;
            case 7:
                weekName = "星期六";
                break;
        }
        return weekName;
    }

}
