package com.xinyi.czbasedevtool.base.bean;

/**
 * author:Created by ChenZhang on 2016/6/29 0029.
 * function:
 */
public class TimeDetailBean {
    private int year,month,day,hour,minute,second;
    private String weekday;

    public TimeDetailBean(int year, int month, int day, int hour, int minute, int second, int weekday) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        switch (weekday){
            case 1:
                this.weekday = "星期日";
                break;
            case 2:
                this.weekday = "星期一";
                break;
            case 3:
                this.weekday = "星期二";
                break;
            case 4:
                this.weekday = "星期三";
                break;
            case 5:
                this.weekday = "星期四";
                break;
            case 6:
                this.weekday = "星期五";
                break;
            case 7:
                this.weekday = "星期六";
                break;


        }

    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "TimeDetailBean{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", hour=" + hour +
                ", minute=" + minute +
                ", second=" + second +
                ", weekday='" + weekday + '\'' +
                '}';
    }
}
