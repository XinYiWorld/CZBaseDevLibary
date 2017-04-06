package com.xinyi.czbasedevtool.base.utils.time;

import com.xinyi.czbasedevtool.base.bean.TimeDetailBean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:
 * 技巧：关于时间的比较，统一转换成毫秒值，按照毫秒值进行比较。
 */
public class TimeUtil {
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");

    private TimeUtil() {
        throw new AssertionError();
    }

    //------------------------------------------
    // Part1:日期格式化部分
    //       毫秒值格式化
    //------------------------------------------
    /**
     * long time to string
     * 毫秒值---》日期  带格式参数
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String longToDate(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     * 毫秒值---》日期  默认格式参数
     * @param timeInMillis
     * @return
     */
    public static String longToDate(long timeInMillis) {
        return longToDate(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * 毫秒格式化为时：分：秒
     * @param milliSecondTime
     * @return
     */
    public static  String calculateTime(long milliSecondTime) {
        long hour = milliSecondTime /(60*60*1000);
        long minute = (milliSecondTime - hour*60*60*1000)/(60*1000);
        long seconds = (milliSecondTime - hour*60*60*1000 - minute*60*1000)/1000;

        if(seconds >= 60 )
        {
            seconds = seconds % 60;
            minute+=seconds/60;
        }
        if(minute >= 60)
        {
            minute = minute % 60;
            hour  += minute/60;
        }

        String sh = "";
        String sm ="";
        String ss = "";
        if(hour <10) {
            sh = "0" + String.valueOf(hour);
        }else {
            sh = String.valueOf(hour);
        }
        if(minute <10) {
            sm = "0" + String.valueOf(minute);
        }else {
            sm = String.valueOf(minute);
        }
        if(seconds <10) {
            ss = "0" + String.valueOf(seconds);
        }else {
            ss = String.valueOf(seconds);
        }

        return sh +":"+sm+":"+ ss;
    }


    /**
     *   友好的方式显示时间内部类
     */
    public static class FriendlyTimeUtil{
        private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
        };

        private final  static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("yyyy-MM-dd");
            }
        };

        /**
         * 以友好的方式显示时间
         * @param
         * @return
         */
        public  static String friendly_time(long millSec) {
            return friendly_time(dateFormater.get().format(new Date(millSec * 1000L)));
        }

        public  static String friendly_time(String sdate) {
            Date time = null;

            if (TimeZoneUtil.isInEasternEightZones())
                time = toDate(sdate);
            else
                time = TimeZoneUtil.transformTime(toDate(sdate),
                        TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault());

            if (time == null) {
                return "Unknown";
            }
            String ftime = "";
            Calendar cal = Calendar.getInstance();

            // 判断是否是同一天
            String curDate = dateFormater2.get().format(cal.getTime());
            String paramDate = dateFormater2.get().format(time);
            if (curDate.equals(paramDate)) {
                int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
                if (hour == 0)
                    ftime = Math.max(
                            (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                            + "分钟前";
                else
                    ftime = hour + "小时前";
                return ftime;
            }

            long lt = time.getTime() / 86400000;
            long ct = cal.getTimeInMillis() / 86400000;
            int days = (int) (ct - lt);
            if (days == 0) {
                int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
                if (hour == 0)
                    ftime = Math.max(
                            (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                            + "分钟前";
                else
                    ftime = hour + "小时前";
            } else if (days == 1) {
                ftime = "昨天";
            } else if (days == 2) {
                ftime = "前天 ";
            } else if (days > 2 && days < 31) {
                ftime = days + "天前";
            } else if (days >= 31 && days <= 2 * 31) {
                ftime = "一个月前";
            } else if (days > 2 * 31 && days <= 3 * 31) {
                ftime = "2个月前";
            } else if (days > 3 * 31 && days <= 4 * 31) {
                ftime = "3个月前";
            } else {
                ftime = dateFormater2.get().format(time);
            }
            return ftime;
        }

        public static  Date toDate(String sdate) {
            return toDate(sdate, dateFormater.get());
        }

        public static  Date toDate(String sdate, SimpleDateFormat dateFormater) {
            try {
                return dateFormater.parse(sdate);
            } catch (Exception e) {
                return null;
            }
        }
    }


    //------------------------------------------
    // Part2: 当前时间操作
    //        当前时间格式化  年月日
    //------------------------------------------
    /**
     * get current time in milliseconds
     * 当前时间的毫秒值
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     * 当前时间的  默认格式化日期
     * @return
     */
    public static String getCurrentTimeInString() {
        return longToDate(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     * 当前时间的  带参的格式化日期
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return longToDate(getCurrentTimeInLong(), dateFormat);
    }

    /**
     * 得到当前时间的详细时间封装类
     * @return
     */
    public static TimeDetailBean getCurrentTimeDetail() {
        Calendar c = Calendar.getInstance();

        return new TimeDetailBean(c.get(Calendar.YEAR)
                , c.get(Calendar.MONTH) + 1
                , c.get(Calendar.DAY_OF_MONTH)
                , c.get(Calendar.HOUR_OF_DAY)
                , c.get(Calendar.MINUTE)
                , c.get(Calendar.SECOND)
                , c.get(Calendar.DAY_OF_WEEK)
        );
    }


}
