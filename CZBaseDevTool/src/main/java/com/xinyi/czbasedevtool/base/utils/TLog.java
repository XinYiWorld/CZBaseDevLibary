package com.xinyi.czbasedevtool.base.utils;

import android.util.Log;

/**
 * 日志管理工具类
 * author:Created by ChenZhang on 2016/6/21 0021.
 * function:
 */
public class TLog {
    private static  String LOG_TAG = "TLog";
    public static boolean DEBUG = true;

    private TLog() {
        throw new RuntimeException("TLog can't init");
    }

    private static String getRuntimeClassName(){
       //TODO
       return "TLog";
    }


    public static final void e(String tag,String log) {
        if (DEBUG)
            Log.e(tag, "" + log);
    }


    public static final void v(String tag,String log) {
        if (DEBUG)
            Log.v(tag, log);
    }

    public static final void w(String tag,String log) {
        if (DEBUG)
            Log.w(tag, log);
    }

    public static final void i(String tag,String log) {
        if (DEBUG)
            Log.i(tag, log);
    }

    public static final void d(String tag,String log) {
        if (DEBUG)
            Log.d(tag, log);
    }


    public static final void e(String log) {
        LOG_TAG = getRuntimeClassName();
        if (DEBUG)
            TLog.e(LOG_TAG, "" + log);
    }


    public static final void v(String log) {
        LOG_TAG = getRuntimeClassName();
        if (DEBUG)
            Log.v(LOG_TAG, log);
    }

    public static final void w(String log) {
        LOG_TAG = getRuntimeClassName();
        if (DEBUG)
            Log.w(LOG_TAG, log);
    }

    public static final void i(String log) {
        LOG_TAG = getRuntimeClassName();
        if (DEBUG)
            Log.i(LOG_TAG, log);
    }

    public static final void d(String log) {
        LOG_TAG = getRuntimeClassName();
        if (DEBUG)
            Log.d(LOG_TAG, log);
    }
}
