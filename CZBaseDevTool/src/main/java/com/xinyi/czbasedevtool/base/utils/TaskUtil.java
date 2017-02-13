package com.xinyi.czbasedevtool.base.utils;

import android.os.Handler;

import com.xinyi.czbasedevtool.base.manager.TwoThreadManager;

/**
 * 任务执行类
 * author:Created by ChenZhang on 2016/7/5 0005.
 * function:
 */
public class TaskUtil {
    private static Handler mHandler;
    static {
        mHandler = new Handler();
    }
    private TaskUtil(){
        ExceptionUtil.throwRunmtimeExcpetionInPrivateConstructor(TaskUtil.class);
    }

    public static void postDelay(Runnable runnable,long delayedSeconds){
        mHandler.postDelayed(runnable,delayedSeconds);
    }
}
